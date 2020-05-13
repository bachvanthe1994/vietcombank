package vnpay.vietcombank.payQRCode;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.OrderQRCode_Type1_Info;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.QRCodePageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;

public class Flow_PayQRCode_Part_2 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private QRCodePageObject payQRCode;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;
	long transferFee = 0;
	String password = "";

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
		
		password = pass;

		homePage = PageFactoryManager.getHomePageObject(driver);
		payQRCode = PageFactoryManager.getQRCodePageObject(driver);

	}

	private long surplus, availableBalance, actualAvailableBalance;

	String codeOrder = "";
	@Test
	public void TC_01_ThanhToanHoaDon_VCB_Type1_QRCode() {
		log.info("TC_01_1_Click QR Pay");
		homePage.sleep(driver, 3000);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "QR Pay");

		log.info("TC_01_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, "Thư viện ảnh");

		log.info("TC_01_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, "Hiển thị gốc");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, "Bản tải xuống");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, "VCB_Type 1");

		log.info("TC_01_4_Lay so luong anh");
		int numberOfImage = payQRCode.getNumberOfImageInLibrary();;

		log.info("TC_01_5_Chon anh");
		OrderQRCode_Type1_Info qrCode = payQRCode.chooseQRCodeType1(numberOfImage, Account_Data.Valid_Account.ACCOUNT1);
		String destinationPlace = qrCode.destinationPlace;
		String namePlace = qrCode.namePlace;
		String codePlace = qrCode.codePlace;

		log.info("TC_01_6_Lay so du");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(qrCode.surplus);

		log.info("TC_01_11_Kiem tra man hinh xac nhan thong tin");
		payQRCode.scrollUpToText(driver, "Tài khoản nguồn");

		log.info("TC_01_11_1_Kiem tra tai khoan nguon");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_01_11_2_Kiem tra thanh toan cho");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Thanh toán cho"), destinationPlace);

		log.info("TC_01_11_3_Kiem tra ten diem ban");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Điểm bán"), namePlace);

		log.info("TC_01_11_4_Kiem tra thanh toan cho");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Mã điểm bán"), codePlace);

		codeOrder = payQRCode.getDynamicTextInTransactionDetail(driver, "Số hóa đơn");

		log.info("TC_01_11_5_Kiem tra so tien");
		verifyTrue(payQRCode.getDynamicTextInTransactionDetail(driver, "Số tiền cần thanh toán").contains(addCommasToLong("100000") + " VND"));

		log.info("TC_01_12_Chon phuong thuc xac thuc");
		payQRCode.scrollDownToText(driver, "Chọn phương thức xác thực");
		payQRCode.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_01_12_01_Kiem tra so tien phi");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_01_13_Click Tiep tuc");
		payQRCode.clickToDynamicButton(driver, "Tiếp tục");

		payQRCode.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		payQRCode.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_14: Kiem  tra giao dich thanh cong");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, "GIAO DỊCH THÀNH CÔNG"));

		log.info("TC_01_15: Kiem  tra so tien giao dich");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, addCommasToLong("100000") + " VND"));

		log.info("TC_01_16: Lay thoi gian tao giao dich");
		transferTime = payQRCode.getTransferTimeSuccess(driver,  "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_01_17: Lay ma giao dich");
		transactionNumber = payQRCode.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_18: Click thuc hien giao dich moi");
		payQRCode.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_19_Chon tai khoan nguon, kiem tra tai khoan chuyen bi tru tien");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		payQRCode.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");
		payQRCode.scrollUpToText(driver, "Tài khoản nguồn");
		payQRCode.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);
		payQRCode.scrollUpToText(driver, "Tài khoản nguồn");
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = canculateAvailableBalances(surplus, Long.parseLong("100000"), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_02_ThanhToanHoaDon_VCB_Type1_QRCode_BaoCao() {
		log.info("TC_02_01: Click quay lai");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_02: Click vao Menu Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_02_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_02_05: Chon Thanh toan QR Code");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán QR Code");

		log.info("TC_02_06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_07: Chon tai khoan nguon");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_02_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02_09: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_02_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").contains("QR Pay"));

		log.info("TC_02_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvContent"), ("- 100,000 VND"));

		log.info("TC_02_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_02_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_02_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_02_17: Kiem tra so tai khoan ghi co");

		log.info("TC_02_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains("100,000 VND"));

		log.info("TC_02_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Thanh toán QR Code");

		String note = "MBVCB" + transactionNumber + ".QR Pay.Thanh toan cho " + codeOrder; 
		log.info("TC_02_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(note));

		log.info("TC_02_24: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_02_25: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_02_26: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
		
	}
	
	@Test
	public void TC_03_ThanhToanHoaDon_VCB_Type3_QRCode() {
		log.info("TC_03_1_Click QR Pay");
		homePage.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, "QR Pay");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "QR Pay");

		log.info("TC_03_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, "Thư viện ảnh");

		log.info("TC_03_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, "Hiển thị gốc");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, "Bản tải xuống");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, "Type 3");

		log.info("TC_03_4_Lay so luong anh");
		int numberOfImage = payQRCode.getNumberOfImageInLibrary();

		log.info("TC_03_5_Chon anh");
		OrderQRCode_Type1_Info qrCode = payQRCode.chooseQRCodeType3(numberOfImage, Account_Data.Valid_Account.ACCOUNT1);
		String destinationPlace = qrCode.destinationPlace;
		String namePlace = qrCode.namePlace;

		log.info("TC_03_7_Lay so du");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(qrCode.surplus);
		
		log.info("TC_03_8_Nhap thong tin lien he");
		payQRCode.inputContactInfomation();

		log.info("TC_03_09_Kiem tra man hinh xac nhan thong tin");
		payQRCode.scrollUpToText(driver, "Tài khoản nguồn");

		log.info("TC_03_09_1_Kiem tra tai khoan nguon");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_03_09_2_Kiem tra thanh toan cho");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Thanh toán cho"), destinationPlace);
		
		log.info("TC_03_09_3_Kiem tra ten diem ban");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Điểm bán"), namePlace);

		long money = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, "Tổng tiền thanh toán"));

		log.info("TC_03_12_Chon phuong thuc xac thuc");
		payQRCode.scrollDownToText(driver, "Chọn phương thức xác thực");
		payQRCode.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_03_11_Kiem tra so tien phi");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_03_12_Click Tiep tuc");
		payQRCode.clickToDynamicButton(driver, "Tiếp tục");

		payQRCode.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		payQRCode.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_13: Kiem  tra giao dich thanh cong");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, "GIAO DỊCH THÀNH CÔNG"));

		log.info("TC_03_14: Kiem  tra so tien giao dich");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, addCommasToLong(money + "") + " VND"));

		log.info("TC_03_15: Lay thoi gian tao giao dich");
		transferTime = payQRCode.getTransferTimeSuccess(driver, "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_03_16: Lay ma giao dich");
		transactionNumber = payQRCode.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_03_17_Kiem tra nha cung cap");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Thanh toán cho"), destinationPlace);

		log.info("TC_03_18_Kiem tra ten dich vu");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Tên điểm bán"), namePlace);

		log.info("TC_03_19: Click thuc hien giao dich moi");
		payQRCode.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_03_20_Chon tai khoan nguon, kiem tra tai khoan chuyen bi tru tien");
		payQRCode.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");
		payQRCode.scrollUpToText(driver, "Tài khoản nguồn");
		payQRCode.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);
		payQRCode.scrollUpToText(driver, "Tài khoản nguồn");
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = canculateAvailableBalances(surplus, money, transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_04_ThanhToanHoaDon_VCB_Type3_QRCode_BaoCao() {
		log.info("TC_04_01: Click quay lai");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_04_02: Click vao Menu Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_04_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_04_05: Chon Thanh toan QR Code");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán QR Code");

		log.info("TC_04_06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_07: Chon tai khoan nguon");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_04_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_04_09: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		
		log.info("TC_04_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_04_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals("QR Pay"));

		log.info("TC_04_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvContent"), ("- 100,000 VND"));

		log.info("TC_04_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_04_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_04_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_04_17: Kiem tra so tai khoan ghi co");

		log.info("TC_04_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains("100,000 VND"));

		log.info("TC_04_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Thanh toán QR Code");

		String note = "MBVCB" + transactionNumber + ".QR Pay.Thanh toan cho " + codeOrder; 
		log.info("TC_04_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(note));

		log.info("TC_04_24: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_04_25: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_04_26: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
