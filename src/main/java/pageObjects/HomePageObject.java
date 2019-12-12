package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class HomePageObject extends AbstractPage{

	public HomePageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
}
