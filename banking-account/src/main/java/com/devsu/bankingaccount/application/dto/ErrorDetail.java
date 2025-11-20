package com.devsu.bankingaccount.application.dto;

import java.io.Serializable;

public class ErrorDetail implements Serializable {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
