
package org.jsp.Flight_Ticket_Bookinggg.servicee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.jsp.Flight_Ticket_Bookinggg.dao.AgencyDao;
import org.jsp.Flight_Ticket_Bookinggg.dao.CustomerDao;
import org.jsp.Flight_Ticket_Bookinggg.dto.Agency;
import org.jsp.Flight_Ticket_Bookinggg.dto.Flight;
import org.jsp.Flight_Ticket_Bookinggg.dto.Route;
import org.jsp.Flight_Ticket_Bookinggg.dto.Station;
import org.jsp.Flight_Ticket_Bookinggg.helper.AES;
import org.jsp.Flight_Ticket_Bookinggg.helper.MailSendingHelper;
import org.jsp.Flight_Ticket_Bookinggg.repository.Flight_Repository;
import org.jsp.Flight_Ticket_Bookinggg.repository.RouteRepository;
import org.jsp.Flight_Ticket_Bookinggg.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
 

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Service
public class AgencyService // 24th
{
	@Autowired
	AgencyDao agencyDao; // 29

	@Autowired
	CustomerDao customerDao; // 32

	@Autowired
	MailSendingHelper mailSendingHelper; // 88

	@Autowired // 237
	Flight_Repository flight_Repository;
	
	@Autowired
	RouteRepository routeRepository;//256
	
	@Autowired  //314
	StationRepository stationRepository;
	
 
	

	public String signup(@Valid Agency agency, BindingResult result, HttpSession session) // 25th
	{

		if (!agency.getPassword().equals(agency.getCpassword())) // 26
			result.rejectValue("cpassword", "error.cpassword", "* Password and Confirm Password Should be Matching");
		if (agencyDao.checkEmail(agency.getEmail()) || customerDao.checkEmail(agency.getEmail())) // 30
			result.rejectValue("email", "error.email", "* Email Should be Unique");
		if (agencyDao.checkMobile(agency.getMobile()) || customerDao.checkMobile(agency.getMobile())) // 41
			result.rejectValue("mobile", "error.mobile", "* Mobile Number Should be Unique"); // 48

		if (result.hasErrors())// 51
			return "agency-signup.html";// 52
		else {
			agencyDao.deleteIfExists(agency); // 53
			agency.setCpassword(AES.encrypt(agency.getCpassword(), "123")); // 68---since password will be in string
																			// format so automatically encrypt method
																			// return type will get changed into string
			agency.setPassword(AES.encrypt(agency.getPassword(), "123"));// 84
			agency.setOtp(new Random().nextInt(100000, 1000000));// 85
			System.out.println("Otp - " + agency.getOtp());// 86
			if (mailSendingHelper.sendEmail(agency))// 89---as soon as mail has been sent controller will come inside
													// the if block
			{
				agencyDao.save(agency);// 104
				session.setAttribute("successMessage", "Otp Sent Success");// 105
				return "redirect:/agency/send-otp/" + agency.getId() + "";// 106
			} else {
				session.setAttribute("failMessage", "Sorry Not able to send OTP"); // 105 a
				return "redirect:/agency/signup"; // 105 b
			}
		}
	}

	public String verifyOtp(int id, int otp, HttpSession session) // 116
	{
		Agency agency = agencyDao.findById(id); // 117

		if (agency.getOtp() == otp) // 118
		{
			agency.setStatus(true);// 121----since we have done update again we need to save agency over here or
									// you can update
			agencyDao.save(agency);// 122
			session.setAttribute("successMessage", "Otp Verified Success, You can Login Now");// 123
			return "redirect:/login";// 124

		} else {
			session.setAttribute("failMessage", "Invalid Otp Try Again");// 124 a
			return "redirect:/agency/send-otp/" + agency.getId() + "";// 125 b
		}

	}

	public String resendOtp(int id, HttpSession session) // 125 i
	{
		Agency agency = agencyDao.findById(id); // 125 j
		agency.setOtp(new Random().nextInt(100000, 1000000)); // 125 k
		System.out.println("Otp - " + agency.getOtp()); // 125 l
		if (mailSendingHelper.sendEmail(agency))// 125 m
		{
			agencyDao.save(agency); // 125 n
			session.setAttribute("successMessage", "Otp Re-Sent Success");// 125 o
			return "redirect:/agency/send-otp/" + agency.getId() + "";// 125 p----this is for verify otp
		} else {
			session.setAttribute("failMessage", "Failed to Send Otp");// 125 q
			return "redirect:/agency/send-otp/" + agency.getId() + ""; // 125 r----this is for again resending the otp
		}
	}

