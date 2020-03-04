package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.TransferMoneyCharityPageUIs;

public class TransferMoneyCharityPageObject extends AbstractPage {
	public TransferMoneyCharityPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public long canculateAvailableBalances(long surPlus, long money) {
		return surPlus - money;

	}

	public String getTextCurrencyCharity() {

		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TransferMoneyCharityPageUIs.TEXT_VIEW_CURRENCY_CHARITY);
		if (status == true) {
			text = getTextElement(driver, TransferMoneyCharityPageUIs.TEXT_VIEW_CURRENCY_CHARITY);

		}
		return text;

	}

	public List<String> getListOrganizationCharity() {
		boolean status = false;
		List<String> listOrganizationCharity = null;
		status = waitForElementVisible(driver, TransferMoneyCharityPageUIs.TEXT_VIEW_LIST_ORGANIZATION);
		if (status) {
			List<MobileElement> listElement = driver.findElements(By.xpath(TransferMoneyCharityPageUIs.TEXT_VIEW_LIST_ORGANIZATION));
			listOrganizationCharity = new ArrayList<String>();

			for (MobileElement element : listElement) {
				listOrganizationCharity.add(element.getText());
			}
		}
		return listOrganizationCharity;

	}

	public void inputOTPInvalidBy_N_Times(int time) {
		for (int i = 0; i < time; i++) {
			inputToDynamicOtpOrPIN(driver, "213456", "Tiếp tục");
			clickToDynamicButton(driver, "Tiếp tục");
			if (i < time - 1) {
				clickToDynamicButton(driver, "Đóng");
			}
		}
	}

	public void inputPasswordInvalidBy_N_Times(int time) {
		for (int i = 0; i < time; i++) {
			inputToDynamicPopupPasswordInput(driver, "12345678", "Tiếp tục");
			clickToDynamicButton(driver, "Tiếp tục");
			if (i < time - 1) {
				clickToDynamicButton(driver, "Đóng");
			}
		}
	}

}
