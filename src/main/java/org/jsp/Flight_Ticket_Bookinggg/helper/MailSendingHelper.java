package org.jsp.Flight_Ticket_Bookinggg.helper;

import org.jsp.Flight_Ticket_Bookinggg.dto.Agency;
import org.jsp.Flight_Ticket_Bookinggg.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

 

import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;

@Component
public class MailSendingHelper //87
{

	 
	
	@Autowired
	JavaMailSender mailSender;//91

	@Autowired
	TemplateEngine engine; //100

	public boolean sendEmail(Agency agency) //90
	{
		MimeMessage message = mailSender.createMimeMessage();//92--MIME (Multipurpose Internet Mail Extensions-->it is used to set from to subject and body to generate a new mail
		MimeMessageHelper helper = new MimeMessageHelper(message);//93--mime message helper provides convinient methods to  setting the subject,adding therecepients,adding attachments e,t,c

		try {

			helper.setFrom("saishkulkarni7@gmail.com", "Airlines.com");//94
			helper.setTo(agency.getEmail());//95
			helper.setSubject("Account creation OTP");//96

			Context context = new Context();//97
			context.setVariable("obj", agency);//98

			String template = null;//99
			try {
				template = engine.process("email-template.html", context);//101
			} catch (Exception e) {
				e.printStackTrace();
			}
			helper.setText(template, true);//102

			mailSender.send(message);//103
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean sendEmail(Customer customer) //419
	{
//		MimeMessage message = mailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message);
//		
//		
//		//very important 
//		//MimeMessage represents a MIME (Multipurpose Internet Mail Extensions) email message. MIME is used to define the format of email messages that can contain attachments like images, files, etc
//      //MimeMessageHelper--->It helps in setting attributes such as the subject, recipients, body, and attachments.
//		try {
//
//			helper.setFrom("saishkulkarni7@gmail.com", "Airlines.com");
//			helper.setTo(customer.getEmail());
//			helper.setSubject("Account creation OTP");
//
//			Context context = new Context();
//			context.setVariable("obj", customer);
//
//			String template = null;
//			try {
//				template = engine.process("email-template.html", context);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			helper.setText(template, true);
//
//			mailSender.send(message);
//			return true;//420
//		} catch (Exception e) {
//			return false;
//		}
//	}
	
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		
		//very important 
		//MimeMessage represents a MIME (Multipurpose Internet Mail Extensions) email message. MIME is used to define the format of email messages that can contain attachments like images, files, etc
      //MimeMessageHelper--->It helps in setting attributes such as the subject, recipients, body, and attachments.
		try {

			helper.setFrom("saishkulkarni7@gmail.com", "Airlines.com");
			helper.setTo(customer.getEmail());
			helper.setSubject("Account creation OTP");

			Context context = new Context();
			context.setVariable("obj", customer);

			String template = null;
			try {
				template = engine.process("email-template.html", context);
			} catch (Exception e) {
				e.printStackTrace();
			}
			helper.setText(template, true);

			mailSender.send(message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	 
}
