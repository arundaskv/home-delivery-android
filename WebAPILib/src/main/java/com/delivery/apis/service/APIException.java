package com.delivery.apis.service;

/**
 * Created by s on 19-04-2015.
 */
public class APIException extends Exception {
    private static final long serialVersionUID = 1L;

    public APIException(String message){
        super(message);
    }
}
