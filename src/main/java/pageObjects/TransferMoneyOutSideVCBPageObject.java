package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.TransferMoneyOutSideVCBPageUIs;

public class TransferMoneyOutSideVCBPageObject extends AbstractPage{
	public TransferMoneyOutSideVCBPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}
	
	private AndroidDriver<AndroidElement> driver;
	
	
	
}
