package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_In_Future_Validation_Part1 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private String tomorrow = getForwardDate(1);

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

	}

	@Test
	public void TC_01_KiemTraMaxTaiKhoanDich() {
		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_Step_02:  Chon chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_ACCOUNT_15_CHARACTERS, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_03: Lay do dai tai khoan huong duoc nhap vao");
		int accountNumber = transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "1").length();

		log.info("TC_01_Step_04: Kiem tra so tai khoan huong bi cat bot con 13 ky tu");
		verifyEquals(accountNumber, 13);

		log.info("TC_01_Step_04: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_02_KiemTraTaiKhoanDichNho10KyTu() {
		log.info("TC_02_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_02_Step_02: Chon chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_ACCOUNT_LESS_THAN_10_CHARACTERS, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_02_Step_03: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_02_Step_04: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_02_Step_05: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_06: Kiem tra message loi hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.ERRROR_MESSAGE_FOR_ACCOUNT_LESS_THAN_10_CHARACTER));

		log.info("TC_02_Step_07: Click Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_02_Step_08: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_03_KiemTraTaiKhoanDichNhoHon13KyTuLonHon10KyTu() {
		log.info("TC_03_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_03_Step_02: Chon chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_ACCOUNT_LESS_THAN_13_CHARACTERS, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_03_Step_03: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_03_Step_04: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_03_Step_05: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.ERRROR_MESSAGE_FOR_ACCOUNT_LESS_THAN_13_CHARACTER);

		log.info("TC_03_Step_06: Click Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_02_Step_07: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_04_KiemTraKhiNhapKyTuDacBietVaoTaiKhoanDich() {
		log.info("TC_04_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_04_Step_02:  Chon chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.TEXT_AND_SPECIAL_CHARACTERS, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_04_Step_03:Kiem tra tai khoan khong duoc dien");
		verifyEquals(transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "1"), "Nhập/chọn tài khoản nhận VND");

		log.info("TC_04_Step_04: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_05_KiemTraMacDinhComboxNgayHieuLuc() {

		log.info("TC_05_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_05_Step_03: Chon Chuyen tien ngay tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_05_Step_04: Kiem tra ngay hieu luc la today");
		verifyEquals(transferInVCB.getDynamicTextInDropDownByHeader(driver, "Ngày hiệu lực", "1"), tomorrow);

	}

	@Test
	public void TC_06_KiemTraChonNgayHieuLucCachNgayHienTaiMotNam() {

		log.info("TC_06_Step_01: Click vao o chon ngay hieu luc");
		transferInVCB.clickToDynamicDropdownByHeader(driver, "Ngày hiệu lực", "1");

		log.info("TC_06_Step_02: Click chon nam");
		transferInVCB.clickToDynamicDropdownAndDateTimePicker(driver, "android:id/date_picker_header_year");

		log.info("TC_06_Step_03: Chon 2021");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Integer.parseInt(getCurrentYear()) + 1 + "");

		log.info("TC_06_Step_04: CLick Ok");
		transferInVCB.clickToDynamicButton(driver, "OK");

		log.info("TC_06_Step_05: Kiem tra date do k duoc chon, mac dinh van la ngay mai");
		verifyEquals(transferInVCB.getDynamicTextInDropDownByHeader(driver, "Ngày hiệu lực", "1"), tomorrow);

	}

	@Test
	public void TC_07_KiemTraChonNgayHieuLucTrongQuaKhu() {

		log.info("TC_07_Step_01: Click vao o chon ngay hieu luc");
		transferInVCB.clickToDynamicDropdownByHeader(driver, "Ngày hiệu lực", "1");

		log.info("TC_07_Step_02: Click chon ngay hom qua");
		transferInVCB.clickToDynamicDateInDateTimePicker(driver, getBackWardDay(1));

		log.info("TC_07_Step_04: CLick Ok");
		transferInVCB.clickToDynamicButton(driver, "OK");

		log.info("TC_07_Step_04: Kiem tra date do k duoc chon, mac dinh van la ngay mai");
		verifyEquals(transferInVCB.getDynamicTextInDropDownByHeader(driver, "Ngày hiệu lực", "1"), tomorrow);

		log.info("TC_24_Step_07: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_08_KiemTraMacDinhOSoTien() {

		log.info("TC_08_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_08_Step_02: Chon Chuyen tien ngay tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_08_Step_03: Kiem tra placeholder o so tien va don vi tien te");
		verifyEquals(transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1"), "Số tiền");
		verifyEquals(transferInVCB.getDynamicCurrencyInMoneyTextbox(driver, "Số tiền"), "VND");

		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_08_Step_04: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		verifyEquals(transferInVCB.getDynamicCurrencyInMoneyTextbox(driver, "Số tiền"), "EUR");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_08_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

		verifyEquals(transferInVCB.getDynamicCurrencyInMoneyTextbox(driver, "Số tiền"), "USD");

		log.info("TC_08_Step_06: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_09_KiemTraTiGiaQuyDoi() {
		log.info("TC_09_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_09_Step_02: Chon Chuyen tien ngay tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_09_Step_03:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_09_Step_04: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Tỷ giá quy đổi tham khảo"));
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.EUR_EXCHANGE_RATE));

		log.info("TC_09_Step_05: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_09_Step_06: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_09_Step_07:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_09_Step_08: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Tỷ giá quy đổi tham khảo"));
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.USD_EXCHANGE_RATE));

		log.info("TC_09_Step_04: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_10_KiemTraMaxlengthSoTien() {
		log.info("TC_10_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_10_Step_02: Chon Chuyen tien ngay tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_10_Step_02: Nhap so tien");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_MONEY, "Số tiền");

		log.info("TC_10_Step_03: Lay do dai so tien duoc nhap vao");
		String Money = transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_10_Step_04: Kiem tra so tien bi cat bot con 10 ky tu");
		verifyEquals(Money, "Số tiền");

		log.info("TC_10_Step_04: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_11_KiemTraMaxlengthSoTienTaiKHoanNgoaiTe() {
		log.info("TC_11_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_11_Step_02: Chon Chuyen tien ngay tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_11_Step_02: Click chon tai khoan nguon ");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_11_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

		log.info("TC_11_Step_03: Chon tai khoan dich");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_DECIMAL_MONEY, "Số tiền");

		log.info("TC_11_Step_03: Lay do dai so tien duoc nhap vao");
		String Money = transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_11_Step_04: Kiem tra so tien bi cat bot con 10 ky tu");
		verifyEquals(Money, "Số tiền");

		log.info("TC_11_Step_04: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_12_KiemTraKyTuChoNhap() {
		log.info("TC_12_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_12_Step_02: Chon Chuyen tien ngay tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_12_Step_02: Nhap so tien");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.TEXT_AND_SPECIAL_CHARACTERS, "Số tiền");

		log.info("TC_12_Step_03: Lay do dai so tien duoc nhap vao");
		String Money = transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_12_Step_04: Kiem tra text khong duoc nhap");
		verifyEquals(Money, "Số tiền");

		log.info("TC_12_Step_02: Click chon tai khoan nguon ");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_12_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.EUR_PAYMENT_BY_OTP_FEE, "Thông tin giao dịch", "1");

		log.info("TC_12_Step_03: Lay do dai so tien duoc nhap vao");
		Money = transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_12_Step_04: Kiem tra so tien duoc nhap");
		verifyEquals(Money, TransferMoneyInVCB_Data.InputDataInVCB.EUR_PAYMENT_BY_OTP_FEE);

		log.info("TC_12_Step_04: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_13_KiemTraFocusVaoOSoTien() {
		log.info("TC_13_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_13_Step_02: Chon Chuyen tien ngay tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_13_Step_03: Click vao textbox so tien");
		transferInVCB.clickToDynamicInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_13_Step_04: Kiem tra key board hien thi");
		verifyTrue(transferInVCB.isKeyBoardDisplayed(driver));

		log.info("TC_13_Step_05: Dong key board ");
		transferInVCB.hideKeyBoard(driver);

		log.info("TC_13_Step_06: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_14_KiemTraHienThiGoiYNhanhVoiTaiKhoanVND() {
		log.info("TC_14_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_14_Step_02: Chon Chuyen tien ngay tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_14_Step_03: Nhap so tien");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_14_Step_04: Kiem tra so tien goi y");
		List<String> suggestedMoney = transferInVCB.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");
		verifyEquals(TransferMoneyInVCB_Data.InputDataInVCB.SUGGESTED_VND_MONEY, suggestedMoney);

		log.info("TC_14_Step_05: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_15_KiemTraHienThiGoiYNhanhVoiTaiKhoanEUR() {
		log.info("TC_15_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_15_Step_02: Chon Chuyen tien ngay tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_15_Step_03:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_15_Step_04: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_15_Step_05: Nhap so tien");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");

		log.info("TC_15_Step_06: Kiem tra so tien goi y");
		List<String> suggestedMoney = transferInVCB.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");
		verifyEquals(TransferMoneyInVCB_Data.InputDataInVCB.SUGGESTED_EUR_MONEY, suggestedMoney);

		log.info("TC_15_Step_07: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_16_KiemTraDongDanhSachGoiYSauKhiNhanNutDong() {
		log.info("TC_16_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_16_Step_02: Chon Chuyen tien ngay tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_16_Step_02:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_16_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_16_Step_04: Nhap so tien");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");

		log.info("TC_16_Step_05: Click dong suggest list");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/ivClearInput");

		log.info("TC_16_Step_06: Kiem tra suggest list khong hien thi");
		verifyTrue(transferInVCB.isDynamicSuggestedMoneyUndisplayed(driver, "com.VCB:id/tvAmount"));

		verifyEquals(transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1"), "Số tiền");

		log.info("TC_16_Step_07: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_17_KiemTraDongDanhSachGoiYSauKhiFocusRaBenNgoai() {
		log.info("TC_17_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_16_Step_02: Chon Chuyen tien ngay tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_17_Step_03:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_17_Step_04: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_17_Step_05: Nhap so tien");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");

		log.info("TC_17_Step_06: Click dong suggest list");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_17_Step_07: Kiem tra suggest list khong hien thi");
		verifyTrue(transferInVCB.isDynamicSuggestedMoneyUndisplayed(driver, "com.VCB:id/tvAmount"));

		verifyEquals(transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1"), TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER);

		log.info("TC_17_Step_08: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_18_KiemTraDongDanhSachGoiYSauKhiFocusVaoSoTienLan2() {
		log.info("TC_18_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_18_Step_02: Chon Chuyen tien ngay tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_18_Step_03:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_18_Step_04: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_18_Step_05: Nhap so tien");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");

		log.info("TC_18_Step_06: Focus ra ngoai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_18_Step_07:Focus lai vao o so tien");
		transferInVCB.clickToDynamicInput(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER);
		transferInVCB.sleep(driver, 1000);
		transferInVCB.hideKeyBoard(driver);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_18_Step_08: Kiem tra suggest list hien thi");
		verifyFailure(transferInVCB.isDynamicSuggestedMoneyUndisplayed(driver, "com.VCB:id/tvAmount"));

		verifyEquals(transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1"), TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER);

		log.info("TC_18_Step_09: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_19_KiemTraDongDanhSachGoiYSauKhiChonGiaTriSuggest() {
		log.info("TC_19_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_16_Step_02: Chon Chuyen tien ngay tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_19_Step_03:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_19_Step_04: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_19_Step_05: Nhap so tien");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_19_Step_06: Click so tien goi y");
		List<String> suggestedMoney = transferInVCB.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");

		log.info("TC_19_Step_07: Click dong suggest list");
		transferInVCB.clickToDynamicSuggestedMoney(driver, 0, "com.VCB:id/tvAmount");

		log.info("TC_19_Step_08: Kiem tra suggest list khong hien thi");
		verifyTrue(transferInVCB.isDynamicSuggestedMoneyUndisplayed(driver, "com.VCB:id/tvAmount"));

		verifyEquals(transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1"), suggestedMoney.get(0).replaceAll(" VND", ""));

		log.info("TC_19_Step_09: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
