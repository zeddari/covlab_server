package com.axilog.cov.service.dto.representation;

import java.util.List;

public interface PageRepresentation<T> {
    List<T> getContent();
    long getTotalElements();
}
