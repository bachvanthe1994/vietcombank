package pageObjects;

import java.text.SimpleDateFormat;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TransferMoneyOutSideVCBPageObject extends AbstractPage {
	public TransferMoneyOutSideVCBPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

}
