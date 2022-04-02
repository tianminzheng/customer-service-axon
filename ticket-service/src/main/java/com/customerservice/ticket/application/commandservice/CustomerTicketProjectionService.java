package com.customerservice.ticket.application.commandservice;

import org.springframework.stereotype.Service;

import com.customerservice.ticket.domain.projection.CustomerTicketSummary;
import com.customerservice.ticket.infrastructure.repository.mapper.CustomerTicketSummaryMapper;

@Service
public class CustomerTicketProjectionService {

	private CustomerTicketSummaryMapper customerTicketSummaryMapper;

	public CustomerTicketProjectionService(CustomerTicketSummaryMapper customerTicketSummaryMapper) {
		this.customerTicketSummaryMapper = customerTicketSummaryMapper;
	}

	public void saveCustomerTicketSummary(CustomerTicketSummary customerTicketSummary) {
		customerTicketSummaryMapper.save(customerTicketSummary);
	}
}
