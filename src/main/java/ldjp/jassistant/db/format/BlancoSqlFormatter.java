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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Stack;

import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.db.format.valueobject.BlancoSqlToken;

/**
 * The Class of SQL format tool according to SQL rules
 * and it is based on the correct input SQL
 *
 * Please refer to the URL below to see the SQL rules used in this class
 * http://homepage2.nifty.com/igat/igapyon/diary/2005/ig050613.html
 *
 * @author Yoshinori WATANABE (a-san) : original version at 2005.07.04.
 * @author Tosiki Iga : marge into blanc Framework at 2005.07.04
 */
public class BlancoSqlFormatter {
    private final BlancoSqlParser fParser = new BlancoSqlParser();

    private BlancoSqlRule fRule = null;

    /**
     * The stack to remember if the context between brackets is a function
     */
    private Stack<Boolean> functionBracket = new Stack<Boolean>();

    /**
     * Construction function
     *
     * @param argRule the format rule of SQL
     */
    public BlancoSqlFormatter(final BlancoSqlRule argRule) {
        fRule = argRule;
    }

    /**
     * Format the SQL
     * Return code will be remained
     *
     * @param argSql SQL to format
     * @return formated SQL
     * @throws BlancoSqlFormatterException
     */
    public String format(final String argSql)
            throws BlancoSqlFormatterException {
        functionBracket.clear();
        try {
            boolean isSqlEndsWithNewLine = false;
            if (argSql.endsWith("\n")) {
                isSqlEndsWithNewLine = true;
            }

            List<BlancoSqlToken> list = fParser.parse(argSql);

            list = format(list);

            // the string of format result
            String after = PJConst.EMPTY;
            for (int index = 0; index < list.size(); index++) {
                BlancoSqlToken token = list.get(index);
                after += token.getStr();
            }

            if (isSqlEndsWithNewLine) {
                after += "\n";
            }

            return after;
        } catch (Exception ex) {
            final BlancoSqlFormatterException sqlException = new BlancoSqlFormatterException(
                    ex.toString());
            sqlException.initCause(ex);
            throw sqlException;
        }
    }

