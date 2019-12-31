package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TransactionReportPageObject extends AbstractPage {

	public TransactionReportPageObject(AppiumDriver<MobileElement> driver2) {
		driver = driver2;
	}

	AppiumDriver<MobileElement> driver;
}
