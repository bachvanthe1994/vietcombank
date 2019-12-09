package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.TransferInVCBRecurrent;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoney_Data;

public class TransferMoneyRecurrent extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyInVcbPageObject transferRecurrent;
	TransferInVCBRecurrent info = new TransferInVCBRecurrent("0010000000322", "0010000000318", "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "SMS OTP");
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeMethod  
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver= openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);
		transferRecurrent = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");
		
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);
		
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		login.clickToDynamicButton(driver, "Tiếp tục");
		
	}
	
	private long surplus, availableBalance, actualAvailableBalance;
	@Test
	public void TC_01_ChuyenTienLienNganHangCoPhiGiaoDichNguoiChuyenTraXacThucBangOTP(){	
		log.info("TC_01_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");
		
		log.info("TC_01_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		
		log.info("TC_01_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		surplus = Long.parseLong(transferRecurrent.getDynamicTextInTextView(driver, "Số dư khả dụng").replaceAll("\\D+",""));
		
		transferRecurrent.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_01_4_Chon tan suat");
		transferRecurrent.inputFrequencyNumber(info.frequencyNumber);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.frequencyCategory);
		
		log.info("TC_01_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info.money, "Số tiền");
		
		log.info("TC_01_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");
		
		log.info("TC_01_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info.note, "Nội dung");
		
		log.info("TC_01_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_9_Kiem tra man hinh xac nhan thong tin");
		verifyEquals(transferRecurrent.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");
		verifyEquals(transferRecurrent.getDynamicTextInTextView(driver, "Tài khoản nguồn"), info.sourceAccount);
		verifyTrue(transferRecurrent.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(info.destinationAccount));
		verifyTrue(transferRecurrent.getDynamicTextInTextView(driver, "Số tiền").contains(addCommasToLong(info.money)));
		verifyEquals(transferRecurrent.getDynamicTextInTextView(driver, "Tần suất"), info.frequencyNumber + " " + info.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTextView(driver, "Số lần giao dịch"), "2");
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2(driver, "Số tiền phí"), info.fee);
		verifyEquals(transferRecurrent.getDynamicTextInTextView(driver, "Nội dung"), info.note);
		
		log.info("TC_01_9_Chon phuong thuc xac thuc");
		transferRecurrent.scrollToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		long fee = Long.parseLong(transferRecurrent.getDynamicTextInTextView(driver, info.authenticationMethod).replaceAll("\\D+",""));
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);
		
		log.info("TC_01_10_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		transferRecurrent.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_11_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoney_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));
		verifyEquals(transferRecurrent.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), "NGO TRI NAM");
		verifyEquals(transferRecurrent.getDynamicTextInTextView(driver, "Tài khoản đích"), info.destinationAccount);
		verifyEquals(transferRecurrent.getDynamicTextInTextView(driver, "Nội dung"), info.note);
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));
		
		log.info("TC_01_12_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
//		log.info("TC_01_13_Kiem tra so du kha dung luc sau");
//		transferRecurrent.clickToDynamicDropDown(driver,"Tài khoản nguồn");
//		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
//		actualAvailableBalance = Long.parseLong(transferRecurrent.getDynamicTextInTextView(driver, "Số dư khả dụng").replaceAll("\\D+",""));
//
//		availableBalance = surplus - Long.parseLong(info.money) - fee;
		
	}
	
	@AfterMethod(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
