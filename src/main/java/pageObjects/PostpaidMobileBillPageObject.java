package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.SkipException;

import commons.AbstractPage;
import commons.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;



public class PostpaidMobileBillPageObject extends AbstractPage {


	public PostpaidMobileBillPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	
	public String inputPhoneNumberPostPaidMobile(List<String> listPhone) {
		
		String phoneNumber="";
		boolean check = false;
		for (String phone : listPhone) {
			inputToDynamicEditviewByLinearlayoutId(driver, phone, "com.VCB:id/llCode");
			clickToDynamicButton(driver, "Tiếp tục");
			
			overRideTimeOut(driver, 5);
			String locator = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Xác nhận thông tin");
			List<MobileElement> elementsOne = driver.findElements(By.xpath(locator));
			
			phoneNumber = phone;
			if(elementsOne.size() > 0) {
				check = true;
				break;
			}
			else{
				clickToDynamicButton(driver, "Đóng");
				continue;
			}
			
		}
		overRideTimeOut(driver, Constants.LONG_TIME);
		if (!check) {
			throw new SkipException("Khong co hoa don nao de thanh toan");
		}
		return phoneNumber;
	}
	
public String inputPhoneNumberPostPaidMobiletoUpperCase(List<String> listPhone) {
		
		String phoneNumber="";
		boolean check = false;
		for (String phone : listPhone) {
			inputToDynamicEditviewByLinearlayoutId(driver, phone.toUpperCase(), "com.VCB:id/llCode");
			clickToDynamicButton(driver, "Tiếp tục");
			
			overRideTimeOut(driver, 5);
			String locator = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Xác nhận thông tin");
			List<MobileElement> elementsOne = driver.findElements(By.xpath(locator));
			
			phoneNumber = phone;
			if(elementsOne.size() > 0) {
				check = true;
				break;
			}
			else{
				clickToDynamicButton(driver, "Đóng");
				continue;
			}
			
		}
		overRideTimeOut(driver, Constants.LONG_TIME);
		if (!check) {
			throw new SkipException("Khong co hoa don nao de thanh toan");
		}
		return phoneNumber;
	}

}
