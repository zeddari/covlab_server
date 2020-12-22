package com.axilog.cov.dto.topology.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KpiTableInfo {

	String tableName;
	String columnName;
}
