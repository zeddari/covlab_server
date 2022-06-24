package com.axilog.cov.dto.query;

import com.axilog.cov.dto.base.AbstractQuery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class SearchDeadLetterJobsQuery extends AbstractQuery {

	public static final String SUPPORTED_SORT_KEYS = "jobId,jobDuedate,executionId,processInstanceId";
	private static final String SORT_KEYS_SEPARATOR = ",";
	public static final String[] SUPPORTED_SORT_KEYS_ARRAY = SUPPORTED_SORT_KEYS.split(SORT_KEYS_SEPARATOR);

	private String businessKey;
	private String processDefinitionName;
	private String exceptionMessage;
	@ApiModelProperty(value = "Due Date using the like search pattern, format is : yyyy-mm-dd")
	private String dueDate;

	@Builder
	public SearchDeadLetterJobsQuery(int page, int size, String sort) {
		super(page, size, sort);
	}

	@ApiModelProperty(value = "Supported sort keys : " + SUPPORTED_SORT_KEYS)
	@Override
	public String getSort() {
		return super.getSort();
	}

	@ApiModelProperty(hidden = true, readOnly = true)
	@Override
	public String[] getSupportedSortKeys() {
		return SUPPORTED_SORT_KEYS_ARRAY;
	}
}
