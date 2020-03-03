package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class LogInPageObject extends AbstractPage {

	public LogInPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AndroidDriver<AndroidElement> driver;

	public void Global_login(String phone, String pass, String otp) {
		clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		inputToDynamicLogInTextBox(driver, phone, "Tiếp tục");

		clickToDynamicButton(driver, "Tiếp tục");

		if (getPageSource(driver).contains("Tài khoản của Quý khách đã đăng nhập trên thiết bị khác. Quý khách vui lòng đăng nhập lại.")) {

			clickToDynamicButton(driver, "Đồng ý");
		}

		inputToDynamicInputBox(driver, pass, "Mật khẩu đăng nhập");

		clickToDynamicButton(driver, "Tiếp tục");

		inputToDynamicOtpOrPIN(driver, otp, "Tiếp tục");
		clickToDynamicButton(driver, "Tiếp tục");

		clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

	}

}
