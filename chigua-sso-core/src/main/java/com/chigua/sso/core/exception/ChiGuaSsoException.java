package com.chigua.sso.core.exception;

/**
 * @author xuxueli 2018-04-02 21:01:41
 */
public class ChiGuaSsoException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public ChiGuaSsoException(String msg) {
        super(msg);
    }

    public ChiGuaSsoException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ChiGuaSsoException(Throwable cause) {
        super(cause);
    }

}
