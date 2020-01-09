package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Validation_QuickMoneyTransfer247_6 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyObject transferMoney;
	List<String> listExpect;
	List<String> listActual;
	private String Note;
	private String amountTranferString;
	private String costTranferString;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
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
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);

		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Thông tin giao dịch", "1");

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

	}

	@Test
	public void TC_82_KiemTraTextHienThiTaiKhoanNguon() {
		log.info("TC_05_Step_Verify text tai khoan nguon");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_FROM_LABEL));

		log.info("TC_58_Lay gia tri tai khoan nguon tren man xac nhan thong tin");
		String accountFrom = transferMoney.getDynamicAmountLabel(driver, "Tài khoản nguồn");

		log.info("TC_58_verify so tai khoan");
		verifyEquals(accountFrom, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);
	}

	@Test
	public void TC_83_KiemTraTextHienThiTaiKhoanDichVaHoTen() {
		log.info("TC_05_Step_Verify text tai khoan dich");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO_LABEL));

		log.info("TC_01_Step_: Tai khoan dich va ten nguoi huong");
		verifyEquals(transferMoney.getDynamicAmountLabel(driver, "Tài khoản đích/ VND"), Account_Data.Valid_Account.ACCOUNT_TO + "/ " + TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);
	}

	@Test
	public void TC_84_KiemTraTextHienThiSoTienGiaoDich() {
		log.info("TC_05_Step_Verify text so tien");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.MOUNT_LABEL));

		log.info("TC_05_Step_Verify so tien chuyen");
		amountTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replaceAll("\\D+", "");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);
	}

	@Test
	public void TC_85_KiemTraSoTienGocVoiTaiKhoanNguonLaUSD() {
		log.info("TC_05_Step_Click quay lai man hinh chuyen tien nhanh 24/7");
		transferMoney.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Step_Select tai khoan nguon la USD");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[1]);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, "Thông tin giao dịch", "1");

		log.info("TC_01_Lay so tien ty gia quy doi");
		String [] a = transferMoney.getDynamicAmountLabel(driver, "Tỷ giá quy đổi tham khảo").split("~");
		String getChangeVNDString = a[1].replaceAll("\\D+", "");
		int getChangeVND = Integer.parseInt(getChangeVNDString);
		
		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_Verify so tien chuyen USD");
		amountTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replace(".00 USD", "");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY_USD);
		long amountAfter = Long.parseLong(amountTranferString);
		
		log.info("TC_05_Step_Verify so tien chuyen VND sau quy doi");
		String amountTranferUSDString = transferMoney.getDynamicTextInTextViewLine2(driver, "Số tiền").replaceAll("\\D+", "");
		long amountAfterUSD = Long.parseLong(amountTranferUSDString);
		verifyEquals(amountAfterUSD, amountAfter * getChangeVND);
	}
	
	@Test
	public void TC_86_KiemTraPhiChuyenTien() {
		log.info("TC_05_Step_Verify label so tien phi");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.AMOUNT_FEE_LABEL));

		log.info("TC_01_Step_: Verify so tien phi");
		String amountfeeString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replace(" USD", "");
		verifyEquals(amountfeeString, TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT_USD);
		
		log.info("TC_01_Step_: Verify loai phi");
		verifyEquals(transferMoney.getDynamicTextInTextViewLine2(driver, "Số tiền phí"),TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);
	}

	@Test
	public void TC_87_KiemTraNoiDungChuyenTien() {
		log.info("TC_05_Step_Verify label noi dung");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_LABEL));
		
		log.info("TC_58_Lay gia tri noi dung chuyen tien");
		String note = transferMoney.getDynamicAmountLabel(driver, "Nội dung");

		log.info("TC_58_verify noi dung");
		verifyEquals(note, TransferMoneyQuick_Data.TransferQuick.NOTE);
	}
	
	@Test
	public void TC_88_KiemTraButtonTiepTuc() {
		log.info("TC_05_Step_click button tiep tục");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_Verify text xac thuc giao dich");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, TransferMoneyQuick_Data.TransferQuick.CONFIRM_LABEL), "Xác thực giao dịch");
	}
	
	@Test
	public void TC_89_KiemTraButtonQuayLai() {
		log.info("TC_05_Step_click button quay lai");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");
		
		log.info("TC_05_Verify text man hinh xac nhan thong tin");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, TransferMoneyQuick_Data.TransferQuick.CONFIRM_INFO_LABEL), "Xác nhận thông tin");
	}
	
	@Test
	public void TC_90_KiemTraManHinhXacThucBangOTP() {
		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);
		
		log.info("TC_05_Step_click button tiep tục");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_Verify text xac thuc giao dich");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, TransferMoneyQuick_Data.TransferQuick.CONFIRM_LABEL), "Xác thực giao dịch");
		
		String [] a = transferMoney.getDynamicAmountLabel(driver, "Xác thực giao dịch").split("thoại ");
		System.out.print(a);
		String c =a[1];



		
	}
}