    /**
     * Format the SQL with the SQL rule which is already defined
     *
     * @param argList SQL token list to format
     * @return formated SQL token list
     */
    private List<BlancoSqlToken> format(final List<BlancoSqlToken> argList) {

        // delete the space before or after the SQL
        BlancoSqlToken token = argList.get(0);
        if (token.getType() == BlancoSqlTokenConstants.SPACE) {
            argList.remove(0);
        }

        token = argList.get(argList.size() - 1);
        if (token.getType() == BlancoSqlTokenConstants.SPACE) {
            argList.remove(argList.size() - 1);
        }

        for (int index = 0; index < argList.size(); index++) {
            token = argList.get(index);
            if (token.getType() == BlancoSqlTokenConstants.KEYWORD) {
                switch (fRule.keyword) {
                case BlancoSqlRule.KEYWORD_NONE:
                    break;
                case BlancoSqlRule.KEYWORD_UPPER_CASE:
                    token.setStr(token.getStr().toUpperCase());
                    break;
                case BlancoSqlRule.KEYWORD_LOWER_CASE:
                    token.setStr(token.getStr().toLowerCase());
                    break;
                }
            }
        }

        // delete the space before or after the mark
        for (int index = argList.size() - 1; index >= 1; index--) {
            token = argList.get(index);
            BlancoSqlToken prevToken = argList.get(index - 1);
            if (token.getType() == BlancoSqlTokenConstants.SPACE
                    && (prevToken.getType() == BlancoSqlTokenConstants.SYMBOL || prevToken
                            .getType() == BlancoSqlTokenConstants.COMMENT)) {
                argList.remove(index);
            } else if ((token.getType() == BlancoSqlTokenConstants.SYMBOL || token
                    .getType() == BlancoSqlTokenConstants.COMMENT)
                    && prevToken.getType() == BlancoSqlTokenConstants.SPACE) {
                argList.remove(index - 1);
            } else if (token.getType() == BlancoSqlTokenConstants.SPACE) {
                token.setStr(" ");
            }
        }

        // Treat the two words set as one word
        // For example,"INSERT INTO", "ORDER BY" and etc
        for (int index = 0; index < argList.size() - 2; index++) {
            BlancoSqlToken t0 = argList.get(index);
            BlancoSqlToken t1 = argList.get(index + 1);
            BlancoSqlToken t2 = argList.get(index + 2);

            if (t0.getType() == BlancoSqlTokenConstants.KEYWORD
                    && t1.getType() == BlancoSqlTokenConstants.SPACE
                    && t2.getType() == BlancoSqlTokenConstants.KEYWORD) {
                if (((t0.getStr().equalsIgnoreCase("ORDER") || t0
                        .getStr().equalsIgnoreCase("GROUP")) && t2
                        .getStr().equalsIgnoreCase("BY"))) {
                    t0.setStr(t0.getStr() + " " + t2.getStr());
                    argList.remove(index + 1);
                    argList.remove(index + 1);
                }
            }

            // For Oracle begin 2007/10/24 A.Watanabe
            // Treat the Outer join of Oracle as one operator
            if (t0.getStr().equals("(") && t1.getStr().equals("+")
                    && t2.getStr().equals(")")) {
                t0.setStr("(+)");
                argList.remove(index + 1);
                argList.remove(index + 1);
            }
            // For Oracle end
        }

        // organize the indent
        int indent = 0;
        // remember positions of brackets' the indent
        Stack<Integer> bracketIndent = new Stack<Integer>();
        BlancoSqlToken prev = new BlancoSqlToken(BlancoSqlTokenConstants.SPACE," ");
        boolean encounterBetween = false;
        for (int index = 0; index < argList.size(); index++) {
            token = argList.get(index);
            if (token.getType() == BlancoSqlTokenConstants.SYMBOL) {
                // increase indent by one and go to next line after the mark '('
                if (token.getStr().equals("(")) {
                    functionBracket.push(fRule.isFunction(prev.getStr()) ? Boolean.TRUE
                                    : Boolean.FALSE);
                    bracketIndent.push(new Integer(indent));
                    indent++;
                    index += insertReturnAndIndent(argList, index + 1, indent);
                }
                // increase indent by one and go to next line before and after the mark ')'
                else if (token.getStr().equals(")")) {
                    indent = bracketIndent.pop().intValue();
                    index += insertReturnAndIndent(argList, index, indent);
                    functionBracket.pop();
                }
                // add return to next line after mark ','
                else if (token.getStr().equals(",")) {
                    index += insertReturnAndIndent(argList, index, indent);
                } else if (token.getStr().equals(";")) {
                    indent = 0;
                    index += insertReturnAndIndent(argList, index, indent);
                }
            } else if (token.getType() == BlancoSqlTokenConstants.KEYWORD) {
                // increase indent by one and go to next line
                if (token.getStr().equalsIgnoreCase("DELETE")
                        || token.getStr().equalsIgnoreCase("SELECT")
                        || token.getStr().equalsIgnoreCase("UPDATE")) {
                    indent += 2;
                    index += insertReturnAndIndent(argList, index + 1, indent);
                }
                // increase indent by one and go to next line after the keywords
                // [INSERT,INTO,CREATE,CREATE,DROP,TRUNCATE,TABLE,CASE]
                if (token.getStr().equalsIgnoreCase("INSERT")
                        || token.getStr().equalsIgnoreCase("INTO")
                        || token.getStr().equalsIgnoreCase("CREATE")
                        || token.getStr().equalsIgnoreCase("DROP")
                        || token.getStr().equalsIgnoreCase("TRUNCATE")
                        || token.getStr().equalsIgnoreCase("TABLE")
                        || token.getStr().equalsIgnoreCase("CASE")) {
                    indent++;
                    index += insertReturnAndIndent(argList, index + 1, indent);
                }
                // decrease indent by one and go to next line before the keywords
                // then  increase the indent by one and go to the next line after the keywords
                // [FROM,WHERE,SET,ORDER BY,GROUP BY,HAVING]
                if (token.getStr().equalsIgnoreCase("FROM")
                        || token.getStr().equalsIgnoreCase("WHERE")
                        || token.getStr().equalsIgnoreCase("SET")
                        || token.getStr().equalsIgnoreCase("ORDER BY")
                        || token.getStr().equalsIgnoreCase("GROUP BY")
                        || token.getStr().equalsIgnoreCase("HAVING")) {
                    index += insertReturnAndIndent(argList, index, indent - 1);
                    index += insertReturnAndIndent(argList, index + 1, indent);
                }
                // decrease indent by one and go to next line before the keywords
                // then  increase the indent by one and go to the next line after the keywords
                // [VALUES]
                if (token.getStr().equalsIgnoreCase("VALUES")) {
                    indent--;
                    index += insertReturnAndIndent(argList, index, indent);
                }
                // decrease indent by one and go to next line before the keyword[END]
                if (token.getStr().equalsIgnoreCase("END")) {
                    indent--;
                    index += insertReturnAndIndent(argList, index, indent);
                }
                // go to next line before the keywords[OR,THEN,ELSE]
                if (token.getStr().equalsIgnoreCase("OR")
                        || token.getStr().equalsIgnoreCase("THEN")
                        || token.getStr().equalsIgnoreCase("ELSE")) {
                    index += insertReturnAndIndent(argList, index, indent);
                }
                // go to next line before the keywords[ONE,USING]
                if (token.getStr().equalsIgnoreCase("ON")
                        || token.getStr().equalsIgnoreCase("USING")) {
                    index += insertReturnAndIndent(argList, index, indent + 1);
                }
                // set indent to 0 and go to next line before the keywords
                // [UNION,INTERSECT,EXCEPT]
                if (token.getStr().equalsIgnoreCase("UNION")
                        || token.getStr().equalsIgnoreCase("INTERSECT")
                        || token.getStr().equalsIgnoreCase("EXCEPT")) {
                    indent -= 2;
                    index += insertReturnAndIndent(argList, index, indent);
                    index += insertReturnAndIndent(argList, index + 1, indent);
                }
                if (token.getStr().equalsIgnoreCase("BETWEEN")) {
                    encounterBetween = true;
                }
                if (token.getStr().equalsIgnoreCase("AND")) {
                    // the content after the keyword[AND] which is located after [BETWEEN]
                    // will not go to the next line
                    if (!encounterBetween) {
                        index += insertReturnAndIndent(argList, index, indent);
                    }
                    encounterBetween = false;
                }
            } else if (token.getType() == BlancoSqlTokenConstants.COMMENT) {
                if (token.getStr().startsWith("/*")) {
                    // comment with multiple lines will go to the next line at the tail
                    index += insertReturnAndIndent(argList, index + 1, indent);
                }
            }
            prev = token;
        }

        // Deal the items in brackets range with special handle
        for (int index = argList.size() - 1; index >= 4; index--) {
            if (index >= argList.size()) {
                continue;
            }

            BlancoSqlToken t0 = argList.get(index);
            BlancoSqlToken t1 = argList.get(index - 1);
            BlancoSqlToken t2 = argList.get(index - 2);
            BlancoSqlToken t3 = argList.get(index - 3);
            BlancoSqlToken t4 = argList.get(index - 4);

            if (t4.getStr().equalsIgnoreCase("(")
                    && t3.getStr().trim().equalsIgnoreCase(PJConst.EMPTY)
                    && t1.getStr().trim().equalsIgnoreCase(PJConst.EMPTY)
                    && t0.getStr().equalsIgnoreCase(")")) {
                t4.setStr(t4.getStr() + t2.getStr() + t0.getStr());
                argList.remove(index);
                argList.remove(index - 1);
                argList.remove(index - 2);
                argList.remove(index - 3);
            }
        }

        // add space before and after the token
        for (int index = 1; index < argList.size(); index++) {
            prev = argList.get(index - 1);
            token = argList.get(index);

            if (prev.getType() != BlancoSqlTokenConstants.SPACE
                    && token.getType() != BlancoSqlTokenConstants.SPACE) {
                // continue when comma
                if (prev.getStr().equals(",")) {
                    continue;
                }
                // continue when it is a function name
                if (fRule.isFunction(prev.getStr())
                        && token.getStr().equals("(")) {
                    continue;
                }
                argList.add(index, new BlancoSqlToken(
                        BlancoSqlTokenConstants.SPACE, " "));
            }
        }

        return argList;
    }

