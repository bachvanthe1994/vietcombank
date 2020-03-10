package pageObjects;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	
	
//	public boolean isMoneyLessThanExpextedDisplayed(String dynamicID,long expValue) {
//		
//		boolean isDisplayed = false;
//		String dynamicTextValue = getTextInconvertListStringToLong(expValue, dynamicID);
//		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
//		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
//		if (status == true) {
//			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
//		}
//		return isDisplayed;
//
//	}
//	
//	public void clickToDynamicButtonLinkOrLinkText(String dynamicID,long expValue) {
//		boolean status = false;
//		String dynamicTextValue = getTextInconvertListStringToLong(expValue, dynamicID);
//		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
//		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
//		if (status == true) {
//			clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
//
//		}
//	}
//	
//	public String getTextInconvertListStringToLong(long expValue,String dynamicID){
//		
//		String getText = "";
//		List<String> listText = getListOfSuggestedMoneyOrListText(driver, dynamicID);
//		ArrayList<Long> longList = new ArrayList<Long>(Arrays.asList(1L, 2L));
//		for(String text :listText) {
//			longList.add(convertMoneyToLong(text, "VND"));
//		}
//		for(int i=0;i<longList.size();i++) {
//			if(expValue > longList.indexOf(i)) {
//				getText = longList.indexOf(i)+" VND";
//			}
//		}
//		return getText;
//		
//	}
//	
//	public long convertMoneyToLong(String money, String currency) {
//		money = money.replaceAll(" " + currency, "");
//		money = money.replaceAll(",", "");
//		long m = Long.parseLong(money);
//		return m;
//	}
//	
//	public static String addCommasToLong(String number) {
//		String m = "";
//		try {
//			long amount = Long.parseLong(number);
//			m = String.format("%,d", amount);
//		} catch (Exception e) {
//			
//		}
//		
//		return m;
//	}

}
