package com.axilog.cov.dto.representation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChartDetail {
	
	 public String name;	
	 public List<SeriesDetail> series;
	 

}
