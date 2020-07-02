package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import commons.WebPageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.ServiceLimitInfo;
import model.SourceAccountModel;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.TransferMoneyQuick_Data;
import vietcombank_test_data.TransferMoneyQuick_Data.Tittle_Quick;
import vietcombank_test_data.TransferMoneyQuick_Data.TransferQuick;

public class Transaction_Limit_Card extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driverWeb;
	private LogInPageObject login;
	private TransferMoneyObject transferMoney;
	private WebBackendSetupPageObject webBackend;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	private String bankOut, accountRecived;
	private String password, cardNumber, otp  = "";
	List<String> listCard = new ArrayList<String>();

	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "1000000", "1000100");
	String inputAmountMin  = inputInfo.minTran ;
	String inputAmountMax   = inputInfo.maxTran ;
	String inputTotalLimit   = inputInfo.totalLimit  ;
	String amountType = "900000";
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		webBackend = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		webBackend.Login_Web_Backend(driverWeb, username, passWeb);

		webBackend.addMethodOtpLimit(driverWeb, "Chuyển khoản liên ngân hàng đến số thẻ");
		webBackend.setupAssignServicesLimit(driverWeb, "Chuyển khoản liên ngân hàng đến số thẻ", inputInfo,"TESTBUG");

		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		otp = opt;
		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
		bankOut = getDataInCell(21);
		accountRecived = getDataInCell(20);
	}

	@Test
	public void TC_01_SoTienNhoHonHanMucToiThieuTrenMotLanGiaoDich_TaiKhoan() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_09_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		listCard = transferMoney.getListCard();
		transferMoney.scrollUpToText(driver, listCard.get(0));

		for (String card : listCard) {
			transferMoney.scrollDownToText(driver, card);
			log.info("TC_01_Step_06: Chon tai khoan the tin dung:");
			transferMoney.clickToDynamicButtonLinkOrLinkText(driver, card);
			if (transferMoney.getPageSource(driver).contains(Tittle_Quick.UNSATISFACTORY)) {
				log.info("TC_01_Step_07: Click btn Dong y:");
				transferMoney.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
				log.info("TC_01_Step_08: Click chon so tai khoan the :");
				transferMoney.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
				continue;
			} else {
				cardNumber = transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContentCard");
				sourceAccount.account = transferMoney.getDynamicTextByLabel(driver, TransferQuick.ACCOUNT_FROM_LABEL);
				sourceAccount.currentcy = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
				if (sourceAccount.currentcy.length() > 3) {
					sourceAccount.currentcy = sourceAccount.currentcy.split(" ")[1];
					sourceAccount.balance = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
					if (!sourceAccount.currentcy.equals(Constants.VND_CURRENCY) || convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccount.balance) < Long.valueOf(Constants.MONEY_CHECK_VND)) {
						transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
						transferMoney.scrollUpToText(driver, listCard.get(0));
						continue;
					} else {
						break;
					}
				} else {
					break;
				}

			}
		}

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver,(Integer.parseInt(inputAmountMin) -1) +"", TransferQuick.MOUNT_LABEL);

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);
		
		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(transferMoney.getDynamicTextView(driver,"com.VCB:id/tvContent"), "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức "+addCommasToLong(inputInfo.minTran)+" VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");
	
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		transferMoney.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);
	}
	
	@Test
	public void TC_02_SoTienLonHonHanMucToiDaTrenMotLanGiaoDich_TaiKhoan() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);
		
		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_09_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		listCard = transferMoney.getListCard();
		transferMoney.scrollUpToText(driver, listCard.get(0));

		for (String card : listCard) {
			transferMoney.scrollDownToText(driver, card);
			log.info("TC_01_Step_06: Chon tai khoan the tin dung:");
			transferMoney.clickToDynamicButtonLinkOrLinkText(driver, card);
			if (transferMoney.getPageSource(driver).contains(Tittle_Quick.UNSATISFACTORY)) {
				log.info("TC_01_Step_07: Click btn Dong y:");
				transferMoney.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
				log.info("TC_01_Step_08: Click chon so tai khoan the :");
				transferMoney.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
				continue;
			} else {
				cardNumber = transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContentCard");
				sourceAccount.account = transferMoney.getDynamicTextByLabel(driver, TransferQuick.ACCOUNT_FROM_LABEL);
				sourceAccount.currentcy = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
				if (sourceAccount.currentcy.length() > 3) {
					sourceAccount.currentcy = sourceAccount.currentcy.split(" ")[1];
					sourceAccount.balance = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
					if (!sourceAccount.currentcy.equals(Constants.VND_CURRENCY) || convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccount.balance) < Long.valueOf(Constants.MONEY_CHECK_VND)) {
						transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
						transferMoney.scrollUpToText(driver, listCard.get(0));
						continue;
					} else {
						break;
					}
				} else {
					break;
				}

			}
		}

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, (Integer.parseInt(inputAmountMax)+1) +"", Tittle_Quick.TRANSACTION_INFO, "1");

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);
		
		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi da  ");
		verifyEquals(transferMoney.getDynamicTextView(driver,"com.VCB:id/tvContent"), "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức "+addCommasToLong(inputInfo.maxTran)+" VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");
		
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		transferMoney.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);

	}
	
	@Test
	public void TC_03_SoTienLonHonHanMucToiDaTrenMotNgayGiaoDich_TaiKhoan() throws InterruptedException {
		
		log.info("TC_09_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_09_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_09_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		listCard = transferMoney.getListCard();
		transferMoney.scrollUpToText(driver, listCard.get(0));

		for (String card : listCard) {
			transferMoney.scrollDownToText(driver, card);
			log.info("TC_01_Step_06: Chon tai khoan the tin dung:");
			transferMoney.clickToDynamicButtonLinkOrLinkText(driver, card);
			if (transferMoney.getPageSource(driver).contains(Tittle_Quick.UNSATISFACTORY)) {
				log.info("TC_01_Step_07: Click btn Dong y:");
				transferMoney.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
				log.info("TC_01_Step_08: Click chon so tai khoan the :");
				transferMoney.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
				continue;
			} else {
				cardNumber = transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContentCard");
				sourceAccount.account = transferMoney.getDynamicTextByLabel(driver, TransferQuick.ACCOUNT_FROM_LABEL);
				sourceAccount.currentcy = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
				if (sourceAccount.currentcy.length() > 3) {
					sourceAccount.currentcy = sourceAccount.currentcy.split(" ")[1];
					sourceAccount.balance = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
					if (!sourceAccount.currentcy.equals(Constants.VND_CURRENCY) || convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccount.balance) < Long.valueOf(Constants.MONEY_CHECK_VND)) {
						transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
						transferMoney.scrollUpToText(driver, listCard.get(0));
						continue;
					} else {
						break;
					}
				} else {
					break;
				}

			}
		}

		log.info("TC_09_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_09_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, inputAmountMin, TransferQuick.MOUNT_LABEL);

		log.info("TC_09_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_09_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_09_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_09_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_09_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_09_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtp(driver, otp, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_09_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_09_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_09_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_09_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_09_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, cardNumber);
		
		//Setup han muc trong 1 ngay
		webBackend.setupAssignServicesLimit_Total_Day(driverWeb, "Chuyển khoản liên ngân hàng đến số thẻ", inputInfo,Constants.BE_CODE_PACKAGE);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver,inputAmountMax, TransferQuick.MOUNT_LABEL);

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);
		
		log.info("TC_01_Step_verify message gioi han");
		verifyEquals(transferMoney.getDynamicTextView(driver,"com.VCB:id/tvContent"),"Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức "+addCommasToLong(inputTotalLimit)+" VND/1 ngày, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp..");
		
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		transferMoney.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);
	
		webBackend.resetAssignServicesLimit(driverWeb, "Chuyển khoản liên ngân hàng đến số thẻ",Constants.BE_CODE_PACKAGE);

	}
	
	@Test
	public void TC_04_SoTienLonHonHanMucToiDaTrenMotNgayNhomGiaoDich_TaiKhoan() throws InterruptedException {
			webBackend.Setup_Assign_Services_Type_Limit(driverWeb, "TESTBUG","Chuyển khoản", amountType );
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_09_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		listCard = transferMoney.getListCard();
		transferMoney.scrollUpToText(driver, listCard.get(0));

		for (String card : listCard) {
			transferMoney.scrollDownToText(driver, card);
			log.info("TC_01_Step_06: Chon tai khoan the tin dung:");
			transferMoney.clickToDynamicButtonLinkOrLinkText(driver, card);
			if (transferMoney.getPageSource(driver).contains(Tittle_Quick.UNSATISFACTORY)) {
				log.info("TC_01_Step_07: Click btn Dong y:");
				transferMoney.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
				log.info("TC_01_Step_08: Click chon so tai khoan the :");
				transferMoney.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
				continue;
			} else {
				cardNumber = transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContentCard");
				sourceAccount.account = transferMoney.getDynamicTextByLabel(driver, TransferQuick.ACCOUNT_FROM_LABEL);
				sourceAccount.currentcy = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
				if (sourceAccount.currentcy.length() > 3) {
					sourceAccount.currentcy = sourceAccount.currentcy.split(" ")[1];
					sourceAccount.balance = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
					if (!sourceAccount.currentcy.equals(Constants.VND_CURRENCY) || convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccount.balance) < Long.valueOf(Constants.MONEY_CHECK_VND)) {
						transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
						transferMoney.scrollUpToText(driver, listCard.get(0));
						continue;
					} else {
						break;
					}
				} else {
					break;
				}

			}
		}

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver,(Integer.parseInt(amountType )+1) +"", TransferQuick.MOUNT_LABEL);

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);
		
		log.info("TC_01_Step_verify message gioi han");
		verifyEquals(transferMoney.getDynamicTextView(driver,"com.VCB:id/tvContent"),"Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức "+addCommasToLong(amountType)+" VND/1 ngày của nhóm dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp..");
		
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		transferMoney.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);
		
		log.info("TC_01_Step_reset cai dat nhom dich vu ");
		webBackend.Reset_Setup_Assign_Services_Type_Limit(driverWeb, "TESTBUG","Chuyển khoản" );
	}
	
	@Test
	public void TC_05_SoTienLomHonHanMucToiDaTrenMotNgayGoiGiaoDich_TaiKhoan() throws InterruptedException {
		log.info("TC_01_Setup package ");
		webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, "TESTBUG", "Method Otp",amountType);
		
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);
		log.info("TC_09_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		listCard = transferMoney.getListCard();
		transferMoney.scrollUpToText(driver, listCard.get(0));

		for (String card : listCard) {
			transferMoney.scrollDownToText(driver, card);
			log.info("TC_01_Step_06: Chon tai khoan the tin dung:");
			transferMoney.clickToDynamicButtonLinkOrLinkText(driver, card);
			if (transferMoney.getPageSource(driver).contains(Tittle_Quick.UNSATISFACTORY)) {
				log.info("TC_01_Step_07: Click btn Dong y:");
				transferMoney.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
				log.info("TC_01_Step_08: Click chon so tai khoan the :");
				transferMoney.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
				continue;
			} else {
				cardNumber = transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContentCard");
				sourceAccount.account = transferMoney.getDynamicTextByLabel(driver, TransferQuick.ACCOUNT_FROM_LABEL);
				sourceAccount.currentcy = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
				if (sourceAccount.currentcy.length() > 3) {
					sourceAccount.currentcy = sourceAccount.currentcy.split(" ")[1];
					sourceAccount.balance = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
					if (!sourceAccount.currentcy.equals(Constants.VND_CURRENCY) || convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccount.balance) < Long.valueOf(Constants.MONEY_CHECK_VND)) {
						transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
						transferMoney.scrollUpToText(driver, listCard.get(0));
						continue;
					} else {
						break;
					}
				} else {
					break;
				}

			}
		}

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver,(Integer.parseInt(amountType )+1) +"", TransferQuick.MOUNT_LABEL);

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);
		
		log.info("TC_01_Step_verify message gioi han");
		verifyEquals(transferMoney.getDynamicTextView(driver,"com.VCB:id/tvContent"),"Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức "+addCommasToLong(amountType)+" VND/1 ngày của gói dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp..");
	
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		transferMoney.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);
		
		log.info("TC_01_Step_reset cai dat nhom dich vu ");
		webBackend.Reset_Package_Total_Limit(driver, "TESTBUG", "Method Otp");
	}


	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}