    /**
     * insert enter and  indents
     *
     * @param argList
     * @param argIndex
     * @param argIndent
     * @return 1 when space is inserted and 0 when space is replaced
     */
    private int insertReturnAndIndent(final List<BlancoSqlToken> argList, final int argIndex,
            final int argIndent) {
        if (functionBracket.contains(Boolean.TRUE))
            return 0;
        try {
            // string to insert
            String s = "\n";
            // add no line code if there is one before
            BlancoSqlToken prevToken = argList.get(argIndex - 1);
            if (prevToken.getType() == BlancoSqlTokenConstants.COMMENT
                    && prevToken.getStr().startsWith("--")) {
                s = PJConst.EMPTY;
            }
            for (int index = 0; index < argIndent; index++) {
                s += fRule.indentString;
            }

            BlancoSqlToken token = argList.get(argIndex);
            if (token.getType() == BlancoSqlTokenConstants.SPACE) {
                token.setStr(s);
                return 0;
            }

            token = argList.get(argIndex - 1);
            if (token.getType() == BlancoSqlTokenConstants.SPACE) {
                token.setStr(s);
                return 0;
            }
            argList.add(argIndex, new BlancoSqlToken(
                    BlancoSqlTokenConstants.SPACE, s));
            return 1;
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    public static void main(final String[] args) throws Exception {
        // rule to set
        final BlancoSqlRule rule = new BlancoSqlRule();
        rule.keyword = BlancoSqlRule.KEYWORD_UPPER_CASE;
        rule.indentString = "    ";
        String[] mySqlFuncs = {
                // getNumericFunctions
                "ABS", "ACOS", "ASIN", "ATAN", "ATAN2", "BIT_COUNT", "CEILING",
                "COS", "COT", "DEGREES", "EXP",
                "FLOOR",
                "LOG",
                "LOG10",
                "MAX",
                "MIN",
                "MOD",
                "PI",
                "POW",
                "POWER",
                "RADIANS",
                "RAND",
                "ROUND",
                "SIN",
                "SQRT",
                "TAN",
                "TRUNCATE",
                // getStringFunctions
                "ASCII", "BIN", "BIT_LENGTH", "CHAR", "CHARACTER_LENGTH",
                "CHAR_LENGTH", "CONCAT", "CONCAT_WS", "CONV", "ELT",
                "EXPORT_SET", "FIELD", "FIND_IN_SET", "HEX,INSERT", "INSTR",
                "LCASE", "LEFT", "LENGTH", "LOAD_FILE", "LOCATE", "LOCATE",
                "LOWER", "LPAD", "LTRIM", "MAKE_SET", "MATCH", "MID", "OCT",
                "OCTET_LENGTH", "ORD", "POSITION", "QUOTE", "REPEAT",
                "REPLACE", "REVERSE", "RIGHT", "RPAD", "RTRIM", "SOUNDEX",
                "SPACE", "STRCMP", "SUBSTRING",
                "SUBSTRING",
                "SUBSTRING",
                "SUBSTRING",
                "SUBSTRING_INDEX",
                "TRIM",
                "UCASE",
                "UPPER",
                // getSystemFunctions
                "DATABASE", "USER",
                "SYSTEM_USER",
                "SESSION_USER",
                "PASSWORD",
                "ENCRYPT",
                "LAST_INSERT_ID",
                "VERSION",
                // getTimeDateFunctions
                "DAYOFWEEK", "WEEKDAY", "DAYOFMONTH", "DAYOFYEAR", "MONTH",
                "DAYNAME", "MONTHNAME", "QUARTER", "WEEK", "YEAR", "HOUR",
                "MINUTE", "SECOND", "PERIOD_ADD", "PERIOD_DIFF", "TO_DAYS",
                "FROM_DAYS", "DATE_FORMAT", "TIME_FORMAT", "CURDATE",
                "CURRENT_DATE", "CURTIME", "CURRENT_TIME", "NOW", "SYSDATE",
                "CURRENT_TIMESTAMP", "UNIX_TIMESTAMP", "FROM_UNIXTIME",
                "SEC_TO_TIME", "TIME_TO_SEC" };
        rule.setFunctionNames(mySqlFuncs);
        final BlancoSqlFormatter formatter = new BlancoSqlFormatter(rule);

        // change all files in the test directory
        final File[] files = new File("Test").listFiles();
        for (int i = 0; i < files.length; i++) {
            System.out.println("-- " + files[i]);
            // read SQL from the file
            final BufferedReader reader = new BufferedReader(new FileReader(
                    files[i]));
            String before = PJConst.EMPTY;
            while (reader.ready()) {
                String line = reader.readLine();
                if (line == null)
                    break;
                before += line + "\n";
            }
            reader.close();

            // do format
            System.out.println("[before]\n" + before);
            String after = formatter.format(before);
            System.out.println("[after]\n" + after);
        }
    }
}
