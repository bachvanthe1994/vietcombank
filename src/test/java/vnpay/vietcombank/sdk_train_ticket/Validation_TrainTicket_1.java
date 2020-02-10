package vnpay.vietcombank.sdk_train_ticket;

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
import vietcombank_test_data.TrainTicket_Data;

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
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, "Đặt vé tàu");

		log.info("TC_01_Step_Click close message");
		login.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_01_Check title dat ve tau");
		trainTicket.isDynamicMessageAndLabelTextDisplayed(driver, "ĐẶT VÉ TÀU");

		log.info("TC_01_Check hien thi nut back");
		trainTicket.isDynamicBackIconDisplayed(driver, "Lịch sử đặt vé");

		log.info("TC_01_Check hien thi nut lich su dat ve");
		trainTicket.isDynamicHistoryIconDisplayed(driver, "Lịch sử đặt vé");

		log.info("TC_01_Check text ga khoi hanh");
		verifyEquals(trainTicket.getDynamicPointStartAndEnd(driver, "ĐẶT VÉ TÀU", TrainTicket_Data.textDefault.TITLE_START), "Ga khởi hành");

		log.info("TC_01_Check text ga den");
		verifyEquals(trainTicket.getDynamicPointStartAndEnd(driver, "ĐẶT VÉ TÀU", TrainTicket_Data.textDefault.TITLE_END), "Ga đến");

		log.info("TC_01_Check hien thi button doi ga tau");
		trainTicket.isDynamicChangeIconDisplayed(driver, TrainTicket_Data.textDefault.TITLE_END);

		log.info("TC_01_Check hien thi icon 1 chieu");
		trainTicket.isDynamicHistoryIconDisplayed(driver, "Một chiều");

		log.info("TC_01_Check hien thi icon khu hoi");
		trainTicket.isDynamicHistoryIconDisplayed(driver, "Khứ hồi");

		log.info("TC_01_Check hien thi ngay di");
		trainTicket.isDynamicMessageAndLabelTextDisplayed(driver, "Ngày đi");

		log.info("TC_01_Check hien thi ngay ve");
		trainTicket.isDynamicMessageAndLabelTextDisplayed(driver, "Ngày về");

		log.info("TC_01_Check hien thi combobox hanh khach");
		trainTicket.isDynamicComboboxDisplayed(driver, "Hành khách");

		log.info("TC_01_Check hien thi combobox loai cho");
		trainTicket.isDynamicComboboxDisplayed(driver, "Loại chỗ");

		log.info("TC_01_Check hien thi button tiep tuc");
		trainTicket.isDynamicButtonDisplayed(driver, "TIẾP TỤC");
	}

	@Test
	public void TC_02_KiemTraHienThiMacDinhKhuHoi() {
		log.info("TC_02_Khi mac dinh tick chon ve khu hoi, se hien thi them truong ngay ve. Do vay se check hien thi ngay ve thay cho check icon tick ve khu hoi");
		trainTicket.isDynamicMessageAndLabelTextDisplayed(driver, "Ngày về");
	}

	@Test
	public void TC_03_KiemTraLinkLichSuDatVe() {
		log.info("TC_01_Click lich su dat ve");
		trainTicket.clickToDynamicLink(driver, "Lịch sử đặt vé");

		log.info("TC_01_Check text man hinh lich su dat ve");
		trainTicket.isDynamicBackIconDisplayed(driver, "Lịch sử đặt vé");

		log.info("TC_01_Click quay tro ve man hinh dat ve");
		trainTicket.clickToDynamicBackIcon(driver, "Lịch sử đặt vé");
	}

	@Test
	public void TC_04_KiemTraHienThiMacDinhButtonTiepTuc() {
		log.info("TC_04_Check hien thi button tiep tuc");
		trainTicket.isDynamicButtonDisplayed(driver, "TIẾP TỤC");

		log.info("TC_04_Click button tiep tuc");
		trainTicket.clickToDynamicButton(driver, "TIẾP TỤC");

		log.info("TC_04_Click button dong y dong popup");
		trainTicket.clickToDynamicButton(driver, "ĐỒNG Ý");
	}

	@Test
	public void TC_05_CheckMacDinhHienThiTextGaKhoiHanh() {
		log.info("TC_01_Check text ga khoi hanh");
		verifyEquals(trainTicket.getDynamicPointStartAndEnd(driver, "ĐẶT VÉ TÀU", TrainTicket_Data.textDefault.TITLE_START), "Ga khởi hành");
	}

	@Test
	public void TC_06_CheckTextBoTrongGaKhoiHanh() {
		log.info("TC_06_Click ga den");
		trainTicket.clickDynamicPointStartAndEnd(driver, "ĐẶT VÉ TÀU", TrainTicket_Data.textDefault.TITLE_END);

		log.info("TC_06_Check message");
		verifyEquals(trainTicket.getDynamicTextInPopUp(driver, TrainTicket_Data.message.BLANK_START), "Quý khách vui lòng nhập Ga đi trước khi nhập Ga đến.");

		log.info("TC_06_Click OK");
		trainTicket.clickToDynamicButton(driver, "OK");
	}

	@Test
	public void TC_07_NhanIconXDongManHinhChonGa() {
		log.info("TC_07_Click icon X cancel");
		trainTicket.clickDynamicCancelIcon(driver, "Ga khởi hành");
	}

	@Test
	// Lỗi app vẫn cho phép nhập hơn 100 ký tự
	public void TC_08_NhapToiDaKyTuTruongGaKhoiHanh() {
		log.info("TC_06_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd(driver, "ĐẶT VÉ TÀU", TrainTicket_Data.textDefault.TITLE_START);

		log.info("TC_06_Nhap text ga khoi hanh 101 ky tu vuot qua gioi han cho phep");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_MAX_INVALID, "0");
	}

	@Test
	public void TC_09_NhapKyTuDacBietVaKyTuKhacTruongGaKhoiHanh() {
		log.info("TC_06_Nhap text ky tu dac biet");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_SPECIAL_INVALID, "0");

		log.info("TC_06_Verify lay gia tri text rong");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "0"), TrainTicket_Data.textDefault.TITLE_START);

		log.info("TC_06_Nhap text ky tu so tu 0 den 9");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_NUMBER_VALID, "0");

		log.info("TC_06_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "0"), TrainTicket_Data.inputText.POINT_NUMBER_VALID);

		log.info("TC_06_Nhap text ky tu chu thuong va chu hoa");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_CHAR_VALID, "0");

		log.info("TC_06_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "0"), TrainTicket_Data.inputText.POINT_CHAR_VALID);

		log.info("TC_06_Nhap text ky tu tieng viet co dau");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_VIETNAM_VALID, "0");

		log.info("TC_06_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "0"), TrainTicket_Data.inputText.POINT_VIETNAM_VALID);

	}

	@Test
	public void TC_10_NhapGaKhoiHanhHopLe() {
		log.info("TC_06_Nhap text ga khoi hanh co trong danh sach");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_START_END_VALID, "0");

		log.info("TC_06_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "0"), TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_06_Verify gia tri tim kiem trong danh sach");
		verifyEquals(trainTicket.getDynamicTextPointStart(driver, "com.VCB:id/tvTen"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_11_ChonMotGaKhoiHanhHopLe() {
		log.info("TC_06_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_06_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "0"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_12_KiemTraGiaTriNhapVaoGaKhoiHanh() {
		log.info("TC_06_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "0"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_13_KiemTraSuaGaKhoiHanh() {
		log.info("TC_06_Nhap text ga khoi hanh co trong danh sach");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "0");

		log.info("TC_06_Verify lay gia tri ga khoi hanh vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "0"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);
	}

	@Test
	public void TC_14_TimKiemGaKhoiHanhKhongTonTai() {
		log.info("TC_06_Nhap text ga khoi hanh co trong danh sach");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_NUMBER_VALID, "0");

		log.info("TC_06_verify message khong co du lieu");
		verifyEquals(trainTicket.getDynamicTextInPopUp(driver, TrainTicket_Data.message.EMPTY_START), "Không có dữ liệu hợp lệ");
	}

	@Test
	public void TC_15_CheckHienThiMacDinhTextGaDen() {
		log.info("TC_01_Check text ga den");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "2"), "Ga đến");
	}

	@Test
	public void TC_16_DongPopupVaCheckManHinhNgoai() {
		log.info("TC_07_Click icon X cancel");
		trainTicket.clickDynamicCancelIcon(driver, TrainTicket_Data.inputText.POINT_NUMBER_VALID);

		log.info("TC_01_Check text ga khoi hanh chua duoc chon gia tri");
		verifyEquals(trainTicket.getDynamicPointStartAndEnd(driver, "ĐẶT VÉ TÀU", TrainTicket_Data.textDefault.TITLE_START), "Ga khởi hành");

		log.info("TC_01_Check text ga den mac dinh la ga den");
		verifyEquals(trainTicket.getDynamicPointStartAndEnd(driver, "ĐẶT VÉ TÀU", TrainTicket_Data.textDefault.TITLE_END), "Ga đến");
	}

	@Test
	// Lỗi app vẫn cho phép nhập hơn 100 ký tự
	public void TC_17_NhapToiDaKyTuTruongGaden() {
		log.info("TC_06_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd(driver, "ĐẶT VÉ TÀU", TrainTicket_Data.textDefault.TITLE_START);

		log.info("TC_06_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "0");

		log.info("TC_06_Click gia tri ga khoi hanh");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_06_Nhap text ga den 101 ky tu vuot qua gioi han cho phep");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_MAX_INVALID, "2");
	}

	@Test
	public void TC_18_NhapKyTuDacBietVaKyTuKhacTruongGaDen() {
		log.info("TC_06_Nhap text ky tu dac biet");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_SPECIAL_INVALID, "2");

		log.info("TC_06_Verify lay gia tri text rong");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "2"), TrainTicket_Data.textDefault.TITLE_END);

		log.info("TC_06_Nhap text ky tu so tu 0 den 9");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_NUMBER_VALID, "2");

		log.info("TC_06_Verify lay gia tri ga den vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "2"), TrainTicket_Data.inputText.POINT_NUMBER_VALID);

		log.info("TC_06_Nhap text ky tu chu thuong va chu hoa");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_CHAR_VALID, "2");

		log.info("TC_06_Verify lay gia tri ga den vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "2"), TrainTicket_Data.inputText.POINT_CHAR_VALID);

		log.info("TC_06_Nhap text ky tu tieng viet co dau");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_VIETNAM_VALID, "2");

		log.info("TC_06_Verify lay gia tri ga den vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "2"), TrainTicket_Data.inputText.POINT_VIETNAM_VALID);
	}

	@Test
	public void TC_19_NhapGaDenHopLe() {
		log.info("TC_06_Nhap text ga den co trong danh sach");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_START_END_VALID, "2");

		log.info("TC_06_Verify lay gia tri ga den vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "2"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_20_ChonMotGaDenHopLe() {
		log.info("TC_06_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_01_Check text ga den");
		verifyEquals(trainTicket.getDynamicPointStartAndEnd(driver, "ĐẶT VÉ TÀU", TrainTicket_Data.inputText.POINT_START_END_VALID), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_21_KiemTraGiaTriNhapVaoGaDen() {
		log.info("TC_01_Check text ga den");
		verifyEquals(trainTicket.getDynamicPointStartAndEnd(driver, "ĐẶT VÉ TÀU", TrainTicket_Data.inputText.POINT_START_END_VALID), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_22_KiemTraEditGaDen() {
		log.info("TC_06_Click ga den");
		trainTicket.clickDynamicPointStartAndEnd(driver, "ĐẶT VÉ TÀU", TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_06_Nhap text ga den co trong danh sach");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "2");

		log.info("TC_06_Verify lay gia tri ga den vua nhap");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "2"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);
	}

	@Test
	public void TC_23_TimKiemGaDenKhongTonTai() {
		log.info("TC_06_Nhap text ga den khong co trong danh sach");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_NUMBER_VALID, "2");

		log.info("TC_06_verify message khong co du lieu");
		verifyEquals(trainTicket.getDynamicTextInPopUp(driver, TrainTicket_Data.message.EMPTY_START), "Không có dữ liệu hợp lệ");
	}

	@Test
	public void TC_24_KiemTraChonGaDenTruocGaKhoiHanh() {
		log.info("TC_07_Click icon X cancel");
		trainTicket.clickDynamicCancelIcon(driver, TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_07_Click icon back lai man hinh home, de reset lai diem khoi hanh va diem den");
		trainTicket.clickToDynamicBackIcon(driver, "Lịch sử đặt vé");

		log.info("TC_01_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, "Đặt vé tàu");

		log.info("TC_01_Step_Click close message");
		login.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_06_Click ga den");
		trainTicket.clickDynamicPointStartAndEnd(driver, "ĐẶT VÉ TÀU", TrainTicket_Data.textDefault.TITLE_END);

		log.info("TC_06_Check message");
		verifyEquals(trainTicket.getDynamicTextInPopUp(driver, TrainTicket_Data.message.BLANK_START), "Quý khách vui lòng nhập Ga đi trước khi nhập Ga đến.");

		log.info("TC_06_Click OK");
		trainTicket.clickToDynamicButton(driver, "OK");
	}

	@Test
	public void TC_25_KiemTraSuaGaKhoiHanhSauKhiChonGaDenHopLe() {
		log.info("TC_06_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "0");

		log.info("TC_06_Nhap text ga den co trong danh sach");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "2");

		log.info("TC_06_Nhap lai ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_START_END_VALID, "0");

		log.info("TC_06_click chon ga khoi hanh");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_01_Check text ga den mac dinh la ga den");
		verifyEquals(trainTicket.getDynamicInputPoint(driver, "2"), TrainTicket_Data.textDefault.TITLE_END);
	}
}
