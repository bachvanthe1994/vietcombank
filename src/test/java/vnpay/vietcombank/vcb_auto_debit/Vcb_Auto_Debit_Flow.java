package vnpay.vietcombank.vcb_auto_debit;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.VCBAutoDebitPageObject;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.VCBAuto_Debit_Data;
import vietcombank_test_data.VCBAuto_Debit_Data.TEXT;

public class Vcb_Auto_Debit_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private VCBAutoDebitPageObject vcbAutoDebit;

	private String hostContact, address, customerID,vnptKienGiang;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	List<String> listWaterBills = new ArrayList<String>();
	List<String> listElectricBills = new ArrayList<String>();
	List<String> listVNPTBills = new ArrayList<String>();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		log.info("TC_00_Step_01: Chon tab Menu");
		home = PageFactoryManager.getHomePageObject(driver);
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
		vcbAutoDebit = PageFactoryManager.getVCBAutoDebitPageObject(driver);

		log.info("TC_01_Step_01: Click vao VCB-Auto Debit");
		home.clickToDynamicButtonLinkOrLinkText(driver,Home_Text_Elements.SERVICE_AUTO_DEBIT );

		listWaterBills = Arrays.asList(getDataInCell(23).split(";"));
		listElectricBills = Arrays.asList(getDataInCell(24).split(";"));
		listVNPTBills = Arrays.asList(getDataInCell(25).split(";"));
		vnptKienGiang = getDataInCell(33).trim();

	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_DangKyDichVu_HoaDonTienDien_XacThucOTP(String otp) {

		home.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.SERVICE_REGISTER);

		log.info("TC_01_Step_03: Chon tai khoan nguon");
		vcbAutoDebit.clickToTextID(driver, "com.VCB:id/tvContent");
		sourceAccount = vcbAutoDebit.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_03: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_01_Step_04: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.EVN_HA_NOI);

		log.info("TC_01_Step_05: Nhap Ma Khach Hang");
		customerID = vcbAutoDebit.inputCustomerId(listElectricBills);

		log.info("TC_01_Step_07: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_08: Hien thi man hinh xac nhan thong tin");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), TEXT.CONFIRM_INFO);

		log.info("TC_01_Step_09: Hien thi thông tin xac nhan");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), TEXT.CUSTOMER_PLEASE_INFO);

		log.info("TC_01_Step_10: Hien thi tai khoan nguon");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_01_Step_11: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SERVICE), VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_01_Step_12: Hien thi don vi truc thuoc");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.UNIT), VCBAuto_Debit_Data.TEXT.BELONGING_UNIT);

		log.info("TC_01_Step_12: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), VCBAuto_Debit_Data.TEXT.EVN_HA_NOI);

		log.info("TC_01_Step_13: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), customerID);

		log.info("TC_01_Step_14: Hien thi Chu hop dong");
		hostContact = vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT);

		log.info("TC_01_Step_15: Hien thi dia chi");
		address = vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ADDRESS);

		log.info("TC_01_Step_16: Chon phương thuc xac thuc");
		vcbAutoDebit.clickToTextID(driver, "com.VCB:id/tvptxt");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, TEXT.SMS_OTP);

		log.info("TC_01_Step_17: An nut 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_18: Nhap du ki tu vao o nhap OTP");
		vcbAutoDebit.inputToDynamicOtp(driver, otp, TEXT.CONTINUE);

		log.info("TC_01_Step_19: An tiep button 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_20: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.REGISTER_SUCCESS_TITLE);

		log.info("TC_01_Step_21: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_01_Step_22: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_01_Step_23: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SERVICE), VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_01_Step_24: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), VCBAuto_Debit_Data.TEXT.EVN_HA_NOI);

		log.info("TC_01_Step_25: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), customerID);

		log.info("TC_01_Step_26: Hien thi Chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_01_Step_27: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_28: Chon tai khoan nguon");
		vcbAutoDebit.clickToTextID(driver, "com.VCB:id/tvContent");
		sourceAccount = vcbAutoDebit.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_29: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_01_Step_30: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.EVN_HA_NOI);

		log.info("TC_01_Step_31: Nhap Ma Khach Hang");
		vcbAutoDebit.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/layoutMaHoaDon");

		log.info("TC_01_Step_33: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_34: Xac nhan hien thi thong bao da dang ky dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), VCBAuto_Debit_Data.TEXT.REGISTED_MESSAGE);
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_01_Step_35: Click back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_03_ThayDoiThongTin_HoaDonTienDien_XacThucOTP(String otp) {

		log.info("TC_03_Step_02: Chon thay doi thong tin");
		home.clickToDynamicButtonLinkOrLinkText(driver, TEXT.CHANGE_SOURCE_ACCOUNT);

		log.info("TC_03_Step_03: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_03_Step_04: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.EVN_HA_NOI);

		log.info("TC_03_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, customerID + " - " + hostContact);

		log.info("TC_03_Step_06: Xac nhan hien thi dung chu hop dong");
		vcbAutoDebit.scrollDownToButton(driver, TEXT.CONTINUE);
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_03_Step_07: Xac nhan hien thi dung dia chi");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ADDRESS), address);

		log.info("TC_03_Step_08: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ACCOUNT_NUMBER), sourceAccount.account);

		log.info("TC_03_Step_09: Chon thay doi tai khoan");
		vcbAutoDebit.scrollDownToButton(driver, TEXT.CONTINUE);
		vcbAutoDebit.clickToDynamicDropdownByHeader(driver, TEXT.CHANGE_ACCOUNT, "2");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_03_Step_10: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_03_Step_11: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_12: Hien thi man hinh xac nhan thong tin");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), TEXT.CONFIRM_INFO);

		log.info("TC_03_Step_13: Hien thi thong tin xac nhan");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), TEXT.CUSTOMER_PLEASE_CHANGE);

		log.info("TC_03_Step_14: Hien thi tai khoan nguon");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_03_Step_15: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.TYPE_SERVICE), VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_03_Step_16: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), VCBAuto_Debit_Data.TEXT.EVN_HA_NOI);

		log.info("TC_03_Step_17: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), customerID);

		log.info("TC_03_Step_18: Hien thi Chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_03_Step_19: Hien thi dia chi");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ADDRESS), address);

		log.info("TC_03_Step_20: Chon phương thuc xac thuc");
		vcbAutoDebit.clickToTextID(driver, "com.VCB:id/tvptxt");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, TEXT.SMS_OTP);

		log.info("TC_03_Step_21: An nut 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_22: Nhap du ki tu vao o nhap OTP");
		vcbAutoDebit.inputToDynamicOtp(driver, otp, TEXT.CONTINUE);

		log.info("TC_03_Step_23: An tiep button 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_24: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.CHANGE_SUCCESS_TITLE);

		log.info("TC_03_Step_25: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_03_Step_26: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_03_Step_27: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SERVICE), VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_03_Step_28: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), VCBAuto_Debit_Data.TEXT.EVN_HA_NOI);

		log.info("TC_03_Step_29: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), customerID);

		log.info("TC_03_Step_30: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_31: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_03_Step_32: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.EVN_HA_NOI);

		log.info("TC_03_Step_33: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, customerID + " - " + hostContact);

		log.info("TC_03_Step_34: Xac nhan hien thi dung chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_03_Step_35: Xac nhan hien thi dung dia chi");
		vcbAutoDebit.scrollDownToButton(driver, TEXT.CONTINUE);
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ADDRESS), address);

		log.info("TC_03_Step_36: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ACCOUNT_NUMBER), sourceAccount.account);

		log.info("TC_03_Step_37: Click back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_05_HuyDangKy_HoaDonTienDien_XacThucOTP() {

		log.info("TC_05_Step_02: Chon huy dang ky");
		home.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.SERVICE_STOP);

		log.info("TC_05_Step_03: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_05_Step_04: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.EVN_HA_NOI);

		log.info("TC_05_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		customerID = vcbAutoDebit.inputCustomerId(listElectricBills);

		log.info("TC_05_Step_06: Xac nhan hien thi dung chu hop dong");
		vcbAutoDebit.scrollDownToButton(driver, TEXT.CONTINUE);
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_05_Step_07: Xac nhan hien thi dung dia chi");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ADDRESS), address);

		log.info("TC_05_Step_08: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ACCOUNT_NUMBER), sourceAccount.account);

		log.info("TC_05_Step_09: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_05_Step_10: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_11: Hien thi man hinh thong bao huy giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.CANCELED_SUCCESS_TITLE);

		log.info("TC_05_Step_12: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_05_Step_13: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_05_Step_14: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SERVICE), VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_05_Step_15: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), VCBAuto_Debit_Data.TEXT.EVN_HA_NOI);

		log.info("TC_05_Step_16: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), customerID);

		log.info("TC_05_Step_17: Xac nhan hien thi dung chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_05_Step_18: Hien thi Icon Chia se");
		verifyTrue(vcbAutoDebit.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_05_Step_19: Hien thi Icon Luu anh");
		verifyTrue(vcbAutoDebit.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_05_Step_20: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_21: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_05_Step_22: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.EVN_HA_NOI);

		log.info("TC_05_Step_23: Xac nhan ma khach hang khong con trong dropdown");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		verifyTrue(vcbAutoDebit.isDynamicMessageAndLabelTextUndisplayed(driver, customerID + " - " + hostContact));

		log.info("TC_05_Step_24: Dong dropdown va back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicButton(driver, TEXT.CLOSE);
		vcbAutoDebit.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_09_DangKyDichVu_HoaDonTienNuoc_XacThucOTP(String otp) {

		log.info("TC_09_Step_02: Chon huy dang ky");
		home.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.SERVICE_STOP);

		log.info("TC_09_Step_03: Chon loai dich vu 'Hoa don tien nuoc'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_09_Step_04: Chon nha cung cap 'Nuoc Da Nang'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.inputIntoEditTextByID(driver, VCBAuto_Debit_Data.TEXT.WATER_DA_NANG, "com.VCB:id/edtSearch");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_DA_NANG);

		log.info("TC_09_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		
		customerID = vcbAutoDebit.inputCustomerId(listWaterBills);
		
		log.info("TC_09_Step_24: Dong dropdown va back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_09_Step_02: Chon Dang ki dich vu");
		home.clickToDynamicButtonLinkOrLinkText(driver, TEXT.REGISTER_SERVICE);
		vcbAutoDebit = PageFactoryManager.getVCBAutoDebitPageObject(driver);

		log.info("TC_09_Step_03: Chon tai khoan nguon");
		vcbAutoDebit.clickToTextID(driver, "com.VCB:id/tvContent");
		sourceAccount = vcbAutoDebit.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_09_Step_03: Chon loai dich vu 'Hoa don tien nuoc'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_09_Step_04: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.inputIntoEditTextByID(driver, VCBAuto_Debit_Data.TEXT.WATER_DA_NANG, "com.VCB:id/edtSearch");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_DA_NANG);

		log.info("TC_09_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/layoutMaHoaDon");

		log.info("TC_09_Step_06: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_09_Step_07: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_08: Hien thi man hinh xac nhan thong tin");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), TEXT.CONFIRM_INFO);

		log.info("TC_09_Step_09: Hien thi thông tin xac nhan");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), TEXT.CUSTOMER_PLEASE_INFO);

		log.info("TC_09_Step_10: Hien thi tai khoan nguon");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_09_Step_11: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SERVICE), VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_09_Step_12: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), VCBAuto_Debit_Data.TEXT.WATER_DA_NANG);

		log.info("TC_09_Step_13: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), customerID);

		log.info("TC_09_Step_14: Hien thi Chu hop dong");
		hostContact = vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT);

		log.info("TC_09_Step_15: Hien thi dia chi");
		address = vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ADDRESS);

		log.info("TC_09_Step_16: Chon phương thuc xac thuc");
		vcbAutoDebit.clickToTextID(driver, "com.VCB:id/tvptxt");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, TEXT.SMS_OTP);

		log.info("TC_09_Step_17: An nut 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_18: Nhap du ki tu vao o nhap OTP");
		vcbAutoDebit.inputToDynamicOtp(driver, otp, TEXT.CONTINUE);

		log.info("TC_09_Step_19: An tiep button 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_20: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.REGISTER_SUCCESS_TITLE);

		log.info("TC_09_Step_21: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_09_Step_22: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_09_Step_23: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SERVICE), VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_09_Step_24: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), VCBAuto_Debit_Data.TEXT.WATER_DA_NANG);

		log.info("TC_09_Step_25: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), customerID);

		log.info("TC_09_Step_26: Hien thi Chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_09_Step_27: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_28: Chon tai khoan nguon");
		vcbAutoDebit.clickToTextID(driver, "com.VCB:id/tvContent");
		sourceAccount = vcbAutoDebit.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_09_Step_29: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_09_Step_30: Chon nha cung cap 'Da Nang'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.inputIntoEditTextByID(driver, VCBAuto_Debit_Data.TEXT.WATER_DA_NANG, "com.VCB:id/edtSearch");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_DA_NANG);

		log.info("TC_09_Step_31: Nhap Ma Khach Hang");
		vcbAutoDebit.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/layoutMaHoaDon");

		log.info("TC_09_Step_32: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_09_Step_33: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_34: Xac nhan hien thi thong bao da dang ky dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), VCBAuto_Debit_Data.TEXT.REGISTED_MESSAGE);
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_09_Step_35: Click back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_09_ThayDoiThongTin_HoaDonTienNuoc_XacThucOTP(String otp) {

		log.info("TC_09_Step_02: Chon thay doi thong tin");
		home.clickToDynamicButtonLinkOrLinkText(driver, TEXT.CHANGE_SOURCE_ACCOUNT);

		log.info("TC_09_Step_03: Chon loai dich vu 'Hoa don tien nuoc'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_09_Step_04: Chon nha cung cap 'Nuoc Da Nang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.inputIntoEditTextByID(driver, VCBAuto_Debit_Data.TEXT.WATER_DA_NANG, "com.VCB:id/edtSearch");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_DA_NANG);

		log.info("TC_09_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, customerID + " - " + hostContact);

		log.info("TC_09_Step_06: Xac nhan hien thi dung chu hop dong");
		vcbAutoDebit.scrollDownToButton(driver, TEXT.CONTINUE);
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_09_Step_07: Xac nhan hien thi dung dia chi");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ADDRESS), address);

		log.info("TC_09_Step_08: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ACCOUNT_NUMBER), sourceAccount.account);

		log.info("TC_09_Step_09: Chon thay doi tai khoan");
		vcbAutoDebit.scrollDownToButton(driver, TEXT.CONTINUE);
		vcbAutoDebit.clickToDynamicDropdownByHeader(driver, TEXT.CHANGE_ACCOUNT, "2");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_09_Step_10: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_09_Step_11: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_12: Hien thi man hinh xac nhan thong tin");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), TEXT.CONFIRM_INFO);

		log.info("TC_09_Step_13: Hien thi thong tin xac nhan");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), TEXT.CUSTOMER_PLEASE_CHANGE);

		log.info("TC_09_Step_14: Hien thi tai khoan nguon");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_09_Step_15: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.TYPE_SERVICE), VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_09_Step_16: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), VCBAuto_Debit_Data.TEXT.WATER_DA_NANG);

		log.info("TC_09_Step_17: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), customerID);

		log.info("TC_09_Step_18: Hien thi Chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_09_Step_19: Hien thi dia chi");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ADDRESS), address);

		log.info("TC_09_Step_20: Chon phương thuc xac thuc");
		vcbAutoDebit.clickToTextID(driver, "com.VCB:id/tvptxt");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, TEXT.SMS_OTP);

		log.info("TC_09_Step_21: An nut 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_22: Nhap du ki tu vao o nhap OTP");
		vcbAutoDebit.inputToDynamicOtp(driver, otp, TEXT.CONTINUE);

		log.info("TC_09_Step_23: An tiep button 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_24: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.CHANGE_SUCCESS_TITLE);

		log.info("TC_09_Step_25: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_09_Step_26: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_09_Step_27: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SERVICE), VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_09_Step_28: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), VCBAuto_Debit_Data.TEXT.WATER_DA_NANG);

		log.info("TC_09_Step_29: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), customerID);

		log.info("TC_09_Step_30: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_31: Chon loai dich vu 'Hoa don tien nuoc'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_09_Step_32: Chon nha cung cap 'Nuoc Da Nang'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.inputIntoEditTextByID(driver, VCBAuto_Debit_Data.TEXT.WATER_DA_NANG, "com.VCB:id/edtSearch");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_DA_NANG);

		log.info("TC_09_Step_33: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, customerID + " - " + hostContact);

		log.info("TC_09_Step_34: Xac nhan hien thi dung chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_09_Step_35: Xac nhan hien thi dung dia chi");
		vcbAutoDebit.scrollDownToButton(driver, TEXT.CONTINUE);
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ADDRESS), address);

		log.info("TC_09_Step_36: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ACCOUNT_NUMBER), sourceAccount.account);

		log.info("TC_09_Step_37: Click back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_11_HuyDangKy_HoaDonTienNuoc_XacThucOTP() {

		log.info("TC_11_Step_02: Chon huy dang ky");
		home.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.SERVICE_STOP);

		log.info("TC_11_Step_03: Chon loai dich vu 'Hoa don tien nuoc'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_11_Step_04: Chon nha cung cap 'Nuoc Da Nang'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.inputIntoEditTextByID(driver, VCBAuto_Debit_Data.TEXT.WATER_DA_NANG, "com.VCB:id/edtSearch");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_DA_NANG);

		log.info("TC_11_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, customerID + " - " + hostContact);

		log.info("TC_11_Step_06: Xac nhan hien thi dung chu hop dong");
		vcbAutoDebit.scrollDownToButton(driver, TEXT.CONTINUE);
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_11_Step_07: Xac nhan hien thi dung dia chi");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ADDRESS), address);

		log.info("TC_11_Step_08: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ACCOUNT_NUMBER), sourceAccount.account);

		log.info("TC_11_Step_09: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_11_Step_10: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_11_Step_11: Hien thi man hinh thong bao huy giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.CANCELED_SUCCESS_TITLE);

		log.info("TC_11_Step_12: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_11_Step_13: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_11_Step_14: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SERVICE), VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_11_Step_15: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), VCBAuto_Debit_Data.TEXT.WATER_DA_NANG);

		log.info("TC_11_Step_16: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), customerID);

		log.info("TC_11_Step_17: Xac nhan hien thi dung chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_11_Step_18: Hien thi Icon Chia se");
		verifyTrue(vcbAutoDebit.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_11_Step_19: Hien thi Icon Luu anh");
		verifyTrue(vcbAutoDebit.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_11_Step_20: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_11_Step_21: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_11_Step_22: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_DA_NANG);

		log.info("TC_11_Step_23: Xac nhan ma khach hang khong con trong dropdown");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		verifyTrue(vcbAutoDebit.isDynamicMessageAndLabelTextUndisplayed(driver, customerID + " - " + hostContact));

		log.info("TC_11_Step_24: Dong dropdown va back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicButton(driver, TEXT.CLOSE);
		vcbAutoDebit.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_13_DangKyDichVu_HoaDonVNPT_XacThucOTP(String otp) {

		log.info("TC_13_Step_02: Chon huy dang ky");
		home.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.SERVICE_STOP);
		vcbAutoDebit = PageFactoryManager.getVCBAutoDebitPageObject(driver);

		log.info("TC_13_Step_03: Chon loai dich vu 'Hoa don tien nuoc'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.VNPT_BILL_TEXT);

		log.info("TC_13_Step_04: Chon nha cung cap 'Nuoc Da Nang'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, vnptKienGiang);

		log.info("TC_13_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		customerID = vcbAutoDebit.inputCustomerId(listVNPTBills);

		log.info("TC_13_Step_24: Dong dropdown va back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_13_Step_02: Chon Dang ki dich vu");
		home.clickToDynamicButtonLinkOrLinkText(driver, TEXT.REGISTER_SERVICE);

		log.info("TC_13_Step_03: Chon tai khoan nguon");
		vcbAutoDebit.clickToTextID(driver, "com.VCB:id/tvContent");
		sourceAccount = vcbAutoDebit.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_13_Step_03: Chon loai dich vu 'Hoa don VNPT'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.VNPT_BILL_TEXT);

		log.info("TC_13_Step_04: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, vnptKienGiang);

		log.info("TC_13_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/layoutMaHoaDon");

		log.info("TC_13_Step_06: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_13_Step_07: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_13_Step_08: Hien thi man hinh xac nhan thong tin");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), TEXT.CONFIRM_INFO);

		log.info("TC_13_Step_09: Hien thi thông tin xac nhan");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), TEXT.CUSTOMER_PLEASE_INFO);

		log.info("TC_13_Step_10: Hien thi tai khoan nguon");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_13_Step_11: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SERVICE), VCBAuto_Debit_Data.TEXT.VNPT_BILL_TEXT);

		log.info("TC_13_Step_12: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), vnptKienGiang);

		log.info("TC_13_Step_13: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), customerID);

		log.info("TC_13_Step_14: Hien thi Chu hop dong");
		hostContact = vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT);


		log.info("TC_13_Step_16: Chon phương thuc xac thuc");
		vcbAutoDebit.clickToTextID(driver, "com.VCB:id/tvptxt");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, TEXT.SMS_OTP);

		log.info("TC_13_Step_17: An nut 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_13_Step_18: Nhap du ki tu vao o nhap OTP");
		vcbAutoDebit.inputToDynamicOtp(driver, otp, TEXT.CONTINUE);

		log.info("TC_13_Step_19: An tiep button 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_13_Step_20: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.REGISTER_SUCCESS_TITLE);

		log.info("TC_13_Step_21: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_13_Step_22: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_13_Step_23: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SERVICE), VCBAuto_Debit_Data.TEXT.VNPT_BILL_TEXT);

		log.info("TC_13_Step_24: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), vnptKienGiang);

		log.info("TC_13_Step_25: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), customerID);

		log.info("TC_13_Step_26: Hien thi Chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_13_Step_27: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_13_Step_28: Chon tai khoan nguon");
		vcbAutoDebit.clickToTextID(driver, "com.VCB:id/tvContent");
		sourceAccount = vcbAutoDebit.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_13_Step_29: Chon loai dich vu 'Hoa don VNPT'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.VNPT_BILL_TEXT);

		log.info("TC_13_Step_30: Chon nha cung cap 'Da Nang'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, vnptKienGiang);

		log.info("TC_13_Step_31: Nhap Ma Khach Hang");
		vcbAutoDebit.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/layoutMaHoaDon");

		log.info("TC_13_Step_32: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_13_Step_33: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_13_Step_34: Xac nhan hien thi thong bao da dang ky dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), VCBAuto_Debit_Data.TEXT.REGISTED_MESSAGE);
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_13_Step_35: Click back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_15_ThayDoiThongTin_HoaDonVNPT_XacThucOTP(String otp) {

		log.info("TC_15_Step_02: Chon thay doi thong tin");
		home.clickToDynamicButtonLinkOrLinkText(driver, TEXT.CHANGE_SOURCE_ACCOUNT);
		vcbAutoDebit = PageFactoryManager.getVCBAutoDebitPageObject(driver);

		log.info("TC_15_Step_03: Chon loai dich vu 'Hoa don VNPT'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.VNPT_BILL_TEXT);

		log.info("TC_15_Step_04: Chon nha cung cap 'VNPT Kien Giang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, vnptKienGiang);

		log.info("TC_15_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, customerID + " - " + hostContact);

		log.info("TC_15_Step_06: Xac nhan hien thi dung chu hop dong");
		vcbAutoDebit.scrollDownToButton(driver, TEXT.CONTINUE);
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_15_Step_07: Xac nhan hien thi dung dia chi");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ADDRESS), address);

		log.info("TC_15_Step_08: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ACCOUNT_NUMBER), sourceAccount.account);

		log.info("TC_15_Step_09: Chon thay doi tai khoan");
		vcbAutoDebit.scrollDownToButton(driver, TEXT.CONTINUE);
		vcbAutoDebit.clickToDynamicDropdownByHeader(driver, TEXT.CHANGE_ACCOUNT, "2");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_15_Step_10: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_15_Step_11: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_15_Step_12: Hien thi man hinh xac nhan thong tin");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), TEXT.CONFIRM_INFO);

		log.info("TC_15_Step_13: Hien thi thong tin xac nhan");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), TEXT.CUSTOMER_PLEASE_CHANGE);

		log.info("TC_15_Step_14: Hien thi tai khoan nguon");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_15_Step_15: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.TYPE_SERVICE), VCBAuto_Debit_Data.TEXT.VNPT_BILL_TEXT);

		log.info("TC_15_Step_16: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), vnptKienGiang);

		log.info("TC_15_Step_17: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), customerID);

		log.info("TC_15_Step_18: Hien thi Chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);


		log.info("TC_15_Step_20: Chon phương thuc xac thuc");
		vcbAutoDebit.clickToTextID(driver, "com.VCB:id/tvptxt");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, TEXT.SMS_OTP);

		log.info("TC_15_Step_21: An nut 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_15_Step_22: Nhap du ki tu vao o nhap OTP");
		vcbAutoDebit.inputToDynamicOtp(driver, otp, TEXT.CONTINUE);

		log.info("TC_15_Step_23: An tiep button 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_15_Step_24: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.CHANGE_SUCCESS_TITLE);

		log.info("TC_15_Step_25: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_15_Step_26: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_15_Step_27: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SERVICE), VCBAuto_Debit_Data.TEXT.VNPT_BILL_TEXT);

		log.info("TC_15_Step_28: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), customerID);

		log.info("TC_15_Step_29: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), vnptKienGiang);

		log.info("TC_15_Step_30: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_15_Step_31: Chon loai dich vu 'Hoa don tien nuoc'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.VNPT_BILL_TEXT);

		log.info("TC_15_Step_32: Chon nha cung cap 'Nuoc Da Nang'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, vnptKienGiang);

		log.info("TC_15_Step_33: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, customerID + " - " + hostContact);

		log.info("TC_15_Step_34: Xac nhan hien thi dung chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_15_Step_35: Xac nhan hien thi dung dia chi");
		vcbAutoDebit.scrollDownToButton(driver, TEXT.CONTINUE);
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ADDRESS), address);

		log.info("TC_15_Step_36: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ACCOUNT_NUMBER), sourceAccount.account);

		log.info("TC_15_Step_37: Click back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_17_HuyDangKy_VNPT_XacThucOTP() {

		log.info("TC_17_Step_02: Chon huy dang ky");
		home.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.SERVICE_STOP);
		vcbAutoDebit = PageFactoryManager.getVCBAutoDebitPageObject(driver);

		log.info("TC_17_Step_03: Chon loai dich vu 'Hoa don tien nuoc'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.VNPT_BILL_TEXT);

		log.info("TC_17_Step_04: Chon nha cung cap 'Nuoc Da Nang'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, vnptKienGiang);

		log.info("TC_17_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, customerID + " - " + hostContact);

		log.info("TC_17_Step_06: Xac nhan hien thi dung chu hop dong");
		vcbAutoDebit.scrollDownToButton(driver, TEXT.CONTINUE);
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_17_Step_08: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.ACCOUNT_NUMBER), sourceAccount.account);

		log.info("TC_17_Step_09: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_17_Step_10: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_17_Step_11: Hien thi man hinh thong bao huy giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.CANCELED_SUCCESS_TITLE);

		log.info("TC_17_Step_12: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_17_Step_13: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_17_Step_14: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SERVICE), VCBAuto_Debit_Data.TEXT.VNPT_BILL_TEXT);

		log.info("TC_17_Step_15: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.SUPPLIER), customerID);

		log.info("TC_17_Step_16: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.CUSTOMER_CODE), vnptKienGiang);

		log.info("TC_17_Step_17: Xac nhan hien thi dung chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, TEXT.OWNER_CONTRACT), hostContact);

		log.info("TC_17_Step_18: Hien thi Icon Chia se");
		verifyTrue(vcbAutoDebit.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_17_Step_19: Hien thi Icon Luu anh");
		verifyTrue(vcbAutoDebit.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_17_Step_20: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_17_Step_21: Chon loai dich vu 'Hoa don VNPT'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.VNPT_BILL_TEXT);

		log.info("TC_17_Step_22: Chon nha cung cap 'VNPT kien giang'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, vnptKienGiang);

		log.info("TC_17_Step_23: Xac nhan ma khach hang khong con trong dropdown");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		verifyTrue(vcbAutoDebit.isDynamicMessageAndLabelTextUndisplayed(driver, customerID + " - " + hostContact));

		log.info("TC_17_Step_24: Dong dropdown va back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicButton(driver, TEXT.CLOSE);
		vcbAutoDebit.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
