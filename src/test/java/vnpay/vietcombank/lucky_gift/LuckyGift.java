package vnpay.vietcombank.lucky_gift;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import pageObjects.LuckyGiftPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.LuckyGift_Data;

public class LuckyGift extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private LuckyGiftPageObject luckyGift;
	private long amountStart;
	private long amountTranfer;
	private String amountStartString;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })

	@BeforeMethod
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
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

		luckyGift = PageFactoryManager.getLuckyGiftPageObject(driver);
	}

	@Test
	public void TC_01_ChuyenTienNguoiNhanTaiVCBXacNhanMKVaSDT() {
		log.info("TC_01_1_Click trang thai lenh chuyen tien");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Quà tặng may mắn");

		log.info("TC_01_Step_Select tai khoan nguon");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

		log.info("TC_01_Step_Get so du kha dung");
		amountStartString = luckyGift.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		log.info("TC_01_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_01_Step_Select ngan hang");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_OPTION[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_OPTION[0]);

		log.info("TC_01_Step_Verify so nguoi nhan, neu trong ngan hang VCB, so list danh sach se hien thi toi da 10");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Nguời nhận (0/10)"));

		log.info("TC_01_Step_Click add danh sach nguoi nhan");
		luckyGift.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivAdd");

		log.info("TC_01_Step_Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Hinh thuc nhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TYPE_ACCEPT_OPTION[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TYPE_ACCEPT_OPTION[1]);

		log.info("TC_01_Step_Verify so dien thoai nhan 1");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Số điện thoại nhận 1"));

		log.info("TC_01_Step_So dien thoai/ tai khoan nhan");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MOBI_ACCEPT, "Số điện thoại/tài khoản nhận");

		log.info("TC_01_Step_Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, "Số tiền");

		log.info("TC_01_Step_Chon loi chuc");
		luckyGift.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/more_content");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION[0]);

		log.info("TC_01_Step_Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Lay gia tri so tien chuyen");
		String amountTranferString = luckyGift.getDynamicAmountLabel(driver, "TỔNG TIỀN").replaceAll("\\D+", "");

		log.info("TC_01_Step_Doi kieu du lieu");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_01_Step_Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Verify hinh thuc giao dich");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Hình thức giao dịch"), "Quà tặng may mắn");

		log.info("TC_01_Verify tai khoan nguon");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

		log.info("TC_01_Verify thong tin nguoi nhan");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.LuckyGift.MOBI_ACCEPT + "/ " + LuckyGift_Data.LuckyGift.NAME_ACCEPT));

		log.info("TC_01_Verify So tien chuyen");
		verifyEquals(amountTranferString, LuckyGift_Data.LuckyGift.MONEY);

		log.info("TC_01_Verify phi giao dich");
		String amountCostString = luckyGift.getDynamicAmountCostLabel(driver, "Tổng số tiền phí").replaceAll("\\D+", "");

		log.info("TC_01_Step_: Verify loi chuc");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Lời chúc"), LuckyGift_Data.LuckyGift.WISHES_OPTION[0]);

		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCURACY[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCURACY[0]);

		log.info("TC_01_Step_Tiep tuc");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		luckyGift.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_01_Step_Tiep tuc");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	}

	@Test
	public void TC_02_ChuyenTienNguoiNhanTaiVCBXacNhanMKVaTK() {
		log.info("TC_02_1_Click trang thai lenh chuyen tien");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Quà tặng may mắn");

		log.info("TC_02_Step_Select tai khoan nguon");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

		log.info("TC_02_Step_Get so du kha dung");
		amountStartString = luckyGift.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		log.info("TC_02_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_02_Step_Select ngan hang");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_OPTION[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_OPTION[0]);

		log.info("TC_02_Step_Verify so nguoi nhan, neu trong ngan hang VCB, so list danh sach se hien thi toi da 10");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Nguời nhận (0/10)"));

		log.info("TC_02_Step_Click add danh sach nguoi nhan");
		luckyGift.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivAdd");

		log.info("TC_02_Step_Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Hinh thuc nhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TYPE_ACCEPT_OPTION[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TYPE_ACCEPT_OPTION[0]);

		log.info("TC_02_Step_Verify so dien thoai nhan 1");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Số tài khoản nhận 1"));

		log.info("TC_02_Step_So dien thoai/ tai khoan nhan");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_IN_VCB, "Số điện thoại/tài khoản nhận");

		log.info("TC_02_Step_Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, "Số tiền");

		log.info("TC_02_Step_Chon loi chuc");
		luckyGift.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/more_content");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION[0]);

		log.info("TC_02_Step_Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Lay gia tri so tien chuyen");
		String amountTranferString = luckyGift.getDynamicAmountLabel(driver, "TỔNG TIỀN").replaceAll("\\D+", "");

		log.info("TC_02_Step_Doi kieu du lieu");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_02_Step_Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Verify hinh thuc giao dich");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Hình thức giao dịch"), "Quà tặng may mắn");

		log.info("TC_02_Verify tai khoan nguon");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

		log.info("TC_02_Verify thong tin nguoi nhan");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_IN_VCB + "/ " + LuckyGift_Data.LuckyGift.NAME_ACCEPT));

		log.info("TC_02_Verify So tien chuyen");
		verifyEquals(amountTranferString, LuckyGift_Data.LuckyGift.MONEY);

		log.info("TC_02_Verify phi giao dich");
		String amountCostString = luckyGift.getDynamicAmountCostLabel(driver, "Tổng số tiền phí").replaceAll("\\D+", "");

		log.info("TC_02_Step_: Verify loi chuc");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Lời chúc"), LuckyGift_Data.LuckyGift.WISHES_OPTION[0]);

		log.info("TC_02_Step_Chon phuong thuc xac thuc");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCURACY[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCURACY[0]);

		log.info("TC_02_Step_Tiep tuc");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		luckyGift.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_02_Step_Tiep tuc");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");
	}

	@Test
	public void TC_03_ChuyenTienNguoiNhanVCBXacNhanOTPVaSĐT() {
		log.info("TC_01_1_Click trang thai lenh chuyen tien");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Quà tặng may mắn");

		log.info("TC_03_Step_Select tai khoan nguon");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

		log.info("TC_03_Step_Get so du kha dung");
		amountStartString = luckyGift.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		log.info("TC_03_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_03_Step_Select ngan hang");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_OPTION[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_OPTION[0]);

		log.info("TC_03_Step_Verify so nguoi nhan, neu trong ngan hang VCB, so list danh sach se hien thi toi da 10");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Nguời nhận (0/10)"));

		log.info("TC_03_Step_Click add danh sach nguoi nhan");
		luckyGift.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivAdd");

		log.info("TC_03_Step_Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_Hinh thuc nhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TYPE_ACCEPT_OPTION[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TYPE_ACCEPT_OPTION[1]);

		log.info("TC_03_Step_Verify so dien thoai nhan 1");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Số điện thoại nhận 1"));

		log.info("TC_03_Step_So dien thoai/ tai khoan nhan");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MOBI_ACCEPT, "Số điện thoại/tài khoản nhận");

		log.info("TC_03_Step_Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, "Số tiền");

		log.info("TC_03_Step_Chon loi chuc");
		luckyGift.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/more_content");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION[0]);

		log.info("TC_03_Step_Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_Lay gia tri so tien chuyen");
		String amountTranferString = luckyGift.getDynamicAmountLabel(driver, "TỔNG TIỀN").replaceAll("\\D+", "");

		log.info("TC_03_Step_Doi kieu du lieu");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_03_Step_Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Verify hinh thuc giao dich");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Hình thức giao dịch"), "Quà tặng may mắn");

		log.info("TC_03_Verify tai khoan nguon");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

		log.info("TC_03_Verify thong tin nguoi nhan");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.LuckyGift.MOBI_ACCEPT + "/ " + LuckyGift_Data.LuckyGift.NAME_ACCEPT));

		log.info("TC_03_Verify So tien chuyen");
		verifyEquals(amountTranferString, LuckyGift_Data.LuckyGift.MONEY);

		log.info("TC_03_Verify phi giao dich");
		String amountCostString = luckyGift.getDynamicAmountCostLabel(driver, "Tổng số tiền phí").replaceAll("\\D+", "");

		log.info("TC_03_Step_: Verify loi chuc");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Lời chúc"), LuckyGift_Data.LuckyGift.WISHES_OPTION[0]);

		log.info("TC_03_Step_Chon phuong thuc xac thuc");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCURACY[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCURACY[1]);

		log.info("TC_03_Step_Tiep tuc");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_Nhap ma xac thuc");
		luckyGift.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_03_Step_Tiep tuc");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");
	}

	@Test
	public void TC_04_ChuyenTienNguoiNhanVCBXacNhanOTPVaSTK() {
		log.info("TC_04_1_Click trang thai lenh chuyen tien");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Quà tặng may mắn");

		log.info("TC_04_Step_Select tai khoan nguon");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

		log.info("TC_04_Step_Get so du kha dung");
		amountStartString = luckyGift.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		log.info("TC_04_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_04_Step_Select ngan hang");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_OPTION[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_OPTION[0]);

		log.info("TC_04_Step_Verify so nguoi nhan, neu trong ngan hang VCB, so list danh sach se hien thi toi da 10");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Nguời nhận (0/10)"));

		log.info("TC_04_Step_Click add danh sach nguoi nhan");
		luckyGift.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivAdd");

		log.info("TC_04_Step_Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_Hinh thuc nhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TYPE_ACCEPT_OPTION[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TYPE_ACCEPT_OPTION[0]);

		log.info("TC_04_Step_Verify so dien thoai nhan 1");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Số tài khoản nhận 1"));

		log.info("TC_04_Step_So dien thoai/ tai khoan nhan");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_IN_VCB, "Số điện thoại/tài khoản nhận");

		log.info("TC_04_Step_Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, "Số tiền");

		log.info("TC_04_Step_Chon loi chuc");
		luckyGift.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/more_content");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION[0]);

		log.info("TC_04_Step_Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_Lay gia tri so tien chuyen");
		String amountTranferString = luckyGift.getDynamicAmountLabel(driver, "TỔNG TIỀN").replaceAll("\\D+", "");

		log.info("TC_04_Step_Doi kieu du lieu");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_04_Step_Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Verify hinh thuc giao dich");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Hình thức giao dịch"), "Quà tặng may mắn");

		log.info("TC_04_Verify tai khoan nguon");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

		log.info("TC_04_Verify thong tin nguoi nhan");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_IN_VCB + "/ " + LuckyGift_Data.LuckyGift.NAME_ACCEPT));

		log.info("TC_04_Verify So tien chuyen");
		verifyEquals(amountTranferString, LuckyGift_Data.LuckyGift.MONEY);

		log.info("TC_04_Verify phi giao dich");
		String amountCostString = luckyGift.getDynamicAmountCostLabel(driver, "Tổng số tiền phí").replaceAll("\\D+", "");

		log.info("TC_04_Step_: Verify loi chuc");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Lời chúc"), LuckyGift_Data.LuckyGift.WISHES_OPTION[0]);

		log.info("TC_04_Step_Chon phuong thuc xac thuc");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCURACY[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCURACY[1]);

		log.info("TC_04_Step_Tiep tuc");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_Nhap ma xac thuc");
		luckyGift.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_04_Step_Tiep tuc");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");
	}

	@Test
	public void TC_05_ChuyenTienNguoiNhanKhacVCBXacNhanMKVaSTK() {
		log.info("TC_05_1_Click trang thai lenh chuyen tien");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Quà tặng may mắn");

		log.info("TC_05_Step_Select tai khoan nguon");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

		log.info("TC_05_Step_Get so du kha dung");
		amountStartString = luckyGift.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		log.info("TC_05_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_05_Step_Select ngan hang");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_OPTION[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_OPTION[1]);

		log.info("TC_05_Step_Verify so nguoi nhan, neu trong ngan hang VCB, so list danh sach se hien thi toi da 3");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Nguời nhận (0/3)"));

		log.info("TC_05_Step_Click add danh sach nguoi nhan");
		luckyGift.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivAdd");

		log.info("TC_05_Step_So dien thoai/ tai khoan nhan");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_OUT_VCB, "Số điện thoại/tài khoản nhận");

		log.info("TC_05_Step_Ngan hang thu huong");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng thụ hưởng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK);

		log.info("TC_05_Step_Verify so dien thoai nhan 1");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Số tài khoản 1"));

		log.info("TC_05_Step_Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, "Số tiền");

		log.info("TC_05_Step_Chon loi chuc");
		luckyGift.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/more_content");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION[0]);

		log.info("TC_05_Step_Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_Lay gia tri so tien chuyen");
		String amountTranferString = luckyGift.getDynamicAmountLabel(driver, "TỔNG TIỀN").replaceAll("\\D+", "");

		log.info("TC_05_Step_Doi kieu du lieu");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_05_Step_Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Verify hinh thuc giao dich");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Hình thức giao dịch"), "Quà tặng may mắn");

		log.info("TC_05_Verify tai khoan nguon");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

		log.info("TC_05_Verify thong tin nguoi nhan");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_OUT_VCB + "/ " + LuckyGift_Data.LuckyGift.NAME_ACCEPT));

		log.info("TC_05_Verify So tien chuyen");
		verifyEquals(amountTranferString, LuckyGift_Data.LuckyGift.MONEY);

		log.info("TC_05_Verify phi giao dich");
		String amountCostString = luckyGift.getDynamicAmountCostLabel(driver, "Tổng số tiền phí").replaceAll("\\D+", "");

		log.info("TC_05_Step_: Verify loi chuc");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Lời chúc"), LuckyGift_Data.LuckyGift.WISHES_OPTION[0]);

		log.info("TC_05_Step_Chon phuong thuc xac thuc");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCURACY[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCURACY[0]);

		log.info("TC_05_Step_Tiep tuc");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_Nhap ma xac thuc");
		luckyGift.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_05_Step_Tiep tuc");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");
	}

	@Test
	public void TC_06_ChuyenTienNguoiNhanKhacVCBXacNhanOTPVaSTK() {
		log.info("TC_06_1_Click trang thai lenh chuyen tien");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Quà tặng may mắn");

		log.info("TC_06_Step_Select tai khoan nguon");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

		log.info("TC_06_Step_Get so du kha dung");
		amountStartString = luckyGift.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		log.info("TC_06_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_06_Step_Select ngan hang");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_OPTION[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_OPTION[1]);

		log.info("TC_06_Step_Verify so nguoi nhan, neu trong ngan hang VCB, so list danh sach se hien thi toi da 3");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Nguời nhận (0/3)"));

		log.info("TC_06_Step_Click add danh sach nguoi nhan");
		luckyGift.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivAdd");

		log.info("TC_06_Step_Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_Step_Hinh thuc nhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TYPE_ACCEPT_OPTION[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TYPE_ACCEPT_OPTION[1]);

		log.info("TC_06_Step_Verify so dien thoai nhan 1");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Số tài khoản nhận 1"));

		log.info("TC_06_Step_So dien thoai/ tai khoan nhan");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_OUT_VCB, "Số điện thoại/tài khoản nhận");

		log.info("TC_06_Step_Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, "Số tiền");

		log.info("TC_06_Step_Chon loi chuc");
		luckyGift.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/more_content");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION[1]);

		log.info("TC_06_Step_Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_Step_Lay gia tri so tien chuyen");
		String amountTranferString = luckyGift.getDynamicAmountLabel(driver, "TỔNG TIỀN").replaceAll("\\D+", "");

		log.info("TC_06_Step_Doi kieu du lieu");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_06_Step_Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_Verify hinh thuc giao dich");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Hình thức giao dịch"), "Quà tặng may mắn");

		log.info("TC_06_Verify tai khoan nguon");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

		log.info("TC_06_Verify thong tin nguoi nhan");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_OUT_VCB + "/ " + LuckyGift_Data.LuckyGift.NAME_ACCEPT));

		log.info("TC_06_Verify So tien chuyen");
		verifyEquals(amountTranferString, LuckyGift_Data.LuckyGift.MONEY);

		log.info("TC_06_Verify phi giao dich");
		String amountCostString = luckyGift.getDynamicAmountCostLabel(driver, "Tổng số tiền phí").replaceAll("\\D+", "");

		log.info("TC_06_Step_: Verify loi chuc");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Lời chúc"), LuckyGift_Data.LuckyGift.WISHES_OPTION[1]);

		log.info("TC_06_Step_Chon phuong thuc xac thuc");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCURACY[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCURACY[1]);

		log.info("TC_06_Step_Tiep tuc");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_Step_Nhap ma xac thuc");
		luckyGift.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_06_Step_Tiep tuc");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	}

	@AfterMethod(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		// service.stop();
	}

}
