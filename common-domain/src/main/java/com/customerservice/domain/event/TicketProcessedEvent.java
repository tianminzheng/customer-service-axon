package com.customerservice.domain.event;

import com.customerservice.domain.model.entity.StaffProfile;
import com.customerservice.domain.model.valueobject.MessageSource;

public class TicketProcessedEvent extends TicketEvent{

	public TicketProcessedEvent(String ticketId, String account, StaffProfile staff, MessageSource messageSource,
			String message) {
		super(ticketId, account, staff, messageSource, message);
	}
}
