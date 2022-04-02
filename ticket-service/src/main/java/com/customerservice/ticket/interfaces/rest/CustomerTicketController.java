package com.customerservice.ticket.interfaces.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerservice.ticket.application.commandservice.CustomerTicketCommandService;
import com.customerservice.ticket.application.queryservice.CustomerTicketQueryService;
import com.customerservice.ticket.domain.command.ApplyTicketCommand;
import com.customerservice.ticket.domain.command.FinishTicketCommand;
import com.customerservice.ticket.domain.command.ProcessTicketCommand;
import com.customerservice.ticket.domain.projection.CustomerTicketSummary;
import com.customerservice.ticket.interfaces.rest.assembler.ApplyTicketCommandDTOAssembler;
import com.customerservice.ticket.interfaces.rest.assembler.FinishTicketCommandDTOAssembler;
import com.customerservice.ticket.interfaces.rest.assembler.ProcessTicketCommandDTOAssembler;
import com.customerservice.ticket.interfaces.rest.dto.ApplyTicketDTO;
import com.customerservice.ticket.interfaces.rest.dto.ProcessTicketDTO;
import com.customerservice.ticket.interfaces.rest.dto.FinishTicketDTO;

@RestController
@RequestMapping(value = "tickets")
public class CustomerTicketController {

	private CustomerTicketCommandService customerTicketCommandService;
	private CustomerTicketQueryService customerTicketQueryService;

	public CustomerTicketController(CustomerTicketCommandService customerTicketCommandService,
			CustomerTicketQueryService customerTicketQueryService) {
		this.customerTicketCommandService = customerTicketCommandService;
		this.customerTicketQueryService = customerTicketQueryService;
	}
	
	@GetMapping(value = "/{ticketId}")
	public CustomerTicketSummary getCustomerTicketById(@PathVariable String ticketId) {
		CustomerTicketSummary customerTicketSummary = customerTicketQueryService.findByTicketId(ticketId).getCustomerTicketSummary();
		return customerTicketSummary;
	}
	
	@GetMapping(value = "/account/{account}")
	public List<CustomerTicketSummary> getCustomerTicketByAccount(@PathVariable String account) {
		List<CustomerTicketSummary> customerTicketSummarys = customerTicketQueryService.findByUserAccount(account).getCustomerTicketSummaries();
		return customerTicketSummarys;
	}

	@PostMapping(value = "/application")
	public void applyTicket(@RequestBody ApplyTicketDTO applyTicketDTO) {
		ApplyTicketCommand applyTicketCommand = ApplyTicketCommandDTOAssembler.toCommandFromDTO(applyTicketDTO);
		customerTicketCommandService.handleCustomerTicketApplication(applyTicketCommand);
	}

	@PostMapping(value = "/processing")
	public void processTicket(@RequestBody ProcessTicketDTO processTicketDTO) {
		ProcessTicketCommand processTicketCommand = ProcessTicketCommandDTOAssembler.toCommandFromDTO(processTicketDTO);
		customerTicketCommandService.handleCustomerTicketProcessing(processTicketCommand);
	}
	
	@PostMapping(value = "/finishing")
	public void finishPlan(@RequestBody FinishTicketDTO finishTicketDTO) {
		FinishTicketCommand finishTicketCommand = FinishTicketCommandDTOAssembler.toCommandFromDTO(finishTicketDTO);
		customerTicketCommandService.handleCustomerTicketFinishing(finishTicketCommand);
	}

}