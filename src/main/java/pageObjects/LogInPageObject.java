package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class LogInPageObject extends AbstractPage {

	public LogInPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public void Global_login(String phone, String pass, String otp) {
		if (getDriverName(driver).contains("android")) {

//			clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
//
//			inputToDynamicLogInTextBox(driver, phone, "Tiếp tục");
//
//			clickToDynamicButton(driver, "Tiếp tục");
//
//			if (getPageSource(driver).contains("Tài khoản của Quý khách đã đăng nhập trên thiết bị khác. Quý khách vui lòng đăng nhập lại.")) {
//
//				clickToDynamicButton(driver, "Đồng ý");
//			}

			inputToDynamicInputBox(driver, pass, "Mật khẩu");

			clickToDynamicButton(driver, "Đăng nhập");

//			inputToDynamicOtp(driver, otp, "Tiếp tục");
//			clickToDynamicButton(driver, "Tiếp tục");
//			clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		} else if (getDriverName(driver).contains("ios")) {

			inputToDynamicInputBox(driver, pass, "Mật khẩu đăng nhập");

			clickToDynamicButton(driver, "Tiếp tục");

		}
	}

}
