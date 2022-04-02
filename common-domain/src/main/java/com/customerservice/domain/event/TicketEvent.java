package com.customerservice.domain.event;

import com.customerservice.domain.event.DomainEvent;
import com.customerservice.domain.model.entity.StaffProfile;
import com.customerservice.domain.model.valueobject.MessageSource;

public class TicketEvent extends DomainEvent{

	private String ticketId;
	private String account;
	private MessageSource messageSource;
	private String message;
	private StaffProfile staff;
		
	public TicketEvent(String ticketId, String account, StaffProfile staff, MessageSource messageSource, String message) {
		super();
		this.ticketId = ticketId;
		this.account = account;
		this.staff = staff;
		this.messageSource = messageSource;
		this.message = message;
	}
	
	public String getTicketId() {
		return ticketId;
	}
	public String getAccount() {
		return account;
	}
	public StaffProfile getStaff() {
		return staff;
	}
	public MessageSource getMessageSource() {
		return messageSource;
	}
	public String getMessage() {
		return message;
	}	
}
