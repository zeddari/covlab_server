package com.axilog.cov.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

public interface  PoUpdateProjection {
	
	public String getCategory();
	public Double getCode();
	public Long getId();
	public Double getQuantity();
	public String getUom();
    
}
