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

import ldjp.jassistant.common.PJConst;

/**
 * Utility class of Blanco.
 *
 * @author WATANABE Yoshinori (a-san) : original version at 2005.07.04.
 * @author IGA Tosiki : marge into blanc Framework at 2005.07.04
 */
public class BlancoSqlUtil {
    /**
     * Replace the string
     *
     * @param argTargetString
     * @param argFrom
     * @param argTo
     * @return
     */
    public static String replace(final String argTargetString,
            final String argFrom, final String argTo) {
        String newStr = PJConst.EMPTY;
        int lastpos = 0;

        for (;;) {
            int pst = argTargetString.indexOf(argFrom, lastpos);
            if (pst == -1) {
                break;
            }

            newStr += argTargetString.substring(lastpos, pst);
            newStr += argTo;
            lastpos = pst + argFrom.length();
        }

        return newStr + argTargetString.substring(lastpos);
    }
}
