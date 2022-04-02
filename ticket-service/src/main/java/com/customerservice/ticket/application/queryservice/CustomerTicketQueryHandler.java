package com.customerservice.ticket.application.queryservice;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import com.customerservice.ticket.domain.projection.CustomerTicketSummary;
import com.customerservice.ticket.domain.query.CustomerTicketSummaryAccountQuery;
import com.customerservice.ticket.domain.query.CustomerTicketSummaryListResult;
import com.customerservice.ticket.domain.query.CustomerTicketSummaryQuery;
import com.customerservice.ticket.domain.query.CustomerTicketSummaryResult;
import com.customerservice.ticket.infrastructure.repository.mapper.CustomerTicketSummaryMapper;

@Service
public class CustomerTicketQueryHandler {

	private CustomerTicketSummaryMapper customerTicketSummaryMapper;

	public CustomerTicketQueryHandler(CustomerTicketSummaryMapper customerTicketSummaryMapper) {
		this.customerTicketSummaryMapper = customerTicketSummaryMapper;
	}

	@QueryHandler
	public CustomerTicketSummaryResult handle(CustomerTicketSummaryQuery customerTicketSummaryQuery) {
		
		CustomerTicketSummary customerTicketSummary = customerTicketSummaryMapper.findByTicketId(customerTicketSummaryQuery.getTicketId());
		CustomerTicketSummaryResult customerTicketSummaryResult = new CustomerTicketSummaryResult(customerTicketSummary);
		return customerTicketSummaryResult;
	}

	@QueryHandler
	public CustomerTicketSummaryListResult handle(CustomerTicketSummaryAccountQuery customerTicketSummaryAccountQuery) {
		
		List<CustomerTicketSummary> customerTicketSummaries = customerTicketSummaryMapper.findByAccount(customerTicketSummaryAccountQuery.getAccount());
		CustomerTicketSummaryListResult customerTicketSummaryListResult = new CustomerTicketSummaryListResult(customerTicketSummaries);
		return customerTicketSummaryListResult;
	}
}
