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
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;
import vietcombank_test_data.TransferMoneyStatus_Data;

public class TransferMoneyRecurrent extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyInVcbPageObject transferRecurrent;
	private TransferMoneyStatusPageObject transferStatus;
	private HomePageObject homePage;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	private String transferTime;

	TransferInVCBRecurrent info = new TransferInVCBRecurrent("0010000000322", "0010000000318", "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "SMS OTP");
	TransferInVCBRecurrent info1 = new TransferInVCBRecurrent("0011140000647", "0010000000318", "2", "Ngày", "", "", "10", "Người nhận trả", "test", "SMS OTP");
	TransferInVCBRecurrent info2 = new TransferInVCBRecurrent("0010000000322", "0010000000318", "1", "Tháng", "", "", "500000", "Người chuyển trả", "test", "Mật khẩu đăng nhập");
	TransferInVCBRecurrent info3 = new TransferInVCBRecurrent("0011140000647", "0010000000318", "2", "Tháng", "", "", "10", "Người nhận trả", "test", "Mật khẩu đăng nhập");

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
		transferStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
	}

//	@Test
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

		transferTime = transferRecurrent.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT, "4");

		log.info("TC_01_12_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_01_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info.destinationAccount);

		log.info("TC_01_12_3_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info.note);
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_01_13_Click Thuc hien giao dich moi");
		login.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
	}

//	@Test
	public void TC_02_TrangThaiGiaoDich_ChuyenTien_VND_DinhKy_1Ngay_CoPhiGiaoDichNguoiChuyenTra_XacThucBangOTP() {
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info.frequencyNumber));

		transferStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);

		log.info("TC_02_1: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_02_2: Click Trang thai lenh chuyen tien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_02_3: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_02_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_02_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_02_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_02_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_02_9: Kiem tra thoi gian tao dao dich");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_02_10: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_02_11: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_02_12: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(info.money) + " VND");

		log.info("TC_02_13: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_02_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_02_16: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_02_17: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(info.money) + " VND");

		log.info("TC_02_18: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info.frequencyNumber + " " + info.frequencyCategory + "/ lần");

		log.info("TC_02_19: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_02_20: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_02_21: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

//	@Test
	public void TC_03_HuyGiaoDich_ChuyenTien_VND_DinhKy_1Ngay_CoPhiGiaoDichNguoiChuyenTra_XacThucBangOTP() {
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info.frequencyNumber));

		log.info("TC_03_01: Click Huy Lenh");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_03_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_03_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_03_04: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_03_05: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_03_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_03_07: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_03_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_03_09: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_03_10: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(info.money) + " VND");

		log.info("TC_03_11: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info.frequencyNumber + " " + info.frequencyCategory + "/ lần");

		log.info("TC_03_12: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_03_13: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_03_14: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Không thành công"));

		log.info("TC_03_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_03_16: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
	}

//	@Test
	public void TC_04_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		log.info("TC_04_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_04_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_04_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		transferRecurrent.inputToDynamicInputBox(driver, info1.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_04_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info1.frequencyNumber);

		log.info("TC_04_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info1.money, "Số tiền");

		log.info("TC_04_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.fee);

		log.info("TC_04_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info1.note, "Nội dung");

		log.info("TC_04_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_9_Kiem tra man hinh xac nhan thong tin");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

		log.info("TC_04_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info1.sourceAccount);

		log.info("TC_04_9_2_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info1.destinationAccount));

		log.info("TC_04_9_3_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất"), info1.frequencyNumber + " " + info1.frequencyCategory + "/ lần");

		log.info("TC_04_9_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

		log.info("TC_04_9_5_Kiem tra so tien phi");
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2(driver, "Số tiền phí"), info1.fee);

		log.info("TC_04_9_6_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info1.note);

		log.info("TC_04_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.authenticationMethod);

		log.info("TC_04_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		transferRecurrent.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		transferTime = transferRecurrent.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT, "4");

		log.info("TC_04_12_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_04_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info1.destinationAccount);

		log.info("TC_04_12_3_Kiem tra ten noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info1.note);

		log.info("TC_04_12_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_04_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	}

//	@Test
	public void TC_05_TrangThaiGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info1.frequencyNumber));

		log.info("TC_05_01: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_05_02: Click Trang thai lenh chuyen tien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_05_03: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_05_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_05_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_05_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_05_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_05_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_05_9: Kiem tra thoi gian tao dao dich");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_05_10: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_05_11: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_05_12: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), info1.money + " EUR");

		log.info("TC_05_13: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_05_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_05_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_05_16: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_05_17: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), info1.money + " EUR");

		log.info("TC_05_18: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info1.frequencyNumber + " " + info1.frequencyCategory + "/ lần");

		log.info("TC_05_19: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_05_20: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_05_21: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

//	@Test
	public void TC_06_HuyGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info1.frequencyNumber));

		log.info("TC_06_01: Click Huy Lenh");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_06_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_06_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_06_04: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_06_05: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_06_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_06_07: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_06_09: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_06_10: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(info1.money) + " EUR");

		log.info("TC_06_11: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info1.frequencyNumber + " " + info1.frequencyCategory + "/ lần");

		log.info("TC_06_12: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_06_13: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_06_14: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Không thành công"));

		log.info("TC_06_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_06_16: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
	}

	@Test
	public void TC_07_ChuyenTien_VND_DinhKy_1Thang_CoPhiGiaoDichNguoiChuyenTra_XacThucBangMatKhau() {
		log.info("TC_07_01_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_07_02_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_07_03_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
		transferRecurrent.inputToDynamicInputBox(driver, info2.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_07_04_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info2.frequencyNumber);

		log.info("TC_07_05_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info2.money, "Số tiền");

		log.info("TC_07_06_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.fee);

		log.info("TC_07_07_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info2.note, "Nội dung");

		log.info("TC_07_08_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_09_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_07_09_1_Kiem tra hinh thuc chuyen tien");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

		log.info("TC_07_09_2_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info2.sourceAccount);

		log.info("TC_07_09_3_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info2.destinationAccount));

		log.info("TC_07_09_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất"), info2.frequencyNumber + " " + info2.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

		log.info("TC_07_09_5_Kiem tra so tien phi");
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2(driver, "Số tiền phí"), info2.fee);

		log.info("TC_07_9_6_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info2.note);

		log.info("TC_07_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.authenticationMethod);

		log.info("TC_07_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		transferRecurrent.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		transferRecurrent.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);

		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		transferTime = transferRecurrent.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT, "4");

		log.info("TC_07_12_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_07_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info2.destinationAccount);

		log.info("TC_07_12_3_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info2.note);

		log.info("TC_07_12_4_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_07_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	}

	@Test
	public void TC_08_TrangThaiGiaoDich_ChuyenTien_VND_DinhKy_1Thang_CoPhiGiaoDichNguoiChuyenTra_XacThucBangMatKhau() {
		String startDate = getForwardDate(1);
		String endDate = getForwardMonthAndForwardDay(Long.parseLong(info2.frequencyNumber), 1);

		log.info("TC_08_01: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_08_02: Click Trang thai lenh chuyen tien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_08_03: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_08_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_08_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_08_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_08_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_08_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_08_09: Kiem tra thoi gian tao dao dich");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_08_10: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_08_11: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_08_12: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(info2.money) + " VND");

		log.info("TC_08_13: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_08_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_08_16: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_08_17: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(info2.money) + " VND");

		log.info("TC_08_18: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info2.frequencyNumber + " " + info2.frequencyCategory + "/ lần");

		log.info("TC_08_19: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_08_20: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_08_21: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

	@Test
	public void TC_09_HuyGiaoDich_ChuyenTien_VND_DinhKy_1Thang_CoPhiGiaoDichNguoiChuyenTra_XacThucBangMatKhau() {
		String startDate = getForwardDate(1);
		String endDate = getForwardMonthAndForwardDay(Long.parseLong(info2.frequencyNumber), 1);

		log.info("TC_09_01: Click Huy Lenh");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_09_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_09_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_09_04: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_09_05: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_09_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_09_07: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_09_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_09_09: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_09_10: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(info2.money) + " VND");

		log.info("TC_09_11: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info2.frequencyNumber + " " + info2.frequencyCategory + "/ lần");

		log.info("TC_09_12: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_09_13: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_09_14: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Không thành công"));

		log.info("TC_09_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_09_16: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
	}

	@Test
	public void TC_10_ChuyenTien_NgoaiTe_DinhKy_2Thang_CoPhiGiaoDichNguoiNhanTra_XacThucBangMatKhauDangNhap() {
		log.info("TC_10_01_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_10_02_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_10_03_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.sourceAccount);
		transferRecurrent.inputToDynamicInputBox(driver, info3.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_10_04_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info3.frequencyNumber);

		log.info("TC_10_05_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info3.money, "Số tiền");

		log.info("TC_10_06_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.fee);

		log.info("TC_10_07_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info3.note, "Nội dung");

		log.info("TC_10_08_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_10_09_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_10_09_1_Kiem tra hinh thuc chuyen tien");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

		log.info("TC_10_09_2_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info3.sourceAccount);

		log.info("TC_10_09_3_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info3.destinationAccount));

		log.info("TC_10_09_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất"), info3.frequencyNumber + " " + info3.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

		log.info("TC_10_09_5_Kiem tra so tien phi");
		verifyEquals(transferRecurrent.getDynamicTextInTextViewLine2(driver, "Số tiền phí"), info3.fee);

		log.info("TC_10_09_6_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info3.note);

		log.info("TC_10_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.authenticationMethod);

		log.info("TC_10_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		transferRecurrent.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_10_12_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		transferTime = transferRecurrent.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT, "4");

		log.info("TC_10_12_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), "NGO TRI NAM");

		log.info("TC_10_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info3.destinationAccount);

		log.info("TC_10_12_3_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info3.note);

		log.info("TC_10_12_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_10_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	}

	@Test
	public void TC_11_TrangThaiGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Thang_CoPhiGiaoDichNguoiNhanTra_XacThucBangMatKhauDangNhap() {
		String startDate = getForwardDate(1);
		String endDate = getForwardMonthAndForwardDay(Long.parseLong(info3.frequencyNumber), 1);

		log.info("TC_11_01: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_11_02: Click Trang thai lenh chuyen tien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_11_03: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_11_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_011_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_11_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_11_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_11_08: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_11_9: Kiem tra thoi gian tao dao dich");
		verifyTrue(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_11_10: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_11_11: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_11_12: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), info3.money + " EUR");

		log.info("TC_11_13: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_11_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_11_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_11_16: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_11_17: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), info3.money + " EUR");

		log.info("TC_11_18: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info3.frequencyNumber + " " + info3.frequencyCategory + "/ lần");

		log.info("TC_11_19: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_11_20: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Ngày kết thúc"), endDate);

		log.info("TC_11_21: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

	@Test
	public void TC_12_HuyGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Thang_CoPhiGiaoDichNguoiNhanTra_XacThucBangMatKhauDangNhap() {
		String startDate = getForwardDate(1);
		String endDate = getForwardMonthAndForwardDay(Long.parseLong(info3.frequencyNumber), 1);

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
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_12_10: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(info3.money) + " EUR");

		log.info("TC_12_11: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, "Tần suất"), info3.frequencyNumber + " " + info3.frequencyCategory + "/ lần");

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

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
