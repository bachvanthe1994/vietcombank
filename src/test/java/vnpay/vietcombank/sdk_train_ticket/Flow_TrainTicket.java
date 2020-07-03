package vnpay.vietcombank.sdk_train_ticket;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.SourceAccountModel;
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.sdk.trainTicket.TrainTicketPageObject;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vietcombank_test_data.TransferMoneyQuick_Data;
import vnpay.vietcombank.sdk.vehicleTicket.data.VehicalData;
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data;
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data.message;
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data.textDefault;

public class Flow_TrainTicket extends Base {
	AppiumDriver<MobileElement> driver;
	private TransactionReportPageObject transReport;
	private LogInPageObject login;
	private TrainTicketPageObject trainTicket;
	private SettingVCBSmartOTPPageObject smartOTP;

	List<String> listExpect;
	List<String> listActual;
	String phoneNumber = "";
	String hoTen = "";
	String SDT = "";
	String email = "";
	String hoChieu = "";
	String tongTienThanhToan = "";
	String soTienPhi = "";
	String account;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String passSmartOTP = "111222";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
//		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
//		smartOTP.setupSmartOTP(passSmartOTP, getDataInCell(6));
		phoneNumber = phone;
		trainTicket = PageFactoryManager.getTrainTicketPageObject(driver);

	}
	
	@Parameters({ "pass" })
	@Test
	public void TC_01_DatVe_MotChieu_SoLuongNguoiNhoNhat_XacThucBang_MatKhauDangNhap(String pass) {
		log.info("TC_01_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.TRAIN_BOOKING);

		log.info("TC_01_Step_Click close message");
		trainTicket.clickToDynamicButton(textDefault.AGREE);

		log.info("TC_01_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd(textDefault.TRAIN_BOOKING_CAPITALIZE, "com.VCB:id/tvTextPickUp");

		log.info("TC_01_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/edtTextPickUp");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/edtTextArrival");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_01_Chon Mot chieu");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.ONE_WAY);

		log.info("TC_01_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tv_ngay_di");

		String startDay = getForWardDay(5);

		log.info("TC_01_Chon ngay di la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(5), startDay);

		log.info("TC_01_Click button tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.CONFIRM);

		log.info("TC_01_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.PASSENGER);

		log.info("TC_01_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber(textDefault.ADULTS, "com.VCB:id/ivIncrase");

		log.info("TC_01_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.DONE);

		log.info("TC_01_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.TYPE_SEAT);

		log.info("TC_01_Click radio chon tat ca");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.CHOOSE_ALL);

		log.info("TC_01_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.DONE);

		log.info("TC_01_Click button tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_01_verify chuyen sang man chon cho chieu di");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.LIST_TO);

		log.info("TC_01_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_01_Click chon gio khoi hanh");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivBgItem");

		log.info("TC_01_Click TIEP TUC");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_01_Click chon toa");
		trainTicket.clickDynamicSelectLocation("0", "com.VCB:id/tvWagon");

		log.info("TC_01_get lay mau o cho trong");
		String colorOfSeat = trainTicket.getColorOfElement(TrainTicketPageUIs.IMAGE_BY_TEXT, textDefault.BLANK);

		log.info("TC_01_Click chon cho trong");
		trainTicket.scrollIDownOneTime(driver);
		listActual = trainTicket.chooseSeats(1, colorOfSeat);

		log.info("TC_01Verify so ghe da chon");
		listExpect = Arrays.asList(trainTicket.getTextInDynamicPopup("com.VCB:id/tvSeat"));
		verifyEquals(listActual, listExpect);

		log.info("TC_01_Click tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_01_Verify man hinh");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.BOOKING);

		log.info("TC_01_Get so tien chieu di");
		String amountStart = trainTicket.getTextInDynamicPopup("com.VCB:id/tvAmount");
		long amountStartConvert = convertAvailableBalanceCurrentcyOrFeeToLong(amountStart);

		String amountTotal = addCommasToLong(amountStartConvert + "") + " VND";

		log.info("TC_01_Verify tong so tien thanh toan");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTotalAmount"), amountTotal);

		log.info("TC_01_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.CONTINUE);

		log.info("TC_01_Verify man hinh thong tin hanh khach");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.INFOMATION_PASSENGER);

		log.info("TC_01_Nhap ho ten khách hang");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CUSTOMER_NAME, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvHoTen");

		log.info("TC_01_Nhap so CMT");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CARD_NO, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvCMND");

		log.info("TC_01_Nhap so CMT");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.CARD_NO,textDefault.CONTACT_INFO, "com.VCB:id/tvCMND");

		log.info("TC_01_Nhap email");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.email,textDefault.CONTACT_INFO, "com.VCB:id/tvEmail");

		log.info("TC_01_Click radio khong xuat hoa don");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivNoXuatHoaDon");

		log.info("TC_01_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.CONTINUE);

		log.info("TC_01_click button dong y dong popup");
		trainTicket.clickToDynamicButtonContains("Đ");

		log.info("TC_01_Verify man hinh thong tin dat ve");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.INFOMATION_BOOKING);

		log.info("TC_01_Get tong tien chieu di");
		trainTicket.getTextTotal("0", "com.VCB:id/tvTotalAmount");

		log.info("TC_01_click button Thanh toan");
		trainTicket.clickToDynamicButtonContains(textDefault.PAY);

		log.info("TC_01_click button Khong");
		trainTicket.clickToDynamicButton(textDefault.NO);

		log.info("TC_01_verify hien thi Thong tin ve Tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(textDefault.INFOMATION_TRAIN_TICKET));

		trainTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = trainTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		
		log.info("TC_01_veriFy Email");
		email = trainTicket.getDynamicTextOld(driver, "Email");
		verifyEquals(TrainTicket_Data.inputText.email, email);

		log.info("TC_01_veriFy Thong tin ho chieu");
		hoChieu = trainTicket.getDynamicTextOld(driver, textDefault.IDENTITY_CARD);

		verifyEquals(TrainTicket_Data.inputText.CARD_NO, hoChieu);
		trainTicket.scrollDownToText(driver, textDefault.TOTAL_MONEY_PAY);

		log.info("TC_01_Verify tong tien thanh toan-------");
		tongTienThanhToan = trainTicket.getDynamicTextOld(driver, textDefault.TOTAL_MONEY_PAY);

		log.info("TC_01_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_01_Verify hien thi man hinh xac nhan thong tin");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(textDefault.CONFIRM_INFORMATION));
		log.info("TC_01_Verify tai khoan nguon");
		verifyEquals(account, trainTicket.getDynamicTextOld(textDefault.SOURCE_ACCOUNT));

		log.info("TC_01_Verify rong so tien thanh toan");
		verifyTrue(trainTicket.getDynamicTextOld(textDefault.TITLE_AMOUNT_MONEY).contains(tongTienThanhToan));

		log.info("TC_01_Step_06: Chon phuong thuc xac thuc Mat khau dang nhap");
		trainTicket.clickToTextID(driver, "com.VCB:id/tvptxt");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_01_Step_07: An nut 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_01_Step_08: Nhap du ki tu vao o nhap OTP");
		trainTicket.inputToDynamicPopupPasswordInput(driver, pass, textDefault.NEXT);

		log.info("TC_01_Step_09: An tiep button 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_01_Step_09: An tiep button Hien thi thong bao thanh toan thanh cong");
		trainTicket.clickToDynamicButtonContains(textDefault.NEW_TRANSFER);

		log.info("TC_01_Step_09: Click thong quay lai man hinh home");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivClose");

	}

	@Test
	public void TC_02_BaoCao_DatVe_MotChieu_SoLuongNguoiNhoNhat_XacThucBang_MatKhauDangNhap() {
		log.info("TC_02: Click menu header");
		trainTicket.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_02:_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRAIN_TICKET);

		log.info("TC_02: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_02:: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02:: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02:: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02:: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		verifyEquals(transferTimeInReport, transferTimeInReport1);
		log.info("TC_02:: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_02:So tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(tongTienThanhToan));

		log.info("TC_02:: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.TRAIN_TICKET);

		log.info("TC_02:: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOI_DUNG));

		log.info("TC_02: Click Back ve Home");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({ "otp" })
	@Test(invocationCount=2) 
	public void TC_03_DatVe_MotChieu_SoLuongNguoiNhoNhat_XacThucBang_SMSOTP(String otp) {
		log.info("TC_03_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.TRAIN_BOOKING);

		log.info("TC_03_Step_Click close message");
		trainTicket.clickToDynamicButton(textDefault.AGREE);

		log.info("TC_03_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(textDefault.TRAIN_BOOKING_CAPITALIZE));

		log.info("TC_03_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd(textDefault.TRAIN_BOOKING_CAPITALIZE, "com.VCB:id/tvTextPickUp");

		log.info("TC_03_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/edtTextPickUp");

		log.info("TC_03_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_03_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/edtTextArrival");

		log.info("TC_03_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_03_Chon Mot chieu");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.ONE_WAY);

		log.info("TC_03_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tv_ngay_di");

		String startDay = getForWardDay(5);

		log.info("TC_03_Chon ngay di la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(5), startDay);

		log.info("TC_03_Click button tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.CONFIRM);

		log.info("TC_03_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.PASSENGER);

		log.info("TC_03_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber(textDefault.ADULTS, "com.VCB:id/ivIncrase");

		log.info("TC_03_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.DONE);

		log.info("TC_03_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.TYPE_SEAT);

		log.info("TC_03_Click radio chon tat ca");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.CHOOSE_ALL);

		log.info("TC_03_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.DONE);

		log.info("TC_03_Click button tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_03_verify chuyen sang man chon cho chieu di");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.LIST_TO);

		log.info("TC_03_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_03_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_03_Click chon gio khoi hanh");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivBgItem");

		log.info("TC_03_Click TIEP TUC");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_03_Click chon toa");
		trainTicket.clickDynamicSelectLocation("0", "com.VCB:id/tvWagon");

		log.info("TC_03_get lay mau o cho trong");
		String colorOfSeat = trainTicket.getColorOfElement(TrainTicketPageUIs.IMAGE_BY_TEXT, textDefault.BLANK);

		log.info("TC_03_Click chon cho trong");
		trainTicket.scrollIDownOneTime(driver);
		listActual = trainTicket.chooseSeats(1, colorOfSeat);

		log.info("TC_03Verify so ghe da chon");
		listExpect = Arrays.asList(trainTicket.getTextInDynamicPopup("com.VCB:id/tvSeat"));
		verifyEquals(listActual, listExpect);

		log.info("TC_03_Click tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_03_Verify man hinh");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.BOOKING);

		log.info("TC_03_Get so tien chieu di");
		String amountStart = trainTicket.getTextInDynamicPopup("com.VCB:id/tvAmount");
		long amountStartConvert = convertAvailableBalanceCurrentcyOrFeeToLong(amountStart);

		String amountTotal = addCommasToLong(amountStartConvert + "") + " VND";

		log.info("TC_03_Verify tong so tien thanh toan");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTotalAmount"), amountTotal);

		log.info("TC_03_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.CONTINUE);

		log.info("TC_03_Verify man hinh thong tin hanh khach");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.INFOMATION_PASSENGER);

		log.info("TC_03_Nhap ho ten khách hang");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CUSTOMER_NAME, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvHoTen");

		log.info("TC_03_Nhap so CMT");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CARD_NO, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvCMND");

		log.info("TC_03_Nhap so CMT");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.CARD_NO,textDefault.CONTACT_INFO, "com.VCB:id/tvCMND");
		
		log.info("TC_03_Nhap email");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.email,textDefault.CONTACT_INFO, "com.VCB:id/tvEmail");

		log.info("TC_03_Click radio khong xuat hoa don");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivNoXuatHoaDon");

		log.info("TC_03_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.CONTINUE);

		log.info("TC_03_click button dong y dong popup");
		trainTicket.clickToDynamicButtonContains("Đ");

		log.info("TC_03_Get tong tien chieu di");
		trainTicket.getTextTotal("0", "com.VCB:id/tvTotalAmount");

		log.info("TC_03_click button Thanh toan");
		trainTicket.clickToDynamicButtonContains(textDefault.PAY);

		log.info("TC_03_click button Khong");
		trainTicket.clickToDynamicButton(textDefault.NO);

		log.info("TC_03_verify hien thi Thong tin ve Tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(textDefault.INFOMATION_TRAIN_TICKET));
		
		trainTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = trainTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		log.info("TC_03_veriFy Email");
		email = trainTicket.getDynamicTextOld(driver, "Email");
		verifyEquals(TrainTicket_Data.inputText.email, email);

		log.info("TC_03_veriFy Thong tin ho chieu");
		hoChieu = trainTicket.getDynamicTextOld(driver, textDefault.IDENTITY_CARD);

		verifyEquals(TrainTicket_Data.inputText.CARD_NO, hoChieu);
		trainTicket.scrollDownToText(driver, textDefault.TOTAL_MONEY_PAY);

		log.info("TC_03_Verify tong tien thanh toan-------");
		tongTienThanhToan = trainTicket.getDynamicTextOld(driver, textDefault.TOTAL_MONEY_PAY);

		log.info("TC_03_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_03_Verify hien thi man hinh xac nhan thong tin");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(textDefault.CONFIRM_INFORMATION));
		
		log.info("TC_03_Verify tai khoan nguon");
		verifyEquals(account, trainTicket.getDynamicTextOld(textDefault.SOURCE_ACCOUNT));

		log.info("TC_03_Verify rong so tien thanh toan");
		verifyTrue(trainTicket.getDynamicTextOld(textDefault.TITLE_AMOUNT_MONEY).contains(tongTienThanhToan));

		log.info("TC_03_Step_06: Chon phuong thuc xac thuc SMS OTP");
		trainTicket.clickToTextID(driver, "com.VCB:id/tvptxt");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, textDefault.TITLE_OTP);

		log.info("TC_03_Step_07: An nut 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_03_Step_08: Nhap du ki tu vao o nhap OTP");
		trainTicket.inputToDynamicOtp(driver, otp, textDefault.NEXT);

		log.info("TC_03_Step_09: An tiep button 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_03_Step_09: An tiep button Hien thi thong bao thanh toan thanh cong");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(message.MESSAGE_SUCCESS));

		log.info("TC_03_Step_09: Click thong quay lai man hinh home");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivHome");

	}

	@Test
	public void TC_04_BaoCao_DatVe_MotChieu_SoLuongNguoiNhoNhat_XacThucBang_SMSOTP() {
		log.info("TC_04: Click menu header");
		trainTicket.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_04:_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRAIN_TICKET);

		log.info("TC_04: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_04:: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_04:: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04:: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04:: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		verifyEquals(transferTimeInReport, transferTimeInReport1);
		log.info("TC_04:: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_04:: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.TRAIN_TICKET);

		log.info("TC_04:: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOI_DUNG));

		log.info("TC_02: Click Back ve Home");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
//	@Test
	public void TC_05_DatVe_KhuHoi_SoLuongNguoiNhoNhat_XacThucBang_SMSOTP(String otp) {
		log.info("TC_05_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.TRAIN_BOOKING);
		
		log.info("TC_01_Step_Click close message");
		trainTicket.clickToDynamicButton(textDefault.AGREE);
		
		log.info("TC_01_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd(textDefault.TRAIN_BOOKING_CAPITALIZE, "com.VCB:id/tvTextPickUp");

		log.info("TC_01_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/edtTextPickUp");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/edtTextArrival");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);
		
		log.info("TC_05_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.PASSENGER);

		log.info("TC_05_Click them hanh khach");
		trainTicket.clickDynamicButtonNumber(textDefault.ADULTS, "com.VCB:id/ivIncrase");

		log.info("TC_05_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.DONE);
		
		log.info("TC_01_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.TYPE_SEAT);

		log.info("TC_01_Click radio chon tat ca");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.CHOOSE_ALL);

		log.info("TC_01_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.DONE);

		log.info("TC_05_Click button ap dung");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_05_Click chon mot chuyen di");
		trainTicket.clickDynamicSelectTrain("0", "com.VCB:id/tv_ten_tau");

		log.info("TC_05_get ten tau di");
		String codeTrainStart = trainTicket.getDynamicTextViewIndex("0", "com.VCB:id/tv_ten_tau");

		log.info("TC_05_Click button tiep tuc chon chieu ve");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE_TO_CHOOSE);

		log.info("TC_05_Click chon mot chuyen di");
		trainTicket.clickDynamicSelectTrain("1", "com.VCB:id/tv_ten_tau");
		String codeTrainEnd = trainTicket.getDynamicTextViewIndex("0", "com.VCB:id/tv_ten_tau");

		log.info("TC_05_Click button tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_05_verify chuyen sang man chon cho chieu di");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.CHOISE_FROM);

		log.info("TC_05_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_05_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_05_verify tên tàu");
		verifyEquals(trainTicket.getDynamicTextViewIndex("0", "com.VCB:id/tv_ten_tau"), codeTrainStart);

		log.info("TC_05_Click chon toa");
		trainTicket.clickDynamicSelectLocation("0", "com.VCB:id/tvWagon");

		log.info("TC_05_get lay mau o cho trong");
		String colorOfSeat = trainTicket.getColorOfElement(TrainTicketPageUIs.IMAGE_BY_TEXT, textDefault.BLANK, textDefault.EXTRA_SEATS);

		log.info("TC_05_Click chon cho trong");
		trainTicket.scrollIDownOneTime(driver);
		listActual = trainTicket.chooseSeats(1, colorOfSeat);

		log.info("TC_05Verify so ghe da chon");
		listExpect = Arrays.asList(trainTicket.getTextInDynamicPopup("com.VCB:id/tvSeat"));
		verifyEquals(listActual, listExpect);

		log.info("TC_05_Click tiep tuc chon chieu ve");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE_TO_CHOOSE);

		log.info("TC_05_verify chuyen sang man chon cho chieu ve");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.CHOOSE_SEAT_TO);

		log.info("TC_05_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from_return"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_05_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to_return"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_05_verify tên tàu");
		verifyEquals(trainTicket.getDynamicTextViewIndex("0", "com.VCB:id/tv_ten_tau_return"), codeTrainEnd);

		log.info("TC_05_Click chon toa");
		trainTicket.clickDynamicSelectLocation("0", "com.VCB:id/tvWagon");

		log.info("TC_05_get lay mau o cho trong");
		String colorOfSeatEnd = trainTicket.getColorOfElement(TrainTicketPageUIs.IMAGE_BY_TEXT, textDefault.BLANK, textDefault.EXTRA_SEATS);

		log.info("TC_05_Click chon cho trong");
		trainTicket.scrollIDownOneTime(driver);
		listActual = trainTicket.chooseSeats(1, colorOfSeatEnd);

		log.info("TC_05_Verify so ghe da chon");
		listExpect = Arrays.asList(trainTicket.getTextInDynamicPopup("com.VCB:id/tvSeat"));
		verifyEquals(listActual, listExpect);

		log.info("TC_05_Click tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_05_Get so tien chieu di");
		String amountStart = trainTicket.getTextInDynamicPopup("com.VCB:id/tvAmount");
		long amountStartConvert = convertAvailableBalanceCurrentcyOrFeeToLong(amountStart);

		log.info("TC_05_Get so tien chieu ve");
		String amountEnd = trainTicket.getTextInDynamicPopup("com.VCB:id/tvAmountReturn");
		long amountEndConvert = convertAvailableBalanceCurrentcyOrFeeToLong(amountEnd);
		String amountTotal = addCommasToLong(amountEndConvert + amountStartConvert + "") + " VND";

		log.info("TC_05_Verify tong so tien thanh toan");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTotalAmount"), amountTotal);

		log.info("TC_05_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.CONTINUE);

		log.info("TC_05_Verify man hinh thong tin hanh khach");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.INFOMATION_PASSENGER);

		log.info("TC_05_Nhap ho ten khách hang");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CUSTOMER_NAME, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvHoTen");

		log.info("TC_05_Nhap so CMT");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CARD_NO, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvCMND");

		log.info("TC_05_Nhap so CMT");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.CARD_NO, textDefault.CONTACT_INFO, "com.VCB:id/tvCMND");

		log.info("TC_05_Nhap email");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.email, textDefault.CONTACT_INFO, "com.VCB:id/tvEmail");

		log.info("TC_05_Click radio khong xuat hoa don");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivNoXuatHoaDon");

		log.info("TC_05_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.CONTINUE);

		log.info("TC_05_click button dong y dong popup");
		trainTicket.clickToDynamicButtonContains("Đ");

		log.info("TC_05_Get tong tien chieu di");
		trainTicket.getTextTotal("0", "com.VCB:id/tvTotalAmount");

		log.info("TC_05_Get tong tien chieu ve");
		trainTicket.getTextTotal("1", "com.VCB:id/tvTotalAmount");

		log.info("TC_05_click button Thanh toan");
		trainTicket.clickToDynamicButtonContains(textDefault.PAY);

		log.info("TC_05_click button Khong");
		trainTicket.clickToDynamicButton(textDefault.NO);

		log.info("TC_05_verify hien thi Thong tin ve Tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(textDefault.INFOMATION_TRAIN_TICKET));
		
		trainTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = trainTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		
		log.info("TC_05_veriFy So Dien Thoai");
		SDT = trainTicket.getDynamicTextOld(driver, textDefault.PHONE_NUMBER);
		verifyEquals(phoneNumber, SDT);

		log.info("TC_05_veriFy Email");
		email = trainTicket.getDynamicTextOld(driver, "Email");
		verifyEquals(TrainTicket_Data.inputText.email, email);

		log.info("TC_05_veriFy Thong tin ho chieu");
		hoChieu = trainTicket.getDynamicTextOld(driver, textDefault.IDENTITY_CARD);

		verifyEquals(TrainTicket_Data.inputText.CARD_NO, hoChieu);
		trainTicket.scrollDownToText(driver, textDefault.TOTAL_MONEY_PAY);

		log.info("TC_05_Verify tong tien thanh toan-------");
		tongTienThanhToan = trainTicket.getDynamicTextOld(driver, textDefault.TOTAL_MONEY_PAY);

		log.info("TC_05_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_05_Verify hien thi man hinh xac nhan thong tin");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(textDefault.CONFIRM_INFORMATION));
		log.info("TC_05_Verify tai khoan nguon");
		verifyEquals(account, trainTicket.getDynamicTextOld(textDefault.SOURCE_ACCOUNT));

		log.info("TC_05_Verify rong so tien thanh toan");
		verifyTrue(trainTicket.getDynamicTextOld("Số tiền").contains(tongTienThanhToan));

		log.info("TC_05_Step_06: Chon phuong thuc xac thuc SMS OTP");
		trainTicket.clickToTextID(driver, "com.VCB:id/tvptxt");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_05_Verify Phi giao dich");
		soTienPhi = trainTicket.getDynamicTextOld(textDefault.TRANSACTION_FEE);

		log.info("TC_05_Step_07: An nut 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_05_Step_08: Nhap du ki tu vao o nhap OTP");
		trainTicket.inputToDynamicOtp(driver, otp, textDefault.NEXT);

		log.info("TC_05_Step_09: An tiep button 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_05_Step_09: An tiep button Hien thi thong bao thanh toan thanh cong");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(message.MESSAGE_SUCCESS));

		log.info("TC_05_Step_09: Click thong quay lai man hinh home");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivHome");

	}

//	@Test
	public void TC_06_BaoCaoGiaoDich_DatVe_KhuHoi_SoLuongNguoiNhoNhat_XacThucBang_SMSOTP() {
		log.info("TC_06: Click menu header");
		trainTicket.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_06:_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRAIN_TICKET);

		log.info("TC_06:: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_06: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_06:: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_06:: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06:: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06:: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		verifyEquals(transferTimeInReport, transferTimeInReport1);
		log.info("TC_06:: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_06:So tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(tongTienThanhToan));

		log.info("TC_06: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE), soTienPhi);

		log.info("TC_06:: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.TRAIN_TICKET);

		log.info("TC_06:: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOI_DUNG));

		log.info("TC_06: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

	}

	@Test
	public void TC_07_DatVe_MotChieu_SoLuongNguoiNhoNhat_XacThucBang_SmartOTP() {
		log.info("TC_07_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.TRAIN_BOOKING);

		log.info("TC_07_Step_Click close message");
		trainTicket.clickToDynamicButton(textDefault.AGREE);

		log.info("TC_07_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(textDefault.TRAIN_BOOKING_CAPITALIZE));

		log.info("TC_07_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd(textDefault.TRAIN_BOOKING_CAPITALIZE, "com.VCB:id/tvTextPickUp");

		log.info("TC_07_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/edtTextPickUp");

		log.info("TC_07_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_07_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/edtTextArrival");

		log.info("TC_07_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_07_Chon Mot chieu");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.ONE_WAY);

		log.info("TC_07_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tv_ngay_di");

		String startDay = getForWardDay(5);

		log.info("TC_07_Chon ngay di la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(5), startDay);

		log.info("TC_07_Click button tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.CONFIRM);

		log.info("TC_07_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.PASSENGER);

		log.info("TC_07_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber(textDefault.ADULTS, "com.VCB:id/ivIncrase");

		log.info("TC_07_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.DONE);

		log.info("TC_07_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.TYPE_SEAT);

		log.info("TC_07_Click radio chon tat ca");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.CHOOSE_ALL);

		log.info("TC_07_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.DONE);

		log.info("TC_07_Click button tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_07_verify chuyen sang man chon cho chieu di");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.LIST_TO);

		log.info("TC_07_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_07_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_07_Click chon gio khoi hanh");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivBgItem");

		log.info("TC_07_Click TIEP TUC");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_07_Click chon toa");
		trainTicket.clickDynamicSelectLocation("0", "com.VCB:id/tvWagon");

		log.info("TC_07_get lay mau o cho trong");
		String colorOfSeat = trainTicket.getColorOfElement(TrainTicketPageUIs.IMAGE_BY_TEXT, textDefault.BLANK);

		log.info("TC_07_Click chon cho trong");
		trainTicket.scrollIDownOneTime(driver);
		listActual = trainTicket.chooseSeats(1, colorOfSeat);

		log.info("TC_07Verify so ghe da chon");
		listExpect = Arrays.asList(trainTicket.getTextInDynamicPopup("com.VCB:id/tvSeat"));
		verifyEquals(listActual, listExpect);

		log.info("TC_07_Click tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_07_Verify man hinh");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.BOOKING);

		log.info("TC_07_Get so tien chieu di");
		String amountStart = trainTicket.getTextInDynamicPopup("com.VCB:id/tvAmount");
		long amountStartConvert = convertAvailableBalanceCurrentcyOrFeeToLong(amountStart);

		String amountTotal = addCommasToLong(amountStartConvert + "") + " VND";

		log.info("TC_07_Verify tong so tien thanh toan");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTotalAmount"), amountTotal);

		log.info("TC_07_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.CONTINUE);

		log.info("TC_07_Verify man hinh thong tin hanh khach");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.INFOMATION_PASSENGER);

		log.info("TC_07_Nhap ho ten khách hang");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CUSTOMER_NAME, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvHoTen");

		log.info("TC_07_Nhap so CMT");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CARD_NO, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvCMND");

		log.info("TC_07_Nhap so CMT");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.CARD_NO,textDefault.CONTACT_INFO, "com.VCB:id/tvCMND");

		log.info("TC_07_Nhap email");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.email,textDefault.CONTACT_INFO, "com.VCB:id/tvEmail");

		log.info("TC_07_Click radio khong xuat hoa don");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivNoXuatHoaDon");

		log.info("TC_07_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.CONTINUE);

		log.info("TC_07_click button dong y dong popup");
		trainTicket.clickToDynamicButtonContains("Đ");

		log.info("TC_07_Verify man hinh thong tin dat ve");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.INFOMATION_BOOKING);

		log.info("TC_07_Get tong tien chieu di");
		trainTicket.getTextTotal("0", "com.VCB:id/tvTotalAmount");

		log.info("TC_07_click button Thanh toan");
		trainTicket.clickToDynamicButtonContains(textDefault.PAY);

		log.info("TC_07_click button Khong");
		trainTicket.clickToDynamicButton(textDefault.NO);

		log.info("TC_07_verify hien thi Thong tin ve Tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(textDefault.INFOMATION_TRAIN_TICKET));
		
		trainTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = trainTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		log.info("TC_07_veriFy Email");
		email = trainTicket.getDynamicTextOld(driver, "Email");
		verifyEquals(TrainTicket_Data.inputText.email, email);

		log.info("TC_07_veriFy Thong tin ho chieu");
		hoChieu = trainTicket.getDynamicTextOld(driver, textDefault.IDENTITY_CARD);

		verifyEquals(TrainTicket_Data.inputText.CARD_NO, hoChieu);
		trainTicket.scrollDownToText(driver, textDefault.TOTAL_MONEY_PAY);

		log.info("TC_07_Verify tong tien thanh toan-------");
		tongTienThanhToan = trainTicket.getDynamicTextOld(driver, textDefault.TOTAL_MONEY_PAY);

		log.info("TC_07_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_07_Verify hien thi man hinh xac nhan thong tin");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(textDefault.CONFIRM_INFORMATION));
		
		log.info("TC_07_Verify tai khoan nguon");
		verifyEquals(account, trainTicket.getDynamicTextOld(textDefault.SOURCE_ACCOUNT));

		log.info("TC_07_Verify rong so tien thanh toan");
		verifyTrue(trainTicket.getDynamicTextOld(textDefault.TITLE_AMOUNT_MONEY).contains(tongTienThanhToan));

		log.info("TC_07_Step_06: Chon phuong thuc xac thuc Smart OTP");
		trainTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		trainTicket.clickToDynamicButtonLinkOrLinkText(VehicalData.DATA_ORDER_TICKET.SMART_OTP);

		log.info("TC_07_Step_07: An nut 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_07_Step_08: Nhap du ki tu vao o nhap OTP");
		trainTicket.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");
		trainTicket.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");

		log.info("TC_07_Step_09: An tiep button 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_07_Step_09: An tiep button Hien thi thong bao thanh toan thanh cong");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(message.MESSAGE_SUCCESS));

		log.info("TC_07_Step_09: Click thong quay lai man hinh home");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivHome");

	}

	@Test
	public void TC_08_BaoCao_DatVe_MotChieu_SoLuongNguoiNhoNhat_XacThucBang_SmartOTP() {
		log.info("TC_04: Click menu header");
		trainTicket.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_04:_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRAIN_TICKET);

		log.info("TC_04: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_04:: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_04:: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04:: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04:: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		verifyEquals(transferTimeInReport, transferTimeInReport1);
		log.info("TC_04:: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_04:: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.TRAIN_TICKET);

		log.info("TC_04:: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOI_DUNG));

		log.info("TC_04: Click Back ve Home");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);
	}

	@Test
	public void TC_09_DatVe_KhuHoi_SoLuongNguoiNhoNhat_XacThucBang_SmartOTP() {
		log.info("TC_09_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.TRAIN_BOOKING);
		
		log.info("TC_01_Step_Click close message");
		trainTicket.clickToDynamicButton(textDefault.AGREE);
		
		log.info("TC_01_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd(textDefault.TRAIN_BOOKING_CAPITALIZE, "com.VCB:id/tvTextPickUp");

		log.info("TC_01_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/edtTextPickUp");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/edtTextArrival");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);
		
		log.info("TC_09_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.PASSENGER);

		log.info("TC_09_Click them hanh khach");
		trainTicket.clickDynamicButtonNumber(textDefault.ADULTS, "com.VCB:id/ivIncrase");

		log.info("TC_09_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.DONE);
		
		log.info("TC_01_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.TYPE_SEAT);

		log.info("TC_01_Click radio chon tat ca");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.CHOOSE_ALL);

		log.info("TC_01_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.DONE);

		log.info("TC_09_Click button ap dung");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_09_Click chon mot chuyen di");
		trainTicket.clickDynamicSelectTrain("0", "com.VCB:id/tv_ten_tau");

		log.info("TC_09_get ten tau di");
		String codeTrainStart = trainTicket.getDynamicTextViewIndex("0", "com.VCB:id/tv_ten_tau");

		log.info("TC_09_Click button tiep tuc chon chieu ve");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE_TO_CHOOSE);

		log.info("TC_09_Click chon mot chuyen di");
		trainTicket.clickDynamicSelectTrain("1", "com.VCB:id/tv_ten_tau");
		String codeTrainEnd = trainTicket.getDynamicTextViewIndex("0", "com.VCB:id/tv_ten_tau");

		log.info("TC_09_Click button tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_09_verify chuyen sang man chon cho chieu di");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.CHOISE_FROM);

		log.info("TC_09_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_09_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_09_verify tên tàu");
		verifyEquals(trainTicket.getDynamicTextViewIndex("0", "com.VCB:id/tv_ten_tau"), codeTrainStart);

		log.info("TC_09_Click chon toa");
		trainTicket.clickDynamicSelectLocation("0", "com.VCB:id/tvWagon");

		log.info("TC_09_get lay mau o cho trong");
		String colorOfSeat = trainTicket.getColorOfElement(TrainTicketPageUIs.IMAGE_BY_TEXT, textDefault.BLANK, textDefault.EXTRA_SEATS);

		log.info("TC_09_Click chon cho trong");
		trainTicket.scrollIDownOneTime(driver);
		listActual = trainTicket.chooseSeats(1, colorOfSeat);

		log.info("TC_09Verify so ghe da chon");
		listExpect = Arrays.asList(trainTicket.getTextInDynamicPopup("com.VCB:id/tvSeat"));
		verifyEquals(listActual, listExpect);

		log.info("TC_09_Click tiep tuc chon chieu ve");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE_TO_CHOOSE);

		log.info("TC_09_verify chuyen sang man chon cho chieu ve");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.CHOOSE_SEAT_TO);

		log.info("TC_09_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from_return"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_09_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to_return"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_09_verify tên tàu");
		verifyEquals(trainTicket.getDynamicTextViewIndex("0", "com.VCB:id/tv_ten_tau_return"), codeTrainEnd);

		log.info("TC_09_Click chon toa");
		trainTicket.clickDynamicSelectLocation("0", "com.VCB:id/tvWagon");

		log.info("TC_09_get lay mau o cho trong");
		String colorOfSeatEnd = trainTicket.getColorOfElement(TrainTicketPageUIs.IMAGE_BY_TEXT, textDefault.BLANK, textDefault.EXTRA_SEATS);

		log.info("TC_09_Click chon cho trong");
		trainTicket.scrollIDownOneTime(driver);
		listActual = trainTicket.chooseSeats(1, colorOfSeatEnd);

		log.info("TC_09_Verify so ghe da chon");
		listExpect = Arrays.asList(trainTicket.getTextInDynamicPopup("com.VCB:id/tvSeat"));
		verifyEquals(listActual, listExpect);

		log.info("TC_09_Click tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_09_Get so tien chieu di");
		String amountStart = trainTicket.getTextInDynamicPopup("com.VCB:id/tvAmount");
		long amountStartConvert = convertAvailableBalanceCurrentcyOrFeeToLong(amountStart);

		log.info("TC_09_Get so tien chieu ve");
		String amountEnd = trainTicket.getTextInDynamicPopup("com.VCB:id/tvAmountReturn");
		long amountEndConvert = convertAvailableBalanceCurrentcyOrFeeToLong(amountEnd);
		String amountTotal = addCommasToLong(amountEndConvert + amountStartConvert + "") + " VND";

		log.info("TC_09_Verify tong so tien thanh toan");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTotalAmount"), amountTotal);

		log.info("TC_09_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.CONTINUE);

		log.info("TC_09_Verify man hinh thong tin hanh khach");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.INFOMATION_PASSENGER);

		log.info("TC_09_Nhap ho ten khách hang");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CUSTOMER_NAME, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvHoTen");

		log.info("TC_09_Nhap so CMT");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CARD_NO, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvCMND");

		log.info("TC_09_Nhap so CMT");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.CARD_NO, textDefault.CONTACT_INFO, "com.VCB:id/tvCMND");

		log.info("TC_09_Nhap email");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.email, textDefault.CONTACT_INFO, "com.VCB:id/tvEmail");

		log.info("TC_09_Click radio khong xuat hoa don");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivNoXuatHoaDon");

		log.info("TC_09_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.CONTINUE);

		log.info("TC_09_click button dong y dong popup");
		trainTicket.clickToDynamicButtonContains("Đ");

		log.info("TC_09_Verify man hinh thong tin dat ve");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.INFOMATION_BOOKING);

		log.info("TC_09_Get tong tien chieu di");
		trainTicket.getTextTotal("0", "com.VCB:id/tvTotalAmount");

		log.info("TC_09_Get tong tien chieu ve");
		trainTicket.getTextTotal("1", "com.VCB:id/tvTotalAmount");

		log.info("TC_09_click button Thanh toan");
		trainTicket.clickToDynamicButtonContains(textDefault.PAY);

		log.info("TC_09_click button Khong");
		trainTicket.clickToDynamicButton(textDefault.NO);

		log.info("TC_09_verify hien thi Thong tin ve Tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(textDefault.INFOMATION_TRAIN_TICKET));
		
		trainTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = trainTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		log.info("TC_09_veriFy So Dien Thoai");
		SDT = trainTicket.getDynamicTextOld(driver, textDefault.PHONE_NUMBER);
		verifyEquals(phoneNumber, SDT);

		log.info("TC_09_veriFy Email");
		email = trainTicket.getDynamicTextOld(driver, "Email");
		verifyEquals(TrainTicket_Data.inputText.email, email);

		log.info("TC_09_veriFy Thong tin ho chieu");
		hoChieu = trainTicket.getDynamicTextOld(driver, textDefault.IDENTITY_CARD);

		verifyEquals(TrainTicket_Data.inputText.CARD_NO, hoChieu);
		trainTicket.scrollDownToText(driver, textDefault.TOTAL_MONEY_PAY);

		log.info("TC_09_Verify tong tien thanh toan-------");
		tongTienThanhToan = trainTicket.getDynamicTextOld(driver, textDefault.TOTAL_MONEY_PAY);

		log.info("TC_09_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_09_Verify hien thi man hinh xac nhan thong tin");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(textDefault.CONFIRM_INFORMATION));
		log.info("TC_09_Verify tai khoan nguon");
		verifyEquals(account, trainTicket.getDynamicTextOld(textDefault.SOURCE_ACCOUNT));

		log.info("TC_09_Verify rong so tien thanh toan");
		verifyTrue(trainTicket.getDynamicTextOld("Số tiền").contains(tongTienThanhToan));
		
		log.info("TC_09_Step_06: Chon phuong thuc xac thuc Smart OTP");
		trainTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		trainTicket.clickToDynamicButtonLinkOrLinkText(VehicalData.DATA_ORDER_TICKET.SMART_OTP);
		
		log.info("TC_09_Verify Phi giao dich");
		soTienPhi = trainTicket.getDynamicTextOld(textDefault.TRANSACTION_FEE);

		log.info("TC_09_Step_07: An nut 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_09_Step_08: Nhap du ki tu vao o nhap OTP");
		trainTicket.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");
		trainTicket.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");

		log.info("TC_09_Step_09: An tiep button 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_09_Step_09: An tiep button Hien thi thong bao thanh toan thanh cong");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(message.MESSAGE_SUCCESS));

		log.info("TC_09_Step_09: Click thong quay lai man hinh home");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivHome");

	}

	@Test
	public void TC_10_BaoCaoGiaoDich_DatVe_KhuHoi_SoLuongNguoiNhoNhat_XacThucBang_SmartOTP() {
		log.info("TC_10: Click menu header");
		trainTicket.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_10: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_10: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_10:_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRAIN_TICKET);

		log.info("TC_10:: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_10: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_10:: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_10:: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10:: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10:: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		verifyEquals(transferTimeInReport, transferTimeInReport1);
		log.info("TC_10:: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_10:So tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(tongTienThanhToan));

		log.info("TC_10: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE), soTienPhi);

		log.info("TC_10:: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.TRAIN_TICKET);

		log.info("TC_10:: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOI_DUNG));

		log.info("TC_10: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
