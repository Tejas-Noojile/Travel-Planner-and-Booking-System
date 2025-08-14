package com.travel.travelapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NetBankingFinalizationRequestDTO {
	 @NotBlank(message = "Bank name is required")
	    private String bankName;

	    @NotBlank(message = "Bank account number is required")
	    private String accountNumber; 

	    @NotBlank(message = "IFSC code is required")
	    private String ifscCode;
}