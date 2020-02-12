package vnpay.vietcombank.SDK_train_ticket;

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
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, "Đặt vé tàu");

		log.info("TC_01_Step_Click close message");
		login.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_01_Check title dat ve tau");
		trainTicket.isDynamicMessageAndLabelTextDisplayed(driver, "ĐẶT VÉ TÀU");

		log.info("TC_06_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd(driver, "ĐẶT VÉ TÀU", TrainTicket_Data.textDefault.TITLE_START);

		log.info("TC_06_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_START_END_VALID, "0");

		log.info("TC_06_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_06_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "2");

		log.info("TC_06_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Check text ga khoi hanh man hinh Dat ve tau truoc khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart(driver, "com.VCB:id/tvTextPickUp"), TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_01_Check text ga den man hinh dat ve tau truoc khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart(driver, "com.VCB:id/tvTextArrival"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Click button change");
		trainTicket.clickToDynamicIconChange(driver, TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Check text ga khoi hanh man hinh Dat ve tau sau khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart(driver, "com.VCB:id/tvTextPickUp"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Check text ga den man hinh dat ve tau sau khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart(driver, "com.VCB:id/tvTextArrival"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

//@Test
	// Chưa check được với ngày cuối tháng
	public void TC_02_CheckHienThiThoiGianKhoiHanh() {
		log.info("TC_02_Check hien thi ngay di la ngay hien tai");
		verifyEquals(trainTicket.getDynamicDateTime(driver, "com.VCB:id/tv_ngay_di"), currentDay);

		log.info("TC_02_Check hien thi thang di la thang hien tai");
		verifyEquals(trainTicket.getDynamicDateTime(driver, "com.VCB:id/tv_thang_di"), convertMonFomatTH(currentMonth));

		log.info("TC_02_Check hien thi nam di la nam hien tai");
		verifyEquals(trainTicket.getDynamicDateTime(driver, "com.VCB:id/tv_nam_di"), currentYear);

		log.info("TC_02_Check hien thi ngay ve la ngay di + 3");
		verifyEquals(Integer.parseInt(trainTicket.getDynamicDateTime(driver, "com.VCB:id/tv_ngay_ve")), Integer.parseInt(currentDay) + 3);

		log.info("TC_02_Check hien thi thang ve la thang hien tai");
		verifyEquals(trainTicket.getDynamicDateTime(driver, "com.VCB:id/tv_thang_ve"), convertMonFomatTH(currentMonth));

		log.info("TC_02_Check hien thi nam ve la nam hien tai");
		verifyEquals(trainTicket.getDynamicDateTime(driver, "com.VCB:id/tv_nam_ve"), currentYear);
	}

	// @Test
	public void TC_03_FocusNgayDiNgayVe() {
		log.info("TC_03_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate(driver, "com.VCB:id/tv_ngay_di");

		log.info("TC_03_verify lable");
		trainTicket.isDynamicMessageAndLabelTextDisplayed(driver, "Chọn ngày");

		log.info("TC_03_verify icon quay ve");
		trainTicket.isDynamicBackIconDisplayed(driver, "Chọn ngày");

		log.info("TC_03_verify lable ngay di");
		verifyEquals(trainTicket.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llNgayDi"), "Ngày đi");

		log.info("TC_03_verify lable ngay ve");
		verifyEquals(trainTicket.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llNgayVe"), "Ngày về");
	}

	//@Test
	public void TC_04_NhanIconBack() {
		log.info("TC_03_Click icon back cua man hinh chon ngay");
		trainTicket.clickToDynamicBackIcon(driver, "Chọn ngày");

		log.info("TC_03_Verify man title man hinh dat ve tau");
		trainTicket.isDynamicMessageAndLabelTextDisplayed(driver, "ĐẶT VÉ TÀU");
	}

	@Test
	public void TC_05_KiemTraTrangThaiButtonTiepTuc() {
		log.info("TC_03_Hien thi button tiep tuc");
		trainTicket.isDynamicButtonDisplayed(driver, "TIẾP TỤC");
	}

	//@Test
	public void TC_06_KiemTraHienThiChonNgayDi() {
		log.info("TC_03_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate(driver, "com.VCB:id/tv_ngay_di");

		log.info("TC_03_verify lable");
		trainTicket.isDynamicMessageAndLabelTextDisplayed(driver, "Chọn ngày");

		log.info("TC_03_verify lable ngay di");
		verifyEquals(trainTicket.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llNgayDi"), "Ngày đi");

		log.info("TC_03_verify lable ngay ve");
		verifyEquals(trainTicket.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llNgayVe"), "Ngày về");

		log.info("TC_03_Lich cho phep chon ngay di, chon ngay di la ngay hien tai");
		trainTicket.clickDynamicDateStartAndEnd(driver, currentDay);

		log.info("TC_03_Check ngay hien tai duoc check");
		verifyTrue(trainTicket.getSelectedAttributeOfDate(TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, trainTicket.getTextInDynamicDateTicket(driver, currentDay)));
	}
	
	@Test
	public void TC_07_KiemTraNhanBack() {
		log.info("TC_03_Click icon back cua man hinh chon ngay");
		trainTicket.clickToDynamicBackIcon(driver, "Chọn ngày");

		log.info("TC_03_Verify man title man hinh dat ve tau");
		trainTicket.isDynamicMessageAndLabelTextDisplayed(driver, "ĐẶT VÉ TÀU");
		
		log.info("TC_03_Verify ngay di ngay ve ban dau");
		verifyEquals(trainTicket.getDynamicDateTime(driver, "com.VCB:id/tv_ngay_di"), currentDay);

		log.info("TC_02_Check hien thi thang di la thang hien tai");
		verifyEquals(trainTicket.getDynamicDateTime(driver, "com.VCB:id/tv_thang_di"), convertMonFomatTH(currentMonth));

		log.info("TC_02_Check hien thi nam di la nam hien tai");
		verifyEquals(trainTicket.getDynamicDateTime(driver, "com.VCB:id/tv_nam_di"), currentYear);

		log.info("TC_02_Check hien thi ngay ve la ngay di + 3");
		verifyEquals(Integer.parseInt(trainTicket.getDynamicDateTime(driver, "com.VCB:id/tv_ngay_ve")),Integer.parseInt(currentDay) + 3);

		log.info("TC_02_Check hien thi thang ve la thang hien tai");
		verifyEquals(trainTicket.getDynamicDateTime(driver, "com.VCB:id/tv_thang_ve"), convertMonFomatTH(currentMonth));

		log.info("TC_02_Check hien thi nam ve la nam hien tai");
		verifyEquals(trainTicket.getDynamicDateTime(driver, "com.VCB:id/tv_nam_ve"), currentYear);
	}
	
	@Test
	public void TC_08_KiemTraNgayDiBiDisable() {
		log.info("TC_03_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate(driver, "com.VCB:id/tv_ngay_di");
	
		log.info("TC_03_ngay hien tai -1");
		String pastDate =Integer.toString( Integer.parseInt(currentDay) -1);
		
		log.info("TC_03_Chon ngay di la ngay hien tai - 1");
		trainTicket.clickDynamicDateStartAndEnd(driver,pastDate );
		
		log.info("TC_03_Check ngay hien tai duoc check");
		verifyFailure(trainTicket.getSelectedAttributeOfDate(TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, trainTicket.getTextInDynamicDateTicket(driver, pastDate)));
		
		
		
		log.info("TC_03_Verify text message");
		verifyEquals(trainTicket.getDynamicMessageInvalid(driver, "1"), "Thời gian phải nằm trong khoảng "+currentDay+" "+ convertMonFomatTh(currentMonth)+", "+currentYear+" đến "+currentDay+" "+convertMonFomatTh(Integer.parseInt(currentMonth)+6+"")+", "+currentYear+". Vui lòng chọn lại.");
	}
}
