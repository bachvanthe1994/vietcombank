package model;

public class ServiceLimitInfo02 {
	
	public String method;
	public String timesDay;
	public String minTran;
	public String maxTran;
	public String totalLimit;
	
	public ServiceLimitInfo02(String method,String timesDay,String minTran,String maxTran,String totalLimit) {
		this.method = method;
		this.timesDay = timesDay;
		this.minTran = minTran;
		this.maxTran = maxTran;
		this.totalLimit = totalLimit;
	}
	
}