package com.customerservice.ticket.infrastructure.repository.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.customerservice.ticket.domain.projection.CustomerTicketSummary;

@Repository
public interface CustomerTicketSummaryMapper extends JpaRepository<CustomerTicketSummary, Long>  {

	CustomerTicketSummary findByTicketId(String ticketId);
			
	@Query("select t from CustomerTicketSummary t where t.account = ?1")
	List<CustomerTicketSummary> findByAccount(String account);	
}
