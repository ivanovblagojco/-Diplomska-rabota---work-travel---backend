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
    public static final String FORGOT_PASSWORD = "/rest/forgot_password";
    public static final String RESET_PASSWORD = "/rest/reset_password";
    public static final String GET_ALL_POSTS = "/rest/getAllPosts/{place}/{page}/{size}";
    public static final String GET_LAST_THREE_POSTS = "/rest/getLastThreePosts";
    public static final String GET_POST = "/rest/getPost/{id}";
    public static final String GET_ALL_POSTS_FROM_USERS = "/rest/getAllPostsFromUsers/{place}/{page}/{size}";
    public static final String GET_ALL_POSTS_FROM_AGENCY = "/rest/getAllPostsFromAgency/{place}/{page}/{size}";
    public static final String GET_ALL_POSTS_FROM_USERS2 = "/rest/getAllPostsFromUsers/{page}/{size}";
    public static final String GET_ALL_POSTS_FROM_AGENCY2 = "/rest/getAllPostsFromAgency/{page}/{size}";
    public static final String GET_ALL_POSTS2 = "/rest/getAllPosts/{page}/{size}";
    public static final String GET_ALL_COMMENTS_FOR_POST = "/rest/getAllComments/{page}/{size}/{post_id}";
    public static final String CREATE_CONTACT = "/rest/createContact";
    public static final String CREATE_APPLICATION = "/rest/createApplication";
    public static final String CREATE_LOCATION = "/rest/getAllLocations/{email}/{page}/{size}";




}
