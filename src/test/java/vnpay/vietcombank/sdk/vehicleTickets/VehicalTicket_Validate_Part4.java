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
import vnpay.vietcombank.sdk.vehicleTicket.data.VehicalData;

public class VehicalTicket_Validate_Part4 extends Base {
    AppiumDriver<MobileElement> driver;
    private LogInPageObject login;
    private VehicalPageObject vehicalTicket;
    LocalDate now = LocalDate.now();
    String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();

    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt)
	    throws IOException, InterruptedException {
	startServer();
	log.info("Before class: Mo app ");
	driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
	login = PageFactoryManager.getLoginPageObject(driver);
	vehicalTicket = PageFactoryManager.getVehicalPageObject(driver);
	login.Global_login(phone, pass, opt);

	vehicalTicket.Vehical_login();
    }

    @Test
    public void TC_46_KiemTraNHanIconLoc() {
	log.info("TC_46_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

	log.info("TC_46_Step_2: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
	vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.EDIT_DESTINATION, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.EDIT_DESTINATION);

	log.info("TC_46_Step_3: click button tìm kiếm chuyến đi");
	vehicalTicket.clickToDynamicButton("Tìm kiếm chuyến đi");

	log.info("TC_46_Step_4: chọn lọc");
	vehicalTicket.clickToDynamicIconChangePlaceAndBack("com.VCB:id/ivTitleRight");

	log.info("TC_46_Step_5: kiểm tra hiển thị title");
	String title = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTitle1");
	verifyEquals(title, VehicalData.DATA_ORDER_TICKET.TITLE_FIND);

	log.info("TC_46_Step_6: kiểm tra hiển thị button quay lại");
	vehicalTicket.isDynamicIconChangePlaceAndBackAndFinndDisplayed("com.VCB:id/ivTitleLeft");
	verifyTrue(vehicalTicket.isDynamicIconChangePlaceAndBackAndFinndDisplayed("com.VCB:id/ivTitleLeft"));

	log.info("TC_46_Step_7: kiểm tra hiển thị button quay lại");
	verifyTrue(vehicalTicket.isDynamicIconChangePlaceAndBackAndFinndDisplayed("com.VCB:id/ivTitleLeft"));

	log.info("TC_46_Step_8: kiểm tra hiển thị swith");
	verifyTrue(vehicalTicket.isDynamicIconChangePlaceAndBackAndFinndDisplayed("com.VCB:id/ivRight"));

	log.info("TC_46_Step_9: kiểm tra hiển thị giả giá");
	verifyTrue(vehicalTicket.isDynamicFilterTripDisplayed("0"));

	log.info("TC_46_Step_10: kiểm tra hiển thị hãng xe");
	verifyTrue(vehicalTicket.isDynamicFilterTripDisplayed("1"));

	log.info("TC_46_Step_11: kiểm tra hiển thị giờ đi");
	verifyTrue(vehicalTicket.isDynamicFilterTripDisplayed("2"));

	log.info("TC_46_Step_12: kiểm tra hiển thị loại xe");
	verifyTrue(vehicalTicket.isDynamicFilterTripDisplayed("3"));
    }

    @Test
    public void TC_47_KiemTraNhanBack() {
	log.info("TC_47_Step_1: click back");
	vehicalTicket.clickToDynamicIconChangePlaceAndBack("com.VCB:id/ivTitleLeft");

	log.info("TC_47_Step_2: kiểm tra màn hình sau khi click back");
	String title = vehicalTicket.getDynamicDayStart("com.VCB:id/tv_title");
	verifyEquals(title, VehicalData.NOTIFICATION.LIST_TRIP);

    }

    @Test
    public void TC_48_KiemTraLocNhungKhongCoKetQuaPhuHop() {
	log.info("TC_48_Step_1: chọn lọc");
	vehicalTicket.clickToDynamicIconChangePlaceAndBack("com.VCB:id/ivTitleRight");

	log.info("TC_48_Step_2: chọn giảm giá");
	vehicalTicket.clickToDynamicIconChangePlaceAndBack("com.VCB:id/ivRight");

	log.info("TC_48_Step_3: chọn áp dụng");
	vehicalTicket.clickToDynamicButtonForID("com.VCB:id/btnApply");

	log.info("TC_48_Step_4: kiểm tra thông báo");
	String confirm = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTitle");
	verifyEquals(confirm, VehicalData.NOTIFICATION.NULL_DATA_FITER_TRIP);

	log.info("TC_42_Step_7: chọn đồng ý");
	vehicalTicket.clickToDynamicButton("ĐỒNG Ý");
    }

    @Test
    public void TC_49_KiemTraLocDieuKienGiamGiaOff() {
	log.info("TC_49_Step_1: chọn giảm giá");
	vehicalTicket.clickToDynamicIconChangePlaceAndBack("com.VCB:id/ivRight");

	log.info("TC_49_Step_2: chọn áp dụng");
	vehicalTicket.clickToDynamicButtonForID("com.VCB:id/btnApply");

	log.info("TC_49_Step_3: kiểm tra hiển thị kết quả");
	verifyTrue(vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnChooseSeat"));

    }

    @Test
    public void TC_50_KiemTraDanhSachHangXe() {
	log.info("TC_50_Step_1: chọn lọc");
	vehicalTicket.clickToDynamicIconChangePlaceAndBack("com.VCB:id/ivTitleRight");

	log.info("TC_50_Step_2: chọn hãng xe");
	vehicalTicket.clickToDynamicText("Hãng xe");

	log.info("TC_50_Step_3: kiểm tra hãng xe");
	verifyTrue(vehicalTicket.isDynamicFilterTripDisplayed("2"));

	log.info("TC_50_Step_4: chọn áp dụng");
	vehicalTicket.clickToDynamicButtonForID("com.VCB:id/btnApply");
    }

//    @Test
    public void TC_52_KiemTraThongTin1ChuyenDi() {
	log.info("TC_52_Step_4: kiểm tra hiển thị tên hãng xe");
	verifyTrue(vehicalTicket.isDynamicDisplayedManufacturerAndRateAndTimeRunAndRouter("com.VCB:id/tv_hang_xe"));

	log.info("TC_52_Step_4: kiểm tra hiển thị đánh giá");
	verifyTrue(vehicalTicket.isDynamicDisplayedManufacturerAndRateAndTimeRunAndRouter("com.VCB:id/tv_count_rate"));

	log.info("TC_52_Step_4: kiểm tra hiển thị thời gian chạy");
	verifyTrue(vehicalTicket.isDynamicDisplayedManufacturerAndRateAndTimeRunAndRouter("com.VCB:id/tvTime2"));

	log.info("TC_52_Step_4: kiểm tra hiển thị lộ trình");
	verifyTrue(vehicalTicket.isDynamicDisplayedManufacturerAndRateAndTimeRunAndRouter("com.VCB:id/tvFrom"));
	verifyTrue(vehicalTicket.isDynamicDisplayedManufacturerAndRateAndTimeRunAndRouter("com.VCB:id/tvTo"));

	log.info("TC_52_Step_4: kiểm tra hiển thị dich vụ");
	verifyTrue(vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnService"));

	log.info("TC_52_Step_4: kiểm tra hiển thị giá vé");
	verifyTrue(vehicalTicket.isDynamicDisplayedManufacturerAndRateAndTimeRunAndRouter("com.VCB:id/tvPrice"));
    }

    @Test
    public void TC_53_KiemTraNhânNutSua() {
	log.info("TC_53_Step_1: lấy thông tin điểm đi điểm đến");
	String fromt = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextPickUp");
	String to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextArrival");

	log.info("TC_53_Step_2: chọn sửa");
	vehicalTicket.clickToDynamicButtonForID("com.VCB:id/btnEdit");

	log.info("TC_53_Step_3: kiểm tra title màn hình");
	String title = vehicalTicket.getDynamicDayStart("com.VCB:id/tv_title");
	verifyEquals(title, VehicalData.DATA_ORDER_TICKET.TITLE_EDIT_DATA_FIND);

	log.info("TC_53_Step_4: kiểm tra hiển thị nut back");
	verifyTrue(vehicalTicket.isDynamicIconChangePlaceAndBackAndFinndDisplayed("com.VCB:id/ivTitleLeft"));

	log.info("TC_53_Step_5: kiểm tra hiển thị điểm đi điểm đến");
	String fromtEdit = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextPickUp");
	String toEdit = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextArrival");
	verifyEquals(fromt, fromtEdit);
	verifyEquals(to, toEdit);

	log.info("TC_53_Step_6: kiểm tra hiển thị button đổi vị trí");
	verifyTrue(vehicalTicket.isDynamicIconChangePlaceAndBackAndFinndDisplayed("com.VCB:id/ivround"));

	log.info("TC_53_Step_7: kiểm tra hiển thị thời gian ");
	verifyTrue(vehicalTicket.isDynamicTimeStartDisplayed("com.VCB:id/tvMonth"));

	log.info("TC_53_Step_8: kiểm tra hiển thị button ngày hôm nay");
	verifyTrue(vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnToday"));

	log.info("TC_53_Step_9: kiểm tra hiển thị button ngày mai");
	verifyTrue(vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnNextday"));

	log.info("TC_53_Step_10: kiểm tra hiển thị button áp dụng");
	verifyTrue(vehicalTicket.isDynamicEditTripDisplayed("com.VCB:id/btnSearch"));
    }

    @Test
    public void TC_54_KiemTraThongTinDiemDiDiemDen() {
	log.info("TC_54_Step_1: kiểm tra điểm đến");
	String fromt = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextPickUp");
	verifyEquals(fromt, VehicalData.DATA_ORDER_TICKET.PLACE_1);

	log.info("TC_54_Step_2: kiểm tra điểm đi");
	String to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextArrival");
	verifyEquals(to, VehicalData.DATA_ORDER_TICKET.EDIT_DESTINATION);
    }

    @Test
    public void TC_55_KiemTraSoKiTuDuocPhepNhap() {
	log.info("TC_55_Step_2: kiểm tra số lượng kí tự được phép nhập");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.LENGTH_201, "com.VCB:id/linPickUp");
	String lengthFromt = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextPickUp");
	verifyEquals(lengthFromt, VehicalData.DATA_ORDER_TICKET.LENGTH_200);
    }

    @Test
    public void TC_56_KiemTraLoaiKiTuDuocPhepNhap() {
	log.info("TC_56_Step_1: nhập kí tự hợp lệ");
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.DATA_INPUT_VN, "com.VCB:id/linPickUp");

	log.info("TC_56_Step_2: kiểm tra hiển thị các kí tự vừa nhập");
	String data_invalid = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextPickUp");
	verifyEquals(data_invalid, VehicalData.DATA_ORDER_TICKET.DATA_INPUT_VN);

	log.info("TC_56_Step_4: nhập kí tự không hợp lệ");
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.DATA_INPUT_SPECCIAL, "com.VCB:id/linPickUp");

	log.info("TC_56_Step_5: kiểm tra hiển thị các kí tự vừa nhập");
	String data_valid = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextPickUp");
	verifyEquals(data_valid, VehicalData.DATA_ORDER_TICKET.DESTINATION);
    }

    @Test
    public void TC_57_NhapDiemDiHopLe() {
	log.info("TC_57_Step_1: nhập và chọn điểm đi");
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_3, "com.VCB:id/linPickUp");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);

	log.info("TC_57_Step_2: kiểm tra hiển thị trên textbox");
	String fromt = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextPickUp");
	verifyEquals(fromt, VehicalData.DATA_ORDER_TICKET.PLACE_3);
    }

    @Test
    public void TC_58_KiemTraTimKiemTuongDoi() {
	log.info("TC_58_Step_1: nhập và chọn điểm đi");
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.CONTENT_DEPARTURE, "com.VCB:id/linPickUp");

	log.info("TC_58_Step_2: lấy vị trí được sugget để so sánh");
	String sugget_fromt = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTen");
	verifyTrue(sugget_fromt.contains(VehicalData.DATA_ORDER_TICKET.CONTENT_DEPARTURE));
    }

    @Test
    public void TC_59_KiemTraSuaDiemDi() {
	log.info("TC_59_Step_1: nhập và chọn điểm đi");
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");

	log.info("TC_59_Step_2: chỉnh sửa lại điểm đi");
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_3, "com.VCB:id/linPickUp");

	log.info("TC_59_Step_3: kiểm tra tìm kiếm tương đối");
	String sugget_to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTen");
	verifyTrue(sugget_to.contains(VehicalData.DATA_ORDER_TICKET.PLACE_3));
    }

    @Test
    public void TC_60_KiemTraGiaTriNhapVaoDiemDi() {
	log.info("TC_60_Step_1: nhập và chọn điểm đi");
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");

	log.info("TC_60_Step_2: kiểm tra tìm kiếm tương đối");
	String sugget_fromt = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTen");
	verifyTrue(sugget_fromt.contains(VehicalData.DATA_ORDER_TICKET.PLACE_1));
    }

    @Test
    public void TC_61_KiemTraDiemDiKhongTonTai() {
	log.info("TC_61_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.DESTINATION_INVALID, "com.VCB:id/linPickUp");

	log.info("TC_61_Step_3: kiểm tra thông báo");
	String nullData = vehicalTicket.getDynamicConfirmNullData("com.VCB:id/rlNullData");
	verifyEquals(nullData, VehicalData.NOTIFICATION.NOTI_NULL_DATA);

    }

    @Test
    public void TC_62_KiemTraTextBoxDiemDen() {
	log.info("TC_62_Step_1: get text");
	String choise_from = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextArrival");

	log.info("TC_62_Step_2: kiểm tra hiển thị");
	verifyEquals(choise_from, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
//		closeApp();
//		service.stop();

    }

}
