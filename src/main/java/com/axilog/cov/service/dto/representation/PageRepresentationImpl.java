package com.axilog.cov.service.dto.representation;

import java.util.List;

public class PageRepresentationImpl<T> implements PageRepresentation<T> {
    private List<T> content;
    private long totalElements;

    public PageRepresentationImpl(List<T> content, long totalElements) {
        this.content = content;
        this.totalElements = totalElements;
    }


    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public long getTotalElements() {
        return totalElements;
    }
}
