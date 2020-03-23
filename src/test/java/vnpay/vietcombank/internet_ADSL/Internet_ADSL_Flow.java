package vnpay.vietcombank.internet_ADSL;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.InternetADSLPageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Internet_ADSL_Data;
import vietcombank_test_data.LogIn_Data;

public class Internet_ADSL_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private InternetADSLPageObject ADSL;
	private TransactionReportPageObject transReport;
	String passLogin = "";
	String transferTime;
	String transactionNumber;
	long amount, fee, amountStart, feeView, amountView, amountAfter = 0;

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
		ADSL = PageFactoryManager.getInternetADSLPageObject(driver);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		passLogin = pass;
	}

	@Test
	public void TC_01_ThanhToanCuocViettelXacThucMatKhau() {
		log.info("TC_01_Step_Click cuoc ADSL");
		ADSL.scrollDownToText(driver, "Thanh toán tiền nước");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Cước Internet ADSL");

		log.info("TC_01_Step_Select tai khoan nguon");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextByLabel(driver, "Số dư khả dụng"));

		log.info("TC_01_Step_Thong tin giao dich chon Viettel");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[0]);
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[0]);

		log.info("TC_01_Input ma khach hang");
		ADSL.inputCustomerCode(Internet_ADSL_Data.Valid_Account.CODEVIETTEL);

		log.info("TC_01_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_Kiem tra tai khoan nguon");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước internet ADSL");

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.NETWORK[0]);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_01_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số tiền thanh toán"));

		log.info("TC_01_Chon phuong thuc xac thuc");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Mật khẩu đăng nhập"));
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_01_verify so tien phi");
		feeView = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số tiền phí"));
		verifyEquals(feeView, fee);

		log.info("TC_01_Click Tiep tuc");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		ADSL.inputToDynamicPopupPasswordInput(driver, passLogin, "Tiếp tục");

		log.info("TC_01_Step_Tiep tuc");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_01_Verify so tien thanh toan");
		amountView = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"));
		verifyEquals(amountView, amount);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = ADSL.getDynamicTransferTimeAndMoney(driver, "GIAO DỊCH THÀNH CÔNG", "4").split(" ")[3];

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước internet ADSL");

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.NETWORK[0]);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase().toUpperCase());

		log.info("TC_01_Step_:Lay ma giao dich");
		transactionNumber = ADSL.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_Step_: Chon thuc hien giao dich");
		ADSL.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		amountAfter = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amount - fee, amountAfter);
	}

	@Test
	public void TC_02_Report_ThanhToanCuocViettelXacThucMatKhau() {
		log.info("TC_02_Step: Click back man hinh home");
		ADSL.clickImageBack( "Cước internet ADSL");

		log.info("TC_02_Step: Click menu header");
		ADSL.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_02_Step: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_02_Step: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán hóa đơn");

		log.info("TC_02_Step: Chon so tai khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_Step: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_02_Step: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_02: Step: verify thoi tim kiem den ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_02_Step: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02: Step: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Step: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_02: Step: Check so tien chuyen");
		String amountExpect = "- " + addCommasToLong(amount + "") + " VND";
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), amountExpect);

		log.info("TC_02: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_02: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_02: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_02: Kiem tra dich vu");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước internet ADSL");

		log.info("TC_02: Kiem tra nha cung cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.NETWORK[0]);

		log.info("TC_02: Kiem tra ma khach hang");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase().toUpperCase());

		log.info("TC_02: Check so tien giao dich");
		verifyEquals(convertAvailableBalanceCurrentcyOrFeeToLong(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch")), amount);

		log.info("TC_02: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Thanh toán hóa đơn");

		log.info("TC_02: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TTC_02: Chon button back");
		ADSL.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_02: Click button home");
		ADSL.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_03_ThanhToanCuocViettelXacThucOTP() {
		log.info("TC_03_Step_Click cuoc ADSL");
		ADSL.scrollDownToText(driver, "Thanh toán tiền nước");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Cước Internet ADSL");

		log.info("TC_03_Step_Select tai khoan nguon");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_03_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextByLabel(driver, "Số dư khả dụng"));

		log.info("TC_03_Step_Thong tin giao dich chon Viettel");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[0]);
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[0]);

		log.info("TC_03_Input ma khach hang");
		ADSL.inputCustomerCode(Internet_ADSL_Data.Valid_Account.CODEVIETTEL);

		log.info("TC_03_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_03_Kiem tra tai khoan nguon");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_03_Kiem tra dich vu");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước internet ADSL");

		log.info("TC_03_Kiem tra nha cung cap");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.NETWORK[0]);

		log.info("TC_03_Kiem tra ma khach hang");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_03_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số tiền thanh toán"));

		log.info("TC_03_Chon phuong thuc xac thuc");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_03_verify so tien phi");
		feeView = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số tiền phí"));
		verifyEquals(feeView, fee);

		log.info("TC_03_Click Tiep tuc");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_Nhap ma xac thuc");
		ADSL.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_03_Step_Tiep tuc");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Verify message thanh cong");
		verifyEquals(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_03_Verify so tien thanh toan");
		amountView = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"));
		verifyEquals(amountView, amount);

		log.info("TC_03_get thoi gian giao dich thanh cong");
		transferTime = ADSL.getDynamicTransferTimeAndMoney(driver, "GIAO DỊCH THÀNH CÔNG", "4").split(" ")[3];

		log.info("TC_03_Kiem tra dich vu");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước internet ADSL");

		log.info("TC_03_Kiem tra nha cung cap");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.NETWORK[0]);

		log.info("TC_03_Kiem tra ma khach hang");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_03_Step_:Lay ma giao dich");
		transactionNumber = ADSL.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_03_Step_: Chon thuc hien giao dich");
		ADSL.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_03_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_03_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_03_Step_:Check so du kha dung sau khi chuyen tien");
		amountAfter = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_03_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amount - fee, amountAfter);
	}

	@Test
	public void TC_04_Report_ThanhToanCuocViettelXacThucOTP() {
		log.info("TC_04_Step: Click back man hinh home");
		ADSL.clickImageBack( "Cước internet ADSL");

		log.info("TC_04_Step: Click menu header");
		ADSL.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_04_Step: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_04_Step: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán hóa đơn");

		log.info("TC_04_Step: Chon so tai khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_Step: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_04_Step: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_04: Step: verify thoi tim kiem den ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_04_Step: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_04: Step: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04: Step: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_04: Step: Check so tien chuyen");
		System.out.print(amount);
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(amount + "") + " VND"));

		log.info("TC_04: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_04: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_04: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_04: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_04: Kiem tra dich vu");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước internet ADSL");

		log.info("TC_04: Kiem tra nha cung cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.NETWORK[0]);

		log.info("TC_04: Kiem tra ma khach hang");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_04: Check so tien giao dich");
		verifyEquals(convertAvailableBalanceCurrentcyOrFeeToLong(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch")), amount);

		log.info("TC_04: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Thanh toán hóa đơn");

		log.info("TC_04: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TTC_04: Chon button back");
		ADSL.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_04: Click button home");
		ADSL.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	 @Test
	public void TC_05_ThanhToanCuocFPTXacThucMatKhau() {
		log.info("TC_05_Step_Click cuoc ADSL");
		ADSL.scrollDownToText(driver, "Thanh toán tiền nước");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Cước Internet ADSL");

		log.info("TC_05_Step_Select tai khoan nguon");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_05_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextByLabel(driver, "Số dư khả dụng"));

		log.info("TC_05_Step_Thong tin giao dich chon FPT");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[0]);
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[1]);

		log.info("TC_05_Input ma khach hang");
		ADSL.inputCustomerCode(Internet_ADSL_Data.Valid_Account.CODEFPT);

		log.info("TC_05_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_05_Kiem tra tai khoan nguon");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_05_Kiem tra dich vu");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước internet ADSL");

		log.info("TC_05_Kiem tra nha cung cap");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.NETWORK[1]);

		log.info("TC_05_Kiem tra ma khach hang");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_05_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số tiền thanh toán"));

		log.info("TC_05_Chon phuong thuc xac thuc");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Mật khẩu đăng nhập"));
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_05_verify so tien phi");
		feeView = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số tiền phí"));
		verifyEquals(feeView, fee);

		log.info("TC_05_Click Tiep tuc");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_Nhap ma xac thuc");
		ADSL.inputToDynamicPopupPasswordInput(driver, passLogin, "Tiếp tục");

		log.info("TC_05_Step_Tiep tuc");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Verify message thanh cong");
		verifyEquals(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_05_Verify so tien thanh toan");
		amountView = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"));
		verifyEquals(amountView, amount);

		log.info("TC_05_get thoi gian giao dich thanh cong");
		transferTime = ADSL.getDynamicTransferTimeAndMoney(driver, "GIAO DỊCH THÀNH CÔNG", "4").split(" ")[3];

		log.info("TC_05_Kiem tra dich vu");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước internet ADSL");

		log.info("TC_05_Kiem tra nha cung cap");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.NETWORK[1]);

		log.info("TC_05_Kiem tra ma khach hang");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_05_Step_:Lay ma giao dich");
		transactionNumber = ADSL.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_05_Step_: Chon thuc hien giao dich");
		ADSL.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_05_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_05_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_05_Step_:Check so du kha dung sau khi chuyen tien");
		amountAfter = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_05_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amount - fee, amountAfter);
	}

 @Test
	public void TC_06_Report_ThanhToanCuocFPTXacThucMatKhau() {
		log.info("TC_06_Step: Click back man hinh home");
		ADSL.clickImageBack( "Cước internet ADSL");

		log.info("TC_06_Step: Click menu header");
		ADSL.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06_Step: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_06_Step: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_06_Step: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán hóa đơn");

		log.info("TC_06_Step: Chon so tai khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_Step: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_06_Step: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_06: Step: verify thoi tim kiem den ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_06_Step: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_06: Step: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06: Step: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_06: Step: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(amount + "") + " VND"));

		log.info("TC_06: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_06: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_06: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_06: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_06: Kiem tra dich vu");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước internet ADSL");

		log.info("TC_06: Kiem tra nha cung cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.NETWORK[1]);

		log.info("TC_06: Kiem tra ma khach hang");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_06: Check so tien giao dich");
		verifyEquals(convertAvailableBalanceCurrentcyOrFeeToLong(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch")), amount);

		log.info("TC_06: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Thanh toán hóa đơn");

		log.info("TC_06: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TTC_06: Chon button back");
		ADSL.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_06: Click button home");
		ADSL.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	 @Test
	public void TC_07_ThanhToanCuocFPTXacThucOTP() {
		log.info("TC_07_Step_Click cuoc ADSL");
		ADSL.scrollDownToText(driver, "Thanh toán tiền nước");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Cước Internet ADSL");

		log.info("TC_07_Step_Select tai khoan nguon");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_07_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextByLabel(driver, "Số dư khả dụng"));

		log.info("TC_07_Step_Thong tin giao dich chon Viettel");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[0]);
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[1]);

		log.info("TC_07_Input ma khach hang");
		ADSL.inputCustomerCode(Internet_ADSL_Data.Valid_Account.CODEFPT);

		log.info("TC_07_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_07_Kiem tra tai khoan nguon");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_07_Kiem tra dich vu");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước internet ADSL");

		log.info("TC_07_Kiem tra nha cung cap");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.NETWORK[0]);

		log.info("TC_07_Kiem tra ma khach hang");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_07_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số tiền thanh toán"));

		log.info("TC_07_Chon phuong thuc xac thuc");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_07_verify so tien phi");
		feeView = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số tiền phí"));
		verifyEquals(feeView, fee);

		log.info("TC_07_Click Tiep tuc");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_Step_Nhap ma xac thuc");
		ADSL.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_07_Step_Tiep tuc");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_Verify message thanh cong");
		verifyEquals(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_07_Verify so tien thanh toan");
		amountView = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"));
		verifyEquals(amountView, amount);

		log.info("TC_07_get thoi gian giao dich thanh cong");
		transferTime = ADSL.getDynamicTransferTimeAndMoney(driver, "GIAO DỊCH THÀNH CÔNG", "4").split(" ")[3];

		log.info("TC_07_Kiem tra dich vu");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước internet ADSL");

		log.info("TC_07_Kiem tra nha cung cap");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.NETWORK[1]);

		log.info("TC_07_Kiem tra ma khach hang");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_07_Step_:Lay ma giao dich");
		transactionNumber = ADSL.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_07_Step_: Chon thuc hien giao dich");
		ADSL.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_07_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_07_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_07_Step_:Check so du kha dung sau khi chuyen tien");
		amountAfter = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_07_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amount - fee, amountAfter);
	}

	 @Test
	public void TC_08_Report_ThanhToanCuocFPTXacThucOTP() {
		log.info("TC_08_Step: Click back man hinh home");
		ADSL.clickImageBack( "Cước internet ADSL");

		log.info("TC_08_Step: Click menu header");
		ADSL.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_08_Step: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_08_Step: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_08_Step: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán hóa đơn");

		log.info("TC_08_Step: Chon so tai khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08_Step: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_08_Step: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_08: Step: verify thoi tim kiem den ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_08_Step: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_08: Step: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08: Step: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_08: Step: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(amount + "") + " VND"));

		log.info("TC_08: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_08: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_08: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_08: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_08: Kiem tra dich vu");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước internet ADSL");

		log.info("TC_08: Kiem tra nha cung cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.NETWORK[1]);

		log.info("TC_08: Kiem tra ma khach hang");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_08: Check so tien giao dich");
		verifyEquals(convertAvailableBalanceCurrentcyOrFeeToLong(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch")), amount);

		log.info("TC_08: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Thanh toán hóa đơn");

		log.info("TC_08: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TTC_08: Chon button back");
		ADSL.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_08: Click button home");
		ADSL.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

}
