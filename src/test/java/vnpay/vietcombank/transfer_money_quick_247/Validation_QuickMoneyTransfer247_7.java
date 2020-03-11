package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SetupContactPageObject;
import pageObjects.TransferMoneyObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SetupContact_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Validation_QuickMoneyTransfer247_7 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private TransferMoneyObject transferMoney;
	private HomePageObject homePage;
	private SetupContactPageObject setupContact;
	List<String> listExpect;
	List<String> listActual;
	private String transferTime;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_00_Step_: Click menu header");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_00Step_: Click cai dat");
		setupContact = PageFactoryManager.getSetupContactPageObject(driver);
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("TC_00_Step_: Click quan ly danh ba");
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, "Quản lý danh bạ");

		log.info("TC_00_Step_: Click Danh bạ người hưởng");
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, "Danh bạ người hưởng");

		log.info("TC_00_Step_: delete danh sach co tu truoc");
		setupContact.deleteContactReceiver();

		log.info("TC_00_Step_: back lai man hinh danh ba");
		setupContact.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_00_Step_: back lai man hinh home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");

		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);

		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Thông tin giao dịch", "1");

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
	}

	@Test
	public void TC_099_VerifyManHinhXacThucGiaoDichBangMatKhau() {
		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_05_Step_click button tiep tục");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_90_Verify text xac thuc giao dich");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, TransferMoneyQuick_Data.TransferQuick.CONFIRM_LABEL), "Xác thực giao dịch");

		log.info("TC_90_Verify message xac thuc mat khau");
		verifyEquals(transferMoney.getDynamicTextByLabel(driver, "Xác thực giao dịch"), "Vui lòng nhập mật khẩu đăng nhập ứng dụng của Quý khách để xác nhận giao dịch");

		log.info("TC_90_Verify hien thi button tiep tuc");
		verifyTrue(transferMoney.isDynamicButtonDisplayed(driver, "Tiếp tục"));
	}

	@Test
	public void TC_100_VerifyMessageKhiKhongNhapMatKhau() {
		log.info("TC_91_Click button tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_91_Step_verify message khi khong nhap mat khau");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.PASS_BLANK_MESSAGE));

		log.info("TC_91_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	// @Test
	public void TC_101_VerifyMessageKhiNhapMatKhauKhongTonTai() {
		log.info("TC_95_Nhap mat khau khong ton tai");
		transferMoney.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.WRONG_PASSWORD, "Tiếp tục");

		log.info("TC_95_Click button tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_95_Step_verify message khi nhap mat khau khong dung");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.PASS_NOT_EXIST_MESSAGE));

		log.info("TC_95_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	// @Test
	public void TC_102_NhapMatKhauVuotQua20KyTu() {
		log.info("TC_95_Nhap mat khau lon hon 20 ky tu");
		transferMoney.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.PASSWORD_GREATER_THAN_20, "Tiếp tục");

		log.info("TC_95_Verify man hinh chi nhap toi da 20 ky tu");
		verifyEquals(transferMoney.getTextInDynamicPasswordInput(driver, "com.VCB:id/pin"), "••••••••••••••••••••");
	}

	@Test
	public void TC_103_NhapMatKhauThanhCong() {
		log.info("TC_95_Nhap mat khau dung");
		transferMoney.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_95_Click button tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_95_Verify text chuyen khoan thanh cong");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY), "CHUYỂN KHOẢN THÀNH CÔNG");
	}

	@Test
	public void TC_104_KiemTraManHinhGiaoDichThanhCong() {
		log.info("TC_01_Verify icon home");
		verifyTrue(transferMoney.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivHome"));

		log.info("TC_01_Verify icon thanh cong");
		verifyTrue(transferMoney.isDynamicImageSuccess(driver, "CHUYỂN KHOẢN THÀNH CÔNG"));

		log.info("TC_95_Verify text chuyen khoan thanh cong");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY), "CHUYỂN KHOẢN THÀNH CÔNG");

		log.info("TC_01_Verify time giao dich thanh cong");
		verifyTrue(transferMoney.isDynamicTimeAndMoneyDisplay(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4"));

		log.info("TC_01_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextByLabel(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_:Ngan hàng huong");
		verifyEquals(transferMoney.getDynamicTextByLabel(driver, "Ngân hàng hưởng"), Account_Data.Valid_Account.BANK[0]);

		log.info("TC_01_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextByLabel(driver, "Tài khoản đích"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_01_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextByLabel(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Hien thi button thuc hien giao dich moi");
		verifyTrue(transferMoney.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_01_Step_: Hien thi Button Chia sẻ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_01_Step_: Hien thi Button luu anh");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_01_Step_: Hien thi Button luu thu huong");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Lưu thụ hưởng"));
	}

	@Test
	public void TC_105_KiemTraDinhDangThoiGianGiaoDichThanhCong() {
		log.info("TC_01_ time giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");

		log.info("TC_08_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4").contains(transferTime.split(" ")[0]));
	}

	@Test
	public void TC_106_KiemTraButtonChiaSe() {
		log.info("TC_01_Nhan button chia se");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chia sẻ");

		log.info("TC_01_Verify man hinh chia se");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Cho phép Vietcombank truy cập ảnh, phương tiện và tệp trên thiết bị của bạn?"));

		log.info("TC_01_Click button cho phep");
		transferMoney.clickToDynamicButton(driver, "CHO PHÉP");

		log.info("TC_01_verify select sahre");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Select"));
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Link Sharing"));

		log.info("TC_01_Click ve man hinh chuyen tien thanh cong");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
	}

	// @Test --- click may ko click dc button
	public void TC_107_KiemTraButtonLuuAnh() {
		log.info("TC_01_Nhan button luu anh");
		transferMoney.clickToDynamicButton(driver, "Lưu ảnh");

		log.info("TC_95_Step_verify message khi nhap mat khau khong dung");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.SAVE_SUCCESS_MESSAGE));

		log.info("TC_95_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_108_LuuThongTinThuHuongSTKChuaCoTrongDanhBa() {
		log.info("TC_01_Verify hien thi button luu thu huong");
		verifyTrue(transferMoney.isDynamicButtonDisplayed(driver, "Lưu thụ hưởng"));

		log.info("TC_01_Nhan button Lưu thụ hưởng");
		transferMoney.clickToDynamicButton(driver, "Lưu thụ hưởng");

		log.info("TC_01_Verify label danh ba");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, "Lưu danh bạ"), SetupContact_Data.UI.SAVE_CONTACT_LABEL);

		log.info("TC_01_Verify label danh ba");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, "Lưu danh bạ"), SetupContact_Data.UI.SAVE_CONTACT_LABEL);

		log.info("TC_01_Input name contact");
		transferMoney.inputToDynamicInputBoxByHeader(driver, SetupContact_Data.UI.NAME_CARD[0], "Lưu danh bạ");

		log.info("TC_01_Verify so tai khoan");
		verifyEquals(transferMoney.getDynamicTextByLabel(driver, "Số tài khoản"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_01_Verify ngan hàng huong");
		verifyEquals(transferMoney.getDynamicTextByLabel(driver, "Ngân hàng hưởng"), Account_Data.Valid_Account.BANK);

		log.info("TC_01_Nhan button luu");
		transferMoney.clickToDynamicButton(driver, "Lưu");
	}
}
