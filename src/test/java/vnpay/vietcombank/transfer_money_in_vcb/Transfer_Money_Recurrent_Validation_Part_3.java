package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.TransferInVCBRecurrent;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_Recurrent_Validation_Part_3 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyInVcbPageObject transferRecurrent;
	private HomePageObject homePage;

	TransferInVCBRecurrent info = new TransferInVCBRecurrent(Account_Data.Valid_Account.DEFAULT_ACCOUNT2, Account_Data.Valid_Account.ACCOUNT3, "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "SMS OTP");
	TransferInVCBRecurrent info1 = new TransferInVCBRecurrent(Account_Data.Valid_Account.EUR_ACCOUNT, Account_Data.Valid_Account.ACCOUNT3, "2", "Ngày", "", "", "10", "Người nhận trả", "test", "SMS OTP");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
	}

	@Test
	public void TC_01_ChuyenTienDinhKy_KiemTraGiaTriNgayKetThuc_SauKhiNhapChonTanSuat_BangNgay() {
		String endDate = getForwardDate(1 + Integer.parseInt(TransferMoneyInVCB_Data.InputDataInVCB.NUMBER_DAY_FREQUENCY));
		log.info("TC_01_01_Click Chuyen tien trong ngan hang");
		homePage.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_02_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_01_03_Nhap tan suat");
		transferRecurrent.inputFrequencyNumber(TransferMoneyInVCB_Data.InputDataInVCB.NUMBER_DAY_FREQUENCY);

		log.info("TC_01_04_Kiem tra ngay ket thuc");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, endDate));

	}

	@Test
	public void TC_02_ChuyenTienDinhKy_KiemTraGiaTriNgayKetThuc_SauKhiNhapChonTanSuat_BangTuan() {
		String endDate = getForwardDate(1 + Integer.parseInt(TransferMoneyInVCB_Data.InputDataInVCB.NUMBER_WEEK_FREQUENCY) * 7);

		log.info("TC_02_01_Chon chu ky Tuan");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Tuần");

		log.info("TC_02_02_Nhap tan suat");
		transferRecurrent.inputFrequencyNumber(TransferMoneyInVCB_Data.InputDataInVCB.NUMBER_WEEK_FREQUENCY);

		log.info("TC_02_03_Kiem tra ngay ket thuc");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, endDate));

	}

	@Test
	public void TC_03_ChuyenTienDinhKy_KiemTraGiaTriNgayKetThuc_SauKhiNhapChonTanSuat_BangThang() {
		String endDate = getForwardMonthAndForwardDay(Long.parseLong(TransferMoneyInVCB_Data.InputDataInVCB.NUMBER_MONTH_FREQUENCY), 1);

		log.info("TC_03_01_Chon chu ky Tuan");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Tuần");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Tháng");

		log.info("TC_03_02_Nhap tan suat");
		transferRecurrent.inputFrequencyNumber(TransferMoneyInVCB_Data.InputDataInVCB.NUMBER_MONTH_FREQUENCY);

		log.info("TC_03_03_Kiem tra ngay ket thuc");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, endDate));

	}

	@Test
	public void TC_04_ChuyenTienDinhKy_KiemTraLinkHanMuc() {
		log.info("TC_04_01_Kiem tra link han muc");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Hạn mức"));

		log.info("TC_04_02_Chon link han muc");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Hạn mức");

		log.info("TC_04_03_Kiem tra den trang han muc");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Hạn mức"));

		log.info("TC_04_04_Clicl nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Hạn mức");
	}

	@Test
	public void TC_05_ChuyenTienDinhKy_TextboxSoTien_KiemTraHienThiMacDinh() {
		log.info("TC_05_01_Chon tai khoan nguon VND");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_05_02_Scroll den nut Tiep tuc");
		transferRecurrent.scrollToText(driver, "Tiếp tục");

		String actualAmountMoney = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		log.info("TC_05_03_Kiem tra gia tri trong o So tien");
		verifyEquals(actualAmountMoney, "Số tiền");

		log.info("TC_05_04_Chon tai khoan nguon ngoai te");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		actualAmountMoney = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		log.info("TC_05_05_Kiem tra gia tri trong o So tien");
		verifyEquals(actualAmountMoney, "Số tiền");

	}

	@Test
	public void TC_06_ChuyenTienDinhKy_TextboxSoTien_KiemTraGioiHanNhap_VND() {
		String actualAmountMoney;
		log.info("TC_06_01_Chon tai khoan nguon VND");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_06_02_Nhap ki tu so, chu, ki tu dac biet, dau cham vao o So tien");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, "123456789999", "Thông tin giao dịch", "1");

		log.info("TC_06_03_Kiem tra gioi han nhap");
		actualAmountMoney = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyEquals(actualAmountMoney.replaceAll("\\D+", "").length(), 10);

	}

	@Test
	public void TC_07_ChuyenTienDinhKy_TextboxSoTien_KiemTraGioiHanNhap_NgoaiTe() {
		String actualAmountMoney;
		log.info("TC_07_01_Chon tai khoan nguon Ngoai te");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		log.info("TC_07_02_Nhap ki tu so, chu, ki tu dac biet, dau cham vao o So tien ung ho");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, "1111111111111.11", "Thông tin giao dịch", "1");

		log.info("TC_07_03_Kiem tra gioi han nhap");
		actualAmountMoney = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyEquals(actualAmountMoney.replaceAll("\\D+", "").length(), 12);

		transferRecurrent.inputToDynamicInputBoxByHeader(driver, "", "Thông tin giao dịch", "1");
	}

	@Test
	public void TC_08_ChuyenTienDinhKy_KiemTraGoiYNhanh_LucChuaNhapGiaTri() {
		List<String> listActualAmountMoney = new ArrayList<String>();
		List<String> listExpectAmountMoney = new ArrayList<String>();

		log.info("TC_08_01_Chon tai khoan nguon VND");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_08_02_Focus vao o nhap So tien");
		transferRecurrent.clickToDynamicInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_08_03_Lay danh sach goi y nhanh");
		listActualAmountMoney = transferRecurrent.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");

		listExpectAmountMoney = Arrays.asList("1,000", "10,000", "100,000");

		log.info("TC_08_04_Kiem tra so tien goi y");
		verifyEquals(listActualAmountMoney, listExpectAmountMoney);

		log.info("TC_08_05_Chon tai khoan nguon VND");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		log.info("TC_08_06_Focus lai vao o nhap So tien");
		transferRecurrent.clickToDynamicInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		listExpectAmountMoney = Arrays.asList("10", "100");
	}

	@Test
	public void TC_09_ChuyenTienDinhKy_KiemTraGoiYSoTienVND() {
		List<String> listActualAmountMoney = new ArrayList<String>();
		List<String> listExpectAmountMoney = new ArrayList<String>();
		log.info("TC_09_01_Chon tai khoan nguon VND");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_09_02_Nhap so tien");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, "5000", "Thông tin giao dịch", "1");

		log.info("TC_09_03_Lay danh sach goi y nhanh");
		listActualAmountMoney = transferRecurrent.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");

		listExpectAmountMoney = Arrays.asList("50,000 VND", "500,000 VND", "5,000,000 VND");

		log.info("TC_09_04_Kiem tra so tien goi y");
		verifyEquals(listActualAmountMoney, listExpectAmountMoney);
	}

	@Test
	public void TC_10_ChuyenTienDinhKy_KiemTraGoiYSoTienNgoaiTe() {
		List<String> listActualAmountMoney = new ArrayList<String>();
		List<String> listExpectAmountMoney = new ArrayList<String>();
		log.info("TC_10_01_Chon tai khoan nguon Ngoai te");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		log.info("TC_10_02_Nhap so tien");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, "10", "Thông tin giao dịch", "1");

		log.info("TC_10_03_Lay danh sach goi y nhanh");
		listActualAmountMoney = transferRecurrent.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");

		listExpectAmountMoney = Arrays.asList("100 EUR ~ 2,700,600 VND", "1,000 EUR ~ 27,006,000 VND");

		log.info("TC_10_04_Kiem tra so tien goi y");
		verifyEquals(listActualAmountMoney, listExpectAmountMoney);
	}

	@Test
	public void TC_11_ChuyenTienDinhKy_FocusRaBenNgoai() {
		String actualAmountMoney;

		log.info("TC_11_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_11_02_Nhap so tien");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, "50000", "Thông tin giao dịch", "1");

		log.info("TC_11_03_Focus ra ngoai o nhap So tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_11_04_Kiem tra so tien trong o nhap So tien");
		actualAmountMoney = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyEquals(actualAmountMoney, "50,000");
	}

	@Test
	public void TC_12_ChuyenTienDinhKy_FocusLaiVaoOSoTien() {
		List<String> listActualAmountMoney = new ArrayList<String>();
		List<String> listExpectAmountMoney = new ArrayList<String>();

		log.info("TC_12_01_Focus lai vao o nhap So tien");
		transferRecurrent.clickToDynamicInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		transferRecurrent.scrollToText(driver, "50,000,000 VND");

		log.info("TC_12_02_Lay danh sach goi y nhanh");
		listActualAmountMoney = transferRecurrent.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");

		listExpectAmountMoney = Arrays.asList("500,000 VND", "5,000,000 VND", "50,000,000 VND");

		log.info("TC_12_03_Kiem tra so tien goi y");
		verifyEquals(listActualAmountMoney, listExpectAmountMoney);

	}

	@Test
	public void TC_13_ChuyenTienDinhKy_ChonMotGiaTriGoiY() {
		String actualAmountMoney;
		log.info("TC_13_01_Nhap so tien");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, "5000", "Thông tin giao dịch", "1");

		log.info("TC_13_02_Chon 1 gia tri trong danh sach goi y");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "50,000 VND");

		log.info("TC_13_03_Kiem tra so tien trong o nhap So tien");
		actualAmountMoney = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyEquals(actualAmountMoney, "50,000");

	}

	@Test
	public void TC_14_ChuyenTienDinhKy_ComboPhiGiaoDich_KiemTraHienThiMacDinh() {
		log.info("TC_14_01_Kiem tra hien thi mac dinh Phi giao dich nguoi chuyen tra");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Phí giao dịch người chuyển trả"));

		log.info("TC_14_02_Click chon combo Phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_14_03_Kiem tra Danh sach se kich chon doi voi gia tri duoc chon");
		verifyTrue(transferRecurrent.isDynamicImageViewByTextView(driver, "Người chuyển trả"));

	}

	@Test
	public void TC_15_ChuyenTienDinhKy_ComboPhiGiaoDich_KiemTraDanhSachPhiGiaoDich() {
		List<String> listActual = new ArrayList<String>();
		List<String> listExpect = new ArrayList<String>();

		log.info("TC_15_01_Lay danh sach Phi giao dich");
		listActual = transferRecurrent.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvContent");

		listExpect = Arrays.asList("Người chuyển trả", "Người nhận trả");

		log.info("TC_15_02_Kiem tra danh sach Phi giao dich");
		verifyEquals(listActual, listExpect);

	}

	@Test
	public void TC_16_ChuyenTienDinhKy_ComboPhiGiaoDich_ChonMotLoaiPhi() {
		log.info("TC_16_01_Lay danh sach Phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_16_02_Kiem tra hien thi loai Phi giao dich duoc chon");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Phí giao dịch người nhận trả"));
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
