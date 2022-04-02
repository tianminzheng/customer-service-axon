package com.customerservice.ticket.domain.query;

import java.util.List;

import com.customerservice.ticket.domain.projection.CustomerTicketSummary;

public class CustomerTicketSummaryListResult {

	private List<CustomerTicketSummary> customerTicketSummaries;
	
	public CustomerTicketSummaryListResult(List<CustomerTicketSummary> customerTicketSummaries) {
		super();
		this.customerTicketSummaries = customerTicketSummaries;
	}

	public List<CustomerTicketSummary> getCustomerTicketSummaries() {
		return customerTicketSummaries;
	}

	public void setCustomerTicketSummaries(List<CustomerTicketSummary> customerTicketSummaries) {
		this.customerTicketSummaries = customerTicketSummaries;
	}
}
