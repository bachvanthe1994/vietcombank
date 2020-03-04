package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class LuckyGiftPageObject extends AbstractPage {

    public LuckyGiftPageObject(AppiumDriver<MobileElement> mappingDriver) {
	driver = mappingDriver;
    }

    private AppiumDriver<MobileElement> driver;
}
