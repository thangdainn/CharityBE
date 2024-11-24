package org.dainn.charitybe.constants;

public class Endpoint {
    public static final String API_PREFIX = "/api";

    public static final class Auth {
        public static final String BASE = API_PREFIX + "/auth";
        public static final String ID = "/{id}";
        public static final String LOGIN = "/login";
        public static final String LOGIN_GOOGLE = "/login/oauth2/google";
        public static final String REGISTER = "/register";
        public static final String REFRESH_TOKEN = "/refresh-token";
    }

    public static final class User {
        public static final String BASE = API_PREFIX + "/users";
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

    public static final class Campaign {
        public static final String BASE = API_PREFIX + "/campaigns";
        public static final String ID = "/{id}";
        public static final String CODE = "/{code}";

    }

    public static final class Education {
        public static final String BASE = API_PREFIX + "/educations";
        public static final String ID = "/{id}";
    }

    public static final class File {
        public static final String BASE = API_PREFIX + "/upload";
    }

    public static final class Payment {
        public static final String BASE = API_PREFIX + "/payment";
        public static final String VN_PAY = "/vnp";
        public static final String VN_PAY_CALLBACK = "/vnp-callback";
    }

    public static final class FinancialReport {
        public static final String BASE = API_PREFIX + "/financial-report";
        public static final String ID = "/{id}";
    }

    public static final class Recipient {
        public static final String BASE = API_PREFIX + "/recipients";
        public static final String ID = "/{id}";
    }

    public static final class Statistic {
        public static final String BASE = API_PREFIX + "/statistics";
        public static final String CAMPAIGN = "/campaign";
    }

    public static final class Donation {
        public static final String BASE = API_PREFIX + "/donations";
    }
}
