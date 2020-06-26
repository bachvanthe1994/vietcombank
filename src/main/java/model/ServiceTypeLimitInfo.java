package model;

public class ServiceTypeLimitInfo {
	
	public String methodOTP;
	public String currentcy;
	public String totalLimit;
	
	
	public  ServiceTypeLimitInfo(String methodOTP,String currentcy,String totalLimit) {
		this.methodOTP = methodOTP;
		this.currentcy = currentcy;
		this.totalLimit = totalLimit;
	}
	
}