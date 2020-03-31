package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;
import java.util.Arrays;
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
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_Recurrent_Validation_Part_2 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private TransferMoneyInVcbPageObject transferRecurrent;
	private HomePageObject homePage;

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
	}

	@Test
	public void TC_01_ChuyenTienDinhKy_KiemTraNhomThongTinTanSuat() {
		transferRecurrent = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_01_Click Chuyen tien trong ngan hang");
		homePage.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_02_Kiem tra chua xuat hien nhom thong tin Tan suat");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextUndisplayed(driver, "Tần suất"));

		log.info("TC_01_03_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_01_04_Scroll den nut Tiep tuc");
		transferRecurrent.scrollDownToButton(driver, "Tiếp tục");

		log.info("TC_01_05_Kiem tra xuat hien nhom thong Tan suat");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Tần suất"));

		log.info("TC_01_06_Kiem tra hien thi tan suat");
		verifyTrue(transferRecurrent.isDynamicTextInInputBoxDisPlayed(driver, "1"));

		log.info("TC_01_07_Kiem tra hien thi don vi");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Ngày"));

		String startDate = getForwardDate(1);
		String endDate = getForwardDate(2);
		log.info("TC_01_08_Kiem tra hien thi ngay bat dau");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, startDate));

		log.info("TC_01_09_Kiem tra hien thi ngay ket thuc");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, endDate));

		String actualString = transferRecurrent.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvName") + transferRecurrent.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvValue");
		log.info("TC_01_10_Kiem tra hien thi so lan giao dich");
		verifyTrue(actualString.contains(TransferMoneyInVCB_Data.InputDataInVCB.NUMBER_TRANSACTION));

	}

	@Test
	public void TC_02_ChuyenTienDinhKy_KiemTraDanhSachChuKy() {
		log.info("TC_02_01_Mo danh sach chu ky");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");

		List<String> actualList = transferRecurrent.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvContent");

		log.info("TC_02_02_danh sach gia tri chu ky");
		List<String> expectList = Arrays.asList("Ngày", "Tuần", "Tháng");

		log.info("TC_02_03_Kiem tra danh sach chi ky");
		verifyEquals(actualList, expectList);

		log.info("TC_02_01_Chon chu ky Ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
	}

	@Test
	public void TC_03_ChuyenTienDinhKy_KiemTraKyTuNhapOSoLuong() {
		log.info("TC_03_01_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("22");

		String actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_03_2_Kiem tra gia tri trong o So luong");
		verifyEquals(actualAmount, "22");

	}

	@Test
	public void TC_04_ChuyenTienDinhKy_KiemTraNhapKyTu0() {
		log.info("TC_04_01_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("0");

		String actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_04_2_Kiem tra gia tri trong o So luong");
		verifyEquals(actualAmount, "");

	}

	@Test
	public void TC_05_ChuyenTienDinhKy_KiemTraNhapSoLuongKhiDonviLaNgay_NhapNhoHon31() {
		log.info("TC_05_01_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("30");

		String actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_05_02_Kiem tra gia tri trong o So luong");
		verifyEquals(actualAmount, "30");

		log.info("TC_05_03_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("31");

		actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_05_04_Kiem tra gia tri trong o So luong");
		verifyEquals(actualAmount, "31");
	}

	@Test
	public void TC_06_ChuyenTienDinhKy_KiemTraNhapSoLuongKhiDonviLaNgay_NhapLonHon31() {
		log.info("TC_06_01_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("32");

		String actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_06_02_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmount, "");

	}

	@Test
	public void TC_07_ChuyenTienDinhKy_KiemTraNhapSoLuongKhiDonviLaTuan_NhapNhoHon53() {
		log.info("TC_07_01_Chon chu ky Tuan");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Tuần");

		log.info("TC_07_02_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("52");

		String actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_07_03_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmount, "52");

		log.info("TC_07_04_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("53");

		actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_07_5_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmount, "53");

	}

	@Test
	public void TC_08_ChuyenTienDinhKy_KiemTraNhapSoLuongKhiDonviLaTuan_NhapLonHon53() {
		log.info("TC_08_01_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("54");

		String actualAmountMoney = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_08_2_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmountMoney, "");

	}

	@Test
	public void TC_09_ChuyenTienDinhKy_KiemTraNhapSoLuongKhiDonviLaThang_NhapNhoHon12() {
		log.info("TC_09_01_Chon chu ky Ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Tuần");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Tháng");

		log.info("TC_09_02_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("11");

		String actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_09_03_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmount, "11");

		log.info("TC_09_04_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("12");

		actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_09_5_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmount, "12");

	}

	@Test
	public void TC_10_ChuyenTienDinhKy_KiemTraNhapSoLuongKhiDonviLaThang_NhapLonHon12() {
		log.info("TC_10_01_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("13");

		String actualAmountMoney = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_10_2_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmountMoney, "");

	}

	@Test
	public void TC_11_ChuyenTienDinhKy_ComboNgayBatDau_KiemTraHienThiMacDinh() {
		String startDate = getForwardDate(1);
		log.info("TC_11_01_Kiem tra hien thi ngay bat dau");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, startDate));

	}

	@Test
	public void TC_12_ChuyenTienDinhKy_ComboNgayBatDau_KiemTraChonNgayBatDau_HonNgayHienTai_1Nam() {
		String startDate;
		startDate = getForwardDate(1);

		log.info("TC_12_01_Chon Ngay bat dau");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, startDate);

		startDate = getForwardYear(1);

		log.info("TC_12_02_Chon Ngay nam sau");
		transferRecurrent.chooseDateNextYearInDatePicker("android:id/month_view", startDate.split("/")[2]);

		int chooseDate = Integer.parseInt(startDate.split("/")[0]) - 1;
		log.info("TC_12_03_Kiem tra cho phep chon");
		verifyTrue(transferRecurrent.checkDateNextYearEnable("android:id/month_view", chooseDate));

	}

	@Test
	public void TC_13_ChuyenTienDinhKy_ComboNgayBatDau_KiemTraChonNgayBatDau_HonNgayHienTai_LonHon1Nam() {
		String startDate;
		startDate = getForwardYear(1);
		int chooseDate = Integer.parseInt(startDate.split("/")[0]);
		log.info("TC_13_01_Kiem tra khong cho phep chon");
		verifyFailure(transferRecurrent.checkDateNextYearEnable("android:id/month_view", chooseDate));

	}

	@Test
	public void TC_14_ChuyenTienDinhKy_ComboNgayBatDau_KiemTraChonNgayBatDau_LaNgayQuaKhu() {
		String startDate;
		startDate = getForwardDate(1);

		log.info("TC_14_01_Click Huy");
		login.clickToDynamicAcceptButton(driver, "android:id/button2");

		log.info("TC_14_02_Click Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_14_03_Click Chuyen tien trong ngan hang");
		homePage.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_14_04_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_14_05_Scroll den nut Tiep tuc");
		transferRecurrent.scrollDownToButton(driver, "Tiếp tục");

		log.info("TC_14_06_Chon Ngay bat dau");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, startDate);

		startDate = getForwardYear(1);
		int chooseDate = Integer.parseInt(startDate.split("/")[0]) - 2;
		log.info("TC_14_07_Kiem tra khong cho phep chon");
		verifyFailure(transferRecurrent.checkDateNextYearEnable("android:id/month_view", chooseDate));

		log.info("TC_14_08_Click Huy");
		login.clickToDynamicAcceptButton(driver, "android:id/button2");

		log.info("TC_14_09_Click Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_14_10_Click Chuyen tien trong ngan hang");
		homePage.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_14_11_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_14_12_Scroll den nut Tiep tuc");
		transferRecurrent.scrollDownToButton(driver, "Tiếp tục");

	}

	@Test
	public void TC_15_ChuyenTienDinhKy_ComboNgayKetThuc_KiemTraHienThiMacDinh() {
		String endDate = getForwardDate(2);
		log.info("TC_15_01_Kiem tra hien thi ngay bat dau");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, endDate));

	}

	@Test
	public void TC_16_ChuyenTienDinhKy_ComboNgayKetThuc_KiemTraChonNgayKetThuc_HonNgayHienTai_1Nam() {
		String endDate;
		endDate = getForwardDate(2);

		log.info("TC_16_01_Chon Ngay bat dau");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, endDate);

		endDate = getForwardYear(1);

		log.info("TC_16_02_Chon Ngay nam sau");
		transferRecurrent.chooseDateNextYearInDatePicker("android:id/month_view", endDate.split("/")[2]);

		int chooseDate = Integer.parseInt(endDate.split("/")[0]) - 1;
		log.info("TC_16_03_Kiem tra cho phep chon");
		verifyTrue(transferRecurrent.checkDateNextYearEnable("android:id/month_view", chooseDate));

	}

	@Test
	public void TC_17_ChuyenTienDinhKy_ComboNgayKetThuc_KiemTraChonNgayKetThuc_HonNgayHienTai_LonHon1Nam() {
		String endDate;
		endDate = getForwardYear(1);
		int chooseDate = Integer.parseInt(endDate.split("/")[0]);
		log.info("TC_17_01_Kiem tra khong cho phep chon");
		verifyFailure(transferRecurrent.checkDateNextYearEnable("android:id/month_view", chooseDate));

	}

	@Test
	public void TC_18_ChuyenTienDinhKy_ComboNgayBatDau_KiemTraKhongChonNgayKetThuc_LaNgayQuaKhu() {
		String endDate;
		endDate = getForwardDate(2);

		log.info("TC_18_01_Click Huy");
		login.clickToDynamicAcceptButton(driver, "android:id/button2");

		log.info("TC_18_02_Click Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_18_03_Click Chuyen tien trong ngan hang");
		homePage.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_18_04_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_18_05_Scroll den nut Tiep tuc");
		transferRecurrent.scrollDownToButton(driver, "Tiếp tục");

		log.info("TC_18_06_Chon Ngay ket thuc");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, endDate);

		endDate = getForwardYear(1);
		int chooseDate = Integer.parseInt(endDate.split("/")[0]) - 2;
		log.info("TC_18_07_Kiem tra khong cho phep chon");
		verifyFailure(transferRecurrent.checkDateNextYearEnable("android:id/month_view", chooseDate));

		log.info("TC_18_08_Click Huy");
		login.clickToDynamicAcceptButton(driver, "android:id/button2");

	}

	@Test
	public void TC_19_ChuyenTienDinhKy_ComboNgayKetThuc_KiemTraChonNgayKetThuc_BangNgayBatDau() {
		String endDate = getForwardDate(2);

		log.info("TC_19_01_Click Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_19_02_Click Chuyen tien trong ngan hang");
		homePage.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_19_03_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_19_04_Scroll den nut Tiep tuc");
		transferRecurrent.scrollDownToButton(driver, "Tiếp tục");

		log.info("TC_19_05_Chon Ngay Ket thuc");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, endDate);

		endDate = getForwardYear(1);
		int chooseDate = Integer.parseInt(endDate.split("/")[0]) - 1;
		log.info("TC_19_06_Kiem tra khong cho phep chon");
		verifyFailure(transferRecurrent.checkDateNextYearEnable("android:id/month_view", chooseDate));
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
