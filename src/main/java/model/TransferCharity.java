package model;

public class TransferCharity {
	public String sourceAccount;
	public String organization;
	public String money;
	public String name;
	public String address;
	public String status;
	public String authenticationMethod;
	
	public TransferCharity(String sourceAccount, String organization, String money, String name, String address, String status, String authenticationMethod) {
		this.sourceAccount = sourceAccount;
		this.organization = organization;
		this.money = money;
		this.name = name;
		this.address = address;
		this.status = status;
		this.authenticationMethod = authenticationMethod;
	}
}