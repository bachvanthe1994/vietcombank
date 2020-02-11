package model;

import java.util.Date;

public class HotelBookingInfo {
	public String payCode;
	public String hotelName;
	public String hotelAddress;
	public String createDate;
	public String checkinDate;
	public String price;
	public String status;
	
	public HotelBookingInfo(String payCode, String hotelName, String hotelAddress, String createDate, String checkinDate, String price, String status) {
		this.payCode = payCode;
		this.hotelName = hotelName;
		this.hotelAddress = hotelAddress;
		this.createDate = createDate;
		this.checkinDate = checkinDate;
		this.price = price;
		this.status = status;
	}
	
}
