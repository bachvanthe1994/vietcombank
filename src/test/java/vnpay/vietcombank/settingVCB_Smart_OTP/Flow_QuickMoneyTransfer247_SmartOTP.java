package vnpay.vietcombank.settingVCB_Smart_OTP;

import java.io.IOException;
import java.security.GeneralSecurityException;

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
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransferMoneyObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SettingVCBSmartOTP_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Flow_QuickMoneyTransfer247_SmartOTP extends Base {
	AppiumDriver<MobileElement> driver;
	private String transferTime;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyObject transferMoney;
	private SettingVCBSmartOTPPageObject smartOTP;
	private String transactionNumber;
	private String amountStartString;
	private long amountStart;
	private String amountTranferString;
	private long amountTranfer;
	private String costTranferString;
	private long costTranfer;
	private String accountStart;
	private String exchangeRate;
	private String password = "";
	private String nameCustomer;
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName",  "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName,  String pass, String opt) throws IOException, InterruptedException {
		startServer();
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		//login.Global_login("0904797864", pass, opt);
		password = pass;
		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
	}
	

	@Test
	public void TC_01_CaiDatPhuongThucXacThucOTP() throws GeneralSecurityException, IOException  {
		smartOTP.setupSmartOTP("111222", getDataInCell(6));
	}


	@Test
	//Lỗi app với PTXT smart OTP, khi chọn smart OTP hệ thống báo lỗi kết nối gián đoạn
	public void TC_02_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangSmartOTP() throws InterruptedException {
		log.info("TC_02_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_02_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_02_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_02_Step_Get so tai khoan nguon");
		accountStart = transferMoney.getDynamicTextByLabel(driver, "Tài khoản nguồn");

		log.info("TC_02_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		log.info("TC_02_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_02_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.CARD_TO, "Nhập/chọn số thẻ");

		log.info("TC_02_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_02_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[2]);

		log.info("TC_02_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền").replaceAll("\\D+", "");

		log.info("TC_02_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_02_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_02_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_02_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/submit");
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_02_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4").split(" ")[3];

		log.info("TC_02_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_02_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_02_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Account_Data.Valid_Account.CARD_TO);

		log.info("TC_02_Step_: Noi dung");
//verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_02_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_02_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_02_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_02_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_02_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amountTranfer, amountAfter);
	}


 @Test
	//Lỗi app với PTXT smart OTP, khi chọn smart OTP hệ thống báo lỗi kết nối gián đoạn
//Lỗi app, số tiền bị thiếu đơn vị USD
	public void TC_03_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangSmartOTP() throws InterruptedException {
		log.info("TC_03_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_03_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_03_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_03_Step_Get so tai khoan nguon");
		accountStart = transferMoney.getDynamicTextByLabel(driver, "Tài khoản nguồn");

		log.info("TC_03_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, "Số dư khả dụng");
		double amountStart = convertMoneyToDouble(amountStartString, "USD");

		log.info("TC_03_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.CARD_TO, "Nhập/chọn số thẻ");

		log.info("TC_03_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, "Số tiền");

		log.info("TC_03_Step_:Lay so tien quy doi ra VND");
		String exchange = transferMoney.getDynamicTextInTransactionDetail(driver, "Tỷ giá quy đổi tham khảo").split(" ~ ", 2)[1];
		exchangeRate = exchange.replace(" VND", "");

		log.info("TC_03_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_03_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền").replaceAll(".00 USD", "");

		log.info("TC_03_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY_USD);

		log.info("TC_03_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_03_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[2]);

		log.info("TC_03_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_03_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/submit");
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_03_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4").split(" ")[3];

		log.info("TC_03_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_03_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_03_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Account_Data.Valid_Account.CARD_TO);

		log.info("TC_03_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_03_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_03_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_03_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_03: Lay so du kha dung the");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double amountAfter = convertMoneyToDouble(amountAfterString, "USD");
		double amountExpect = Math.round((amountAfter) * 100) / 100;

		log.info("TC_03: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, " USD");

		log.info("TC_03: Kiem tra so du tai khoan USD sau khi chuyen tien");
		double amountAtual = Math.round((amountStart - transferMoney) * 100) / 100;
		verifyEquals(amountAtual, amountExpect);
	}

 


	@Test
	// Lỗi app, số tiền bị bỏ đơn vị USD
	//Lỗi app với PTXT smart OTP, khi chọn smart OTP hệ thống báo lỗi kết nối gián đoạn
	public void TC_04_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangSmartOTP() throws InterruptedException {
		log.info("TC_04_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_04_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_04_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_04_Step_Get so tai khoan nguon");
		accountStart = transferMoney.getDynamicTextByLabel(driver, "Tài khoản nguồn");

		log.info("TC_04_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, "Số dư khả dụng");
		double amountStart = convertMoneyToDouble(amountStartString, "EUR");

		log.info("TC_04_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.CARD_TO, "Nhập/chọn số thẻ");

		log.info("TC_04_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_EUR , "Số tiền");

		log.info("TC_04_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_04_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_04_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_Lay gia tri so tien chuyen");// Loi app, số tiền bỏ đơn vị EUR
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền").replaceAll(".00 EUR", "");

		log.info("TC_04_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY_EUR);

		log.info("TC_04_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_04_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[2]);

		log.info("TC_04_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_04_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_04_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_04_Step_Tiep tuc");
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/submit");
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_04_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_04_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4").split(" ")[3];

		log.info("TC_04_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_04_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_04_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Account_Data.Valid_Account.CARD_TO);

		log.info("TC_04_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_04_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_04_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_04_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_04: Lay so du kha dung the");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double amountAfter = convertMoneyToDouble(amountAfterString, "EUR");
		double amountExpect = Math.round((amountAfter) * 100) / 100;

		log.info("TC_04: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_EUR, " EUR");

		log.info("TC_04: Kiem tra so du tai khoan EUR sau khi chuyen tien");
		double amountAtual = Math.round((amountStart - transferMoney) * 100) / 100;
		verifyEquals(amountAtual, amountExpect);
	}

	@Test
	public void TC_05_HuyKichHoatVCBSmartOPT() {
		smartOTP.cancelSetupSmartOTP();
	}

	

	
}