package com.safetransfer.domain;

import com.safetransfer.dto.MoneyTransferRequest;
import com.safetransfer.entity.Account;
import com.safetransfer.exception.AccountNotFoundException;
import com.safetransfer.exception.SameAccountMoneyTransferException;
import com.safetransfer.exception.SourceAccountHasInsufficientFundsException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class TransferAmountValidator {

    /**
     * @param account            {@link Account} contains source or target account entity to check if exists
     * @param requestedAccountId {@link MoneyTransferRequest#getSourceAccountId()} {@link MoneyTransferRequest#getTargetAccountId()}, contains source or target accountId for logging purposes
     * @throws AccountNotFoundException if account is not found
     */
    public void checkIfAccountExists(Account account, UUID requestedAccountId) throws AccountNotFoundException {
        if (Optional.ofNullable(account).isEmpty()) {
            String accountNotFoundExceptionMessage = String.format("Account with id %s is not present ", requestedAccountId);
            throw new AccountNotFoundException(accountNotFoundExceptionMessage);
        }
    }

    /**
     * @param sourceAccountId {@link MoneyTransferRequest#getSourceAccountId()}, source accountId of same account comparison
     * @param targetAccountId {@link MoneyTransferRequest#getTargetAccountId()}, target accountId of same account comparison
     * @throws SameAccountMoneyTransferException if source accountId equals target accountId
     */
    public void checkIfMoneyTransferIsExecutedBetweenSameAccount(UUID sourceAccountId, UUID targetAccountId) throws SameAccountMoneyTransferException {
        if (sourceAccountId.equals(targetAccountId)) {
            String sameAccountMoneyTransferExceptionMessage = String.format("Source and Target accounts are same , id %s ", sourceAccountId);
            throw new SameAccountMoneyTransferException(sameAccountMoneyTransferExceptionMessage);
        }
    }

    /**
     * @param accountId             {@link Account#getId()},the accountId of source account
     * @param accountBalance        {@link Account#getBalance()}, balance of the source account
     * @param amountToBeTransferred {@link MoneyTransferRequest#getAmount()}, transaction amount
     * @throws SourceAccountHasInsufficientFundsException if source account contains insufficient funds
     */
    public void checkIfSourceAccountHasSufficientBalance(UUID accountId, BigDecimal accountBalance, BigDecimal amountToBeTransferred) throws SourceAccountHasInsufficientFundsException {
        if (amountToBeTransferred.compareTo(accountBalance) > 0) {
            String sourceAccountHasInsufficientFundsException = String.format("Source account with id %s contains insufficient funds,  ", accountId);
            throw new SourceAccountHasInsufficientFundsException(sourceAccountHasInsufficientFundsException);
        }
    }
}
