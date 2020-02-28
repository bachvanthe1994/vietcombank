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

}
