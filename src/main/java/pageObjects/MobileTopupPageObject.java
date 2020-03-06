package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;



public class MobileTopupPageObject extends AbstractPage {


	public MobileTopupPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	


}
