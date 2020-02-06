package Test;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombank_test_data.LogIn_Data;

public class Global_Login extends AbstractPage {
	
	public Global_Login(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AndroidDriver<AndroidElement> driver;

	public void Global_login() {

		clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		clickToDynamicButton(driver, "Tiếp tục");

		inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		clickToDynamicButton(driver, "Tiếp tục");

		inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		clickToDynamicButton(driver, "Tiếp tục");

		clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

	}

}
