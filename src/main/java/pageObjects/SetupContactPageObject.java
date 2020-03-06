package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.LockCardPageUIs;
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
			clickToDynamicButtonLinkOrLinkText(driver, "Xóa");

			clickToDynamicButton(driver, "Đồng ý");

			clickToDynamicButton(driver, "Đóng");
			countList--;

		}
	}

	public void addContactReceiver(String type, String nameBank, String name, String account) {
		clickToDynamicButtonLinkOrLinkText(driver, "Thêm mới");
		clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");

		clickToDynamicButtonLinkOrLinkText(driver, type);

		clickToDynamicButtonLinkOrLinkText(driver, nameBank);

		inputToDynamicInputBox(driver, name, "Tên gợi nhớ");
		inputToDynamicInputBox(driver, account, "Số tài khoản");

		clickToDynamicButton(driver, "Hoàn thành");

		clickToDynamicButton(driver, "Đóng");
	}
	
	public void clickToContactKeyMenu(String dynamicKey) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CONTACT_KEY_MENU, dynamicKey);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_CONTACT_KEY_MENU, dynamicKey);
	}
	
	public void clickToUpdateDeleteContactLabel(String dynamicValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LINEAERLAYOUT_BY_ID,dynamicValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_LINEAERLAYOUT_BY_ID,dynamicValue);
	}
	
	public void clickToClearInputLabel(String dynamicValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_IMAGEVIEW_BY_LINEARLAYOUT_ID,dynamicValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_IMAGEVIEW_BY_LINEARLAYOUT_ID, dynamicValue);
	}
	
	public void clickBackToHomePage() {
		waitForElementVisible(driver, LockCardPageUIs.BACK_BUTTON);
		clickToElement(driver, LockCardPageUIs.BACK_BUTTON);
	}

}
