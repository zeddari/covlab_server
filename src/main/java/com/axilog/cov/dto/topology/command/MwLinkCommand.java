package com.axilog.cov.dto.topology.command;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Command for mw link", value = "MwLinkCommand")
public class MwLinkCommand {

	private String uplinkId;
	private String uplinkUtilization;
	private Map<String,String> downLinks;
}
