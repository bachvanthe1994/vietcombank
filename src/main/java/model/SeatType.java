package model;

public class SeatType {
	public String name;
	public String price;
	public String number;
	
	public SeatType(String name, String price, String number) {
		this.name = name;
		this.price = price;
		this.number = number;
	}
	
	public enum TypeButton {
		  INCREASE, DECREASE
	}
	
}
