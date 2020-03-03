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
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.TransferMoneyStatusPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;
import vietcombank_test_data.TransferMoneyStatus_Data;

public class TransferMoneyRecurrent extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyInVcbPageObject transferRecurrent;
	private TransferMoneyStatusPageObject transferStatus;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	private String transferTime;
	String expectAvailableBalance;

	TransferInVCBRecurrent info = new TransferInVCBRecurrent(Account_Data.Valid_Account.ACCOUNT2, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "SMS OTP");
	TransferInVCBRecurrent info1 = new TransferInVCBRecurrent(Account_Data.Valid_Account.EUR_ACCOUNT, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "2", "Ngày", "", "", "10", "Người chuyển trả", "test", "SMS OTP");
	TransferInVCBRecurrent info2 = new TransferInVCBRecurrent(Account_Data.Valid_Account.ACCOUNT2, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "1", "Tháng", "", "", "500000", "Người nhận trả", "test", "Mật khẩu đăng nhập");
	TransferInVCBRecurrent info3 = new TransferInVCBRecurrent(Account_Data.Valid_Account.EUR_ACCOUNT, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "2", "Tháng", "", "", "10", "Người nhận trả", "test", "Mật khẩu đăng nhập");
	TransferInVCBRecurrent info4 = new TransferInVCBRecurrent(Account_Data.Valid_Account.USD_ACCOUNT, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "2", "Ngày", "", "", "10", "Người chuyển trả", "test", "SMS OTP");
	TransferInVCBRecurrent info5 = new TransferInVCBRecurrent(Account_Data.Valid_Account.USD_ACCOUNT, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "2", "Tháng", "", "", "10", "Người nhận trả", "test", "Mật khẩu đăng nhập");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
	}

	@Test
	public void TC_01_ChuyenTien_VND_DinhKy_1Ngay_CoPhiGiaoDichNguoiChuyenTra_XacThucBangOTP() {
		transferRecurrent = PageFactoryManager.getTransferMoneyInVcbPageObject(driver); 
		log.info("TC_01_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_01_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		expectAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

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
		transferRecurrent.scrollUp(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Hình thức chuyển tiền");
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
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_OTP_FEE_TO_OTHER_ACCOUNT_OWNER + " VND");

		log.info("TC_01_9_7_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info.note);

		log.info("TC_01_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_01_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		transferRecurrent.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		transferTime = transferRecurrent.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT, "4");

		log.info("TC_01_12_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_01_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info.destinationAccount);

		log.info("TC_01_12_3_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info.note);
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_01_13_Click Thuc hien giao dich moi");
		login.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
	}

	@Test
	public void TC_02_ChuyenTien_VND_KiemTraSoDuSauGiaoDich_PhiGiaoDich_NguoiChuyenTra() {
		log.info("TC_02_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_02_02_Lay so du");
		String actualAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

		log.info("TC_02_03_Kiem tra so du khong thay doi khi chua den han");
		verifyEquals(actualAvailableBalance, expectAvailableBalance);

	}

	@Test
	public void TC_03_TrangThaiGiaoDich_ChuyenTien_VND_DinhKy_1Ngay_CoPhiGiaoDichNguoiChuyenTra_XacThucBangOTP() {
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info.frequencyNumber));

		transferStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);

		log.info("TC_03_1: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_03_2: Click Trang thai lenh chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_03_3: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_03_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_03_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_03_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_03_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_03_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_03_9: Kiem tra thoi gian tao giao dich");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_03_10: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_03_11: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_03_12: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(info.money) + " VND");

		log.info("TC_03_13: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_03_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_03_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_03_16: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_03_17: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(info.money) + " VND");

		log.info("TC_03_18: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info.frequencyNumber + " " + info.frequencyCategory + "/ lần");

		log.info("TC_03_19: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_03_20: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_03_21: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

	@Test
	public void TC_04_HuyGiaoDich_ChuyenTien_VND_DinhKy_1Ngay_CoPhiGiaoDichNguoiChuyenTra_XacThucBangOTP() {
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info.frequencyNumber));

		log.info("TC_04_01: Click Huy Lenh");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_04_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_04_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_04_04: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_04_05: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_04_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_04_07: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_04_09: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_04_10: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(info.money) + " VND");

		log.info("TC_04_11: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info.frequencyNumber + " " + info.frequencyCategory + "/ lần");

		log.info("TC_04_12: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_04_13: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_04_14: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Không thành công"));

		log.info("TC_04_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_04_16: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
	}

	@Test
	public void TC_05_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		log.info("TC_05_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_05_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_05_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		expectAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

		transferRecurrent.inputToDynamicInputBox(driver, info1.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_05_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info1.frequencyNumber);

		log.info("TC_05_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info1.money, "Số tiền");

		log.info("TC_05_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.fee);

		log.info("TC_05_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info1.note, "Nội dung");

		log.info("TC_05_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_9_Kiem tra man hinh xac nhan thong tin");
		transferRecurrent.scrollUp(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Hình thức chuyển tiền");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

		log.info("TC_05_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info1.sourceAccount);

		log.info("TC_05_9_2_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info1.destinationAccount));

		log.info("TC_05_9_3_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất"), info1.frequencyNumber + " " + info1.frequencyCategory + "/ lần");

		log.info("TC_05_9_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

		log.info("TC_05_9_5_Kiem tra so tien phi");

		log.info("TC_05_9_6_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info1.note);

		log.info("TC_05_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.authenticationMethod);

		log.info("TC_05_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		transferRecurrent.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		transferTime = transferRecurrent.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT, "4");

		log.info("TC_05_12_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_05_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info1.destinationAccount);

		log.info("TC_05_12_3_Kiem tra ten noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info1.note);

		log.info("TC_05_12_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_05_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	}

	@Test
	public void TC_06_ChuyenTien_EUR_KiemTraSoDuSauGiaoDich_PhiGiaoDich_NguoiChuyenTra() {
		log.info("TC_06_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		log.info("TC_06_02_Lay so du");
		String actualAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

		log.info("TC_06_03_Kiem tra so du khong thay doi khi chua den han");
		verifyEquals(actualAvailableBalance, expectAvailableBalance);

	}

	@Test
	public void TC_07_TrangThaiGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info1.frequencyNumber));

		log.info("TC_07_01: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_07_02: Click Trang thai lenh chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_07_03: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_07_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_07_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_07_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_07_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_07_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_07_9: Kiem tra thoi gian tao dao dich");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_07_10: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_07_11: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_07_12: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), info1.money + " EUR");

		log.info("TC_07_13: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_07_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_07_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_07_16: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_07_17: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), info1.money + " EUR");

		log.info("TC_07_18: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info1.frequencyNumber + " " + info1.frequencyCategory + "/ lần");

		log.info("TC_07_19: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_07_20: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_07_21: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

	@Test
	public void TC_08_HuyGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info1.frequencyNumber));

		log.info("TC_08_01: Click Huy Lenh");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_08_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_08_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_08_04: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_08_05: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_08_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_08_07: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_08_09: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_08_10: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(info1.money) + " EUR");

		log.info("TC_08_11: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info1.frequencyNumber + " " + info1.frequencyCategory + "/ lần");

		log.info("TC_08_12: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_08_13: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_08_14: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Không thành công"));

		log.info("TC_08_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_08_16: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
	}

	@Test
	public void TC_09_ChuyenTien_VND_DinhKy_1Thang_CoPhiGiaoDichNguoiChuyenTra_XacThucBangMatKhau() {
		log.info("TC_09_01_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_09_02_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_09_03_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);

		expectAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

		transferRecurrent.inputToDynamicInputBox(driver, info2.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_09_04_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info2.frequencyNumber);

		log.info("TC_09_05_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info2.money, "Số tiền");

		log.info("TC_09_06_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.fee);

		log.info("TC_09_07_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info2.note, "Nội dung");

		log.info("TC_09_08_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09_09_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_09_09_1_Kiem tra hinh thuc chuyen tien");
		transferRecurrent.scrollUp(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Hình thức chuyển tiền");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

		log.info("TC_09_09_2_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info2.sourceAccount);

		log.info("TC_09_09_3_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info2.destinationAccount));

		log.info("TC_09_09_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất"), info2.frequencyNumber + " " + info2.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

		log.info("TC_09_09_5_Kiem tra so tien phi");

		log.info("TC_09_9_6_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info2.note);

		log.info("TC_09_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.authenticationMethod);

		log.info("TC_09_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		transferRecurrent.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		transferTime = transferRecurrent.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT, "4");

		log.info("TC_09_12_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_09_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info2.destinationAccount);

		log.info("TC_09_12_3_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info2.note);

		log.info("TC_09_12_4_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_09_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	}

	@Test
	public void TC_10_ChuyenTien_VND_KiemTraSoDuSauGiaoDich_PhiGiaoDich_NguoiNhanTra() {
		log.info("TC_10_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);

		log.info("TC_10_02_Lay so du");
		String actualAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

		log.info("TC_10_03_Kiem tra so du khong thay doi khi chua den han");
		verifyEquals(actualAvailableBalance, expectAvailableBalance);

	}

	@Test
	public void TC_11_TrangThaiGiaoDich_ChuyenTien_VND_DinhKy_1Thang_CoPhiGiaoDichNguoiChuyenTra_XacThucBangMatKhau() {
		String startDate = getForwardDate(1);
		String endDate = getForwardMonthAndForwardDay(Long.parseLong(info2.frequencyNumber), 1);

		log.info("TC_11_01: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_11_02: Click Trang thai lenh chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_11_03: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_11_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_11_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_11_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_11_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_11_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_11_09: Kiem tra thoi gian tao dao dich");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_11_10: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_11_11: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_11_12: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(info2.money) + " VND");

		log.info("TC_11_13: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_11_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_11_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_11_16: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_11_17: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(info2.money) + " VND");

		log.info("TC_11_18: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info2.frequencyNumber + " " + info2.frequencyCategory + "/ lần");

		log.info("TC_11_19: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_11_20: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_11_21: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

	@Test
	public void TC_12_HuyGiaoDich_ChuyenTien_VND_DinhKy_1Thang_CoPhiGiaoDichNguoiChuyenTra_XacThucBangMatKhau() {
		String startDate = getForwardDate(1);
		String endDate = getForwardMonthAndForwardDay(Long.parseLong(info2.frequencyNumber), 1);

		log.info("TC_12_01: Click Huy Lenh");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_12_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_12_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_12_04: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_12_05: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_12_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_12_07: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_12_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_12_09: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_12_10: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(info2.money) + " VND");

		log.info("TC_12_11: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info2.frequencyNumber + " " + info2.frequencyCategory + "/ lần");

		log.info("TC_12_12: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_12_13: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_12_14: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Không thành công"));

		log.info("TC_12_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_12_16: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
	}

	@Test
	public void TC_13_ChuyenTien_NgoaiTe_DinhKy_2Thang_CoPhiGiaoDichNguoiNhanTra_XacThucBangMatKhauDangNhap() {
		log.info("TC_13_01_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_13_02_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_13_03_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.sourceAccount);

		expectAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

		transferRecurrent.inputToDynamicInputBox(driver, info3.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_13_04_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info3.frequencyNumber);

		log.info("TC_13_05_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info3.money, "Số tiền");

		log.info("TC_13_06_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.fee);

		log.info("TC_13_07_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info3.note, "Nội dung");

		log.info("TC_13_08_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_09_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_13_09_1_Kiem tra hinh thuc chuyen tien");
		transferRecurrent.scrollUp(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Hình thức chuyển tiền");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

		log.info("TC_13_09_2_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info3.sourceAccount);

		log.info("TC_13_09_3_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info3.destinationAccount));

		log.info("TC_13_09_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất"), info3.frequencyNumber + " " + info3.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

		log.info("TC_13_09_5_Kiem tra so tien phi");

		log.info("TC_13_09_6_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info3.note);

		log.info("TC_13_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.authenticationMethod);

		log.info("TC_13_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		transferRecurrent.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		transferTime = transferRecurrent.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT, "4");

		log.info("TC_13_12_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), "NGO TRI NAM");

		log.info("TC_13_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info3.destinationAccount);

		log.info("TC_13_12_3_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info3.note);

		log.info("TC_13_12_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_13_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	}

	@Test
	public void TC_14_ChuyenTien_EUR_KiemTraSoDuSauGiaoDich_PhiGiaoDich_NguoiNhanTra() {
		log.info("TC_14_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.sourceAccount);

		log.info("TC_14_02_Lay so du");
		String actualAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

		log.info("TC_14_03_Kiem tra so du khong thay doi khi chua den han");
		verifyEquals(actualAvailableBalance, expectAvailableBalance);

	}

	@Test
	public void TC_15_TrangThaiGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Thang_CoPhiGiaoDichNguoiNhanTra_XacThucBangMatKhauDangNhap() {
		String startDate = getForwardDate(1);
		String endDate = getForwardMonthAndForwardDay(Long.parseLong(info3.frequencyNumber), 1);

		log.info("TC_15_01: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_15_02: Click Trang thai lenh chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_15_03: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_15_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_15_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_15_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_15_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_15_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_15_9: Kiem tra thoi gian tao dao dich");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_15_10: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_15_11: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_15_12: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), info3.money + " EUR");

		log.info("TC_15_13: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_15_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_15_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_15_16: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_15_17: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), info3.money + " EUR");

		log.info("TC_15_18: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info3.frequencyNumber + " " + info3.frequencyCategory + "/ lần");

		log.info("TC_15_19: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_15_20: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_15_21: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

	@Test
	public void TC_16_HuyGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Thang_CoPhiGiaoDichNguoiNhanTra_XacThucBangMatKhauDangNhap() {
		String startDate = getForwardDate(1);
		String endDate = getForwardMonthAndForwardDay(Long.parseLong(info3.frequencyNumber), 1);

		log.info("TC_16_01: Click Huy Lenh");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_16_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_16_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_16_04: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_16_05: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_16_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_16_07: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_16_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_16_09: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_16_10: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(info3.money) + " EUR");

		log.info("TC_16_11: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info3.frequencyNumber + " " + info3.frequencyCategory + "/ lần");

		log.info("TC_16_12: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_16_13: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_16_14: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Không thành công"));

		log.info("TC_16_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_16_16: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
	}

	@Test
	public void TC_17_ChuyenTien_NgoaiTe_USD_DinhKy_2Ngay_CoPhiGiaoDichNguoiChuyenTra_XacThucBangOTP() {
		log.info("TC_17_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_17_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_17_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info4.sourceAccount);

		expectAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

		transferRecurrent.inputToDynamicInputBox(driver, info4.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_17_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info4.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info4.frequencyNumber);

		log.info("TC_17_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info4.money, "Số tiền");

		log.info("TC_17_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info4.fee);

		log.info("TC_17_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info4.note, "Nội dung");

		log.info("TC_17_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_17_9_Kiem tra man hinh xac nhan thong tin");
		transferRecurrent.scrollUp(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Hình thức chuyển tiền");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

		log.info("TC_17_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info4.sourceAccount);

		log.info("TC_17_9_2_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info4.destinationAccount));

		log.info("TC_17_9_3_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất"), info4.frequencyNumber + " " + info4.frequencyCategory + "/ lần");

		log.info("TC_17_9_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

		log.info("TC_17_9_5_Kiem tra so tien phi");

		log.info("TC_17_9_6_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info4.note);

		log.info("TC_17_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info4.authenticationMethod);

		log.info("TC_17_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		transferRecurrent.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_17_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		transferTime = transferRecurrent.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT, "4");

		log.info("TC_17_12_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_17_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info4.destinationAccount);

		log.info("TC_17_12_3_Kiem tra ten noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info4.note);

		log.info("TC_17_12_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_17_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	}

	@Test
	public void TC_18_ChuyenTien_USD_KiemTraSoDuSauGiaoDich_PhiGiaoDich_NguoiChuyenTra() {
		log.info("TC_18_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info4.sourceAccount);

		log.info("TC_18_02_Lay so du");
		String actualAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

		log.info("TC_18_03_Kiem tra so du khong thay doi khi chua den han");
		verifyEquals(actualAvailableBalance, expectAvailableBalance);

	}

	@Test
	public void TC_19_TrangThaiGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info4.frequencyNumber));

		log.info("TC_19_01: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_19_02: Click Trang thai lenh chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_19_03: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_19_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_19_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_19_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_19_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_19_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_19_9: Kiem tra thoi gian tao dao dich");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_19_10: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_19_11: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_19_12: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), info4.money + " USD");

		log.info("TC_19_13: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_19_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_19_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_19_16: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_19_17: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), info4.money + " USD");

		log.info("TC_19_18: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info4.frequencyNumber + " " + info4.frequencyCategory + "/ lần");

		log.info("TC_19_19: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_19_20: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_19_21: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

	@Test
	public void TC_20_HuyGiaoDich_ChuyenTien_NgoaiTe_USD_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info5.frequencyNumber));

		log.info("TC_20_01: Click Huy Lenh");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_20_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_20_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_20_04: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_20_05: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_20_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_20_07: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_20_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_20_09: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_20_10: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(info4.money) + " USD");

		log.info("TC_20_11: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info4.frequencyNumber + " " + info4.frequencyCategory + "/ lần");

		log.info("TC_20_12: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_20_13: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_20_14: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Không thành công"));

		log.info("TC_20_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_20_16: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
	}

	@Test
	public void TC_21_ChuyenTien_NgoaiTe_USD_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		log.info("TC_21_01_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_21_02_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_21_03_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info5.sourceAccount);

		expectAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

		transferRecurrent.inputToDynamicInputBox(driver, info5.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_21_04_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info5.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info5.frequencyNumber);

		log.info("TC_21_05_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info5.money, "Số tiền");

		log.info("TC_21_06_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info5.fee);

		log.info("TC_21_07_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info5.note, "Nội dung");

		log.info("TC_21_08_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_21_09_Kiem tra man hinh xac nhan thong tin");
		transferRecurrent.scrollUp(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Hình thức chuyển tiền");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

		log.info("TC_21_09_1_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info5.sourceAccount);

		log.info("TC_21_09_2_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info5.destinationAccount));

		log.info("TC_21_09_3_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất"), info5.frequencyNumber + " " + info5.frequencyCategory + "/ lần");

		log.info("TC_21_09_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

		log.info("TC_21_09_5_Kiem tra so tien phi");

		log.info("TC_21_09_6_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info5.note);

		log.info("TC_21_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info5.authenticationMethod);

		log.info("TC_21_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		transferRecurrent.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_21_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		transferTime = transferRecurrent.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT, "4");

		log.info("TC_21_12_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_21_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info5.destinationAccount);

		log.info("TC_21_12_3_Kiem tra ten noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info5.note);

		log.info("TC_21_12_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_21_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	}

	@Test
	public void TC_22_ChuyenTien_USD_KiemTraSoDuSauGiaoDich_PhiGiaoDich_NguoiNhanTra() {
		log.info("TC_22_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info5.sourceAccount);

		log.info("TC_22_02_Lay so du");
		String actualAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

		log.info("TC_22_03_Kiem tra so du khong thay doi khi chua den han");
		verifyEquals(actualAvailableBalance, expectAvailableBalance);

	}

	@Test
	public void TC_23_TrangThaiGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info5.frequencyNumber));

		log.info("TC_23_01: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_23_02: Click Trang thai lenh chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_23_03: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_23_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_23_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_23_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_23_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_23_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_23_9: Kiem tra thoi gian tao dao dich");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_23_10: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_23_11: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_23_12: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), info5.money + " USD");

		log.info("TC_23_13: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_23_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_23_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_23_16: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_23_17: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), info5.money + " USD");

		log.info("TC_23_18: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info5.frequencyNumber + " " + info5.frequencyCategory + "/ lần");

		log.info("TC_23_19: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_23_20: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_23_21: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

	@Test
	public void TC_24_HuyGiaoDich_ChuyenTien_NgoaiTe_USD_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info5.frequencyNumber));

		log.info("TC_24_01: Click Huy Lenh");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_24_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_24_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_24_04: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_24_05: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_24_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_24_07: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_24_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_24_09: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_24_10: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(info5.money) + " USD");

		log.info("TC_24_11: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info5.frequencyNumber + " " + info5.frequencyCategory + "/ lần");

		log.info("TC_24_12: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_24_13: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_24_14: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Không thành công"));

		log.info("TC_24_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_24_16: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
