package model;

public class ServiceLimitInfo {
	
	public String timesDay;
	public String minTran;
	public String maxTran;
	public String totalLimit;
	
	public ServiceLimitInfo(String timesDay,String minTran,String maxTran,String totalLimit) {
		this.timesDay = timesDay;
		this.minTran = minTran;
		this.maxTran = maxTran;
		this.totalLimit = totalLimit;
	}
	
}