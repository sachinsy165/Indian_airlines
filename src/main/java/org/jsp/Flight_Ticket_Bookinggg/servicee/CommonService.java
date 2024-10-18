package org.jsp.Flight_Ticket_Bookinggg.servicee;

import java.beans.Customizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.jsp.Flight_Ticket_Bookinggg.dao.AgencyDao;
import org.jsp.Flight_Ticket_Bookinggg.dao.CustomerDao;
import org.jsp.Flight_Ticket_Bookinggg.dto.Agency;
import org.jsp.Flight_Ticket_Bookinggg.dto.Customer;
import org.jsp.Flight_Ticket_Bookinggg.dto.Flight;
import org.jsp.Flight_Ticket_Bookinggg.dto.Route;
import org.jsp.Flight_Ticket_Bookinggg.dto.Station;
import org.jsp.Flight_Ticket_Bookinggg.dto.TripOrder;
import org.jsp.Flight_Ticket_Bookinggg.helper.AES;
import org.jsp.Flight_Ticket_Bookinggg.helper.Calculator;
import org.jsp.Flight_Ticket_Bookinggg.repository.CustomerRepository;
import org.jsp.Flight_Ticket_Bookinggg.repository.Flight_Repository;
import org.jsp.Flight_Ticket_Bookinggg.repository.RouteRepository;
import org.jsp.Flight_Ticket_Bookinggg.repository.StationRepository;
import org.jsp.Flight_Ticket_Bookinggg.repository.TripOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
 

import jakarta.servlet.http.HttpSession;

@Service    //10th
public class CommonService 
{
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	AgencyDao agencyDao;//143
	
	@Autowired
	StationRepository stationRepository;
	
	@Autowired
	RouteRepository routeRepository;//505
	
	@Autowired
	Calculator calculator;//513
	
	@Autowired
	TripOrderRepository orderRepository;//531
	
	@Autowired
	CustomerRepository customerRepository;//533
	
	@Autowired
	Flight_Repository flight_Repository;
	
	public String signup(String role) //11
	{
		
		if(role.equals("agency"))
		return "redirect:/agency/signup";//14th---directly it will move for Airlines Controller Class
			
		else
	        return "redirect:/customer/signup";//382
		
		
	}

