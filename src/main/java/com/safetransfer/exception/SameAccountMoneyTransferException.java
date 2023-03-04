package com.safetransfer.exception;

public class SameAccountMoneyTransferException extends Exception {

    public SameAccountMoneyTransferException(String msg) {
        super(msg);
    }
}
