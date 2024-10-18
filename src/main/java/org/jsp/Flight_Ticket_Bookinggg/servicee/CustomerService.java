package org.jsp.Flight_Ticket_Bookinggg.servicee;

import org.jsp.Flight_Ticket_Bookinggg.dto.Customer;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

public interface CustomerService 
{

	String signup(@Valid Customer customer, BindingResult result, HttpSession session);//393

	String verifyOtp(int id, int otp, HttpSession session);//437

	String resendOtp(int id, HttpSession session);//453

	String viewbookings(HttpSession session, ModelMap map);//556

}