	public String addFlight(Flight flight, MultipartFile image, HttpSession session) // 182
	{
		Agency agency = (Agency) session.getAttribute("agency");// 183----continuation of 161 step
		if (agency == null) {
			session.setAttribute("failMessage", "Invalid Session");// 184
			return "redirect:/"; // 185
		} else {
			flight.setImageLink(addToCloudinary(image));// 185----192--here i have set the image link seperately after
														// converting into string but from front end all the flight data
														// is going to be stored inside the database with the help of
														// cascade--very very imp
			agency.getFlights().add(flight);// 193
			agencyDao.save(agency); // 194 after establishing @OneToMany with flight drop all the tables and again
									// restore the updated data with updated column that is flights.
			session.setAttribute("agency", agencyDao.findById(agency.getId()));// 195---in value pair section iam not
																				// suppose to mention agency i need to
																				// find agency freshly again after
																				// adding the flight column---like
																				// this-----agencyDao.findById(agency.getId()
			session.setAttribute("successMessage", "Flight Added Success");// 196
			return "redirect:/";// 197

		}

	}
	
	 
	

	public String addToCloudinary(MultipartFile image) // 186
	{
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "djkyoabl5", "api_key",
				"437797232682629", "api_secret", "eGy3AaCBV6aH7gLbUe67lw2RZrg", "secure", true));
		; // 187 Cloudinary provides an API that allows you to upload images or videos
			// from your Spring Boot application to its cloud storage

		Map resume = null;
		try {
			Map<String, Object> uploadOptions = new HashMap<String, Object>();// 188
			uploadOptions.put("folder", "Flight");// 189
			resume = cloudinary.uploader().upload(image.getBytes(), uploadOptions);// 190
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String) resume.get("url");// 191The method returns the URL of the uploaded image by retrieving it from
											// the resume map.
											// This URL can then be used to access the image hosted on Cloudinary.
	}

	public String addRoute(HttpSession session, ModelMap map) // 202
	{
		Agency agency = (Agency) session.getAttribute("agency"); // 203----continuation of 161 step

		if (agency == null) {
			session.setAttribute("failMessage", "Invalid Session");// 204
			return "redirect:/";// 205
		} else {
			List<Flight> flights = agency.getFlights();// 205

			if (flights.isEmpty()) {
				session.setAttribute("failMessage", "First_Add_A_Flight");// 206
				return "redirect:/";// 207
			} else {
				map.put("flights", flights);// 208
				return "add-route.html";// 209
			}

		}
	}

 

	public String addRoute(Route route, HttpSession session) // 232
	{
		Agency agency = (Agency) session.getAttribute("agency");// 233--continuation of 161 after login we are setting
																// the session and dumping the information of agency
																// inside SetAttribute method that information we are
																// fetching over here

		if (agency == null) {
			session.setAttribute("failMessage", "Invalid Session"); // 234---after login if we dont add bus or route for
																	// longer duration session will get Invalid at that
																	// time we will get this FailMessage
			return "redirect:/";// 235 again redirect to the home page
		} else {
			Flight flight = flight_Repository.findById(route.getFlight().getId()).orElse(null);// 236

			List<Station> stations = route.getStations();// 237
			for (Station station : stations) // 237--this data we are bringing from front end--add-route.html--from
												// station,mid station.to station
			{
				station.setRoute(route);// 238 inside route already stations information is there but inside each and
										// every station from station,midstation,end station we need to add the route
										// information by taking for each loop since it is @OnetoMany bidirectional
										// mapping

			}
			route.setFlight(flight); // 239---route information wll get stored inside data base auto matically cause
										// in //225 step we hace used cascade for route.
			route.setAgency(agency);// 240
			flight.getRoutes().add(route);// 241
			flight_Repository.save(flight);// 242since here we have done update again i need to save flight entity.
			session.setAttribute("successMessage", "Route Added Success");// 243
			return "redirect:/";// 244
		}
	}

	public String fetchRoute(HttpSession session, ModelMap map) //249
	{
		Agency agency    = (Agency) session.getAttribute("agency");//250
		
		if (agency == null) 
		{
			session.setAttribute("failMessage", "Invalid Session");//251
			return "redirect:/";//252
		}
		else
		{
			List<Flight>  flights =agency.getFlights();//252
			if (flights.isEmpty()) 
			{
				session.setAttribute("failMessage", "No Routes or Airways Added Yet");//2253
				return "redirect:/";//254
			}
			else
			{
				List<Integer> list = flights.stream().mapToInt(x -> x.getId()).boxed().collect(Collectors.toList());//255--->it will help us to pass the flight id like 1 2 3 4 5 in the list format
			List<Route> routes	=routeRepository.findByFlight_idIn(list);//256--258
			
			if (routes.isEmpty()) 
			{
				session.setAttribute("failMessage", "No Routes or Airways Added Yet");//259
				return "redirect:/";//260
			}
			else
			{
				map.put("routes", routes);//261
				return "fetch-routes.html";//262
			}
				
			}
			
		}
	}
	
 

	public String editRoute(int id, HttpSession session,ModelMap map) //268
	{
		Agency agency   = (Agency) session.getAttribute("agency");//--->continuation of 161 th step-269
		if(agency==null)
		{
			session.setAttribute("failMessage", "Invalid session");//270
			return "redirect:/";
		}
		else
		{
//			Optional<Route> optional  =routeRepository.findById(id);
//			Route route   =optional.get();---i can do like this also
			
//			  List<Flight> flights   =  agency.getFlights();        //271
			
			
			Route route   =routeRepository.findById(id).orElseThrow();//271---this is very important step
			Agency agency1  = route.getAgency();
			List<Flight>  flights       =agency1.getFlights();
			map.put("route", route);//272----it is required for <!--275-->th step   this is very important step
			map.put("flights", flights);//272---it is required for <!--276-->th step
			return "edit-route.html";//273
		}
	}
	
 
	
	

	public String editRoute(Route route, HttpSession session) //279
	{
		Agency agency   = (Agency) session.getAttribute("agency"); //280
		if(agency==null)
		{
			session.setAttribute("failMessage", "Invalid Session");
			return "redirect:/";
		}
		 
		 Route existingRoute   =routeRepository.findById(route.getId()).orElse(null);//282
		System.out.println(existingRoute.getId());
		
		if(existingRoute!=null)//283
		{
			//Flight flight  =route.getFlight();----in the first iteration if route is not added means i cant get flight soo it will throw null pointer exception
//			existingRoute.setFlight(flight);
		List<Station>stations	  =route.getStations();//this is front end data completely presented inside route reference variable it is consisting route+flightname+stations details--like from to mid and days and flight name--ex:indigo
		
	 for (Station station : stations) //284
	 {
		station.setRoute(route);//285
	}
		
	    existingRoute.setStations(stations);//286since from front end we wre trying to change from to and mid stations along with time and price and days so its better to update only stations details with route refference variable
	  existingRoute.setSunday(route.isSunday());
	  existingRoute.setMonday(route.isMonday());
	  existingRoute.setTuesday(route.isTuesday());
	  existingRoute.setWednesday(route.isWednesday());
	  existingRoute.setThursday(route.isThursday());
	  existingRoute.setFriday(route.isFriday());
	  existingRoute.setSaturday(route.isSaturday());
	    routeRepository.save(existingRoute);//287
	    session.setAttribute("successMessage", "Route edited successfully");//288
	    return "redirect:/";//289
		}
		
		else
		{
			 session.setAttribute("failMessage", "Route not found");//290
			   return "redirect:/";//291
		}
		
}
 
	
 
	public String deleteRoute(int id, HttpSession session) // 295
{

	Agency agency = (Agency) session.getAttribute("agency");// 296

	if (agency == null) {
		session.setAttribute("failMessage", "Invalid Session");// 297
		return "redirect:/";// 298
	}

	else
	{
	Route existing_route	  =routeRepository.findById(id).orElseThrow();//299
	
	  
	 Flight flight     =existing_route.getFlight();//300--i need to remove the details of route inside flight class also because in flight class route data is going to be automatically saved because of cascade soo i should remove the data from flight class also

	   Route   route=null;
	 List<Route>  total_no_of_routes_of_that_flight   =flight.getRoutes();//301
	     
	   
	     for (Route route1 : total_no_of_routes_of_that_flight) //302
	     {
			if(route1.getId()==id)//303
			{
				route=route1;//304
				break;
			}
		}
	List<Station>  existingstations  = existing_route.getStations();//304
	//delete this route from existing stations because we have established mapping between route and stations so i cant directly delete the routes i nedd to remove the route from stations also.
	 
	for (Station station : existingstations) 
	{
//		station.setRoute(route);
		station.setRoute(null);//304--set the station as null to vanish the data of route inside station also or to break the relation ship
		stationRepository.delete(station);//304 <-- Save the updated station to persist the null reference--to break the reletionship of route with stations
	}
	
	     
	  flight.getRoutes().remove(route);//305from multiple routes of that flight one current route has been deleted
	  flight_Repository.save(flight);//306 after removing the currecnt route from the particular flight i need to update the flight class here in spring boot for update also we will use save method
	  routeRepository.delete(route);//307 
	  session.setAttribute("agency", agencyDao.findById(agency.getId()));//308 after deleting the route i need to stay in same page or same session soo this line is essential for that
	 session.setAttribute("successMessage", "route Removed Successfully");//309
String redi	 = "redirect:/agency/manage-route";//310---it will  
return redi;//311--it will go back to its original position step 294 in agency controller
	}

}
 
	
	

	public String fetchFlights(HttpSession session, ModelMap map) //317
	{
		 
		Agency agency    =(Agency) session.getAttribute("agency");//318---continuation of 161st step
		
		if(agency==null)
		{
			session.setAttribute("failMessage", "Invalid Session");//319
			return"redirect:/";//320
		}
		else
		{
			List<Flight>  flights =agency.getFlights();//321 we can get the list of flights by using agency reference variable because agency class is having @OneToMany relationship with Flights
		if(flights.isEmpty())
		{
			session.setAttribute("failMessage", "No flights added Yet");//322
			return "redirect:/";//323
		}
		else
			
		{
			map.put("flights", flights);//324
			return "fetch-flights.html";//325
		}
		}
	}
	
 
	public String deleteFlight(int id, HttpSession session) //332
	{
	 
		Agency agency =(Agency) session.getAttribute("agency");//333
		
		if(agency==null)
		{
			session.setAttribute("failMessage", "Invalid Session");
			return "redirect:/";//334
		}
		else
		{
			Flight flight=null;//335
		List<Flight>existing_flights	  =agency.getFlights();//336
		
		for (Flight flight1 : existing_flights) //337
		{
			if(flight1.getId()==id)//338
			{
				flight=flight1;//339
				break;
			}
		}
		
	List<Route>	 routes_of_this_current_flight  =flight.getRoutes();//340--flight is having relationship with routes also soo one_many soo that i need to remove the information of this slight from every route.
		
	for (Route route : routes_of_this_current_flight) //340
	{
		route.setFlight(null);//340----break the relationship--between route and flight like this
		routeRepository.delete(route);//340----now from each and every route current flight relationship is going to be removed
	}
	
	
	//340-instead of writing above code i can write single line to remove the flight from each and every entity that is @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
//340	orphanRemoval = true will automatically delete the respective routes of this current flights in route entity	
		agency.getFlights().remove(flight);//340---since flight class is having the reletion ship with agency class i need to remove this current flight from agency class.to brek the relationship
		agencyDao.save(agency);
		flight_Repository.delete(flight);
		session.setAttribute("agency", agencyDao.findById(agency.getId()));
		session.setAttribute("successMessage", "Flight Removed Sucessfully");
		return "redirect:/agency/manage-flight";//341----it will go to agency controller--near 331th step
		}
		
	}
	
	
 
	
	
	

	public String editFlight(int id, HttpSession session, ModelMap map) //346
	{
	  Agency agency	   = (Agency) session.getAttribute("agency");//347
	  
	  if(agency==null)//348
	  {
		  session.setAttribute("failMessage", "Invalid Session");//349
		  return "redirect:/";//350
	  }
	  else
	  {
		Flight flight    = flight_Repository.findById(id).orElseThrow();//351
		map.put("flight", flight);//352
		return "edit-flight.html";//353
		  
	  }
	}
	
	
 
	
	
	
	

	public String editFlight(Flight flight, MultipartFile image, HttpSession session) //358
	{
	 Agency agency    =	 (Agency) session.getAttribute("agency");//359
	 
	 if(agency==null)
	 {
		 session.setAttribute("failMessage", "Invalid Session");//360
		 return "redirect:/";//361
	 }
	 else
	 {
		 try {
			if(image.getInputStream().available() !=0) //362 from the editflight.html if image is coming means definately it will be having value 1 for 1 image soo 1!=0 soo condition true soo it will comw inside if Block
			 {
				 flight.setImageLink(addToCloudinary(image));//363
			 }
			else
			{
			    String existingImageLink          =	flight_Repository.findById(flight.getId()).orElseThrow().getImageLink();//364
				   flight.setImageLink(existingImageLink); //365 if iam not editing image in edit.html means again save the same  existingImageLink                
			}
		} 
		 catch (IOException e) 
		 {
		 
			e.printStackTrace();//366
		}
		 flight_Repository.save(flight);//367 since iam updating only flight information just iam saving flight reference variable only here iam not touching routes or stations or agency
	 session.setAttribute("agency", agencyDao.findById(agency.getId()));//368
	 session.setAttribute("successMessage", "Flight Details Updated successfully");//369
	 return "redirect:/agency/manage-flight";//370
	 
	 
	 
	 }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
 

	
	
	}

 


