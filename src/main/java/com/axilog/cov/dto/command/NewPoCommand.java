package com.axilog.cov.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewPoCommand {
	private String quotationId;
    private String poNumber;
    private Long poAmount;
}
