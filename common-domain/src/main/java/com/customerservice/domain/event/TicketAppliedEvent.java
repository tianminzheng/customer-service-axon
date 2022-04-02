package com.customerservice.domain.event;

import com.customerservice.domain.model.entity.OrderProfile;
import com.customerservice.domain.model.entity.StaffProfile;
import com.customerservice.domain.model.valueobject.MessageSource;

public class TicketAppliedEvent extends TicketEvent{
	
	private OrderProfile order;
	
	public TicketAppliedEvent(String ticketId, String account, StaffProfile staff, MessageSource messageSource,
			String message, OrderProfile order) {
		super(ticketId, account, staff, messageSource, message);
		
		this.order = order;
	}

	public OrderProfile getOrder() {
		return order;
	}
}
