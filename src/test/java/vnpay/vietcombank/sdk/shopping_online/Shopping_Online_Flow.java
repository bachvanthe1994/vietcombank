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
		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login1(phone, pass, opt);

		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, "© 2019 Vietcombank");
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Mua sắm trực tuyến");
		homePage.waitForElementInvisible(driver, "android.widget.Image[@text='VNShop']");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_ChonMuaMotSanPhamThanhToanOTPKhongChonKhuyenMai(String otp) {
		log.info("TC_01_STEP_1: Lay danh sach gia tri loai chuyen tien");
		shopping.clickChooseOrrder(1, "Bán chạy trong tháng");

		log.info("TC_01_STEP_2: Them vao gio hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");
		
		log.info("TC_01_STEP_3: click icon gio hang");
		shopping.clickToDynamicCart("1", "1");

		log.info("TC_01_STEP_3: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextView("VNPAY Shop", "3").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("TC_01_STEP_4: click dat hang");
		shopping.clickToDynamicButton("Đặt hàng");

		log.info("TC_01_STEP_5: click thanh toán");
		shopping.clickToDynamicButton("Thanh toán");
		
		if (shopping.isDynamicTextInfoDisplayed("Thông báo") == true) {
			log.info("TC_01_STEP_4_1: click the moi");
			shopping.clickToDynamicButton("Thêm mới");
			
			log.info("TC_01_STEP_4_2: nhap ten");
			shopping.clickToDynamicDateInDateTimePicker("Họ tên người nhận");
			shopping.inputToDynamicUser("Họ tên người nhận", "Họ tên người nhận", "0");
			
			log.info("TC_01_STEP_4_2: nhap so dien thoai");
			shopping.inputToDynamicUser("Ngyen Van A", "Số điện thoại người nhận");
			
			log.info("TC_01_STEP_4_3: chon tinh thanh pho");
			shopping.clickToDynamicCustomer("Tỉnh/Thành phố");
			shopping.clickToDynamicListProvince("Thành phố Hà Nội");
			
			log.info("TC_01_STEP_4_3: chon tinh quan huyen");
			shopping.clickToDynamicCustomer("Quận/Huyện");
			shopping.clickToDynamicListProvince("Quận Ba Đình");
			
			log.info("TC_01_STEP_4_3: chon tinh xa phuong");
			shopping.clickToDynamicCustomer("Quận/Huyện");
			shopping.clickToDynamicListProvince("Phường Biên Giang");
			
			log.info("TC_01_STEP_4_3: dia chi cu the");
			shopping.inputToDynamicUser("22 abc", "Địa chỉ cụ thể (Số nhà, tên đường...)");
			
			log.info("TC_01_STEP_4_3: chon hoan tat");
			shopping.clickToDynamicButton("Hoàn tất");
		}

		log.info("TC_01_STEP_6: click thanh toan");
		shopping.clickToDynamicDateInDateTimePicker("Thanh toán ngay");

		log.info("TC_01_STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

		log.info("TC_01_STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_01_STEP_10: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", ""));

		log.info("TC_01_STEP_11: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Tổng tiền:").split(" ");
		double totalMoney = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) tottalMoneyCart, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_01_STEP_12: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_01_STEP_13: Kiem tra tai khoan nguon");
		String account = shopping.getMoneyByAccount("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

		log.info("TC_01_STEP_14: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");

		log.info("TC_01_STEP_16: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("TC_01_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_STEP_18: dien otp");
		shopping.inputToDynamicOtp(otp, "Tiếp tục");

		log.info("TC_01_STEP_19: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_STEP_20: thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");
	}

	@Parameters({ "otp" })
//	@Test
	public void TC_02_ChonMuaNhieuSanPhamThanhToanOTPKhongChonKhuyenMai(String otp) {
		log.info("TC_02_STEP_1: Lay danh sach gia tri loai chuyen tien");
		shopping.clickChooseOrrder(1, "Bán chạy trong tháng");
	
		log.info("TC_02_STEP_2: Them vao gio hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");
		
		log.info("TC_01_STEP_1: khong chon khuyen mai");
		shopping.clickToDynamicDisCount("Chọn thêm 1 trong những khuyến mại sau", "0");

		log.info("TC_02_STEP_3: click icon gio hang");
		shopping.clickToDynamicCart("1", "1");

		log.info("TC_02_STEP_3: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextView("Chi tiết").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("TC_02_STEP_4: click dat hang");
		shopping.clickToDynamicButton("Đặt hàng");

		log.info("TC_02_STEP_5: click thanh toán");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_02_STEP_6: click thanh toan");
		shopping.clickToDynamicDateInDateTimePicker("Thanh toán ngay");

		log.info("TC_02_STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);


		log.info("TC_02_STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_02_STEP_10: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", ""));

		log.info("TC_02_STEP_12: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Tổng tiền:").split(" ");
		double totalMoney = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) tottalMoneyCart, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_02_STEP_13: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_02_STEP_14: Kiem tra tai khoan nguon");
		String account = shopping.getMoneyByAccount("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

		log.info("TC_02_STEP_15: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");


		log.info("TC_02_STEP_17: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("TC_02_STEP_18: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_02_STEP_19: dien otp");
		shopping.inputToDynamicOtp(otp, "Tiếp tục");

		log.info("TC_02_STEP_20: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_02_STEP_21: thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");
	}

	@Parameters({ "pass" })
//	@Test
	public void TC_03_ChonMuaMotSanPhamThanhToanMKKhongChonKhuyenMai(String pass) {
		log.info("TC_03_STEP_1: Lay danh sach gia tri loai chuyen tien");
		shopping.clickChooseOrrder(1, "Bán chạy trong tháng");
		
		log.info("TC_03_STEP_2: Them vao gio hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");
		
		log.info("TC_01_STEP_1: khong chon khuyen mai");
		shopping.clickToDynamicDisCount("Chọn thêm 1 trong những khuyến mại sau", "0");

		log.info("TC_03_STEP_3: click icon gio hang");
		shopping.clickToDynamicCart("1", "1");

		log.info("TC_03_STEP_3: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextView("Chi tiết").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("TC_03_STEP_4: click dat hang");
		shopping.clickToDynamicButton("Đặt hàng");

		log.info("TC_03_STEP_5: click thanh toán");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_03_STEP_6: click thanh toan");
		shopping.clickToDynamicDateInDateTimePicker("Thanh toán ngay");

		log.info("TC_03_STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);


		log.info("TC_03_STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_03_STEP_10: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", ""));

		log.info("TC_03_STEP_11: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Tổng tiền:").split(" ");
		double totalMoney = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) tottalMoneyCart, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_03_STEP_12: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_03_STEP_13: Kiem tra tai khoan nguon");
		String account = shopping.getMoneyByAccount("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

		log.info("TC_03_STEP_14: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");


		log.info("TC_03_STEP_16: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");

		log.info("TC_03_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_STEP_18: dien otp");
		shopping.inputToDynamicInputBox(pass, "Nhập mật khẩu");

		log.info("TC_03_STEP_19: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_STEP_20: thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");
	}

	@Parameters({ "pass" })
//	@Test
	public void TC_04_ChonMuaNhieuSanPhamThanhToanMKKhongChonKhuyenMai(String pass) {
		log.info("TC_04_STEP_1: Lay danh sach gia tri loai chuyen tien");
		shopping.clickChooseOrrder(1, "Bán chạy trong tháng");

		log.info("TC_04_STEP_2: Them vao gio hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");
		
		log.info("TC_01_STEP_1: khong chon khuyen mai");
		shopping.clickToDynamicDisCount("Chọn thêm 1 trong những khuyến mại sau", "0");

		log.info("TC_04_STEP_3: click icon gio hang");
		shopping.clickToDynamicCart("1", "1");

		log.info("TC_04_STEP_3: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextView("Chi tiết").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("TC_04_STEP_4: click dat hang");
		shopping.clickToDynamicButton("Đặt hàng");

		log.info("TC_04_STEP_5: click thanh toán");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_04_STEP_6: click thanh toan");
		shopping.clickToDynamicDateInDateTimePicker("Thanh toán ngay");

		log.info("TC_04_STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);


		log.info("TC_04_STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_04_STEP_10: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", ""));

		log.info("TC_04_STEP_11: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Tổng tiền:").split(" ");
		double totalMoney = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) tottalMoneyCart, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_04_STEP_12: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_04_STEP_13: Kiem tra tai khoan nguon");
		String account = shopping.getMoneyByAccount("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

		log.info("TC_04_STEP_14: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");


		log.info("TC_04_STEP_16: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");

		log.info("TC_04_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_04_STEP_18: dien otp");
		shopping.inputToDynamicInputBox(pass, "Nhập mật khẩu");

		log.info("TC_04_STEP_19: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_04_STEP_20: thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");
	}

	@Parameters({ "otp" })
//	@Test
	public void TC_05_ChonMuaMotSanPhamCoKhuyenMaiThanhToanOTP(String otp) {
		log.info("TC_01_STEP_1: Lay danh sach gia tri loai chuyen tien");
		shopping.clickChooseOrrder(1, "Bán chạy trong tháng");
		
		log.info("TC_01_STEP_2: Them vao gio hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");

		log.info("TC_01_STEP_3: click icon gio hang");
		shopping.clickToDynamicCart("1", "1");

		log.info("TC_01_STEP_3: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextView("Chi tiết").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("TC_01_STEP_4: click dat hang");
		shopping.clickToDynamicButton("Đặt hàng");

		log.info("TC_01_STEP_5: click thanh toán");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_01_STEP_6: click thanh toan");
		shopping.clickToDynamicDateInDateTimePicker("Thanh toán ngay");

		log.info("TC_01_STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

		log.info("TC_01_STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_01_STEP_10: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", ""));

		log.info("TC_01_STEP_11: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Tổng tiền:").split(" ");
		double totalMoney = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) tottalMoneyCart, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_01_STEP_12: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_01_STEP_13: Kiem tra tai khoan nguon");
		String account = shopping.getMoneyByAccount("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

		log.info("TC_01_STEP_14: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");

		log.info("TC_01_STEP_16: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("TC_01_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_STEP_18: dien otp");
		shopping.inputToDynamicOtp(otp, "Tiếp tục");

		log.info("TC_01_STEP_19: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_STEP_20: thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");
	}
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
