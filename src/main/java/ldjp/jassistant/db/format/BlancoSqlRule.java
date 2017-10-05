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

/**
 * BlancoSqlRule : Class of SQL format rule
 *
 * @author WATANABE Yoshinori (a-san) : original version at 2005.07.04.
 * @author IGA Tosiki : marge into blanc Framework at 2005.07.04
 */
public class BlancoSqlRule {
    /**
     * Rule of keywords
     */
    int keyword = KEYWORD_UPPER_CASE;

    /**
     *  Rule of keywords : Do nothing
     */
    public static final int KEYWORD_NONE = 0;

    /**
     *  Rule of keywords : UPPER CASE
     */
    public static final int KEYWORD_UPPER_CASE = 1;

    /**
     *  Rule of keywords : LOWER CASE
     */
    public static final int KEYWORD_LOWER_CASE = 2;

    /**
     * String of indent which is originally set by one of [" "," ","\t"]
     */
    String indentString = "    ";

    /**
     * Function names
     */
    private String[] functionNames = null;

    public void setKeywordCase(int keyword) {
        this.keyword = keyword;
    }

    /**
     * Judge if it is a function name
     *
     * @param name string to judge
     * @return result
     */
    boolean isFunction(String name) {
        if (functionNames == null)
            return false;
        for (int i = 0; i < functionNames.length; i++) {
            if (functionNames[i].equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    /**
     * Set function name list
     *
     * @param functionNames function name list to set
     */
    public void setFunctionNames(String[] functionNames) {
        this.functionNames = functionNames;
    }

}
