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
 * BlancoSqlConstants class is created for defining reserved words of ANSI SQL
 * @author IGA Tosiki
 */
public class BlancoSqlConstants {
    /**
     * Keywords of ANSI SQL
     */
    public static final String[] SQL_RESERVED_WORDS = {
            // ANSI SQL89
            "ALL", "AND", "ANY", "AS", "ASC", "AUTHORIZATION", "AVG", "BEGIN",
            "BETWEEN", "BY", "CHAR", "CHARACTER", "CHECK", "CLOSE", "COBOL",
            "COMMIT", "CONTINUE", "COUNT", "CREATE", "CURRENT", "CURSOR",
            "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DELETE", "DESC",
            "DISTINCT", "DOUBLE", "END", "ESCAPE", "EXEC", "EXISTS", "FETCH",
            "FLOAT", "FOR", "FOREIGN", "FORTRAN", "FOUND", "FROM", "GO",
            "GOTO", "GRANT", "GROUP", "HAVING", "IN", "INDICATOR", "INSERT",
            "INT", "INTEGER", "INTO", "IS", "KEY", "LANGUAGE", "LIKE", "MAX",
            "MIN", "MODULE", "NOT",
            "NULL",
            "NUMERIC",
            "OF",
            "ON",
            "OPEN",
            "OPTION",
            "OR",
            "ORDER",
            "PASCAL",
            "PLI",
            "PRECISION",
            "PRIMARY",
            "PRIVILEGES",
            "PROCEDURE",
            "PUBLIC",
            "REAL",
            "REFERENCES",
            "ROLLBACK",
            "SCHEMA",
            "SECTION",
            "SELECT",
            "SET",
            "SMALLINT",
            "SOME",
            "SQL",
            "SQLCODE",
            "SQLERROR",
            "SUM",
            "TABLE",
            "TO",
            "UNION",
            "UNIQUE",
            "UPDATE",
            "USER",
            "VALUES",
            "VIEW",
            "WHENEVER",
            "WHERE",
            "WITH",
            "WORK",
            // ANSI SQL92
            "ABSOLUTE", "ACTION", "ADD", "ALLOCATE", "ALTER", "ARE",
            "ASSERTION", "AT", "BIT", "BIT_LENGTH", "BOTH", "CASCADE",
            "CASCADED", "CASE", "CAST", "CATALOG", "CHAR_LENGTH",
            "CHARACTER_LENGTH", "COALESCE", "COLLATE", "COLLATION", "COLUMN",
            "CONNECT", "CONNECTION", "CONSTRAINT", "CONSTRAINTS", "CONVERT",
            "CORRESPONDING", "CROSS", "CURRENT_DATE", "CURRENT_TIME",
            "CURRENT_TIMESTAMP", "CURRENT_USER", "DATE", "DAY", "DEALLOATE",
            "DEFERRABLE", "DEFERRED", "DESCRIBE", "DESCRIPTOR", "DIAGNOSTICS",
            "DISCONNECT", "DOMAIN", "DROP", "ELSE", "END-EXEC", "EXCEPT",
            "EXCEPTION", "EXECUTE", "EXTERNAL", "EXTRACT", "FALSE", "FIRST",
            "FULL", "GET", "GLOBAL", "HOUR", "IDENTITY", "IMMEDIATE",
            "INITIALLY", "INNER", "INPUT", "INSENSITIVE", "INTERSECT",
            "INTERVAL", "ISOLATION", "JOIN", "LAST", "LEADING", "LEFT",
            "LEVEL", "LOCAL", "LOWER", "MATCH", "MINUTE", "MONTH", "NAMES",
            "NATIONAL", "NATURAL", "NCHAR", "NEXT", "NO", "NULLIF",
            "OCTET_LENGTH", "ONLY", "OUTER", "OUTPUT", "OVERLAPS", "PAD",
            "PARTIAL", "POSITION", "PREPARE", "PRESERVE", "PRIOR", "READ",
            "RELATIVE", "RESTRICT", "REVOKE", "RIGHT", "ROWS", "SCROLL",
            "SECOND", "SESSION", "SESSION_USER", "SIZE", "SPACE", "SQLSTATE",
            "SUBSTRING", "SYSTEM_USER", "TEMPORARY", "THEN", "TIME",
            "TIMESTAMP", "TIMEZONE_HOUR", "TIMEZONE_MINUTE", "TRAILING",
            "TRANSACTION", "TRANSLATE", "TRANSLATION",
            "TRIM",
            "TRUE",
            "UNKNOWN",
            "UPPER",
            "USAGE",
            "USING",
            "VALUE",
            "VARCHAR",
            "VARYING",
            "WHEN",
            "WRITE",
            "YEAR",
            "ZONE",
            // ANSI SQL99
            "ADMIN", "AFTER", "AGGREGATE", "ALIAS", "ARRAY", "BEFORE",
            "BINARY", "BLOB", "BOOLEAN", "BREADTH", "CALL", "CLASS", "CLOB",
            "COMPLETION", "CONDITION", "CONSTRUCTOR", "CUBE", "CURRENT_PATH",
            "CURRENT_ROLE", "CYCLE", "DATA", "DEPTH", "DEREF", "DESTROY",
            "DESTRUCTOR", "DETERMINISTIC", "DICTIONARY", "DO", "DYNAMIC",
            "EACH", "ELSEIF", "EQUALS", "EVERY", "EXIT", "FREE", "FUNCTION",
            "GENERAL", "GROUPING", "HANDLER", "HOST", "IF", "IGNORE",
            "INITIALIZE", "INOUT", "ITERATE", "LARGE", "LATERAL", "LEAVE",
            "LESS", "LIMIT", "LIST", "LOCALTIME", "LOCALTIMESTAMP", "LOCATOR",
            "LONG", "LOOP", "MAP", "MODIFIES", "MODIFY", "NCLOB", "NEW",
            "NONE", "NUMBER", "OBJECT", "OFF", "OLD", "OPERATION",
            "ORDINALITY", "OUT", "PARAMETER", "PARAMETERS", "PATH", "POSTFIX",
            "PREFIX", "PREORDER", "RAW", "READS", "RECURSIVE", "REDO",
            // Not defined in ANSI SQL
            "TRUNCATE" };

