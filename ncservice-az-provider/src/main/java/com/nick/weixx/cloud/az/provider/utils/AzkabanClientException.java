package com.nick.weixx.cloud.az.provider.utils;

public class AzkabanClientException extends Exception {

    private static final long serialVersionUID = -8231674899410334020L;

    public AzkabanClientException(String msg) {
        super("azkaban client error..." + msg);
    }

}
