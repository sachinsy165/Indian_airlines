package org.jsp.Flight_Ticket_Bookinggg.controller;

import java.awt.BufferCapabilities.FlipContents;
import java.util.Map;

import org.jsp.Flight_Ticket_Bookinggg.dto.Agency;
import org.jsp.Flight_Ticket_Bookinggg.dto.Flight;
import org.jsp.Flight_Ticket_Bookinggg.dto.Route;
import org.jsp.Flight_Ticket_Bookinggg.servicee.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

 

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/agency")  //15th
public class AgencyController 
{

	@Autowired
	Agency agency;
	
	@Autowired
	AgencyService agencyService;//26th
	
	@Autowired
	Flight flight; //extra
	
	@GetMapping("/signup")  //16th
	public String loadSignup(ModelMap map)  //17th
	{
		map.put("agency", agency);  //19th
		
		return "agency-signup.html";//20
	}
	
	@PostMapping("/signup")//22
	public String signup(@Valid Agency agency,BindingResult result,HttpSession session) //23
	{
		return agencyService.signup(agency,result,session); //27th
	}
	
 
	@GetMapping("/send-otp/{id}")//107----   125 c
	 public String loadOtpPage(@PathVariable int id,ModelMap modelMap) //108
	 {
		modelMap.put("id",id);//109
		return "agency-otp.html";//111----125 d
		
	  }
	
 
	
	
	@PostMapping("/verify-otp")//113
	public String verifyOtp(@RequestParam int id,@RequestParam int otp,HttpSession session) //114
	{
		return agencyService.verifyOtp(id,otp,session);//115
	}
	
	
 
	
	@GetMapping("/resend-otp/{id}") //125 f
	public String resendOtp(@PathVariable int id, HttpSession session) //125 g
	{
	return	agencyService.resendOtp(id,session);//125 h
	}
	
	@GetMapping("/add-flight")  //173---from navbar this request will come
//	public String addFlight() //174----like this also it will work
	public String addFlight(ModelMap map) //extra
	{
		map.put("flight", flight);
		return "add-flight.html";//175
	}
	
	 
	
	@PostMapping("/add-flight")//177
	public String addFlight(Flight flight,@RequestParam MultipartFile image,HttpSession session) //180 since image will store and come in the file format i need to take data tyoe or class type as MultipartFile and since it is having unique data type its better to use @Requestparam
	{
		String response = agencyService.addFlight(flight, image, session);//181
		return response;//198
	}
	
 
	
	@GetMapping("/add-route")
	public String addRoute(HttpSession session,ModelMap map) //200----from navbar this request is coming
	{
		return agencyService.addRoute(session,map);
	}
 
	
	@PostMapping("/add-route")//229
	public String addRoute(Route route,HttpSession session) //230---inside route reference variable we are carrying both station and route information like from station mid station,to station and from route sections sunday monday tuesday   like that we can do like this because we established @Onetomany bidirectional relation ship between Route and stations with the help of Cascade
	{
	 return	agencyService.addRoute(route, session);//231----245
	}
	
    @GetMapping("/manage-route")//247---this url is coming from navbar.html
	public String manageRoute(HttpSession session,ModelMap map) 
	{
	 return  agencyService.fetchRoute(session,map);//248 ----263
	}
    
 
    
    
    @GetMapping("/edit-route/{id}")
    public String editRoute(@PathVariable int id,HttpSession session,ModelMap map) //266
    {
	   return	agencyService.editRoute(id,session,map);//267---274
	}
    
	 
	 
    
    @PostMapping("/edit-route")
    public String name(@ModelAttribute Route route,HttpSession session)  //277
    {
		return agencyService.editRoute(route,session);//278
	}
    
 
      @GetMapping("/delete-route/{id}")
    public String deleteRoute(@PathVariable int id,HttpSession session) //293
    {
		String response = agencyService.deleteRoute(id,session);//294--312
		return response; //313---from here it will go to navabar.html near 246 th step
	}
    
 
    
      @GetMapping("/manage-flight")//315---frrom navbar.html
      public String manageFlight(HttpSession session,ModelMap map) 
      {
		return   agencyService.fetchFlights(session,map);//316---326
	}
      
 
    
      @GetMapping("/delete-flight/{id}")
    public String deleteFlight(@PathVariable int id,HttpSession session) //330_from fetch_flights.html
    {
		return agencyService.deleteFlight(id,session);//331----342---it will go to front end that is fetch-flights.html because it is having url "redirect:/agency/manage-flight";
	}
    
    
 
    
      @GetMapping("/edit-flight/{id}")
      public String editFlight(@PathVariable int id,HttpSession session,ModelMap map)   //344
    {
		return agencyService.editFlight(id,session,map);//345----354
	}
    
      
//      @PostMapping("/edit-bus")
//  	public String editBus(Bus bus, @RequestParam MultipartFile image,HttpSession session) {
//  		return agencyService.editBus(bus,image,session);
//  	}
    
      @PostMapping("/edit-flight")
    public String editFlight(Flight flight,@RequestParam MultipartFile image,HttpSession session)   //357
    {
	  return	agencyService.editFlight(flight,image, session);//358-----371---it will get navbar.html
	}
    
    
    
    
	
}
