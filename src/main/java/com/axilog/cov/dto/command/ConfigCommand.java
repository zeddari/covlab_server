package com.axilog.cov.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfigCommand {
	private String destinationEmails;
	private String ccEmails;
	private String outlet;
}
