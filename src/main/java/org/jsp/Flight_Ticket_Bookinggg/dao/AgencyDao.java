package org.jsp.Flight_Ticket_Bookinggg.dao;

import org.jsp.Flight_Ticket_Bookinggg.dto.Agency;
import org.jsp.Flight_Ticket_Bookinggg.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import jakarta.validation.Valid;

@Component // 28
//@Repository //34
public class AgencyDao // 27
{
	@Autowired
	AgencyRepository agencyRepository;// 35

	public boolean checkEmail(String email) // 32nd
	{
		boolean response = agencyRepository.existsByEmailAndStatusTrue(email);// 36
		return response; // 37
	}

	public boolean checkMobile(long mobile) // 42
	{
		boolean response = agencyRepository.existsByMobileAndStatusTrue(mobile);// 44
		return response; // 45
	}

	public Agency findByMobile(long mobile) // 56----- 145
	{
		Agency agency = agencyRepository.findByMobile(mobile);// 57
		return agency;// 58
	}

	public Agency findByEmail(String email) {
		Agency agency = agencyRepository.findByEmail(email);// 65---154
		return agency;// 66
	}

	public void deleteIfExists(@Valid Agency agency) // 54
	{
		if (findByMobile(agency.getMobile()) != null) {
			Agency agency1 = findByMobile(agency.getMobile());// 59
			delete(agency1);// 61
		}

		if (findByEmail(agency.getEmail()) != null) // 63
		{

			delete(findByEmail(agency.getEmail()));// 67
		}

	}

	public void delete(Agency agency) // 60
	{
		agencyRepository.delete(agency);// 62
	}

	public void save(@Valid Agency agency) //105
	{
		 agencyRepository.save(agency); //106
		
	}

	public Agency findById(int id)  //118
	{
	Agency agency	= agencyRepository.findById(id).orElseThrow();//119
	return agency;//120
		
	}

}
