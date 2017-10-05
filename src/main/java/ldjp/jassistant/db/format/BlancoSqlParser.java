/*
 * blanco Framework
 * Copyright (C) 2004-2006 WATANABE Yoshinori
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package ldjp.jassistant.db.format;

import java.util.ArrayList;

import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.db.format.valueobject.BlancoSqlToken;

/**
 * Class BlancoSqlParser is used to parser the SQL
 *
 * @author WATANABE Yoshinori (a-san) : original version at 2005.07.04.
 * @author IGA Tosiki : marge into blanc Framework at 2005.07.04
 */
public class BlancoSqlParser {

    /**
     * String to parse
     */
    private String before;

    /**
     * Dealing character
     */
    private char chr;

    /**
     * Current position
     */
    private int pst;

    /**
     * Combo of two characters
     */
    private static final String[] twoCharacterSymbol = { "!=", "<>", "<=", ">=", "||" };

    /**
     * Construction function
     */
    public BlancoSqlParser() {
    }

    /**
     * Judge the char if it is a space
     *
     * @param argChar
     * @return
     */
    public static boolean isSpace(final char argChar) {
        return argChar == ' ' || argChar == '\t' || argChar == '\n'
                || argChar == '\r' || argChar == 65535;
    }

    /**
     * Check the characters if they are a letter
     *
     * @param argChar
     * @return result
     */
    public static boolean isLetter(final char argChar) {
        if (isSpace(argChar)) {
            return false;
        }
        if (isDigit(argChar)) {
            return false;
        }
        if (isSymbol(argChar)) {
            return false;
        }
        return true;
    }

    /**
     * Judge if it is a number character
     *
     * @param argChar
     * @return
     */
    public static boolean isDigit(final char argChar) {
        return '0' <= argChar && argChar <= '9';
    }

    /**
     * Judge if it is a mark character
     *
     * @param argChar
     * @return
     */
    public static boolean isSymbol(final char argChar) {
        switch (argChar) {
        case '"': // double quote
        case '?': // question mark
        case '%': // percent
        case '&': // ampersand
        case '\'': // quote
        case '(': // left paren
        case ')': // right paren
        case '|': // vertical bar
        case '*': // asterisk
        case '+': // plus sign
        case ',': // comma
        case '-': // minus sign
        case '.': // period
        case '/': // solidus
        case ':': // colon
        case ';': // semicolon
        case '<': // less than operator
        case '=': // equals operator
        case '>': // greater than operator
            return true;
        default:
            return false;
        }
    }

