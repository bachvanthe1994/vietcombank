//package vnpay.vietcombank.payQRCode;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//import commons.Base;
//import commons.PageFactoryManager;
//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.MobileElement;
//import model.OrderQRCode_Type1_Info;
//import pageObjects.HomePageObject;
//import pageObjects.LogInPageObject;
//import pageObjects.QRCodePageObject;
//import pageObjects.TransactionReportPageObject;
//import vietcombank_test_data.Account_Data;
//import vietcombank_test_data.LogIn_Data;
//import vietcombank_test_data.TransferMoneyInVCB_Data;
//
//public class Flow_PayQRCode_Part_3 extends Base {
//	AppiumDriver<MobileElement> driver;
//	private LogInPageObject login;
//	private HomePageObject homePage;
//	private QRCodePageObject payQRCode;
//	private TransactionReportPageObject transReport;
//	private String transferTime;
//	private String transactionNumber;
//	long transferFee = 0;
//	String password, device, id, urlServer, phoneNumber, otpNumber = "";
//
//	@Parameters({ "deviceName", "deviceUDID", "hubURL", "phone", "pass", "otp" })
//	@BeforeClass
//	public void beforeClass(String deviceName, String udid, String url, String phone, String pass, String opt)
//			throws IOException, InterruptedException {
//		startServer();
//		
//		log.info("Before class: Mo app ");
//		driver = openIOSApp(deviceName, udid, url);
//		login = PageFactoryManager.getLoginPageObject(driver);
//		log.info("Before class: Log in ");
//		login.Global_login(phone, pass, opt);
//		password = pass;
//		device = deviceName;
//		id = udid;
//		urlServer = url;
//		phoneNumber = phone;
//		otpNumber = opt;
//		
//		log.info("TC_00_1_Tao ma QR code type 4");
//		driver = openSafari(deviceName, udid, url);
//		homePage = PageFactoryManager.getHomePageObject(driver);
//		payQRCode = PageFactoryManager.getQRCodePageObject(driver);
//		
//		payQRCode.clickToDynamicButton(driver, "URL");
//		payQRCode.inputToDynamicInputBoxByName(driver, "https://sandbox.vnpayment.vn/tryitnow/Home/CreateOrder", "URL");
//		payQRCode.clickToDynamicButton(driver, "Go");
//		payQRCode.scrollIDownOneTime(driver);
//		payQRCode.clickToOtherElementByValue("Không chọn");
//		payQRCode.inputToDynamicTextIntoTypePicker("VNPAYQR");
//		payQRCode.clickToOtherElementByValue("Tiếng Việt");
//		payQRCode.inputToDynamicTextIntoTypePicker("Tiếng Việt");
//		payQRCode.clickToDynamicButton(driver, "Thanh toán Popup");
//		payQRCode.clickToDynamicIconByName(driver, "VCB Mobile-B@anking");
//		payQRCode.clickToDynamicButton(driver, "Mở");
//
//	}
//
//	private long surplus, availableBalance, actualAvailableBalance;
//
//	String codeOrder = "";
//	long money = 0;
//	@Test
//	public void TC_01_ThanhToanHoaDon_Type4_QRCode() {
//		log.info("TC_01_1_Chon tai khoan nguon");
//		payQRCode.clickToDynamicDropDown(driver, "Tài khoản nguồn");
//		payQRCode.clickToDynamTextByCell(driver, Account_Data.Valid_Account.ACCOUNT1);
//
//		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
//		
//		log.info("TC_01_2_Lay thong tin hoa don");
//		String destinationPlace = payQRCode.getDynamicTextInTransactionDetail(driver, "Thanh toán cho");
//		String namePlace = payQRCode.getDynamicTextInTransactionDetail(driver, "Tên điểm bán");
//		codeOrder = payQRCode.getDynamicTextInTransactionDetail(driver, "Số hóa đơn");
//		money = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, "Số tiền thanh toán"));
//		
//		log.info("TC_01_3_Kiem tra mo ta");
//		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Mô tả"), "Thanh toan Qrcode");
//		
//		log.info("TC_01_4_Click Thanh toan");
//		payQRCode.clickToDynamicButton(driver, "Thanh toán");
//		
//		log.info("TC_01_11_Kiem tra man hinh xac nhan thong tin");
//		payQRCode.scrollUpToText(driver, "Tài khoản nguồn");
//
//		log.info("TC_01_11_1_Kiem tra tai khoan nguon");
//		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT1);
//
//		log.info("TC_01_11_2_Kiem tra thanh toan cho");
//		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Thanh toán cho"), destinationPlace);
//
//		log.info("TC_01_11_3_Kiem tra ten diem ban");
//		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Tên điểm bán"), namePlace);
//
//		log.info("TC_01_11_4_Kiem tra thanh toan cho");
//		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Số hóa đơn"), codeOrder);
//
//		log.info("TC_01_11_5_Kiem tra so tien");
//		verifyTrue(payQRCode.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(money + "") + " VND"));
//
//		log.info("TC_01_12_Chon phuong thuc xac thuc");
//		payQRCode.scrollDownToText(driver, "Chọn phương thức xác thực");
//		payQRCode.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
//		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
//		payQRCode.clickToDynamTextByCell(driver, "SMS OTP");
//
//		log.info("TC_01_12_01_Kiem tra so tien phi");
//		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");
//
//		log.info("TC_01_13_Click Tiep tuc");
//		payQRCode.clickToDynamicButton(driver, "Tiếp tục");
//
//		payQRCode.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
//
//		payQRCode.clickToDynamicButtonVisible(driver, "Tiếp tục");
//
//		log.info("TC_01_15: Kiem  tra so tien giao dich");
//		verifyTrue(payQRCode.isDynamicTextDisplayed(driver, addCommasToLong(money + "") + " VND"));
//
//		log.info("TC_01_16: Lay ma giao dich");
//		transactionNumber = payQRCode.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
//
//	}
//
//	@Test
//	public void TC_02_ThanhToanHoaDon_Type4_QRCode_BaoCao() throws MalformedURLException {
//		log.info("TC_02_00: Mo app va login");
//		driver = openIOSApp(device, id, urlServer);
//		login = PageFactoryManager.getLoginPageObject(driver);
//		log.info("Before class: Log in ");
//		login.Global_login(phoneNumber, password, otpNumber);
//		
//		log.info("TC_02_02: Click vao Menu Icon");
//		homePage.clickToMenuIcon(driver);
//
//		log.info("TC_02_03: Click Bao Cao Dao Dich");
//		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
//		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
//
//		log.info("TC_02_04: Click Tat Ca Cac Loai Giao Dich");
//		transReport.clickToDynamicButtonByText(driver, "Tất cả các loại giao dịch");
//
//		log.info("TC_02_05: Chon Thanh toan QR Code");
//		transReport.clickToDynamTextByCell(driver, "Thanh toán QR Code");
//
//		log.info("TC_02_06: Click Chon Tai Khoan");
//		transReport.clickToDynamicSourceAccountInReport(driver, "Thanh toán QR Code");
//		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, Account_Data.Valid_Account.ACCOUNT2));
//
//		log.info("TC_02_07: Chon tai khoan nguon");
//		transReport.clickToDynamTextByCell(driver, Account_Data.Valid_Account.ACCOUNT1);
//		availableBalance = canculateAvailableBalances(surplus, money, transferFee);
//		verifyEquals(actualAvailableBalance, availableBalance);
//		
//
//		log.info("TC_02_08: CLick Tim Kiem");
//		transReport.clickToDynamicButtonVisible(driver, "Tìm kiếm");
//
//		log.info("TC_02_09: Lay ngay tao giao dich hien thi");
//		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, 0);
//
//		log.info("TC_02_10: Kiem tra ngay tao giao dich hien thi");
//		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), convertTransferTimeToReportDateTime(transferTime));
//
//		log.info("TC_02_11: Kiem tra noi dung hien thi");
//		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, 1).contains("QR Pay"), "QR Pay", transReport.getTextInDynamicTransactionInReport(driver, 1));
//
//		log.info("TC_02_12: Kiem tra so tien chuyen hien thi");
//		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, 2), ("- 100,000 VND"));
//
//		log.info("TC_02_13: Click vao giao dich");
//		transReport.clickToDynamicTransactionInReport(driver);
//
//		log.info("TC_02_14: Kiem tra thoi gian tao giao dich hien thi");
//		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
//		verifyEquals(reportTime1, transferTimeInReport);
//
//		log.info("TC_02_15: Kiem tra thoi gian tao giao dich hien thi");
//		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);
//
//		log.info("TC_02_16: Kiem tra so tai khoan trich no");
//		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản trích nợ"), Account_Data.Valid_Account.ACCOUNT2);
//
//		log.info("TC_02_17: Kiem tra so tai khoan ghi co");
//
//		log.info("TC_02_18: Kiem tra so tien giao dich hien thi");
//		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains("100,000 VND"), "100,000 VND", transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch"));
//
//		log.info("TC_02_22: Kiem tra loai giao dich");
//		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Thanh toán QR Code");
//
//		String note = "MBVCB" + transactionNumber + ".QR Pay.Thanh toan cho " + codeOrder; 
//		log.info("TC_02_23: Kiem Tra noi dung giao dich");
//		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(note), note, transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch"));
//
//		log.info("TC_02_24: Click Back icon");
//		transReport.clickToDynamicButton(driver, "ic header back");
//
//		log.info("TC_02_25: Click quay lai");
//		payQRCode.clickToDynamicButton(driver, "ic header back");
//		payQRCode.clickToHomeIcon(driver);
//		
//	}
//
//	@AfterClass(alwaysRun = true)
//	public void afterClass() {
////		closeApp();
//		service.stop();
//	}
//
//}
