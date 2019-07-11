package com.doit.net.gsm.collector.server;

/**
 * Created by wiker on 2016/4/21.
 */
public class GSMException extends Exception {

    public GSMException() {
    }

    public GSMException(String message) {
        super(message);
    }

    public GSMException(String message, Throwable cause) {
        super(message, cause);
    }

    public GSMException(Throwable cause) {
        super(cause);
    }
}
