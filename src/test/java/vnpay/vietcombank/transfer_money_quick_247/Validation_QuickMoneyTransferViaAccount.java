package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
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
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private SetupContactPageObject setupContact;
	private TransferMoneyObject transferMoney;
	private String amountStartString;
	private String defaultAccount;
	private String actualDefaultAccount;
	List<String> listExpect;
	List<String> listActual;

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
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
	}

 @Test
	public void TC_00_TaoDanhBaThuHuong() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_00_Step_: Click menu header");
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

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
		setupContact.addContactReceiver(SetupContact_Data.UI.TYPE_TRANFER[3], TransferMoneyQuick_Data.TransferQuick.BANK[0], SetupContact_Data.UI.NAME_CARD[0], SetupContact_Data.UI.ACCOUNT[0]);

		log.info("TC_00_Step_: Add contact 2");
		setupContact.addContactReceiver(SetupContact_Data.UI.TYPE_TRANFER[3], TransferMoneyQuick_Data.TransferQuick.BANK[0], SetupContact_Data.UI.NAME_CARD[1], SetupContact_Data.UI.ACCOUNT[1]);

		log.info("TC_00_Step_: back lai man hinh danh ba");
		setupContact.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_00_Step_: back lai man hinh home");
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_01_KiemTraButtonBack() {
		log.info("TC_01_Step_Scoll den man hinh chuyen tien nhanh");
		transferMoney.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Kiem tra button back");
		transferMoney.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Step_verify man hinh home");
		verifyTrue(transferMoney.isDynamicTextDetailByID(driver, "com.VCB:id/tvDefaultAcc"));

		log.info("TC_01_Lay gia tri tai khoan mac dinh account");
		defaultAccount = transferMoney.getDynamicTextDetailByID(driver, "com.VCB:id/tvDefaultAcc");
	}

	@Test
	public void TC_02_KiemTraGiaTriDefaultHinhThucChuyenTien() {
		log.info("TC_02_Step_Quay lai man hinh chuyen tien nhanh");
		transferMoney.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_02_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]));
	}

	@Test
	public void TC_03_CheckListComboboxHinhThucChuyenTien() {
		log.info("TC_03_Step_Click combobox hinh thuc chuyen tien");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_03_Lay danh sach gia tri loai chuyen tien");
		listActual = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvContent");

		log.info("TC_03_danh sach gia tri bank actual");
		listExpect = Arrays.asList(TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER);

		log.info("TC_03_Verify gia tri tim kiem");
		verifyEquals(listActual, listExpect);

	}

	@Test
	public void TC_04_ChonMotHinhThucChuyenTien() {
		log.info("TC_04_Step_click hinh thuc chuyen tien");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_04_Step_verify hinh thuc chuyen tien");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]));
	}

	@Test
	public void TC_05_TaiKhoanNhanTrong() {
		log.info("TC_05_Step_verify message khi tai khoan nguoi nhan khong ton tai ");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0]);

		log.info("TC_05_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_05_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_05_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_verify message khi tai khoan nguoi nhan trong ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.ACCOUNT_BLANK));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_06_KiemTraHienThiTaiKhoanMacDinh() {
		log.info("TC_06_01_Kiem tra hien thi tai khoan mac dinh");
		actualDefaultAccount = transferMoney.getTextDynamicDefaultSourceAccount(driver, "Tài khoản nguồn");
		verifyEquals(actualDefaultAccount, defaultAccount);
	}

	@Test
	public void TC_07_LayDanhSachTaiKhoanNguon() {
		log.info("TC_07_Step_Cho phep chon tai khoan thanh toan khac tai khoan mac dinh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_07_Lay danh sach gia tri loai chuyen tien");
		listActual = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvContent1");

		log.info("TC_07_danh sach gia tri bank actual");
		listExpect = Arrays.asList(TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM);

		log.info("TC_07_Verify gia tri tim kiem");
		verifyEquals(listActual, listExpect);
	}

	@Test
	public void TC_08_ChonTaiKhoanNguonKhacMacDinh() {
		log.info("TC_08_Step_Cho phep chon tai khoan thanh toan khac tai khoan mac dinh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[1]);

		log.info("TC_08_Step_Verify tai khoan");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[1]));

		log.info("TC_08_Step_Chon lai ve man hinh tai khoan mac dinh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[1]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

	}

	@Test
	public void TC_09_KiemTralabelThongTinNguoiChuyen() {
		log.info("TC_09_Step_verify label thong tin nguoi chuyen");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.INFO_FROM_LABEL));
	}

	@Test
	public void TC_10_KiemTraThongTinTaiKhoanNguon() {
		log.info("TC_10_Step_Verify tai khoan");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]));

		log.info("TC_10_Step_Lay so du kha dung man hinh home");
		amountStartString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng");
		System.out.println(amountStartString);

		log.info("TC_10_Step_Click tai khoan nguon");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_10_Step_Lay so du kha dung man hinh tai khoan nguon");
		String amountStartDefault = transferMoney.getDynamicAmountLabelList(driver, "Tài khoản nguồn", "0", "com.VCB:id/tvContent2");

		log.info("TC_10_Step_Verify ket qua");
		verifyEquals(amountStartString, amountStartDefault);
		
		log.info("TC_10_Click ve man hinh chuyen tien");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]));
	}

	@Test
	public void TC_11_ThongTinNguoiHuong() {
		log.info("TC_11_Step_verify label thong tin nguoi huong");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.INFO_TO_LABEL));
	}

	@Test
	public void TC_12_KiemTraHienThilabelChonTaiKhoanNhan() {
		log.info("TC_12_Step_verify text");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Nhập/chọn tài khoản nhận VND"));
	}

	// @Test--------------Chưa tìm đc giải pháp
	public void TC_13_TaiKhoanNhanLonHon25KyTu() {
		log.info("TC_02_Nhap 26 ky tu vao truong tai khoan");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.INVALID_ACC_ACCEPT_OVER_MAX, "Thông tin người hưởng", "1");
	}

	@Test
	public void TC_14_TaiKhoanNhanNhoHon25KyTu() {
		log.info("TC_14_Nhap 26 ky tu vao truong tai khoan");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.INVALID_ACC_ACCEPT, "Thông tin người hưởng", "1");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, TransferMoneyQuick_Data.TransferQuick.INVALID_ACC_ACCEPT));
	}

	@Test
	public void TC_15_TaiKhoanNhanNhapKyTuTiengViet() {
		log.info("TC_15_Step_Invalid nhap so tai khoan nguoi nhan co ky tu tieng viet co dau");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.INVALID_ACC_VIETNAM_KEY, "Thông tin người hưởng", "1");

		log.info("TC_15_Step_Verify chuyen doi thanh tieng viet khong dau");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, transferMoney.removeUnicode(driver, TransferMoneyQuick_Data.TransferQuick.INVALID_ACC_VIETNAM_KEY)));

	}

	@Test
	public void TC_16_TaiKhoanNhanKhongTonTai() {
		log.info("TC_16_Step_Invalid nhap so tai khoan nguoi nhan khong ton tai");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.INVALID_ACC_ACCEPT, "Thông tin người hưởng", "1");

		log.info("TC_16_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_16_Step_verify message khi tai khoan nguoi nhan khong ton tai ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.ACCOUNT_NOT_EXIST));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_17_KhongNhapTaiKhoanNhan() {
		log.info("TC_17_Step_Invalid bo trong tai khoan huong");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.INVALID_ACC_BLANK, "Thông tin người hưởng", "1");

		log.info("TC_17_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_17_Step_verify message khi tai khoan nguoi nhan khong ton tai ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.ACCOUNT_BLANK));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_18_ClickIconDanhBa() {
		log.info("TC_18_click danh ba nguoi thu huong");
		transferMoney.clickToDynamicBottomMenu(driver, "com.VCB:id/ivContent1");

		log.info("TC_18_Lay danh sach gia tri danh ba nguoi huong");
		listActual = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvName");

		log.info("TC_18_danh sach gia tri bank actual");
		listExpect = Arrays.asList(SetupContact_Data.UI.NAME_CARD);

		log.info("TC_18_Verify gia tri tim kiem");
		verifyEquals(listActual, listExpect);

	}

	@Test
	public void TC_19_VerifyDefaultDanhBaThuHuong() {
		log.info("TC_19_verify tieu de danh ba nguoi huong");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Danh bạ người hưởng"));

		log.info("TC_19_verify button dong");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Đóng"));

		log.info("TC_19_verify tim kiem");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Tìm kiếm"));

		log.info("TC_19_Lay danh sach gia tri danh ba nguoi huong");
		listActual = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvName");

		log.info("TC_19_danh sach gia tri bank actual");
		listExpect = Arrays.asList(SetupContact_Data.UI.NAME_CARD);

		log.info("TC_19_Verify gia tri tim kiem");
		verifyEquals(listActual, listExpect);
	}

	@Test
	public void TC_20_TimKiemDanhBaTenThuHuong() {
		log.info("TC_20_Tim kiem theo so tai khoan thu huong");
		transferMoney.inputToDynamicInputBoxSearch(driver, SetupContact_Data.UI.NAME_CARD[0], "Danh bạ người hưởng");

		log.info("TC_20_Lay danh sach gia tri danh ba nguoi huong");
		listActual = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvName");

		log.info("TC_20_danh sach gia tri bank actual");
		listExpect = Arrays.asList(SetupContact_Data.UI.NAME_CARD[0]);

		log.info("TC_20_Verify gia tri tim kiem");
		verifyEquals(listActual, listExpect);
	}

	@Test
	public void TC_21_TimKiemDanhBaSoTaiKhoanThuHuong() {
		log.info("TC_21_Tim kiem theo so tai khoan thu huong");
		transferMoney.inputToDynamicInputBoxSearch(driver, SetupContact_Data.UI.ACCOUNT[0], "Danh bạ người hưởng");

		log.info("TC_21_Lay danh sach gia tri danh ba nguoi huong");
		listActual = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvNo");

		log.info("TC_21_danh sach gia tri bank actual");
		listExpect = Arrays.asList(SetupContact_Data.UI.ACCOUNT[0]);

		log.info("TC_21_Verify gia tri tim kiem");
		verifyEquals(listActual, listExpect);
	}

	@Test
	public void TC_22_TimKiemDanhBaThuHuongKhongTonTai() {
		log.info("TC_22_Tim kiem theo ten nguoi thu huong khong ton tai");
		transferMoney.inputToDynamicInputBoxSearch(driver, SetupContact_Data.UI.NAME_INVALID, "Danh bạ người hưởng");

		log.info("TC_22_verify so tai khoan trong contact");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, TransferMoneyQuick_Data.TransferQuick.NAME_INVALID));

	}

	@Test
	public void TC_23_TimKiemChinhXacMotDanhBaThuHuong() {
		log.info("TC_23_Tim kiem ten nguoi thu huong");
		transferMoney.inputToDynamicInputBoxSearch(driver, SetupContact_Data.UI.NAME_CARD[0], "Danh bạ người hưởng");

		log.info("TC_23_chon 1 ten nguoi thu huong");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, SetupContact_Data.UI.NAME_CARD[0]);

		log.info("TC_23_check tai khoan vua chon ngoai man hinh khoi tao");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, SetupContact_Data.UI.ACCOUNT[0]));

		log.info("TC_23_check ngan hang thu huong");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0]));
	}

	@Test
	public void TC_24_NganHangHuongBoTrong() {
		log.info("TC_24_Step_button back");
		transferMoney.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_24_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_24_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_24_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO, "Thông tin người hưởng", "1");

		log.info("TC_24_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_24_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_24_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_24_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_24_Step_verify message khi khong chon ngan hang huong ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.BANK_BLANK));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_25_ValidationManHinhTimKiemNganHang() {
		log.info("TC_25_click danh sach ngan hang huong");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");

		log.info("TC_25_verify title ngan hang huong");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Ngân hàng hưởng"));

		log.info("TC_25_verify tim kiem");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Tìm kiếm"));
	}

	@Test
	public void TC_26_ValidationTimKiemNganHang() {
		log.info("TC_26_Tim kiem ngan hang theo ten ngan hang, nhap ten ngan hang");
		transferMoney.inputToDynamicInputBoxSearchBank(driver, TransferMoneyQuick_Data.TransferQuick.BANK[2], "Ngân hàng hưởng");

		log.info("TC_26_Lay danh sach gia tri bank");
		listActual = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvContent");

		log.info("TC_26_danh sach gia tri bank actual");
		listExpect = Arrays.asList(TransferMoneyQuick_Data.TransferQuick.BANK[2]);

		log.info("TC_26_Verify gia tri tim kiem");
		verifyEquals(listActual, listExpect);
	}

	@Test
	public void TC_27_NganHangHuongKhongTonTai() {
		log.info("TC_27_Tim kiem ngan hang theo ten ngan hang, nhap ten ngan hang");
		transferMoney.inputToDynamicInputBoxSearchBank(driver, TransferMoneyQuick_Data.TransferQuick.BANK_INVALID, "Ngân hàng hưởng");

		log.info("TC_27_verify ket qua tim kiem");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, TransferMoneyQuick_Data.TransferQuick.BANK_INVALID));
	}

	@Test
	public void TC_28_ChonMotNganHangThuHuong() {
		log.info("TC_28_Tim kiem ngan hang theo ten ngan hang, nhap ten ngan hang");
		transferMoney.inputToDynamicInputBoxSearchBank(driver, TransferMoneyQuick_Data.TransferQuick.BANK[1], "Ngân hàng hưởng");

		log.info("TC_28_Click ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK[1]);

		log.info("TC_28_verify ket qua tim kiem");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.BANK[1]));
	}

	@Test
	public void TC_29_ChonMotNganHangThuHuongKhac() {
		log.info("TC_29_Click ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK[1]);

		log.info("TC_29_Tim kiem ngan hang theo ten ngan hang, nhap ten ngan hang");
		transferMoney.inputToDynamicInputBoxSearchBank(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0], "Ngân hàng hưởng");

		log.info("TC_29_Click ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0]);

		log.info("TC_29_verify ket qua tim kiem");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0]));
	}

}
