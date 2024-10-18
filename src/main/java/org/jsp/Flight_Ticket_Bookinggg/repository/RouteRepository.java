package org.jsp.Flight_Ticket_Bookinggg.repository;

import java.util.ArrayList;
import java.util.List;

import org.jsp.Flight_Ticket_Bookinggg.dto.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

public interface RouteRepository extends JpaRepository<Route, Integer>//254
{

	List<Route>   findByFlight_idIn(List<Integer> list);//257---here base on flight_id---1----it will help us to fetch the route from banglore  to ckm
                                                                 //	flight_id---2----from Kolar to mylar  
	                                                             //	flight_id---2----from manglore  mid udupi to ckm
	   
	   //257line-Justification-because of this--225th  line--->@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)//cascade and fetch=FetchType.EAGER will help us to store and fetch the information of all routes directly without writing queries
       //257-line justification-List<Route> routes=new ArrayList<Route>();

}
