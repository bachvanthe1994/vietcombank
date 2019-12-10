package vnpay.vietcombank.TransferIdentityCard;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.TransferIdentiryPageObject;
import pageObjects.LogInPageObject;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import pageObjects.TransactionReportPageObject;

public class TransferIdentity extends Base {
//	AndroidDriver<AndroidElement> driver;
//	private LogInPageObject login;
//	private HomePageObject homePage;
//	private TransferIdentiryPageObject trasferPage;
//	private TransactionReportPageObject transReport;
//
//
//	String userName = "Hoangkm";
//	String Identtity = "123456789";
//
//	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
//	@BeforeClass
//	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
//			String appPackage, String appName) throws IOException, InterruptedException {
//		startServer();
//		log.info("Before class: Mo app ");
//		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
//
//		login = PageFactoryManager.getLoginPageObject(driver);
//		homePage = PageFactoryManager.getHomePageObject(driver);
//		trasferPage = PageFactoryManager.getTransferPageObject(driver);
//
//		log.info("Before class: Click Allow Button");
//		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
//
//		// login
//		log.info("TC_06_Step_0");
//		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");
//
//		log.info("TC_06_Step_0");
//		login.clickToDynamicButton(driver, "Tiếp tục");
//
//		log.info("TC_05_Step_0");
//		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);
//
//		log.info("TC_06_Step_0");
//		login.clickToDynamicButton(driver, "Tiếp tục");
//
//		log.info("TC_06_Step_0");
//		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
//
//		log.info("TC_06_Step_0");
//		login.clickToDynamicButton(driver, "Tiếp tục");
//
//		log.info("TC_06_Step_0");
//		login.clickToDynamicButton(driver, "TỪ CHỐI");
//		
//		homePage.scrollToText(driver, "Chuyển tiền nhận bằng CMT");
//
//		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");
//		
//		String text = homePage.getTextDynamicPopup(driver, Transfer_Data.textCheckElement.PAGE_TRANSFER).trim();
//		verifyTrue(text.equals(Transfer_Data.textCheckElement.PAGE_TRANSFER));
//	}
//
//	@Test
//	public void TC_01_ChuyenTienQuaCMTNguoiChuyenTraPhiVNDXacNhanMatKhau(){
//		// Man hinh dien thong tin chuyen tien		
//		log.info("TC_01: chon tai khoan");
//		trasferPage.clickToDynamicAccount(driver, "Tài khoản nguồn");
//		trasferPage.scrollToText(driver, "0011000000526");
//		trasferPage.clickToDynamicAcceptText(driver, "0011000000526");
//		
//		// lay so tien truoc khi chuyen		
//		String overbalanceBefore = trasferPage.getDynamicTextInTextView(driver, "Số dư khả dụng");
//		Long overbalanceBeforeInt = convertMoneyToLong(overbalanceBefore, "VND");
//		
//		log.info("TC_01: nhap ten nguoi thu huong");
//		trasferPage.inputBeneficiary("Hoangkm");
//		
//		log.info("TC_01: chon giay to tuy than");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");
//		
//		log.info("TC_01: so CMT");
//		trasferPage.inputIdentityNumber("123456789");
//		
//		log.info("TC_01: ngay cap");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
//		trasferPage.clickToDynamicButton(driver, "OK");
//		
//		trasferPage.scrollToText(driver, "Thông tin giao dịch");
//		
//		log.info("TC_01: noi cap");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thành phố Hà Nội ");
//		
//		log.info("TC_01: chon so tien");
//		trasferPage.inputMoney("10000");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");
//		
//		log.info("TC_01: noi dung");
//		trasferPage.inputContent("abc123");
//		
//		log.info("TC_01: noi dung");
//		trasferPage.clickToDynamicButton(driver, "Tiếp tục");
//		
//		//Man hinh xac nhan thong tin
//		String text = homePage.getTextDynamicPopup(driver, Transfer_Data.textCheckElement.PAGE_CONFIRM).trim();
//		verifyTrue(text.equals(Transfer_Data.textCheckElement.PAGE_CONFIRM));
//		
//		trasferPage.scrollToText(driver, "Chọn phương thức xác thực");
//		log.info("TC_01: chon phuong thuc xac thuc");
//		trasferPage.clickToDynamicAcceptText(driver, "Mật khẩu đăng nhập");
//		trasferPage.clickToDynamicAcceptText(driver, "Mật khẩu đăng nhập");
//		
//		// lay ra so tien chuyen di va phi truyen		
//		String moneyTransfer = trasferPage.getDynamicTextInTextView(driver, "Số tiền");
//		Long moneyTransferInt = convertMoneyToLong(overbalanceBefore, "VND");
//		
//		String amount = trasferPage.getDynamicTextInTextView(driver, "Số tiền phí");
//		Long amountInt = convertMoneyToLong(overbalanceBefore,"VND");
//		trasferPage.clickToDynamicButton(driver, "Tiếp tục");
//		
//		log.info("TC_01: xac thuc giao dich");
//		trasferPage.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
//		trasferPage.clickToDynamicButton(driver, "Tiếp tục");
//		
//		log.info("TC_01: xac thuc thuc hien giao dich moi");
//		String newDealConfirm = trasferPage.getTextDynamicPopup(driver, Transfer_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
//		verifyTrue(newDealConfirm.equals(Transfer_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS));
//		trasferPage.clickToDynamicButton(driver, "Tiếp tục");
//			
//	}
//	
////	@Test
//	public void TC_02_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
//		log.info("TC_02_Step_:");
//		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
//		
//		log.info("TC_02_Step_:");
//		homePage = PageFactoryManager.getHomePageObject(driver);
//		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
//		
//		log.info("TC_02_Step_:");
//		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
//		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
//		
//		log.info("TC_02_Step_:");
//		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
//		
//		log.info("TC_02_Step_:");
//		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
//		
//		log.info("TC_02_Step_:");
//		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
//		
//		log.info("TC_02_Step_:");
//		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
//		
//		log.info("TC_02_Step_:");
//		transReport.clickToDynamicButton(driver, "Tìm kiếm");
//	
//		log.info("TC_02_Step_:");
//		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
//		
//		log.info("TC_02_Step_:");
//		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
//		
//		log.info("TC_02_Step_:");
//		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
//		
//		log.info("TC_02_Step_:");
//		transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
//		
//		log.info("TC_02_Step_:");
//		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
//		
//		log.info("TC_02_Step_:");
//		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
//		
//		log.info("TC_02_Step_:");
//		verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains( addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
//		
//		log.info("TC_02_Step_:");
//		verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));
//
//		log.info("TC_02_Step_:");
//		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
//		
//		log.info("TC_02_Step_:");
//		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
//		
//		log.info("TC_02_Step_:");
//		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
//
//		log.info("TC_02_Step_:");
//		transReport.clickToDynamicButton(driver, "Tìm kiếm");
//
//		log.info("TC_02_Step_:");
//		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
//		
//		log.info("TC_02_Step_:");
//		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
//		
//		log.info("TC_02_Step_:");
//		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
//		
//		log.info("TC_02_Step_:");
//		transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
//		
//		log.info("TC_02_Step_:");
//		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
//		
//		log.info("TC_02_Step_:");
//		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
//		
//		log.info("TC_02_Step_:");
//		verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
//		
//		log.info("TC_02_Step_:");
//		verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
//		
//		log.info("TC_02_Step_:");
//		verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));
//
//		log.info("TC_02_Step_:");
//		transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
//		
//		log.info("TC_02_Step_:");
//		transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
//		
//		log.info("TC_02_Step_:");
//		transferInVCB.navigateBack(driver);
//	}
//	
//	//@Test
//	public void TC_03_ChuyenTienNgoaiTe(){
//		log.info("TC_01: chon tai khoan ngoai te");
//		trasferPage.clickToDynamicAccount(driver, "Tài khoản nguồn");
//		trasferPage.clickToDynamicAcceptText(driver, "0012370377247");
//		
//		log.info("TC_01: nhap ten nguoi thu huong");
//		trasferPage.inputBeneficiary("Hoangkm");
//		
//		log.info("TC_01: chon giay to tuy than");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");
//		
//		log.info("TC_01: so CMT");
//		trasferPage.inputIdentityNumber("123456789");
//		
//		log.info("TC_01: ngay cap");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
//		trasferPage.clickToDynamicButton(driver, "OK");
//		
//		trasferPage.scrollToText(driver, "Thông tin giao dịch");
//		
//		log.info("TC_01: noi cap");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thành phố Hà Nội ");
//		
//		log.info("TC_01: chon so tien");
//		trasferPage.inputMoney("1000");
//		
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");
//		
//		log.info("TC_01: noi dung");
//		trasferPage.inputContent("abc123");
//		
//		log.info("TC_01: noi dung");
//		trasferPage.clickToDynamicButton(driver, "Tiếp tục");
//	}
//
//	@AfterClass(alwaysRun = true)
//	public void afterClass() {
////		closeApp();
//		service.stop();
//	}

}
