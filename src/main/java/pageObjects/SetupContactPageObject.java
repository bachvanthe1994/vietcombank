package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.SetupContactPageUIs;

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
			AndroidElement delete = driver.findElement(By.xpath(SetupContactPageUIs.DELETE));
			delete.click();

			AndroidElement accept = driver.findElement(By.xpath(SetupContactPageUIs.ACCEPT));
			accept.click();

			AndroidElement close = driver.findElement(By.xpath(SetupContactPageUIs.CLOSE));
			close.click();
			countList--;

		}
	}

	public void addContactReceiver(String type,String nameBank, String name, String account) {

		AndroidElement add = driver.findElement(By.xpath(SetupContactPageUIs.ADD));
		add.click();

		AndroidElement type1 = driver.findElement(By.xpath(SetupContactPageUIs.TYPE_TRANFER));
		type1.click();

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

}
