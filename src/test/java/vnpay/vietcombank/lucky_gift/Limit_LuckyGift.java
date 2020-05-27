package vnpay.vietcombank.lucky_gift;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.LuckyGiftPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LuckyGift_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.LuckyGift_Data.TitleLuckyGift;

public class Limit_LuckyGift extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private LuckyGiftPageObject luckyGift;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		luckyGift = PageFactoryManager.getLuckyGiftPageObject(driver);
		luckyGift.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");

	}

	@Test

	public void TC_01_ChuyenTienQuaThapHonHanMucToiThieu() {

		log.info("TC_01_Step_1: Chọn quà tặng may mắn");

		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_01_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("-TC_01_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("-TC_01_Step_4: Click tiep tuc popup");
		luckyGift.waitUntilPopUpDisplay("Hướng dẫn");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_5: chọn hình thức nhận");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThucNhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Số điện thoại");

		log.info("TC_01_Step_6: nhập số điện thoại");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MOBI_ACCEPT, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_01_Step_7: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.Limit_Money_Gift.LOWER_MIN_MONEY_A_TRANSACTION, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("-TC_01_Step_8: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToTextID(driver, "com.VCB:id/content_counter");

		log.info("TC_01_Step_9: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_10: Verify hien thi man hinh thong bao loi");
		luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.Messege_Limit.MESSEGE_ERROR_LOWER_MIN_TRAN);


		log.info("TC_01_Step_11: Click btn Dong");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_02_ChuyenTienQuaCaoHonHanMucToiDa() {

		log.info("TC_02_Step_01: Nhap so tien chuyen");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.Limit_Money_Gift.HIGHER_MAX_MONEY_A_TRANSACTION, "com.VCB:id/amount");

		log.info("-TC_02_Step_02: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("-TC_02_Step_03: Verify hien thi man hinh thong bao loi");
		luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.Messege_Limit.MESSEGE_ERROR_HIGHER_MAX_TRAN);

		log.info("TC_02_Step_04: Click btn Dong");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	// Set BE goi han nhom dich vu la 100 trieu

	public void TC_03_ChuyenTienQuaCaoHonHanMucToiDaNhomDichVu() {

		log.info("-TC_03_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("-TC_03_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("-TC_03_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_03_Step_4: Click tiep tuc popup");
		luckyGift.waitUntilPopUpDisplay("Hướng dẫn");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("-TC_03_Step_5: chọn hình thức nhận");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThucNhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Số điện thoại");

		log.info("TC_03_Step_5: nhập số điện thoại");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MOBI_ACCEPT, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_03_Step_6: Nhap so tien chuyen");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.Limit_Money_Gift.HIGHER_MAX_MONEY_A_TRANSACTION, "com.VCB:id/amount");

		log.info("-TC_03_Step_7: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToTextID(driver, "com.VCB:id/content_counter");

		log.info("TC_03_Step_8: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_09: Verify hien thi man hinh thong bao loi");
		luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.Messege_Limit.MESSEGE_ERROR_HIGHER_MAX_GROUP);

		log.info("TC_02_Step_10: Click btn Dong");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	// Set BE goi han goi dich vu la 100 trieu
	public void TC_04_ChuyenTienQuaCaoHonHanMucToiDaGoiDichVu() {

		log.info("TC_04_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_04_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("-TC_04_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_04_Step_4: Click tiep tuc popup");
		luckyGift.waitUntilPopUpDisplay("Hướng dẫn");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_5: chọn hình thức nhận");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThucNhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Số điện thoại");

		log.info("TC_04_Step_5: nhập số điện thoại");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MOBI_ACCEPT, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("-TC_04_Step_6: Nhap so tien chuyen");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.Limit_Money_Gift.HIGHER_MAX_MONEY_A_TRANSACTION, "com.VCB:id/amount");

		log.info("TC_04_Step_7: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToTextID(driver, "com.VCB:id/content_counter");
		luckyGift.hideKeyBoard(driver);
		
		log.info("TC_04_Step_8: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_9: Verify hien thi man hinh thong bao loi");
		luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.Messege_Limit.MESSEGE_ERROR_HIGHER_MAX_PACKAGE);

		log.info("TC_04_Step_10: Click btn Dong");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Parameters  ({"pass"})
	@Test
	// Set BE goi han goi dich vu la 100 000 001
	public void TC_05_ChuyenTienQuaCaoHonHanMucToiDaTrongNgay(String pass) {

		log.info("TC_05_Step_01: Nhap so tien chuyen");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.Limit_Money_Gift.MAX_MONEY_A_TRANSACTION, "com.VCB:id/amount");

		log.info("TC_05_Step_02: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");
		
		luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Xác nhận thông tin");

		log.info("TC_05_Step_03: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_Step_06: điền mât khẩu");
		luckyGift.inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");

		log.info("TC_05_Step_07: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		
		log.info("TC_05_Step_08: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch mới");

		log.info("TC_05_Step_09: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");


		log.info("TC_5_Step_10: chọn hình thức nhận");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThucNhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Số điện thoại");

		log.info("TC_05_Step_11: nhập số điện thoại");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MOBI_ACCEPT, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_05_Step_12: Nhap so tien chuyen");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.Limit_Money_Gift.MIN_MONEY_A_TRANSACTION, "com.VCB:id/amount");

		log.info("TC_05_Step_13: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToTextID(driver, "com.VCB:id/content_counter");

		log.info("TC_05_Step_14: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_15: Verify hien thi man hinh thong bao loi");
		luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.Messege_Limit.MESSEGE_ERROR_HIGHER_MAX_DAY);

		log.info("TC_05_Step_16: Click btn Dong");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
