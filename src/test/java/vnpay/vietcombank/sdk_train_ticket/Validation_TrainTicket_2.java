package vnpay.vietcombank.sdk_train_ticket;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import pageObjects.sdk.trainTicket.TrainTicketPageObject;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;
import vietcombank_test_data.TrainTicket_Data;

public class Validation_TrainTicket_2 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TrainTicketPageObject trainTicket;
	List<String> listExpect;
	List<String> listActual;
	private String currentDay = getCurrentDay();
	private String currentMonth = getCurrenMonth();
	private String currentYear = getCurrentYear();

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
	public void TC_01_DoiGaKhoiHanhVaGaDen() {
		trainTicket = PageFactoryManager.getTrainTicketPageObject(driver);

		log.info("TC_01_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText( "Đặt vé tàu");

		log.info("TC_01_Step_Click close message");
		trainTicket.clickToDynamicButton( "Đồng ý");

		log.info("TC_01_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed( "ĐẶT VÉ TÀU"));

		log.info("TC_01_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd("ĐẶT VÉ TÀU", TrainTicket_Data.textDefault.TITLE_START);

		log.info("TC_01_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_START_END_VALID, "0");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText( TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_01_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "2");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Check text ga khoi hanh man hinh Dat ve tau truoc khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart( "com.VCB:id/tvTextPickUp"), TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_01_Check text ga den man hinh dat ve tau truoc khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart( "com.VCB:id/tvTextArrival"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Click button change");
		trainTicket.clickToDynamicIconChange(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Check text ga khoi hanh man hinh Dat ve tau sau khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart( "com.VCB:id/tvTextPickUp"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Check text ga den man hinh dat ve tau sau khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart( "com.VCB:id/tvTextArrival"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_02_CheckHienThiThoiGianKhoiHanh() {
		log.info("TC_02_Check hien thi ngay di la ngay hien tai");
		verifyEquals(trainTicket.getDynamicDateTime( "com.VCB:id/tv_ngay_di"), currentDay);

		log.info("TC_02_Check hien thi thang di la thang hien tai");
		verifyEquals(trainTicket.getDynamicDateTime( "com.VCB:id/tv_thang_di"), convertMonthFomatTH(currentMonth));

		log.info("TC_02_Check hien thi nam di la nam hien tai");
		verifyEquals(trainTicket.getDynamicDateTime( "com.VCB:id/tv_nam_di"), currentYear);

		log.info("TC_02_Check hien thi ngay ve la ngay di + 3");
		verifyEquals(Integer.parseInt(trainTicket.getDynamicDateTime( "com.VCB:id/tv_ngay_ve")), Integer.parseInt(currentDay) + 3);

		log.info("TC_02_Check hien thi thang ve la thang hien tai");
		verifyEquals(trainTicket.getDynamicDateTime( "com.VCB:id/tv_thang_ve"), convertMonthFomatTH(currentMonth));

		log.info("TC_02_Check hien thi nam ve la nam hien tai");
		verifyEquals(trainTicket.getDynamicDateTime( "com.VCB:id/tv_nam_ve"), currentYear);
	}

	@Test
	public void TC_03_FocusNgayDiNgayVe() {
		log.info("TC_03_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate( "com.VCB:id/tv_ngay_di");

		log.info("TC_03_verify lable");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed( "Chọn ngày"));

		log.info("TC_03_verify icon quay ve");
		verifyTrue(trainTicket.isDynamicBackIconDisplayed( "Chọn ngày"));

		log.info("TC_03_verify lable ngay di");
		verifyEquals(trainTicket.getTextTextViewByLinearLayoutID( "com.VCB:id/llNgayDi"), "Ngày đi");

		log.info("TC_03_verify lable ngay ve");
		verifyEquals(trainTicket.getTextTextViewByLinearLayoutID( "com.VCB:id/llNgayVe"), "Ngày về");
	}

	@Test
	public void TC_04_NhanIconBack() {
		log.info("TC_04_Click icon back cua man hinh chon ngay");
		trainTicket.clickToDynamicBackIcon( "Chọn ngày");

		log.info("TC_04_Verify man title man hinh dat ve tau");
		trainTicket.isDynamicMessageAndLabelTextDisplayed( "ĐẶT VÉ TÀU");
	}

	@Test
	public void TC_05_KiemTraTrangThaiButtonTiepTuc() {
		log.info("TC_05_Hien thi button tiep tuc");
		verifyTrue(trainTicket.isDynamicButtonDisplayed( "TIẾP TỤC"));
	}

	@Test
	public void TC_06_KiemTraHienThiChonNgayDi() {
		log.info("TC_06_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tv_ngay_di");

		log.info("TC_06_verify lable");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed( "Chọn ngày"));

		log.info("TC_06_verify lable ngay di");
		verifyEquals(trainTicket.getTextTextViewByLinearLayoutID( "com.VCB:id/llNgayDi"), "Ngày đi");

		log.info("TC_06_verify lable ngay ve");
		verifyEquals(trainTicket.getTextTextViewByLinearLayoutID( "com.VCB:id/llNgayVe"), "Ngày về");

		log.info("TC_06_Lich cho phep chon ngay di, chon ngay di la ngay hien tai");
		trainTicket.clickDynamicDateStartAndEnd( trainTicket.getCurentMonthAndYear(), currentDay);

		log.info("TC_06_Check ngay hien tai duoc check");
		verifyTrue(trainTicket.getSelectedAttributeOfDate(TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, trainTicket.getCurentMonthAndYear(), currentDay));

	}

	@Test
	public void TC_07_KiemTraNhanBack() {
		log.info("TC_07_Click icon back cua man hinh chon ngay");
		trainTicket.clickToDynamicBackIcon( "Chọn ngày");

		log.info("TC_07_Verify man title man hinh dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed( "ĐẶT VÉ TÀU"));

		log.info("TC_7_Verify ngay di ngay ve ban dau");
		verifyEquals(trainTicket.getDynamicDateTime( "com.VCB:id/tv_ngay_di"), currentDay);

		log.info("TC_07_Check hien thi thang di la thang hien tai");
		verifyEquals(trainTicket.getDynamicDateTime( "com.VCB:id/tv_thang_di"), convertMonthFomatTH(currentMonth));

		log.info("TC_07_Check hien thi nam di la nam hien tai");
		verifyEquals(trainTicket.getDynamicDateTime( "com.VCB:id/tv_nam_di"), currentYear);

		log.info("TC_07_Check hien thi ngay ve la ngay di + 3");
		verifyEquals(Integer.parseInt(trainTicket.getDynamicDateTime( "com.VCB:id/tv_ngay_ve")), Integer.parseInt(currentDay) + 3);

		log.info("TC_07_Check hien thi thang ve la thang hien tai");
		verifyEquals(trainTicket.getDynamicDateTime( "com.VCB:id/tv_thang_ve"), convertMonthFomatTH(currentMonth));

		log.info("TC_07_Check hien thi nam ve la nam hien tai");
		verifyEquals(trainTicket.getDynamicDateTime( "com.VCB:id/tv_nam_ve"), currentYear);
	}

	@Test
	public void TC_08_KiemTraNgayDiBiDisable() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, 6);
		String next6Month = "THÁNG " + (date.get(Calendar.MONTH) + 1) + " " + date.get(Calendar.YEAR);

		String next6MonthFormat = "Th" + (date.get(Calendar.MONTH) + 1) + ", " + date.get(Calendar.YEAR);
		String nextDay = getForWardDay(1);
		String backDay = getBackWardDay(1);

		log.info("TC_08_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tv_ngay_di");

		log.info("TC_08_Chon ngay di la ngay hien tai - 1");
		trainTicket.clickDynamicDateStartAndEnd( trainTicket.getCurentMonthAndYear(), backDay);

		String toastMessage = trainTicket.getToastMessage(driver);
		
		log.info("TC_08_Verify message thoi gian cho phep chon");
		verifyTrue(toastMessage.contains("Thời gian phải nằm trong khoảng " + currentDay + " " + convertMonthFomatTh(currentMonth) + ", " + currentYear + " đến " + currentDay + " " + next6MonthFormat + ". Vui lòng chọn lại."));

		log.info("TC_08_Check ngay qua khu bi disable");
		verifyFailure((trainTicket.getSelectedAttributeOfDate(TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, trainTicket.getCurentMonthAndYear(), backDay)));

		log.info("TC_08_Lich cho phep chon ngay di, chon ngay di lon hon ngay hien tai hon 6 thang");
		trainTicket.clickDynamicDateStartAndEnd( next6Month, nextDay);

		verifyTrue(toastMessage.contains("Thời gian phải nằm trong khoảng " + currentDay + " " + convertMonthFomatTh(currentMonth) + ", " + currentYear + " đến " + currentDay + " " + next6MonthFormat + ". Vui lòng chọn lại."));

		log.info("TC_08_Check ngay tuong lai cach ngay hien tai hon 6 thang disable");
		verifyFailure((trainTicket.getSelectedAttributeOfDate(TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, next6Month, nextDay)));
	}

	@Test
	public void TC_09_KiemTraNgayDiHopLe() {
		log.info("TC_09_Click icon back cua man hinh chon ngay");
		trainTicket.clickToDynamicBackIcon( "Chọn ngày");

		log.info("TC_09_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate( "com.VCB:id/tv_ngay_di");

		log.info("TC_09_Chon ngay di");
		trainTicket.clickDynamicDateStartAndEnd( trainTicket.getCurentMonthAndYear(), Integer.parseInt(currentDay) + 5 + "");

		log.info("TC_09_Ngay ve se bi remove, se thay bang title Chon ngay");
		verifyEquals(trainTicket.getDynamicTitleSelectDate( "Ngày về"), "Chọn ngày");
	}

	@Test
	public void TC_10_KiemTraChoPhepChonNgayVe() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, 6);
		String next6Month = "THÁNG " + (date.get(Calendar.MONTH) + 1) + " " + date.get(Calendar.YEAR);
		String next6MonthFormat = "Th" + (date.get(Calendar.MONTH) + 1) + ", " + date.get(Calendar.YEAR);
		String nextDay = getForWardDay(1);
		String backDay = getBackWardDay(1);

		log.info("TC_10_Chon ngay ve la ngay hien tai - 1");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getCurentMonthAndYear(), backDay);

		String toastMessage = trainTicket.getToastMessage(driver);
		log.info("TC_10_Hien thi message");
		verifyTrue(toastMessage.contains("Thời gian phải nằm trong khoảng " + currentDay + " " + convertMonthFomatTh(currentMonth) + ", " + currentYear + " đến " + currentDay + " " + next6MonthFormat + ". Vui lòng chọn lại."));

		log.info("TC_10_Check ngay ve nho hon ngay hien tai bi disable");
		verifyFailure((trainTicket.getSelectedAttributeOfDate(TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, trainTicket.getCurentMonthAndYear(), backDay)));

		log.info("TC_10_Lich cho phep chon ngay di, chon ngay di lon hon ngay hien tai hon 6 thang");
		trainTicket.clickDynamicDateStartAndEnd( next6Month, nextDay);

		log.info("TC_10_Check ngay ve lon hon ngay hien tai hon 6 thang");
		verifyTrue(toastMessage.contains("Thời gian phải nằm trong khoảng " + currentDay + " " + convertMonthFomatTh(currentMonth) + ", " + currentYear + " đến " + currentDay + " " + next6MonthFormat + ". Vui lòng chọn lại."));

		log.info("TC_10_Check ngay ve lon hon ngay hien tai hon 6 thang");
		verifyFailure((trainTicket.getSelectedAttributeOfDate(TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, next6Month, nextDay)));
	}

	@Test
	public void TC_11_KiemTraRangBuocNgayDiNgayVe() {
		log.info("TC_11_Click icon back cua man hinh chon ngay");
		trainTicket.clickToDynamicBackIcon( "Chọn ngày");

		log.info("TC_11_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate( "com.VCB:id/tv_ngay_di");

		log.info("TC_11_Chon ngay di");
		trainTicket.clickDynamicDateStartAndEnd( trainTicket.getCurentMonthAndYear(), Integer.parseInt(currentDay) + 5 + "");

		log.info("TC_11_Chon ngay ve nho hon ngay di, chuong trinh khong chon duoc ngay ve hien thi ngay di");
		trainTicket.clickDynamicDateStartAndEnd( trainTicket.getCurentMonthAndYear(), Integer.parseInt(currentDay) + 4 + "");

		log.info("TC_11_Ngay ve se bi remove, se thay bang title Chon ngay");
		verifyEquals(trainTicket.getDynamicTitleSelectDate( "Ngày về"), "Chọn ngày");

		log.info("TC_11_Chon ngay ve  = ngay di");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getCurentMonthAndYear(), Integer.parseInt(currentDay) + 4 + "");

		log.info("TC_11_Check hien thi button xac nhan");
		verifyTrue(trainTicket.isDynamicButtonDisplayed( "Tiếp tục"));
	}

	@Test
	public void TC_12_KiemTraButtonTiepTucEnable() {
		log.info("TC_12_Check hien thi button xac nhan");
		trainTicket.isDynamicButtonDisplayed( "Tiếp tục");

		log.info("TC_12_Step_Click button tiep tuc");
		trainTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_12_Click button tiep tuc, quay ve man hinh dat ve tau de chon lai ga khoi hanh");
		trainTicket.isDynamicMessageAndLabelTextDisplayed( "ĐẶT VÉ TÀU");
	}

	@Test
	public void TC_13_KiemTraNgayVeLonHonHoacBangNgayDi() {
		LocalDate now = LocalDate.now();

		LocalDate dateStart = now.plusDays(1);
		LocalDate dateEnd = now.plusDays(2);
		log.info("TC_13_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate( "com.VCB:id/tv_ngay_di");

		log.info("TC_13_Chon ngay di");
		trainTicket.clickDynamicDateStartAndEnd( trainTicket.getCurentMonthAndYear(), Integer.parseInt(currentDay) + 1 + "");

		log.info("TC_13_Chon ngay ve");
		trainTicket.clickDynamicDateStartAndEnd( trainTicket.getCurentMonthAndYear(), Integer.parseInt(currentDay) + 2 + "");

		log.info("TC_13_Verify gia tri ngay di");

		verifyEquals(trainTicket.getTextInDynamicDateTicket( trainTicket.getCurentMonthAndYear(), Integer.parseInt(currentDay) + 1 + ""), Integer.parseInt(currentDay) + 1 + "");

		log.info("TC_13_Verify gia tri thu di");
		verifyEquals(trainTicket.getDynamicTitleWeek("Ngày đi", "com.VCB:id/tv_thang_di"), convertDayOfWeekVietNameseFull(getCurrentDayOfWeek(dateStart)));

		log.info("TC_13_Verify gia tri thang nam di");
		verifyEquals(trainTicket.getDynamicTitleWeek( "Ngày đi", "com.VCB:id/tv_nam_di"), trainTicket.getMonthAndYearFORMAT());

		log.info("TC_13_Verify gia tri ngay ve");
		verifyEquals(trainTicket.getTextInDynamicDateTicket( trainTicket.getCurentMonthAndYear(), Integer.parseInt(currentDay) + 2 + ""), Integer.parseInt(currentDay) + 2 + "");

		log.info("TC_13_Verify gia tri thu ve");
		verifyEquals(trainTicket.getDynamicTitleWeek( "Ngày về", "com.VCB:id/tv_thang_ve"), convertDayOfWeekVietNameseFull(getCurrentDayOfWeek(dateEnd)));

		log.info("TC_13_Verify gia tri thang nam ve");
		verifyEquals(trainTicket.getDynamicTitleWeek( "Ngày về", "com.VCB:id/tv_nam_ve"), trainTicket.getMonthAndYearFORMAT());
	}

	@Test
	public void TC_14_KiemTraChonLaiNgayDiHoacNgayVe() {
		LocalDate now = LocalDate.now();

		LocalDate dateStart = now.plusDays(3);
		LocalDate dateEnd = now.plusDays(4);

		log.info("TC_14_Chon ngay di");
		trainTicket.clickDynamicDateStartAndEnd( trainTicket.getCurentMonthAndYear(), Integer.parseInt(currentDay) + 3 + "");

		log.info("TC_14_Verify gia tri ngay di");

		verifyEquals(trainTicket.getTextInDynamicDateTicket( trainTicket.getCurentMonthAndYear(), Integer.parseInt(currentDay) + 3 + ""), Integer.parseInt(currentDay) + 3 + "");

		log.info("TC_14_Verify gia tri thu di");
		verifyEquals(trainTicket.getDynamicTitleWeek( "Ngày đi", "com.VCB:id/tv_thang_di"), convertDayOfWeekVietNameseFull(getCurrentDayOfWeek(dateStart)));

		log.info("TC_14_Verify gia tri thang nam di");
		verifyEquals(trainTicket.getDynamicTitleWeek( "Ngày đi", "com.VCB:id/tv_nam_di"), trainTicket.getMonthAndYearFORMAT());

		log.info("TC_14_Ngay ve se bi remove, se thay bang title Chon ngay");
		verifyEquals(trainTicket.getDynamicTitleSelectDate( "Ngày về"), "Chọn ngày");

		log.info("TC_14_Chon ngay ve nho hon ngay di vua chon");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getCurentMonthAndYear(), Integer.parseInt(currentDay) + 2 + "");

		log.info("TC_14_Ngay ve se bi remove, se thay bang title Chon ngay");
		verifyEquals(trainTicket.getDynamicTitleSelectDate( "Ngày về"), "Chọn ngày");

		log.info("TC_14_Chon ngay ve lon hon ngay di vua chon");
		trainTicket.clickDynamicDateStartAndEnd( trainTicket.getCurentMonthAndYear(), Integer.parseInt(currentDay) + 4 + "");

		log.info("TC_14_Verify gia tri ngay ve");
		verifyEquals(trainTicket.getTextInDynamicDateTicket(trainTicket.getCurentMonthAndYear(), Integer.parseInt(currentDay) + 4 + ""), Integer.parseInt(currentDay) + 4 + "");

		log.info("TC_14_Verify gia tri thu ve");
		verifyEquals(trainTicket.getDynamicTitleWeek( "Ngày về", "com.VCB:id/tv_thang_ve"), convertDayOfWeekVietNameseFull(getCurrentDayOfWeek(dateEnd)));

		log.info("TC_14_Verify gia tri thang nam ve");
		verifyEquals(trainTicket.getDynamicTitleWeek( "Ngày về", "com.VCB:id/tv_nam_ve"), trainTicket.getMonthAndYearFORMAT());

		log.info("TC_14_Step_Click button tiep tuc");
		trainTicket.clickToDynamicButton("Tiếp tục");
	}

	@Test
	public void TC_15_KiemTraHienThiMacDinhComboboxKhachHang() {
		log.info("TC_15_Step_Verify khong hien thi so luong hanh khach");
		verifyTrue(trainTicket.isDynamicTextNumberCustomerUnDisplayed( "Hành khách"));
	}

	@Test
	public void TC_16_BoTrongKhongChonSoLuongHanhKhach() {
		log.info("TC_16_Click chon loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText( "Loại chỗ");

		log.info("TC_16_Click chon loai option");
		trainTicket.clickDynamicRadioSelectType( "Chọn tất cả");

		log.info("TC_16_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText( "Xong");

		log.info("TC_16_Step_Click button tiep tuc");
		trainTicket.clickToDynamicButton("TIẾP TỤC");

		verifyEquals(trainTicket.getDynamicMessageInvalid( "Thông báo"), TrainTicket_Data.message.LOCATION_BLANK);

		log.info("TC_16_Step_Click button tiep tuc");
		trainTicket.clickToDynamicButton("ĐỒNG Ý");
	}

	@Test
	public void TC_17_FocusComboboxHanhKhach() {
		log.info("TC_17_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText( "Hành khách");

		log.info("TC_17_Hien thi icon x");
		verifyTrue(trainTicket.isDynamicImageSuccess( "Số lượng hành khách"));

		log.info("TC_17_Hien thi button xong");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed( "Xong"));

		log.info("TC_17_Hien thi nguoi lon");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed( "Người lớn"));

		log.info("TC_17_Hien thi button giam");
		verifyTrue(trainTicket.isDynamicIconChangeNumber( "Người lớn", "com.VCB:id/ivDecrase"));

		log.info("TC_17_Hien thi so luong nguoi mac dinh la 0");
		verifyTrue(trainTicket.isDynamicTextChangeNumber( "Người lớn", "com.VCB:id/tvQuantity"));

		log.info("TC_17_Hien thi button tang");
		verifyTrue(trainTicket.isDynamicIconChangeNumber( "Người lớn", "com.VCB:id/ivIncrase"));

		log.info("TC_17_Hien thi tre em");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed( "Trẻ em"));

		log.info("TC_17_Hien thi button giam");
		verifyTrue(trainTicket.isDynamicIconChangeNumber("Trẻ em", "com.VCB:id/ivDecrase"));

		log.info("TC_17_Hien thi so luong nguoi mac dinh la 0");
		verifyTrue(trainTicket.isDynamicTextChangeNumber( "Trẻ em", "com.VCB:id/tvQuantity"));

		log.info("TC_17_Hien thi so luong tre");
		verifyEquals(trainTicket.getDynamicTextOld("Trẻ em"), "0-10 tuổi");

		log.info("TC_17_Hien thi button tang");
		verifyTrue(trainTicket.isDynamicIconChangeNumber( "Trẻ em", "com.VCB:id/ivIncrase"));

		log.info("TC_17_Hien thi sinh vien");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Sinh viên"));

		log.info("TC_17_Hien thi button giam");
		verifyTrue(trainTicket.isDynamicIconChangeNumber( "Sinh viên", "com.VCB:id/ivDecrase"));

		log.info("TC_17_Hien thi so luong nguoi mac dinh la 0");
		verifyTrue(trainTicket.isDynamicTextChangeNumber( "Sinh viên", "com.VCB:id/tvQuantity"));

		log.info("TC_17_Hien thi button tang");
		verifyTrue(trainTicket.isDynamicIconChangeNumber( "Sinh viên", "com.VCB:id/ivIncrase"));

		log.info("TC_17_Hien thi Người cao tuổi");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed( "Người cao tuổi"));

		log.info("TC_17_Hien thi tu 60 tuoi");
		verifyEquals(trainTicket.getDynamicTextOld( "Người cao tuổi"), "Từ 60 tuổi");

		log.info("TC_17_Hien thi button giam");
		verifyTrue(trainTicket.isDynamicIconChangeNumber( "Người cao tuổi", "com.VCB:id/ivDecrase"));

		log.info("TC_17_Hien thi so luong nguoi mac dinh la 0");
		verifyTrue(trainTicket.isDynamicTextChangeNumber( "Người cao tuổi", "com.VCB:id/tvQuantity"));

		log.info("TC_17_Hien thi button tang");
		verifyTrue(trainTicket.isDynamicIconChangeNumber( "Người cao tuổi", "com.VCB:id/ivIncrase"));

		log.info("TC_17_Hien thi title note");
		verifyEquals(trainTicket.getTextInDynamicNote( "4"), TrainTicket_Data.message.NOTE_NUMBER_CUSTOMER);
	}
	
	@Test
	public void TC_18_KiemTraThayDoiSoLuonNguoiLon() {
		log.info("TC_17_Hien thi so luong nguoi mac dinh la 0");
		verifyTrue(trainTicket.isDynamicTextChangeNumber( "Người lớn", "com.VCB:id/tvQuantity"));
		
		log.info("TC_17_Click tang so luong len 1 don vi");
		verifyTrue(trainTicket.isDynamicIconChangeNumber("Người lớn", "com.VCB:id/ivIncrase"));
	}
}