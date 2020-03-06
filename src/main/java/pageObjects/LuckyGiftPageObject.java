package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;

public class LuckyGiftPageObject extends AbstractPage {

    public LuckyGiftPageObject(AppiumDriver<MobileElement> mappingDriver) {
	driver = mappingDriver;
    }

    private AppiumDriver<MobileElement> driver;

    // Click vào menu tại bottom hoặc icon đóng k chứa text, tham số truyền vào là
    // resource id
    public void clickToDynamicImageViewByID(String dynamicID) {
	boolean status = false;
	scrollUp(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
	status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
	if (status == true) {
	    clickToElement(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
	}
    }

//    chọn phương thức xác thực
    public void clickToTextID(String dynamicID) {
	boolean status = false;
	scrollIDown(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
	status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
	if (status == true) {
	    clickToElement(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
	}

    }

    // Click vào ô dropdown, và ô date time , tham số truyền vào là resource id
    public void clickToDynamicDropdownAndDateTimePicker(String dynamicID) {
	boolean status = false;
	scrollIDown(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
	status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
	if (status == true) {
	    clickToElement(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
	}

    }
}
