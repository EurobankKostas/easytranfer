package com.safetransfer.service.impl;

import com.safetransfer.domain.TransferAmountValidator;
import com.safetransfer.dto.MoneyTransferRequest;
import com.safetransfer.dto.MoneyTransferResponse;
import com.safetransfer.entity.Account;
import com.safetransfer.entity.Transaction;
import com.safetransfer.exception.AccountNotFoundException;
import com.safetransfer.exception.SameAccountMoneyTransferException;
import com.safetransfer.exception.SourceAccountHasInsufficientFundsException;
import com.safetransfer.repository.AccountRepository;
import com.safetransfer.repository.TransactionRepository;
import com.safetransfer.service.MoneyTransfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EasyTransferService implements MoneyTransfer {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    /**
     *
     * @param moneyTransferRequest {@link MoneyTransferRequest}
     * @return
     * @throws AccountNotFoundException
     * @throws SameAccountMoneyTransferException
     * @throws SourceAccountHasInsufficientFundsException
     */
    @Transactional
    public MoneyTransferResponse executeMoneyTransfer(MoneyTransferRequest moneyTransferRequest) throws AccountNotFoundException, SameAccountMoneyTransferException, SourceAccountHasInsufficientFundsException, InterruptedException {
        log.debug("start executeMoneyTransfer request {} ", moneyTransferRequest);
        Account sourceAccount = accountRepository.findAccountByAccountId(moneyTransferRequest.getSourceAccountId());
        TransferAmountValidator transferAmountValidator = new TransferAmountValidator();
        transferAmountValidator.checkIfAccountExists(sourceAccount, moneyTransferRequest.getSourceAccountId());

        transferAmountValidator.checkIfMoneyTransferIsExecutedBetweenSameAccount(moneyTransferRequest.getSourceAccountId(), moneyTransferRequest.getTargetAccountId());

        Account targetAccount = accountRepository.findAccountByAccountId(moneyTransferRequest.getTargetAccountId());
        transferAmountValidator.checkIfAccountExists(targetAccount, moneyTransferRequest.getTargetAccountId());

        transferAmountValidator.checkIfSourceAccountHasSufficientBalance(moneyTransferRequest.getSourceAccountId(), sourceAccount.getBalance(), moneyTransferRequest.getAmount());
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(moneyTransferRequest.getAmount()));
        targetAccount.setBalance(targetAccount.getBalance().add(moneyTransferRequest.getAmount()));

        Transaction moneyTransferTransaction = Transaction.builder()
                .sourceAccount(sourceAccount)
                .targetAccount(targetAccount)
                .amount(moneyTransferRequest.getAmount())
                .currency(moneyTransferRequest.getCurrency())
                .build();
        transactionRepository.save(moneyTransferTransaction);
        String successTransferMessage = String.format("Transfer of %s from account %s to account %s was successful", moneyTransferRequest.getAmount(), moneyTransferRequest.getSourceAccountId(), moneyTransferRequest.getTargetAccountId());
        return MoneyTransferResponse.builder().actionsCompleted(successTransferMessage).build();
    }

}
