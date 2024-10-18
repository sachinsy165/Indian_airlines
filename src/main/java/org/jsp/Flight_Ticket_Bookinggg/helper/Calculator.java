package org.jsp.Flight_Ticket_Bookinggg.helper;

import java.time.LocalTime;
import java.util.List;

import org.jsp.Flight_Ticket_Bookinggg.dto.Route;
import org.jsp.Flight_Ticket_Bookinggg.dto.Station;
import org.springframework.stereotype.Component;

 

@Component //512
public class Calculator //511
{

	public double calculatePrice(String from, String to, Route route) //515
	{
		 double fromPrice=0;
		 double toPrice=0;
		 
	List<Station>stations	= route.getStations();
	for (Station station : stations) 
	{
		if(station.getName().equals(from))
		{
			fromPrice =station.getPrice();
		}
		if(station.getName().equals(to))
		{
			toPrice  =station.getPrice();
		}
	}
	double actual_price	=toPrice-fromPrice;
	return actual_price;//516
	}

	public LocalTime timeCalculator(String location, Route route) //528
	{
	 
	for ( Station station :  route.getStations()) 
	{
		if(station.getName().equals(location))
		{
		  return 	station.getTime();
		}
		
	}	   
		return null;
	}

 
	
	
}
