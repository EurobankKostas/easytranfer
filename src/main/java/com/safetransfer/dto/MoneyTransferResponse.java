package com.safetransfer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MoneyTransferResponse {

    private String actionsCompleted;
}
