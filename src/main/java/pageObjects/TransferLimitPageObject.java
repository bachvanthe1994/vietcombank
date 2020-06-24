package pageObjects;
import java.io.IOException;
import java.security.GeneralSecurityException;

import commons.AbstractPage;
import commons.Base;
import commons.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.SourceAccountModel;


public class TransferLimitPageObject extends AbstractPage {
	SourceAccountModel sourceAccount = new SourceAccountModel();

	public TransferLimitPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	
	//Thực hiện chuyển tiền thành công với PTXT SMS OTP 
	public void TransferQuick247_SMSOTP() throws GeneralSecurityException, IOException {
	clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

	clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7 qua tài khoản");
	clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7 qua tài khoản");

		clickToDynamicDropDown(driver, "Tài khoản nguồn");
		sourceAccount = chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		
		inputToDynamicInputBox(driver, Base.getDataInCell(4) , "Nhập/ chọn tài khoản nhận VND");

		clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng thụ hưởng");
		inputToDynamicInputBox(driver, Base.getDataInCell(21), "Tìm kiếm");
		clickToDynamicButtonLinkOrLinkText(driver, Base.getDataInCell(21));

		inputToDynamicInputBox(driver, "100000", "Số tiền");

		inputToDynamicInputBoxByHeader(driver, "test", "Thông tin giao dịch", "3");

		clickToDynamicButton(driver, "Tiếp tục");

		scrollUpToText(driver, "Tài khoản nguồn");

		
		scrollDownToText(driver, "Chọn phương thức xác thực");
		clickToDynamicButtonLinkOrLinkText(driver, "Chọn phương thức xác thực");
		clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		clickToDynamicButton(driver, "Tiếp tục");

		inputToDynamicOtp(driver, "123456", "Tiếp tục");

		clickToDynamicButton(driver, "Tiếp tục");

	
			clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		}
	
	
	}

