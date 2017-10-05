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

import java.io.IOException;

/**
 * BlancoSqlFormatterException : class of Exception occurred when formating the SQL
 *
 * @author IGA Tosiki : created at 2005.08.03
 */
public class BlancoSqlFormatterException extends IOException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construction Method
     */
    public BlancoSqlFormatterException() {
        super();
    }

    /**
     * Construction Method
     *
     * @param argMessage exception message
     */
    public BlancoSqlFormatterException(final String argMessage) {
        super(argMessage);
    }
}
