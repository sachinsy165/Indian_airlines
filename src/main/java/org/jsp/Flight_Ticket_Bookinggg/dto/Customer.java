package org.jsp.Flight_Ticket_Bookinggg.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

 

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

 
@Data
@Component//385
@Entity
public class Customer   //49
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Size(min = 3, max = 30, message = "* Enter between 3 to 30 charecters")
	private String name;
	@NotEmpty(message = "* this is Required field")
	private String address;
	@DecimalMax(value = "9999999999", message = "* Enter Proper Mobile Number")
	@DecimalMin(value = "6000000000", message = "* Enter Proper Mobile Number")
	private long mobile;
	@NotEmpty(message = "* this is Required field")
	@Email(message = "* Enter Proper Email")
	private String email;
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "* Password should contain minimum 8 chareecter, inlcude one upper case, lowercase , number and special charecter")
	private String password;
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "* Password should contain minimum 8 chareecter, inlcude one upper case, lowercase , number and special charecter")
	private String cpassword;
	@Past(message = "* Enter proper Dob")
	@NotNull(message = "* this is Required field")
	private LocalDate dob;
	@NotEmpty(message = "* this is Required field")
	private String gender;
	private int otp;
	boolean status;//50
 
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	List<TripOrder> tripOrders = new ArrayList<TripOrder>();//525
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	List<TripOrder>tripOrders=new ArrayList<TripOrder>();//525

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<TripOrder> getTripOrders() {
		return tripOrders;
	}

	public void setTripOrders(List<TripOrder> tripOrders) {
		this.tripOrders = tripOrders;
	}

	 
	
}
