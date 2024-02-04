package com.maradamark99.thesis.util;

import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CaseInsensitivePageable implements Pageable {

    private final Pageable pageable;

    public CaseInsensitivePageable(Pageable pageable) {
        this.pageable = pageable;
    }

    @Override
    public int getPageNumber() {
        return pageable.getPageNumber();
    }

    @Override
    public int getPageSize() {
        return pageable.getPageSize();
    }

    @Override
    public long getOffset() {
        return pageable.getOffset();
    }

    @Override
    public Sort getSort() {
        Sort originalSort = pageable.getSort();
        var ignoreCaseOrders = originalSort.stream()
                .map(order -> order.ignoreCase())
                .collect(Collectors.toList());
        return Sort.by(ignoreCaseOrders);
    }

    @Override
    public Pageable next() {
        return pageable.next();
    }

    @Override
    public Pageable previousOrFirst() {
        return pageable.previousOrFirst();
    }

    @Override
    public Pageable first() {
        return pageable.first();
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return pageable.withPage(pageNumber);
    }

    @Override
    public boolean hasPrevious() {
        return pageable.hasPrevious();
    }

}
