package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.SetupContactPageUIs;
import vietcombankUI.TransferIdentityPageUIs;

public class SetupContactPageObject extends AbstractPage {

	public SetupContactPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AndroidDriver<AndroidElement> driver;

	public void deleteContactReceiver() {
		List<AndroidElement> listContact = new ArrayList<AndroidElement>();
		listContact = driver.findElements(By.xpath(SetupContactPageUIs.LIST_CONTACT));
		int countList = listContact.size();
		while (countList > 0) {

			AndroidElement contact = driver.findElements(By.xpath(SetupContactPageUIs.LIST_CONTACT)).get(0);
			contact.click();
			
			clickToDynamicButton(driver, "Xóa");

			AndroidElement accept = driver.findElement(By.xpath(SetupContactPageUIs.ACCEPT));
			accept.click();

			AndroidElement close = driver.findElement(By.xpath(SetupContactPageUIs.CLOSE));
			close.click();
			countList--;

		}
	}

	public void addContactReceiver(String type,String nameBank, String name, String account) {

		clickToDynamicButton(driver, "Thêm mới");

		clickToDynamicButton(driver, "Chuyển tiền trong Vietcombank");

		clickToDynamicButtonLinkOrLinkText(driver, type);

		clickToDynamicButtonLinkOrLinkText(driver, nameBank);


		AndroidElement name1 = driver.findElement(By.xpath(SetupContactPageUIs.INPUT_NAME));
		name1.sendKeys(name);

		AndroidElement account1 = driver.findElement(By.xpath(SetupContactPageUIs.INPUT_ACCOUNT));
		account1.sendKeys(account);

		AndroidElement finish = driver.findElement(By.xpath(SetupContactPageUIs.BUTTON_FINISH));
		finish.click();

		AndroidElement close = driver.findElement(By.xpath(SetupContactPageUIs.BUTTON_CLOSE));
		close.click();
	}



public void clickToDynamicButton(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	waitForElementVisible(driver, SetupContactPageUIs.CLICK_TEXT, dynamicTextValue);
	clickToElement(driver, SetupContactPageUIs.CLICK_TEXT, dynamicTextValue);
}

public void clickToDynamicText(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	waitForElementVisible(driver, TransferIdentityPageUIs.CLICK_TEXT, dynamicTextValue);
	clickToElement(driver, TransferIdentityPageUIs.CLICK_TEXT, dynamicTextValue);
}
}
