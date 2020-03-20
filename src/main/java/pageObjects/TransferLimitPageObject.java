package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;



public class TransferLimitPageObject extends AbstractPage {


	public TransferLimitPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	

}
