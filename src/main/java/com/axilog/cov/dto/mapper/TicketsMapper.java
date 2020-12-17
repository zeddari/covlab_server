package com.axilog.cov.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Tickets;
import com.axilog.cov.dto.representation.InventoryDetail;
import com.axilog.cov.dto.representation.InventoryRepresentation;
import com.axilog.cov.dto.representation.TicketsDetail;
import com.axilog.cov.dto.representation.TicketsRepresentation;


@Component
public class TicketsMapper {

	/**
	 * @param 
	 * @return
	 */
	public TicketsDetail toTicketsDetail(Tickets tickets) {
		return TicketsDetail.builder()
				.ticketNo(tickets.getTicketNo())
				.ticketProductCodeString(tickets.getProduct().getProductCode())
				.ticketType(tickets.getTicketType())
				.ticketStatus(tickets.getTicketStatus())	
				.ticketDueDate(tickets.getTicketDueDate())
				.ticketPriority(tickets.getTicketPriority())
				.ticketCreatedOn(tickets.getTicketCreatedOn())
				.ticketRegion(tickets.getOutlet().getOutletRegion())
				.ticketUpdateAt(tickets.getTicketUpdateAt())
				.build();
	}
	
	/**
	 * @param tickets
	 * @return
	 */
	public TicketsRepresentation toTicketsRepresentation(List<Tickets> tickets) {
		if (tickets == null) return TicketsRepresentation.builder().build();
		TicketsRepresentation ticketsRepresentation = TicketsRepresentation.builder().build();
		ticketsRepresentation.setTicketsData(new ArrayList<>());
		tickets.forEach(inventory -> {
		//	inventoryRepresentation.getInventoryData().add(toInventoryDetail(inventory));
		});
		return ticketsRepresentation;
	}
	
}
