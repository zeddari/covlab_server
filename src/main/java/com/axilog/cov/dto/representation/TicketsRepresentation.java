package com.axilog.cov.dto.representation;
import lombok.Builder;
import lombok.Data;
import java.util.List;
@Builder
@Data
public class TicketsRepresentation {
	List<TicketsDetail> ticketsData;

}
