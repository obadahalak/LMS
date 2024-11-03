package com.example.lms.shared.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private String message;
    public Object errors;
}