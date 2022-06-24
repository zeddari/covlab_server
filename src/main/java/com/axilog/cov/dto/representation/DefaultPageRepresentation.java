package com.axilog.cov.dto.representation;

import java.util.List;

import com.axilog.cov.dto.base.PageRepresentation;

public class DefaultPageRepresentation<T> implements PageRepresentation<T> {

    private final List<T> data;
    private final long totalElements;

    public DefaultPageRepresentation(List<T> data, long totalElements) {
        this.data = data;
        this.totalElements = totalElements;
    }

    @Override
    public List<T> getContent() {
        return this.data;
    }

    @Override
    public long getTotalElements() {
        return this.totalElements;
    }

	@Override
	public long getPeriodLimitSearchDate() {
		return 0;
	}
}