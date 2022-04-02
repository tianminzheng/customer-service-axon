package com.customerservice.ticket.application.commandservice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import com.customerservice.domain.model.entity.OrderProfile;
import com.customerservice.domain.model.entity.StaffProfile;
import com.customerservice.domain.model.valueobject.GoodsProfile;
import com.customerservice.ticket.domain.command.ApplyTicketCommand;
import com.customerservice.ticket.domain.command.FinishTicketCommand;
import com.customerservice.ticket.domain.command.ProcessTicketCommand;
import com.customerservice.ticket.domain.model.aggregate.CustomerTicket;
import com.customerservice.ticket.infrastructure.mask.MaskService;
import com.customerservice.ticket.integration.acl.AclOrderService;
import com.customerservice.ticket.integration.acl.AclStaffService;

@Service
public class CustomerTicketCommandService {

	private CommandGateway commandGateway;
	private AclOrderService aclOrderService;
	private AclStaffService aclStaffService;

	public CustomerTicketCommandService(CommandGateway commandGateway, AclOrderService aclOrderService, AclStaffService aclStaffService) {
		this.commandGateway = commandGateway;
		this.aclOrderService = aclOrderService;
		this.aclStaffService = aclStaffService;
	}

	public void handleCustomerTicketApplication(ApplyTicketCommand applyTicketCommand) {

		// 生成TicketId
		String ticketId = "Ticket" + UUID.randomUUID().toString().toUpperCase();
		applyTicketCommand.setTicketId(ticketId);

		// 调用Order限界上下文获取OrderProfile并填充ApplyTicketCommand
		OrderProfile order = aclOrderService.getOrderInfo(applyTicketCommand.getOrderNumber());
		applyTicketCommand.setOrder(order);
		
		// 调用StaffOrder限界上下文获取StaffOrderProfile并填充ApplyTicketCommand
		StaffProfile staff = aclStaffService.getOptimalStaff(applyTicketCommand.getAccount(),
				applyTicketCommand.getOrder(), applyTicketCommand.getInquire());
		applyTicketCommand.setStaff(staff);

		commandGateway.send(applyTicketCommand);
	}
	
	
	
	public void handleCustomerTicketApplication2(ApplyTicketCommand applyTicketCommand) {

		// 生成TicketId
		String ticketId = "Ticket" + UUID.randomUUID().toString().toUpperCase();
		applyTicketCommand.setTicketId(ticketId);

		// 调用Order限界上下文获取OrderProfile并填充ApplyTicketCommand
//		OrderProfile order = aclOrderService.getOrderInfo(applyTicketCommand.getOrderNumber());
		// mock
		OrderProfile order = new OrderProfile();
		order.setOrderNumber("orderNumber1");
		List<GoodsProfile> goodsList = new ArrayList<GoodsProfile>();
		goodsList.add(new GoodsProfile("goodsCode1", "goodsName1", 100F));
		order.setGoodsList(goodsList);
		order.setDeliveryAddress("deliveryAddress1");
		applyTicketCommand.setOrder(order);

		// 调用StaffOrder限界上下文获取StaffOrderProfile并填充ApplyTicketCommand
//		StaffProfile staff = aclStaffService.getOptimalStaff(applyTicketCommand.getAccount(),
//				applyTicketCommand.getOrder(), applyTicketCommand.getInquire());
		// mock
		StaffProfile staff = new StaffProfile("staff1", "staffname1", "description1");
		applyTicketCommand.setStaff(staff);

		commandGateway.send(applyTicketCommand);
	}

	public void handleCustomerTicketProcessing(ProcessTicketCommand processTicketCommand) {
		// 调用基础设施层的MaskService进行脱敏
		String message = processTicketCommand.getMessage();
		processTicketCommand.setMessage(MaskService.performMask(message));

		commandGateway.send(processTicketCommand);
	}

	public void handleCustomerTicketFinishing(FinishTicketCommand finishTicketCommand) {
		commandGateway.send(finishTicketCommand);
	}
}
