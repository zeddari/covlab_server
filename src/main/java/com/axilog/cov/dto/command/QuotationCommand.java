package com.axilog.cov.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuotationCommand {

    private String customerName;
    private String customerEmail;
    private String mobileNumber;
    private String requestedProductCode;
}
