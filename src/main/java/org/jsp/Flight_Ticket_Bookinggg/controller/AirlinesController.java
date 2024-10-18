package org.jsp.Flight_Ticket_Bookinggg.controller;

import java.time.LocalDate;

import org.jsp.Flight_Ticket_Bookinggg.servicee.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AirlinesController 
{
	@Autowired
	CommonService commonService;   //12
	
	
	@GetMapping("/")     //1st
	public String loadHome() 
	{
		return "home.html";
	}
	
	 
	@GetMapping("/signup")//5th---it is coming from navbar.html
	public String loadsignup() 
	{
		return "signup.html"; //6th
	}
	
	@PostMapping("/signup")  //8th
	public String signup(@RequestParam String role) //9th
	{
	 return	commonService.signup(role);  //13--------->381
	}
	
 
	
	@GetMapping("/login") //125--459
	public String login()  //126
	{
	  return "login.html";//127--460
	}
	
	@PostMapping("/login")//129--462
	public String login(@RequestParam("email-phone") String emph, String password, HttpSession session) {
		return commonService.login(emph, password, session);//130----171---481
	}
	
	 	 @GetMapping("/logout")//376---from navbar.html
	public String logout(HttpSession session) //375
	{
		session.removeAttribute("agency");//377
		session.removeAttribute("customer");//550 
		session.setAttribute("successMessage", "Logout Success");//378
		return "redirect:/";//379
	}
	 	 
	 	@GetMapping("/book-flight")
		public String loadBookflight() //482
	 	{
			return "book-flight.html";//483
		}
 

	 	@PostMapping("/book-flight")
	 	public String showFlights(@RequestParam String from,@RequestParam String to,@RequestParam LocalDate date,HttpSession session,ModelMap map)  //485
	 	{
			return  commonService.searchFlights(from,to,date,session,map);//486--499--it will go near view routes.html
		}
	 	
	 	@PostMapping("/book-ticket")//502---it is heading from view_routes.html
		public String bookTicket(@RequestParam String from, @RequestParam String to, @RequestParam int routeId,
				HttpSession session, ModelMap map,@RequestParam int seat) 
	 	{
			return  commonService.bookTicket(from, to, routeId, session, map,seat);//503---538--it will go to razorpay.html
		}
	 	@PostMapping("/confirm-order/{id}")//541---it is coming from razorpay.html
		public String confirmOrder(@PathVariable int id,@RequestParam String razorpay_payment_id,HttpSession session) {
			return commonService.confirmOrder(id,razorpay_payment_id,session);//542--551
		}
}
