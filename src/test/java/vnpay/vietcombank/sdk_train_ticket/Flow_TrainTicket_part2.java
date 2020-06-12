package vnpay.vietcombank.sdk_train_ticket;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.sdk.trainTicket.TrainTicketPageObject;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;
import vietcombank_test_data.TransferMoneyQuick_Data;
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data;
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data.textDefault;

public class Flow_TrainTicket_part2 extends Base {
	AppiumDriver<MobileElement> driver;
	private TransactionReportPageObject transReport;
	private LogInPageObject login;
	private TrainTicketPageObject trainTicket;

	List<String> listExpect;
	List<String> listActual;
	String phoneNumber = "";
	String taiKhoanNguon = "";
	String email = "";
	String hoChieu = "";
	String tongTienThanhToan = "";
	String soTienPhi = "";
	String password = "";

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
		phoneNumber = phone;
		trainTicket = PageFactoryManager.getTrainTicketPageObject(driver);
		password = pass;
	}

	@Test
	public void TC_01_DatVe_MotChieu_SoLuongNguoiNhoNhat_XacThucBang_MatKhauDangNhap() {
		log.info("TC_01_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.TRAIN_BOOKING);

		log.info("TC_01_Step_Click close message");
		trainTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_01_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(textDefault.TRAIN_BOOKING));

		log.info("TC_01_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd(textDefault.TRAIN_BOOKING, "com.VCB:id/tvTextPickUp");

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
		trainTicket.clickToDynamicButton(textDefault.NEXT);

		log.info("TC_01_verify chuyen sang man chon cho chieu di");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.LIST_TO);

		log.info("TC_01_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_01_Click chon gio khoi hanh");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivBgItem");

		log.info("TC_01_Click TIEP TUC");
		trainTicket.clickToDynamicButton(textDefault.NEXT);

		log.info("TC_01_Click chon toa");
		trainTicket.clickDynamicSelectLocation("0", "com.VCB:id/tvWagon");

		log.info("TC_01_get lay mau o cho trong");
		String colorOfSeat = trainTicket.getColorOfElement(TrainTicketPageUIs.IMAGE_BY_TEXT, "Chỗ trống");

		log.info("TC_01_Click chon cho trong");
		trainTicket.scrollIDownOneTime(driver);
		listActual = trainTicket.chooseSeats(1, colorOfSeat);

		log.info("TC_01Verify so ghe da chon");
		listExpect = Arrays.asList(trainTicket.getTextInDynamicPopup("com.VCB:id/tvSeat"));
		verifyEquals(listActual, listExpect);

		log.info("TC_01_Click tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.NEXT);

		log.info("TC_01_Verify man hinh");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), "Chỗ đang đặt");

		log.info("TC_01_Get so tien chieu di");
		String amountStart = trainTicket.getTextInDynamicPopup("com.VCB:id/tvAmount");
		long amountStartConvert = convertAvailableBalanceCurrentcyOrFeeToLong(amountStart);

		String amountTotal = addCommasToLong(amountStartConvert + "") + " VND";

		log.info("TC_01_Verify tong so tien thanh toan");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTotalAmount"), amountTotal);

		log.info("TC_01_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_01_Verify man hinh thong tin hanh khach");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), "Thông tin hành khách");

		log.info("TC_01_Nhap ho ten khách hang");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CUSTOMER_NAME, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvHoTen");

		log.info("TC_01_Nhap so CMT");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CARD_NO, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvCMND");

		log.info("TC_01_Nhap so CMT");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.CARD_NO, "Thông tin liên hệ", "com.VCB:id/tvCMND");

		log.info("TC_01_Nhap email");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.email, "Thông tin liên hệ", "com.VCB:id/tvEmail");

		log.info("TC_01_Click radio khong xuat hoa don");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivNoXuatHoaDon");

		log.info("TC_01_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_01_click button dong y dong popup");
		trainTicket.clickToDynamicButtonContains("Đ");

		log.info("TC_01_Verify man hinh thong tin dat ve");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), "Thông tin đặt vé");

		log.info("TC_01_Get tong tien chieu di");
		trainTicket.getTextTotal("0", "com.VCB:id/tvTotalAmount");

		log.info("TC_01_click button Thanh toan");
		trainTicket.clickToDynamicButtonContains("THANH TOÁN");

		log.info("TC_01_click button Khong");
		trainTicket.clickToDynamicButton("Không");

		log.info("TC_01_verify hien thi Thong tin ve Tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Thông tin vé tàu"));

		
		trainTicket.scrollUpToText("Thông tin người chuyển");
		log.info("TC_01_lay Tai khoan nguon");
		taiKhoanNguon = trainTicket.getDynamicDateTime("com.VCB:id/number_account");

		log.info("TC_01_veriFy Email");
		email = trainTicket.getDynamicTextOld(driver, "Email");
		verifyEquals(TrainTicket_Data.inputText.email, email);

		log.info("TC_01_veriFy Thong tin ho chieu");
		hoChieu = trainTicket.getDynamicTextOld(driver, "CMND/CCCD/Hộ chiếu");

		verifyEquals(TrainTicket_Data.inputText.CARD_NO, hoChieu);
		trainTicket.scrollDownToText(driver, "Tổng tiền thanh toán");

		log.info("TC_01_Verify tong tien thanh toan-------");
		tongTienThanhToan = trainTicket.getDynamicTextOld(driver, "Tổng tiền thanh toán");

		log.info("TC_01_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_01_Verify hien thi man hinh xac nhan thong tin");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Xác nhận thông tin"));
		log.info("TC_01_Verify tai khoan nguon");
		verifyEquals(taiKhoanNguon, trainTicket.getDynamicTextOld("Tài khoản nguồn"));

		log.info("TC_01_Verify rong so tien thanh toan");
		verifyTrue(trainTicket.getDynamicTextOld("Số tiền").contains(tongTienThanhToan));

		log.info("TC_01_Step_06: Chon phuong thuc xac thuc Mat khau dang nhap");
		trainTicket.clickToTextID(driver, "com.VCB:id/tvptxt");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_01_Step_07: An nut 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_01_Step_08: Nhap du ki tu vao o nhap OTP");
		trainTicket.inputToDynamicPopupPasswordInput(driver, password, textDefault.NEXT);

		log.info("TC_01_Step_09: An tiep button 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_01_Step_09: An tiep button Hien thi thong bao thanh toan thanh cong");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("THANH TOÁN THÀNH CÔNG"));//

		log.info("TC_01_Step_09: Click thong quay lai man hinh home");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivHome");

	}

	@Test
	public void TC_02_BaoCao_DatVe_MotChieu_SoLuongNguoiNhoNhat_XacThucBang_MatKhauDangNhap() {
		log.info("TC_02: Click menu header");
		trainTicket.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_02: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_02:_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán vé tàu");

		log.info("TC_02:: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

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
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), taiKhoanNguon);

