package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import commons.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;

public class InternetADSLPageObject extends AbstractPage {

	public static String codeADSL = "";

	public InternetADSLPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public void inputCustomerCode(List<String> codeCustomer) {
		boolean check = false;
		for (String code : codeCustomer) {
			inputIntoEditTextByID(driver, code, "com.VCB:id/code");
			clickToDynamicButton(driver, "Tiếp tục");
			
			overRideTimeOut(driver, 5);
			String locator = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Xác nhận thông tin");
			List<MobileElement> elementsOne = driver.findElements(By.xpath(locator));

			if (elementsOne.size() > 0) {
				check = true;
				codeADSL = code;
				break;
			} 
			
			else {
				clickToDynamicButton(driver, "Đóng");
				continue;
			}

		}
		overRideTimeOut(driver, Constants.LONG_TIME);
		if (!check) {
			throw new RuntimeException("Khong co hoa don nao de thanh toan");
		}

	}
	
	//Click image back, trong trường hợp auto click 1 lần không được
	public void clickImageBack(String dymanicText){
		boolean status = false;
		String locator = String.format(DynamicPageUIs.DYNAMIC_BACK_ICON, dymanicText);
		status = waitForElementVisible(driver, locator);
		if (status == true) {
			clickToElement(driver, locator);
			List<MobileElement> element = driver.findElements(By.xpath(locator));
			if(element.size() > 0) {
				clickToElement(driver, locator);
			}
		}
	}
	
}
	
	
	
	
	
	
	
