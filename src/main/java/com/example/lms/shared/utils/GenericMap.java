package com.example.lms.shared.utils;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenericMap {

    public static <S, T> Page<T> mapPage(Page<S> page, Class<T> responseClass) {
        List<T> mappedList = page
                .stream()
                .map(element -> new ModelMapper().map(element, responseClass))
                .collect(Collectors.toList());

        return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
    }
}