package com.diplomska.backend.security;

public class SecurityConstants {
    public static final String SECRET = "WorkAndTravel";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String ADMIN_CREATE_USER = "/rest/adminCreateUser";
    public static final String LOGIN_URL = "/rest/login";
    public static final String USER_CREATE_USER = "/rest/userCreateUser";
    public static final String FACEBOOK_LOGIN = "/facebook/signin";
    public static final String CONFIRM_ACCOUNT = "/rest/confirm-account";

}
