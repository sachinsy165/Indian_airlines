package org.jsp.Flight_Ticket_Bookinggg.repository;

import org.jsp.Flight_Ticket_Bookinggg.dto.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency, Integer>
{
  boolean   existsByEmailAndStatusTrue(String email);//33
  
  boolean   existsByMobileAndStatusTrue(long mobile); //43
  
	Agency findByMobile(long mobile);//55
	
   Agency	findByEmail(String email);  //64
   
    
}
