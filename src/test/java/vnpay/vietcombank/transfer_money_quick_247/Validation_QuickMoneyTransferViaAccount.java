package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferMoneyObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;
import vietcombank_test_data.ValidationTransferMoneyQuick_Data;

public class Validation_QuickMoneyTransferViaAccount extends Base {
	AndroidDriver<AndroidElement> driver;
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

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("Before class: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		log.info("Before class");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("Before class");
		login.clickToDynamicButton(driver, "CHO PHÉP");

		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
	}

	@Test
	public void TC_01_ValidationChuyenNhanhQuaTaiKhoan() {
		log.info("TC_01_Step_Scoll den man hinh chuyen tien nhanh");
		transferMoney.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Kiem tra button back");
		transferMoney.clickToDynamicBottomMenu(driver, 	"com.VCB:id/ivTitleLeft");
		
		log.info("TC_01_Step_Quay lai man hinh chuyen tien nhanh");
		transferMoney.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");
		
		log.info("TC_01_Step_Click combobox hinh thuc chuyen tien");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, ValidationTransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		
		log.info("TC_01_Step_Check value trong hinh thuc chuyen tien");
		transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, ValidationTransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, ValidationTransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, ValidationTransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		
		log.info("TC_01_Step_verify label thong tin nguoi chuyen");
		transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, ValidationTransferMoneyQuick_Data.TransferQuick.INFO_FORM_LABEL);
		
		log.info("TC_01_Step_verify tai khoan nguon mac dinh la tai khoan thanh toan");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, ValidationTransferMoneyQuick_Data.TransferQuick.ACCOUNT_FORM_LIST[0]));
		
		log.info("TC_01_Step_Click combobox tai khoan nguon");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, ValidationTransferMoneyQuick_Data.TransferQuick.ACCOUNT_FORM_LIST[0]);
		
		log.info("TC_01_Step_Cho phep chon tai khoan thanh toan khac tai khoan mac dinh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, ValidationTransferMoneyQuick_Data.TransferQuick.ACCOUNT_FORM_LIST[1]);
		
		log.info("TC_01_Step_Chon lai ve man hinh tai khoan mac dinh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, ValidationTransferMoneyQuick_Data.TransferQuick.ACCOUNT_FORM_LIST[1]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, ValidationTransferMoneyQuick_Data.TransferQuick.ACCOUNT_FORM_LIST[0]);
		
		log.info("TC_01_Step_Lay so du kha dung");
		amountStartString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");
		
		log.info("TC_01_Step_Check so du kha dung hien thi so du va loai tien tuong ung");
		verifyTrue(transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng")
				.contains(addCommasToLong(amountStartString) + " VND"));
		
		log.info("TC_01_Step_verify label thong tin nguoi huong");
		transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, ValidationTransferMoneyQuick_Data.TransferQuick.INFO_TO_LABEL);
		
		log.info("TC_01_Step_verify text");
		transferMoney.isDynamicTextInInputBoxDisPlayed(driver,"Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_01_Step_Invalid nhap so tai khoan nguoi nhan vuot qua 25 ky tu");
		transferMoney.inputToDynamicInputBox(driver, ValidationTransferMoneyQuick_Data.TransferQuick.INVALID_ACC_ACCEPT_OVER_MAX,
				"Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_01_Step_Invalid nhap so tai khoan nguoi nhan co ky tu tieng viet co dau");
		transferMoney.inputToDynamicInputBox(driver, ValidationTransferMoneyQuick_Data.TransferQuick.INVALID_ACC_VIETNAM_KEY,
				"Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_01_Step_Verify chuyen doi thanh tieng viet khong dau");
		transferMoney.isDynamicTextInInputBoxDisPlayed(driver, transferMoney.removeUnicode(driver, ValidationTransferMoneyQuick_Data.TransferQuick.INVALID_ACC_VIETNAM_KEY));
		
		log.info("TC_01_Step_verify message khi tai khoan nguoi nhan khong ton tai ");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, ValidationTransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, ValidationTransferMoneyQuick_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Step_verify message khi tai khoan nguoi nhan khong ton tai ");
		transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, ValidationTransferMoneyQuick_Data.MessageTransferMoney.ACCOUNT_NOT_EXIST);
		
		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
		
	}

	
}
