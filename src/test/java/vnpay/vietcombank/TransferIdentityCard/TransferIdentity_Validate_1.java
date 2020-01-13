package vnpay.vietcombank.TransferIdentityCard;

import java.io.IOException;
import java.util.List;

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
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Account_Data.Valid_Account;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferIdentity_Data;

public class TransferIdentity_Validate_1 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferIdentiryPageObject trasferPage;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;

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
	public void TC_18_KiemTraSoTienGiaoDichNhoHonHanMucGiaoDich() {
		log.info("TC_18_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_18_Step_1: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

		log.info("TC_18_Step_2: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

		log.info("TC_18_Step_3: so CMT");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

		log.info("TC_18_Step_4: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");

		log.info("TC_18_Step_5: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);

		log.info("TC_18_STEP_6: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MIN_TRANSFER, "Số tiền");

		log.info("TC_18_Step_7: noi dung");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

		log.info("TC_18_STEP_8: chon tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_18_STEP_9: kiem tra thong bao khi nhap so tien nho hơn han muc nho nhat cua 1 lan giao dich");
		verifyEquals(trasferPage.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.textDataInputForm.CONFIRM_MIN_TRANSFER);

		log.info("TC_18_STEP_10: chon tiep tuc");
		login.clickToDynamicButton(driver, "Đóng");

		log.info("TC_18_Step_11: Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_19_KiemTraSoTienGiaoDichLonHonHanMucGiaoDich() {
		log.info("TC_19_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_19_STEP_1: chon tai khoan nguon");
		trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.VND_ACCOUNT);

		log.info("TC_19_Step_2: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

		log.info("TC_19_Step_3: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

		log.info("TC_19_Step_4: so CMT");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

		log.info("TC_19_Step_5: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");

		log.info("TC_19_Step_6: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);

		log.info("TC_19_STEP_7: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MAX_TRANSFER, "Số tiền");

		log.info("TC_19_Step_8: noi dung");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

		log.info("TC_19_STEP_9: chon tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_19_STEP_10: kiem tra thong bao khi nhap so tien nho hơn han muc nho nhat cua 1 lan giao dich");
		verifyEquals(trasferPage.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.textDataInputForm.CONFIRM_MAX_TRANSFER);

		log.info("TC_19_STEP_11: chon tiep tuc");
		login.clickToDynamicButton(driver, "Đóng");

		log.info("TC_19_Step_12: Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_20_KiemTraMaxlength10VaKhongHienThi0CuoiSoTien() {
		log.info("TC_01_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_20_Step_01: Nhap so tien");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.INVALID_MONEY, "Số tiền");

		log.info("TC_20_Step_02: Lay do dai so tien duoc nhap vao");
		String Money = trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		String money = Money.replaceAll(",", "");

		log.info("TC_20_Step_03: Kiem tra so tien bi cat bot con 10 ky tu");
		verifyEquals(money, "Số tiền");

		log.info("TC_20_Step_04: Dien so tien bang 10 ky tu");
		trasferPage.inputToDynamicInputBoxByHeader(driver, "100000000", "Thông tin giao dịch", "1");

		log.info("TC_20_Step_05: Lay do dai so tien duoc nhap vao");
		String Money1 = trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		String money1 = Money1.replaceAll(",", "");

		log.info("TC_20_Step_06: Kiem tra so tien bi cat bot con 10 ky tu");
		verifyEquals(money1, "100000000");

		log.info("TC_20_Step_07: Click quay lai");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_21_KiemTraMaxlength13SoTien() {
		log.info("TC_21_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_21_STEP_1: chon tai khoan nguon ngoai te");
		trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.USD_ACCOUNT);

		log.info("TC_08_Step_02: Nhap so tien");
		trasferPage.inputToDynamicInputBoxByHeader(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_13_MONEY, "Thông tin giao dịch", "1");

		log.info("TC_08_Step_03: Lay do dai so tien duoc nhap vao");
		String Money = trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		String money = Money.replaceAll(",", "");

		log.info("TC_08_Step_04: Kiem tra ki tu vua nhap");
		verifyEquals(money, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_13_MONEY);

		log.info("TC_03_Step_05: Click quay lai");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_22_KiemTraDinhDangSoTienNhap() {
		log.info("TC_22_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_22_STEP_01: chon tai khoan nguon ngoai te");
		trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.EUR_ACCOUNT);

		log.info("TC_22_Step_02: Nhap so tien");
		trasferPage.inputToDynamicInputBoxByHeader(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Thông tin giao dịch", "1");

		log.info("TC_22_Step_03: Lay do dai so tien duoc nhap vao");
		String Money = trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_22_Step_04: Kiem tra dinh dang");
		verifyEquals(Money, "1,000,000");

		log.info("TC_22_Step_05: Click quay lai");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_23_KiemTraGoiYNhanhSoTienVND() {
		log.info("TC_23_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_22_STEP_01: chon tai khoan nguon VND");
		trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.VND_ACCOUNT);

		log.info("TC_23_Step_01: Nhap so tien");
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");

		log.info("TC_23_Step_03: Kiem tra so tien goi y");
		List<String> suggestedMoney = trasferPage.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");
		verifyEquals(TransferIdentity_Data.textDataInputForm.SUGGESTED_VND_MONEY, suggestedMoney);

		log.info("TC_03_Step_04: Click quay lai");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_24_KiemTraGoiYNhanhSoTienEUR() {
		log.info("TC_24_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_24_Step_01:Click tai khoan nguon");
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
		trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_24_Step_02: Chon tai khoan dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.EUR_ACCOUNT);

		log.info("TC_24_Step_03: Nhap so tien");
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR, "Số tiền");

		log.info("TC_24_Step_04: Kiem tra so tien goi y");
		List<String> suggestedMoney = trasferPage.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");
		verifyEquals(TransferIdentity_Data.textDataInputForm.SUGGESTED_EUR_MONEY, suggestedMoney);

		log.info("TC_024_Step_05: Click quay lai");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_25_KiemTraDongDanhSachGoiYSauKhiFocusRaBenNgoai() {
		log.info("TC_25_Step_01: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_25_Step_02:Click tai khoan nguon");
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
		trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_25_Step_03: Chon tai khoan dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.EUR_ACCOUNT);

		log.info("TC_25_Step_02: Nhap so tien");
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR, "Số tiền");

		log.info("TC_25_Step_02: Click dong suggest list");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_25_Step_02: Kiem tra suggest list khong hien thi");
		verifyTrue(trasferPage.isDynamicSuggestedMoneyUndisplayed(driver, "com.VCB:id/tvAmount"));

		verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1"), TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR);

		log.info("TC_25_Step_04: Click quay lai");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_26_KiemTraDongDanhSachGoiYSauKhiFocusVaoSoTienLan2() {
		log.info("TC_26_Step_01: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_26_Step_02:Click tai khoan nguon");
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
		trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_26_Step_03: Chon tai khoan dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.EUR_ACCOUNT);

		log.info("TC_26_Step_04: Nhap so tien");
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR, "Số tiền");

		log.info("TC_26_Step_05: Focus ra ngoai");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_26_Step_06:Focus lai vao o so tien");
		trasferPage.clickToDynamicInput(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR);
		trasferPage.sleep(driver, 1000);
		trasferPage.hideKeyBoard(driver);
		trasferPage.sleep(driver, 1000);

		log.info("TC_26_Step_07: Kiem tra suggest list hien thi");
		verifyFailure(trasferPage.isDynamicSuggestedMoneyUndisplayed(driver, "com.VCB:id/tvAmount"));

		verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1"), TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR);

		log.info("TC_26_Step_08: Click quay lai");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

	}

	@Test
	public void TC_27_KiemTraDongDanhSachGoiYSauKhiChonGiaTriSuggest() {
		log.info("TC_27_Step_01: Click Chuyen tien trong VCB");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_27_Step_02:Click tai khoan nguon");
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
		trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_27_Step_03: Chon tai khoan dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.VND_ACCOUNT);

		log.info("TC_27_Step_04: Nhap so tien");
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");

		log.info("TC_27_Step_05: Click so tien goi y");
		List<String> suggestedMoney = trasferPage.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");

		log.info("TC_27_Step_06: Click dong suggest list");
		trasferPage.clickToDynamicSuggestedMoney(driver, 0, "com.VCB:id/tvAmount");

		log.info("TC_26_Step_07: Focus ra ngoai");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_27_Step_08: Kiem tra suggest list khong hien thi");
		verifyTrue(trasferPage.isDynamicSuggestedMoneyUndisplayed(driver, "com.VCB:id/tvAmount"));

		verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1"), suggestedMoney.get(0).replaceAll(" VND", ""));

		log.info("TC_27_Step_09: Click quay lai");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

	}

	@Test
	public void TC_28_KiemTraMacDinhPhiGiaoDich() {
		log.info("TC_28_Step_01: Click Chuyen tien trong VCB");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_28_STEP_02: kiem tra hien thị mac dinh");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, "Phí giao dịch người chuyển trả"));

		log.info("TC_28_Step_03: Click quay lai");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_29_KiemTraDanhSachPhiGiaoDich() {
		log.info("TC_29_Step_01: Click Chuyen tien trong VCB");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_29_STEP_02: kiem tra hien thi nguoi chuyen tra");
		trasferPage.scrollToText(driver, "Tiếp tục");
		verifyEquals(trasferPage.getDynamicTextInDropDownByHeader(driver, "Thông tin giao dịch", "2"), "Phí giao dịch người chuyển trả");

		log.info("TC_29_STEP_03: chon phi giao dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_29_STEP_04: kiem tra hien thi nguoi nhan tra");
		verifyEquals(trasferPage.getDynamicTextInDropDownByHeader(driver, "Thông tin giao dịch", "2"), "Phí giao dịch người nhận trả");

		log.info("TC_29_Step_05: Click quay lai");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_30_KiemTraChọnMotLoaiPhiGiaoDichVaKiemTraHienThi() {
		log.info("TC_30_Step_01: Click Chuyen tien trong VCB");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_30_STEP_02: kiem tra hien thị mac dinh");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_30_STEP_03: chon phi giao dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_28_STEP_05: kiem tra hien thị");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, "Phí giao dịch người nhận trả"));

		log.info("TC_30_Step_06: Click quay lai");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_31_KiemTraMacDinhNoiDung() {
		log.info("TC_31_Step_01: Click Chuyen tien trong VCB");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_31_STEP_02: kiem tra hien thi mac dinh");
		trasferPage.scrollToText(driver, "Nội dung");
		verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3"), "Nội dung");

		log.info("TC_31_Step_03: Click quay lai");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_32_KiemTraHienThiNoiDungSauKhiNhap() {
		log.info("TC_31_Step_01: Click Chuyen tien trong VCB");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_31_STEP_02: nhap noi dung");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textCheckElement.PAGE_TRANSFER, "Nội dung");

		log.info("TC_31_STEP_03: kiem tra hien thi noi dung vua nhap");
		verifyTrue(trasferPage.isDynamicTextInInputBoxDisPlayed(driver, "Chuyen tien cho nguoi nhan tai quay"));

		log.info("TC_31_Step_04: Click quay lai");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
