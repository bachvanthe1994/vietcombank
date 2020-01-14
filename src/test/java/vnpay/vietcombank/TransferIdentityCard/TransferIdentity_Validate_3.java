package vnpay.vietcombank.TransferIdentityCard;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferIdentiryPageObject;
import vietcombank_test_data.Account_Data.Valid_Account;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.LogIn_Data.Login_Account;
import vietcombank_test_data.TransferIdentity_Data;
import vietcombank_test_data.TransferIdentity_Data.textCheckElement;

public class TransferIdentity_Validate_3 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferIdentiryPageObject trasferPage;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("Before class: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		log.info("TC_00_Step_1: chon tiep tuc");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		log.info("TC_00_Step_2: chon tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_00_Step_3: chon tiep tuc");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		log.info("TC_00_Step_4: chon tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_00_Step_5: chon tiep tuc");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_00_Step_6: chon tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_00_Step_7: chon tu choi");
		login.clickToDynamicButton(driver, "TỪ CHỐI");

	}

	@Test
	public void TC_48_ManHinhXacNhanGiaoDich() {
		log.info("TC_48_Step_1: chon chuyển tiền nhận bằng CMT");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_48_Step_2: chon tai khoan");
		trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.USD_ACCOUNT);

		log.info("TC_48_Step_3: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

		log.info("TC_48_Step_4: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

		log.info("TC_48_Step_5: so");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Số");

		log.info("TC_48_Step_6: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");

		log.info("TC_48_Step_7: noi cap");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "Nơi cấp");

		log.info("TC_48_Step_8: nguoi tra phi giao dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_48_Step_9: nhap so tien chuyen di");
		trasferPage.inputToDynamicInputBox(driver, "50", "Số tiền");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_48_Step_10: noi dung");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

		log.info("TC_48_Step_11: tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		trasferPage.scrollToText(driver, "Tài khoản nguồn");

		log.info("TC_48_Step_14: kiem tra title man hinh");
		String titleBar = trasferPage.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar");
		verifyEquals(titleBar, textCheckElement.TITLEBAR);

		log.info("TC_48_Step_15: kiem tra title man hinh");
		String titleHead = trasferPage.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleHead");
		verifyEquals(titleHead, textCheckElement.TITLEHEAD);

		log.info("TC_48_Step_16: kiem tra title tai khoan nguon");
		String account = trasferPage.getDynamicAccountTitle(driver, "1");
		verifyEquals(account, textCheckElement.ACCOUNT);

		log.info("TC_48_Step_17: kiem tra title ten nguoi thu huong");
		String user = trasferPage.getDynamicTextTitle(driver, "1");
		verifyEquals(user, textCheckElement.BENEFICIARY_NAME);

		log.info("TC_48_Step_18: kiem tra title giấy tờ tùy thân");
		String identity = trasferPage.getDynamicTextTitle(driver, "2");
		verifyEquals(identity, textCheckElement.IDENTITY);

		log.info("TC_48_Step_19: kiem tra title số");
		String number = trasferPage.getDynamicTextTitle(driver, "3");
		verifyEquals(number, textCheckElement.NUMBER);

		log.info("TC_48_Step_20: kiem tra title ngày cấp");
		String date = trasferPage.getDynamicTextTitle(driver, "4");
		verifyEquals(date, textCheckElement.DATE);

		log.info("TC_48_Step_21: kiem tra title nơi cấp");
		String issued = trasferPage.getDynamicTextTitle(driver, "5");
		verifyEquals(issued, textCheckElement.ISSUED);

		log.info("TC_48_Step_22: kiem tra title số tiền");
		String money = trasferPage.getDynamicTextTitleMoneyUSD(driver, "6");
		verifyEquals(money, textCheckElement.MONEY_USD);

		trasferPage.scrollToText(driver, "Tiếp tục");

		log.info("TC_48_Step_23: kiem tra title số tiền phí");
		String transaction_fee = trasferPage.getDynamicTextTitle(driver, "6");
		verifyEquals(transaction_fee, textCheckElement.TRANSACTION_FEE);

		log.info("TC_48_Step_24: kiem tra title nội dung");
		String content = trasferPage.getDynamicTextTitle(driver, "7");
		verifyEquals(content, textCheckElement.CONNTENT);

		log.info("TC_48_Step_25: kiem tra title nội dung");
		trasferPage.isDynamicButtonDisplayed(driver, "Tiếp tục");

		log.info("TC_48_Step_26 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Xác nhận thông tin");

		log.info("TC_48_Step_27: chon tai khoan");
		trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.VND_ACCOUNT);

		log.info("TC_48_Step_28: nhap so tien chuyen di");
		trasferPage.inputToDynamicInputBoxByHeader(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Thông tin giao dịch", "1");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_48_Step_29: tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		trasferPage.scrollToText(driver, "Tài khoản nguồn");

		log.info("TC_48_Step_30: kiem tra title man hinh");
		String titleBarVND = trasferPage.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar");
		verifyEquals(titleBarVND, textCheckElement.TITLEBAR);

		log.info("TC_48_Step_31: kiem tra title man hinh");
		String titleHeadVND = trasferPage.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleHead");
		verifyEquals(titleHeadVND, textCheckElement.TITLEHEAD);

		log.info("TC_48_Step_32: kiem tra title tai khoan nguon");
		String accountVND = trasferPage.getDynamicAccountTitle(driver, "1");
		verifyEquals(accountVND, textCheckElement.ACCOUNT);

		log.info("TC_48_Step_33: kiem tra title ten nguoi thu huong");
		String userVND = trasferPage.getDynamicTextTitle(driver, "1");
		verifyEquals(userVND, textCheckElement.BENEFICIARY_NAME);

		log.info("TC_48_Step_34: kiem tra title giấy tờ tùy thân");
		String identityVND = trasferPage.getDynamicTextTitle(driver, "2");
		verifyEquals(identityVND, textCheckElement.IDENTITY);

		log.info("TC_48_Step_35: kiem tra title số");
		String numberVND = trasferPage.getDynamicTextTitle(driver, "3");
		verifyEquals(numberVND, textCheckElement.NUMBER);

		log.info("TC_48_Step_36: kiem tra title ngày cấp");
		String dateVND = trasferPage.getDynamicTextTitle(driver, "4");
		verifyEquals(dateVND, textCheckElement.DATE);

		log.info("TC_48_Step_37: kiem tra title nơi cấp");
		String issuedVND = trasferPage.getDynamicTextTitle(driver, "5");
		verifyEquals(issuedVND, textCheckElement.ISSUED);

		log.info("TC_48_Step_38: kiem tra title số tiền");
		String moneyVND = trasferPage.getDynamicTextTitle(driver, "6");
		verifyEquals(moneyVND, textCheckElement.MONEY);

		trasferPage.scrollToText(driver, "Tiếp tục");

		log.info("TC_48_Step_39: kiem tra title số tiền phí");
		String transaction_feeVND = trasferPage.getDynamicTextTitle(driver, "7");
		verifyEquals(transaction_feeVND, textCheckElement.TRANSACTION_FEE);

		log.info("TC_48_Step_40: kiem tra title nội dung");
		String contentVND = trasferPage.getDynamicTextTitle(driver, "8");
		verifyEquals(contentVND, textCheckElement.CONNTENT);

		log.info("TC_48_Step_41: kiem tra title nội dung");
		trasferPage.isDynamicButtonDisplayed(driver, "Tiếp tục");
	}

	@Test
	public void TC_49_ManHinhXacNhanGiaoDichBangSMSOTP() {
		log.info("TC_49_Step_01: chon phuong thuc xac thuc");
		trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_49_Step_02: tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_49_Step_03: kiem tra hien thi title");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, "Xác thực giao dịch"));

		log.info("TC_49_Step_04: kiem tra hien link quay lai");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, "Quay lại"));

		log.info("TC_49_Step_05: kiem tra text ghi chu");
		String title = (trasferPage.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/lblMessage"));
		verifyEquals(title, TransferIdentity_Data.confirmMessage.TEXT_CONFIRM);
	}


    @Test
    public void TC_49_NhanIconBack() {
	log.info("TC_49_Step_1 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Quay lại");

	log.info("TC_49_Step_2: kiem tra title man hinh");
	String titleBar = trasferPage.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar");
	verifyEquals(titleBar, textCheckElement.TITLEBAR);
    }

	@Test
	public void TC_50_BoTrongOTP() {
		log.info("TC_50_Step_1: chon phuong thuc xac thuc");
		trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_50_Step_2 : chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_50_Step_3 : de trong OTP chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_50_Step_4 : kiểm tra bỏ trống OTP");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_OTP_NOT_FOUND));

		log.info("TC_50_Step_5: chọn đóng");
		trasferPage.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_51_NhapOTPItHon6KiTu() {
		log.info("TC_51_Step_1: điền thiếu otp");
		trasferPage.inputToDynamicOtpOrPIN(driver, TransferIdentity_Data.textCheckElement.OTP_DATA, "Tiếp tục");

		log.info("TC_51_Step_2 : chon tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_52_Step_2: kiem tra OTP da dien");
		String otp_message = trasferPage.getTextConfirmOtp(driver, "Đóng");
		verifyEquals(otp_message, TransferIdentity_Data.confirmMessage.MESSSAGE_OTP_MISSING);

		log.info("TC_51_Step_3: chọn đóng");
		trasferPage.clickToDynamicButton(driver, "Đóng");
    }


	@Test
	public void TC_52_NhapOTPNhieuHon6KiTu() {
		log.info("TC_52_Step_1: điền thiếu otp");
		trasferPage.inputToDynamicOtpOrPIN(driver, TransferIdentity_Data.textDataInputForm.MONEY_0, "Tiếp tục");

		log.info("TC_51_Step_2 : chon tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_52_Step_3: kiem tra OTP da dien");
		String otp = trasferPage.getTextConfirmOtp(driver, "Đóng");
		verifyEquals(otp, TransferIdentity_Data.confirmMessage.MESSSAGE_OTP_FALSE);

		log.info("TC_52_Step_4: chọn đóng");
		trasferPage.clickToDynamicButton(driver, "Đóng");
		}

		@Test
		public void TC_53_NhapOTPKhongDung() {
		log.info("TC_53_Step_1 : nhap otp sai");
		trasferPage.inputToDynamicOtpOrPIN(driver, TransferIdentity_Data.textDataInputForm.PASS_FALSE, "Tiếp tục");

		log.info("TC_53_Step_2 : chon tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_53_Step_3 : kiểm tra điền sai OTP");
		String otp = trasferPage.getTextConfirmOtp(driver, "Đóng");
		verifyEquals(otp, TransferIdentity_Data.confirmMessage.MESSSAGE_OTP_FALSE);

		log.info("TC_53_Step_4: chọn đóng");
		trasferPage.clickToDynamicButton(driver, "Đóng");

	}

	@Test
	public void TC_54_NhapOTPDung() {
		log.info("TC_54_Step_1: điền đúng otp");
		trasferPage.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_54_Step_2 : chon tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_54_Step_3 : chon thuc hien giao dich moi");
		trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
	}

	@Test
	public void TC_55_KiemTraManHinhXacThucGiaoDichBangMatKhau() {

		log.info("TC_55_Step_2: chon tai khoan");
		trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.VND_ACCOUNT);

		log.info("TC_55_Step_3: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

		log.info("TC_55_Step_4: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

		log.info("TC_55_Step_5: so");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Số");

		log.info("TC_55_Step_6: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");

		log.info("TC_55_Step_7: noi cap");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "Nơi cấp");

		log.info("TC_55_Step_8: nguoi tra phi giao dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_55_Step_9: nhap so tien chuyen di");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_55_Step_10: noi dung");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

		log.info("TC_55_Step_11: tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		trasferPage.scrollToText(driver, "Tiếp tục");

		log.info("TC_55_Step_12: kiem tra title nội dung");
		trasferPage.isDynamicButtonDisplayed(driver, "Tiếp tục");

		log.info("TC_55_Step_13: chon phuong thuc xac thuc");
		trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_55_Step_14: kiem tra title nội dung");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_55_Step_15: kiem tra hien thi title");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, "Xác thực giao dịch"));

		log.info("TC_55_Step_16: kiem tra hien link quay lai");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, "Quay lại"));

		log.info("TC_55_Step_17: kiem tra text ghi chu");
		String textNote = trasferPage.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/lblMessage");
		verifyEquals(textNote, TransferIdentity_Data.confirmMessage.TEXT_CONFIRM_PASS);
	}

	@Test
	public void TC_56_BoTrongMatKhau() {
		log.info("TC_56_Step_1 : chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_56_Step_2 : kiểm tra bỏ trống mat khau");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_PASSWORD_FOUND));

		log.info("TC_56_Step_3: chọn đóng");
		trasferPage.clickToDynamicButton(driver, "Đóng");

	}

    @Test
    public void TC_57_MatKhauKhongChinhXac() {
	log.info("TC_57_Step_1: điền mat khau sai");
	trasferPage.inputToDynamicPopupPasswordInput(driver, TransferIdentity_Data.textDataInputForm.PASS_FALSE, "Tiếp tục");

	log.info("TC_57_Step_2 : chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_57_Step_3 : kiểm tra dien sai mat khau");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_PASSWORD_FALSE));

	log.info("TC_57_Step_4: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");
    }

	@Test
	public void TC_58_DienThieuMatKhau() {
		log.info("TC_58_Step_1: điền thiếu otp");
		trasferPage.inputToDynamicPopupPasswordInput(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Tiếp tục");

		log.info("TC_58_Step_2 : chon tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_58_Step_3 : kiểm tra điền thiếu OTP");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_PASSWORD_FALSE));

		log.info("TC_58_Step_4: chọn đóng");
		trasferPage.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_59_KiemTraMaxlength() {
		log.info("TC_59_Step_1: kiem tra maxlength với 20 kí tự");
		trasferPage.inputToDynamicPopupPasswordInput(driver, TransferIdentity_Data.textCheckElement.PASS_MAXLENGTH_20, "Tiếp tục");

		log.info("TC_59_Step_2: kiem tra maxlength");
		String pass = trasferPage.getDynamicPopupPassword(driver, "Tiếp tục");
		int coutnPass = pass.length();
		verifyEquals(coutnPass, 20);

		log.info("TC_38_Step_3 : chon quay lại");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");

		log.info("TC_38_Step_4 : chon tiep tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_59_Step_5: kiem tra maxlength với 21 kí tự");
		trasferPage.inputToDynamicPopupPasswordInput(driver, TransferIdentity_Data.textCheckElement.PASS_MAXLENGTH_21, "Tiếp tục");

		log.info("TC_59_Step_6: kiem tra maxlength");
		String pass21 = trasferPage.getDynamicPopupPassword(driver, "Tiếp tục");
		int coutnPass21 = pass21.length();
		verifyEquals(coutnPass, 20);

		log.info("TC_38_Step_7 : chon tiep tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_59_Step_8: chọn đóng");
		trasferPage.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_60_MatKhauHopLe() {
		log.info("TC_36_Step_39: kiem tra maxlength với 21 kí tự");
		trasferPage.inputToDynamicPopupPasswordInput(driver, Login_Account.WRONG_PASSWORD, "Tiếp tục");

		log.info("TC_38_Step_42 : chon tiep tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_38_Step_42 : kiem tra man hinh xac nhan thanh cong");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, trasferPage.getTextInDynamicPopup(driver, "com.VCB:id/tvTitle")));
	}


	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
