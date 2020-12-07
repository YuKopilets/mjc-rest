package com.epam.esm.jwt;

public final class JwtConstant {
    public static final Long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 30;
    public static final String AUTHENTICATION_TYPE_CLAIM = "auth";
    public static final String OAUTH2_SUB_CLAIM = "oa2sub";
}
