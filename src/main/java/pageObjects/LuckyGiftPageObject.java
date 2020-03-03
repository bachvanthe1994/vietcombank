package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;

public class LuckyGiftPageObject extends AbstractPage {

    public LuckyGiftPageObject(AndroidDriver<AndroidElement> mappingDriver) {
	driver = mappingDriver;
    }

    private AndroidDriver<AndroidElement> driver;

    // Click vào menu tại bottom hoặc icon đóng k chứa text, tham số truyền vào là
    // resource id
    public void clickToDynamicImageViewByID(String dynamicID) {
	boolean status = false;
	scrollIDown(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
	status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
	if (status == true) {
	    clickToElement(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
	}
    }
}
