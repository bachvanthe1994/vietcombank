package vnpay.vietcombank.transfer_money_in_vcb;

import java.text.DecimalFormat;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

public class test extends Base {

	public static void main(String[] args) {
		String text ="1 USD ~ 30,000 VND";
		String [] exchangeRateUSD =text.split(" ~ ");
		System.out.println(exchangeRateUSD[1]);
		long rate = convertMoneyToLong(exchangeRateUSD[1], "VND");
		System.out.println(rate);
	    
	}

	
	public static long convertMoneyToLong(String money, String currency) {
		money = money.replaceAll(" " + currency, "");
		money = money.replaceAll(",", "");
		long m = Long.parseLong(money);
		return m;
	}
	
	
	
	
	
	
	

}