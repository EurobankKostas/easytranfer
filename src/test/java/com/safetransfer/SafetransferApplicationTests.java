package com.safetransfer;

import com.safetransfer.dto.MoneyTransferRequest;
import com.safetransfer.exception.AccountNotFoundException;
import com.safetransfer.exception.SameAccountMoneyTransferException;
import com.safetransfer.exception.SourceAccountHasInsufficientFundsException;
import com.safetransfer.service.impl.EasyTransferService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
class SafetransferApplicationTests {

    @Autowired
    private EasyTransferService easyTransferService;

    @Test
    void execute_payment_happy_scenario() throws SameAccountMoneyTransferException, SourceAccountHasInsufficientFundsException, AccountNotFoundException, InterruptedException {
        MoneyTransferRequest moneyTransferRequest = MoneyTransferRequest.builder()
                .sourceAccountId(UUID.fromString("123e4567-e89b-42d3-a456-556642440014"))
                .targetAccountId(UUID.fromString("123e4567-e89b-42d3-a456-556642440019"))
                .currency("EUR")
                .amount(BigDecimal.valueOf(10)).build();
        easyTransferService.executeMoneyTransfer(moneyTransferRequest);
    }

    @Test
    void execute_payment_with_non_existing_account() {
        MoneyTransferRequest moneyTransferRequest = MoneyTransferRequest.builder()
                .sourceAccountId(UUID.fromString("123e4567-e89b-42d3-a456-556642440914"))
                .targetAccountId(UUID.fromString("123e4567-e89b-42d3-a456-556642440019"))
                .currency("EUR")
                .amount(BigDecimal.valueOf(10)).build();
        assertThrows(AccountNotFoundException.class, () -> easyTransferService.executeMoneyTransfer(moneyTransferRequest));
    }

    @Test
    void execute_payment_with_same_account() {
        MoneyTransferRequest moneyTransferRequest = MoneyTransferRequest.builder()
                .sourceAccountId(UUID.fromString("123e4567-e89b-42d3-a456-556642440019"))
                .targetAccountId(UUID.fromString("123e4567-e89b-42d3-a456-556642440019"))
                .currency("EUR")
                .amount(BigDecimal.valueOf(10)).build();
        assertThrows(SameAccountMoneyTransferException.class, () -> easyTransferService.executeMoneyTransfer(moneyTransferRequest));
    }

    @Test
    void execute_payment_with_source_account_having_insufficient_funds() {
        MoneyTransferRequest moneyTransferRequest = MoneyTransferRequest.builder()
                .sourceAccountId(UUID.fromString("123e4567-e89b-42d3-a456-556642440019"))
                .targetAccountId(UUID.fromString("123e4567-e89b-42d3-a456-556642440013"))
                .currency("EUR")
                .amount(BigDecimal.valueOf(10000)).build();
        assertThrows(SourceAccountHasInsufficientFundsException.class, () -> easyTransferService.executeMoneyTransfer(moneyTransferRequest));
    }


}
