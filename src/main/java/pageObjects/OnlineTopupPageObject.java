package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;



public class OnlineTopupPageObject extends AbstractPage {


	public OnlineTopupPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	

	public String getStringNumberAfterCaculate(String beforeAcc,String cardMoney, String feeMoney) {
		
		long before = Long.parseLong(beforeAcc.replaceAll("\\D+",""));
		long card = Long.parseLong(cardMoney.replaceAll("\\D+",""));
		long fee = Long.parseLong(feeMoney.replaceAll("\\D+",""));
		
		long after = before - card - fee;
		String afterCal = after+"";
		
		return addCommasToLong(afterCal);
	}
	
	public static String addCommasToLong(String number) {
		String m = "";
		try {
			long amount = Long.parseLong(number);
			m = String.format("%,d", amount);
		} catch (Exception e) {
			
		}
		
		return m;
	}
}
