package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.TransferInVCBRecurrent;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoney_Data;

public class TransferMoneyRecurrent extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyInVcbPageObject transferRecurrent;
	TransferInVCBRecurrent info = new TransferInVCBRecurrent("0010000000322", "0010000000318", "1", "Ngày", "", "",
			"500000", "Người chuyển trả", "test", "SMS OTP");
	TransferInVCBRecurrent info1 = new TransferInVCBRecurrent("0011140000647", "0010000000318", "2", "Ngày", "", "",
			"10", "Người nhận trả", "test", "SMS OTP");
	TransferInVCBRecurrent info2 = new TransferInVCBRecurrent("0010000000322", "0010000000318", "1", "Tháng", "", "",
			"500000", "Người chuyển trả", "test", "Mật khẩu đăng nhập");
	TransferInVCBRecurrent info3 = new TransferInVCBRecurrent("0011140000647", "0010000000318", "2", "Tháng", "", "",
			"10", "Người nhận trả", "test", "Mật khẩu đăng nhập");
	TransferInVCBRecurrent info4 = new TransferInVCBRecurrent("0010000000322", "0010000000318", "1", "Năm", "", "",
			"500000", "Người chuyển trả", "test", "Mật khẩu đăng nhập");
	TransferInVCBRecurrent info5 = new TransferInVCBRecurrent("0011140000647", "0010000000318", "2", "Năm", "", "",
			"10", "Người nhận trả", "test", "Mật khẩu đăng nhập");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeMethod
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);
		transferRecurrent = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		login.clickToDynamicAcceptButton("com.android.packageinstaller:id/permission_allow_button");

		login.inputToDynamicLogInTextBox(LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		login.clickToDynamicButton("Tiếp tục");

		login.inputToDynamicInputBox(LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		login.clickToDynamicButton("Tiếp tục");

		login.inputToDynamicOtpOrPIN(LogIn_Data.Login_Account.OTP, "Tiếp tục");

		login.clickToDynamicButton("Tiếp tục");
		
		login.clickToDynamicAcceptButton("com.android.packageinstaller:id/permission_allow_button");

	}
	
	@Test
	public void TC_01_ChuyenTien_VND_DinhKy_1Ngay_CoPhiGiaoDichNguoiChuyenTra_XacThucBangOTP() {
		log.info("TC_01_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollToText("Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong VCB");

		log.info("TC_01_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền định kỳ");

		log.info("TC_01_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown("Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info.sourceAccount);
		transferRecurrent.inputToDynamicInputBox(info.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info.frequencyNumber);

		log.info("TC_01_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(info.money, "Số tiền");

		log.info("TC_01_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info.fee);

		log.info("TC_01_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(info.note, "Nội dung");

		log.info("TC_01_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_9_Kiem tra man hinh xac nhan thong tin");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Hình thức chuyển tiền"),
				"Chuyển tiền định kỳ");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tài khoản nguồn"), info.sourceAccount);
		verifyTrue(transferRecurrent.getDynamicTextInTextView("Tài khoản đích/ VND")
				.contains(info.destinationAccount));
		verifyTrue(transferRecurrent.getDynamicTextInTextView("Số tiền").contains(addCommasToLong(info.money)));
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tần suất"),
				info.frequencyNumber + " " + info.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Số lần giao dịch"), "2");
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2("Số tiền phí"), info.fee);
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Nội dung"), info.note);

		log.info("TC_01_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollToText("Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info.authenticationMethod);

		log.info("TC_01_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton("Tiếp tục");

		transferRecurrent.inputToDynamicOtpOrPIN(LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferRecurrent.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(TransferMoney_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tên người thụ hưởng"), "NGO TRI NAM");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tài khoản đích"), info.destinationAccount);
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Nội dung"), info.note);
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));
	}

	@Test
	public void TC_02_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		log.info("TC_02_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollToText("Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong VCB");

		log.info("TC_02_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền định kỳ");

		log.info("TC_02_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown("Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info1.sourceAccount);
		transferRecurrent.inputToDynamicInputBox(info1.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_02_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info1.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info1.frequencyNumber);

		log.info("TC_02_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(info1.money, "Số tiền");

		log.info("TC_02_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info1.fee);

		log.info("TC_02_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(info1.note, "Nội dung");

		log.info("TC_02_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton("Tiếp tục");

		log.info("TC_02_9_Kiem tra man hinh xac nhan thong tin");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Hình thức chuyển tiền"),
				"Chuyển tiền định kỳ");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tài khoản nguồn"), info1.sourceAccount);
		verifyTrue(transferRecurrent.getDynamicTextInTextView("Tài khoản đích/ VND")
				.contains(info1.destinationAccount));
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tần suất"),
				info1.frequencyNumber + " " + info1.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Số lần giao dịch"), "2");
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2("Số tiền phí"), info1.fee);
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Nội dung"), info1.note);

		log.info("TC_02_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollToText("Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info1.authenticationMethod);

		log.info("TC_02_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton("Tiếp tục");

		transferRecurrent.inputToDynamicOtpOrPIN(LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferRecurrent.clickToDynamicButton("Tiếp tục");

		log.info("TC_02_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(TransferMoney_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tên người thụ hưởng"), "NGO TRI NAM");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tài khoản đích"), info1.destinationAccount);
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Nội dung"), info1.note);
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));

		log.info("TC_02_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton("Thực hiện giao dịch mới");

	}

	@Test
	public void TC_03_ChuyenTien_VND_DinhKy_1Thang_CoPhiGiaoDichNguoiChuyenTra_XacThucBangMatKhau() {
		log.info("TC_03_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollToText("Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong VCB");

		log.info("TC_03_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền định kỳ");

		log.info("TC_03_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown("Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info2.sourceAccount);
		transferRecurrent.inputToDynamicInputBox(info2.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_03_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info2.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info2.frequencyNumber);

		log.info("TC_03_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(info2.money, "Số tiền");

		log.info("TC_03_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info2.fee);

		log.info("TC_03_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(info2.note, "Nội dung");

		log.info("TC_03_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_9_Kiem tra man hinh xac nhan thong tin");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Hình thức chuyển tiền"),
				"Chuyển tiền định kỳ");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tài khoản nguồn"), info2.sourceAccount);
		verifyTrue(transferRecurrent.getDynamicTextInTextView("Tài khoản đích/ VND")
				.contains(info2.destinationAccount));
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tần suất"),
				info2.frequencyNumber + " " + info2.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Số lần giao dịch"), "2");
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2("Số tiền phí"), info2.fee);
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Nội dung"), info2.note);

		log.info("TC_03_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollToText("Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info2.authenticationMethod);

		log.info("TC_03_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton("Tiếp tục");

		transferRecurrent.inputToPasswordConfirm(LogIn_Data.Login_Account.NEW_PASSWORD);

		transferRecurrent.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(TransferMoney_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tên người thụ hưởng"), "NGO TRI NAM");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tài khoản đích"), info2.destinationAccount);
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Nội dung"), info2.note);
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));

		log.info("TC_03_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton("Thực hiện giao dịch mới");

	}

	@Test
	public void TC_04_ChuyenTien_NgoaiTe_DinhKy_2Thang_CoPhiGiaoDichNguoiNhanTra_XacThucBangMatKhauDangNhap() {
		log.info("TC_04_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollToText("Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong VCB");

		log.info("TC_04_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền định kỳ");

		log.info("TC_04_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown("Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info3.sourceAccount);
		transferRecurrent.inputToDynamicInputBox(info3.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_04_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info3.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info3.frequencyNumber);

		log.info("TC_04_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(info3.money, "Số tiền");

		log.info("TC_04_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info3.fee);

		log.info("TC_04_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(info3.note, "Nội dung");

		log.info("TC_04_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton("Tiếp tục");

		log.info("TC_04_9_Kiem tra man hinh xac nhan thong tin");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Hình thức chuyển tiền"),
				"Chuyển tiền định kỳ");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tài khoản nguồn"), info3.sourceAccount);
		verifyTrue(transferRecurrent.getDynamicTextInTextView("Tài khoản đích/ VND")
				.contains(info3.destinationAccount));
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tần suất"),
				info3.frequencyNumber + " " + info3.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Số lần giao dịch"), "2");
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2("Số tiền phí"), info3.fee);
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Nội dung"), info3.note);

		log.info("TC_04_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollToText("Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info3.authenticationMethod);

		log.info("TC_04_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton("Tiếp tục");

		transferRecurrent.inputToPasswordConfirm(LogIn_Data.Login_Account.NEW_PASSWORD);

		transferRecurrent.clickToDynamicButton("Tiếp tục");

		log.info("TC_04_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(TransferMoney_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tên người thụ hưởng"), "NGO TRI NAM");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tài khoản đích"), info3.destinationAccount);
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Nội dung"), info3.note);
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));

		log.info("TC_04_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton("Thực hiện giao dịch mới");

	}

//	@Test
	public void TC_05_ChuyenTien_VND_DinhKy_1Nam_CoPhiGiaoDichNguoiChuyenTra_XacThucBangMatKhauDangNhap() {
		log.info("TC_05_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollToText("Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong VCB");

		log.info("TC_05_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền định kỳ");

		log.info("TC_05_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown("Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info4.sourceAccount);
		transferRecurrent.inputToDynamicInputBox(info4.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_05_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info4.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info4.frequencyNumber);

		log.info("TC_05_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(info4.money, "Số tiền");

		log.info("TC_05_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info4.fee);

		log.info("TC_05_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(info4.note, "Nội dung");

		log.info("TC_05_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_9_Kiem tra man hinh xac nhan thong tin");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Hình thức chuyển tiền"),
				"Chuyển tiền định kỳ");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tài khoản nguồn"), info4.sourceAccount);
		verifyTrue(transferRecurrent.getDynamicTextInTextView("Tài khoản đích/ VND")
				.contains(info4.destinationAccount));
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tần suất"),
				info4.frequencyNumber + " " + info4.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Số lần giao dịch"), "2");
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2("Số tiền phí"), info4.fee);
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Nội dung"), info4.note);

		log.info("TC_05_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollToText("Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info4.authenticationMethod);

		log.info("TC_05_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton("Tiếp tục");

		transferRecurrent.inputToPasswordConfirm(LogIn_Data.Login_Account.NEW_PASSWORD);

		transferRecurrent.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(TransferMoney_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tên người thụ hưởng"), "NGO TRI NAM");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tài khoản đích"), info4.destinationAccount);
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Nội dung"), info4.note);
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));

		log.info("TC_05_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton("Thực hiện giao dịch mới");

	}

//	@Test
	public void TC_06_ChuyenTien_NgoaiTe_DinhKy_2Nam_CoPhiGiaoDichNguoiChuyenTra_XacThucBangMatKhauDangNhap() {
		log.info("TC_06_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollToText("Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong VCB");

		log.info("TC_06_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Chuyển tiền định kỳ");

		log.info("TC_06_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown("Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info5.sourceAccount);
		transferRecurrent.inputToDynamicInputBox(info5.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_06_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info5.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info5.frequencyNumber);

		log.info("TC_06_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(info5.money, "Số tiền");

		log.info("TC_06_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info5.fee);

		log.info("TC_06_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(info5.note, "Nội dung");

		log.info("TC_06_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton("Tiếp tục");

		log.info("TC_06_9_Kiem tra man hinh xac nhan thong tin");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Hình thức chuyển tiền"),
				"Chuyển tiền định kỳ");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tài khoản nguồn"), info5.sourceAccount);
		verifyTrue(transferRecurrent.getDynamicTextInTextView("Tài khoản đích/ VND")
				.contains(info5.destinationAccount));
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tần suất"),
				info5.frequencyNumber + " " + info5.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Số lần giao dịch"), "2");
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2("Số tiền phí"), info5.fee);
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Nội dung"), info5.note);

		log.info("TC_06_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollToText("Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(info5.authenticationMethod);

		log.info("TC_06_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton("Tiếp tục");

		transferRecurrent.inputToPasswordConfirm(LogIn_Data.Login_Account.NEW_PASSWORD);

		transferRecurrent.clickToDynamicButton("Tiếp tục");

		log.info("TC_06_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(TransferMoney_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tên người thụ hưởng"), "NGO TRI NAM");
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Tài khoản đích"), info5.destinationAccount);
		verifyEquals(transferRecurrent.getDynamicTextInTextView("Nội dung"), info5.note);
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));

		log.info("TC_06_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton("Thực hiện giao dịch mới");

	}

	@AfterMethod(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
