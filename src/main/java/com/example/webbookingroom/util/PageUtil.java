package com.example.webbookingroom.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

public class PageUtil {
    public static Pageable createPageable(int currentPage, int limit, String sort) {
        String[] sortParams = sort.split("-");
        String sortBy = String.format("(%s)", sortParams[0]);
        Sort sortPageable = JpaSort.unsafe(sortParams[1].equals(Sort.Direction.ASC.toString()) ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        return PageRequest.of(currentPage - 1, limit, sortPageable);
    }

    public static Pageable getPageable(Integer currentPage, Integer limit, String sort) {
        String[] sortParam = sort.split("-");
        Sort sortPageable = Sort.by(sortParam[1].equals(Sort.Direction.ASC.toString()) ? Sort.Direction.ASC : Sort.Direction.DESC, sortParam[0]);
        return PageRequest.of(currentPage - 1, limit, sortPageable);
    }
}
