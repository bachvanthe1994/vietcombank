package pageObjects.saving_online;

import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import commons.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.saving_online.SavingOnlineUIs;
import io.appium.java_client.TouchAction;

public class SavingOnlinePageObject extends AbstractPage {

	public SavingOnlinePageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;
	
	//Get thông tin được tạo trong chi tiết giao dich , tham số truyền vào là text phía bên tay trái
	public String getDynamicTextAvailableBalanceInSavingOnline(String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, SavingOnlineUIs.AVAILABLE_BALANCE_SAVING_ONLINE, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, SavingOnlineUIs.AVAILABLE_BALANCE_SAVING_ONLINE, dynamicTextValue);

		}
		return text;

	}
	
	//Click vào dropdown list tham số truyển vào là label của ô dropdown list đó
	public void clickToDynamicDropDownInSavingOnline(String dymanicText) {
		boolean status = false;
		String locator = String.format(SavingOnlineUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		scrollIDown(driver, locator);
		status = waitForElementVisible(driver, locator);
		if (status == true) {
			overRideTimeOut(driver, 2);
			clickToElement(driver, locator);
			String locatorCheck = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Đóng");
			List<MobileElement> element = driver.findElements(By.xpath(locatorCheck));
			if(element.size() == 0) {
				clickToElement(driver, locator);
			}
			overRideTimeOut(driver, Constants.LONG_TIME);
		}

	}
	
	//Click vao 1 button sử dụng  tham số là text
	public void clickToDynamicButton(String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}

	}
	
}
