package org.jsp.Flight_Ticket_Bookinggg.helper;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Service    //----to over come the error of no such bean called  SessionMessageRemover
public class SessionMessageRemover 
{
	public void removeSessionMessage() 
	{
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getSession();
		session.removeAttribute("successMessage");
		session.removeAttribute("failMessage");
	}
}
