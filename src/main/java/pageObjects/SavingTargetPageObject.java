package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;



public class SavingTargetPageObject extends AbstractPage {


	public SavingTargetPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	

}
