package model;

public class Train_Ticket_Object {
	public String timeStart;
	public String timeEnd;
	public String totalTimeRun;
	public String codeTrain;
	
	
	public Train_Ticket_Object(String timeStart, String totalTimeRun, String codeTrain, String timeEnd) {
		this.timeStart = timeStart;
		this.totalTimeRun = totalTimeRun;
		this.codeTrain = codeTrain;
		this.timeEnd = timeEnd;
	}
	
}
