package vnpay.vietcombank.transfer_money_in_vcb;

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
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_In_Future_Validation_Part2 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class_Step_00: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("Before class_Step_01: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		log.info("Before class_Step_02: Dien so dien thoai ");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		log.info("Before class_Step_03: Click Tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class_Step_03:  Dien mat khau");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		log.info("Before class_Step_05: Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class_Step_06: Nhap OTP");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("Before class_Step_07: Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class_Step_01: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollToText(driver, "Trạng thái lệnh chuyển tiền");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

	}

	@Test
	public void TC_20_KiemTraHienThiMacDinhPhiGiaoDich() {

		log.info("TC_20_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_20_Step_02: Chon Chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_20_Step_03: Kiem tra Chuyen Tien Ngay hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Phí giao dịch người chuyển trả"));

		log.info("TC_20_Step_04: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_21_KiemTraDanhSachPhiGiaoDich() {

		log.info("TC_21_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_21_Step_02: Chon Chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_21_Step_02: Click phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_21_Step_03: Kiem tra Chuyen Tien Ngay hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Phí giao dịch"));

		log.info("TC_21_Step_04: Kiem tra Chuyen Tien Ngay hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Đóng"));

		log.info("TC_21_Step_05: Kiem tra Chuyen Tien Ngay hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Người chuyển trả"));

		log.info("TC_21_Step_06: Kiem tra Chuyen Tien Ngay hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Người nhận trả"));

		log.info("TC_21_Step_07: Kiem tra Chuyen Tien Ngay hien thi");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_21_Step_08:Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_22_KiemTraChonPhiGiaoDich() {

		log.info("TC_22_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_22_Step_02: Chon Chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_22_Step_02: Click phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_22_Step_03: Kiem tra Chuyen Tien Ngay hien thi");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_22_Step_04: Kiem tra Chuyen Tien Ngay hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Phí giao dịch người chuyển trả"));

		log.info("TC_22_Step_05: Click phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_22_Step_06: Kiem tra Chuyen Tien Ngay hien thi");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_22_Step_07: Kiem tra Chuyen Tien Ngay hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Phí giao dịch người nhận trả"));

		log.info("TC_22_Step_08: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_23_KiemTraHienThiMacDinhNoiDung() {

		log.info("TC_23_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_23_Step_02: Chon Chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_23_Step_03: Kiem tra text noi dung mac dinh");
		verifyEquals(transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3"), "Nội dung");

		log.info("TC_23_Step_04: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_24_KiemTraKyTuChoPhepNhapNoiDung() {

		log.info("TC_24_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_24_Step_02: Chon Chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_24_Step_03: Dien text, number, ky tu dac biet");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.TEXT_NUMBER_AND_SPECIAL_CHARACTERS, "Nội dung");

		log.info("TC_24_Step_04: Kiem tra text duoc input vao");
		verifyEquals(transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3"), TransferMoneyInVCB_Data.InvalidInputData.TEXT_NUMBER_AND_SPECIAL_CHARACTERS);

		log.info("TC_24_Step_05: Dien tieng viet co dau vao o noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InvalidInputData.VIETNAMESE_LANGUAGE, "Thông tin giao dịch", "3");

		log.info("TC_24_Step_06: Kiem tra text khong duoc dien vao");
		verifyEquals(transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3"), TransferMoneyInVCB_Data.InvalidInputData.CHANGED_VIETNAMESE_LANGUAGE);

		log.info("TC_24_Step_07: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_25_KiemTraMaxLengthNoiDung() {

		log.info("TC_25_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_25_Step_02: Chon Chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_25_Step_03: Dien text, number, ky tu dac biet");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.TEXT_WITH_MORE_THAN_140_CHARAC, "Nội dung");

		log.info("TC_25_Step_04: Kiem tra text duoc input vao");
		verifyEquals(transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3").length(), 140);

		log.info("TC_25_Step_05: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_26_KiemTraBoTrongTaiKhoanDichVaDongPopup() {

		log.info("TC_26_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_26_Step_02: Chon Chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_26_Step_03: Nhap so tien chuyen");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_26_Step_04: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_26_Step_05: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_26_Step_06: Kiem tra yeu cau nhap tai khoan nhan");

		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.EMPTY_RECEIVE_ACCOUNT_MESSAGE);

		log.info("TC_26_Step_07: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_26_Step_08: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_27_KiemTraBoTrongSoTienVaDongPopup() {

		log.info("TC_27_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_27_Step_02: Chon Chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_27_Step_02: Nhap so tien chuyen");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_27_Step_03: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_27_Step_04: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_27_Step_05: Kiem tra yeu cau nhap so tien");

		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.EMPTY_MONEY_MESSAGE);

		log.info("TC_27_Step_06: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_27_Step_07: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_28_KiemTraBoTrongNoiDungVaDongPopup() {

		log.info("TC_28_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_28_Step_02: Chon Chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_28_Step_02: Nhap so tien chuyen");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_28_Step_03: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_28_Step_04: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_28_Step_05: Kiem tra yeu cau nhap noi dung");

		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.EMPTY_TRANSFER_NOTE_MESSAGE);

		log.info("TC_28_Step_06: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_28_Step_07: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_29_KiemTraTaiKhoanDongChuSoHuuVaDongPopup() {

		log.info("TC_29_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_29_Step_02: Chon Chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_29_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_29_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Invalid_Account.SAME_OWNER_ACCOUNT_1);

		log.info("TC_29_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Invalid_Account.SAME_OWNER_ACCOUNT_2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_29_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_29_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_29_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_29_Step_08: Kiem tra yeu cau nhap noi dung");

		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.SAME_ACCOUNT_OWNER_MESSAGE);

		log.info("TC_29_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_29_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_30_KiemTraTaiKhoanTrungNhauVaDongPopup() {

		log.info("TC_30_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_30_Step_02: Chon Chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_30_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_30_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Invalid_Account.SAME_OWNER_ACCOUNT_1);

		log.info("TC_30_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Invalid_Account.SAME_OWNER_ACCOUNT_1, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_30_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_30_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_30_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_30_Step_08: Kiem tra yeu cau nhap noi dung");

		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.DUPLICATED_ACCOUNT_MESSAGE);

		log.info("TC_30_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_30_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_31_KiemTraTaiKhoanNhanKhongTonTaiTrenHeThongVaDongPopup() {

		log.info("TC_31_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_31_Step_02: Chon Chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_31_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_31_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_31_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_ACCOUNT_15_CHARACTERS, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_31_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_31_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_31_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_31_Step_08: Kiem tra yeu cau nhap noi dung");

		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.INEXISTED_ACCOUNT_OWNER_MESSAGE);

		log.info("TC_31_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_31_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_32_KiemTraChuyenKhoanTuTaiKhoanVNDSangUSDVaDongPopup() {

		log.info("TC_32_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_32_Step_02: Chon Chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_32_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_32_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_32_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_32_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_32_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_32_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_32_Step_08: Kiem tra yeu cau nhap noi dung");

		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.INVALID_RECEIVE_ACCOUNT);

		log.info("TC_32_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_32_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_33_KiemTraChuyenKhoanTuTaiKhoanVNDSangEURVaDongPopup() {

		log.info("TC_33_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_33_Step_02: Chon Chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_33_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_33_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_33_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_33_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_33_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_33_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_33_Step_08: Kiem tra yeu cau nhap noi dung");

		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.INVALID_RECEIVE_ACCOUNT);

		log.info("TC_33_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_33_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_34_KiemTraChuyenKhoanTuTaiKhoanUSDSangEURVaDongPopup() {

		log.info("TC_34_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_34_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_34_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

		log.info("TC_34_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.EUR_ACCOUNT, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_34_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_34_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_34_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_34_Step_08: Kiem tra yeu cau nhap noi dung");

		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.INVALID_RECEIVE_ACCOUNT);

		log.info("TC_34_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_34_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_35_KiemTraChuyenKhoanTuTaiKhoanEURSangUSDVaDongPopup() {

		log.info("TC_35_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_35_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_35_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_35_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.USD_ACCOUNT, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_35_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_35_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_35_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_35_Step_08: Kiem tra yeu cau nhap noi dung");

		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.INVALID_RECEIVE_ACCOUNT);

		log.info("TC_35_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_35_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
