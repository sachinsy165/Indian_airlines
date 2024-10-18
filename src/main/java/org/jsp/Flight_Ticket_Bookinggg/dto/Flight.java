package org.jsp.Flight_Ticket_Bookinggg.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

 

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data //179
@Component
@Entity //178

public class Flight //177
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private boolean ac;
	private boolean sleeper;
	private int seat;
	private String regno;
	private String imageLink;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)//177cascade will help us to store the information of all routes directly without writing queries
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)//341 it will help when flight entity is Deleted the respective route entities also will get deleted automatically--here without writing code to seperate routes from flight when we are going to delete the flight we can delete the routes
	List<Route> routes=new ArrayList<Route>();//225

	public int getId() 
	{
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAc() {
		return ac;
	}

	public void setAc(boolean ac) {
		this.ac = ac;
	}

	public boolean isSleeper() {
		return sleeper;
	}

	public void setSleeper(boolean sleeper) {
		this.sleeper = sleeper;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
	 
	
	
	
	 

}
