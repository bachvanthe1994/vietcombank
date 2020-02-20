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
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data;

public class Validation_TrainTicket_4 extends Base {
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
	}

	@Test
	public void TC_01_KiemTraNhomThongTinSapXep() {
		log.info("TC_17_Kiem tra icon sap xep, mac dinh o tab gio chay");
		verifyTrue(trainTicket.isDynamicHistoryIconDisplayed("Giờ chạy"));

		log.info("TC_17_Kiem tra icon sap xep, mac dinh o tab gio chay");
		verifyTrue(trainTicket.isDynamicHistoryIconDisplayed("Giờ chạy"));

		log.info("TC_21_Click tab Thoi gian chay");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Thời gian chạy");

		log.info("TC_17_Kiem tra icon sap xep Thời gian chạy ");
		verifyTrue(trainTicket.isDynamicHistoryIconDisplayed("Thời gian chạy"));
	}

	@Test
	public void TC_02_KiemTraNhanSapXepTheoGioChay() {
		log.info("TC_10_Lay danh sach gio chay");
		List<String> listSuggestIncrase = trainTicket.getListOfSuggestedMoneyOrListText("com.VCB:id/tv_tong_thoi_gian");

		log.info("TC_10_Verify gio chay sap xep theo thu tu tang dan");
		verifyTrue(trainTicket.orderSortIncrase(listSuggestIncrase));
		
		log.info("TC_21_Click tab Thoi gian chay lan nua de sap xep theo thu tu giam dan");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Thời gian chạy");
		
		log.info("TC_10_Lay danh sach gio chay");
		List<String> listSuggestDecrase = trainTicket.getListOfSuggestedMoneyOrListText("com.VCB:id/tv_tong_thoi_gian");

		log.info("TC_10_Verify gio chay sap xep theo thu tu giam dan");
		verifyTrue(trainTicket.orderSortDecrase(listSuggestDecrase));
	}
	
	//@Test
	public void TC_03_KiemTraNhanSapXepTheoGioKhoiHanh() {
		log.info("TC_10_Lay danh sach gio chay");
		List<String> listSuggestTime = trainTicket.getListOfSuggestedMoneyOrListText("com.VCB:id/tv_tong_thoi_gian");

		log.info("TC_10_Verify gio chay sap xep theo thu tu tang dan");
		verifyTrue(trainTicket.orderSortIncrase(listSuggestTime));

	}

}