    /**
     * Go to the next token
     * @return next token
     */
    BlancoSqlToken nextToken() {
        int start_pos = pst;
        if (pst >= before.length()) {
            pst++;
            return new BlancoSqlToken(BlancoSqlTokenConstants.END, PJConst.EMPTY,
                    start_pos);
        }

        chr = before.charAt(pst);

        if (isSpace(chr)) {
            String workString = PJConst.EMPTY;
            for (;;) {
                workString += chr;
                chr = before.charAt(pst);
                if (!isSpace(chr)) {
                    return new BlancoSqlToken(BlancoSqlTokenConstants.SPACE,
                            workString, start_pos);
                }
                pst++;
                if (pst >= before.length()) {
                    return new BlancoSqlToken(BlancoSqlTokenConstants.SPACE,
                            workString, start_pos);
                }
            }
        } else if (chr == ';') {
            pst++;
            return new BlancoSqlToken(BlancoSqlTokenConstants.SYMBOL, ";",
                    start_pos);
        } else if (isDigit(chr)) {
            String s = PJConst.EMPTY;
            while (isDigit(chr) || chr == '.') {
                s += chr;
                pst++;

                if (pst >= before.length()) {
                    // break when the length out of range
                    break;
                }

                chr = before.charAt(pst);
            }
            return new BlancoSqlToken(BlancoSqlTokenConstants.VALUE, s,
                    start_pos);
        } else if (isLetter(chr)) {
            String s = PJConst.EMPTY;
            while (isLetter(chr) || isDigit(chr) || chr == '.') {
                s += chr;
                pst++;
                if (pst >= before.length()) {
                    break;
                }

                chr = before.charAt(pst);
            }
            for (int i = 0; i < BlancoSqlConstants.SQL_RESERVED_WORDS.length; i++) {
                if (s
                        .compareToIgnoreCase(BlancoSqlConstants.SQL_RESERVED_WORDS[i]) == 0) {
                    return new BlancoSqlToken(BlancoSqlTokenConstants.KEYWORD,
                            s, start_pos);
                }
            }
            return new BlancoSqlToken(BlancoSqlTokenConstants.NAME, s,
                    start_pos);
        }
        // single line comment
        else if (chr == '-') {
            pst++;
            char ch2 = before.charAt(pst);
            if (ch2 != '-') {
                return new BlancoSqlToken(BlancoSqlTokenConstants.SYMBOL, "-",
                        start_pos);
            }
            pst++;
            String s = "--";
            for (;;) {
                chr = before.charAt(pst);
                s += chr;
                pst++;
                if (chr == '\n' || pst >= before.length()) {
                    return new BlancoSqlToken(BlancoSqlTokenConstants.COMMENT,
                            s, start_pos);
                }
            }
        }
        // comment with multiple lines
        else if (chr == '/') {
            pst++;
            char ch2 = before.charAt(pst);
            if (ch2 != '*') {
                return new BlancoSqlToken(BlancoSqlTokenConstants.SYMBOL, "/",
                        start_pos);
            }

            String s = "/*";
            pst++;
            int ch0 = -1;
            for (;;) {
                ch0 = chr;
                chr = before.charAt(pst);
                s += chr;
                pst++;
                if (ch0 == '*' && chr == '/') {
                    return new BlancoSqlToken(BlancoSqlTokenConstants.COMMENT,
                            s, start_pos);
                }
            }
        } else if (chr == '\'') {
            pst++;
            String s = "'";
            for (;;) {
                chr = before.charAt(pst);
                s += chr;
                pst++;
                if (chr == '\'') {
                    return new BlancoSqlToken(BlancoSqlTokenConstants.VALUE, s,
                            start_pos);
                }
            }
        } else if (chr == '\"') {
            pst++;
            String s = "\"";
            for (;;) {
                chr = before.charAt(pst);
                s += chr;
                pst++;
                if (chr == '\"') {
                    return new BlancoSqlToken(BlancoSqlTokenConstants.NAME, s,
                            start_pos);
                }
            }
        }

        else if (isSymbol(chr)) {
            // mark
            String s = String.valueOf(chr);
            pst++;
            if (pst >= before.length()) {
                return new BlancoSqlToken(BlancoSqlTokenConstants.SYMBOL, s,
                        start_pos);
            }
            // judge that if it is a mark with two characters
            char ch2 = before.charAt(pst);
            for (int i = 0; i < twoCharacterSymbol.length; i++) {
                if (twoCharacterSymbol[i].charAt(0) == chr
                        && twoCharacterSymbol[i].charAt(1) == ch2) {
                    pst++;
                    s += ch2;
                    break;
                }
            }
            return new BlancoSqlToken(BlancoSqlTokenConstants.SYMBOL, s,
                    start_pos);
        } else {
            pst++;
            return new BlancoSqlToken(BlancoSqlTokenConstants.UNKNOWN,
                    String.valueOf(chr), start_pos);
        }
    }

    /**
     * Translate the SQL string into a token list
     *
     * @param argSql SQL string
     * @return Token list
     */
    public ArrayList<BlancoSqlToken> parse(final String argSql) {
        pst = 0;
        before = argSql;

        final ArrayList<BlancoSqlToken> list = new ArrayList<BlancoSqlToken>();
        for (;;) {
            final BlancoSqlToken token = nextToken();
            if (token.getType() == BlancoSqlTokenConstants.END) {
                break;
            }

            list.add(token);
        }
        return list;
    }
}
