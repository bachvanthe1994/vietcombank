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
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data;

public class Validation_TrainTicket_1 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TrainTicketPageObject trainTicket;
	List<String> listExpect;
	List<String> listActual;

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
	public void TC_01_KiemTraManHinhTimKiemChuyenDi() {
		trainTicket = PageFactoryManager.getTrainTicketPageObject(driver);

		log.info("TC_01_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Đặt vé tàu");

		log.info("TC_01_Step_Click close message");
		trainTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_01_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("ĐẶT VÉ TÀU"));

		log.info("TC_01_Check hien thi nut back");
		verifyTrue(trainTicket.isDynamicBackIconDisplayed( "Lịch sử đặt vé"));

		log.info("TC_01_Check hien thi nut lich su dat ve");
		verifyTrue(trainTicket.isDynamicHistoryIconDisplayed( "Lịch sử đặt vé"));

		log.info("TC_01_Check text ga khoi hanh");
		verifyEquals(trainTicket.getDynamicPointStartAndEnd("ĐẶT VÉ TÀU", "com.VCB:id/tvTextPickUp"), "Ga khởi hành");

		log.info("TC_01_Check text ga den");
		verifyEquals(trainTicket.getDynamicPointStartAndEnd( "ĐẶT VÉ TÀU", "com.VCB:id/tvTextArrival"), "Ga đến");

		log.info("TC_01_Check hien thi button doi ga tau");
		verifyTrue(trainTicket.isDynamicChangeIconDisplayed( TrainTicket_Data.textDefault.TITLE_END));

		log.info("TC_01_Check hien thi icon 1 chieu");
		verifyTrue(trainTicket.isDynamicHistoryIconDisplayed( "Một chiều"));

		log.info("TC_01_Check hien thi icon khu hoi");
		verifyTrue(trainTicket.isDynamicHistoryIconDisplayed( "Khứ hồi"));

		log.info("TC_01_Check hien thi ngay di");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed( "Ngày đi"));

		log.info("TC_01_Check hien thi ngay ve");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed( "Ngày về"));

		log.info("TC_01_Check hien thi combobox hanh khach");
		verifyTrue(trainTicket.isDynamicComboboxDisplayed("Hành khách"));

		log.info("TC_01_Check hien thi combobox loai cho");
		verifyTrue(trainTicket.isDynamicComboboxDisplayed( "Loại chỗ"));

		log.info("TC_01_Check hien thi button tiep tuc");
		verifyTrue(trainTicket.isDynamicButtonDisplayed( "TIẾP TỤC"));
	}

	@Test
	public void TC_02_KiemTraHienThiMacDinhKhuHoi() {
		log.info("TC_02_Khi mac dinh tick chon ve khu hoi, se hien thi them truong ngay ve. Do vay se check hien thi ngay ve thay cho check icon tick ve khu hoi");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed( "Ngày về"));
	}

	@Test
	public void TC_03_KiemTraLinkLichSuDatVe() {
		log.info("TC_03_Click lich su dat ve");
		trainTicket.clickToDynamicLink("Lịch sử đặt vé");

		log.info("TC_03_Check text man hinh lich su dat ve");
		verifyTrue(trainTicket.isDynamicBackIconDisplayed( "Lịch sử đặt vé"));

		log.info("TC_03_Click quay tro ve man hinh dat ve");
		trainTicket.clickToDynamicBackIcon( "Lịch sử đặt vé");
	}

	@Test
	public void TC_04_KiemTraHienThiMacDinhButtonTiepTuc() {
		log.info("TC_04_Check hien thi button tiep tuc");
		verifyTrue(trainTicket.isDynamicButtonDisplayed( "TIẾP TỤC"));

		log.info("TC_04_Click button tiep tuc");
		trainTicket.clickToDynamicButton("TIẾP TỤC");

		log.info("TC_04_Click button dong y dong popup");
		trainTicket.clickToDynamicButton( "ĐỒNG Ý");
	}

	@Test
	public void TC_05_CheckMacDinhHienThiTextGaKhoiHanh() {
		log.info("TC_05_Check text ga khoi hanh");
		verifyEquals(trainTicket.getDynamicPointStartAndEnd( "ĐẶT VÉ TÀU", "com.VCB:id/tvTextPickUp"), "Ga khởi hành");
	}

	@Test
	public void TC_06_CheckTextBoTrongGaKhoiHanh() {
		log.info("TC_06_Click ga den");
		trainTicket.clickDynamicPointStartAndEnd("ĐẶT VÉ TÀU", "com.VCB:id/tvTextArrival");

		log.info("TC_06_Check message");
		verifyEquals(trainTicket.getTextInDynamicPopup( "android:id/message"), "Quý khách vui lòng nhập Ga đi trước khi nhập Ga đến.");
		
		log.info("TC_06_Click OK");
		trainTicket.clickToDynamicButton( "OK");
	}

	@Test
	public void TC_07_NhanIconXDongManHinhChonGa() {
		log.info("TC_07_Click icon X cancel");
		trainTicket.clickDynamicCancelIcon("Ga khởi hành");
		
		log.info("TC_01_quay ve man hinh dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("ĐẶT VÉ TÀU"));
	}

	@Test
	// Lỗi app vẫn cho phép nhập hơn 100 ký tự
	public void TC_08_NhapToiDaKyTuTruongGaKhoiHanh() {
		log.info("TC_08_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd("ĐẶT VÉ TÀU", "com.VCB:id/tvTextPickUp");

		log.info("TC_08_Nhap text ga khoi hanh 101 ky tu vuot qua gioi han cho phep");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_MAX_INVALID, "com.VCB:id/linPickUp");
		
		log.info("TC_08_Verify text nhap vao toi da");
		verifyEquals(trainTicket.getTextMaxLength("com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_MAX_INVALID.substring(0, 200));
		
	}

	@Test
	public void TC_09_NhapKyTuDacBietVaKyTuKhacTruongGaKhoiHanh() {
		log.info("TC_09_Nhap text ky tu dac biet");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_SPECIAL_INVALID, "com.VCB:id/linPickUp");

		log.info("TC_09_Verify lay gia tri text rong");
		verifyEquals(trainTicket.getDynamicInputPoint( "com.VCB:id/linPickUp"), TrainTicket_Data.textDefault.TITLE_START);

		log.info("TC_09_Nhap text ky tu so tu 0 den 9");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_NUMBER_VALID, "com.VCB:id/linPickUp");

		log.info("TC_09_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint( "com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_NUMBER_VALID);

		log.info("TC_09_Nhap text ky tu chu thuong va chu hoa");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_CHAR_VALID, "com.VCB:id/linPickUp");

		log.info("TC_09_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint( "com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_CHAR_VALID);

		log.info("TC_09_Nhap text ky tu tieng viet co dau");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_VIETNAM_VALID, "com.VCB:id/linPickUp");

		log.info("TC_09_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint("com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_VIETNAM_VALID);
	}

	@Test
	public void TC_10_NhapKyTuTimKiemGanDungGaKhoiHanh() {
		log.info("TC_10_Nhap ky tu vao o Search");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_SERACH_THE_SAME, "com.VCB:id/linPickUp");

		log.info("TC_10_Lay danh sach tim kiem");
		List<String> listSuggestPoint = trainTicket.getListOfSuggestedMoneyOrListText( "com.VCB:id/tvTen");

		log.info("TC_10_Kiem tra hien thi ket qua goi y");
		verifyTrue(trainTicket.checkSuggestPoint(listSuggestPoint, "hai"));
	}

	@Test
	public void TC_11_NhapGaKhoiHanhHopLe() {
		log.info("TC_11_Nhap text ga khoi hanh co trong danh sach");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_START_END_VALID, "com.VCB:id/linPickUp");

		log.info("TC_11_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint( "com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_11_Verify gia tri tim kiem trong danh sach");
		verifyEquals(trainTicket.getDynamicTextPointStart( "com.VCB:id/tvTen"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_12_ChonMotGaKhoiHanhHopLe() {
		log.info("TC_12_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_12_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint( "com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_13_KiemTraGiaTriNhapVaoGaKhoiHanh() {
		log.info("TC_13_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint( "com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_14_KiemTraSuaGaKhoiHanh() {
		log.info("TC_14_Nhap text ga khoi hanh co trong danh sach");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/linPickUp");

		log.info("TC_14_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint("com.VCB:id/linPickUp"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);
	}

	@Test
	public void TC_15_TimKiemGaKhoiHanhKhongTonTai() {
		log.info("TC_15_Nhap text ga khoi hanh co trong danh sach");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_NUMBER_VALID, "com.VCB:id/linPickUp");

		log.info("TC_15_verify message khong co du lieu");
		verifyEquals(trainTicket.getDynamicTextInPopUp( "com.VCB:id/vtWarning"), "Không có dữ liệu hợp lệ");
	}

	@Test
	public void TC_16_CheckHienThiMacDinhTextGaDen() {
		log.info("TC_16_Check text ga den");
		verifyEquals(trainTicket.getDynamicInputPoint( "com.VCB:id/linArival"), "Ga đến");
	}

	@Test
	public void TC_17_DongPopupVaCheckManHinhNgoai() {
		log.info("TC_17_Click icon X cancel");
		trainTicket.clickDynamicCancelIcon( TrainTicket_Data.inputText.POINT_NUMBER_VALID);

		log.info("TC_17_Check text ga khoi hanh chua duoc chon gia tri");
		verifyEquals(trainTicket.getDynamicPointStartAndEnd("ĐẶT VÉ TÀU", "com.VCB:id/tvTextPickUp"), "Ga khởi hành");

		log.info("TC_17_Check text ga den mac dinh la ga den");
		verifyEquals(trainTicket.getDynamicPointStartAndEnd("ĐẶT VÉ TÀU", "com.VCB:id/tvTextArrival"), "Ga đến");
	}

	@Test
	// Lỗi app vẫn cho phép nhập hơn 100 ký tự
	public void TC_18_NhapToiDaKyTuTruongGaden() {
		log.info("TC_18_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd("ĐẶT VÉ TÀU", "com.VCB:id/tvTextPickUp");

		log.info("TC_18_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/linPickUp");

		log.info("TC_18_Click gia tri ga khoi hanh");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_18_Nhap text ga den 101 ky tu vuot qua gioi han cho phep");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_MAX_INVALID, "com.VCB:id/linArival");
	
		log.info("TC_18_Verify text nhap vao toi da");
		verifyEquals(trainTicket.getTextMaxLength("com.VCB:id/linArival"), TrainTicket_Data.inputText.POINT_MAX_INVALID.substring(0, 208));
	}

	@Test
	public void TC_19_NhapKyTuDacBietVaKyTuKhacTruongGaDen() {
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
	public void TC_20_NhapKyTimKiemGanDungGaDen() {
		log.info("TC_20_Nhap ky tu vao o Search");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_SERACH_THE_SAME, "com.VCB:id/linArival");

		List<String> listSuggestPoint = trainTicket.getListOfSuggestedMoneyOrListText( "com.VCB:id/tvTen");

		log.info("TC_20_Kiem tra hien thi ket qua goi y");
		verifyTrue(trainTicket.checkSuggestPoint(listSuggestPoint, "Hai"));
	}

	@Test
	public void TC_21_NhapGaDenHopLe() {
		log.info("TC_21_Nhap text ga den co trong danh sach");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_START_END_VALID, "com.VCB:id/linArival");

		log.info("TC_21_Verify lay gia tri ga den vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint( "com.VCB:id/linArival"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_22_ChonMotGaDenHopLe() {
		log.info("TC_22_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText( TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_22_Check text ga den");
		verifyEquals(trainTicket.getDynamicPointStartAndEnd( "ĐẶT VÉ TÀU", "com.VCB:id/tvTextArrival"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_23_KiemTraGiaTriNhapVaoGaDen() {
		log.info("TC_23_Check text ga den");
		verifyEquals(trainTicket.getDynamicPointStartAndEnd( "ĐẶT VÉ TÀU", "com.VCB:id/tvTextArrival"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_24_KiemTraEditGaDen() {
		log.info("TC_24_Click ga den");
		trainTicket.clickDynamicPointStartAndEnd( "ĐẶT VÉ TÀU", "com.VCB:id/tvTextArrival");

		log.info("TC_24_Nhap text ga den co trong danh sach");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/linArival");

		log.info("TC_24_Verify lay gia tri ga den vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint("com.VCB:id/linArival"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);
	}

	@Test
	public void TC_25_TimKiemGaDenKhongTonTai() {
		log.info("TC_25_Nhap text ga den khong co trong danh sach");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_NUMBER_VALID, "com.VCB:id/linArival");

		log.info("TC_25_verify message khong co du lieu");
		verifyEquals(trainTicket.getDynamicTextInPopUp( "com.VCB:id/vtWarning"), "Không có dữ liệu hợp lệ");
	}

	@Test
	public void TC_26_KiemTraChonGaDenTruocGaKhoiHanh() {
		log.info("TC_26_Click icon X cancel");
		trainTicket.clickDynamicCancelIcon( TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_26_Click icon back lai man hinh home, de reset lai diem khoi hanh va diem den");
		trainTicket.clickToDynamicBackIcon( "Lịch sử đặt vé");

		log.info("TC_26_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText( "Đặt vé tàu");

		log.info("TC_26_Step_Click close message");
		trainTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_26_Click ga den");
		trainTicket.clickDynamicPointStartAndEnd( "ĐẶT VÉ TÀU", "com.VCB:id/tvTextArrival");

		log.info("TC_26_Check message");
		verifyEquals(trainTicket.getTextMessageInvalid( "android:id/message"), "Quý khách vui lòng nhập Ga đi trước khi nhập Ga đến.");

		log.info("TC_26_Click OK");
		trainTicket.clickToDynamicButton("OK");
	}

	@Test
	public void TC_27_KiemTraSuaGaKhoiHanhSauKhiChonGaDenHopLe() {
		log.info("TC_27_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/linPickUp");

		log.info("TC_27_Nhap text ga den co trong danh sach");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/linArival");

		log.info("TC_27_Nhap lai ga khoi hanh");
		trainTicket.inputToDynamicTextPoint( TrainTicket_Data.inputText.POINT_START_END_VALID, "com.VCB:id/linPickUp");

		log.info("TC_27_click chon ga khoi hanh");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_27_Check text ga den mac dinh la ga den");
		verifyEquals(trainTicket.getDynamicInputPoint( "com.VCB:id/linArival"), TrainTicket_Data.textDefault.TITLE_END);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}
}
