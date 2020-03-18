package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Validation_QuickMoneyTransfer247_6 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private TransferMoneyObject transferMoney;
	List<String> listExpect;
	List<String> listActual;
	private String amountTranferString;

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
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
	}

	@Test
	public void TC_82_KiemTraTextHienThiTaiKhoanNguon() {
		log.info("TC_82_Step_Verify text tai khoan nguon");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_FROM_LABEL));

		log.info("TC_82_Lay gia tri tai khoan nguon tren man xac nhan thong tin");
		String accountFrom = transferMoney.getDynamicTextByLabel(driver, "Tài khoản nguồn");

		log.info("TC_82_verify so tai khoan");
		verifyEquals(accountFrom, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);
	}

	@Test
	public void TC_83_KiemTraTextHienThiTaiKhoanDichVaHoTen() {
		log.info("TC_83_Step_Verify text tai khoan dich");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO_LABEL));

		log.info("TC_83_Step_: Tai khoan dich va ten nguoi huong");
		verifyEquals(transferMoney.getDynamicTextByLabel(driver, "Tài khoản đích/ VND"), Account_Data.Valid_Account.ACCOUNT_TO +"/" );
	}

	@Test
	public void TC_84_KiemTraTextHienThiSoTienGiaoDich() {
		log.info("TC_84_Step_Verify text so tien");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.MOUNT_LABEL));

		log.info("TC_84_Step_Verify so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền").replaceAll("\\D+", "");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);
	}

	@Test
	public void TC_85_KiemTraSoTienGocVoiTaiKhoanNguonLaUSD() {
		log.info("TC_85_Step_Click quay lai man hinh chuyen tien nhanh 24/7");
		transferMoney.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_85_Step_Select tai khoan nguon la USD");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[1]);

		log.info("TC_85_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, "Thông tin giao dịch", "1");

		log.info("TC_85_Lay so tien ty gia quy doi");
		String[] a = transferMoney.getDynamicTextByLabel(driver, "Tỷ giá quy đổi tham khảo").split("~");
		String getChangeVNDString1 = a[1].replaceAll(".00 VND", "");
		String getChangeVNDString =	getChangeVNDString1.replaceAll("\\D+", "");
		int getChangeVND = Integer.parseInt(getChangeVNDString);

		log.info("TC_85_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_85_Step_Verify so tien chuyen USD");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền").replace(".00 USD", "");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY_USD);
		System.out.print(amountTranferString +"------------");
		long amountAfter = Long.parseLong(amountTranferString);

		log.info("TC_85_Step_Verify so tien chuyen VND sau quy doi");
		String amountTranferUSDString = transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvQuyDoi").replaceAll("\\D+", "");
		long amountAfterUSD = Long.parseLong(amountTranferUSDString);
		
		System.out.print(amountTranferUSDString +"------------");
		System.out.print(amountAfter * getChangeVND +"------------");
		
		verifyEquals(amountAfterUSD, amountAfter * getChangeVND);
	}

	@Test
	public void TC_86_KiemTraPhiChuyenTien() {
		log.info("TC_86_Step_Verify label so tien phi");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.AMOUNT_FEE_LABEL));

		log.info("TC_86_Step_: Verify so tien phi");
		String amountfeeString = transferMoney.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		verifyEquals(amountfeeString, TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT_MK_VND);

		log.info("TC_86_Step_: Verify loai phi");
		verifyEquals(transferMoney.getTextDynamicFollowText(driver, "Số tiền phí","1","com.VCB:id/tvContent"), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);
	}

	@Test
	public void TC_87_KiemTraNoiDungChuyenTien() {
		log.info("TC_87_Step_Verify label noi dung");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_LABEL));

		log.info("TC_87_Lay gia tri noi dung chuyen tien");
		String note = transferMoney.getDynamicTextByLabel(driver, "Nội dung");

		log.info("TC_87_verify noi dung");
		verifyEquals(note, TransferMoneyQuick_Data.TransferQuick.NOTE);
	}

	@Test
	public void TC_88_KiemTraButtonTiepTuc() {
		log.info("TC_88_Step_click button tiep tục");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_88_Verify text xac thuc giao dich");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, TransferMoneyQuick_Data.TransferQuick.CONFIRM_LABEL), "Xác thực giao dịch");
	}

	@Test
	public void TC_89_KiemTraButtonQuayLai() {
		log.info("TC_89_Step_click button quay lai");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");

		log.info("TC_89_Verify text man hinh xac nhan thong tin");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, TransferMoneyQuick_Data.TransferQuick.CONFIRM_INFO_LABEL), "Xác nhận thông tin");

		log.info("TC_89_Step_click tiep button quay lai");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_89_Verify text man hinh xac nhan thong tin");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, TransferMoneyQuick_Data.TransferQuick.TRANSFER_MONEY_LABEL), "Chuyển tiền nhanh 24/7");
	}

	@Test
	public void TC_90_KiemTraManHinhXacThucBangOTP() {
		log.info("TC_88_Step_click button tiep tục");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_90_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_90_Step_click button tiep tục");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_90_Verify text xac thuc giao dich");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, TransferMoneyQuick_Data.TransferQuick.CONFIRM_LABEL), "Xác thực giao dịch");

		log.info("TC_90_Verify so dien thoai bi an");
		String[] phone = transferMoney.getDynamicTextByLabel(driver, "Xác thực giao dịch").split("thoại ");
		verifyEquals(phone[1], LogIn_Data.Login_Account.PHONE_HIDDEN);

		log.info("TC_90_Verify message xac thuc OTP");
		verifyEquals(transferMoney.getDynamicTextByLabel(driver, "Xác thực giao dịch"), "Quý khách vui lòng nhập mã OTP đã được gửi về số điện thoại " + LogIn_Data.Login_Account.PHONE_HIDDEN);

		log.info("TC_90_Verify hien thi button tiep tuc");
		verifyTrue(transferMoney.isDynamicButtonDisplayed(driver, "Tiếp tục"));
	}

	@Test
	public void TC_91_VerifyMessageKhiKhongNhapOTP() {
		log.info("TC_91_Click button tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_91_Step_verify message khi khong nhap ma OTP ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.OTP_BLANK_MESSAGE));

		log.info("TC_91_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_92_VerifyMessageKhiNhapOTPNhoHon6KyTu() {
		log.info("TC_92_Nhap OTP la 123");
		transferMoney.inputToDynamicOtp(driver, "123", "Tiếp tục");

		log.info("TC_92_Click button tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_92_Step_verify message khi nhap ma OTP khong du");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.OTP_NOT_ENOUGH_MESSAGE));

		log.info("TC_92_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	// @Test TC93 ---- Check lai timeout OTP bỏ case này

	@Test
	public void TC_94_VerifyMessageKhiNhapOTPLonHon6KyTu() {
		log.info("TC_94_Nhap OTP la 123");
		transferMoney.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP + "0", "Tiếp tục");

		log.info("TC_94_Lay gia tri OTP nhap tren app");
		String otp = transferMoney.getTextInDynamicOtp(driver, "Tiếp tục");

		log.info("TC_94_Verify nhap OTP chi cho phep 6 ky tu");
		verifyEquals(otp, LogIn_Data.Login_Account.OTP);
	}

	@Test
	public void TC_95_VerifyMessageKhiNhapOTPKhongTonTai() {
		log.info("TC_95_Nhap OTP khong ton tai");
		transferMoney.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP_NUMBER_INVALID, "Tiếp tục");

		log.info("TC_95_Click button tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_95_Step_verify message khi nhap ma OTP khong dung");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.OTP_NOT_EXIST_MESSAGE));

		log.info("TC_95_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");

		log.info("TC_95_Nhap OTP khong ton tai");
		transferMoney.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP_NUMBER_INVALID, "Tiếp tục");

		log.info("TC_95_Click button tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_95_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_95_Nhap OTP khong ton tai");
		transferMoney.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP_NUMBER_INVALID, "Tiếp tục");

		log.info("TC_95_Click button tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_95_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_96_VerifyMessageNhapSaiOTPQua4Lan() {
		log.info("TC_96_Nhap OTP khong ton tai");
		transferMoney.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP_NUMBER_INVALID, "Tiếp tục");

		log.info("TC_96_Click button tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_96_Step_verify message khi nhap ma OTP khong dung");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.OTP_OVER_4_TIMES_MESSAGE));

		log.info("TC_96_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_97_XoaThongTinKhiQua4LanNhap() {
		log.info("TC_97_Kiem tra text mac dinh truong so tai khoan nhan");
		verifyEquals(transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "1"), "Nhập/chọn tài khoản nhận VND");

		log.info("TC_97_Kiem tra text mac dinh truong ngan hang huong");
		verifyEquals(transferMoney.getDynamicTextInDropDownByHeader(driver, "Thông tin người hưởng", "2"), "Ngân hàng hưởng");

		log.info("TC_97_Kiem tra text mac dinh truong So tien");
		verifyEquals(transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1"), "Số tiền");
	}

	@Test
	public void TC_98_OTPNhapDungVaCheckManXacNhan() {
		log.info("TC_98_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_98_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_98_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_98_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_98_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_98_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Thông tin giao dịch", "1");

		log.info("TC_98_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_98_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_98_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_98_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_98_Step_click button tiep tục");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_98_Nhap OTP");
		transferMoney.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_98_Step_click button tiep tục");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Verify icon thanh cong");
		verifyTrue(transferMoney.isDynamicImageSuccess(driver, "CHUYỂN KHOẢN THÀNH CÔNG"));
	}
}
