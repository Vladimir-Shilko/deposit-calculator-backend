package com.example.deposit.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * Единый формат ответа об ошибке, возвращаемый клиенту.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private Instant timestamp;
    private int status;
    private String error;
    private List<String> messages;

    public static ErrorResponse of(int status, String error, List<String> messages) {
        return new ErrorResponse(Instant.now(), status, error, messages);
    }
}
