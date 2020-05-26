package vnpay.vietcombank.interest_rate_calculate;

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
import pageObjects.InterestRateCalculatePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SavingOnlinePageObject;
import vietcombank_test_data.InterestRateCalculatePage_Data;

public class InterestRate_Calculate extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private InterestRateCalculatePageObject interestRate;
	private SavingOnlinePageObject savingOnline;

	private double interestMoney, totalMoney, monthlyMoney, remainMoney, sourceMoney;
	private String rate;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		home.clickToDynamicButtonLinkOrLinkText(driver, "NHẤN và GIỮ để DI CHUYỂN\r\n" + "nhanh đến các nhóm chức năng");

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Chon tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step_02: Mo sub-menu Ho tro");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hỗ trợ");

		log.info("TC_01_Step_03: An vao phan Tinh toan lai suat");
		home.scrollDownToText(driver, "Tra cứu");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tính toán lãi suất");

		log.info("TC_01_Step_04: Hien thi man hinh Tinh toan lai suat");
		interestRate = PageFactoryManager.getInterestRateCalculatePageObject(driver);
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), InterestRateCalculatePage_Data.VALIDATE.INTEREST_RATE_CALCULATE_TITLE);
	}

	@Test
	public void TC_01_TinhLaiTietKiem_VND() {

		log.info("TC_01_Step_05: Xac nhan tab 'Tinh lai tiet kiem' dang duoc chon ");
		verifyEquals(interestRate.isDynamicValuesFocus(driver, InterestRateCalculatePage_Data.VALIDATE.SAVING_INTEREST_RATE_TITLE), true);

		log.info("TC_01_Step_06: Chon 'Loai tien' VND");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, "VND");

		log.info("TC_01_Step_07: Nhap so tien goc VND");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.VND_MONEY, "com.VCB:id/layoutSoTienGoc");

		log.info("TC_01_Step_08: Chon 'Ky han'");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutKyHan");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, InterestRateCalculatePage_Data.DATA.TERM_12_MONTHS);

		log.info("TC_01_Step_09: Lay lai suat hien thi");
		rate = interestRate.getDynamicTextByLabel(driver, "Lãi suất");

		log.info("TC_01_Step_10: Chon 'Ngay gui'");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGui");
		interestRate.clickToDynamicDateInDateTimePicker(driver, getForWardDay(1));
		interestRate.clickToDynamicButton(driver, "OK");

		log.info("TC_01_Step_11: An nut 'Xoa Du lieu Nhap'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btnClean"), "Xóa dữ liệu nhập");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btnClean");

		log.info("TC_01_Step_12: Xac nhan hien thi comboBox 'Loai tien' trong");
		verifyEquals(interestRate.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien"), "Chọn loại tiền");

		log.info("TC_01_Step_13: Xac nhan hien thi textfield 'So tien goc' trong");
		verifyEquals(interestRate.getTextEditViewByLinearLayoutID(driver, "com.VCB:id/layoutSoTienGoc"), "Nhập số tiền gốc");

		log.info("TC_01_Step_14: Xac nhan hien thi combobox 'Ky han' trong");
		verifyEquals(interestRate.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutKyHan"), "Chọn kỳ hạn");

		log.info("TC_01_Step_15: Xac nhan hien thi datepicker 'Ngay gui' trong");
		verifyEquals(interestRate.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGui"), "Ngày gửi");

		log.info("TC_01_Step_16: Nhap lai loai tien VND");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, "VND");

		log.info("TC_01_Step_17: Nhap lai so tien goc");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.VND_MONEY, "com.VCB:id/layoutSoTienGoc");

		log.info("TC_01_Step_18: Nhap lai Ky Han");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutKyHan");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, InterestRateCalculatePage_Data.DATA.TERM_12_MONTHS);

		log.info("TC_01_Step_19: Nhap lai ngay gui");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGui");
		interestRate.clickToDynamicDateInDateTimePicker(driver, getForWardDay(0));
		interestRate.clickToDynamicButton(driver, "OK");

		log.info("TC_01_Step_20: An nut 'Tinh lai'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btnCalculator"), "Tính lãi");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btnCalculator");

		log.info("TC_01_Step_21: Xac nhan hien thị man hinh 'Ket qua tinh toan'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE);

		log.info("TC_01_Step_22: Xac nhan hien thị label 'Luu y'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE_HEAD);

		log.info("TC_01_Step_23: Xac nhan hien thi dung ngay dao han");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Ngày đáo hạn"), getForwardYear(1));

		log.info("TC_01_Step_24: Xac nhan hien thi dung  tien lai");
		interestMoney = (convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY) * convertAvailableBalanceCurrentcyToDouble(getSplitStringIndex(rate, "%", 0))) / 100;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tiền lãi"), addCommasToDouble(interestMoney + "").replace(".00", "") + " VND");

		log.info("TC_01_Step_25: Xac nhan hien thi dung tong tien");
		totalMoney = convertAvailableBalanceCurrentcyOrFeeToLong(InterestRateCalculatePage_Data.DATA.VND_MONEY) + interestMoney;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tổng tiền"), addCommasToDouble(totalMoney + "").replace(".00", "") + " VND");

		log.info("TC_01_Step_26: An nut 'Gui tiet kiem'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Gửi tiết kiệm");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);

		log.info("TC_01_Step_27: Xac nhan hien thi man hinh 'Mo tai khoan tiet kiem'");
		verifyEquals(savingOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Mở tài khoản tiết kiệm");

		log.info("TC_01_Step_28: An nut Back va xac nhan quay ve man hinh 'Ket qua tinh toan'");
		savingOnline.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE);

	}

	@Test
	public void TC_02_TinhLaiTietKiem_USD() {

		log.info("TC_02_Step_01: Quay ve man hinh Tinh toan lai suat");
		interestRate.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_02: Hien thi man hinh Tinh toan lai suat");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), InterestRateCalculatePage_Data.VALIDATE.INTEREST_RATE_CALCULATE_TITLE);

		log.info("TC_02_Step_03: Xac nhan tab 'Tinh lai tiet kiem' dang duoc chon ");
		verifyEquals(interestRate.isDynamicValuesFocus(driver, InterestRateCalculatePage_Data.VALIDATE.SAVING_INTEREST_RATE_TITLE), true);

		log.info("TC_02_Step_04: Chon 'Loai tien' USD");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, "USD");

		log.info("TC_02_Step_05: Nhap so tien goc USD");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.USD_MONEY, "com.VCB:id/layoutSoTienGoc");

		log.info("TC_02_Step_06: Chon 'Ky han'");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutKyHan");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, InterestRateCalculatePage_Data.DATA.TERM_12_MONTHS);

		log.info("TC_02_Step_07: Lay lai suat");
		rate = interestRate.getDynamicTextByLabel(driver, "Lãi suất");

		log.info("TC_02_Step_08: Chon 'Ngay gui'");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGui");
		interestRate.clickToDynamicDateInDateTimePicker(driver, getForWardDay(1));
		interestRate.clickToDynamicButton(driver, "OK");

		log.info("TC_02_Step_09: An nut 'Xoa Du lieu Nhap'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btnClean"), "Xóa dữ liệu nhập");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btnClean");

		log.info("TC_02_Step_10: Xac nhan hien thi comboBox 'Loai tien' trong");
		verifyEquals(interestRate.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien"), "Chọn loại tiền");

		log.info("TC_02_Step_11: Xac nhan hien thi textfield 'So tien goc' trong");
		verifyEquals(interestRate.getTextEditViewByLinearLayoutID(driver, "com.VCB:id/layoutSoTienGoc"), "Nhập số tiền gốc");

		log.info("TC_02_Step_12: Xac nhan hien thi combobox 'Ky han' trong");
		verifyEquals(interestRate.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutKyHan"), "Chọn kỳ hạn");

		log.info("TC_02_Step_13: Xac nhan hien thi datepicker 'Ngay gui' trong");
		verifyEquals(interestRate.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGui"), "Ngày gửi");

		log.info("TC_02_Step_14: Nhap lai Loai tien");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, "USD");

		log.info("TC_02_Step_15: Nhap lai So tien goc");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.USD_MONEY, "com.VCB:id/layoutSoTienGoc");

		log.info("TC_02_Step_16: Chon lai Ky Han");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutKyHan");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, InterestRateCalculatePage_Data.DATA.TERM_12_MONTHS);

		log.info("TC_02_Step_17: Nhap lai Ngay gui");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGui");
		interestRate.clickToDynamicDateInDateTimePicker(driver, getForWardDay(0));
		interestRate.clickToDynamicButton(driver, "OK");

		log.info("TC_02_Step_18: An nut 'Tinh lai'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btnCalculator"), "Tính lãi");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btnCalculator");

		log.info("TC_02_Step_19: Xac nhan hien thị man hinh 'Ket qua tinh toan'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE);

		log.info("TC_02_Step_20: Xac nhan hien thị label 'Luu y'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE_HEAD);

		log.info("TC_02_Step_21: Xac nhan hien thi dung ngay dao han");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Ngày đáo hạn"), getForwardYear(1));

		log.info("TC_02_Step_22: Xac nhan hien thi dung  tien lai");
		interestMoney = (convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.USD_MONEY) * convertAvailableBalanceCurrentcyToDouble(getSplitStringIndex(rate, "%", 0))) / 100;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tiền lãi"), addCommasToDouble(interestMoney + "").replace(".00", "") + " USD");

		log.info("TC_02_Step_23: Xac nhan hien thi dung tong tien");
		totalMoney = convertAvailableBalanceCurrentcyOrFeeToLong(InterestRateCalculatePage_Data.DATA.USD_MONEY) + interestMoney;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tổng tiền"), addCommasToDouble(totalMoney + "").replace(".00", "") + " USD");

		log.info("TC_02_Step_24: An nut 'Gui tiet kiem'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Gửi tiết kiệm");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);

		log.info("TC_02_Step_25: Xac nhan hien thi man hinh 'Mo tai khoan tiet kiem'");
		verifyEquals(savingOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Mở tài khoản tiết kiệm");

		log.info("TC_02_Step_26: An nut Back va xac nhan quay ve man hinh 'Ket qua tinh toan'");
		savingOnline.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE);
	}

	@Test
	public void TC_03_TinhLaiTietKiem_EUR() {

		log.info("TC_03_Step_01: Quay ve man hinh Tinh toan lai suat");
		interestRate.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_03_Step_02: Hien thi man hinh Tinh toan lai suat");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), InterestRateCalculatePage_Data.VALIDATE.INTEREST_RATE_CALCULATE_TITLE);

		log.info("TC_03_Step_03: Xac nhan tab 'Tinh lai tiet kiem' dang duoc chon ");
		verifyEquals(interestRate.isDynamicValuesFocus(driver, InterestRateCalculatePage_Data.VALIDATE.SAVING_INTEREST_RATE_TITLE), true);

		log.info("TC_03_Step_04: Chon 'Loai tien' EUR");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, "EUR");

		log.info("TC_03_Step_05: Nhap so tien goc USD");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.EUR_MONEY, "com.VCB:id/layoutSoTienGoc");

		log.info("TC_03_Step_06: Chon 'Ky han'");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutKyHan");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, InterestRateCalculatePage_Data.DATA.TERM_12_MONTHS);

		log.info("TC_03_Step_07: Lay lai suat");
		rate = interestRate.getDynamicTextByLabel(driver, "Lãi suất");

		log.info("TC_03_Step_08: Chon 'Ngay gui'");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGui");
		interestRate.clickToDynamicDateInDateTimePicker(driver, getForWardDay(1));
		interestRate.clickToDynamicButton(driver, "OK");

		log.info("TC_03_Step_09: An nut 'Xoa Du lieu Nhap'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btnClean"), "Xóa dữ liệu nhập");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btnClean");

		log.info("TC_03_Step_10: Xac nhan hien thi comboBox 'Loai tien' trong");
		verifyEquals(interestRate.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien"), "Chọn loại tiền");

		log.info("TC_03_Step_11: Xac nhan hien thi textfield 'So tien goc' trong");
		verifyEquals(interestRate.getTextEditViewByLinearLayoutID(driver, "com.VCB:id/layoutSoTienGoc"), "Nhập số tiền gốc");

		log.info("TC_03_Step_12: Xac nhan hien thi combobox 'Ky han' trong");
		verifyEquals(interestRate.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutKyHan"), "Chọn kỳ hạn");

		log.info("TC_03_Step_13: Xac nhan hien thi datepicker 'Ngay gui' trong");
		verifyEquals(interestRate.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGui"), "Ngày gửi");

		log.info("TC_03_Step_14: Nhap lai loai tien");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, "EUR");

		log.info("TC_03_Step_15: Nhap lai So tien goc");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.EUR_MONEY, "com.VCB:id/layoutSoTienGoc");

		log.info("TC_03_Step_16: Chon lai Ky Han");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutKyHan");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, InterestRateCalculatePage_Data.DATA.TERM_12_MONTHS);

		log.info("TC_03_Step_17: Nhap lai ngay gui");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGui");
		interestRate.clickToDynamicDateInDateTimePicker(driver, getForWardDay(0));
		interestRate.clickToDynamicButton(driver, "OK");

		log.info("TC_03_Step_16: An nut 'Tinh lai'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btnCalculator"), "Tính lãi");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btnCalculator");

		log.info("TC_03_Step_17: Xac nhan hien thị man hinh 'Ket qua tinh toan'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE);

		log.info("TC_03_Step_18: Xac nhan hien thị label 'Luu y'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE_HEAD);

		log.info("TC_03_Step_19: Xac nhan hien thi dung ngay dao han");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Ngày đáo hạn"), getForwardYear(1));

		log.info("TC_03_Step_20: Xac nhan hien thi dung  tien lai");
		interestMoney = (convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.EUR_MONEY) * convertAvailableBalanceCurrentcyToDouble(getSplitStringIndex(rate, "%", 0))) / 100;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tiền lãi"), addCommasToDouble(interestMoney + "").replace(".00", "") + " EUR");

		log.info("TC_03_Step_21: Xac nhan hien thi dung tong tien");
		totalMoney = convertAvailableBalanceCurrentcyOrFeeToLong(InterestRateCalculatePage_Data.DATA.EUR_MONEY) + interestMoney;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tổng tiền"), addCommasToDouble(totalMoney + "").replace(".00", "") + " EUR");

		log.info("TC_03_Step_22: An nut 'Gui tiet kiem'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Gửi tiết kiệm");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);

		log.info("TC_03_Step_23: Xac nhan hien thi man hinh 'Mo tai khoan tiet kiem'");
		verifyEquals(savingOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Mở tài khoản tiết kiệm");

		log.info("TC_03_Step_24: An nut Back va xac nhan quay ve man hinh 'Ket qua tinh toan'");
		savingOnline.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE);
	}

	@Test
	public void TC_04_TinhLichTraNo() {

		log.info("TC_04_Step_01: Quay ve man hinh Tinh toan lai suat");
		interestRate.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_04_Step_02: Chon tab 'Tinh lich tra no'");
		interestRate.clickToTextID(driver, "com.VCB:id/tvRight");
		verifyEquals(interestRate.isDynamicValuesFocus(driver, InterestRateCalculatePage_Data.VALIDATE.DEBT_RATE_CALCULATE_TITLE), true);

		log.info("TC_04_Step_03: Nhap so tien vay");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.VND_MONEY, "com.VCB:id/layoutSoTienGoc");

		log.info("TC_04_Step_04: Nhap so thang vay");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.MONTHS, "com.VCB:id/layoutSoThangVay");

		log.info("TC_04_Step_05: Nhap lai suat");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.RATE, "com.VCB:id/layoutLaiSuat");

		log.info("TC_04_Step_06: Nhap ngay giai ngan");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGiaiNgan");
		interestRate.clickToDynamicDateInDateTimePicker(driver, getForWardDay(0));
		interestRate.clickToDynamicButton(driver, "OK");

		log.info("TC_04_Step_07: An nut 'Xoa Du lieu Nhap'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btnClean"), "Xóa dữ liệu nhập");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btnClean");

		log.info("TC_04_Step_08: Xac nhan hien thi textfield 'So tien vay' trong");
		verifyEquals(interestRate.getTextEditViewByLinearLayoutID(driver, "com.VCB:id/layoutSoTienGoc"), "Nhập số tiền vay");

		log.info("TC_04_Step_09: Xac nhan hien thi textfield 'So thang vay' trong");
		verifyEquals(interestRate.getTextEditViewByLinearLayoutID(driver, "com.VCB:id/layoutSoThangVay"), "Số tháng vay");

		log.info("TC_04_Step_10: Xac nhan hien thi textfield 'lai suat' trong");
		verifyEquals(interestRate.getTextEditViewByLinearLayoutID(driver, "com.VCB:id/layoutLaiSuat"), "Nhập lãi suất");

		log.info("TC_04_Step_11: Xac nhan hien thi datepicker 'Ngay giai ngan' trong");
		verifyEquals(interestRate.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGiaiNgan"), "Ngày giải ngân");

		log.info("TC_04_Step_12: Nhap lai so tien goc");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.VND_MONEY, "com.VCB:id/layoutSoTienGoc");

		log.info("TC_04_Step_13: Nhap lai so thang vay");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.MONTHS, "com.VCB:id/layoutSoThangVay");

		log.info("TC_04_Step_14: Nhap lai lai suat");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.RATE, "com.VCB:id/layoutLaiSuat");

		log.info("TC_04_Step_15: Nhap lai Ngay giai ngan");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGiaiNgan");
		interestRate.clickToDynamicDateInDateTimePicker(driver, getForWardDay(0));
		interestRate.clickToDynamicButton(driver, "OK");

		log.info("TC_04_Step_16: An nut 'Tinh lich'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btnCalculator"), "Tính lịch");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btnCalculator");

		log.info("TC_04_Step_17: Xac nhan hien thị man hinh 'Ket qua tinh toan'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE);

		log.info("TC_04_Step_18: Xac nhan hien thị label 'Luu y'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE_HEAD);

		log.info("TC_04_Step_19: Xac nhan hien thi dung ngay tra no");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Ngày trả nợ"), getForwardDate(0));

		log.info("TC_04_Step_20: Xac nhan hien thi dung so goc con lai");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Số gốc còn lại"), addCommasToLong(InterestRateCalculatePage_Data.DATA.VND_MONEY) + " VND");

		log.info("TC_04_Step_21: Xac nhan hien thi dung so goc tra hang thang");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Gốc trả hàng tháng"), "");

		log.info("TC_04_Step_22: Xac nhan hien thi dung Lai tra hang thang");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Lãi trả hàng tháng"), "");

		log.info("TC_04_Step_23: Xac nhan hien thi dung tong tien tra hang thang");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tổng số tiền phải trả hàng tháng"), "");

		log.info("TC_04_Step_24: Chon Ky 1");
		interestRate.clickToDynamicLinerLayoutIndex(driver, "1");

		log.info("TC_04_Step_25: Xac nhan ky 1 duoc highlight");
		verifyEquals(interestRate.isDynamicLinearlayoutIndexFocus(driver, "1"), true);

		log.info("TC_04_Step_26: Xac nhan hien thi dung ngay tra no");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Ngày trả nợ"), getForwardMonthAndForwardDay(1, 0));

		log.info("TC_04_Step_27: Xac nhan hien thi dung so goc con lai");
		sourceMoney = convertMoneyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY, "VND");
		remainMoney = sourceMoney * 2 / 3;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Số gốc còn lại"), addCommasToDouble((remainMoney + "")) + " VND");

		log.info("TC_04_Step_28: Xac nhan hien thi dung so goc tra hang thang");
		monthlyMoney = sourceMoney / 3;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Gốc trả hàng tháng"), addCommasToDouble((monthlyMoney + "")) + " VND");

		log.info("TC_04_Step_29: Xac nhan hien thi dung Lai tra hang thang");
		rate = convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.RATE) + "";
		interestMoney = sourceMoney * Double.parseDouble(rate) / 12 / 100;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Lãi trả hàng tháng"), addCommasToDouble(interestMoney + "").replace(".00", "") + " VND");

		log.info("TC_04_Step_30: Xac nhan hien thi dung tong tien tra hang thang");
		totalMoney = monthlyMoney + interestMoney;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tổng số tiền phải trả hàng tháng"), addCommasToDouble(totalMoney + "") + " VND");

		log.info("TC_04_Step_31: Chon Ky 2");
		interestRate.clickToDynamicLinerLayoutIndex(driver, "2");

		log.info("TC_04_Step_32: Xac nhan ky 2 duoc highlight");
		verifyEquals(interestRate.isDynamicLinearlayoutIndexFocus(driver, "2"), true);

		log.info("TC_04_Step_33: Xac nhan hien thi dung ngay tra no");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Ngày trả nợ"), getForwardMonthAndForwardDay(2, 0));

		log.info("TC_04_Step_34: Xac nhan hien thi dung so goc con lai");
		remainMoney = remainMoney - sourceMoney / 3;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Số gốc còn lại"), addCommasToDouble(remainMoney + "") + " VND");

		log.info("TC_04_Step_35: Xac nhan hien thi dung so goc tra hang thang");
		monthlyMoney = sourceMoney / 3;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Gốc trả hàng tháng"), addCommasToDouble(monthlyMoney + "") + "" + " VND");

		log.info("TC_04_Step_36: Xac nhan hien thi dung Lai tra hang thang");
		interestMoney = (sourceMoney * 2 / 3) * Double.parseDouble(rate) / 12 / 100;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Lãi trả hàng tháng"), addCommasToDouble(interestMoney + "").replace(".00", "") + " VND");

		log.info("TC_04_Step_37: Xac nhan hien thi dung tong tien tra hang thang");
		totalMoney = monthlyMoney + interestMoney;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tổng số tiền phải trả hàng tháng"), addCommasToDouble(totalMoney + "") + " VND");

		log.info("TC_04_Step_38: Chon Ky 3");
		interestRate.clickToDynamicLinerLayoutIndex(driver, "3");

		log.info("TC_04_Step_39: Xac nhan ky 3 duoc highlight");
		verifyEquals(interestRate.isDynamicLinearlayoutIndexFocus(driver, "3"), true);

		log.info("TC_04_Step_40: Xac nhan hien thi dung ngay tra no");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Ngày trả nợ"), getForwardMonthAndForwardDay(3, 0));

		log.info("TC_04_Step_41: Xac nhan hien thi dung so goc con lai");
		remainMoney = 0;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Số gốc còn lại"), addCommasToDouble(remainMoney + "").replace(".00", "") + " VND");

		log.info("TC_04_Step_42: Xac nhan hien thi dung so goc tra hang thang");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Gốc trả hàng tháng"), addCommasToDouble(monthlyMoney + "") + " VND");

		log.info("TC_04_Step_43: Xac nhan hien thi dung Lai tra hang thang");
		interestMoney = sourceMoney / 3 * Double.parseDouble(rate) / 12 / 100;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Lãi trả hàng tháng"), addCommasToDouble(interestMoney + "").replace(".00", "") + " VND");

		log.info("TC_04_Step_44: Xac nhan hien thi dung tong tien tra hang thang");
		totalMoney = monthlyMoney + interestMoney;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tổng số tiền phải trả hàng tháng"), addCommasToDouble(totalMoney + "") + " VND");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
