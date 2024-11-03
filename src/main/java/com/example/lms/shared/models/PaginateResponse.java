package com.example.lms.shared.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PaginateResponse<T> {

    private T data;
    public boolean last;
    public int totalPages;

}