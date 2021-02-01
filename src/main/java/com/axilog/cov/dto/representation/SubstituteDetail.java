package com.axilog.cov.dto.representation;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubstituteDetail {
	 private String substituteCode;
	 private String substituteLabel;
}
