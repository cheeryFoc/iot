package com.hxk.demo.constant;

import java.net.URI;

/**
 * ErrorConstants
 *
 * @author cheer
 */
public final class ErrorConstants {
    private ErrorConstants() {
        throw new IllegalStateException("Cannot create instance of static constant class");
    }


    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final URI DEFAULT_TYPE = null;

}