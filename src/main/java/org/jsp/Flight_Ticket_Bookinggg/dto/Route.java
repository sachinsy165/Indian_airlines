package org.jsp.Flight_Ticket_Bookinggg.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
@Data
@Component
@Entity
public class Route  //222
{
	 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //223
	@ManyToOne    //bidirectional
	Flight flight;//224---it means same flight ex:2134-From akasa--it can travel for many routes like mumbai,kolkata,hyderabad,kochi,banglore etc
	 @ManyToOne    //unidirectional---we can make bidirectional also there is no restriction
	 Agency agency;//226 it means same agency From akasa--flights  can travel for many routes like mumbai,kolkata,hyderabad,kochi,banglore etc
	 @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)//cascade will help us to save station table information without query
	 List<Station> stations;//227--->single route can have multiple stations---->banglore to chickmaglore------>bang-tumkur-ckm
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public Agency getAgency() {
		return agency;
	}
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	public List<Station> getStations() {
		return stations;
	}
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}
	public boolean isMonday() {
		return monday;
	}
	public void setMonday(boolean monday) {
		this.monday = monday;
	}
	public boolean isTuesday() {
		return tuesday;
	}
	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}
	public boolean isWednesday() {
		return wednesday;
	}
	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}
	public boolean isThursday() {
		return thursday;
	}
	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}
	public boolean isFriday() {
		return friday;
	}
	public void setFriday(boolean friday) {
		this.friday = friday;
	}
	public boolean isSaturday() {
		return saturday;
	}
	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}
	public boolean isSunday() {
		return sunday;
	}
	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}
	
}
