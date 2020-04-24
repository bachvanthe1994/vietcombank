package vnpay.vietcombank.online_topup;

import java.io.IOException;

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
import pageObjects.OnlineTopupPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Online_Topup_Data;

public class Online_Topup_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private OnlineTopupPageObject onlineTopup;
	private TransactionReportPageObject transactionReport;

	private String originAccount, originMoney, transactionID, customerID, feeValue;

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
		login.Global_login("0963220894", "qqqq1111", opt);
		login.scrollDownToText(driver, "Tiết kiệm");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_NapTienDienTuVaoViMOMO_ThanhToanOTP(String otp) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Nap tien dien tu'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện tử");
		onlineTopup = PageFactoryManager.getOnlineTopupPageObject(driver);

		log.info("TC_01_Step_02: An mo dropdownlist Ten Dich Vu");
		originAccount = onlineTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/number_account");
		originMoney = onlineTopup.getDynamicTextByLabel(driver, "Số dư khả dụng");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_SERVICE);

		log.info("TC_01_Step_03: Chon 'Nap tien dien tu vao vi'");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_SERVICE);

		log.info("TC_01_Step_04: An mo dropdownlist Nha cung cap");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_NAME);

		log.info("TC_01_Step_05: Chon 'Vi dien tu MOMO'");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_NAME);

		log.info("TC_01_Step_06: An mo dropdownlist Loai dich vu");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "Loại dịch vụ");

		log.info("TC_01_Step_07: Chon 'Nap tien MOMO B'");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_TYPE_B);

		log.info("TC_01_Step_08: Nhap ma khach hang");
		onlineTopup.scrollDownToButton(driver, "Tiếp tục");
		onlineTopup.inputIntoEditTextByID(driver, Online_Topup_Data.MOMO.MOMO_DATA_01, "com.VCB:id/code");

		log.info("TC_01_Step_09: Nhap so tien");
		onlineTopup.inputIntoEditTextByID(driver, Online_Topup_Data.MOMO.MOMO_MONEY, "com.VCB:id/edtContent1");

		log.info("TC_01_Step_10: An vao check box");
		onlineTopup.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_Step_11: An nut 'Tiep tuc'");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_12:Hien thi man hinh xac nhan thong tin");
		log.info("TC_01_Step_12_1:Hien thi dung tai khoan nguon");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Tài khoản nguồn"), originAccount);

		log.info("TC_01_Step_12_2:Hien thi dung loai hinh");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Loại hình"), Online_Topup_Data.MOMO.MOMO_SERVICE);

		log.info("TC_01_Step_12_3:Hien thi dung nha cung cap");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Nhà cung cấp"), Online_Topup_Data.MOMO.MOMO_NAME);

		log.info("TC_01_Step_12_4:Hien thi dung loai dich vu");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Loại dịch vụ"), Online_Topup_Data.MOMO.MOMO_TYPE_B);

		log.info("TC_01_Step_12_5:Lay ma khach hang");
		customerID = onlineTopup.getDynamicTextByLabel(driver, "Mã khách hàng");

		log.info("TC_01_Step_12_6:Hien thi dung so tien");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Số tiền"), addCommasToLong(Online_Topup_Data.MOMO.MOMO_MONEY) + " VND");

		log.info("TC_01_Step_13: Chon phuong thuc xac thuc OTP");
		onlineTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		feeValue = onlineTopup.getDynamicTextByLabel(driver, "Số tiền phí");

		log.info("TC_01_Step_14: An nut 'Tiep tuc'");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_15: Nhap du ki tu vao o nhap OTP");
		onlineTopup.inputToDynamicOtp(driver, otp, "Tiếp tục");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_16: Hien thi man hinh thong bao nap thanh cong");
		verifyEquals(onlineTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");
		verifyEquals(onlineTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), addCommasToLong(Online_Topup_Data.MOMO.MOMO_MONEY) + " VND");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Dịch vụ"), Online_Topup_Data.MOMO.MOMO_SERVICE);
		transactionID = onlineTopup.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_01_Step_17: An nut 'Tiep tuc'");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_18: Xac nhan so tien o tai khoan nguon bi tru dung");
		onlineTopup.clickToTextID(driver, "com.VCB:id/number_account");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, originAccount), (onlineTopup.getStringNumberAfterCaculate(originMoney, Online_Topup_Data.MOMO.MOMO_MONEY, feeValue) + " VND"));
		onlineTopup.clickToTextID(driver, "com.VCB:id/cancel_button");
	}

	@Test
	public void TC_02_KiemTraGiaoDichNapTienDienTuTrongBaoCaoGiaoDich() {

		log.info("TC_02_Step_01: Click back ve man hinh chinh");
		onlineTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_02: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_03: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_02_Step_04: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_Step_05: Chon 'Nap tien dien tu vao vi'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_SERVICE);

		log.info("TC_02_Step_06: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_Step_07: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, originAccount);

		log.info("TC_02_Step_08: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_02_Step_09: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02_Step_10: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_02_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_02_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), originAccount);

		log.info("TC_02_Step_13: Xac nhan hien thi Ten Dich vu");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Dịch vụ"), Online_Topup_Data.MOMO.MOMO_TYPE_B);

		log.info("TC_02_Step_14: Xac nhan hien thi Nha cung cap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Nhà cung cấp"), Online_Topup_Data.MOMO.MOMO_NAME);

		log.info("TC_02_Step_15: Xac nhan hien thi ma khach hang");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Mã khách hàng"), customerID);

		log.info("TC_02_Step_16: Xac nhan hien thi so tien trich no");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền trích nợ"), addCommasToLong(Online_Topup_Data.MOMO.MOMO_MONEY) + " VND");

		log.info("TC_02_Step_17: Xac nhan hien thi so tien nap");
		onlineTopup.scrollDownToText(driver, "Nội dung giao dịch");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền nạp"), addCommasToLong(Online_Topup_Data.MOMO.MOMO_MONEY) + " VND");

		log.info("TC_02_Step_18: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), Online_Topup_Data.MOMO.MOMO_SERVICE);

		log.info("TC_02_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_21: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_03_NapTienDienTuVaoViMOMO_ThanhToanMK() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Nap tien dien tu'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện tử");
		onlineTopup = PageFactoryManager.getOnlineTopupPageObject(driver);

		log.info("TC_03_Step_02: An mo dropdownlist Ten Dich Vu");
		originAccount = onlineTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/number_account");
		originMoney = onlineTopup.getDynamicTextByLabel(driver, "Số dư khả dụng");
		onlineTopup.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/service_selected");

		log.info("TC_03_Step_03: Chon 'Nap tien dien tu vao vi'");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_SERVICE);

		log.info("TC_03_Step_04: An mo dropdownlist Nha cung cap");
		onlineTopup.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/supplier");

		log.info("TC_03_Step_05: Chon 'Vi dien tu MOMO'");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_NAME);

		log.info("TC_03_Step_06: An mo dropdownlist Loai dich vu");
		onlineTopup.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/serviceType");

		log.info("TC_03_Step_07: Chon 'Nap tien MOMO'");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_TYPE_B);

		log.info("TC_03_Step_08: Nhap ma khach hang");
		onlineTopup.scrollDownToButton(driver, "Tiếp tục");
		onlineTopup.inputIntoEditTextByID(driver, Online_Topup_Data.MOMO.MOMO_DATA_01, "com.VCB:id/code");

		log.info("TC_03_Step_09: Nhap so tien");
		onlineTopup.inputIntoEditTextByID(driver, Online_Topup_Data.MOMO.MOMO_MONEY, "com.VCB:id/edtContent1");

		log.info("TC_03_Step_10: An vao check box");
		onlineTopup.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_03_Step_11: An nut 'Tiep tuc'");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_03_Step_12_1:Hien thi dung tai khoan nguon");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Tài khoản nguồn"), originAccount);

		log.info("TC_03_Step_12_2:Hien thi dung loai hinh");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Loại hình"), Online_Topup_Data.MOMO.MOMO_SERVICE);

		log.info("TC_03_Step_12_3:Hien thi dung nha cung cap");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Nhà cung cấp"), Online_Topup_Data.MOMO.MOMO_NAME);

		log.info("TC_03_Step_12_4:Hien thi dung loai dich vu");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Loại dịch vụ"), Online_Topup_Data.MOMO.MOMO_TYPE_B);

		log.info("TC_03_Step_12_5:Lay ma khach hang");
		customerID = onlineTopup.getDynamicTextByLabel(driver, "Mã khách hàng");

		log.info("TC_03_Step_12_6:Hien thi dung so tien");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Số tiền"), addCommasToLong(Online_Topup_Data.MOMO.MOMO_MONEY) + " VND");

		log.info("TC_03_Step_13: Chon phuong thuc xac thuc MK");
		onlineTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		feeValue = onlineTopup.getDynamicTextByLabel(driver, "Số tiền phí");

		log.info("TC_03_Step_14: An nut 'Tiep tuc'");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_15: Nhap ki tu vao o nhap mat khau");
		onlineTopup.inputIntoEditTextByID(driver, "qqqq1111", "com.VCB:id/pin");

		log.info("TC_03_Step_16: An tiep button 'Tiep tuc'");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_17: Hien thi man hinh thong bao nap thanh cong");
		verifyEquals(onlineTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");
		verifyEquals(onlineTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), addCommasToLong(Online_Topup_Data.MOMO.MOMO_MONEY) + " VND");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Dịch vụ"), Online_Topup_Data.MOMO.MOMO_SERVICE);
		transactionID = onlineTopup.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_03_Step_18: An nut 'Tiep tuc'");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_19: Xac nhan so tien o tai khoan nguon bi tru dung");
		onlineTopup.clickToTextID(driver, "com.VCB:id/number_account");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, originAccount), (onlineTopup.getStringNumberAfterCaculate(originMoney, Online_Topup_Data.MOMO.MOMO_MONEY, feeValue) + " VND"));
		onlineTopup.clickToTextID(driver, "com.VCB:id/cancel_button");
	}

	@Test
	public void TC_04_KiemTraGiaoDichNapTienDienTuTrongBaoCaoGiaoDich() {

		log.info("TC_04_Step_01: Click back ve man hinh chinh");
		onlineTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_02: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step_03: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_04_Step_04: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_04_Step_05: Chon 'Nap tien dien tu vao vi'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_SERVICE);

		log.info("TC_04_Step_06: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_Step_07: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, originAccount);

		log.info("TC_04_Step_08: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_04_Step_09: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_04_Step_10: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_04_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_04_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), originAccount);

		log.info("TC_04_Step_13: Xac nhan hien thi Ten Dich vu");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Dịch vụ"), Online_Topup_Data.MOMO.MOMO_TYPE_B);

		log.info("TC_04_Step_14: Xac nhan hien thi Nha cung cap");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Nhà cung cấp"), Online_Topup_Data.MOMO.MOMO_NAME);

		log.info("TC_04_Step_15: Xac nhan hien thi ma khach hang");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Mã khách hàng"), customerID);

		log.info("TC_04_Step_16: Xac nhan hien thi so tien trich no");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền trích nợ"), addCommasToLong(Online_Topup_Data.MOMO.MOMO_MONEY) + " VND");

		log.info("TC_04_Step_17: Xac nhan hien thi so tien nap");
		onlineTopup.scrollDownToText(driver, "Nội dung giao dịch");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền nạp"), addCommasToLong(Online_Topup_Data.MOMO.MOMO_MONEY) + " VND");

		log.info("TC_04_Step_18: Xac nhan hien thi Loai giao dich");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Loại giao dịch"), Online_Topup_Data.MOMO.MOMO_SERVICE);

		log.info("TC_04_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_04_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_21: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_05_NapTienDienTuVaoVETC_ThanhToanMK(String pass) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_05_Step_01: Keo xuong va click vao phan 'Nap tien dien tu'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện tử");
		onlineTopup = PageFactoryManager.getOnlineTopupPageObject(driver);

		log.info("TC_05_Step_02: An mo dropdownlist Ten Dich Vu");
		originAccount = onlineTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/number_account");
		originMoney = onlineTopup.getDynamicTextByLabel(driver, "Số dư khả dụng");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_SERVICE);

		log.info("TC_05_Step_03: Chon 'Nap tien tai khoan VETC'");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.VETC.VETC_SERVICE);

		log.info("TC_05_Step_04: An mo dropdownlist Nha cung cap");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "VETC");

		log.info("TC_05_Step_05: Chon 'VETC'");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "VETC");

		log.info("TC_05_Step_06: Nhap Bien so xe/Ma khach hang");
		onlineTopup.inputToDynamicInputBox(driver, Online_Topup_Data.VETC.VETC_DATA_01, "Biển số xe/Mã khách hàng");

		log.info("TC_05_Step_07: Nhap so tien");
		onlineTopup.scrollDownToButton(driver, "Tiếp tục");
		onlineTopup.inputToDynamicInputBox(driver, Online_Topup_Data.VETC.VETC_MONEY, "Số tiền");

		log.info("TC_05_Step_08: An vao check box");
		onlineTopup.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_05_Step_09: An nut 'Tiep tuc'");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_05_Step_10:Hien thi man hinh xac nhan thong tin");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Tài khoản nguồn"), originAccount);
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Loại hình"), Online_Topup_Data.VETC.VETC_SERVICE);
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Nhà cung cấp"), "VETC");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Biển số xe/Mã khách hàng"), Online_Topup_Data.VETC.VETC_DATA_01);
		customerID = onlineTopup.getDynamicTextByLabel(driver, "Tên khách hàng");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Số tiền nạp"), addCommasToLong(Online_Topup_Data.VETC.VETC_MONEY) + " VND");

		log.info("TC_05_Step_11: Chon phuong thuc xac thuc mat khau");
		onlineTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		feeValue = onlineTopup.getDynamicTextByLabel(driver, "Số tiền phí");

		log.info("TC_05_Step_12: An nut 'Tiep tuc'");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_13: Nhap ki tu vao o nhap mat khau");
		onlineTopup.inputIntoEditTextByID(driver, "qqqq1111", "com.VCB:id/pin");

		log.info("TC_05_Step_14: An tiep button 'Tiep tuc'");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_15: Hien thi man hinh thong bao nap thanh cong");
		verifyEquals(onlineTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");
		verifyEquals(onlineTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), addCommasToLong(Online_Topup_Data.VETC.VETC_MONEY) + " VND");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Loại hình"), Online_Topup_Data.VETC.VETC_SERVICE);
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Nhà cung cấp"), "VETC");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Biển số xe/Mã khách hàng"), Online_Topup_Data.VETC.VETC_DATA_01);
		transactionID = onlineTopup.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_05_Step_16: An nut 'Tiep tuc'");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_17: Xac nhan so tien o tai khoan nguon bi tru dung");
		onlineTopup.clickToTextID(driver, "com.VCB:id/number_account");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, originAccount), (onlineTopup.getStringNumberAfterCaculate(originMoney, Online_Topup_Data.VETC.VETC_MONEY, feeValue) + " VND"));
		onlineTopup.clickToTextID(driver, "com.VCB:id/cancel_button");
	}

	@Test
	public void TC_06_KiemTraGiaoDichNapTienDienTuTrongBaoCaoGiaoDich() {

		log.info("TC_06_Step_01: Click back ve man hinh chinh");
		onlineTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_06_Step_02: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_06_Step_03: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_06_Step_04: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_06_Step_05: Chon 'Nap tien tai khoan VETC'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.VETC.VETC_SERVICE);

		log.info("TC_06_Step_06: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_Step_07: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, originAccount);

		log.info("TC_06_Step_08: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_06_Step_09: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_06_Step_10: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_06_Step_11: Xac nhan hien thi thoi gian giao dich");
//		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDay), "com.VCB:id/tvContent"));

		log.info("TC_06_Step_12: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_06_Step_13: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), originAccount);

		log.info("TC_06_Step_14: Xac nhan hien thi Nha cung cap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Nhà cung cấp"), "VETC");

		log.info("TC_06_Step_15: Xac nhan hien thi ma khach hang");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Biển số xe/Mã khách hàng"), Online_Topup_Data.VETC.VETC_DATA_01);

		log.info("TC_06_Step_16: Xac nhan hien thi so tien nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền nạp"), addCommasToLong(Online_Topup_Data.VETC.VETC_MONEY) + " VND");

		log.info("TC_06_Step_17: Xac nhan hien thi Loai giao dich");
		transactionReport.scrollDownToText(driver, "Nội dung giao dịch");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), Online_Topup_Data.VETC.VETC_SERVICE);

		log.info("TC_06_Step_18: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_06_Step_19: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_06_Step_20: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_07_NapTienDienTuVaoVETC_ThanhToanOTP(String otp) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_07_Step_01: Keo xuong va click vao phan 'Nap tien dien tu'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện tử");
		onlineTopup = PageFactoryManager.getOnlineTopupPageObject(driver);

		log.info("TC_07_Step_02: An mo dropdownlist Ten Dich Vu");
		originAccount = onlineTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/number_account");
		originMoney = onlineTopup.getDynamicTextByLabel(driver, "Số dư khả dụng");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_SERVICE);

		log.info("TC_07_Step_03: Chon 'Nap tien tai khoan VETC'");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.VETC.VETC_SERVICE);

		log.info("TC_07_Step_04: An mo dropdownlist Nha cung cap");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "VETC");

		log.info("TC_07_Step_05: Chon 'VETC'");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "VETC");

		log.info("TC_07_Step_06: Nhap Bien so xe/Ma khach hang");
		onlineTopup.inputToDynamicInputBox(driver, Online_Topup_Data.VETC.VETC_DATA_01, "Biển số xe/Mã khách hàng");

		log.info("TC_07_Step_07: Nhap so tien");
		onlineTopup.scrollDownToButton(driver, "Tiếp tục");
		onlineTopup.inputToDynamicInputBox(driver, Online_Topup_Data.VETC.VETC_MONEY, "Số tiền");

		log.info("TC_07_Step_08: An vao check box");
		onlineTopup.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_07_Step_09: An nut 'Tiep tuc'");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_07_Step_10:Kiem tra hien thi thong tin tai khoan nguon");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Tài khoản nguồn"), originAccount);

		log.info("TC_07_Step_10:Kiem tra hien thi Loai hinh");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Loại hình"), Online_Topup_Data.VETC.VETC_SERVICE);

		log.info("TC_07_Step_10:Kiem tra hien thi nha cung cap");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Nhà cung cấp"), "VETC");

		log.info("TC_07_Step_10:Kiem tra hien thi bien so xe");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Biển số xe/Mã khách hàng"), Online_Topup_Data.VETC.VETC_DATA_01);

		log.info("TC_07_Step_10:Kiem tra hien thi Ten khach hang");
		customerID = onlineTopup.getDynamicTextByLabel(driver, "Tên khách hàng");

		log.info("TC_07_Step_10:Kiem tra hien thi So tien nap");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Số tiền nạp"), addCommasToLong(Online_Topup_Data.VETC.VETC_MONEY) + " VND");

		log.info("TC_07_Step_11: Chon phuong thuc xac thuc OTP");
		onlineTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");
		feeValue = onlineTopup.getDynamicTextByLabel(driver, "Số tiền phí");

		log.info("TC_07_Step_12: An nut 'Tiep tuc'");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_13: Nhap du ki tu vao o nhap OTP");
		onlineTopup.inputToDynamicOtp(driver, otp, "Tiếp tục");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_14: Hien thi man hinh thong bao nap thanh cong");
		verifyEquals(onlineTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");
		verifyEquals(onlineTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), addCommasToLong(Online_Topup_Data.VETC.VETC_MONEY) + " VND");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Loại hình"), Online_Topup_Data.VETC.VETC_SERVICE);
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Nhà cung cấp"), "VETC");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, "Biển số xe/Mã khách hàng"), Online_Topup_Data.VETC.VETC_DATA_01);
		transactionID = onlineTopup.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_07_Step_15: An nut 'Tiep tuc'");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_16: Xac nhan so tien o tai khoan nguon bi tru dung");
		onlineTopup.clickToTextID(driver, "com.VCB:id/number_account");
		verifyEquals(onlineTopup.getDynamicTextByLabel(driver, originAccount), (onlineTopup.getStringNumberAfterCaculate(originMoney, Online_Topup_Data.VETC.VETC_MONEY, feeValue) + " VND"));
		onlineTopup.clickToTextID(driver, "com.VCB:id/cancel_button");
	}

	@Test
	public void TC_08_KiemTraGiaoDichNapTienDienTuTrongBaoCaoGiaoDich() {

		log.info("TC_08_Step_01: Click back ve man hinh chinh");
		onlineTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_08_Step_02: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_08_Step_03: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_08_Step_04: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_08_Step_05: Chon 'Nap tien tai khoan VETC'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.VETC.VETC_SERVICE);

		log.info("TC_08_Step_06: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08_Step_07: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, originAccount);

		log.info("TC_08_Step_08: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_08_Step_09: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_08_Step_10: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_08_Step_11: Xac nhan hien thi thoi gian giao dich");
//		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDay), "com.VCB:id/tvContent"));

		log.info("TC_08_Step_12: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_08_Step_13: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), originAccount);

		log.info("TC_08_Step_14: Xac nhan hien thi Nha cung cap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Nhà cung cấp"), "VETC");

		log.info("TC_08_Step_15: Xac nhan hien thi ma khach hang");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Biển số xe/Mã khách hàng"), Online_Topup_Data.VETC.VETC_DATA_01);

		log.info("TC_08_Step_16: Xac nhan hien thi so tien nap");
		transactionReport.scrollDownToText(driver, "Nội dung giao dịch");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền nạp"), addCommasToLong(Online_Topup_Data.VETC.VETC_MONEY) + " VND");

		log.info("TC_08_Step_17: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), "Nạp tiền tài khoản VETC");

		log.info("TC_08_Step_18: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_08_Step_19: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_08_Step_20: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
