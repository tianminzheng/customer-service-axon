package com.customerservice.ticket.domain.model.aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import com.customerservice.domain.event.TicketAppliedEvent;
import com.customerservice.domain.event.TicketFinishedEvent;
import com.customerservice.domain.event.TicketProcessedEvent;
import com.customerservice.domain.model.entity.StaffProfile;
import com.customerservice.domain.model.valueobject.MessageSource;
import com.customerservice.ticket.domain.command.ApplyTicketCommand;
import com.customerservice.ticket.domain.command.FinishTicketCommand;
import com.customerservice.ticket.domain.command.ProcessTicketCommand;
import com.customerservice.ticket.domain.model.entity.Consultation;
import com.customerservice.ticket.domain.model.valueobject.Message;
import com.customerservice.ticket.domain.model.valueobject.TicketScore;
import com.customerservice.ticket.domain.model.valueobject.TicketStatus;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;;

@Aggregate
public class CustomerTicket {

	@AggregateIdentifier
	private String ticketId;// 客服工单唯一编号
	private Consultation consultation;// 用户咨询
	private StaffProfile staff;// 客服人员
	private TicketStatus status;// 工单状态
	private List<Message> messages;// 工单详细消息列表
	private Message latestMessage;// 工单最近一条消息
	private TicketScore score;// 工单评分
	
	public CustomerTicket() {

	}

	@CommandHandler
	public CustomerTicket(ApplyTicketCommand applyTicketCommand) {

		// 执行业务验证规则
		if (applyTicketCommand.getTicketId().isEmpty()) {
			throw new IllegalArgumentException("TicketId should not be empty.");
		}

		
		// 发送领域事件
		TicketAppliedEvent ticketAppliedEvent = new TicketAppliedEvent(applyTicketCommand.getTicketId(),
				applyTicketCommand.getAccount(), applyTicketCommand.getStaff(), MessageSource.CUSTOMER,
				applyTicketCommand.getInquire(), applyTicketCommand.getOrder());
		apply(ticketAppliedEvent);
	}

	@CommandHandler
	public void processTicket(ProcessTicketCommand processTicketCommand) {

		// 验证TicketId是否对当前CustomerTicket对象是否有效
		String ticketId = processTicketCommand.getTicketId();
		if (!ticketId.equals(this.ticketId)) {
			throw new IllegalArgumentException("TicketId is invalid.");
		}

		// 发送领域事件
		TicketProcessedEvent ticketProcessedEvent = new TicketProcessedEvent(processTicketCommand.getTicketId(),
				this.consultation.getAccount(), this.getStaff(), processTicketCommand.getMessageSource(),
				processTicketCommand.getMessage());
		apply(ticketProcessedEvent);
	}

	@CommandHandler
	public void finishTicket(FinishTicketCommand finishTicketCommand) {

		// 验证TicketId是否对当前CustomerTicket对象是否有效
		String ticketId = finishTicketCommand.getTicketId();
		if (!ticketId.equals(this.ticketId)) {
			throw new IllegalArgumentException("TicketId is invalid.");
		}

		Integer score = finishTicketCommand.getScore();
		if (score < 0 || score > 5) {
			throw new IllegalArgumentException("Ticket Score is invalid.");
		}

		// 发送领域事件
		TicketFinishedEvent ticketFinishedEvent = new TicketFinishedEvent(finishTicketCommand.getTicketId(),
				this.consultation.getAccount(), this.getStaff(), MessageSource.CUSTOMER,
				finishTicketCommand.getMessage(), finishTicketCommand.getScore());
		apply(ticketFinishedEvent);
	}

	@EventSourcingHandler
	public void on(TicketAppliedEvent ticketAppliedEvent) {
		
		// 1.设置聚合标识符
		this.ticketId = ticketAppliedEvent.getTicketId();

		// 2.创建Consultation
		String consultationId = "Consultation" + UUID.randomUUID().toString().toUpperCase();
		this.consultation = new Consultation(consultationId, ticketAppliedEvent.getAccount(),
				ticketAppliedEvent.getOrder(), ticketAppliedEvent.getMessage());

		// 3.获取客服信息
		this.staff = ticketAppliedEvent.getStaff();

		// 4.初始化基础信息
		this.status = TicketStatus.INITIALIZED;
		this.messages = new ArrayList<Message>();
		this.score = new TicketScore(0);
	}

	@EventSourcingHandler
	public void on(TicketProcessedEvent ticketProcessedEvent) {

		// 构建Message
		Message message = new Message(ticketProcessedEvent.getTicketId(), ticketProcessedEvent.getAccount(),
				this.staff.getStaffName(), ticketProcessedEvent.getMessageSource(), ticketProcessedEvent.getMessage());
		latestMessage = message;
		this.messages.add(message);

		// 更新工单状态
		this.status = TicketStatus.INPROCESS;
	}

	@EventSourcingHandler
	public void on(TicketFinishedEvent ticketFinishedEvent) {
		
		// 构建Message
		Message message = new Message(ticketFinishedEvent.getTicketId(), this.consultation.getAccount(),
				this.staff.getStaffName(), MessageSource.CUSTOMER, ticketFinishedEvent.getMessage());
		latestMessage = message;
		this.messages.add(message);

		// 更新工单状态
		this.status = TicketStatus.CLOSED;

		// 设置工单评分
		this.score = new TicketScore(ticketFinishedEvent.getScore());
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public Consultation getConsultation() {
		return consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

	public StaffProfile getStaff() {
		return staff;
	}

	public void setStaff(StaffProfile staff) {
		this.staff = staff;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Message getLatestMessage() {
		return latestMessage;
	}

	public void setLatestMessage(Message latestMessage) {
		this.latestMessage = latestMessage;
	}

	public TicketScore getScore() {
		return score;
	}

	public void setScore(TicketScore score) {
		this.score = score;
	}
}
