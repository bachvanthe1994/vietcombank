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

public class Shopping_Online_Flow1 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private ShoppingOnlinePageObject shopping;
	private HomePageObject homePage;
	String transferTime;
	String transactionNumber;
	List<String> listActual;
	double soDuThuc = 0;
	String maGiaodich = "";
	int indexHang = 0;

	long amount, amountStart, feeView, amountView, amountAfter = 0;

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
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Mua sắm trực tuyến - VNPAY Shopping");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);
		shopping.sleep(driver, 5000);
	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_ChonMuaMotSanPhamThanhToanOTPKhongChonKhuyenMai(String otp) {
		log.info("TC_01_STEP_2: Them vao gio hang");
		shopping.clickToDynamicTextContains("Xem tất cả");

		List<String> listProduct = shopping.getTextInListElementsProduct( "đ");
		
		for (int i = 4; i < listProduct.size(); i++) {
			shopping.clickToDynamicView(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource("Tạm hết hàng")) {
				log.info("TC_01_STEP_5: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_01_STEP_4: click dat hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");

		log.info("TC_01_STEP_4: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("TC_01_STEP_3: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
		log.info("TC_01_STEP_5: click dat hang");
		shopping.clickToDynamicButton("Đặt hàng");

		shopping.scrollDownToTextView("Giao hàng tiêu chuẩn");
		String feeShipping = shopping.getDynamicTextTableByTextView("Giao hàng tiêu chuẩn");
		double feeShippingD = 0;
		if (feeShipping.equals("Miễn phí")) {
			feeShipping = feeShipping.replace("Miễn phí", "0");
			feeShippingD = 0;

		} else {
			feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
			feeShippingD = Double.parseDouble(feeShipping);

		}
		double totalMoney = tottalMoneyCart + feeShippingD;

		log.info("TC_01_STEP_5: click thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		if (shopping.isTextDisplayedInPageSource("Bạn chưa có địa chỉ nhận hàng.")) {

			log.info("TC_01_STEP_4_1: click the moi");
			shopping.clickToDynamicButton("Thêm mới");

			log.info("TC_01_STEP_4_1: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed("Thêm mới địa chỉ");

			log.info("TC_01_STEP_4_2: nhap ten");
			shopping.inputToDynamicInfo("Ngyen Van A", "Họ tên người nhận");

			log.info("TC_01_STEP_4_2: nhap so dien thoai");
			shopping.inputToDynamicInfo("0904797866", "Số điện thoại người nhận");

			log.info("TC_01_STEP_4_3: chon tinh thanh pho");
			shopping.clickToDynamicCustomer("Tỉnh/Thành phố");
			shopping.clickToDynamicListProvince("Thành phố Hà Nội");

			log.info("TC_01_STEP_4_3: chon tinh quan huyen");
			shopping.clickToDynamicCustomer("Quận/Huyện");
			shopping.clickToDynamicListProvince("Quận Ba Đình");

			log.info("TC_01_STEP_4_3: chon tinh xa phuong");
			shopping.clickToDynamicCustomer("Phường/Xã");
			shopping.clickToDynamicListProvince("Phường Cống Vị");

			log.info("TC_01_STEP_4_3: dia chi cu the");
			shopping.inputToDynamicInfo("22 abc", "Địa chỉ cụ thể (Số nhà, tên đường...)");

			log.info("TC_01_STEP_4_3: chon hoan tat");
			shopping.clickToDynamicButton("Hoàn tất");

			log.info("TC_01_STEP_6: click thanh toan");
			shopping.clickToDynamicButton("Thanh toán");
		}

		if (shopping.isTextDisplayedInPageSource("Sản phẩm không còn")) {
			shopping.clickToDynamicButton("Quay về giỏ hàng");

			shopping.clickToDynamicCart("2", "0");
			shopping.clickToDynamicButton("Mua sắm ngay");

			shopping.clickToDynamicCategories("Xem tất cả");

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicView(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource("Tạm hết hàng")) {
					log.info("TC_01_STEP_5: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_01_STEP_4: click dat hang");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");

			log.info("TC_01_STEP_4: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_01_STEP_3: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("TC_01_STEP_5: click dat hang");
			shopping.clickToDynamicButton("Đặt hàng");

			shopping.scrollDownToTextView("Giao hàng tiêu chuẩn");
			feeShipping = shopping.getDynamicTextTableByTextView("Giao hàng tiêu chuẩn");
			feeShippingD = 0;
			if (feeShipping.equals("Miễn phí")) {
				feeShipping = feeShipping.replace("Miễn phí", "0");
				feeShippingD = 0;

			} else {
				feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
				feeShippingD = Double.parseDouble(feeShipping);

			}
			totalMoney = tottalMoneyCart + feeShippingD;

			log.info("TC_01_STEP_5: click thanh toan");
			shopping.clickToDynamicButton("Thanh toán");

			log.info("TC_01_STEP_3: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_01_STEP_4: click dat hang");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");

			log.info("TC_01_STEP_4: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_01_STEP_5: click dat hang");
			shopping.clickToDynamicButton("Đặt hàng");

		}

		log.info("TC_01_STEP_6: click thanh toan ngay");
		shopping.clickToDynamicButton("Đồng ý");

		log.info("TC_01_STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.ACCOUNT2);

		log.info("TC_02_STEP_7: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView("Số dư khả dụng").replace("VND", "").replace(",", ""));

		log.info("TC_02_STEP_7: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_01_STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_01_STEP_9: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		log.info("TC_01_STEP_10: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_01_STEP_11: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Giá trị đơn hàng:").split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_01_STEP_12: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_01_STEP_12: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed("Xác nhận thông tin");

		verifyEquals(shopping.getDynamicTextTableByTextView("Mã đơn hàng"), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView("Số tiền thanh toán").contains(totalMoneyBillString[1]));

		log.info("TC_01_STEP_13: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.ACCOUNT2);

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

		shopping.isDynamicMessageAndLabelTextDisplayed("GIAO DỊCH THÀNH CÔNG");

		soDuThuc = soDuTK - moneyConfirm;

		log.info("TC_01_STEP_19: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView("Nhà cung cấp"), "VNSHOP");

		verifyEquals(shopping.getDynamicTextTableByTextView("Mã đơn hàng"), codeBill);

		maGiaodich = shopping.getDynamicTextTableByTextView("Mã giao dịch");

		log.info("TC_01_STEP_20: thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");

	}

	@Parameters({ "otp" })
	@Test
	public void TC_02_ChonMuaNhieuSanPhamThanhToanOTPKhongChonKhuyenMai(String otp) {

		log.info("TC_02_STEP_: Them vao gio hang");
		shopping.clickToDynamicTextContains("Xem tất cả");

		List<String> listProduct = shopping.getTextInListElementsProduct( "đ");
		
		for (int i = 4; i < listProduct.size(); i++) {
			shopping.clickToDynamicView(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource("Tạm hết hàng")) {
				log.info("TC_01_STEP_5: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_02_STEP_: click dat hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");

		log.info("TC_02_STEP_: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("3");

		log.info("TC_02_STEP_: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
		log.info("TC_02_STEP_: click dat hang");
		shopping.clickToDynamicButton("Đặt hàng");

		shopping.scrollDownToTextView("Giao hàng tiêu chuẩn");
		String feeShipping = shopping.getDynamicTextTableByTextView("Giao hàng tiêu chuẩn");
		double feeShippingD = 0;
		if (feeShipping.equals("Miễn phí")) {
			feeShipping = feeShipping.replace("Miễn phí", "0");
			feeShippingD = 0;

		} else {
			feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
			feeShippingD = Double.parseDouble(feeShipping);

		}
		double totalMoney = tottalMoneyCart + feeShippingD;

		log.info("TC_02_STEP_: click thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		if (shopping.isTextDisplayedInPageSource("Bạn chưa có địa chỉ nhận hàng.")) {

			log.info("TC_02_STEP_: click the moi");
			shopping.clickToDynamicButton("Thêm mới");

			log.info("TC_02_STEP_: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed("Thêm mới địa chỉ");

			log.info("TC_02_STEP_: nhap ten");
			shopping.inputToDynamicInfo("Ngyen Van A", "Họ tên người nhận");

			log.info("TC_02_STEP_: nhap so dien thoai");
			shopping.inputToDynamicInfo("0904797866", "Số điện thoại người nhận");

			log.info("TC_02_STEP_: chon tinh thanh pho");
			shopping.clickToDynamicCustomer("Tỉnh/Thành phố");
			shopping.clickToDynamicListProvince("Thành phố Hà Nội");

			log.info("TC_02_STEP_: chon tinh quan huyen");
			shopping.clickToDynamicCustomer("Quận/Huyện");
			shopping.clickToDynamicListProvince("Quận Ba Đình");

			log.info("TC_02_STEP_: chon tinh xa phuong");
			shopping.clickToDynamicCustomer("Phường/Xã");
			shopping.clickToDynamicListProvince("Phường Cống Vị");

			log.info("TC_02_STEP_: dia chi cu the");
			shopping.inputToDynamicInfo("22 abc", "Địa chỉ cụ thể (Số nhà, tên đường...)");

			log.info("TC_02_STEP_: chon hoan tat");
			shopping.clickToDynamicButton("Hoàn tất");

			log.info("TC_02_STEP_: click thanh toan");
			shopping.clickToDynamicButton("Thanh toán");
		}

		if (shopping.isTextDisplayedInPageSource("Sản phẩm không còn")) {
			shopping.clickToDynamicButton("Quay về giỏ hàng");

			shopping.clickToDynamicCart("2", "0");
			shopping.clickToDynamicButton("Mua sắm ngay");

			shopping.clickToDynamicCategories("Xem tất cả");

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicView(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource("Tạm hết hàng")) {
					log.info("TC_02_STEP_: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_02_STEP_: click dat hang");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");

			log.info("TC_02_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

			log.info("TC_02_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("TC_02_STEP_: click dat hang");
			shopping.clickToDynamicButton("Đặt hàng");

			shopping.scrollDownToTextView("Giao hàng tiêu chuẩn");
			feeShipping = shopping.getDynamicTextTableByTextView("Giao hàng tiêu chuẩn");
			feeShippingD = 0;
			if (feeShipping.equals("Miễn phí")) {
				feeShipping = feeShipping.replace("Miễn phí", "0");
				feeShippingD = 0;

			} else {
				feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
				feeShippingD = Double.parseDouble(feeShipping);

			}
			totalMoney = tottalMoneyCart + feeShippingD;

			log.info("TC_02_STEP_: click thanh toan");
			shopping.clickToDynamicButton("Thanh toán");

			log.info("TC_02_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_02_STEP_: click dat hang");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");

			log.info("TC_02_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

			log.info("TC_02_STEP_: click dat hang");
			shopping.clickToDynamicButton("Đặt hàng");

		}

		log.info("TC_02_STEP_: click thanh toan ngay");
		shopping.clickToDynamicButton("Đồng ý");

		log.info("TC_02_STEP_: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.ACCOUNT2);

		log.info("TC_02_STEP_: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView("Số dư khả dụng").replace("VND", "").replace(",", ""));

		log.info("TC_02_STEP_: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_02_STEP_: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_02_STEP_: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		log.info("TC_02_STEP_: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_02_STEP_: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Giá trị đơn hàng:").split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_02_STEP_: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_02_STEP_: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed("Xác nhận thông tin");

		verifyEquals(shopping.getDynamicTextTableByTextView("Mã đơn hàng"), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView("Số tiền thanh toán").contains(totalMoneyBillString[1]));

		log.info("TC_02_STEP_: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.ACCOUNT2);

		log.info("TC_02_STEP_: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");

		log.info("TC_02_STEP_: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("TC_02_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_02_STEP_: dien otp");
		shopping.inputToDynamicOtp(otp, "Tiếp tục");

		log.info("TC_02_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		shopping.isDynamicMessageAndLabelTextDisplayed("GIAO DỊCH THÀNH CÔNG");

		soDuThuc = soDuTK - moneyConfirm;

		log.info("TC_02_STEP_: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView("Nhà cung cấp"), "VNSHOP");

		verifyEquals(shopping.getDynamicTextTableByTextView("Mã đơn hàng"), codeBill);

		maGiaodich = shopping.getDynamicTextTableByTextView("Mã giao dịch");

		log.info("TC_02_STEP_: thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");

	}

	@Parameters({ "pass" })
	@Test
	public void TC_03_ChonMuaMotSanPhamThanhToanMKKhongChonKhuyenMai(String pass) {
		log.info("TC_03_STEP_: Them vao gio hang");
		shopping.clickToDynamicTextContains("Xem tất cả");

		List<String> listProduct = shopping.getTextInListElementsProduct( "đ");
		
		for (int i = 4; i < listProduct.size(); i++) {
			shopping.clickToDynamicView(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource("Tạm hết hàng")) {
				log.info("TC_03_STEP_: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_03_STEP_: click dat hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");

		log.info("TC_03_STEP_: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("TC_03_STEP_: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
		log.info("TC_03_STEP_: click dat hang");
		shopping.clickToDynamicButton("Đặt hàng");

		shopping.scrollDownToTextView("Giao hàng tiêu chuẩn");
		String feeShipping = shopping.getDynamicTextTableByTextView("Giao hàng tiêu chuẩn");
		double feeShippingD = 0;
		if (feeShipping.equals("Miễn phí")) {
			feeShipping = feeShipping.replace("Miễn phí", "0");
			feeShippingD = 0;

		} else {
			feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
			feeShippingD = Double.parseDouble(feeShipping);

		}
		double totalMoney = tottalMoneyCart + feeShippingD;

		log.info("TC_03_STEP_: click thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		if (shopping.isTextDisplayedInPageSource("Bạn chưa có địa chỉ nhận hàng.")) {

			log.info("TC_03_STEP_: click the moi");
			shopping.clickToDynamicButton("Thêm mới");

			log.info("TC_03_STEP_: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed("Thêm mới địa chỉ");

			log.info("TC_03_STEP_: nhap ten");
			shopping.inputToDynamicInfo("Ngyen Van A", "Họ tên người nhận");

			log.info("TC_03_STEP_: nhap so dien thoai");
			shopping.inputToDynamicInfo("0904797866", "Số điện thoại người nhận");

			log.info("TC_03_STEP_: chon tinh thanh pho");
			shopping.clickToDynamicCustomer("Tỉnh/Thành phố");
			shopping.clickToDynamicListProvince("Thành phố Hà Nội");

			log.info("TC_03_STEP_: chon tinh quan huyen");
			shopping.clickToDynamicCustomer("Quận/Huyện");
			shopping.clickToDynamicListProvince("Quận Ba Đình");

			log.info("TC_03_STEP_: chon tinh xa phuong");
			shopping.clickToDynamicCustomer("Phường/Xã");
			shopping.clickToDynamicListProvince("Phường Cống Vị");

			log.info("TC_03_STEP_: dia chi cu the");
			shopping.inputToDynamicInfo("22 abc", "Địa chỉ cụ thể (Số nhà, tên đường...)");

			log.info("TC_03_STEP_: chon hoan tat");
			shopping.clickToDynamicButton("Hoàn tất");

			log.info("TC_03_STEP_: click thanh toan");
			shopping.clickToDynamicButton("Thanh toán");
		}

		if (shopping.isTextDisplayedInPageSource("Sản phẩm không còn")) {
			shopping.clickToDynamicButton("Quay về giỏ hàng");

			shopping.clickToDynamicCart("2", "0");
			shopping.clickToDynamicButton("Mua sắm ngay");

			shopping.clickToDynamicCategories("Xem tất cả");

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicView(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource("Tạm hết hàng")) {
					log.info("TC_01_STEP_5: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_03_STEP_: click dat hang");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");

			log.info("TC_03_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_03_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("TC_03_STEP_: click dat hang");
			shopping.clickToDynamicButton("Đặt hàng");

			shopping.scrollDownToTextView("Giao hàng tiêu chuẩn");
			feeShipping = shopping.getDynamicTextTableByTextView("Giao hàng tiêu chuẩn");
			feeShippingD = 0;
			if (feeShipping.equals("Miễn phí")) {
				feeShipping = feeShipping.replace("Miễn phí", "0");
				feeShippingD = 0;

			} else {
				feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
				feeShippingD = Double.parseDouble(feeShipping);

			}
			totalMoney = tottalMoneyCart + feeShippingD;

			log.info("TC_03_STEP_: click thanh toan");
			shopping.clickToDynamicButton("Thanh toán");

			log.info("TC_03_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_03_STEP_: click dat hang");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");

			log.info("TC_03_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_03_STEP_: click dat hang");
			shopping.clickToDynamicButton("Đặt hàng");

		}

		log.info("TC_03_STEP_: click thanh toan ngay");
		shopping.clickToDynamicButton("Đồng ý");

		log.info("TC_03_STEP_: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.ACCOUNT2);

		log.info("TC_03_STEP_: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView("Số dư khả dụng").replace("VND", "").replace(",", ""));

		log.info("TC_03_STEP_: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_03_STEP_: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_03_STEP_: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		log.info("TC_03_STEP_: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_03_STEP_: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Giá trị đơn hàng:").split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_03_STEP_: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_03_STEP_: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed("Xác nhận thông tin");

		verifyEquals(shopping.getDynamicTextTableByTextView("Mã đơn hàng"), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView("Số tiền thanh toán").contains(totalMoneyBillString[1]));

		log.info("TC_03_STEP_: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.ACCOUNT2);

		log.info("TC_03_STEP_: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");

		log.info("TC_03_STEP_: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");

		log.info("TC_03_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_STEP_: dien otp");
		shopping.inputToDynamicPopupPasswordInput(pass, "Tiếp tục");

		log.info("TC_03_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		shopping.isDynamicMessageAndLabelTextDisplayed("GIAO DỊCH THÀNH CÔNG");

		soDuThuc = soDuTK - moneyConfirm;

		log.info("TC_03_STEP_: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView("Nhà cung cấp"), "VNSHOP");

		verifyEquals(shopping.getDynamicTextTableByTextView("Mã đơn hàng"), codeBill);

		maGiaodich = shopping.getDynamicTextTableByTextView("Mã giao dịch");

		log.info("TC_03_STEP_: thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_04_ChonMuaNhieuSanPhamThanhToanMKKhongChonKhuyenMai(String pass) {
		log.info("TC_04_STEP_: Them vao gio hang");
		shopping.clickToDynamicTextContains("Xem tất cả");

		List<String> listProduct = shopping.getTextInListElementsProduct( "đ");
		
		for (int i = 4; i < listProduct.size(); i++) {
			shopping.clickToDynamicView(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource("Tạm hết hàng")) {
				log.info("TC_04_STEP_: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_04_STEP_: click dat hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");

		log.info("TC_04_STEP_: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("3");

		log.info("TC_04_STEP_: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
		log.info("TC_04_STEP_: click dat hang");
		shopping.clickToDynamicButton("Đặt hàng");

		shopping.scrollDownToTextView("Giao hàng tiêu chuẩn");
		String feeShipping = shopping.getDynamicTextTableByTextView("Giao hàng tiêu chuẩn");
		double feeShippingD = 0;
		if (feeShipping.equals("Miễn phí")) {
			feeShipping = feeShipping.replace("Miễn phí", "0");
			feeShippingD = 0;

		} else {
			feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
			feeShippingD = Double.parseDouble(feeShipping);

		}
		double totalMoney = tottalMoneyCart + feeShippingD;

		log.info("TC_04_STEP_: click thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		if (shopping.isTextDisplayedInPageSource("Bạn chưa có địa chỉ nhận hàng.")) {

			log.info("TC_04_STEP_: click the moi");
			shopping.clickToDynamicButton("Thêm mới");

			log.info("TC_04_STEP_: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed("Thêm mới địa chỉ");

			log.info("TC_04_STEP_: nhap ten");
			shopping.inputToDynamicInfo("Ngyen Van A", "Họ tên người nhận");

			log.info("TC_02_STEP_: nhap so dien thoai");
			shopping.inputToDynamicInfo("0904797866", "Số điện thoại người nhận");

			log.info("TC_04_STEP_: chon tinh thanh pho");
			shopping.clickToDynamicCustomer("Tỉnh/Thành phố");
			shopping.clickToDynamicListProvince("Thành phố Hà Nội");

			log.info("TC_04_STEP_: chon tinh quan huyen");
			shopping.clickToDynamicCustomer("Quận/Huyện");
			shopping.clickToDynamicListProvince("Quận Ba Đình");

			log.info("TC_04_STEP_: chon tinh xa phuong");
			shopping.clickToDynamicCustomer("Phường/Xã");
			shopping.clickToDynamicListProvince("Phường Cống Vị");

			log.info("TC_04_STEP_: dia chi cu the");
			shopping.inputToDynamicInfo("22 abc", "Địa chỉ cụ thể (Số nhà, tên đường...)");

			log.info("TC_04_STEP_: chon hoan tat");
			shopping.clickToDynamicButton("Hoàn tất");

			log.info("TC_04_STEP_: click thanh toan");
			shopping.clickToDynamicButton("Thanh toán");
		}

		if (shopping.isTextDisplayedInPageSource("Sản phẩm không còn")) {
			shopping.clickToDynamicButton("Quay về giỏ hàng");

			shopping.clickToDynamicCart("2", "0");
			shopping.clickToDynamicButton("Mua sắm ngay");

			shopping.clickToDynamicCategories("Xem tất cả");

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicView(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource("Tạm hết hàng")) {
					log.info("TC_04_STEP_: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_04_STEP_: click dat hang");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");

			log.info("TC_04_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

			log.info("TC_04_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("TC_04_STEP_: click dat hang");
			shopping.clickToDynamicButton("Đặt hàng");

			shopping.scrollDownToTextView("Giao hàng tiêu chuẩn");
			feeShipping = shopping.getDynamicTextTableByTextView("Giao hàng tiêu chuẩn");
			feeShippingD = 0;
			if (feeShipping.equals("Miễn phí")) {
				feeShipping = feeShipping.replace("Miễn phí", "0");
				feeShippingD = 0;

			} else {
				feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
				feeShippingD = Double.parseDouble(feeShipping);

			}
			totalMoney = tottalMoneyCart + feeShippingD;

			log.info("TC_04_STEP_: click thanh toan");
			shopping.clickToDynamicButton("Thanh toán");

			log.info("TC_04_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_04_STEP_: click dat hang");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");

			log.info("TC_04_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

			log.info("TC_04_STEP_: click dat hang");
			shopping.clickToDynamicButton("Đặt hàng");

		}

		log.info("TC_04_STEP_: click thanh toan ngay");
		shopping.clickToDynamicButton("Đồng ý");

		log.info("TC_04_STEP_: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.ACCOUNT2);

		log.info("TC_04_STEP_: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView("Số dư khả dụng").replace("VND", "").replace(",", ""));

		log.info("TC_04_STEP_: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_04_STEP_: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_04_STEP_: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		log.info("TC_04_STEP_: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_04_STEP_: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Giá trị đơn hàng:").split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_04_STEP_: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_04_STEP_: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed("Xác nhận thông tin");

		verifyEquals(shopping.getDynamicTextTableByTextView("Mã đơn hàng"), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView("Số tiền thanh toán").contains(totalMoneyBillString[1]));

		log.info("TC_04_STEP_: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.ACCOUNT2);

		log.info("TC_04_STEP_: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");

		log.info("TC_04_STEP_: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");

		log.info("TC_04_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_04_STEP_: dien otp");
		shopping.inputToDynamicPopupPasswordInput(pass, "Tiếp tục");

		log.info("TC_04_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		shopping.isDynamicMessageAndLabelTextDisplayed("GIAO DỊCH THÀNH CÔNG");

		soDuThuc = soDuTK - moneyConfirm;

		log.info("TC_04_STEP_: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView("Nhà cung cấp"), "VNSHOP");

		verifyEquals(shopping.getDynamicTextTableByTextView("Mã đơn hàng"), codeBill);

		maGiaodich = shopping.getDynamicTextTableByTextView("Mã giao dịch");

		log.info("TC_04_STEP_: thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");

	}

	@Parameters({ "otp" })
	@Test
	public void TC_05_ChonMuaMotSanPhamCoKhuyenMaiThanhToanOTP(String otp) {
		log.info("TC_05_STEP_: Them vao gio hang");
		shopping.scrollIDownOneTime(driver);
		shopping.scrollDownToConatainText("%");
		List<String> listProduct = shopping.getTextInListElementsProduct( "%");
		
		for (int i = 0; i < listProduct.size(); i++) {
			shopping.clickToDynamicView(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource("Tạm hết hàng")) {
				log.info("TC_01_STEP_5: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_05_STEP_: click dat hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");

		log.info("TC_05_STEP_: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("TC_05_STEP_: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
		log.info("TC_05_STEP_: click dat hang");
		shopping.clickToDynamicButton("Đặt hàng");

		shopping.scrollDownToTextView("Giao hàng tiêu chuẩn");
		String feeShipping = shopping.getDynamicTextTableByTextView("Giao hàng tiêu chuẩn");
		double feeShippingD = 0;
		if (feeShipping.equals("Miễn phí")) {
			feeShipping = feeShipping.replace("Miễn phí", "0");
			feeShippingD = 0;

		} else {
			feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
			feeShippingD = Double.parseDouble(feeShipping);

		}
		double totalMoney = tottalMoneyCart + feeShippingD;

		log.info("TC_05_STEP_: click thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		if (shopping.isTextDisplayedInPageSource("Bạn chưa có địa chỉ nhận hàng.")) {

			log.info("TC_05_STEP_: click the moi");
			shopping.clickToDynamicButton("Thêm mới");

			log.info("TC_05_STEP_: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed("Thêm mới địa chỉ");

			log.info("TC_05_STEP_: nhap ten");
			shopping.inputToDynamicInfo("Ngyen Van A", "Họ tên người nhận");

			log.info("TC_05_STEP_: nhap so dien thoai");
			shopping.inputToDynamicInfo("0904797866", "Số điện thoại người nhận");

			log.info("TC_05_STEP_: chon tinh thanh pho");
			shopping.clickToDynamicCustomer("Tỉnh/Thành phố");
			shopping.clickToDynamicListProvince("Thành phố Hà Nội");

			log.info("TC_05_STEP_: chon tinh quan huyen");
			shopping.clickToDynamicCustomer("Quận/Huyện");
			shopping.clickToDynamicListProvince("Quận Ba Đình");

			log.info("TC_05_STEP_: chon tinh xa phuong");
			shopping.clickToDynamicCustomer("Phường/Xã");
			shopping.clickToDynamicListProvince("Phường Cống Vị");

			log.info("TC_05_STEP_: dia chi cu the");
			shopping.inputToDynamicInfo("22 abc", "Địa chỉ cụ thể (Số nhà, tên đường...)");

			log.info("TC_05_STEP_: chon hoan tat");
			shopping.clickToDynamicButton("Hoàn tất");

			log.info("TC_05_STEP_: click thanh toan");
			shopping.clickToDynamicButton("Thanh toán");
		}

		if (shopping.isTextDisplayedInPageSource("Sản phẩm không còn")) {
			shopping.clickToDynamicButton("Quay về giỏ hàng");

			shopping.clickToDynamicCart("2", "0");
			shopping.clickToDynamicButton("Mua sắm ngay");

			shopping.clickToDynamicCategories("Xem tất cả");

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicView(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource("Tạm hết hàng")) {
					log.info("TC_05_STEP_: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_05_STEP_: click dat hang");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");

			log.info("TC_05_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_05_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("TC_05_STEP_: click dat hang");
			shopping.clickToDynamicButton("Đặt hàng");

			shopping.scrollDownToTextView("Giao hàng tiêu chuẩn");
			feeShipping = shopping.getDynamicTextTableByTextView("Giao hàng tiêu chuẩn");
			feeShippingD = 0;
			if (feeShipping.equals("Miễn phí")) {
				feeShipping = feeShipping.replace("Miễn phí", "0");
				feeShippingD = 0;

			} else {
				feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
				feeShippingD = Double.parseDouble(feeShipping);

			}
			totalMoney = tottalMoneyCart + feeShippingD;

			log.info("TC_05_STEP_: click thanh toan");
			shopping.clickToDynamicButton("Thanh toán");

			log.info("TC_05_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_05_STEP_: click dat hang");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");

			log.info("TC_05_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_01_STEP_5: click dat hang");
			shopping.clickToDynamicButton("Đặt hàng");

		}

		log.info("TC_05_STEP_: click thanh toan ngay");
		shopping.clickToDynamicButton("Đồng ý");

		log.info("TC_05_STEP_: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.ACCOUNT2);

		log.info("TC_05_STEP_: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView("Số dư khả dụng").replace("VND", "").replace(",", ""));

		log.info("TC_05_STEP_: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_05_STEP_: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_05_STEP_: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		log.info("TC_05_STEP_: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_05_STEP_: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Giá trị đơn hàng:").split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_05_STEP_: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_05_STEP_: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed("Xác nhận thông tin");

		verifyEquals(shopping.getDynamicTextTableByTextView("Mã đơn hàng"), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView("Số tiền thanh toán").contains(totalMoneyBillString[1]));

		log.info("TC_05_STEP_: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.ACCOUNT2);

		log.info("TC_05_STEP_: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");

		log.info("TC_05_STEP_: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("TC_05_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_STEP_: dien otp");
		shopping.inputToDynamicOtp(otp, "Tiếp tục");

		log.info("TC_05_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		shopping.isDynamicMessageAndLabelTextDisplayed("GIAO DỊCH THÀNH CÔNG");

		soDuThuc = soDuTK - moneyConfirm;

		log.info("TC_01_STEP_19: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView("Nhà cung cấp"), "VNSHOP");

		verifyEquals(shopping.getDynamicTextTableByTextView("Mã đơn hàng"), codeBill);

		maGiaodich = shopping.getDynamicTextTableByTextView("Mã giao dịch");

		log.info("TC_05_STEP_: thuc hien giao dich moi");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}