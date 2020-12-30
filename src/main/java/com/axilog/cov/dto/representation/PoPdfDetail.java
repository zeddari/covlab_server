package com.axilog.cov.dto.representation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoPdfDetail {

	private List<InventoryPdfDetail> listDetails;
	private HeaderPdfDetail headerPdfDetail;
	private String outlet;
}
