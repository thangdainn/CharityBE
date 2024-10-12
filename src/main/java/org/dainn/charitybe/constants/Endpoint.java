package org.dainn.charitybe.constants;

public class Endpoint {
    public static final String API_PREFIX = "/api";

    public static final class Auth {
        public static final String BASE = API_PREFIX + "/auth";
        public static final String LOGIN = "/login";
        public static final String LOGIN_GOOGLE = "/login/oauth2/google";
        public static final String REGISTER = "/register";
        public static final String REFRESH_TOKEN = "/refresh-token";
    }

    public static final class User {
        public static final String BASE = API_PREFIX + "/users";
        public static final String ME = "/me";
        public static final String ID = "/{id}";
    }

    public static final class Role {
        public static final String BASE = API_PREFIX + "/roles";
        public static final String ID = "/{id}";
        public static final String NAME = "/{name}";
    }

    public static final class Category {
        public static final String BASE = API_PREFIX + "/categories";
        public static final String ID = "/{id}";
    }

    public static final class Sponsor {
        public static final String BASE = API_PREFIX + "/sponsors";
        public static final String ID = "/{id}";
    }

    public static final class Project {
        public static final String BASE = API_PREFIX + "/projects";
        public static final String ID = "/{id}";
        public static final String CODE = "/{code}";
    }

    public static final class Education {
        public static final String BASE = API_PREFIX + "/educations";
        public static final String ID = "/{id}";
    }

}
