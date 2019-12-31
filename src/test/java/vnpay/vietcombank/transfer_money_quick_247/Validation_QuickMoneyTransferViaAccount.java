package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;

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
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferMoneyObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SetupContact_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Validation_QuickMoneyTransferViaAccount extends Base {
	AppiumDriver<MobileElement> driver;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private LogInPageObject login;
	private HomePageObject homePage;
	private SetupContactPageObject setupContact;
	private TransferMoneyObject transferMoney;
	private String transactionNumber;
	private String amountStartString;
	private String defaultAccount;
	private String actualDefaultAccount;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("Before class: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		log.info("Before class");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class");
		login.clickToDynamicButton(driver, "CHO PHÉP");

		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
	}

	// @Test
	public void TC_00_TaoDanhBaThuHuong() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_00Step_: Click menu header");
		homePage.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_00Step_: Click cai dat");
		setupContact = PageFactoryManager.getSetupContactPageObject(driver);
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("TC_00_Step_: Click quan ly danh ba");
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, "Quản lý danh bạ");

		log.info("TC_00_Step_: Click Danh bạ người hưởng");
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, "Danh bạ người hưởng");

		log.info("TC_00_Step_: delete danh sach co tu truoc");
		setupContact.deleteContactReceiver();

		log.info("TC_00_Step_: Add contact 1");
		setupContact.addContactReceiver(SetupContact_Data.UI.TYPE_TRANFER[3], SetupContact_Data.UI.BANK, SetupContact_Data.UI.NAME_CARD1, SetupContact_Data.UI.ACCOUNT1);

		log.info("TC_00_Step_: Add contact 2");
		setupContact.addContactReceiver(SetupContact_Data.UI.TYPE_TRANFER[3], SetupContact_Data.UI.BANK, SetupContact_Data.UI.NAME_CARD2, SetupContact_Data.UI.ACCOUNT2);

		log.info("TC_00_Step_: back lai man hinh danh ba");
		setupContact.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_00_Step_: back lai man hinh home");
		homePage.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/menu_1");
	}

	/*
	 * @Test public void TC_01_ChuyenNhanhQuaTaiKhoan() {
	 * log.info("TC_01_Lay gia tri tai khoan mac dinh account"); defaultAccount =
	 * transferMoney.getDynamicTextDetailByID(driver, "com.VCB:id/tvDefaultAcc");
	 * 
	 * log.info("TC_01_Step_Scoll den man hinh chuyen tien nhanh");
	 * transferMoney.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
	 * 
	 * log.info("TC_01_Step_Click Chuyen tien nhanh");
	 * transferMoney.clickToDynamicButtonLinkOrLinkText(driver,
	 * "Chuyển tiền nhanh 24/7"); }
	 * 
	 * @Test public void TC_02_CheckButtonBackChuyenNhanh247() {
	 * log.info("TC_01_Step_Kiem tra button back");
	 * transferMoney.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
	 * 
	 * log.info("TC_01_Step_Quay lai man hinh chuyen tien nhanh");
	 * transferMoney.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
	 * 
	 * log.info("TC_01_Step_Click Chuyen tien nhanh");
	 * transferMoney.clickToDynamicButtonLinkOrLinkText(driver,
	 * "Chuyển tiền nhanh 24/7");
	 * 
	 * }
	 * 
	 * @Test public void TC_03_CheckComboboxHinhThucChuyenTien() {
	 * log.info("TC_01_Step_Click combobox hinh thuc chuyen tien");
	 * transferMoney.clickToDynamicButtonLinkOrLinkText(driver,
	 * TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
	 * 
	 * log.info("TC_01_Step_Check value trong hinh thuc chuyen tien");
	 * transferMoney.isDynamicMessageAndLabelTextDisplayed(driver,
	 * TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
	 * transferMoney.isDynamicMessageAndLabelTextDisplayed(driver,
	 * TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);
	 * 
	 * }
	 * 
	 * @Test public void TC_04_ChonHinhThucChuyenTien() {
	 * log.info("TC_01_Step_click hinh thuc chuyen tien");
	 * transferMoney.clickToDynamicButtonLinkOrLinkText(driver,
	 * TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
	 * 
	 * log.info("TC_01_Step_verify label thong tin nguoi chuyen");
	 * transferMoney.isDynamicMessageAndLabelTextDisplayed(driver,
	 * TransferMoneyQuick_Data.TransferQuick.INFO_FORM_LABEL); }
	 * 
	 * @Test public void TC_05_KiemTraHienThiTaiKhoanMacDinh() {
	 * log.info("TC_03_01_Kiem tra hien thi tai khoan mac dinh");
	 * actualDefaultAccount =
	 * transferMoney.getTextDynamicDefaultSourceAccount(driver, "Tài khoản nguồn");
	 * verifyEquals(actualDefaultAccount, defaultAccount); }
	 * 
	 * @Test public void TC_06_ValidationTaiKhoanNguon() {
	 * log.info("TC_02_Step_Click combobox tai khoan nguon");
	 * transferMoney.clickToDynamicButtonLinkOrLinkText(driver,
	 * actualDefaultAccount);
	 * 
	 * }
	 * 
	 * @Test public void TC_07_ChonTaiKhoanNguonKhacMacDinh() { log.
	 * info("TC_02_Step_Cho phep chon tai khoan thanh toan khac tai khoan mac dinh"
	 * ); transferMoney.clickToDynamicButtonLinkOrLinkText(driver,
	 * TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[1]);
	 * 
	 * log.info("TC_02_Step_Chon lai ve man hinh tai khoan mac dinh");
	 * transferMoney.clickToDynamicButtonLinkOrLinkText(driver,
	 * TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[1]);
	 * transferMoney.clickToDynamicButtonLinkOrLinkText(driver,
	 * actualDefaultAccount); }
	 * 
	 * @Test public void TC_08_ChecksoDuKhaDung() {
	 * log.info("TC_02_Step_Lay so du kha dung"); amountStartString =
	 * transferMoney.getDynamicAmountLabel(driver,
	 * "Số dư khả dụng").replaceAll("\\D+", "");
	 * 
	 * log.
	 * info("TC_02_Step_Check so du kha dung hien thi so du va loai tien tuong ung"
	 * ); verifyTrue(transferMoney.getDynamicAmountLabel(driver,
	 * "Số dư khả dụng").contains(addCommasToLong(amountStartString) + " VND")); }
	 * 
	 * @Test public void TC_09_ThongTinNguoiHuong() {
	 * log.info("TC_03_Step_verify label thong tin nguoi huong");
	 * transferMoney.isDynamicMessageAndLabelTextDisplayed(driver,
	 * TransferMoneyQuick_Data.TransferQuick.INFO_TO_LABEL);
	 * 
	 * log.info("TC_03_Step_verify text");
	 * transferMoney.isDynamicTextInInputBoxDisPlayed(driver,
	 * "Nhập/chọn tài khoản nhận VND");
	 * 
	 * log.info("Check case bo trong truong tai khoan nguoi nhan"); }
	 * 
	 * @Test public void TC_10_TaiKhoanNhanTrong() {
	 * log.info("TC_03_Step_verify message khi tai khoan nguoi nhan khong ton tai "
	 * ); transferMoney.clickToDynamicButtonLinkOrLinkText(driver,
	 * "Ngân hàng hưởng"); transferMoney.clickToDynamicButtonLinkOrLinkText(driver,
	 * TransferMoneyQuick_Data.TransferQuick.BANK[0]);
	 * 
	 * log.info("TC_03_Step_Nhap so tien chuyen");
	 * transferMoney.inputToDynamicInputBox(driver,
	 * TransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");
	 * 
	 * log.info("TC_03_Step_Chon phi giao dich la nguoi chuyen tra");
	 * transferMoney.clickToDynamicButtonLinkOrLinkText(driver,
	 * TransferMoneyQuick_Data.TransferQuick.COST[0]);
	 * transferMoney.clickToDynamicButtonLinkOrLinkText(driver,
	 * TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);
	 * 
	 * log.info("TC_03_Step_Nhap noi dung");
	 * transferMoney.inputToDynamicInputBox(driver,
	 * TransferMoneyQuick_Data.TransferQuick.NOTE, "Nội dung");
	 * 
	 * log.info("TC_03_Step_Tiep tuc"); transferMoney.clickToDynamicButton(driver,
	 * "Tiếp tục");
	 * 
	 * log.info("TC_03_Step_verify message khi tai khoan nguoi nhan trong ");
	 * transferMoney.isDynamicMessageAndLabelTextDisplayed(driver,
	 * TransferMoneyQuick_Data.MessageTransferMoney.ACCOUNT_BLANK);
	 * 
	 * log.info("Close popup"); transferMoney.clickToDynamicButton(driver, "Đóng");
	 * 
	 * }
	 * 
	 * @Test public void TC_11_TaiKhoanNhanLonHon25KyTu() {
	 * 
	 * log.info("TC_03_Step_Invalid nhap so tai khoan nguoi nhan vuot qua 25 ky tu"
	 * ); transferMoney.inputToDynamicInputBox(driver,
	 * TransferMoneyQuick_Data.TransferQuick.INVALID_ACC_ACCEPT_OVER_MAX,
	 * "Nhập/chọn tài khoản nhận VND");
	 * 
	 * }
	 * 
	 * @Test public void TC_12_TaiKhoanNhanNhapKyTuCoDau() { log.
	 * info("TC_03_Step_Invalid nhap so tai khoan nguoi nhan co ky tu tieng viet co dau"
	 * ); transferMoney.inputToDynamicInputBox(driver,
	 * TransferMoneyQuick_Data.TransferQuick.INVALID_ACC_VIETNAM_KEY,
	 * "Nhập/chọn tài khoản nhận VND");
	 * 
	 * log.info("TC_03_Step_Verify chuyen doi thanh tieng viet khong dau");
	 * transferMoney.isDynamicTextInInputBoxDisPlayed(driver,
	 * transferMoney.removeUnicode(driver,
	 * TransferMoneyQuick_Data.TransferQuick.INVALID_ACC_VIETNAM_KEY));
	 * 
	 * log.info("TC_03_Step_Tiep tuc"); transferMoney.clickToDynamicButton(driver,
	 * "Tiếp tục");
	 * 
	 * log.info("TC_03_Step_verify message khi tai khoan nguoi nhan khong ton tai "
	 * ); transferMoney.isDynamicMessageAndLabelTextDisplayed(driver,
	 * TransferMoneyQuick_Data.MessageTransferMoney.ACCOUNT_NOT_EXIST);
	 * 
	 * log.info("Close popup"); transferMoney.clickToDynamicButton(driver, "Đóng");
	 * 
	 * }
	 * 
	 * @Test public void TC_13_VerifyDefaultDanhBaThuHuong() {
	 * log.info("click danh ba nguoi thu huong");
	 * transferMoney.clickToDynamicBottomMenu(driver, "com.VCB:id/ivContent1");
	 * 
	 * log.info("verify tieu de danh ba nguoi huong");
	 * transferMoney.isDynamicMessageAndLabelTextDisplayed(driver,
	 * "Danh bạ người hưởng");
	 * 
	 * log.info("verify button dong");
	 * transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Đóng");
	 * 
	 * log.info("verify tim kiem");
	 * transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Tìm kiếm");
	 * 
	 * log.info("verify ten trong contact");
	 * transferMoney.isDynamicMessageAndLabelTextDisplayed(driver,
	 * SetupContact_Data.UI.NAME_CARD1);
	 * 
	 * log.info("verify so tai khoan trong contact");
	 * transferMoney.isDynamicMessageAndLabelTextDisplayed(driver,
	 * SetupContact_Data.UI.ACCOUNT1);
	 * 
	 * }
	 * 
	 * @Test public void TC_14_TimKiemDanhBaThuHuong() {
	 * log.info("Tim kiem theo so tai khoan thu huong");
	 * transferMoney.inputToDynamicInputBox(driver, SetupContact_Data.UI.ACCOUNT1,
	 * "Tìm kiếm");
	 * 
	 * log.info("verify so tai khoan trong contact");
	 * transferMoney.isDynamicMessageAndLabelTextDisplayed(driver,
	 * SetupContact_Data.UI.ACCOUNT1);
	 * 
	 * log.info("Clear text"); transferMoney.clearText(driver,
	 * DynamicPageUIs.DYNAMIC_INPUT_BOX, SetupContact_Data.UI.ACCOUNT1);
	 * 
	 * }
	 * 
	 * @Test public void TC_15_TimKiemDanhBaThuHuongKhongTonTai() {
	 * log.info("Tim kiem theo ten nguoi thu huong khong ton tai");
	 * transferMoney.inputToDynamicInputBox(driver,
	 * TransferMoneyQuick_Data.TransferQuick.NAME_INVALID, "Tìm kiếm");
	 * 
	 * log.info("verify so tai khoan trong contact");
	 * transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver,
	 * TransferMoneyQuick_Data.TransferQuick.NAME_INVALID);
	 * 
	 * log.info("Clear text"); transferMoney.clearText(driver,
	 * DynamicPageUIs.DYNAMIC_INPUT_BOX,
	 * TransferMoneyQuick_Data.TransferQuick.NAME_INVALID);
	 * 
	 * }
	 * 
	 * @Test public void TC_16_TimKiemChinhXacMotDanhBaThuHuong() {
	 * log.info("Tim kiem ten nguoi thu huong");
	 * transferMoney.inputToDynamicInputBox(driver, SetupContact_Data.UI.NAME_CARD1,
	 * "Tìm kiếm");
	 * 
	 * log.info("chon 1 ten nguoi thu huong");
	 * transferMoney.clickToDynamicButtonLinkOrLinkText(driver,
	 * SetupContact_Data.UI.NAME_CARD1);
	 * 
	 * }
	 * 
	 * @Test public void TC_17_VerifyManHinhTimKiem() {
	 * log.info("check tai khoan vua chon ngoai man hinh khoi tao");
	 * transferMoney.isDynamicTextInInputBoxDisPlayed(driver,
	 * SetupContact_Data.UI.ACCOUNT1);
	 * 
	 * log.info("check ngan hang thu huong");
	 * transferMoney.isDynamicMessageAndLabelTextDisplayed(driver,
	 * SetupContact_Data.UI.BANK); }
	 */
	@Test
	public void TC_18_NganHangHuongBoTrong() {
		/*
		 * log.info("TC_04_Step_button back");
		 * transferMoney.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		 */
		log.info("TC_04_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_04_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_04_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, defaultAccount);

		log.info("TC_04_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_04_Step_Khong chon ngan hang chuyen tien");

		log.info("TC_04_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_04_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_04_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_04_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_verify message khi khong chon ngan hang huong ");
		transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.BANK_BLANK);

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");

		log.info("click danh sach ngan hang huong");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");

		log.info("verify title ngan hang huong");
		transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Ngân hàng hưởng");

		log.info("verify tim kiem");
		transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Tìm kiếm");
	}

	@Test
	public void TC_19_ValidationTimKiemNganHang() {
		log.info("Tim kiem ngan hang theo ten ngan hang, nhap ten ngan hang");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0], "Tìm kiếm");

		log.info("verify ket qua tim kiem");
		transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0]);

		log.info("Clear text");
		transferMoney.clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, TransferMoneyQuick_Data.TransferQuick.BANK[0]);
	}

	@Test
	public void TC_20_NganHangHuongKhongTonTai() {
		log.info("Tim kiem ngan hang theo ten ngan hang, nhap ten ngan hang");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.BANK_INVALID, "Tìm kiếm");

		log.info("verify ket qua tim kiem");
		transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, TransferMoneyQuick_Data.TransferQuick.BANK_INVALID);

		log.info("Clear text");
		transferMoney.clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, TransferMoneyQuick_Data.TransferQuick.BANK_INVALID);
	}

	@Test
	public void TC_21_ChonMotNganHangThuHuong() {
		log.info("Tim kiem ngan hang theo ten ngan hang, nhap ten ngan hang");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0], "Tìm kiếm");

		log.info("Click ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0]);
	}

	@Test
	public void TC_22_ChonMotNganHangThuHuongKhac() {
		log.info("Click ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0]);

		log.info("Tim kiem ngan hang theo ten ngan hang, nhap ten ngan hang");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.BANK[1], "Tìm kiếm");

		log.info("Click ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK[1]);
	}

}
