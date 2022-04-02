package com.customerservice.ticket.domain.query;

import com.customerservice.ticket.domain.projection.CustomerTicketSummary;

public class CustomerTicketSummaryResult {

	private CustomerTicketSummary customerTicketSummary;
	
	public CustomerTicketSummaryResult(CustomerTicketSummary customerTicketSummary) {
		super();
		this.customerTicketSummary = customerTicketSummary;
	}

	public CustomerTicketSummary getCustomerTicketSummary() {
		return customerTicketSummary;
	}

	public void setCustomerTicketSummary(CustomerTicketSummary customerTicketSummary) {
		this.customerTicketSummary = customerTicketSummary;
	}
}
