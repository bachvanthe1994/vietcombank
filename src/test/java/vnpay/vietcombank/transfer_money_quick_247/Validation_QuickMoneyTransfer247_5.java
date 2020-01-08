package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Validation_QuickMoneyTransfer247_5 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyObject transferMoney;
	List<String> listExpect;
	List<String> listActual;
	private String Note;
	private String costTranferString;

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

		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");
	}

	@Test
	public void TC_64_KiemTraLabelLinkHanMuc() {
		log.info("TC_58_Check text han muc");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Hạn mức"));

		log.info("TC_58_Click link han muc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Hạn mức");

		log.info("TC_58_Check mot phan noi dung han muc");
		transferMoney.isDynamicTextInfoDisplayed(driver, "Hạn mức giao dịch áp dụng cho các chức năng chuyển tiền:");

		log.info("TC_58_Quay lai man hinh chuyen tien nhanh 24/7");
		transferMoney.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_65_KiemTraHienThiTextNoiDung() {
		log.info("TC_059_Kiem tra label noi dung");
		verifyEquals(transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3"), "Nội dung");
	}

	 @Test
	public void TC_66_KiemTraGiaTriChoPhepNoiDung() {
		log.info("TC_059_Nhap 2 ky tu cho phep");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_2_CHAR, "Thông tin giao dịch", "3");

		log.info("TC_59_text mac dinh 'noi dung' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Nội dung"));

		log.info("TC_59_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");

		log.info("TC_59_Verify text noi dung");
		verifyEquals(Note, TransferMoneyQuick_Data.TransferQuick.NOTE_2_CHAR);

		log.info("TC_059_Nhap 140 ky tu cho phep");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_140_CHAR, "Thông tin giao dịch", "3");

		log.info("TC_59_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");

		log.info("TC_59_Verify text noi dung");
		verifyEquals(Note, TransferMoneyQuick_Data.TransferQuick.NOTE_140_CHAR);

		log.info("TC_059_Nhap tieng viet co dau");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_VIETNAM_KEY_INVALID, "Thông tin giao dịch", "3");

		log.info("TC_59_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");

		log.info("TC_59_Verify text noi dung");
		verifyEquals(Note, transferMoney.removeUnicode(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_VIETNAM_KEY_INVALID));
	}

	@Test
	public void TC_67_KiemTraNhap1KyTuVaoNoiDung() {
		log.info("TC_059_Nhap 1 ky tu cho phep");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_1_CHAR_VIETNAM, "Thông tin giao dịch", "3");

		log.info("TC_59_text mac dinh 'noi dung' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Nội dung"));

		log.info("TC_59_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");

		log.info("TC_59_Verify text khong cho phep nhap chu tieng viet co dau");
		verifyEquals(Note, transferMoney.removeUnicode(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_1_CHAR_VIETNAM));
	}

	 @Test
	public void TC_68_KiemTraNhap140KyTuVaoNoiDung() {
		log.info("TC_059_Nhap 1 ky tu cho phep");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_140_CHAR_VIETNAM, "Thông tin giao dịch", "3");

		log.info("TC_59_text mac dinh 'noi dung' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Nội dung"));

		log.info("TC_59_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");

		log.info("TC_59_Verify text khong cho phep nhap chu tieng viet co dau");
		verifyEquals(Note, transferMoney.removeUnicode(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_140_CHAR_VIETNAM));
	}

	 @Test
	public void TC_69_KiemTraNhap141KyTuVaoNoiDung() {
		log.info("TC_059_Nhap 1 ky tu cho phep");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_141_CHAR_INVALID, "Thông tin giao dịch", "3");

		log.info("TC_59_text mac dinh 'noi dung' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Nội dung"));

		log.info("TC_63_Lay do dai ky tu text nhap vao");
		int textNumber = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3").length();

		log.info("TC_02_Step_04: Kiem tra so tai khoan huong bi cat bot con 140 ky tu");
		verifyEquals(textNumber, 140);
	}

	 @Test
	public void TC_70_KiemTraNhapKyTuDacBietVaSo() {
		log.info("TC_059_Nhap ky tu dac biet");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_SPECAL_CHAR, "Thông tin giao dịch", "3");

		log.info("TC_59_text mac dinh 'noi dung' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Nội dung"));

		log.info("TC_59_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");

		log.info("TC_59_Verify text noi dung");
		verifyEquals(Note, TransferMoneyQuick_Data.TransferQuick.NOTE_SPECAL_CHAR);

		log.info("TC_059_Nhap ky tu so");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_NUMBER_CHAR, "Thông tin giao dịch", "3");

		log.info("TC_59_text mac dinh 'noi dung' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Nội dung"));

		log.info("TC_59_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");

		log.info("TC_59_Verify text noi dung");
		verifyEquals(Note, TransferMoneyQuick_Data.TransferQuick.NOTE_NUMBER_CHAR);
	}

	@Test
	public void TC_71_BoTrongTruongNoiDung() {
		log.info("TC_06_Clear text truong nhap so tien");
		transferMoney.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivClearInput2");

		log.info("TC_06_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Thông tin giao dịch", "1");

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_verify message khi tai khoan nguoi nhan trong ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.NOTE_BLANK));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");

		log.info("Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");
	}

	@Test
	public void TC_72_CheckDefaultComboboxPhiGiaoDich() {
		log.info("TC_059_Kiem tra gia tri mac dinh hien thi la so tien phi nguoi chuyen tra");
		verifyEquals(transferMoney.getTextInDynamicFee(driver, "Thông tin giao dịch", "2"), "Phí giao dịch người chuyển trả");
	}

	@Test
	public void TC_73_ChonPhiGiaoDichKhacDefaultVaKiemTraGiaTriSauChon() {
		log.info("TC_01_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_059_Kiem tra gia tri mac dinh hien thi la so tien phi nguoi nhan tra");
		verifyEquals(transferMoney.getTextInDynamicFee(driver, "Thông tin giao dịch", "2"), "Phí giao dịch người nhận trả");

		log.info("TC_059_Verify tick chon nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[1]);
		verifyTrue(transferMoney.isDynamicImageSelect(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]));
	}

	@Test
	public void TC_74_ChonPhiGiaoDichNguoiChuyenVaKiemTraPhiChuyen() {
		log.info("TC_01_Step_Kiem tra gia tri trong combobox");
		listActual = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvContent");

		log.info("TC_18_danh sach phi giao dich actual");
		listExpect = Arrays.asList(TransferMoneyQuick_Data.TransferQuick.COST_SUB);

		log.info("TC_18_Verify gia tri tim kiem");
		verifyTrue(transferMoney.checkListContain(listActual, listExpect));

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_01_Step_Verify so tien phi");
		verifyEquals(costTranferString, TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT);

		log.info("TC_01_Step_Verify phi giao dich nguoi chuyen tra");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]));
	}
	
	@Test
	public void TC_75_ChonPhiGiaoDichNguoiNhanVaKiemTraPhiChuyen() {
		log.info("TC_58_Quay lai man hinh chuyen tien nhanh 24/7");
		transferMoney.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_01_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		
		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_01_Step_Verify so tien phi");
		verifyEquals(costTranferString, TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT);

		log.info("TC_01_Step_Verify phi giao dich nguoi chuyen tra");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]));
	}
	
	@Test
	public void TC_76_ChonTaiKhoanNguonKhongDuSoDuVND() {
		log.info("TC_58_Quay lai man hinh chuyen tien nhanh 24/7");
		transferMoney.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("Input so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_VND, "Thông tin giao dịch", "1");
		
		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_Step_verify message khi so tien chuyen vuot qua so du trong tai khoan ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.AMOUNT_NOT_ENOUGH));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}
	
	@Test
	public void TC_77_ChonTaiKhoanNguonKhongDuSoDuEUR() {
		log.info("TC_06_Chon tai khoan nguon VND");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[2]);
		
		log.info("Input so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_USD_EUR, "Thông tin giao dịch", "1");
		
		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_Step_verify message khi so tien chuyen vuot qua so du trong tai khoan ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.AMOUNT_NOT_ENOUGH));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}
	
	@Test
	public void TC_78_ChonTaiKhoanNguonKhongDuSoDuUSD() {
		log.info("TC_06_Chon tai khoan nguon VND");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[1]);
		
		log.info("Input so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_USD_EUR, "Thông tin giao dịch", "1");
		
		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_Step_verify message khi so tien chuyen vuot qua so du trong tai khoan ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.AMOUNT_NOT_ENOUGH));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}
	
	@Test
	public void TC_79_ChonTaiKhoanDongChuSoHuu() {
		log.info("TC_06_Chon tai khoan nguon VND");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[5]);
		
		log.info("Input so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_VND, "Thông tin giao dịch", "1");
		
		log.info("TC_01_Step_Nhap so tai khoan chuyen dong chu so huu voi tai khoan nguon");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.SAME_OWNER_ACCOUNT, "Thông tin người hưởng", "1");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);
		
		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_Step_verify message khi tai khoan dong chu so huu ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.SAME_ACCOUNT_NAME));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}
	
	@Test
	public void TC_80_ChonTaiKhoanHuongKhongHopLe() {
		log.info("TC_06_Chon tai khoan nguon VND");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);
		
		log.info("Input so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_VND, "Thông tin giao dịch", "1");
		
		log.info("TC_01_Step_Nhap so tai khoan chuyen dong chu so huu voi tai khoan nguon");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO_INVALID, "Thông tin người hưởng", "1");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);
		
		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_Step_verify message khi tai khoan dong chu so huu ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.ACCOUNT_TO_INVALID_MESSAGE));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}
	
	@Test
	public void TC_81_NhapHopLeThongTinChuyenTienNhanh() {
		log.info("TC_01_Step_Nhap so tai khoan chuyen dong chu so huu voi tai khoan nguon");
		transferMoney.inputToDynamicInputBoxByHeader(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Thông tin người hưởng", "1");

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_verify man xac nhan thong tin khi nhap so tien hop le");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Xác nhận thông tin"));
	}
	
}
