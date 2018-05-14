package com.viadee.sonarQuest.exception;

/**
 * Runtime-Exception from the Backend, e.g. unavailable Services, DB-Errors etc.
 */
@SuppressWarnings("serial")
public class BackendServiceRuntimeException extends RuntimeException {

    public BackendServiceRuntimeException(String msg, Exception rootCause) {
        super(msg, rootCause);
    }

}
