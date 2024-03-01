package com.efrei.mailsendermicroservice.exception.custom;

public class WrongUserRoleException extends RuntimeException {
    public WrongUserRoleException(String message) {
        super(message);
    }
}
