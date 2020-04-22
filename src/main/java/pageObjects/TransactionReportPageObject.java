package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;

public class TransactionReportPageObject extends AbstractPage {

	public TransactionReportPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public String getDynamicTextOld(String... dynamicText) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_NUMBER_CUSTOMER, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_NUMBER_CUSTOMER, dynamicText);
		}
		return text;
	}
}
