package org.jsp.Flight_Ticket_Bookinggg.service.serviceimplementation;

import java.util.List;
import java.util.Random;

import org.jsp.Flight_Ticket_Bookinggg.dao.CustomerDao;
import org.jsp.Flight_Ticket_Bookinggg.dto.Customer;
import org.jsp.Flight_Ticket_Bookinggg.dto.TripOrder;
import org.jsp.Flight_Ticket_Bookinggg.helper.AES;
import org.jsp.Flight_Ticket_Bookinggg.helper.MailSendingHelper;
import org.jsp.Flight_Ticket_Bookinggg.servicee.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
 

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Service//403
public class Customer_service_implementation implements CustomerService//394
{

	 
		 
		
		@Autowired
		CustomerDao customerDao;//395

		@Autowired
		MailSendingHelper mailSendingHelper;//396

		@Override
		public String signup(@Valid Customer customer, BindingResult result, HttpSession session) {
			if (!customer.getPassword().equals(customer.getCpassword()))//397
				result.rejectValue("cpassword", "error.cpassword", "* Password and Confirm Password Should be Matching");//398
			if (customerDao.checkEmail(customer.getEmail()) || customerDao.checkEmail(customer.getEmail()))//399
				result.rejectValue("email", "error.email", "* Email Should be Unique");//400
			if (customerDao.checkMobile(customer.getMobile()) || customerDao.checkMobile(customer.getMobile()))//401
				result.rejectValue("mobile", "error.mobile", "* Mobile Number Should be Unique");//402

			if (result.hasErrors())//403
				return "customer-signup.html";//404
			else {
				customerDao.deleteIfExists(customer);//405
				customer.setCpassword(AES.encrypt(customer.getCpassword(), "123"));//406
				customer.setPassword(AES.encrypt(customer.getPassword(), "123"));
				customer.setOtp(new Random().nextInt(100000, 1000000));
				System.out.println("Otp - " + customer.getOtp());
				if (mailSendingHelper.sendEmail(customer)) 
				{
					customerDao.save(customer);//415
					session.setAttribute("successMessage", "Otp Sent Success");
					return "redirect:/customer/send-otp/" + customer.getId() + "";//425
				} else {
					session.setAttribute("failMessage", "Sorry Not able to send OTP");
					return "redirect:/customer/signup";//426
				}

			}
		}

		@Override
		public String verifyOtp(int id, int otp, HttpSession session) //438
		{
			Customer customer = customerDao.findById(id);//439
			if (customer.getOtp() == otp) //440
			{
				customer.setStatus(true);//441
				customerDao.save(customer);//442
				session.setAttribute("successMessage", "Otp Verified Success, You can Lgin Now");//444
				return "redirect:/login";//445
			} else {
				session.setAttribute("failMessage", "Invalid Otp, Try Again");//446
				return "redirect:/customer/send-otp/" + customer.getId() + "";//447
			}
		 
		}

		@Override
		public String resendOtp(int id, HttpSession session) //454
		{
			Customer customer = customerDao.findById(id);
			customer.setOtp(new Random().nextInt(100000, 1000000));
			System.out.println("Otp - " + customer.getOtp());
			if (mailSendingHelper.sendEmail(customer)) {
				customerDao.save(customer);
				session.setAttribute("successMessage", "Otp Re-Sent Success");
				return "redirect:/customer/send-otp/" + customer.getId() + "";//455
			} else {
				session.setAttribute("failMessage", "Failed to Send Otp");
				return "redirect:/customer/send-otp/" + customer.getId() + "";//456
			}
		}

		@Override
		public String viewbookings(HttpSession session, ModelMap map) //557
		{
			Customer customer = (Customer) session.getAttribute("customer");//558--continuation of 165th step
			if(customer==null)
			{  
				       
				session.setAttribute("failMessage", "first login to see Bookings");//559
				return "redirect:/login";//560
				
			}
			else
			{
			List<TripOrder>	orders    =customer.getTripOrders();
			if(orders.isEmpty())
			{
				session.setAttribute("failMessage", "No Bookings Yet");//561
				return "redirect:/";//562
			}
			else
			{
				map.put("orders", orders);//563
				return "view-bookings.html";//564
			}
			}
		 
			
			
			
		}
		
		
		
		
		
		
		
//		@Override
//		public String viewbookings(HttpSession session, ModelMap map) {
//			Customer customer = (Customer) session.getAttribute("customer");
//			if (customer == null) {
//				session.setAttribute("failMessage", "First Login to Book");
//				return "redirect:/login";
//			} else {
//				List<TripOrder> orders=customer.getTripOrders();
//				if(orders.isEmpty()) {
//					session.setAttribute("failMessage", "No Bookings Yet");
//					return "redirect:/";
//				}
//				else {
//					map.put("orders", orders);
//					return "view-bookings.html";
//				}
//			}
//		}		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}



