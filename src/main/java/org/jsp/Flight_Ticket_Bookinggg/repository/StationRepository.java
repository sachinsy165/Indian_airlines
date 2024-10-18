package org.jsp.Flight_Ticket_Bookinggg.repository;

import java.util.List;

import org.jsp.Flight_Ticket_Bookinggg.dto.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository   extends JpaRepository<Station, Integer>                   //314
{

//	void findbyName(String from);
	List<Station> findByName(String from);//491

}
