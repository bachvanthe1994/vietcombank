package vnpay.vietcombank.sdk_train_ticket;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import commons.WebPageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.ServiceLimitInfo;
import model.SourceAccountModel;
import pageObjects.LogInPageObject;
import pageObjects.WebBackendSetupPageObject;
import pageObjects.sdk.trainTicket.TrainTicketPageObject;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data;
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data.textDefault;

public class Limit_Train_Ticket extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private TrainTicketPageObject trainTicket;

	List<String> listExpect;
	List<String> listActual;
	String phoneNumber = "";
	String account;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	private WebBackendSetupPageObject webBackend;
	WebDriver driverWeb;
	long amount;

	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "5000000", "5500000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		log.info("Before class: Mo backend ");
		startServer();
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		webBackend = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		webBackend.Login_Web_Backend(driverWeb, username, passWeb);

		webBackend.addMethod(driverWeb, "Thanh toán vé tàu", inputInfo, Constants.BE_CODE_PACKAGE);
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

	}

	@Parameters({ "pass" })
	@Test
	public void TC_01_DatVeTau_Thanh_Toan_Nho_Hon_Han_Muc_Min_Transfer(String pass) throws InterruptedException {
		log.info("TC_01_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText(textDefault.TRAIN_BOOKING);

		log.info("TC_01_Step_Click close message");
		trainTicket.clickToDynamicButton(textDefault.AGREE);

		log.info("TC_01_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd(textDefault.TRAIN_BOOKING_CAPITALIZE, "com.VCB:id/tvTextPickUp");

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
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_01_Click chon gio khoi hanh");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivBgItem");

		log.info("TC_01_Click TIEP TUC");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		log.info("TC_01_Click chon toa");
		trainTicket.clickDynamicSelectLocation("0", "com.VCB:id/tvWagon");

		log.info("TC_01_get lay mau o cho trong");
		String colorOfSeat = trainTicket.getColorOfElement(TrainTicketPageUIs.IMAGE_BY_TEXT, textDefault.BLANK);

		log.info("TC_01_Click chon cho trong");
		trainTicket.scrollIDownOneTime(driver);
		listActual = trainTicket.chooseSeats(1, colorOfSeat);

		log.info("TC_01Verify so ghe da chon");
		listExpect = Arrays.asList(trainTicket.getTextInDynamicPopup("com.VCB:id/tvSeat"));

		log.info("TC_01_Click tiep tuc");
		trainTicket.clickToDynamicButton(textDefault.CONTINUE);

		amount = convertAvailableBalanceCurrentcyOrFeeToLong(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTotalAmount"));

		ServiceLimitInfo inputInfoMin = new ServiceLimitInfo("1000", (amount + 20) + "", (amount + 100) + "", "10000000");

		webBackend.getInfoServiceLimit(driverWeb, "Thanh toán vé tàu", inputInfoMin, Constants.BE_CODE_PACKAGE);

		log.info("TC_01_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.CONTINUE);

		log.info("TC_01_Verify man hinh thong tin hanh khach");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), textDefault.INFOMATION_PASSENGER);

		log.info("TC_01_Nhap ho ten khách hang");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CUSTOMER_NAME + randomNumber(), "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvHoTen");

		log.info("TC_01_Nhap so CMT");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CARD_NO_SHORT + randomNumber(), "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvCMND");

		log.info("TC_01_Nhap so CMT");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.CARD_NO_SHORT + randomNumber(), textDefault.CONTACT_INFO, "com.VCB:id/tvCMND");

		log.info("TC_01_Nhap so CMT");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.TELEPHONE_NO_SHORT + randomNumber(), textDefault.CONTACT_INFO, "com.VCB:id/tvSDT");

		log.info("TC_01_Nhap email");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.emailName + randomNumber() + "@gmail.com", textDefault.CONTACT_INFO, "com.VCB:id/tvEmail");

		log.info("TC_01_Click radio khong xuat hoa don");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivNoXuatHoaDon");

		log.info("TC_01_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.CONTINUE);

		log.info("TC_01_click button dong y dong popup");
		trainTicket.clickToDynamicButtonContains("Đ");

		log.info("TC_01_Get tong tien chieu di");
		trainTicket.getTextTotal("0", "com.VCB:id/tvTotalAmount");

		log.info("TC_01_click button Thanh toan");
		trainTicket.clickToDynamicButtonContains(textDefault.PAY);

		log.info("TC_01_click button Khong");
		trainTicket.clickToDynamicButton(textDefault.NO);

		trainTicket.scrollDownToText(driver, textDefault.TOTAL_MONEY_PAY);

		log.info("TC_01_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(trainTicket.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((amount + 20) + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");

		trainTicket.clickToDynamicContinue("com.VCB:id/btOK");

	}

	@Test
	public void TC_02_DatVeTau_Thanh_Toan_Nho_Hon_Han_Muc_Max_Transfer() throws InterruptedException {

		ServiceLimitInfo inputInfoMax = new ServiceLimitInfo("1000", (amount - 20) + "", (amount - 10) + "", "10000000");

		webBackend.getInfoServiceLimit1(driverWeb, "Thanh toán vé tàu", inputInfoMax, Constants.BE_CODE_PACKAGE);

		log.info("TC_01_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		verifyEquals(trainTicket.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 10) + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");

		ServiceLimitInfo inputInfoMax1 = new ServiceLimitInfo("1000", (amount*100) + "", (amount*10000) + "", "10000000");
		webBackend.getInfoServiceLimit1(driverWeb, "Thanh toán vé tàu", inputInfoMax1, Constants.BE_CODE_PACKAGE);

	}

	@Parameters({ "otp" })
	@Test
	public void TC_03_DatVeTau_Thanh_Toan_Lon_Hon_Han_Muc_Max_Nhom() throws InterruptedException {

		webBackend.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán hóa đơn", (amount - 10000) + "");

		log.info("TC_01_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		verifyEquals(trainTicket.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 10000) + "") + " VND/1 lần ngày của nhóm dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");

		trainTicket.clickToDynamicContinue("com.VCB:id/btOK");

		webBackend.Reset_Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán hóa đơn");

	}

	@Test
	public void TC_04_DatVeTau_Thanh_Toan_Lon_Hon_Han_Muc_Max_Goi() throws InterruptedException {

		webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp", (amount - 1000) + "");

		log.info("TC_04_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains(textDefault.NEXT);

		verifyEquals(trainTicket.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount -1000) + "") + " VND/1 lần ngày của gói dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");

		trainTicket.clickToDynamicContinue("com.VCB:id/btOK");

		webBackend.Reset_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
