package pageObjects;

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
	
}
