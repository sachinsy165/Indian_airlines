package org.jsp.Flight_Ticket_Bookinggg.dao;

import org.jsp.Flight_Ticket_Bookinggg.dto.Customer;
import org.jsp.Flight_Ticket_Bookinggg.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import jakarta.validation.Valid;

@Component   //31st
//@Repository   //38
public class CustomerDao 
{

	@Autowired
	CustomerRepository cusRepository;
	
	public boolean checkEmail(String email)   //33
	{
		      boolean response  =  cusRepository.existsByEmail(email);      //39
		return response;
	}

	public boolean checkMobile(long mobile)  //46
	{
		return cusRepository.existsByMobile(mobile);//47
		 
	}

	public Customer findByMobile(long mobile)  //138
	{
		Customer customer= cusRepository.findByMobile(mobile);//140
		return customer;//141
		
	}

	public Customer findByEmail(String email)  //149
	{
	  Customer customer	=cusRepository.findByEmail(email) ;//151
	  return customer;//152
		
	}
	
 
	
	

	public void deleteIfExists(@Valid Customer customer) //407
	{
		if(findByMobile(customer.getMobile())!=null) 
		{
			delete(findByMobile(customer.getMobile()));//408
		}
		if(findByEmail(customer.getEmail())!=null)//411
		{
			delete(findByEmail(customer.getEmail()));//412
		}
		
	}
	
	
	public  void delete(Customer customer) //409--413
	{
		cusRepository.delete(customer);//410--414
		 
	}

	public Customer save(@Valid Customer customer) //422
	{
	Customer customer2	= cusRepository.save(customer);  //423
	return customer2;//424
		
	}

	public Customer findById(int id) //440
	{
	  Customer customer	= cusRepository.findById(id).orElseThrow();//441
		return customer;//442
	}
	
	
	
	
	
	
	

}
