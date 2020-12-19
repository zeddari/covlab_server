package com.axilog.cov.dto.representation;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TicketsDetail {
	private Long ticketNo;	
	private String ticketProductCodeString;		
	private String ticketType;
	private String ticketStatus	;
	private LocalDate ticketDueDate	;
	private String ticketPriority;	
	private LocalDate ticketCreatedOn;
	private String ticketRegion;
	private LocalDate ticketUpdateAt;

}
