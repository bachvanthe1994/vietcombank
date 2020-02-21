package vnpay.vietcombank.sdk_train_ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import vietcombankUI.DynamicPageUIs;
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data;

public class Validation_TrainTicket_5 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TrainTicketPageObject trainTicket;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		trainTicket = PageFactoryManager.getTrainTicketPageObject(driver);
		String startDay = getForWardDay(5);
		String EndDay = getForWardDay(7);
		log.info("TC_01_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Đặt vé tàu");

		log.info("TC_01_Step_Click close message");
		trainTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_01_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("ĐẶT VÉ TÀU"));

		log.info("TC_01_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd("ĐẶT VÉ TÀU", "com.VCB:id/tvTextPickUp");

		log.info("TC_01_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/linPickUp");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/linArival");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_08_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tv_ngay_di");

		log.info("TC_08_Chon ngay di la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getCurentMonthAndYear(), startDay);

		log.info("TC_08_Chon ngay ve la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getCurentMonthAndYear(), EndDay);

		log.info("TC_12_Click button tiep tuc");
		trainTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_17_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Hành khách");

		log.info("TC_21_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_21_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivIncrase");

		log.info("TC_21_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_17_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_17_Click radio chon tat ca");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Chọn tất cả");

		log.info("TC_21_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_12_Click button tiep tuc");
		trainTicket.clickToDynamicButton("TIẾP TỤC");
		
		log.info("TC_12_Click button sua");
		trainTicket.clickToDynamicButton("Sửa");
		
		log.info("TC_24_Click khoi hanh");
		trainTicket.clickToDynamicSelectDate( "com.VCB:id/tvTextPickUp");
		
		log.info("TC_01_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/linPickUp");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);
	}


	@Test
	// Lỗi app vẫn cho phép nhập hơn 100 ký tự
	public void TC_01_NhapToiDaKyTuTruongGaden() {
		log.info("TC_18_Nhap text ga den 101 ky tu vuot qua gioi han cho phep");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_MAX_INVALID, "com.VCB:id/linArival");
	
		log.info("TC_18_Verify text nhap vao toi da");
		verifyEquals(trainTicket.getTextMaxLength("com.VCB:id/linArival"), TrainTicket_Data.inputText.POINT_MAX_INVALID.substring(0, 208));
	}

	@Test
	public void TC_02_NhapKyTuDacBietVaKyTuKhacTruongGaDen() {
		log.info("TC_19_Nhap text ky tu dac biet");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_SPECIAL_INVALID, "com.VCB:id/linArival");

		log.info("TC_19_Verify lay gia tri text rong");
		verifyEquals(trainTicket.getDynamicInputPoint( "com.VCB:id/linArival"), TrainTicket_Data.textDefault.TITLE_END);

		log.info("TC_19_Nhap text ky tu so tu 0 den 9");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_NUMBER_VALID, "com.VCB:id/linArival");

		log.info("TC_19_Verify lay gia tri ga den vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint( "com.VCB:id/linArival"), TrainTicket_Data.inputText.POINT_NUMBER_VALID);

		log.info("TC_19_Nhap text ky tu chu thuong va chu hoa");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_CHAR_VALID, "com.VCB:id/linArival");

		log.info("TC_19_Verify lay gia tri ga den vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint( "com.VCB:id/linArival"), TrainTicket_Data.inputText.POINT_CHAR_VALID);

		log.info("TC_019_Nhap text ky tu tieng viet co dau");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_VIETNAM_VALID, "com.VCB:id/linArival");

		log.info("TC_19_Verify lay gia tri ga den vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint( "com.VCB:id/linArival"), TrainTicket_Data.inputText.POINT_VIETNAM_VALID);
	}

	@Test
	public void TC_03_NhapKyTimKiemGanDungGaDen() {
		log.info("TC_20_Nhap ky tu vao o Search");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_SERACH_THE_SAME, "com.VCB:id/linArival");

		List<String> listSuggestPoint = trainTicket.getListOfSuggestedMoneyOrListText( "com.VCB:id/tvTen");

		log.info("TC_20_Kiem tra hien thi ket qua goi y");
		verifyTrue(trainTicket.checkSuggestPoint(listSuggestPoint, "Hai"));
	}

	@Test
	public void TC_04_NhapGaDenHopLe() {
		log.info("TC_21_Nhap text ga den co trong danh sach");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_START_END_VALID, "com.VCB:id/linArival");

		log.info("TC_21_Verify lay gia tri ga den vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint( "com.VCB:id/linArival"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_05_ChonMotGaDenHopLe() {
		log.info("TC_22_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText( TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_22_Check text ga den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tvTextArrival"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_06_KiemTraGiaTriNhapVaoGaDen() {
		log.info("TC_23_Check text ga den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tvTextArrival"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_07_KiemTraEditGaDen() {
		log.info("TC_24_Click ga den");
		trainTicket.clickToDynamicSelectDate( "com.VCB:id/tvTextArrival");

		log.info("TC_24_Nhap text ga den co trong danh sach");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/linArival");

		log.info("TC_24_Verify lay gia tri ga den vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint("com.VCB:id/linArival"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);
	}

	@Test
	public void TC_08_TimKiemGaDenKhongTonTai() {
		log.info("TC_25_Nhap text ga den khong co trong danh sach");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_NUMBER_VALID, "com.VCB:id/linArival");

		log.info("TC_25_verify message khong co du lieu");
		verifyEquals(trainTicket.getDynamicTextInPopUp( "com.VCB:id/vtWarning"), "Không có dữ liệu hợp lệ");
	}


}


