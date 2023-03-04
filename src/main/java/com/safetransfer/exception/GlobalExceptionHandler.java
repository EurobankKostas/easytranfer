package com.safetransfer.exception;

import com.safetransfer.dto.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public final ResponseEntity<ErrorInfo> handleAccountNotFoundException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ErrorInfo.builder().message(ex.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({SameAccountMoneyTransferException.class, SourceAccountHasInsufficientFundsException.class})
    public final ResponseEntity<ErrorInfo> handleSameAccountMoneyTransferException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ErrorInfo.builder().message(ex.getMessage()).build(), HttpStatus.NOT_ACCEPTABLE);
    }
}
