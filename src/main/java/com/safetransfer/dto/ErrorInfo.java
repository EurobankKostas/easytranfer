package com.safetransfer.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorInfo {

    private String message;
}
