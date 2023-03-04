package com.safetransfer.controller;

import com.safetransfer.dto.MoneyTransferRequest;
import com.safetransfer.dto.MoneyTransferResponse;
import com.safetransfer.exception.AccountNotFoundException;
import com.safetransfer.exception.SameAccountMoneyTransferException;
import com.safetransfer.exception.SourceAccountHasInsufficientFundsException;
import com.safetransfer.service.MoneyTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EasyTransferController implements MoneyTransferController {

    private final MoneyTransfer moneyTransfer;

    @PostMapping
    public ResponseEntity<MoneyTransferResponse> executeMoneyTransfer(@RequestBody MoneyTransferRequest moneyTransferRequest) throws SameAccountMoneyTransferException, SourceAccountHasInsufficientFundsException, AccountNotFoundException, InterruptedException {
        return ResponseEntity.ok(moneyTransfer.executeMoneyTransfer(moneyTransferRequest));
    }
}
