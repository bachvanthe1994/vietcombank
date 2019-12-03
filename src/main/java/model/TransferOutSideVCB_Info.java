package model;

public class TransferOutSideVCB_Info {
	public String sourceAccount;
	public String destinationAccount;
	public String name;
	public String destinationBank;
	public String money;
	public String transactionFee;
	public String note;
	
	public TransferOutSideVCB_Info(String sourceAccount, String destinationAccount, String name, String destinationBank, String money, String transactionFee, String note) {
		this.sourceAccount = sourceAccount;
		this.destinationAccount = destinationAccount;
		this.name = name;
		this.destinationBank = destinationBank;
		this.money = money;
		this.transactionFee = transactionFee;
		this.note = note;
		
	}
	
}
