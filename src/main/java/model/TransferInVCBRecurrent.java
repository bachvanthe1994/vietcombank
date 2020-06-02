package model;

public class TransferInVCBRecurrent {
	public String sourceAccount;
	public String destinationAccount;
	public String frequencyNumber;
	public String frequencyCategory;
	public String startDate;
	public String endDate;
	public String money;
	public String fee;
	public String note;
	public String authenticationMethod;
	
	public TransferInVCBRecurrent(String sourceAccount, String destinationAccount, String frequencyNumber, String frequencyCategory, String startDate, String endDate, String money, String fee, String note, String authenticationMethod) {
		this.sourceAccount = sourceAccount;
		this.destinationAccount = destinationAccount;
		this.frequencyNumber = frequencyNumber;
		this.frequencyCategory = frequencyCategory;
		this.startDate = startDate;
		this.endDate = endDate;
		this.money = money;
		this.fee = fee;
		this.note = note;
		this.authenticationMethod = authenticationMethod;
	}
	


	public enum Currency {
		  VND, CURRENCY
	}
	
}
