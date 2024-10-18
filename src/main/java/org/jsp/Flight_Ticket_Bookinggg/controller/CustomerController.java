package org.jsp.Flight_Ticket_Bookinggg.controller;

import org.jsp.Flight_Ticket_Bookinggg.dto.Customer;
import org.jsp.Flight_Ticket_Bookinggg.servicee.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
@Controller
@RequestMapping("/customer") //383
public class CustomerController //385
{
@Autowired
Customer customer;//386

@Autowired
CustomerService customerService;//396
	
	@GetMapping("/signup")//384
	public String loadSignup(ModelMap map) //387
	{
		map.put("customer", customer);//388-----this customer class object we are taking it for
		return "customer-signup.html";//389
	}
	
 
	@PostMapping("/signup")//391
	public String signup(@Valid Customer customer,BindingResult result,HttpSession session) //392
	{
		return customerService.signup(customer,result,session);  //397--429
	}
	
	
 
	
	@GetMapping("/send-otp/{id}")//430
	public String loadOtpPage(@PathVariable int id,ModelMap map) //431
	{
		map.put("id", id);//432
		return "customer-otp.html";//433
	}
	
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam int id, @RequestParam int otp, HttpSession session) //435
	{
		return customerService.verifyOtp(id, otp, session);//436--449--continuation of 445
	}
	
	@GetMapping("/resend-otp/{id}")//451---it is coming from customer-otp.html
	public String resendOtp(@PathVariable int id, HttpSession session) 
	{
		return customerService.resendOtp(id, session);//452--457
	}
	
 
	@GetMapping("/view-bookings")
	public String viewPastBookings(HttpSession session,ModelMap map) //554--554---it is coming from navbar.html
	{
           return   customerService.viewbookings(session, map);//555
	}
}
