package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import com.google.common.base.Verify;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;



public class SettingVCBSmartOTPPageObject extends AbstractPage {


	public SettingVCBSmartOTPPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	
	
	public void setupSmartOTP (String pass, String code) {
	clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

	clickToDynamicTextFollowText(driver, "Chưa kích hoạt");

	clickToTextID(driver, "com.VCB:id/rule");

		clickToDynamicButton(driver, "Đồng ý");

	inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");

		inputToDynamicInputBox(driver, pass, "Nhập lại mật khẩu");

	clickToDynamicButton(driver, "Tiếp tục");

	inputToDynamicSmartOtp(driver, code, "com.VCB:id/otp");

		clickToDynamicButton(driver, "Tiếp tục");

		//Verify cai dat thanh cong
	waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
	isDynamicMessageAndLabelTextDisplayed(driver, "Đã kích hoạt");

		clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");

		clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	

	public void cancelSetupSmartOTP () {

		clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

		//Click btn Huy Cai dat");
		clickToDynamicButtonLinkOrLinkText(driver, "Hủy");

		//Verify hien thi popup xac nhan huy cai dat OTP");
	isDynamicMessageAndLabelTextDisplayed(driver, "Quý khách có chắc chắn muốn hủy phương thức xác thực bằng VCB-Smart OTP không?");

		//Verify hien thi popup xac nhan huy cai dat OTP");
	clickToDynamicButtonLinkOrLinkText(driver, "Có");
	}
	
	
	
}