//		log.info("TC_02:: Check tai khoan ghi co");
//		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_02:So tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(tongTienThanhToan));

		log.info("TC_02:: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Thanh toán vé tàu");

		log.info("TC_02:: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOI_DUNG));

		log.info("TC_02: Click Back ve Home");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
		transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_02: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_03_DatVe_MotChieu_SoLuongNguoiNhoNhat_XacThucBang_SMSOTP() {
		log.info("TC_03_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.TRAIN_BOOKING);

		log.info("TC_03_Step_Click close message");
		trainTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_03_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed(textDefault.TRAIN_BOOKING));

		log.info("TC_03_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd(textDefault.TRAIN_BOOKING, "com.VCB:id/tvTextPickUp");

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
		trainTicket.clickToDynamicButton(textDefault.NEXT);

		log.info("TC_03_verify chuyen sang man chon cho chieu di");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.LIST_TO);

		log.info("TC_03_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_03_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_03_Click chon gio khoi hanh");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivBgItem");

		log.info("TC_03_Click TIEP TUC");
		trainTicket.clickToDynamicButton(textDefault.NEXT);

		log.info("TC_03_Click chon toa");
		trainTicket.clickDynamicSelectLocation("0", "com.VCB:id/tvWagon");

		log.info("TC_03_get lay mau o cho trong");
		String colorOfSeat = trainTicket.getColorOfElement(TrainTicketPageUIs.IMAGE_BY_TEXT, "Chỗ trống");

		log.info("TC_03_Click chon cho trong");
		trainTicket.scrollIDownOneTime(driver);
		listActual = trainTicket.chooseSeats(1, colorOfSeat);

		log.info("TC_03Verify so ghe da chon");
		listExpect = Arrays.asList(trainTicket.getTextInDynamicPopup("com.VCB:id/tvSeat"));
		verifyEquals(listActual, listExpect);

		log.info("TC_03_Click tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.NEXT);

		log.info("TC_03_Verify man hinh");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), "Chỗ đang đặt");

		log.info("TC_03_Get so tien chieu di");
		String amountStart = trainTicket.getTextInDynamicPopup("com.VCB:id/tvAmount");
		long amountStartConvert = convertAvailableBalanceCurrentcyOrFeeToLong(amountStart);

		String amountTotal = addCommasToLong(amountStartConvert + "") + " VND";

		log.info("TC_03_Verify tong so tien thanh toan");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTotalAmount"), amountTotal);

		log.info("TC_03_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_03_Verify man hinh thong tin hanh khach");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), "Thông tin hành khách");

		log.info("TC_03_Nhap ho ten khách hang");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CUSTOMER_NAME, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvHoTen");

		log.info("TC_03_Nhap so CMT");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CARD_NO, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvCMND");

		log.info("TC_03_Nhap so CMT");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.CARD_NO, "Thông tin liên hệ", "com.VCB:id/tvCMND");

		log.info("TC_03_Nhap email");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.email, "Thông tin liên hệ", "com.VCB:id/tvEmail");

		log.info("TC_03_Click radio khong xuat hoa don");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivNoXuatHoaDon");

		log.info("TC_03_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_03_click button dong y dong popup");
		trainTicket.clickToDynamicButtonContains("Đ");

		log.info("TC_03_Verify man hinh thong tin dat ve");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), "Thông tin đặt vé");

		log.info("TC_03_Get tong tien chieu di");
		trainTicket.getTextTotal("0", "com.VCB:id/tvTotalAmount");

		log.info("TC_03_click button Thanh toan");
		trainTicket.clickToDynamicButtonContains("THANH TOÁN");

		log.info("TC_03_click button Khong");
		trainTicket.clickToDynamicButton("Không");

		log.info("TC_03_verify hien thi Thong tin ve Tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Thông tin vé tàu"));
		
		trainTicket.scrollUpToText("Thông tin người chuyển");

		log.info("TC_03_lay Tai khoan nguon");
		taiKhoanNguon = trainTicket.getDynamicDateTime("com.VCB:id/number_account");

		log.info("TC_03_veriFy Email");
		email = trainTicket.getDynamicTextOld(driver, "Email");
		verifyEquals(TrainTicket_Data.inputText.email, email);

		log.info("TC_03_veriFy Thong tin ho chieu");
		hoChieu = trainTicket.getDynamicTextOld(driver, "CMND/CCCD/Hộ chiếu");

		verifyEquals(TrainTicket_Data.inputText.CARD_NO, hoChieu);
		trainTicket.scrollDownToText(driver, "Tổng tiền thanh toán");

		log.info("TC_03_Verify tong tien thanh toan-------");
		tongTienThanhToan = trainTicket.getDynamicTextOld(driver, "Tổng tiền thanh toán");

		log.info("TC_03_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_03_Verify hien thi man hinh xac nhan thong tin");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Xác nhận thông tin"));
		
		log.info("TC_03_Verify tai khoan nguon");
		verifyEquals(taiKhoanNguon, trainTicket.getDynamicTextOld("Tài khoản nguồn"));

		log.info("TC_03_Verify rong so tien thanh toan");
		verifyTrue(trainTicket.getDynamicTextOld("Số tiền").contains(tongTienThanhToan));

		log.info("TC_03_Step_06: Chon phuong thuc xac thuc SMS OTP");
		trainTicket.clickToTextID(driver, "com.VCB:id/tvptxt");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_03_Step_07: An nut 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_03_Step_08: Nhap du ki tu vao o nhap OTP");
		trainTicket.inputToDynamicOtp(driver, "123456", textDefault.NEXT);

		log.info("TC_03_Step_09: An tiep button 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_03_Step_09: An tiep button Hien thi thong bao thanh toan thanh cong");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("THANH TOÁN THÀNH CÔNG"));

		log.info("TC_03_Step_09: Click thong quay lai man hinh home");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivHome");

	}

	@Test
	public void TC_04_BaoCao_DatVe_MotChieu_SoLuongNguoiNhoNhat_XacThucBang_SMSOTP() {
		log.info("TC_04: Click menu header");
		trainTicket.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_04: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_04:_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán vé tàu");

		log.info("TC_04:: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

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
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), taiKhoanNguon);

//		log.info("TC_04:: Check tai khoan ghi co");
//		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_04:So tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(tongTienThanhToan));

		log.info("TC_04:: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Thanh toán vé tàu");

		log.info("TC_04:: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOI_DUNG));

		log.info("TC_04: Click Back ve Home");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
		transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
