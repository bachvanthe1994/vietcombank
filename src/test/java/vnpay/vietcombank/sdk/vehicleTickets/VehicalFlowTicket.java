package vnpay.vietcombank.sdk.vehicleTickets;

import java.io.IOException;
import java.time.LocalDate;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import vehicalPageObject.VehicalPageObject;
import vehicalTicketBookingUI.CommonPageUIs;
import vietcombankUI.DynamicPageUIs;
import vnpay.vietcombank.sdk.vehicleTicket.data.VehicalData;

public class VehicalFlowTicket extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private VehicalPageObject vehicalTicket;
	public String amountFee = "- ";
	public String nameTyped = "";
	public String phoneTyped = "";
	public String emailTyped = "";
	public String diemDi = "";
	public String diemDen = "";
	public String hangXe = "";
	public String soGhe = "";
	public String soLuongVe = "";
	public String tongTien = "";
	public String taiKhoanNguon = "";
	public String maThanhToan = "";
	public String maGiaodich = "";
	public String maVe = "";

	LocalDate now = LocalDate.now();
	String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tomorow = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + Integer.valueOf(getCurrentDay()) + 1 + "/" + getCurrenMonth() + "/" + getCurrentYear();

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
		vehicalTicket = PageFactoryManager.getVehicalPageObject(driver);
		login.Global_login(phone, pass, opt);
		login.scrollDownToText(driver, VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		login.scrollIDownOneTime(driver);

		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		vehicalTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_01_MuaVeXeBangPassword() {
		log.info("============TC_01_Step_01: Kiem tra hien thi thoi gian khoi hanh");
		String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		verifyEquals(DayStart, today);

		log.info("=========TC_01_Step_02: Kiem tra hien thi ngay hom nay");
		String today = vehicalTicket.getDynamicDayStart("com.VCB:id/tvHomnay");
		verifyEquals(today, VehicalData.DATA_ORDER_TICKET.TODAY);

		log.info("===========TC_01_Step_03: Kiem tra hien thi ngay mai");
		String tomorrow = vehicalTicket.getDynamicDayStart("com.VCB:id/tvNgayMai");
		verifyEquals(tomorrow, VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("=======TC_01_Step_04: Kiem tra hien thi button tim kiem chuyen di");
		verifyTrue(vehicalTicket.isDynamicButtonDisplayed(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP));

		log.info("==========TC_01_Step_05: Chon va nhap diem di");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.DESTINATION);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("========TC_01_Step_06: Chon va nhap diem den");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);

		log.info("=====TC_01_Step_07: Chon ngay muon di");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_01_Step_8: Tim kiem chuyen di");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_01_Step_09: Chon ghe: ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TITLE_CHOISE_CHAIR);
		String colorSeat = "(255,255,255)";
		vehicalTicket.chooseSeats(1, colorSeat);

		log.info("TC_01_Step_10 : Dat chuyen di: ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BOOK_SEAT);

		log.info("TC_01_Step_11: Chon ben diem di: ");
		vehicalTicket.waitForElementVisible(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");
		vehicalTicket.clickToElement(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");

		log.info("TC_01_Step_12 Chon ben diem den: ");
		vehicalTicket.waitForElementVisible(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");
		vehicalTicket.clickToElement(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");

		log.info("TC_01_Step_13 Click chon tiep tuc ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_01_Step_14 Click chọn Cho phep");
		vehicalTicket.clickToDynamicAcceptButton("com.android.packageinstaller:id/permission_allow_button");

		log.info("TC_01_Step_15 Input email");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.EMAIL, VehicalData.DATA_ORDER_TICKET.INPUT_INFO);

		log.info("------TC_01_Step_16 Lay thong tin ca nhan-------------");

		log.info("------TC_01_Step_17 ho va ten-------------");
		nameTyped = vehicalTicket.getDynamicEditText("com.VCB:id/full_name");

		log.info("------TC_01_Step_18 So dien thoai -------------");
		phoneTyped = vehicalTicket.getDynamicEditText("com.VCB:id/autoCompletePhone");

		log.info("------TC_01_Step_19 Email-------------");
		emailTyped = vehicalTicket.getDynamicEditText("com.VCB:id/email");

		log.info("------TC_01_Step_20 thon tin diem xuat phat-------------");
		diemDi = vehicalTicket.getDynamicTextView("com.VCB:id/tvFrom");

		log.info("------TC_01_Step_21 Thong tin diem den-------------");
		diemDen = vehicalTicket.getDynamicTextView("com.VCB:id/tvTo");

		log.info("------TC_01_Step_22 Hang xe -------------");
		hangXe = vehicalTicket.getDynamicTextView("com.VCB:id/tv_hang_xe");

		log.info("------TC_01_Step_23  So ghe-------------");
		soGhe = vehicalTicket.getDynamicTextView("com.VCB:id/tvAllSeat");

		log.info("------TC_01_Step_24 So luong ghe ngoi-------------");
		soLuongVe = vehicalTicket.getDynamicTextView("com.VCB:id/tvTicketNumber");

		log.info("------TC_01_Step_25 Tong so tien can thanh toan -------------");
		tongTien = vehicalTicket.getDynamicTextView("com.VCB:id/tvTotalAmount");

		log.info("TC_01_Step_26 Click button tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_01_Step_27 Verify hien thi man hinh thong tin khach hang");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.INFO_CUSTOMER));

		log.info("--------TC_01_Step_28 Verify ho va ten khach hang------------------");
		verifyEquals(nameTyped, vehicalTicket.getDynamicEditText("com.VCB:id/full_name"));

		log.info("--------TC_01_Step_30 Verify So dien thoai ------------------");
		verifyEquals(phoneTyped, vehicalTicket.getDynamicEditText("com.VCB:id/autoCompletePhone"));

		log.info("--------TC_01_Step_31 Verify Email------------------");
		verifyEquals(emailTyped, vehicalTicket.getDynamicEditText("com.VCB:id/email"));

		log.info("--------TC_01_Step_32 Verify diem di------------------");
		verifyEquals(diemDi, vehicalTicket.getDynamicTextView("com.VCB:id/tvFrom"));

		log.info("--------TC_01_Step_33 Verify diem den-----------------");
		verifyEquals(diemDen, vehicalTicket.getDynamicTextView("com.VCB:id/tvTo"));

		log.info("--------TC_01_Step_34 Verify hhang Xe-----------------");
		verifyEquals(hangXe, vehicalTicket.getDynamicTextView("com.VCB:id/tv_hang_xe"));

		log.info("--------TC_01_Step_35 Verify So ghe ngoi------------------");
		verifyEquals(soGhe, vehicalTicket.getDynamicTextView("com.VCB:id/tvAllSeat"));

		log.info("--------TC_01_Step_36 Verify tong so ve-----------------");
		verifyEquals(soLuongVe, vehicalTicket.getDynamicTextView("com.VCB:id/tvTicketNumber"));

		log.info("TC_01_Step_37 Verify tong tien ");
		verifyEquals(tongTien, vehicalTicket.getDynamicTextView("com.VCB:id/tvTotalAmount"));

		log.info("TC_01_Step_38 Click btutton Thanh toan");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.THANHTOAN);

		log.info("TC_01_Step_39 Verify hien thi man hinh thong tin ve xe");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.INFO_TICKET));

		log.info("TC_01_Step_40 Lay thong tin tai khoan nguon");
		vehicalTicket.scrollUpToText(VehicalData.DATA_ORDER_TICKET.AMOUNT_ROOT);
		taiKhoanNguon = vehicalTicket.getDynamicTextView("com.VCB:id/number_account");

		log.info("--------TC_01_Step_41 Verify ho va ten khach hang------------------");
		verifyEquals(nameTyped, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.NAME));

		log.info("--------TC_01_Step_42 Verify ho va ten khach hang------------------");
		verifyEquals(phoneTyped, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.PHONE_NUMBER));

		log.info("--------TC_01_Step_42 Verify Email------------------");
		verifyEquals(emailTyped, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.EMAIL_ADDRESS));
		vehicalTicket.scrollIDownOneTime(driver);

		log.info("--------TC_01_Step_44 Verify diem di ------------------");
		verifyEquals(diemDi, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.DESTINATION));

		log.info("--------TC_01_Step_45 Verify diem den ------------------");
		verifyEquals(diemDen, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.ARRIVAL));

		log.info("--------TC_01_Step_46 Verify hang xe------------------");
		verifyEquals(hangXe, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.BRAND));

		log.info("--------TC_01_Step_47 Verify So ghe------------------");
		verifyEquals(soGhe, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.SEAT_NUMBER));

		log.info("--------TC_01_Step_48_Verify So luong ve-----------------");
		verifyEquals(soLuongVe, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.TOTAL_TICKET));

		log.info("-----------TC_01_Step_49 Verify tong tien");
		tongTien = tongTien.replace("đ", "VND");
		verifyEquals(tongTien, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.AMOUNT_TT));

		log.info("------------TC_01_Step_50 Click button tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("-------TC_01_Step_51 Xac nhan hien thi man hinh xac nhan thong tin");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.TITILE_CONFIRM));

		log.info("--------TC_01_Step_52 Verrify tai khoan nguon-----");
		verifyEquals(taiKhoanNguon, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.AMOUNT_ROOT));
		maThanhToan = vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT);

		log.info("-------TC_01_Step_53_ Click chon phuong thuc xac thuc");
		vehicalTicket.clickToDynamicTomorrow("com.VCB:id/llptxt");

		log.info("-------TC_01_Step_54_ Verify hien thi man hinh cac phuong thuc xac minh");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.TITILE_SELECT));

		log.info("-------TC_01_Step_55 Chon hinh thuc xac thuc là mat khau");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PASS);

		log.info("-------TC_01_Step_56 Click button Tiep tuc");
		vehicalTicket.waitForElementVisible(CommonPageUIs.DYNAMIC_BUTTON, VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("-------TC_01_Step_57 Nhap mat khau cua tai khoan");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PASSWORD_LOGIN, VehicalData.DATA_ORDER_TICKET.INPUT_PASSWORD);

		log.info("TC_01_Step_58 Click btn Tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("----------TC_01_Step_59 Verify hien thi man hinh giao dich thanh cong");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.NOTIFICATION.NOTI_SUCCESS));

		log.info("----------TC-O1_Step_60 Verify thông tin ma thanh toan");
		verifyEquals(maThanhToan, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT));

		log.info("---------------TC-O1_Step_61 Lấy thông tin mã giao dịch");
		maGiaodich = vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_GD);

		log.info("--------------TC-O1_Step_62 Verify số tiền thanh toán");
		tongTien = tongTien.replace("đ", "VND");
		verifyEquals(tongTien, vehicalTicket.getDynamicTextView("com.VCB:id/tvAmount"));
	}

	@Test
	public void TC_02_MuaVeXeBangOTP() {
		
		log.info("--------TC_02_Step_01_Click Thuc hien giao dich moi");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BTN_CONTINUE);
		

		log.info("==========TC_02_Step_02: Chon va nhap diem di");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.DESTINATION);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("========TC_02_Step_03: Chon va nhap diem den");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);

		log.info("=====TC_02_Step_04: Chon ngay muon di");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_02_Step_05: Tim kiem chuyen di");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_02_Step_06: Chon ghe: ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TITLE_CHOISE_CHAIR);
		String colorSeat = "(255,255,255)";
		vehicalTicket.chooseSeats(1, colorSeat);

		log.info("TC_02_Step_07 : Dat chuyen di: ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BOOK_SEAT);
		
		log.info("-----------TC_02_Step_08: Chon ben diem di: ");
		vehicalTicket.waitForElementVisible(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");
		vehicalTicket.clickToElement(CommonPageUIs.DYNAMIC_POINT_ARRVAL,"com.VCB:id/tvAddress");

		log.info("-----------TC_02_Step_9 Chon ben diem den: ");
		vehicalTicket.waitForElementVisible(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");
		vehicalTicket.clickToElement(CommonPageUIs.DYNAMIC_POINT_ARRVAL,"com.VCB:id/tvAddress");

		log.info("---------------TC_02_Step_10 Click chon tiep tuc ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("------------TC_02_Step_11___Input email");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.EMAIL, VehicalData.DATA_ORDER_TICKET.INPUT_INFO);

		log.info("------TC_02_Step_12 Lay thong tin ca nhan-------------");

		log.info("------TC_02_Step_13 ho va ten-------------");
		nameTyped = vehicalTicket.getDynamicEditText("com.VCB:id/full_name");

		log.info("------TC_02_Step_14 So dien thoai -------------");
		phoneTyped = vehicalTicket.getDynamicEditText("com.VCB:id/autoCompletePhone");

		log.info("------TC_02_Step_15Email-------------");
		emailTyped = vehicalTicket.getDynamicEditText("com.VCB:id/email");

		log.info("------TC_02_Step_16 thon tin diem xuat phat-------------");
		diemDi = vehicalTicket.getDynamicTextView("com.VCB:id/tvFrom");

		log.info("------TC_02_Step_17 Thong tin diem den-------------");
		diemDen = vehicalTicket.getDynamicTextView("com.VCB:id/tvTo");

		log.info("------TC_02_Step_18 Hang xe -------------");
		hangXe = vehicalTicket.getDynamicTextView("com.VCB:id/tv_hang_xe");

		log.info("------TC_02_Step_19  So ghe-------------");
		soGhe = vehicalTicket.getDynamicTextView("com.VCB:id/tvAllSeat");

		log.info("------TC_02_Step_20 So luong ghe ngoi-------------");
		soLuongVe = vehicalTicket.getDynamicTextView("com.VCB:id/tvTicketNumber");

		log.info("------TC_02_Step_21 Tong so tien can thanh toan -------------");
		tongTien = vehicalTicket.getDynamicTextView("com.VCB:id/tvTotalAmount");

		log.info("-------TC_02_Step_22 Click button tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("-------TC_02_Step_23Verify hien thi man hinh thong tin khach hang");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.INFO_CUSTOMER));

		log.info("--------TC_02_Step_24 Verify ho va ten khach hang------------------");
		verifyEquals(nameTyped, vehicalTicket.getDynamicEditText("com.VCB:id/full_name"));

		log.info("--------TC_02_Step_25 Verify So dien thoai ------------------");
		verifyEquals(phoneTyped, vehicalTicket.getDynamicEditText("com.VCB:id/autoCompletePhone"));

		log.info("--------TC_02_Step_26 Verify Email------------------");
		verifyEquals(emailTyped, vehicalTicket.getDynamicEditText("com.VCB:id/email"));

		log.info("--------TC_02_Step_27 Verify diem di------------------");
		verifyEquals(diemDi, vehicalTicket.getDynamicTextView("com.VCB:id/tvFrom"));

		log.info("--------TC_02_Step_28 Verify diem den-----------------");
		verifyEquals(diemDen, vehicalTicket.getDynamicTextView("com.VCB:id/tvTo"));

		log.info("--------TC_02_Step_30 Verify hhang Xe-----------------");
		verifyEquals(hangXe, vehicalTicket.getDynamicTextView("com.VCB:id/tv_hang_xe"));

		log.info("--------TC_02_Step_31 Verify So ghe ngoi------------------");
		verifyEquals(soGhe, vehicalTicket.getDynamicTextView("com.VCB:id/tvAllSeat"));

		log.info("--------TC_02_Step_32 Verify tong so ve-----------------");
		verifyEquals(soLuongVe, vehicalTicket.getDynamicTextView("com.VCB:id/tvTicketNumber"));

		log.info("------------TC_02_Step_33 Verify tong tien ");
		verifyEquals(tongTien, vehicalTicket.getDynamicTextView("com.VCB:id/tvTotalAmount"));

		log.info("-------TC_02_Step_34 Click btutton Thanh toan");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.THANHTOAN);

		log.info("---------TC_02_Step_35 Verify hien thi man hinh thong tin ve xe");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.INFO_TICKET));

		log.info("----------TC_02_Step_36 Lay thong tin tai khoan nguon");
		vehicalTicket.scrollUpToText(VehicalData.DATA_ORDER_TICKET.AMOUNT_ROOT);
		taiKhoanNguon = vehicalTicket.getDynamicTextView("com.VCB:id/number_account");

		log.info("--------TC_02_Step_37 Verify ho va ten khach hang------------------");
		verifyEquals(nameTyped, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.NAME));

		log.info("--------TC_02_Step_38 Verify ho va ten khach hang------------------");
		verifyEquals(phoneTyped, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.PHONE_NUMBER));

		log.info("--------TC_02_Step_39 Verify Email------------------");
		verifyEquals(emailTyped, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.EMAIL_ADDRESS));
		vehicalTicket.scrollIDownOneTime(driver);

		log.info("--------TC_02_Step_40 Verify diem di ------------------");
		verifyEquals(diemDi, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.DESTINATION));

		log.info("--------TC_02_Step_41 Verify diem den ------------------");
		verifyEquals(diemDen, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.ARRIVAL));

		log.info("--------TC_02_Step_42 Verify hang xe------------------");
		verifyEquals(hangXe, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.BRAND));

		log.info("--------TC_02_Step_43 Verify So ghe------------------");
		verifyEquals(soGhe, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.SEAT_NUMBER));

		log.info("--------TC_02_Step_44 Verify So luong ve-----------------");
		verifyEquals(soLuongVe, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.TOTAL_TICKET));

		log.info("-----------TC_02_Step_45 Verify tong tien");
		tongTien = tongTien.replace("đ", "VND");
		verifyEquals(tongTien, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.AMOUNT_TT));

		log.info("------------TC_02_Step_46 Click button tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("-------TC_02_Step_47 Xac nhan hien thi man hinh xac nhan thong tin");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.TITILE_CONFIRM));

		log.info("--------TC_02_Step_48 Verrify tai khoan nguon-----");
		verifyEquals(taiKhoanNguon, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.AMOUNT_ROOT));
		maThanhToan = vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT);

		log.info("-------TC_02_Step_49 Click chon phuong thuc xac thuc");
		vehicalTicket.clickToDynamicTomorrow("com.VCB:id/llptxt");

		log.info("-------TC_02_Step_50- Verify hien thi man hinh cac phuong thuc xac minh");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.TITILE_SELECT));

		log.info("-------TC_02_Step_51- Chon hinh thuc xac thuc là mat khau");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.OTP);

		log.info("-------TC_02_Step_52 Click button Tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);
		
		log.info("-------TC_02_Step_53Nhap ma OTP");
		vehicalTicket.inputToDynamicOtp(VehicalData.DATA_ORDER_TICKET.NUMBER_OTP, VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);
		
		log.info("TC_02_Step_54 Click btn Tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("----------TC_02_Step_55 Verify hien thi man hinh giao dich thanh cong");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.NOTIFICATION.NOTI_SUCCESS));

		log.info("----------TC-O2_Step_56Verify thông tin ma thanh toan");
		verifyEquals(maThanhToan, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT));

		log.info("---------------TC-O2_Step_57 Lấy thông tin mã giao dịch");
		maGiaodich = vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_GD);

		log.info("--------------TC-O2_Step_58 Verify số tiền thanh toán");
		tongTien = tongTien.replace("đ", "VND");
		verifyEquals(tongTien, vehicalTicket.getDynamicTextView("com.VCB:id/tvAmount"));

	}

	@Test
	public void TC_03_KiemTraLichSuGiaoDich() {

		log.info("--------TC_03_Step_01_Click Thuc hien giao dich moi");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BTN_CONTINUE);

		log.info("-----------TC_03_Step_02_Hien thi man hinh dat ve ");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET));

		log.info("----------TC_03_Step_03_Click lich su dat ve");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.HISTORY_TICKET);

		log.info("-------------TC_03_Step_04_Verify ma thanh toan");
		verifyEquals(vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT), maThanhToan);

		log.info("------------TC_03_Step_05_lay thong tin ma ve");
		maVe = vehicalTicket.getTextDynamicFollowTextTable(DynamicPageUIs.DYNAMIC_TEXT, VehicalData.DATA_ORDER_TICKET.CODE_TICKET);

		log.info("---------TC_03_Step_06_Verify Brand of car");
		verifyEquals(hangXe, vehicalTicket.getTextDynamicFollowTextTable(DynamicPageUIs.DYNAMIC_TEXT, VehicalData.DATA_ORDER_TICKET.BRAND));

		log.info("--------TC_03_Step_07_Verify Tong tien");
		tongTien = tongTien.replace("VND", "đ");
		verifyEquals(tongTien, vehicalTicket.getTextDynamicFollowTextTable(DynamicPageUIs.DYNAMIC_TEXT, VehicalData.DATA_ORDER_TICKET.TOTAL_AMOUNT));

	}

	@Test
	public void TC_04_KiemTraBaocaogaodich() {
		log.info("-----------TC_04_Step_01_Click btn back ");
		vehicalTicket.clickToDynamicButtonBackByID("com.VCB:id/ivTitleLeft");

		log.info("-----------------TC_04_Step_02_Click btn back ");
		vehicalTicket.clickToDynamicButtonBackByID("com.VCB:id/ivTitleLeft");

		log.info("-------------TC_04_Step_03_Chuyen sang man hinh dat ve ");
		vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);

		log.info("------------TC_04_Step_04_Click btn menu");
		vehicalTicket.clickToDynamicBottomMenuOrIcon("com.VCB:id/menu_5");

		log.info("---------TC_04_Step_05_Click Chon muc bao cao giao dich");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TITLE_REPORT);

		log.info("----------TC_04_Step_06_Verify hien thi man hinh bao cao giao dich ");
		vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.TITLE_REPORT);

		log.info("----------TC_04_Step_06_Verify thong tin tai khoan nguon ");
		verifyEquals(taiKhoanNguon, vehicalTicket.getDynamicTextView("com.VCB:id/tvSelectAcc"));

		log.info("------------TC_04_Step_17: Chon tin kiem chuyen di");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_SEARCH);

		log.info("----------TC_04_Step_17: Verify ban ghi Thanh toan ");
		vehicalTicket.clickToDynamicTomorrow("com.VCB:id/llRoot");

		log.info("-----------TC_04_Step_18: Verofy hien thi man hinh chi tiet bao cao giao dich ");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.DETAIL_GD));

		log.info("---------TC_04_Step_19: Verify tai khoan trich no");
		verifyEquals(taiKhoanNguon, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.ACCOUNT));

		log.info("---------TC_04_Step_19: Verify ma thanh toan ");
		verifyEquals(maThanhToan, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT));

		log.info("TC_04_Step_19: Verify so lenh giao dich");
		verifyEquals(maGiaodich, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.NUMBER_ORDER));


		log.info("---------TC_04_Step_19: Verify so tien giao dich ");
		tongTien = tongTien.replace("đ", "VND");
		String tienSo = vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.AMOUNT);
		int indexTien = tienSo.indexOf("(");
		String subTienSo = tienSo.substring(0, indexTien - 1);
		verifyEquals(tongTien, subTienSo);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();

	}

}
