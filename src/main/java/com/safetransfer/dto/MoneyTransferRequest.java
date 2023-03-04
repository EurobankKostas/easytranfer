package com.safetransfer.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MoneyTransferRequest {

    private UUID sourceAccountId;

    private UUID targetAccountId;

    private BigDecimal amount;

    private String currency;
}
