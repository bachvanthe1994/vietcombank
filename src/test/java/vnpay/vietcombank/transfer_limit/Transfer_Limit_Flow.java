package vnpay.vietcombank.transfer_limit;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import pageObjects.TransferLimitPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;


public class Transfer_Limit_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private TransferLimitPageObject transferLimit;
	String passLogin = "";
	List<String> listActual;
	

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
		transferLimit = PageFactoryManager.getTransferLimitPageObject(driver);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

	}

	@Test
	public void TC_01_CaiHanMucThanhCongPhuongThucXacThucOTP() {
		log.info("TC_01_Step: Click menu header");
		transferLimit.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
		
		log.info("TC_01_Step: Click cai dat");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");
		
		log.info("TC_01_Step: Click cai dat han muc chuyen tien");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt hạn mức chuyển tiền");
		
		log.info("TC_01_Step: Lay han muc hien tai");
		String amountCurrent = transferLimit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvCurrentLimit");
		
		log.info("TC_01_Step: Chon han muc can nhap");
		transferLimit.clickToDynamicDropdownByHeader(driver, "Hạn mức hiện tại","3");
		listActual = transferLimit.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvContent");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, listActual.get(0));
		String amountLimit = listActual.get(0);
		
		log.info("TC_01_Click button tiep tuc");
		transferLimit.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_Kiem tra han muc hien tai");
		verifyEquals(transferLimit.getDynamicTextInTransactionDetail(driver, "Hạn mức hiện tại"), amountCurrent);

		log.info("TC_01_Kiem tra han muc thay doi");
		verifyEquals(transferLimit.getDynamicTextInTransactionDetail(driver, "Hạn mức thay đổi"), amountLimit);
		
		log.info("TC_01_Chon phuong thuc xac thuc");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");
		
		log.info("TC_01_Click button tiep tuc");
		transferLimit.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Step_Nhap ma xac thuc");
		transferLimit.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_01_Step_Tiep tuc");
		transferLimit.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(transferLimit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "CÀI ĐẶT HẠN MỨC GIAO DỊCH THÀNH CÔNG");
		
		log.info("TC_01_Verify so tien han muc");
		verifyEquals(transferLimit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), amountLimit);
		
		log.info("TC_01_Step_button dong");
		transferLimit.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_01_Step: Click cai dat han muc chuyen tien");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt hạn mức chuyển tiền");
		
		log.info("TC_01_Step: verify han muc hien tai la han muc vua moi cai dat");
		verifyEquals(transferLimit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvCurrentLimit"), amountLimit);
		
		log.info("TC_01_Step: Click button quay lai");
		transferLimit.clickToDynamicBackIcon(driver, "Cài đặt hạn mức chuyển tiền");
		
		log.info("TC_02_Step: Click menu home");
		transferLimit.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
		
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferLimit.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferLimit.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferLimit.inputToDynamicInputBox(driver, amountLimit + 1 +"", "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferLimit.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_01_Step_Tiep tuc");
		transferLimit.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Verify message thanh cong");
		verifyEquals(transferLimit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức "+ addCommasToLong(amountLimit + "") +" VND/nhóm dich vụ chuyển tiền, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 1900545413 của Vietcombank để được trợ giúp.");
	
		log.info("TC_01_Step_click button dong");
		transferLimit.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_01_Step: Click button quay lai");
		transferLimit.clickToDynamicBackIcon(driver, "Chuyển tiền nhanh 24/7");
	}
	
	@Test
	public void TC_02_CaiHanMucThanhCongPhuongThucXacThucSmartOTP() {
		log.info("TC_02_Step: Click menu header");
		transferLimit.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
		
		log.info("TC_02_Step: Click cai dat");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");
		
		log.info("TC_02_Step: Click cai dat han muc chuyen tien");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt hạn mức chuyển tiền");
		
		log.info("TC_02_Step: Lay han muc hien tai");
		String amountCurrent = transferLimit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvCurrentLimit");
		
		log.info("TC_02_Step: Chon han muc can nhap");
		transferLimit.clickToDynamicDropdownByHeader(driver, "Hạn mức hiện tại","3");
		listActual = transferLimit.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvContent");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, listActual.get(1));
		String amountLimit = listActual.get(1);
		
		log.info("TC_02_Click button tiep tuc");
		transferLimit.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_02_Kiem tra han muc hien tai");
		verifyEquals(transferLimit.getDynamicTextInTransactionDetail(driver, "Hạn mức hiện tại"), amountCurrent);

		log.info("TC_02_Kiem tra han muc thay doi");
		verifyEquals(transferLimit.getDynamicTextInTransactionDetail(driver, "Hạn mức thay đổi"), amountLimit);
		
		log.info("TC_02_Chon phuong thuc xac thuc");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");
		
		log.info("TC_02_Click button tiep tuc");
		transferLimit.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_Step_Nhap ma xac thuc");
		transferLimit.inputToDynamicSmartOtp(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_02_Step_Tiep tuc");
		transferLimit.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Tiep tuc");
		transferLimit.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_Verify message thanh cong");
		verifyEquals(transferLimit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "CÀI ĐẶT HẠN MỨC GIAO DỊCH THÀNH CÔNG");
		
		log.info("TC_02_Verify so tien han muc");
		verifyEquals(transferLimit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), amountLimit);
		
		log.info("TC_02_Step_button dong");
		transferLimit.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_02_Step: Click cai dat han muc chuyen tien");
		transferLimit.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt hạn mức chuyển tiền");
		
		log.info("TC_02_Step: verify han muc hien tai la han muc vua moi cai dat");
		verifyEquals(transferLimit.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvCurrentLimit"), amountLimit);
	}
	

	}
