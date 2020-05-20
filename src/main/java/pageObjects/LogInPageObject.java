package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;

public class LogInPageObject extends AbstractPage {

	public LogInPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public void Global_login1(String phone, String pass, String otp) {

		clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		clickToTextID(driver, "com.VCB:id/tvSkip");

		inputToDynamicLogInTextBox(driver, phone, "Tiếp tục");

		clickToDynamicButton(driver, "Tiếp tục");

		if (getPageSource(driver).contains("kích hoạt trên thiết bị khác")) {

			clickToDynamicButton(driver, "Đồng ý");
		}

		inputToDynamicInputBox(driver, pass, "Mật khẩu đăng nhập");

		clickToDynamicButton(driver, "Tiếp tục");

		inputToDynamicOtp(driver, otp, "Tiếp tục");
		clickToDynamicButton(driver, "Tiếp tục");
		clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		clickToDynamicButtonLinkOrLinkText(driver, "Nhấn giữ để di chuyển nhanh đến các nhóm chức năng");

	}

	public void Global_login(String phone, String pass, String otp) {

		clickToDynamicButton(driver, "Cho phép");
		clickToTextID(driver, "com.VCB:id/tvSkip");

		inputToDynamicLogInTextBox(driver, phone, "Tiếp tục");

		clickToDynamicButton(driver, "Tiếp tục");

		if (getPageSource(driver).contains("kích hoạt trên thiết bị khác")) {

			clickToDynamicButton(driver, "Đồng ý");
		}

		inputToDynamicInputBox(driver, pass, "Mật khẩu đăng nhập");

		clickToDynamicButton(driver, "Tiếp tục");

		inputToDynamicOtp(driver, otp, "Tiếp tục");
		clickToDynamicButton(driver, "Tiếp tục");
		clickToDynamicButton(driver, "Luôn cho phép");
		clickToDynamicButtonLinkOrLinkText(driver, "Nhấn giữ để di chuyển nhanh đến các nhóm chức năng");
	}

	public void Global_login_After(String phone, String pass, String otp) {
		inputToDynamicInputBox(driver, pass, "Mật khẩu");
		clickToDynamicButton(driver, "Đăng nhập");

	}

	public void clickToDynamicAcceptButton(String dynamicIDValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicIDValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicIDValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicIDValue);
		}
	}
}
