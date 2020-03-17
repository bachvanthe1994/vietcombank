package vnpay.vietcombank.TransferIdentityCard;

import java.io.IOException;

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
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferIdentiryPageObject;
import vietcombank_test_data.TransferIdentity_Data;

public class TransferIdentity_Validate extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferIdentiryPageObject trasferPage;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;

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

		homePage = PageFactoryManager.getHomePageObject(driver);
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);

	}

	@Test
	public void TC_01_TenNguoiThuHuong() {
		log.info("TC_01_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_01_STEP_1: kiem tra hien thị mac dinh");
		verifyTrue(trasferPage.isDynamicTextInInputBoxDisPlayed(driver, "Tên người thụ hưởng"));

		log.info("TC_01_Step_2 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_02_NhapTenNguoiThuHuong() {
		log.info("TC_02_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_02_STEP_1: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

		log.info("TC_02_STEP_3: Kiem tra ten nguoi huong duoc nhap");
		verifyTrue(trasferPage.isDynamicTextInInputBoxDisPlayed(driver, TransferIdentity_Data.textDataInputForm.USER_NAME));

		log.info("TC_02_Step_4 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_03_KiemTraLoaiKiTuNhapVaGioiHanKyTuNhapTenNguoiThuHuong() {
		log.info("TC_03_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_03_STEP_1: nhap ten nguoi thu huong gom ki tu so");
		trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Tên người thụ hưởng");

		log.info("TC_03_STEP_2: kiem ta hien thi ten nguoi thu huong vua nhap");
		verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "2"), TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER);

		log.info("TC_03_STEP_4: nhap ten nguoi thu huong gom ki tu dac biet");
		trasferPage.inputToDynamicInputBoxByHeader(driver, TransferIdentity_Data.textDataInputForm.SPECIAL_CHARACTERS, "Thông tin người hưởng", "2");

		log.info("TC_03_STEP_5: kiem tra hien thi ten nguoi thu huong vua nhap");
		verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "2"), "Tên người thụ hưởng");

		log.info("TC_03_STEP_7: nhap ten nguoi thu huong gom ki tu co dau");
		trasferPage.inputToDynamicInputBoxByHeader(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM, "Thông tin người hưởng", "2");

		log.info("TC_03_STEP_8: kiem tra hien thi ten nguoi thu huong vua nhap");
		verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "2"), "Xac nhan thong tin");

		log.info("TC_03_STEP_10: kiem tra max length = 100");
		trasferPage.inputToDynamicInputBoxByHeader(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_100, "Thông tin người hưởng", "2");

		log.info("TC_03_STEP_11: kiem tra hien thi ten nguoi thu huong vua nhap");
		verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "2").length(), 100);

		log.info("TC_03_STEP_13: kiem tra max length = 101");
		trasferPage.inputToDynamicInputBoxByHeader(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_101, "Thông tin người hưởng", "2");

		log.info("TC_03_STEP_14: kiem tra hien thi ten nguoi thu huong vua nhap");
		verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "2").length(), 100);

		log.info("TC_03_Step_16 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

	}

	@Test
	public void TC_04_KiemTraMacDinhGiayToTuyThan() {
		log.info("TC_04_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_04_STEP_1: kiem tra hien thị mac dinh");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, "Giấy tờ tùy thân"));

		log.info("TC_02_Step_2 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_05_ChonLoaiGiayToTuyThan() {
		log.info("TC_05_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_05_STEP_5:click chon CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

		log.info("TC_05_STEP_2:kiem tra hien thi khi click chon CMT");

		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, "Chứng minh nhân dân"));

		log.info("TC_05_STEP_3:click chon HC");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

		log.info("TC_05_STEP_4:kiem tra hien thi khi click chon HC");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, "Hộ chiếu"));

		log.info("TC_05_STEP_5:click chon CMTQD");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "CMT Quân đội");

		log.info("TC_05_STEP_6:kiem tra hien thi khi click chon CMTQD");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, "CMT Quân đội"));

		log.info("TC_05_STEP_7:click chon CCCD");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "CMT Quân đội");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ căn cước công dân");

		log.info("TC_05_STEP_8:kiem tra hien thi khi click chon CCCD");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, "Thẻ căn cước công dân"));

		log.info("TC_05_Step_9 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_06_KiemTraHienThiGiayToTuyThan() {
		log.info("TC_06_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_06_STEP_1:click chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");

		log.info("TC_06_STEP_2:kiem tra vi tri thu nhat hien thi trong danh sach giay to ");
		verifyEquals(trasferPage.getTextInDynamicIdentifition(driver, "0", "com.VCB:id/tvContent"), "Chứng minh nhân dân");

		log.info("TC_06_STEP_3:kiem tra vi tri thu nhat hien thi trong danh sach giay to ");
		verifyEquals(trasferPage.getTextInDynamicIdentifition(driver, "1", "com.VCB:id/tvContent"), "Hộ chiếu");

		log.info("TC_06_STEP_4:kiem tra vi tri thu nhat hien thi trong danh sach giay to ");
		verifyEquals(trasferPage.getTextInDynamicIdentifition(driver, "2", "com.VCB:id/tvContent"), "CMT Quân đội");

		log.info("TC_06_STEP_5:kiem tra vi tri thu nhat hien thi trong danh sach giay to ");
		verifyEquals(trasferPage.getTextInDynamicIdentifition(driver, "3", "com.VCB:id/tvContent"), "Thẻ căn cước công dân");

		log.info("TC_06_STEP_6:click chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ căn cước công dân");

		log.info("TC_06_Step_7 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_07_KiemTraMacDinhOSoCMT() {
		log.info("TC_07_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_07_STEP_1: kiem tra hien thị mac dinh");
		verifyTrue(trasferPage.isDynamicTextInInputBoxDisPlayed(driver, "Số"));

		log.info("TC_07_Step_3 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_08_KiemTraNhapSoCMT() {
		log.info("TC_08_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_08_STEP_1: nhap so");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

		log.info("TC_08_STEP_2: lay so vua nhap");
		String numberInput = trasferPage.getDynamicTextInInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		log.info("TC_08_STEP_3: kiem tra hien thi so vua nhap");
		verifyEquals(numberInput, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		log.info("TC_08_Step_4 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_09_KiemTraMaxLengthSoCMT() {
		log.info("TC_09_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_09_STEP_1: nhap maxlength truong so");
		trasferPage.inputToDynamicInputBoxByHeader(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_20_NUM, "Thông tin người hưởng", "4");

		log.info("TC_09_STEP_2: kiem tra nhap = maxlength = 20 ki tu so");
		String numberInputMaxlength = trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "4");
		verifyEquals(numberInputMaxlength, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_20_NUM);

		log.info("TC_09_STEP_3: nhap lon hon maxlength truong so");
		trasferPage.inputToDynamicInputBoxByHeader(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_21_NUM, "Thông tin người hưởng", "4");

		log.info("TC_09_STEP_4: kiem tra nhap = maxlength = 20 ki tu so");
		String numberInput20 = trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "4");
		verifyEquals(numberInput20, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_20_NUM);

		log.info("TC_09_Step_5 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_10_KiemTraSoCMT() {
		log.info("TC_10_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_10_STEP_1: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

		log.info("TC_10_STEP_2: chon giay to tuy than: chung minh thu");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

		log.info("TC_10_STEP_3: kiem tra nhap nho hon 9 ki tu");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_8_NUM, "Số");

		log.info("TC_10_STEP_4: click tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_10_STEP_5: kiem tra thong bao trong popup");
		String confirmMaxlength = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent");
		verifyEquals(confirmMaxlength, TransferIdentity_Data.textDataInputForm.CONFIRM_IDENTITY);

		log.info("TC_10_STEP_6: click dong popup");
		trasferPage.clickToDynamicButton(driver, "Đóng");

		log.info("TC_10_STEP_7: kiem tra nhap lon hon 15 ki tu");
		trasferPage.inputToDynamicInputBoxByHeader(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_16_NUM, "Thông tin người hưởng", "4");

		log.info("TC_10_STEP_8: click tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_10_STEP_9: kiem tra thong bao trong popup");
		String confirmMaxlength15 = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent");
		verifyEquals(confirmMaxlength15, TransferIdentity_Data.textDataInputForm.CONFIRM_IDENTITY);

		log.info("TC_10_STEP_10: click dong popup");
		trasferPage.clickToDynamicButton(driver, "Đóng");

		log.info("TC_10_STEP_11: kiem tra nhap 15 ki tu");
		trasferPage.inputToDynamicInputBoxByHeader(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_15_NUM, "Thông tin người hưởng", "4");

		log.info("TC_10_STEP_12: click tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_10_STEP_13: kiem tra thong bao trong popup");
		String confirm = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent");
		verifyEquals(confirm, "Quý khách vui lòng chọn ngày cấp");

		log.info("TC_10_STEP_14: click dong popup");
		trasferPage.clickToDynamicButton(driver, "Đóng");

		log.info("TC_10_STEP_15: kiem tra nhap 9 ki tu");
		trasferPage.inputToDynamicInputBoxByHeader(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_9_NUM, "Thông tin người hưởng", "4");

		log.info("TC_10_STEP_16: click tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_10_STEP_17: kiem tra thong bao trong popup");
		String confirm1 = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent");
		verifyEquals(confirm1, "Quý khách vui lòng chọn ngày cấp");

		log.info("TC_10_STEP_18: click dong popup");
		trasferPage.clickToDynamicButton(driver, "Đóng");

		log.info("TC_10_Step_19: Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_11_HienThiMacDinhNoiCap() {
		log.info("TC_11_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_11_STEP_1: kiem tra hien thị mac dinh");
		verifyEquals(trasferPage.getDynamicTextInDropDownByHeader(driver, "Thông tin người hưởng", "7"), "Nơi cấp");

		log.info("TC_12_Step_4 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_12_KiemTraNoiCapCMTKhiChonGiayToLaCMT() {
		log.info("TC_12_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_12_STEP_1:click chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");

		log.info("TC_12_STEP_2:click chon CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

		log.info("TC_12_STEP_3:click chon noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");

		log.info("TC_12_STEP_4:click chon Ha Noi");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);

		log.info("TC_12_STEP_5:kiem tra gia tri vua chon");
		verifyEquals(trasferPage.getDynamicTextInDropDownByHeader(driver, "Thông tin người hưởng", "7"), TransferIdentity_Data.textDataInputForm.ISSUED_SPACE);

		log.info("TC_12_Step_6 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_13_KiemTraNoiCapHoChieuKhiChonGiayToLaHoChieu() {
		log.info("TC_13_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_13_STEP_1:click chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");

		log.info("TC_13_STEP_2:click chon HC");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

		log.info("TC_13_STEP_3:click chon Ha Noi");
		trasferPage.inputToDynamicInputBox(driver, "Thành phố Hà Nội", "Nơi cấp");

		log.info("TC_13_STEP_4:kiem tra gia tri vua chon");
		verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "6"), "Thành phố Hà Nội");

		log.info("TC_13_Step_5 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_14_KiemTraNhapKhiChonCMTQuanDoi() {
		log.info("TC_14_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_13_STEP_1:click chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");

		log.info("TC_13_STEP_2:click chon HC");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "CMT Quân đội");

		log.info("TC_13_STEP_3:click chon Ha Noi");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED_SPACE, "Nơi cấp");

		log.info("TC_13_STEP_4:kiem tra gia tri vua chon");
		verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "6"), TransferIdentity_Data.textDataInputForm.ISSUED_SPACE);

		log.info("TC_13_Step_5 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_15_NoiCapCanCuocCongDan() {
		log.info("TC_15_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_15_STEP_1:click chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");

		log.info("TC_15_STEP_2:click chon HC");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ căn cước công dân");

		log.info("TC_15_STEP_3:kiem tra gia tri hien thi o noi cap");
		verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, "Cục CSĐKQL cư trú và DLQG về dân cư"));

		log.info("TC_15_STEP_4:click chon HC");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Cục CSĐKQL cư trú và DLQG về dân cư");

		log.info("TC_15_STEP_5:click chon HC");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Cục CSQLHC về TTXH");

		log.info("TC_15_Step_6 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_16_kiemTraMacDinhSoTien() {
		log.info("TC_16_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_16_STEP_1: kiem tra hien thi mac dinh so tien");
		verifyTrue(trasferPage.isDynamicTextInInputBoxDisPlayed(driver, "Số tiền"));

		log.info("TC_16_Step_5 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
	}

	@Test
	public void TC_17_kiemTraNhapSoTienBatDauBang0() {
		log.info("TC_16_STEP_0: chon chuyển tiền nhận bằng CMT");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_16_STEP_1: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_0, "Số tiền");

		verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1"), "Số tiền");

		log.info("TC_16_Step_5 : Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
