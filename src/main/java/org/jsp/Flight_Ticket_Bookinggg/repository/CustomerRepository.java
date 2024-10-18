package org.jsp.Flight_Ticket_Bookinggg.repository;

import org.jsp.Flight_Ticket_Bookinggg.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

 

public interface CustomerRepository extends JpaRepository<Customer, Integer>
{

  boolean	existsByEmail(String email);//40
  
  boolean existsByMobile(long mobile);//48
  
   Customer   findByMobile(long mobile);//139
   
   Customer  findByEmail(String email);//150
	   
}
