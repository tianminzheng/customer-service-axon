package com.customerservice.ticket.domain.query;

public class CustomerTicketSummaryAccountQuery {

	private String account;
	
	public CustomerTicketSummaryAccountQuery(String account) {
		super();
		this.account = account;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
