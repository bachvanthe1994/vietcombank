package vnpay.vietcombank.transfer_money_quick_247;

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
import pageObjects.TransferMoneyObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Flow_QuickMoneyTransfer247_Part2 extends Base {
	AppiumDriver<MobileElement> driver;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyObject transferMoney;
	private String transactionNumber;
	private String amountStartString;
	private long amountStart;
	private String amountTranferString;
	private long amountTranfer;
	private String costTranferString;
	private long costTranfer;
	private String accountStart;
	private long fee;
	private String exchangeRate;
	private String password = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login("0904797864", pass, opt);
		password = pass;
		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
	}

//	@Test
	public void TC_09_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
		log.info("TC_09_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_09_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_09_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_09_Step_Get so tai khoan nguon");
		accountStart = transferMoney.getDynamicTextByLabel(driver, "Tài khoản nguồn");

		log.info("TC_09_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		log.info("TC_09_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_09_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.CARD_TO, "Nhập/chọn số thẻ");

		log.info("TC_09_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_09_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_09_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_09_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoney.getDynamicTextInTransactionDetail(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]));
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_09_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền").replaceAll("\\D+", "");

		log.info("TC_09_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_09_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_09_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_09_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_09_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_09_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_09_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_09_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4").split(" ")[3];

		log.info("TC_09_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_09_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_09_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Account_Data.Valid_Account.CARD_TO);

		log.info("TC_09_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_09_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_09_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_09_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_09_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_09_Step_:Check so du kha dung sau khi chuyen tien");
		System.out.print("amountStart =" + amountStart + "-------amountTranfer =" + amountTranfer + "-------costTranfer =" + costTranfer);
		verifyEquals(amountStart - amountTranfer, amountAfter);
	}

	//@Test
	public void TC_10_ReportChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_10: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_10: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_10: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_10: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_10: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển nhanh qua số thẻ");

		log.info("TC_10: Chon so tai khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_10: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, accountStart);

		log.info("TC_10: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_10: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_10: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_10_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_10: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_10: Check so tien chuyen");// lỗi app
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_10: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_10: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_10: So the chuyen di");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số thẻ chuyển đi"), Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_10: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), accountStart);

		log.info("TC_10: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số thẻ chuyển đến/ VND"), Account_Data.Valid_Account.CARD_TO);

		log.info("TC_10: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_10: Check so tien giao dich");// Lỗi app
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_10_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_10_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_10: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_10: click button back");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_10: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_10_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	//@Test
//Lỗi app, số tiền bị thiếu đơn vị USD
	public void TC_11_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangOTP() {
		log.info("TC_11_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_11_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_11_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[1]);

		log.info("TC_11_Step_Get so tai khoan nguon");
		accountStart = transferMoney.getDynamicTextByLabel(driver, "Tài khoản nguồn");

		log.info("TC_04_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, "Số dư khả dụng");
		double amountStart = convertMoneyToDouble(amountStartString, "USD");

		log.info("TC_11_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.CARD_TO, "Nhập/chọn số thẻ");

		log.info("TC_11_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, "Số tiền");

		log.info("TC_11_Step_:Lay so tien quy doi ra VND");
		String exchange = transferMoney.getDynamicTextInTransactionDetail(driver, "Tỷ giá quy đổi tham khảo").split(" ~ ", 2)[1];
		exchangeRate = exchange.replace(" VND", "");

		log.info("TC_11_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_11_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_11_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_11_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền").replaceAll(".00 USD", "");

		log.info("TC_11_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY_USD);

		log.info("TC_11_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_11_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoney.getDynamicTextInTransactionDetail(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]));
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_11_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_11_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_11_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_11_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_11_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_11_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_11_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_11_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4").split(" ")[3];

		log.info("TC_11_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_11_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_11_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Account_Data.Valid_Account.CARD_TO);

		log.info("TC_11_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_11_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_11_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_11_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[1]);

		log.info("TC_11: Lay so du kha dung the");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double amountAfter = convertMoneyToDouble(amountAfterString, "USD");
		double amountExpect = Math.round((amountAfter) * 100) / 100;

		log.info("TC_11: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, " USD");

		log.info("TC_11: Kiem tra so du tai khoan USD sau khi chuyen tien");
		double amountAtual = Math.round((amountStart - transferMoney) * 100) / 100;
		verifyEquals(amountAtual, amountExpect);
	}

	//@Test
	public void TC_12_ReportChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangOTP() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_12: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_12: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_12: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_12: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_12: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển nhanh qua số thẻ");

		log.info("TC_12: Chon so tai khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_12: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, accountStart);

		log.info("TC_12: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_12: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_12: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_12: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_12: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY_USD) + ".00 USD"));

		log.info("TC_12: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_12: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_12: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_12: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_12: So the chuyen di");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số thẻ chuyển đi"), Account_Data.Valid_Account.LIST_CARD_FROM[1]);

		log.info("TC_12: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), accountStart);

		log.info("TC_12: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số thẻ chuyển đến/ VND"), Account_Data.Valid_Account.CARD_TO);

		log.info("TC_12: Check ten nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_12: Check so tien giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch"), TransferMoneyQuick_Data.TransferQuick.MONEY_USD + ".00 USD");

		log.info("TC_12: So tien quy doi expect");
		double amountExpect = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, "USD") * convertMoneyToDouble(exchangeRate, "VND");

		log.info("TC_12: So tien quy doi atual");
		String amountAfter1 = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").replaceAll(".00 USD", "");
		double amountActual = convertMoneyToDouble(amountAfter1, "VND") * convertMoneyToDouble(exchangeRate, "VND");

		log.info("TC_12: verify so tien quy doi");
		verifyEquals(amountActual, amountExpect);

		log.info("TC_12_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_12_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_12: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_12: click button back");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_12: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_12_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_13_ChuyenTienQuaTheNguoiNhanTRaPhiVNDVaXacThucBangMatKhau() {
		log.info("TC_13_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_13_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_13_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_13_Step_Get so tai khoan nguon");
		accountStart = transferMoney.getDynamicTextByLabel(driver, "Tài khoản nguồn");

		log.info("TC_13_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		log.info("TC_13_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_13_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.CARD_TO, "Nhập/chọn số thẻ");

		log.info("TC_13_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_13_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_13_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_13_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoney.getDynamicTextInTransactionDetail(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]));
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_13_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền").replaceAll("\\D+", "");

		log.info("TC_13_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_13_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_13_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_13_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_13_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_13_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicPopupPasswordInput(driver, password, "Tiếp tục");

		log.info("TC_13_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_13_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4").split(" ")[3];

		log.info("TC_13_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_13_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_13_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Account_Data.Valid_Account.CARD_TO);

		log.info("TC_13_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_13_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_13_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_13_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_13_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_13_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amountTranfer, amountAfter);
	}

	@Test
	// App lỗi số tiền giao dịch, hiện tiền VND hiển thị dạng số thập phân VD:
	// 10000.00 VND => tiền ghi bị sai, ghi là "Một triệu đồng", expect ghi "mười
	// nghìn đồng"

	public void TC_14_ReportChuyenTienQuaTheNguoiNhanTRaPhiVNDVaXacThucBangMatKhau() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_14: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_14: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_14: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_14: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_14: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển nhanh qua số thẻ");

		log.info("TC_14: Chon so tai khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_14: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, accountStart);

		log.info("TC_14: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_14: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_14: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_14_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_14: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_14: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_14: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_14: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_14: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_14: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_14: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_14: So the chuyen di");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số thẻ chuyển đi"), Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_14: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), accountStart);

		log.info("TC_14: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số thẻ chuyển đến/ VND"), Account_Data.Valid_Account.CARD_TO);

		log.info("TC_14: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_14: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_14_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_14_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_14: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_14: click button back");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_14: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_14_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	// Lỗi app, số tiền bị bỏ đơn vị USD
	public void TC_15_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangMatKhau() {
		log.info("TC_15_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_15_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_15_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[1]);

		log.info("TC_15_Step_Get so tai khoan nguon");
		accountStart = transferMoney.getDynamicTextByLabel(driver, "Tài khoản nguồn");

		log.info("TC_15_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, "Số dư khả dụng");
		double amountStart = convertMoneyToDouble(amountStartString, "USD");

		log.info("TC_15_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.CARD_TO, "Nhập/chọn số thẻ");

		log.info("TC_15_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, "Số tiền");

		log.info("TC_15_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_15_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_15_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_15_Step_Lay gia tri so tien chuyen");// Loi app, số tiền bỏ đơn vị USD
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền").replaceAll(".00 USD", "");

		log.info("TC_15_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY_USD);

		log.info("TC_15_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_15_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoney.getDynamicTextInTransactionDetail(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]));
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_15_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");

		double usdTransferFee = convertMoneyToDouble(fee + "", "VND") / convertMoneyToDouble(exchangeRate, "VND");

		log.info("TC_15_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee);

		log.info("TC_15_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_15_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_15_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicPopupPasswordInput(driver, password, "Tiếp tục");

		log.info("TC_15_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_15_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_15_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4").split(" ")[3];

		log.info("TC_15_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_15_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_15_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Account_Data.Valid_Account.CARD_TO);

		log.info("TC_15_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_15_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_15_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_15_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[1]);

		log.info("TC_15: Lay so du kha dung the");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double amountAfter = convertMoneyToDouble(amountAfterString, "USD");
		double amountExpect = Math.round((amountAfter) * 100) / 100;

		log.info("TC_15: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, " USD");

		log.info("TC_15: Kiem tra so du tai khoan USD sau khi chuyen tien");
		double amountAtual = Math.round((amountStart - transferMoney) * 100) / 100;
		verifyEquals(amountAtual, amountExpect);
	}

	@Test
	public void TC_16_ReportChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangMatKhau() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_16: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_16: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_16: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_16: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_16: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển nhanh qua số thẻ");

		log.info("TC_16: Chon so tai khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_16: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, accountStart);

		log.info("TC_16: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_16: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_16: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_16_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_16: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_16: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_16: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY_USD) + ".00 USD"));

		log.info("TC_16: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_16: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_16: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_16: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_16: So the chuyen di");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số thẻ chuyển đi"), Account_Data.Valid_Account.LIST_CARD_FROM[1]);

		log.info("TC_16: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), accountStart);

		log.info("TC_16: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số thẻ chuyển đến/ VND"), Account_Data.Valid_Account.CARD_TO);

		log.info("TC_16: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_16: Check so tien giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch"), TransferMoneyQuick_Data.TransferQuick.MONEY_USD + ".00 USD");

		log.info("TC_16: So tien quy doi expect");
		double amountAfter = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, "USD");
		double amountExpect = amountAfter * convertMoneyToDouble(exchangeRate, "VND");

		log.info("TC_16: So tien quy doi atual");
		String amountAfter1 = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").replaceAll(".00 USD", "");
		double amountActual = convertMoneyToDouble(amountAfter1, "VND") * convertMoneyToDouble(exchangeRate, "VND");

		log.info("TC_16: verify so tien quy doi");
		verifyEquals(amountActual, amountExpect);

		log.info("TC_16_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_16_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_16: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_16: click button back");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_16: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_16_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}
}