package com.safetransfer.exception;

public class SourceAccountHasInsufficientFundsException extends Exception {

    public SourceAccountHasInsufficientFundsException(String msg) {
        super(msg);
    }
}
