package vnpay.vietcombank.vcb_auto_debit;

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
import pageObjects.VCBAutoDebitPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.VCBAuto_Debit_Data;

public class Vcb_Auto_Debit_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private VCBAutoDebitPageObject vcbAutoDebit;

	private String hostContact, address;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_DangKyDichVu_HoaDonTienDien_XacThucOTP(String otp) {

		home = PageFactoryManager.getHomePageObject(driver);
		home.scrollDownToText(driver, "Nạp tiền điện tử");
		log.info("TC_01_Step_01: Click vao VCB-Auto Debit");
		home.clickToDynamicButtonLinkOrLinkText(driver, "VCB-Auto Debit");

		log.info("TC_01_Step_02: Chon Dang ki dich vu");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký dịch vụ");
		vcbAutoDebit = PageFactoryManager.getVCBAutoDebitPageObject(driver);

		log.info("TC_01_Step_03: Chon tai khoan nguon");
		vcbAutoDebit.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_03: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_01_Step_04: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.inputToDynamicEditviewByLinearlayoutId(driver, VCBAuto_Debit_Data.TEXT.CUSTOMER_ID_01, "com.VCB:id/layoutMaHoaDon");

		log.info("TC_01_Step_06: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_Step_07: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_08: Hien thi man hinh xac nhan thong tin");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_01_Step_09: Hien thi thông tin xac nhan");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), "Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo");

		log.info("TC_01_Step_10: Hien thi tai khoan nguon");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_11: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Dịch vụ"), VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_01_Step_12: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Nhà cung cấp"), VCBAuto_Debit_Data.TEXT.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_13: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Mã khách hàng"), VCBAuto_Debit_Data.TEXT.CUSTOMER_ID_01);

		log.info("TC_01_Step_14: Hien thi Chu hop dong");
		hostContact = vcbAutoDebit.getDynamicTextByLabel(driver, "Chủ hợp đồng");

		log.info("TC_01_Step_15: Hien thi dia chi");
		address = vcbAutoDebit.getDynamicTextByLabel(driver, "Địa chỉ");

		log.info("TC_01_Step_16: Chon phương thuc xac thuc");
		vcbAutoDebit.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_01_Step_17: An nut 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_18: Nhap du ki tu vao o nhap OTP");
		vcbAutoDebit.inputToDynamicOtp(driver, otp, "Tiếp tục");

		log.info("TC_01_Step_19: An tiep button 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_20: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.REGISTER_SUCCESS_TITLE);

		log.info("TC_01_Step_21: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_01_Step_22: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_01_Step_23: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Dịch vụ"), VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_01_Step_24: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Nhà cung cấp"), VCBAuto_Debit_Data.TEXT.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_25: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Mã khách hàng"), VCBAuto_Debit_Data.TEXT.CUSTOMER_ID_01);

		log.info("TC_01_Step_26: Hien thi Chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Chủ hợp đồng"), hostContact);

		log.info("TC_01_Step_27: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_28: Chon tai khoan nguon");
		vcbAutoDebit.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_29: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_01_Step_30: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_31: Nhap Ma Khach Hang");
		vcbAutoDebit.inputToDynamicEditviewByLinearlayoutId(driver, VCBAuto_Debit_Data.TEXT.CUSTOMER_ID_01, "com.VCB:id/layoutMaHoaDon");

		log.info("TC_01_Step_32: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_Step_33: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_34: Xac nhan hien thi thong bao da dang ky dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), VCBAuto_Debit_Data.TEXT.REGISTED_MESSAGE);
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_01_Step_35: Click back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_02_ThayDoiThongTin_HoaDonTienDien_XacThucOTP(String otp) {

		log.info("TC_02_Step_01: Click vao VCB-Auto Debit");
		home.clickToDynamicButtonLinkOrLinkText(driver, "VCB-Auto Debit");

		log.info("TC_02_Step_02: Chon thay doi thong tin");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Thay đổi thông tin");
		vcbAutoDebit = PageFactoryManager.getVCBAutoDebitPageObject(driver);

		log.info("TC_02_Step_03: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_02_Step_04: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.EVN_MIEN_TRUNG);

		log.info("TC_02_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.CUSTOMER_ID_01 + " - " + hostContact);

		log.info("TC_02_Step_06: Xac nhan hien thi dung chu hop dong");
		vcbAutoDebit.scrollDownToText(driver, "Tiếp tục");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Chủ hợp đồng"), hostContact);

		log.info("TC_02_Step_07: Xac nhan hien thi dung dia chi");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Địa chỉ"), address);

		log.info("TC_02_Step_08: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Số tài khoản"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_02_Step_09: Chon thay doi tai khoan");
		vcbAutoDebit.scrollDownToButton(driver, "Tiếp tục");
		vcbAutoDebit.clickToDynamicDropdownByHeader(driver, "Tài khoản thay đổi", "2");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_02_Step_10: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_02_Step_11: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_12: Hien thi man hinh xac nhan thong tin");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_02_Step_13: Hien thi thong tin xac nhan");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), "Quý khách vui lòng kiểm tra thông tin giao dịch đã thay đổi.");

		log.info("TC_02_Step_14: Hien thi tai khoan nguon");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_02_Step_15: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Loại dịch vụ"), VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_02_Step_16: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Nhà cung cấp"), VCBAuto_Debit_Data.TEXT.EVN_MIEN_TRUNG);

		log.info("TC_02_Step_17: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Mã khách hàng"), VCBAuto_Debit_Data.TEXT.CUSTOMER_ID_01);

		log.info("TC_02_Step_18: Hien thi Chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Chủ hợp đồng"), hostContact);

		log.info("TC_02_Step_19: Hien thi dia chi");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Địa chỉ"), address);

		log.info("TC_02_Step_20: Chon phương thuc xac thuc");
		vcbAutoDebit.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_02_Step_21: An nut 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_22: Nhap du ki tu vao o nhap OTP");
		vcbAutoDebit.inputToDynamicOtp(driver, otp, "Tiếp tục");

		log.info("TC_02_Step_23: An tiep button 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_24: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.CHANGE_SUCCESS_TITLE);

		log.info("TC_02_Step_25: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_02_Step_26: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_02_Step_27: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Dịch vụ"), VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_02_Step_28: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Nhà cung cấp"), VCBAuto_Debit_Data.TEXT.EVN_MIEN_TRUNG);

		log.info("TC_02_Step_29: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Mã khách hàng"), VCBAuto_Debit_Data.TEXT.CUSTOMER_ID_01);

		log.info("TC_02_Step_30: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_31: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_02_Step_32: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.EVN_MIEN_TRUNG);

		log.info("TC_02_Step_33: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.CUSTOMER_ID_01 + " - " + hostContact);

		log.info("TC_02_Step_34: Xac nhan hien thi dung chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Chủ hợp đồng"), hostContact);

		log.info("TC_02_Step_35: Xac nhan hien thi dung dia chi");
		vcbAutoDebit.scrollDownToText(driver, "Tiếp tục");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Địa chỉ"), address);

		log.info("TC_02_Step_36: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Số tài khoản"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_02_Step_37: Click back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_03_HuyDangKy_HoaDonTienDien_XacThucOTP() {

		log.info("TC_03_Step_01: Click vao VCB-Auto Debit");
		home.clickToDynamicButtonLinkOrLinkText(driver, "VCB-Auto Debit");

		log.info("TC_03_Step_02: Chon huy dang ky");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký");
		vcbAutoDebit = PageFactoryManager.getVCBAutoDebitPageObject(driver);

		log.info("TC_03_Step_03: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_03_Step_04: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.EVN_MIEN_TRUNG);

		log.info("TC_03_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.CUSTOMER_ID_01 + " - " + hostContact);

		log.info("TC_03_Step_06: Xac nhan hien thi dung chu hop dong");
		vcbAutoDebit.scrollDownToText(driver, "Tiếp tục");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Chủ hợp đồng"), hostContact);

		log.info("TC_03_Step_07: Xac nhan hien thi dung dia chi");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Địa chỉ"), address);

		log.info("TC_03_Step_08: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Số tài khoản"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_03_Step_09: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_03_Step_10: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_11: Hien thi man hinh thong bao huy giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.CANCELED_SUCCESS_TITLE);

		log.info("TC_03_Step_12: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_03_Step_13: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_03_Step_14: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Dịch vụ"), VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_03_Step_15: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Nhà cung cấp"), VCBAuto_Debit_Data.TEXT.EVN_MIEN_TRUNG);

		log.info("TC_03_Step_16: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Mã khách hàng"), VCBAuto_Debit_Data.TEXT.CUSTOMER_ID_01);

		log.info("TC_03_Step_17: Xac nhan hien thi dung chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Chủ hợp đồng"), hostContact);

		log.info("TC_03_Step_18: Hien thi Icon Chia se");
		verifyTrue(vcbAutoDebit.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_03_Step_19: Hien thi Icon Luu anh");
		verifyTrue(vcbAutoDebit.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_03_Step_20: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_21: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.ELECTRIC_BILL_TEXT);

		log.info("TC_03_Step_22: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.EVN_MIEN_TRUNG);

		log.info("TC_03_Step_23: Xac nhan ma khach hang khong con trong dropdown");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		verifyTrue(vcbAutoDebit.isDynamicMessageAndLabelTextUndisplayed(driver, VCBAuto_Debit_Data.TEXT.CUSTOMER_ID_01 + " - " + hostContact));

		log.info("TC_03_Step_24: Dong dropdown va back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicButton(driver, "Đóng");
		vcbAutoDebit.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	// Chưa có data test
//	@Parameters({ "otp" })
//	@Test
	public void TC_04_DangKyDichVu_HoaDonTienNuoc_XacThucOTP(String otp) {

		home = PageFactoryManager.getHomePageObject(driver);
		home.scrollDownToText(driver, "Nạp tiền điện tử");
		log.info("TC_04_Step_01: Click vao VCB-Auto Debit");
		home.clickToDynamicButtonLinkOrLinkText(driver, "VCB-Auto Debit");

		log.info("TC_04_Step_02: Chon Dang ki dich vu");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký dịch vụ");
		vcbAutoDebit = PageFactoryManager.getVCBAutoDebitPageObject(driver);

		log.info("TC_04_Step_03: Chon tai khoan nguon");
		vcbAutoDebit.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_04_Step_03: Chon loai dich vu 'Hoa don tien nuoc'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_04_Step_04: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_DAWACO);

		log.info("TC_04_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.inputToDynamicEditviewByLinearlayoutId(driver, VCBAuto_Debit_Data.TEXT.WATER_CUSTOMER_01, "com.VCB:id/layoutMaHoaDon");

		log.info("TC_04_Step_06: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_04_Step_07: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_04_Step_08: Hien thi man hinh xac nhan thong tin");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_04_Step_09: Hien thi thông tin xac nhan");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), "Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo");

		log.info("TC_04_Step_10: Hien thi tai khoan nguon");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_04_Step_11: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Dịch vụ"), VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_04_Step_12: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Nhà cung cấp"), VCBAuto_Debit_Data.TEXT.WATER_DAWACO);

		log.info("TC_04_Step_13: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Mã khách hàng"), VCBAuto_Debit_Data.TEXT.WATER_CUSTOMER_01);

		log.info("TC_04_Step_14: Hien thi Chu hop dong");
		hostContact = vcbAutoDebit.getDynamicTextByLabel(driver, "Chủ hợp đồng");

		log.info("TC_04_Step_15: Hien thi dia chi");
		address = vcbAutoDebit.getDynamicTextByLabel(driver, "Địa chỉ");

		log.info("TC_04_Step_16: Chon phương thuc xac thuc");
		vcbAutoDebit.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_04_Step_17: An nut 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_04_Step_18: Nhap du ki tu vao o nhap OTP");
		vcbAutoDebit.inputToDynamicOtp(driver, otp, "Tiếp tục");

		log.info("TC_04_Step_19: An tiep button 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_04_Step_20: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.REGISTER_SUCCESS_TITLE);

		log.info("TC_04_Step_21: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_04_Step_22: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_04_Step_23: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Dịch vụ"), VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_04_Step_24: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Nhà cung cấp"), VCBAuto_Debit_Data.TEXT.WATER_DAWACO);

		log.info("TC_04_Step_25: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Mã khách hàng"), VCBAuto_Debit_Data.TEXT.WATER_CUSTOMER_01);

		log.info("TC_04_Step_26: Hien thi Chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Chủ hợp đồng"), hostContact);

		log.info("TC_04_Step_27: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_04_Step_28: Chon tai khoan nguon");
		vcbAutoDebit.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_04_Step_29: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_04_Step_30: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_DAWACO);

		log.info("TC_04_Step_31: Nhap Ma Khach Hang");
		vcbAutoDebit.inputToDynamicEditviewByLinearlayoutId(driver, VCBAuto_Debit_Data.TEXT.WATER_CUSTOMER_01, "com.VCB:id/layoutMaHoaDon");

		log.info("TC_04_Step_32: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_04_Step_33: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_04_Step_34: Xac nhan hien thi thong bao da dang ky dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), VCBAuto_Debit_Data.TEXT.REGISTED_MESSAGE);
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_04_Step_35: Click back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

//	@Parameters({ "otp" })
//	@Test
	public void TC_05_ThayDoiThongTin_HoaDonTienDien_XacThucOTP(String otp) {

		log.info("TC_05_Step_01: Click vao VCB-Auto Debit");
		home.clickToDynamicButtonLinkOrLinkText(driver, "VCB-Auto Debit");

		log.info("TC_05_Step_02: Chon thay doi thong tin");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Thay đổi thông tin");
		vcbAutoDebit = PageFactoryManager.getVCBAutoDebitPageObject(driver);

		log.info("TC_05_Step_03: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_05_Step_04: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_DAWACO);

		log.info("TC_05_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_CUSTOMER_01 + " - " + hostContact);

		log.info("TC_05_Step_06: Xac nhan hien thi dung chu hop dong");
		vcbAutoDebit.scrollDownToText(driver, "Tiếp tục");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Chủ hợp đồng"), hostContact);

		log.info("TC_05_Step_07: Xac nhan hien thi dung dia chi");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Địa chỉ"), address);

		log.info("TC_05_Step_08: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Số tài khoản"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_05_Step_09: Chon thay doi tai khoan");
		vcbAutoDebit.scrollDownToButton(driver, "Tiếp tục");
		vcbAutoDebit.clickToDynamicDropdownByHeader(driver, "Tài khoản thay đổi", "2");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_05_Step_10: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_05_Step_11: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_12: Hien thi man hinh xac nhan thong tin");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_05_Step_13: Hien thi thong tin xac nhan");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), "Quý khách vui lòng kiểm tra thông tin giao dịch đã thay đổi.");

		log.info("TC_05_Step_14: Hien thi tai khoan nguon");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_05_Step_15: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Loại dịch vụ"), VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_05_Step_16: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Nhà cung cấp"), VCBAuto_Debit_Data.TEXT.WATER_DAWACO);

		log.info("TC_05_Step_17: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Mã khách hàng"), VCBAuto_Debit_Data.TEXT.WATER_CUSTOMER_01);

		log.info("TC_05_Step_18: Hien thi Chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Chủ hợp đồng"), hostContact);

		log.info("TC_05_Step_19: Hien thi dia chi");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Địa chỉ"), address);

		log.info("TC_05_Step_20: Chon phương thuc xac thuc");
		vcbAutoDebit.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_05_Step_21: An nut 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_22: Nhap du ki tu vao o nhap OTP");
		vcbAutoDebit.inputToDynamicOtp(driver, otp, "Tiếp tục");

		log.info("TC_05_Step_23: An tiep button 'Tiep tuc'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_24: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.CHANGE_SUCCESS_TITLE);

		log.info("TC_05_Step_25: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_05_Step_26: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_05_Step_27: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Dịch vụ"), VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_05_Step_28: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Nhà cung cấp"), VCBAuto_Debit_Data.TEXT.WATER_DAWACO);

		log.info("TC_05_Step_29: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Mã khách hàng"), VCBAuto_Debit_Data.TEXT.WATER_CUSTOMER_01);

		log.info("TC_05_Step_30: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_31: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_05_Step_32: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_DAWACO);

		log.info("TC_05_Step_33: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_CUSTOMER_01 + " - " + hostContact);

		log.info("TC_05_Step_34: Xac nhan hien thi dung chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Chủ hợp đồng"), hostContact);

		log.info("TC_05_Step_35: Xac nhan hien thi dung dia chi");
		vcbAutoDebit.scrollDownToText(driver, "Tiếp tục");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Địa chỉ"), address);

		log.info("TC_05_Step_36: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Số tài khoản"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_05_Step_37: Click back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

//	@Test
	public void TC_06_HuyDangKy_HoaDonTienDien_XacThucOTP() {

		log.info("TC_06_Step_01: Click vao VCB-Auto Debit");
		home.clickToDynamicButtonLinkOrLinkText(driver, "VCB-Auto Debit");

		log.info("TC_06_Step_02: Chon huy dang ky");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký");
		vcbAutoDebit = PageFactoryManager.getVCBAutoDebitPageObject(driver);

		log.info("TC_06_Step_03: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_06_Step_04: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_DAWACO);

		log.info("TC_06_Step_05: Nhap Ma Khach Hang");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_CUSTOMER_01 + " - " + hostContact);

		log.info("TC_06_Step_06: Xac nhan hien thi dung chu hop dong");
		vcbAutoDebit.scrollDownToText(driver, "Tiếp tục");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Chủ hợp đồng"), hostContact);

		log.info("TC_06_Step_07: Xac nhan hien thi dung dia chi");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Địa chỉ"), address);

		log.info("TC_06_Step_08: Xac nhan hien thi dung so tai khoan");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Số tài khoản"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_06_Step_09: Click checkbox");
		vcbAutoDebit.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_06_Step_10: Click Tiep tuc");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_06_Step_11: Hien thi man hinh thong bao huy giao dich thanh cong");
		verifyEquals(vcbAutoDebit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), VCBAuto_Debit_Data.TEXT.CANCELED_SUCCESS_TITLE);

		log.info("TC_06_Step_12: Hien thi icon Thanh cong");
		verifyTrue(vcbAutoDebit.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_06_Step_13: Hien thi thoi gian giao dich");
		verifyTrue(vcbAutoDebit.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_06_Step_14: Hien thi loai dich vu");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Dịch vụ"), VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_06_Step_15: Hien thi nha cung cap");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Nhà cung cấp"), VCBAuto_Debit_Data.TEXT.WATER_DAWACO);

		log.info("TC_06_Step_16: Hien thi ma khach hang");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Mã khách hàng"), VCBAuto_Debit_Data.TEXT.WATER_CUSTOMER_01);

		log.info("TC_06_Step_17: Xac nhan hien thi dung chu hop dong");
		verifyEquals(vcbAutoDebit.getDynamicTextByLabel(driver, "Chủ hợp đồng"), hostContact);

		log.info("TC_06_Step_18: Hien thi Icon Chia se");
		verifyTrue(vcbAutoDebit.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_06_Step_19: Hien thi Icon Luu anh");
		verifyTrue(vcbAutoDebit.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_06_Step_20: An tiep button 'Thuc hien giao dich moi'");
		vcbAutoDebit.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_06_Step_21: Chon loai dich vu 'Hoa don tien dien'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutDichVu");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_BILL_TEXT);

		log.info("TC_06_Step_22: Chon nha cung cap 'EVN mien Trung'");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNhaCungCap");
		vcbAutoDebit.clickToDynamicButtonLinkOrLinkText(driver, VCBAuto_Debit_Data.TEXT.WATER_DAWACO);

		log.info("TC_06_Step_23: Xac nhan ma khach hang khong con trong dropdown");
		vcbAutoDebit.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinMaKhachHang");
		verifyTrue(vcbAutoDebit.isDynamicMessageAndLabelTextUndisplayed(driver, VCBAuto_Debit_Data.TEXT.WATER_CUSTOMER_01 + " - " + hostContact));

		log.info("TC_06_Step_24: Dong dropdown va back ve man hinh chinh");
		vcbAutoDebit.clickToDynamicButton(driver, "Đóng");
		vcbAutoDebit.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