	public String login(String emph, String password, HttpSession session)  //131
	{
		 
		Agency agency=null;//132
		Customer customer=null;//133--463
		
		try
		{
		        long mobile    = Long.parseLong(emph);       //134
	              customer        = customerDao.findByMobile(mobile);//137---142--464
	               agency     =  agencyDao.findByMobile(mobile);//144---146
	        
		}
		catch (NumberFormatException e)   //135
	{
			String email=emph;//147
			  customer=customerDao.findByEmail(email);//148---152--465
			agency = agencyDao.findByEmail(email);//153
			 
		}
		if(customer==null && agency==null)//155
		{
			session.setAttribute("failMessage", "Invalid Email Or Phone");//156---467
			return "redirect:/login"; //157--468
		}
		else    //158
	{
		if (customer==null) //159
		{
			String decryptedpass	=AES.decrypt(agency.getPassword(), "123"); //extra-----just to take whether password is correct after Decrypting or not
			System.out.println("decryptedpass  ="   +decryptedpass);
			if(AES.decrypt(agency.getPassword(), "123").equals(password))//159
			{
				session.setAttribute("agency", agency);//161
				session.setAttribute("successMessage", "Login Success");//162
				return "redirect:/";//163
				
			}
			else
			{
				session.setAttribute("failMessage",  "Invalid Password Sachin");//164
				return "redirect:/login";//165
		}
	}
	    //166------>customer login logic to be continued---in future but now only iam writing
		
			else
			{
				//if customer!=null definately it will come inside the else block there i need to write a logic for customer login
				if(AES.decrypt(customer.getPassword(), "123").equals(password))//166--469
				{
					session.setAttribute("customer", customer);//166--470
					session.setAttribute("successMessage", "Login Success");//167--471
					return "redirect:/";//168--478
				}
				
				else
				{
					session.setAttribute("failMessage", "Invalid Password");//169--479
					return "redirect:/login";//170--480
			}
		}
			
		}
		
		
		
		
	}

 
	public String searchFlights(String from, String to, LocalDate date, HttpSession session, ModelMap map) //486
	{
		if (!from.equalsIgnoreCase(to)) //487
		{
			if (date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now())) //488
			{
				String day = date.getDayOfWeek().toString().toLowerCase();//489
				List<Route> routes = new ArrayList<Route>();

				List<Station> fromStations = stationRepository.findByName(from);//490
				List<Station> toStations = stationRepository.findByName(to);//492

				for (Station from1 : fromStations) {
					for (Station to1 : toStations) {
						if (from1.getRoute().getId() == to1.getRoute().getId()) {
							Route route = from1.getRoute();
							if (route.getStations().indexOf(from1) < route.getStations().indexOf(to1)
									|| route.getStations().indexOf(to1) == 1)
								switch (day) {
								case "monday": {
									if (route.isMonday())
										routes.add(route);
									break;
								}
								case "tuesday": {
									if (route.isTuesday())
										routes.add(route);
									break;
								}
								case "wednesday": {
									if (route.isWednesday())
										routes.add(route);
									break;
								}
								case "thursday": {
									if (route.isThursday())
										routes.add(route);
									break;
								}
								case "friday": {
									if (route.isFriday())
										routes.add(route);
								}
								case "saturday": {
									if (route.isSaturday())
										routes.add(route);
									break;
								}
								case "sunday": {
									if (route.isSunday())
										routes.add(route);
									break;
								}

								default:
									throw new IllegalArgumentException("Unexpected value: " + day);//493--if i enter suuunndayyy like this this statement will get executed
								}
						}
					}
				}

				if (routes.isEmpty()) {
					session.setAttribute("failMessage", "No Flight is available in this date");
					return "redirect:/book-flight";//495 it wiil go near navbar.html 481 step
				} else {
					map.put("from", from);
					map.put("to", to);
					map.put("routes", routes);//495
					return "view-routes.html";//496
				}
			} else {
				session.setAttribute("failMessage", "Select Proper Date");
				return "redirect:/book-flight";//497 it wiil go near navbar.html 481 step
			}

		} else {
			session.setAttribute("failMessage", "Enter Proper Destination");//if i enter from to same same
			return "redirect:/book-flight";//498 it wiil go near navbar.html 481 step
		}
	}

	
	public String bookTicket(String from, String to, int routeId, HttpSession session, ModelMap map, int seat) //503
	{
	Customer customer    = (Customer) session.getAttribute("customer");//504---continuation of 166th step
	if(customer==null)//505
	{
		session.setAttribute("failMessage", "First Login to Book");//506
		return "redirect:/login";//it will go near--125th step--507
	}
	else
	{
		Route route= routeRepository.findById(routeId).orElseThrow();//508
		Flight flight=route.getFlight();//509
		if(flight.getSeat()>=seat)//510
		{
			double price=calculator.calculatePrice(from,to,route)*seat;//514--517
			RazorpayClient razorpay=null;  //518
			
		  try {
			razorpay	=new RazorpayClient("rzp_test_f4vcAPoh0RDZfi", "jjblWSJ6F7NJuPUOtNmDjg4i");//519here we need to enter razorpay api key and secret key from razorpaydashboards
		    JSONObject orderRequest    =new JSONObject();//520
		    orderRequest.put("amount", price*100);//521
		    orderRequest.put("currency", "INR");//522
		  Order order     =razorpay.orders.create(orderRequest);//523 here iam passing orderrequest in the form of jsonaoabject
		       TripOrder tripOrder    =new TripOrder();//526
		       tripOrder.setFrom(from);
		       tripOrder.setTo(to);
		       tripOrder.setAmount(price);
		       tripOrder.setBookingDate(LocalDate.now());
		       tripOrder.setArrivalTime(calculator.timeCalculator(to,route));//527
		       tripOrder.setDepartureTime(calculator.timeCalculator(from, route));
		       tripOrder.setOrderId(order.get("id"));
		       tripOrder.setSeat(seat);
		       tripOrder.setFlightId(flight.getId());//529
		       orderRepository.save(tripOrder);//530
		       
		       customer.getTripOrders().add(tripOrder);//531
		       customerDao.save(customer);//532
		       map.put("tripOrder", tripOrder);
		       map.put("key", "rzp_test_f4vcAPoh0RDZfi");
		       map.put("customer", customer);
		       
		       session.setAttribute("customer", customerRepository.findById(customer.getId()).orElseThrow());//532
		       session.setAttribute("successMessage", "Check Details and Do payment");//533
		       return "razor-pay.html";//534
		       
		  } 
		   catch (RazorpayException e) 
		   {
			 
			e.printStackTrace();
			session.setAttribute("failMessage", "Payment Failed");//534if any exception occurs it will come inside catchBlock at that time it will come inside catch Block 
			return "redirect:/";//535
		}  
		 
		}
		
		else
		{
			session.setAttribute("failMessage", "Sorry! Tickets are not Available");//536
			return "redirect:/";//537
			
			
		}
	}
	
	 
	 
	}

	public String confirmOrder(int id, String razorpay_payment_id, HttpSession session) //542
	{
	 
		Customer customer=(Customer) session.getAttribute("customer");//543 ---continuation of 165 th step
		
		if(customer==null)//544
		{
			session.setAttribute("failMessage", "First Login to Book");//545
			return "redirect:/login";//546
		}
		else
		{
		TripOrder order	   =orderRepository.findById(id).orElseThrow();//547
		order.setPaymentId(razorpay_payment_id);//548
		orderRepository.save(order);//549
		
	 Flight flight	    =flight_Repository.findById(order.getFlightId()).orElseThrow();
	 flight.setSeat(flight.getSeat()-order.getSeat());
	 flight_Repository.save(flight);
	 session.setAttribute("successMessage", "Ticket Booked SuccessFully");
	 return "redirect:/";//550
		
		}
		
		 
	}
//	public String confirmOrder(int id, String razorpay_payment_id, HttpSession session) {
//		Customer customer = (Customer) session.getAttribute("customer");
//		if (customer == null) {
//			session.setAttribute("failMessage", "First Login to Book");
//			return "redirect:/login";
//		} else {
//			TripOrder order = orderRepository.findById(id).orElseThrow();
//			order.setPaymentId(razorpay_payment_id);
//			orderRepository.save(order);
//
//			Bus bus = busRepository.findById(order.getBusId()).orElseThrow();
//			bus.setSeat(bus.getSeat() - order.getSeat());
//
//			busRepository.save(bus);
//
//			session.setAttribute("successMessage", "Ticket booked Successfully");
//			return "redirect:/";
//		}
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 
	
}
