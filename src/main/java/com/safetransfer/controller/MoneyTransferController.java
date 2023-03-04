package com.safetransfer.controller;

import com.safetransfer.dto.ErrorInfo;
import com.safetransfer.dto.MoneyTransferRequest;
import com.safetransfer.dto.MoneyTransferResponse;
import com.safetransfer.exception.AccountNotFoundException;
import com.safetransfer.exception.SameAccountMoneyTransferException;
import com.safetransfer.exception.SourceAccountHasInsufficientFundsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(value = "/api/money-transfer")
public interface MoneyTransferController {

    @Operation(operationId = "executeMoneyTransfer", summary = "Executes money transfer between 2 accounts",responses = {
            @ApiResponse(
                    responseCode = "200"  ,description = "Successfully executed money transfer",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = MoneyTransferResponse.class))
            ),
            @ApiResponse(
                    responseCode = "406"  ,description = "Failed to execute money transfer",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorInfo.class))
            ),
            @ApiResponse(
                    responseCode = "404"  ,description = "Account not found",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorInfo.class))
            )
    })
    ResponseEntity<MoneyTransferResponse> executeMoneyTransfer(@RequestBody MoneyTransferRequest moneyTransferRequest) throws SameAccountMoneyTransferException, SourceAccountHasInsufficientFundsException, AccountNotFoundException, InterruptedException;

}