    /**
     * KeyWords of ANSI SQL89
     * Used from Eclipse plugins
     */
    public static final String[] SQL89_RESERVED_WORDS = { "ALL", "AND", "ANY",
            "AS", "ASC", "AUTHORIZATION", "AVG", "BEGIN", "BETWEEN", "BY",
            "CHAR", "CHARACTER", "CHECK", "CLOSE", "COBOL", "COMMIT",
            "CONTINUE", "COUNT", "CREATE", "CURRENT", "CURSOR", "DEC",
            "DECIMAL", "DECLARE", "DEFAULT", "DELETE", "DESC", "DISTINCT",
            "DOUBLE", "END", "ESCAPE", "EXEC", "EXISTS", "FETCH", "FLOAT",
            "FOR", "FOREIGN", "FORTRAN", "FOUND", "FROM", "GO", "GOTO",
            "GRANT", "GROUP", "HAVING", "IN", "INDICATOR", "INSERT", "INT",
            "INTEGER", "INTO", "IS", "KEY", "LANGUAGE", "LIKE", "MAX", "MIN",
            "MODULE", "NOT", "NULL", "NUMERIC", "OF", "ON", "OPEN", "OPTION",
            "OR", "ORDER", "PASCAL", "PLI", "PRECISION", "PRIMARY",
            "PRIVILEGES", "PROCEDURE", "PUBLIC", "REAL", "REFERENCES",
            "ROLLBACK", "SCHEMA", "SECTION", "SELECT", "SET", "SMALLINT",
            "SOME", "SQL", "SQLCODE", "SQLERROR", "SUM", "TABLE", "TO",
            "UNION", "UNIQUE", "UPDATE", "USER", "VALUES", "VIEW", "WHENEVER",
            "WHERE", "WITH", "WORK" };

