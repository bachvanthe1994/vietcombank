package pageObjects;

import java.text.SimpleDateFormat;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.TransferMoneyOutSideVCBPageUIs;

public class TransferMoneyOutSideVCBPageObject extends AbstractPage{
	public TransferMoneyOutSideVCBPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}
	
	private AndroidDriver<AndroidElement> driver;
	
	
	
	public long canculateAvailableBalances(long surPlus, long money, long transactionFree) {
		return surPlus - money - transactionFree;
	}
	
	public double canculateAvailableBalancesCurrentcy(double surPlus, double money, double transactionFree) {
		return surPlus - money - transactionFree;
	}
	
	public double convertAvailableBalanceCurrentcyToDouble(String money) {
		double result = 0;
		try {
			result = Double.parseDouble(money.replaceAll("[^\\.0123456789]",""));
		}catch (Exception e) {
			
		}
		return result;
	}
	
	public long convertAvailableBalanceCurrentcyToLong(String money) {
		long result = 0;
		try {
			result = Long.parseLong(money.replaceAll("[^\\.0123456789]",""));
		}catch (Exception e) {
			
		}
		return result;
	}
	
	public String convertEURO_USDToVNeseMoney(String money, String currentcy) {
		String result = "";
		try {
			result =  String.format("%,d", Math.round(Double.parseDouble(money) * Double.parseDouble(currentcy))) + " VND";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	public String convertDateTimeIgnoreSecond(String stringDate) {
		String result = "";
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    try {
	    	result = formatter2.format(formatter1.parse(stringDate));
	    }
	    catch (Exception e) {
			
		}
		return result;
		
	}
	
	public String convertTransferTimeToReportDateTime(String stringDate) {
		String result = "";
	    try {
	    	result = stringDate.split(" ")[3] + " " + stringDate.split(" ")[0];
	    }
	    catch (Exception e) {
			
		}
		return result;
		
	}
	
}
