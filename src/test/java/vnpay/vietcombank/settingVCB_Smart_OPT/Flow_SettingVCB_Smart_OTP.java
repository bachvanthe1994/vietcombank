package vnpay.vietcombank.settingVCB_Smart_OPT;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.TransferInVCBRecurrent;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SettingVCBSmartOTP_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;
import pageObjects.LogInPageObject;

public class Flow_SettingVCB_Smart_OTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private SettingVCBSmartOTPPageObject smartOTP;
	private TransferMoneyInVcbPageObject transferRecurrent;
	String expectAvailableBalance;
	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	String password = "";
	private String transferTime;

	TransferInVCBRecurrent info = new TransferInVCBRecurrent(Account_Data.Valid_Account.ACCOUNT2, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "SMS OTP");
	TransferInVCBRecurrent info1 = new TransferInVCBRecurrent(Account_Data.Valid_Account.EUR_ACCOUNT, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "2", "Ngày", "", "", "10", "Người chuyển trả", "test", "SMS OTP");
	TransferInVCBRecurrent info2 = new TransferInVCBRecurrent(Account_Data.Valid_Account.ACCOUNT2, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "1", "Tháng", "", "", "500000", "Người nhận trả", "test", "Mật khẩu đăng nhập");
	TransferInVCBRecurrent info3 = new TransferInVCBRecurrent(Account_Data.Valid_Account.EUR_ACCOUNT, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "2", "Tháng", "", "", "10", "Người nhận trả", "test", "Mật khẩu đăng nhập");
	TransferInVCBRecurrent info4 = new TransferInVCBRecurrent(Account_Data.Valid_Account.USD_ACCOUNT, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "2", "Ngày", "", "", "10", "Người chuyển trả", "test", "SMS OTP");
	TransferInVCBRecurrent info5 = new TransferInVCBRecurrent(Account_Data.Valid_Account.USD_ACCOUNT, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "2", "Ngày", "", "", "10", "Người nhận trả", "test", "Mật khẩu đăng nhập");
	

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		password = pass;

		smartOTP = PageFactoryManager.getLocationSettingVCBSmartOTPPageObject(driver);

	}

	@Test
	public void TC_01_SettingVCBSmartOPT() {
		log.info("------------------------TC_01_Step_01: Click menu header-------------------------------");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("-----------------------TC_01_Step_02: Click thanh Cai dat VCB-Smart OTP------------------------");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("---------------------------TC_01_Step_03: Click Cai dat VCB Smart OTP---------------------");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

		log.info("-------------------------------TC_01_Step_04: Verify man hinh cai dat VCB Smart OTP------------------------");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Cài đặt VCB-Smart OTP");

		log.info("---------------------------------TC_01_Step_05: Click btn Cai dat-------------------------");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("------------------TC_01_Step_06: Verify hien thi man hinh dieu khoan dieu kien----------------");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Điều khoản, điều kiện");

		log.info("----------------TC_01_Step_06: Click radio btn dong y dien khoan------------------");
		smartOTP.clickToTextID(driver, "com.VCB:id/rule");

		log.info("----------------TC_01_Step_07: Click btn Dong y------------------");
		smartOTP.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");

		log.info("----------------TC_01_Step_08: Verify hien thi man hinh cai dat mat khau VCB Smart OTP------------------");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Cài đặt mật khẩu VCB-Smart OTP");

		log.info("----------------TC_01_Step_09: Verify hien thi tittle cua man hinh cai dat------------------");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, SettingVCBSmartOTP_Data.TITTLE_SETTING);

		log.info("----------------TC_01_Step_10: Click Nhap mat khau------------------");
		smartOTP.inputToDynamicInputBox(driver, SettingVCBSmartOTP_Data.SMART_OTP, "Nhập mật khẩu");

		log.info("----------------TC_01_Step_11: Click nhap truong nhap lai mat khau-----------------");
		smartOTP.inputToDynamicInputBox(driver, SettingVCBSmartOTP_Data.SMART_OTP, "Nhập lại mật khẩu");

		log.info("----------------TC_01_Step_12: Click btn Tiep tuc---------------------");
		smartOTP.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");

		log.info("----------------TC_01_Step_13: Verify hien thi man hinh Xac thuc giao dich---------------------");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Xác thực giao dịch");

		log.info("----------------TC_01_Step_14: Verify hien thi man hinh Xac thuc giao dich---------------------");
		smartOTP.inputToDynamicSmartOtp(driver, SettingVCBSmartOTP_Data.VALUE_OTP, "com.VCB:id/otp");

		log.info("----------------TC_01_Step_15: Click btn Tiep Tuc---------------------");
		smartOTP.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("----------------TC_01_Step_16: verify Trang thai dã kich hoat Smart OTP--------------------");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Đã kích hoạt");

		log.info("----------------TC_01_Step_17: Click btn Back --------------------");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("----------------TC_01_Step_18: Click btn Trang chu--------------------");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_02_ChuyenTienTrongVCB() {
		transferRecurrent = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		log.info("TC_01_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_01_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		transferRecurrent.scrollUpToText(driver, "Tài khoản nguồn");
		expectAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

		transferRecurrent.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/ chọn tài khoản thụ hưởng");

		log.info("TC_01_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info.frequencyNumber);

		log.info("TC_01_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info.money, "Số tiền");

		log.info("TC_01_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.fee);

		log.info("TC_01_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.note, "Thông tin giao dịch", "3");

		log.info("TC_01_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_9_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_9_1_Kiem tra hinh thuc chuyen tien");
		transferRecurrent.scrollUpToText(driver, "Hình thức chuyển tiền");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

		log.info("TC_01_9_2_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info.sourceAccount);

		log.info("TC_01_9_3_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info.destinationAccount));

		log.info("TC_01_9_4_Kiem tra so tien");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số tiền").contains(addCommasToLong(info.money)));

		log.info("TC_01_9_5_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất chuyển"), info.frequencyNumber + " " + info.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

		log.info("TC_01_9_7_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info.note);

		log.info("TC_01_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(transferRecurrent.getDynamicTextInTransactionDetail(driver, info.authenticationMethod));
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_01_11_Kiem tra so tien phi");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_01_12_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		transferRecurrent.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_13_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		transferTime = transferRecurrent.getTransferMoneyRecurrentTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT);

		log.info("TC_01_13_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_01_13_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), info.destinationAccount);

		log.info("TC_01_13_3_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info.note);
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_01_14_Click Thuc hien giao dich moi");
		login.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
	}

	@Test
	public void TC_03_ChuyenTienNhanh_24_7() {

	}

	@Test
	public void TC_04_ChuyenTienNToiNganHangKhac() {

	}

	@Test
	public void TC_05_ChuyenTienNhanBangTienMat() {

	}

	@Test
	public void TC_06_ChuyenTienTuThien() {

	}

	@Test
	public void TC_07_NapTienDienThoai() {

	}

	@Test
	public void TC_08_HoaDonTienDien() {

	}

	@Test
	public void TC_09_ThanToanTienNuoc() {

	}

	public void TC_HuyKichHoatVCBSmartOPT() {

		log.info("------------------------TC_01_Step_01: Click menu header-------------------------------");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("-----------------------TC_01_Step_02: Click thanh Cai dat VCB-Smart OTP------------------------");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("---------------------------TC_01_Step_03: Click Cai dat VCB Smart OTP---------------------");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

		log.info("-------------------------------TC_01_Step_04: Verify man hinh cai dat VCB Smart OTP------------------------");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Cài đặt VCB-Smart OTP");

		log.info("---------------------------------TC_02_Step_01: Click btn Huy Cai dat-------------------------");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Hủy");

		log.info("----------------------------TC_02_Step_02: Verify hien thi popup xac nhan huy cai dat OTP----------------");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, SettingVCBSmartOTP_Data.MESSEGE_CONFIRM_CANCEL);

		log.info("---------------------------TC_02_Step_03: Verify hien thi popup xac nhan huy cai dat OTP----------------");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Có");

//		log.info("-------------------------------TC_02_Step_04: Verify xac nhan huy Smart OTP thanh cong----------------");
//		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, SettingVCBSmartOTP_Data.MESSEGE_CANCEL_SMART_OTP);

		log.info("--------------------------TC_02_Step_05: verify Trang thai dã kich hoat Smart OTP--------------------");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Chưa kích hoạt");
	}

}