    /**
     * Keywords of ANSI SQL92
     * EUsed from Eclipse plugins
     */
    public static final String[] SQL92_RESERVED_WORDS = { "ABSOLUTE", "ACTION",
            "ADD", "ALLOCATE", "ALTER", "ARE", "ASSERTION", "AT", "BIT",
            "BIT_LENGTH", "BOTH", "CASCADE", "CASCADED", "CASE", "CAST",
            "CATALOG", "CHAR_LENGTH", "CHARACTER_LENGTH", "COALESCE",
            "COLLATE", "COLLATION", "COLUMN", "CONNECT", "CONNECTION",
            "CONSTRAINT", "CONSTRAINTS", "CONVERT", "CORRESPONDING", "CROSS",
            "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP",
            "CURRENT_USER", "DATE", "DAY", "DEALLOATE", "DEFERRABLE",
            "DEFERRED", "DESCRIBE", "DESCRIPTOR", "DIAGNOSTICS", "DISCONNECT",
            "DOMAIN", "DROP", "ELSE", "END-EXEC", "EXCEPT", "EXCEPTION",
            "EXECUTE", "EXTERNAL", "EXTRACT", "FALSE", "FIRST", "FULL", "GET",
            "GLOBAL", "HOUR", "IDENTITY", "IMMEDIATE", "INITIALLY", "INNER",
            "INPUT", "INSENSITIVE", "INTERSECT", "INTERVAL", "ISOLATION",
            "JOIN", "LAST", "LEADING", "LEFT", "LEVEL", "LOCAL", "LOWER",
            "MATCH", "MINUTE", "MONTH", "NAMES", "NATIONAL", "NATURAL",
            "NCHAR", "NEXT", "NO", "NULLIF", "OCTET_LENGTH", "ONLY", "OUTER",
            "OUTPUT", "OVERLAPS", "PAD", "PARTIAL", "POSITION", "PREPARE",
            "PRESERVE", "PRIOR", "READ", "RELATIVE", "RESTRICT", "REVOKE",
            "RIGHT", "ROWS", "SCROLL", "SECOND", "SESSION", "SESSION_USER",
            "SIZE", "SPACE", "SQLSTATE", "SUBSTRING", "SYSTEM_USER",
            "TEMPORARY", "THEN", "TIME", "TIMESTAMP", "TIMEZONE_HOUR",
            "TIMEZONE_MINUTE", "TRAILING", "TRANSACTION", "TRANSLATE",
            "TRANSLATION", "TRIM", "TRUE", "UNKNOWN", "UPPER", "USAGE",
            "USING", "VALUE", "VARCHAR", "VARYING", "WHEN", "WRITE", "YEAR",
            "ZONE" };

    /**
     * KeyWords of ANSI SQL99
     * Used from Eclipse plugins
     */
    public static final String[] SQL99_RESERVED_WORDS = { "ADMIN", "AFTER",
            "AGGREGATE", "ALIAS", "ARRAY", "BEFORE", "BINARY", "BLOB",
            "BOOLEAN", "BREADTH", "CALL", "CLASS", "CLOB", "COMPLETION",
            "CONDITION", "CONSTRUCTOR", "CUBE", "CURRENT_PATH", "CURRENT_ROLE",
            "CYCLE", "DATA", "DEPTH", "DEREF", "DESTROY", "DESTRUCTOR",
            "DETERMINISTIC", "DICTIONARY", "DO", "DYNAMIC", "EACH", "ELSEIF",
            "EQUALS", "EVERY", "EXIT", "FREE", "FUNCTION", "GENERAL",
            "GROUPING", "HANDLER", "HOST", "IF", "IGNORE", "INITIALIZE",
            "INOUT", "ITERATE", "LARGE", "LATERAL", "LEAVE", "LESS", "LIMIT",
            "LIST", "LOCALTIME", "LOCALTIMESTAMP", "LOCATOR", "LONG", "LOOP",
            "MAP", "MODIFIES", "MODIFY", "NCLOB", "NEW", "NONE", "NUMBER",
            "OBJECT", "OFF", "OLD", "OPERATION", "ORDINALITY", "OUT",
            "PARAMETER", "PARAMETERS", "PATH", "POSTFIX", "PREFIX", "PREORDER",
            "RAW", "READS", "RECURSIVE", "REDO" };

    /**
     * Keywords not included in ANSI SQL
     * Used from Eclipse plugins
     */
    public static final String[] SQL_FAMOUS_WORDS = { "TRUNCATE" };
}
