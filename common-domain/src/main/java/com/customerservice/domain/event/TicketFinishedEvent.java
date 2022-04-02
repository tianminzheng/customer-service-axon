package com.customerservice.domain.event;

import com.customerservice.domain.model.entity.StaffProfile;
import com.customerservice.domain.model.valueobject.MessageSource;

public class TicketFinishedEvent extends TicketEvent {

	private Integer score;	
	
	public TicketFinishedEvent(String ticketId, String account, StaffProfile staff, MessageSource messageSource,
			String message, Integer score) {
		super(ticketId, account, staff, messageSource, message);
		this.score = score;
	}

	public Integer getScore() {
		return score;
	}
}
