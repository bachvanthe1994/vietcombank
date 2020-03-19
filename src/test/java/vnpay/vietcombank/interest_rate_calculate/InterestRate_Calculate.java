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
import vietcombank_test_data.InterestRateCalculatePage_Data;

public class InterestRate_Calculate extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private InterestRateCalculatePageObject interestRate;
	
	private double interestMoney, totalMoney;
	private String rate;

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
	public void TC_01_TinhLaiTietKiem_VND() {
		
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_01_Step_01: Chon tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");
		
		log.info("TC_01_Step_02: Mo sub-menu Ho tro");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hỗ trợ");
		
		log.info("TC_01_Step_03: An vao phan Tinh toan lai suat");
		home.scrollDownToText(driver, "Tính toán lãi suất");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tính toán lãi suất");
		
		log.info("TC_01_Step_04: Hien thi man hinh Tinh toan lai suat");
		interestRate = PageFactoryManager.getInterestRateCalculatePageObject(driver);
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), 
				InterestRateCalculatePage_Data.VALIDATE.INTEREST_RATE_CALCULATE_TITLE);
		
		log.info("TC_01_Step_05: Xac nhan tab 'Tinh lai tiet kiem' dang duoc chon ");
		verifyEquals(interestRate.isDynamicValuesFocus(driver, InterestRateCalculatePage_Data.VALIDATE.SAVING_INTEREST_RATE_TITLE), true);
		
		log.info("TC_01_Step_06: Chon 'Loai tien' VND");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, "VND");
		
		log.info("TC_01_Step_07: Nhap so tien goc VND");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.VND_MONEY,"com.VCB:id/layoutSoTienGoc");

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
		
		log.info("TC_01_Step_16: Nhap lai thong so vao cac field");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, "VND");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, 
				InterestRateCalculatePage_Data.DATA.VND_MONEY,"com.VCB:id/layoutSoTienGoc");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutKyHan");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, InterestRateCalculatePage_Data.DATA.TERM_12_MONTHS);
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGui");
		interestRate.clickToDynamicDateInDateTimePicker(driver, getForWardDay(0));
		interestRate.clickToDynamicButton(driver, "OK");
		
		log.info("TC_01_Step_17: An nut 'Tinh lai'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btnCalculator"), "Tính lãi");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btnCalculator");
		
		log.info("TC_01_Step_18: Xac nhan hien thị man hinh 'Ket qua tinh toan'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), 
				InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE);
		
		log.info("TC_01_Step_19: Xac nhan hien thị label 'Luu y'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), 
				InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE_HEAD);
		
		log.info("TC_01_Step_20: Xac nhan hien thi dung ngay dao han");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Ngày đáo hạn"), getForwardYear(1));
		
		log.info("TC_01_Step_20: Xac nhan hien thi dung  tien lai");
		interestMoney = (convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY)
				*convertAvailableBalanceCurrentcyToDouble(getInterestRate(rate)))/100;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tiền lãi"), addCommasToDouble(interestMoney+"")+" VND");
		
		log.info("TC_01_Step_21: Xac nhan hien thi dung tong tien");
		totalMoney = convertAvailableBalanceCurrentcyOrFeeToLong(InterestRateCalculatePage_Data.DATA.VND_MONEY) + interestMoney ;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tổng tiền"), addCommasToDouble(totalMoney+"")+" VND");
		
		log.info("TC_01_Step_22: An nut 'Gui tiet kiem'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Gửi tiết kiệm");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_01_Step_23: Xac nhan hien thi man hinh 'Mo tai khoan tiet kiem'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Mở tài khoản tiết kiệm");
		
		log.info("TC_01_Step_24: An nut Back va xac nhan quay ve man hinh 'Ket qua tinh toan'");
		interestRate.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), 
				InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE);
		
	}
	
	@Test
	public void TC_02_TinhLaiTietKiem_USD() {
		
		log.info("TC_02_Step_01: Quay ve man hinh Tinh toan lai suat");
		interestRate.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_02_Step_02: Hien thi man hinh Tinh toan lai suat");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), 
				InterestRateCalculatePage_Data.VALIDATE.INTEREST_RATE_CALCULATE_TITLE);
		
		log.info("TC_02_Step_03: Xac nhan tab 'Tinh lai tiet kiem' dang duoc chon ");
		verifyEquals(interestRate.isDynamicValuesFocus(driver, InterestRateCalculatePage_Data.VALIDATE.SAVING_INTEREST_RATE_TITLE), true);
		
		log.info("TC_02_Step_04: Chon 'Loai tien' USD");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, "USD");
		
		log.info("TC_02_Step_05: Nhap so tien goc USD");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.USD_MONEY,"com.VCB:id/layoutSoTienGoc");

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
		
		log.info("TC_02_Step_14: Nhap lai thong so vao cac field");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, "USD");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.USD_MONEY,"com.VCB:id/layoutSoTienGoc");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutKyHan");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, InterestRateCalculatePage_Data.DATA.TERM_12_MONTHS);
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGui");
		interestRate.clickToDynamicDateInDateTimePicker(driver, getForWardDay(0));
		interestRate.clickToDynamicButton(driver, "OK");
		
		log.info("TC_02_Step_15: An nut 'Tinh lai'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btnCalculator"), "Tính lãi");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btnCalculator");
		
		log.info("TC_02_Step_16: Xac nhan hien thị man hinh 'Ket qua tinh toan'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), 
				InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE);
		
		log.info("TC_02_Step_17: Xac nhan hien thị label 'Luu y'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), 
				InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE_HEAD);
		
		log.info("TC_02_Step_18: Xac nhan hien thi dung ngay dao han");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Ngày đáo hạn"), getForwardYear(1));
		
		log.info("TC_02_Step_19: Xac nhan hien thi dung  tien lai");
		interestMoney = (convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.USD_MONEY)
				*convertAvailableBalanceCurrentcyToDouble(getInterestRate(rate)))/100;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tiền lãi"), addCommasToDouble(interestMoney+"")+" USD");
		
		log.info("TC_02_Step_20: Xac nhan hien thi dung tong tien");
		totalMoney = convertAvailableBalanceCurrentcyOrFeeToLong(InterestRateCalculatePage_Data.DATA.USD_MONEY) + interestMoney ;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tổng tiền"), addCommasToDouble(totalMoney+"")+" USD");
		
		log.info("TC_02_Step_21: An nut 'Gui tiet kiem'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Gửi tiết kiệm");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_02_Step_22: Xac nhan hien thi man hinh 'Mo tai khoan tiet kiem'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Mở tài khoản tiết kiệm");
		
		log.info("TC_02_Step_23: An nut Back va xac nhan quay ve man hinh 'Ket qua tinh toan'");
		interestRate.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), 
				InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE);
	}
	
	@Test
	public void TC_03_TinhLaiTietKiem_EUR() {
		
		log.info("TC_03_Step_01: Quay ve man hinh Tinh toan lai suat");
		interestRate.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_03_Step_02: Hien thi man hinh Tinh toan lai suat");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), 
				InterestRateCalculatePage_Data.VALIDATE.INTEREST_RATE_CALCULATE_TITLE);
		
		log.info("TC_03_Step_03: Xac nhan tab 'Tinh lai tiet kiem' dang duoc chon ");
		verifyEquals(interestRate.isDynamicValuesFocus(driver, InterestRateCalculatePage_Data.VALIDATE.SAVING_INTEREST_RATE_TITLE), true);
		
		log.info("TC_03_Step_04: Chon 'Loai tien' EUR");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, "EUR");
		
		log.info("TC_03_Step_05: Nhap so tien goc USD");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.EUR_MONEY,"com.VCB:id/layoutSoTienGoc");

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
		
		log.info("TC_03_Step_14: Nhap lai thong so vao cac field");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiTien");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, "EUR");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.EUR_MONEY,"com.VCB:id/layoutSoTienGoc");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutKyHan");
		interestRate.clickToDynamicButtonLinkOrLinkText(driver, InterestRateCalculatePage_Data.DATA.TERM_12_MONTHS);
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGui");
		interestRate.clickToDynamicDateInDateTimePicker(driver, getForWardDay(0));
		interestRate.clickToDynamicButton(driver, "OK");
		
		log.info("TC_03_Step_15: An nut 'Tinh lai'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btnCalculator"), "Tính lãi");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btnCalculator");
		
		log.info("TC_03_Step_16: Xac nhan hien thị man hinh 'Ket qua tinh toan'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), 
				InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE);
		
		log.info("TC_03_Step_17: Xac nhan hien thị label 'Luu y'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), 
				InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE_HEAD);
		
		log.info("TC_03_Step_18: Xac nhan hien thi dung ngay dao han");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Ngày đáo hạn"), getForwardYear(1));
		
		log.info("TC_03_Step_19: Xac nhan hien thi dung  tien lai");
		interestMoney = (convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.EUR_MONEY)
				*convertAvailableBalanceCurrentcyToDouble(getInterestRate(rate)));
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tiền lãi"), addCommasToDouble(interestMoney+"")+" EUR");
		
		log.info("TC_03_Step_20: Xac nhan hien thi dung tong tien");
		totalMoney = convertAvailableBalanceCurrentcyOrFeeToLong(InterestRateCalculatePage_Data.DATA.EUR_MONEY) + interestMoney ;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tổng tiền"), addCommasToDouble(totalMoney+"")+" EUR");
		
		log.info("TC_03_Step_21: An nut 'Gui tiet kiem'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Gửi tiết kiệm");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_03_Step_22: Xac nhan hien thi man hinh 'Mo tai khoan tiet kiem'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Mở tài khoản tiết kiệm");
		
		log.info("TC_03_Step_23: An nut Back va xac nhan quay ve man hinh 'Ket qua tinh toan'");
		interestRate.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), 
				InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE);
	}
	
	//@Test
	public void TC_04_TinhLichTraNo() {
		
		log.info("TC_04_Step_01: Quay ve man hinh Tinh toan lai suat");
		interestRate.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_04_Step_02: Chon tab 'Tinh lich tra no'");
		interestRate.clickToTextID(driver, "com.VCB:id/tvRight");
		verifyEquals(interestRate.isDynamicValuesFocus(driver, InterestRateCalculatePage_Data.VALIDATE.DEBT_RATE_CALCULATE_TITLE), true);
		
		log.info("TC_04_Step_03: Nhap so tien vay");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.VND_MONEY,"com.VCB:id/layoutSoTienGoc");

		log.info("TC_04_Step_04: Nhap so thang vay");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.MONTHS,"com.VCB:id/layoutSoThangVay");

		log.info("TC_04_Step_05: Nhap lai suat");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.RATE,"com.VCB:id/layoutLaiSuat");

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
		
		log.info("TC_04_Step_12: Nhap lai cac thong so");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.VND_MONEY,"com.VCB:id/layoutSoTienGoc");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.MONTHS,"com.VCB:id/layoutSoThangVay");
		interestRate.inputToDynamicEditviewByLinearlayoutId(driver, InterestRateCalculatePage_Data.DATA.RATE,"com.VCB:id/layoutLaiSuat");
		interestRate.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayGiaiNgan");
		interestRate.clickToDynamicDateInDateTimePicker(driver, getForWardDay(0));
		interestRate.clickToDynamicButton(driver, "OK");

		log.info("TC_04_Step_13: An nut 'Tinh lich'");
		verifyEquals(interestRate.getDynamicTextButtonById(driver, "com.VCB:id/btnCalculator"), "Tính lịch");
		interestRate.clickToDynamicAcceptButton(driver, "com.VCB:id/btnCalculator");
		
		log.info("TC_04_Step_14: Xac nhan hien thị man hinh 'Ket qua tinh toan'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), 
				InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE);
		
		log.info("TC_04_Step_15: Xac nhan hien thị label 'Luu y'");
		verifyEquals(interestRate.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), 
				InterestRateCalculatePage_Data.VALIDATE.CALCULATE_RESULT_TITLE_HEAD);
		
		log.info("TC_04_Step_16: Xac nhan ky 0 duoc highlight");
		verifyEquals(interestRate.isDynamicLinearlayoutIndexFocus(driver, "0"), true);
		
		log.info("TC_04_Step_17: Xac nhan hien thi dung ngay tra no");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Ngày trả nợ"), getForwardDate(0));
		
		log.info("TC_04_Step_18: Xac nhan hien thi dung so goc con lai");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Số gốc còn lại"),
				addCommasToLong(InterestRateCalculatePage_Data.DATA.VND_MONEY) +" VND");
		
		log.info("TC_04_Step_19: Xac nhan hien thi dung so goc tra hang thang");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Gốc trả hàng tháng"),"");
		
		log.info("TC_04_Step_20: Xac nhan hien thi dung Lai tra hang thang");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Lãi trả hàng tháng"),"");
		
		log.info("TC_04_Step_21: Xac nhan hien thi dung tong tien tra hang thang");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tổng số tiền phải trả hàng tháng"),"");
		
		log.info("TC_04_Step_22: Chon Ky 1");
		interestRate.clickToDynamicLinerLayoutIndex(driver, "1");
		
		log.info("TC_04_Step_23: Xac nhan ky 1 duoc highlight");
		verifyEquals(interestRate.isDynamicLinearlayoutIndexFocus(driver, "1"), true);
		
		log.info("TC_04_Step_24: Xac nhan hien thi dung ngay tra no");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Ngày trả nợ"), getForwardMonthAndForwardDay(1,0));
		
		log.info("TC_04_Step_25: Xac nhan hien thi dung so goc con lai");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Số gốc còn lại"),
				addCommasToDouble((convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY)*2/3)+"") +" VND");
		
		log.info("TC_04_Step_26: Xac nhan hien thi dung so goc tra hang thang");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Gốc trả hàng tháng"),
				addCommasToDouble((convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY)/3)+"")+" VND");
		
		log.info("TC_04_Step_27: Xac nhan hien thi dung Lai tra hang thang");
		interestMoney = convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY)
				*convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.RATE)/12/100;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Lãi trả hàng tháng"),addCommasToDouble(interestMoney+"")+" VND");
		
		log.info("TC_04_Step_28: Xac nhan hien thi dung tong tien tra hang thang");
		totalMoney = convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY)/3 + interestMoney;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tổng số tiền phải trả hàng tháng"),totalMoney);
		
		log.info("TC_04_Step_29: Chon Ky 2");
		interestRate.clickToDynamicLinerLayoutIndex(driver, "2");
		
		log.info("TC_04_Step_30: Xac nhan ky 2 duoc highlight");
		verifyEquals(interestRate.isDynamicLinearlayoutIndexFocus(driver, "2"), true);
		
		log.info("TC_04_Step_31: Xac nhan hien thi dung ngay tra no");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Ngày trả nợ"), getForwardMonthAndForwardDay(2,0));
		
		log.info("TC_04_Step_32: Xac nhan hien thi dung so goc con lai");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Số gốc còn lại"),
				addCommasToDouble((convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY)/3)+"") +" VND");
		
		log.info("TC_04_Step_33: Xac nhan hien thi dung so goc tra hang thang");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Gốc trả hàng tháng"),
				addCommasToDouble((convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY)/3)+"")+" VND");
		
		log.info("TC_04_Step_34: Xac nhan hien thi dung Lai tra hang thang");
		interestMoney = (convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY)*2/3)
				*convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.RATE)/12/100;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Lãi trả hàng tháng"),addCommasToDouble(interestMoney+"")+" VND");
		
		
		log.info("TC_04_Step_35: Xac nhan hien thi dung tong tien tra hang thang");
		totalMoney = convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY)/3 + interestMoney;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tổng số tiền phải trả hàng tháng"),totalMoney);
		
		log.info("TC_04_Step_36: Chon Ky 3");
		interestRate.clickToDynamicLinerLayoutIndex(driver, "2");
		
		log.info("TC_04_Step_36: Xac nhan ky 3 duoc highlight");
		verifyEquals(interestRate.isDynamicLinearlayoutIndexFocus(driver, "3"), true);
		
		log.info("TC_04_Step_37: Xac nhan hien thi dung ngay tra no");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Ngày trả nợ"), getForwardMonthAndForwardDay(3,0));
		
		log.info("TC_04_Step_38: Xac nhan hien thi dung so goc con lai");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Số gốc còn lại"),
				addCommasToDouble((convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY)/3)+"") +" VND");
		
		log.info("TC_04_Step_39: Xac nhan hien thi dung so goc tra hang thang");
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Gốc trả hàng tháng"),
				addCommasToDouble((convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY)/3)+"")+" VND");
		
		log.info("TC_04_Step_40: Xac nhan hien thi dung Lai tra hang thang");
		interestMoney = (convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY)/3)
				*convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.RATE)/12/100;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Lãi trả hàng tháng"),addCommasToDouble(interestMoney+"")+" VND");
		
		
		log.info("TC_04_Step_41: Xac nhan hien thi dung tong tien tra hang thang");
		totalMoney = convertAvailableBalanceCurrentcyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY)/3 + interestMoney;
		verifyEquals(interestRate.getDynamicTextByLabel(driver, "Tổng số tiền phải trả hàng tháng"),totalMoney);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
