package com.hxk.demo.constant;

public final class UserRoleConstants {

    private UserRoleConstants() {
        throw new IllegalStateException("Cannot create instance of static constant class");
    }

    public static final String ROLE_USER = "ROLE_USER";

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static final String ROLE_ROOT = "ROLE_ROOT";

}
