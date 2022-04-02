package com.customerservice.ticket.integration.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import com.customerservice.domain.event.TicketAppliedEvent;
import com.customerservice.domain.event.TicketFinishedEvent;
import com.customerservice.domain.event.TicketProcessedEvent;
import com.customerservice.ticket.application.commandservice.CustomerTicketProjectionService;
import com.customerservice.ticket.application.queryservice.CustomerTicketQueryService;
import com.customerservice.ticket.domain.model.valueobject.TicketStatus;
import com.customerservice.ticket.domain.projection.CustomerTicketSummary;
import com.customerservice.ticket.infrastructure.messaging.CustomerTicketSource;

@Service
@EnableBinding(CustomerTicketSource.class)
public class TicketEventPublisherService {

	private CustomerTicketProjectionService customerTicketProjectionService;

	private CustomerTicketQueryService customerTicketQueryService;

	private CustomerTicketSource customerTicketSource;

	public TicketEventPublisherService(CustomerTicketProjectionService customerTicketProjectionService,
			CustomerTicketQueryService customerTicketQueryService, CustomerTicketSource customerTicketSource) {
		this.customerTicketProjectionService = customerTicketProjectionService;
		this.customerTicketQueryService = customerTicketQueryService;
		this.customerTicketSource = customerTicketSource;
	}

	@EventHandler
	public void handleTicketAppliedEvent(TicketAppliedEvent ticketAppliedEvent) {

		CustomerTicketSummary customerTicketSummary = new CustomerTicketSummary();
		customerTicketSummary.setTicketId(ticketAppliedEvent.getTicketId());
		customerTicketSummary.setAccount(ticketAppliedEvent.getAccount());
		customerTicketSummary.setOrderNumber(ticketAppliedEvent.getOrder().getOrderNumber());
		customerTicketSummary.setStaff(ticketAppliedEvent.getStaff().getStaffName());
		customerTicketSummary.setStatus(TicketStatus.INITIALIZED.toString());
		customerTicketSummary.setScore(0);

		customerTicketProjectionService.saveCustomerTicketSummary(customerTicketSummary);

		customerTicketSource.ticketApplication().send(MessageBuilder.withPayload(ticketAppliedEvent).build());
	}

	@EventHandler
	public void handleTicketProcessedEvent(TicketProcessedEvent ticketProcessedEvent) {

		CustomerTicketSummary customerTicketSummary = customerTicketQueryService
				.findByTicketId(ticketProcessedEvent.getTicketId()).getCustomerTicketSummary();
		// 针对customerTicketSummary的任何处理

		customerTicketSource.ticketProcessing().send(MessageBuilder.withPayload(ticketProcessedEvent).build());
	}

	@EventHandler
	public void handleTicketFinishedEvent(TicketFinishedEvent ticketFinishedEvent) {

		CustomerTicketSummary customerTicketSummary = customerTicketQueryService
				.findByTicketId(ticketFinishedEvent.getTicketId()).getCustomerTicketSummary();
		// 针对customerTicketSummary的任何处理

		customerTicketSource.ticketFinishing().send(MessageBuilder.withPayload(ticketFinishedEvent).build());
	}
}

