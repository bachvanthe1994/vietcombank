package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.SkipException;

import commons.AbstractPage;
import commons.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;



public class PayBillTelevisionPageObject extends AbstractPage {


	public PayBillTelevisionPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	

	public String inputCustomerId(List<String> listCustomer) {

		String customerID = "";
		boolean check = false;
		for (String customer : listCustomer) {
			inputToDynamicEditviewByLinearlayoutId(driver, customer, "com.VCB:id/llCode");
			clickToDynamicButton(driver, "Tiếp tục");

			overRideTimeOut(driver, 5);
			String locator = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Xác nhận thông tin");
			List<MobileElement> elementsOne = driver.findElements(By.xpath(locator));

			customerID = customer;
			if (elementsOne.size() > 0) {
				check = true;
				break;
			} else {
				clickToDynamicButton(driver, "Đóng");
				continue;
			}

		}
		overRideTimeOut(driver, Constants.LONG_TIME);
		if (!check) {
			throw new SkipException("Khong co hoa don nao de thanh toan");
		}
		return customerID;
	}
	
	public void clickImageBack(String dymanicID) {
		boolean status = false;
		String locator = String.format(DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dymanicID);
		status = waitForElementVisible(driver, locator);
		if (status == true) {
			clickToElement(driver, locator);
			List<MobileElement> element = driver.findElements(By.xpath(locator));
			if (element.size() > 0) {
				clickToElement(driver, locator);
			}
		}
	}
}
