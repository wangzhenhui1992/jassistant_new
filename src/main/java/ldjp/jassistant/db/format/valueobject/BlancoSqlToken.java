/*
 * blanco Framework
 * Copyright (C) 2004-2006 WATANABE Yoshinori
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package ldjp.jassistant.db.format.valueobject;

/**
 * BlancoSqlToken class is a part of the blanco Framework
 * @author WATANABE Yoshinori (a-san) : original version at 2005.07.04.
 * @author IGA Tosiki : marge into blanc Framework at 2005.07.04
 */
public class BlancoSqlToken extends AbstractBlancoSqlToken {
    /**
     * create the instance of the BlancoSqlToken
     *
     * @param argType
     * @param argString
     * @param argpst
     */
    public BlancoSqlToken(int argType, String argString, int argpst) {
        setType(argType);
        setStr(argString);
        setPst(argpst);
    }

    /**
     * create the instance of the BlancoSqlToken
     *
     * @param argType
     * @param argString
     */
    public BlancoSqlToken(int argType, String argString) {
        this(argType, argString, -1);
    }
}
