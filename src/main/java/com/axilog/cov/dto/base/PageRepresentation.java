package com.axilog.cov.dto.base;

import java.util.List;

public interface PageRepresentation<T> {
    List<T> getContent();
    long getTotalElements();
    long getPeriodLimitSearchDate();
}
