package vnpay.vietcombank.sdk.shopping_online;

import java.io.IOException;
import java.util.List;

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
import pageObjects.shopping_online.ShoppingOnlinePageObject;
import vietcombankUI.shopping_online_UI.ShoppingOnlinePageUIs;
import vietcombank_test_data.Account_Data.Valid_Account;

public class Shopping_Online_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private ShoppingOnlinePageObject shopping;
	private HomePageObject homePage;
	String transferTime;
	String transactionNumber;
	List<String> listActual;

	long amount, fee, amountStart, feeView, amountView, amountAfter = 0;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, "© 2019 Vietcombank");
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Mua sắm trực tuyến");
		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);
		shopping.sleep(driver, 5000);

	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_ChonMuaMotSanPhamThanhToanOTPKhongChonKhuyenMai(String otp) {

		log.info("---------------------------TC_01_STEP_2: Them vao gio hang--------------------------------------");
		shopping.clickToDynamicCategories("Xem tất cả");
		shopping.clickToDynamicCategories("đ");

		log.info("---------------------------TC_01_STEP_3: lay tong tien can thanh toan---------------------------");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText("Miễn phí giao hàng cho đơn từ ").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("---------------------------TC_01_STEP_4: click dat hang---------------------------");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");

		log.info("---------------------------TC_01_STEP_4: click dat hang---------------------------");
		shopping.clickToDynamicCart("1", "0");

		log.info("---------------------------TC_01_STEP_4: click Vao gio hang---------------------------");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("---------------------------TC_01_STEP_5: click dat hang---------------------------");
		shopping.clickToDynamicButton("Đặt hàng");

		log.info("---------------------------TC_01_STEP_5: click thanh toan---------------------------");
		shopping.clickToDynamicButton("Thanh toán");

		if (shopping.isTextDisplayedInPageSource(driver, "Thông báo")) {
			log.info("---------------------------TC_01_STEP_4_1: click the moi---------------------------");
			shopping.clickToDynamicButton("Thêm mới");

			log.info("---------------------------TC_01_STEP_4_1: Hien thi man hinh them moi dia chi---------------------------");
			shopping.isDynamicTextInfoDisplayed("Thêm mới địa chỉ");

			log.info("---------------------------TC_01_STEP_4_2: nhap ten");
			shopping.inputToDynamicInfo("Ngyen Van A", "Họ tên người nhận");

			log.info("---------------------------TC_01_STEP_4_2: nhap so dien thoai---------------------------");
			shopping.inputToDynamicInfo("0904797866", "Số điện thoại người nhận");

			log.info("---------------------------TC_01_STEP_4_3: chon tinh thanh pho---------------------------");
			shopping.clickToDynamicCustomer("Tỉnh/Thành phố");
			shopping.clickToDynamicListProvince("Thành phố Hà Nội");

			log.info("---------------------------TC_01_STEP_4_3: chon tinh quan huyen---------------------------");
			shopping.clickToDynamicCustomer("Quận/Huyện");
			shopping.clickToDynamicListProvince("Quận Ba Đình");

			log.info("---------------------------TC_01_STEP_4_3: chon tinh xa phuong---------------------------");
			shopping.clickToDynamicCustomer("Phường/Xã");
			shopping.clickToDynamicListProvince("Phường Cống Vị");

			log.info("---------------------------TC_01_STEP_4_3: dia chi cu the---------------------------");
			shopping.inputToDynamicInfo("22 abc", "Địa chỉ cụ thể (Số nhà, tên đường...)");

			log.info("---------------------------TC_01_STEP_4_3: chon hoan tat---------------------------");
			shopping.clickToDynamicButton("Hoàn tất");

			log.info("---------------------------TC_01_STEP_6: click thanh toan---------------------------");
			shopping.clickToDynamicButton("Thanh toán");
		}
		
		log.info("---------------------------TC_01_STEP_6: click thanh toan ngay---------------------------");
		shopping.clickToDynamicDateInDateTimePicker("Thanh toán ngay");
		shopping.clickToDynamicButton("Đồng ý");
		
		log.info("---------------------------TC_01_STEP_7: click chon tai khoan---------------------------");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.ACCOUNT2);

		log.info("---------------------------TC_01_STEP_9: lay ra phi giao hang---------------------------");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("---------------------------TC_01_STEP_10: giam gia---------------------------");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", ""));

		log.info("---------------------------TC_01_STEP_11: tong tien---------------------------");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Tổng tiền:").split(" ");
		double totalMoney = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) tottalMoneyCart, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("---------------------------TC_01_STEP_12: Chon thanh toan---------------------------");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("---------------------------TC_01_STEP_13: Kiem tra tai khoan nguon---------------------------");
		String account = shopping.getMoneyByAccount("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.ACCOUNT2);

		log.info("---------------------------TC_01_STEP_14: Kiem tra so tien thanh toan---------------------------");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");

		log.info("---------------------------TC_01_STEP_16: Chon phuong thuc thanh toan---------------------------");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("---------------------------TC_01_STEP_17: Chon tiep tuc---------------------------");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("---------------------------TC_01_STEP_18: dien otp---------------------------");
		shopping.inputToDynamicOtp(otp, "Tiếp tục");

		log.info("---------------------------TC_01_STEP_19: Chon tiep tuc---------------------------");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("---------------------------TC_01_STEP_20: thuc hien giao dich moi---------------------------");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");

	}

	@Parameters({ "otp" })
	@Test
	public void TC_02_ChonMuaNhieuSanPhamThanhToanOTPKhongChonKhuyenMai(String otp) {

		log.info("---------------------------TC_02_STEP_2: Them vao gio hang");
		shopping.clickToDynamicCategories("Xem tất cả");
		shopping.clickToDynamicCategories("đ");

		log.info("---------------------------TC_02_STEP_3: lay tong tien can thanh toan");

		String tottalMoneyCartString = shopping.getDynamicTextPricesByText("Miễn phí giao hàng cho đơn từ ").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("---------------------------TC_02_STEP_4: click dat hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");

		log.info("---------------------------TC_02_STEP_4: click dat hang");
		shopping.clickToDynamicCart("1", "0");

		log.info("---------------------------TC_02_STEP_4: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("2");

		log.info("---------------------------TC_02_STEP_5: click thanh toán");
		shopping.clickToDynamicButton("Đặt hàng");

		log.info("---------------------------TC_02_STEP_5: click thanh toán");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("---------------------------TC_02_STEP_6: click thanh toan ngay");
		shopping.clickToDynamicDateInDateTimePicker("Thanh toán ngay");

		shopping.clickToDynamicButton("Đồng ý");

		log.info("---------------------------TC_02_STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.ACCOUNT2);

		log.info("---------------------------TC_02_STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("---------------------------TC_02_STEP_10: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", ""));

		log.info("---------------------------TC_02_STEP_11: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Tổng tiền:").split(" ");
		double totalMoney = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) tottalMoneyCart, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("---------------------------TC_02_STEP_12: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("---------------------------TC_02_STEP_13: Kiem tra tai khoan nguon");
		String account = shopping.getMoneyByAccount("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.ACCOUNT2);

		log.info("---------------------------TC_02_STEP_14: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");

		log.info("---------------------------TC_02_STEP_16: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("---------------------------TC_02_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("---------------------------TC_02_STEP_18: dien otp");
		shopping.inputToDynamicOtp(otp, "Tiếp tục");

		log.info("---------------------------TC_02_STEP_19: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("---------------------------TC_02_STEP_20: thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");

	}

//	@Parameters({ "pass" })
//	@Test
	public void TC_03_ChonMuaMotSanPhamThanhToanMKKhongChonKhuyenMai(String pass) {
		log.info("---------------------------TC_03_STEP_2: Them vao gio hang");
		shopping.clickToDynamicCategories("Xem tất cả");
		shopping.clickToDynamicCategories("đ");

		log.info("---------------------------TC_03STEP_3: lay tong tien can thanh toan");

		String tottalMoneyCartString = shopping.getDynamicTextPricesByText("Miễn phí giao hàng cho đơn từ ").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("---------------------------TC_03STEP_4: click dat hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");

		log.info("---------------------------TC_03_STEP_4: click dat hang");
		shopping.clickToDynamicCart("1", "0");

		log.info("---------------------------TC_03STEP_4: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("---------------------------TC_03_STEP_5: click thanh toán");
		shopping.clickToDynamicButton("Đặt hàng");

		log.info("---------------------------TC_03_STEP_5: click thanh toán");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("---------------------------TC_03_STEP_6: click thanh toan ngay");
		shopping.clickToDynamicDateInDateTimePicker("Thanh toán ngay");

		shopping.clickToDynamicButton("Đồng ý");

		log.info("---------------------------TC_03_STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.ACCOUNT2);

		log.info("---------------------------TC_03_STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("T---------------------------TC_03_STEP_10: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", ""));

		log.info("---------------------------TC_03_STEP_11: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Tổng tiền:").split(" ");
		double totalMoney = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) tottalMoneyCart, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("---------------------------TC_03STEP_12: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("---------------------------TC_03_STEP_13: Kiem tra tai khoan nguon");
		String account = shopping.getMoneyByAccount("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.ACCOUNT2);

		log.info("---------------------------TC_03_STEP_14: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");

		log.info("---------------------------TC_03_STEP_16: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");

		log.info("---------------------------TC_03_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("---------------------------TC_03_STEP_18: dien otp");
		shopping.inputToDynamicInputBox(pass, "Nhập mật khẩu");

		log.info("---------------------------TC_03_STEP_19: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("---------------------------TC_03_STEP_20: thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("---------------------------TC_03STEP_20: thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");
	}

//	@Parameters({ "pass" })
//	@Test
	public void TC_04_ChonMuaNhieuSanPhamThanhToanMKKhongChonKhuyenMai(String pass) {
		log.info("---------------------------TC_04_STEP_2: Them vao gio hang");
		shopping.clickToDynamicCategories("Xem tất cả");
		shopping.clickToDynamicCategories("đ");

		log.info("---------------------------TC_04_STEP_3: lay tong tien can thanh toan");

		String tottalMoneyCartString = shopping.getDynamicTextPricesByText("Miễn phí giao hàng cho đơn từ ").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("---------------------------TC_04_STEP_4: click dat hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");

		log.info("---------------------------TC_04_STEP_4: click dat hang");
		shopping.clickToDynamicCart("1", "0");

		log.info("---------------------------TC_04_STEP_4: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("2");

		log.info("---------------------------TC_04: click thanh toán");
		shopping.clickToDynamicButton("Đặt hàng");

		log.info("---------------------------TC_04: click thanh toán");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("---------------------------TC_04: click thanh toan ngay");
		shopping.clickToDynamicDateInDateTimePicker("Thanh toán ngay");

		shopping.clickToDynamicButton("Đồng ý");

		log.info("---------------------------TC_04: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.ACCOUNT2);

		log.info("---------------------------TC_04: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("---------------------------TC_04: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", ""));

		log.info("---------------------------TC_04: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Tổng tiền:").split(" ");
		double totalMoney = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) tottalMoneyCart, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("---------------------------TC_04 Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("---------------------------TC_04: Kiem tra tai khoan nguon");
		String account = shopping.getMoneyByAccount("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.ACCOUNT2);

		log.info("---------------------------TC_04: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");

		log.info("---------------------------TC_04: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");

		log.info("---------------------------TC_04: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("---------------------------TC_04: dien otp");
		shopping.inputToDynamicInputBox(pass, "Nhập mật khẩu");

		log.info("---------------------------TC_04 Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("---------------------------TC_04 thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");


	}

	@Parameters({ "otp" })
	public void TC_05_ChonMuaMotSanPhamCoKhuyenMaiThanhToanOTP(String otp) {
		log.info("---------------------------TC_05_STEP_2: Them vao gio hang");
		shopping.clickToDynamicCategories("%");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText("Miễn phí giao hàng cho đơn từ ").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("---------------------------TC_05_ click dat hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");

		log.info("T---------------------------TC_05_: click dat hang");
		shopping.clickToDynamicCart("1", "0");

		log.info("---------------------------TC_05_: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("---------------------------TC_05_: click thanh toán");
		shopping.clickToDynamicButton("Đặt hàng");

		log.info("---------------------------TC_05__STEP_5: click thanh toán");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("---------------------------TC_05_STEP_6: click thanh toan ngay");
		shopping.clickToDynamicDateInDateTimePicker("Thanh toán ngay");

		shopping.clickToDynamicButton("Đồng ý");

		log.info("---------------------------TC_05__STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.ACCOUNT2);

		log.info("---------------------------TC_05__STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("---------------------------TC_05__STEP_10: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", ""));

		log.info("---------------------------TC_05__STEP_11: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Tổng tiền:").split(" ");
		double totalMoney = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) tottalMoneyCart, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("---------------------------TC_05__STEP_12: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("---------------------------TC_05__STEP_13: Kiem tra tai khoan nguon");
		String account = shopping.getMoneyByAccount("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.ACCOUNT2);

		log.info("---------------------------TC_05__STEP_14: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");
		
		log.info("---------------------------TC_05__STEP_16: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("---------------------------TC_05_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("---------------------------TC_05__STEP_18: dien otp");
		shopping.inputToDynamicOtp(otp, "Tiếp tục");

		log.info("---------------------------TC_05__STEP_20: thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
