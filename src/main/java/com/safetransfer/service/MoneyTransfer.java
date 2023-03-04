package com.safetransfer.service;

import com.safetransfer.dto.MoneyTransferRequest;
import com.safetransfer.dto.MoneyTransferResponse;
import com.safetransfer.exception.AccountNotFoundException;
import com.safetransfer.exception.SameAccountMoneyTransferException;
import com.safetransfer.exception.SourceAccountHasInsufficientFundsException;

public interface MoneyTransfer {

    MoneyTransferResponse executeMoneyTransfer(MoneyTransferRequest moneyTransferRequest) throws AccountNotFoundException, SameAccountMoneyTransferException, SourceAccountHasInsufficientFundsException, InterruptedException;
}
