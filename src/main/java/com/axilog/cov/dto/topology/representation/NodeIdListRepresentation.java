package com.axilog.cov.dto.topology.representation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NodeIdListRepresentation {

	private Integer item_id;
	private String item_text;
}
