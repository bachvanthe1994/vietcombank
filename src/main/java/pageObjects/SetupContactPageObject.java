package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.SetupContactPageUIs;

public class SetupContactPageObject extends AbstractPage {

	public SetupContactPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public void deleteContactReceiver() {
		List<MobileElement> listContact = new ArrayList<MobileElement>();
		listContact = driver.findElements(By.xpath(SetupContactPageUIs.LIST_CONTACT));
		int countList = listContact.size();
		while (countList > 0) {

			MobileElement contact = driver.findElements(By.xpath(SetupContactPageUIs.LIST_CONTACT)).get(0);
			contact.click();
			clickToDynamicText(driver, "Xóa");

			clickToDynamicButton(driver, "Đồng ý");

			clickToDynamicButton(driver, "Đóng");
			countList--;

		}
	}

	public void addContactReceiver(String type, String nameBank, String name, String account) {

		clickToDynamicText(driver, "Thêm mới");

		clickToDynamicText(driver, "Chuyển tiền trong Vietcombank");

		clickToDynamicButtonLinkOrLinkText(driver, type);

		clickToDynamicButtonLinkOrLinkText(driver, nameBank);

		MobileElement name1 = driver.findElement(By.xpath(SetupContactPageUIs.INPUT_NAME));
		name1.sendKeys(name);

		MobileElement account1 = driver.findElement(By.xpath(SetupContactPageUIs.INPUT_ACCOUNT));
		account1.sendKeys(account);

		clickToDynamicButton(driver, "Hoàn thành");

		clickToDynamicButton(driver, "Đóng");
	}

	public void clickToDynamicButton(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		waitForElementVisible(driver, SetupContactPageUIs.CLICK_BUTTON, dynamicTextValue);
		clickToElement(driver, SetupContactPageUIs.CLICK_BUTTON, dynamicTextValue);
	}

	public void clickToDynamicText(AppiumDriver<MobileElement> driver2, String dynamicTextValue) {
		waitForElementVisible(driver2, SetupContactPageUIs.CLICK_TEXT, dynamicTextValue);
		clickToElement(driver2, SetupContactPageUIs.CLICK_TEXT, dynamicTextValue);
	}
}
