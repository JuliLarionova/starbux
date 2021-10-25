package com.bstlr.starbux.common;

import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {
    private String code;

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, String code) {
        super(message);
        this.code = code;
    }
}
