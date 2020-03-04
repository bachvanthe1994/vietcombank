package pageObjects.saving_online;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class SavingOnlinePageObject extends AbstractPage {

	public SavingOnlinePageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

}
