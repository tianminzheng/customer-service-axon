package com.customerservice.ticket.application.queryservice;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import com.customerservice.ticket.domain.query.CustomerTicketSummaryAccountQuery;
import com.customerservice.ticket.domain.query.CustomerTicketSummaryListResult;
import com.customerservice.ticket.domain.query.CustomerTicketSummaryQuery;
import com.customerservice.ticket.domain.query.CustomerTicketSummaryResult;

@Service
public class CustomerTicketQueryService {

    private QueryGateway queryGateway;

	public CustomerTicketQueryService(QueryGateway queryGateway) {
		this.queryGateway = queryGateway;
	}

	public CustomerTicketSummaryResult findByTicketId(String ticketId) {

		CustomerTicketSummaryQuery customerTicketSummaryQuery = new CustomerTicketSummaryQuery(ticketId);
		CustomerTicketSummaryResult customerTicketSummaryResult = queryGateway.query(customerTicketSummaryQuery,
				CustomerTicketSummaryResult.class).join();

	    return customerTicketSummaryResult;	
	}
	
	public CustomerTicketSummaryListResult findByUserAccount(String account) {
		
		CustomerTicketSummaryAccountQuery customerTicketSummaryAccountQuery = new CustomerTicketSummaryAccountQuery(account);
		CustomerTicketSummaryListResult customerTicketSummaryListResult = queryGateway.query(customerTicketSummaryAccountQuery,
				CustomerTicketSummaryListResult.class).join();
		
		return customerTicketSummaryListResult;
	}	
}
