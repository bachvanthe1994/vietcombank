package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.TransferInVCBRecurrent;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.TransferMoneyStatusPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class TransferMoneyRecurrent extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyInVcbPageObject transferRecurrent;
	private TransferMoneyStatusPageObject transferStatus;
	private HomePageObject homePage;
	
	TransferInVCBRecurrent info = new TransferInVCBRecurrent("0010000000322", "0010000000318", "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "SMS OTP");
	TransferInVCBRecurrent info1 = new TransferInVCBRecurrent("0011140000647", "0010000000318", "2", "Ngày", "", "", "10", "Người nhận trả", "test", "SMS OTP");
	TransferInVCBRecurrent info2 = new TransferInVCBRecurrent("0010000000322", "0010000000318", "1", "Tháng", "", "", "500000", "Người chuyển trả", "test", "Mật khẩu đăng nhập");
	TransferInVCBRecurrent info3 = new TransferInVCBRecurrent("0011140000647", "0010000000318", "2", "Tháng", "", "", "10", "Người nhận trả", "test", "Mật khẩu đăng nhập");
	TransferInVCBRecurrent info4 = new TransferInVCBRecurrent("0010000000322", "0010000000318", "1", "Năm", "", "", "500000", "Người chuyển trả", "test", "Mật khẩu đăng nhập");
	TransferInVCBRecurrent info5 = new TransferInVCBRecurrent("0011140000647", "0010000000318", "2", "Năm", "", "", "10", "Người nhận trả", "test", "Mật khẩu đăng nhập");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);
		transferRecurrent = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
	}

	@Test
	public void TC_01_ChuyenTien_VND_DinhKy_1Ngay_CoPhiGiaoDichNguoiChuyenTra_XacThucBangOTP() {
		log.info("TC_01_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_01_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		transferRecurrent.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info.frequencyNumber);

		log.info("TC_01_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info.money, "Số tiền");

		log.info("TC_01_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.fee);

		log.info("TC_01_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info.note, "Nội dung");

		log.info("TC_01_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_9_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_9_1_Kiem tra hinh thuc chuyen tien");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

		log.info("TC_01_9_2_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info.sourceAccount);

		log.info("TC_01_9_3_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info.destinationAccount));

		log.info("TC_01_9_4_Kiem tra so tien");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số tiền").contains(addCommasToLong(info.money)));

		log.info("TC_01_9_5_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất"), info.frequencyNumber + " " + info.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

		log.info("TC_01_9_6_Kiem tra so tien phi");
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2(driver, "Số tiền phí"), info.fee);

		log.info("TC_01_9_7_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info.note);

		log.info("TC_01_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_01_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		transferRecurrent.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		log.info("TC_01_12_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), "NGO TRI NAM");

		log.info("TC_01_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info.destinationAccount);

		log.info("TC_01_12_3_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info.note);
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));
		
		log.info("TC_01_13_Click Thuc hien giao dich moi");
		login.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
	}
	
	@Test
	public void TC_02_TrangThaiGiaoDich_ChuyenTien_VND_DinhKy_1Ngay_CoPhiGiaoDichNguoiChuyenTra_XacThucBangOTP() {
		transferStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		
		log.info("TC_02_1: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_02_2: Click Trang thai lenh chuyen tien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");
		
		log.info("TC_02_3: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		
		log.info("TC_02_4: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
	}

	@Test
	public void TC_03_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		log.info("TC_03_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_03_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_03_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		transferRecurrent.inputToDynamicInputBox(driver, info1.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_03_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info1.frequencyNumber);

		log.info("TC_03_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info1.money, "Số tiền");

		log.info("TC_03_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.fee);

		log.info("TC_03_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info1.note, "Nội dung");

		log.info("TC_03_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_9_Kiem tra man hinh xac nhan thong tin");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

		log.info("TC_03_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info1.sourceAccount);

		log.info("TC_03_9_2_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info1.destinationAccount));

		log.info("TC_03_9_3_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất"), info1.frequencyNumber + " " + info1.frequencyCategory + "/ lần");

		log.info("TC_03_9_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

		log.info("TC_03_9_5_Kiem tra so tien phi");
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2(driver, "Số tiền phí"), info1.fee);

		log.info("TC_03_9_6_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info1.note);

		log.info("TC_03_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.authenticationMethod);

		log.info("TC_03_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		transferRecurrent.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		log.info("TC_03_12_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), "NGO TRI NAM");

		log.info("TC_03_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info1.destinationAccount);

		log.info("TC_03_12_3_Kiem tra ten noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info1.note);

		log.info("TC_03_12_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_03_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
	}

	@Test
	public void TC_04_TrangThaiGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		transferStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		
		log.info("TC_04_1: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_04_2: Click Trang thai lenh chuyen tien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");
		
		log.info("TC_04_3: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		
		log.info("TC_04_4: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
	}
	
	@Test
	public void TC_05_ChuyenTien_VND_DinhKy_1Thang_CoPhiGiaoDichNguoiChuyenTra_XacThucBangMatKhau() {
		log.info("TC_05_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_05_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_05_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
		transferRecurrent.inputToDynamicInputBox(driver, info2.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_05_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info2.frequencyNumber);

		log.info("TC_05_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info2.money, "Số tiền");

		log.info("TC_05_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.fee);

		log.info("TC_05_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info2.note, "Nội dung");

		log.info("TC_05_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_9_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_05_9_1_Kiem tra hinh thuc chuyen tien");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

		log.info("TC_05_9_2_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info2.sourceAccount);

		log.info("TC_05_9_3_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info2.destinationAccount));

		log.info("TC_05_9_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất"), info2.frequencyNumber + " " + info2.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

		log.info("TC_05_9_5_Kiem tra so tien phi");
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2(driver, "Số tiền phí"), info2.fee);

		log.info("TC_05_9_6_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info2.note);

		log.info("TC_05_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.authenticationMethod);

		log.info("TC_05_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		transferRecurrent.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);

		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		log.info("TC_05_12_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), "NGO TRI NAM");

		log.info("TC_05_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info2.destinationAccount);

		log.info("TC_05_12_3_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info2.note);

		log.info("TC_05_12_4_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_05_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
	}

	@Test
	public void TC_06_TrangThaiGiaoDich_ChuyenTien_VND_DinhKy_1Thang_CoPhiGiaoDichNguoiChuyenTra_XacThucBangMatKhau() {
		transferStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		
		log.info("TC_06_1: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_06_2: Click Trang thai lenh chuyen tien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");
		
		log.info("TC_06_3: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		
		log.info("TC_06_4: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
	}
	
	@Test
	public void TC_07_ChuyenTien_NgoaiTe_DinhKy_2Thang_CoPhiGiaoDichNguoiNhanTra_XacThucBangMatKhauDangNhap() {
		log.info("TC_07_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_07_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_07_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.sourceAccount);
		transferRecurrent.inputToDynamicInputBox(driver, info3.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_07_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info3.frequencyNumber);

		log.info("TC_07_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info3.money, "Số tiền");

		log.info("TC_07_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.fee);

		log.info("TC_07_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info3.note, "Nội dung");

		log.info("TC_07_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_9_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_07_9_1_Kiem tra hinh thuc chuyen tien");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

		log.info("TC_07_9_2_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info3.sourceAccount);

		log.info("TC_07_9_3_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info3.destinationAccount));

		log.info("TC_07_9_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất"), info3.frequencyNumber + " " + info3.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

		log.info("TC_07_9_5_Kiem tra so tien phi");
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2(driver, "Số tiền phí"), info3.fee);

		log.info("TC_07_9_6_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info3.note);

		log.info("TC_07_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.authenticationMethod);

		log.info("TC_07_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		transferRecurrent.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);

		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		log.info("TC_07_12_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), "NGO TRI NAM");

		log.info("TC_07_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info3.destinationAccount);

		log.info("TC_07_12_3_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info3.note);

		log.info("TC_07_12_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_07_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	}

	@Test
	public void TC_08_TrangThaiGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Thang_CoPhiGiaoDichNguoiNhanTra_XacThucBangMatKhauDangNhap() {
		transferStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		
		log.info("TC_06_1: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_06_2: Click Trang thai lenh chuyen tien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");
		
		log.info("TC_06_3: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		
		log.info("TC_06_4: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
