package com.axilog.cov.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmsCommand {
	private String message;
    private String messagingServiceSid;
    private String phone;
    private String authToken;
}
