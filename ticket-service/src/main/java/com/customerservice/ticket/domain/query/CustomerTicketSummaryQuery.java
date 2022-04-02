package com.customerservice.ticket.domain.query;

public class CustomerTicketSummaryQuery {

	private String ticketId;
	
	public CustomerTicketSummaryQuery(String ticketId) {
		super();
		this.ticketId = ticketId;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

}
