package vnpay.vietcombank.transfer_money_out_side_vcb;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.TransferOutSideVCB_Info;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyOutSideVCBPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyOutVCB_Data;

public class Limit_Transfer_MoneyOutSide_VCB extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyOutSideVCBPageObject transferMoneyOutSide;

	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	String password, currentcy = "";

	TransferOutSideVCB_Info info = new TransferOutSideVCB_Info(Account_Data.Valid_Account.ACCOUNT2, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "50000", "Phí giao dịch người chuyển trả", "test", "SMS OTP");

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
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
		password = pass;

		transferMoneyOutSide.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");
	}

	@Test
	public void TC_01_ChuyenTienToiTaiKhoanKhacNhoHonDinhMucToiThieu() {

		log.info("TC_01_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_01_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_01_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/ chọn tài khoản nhận VND");

		log.info("TC_01_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, "Tên người thụ hưởng");

		log.info("TC_01_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng thụ hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_01_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, TransferMoneyOutVCB_Data.InputDataAmountOutVCB.LOWER_MIN_TRANSFER, "Số tiền");

		log.info("TC_01_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_01_7_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, "Thông tin giao dịch", "3");

		log.info("TC_01_8_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_11: Verify hien thi man hinh thong bao loi");
		transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyOutVCB_Data.MESSEGE_ERROR.LOWER_MIN_AMOUNT_A_TRAN);

		log.info("TC_01_8_Click Dong");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_02_ChuyenTienToiTaiKhoanKhacCaoHonMucToiDa() {

		log.info("---------------------TC_02_1_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, TransferMoneyOutVCB_Data.InputDataAmountOutVCB.HIGHER_MAX_TRANSFER, "Thông tin giao dịch", "1");

		log.info("-------------------TC_02_2_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("----------------------TC_02_Step_3: Verify hien thi man hinh thong bao loi");
		transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyOutVCB_Data.MESSEGE_ERROR.HIGHER_MAX_AMOUNT_A_TRAN);

		log.info("-----------------TC_01_8_Click Dong");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	// Han giao dich cua 1 ngay là 100 000 000
	@Test
	public void TC_03_ChuyenTienToiTaiKhoanKhacCaoHonMucToiDaTrongNgay() {

		log.info("----------------------TC_03_1_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, TransferMoneyOutVCB_Data.InputDataAmountOutVCB.MAX_TRANSFER, "Thông tin giao dịch", "1");

		log.info("-------------------------TC_03_2_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("-------------------------TC_03_3_Hien thi man hinh");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, "Xác nhận thông tin"));

		log.info("--------------------TC_03_4_Click Tiep tuc va nhap OTP");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		transferMoneyOutSide.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		log.info("--------------------TC_03_Verify hien thi man hinh thong bao");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyOutVCB_Data.MESSEGE_SUCCESS.SUCCESS_TRANSFER_MONEY));

		log.info("--------------------TC_03_5_Kiem tra nut Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_03_6_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_03_7_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/ chọn tài khoản nhận VND");

		log.info("TC_03_8_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, "Tên người thụ hưởng");

		log.info("TC_03_9_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng thụ hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_03_10_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, TransferMoneyOutVCB_Data.InputDataAmountOutVCB.MIN_TRANSFER, "Thông tin giao dịch", "1");

		log.info("TC_03_11_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_03_12_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, "Thông tin giao dịch", "3");

		log.info("TC_03_13_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_14: Verify hien thi man hinh thong bao loi");
		transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyOutVCB_Data.MESSEGE_ERROR.HIGHER_MAX_AMOUNT_A_TRAN_A_DAY);

		log.info("TC_03_15_Click Dong");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	public void TC_04_ChuyenTienToiTaiKhoanKhacNhom() {

		log.info("TC_04_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_04_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_04_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/ chọn tài khoản nhận VND");

		log.info("TC_04_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, "Tên người thụ hưởng");

		log.info("TC_04_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng thụ hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_04_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, TransferMoneyOutVCB_Data.InputDataAmountOutVCB.HIGHER_MAX_TRANSFER, "Số tiền");

		log.info("TC_04_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_04_7_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, "Thông tin giao dịch", "3");

		log.info("TC_04_8_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_04_Step_9: Verify hien thi man hinh thong bao loi");
		transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyOutVCB_Data.MESSEGE_ERROR.HIGHER_MAX_AMOUNT_A_TRAN_GROUP);

		log.info("TC_04_10_Click Dong");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	// Han muc goi 100 000 000
	public void TC_05_ChuyenTienToiTaiKhoanKhacGoi() {

		log.info("TC_05_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_05_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_05_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/ chọn tài khoản nhận VND");

		log.info("TC_05_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, "Tên người thụ hưởng");

		log.info("TC_05_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng thụ hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_05_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, TransferMoneyOutVCB_Data.InputDataAmountOutVCB.HIGHER_MAX_TRANSFER, "Số tiền");

		log.info("TC_05_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_05_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, "Thông tin giao dịch", "3");

		log.info("TC_05_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_10: Verify hien thi man hinh thong bao loi");
		transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyOutVCB_Data.MESSEGE_ERROR.HIGHER_MAX_AMOUNT_A_TRAN_PACKAGE);

		log.info("TC_05_11: Click Dong y");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
