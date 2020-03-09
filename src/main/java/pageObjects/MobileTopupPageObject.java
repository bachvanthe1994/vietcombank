package pageObjects;

import java.util.ArrayList;
import java.util.List;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;



public class MobileTopupPageObject extends AbstractPage {


	public MobileTopupPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	
	public String getStringNumber(long expVal,String... dynamicValue){
		
		long exp = 0;
		String locator = String.format(DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, (Object[]) dynamicValue);
		ArrayList<Long> allStringNumber = new ArrayList<Long>();
		List<MobileElement> elements = driver.findElementsByXPath(locator);
		for(MobileElement element : elements) {
			allStringNumber.add(Long.parseLong(element.getText().replaceAll("\\D+","")));
		}
		for(int i=0;i<allStringNumber.size();i++) {
			if(expVal > allStringNumber.get(i)) {
				exp = allStringNumber.get(i);
			}
		}
		String data = Long.toString(exp); 
		data = addCommasToLong(data);
		return data;		
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
	
	public String getStringNumberAfterCaculate(String beforeAcc,String cardMoney, String feeMoney) {
		
		long before = Long.parseLong(beforeAcc.replaceAll("\\D+",""));
		long card = Long.parseLong(cardMoney.replaceAll("\\D+",""));
		long fee = Long.parseLong(feeMoney.replaceAll("\\D+",""));
		
		long after = before - card - fee;
		String afterCal = after+"";
		
		return addCommasToLong(afterCal);
	}

}
