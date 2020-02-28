package vnpay.vietcombank.sdk_train_ticket;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.AfterClass;
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
		
		log.info("TC_00_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Đặt vé tàu");

		log.info("TC_00_Step_Click close message");
		trainTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_00_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("ĐẶT VÉ TÀU"));

		log.info("TC_00_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd("ĐẶT VÉ TÀU", "com.VCB:id/tvTextPickUp");

		log.info("TC_00_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/linPickUp");

		log.info("TC_00_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_00_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/linArival");

		log.info("TC_00_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_00_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tv_ngay_di");

		String nextDay = getForWardDay(5);
		String EndDay = getForWardDay(7);

		log.info("TC_00_Chon ngay di la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(5), nextDay);

		log.info("TC_00_Chon ngay ve la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(7), EndDay);

		log.info("TC_00_Click button tiep tuc");
		trainTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_00_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Hành khách");

		log.info("TC_00_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_00_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivIncrase");

		log.info("TC_00_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_00_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_00_Click radio chon tat ca");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Chọn tất cả");

		log.info("TC_00_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_00_Click button tiep tuc");
		trainTicket.clickToDynamicButton("TIẾP TỤC");
	}

	@Test
	public void TC_01_KiemTraNhomThongTinSapXep() {
		log.info("TC_01_Kiem tra icon sap xep, mac dinh o tab gio chay");
		verifyTrue(trainTicket.isDynamicHistoryIconDisplayed("Giờ chạy"));

		log.info("TC_01_Kiem tra icon sap xep, mac dinh o tab gio chay");
		verifyTrue(trainTicket.isDynamicHistoryIconDisplayed("Giờ chạy"));

		log.info("TC_01_Click tab Thoi gian chay");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Thời gian chạy");

		log.info("TC_01_Kiem tra icon sap xep Thời gian chạy ");
		verifyTrue(trainTicket.isDynamicHistoryIconDisplayed("Thời gian chạy"));
	}

	@Test
	public void TC_02_KiemTraNhanSapXepTheoGioChay() {
		log.info("TC_02_Lay danh sach gio chay");
		List<String> listSuggestIncrase = trainTicket.getListOfSuggestedMoneyOrListText("com.VCB:id/tv_tong_thoi_gian");

		log.info("TC_02_Verify gio chay sap xep theo thu tu tang dan");
		verifyTrue(trainTicket.orderSortIncrase(listSuggestIncrase));

		log.info("TC_02_Click tab Thoi gian chay lan nua de sap xep theo thu tu giam dan");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Thời gian chạy");

		log.info("TC_02_Lay danh sach gio chay");
		List<String> listSuggestDecrase = trainTicket.getListOfSuggestedMoneyOrListText("com.VCB:id/tv_tong_thoi_gian");

		log.info("TC_02_Verify gio chay sap xep theo thu tu giam dan");
		verifyTrue(trainTicket.orderSortDecrase(listSuggestDecrase));
	}

	@Test
	public void TC_03_KiemTraNhanSapXepTheoGioKhoiHanh() {
		log.info("TC_03_Click tab gio chay");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Giờ chạy");

		log.info("TC_03_Lay danh sach gio chay");
		List<String> listSuggestDecrase = trainTicket.getListOfSuggestedMoneyOrListText("com.VCB:id/tv_thoi_di");

		log.info("TC_03_Verify gio chay sap xep theo thu tu giam dan");
		verifyTrue(trainTicket.orderSortDecraseFormat(listSuggestDecrase));

		log.info("TC_03_Click tab gio chay");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Giờ chạy");

		log.info("TC_03_Lay danh sach gio chay");
		List<String> listSuggestIncrase = trainTicket.getListOfSuggestedMoneyOrListText("com.VCB:id/tv_thoi_di");

		log.info("TC_03_Verify gio chay sap xep theo thu tu tang dan");
		verifyTrue(trainTicket.orderSortIncraseFormat(listSuggestIncrase));
	}

	@Test
	public void TC_04_KiemTraKetHopLonHon1TieuChiSapXep() {
		log.info("TC_04_Lay danh sach gio chay");
		List<String> listSuggestIncrase = trainTicket.getListOfSuggestedMoneyOrListText("com.VCB:id/tv_tong_thoi_gian");

		log.info("TC_04_Verify gio chay khong sap xep theo thu tu tang dan hoac giam dan");
		verifyFailure(trainTicket.orderSortIncrase(listSuggestIncrase));

		log.info("TC_04_Verify chi co thoi gian di duoc sap xep theo thu tu tang dan");
		List<String> listSuggestTime = trainTicket.getListOfSuggestedMoneyOrListText("com.VCB:id/tv_thoi_di");
		verifyTrue(trainTicket.orderSortIncraseFormat(listSuggestTime));

	}

	@Test
	public void TC_05_KiemTraNhomThongTinMotChuyenDi() {

	}

	@Test
	public void TC_06_KiemTraThaoTacChonMotChuyenDi() {
		log.info("TC_06_Click chon mot chuyen di");
		trainTicket.clickDynamicSelectTrain("com.VCB:id/tv_ten_tau", "0");

		log.info("TC_06_Check hien thi button tiep tuc chon chieu ve");
		verifyTrue(trainTicket.isDynamicButtonDisplayed("TIẾP TỤC CHỌN CHIỀU VỀ"));

		log.info("TC_06_Step_Check button tiep tuc enable");
		verifyTrue(trainTicket.isControlEnabled(driver, DynamicPageUIs.DYNAMIC_BUTTON, "TIẾP TỤC CHỌN CHIỀU VỀ"));
	}

	@Test
	public void TC_07_KiemTraNhanNutSua() {
		log.info("TC_07_Click button sua");
		trainTicket.clickToDynamicButton("Sửa");

		log.info("TC_07_verify title sua thong tin");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tvTitle1"), "Sửa thông tin tìm kiếm");

		log.info("TC_07_Hien thi button back");
		verifyTrue(trainTicket.isDynamicRadioDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_07_Check text ga khoi hanh man hinh Dat ve tau truoc khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart("com.VCB:id/tvTextPickUp"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_07_Check text ga den man hinh dat ve tau truoc khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart("com.VCB:id/tvTextArrival"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_07_Hien thi button chuyen doi");
		verifyTrue(trainTicket.isDynamicRadioDisplayed("com.VCB:id/ivround"));

		log.info("TC_07_Check hien thi icon 1 chieu");
		verifyTrue(trainTicket.isDynamicHistoryIconDisplayed("Một chiều"));

		log.info("TC_07_Check hien thi icon khu hoi");
		verifyTrue(trainTicket.isDynamicHistoryIconDisplayed("Khứ hồi"));

		log.info("TC_07_Check hien thi ngay di");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Ngày đi"));

		log.info("TC_07_Check hien thi ngay ve");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Ngày về"));

		log.info("TC_07_Check hien thi combobox hanh khach");
		verifyTrue(trainTicket.isDynamicComboboxDisplayed("Hành khách"));

		log.info("TC_07_Check hien thi combobox loai cho");
		verifyTrue(trainTicket.isDynamicComboboxDisplayed("Loại chỗ"));

		log.info("TC_07_Check hien thi button tiep tuc");
		verifyTrue(trainTicket.isDynamicButtonDisplayed("ÁP DỤNG"));
	}

	@Test
	public void TC_08_KiemTraThongTinGaDenVaGaDi() {
		log.info("TC_08_Check text ga khoi hanh man hinh Dat ve tau truoc khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart("com.VCB:id/tvTextPickUp"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_08_Check text ga den man hinh dat ve tau truoc khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart("com.VCB:id/tvTextArrival"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);
	}

	@Test
	// Lỗi app vẫn cho phép nhập hơn 100 ký tự
	public void TC_09_NhapToiDaKyTuTruongGaKhoiHanh() {
		log.info("TC_09_Click ga khoi hanh");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tvTextPickUp");

		log.info("TC_09_Nhap text ga khoi hanh 101 ky tu vuot qua gioi han cho phep");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_MAX_INVALID, "com.VCB:id/linPickUp");

		log.info("TC_09_Verify text nhap vao toi da");
		verifyEquals(trainTicket.getTextMaxLength("com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_MAX_INVALID.substring(0, 200));
	}

	@Test
	public void TC_10_NhapKyTuDacBietVaKyTuKhacTruongGaKhoiHanh() {
		log.info("TC_10_Nhap text ky tu dac biet");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_SPECIAL_INVALID, "com.VCB:id/linPickUp");

		log.info("TC_010_Verify lay gia tri text rong");
		verifyEquals(trainTicket.getDynamicInputPoint("com.VCB:id/linPickUp"), TrainTicket_Data.textDefault.TITLE_START);

		log.info("TC_10_Nhap text ky tu so tu 0 den 9");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_NUMBER_VALID, "com.VCB:id/linPickUp");

		log.info("TC_10_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint("com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_NUMBER_VALID);

		log.info("TC_10_Nhap text ky tu chu thuong va chu hoa");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_CHAR_VALID, "com.VCB:id/linPickUp");

		log.info("TC_10_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint("com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_CHAR_VALID);

		log.info("TC_10_Nhap text ky tu tieng viet co dau");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_VIETNAM_VALID, "com.VCB:id/linPickUp");

		log.info("TC_10_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint("com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_VIETNAM_VALID);
	}

	@Test
	public void TC_11_NhapKyTuTimKiemGanDungGaKhoiHanh() {
		log.info("TC_11_Nhap ky tu vao o Search");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_SERACH_THE_SAME, "com.VCB:id/linPickUp");

		log.info("TC_11_Lay danh sach tim kiem");
		List<String> listSuggestPoint = trainTicket.getListOfSuggestedMoneyOrListText("com.VCB:id/tvTen");

		log.info("TC_11_Kiem tra hien thi ket qua goi y");
		verifyTrue(trainTicket.checkSuggestPoint(listSuggestPoint, "hai"));
	}

	@Test
	public void TC_12_NhapGaKhoiHanhHopLe() {
		log.info("TC_12_Nhap text ga khoi hanh co trong danh sach");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_START_END_VALID, "com.VCB:id/linPickUp");

		log.info("TC_12_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint("com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_12_Verify gia tri tim kiem trong danh sach");
		verifyEquals(trainTicket.getDynamicTextPointStart("com.VCB:id/tvTen"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_13_ChonMotGaKhoiHanhHopLe() {
		log.info("TC_13_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_13_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint("com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_14_KiemTraGiaTriNhapVaoGaKhoiHanh() {
		log.info("TC_14_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint("com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_15_KiemTraSuaGaKhoiHanh() {
		log.info("TC_15_Nhap text ga khoi hanh co trong danh sach");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/linPickUp");

		log.info("TC_15_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint("com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);
	}

	@Test
	public void TC_16_TimKiemGaKhoiHanhKhongTonTai() {
		log.info("TC_16_Nhap text ga khoi hanh co trong danh sach");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_NUMBER_VALID, "com.VCB:id/linPickUp");

		log.info("TC_16_verify message khong co du lieu");
		verifyEquals(trainTicket.getDynamicTextInPopUp("com.VCB:id/vtWarning"), "Không có dữ liệu hợp lệ");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}
}
