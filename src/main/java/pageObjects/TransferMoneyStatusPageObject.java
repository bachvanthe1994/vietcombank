package pageObjects;

import java.util.List;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;

public class TransferMoneyStatusPageObject extends AbstractPage {

    public TransferMoneyStatusPageObject(AndroidDriver<AndroidElement> mappingDriver) {
	driver = mappingDriver;
    }

    private AndroidDriver<AndroidElement> driver;

    public List<String> getListStatusTransfer(AndroidDriver<AndroidElement> driver, String dynamicIndex) {
	waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_STARUS, dynamicIndex);
	return getTextInListElements(driver, DynamicPageUIs.DYNAMIC_STARUS, dynamicIndex);
    }
}
