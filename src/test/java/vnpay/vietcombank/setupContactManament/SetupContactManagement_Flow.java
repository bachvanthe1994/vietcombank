package vnpay.vietcombank.setupContactManament;

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
import pageObjects.SetupContactPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SetupContact_Data.Contact;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputDataInVCB;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputText_MoneyRecurrent;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;;

public class SetupContactManagement_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private SetupContactPageObject setupContact;
	private TransferMoneyInVcbPageObject transferInVCB;

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
		home = PageFactoryManager.getHomePageObject(driver);
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		setupContact = PageFactoryManager.getSetupContactPageObject(driver);

	}

	@Test
	public void TC_01_LuuDanhBaNguoiHuongTuGiaoDich() {
		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		home.clickToDynamicIcon(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_01_Step_02:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_01_Step_03: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_01_Step_04: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_01_Step_05: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, TittleData.AMOUNT);

		log.info("TC_01_Step_06: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_01_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_08: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_01_Step_09: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_01_Step_10: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_11: Nhap OTP");
		transferInVCB.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_12: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_25: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		if (transferInVCB.getPageSource(driver).contains(InputText_MoneyRecurrent.SAVE_RECEIVED_ACCOUNT_TEXT)) {
			log.info("TC_01_Step_13: Click button chia se");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputText_MoneyRecurrent.SAVE_RECEIVED_ACCOUNT_TEXT);
			transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btSave");

			log.info("TC_01_Step_15: Kiem tra user khong duoc cap quyen");
			verifyEquals(transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), InputText_MoneyRecurrent.SAVE_CONTACT_SUCCESS_MESSAGE);

			log.info("TC_01_Step_16: Click Dong");
			transferInVCB.clickToDynamicButton(driver, TittleData.CLOSE);
		}
		
		log.info("TC_01_Step_16: Click thuc hien giao dich ");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);
		
		log.info("TC_01_Step_17: Quay lai man home");
		transferInVCB.clickToDynamicBackIcon(driver, InputDataInVCB.TRANSFER_TYPE_VIETCOM_BANL);

		log.info("TC_01_Step_18: Mo tab 'Menu' ");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step_19: Mo sub-menu 'Cai dat' ");
		home.clickToDynamicButtonLinkOrLinkText(driver, TittleData.SETTING);

		log.info("TC_01_Step_20: Click vao sub-menu 'Quan ly danh ba' ");
		home.clickToDynamicButtonLinkOrLinkText(driver,  Home_Text_Elements.MANAGER_CONTACT);

		log.info("TC_01_Step_21: Chon 'Danh ba nguoi huong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, TittleData.CONTACT_BENFICAL);

		log.info("TC_01_Step_22: Xac nhan lai thong tin");
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey1"), Account_Data.Valid_Account.ACCOUNT2);
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvValue"), InputDataInVCB.TRANSFER_TYPE_VIETCOM_BANL);

		log.info("TC_01_Step_24: An nut ba cham ben canh thong tin nguoi huong");
		setupContact.clickToDynamicIconByText(driver, TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_01_Step_25: An vao phan 'Xoa'");
		setupContact.clickToDynamicIconByLinerLayout(driver, "com.VCB:id/liDelete");

		log.info("TC_01_Step_26: An nut 'Dong y' xoa danh ba");
		setupContact.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_01_Step_27: Kiem tra user khong duoc cap quyen");
		verifyEquals(transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TittleData.DELETE_CONTACT);

		log.info("TC_01_Step_28: Click Dong");
		transferInVCB.clickToDynamicButton(driver, TittleData.CLOSE);
	}

	@Test
	public void TC_02_TaoDanhBaNguoiHuong() {

		log.info("TC_02_Step_05: Tao moi 'Thong tin nguoi thu huong' ");
		setupContact.clickToTextID(driver, "com.VCB:id/btnAddContact");

		log.info("TC_02_Step_06: Mo DropdownList 'Chuyen Tien' ");
		setupContact.clickToDynamicDropdownByHeader(driver, Contact.INFOR_BENFICI, "1");

		log.info("TC_02_Step_07: Chon 'Chuyen tien nhanh qua so the' ");
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.CARD_FAST_TRANSACTION);

		log.info("TC_02_Step_08: Nhap thong tin vao o 'Ten goi nho' ");
		setupContact.inputIntoEditTextByID(driver, Contact.CONTACT_NAME_01, "com.VCB:id/edt1");

		log.info("TC_02_Step_09: Nhap thong tin vao o 'So the' ");
		setupContact.inputIntoEditTextByID(driver, Contact.CONTACT_CARD_NUMBER_01, "com.VCB:id/edt2");

		log.info("TC_02_Step_10: An nut 'Hoan thanh' ");
		setupContact.clickToDynamicButton(driver, Contact.COMPLETE);

		log.info("TC_02_Step_11: An nut 'OK' thong bao luu danh ba thanh cong");
		setupContact.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_02_Step_12: Xac nhan tạo thong tin nguoi huong thanh cong");
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey"), Contact.CONTACT_NAME_01);
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey1"), Contact.CONTACT_CARD_NUMBER_01);
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvValue"), Contact.CARD_FAST_TRANSACTION);

	}

	@Test
	public void TC_03_SuaDanhBaNguoiHuong() {

		log.info("TC_03_Step_01: An nut ba cham ben canh thong tin nguoi huong");
		setupContact.clickToDynamicIconByText(driver, Contact.CONTACT_NAME_01);

		log.info("TC_03_Step_02: An vao phan 'Cap nhat thong tin'");
		setupContact.clickToDynamicIconByLinerLayout(driver, "com.VCB:id/liEdit");

		log.info("TC_03_Step_03: Xac nhan lai thong tin");
		verifyEquals(setupContact.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSelect1"), Contact.CARD_FAST_TRANSACTION);
		verifyEquals(setupContact.getTextInEditTextFieldByID(driver, "com.VCB:id/edt1"), Contact.CONTACT_NAME_01);
		verifyEquals(setupContact.getTextInEditTextFieldByID(driver, "com.VCB:id/edt2"), Contact.CONTACT_CARD_NUMBER_01);

		log.info("TC_03_Step_04: Cap nhat lai thong tin");
		setupContact.clickToDynamicDropdownByHeader(driver, "Contact.INFOR_BENFICI", "1");
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.CARD_ACCOUNT_TRANSACTION);
		setupContact.clickToDynamicDropdownByHeader(driver, "Contact.INFOR_BENFICI", "2");
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.BANK_NAME_B);
		setupContact.inputIntoEditTextByID(driver, Contact.CONTACT_NAME_02, "com.VCB:id/edt1");
		setupContact.inputIntoEditTextByID(driver, Contact.CONTACT_CARD_NUMBER_02, "com.VCB:id/edt3");

		log.info("TC_03_Step_05: An nut 'Cap nhat' ");
		setupContact.clickToDynamicButton(driver, Contact.UPDATE);

		log.info("TC_03_Step_06: An nut 'OK' thong bao luu danh ba thanh cong");
		setupContact.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_03_Step_07: Xac nhan tạo thong tin nguoi huong thanh cong");
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey"), Contact.CONTACT_NAME_02);
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey1"), Contact.CONTACT_CARD_NUMBER_02);
		verifyTrue(setupContact.isDynamicMessageAndLabelTextDisplayed(driver, Contact.CARD_ACCOUNT_TRANSACTION));
	}

	@Test
	public void TC_04_XoaDanhBaNguoiHuong() {

		log.info("TC_04_Step_01: An nut ba cham ben canh thong tin nguoi huong");
		setupContact.clickToDynamicIconByText(driver, Contact.CONTACT_NAME_02);

		log.info("TC_04_Step_02: An vao phan 'Xoa'");
		setupContact.clickToDynamicIconByLinerLayout(driver, "com.VCB:id/liDelete");

		log.info("TC_04_Step_03: An nut 'Dong y' xoa danh ba");
		setupContact.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_04_Step_04: An nut 'Dong' xoa danh ba thanh cong");
		setupContact.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_04_Step_05: Xac nhan danh ba da bi xoa khoi danh sach");
		verifyTrue(setupContact.isDynamicMessageAndLabelTextUndisplayed(driver, Contact.CONTACT_NAME_02));
		verifyTrue((setupContact.isDynamicMessageAndLabelTextUndisplayed(driver, Contact.CARD_ACCOUNT_TRANSACTION)));
	}

	@Test
	public void TC_05_TaoDanhBaHoaDon() {

		log.info("TC_05_Step_01: Click ve 'Trang chu'");
		setupContact.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_05_Step_02: Click vao sub-menu 'Quan ly danh ba' ");
		home.clickToDynamicButtonLinkOrLinkText(driver,  Home_Text_Elements.MANAGER_CONTACT);

		log.info("TC_05_Step_03: Chon 'Danh ba hoa don'");
		home.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_INTERNET);
		setupContact = PageFactoryManager.getSetupContactPageObject(driver);

		log.info("TC_05_Step_04: Tao moi 'Danh ba hoa don'");
		setupContact.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ic_add_contact");

		log.info("TC_05_Step_05: Nhap thong tin 'Danh ba hoa don'");
		setupContact.clickToDynamicDropdownByHeader(driver, Contact.INFOR_CONTACT_BILL, "1");
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.ADSL_SERVICE);
		setupContact.clickToDynamicDropdownByHeader(driver, Contact.INFOR_CONTACT_BILL, "2");
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.FPT_SUPPLIER_NAME);
		setupContact.inputIntoEditTextByID(driver, Contact.CONTACT_NAME_01, "com.VCB:id/edt1");
		setupContact.inputIntoEditTextByID(driver, Contact.CONTACT_CARD_NUMBER_01, "com.VCB:id/edt2");

		log.info("TC_05_Step_06: An nut 'Hoan thanh' ");
		setupContact.clickToDynamicButton(driver, Contact.COMPLETE);

		log.info("TC_05_Step_07: An nut 'OK' thong bao luu danh ba thanh cong");
		setupContact.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_05_Step_08: Xac nhan tạo thong tin nguoi huong thanh cong");
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey"), Contact.CONTACT_NAME_01);
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey1"), Contact.CONTACT_CARD_NUMBER_01);
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvValue"), Contact.ADSL_SERVICE);
	}

	@Test
	public void TC_06_SuaDanhBaHoaDon() {

		log.info("TC_06_Step_01: An nut ba cham ben canh thong tin nguoi huong");
		setupContact.clickToDynamicIconByText(driver, Contact.CONTACT_NAME_01);

		log.info("TC_06_Step_02: An vao phan 'Cap nhat thong tin'");
		setupContact.clickToDynamicIconByLinerLayout(driver, "com.VCB:id/liEdit");

		log.info("TC_06_Step_03: Xac nhan lai thong tin");
		verifyEquals(setupContact.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSelect1"), Contact.ADSL_SERVICE);
		verifyEquals(setupContact.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSelect2"), Contact.FPT_SUPPLIER_NAME);
		verifyEquals(setupContact.getTextInEditTextFieldByID(driver, "com.VCB:id/edt1"), Contact.CONTACT_NAME_01);
		verifyEquals(setupContact.getTextInEditTextFieldByID(driver, "com.VCB:id/edt2"), Contact.CONTACT_CARD_NUMBER_01);

		log.info("TC_06_Step_04: Cap nhat lai thong tin 'Danh ba hoa don'");
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.ADSL_SERVICE);
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.VNPT_SERVICE);
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.SUPPLIER);
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.VNPT_SUPPLIER_NAME);
		setupContact.inputIntoEditTextByID(driver, Contact.CONTACT_NAME_02, "com.VCB:id/edt1");
		setupContact.inputIntoEditTextByID(driver, Contact.CONTACT_CARD_NUMBER_02, "com.VCB:id/edt2");

		log.info("TC_06_Step_05: An nut 'Cap nhat' ");
		setupContact.clickToDynamicButton(driver, Contact.UPDATE);

		log.info("TC_06_Step_06: An nut 'Dong' thong bao luu danh ba thanh cong");
		setupContact.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_06_Step_07: Xac nhan tạo thong tin nguoi huong thanh cong");
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey"), Contact.CONTACT_NAME_02);
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey1"), Contact.CONTACT_CARD_NUMBER_02);
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvValue"), Contact.VNPT_SERVICE);
	}

	@Test
	public void TC_07_XoaDanhBaHoaDon() {

		log.info("TC_07_Step_01: An nut ba cham ben canh thong tin nguoi huong");
		setupContact.clickToDynamicIconByText(driver, Contact.CONTACT_NAME_02);

		log.info("TC_07_Step_02: An vao phan 'Xoa'");
		setupContact.clickToDynamicIconByLinerLayout(driver, "com.VCB:id/liDelete");

		log.info("TC_07_Step_03: An nut 'Dong y' xoa danh ba");
		setupContact.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_07_Step_04: An nut 'Dong' xoa danh ba thanh cong");
		setupContact.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_07_Step_05: Xac nhan danh ba da bi xoa khoi danh sach");
		verifyTrue((setupContact.isDynamicMessageAndLabelTextUndisplayed(driver, Contact.CONTACT_NAME_02)));
		verifyTrue((setupContact.isDynamicMessageAndLabelTextUndisplayed(driver, Contact.VNPT_SERVICE)));
	}

	@Test
	public void TC_08_TaoDanhBaThe_ViDienTu() {

		log.info("TC_08_Step_01: Click ve 'Trang chu'");
		setupContact.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_08_Step_02: Click vao sub-menu 'Quan ly danh ba' ");
		home.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.MANAGER_CONTACT);

		log.info("TC_08_Step_03: Chon 'Danh ba the/vi dien tu'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Danh bạ thẻ/ ví điện tử");
		setupContact = PageFactoryManager.getSetupContactPageObject(driver);

		log.info("TC_08_Step_04: Tao moi 'Danh ba the/vi dien tu'");
		setupContact.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ic_add_contact");

		log.info("TC_08_Step_05: Nhap thong tin 'Danh ba the/vi dien tu'");
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.BANK_CARD_CHARGE);
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.E_WALLET_CHARGE);
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.SUPPLIER);
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.MOMO_EWALLET);
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.TYPE_SERVICE);
		setupContact.clickToDynamicButtonLinkOrLinkText(driver,Contact.CHARGE_TO_WALLET_MOMO_B);
		setupContact.inputToDynamicInputBox(driver, Contact.CONTACT_NAME_02, Contact.HINT_NAME);
		setupContact.inputToDynamicInputBox(driver, Contact.CONTACT_CARD_NUMBER_02, Contact.CARD_NUMBER_CUSTOMER);

		log.info("TC_08_Step_06: An nut 'Hoan thanh' ");
		setupContact.clickToDynamicButton(driver, Contact.COMPLETE);

		log.info("TC_08_Step_07: An nut 'OK' thong bao luu danh ba thanh cong");
		setupContact.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_08_Step_08: Xac nhan tạo thong tin nguoi huong thanh cong");
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey"), Contact.CONTACT_NAME_02);
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey1"), Contact.CONTACT_CARD_NUMBER_02);
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvValue"), Contact.CHARGE_TO_WALLET);
	}

	@Test
	public void TC_09_SuaDanhBaThe_ViDienTu() {

		log.info("TC_09_Step_01: An nut ba cham ben canh thong tin nguoi huong");
		setupContact.clickToDynamicIconByText(driver, Contact.CONTACT_NAME_02);

		log.info("TC_09_Step_02: An vao phan 'Cap nhat thong tin'");
		setupContact.clickToDynamicIconByLinerLayout(driver, "com.VCB:id/liEdit");

		log.info("TC_09_Step_03: Xac nhan lai thong tin");
		verifyEquals(setupContact.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvContent"), Contact.E_WALLET_CHARGE);
		verifyTrue(setupContact.isDynamicMessageAndLabelTextDisplayed(driver, Contact.MOMO_EWALLET));
		verifyTrue(setupContact.isDynamicMessageAndLabelTextDisplayed(driver, Contact.CHARGE_TO_WALLET_MOMO_B));
		verifyTrue(setupContact.isDynamicTextInInputBoxDisPlayed(driver, Contact.CONTACT_NAME_02));
		verifyTrue(setupContact.isDynamicTextInInputBoxDisPlayed(driver, Contact.CONTACT_CARD_NUMBER_02));

		log.info("TC_09_Step_04: An nut 'Cap nhat thong tin'");
		setupContact.clickToDynamicAcceptButton(driver, "com.VCB:id/btEdit");
		;

		log.info("TC_09_Step_05: Nhap thong tin 'Danh ba the/vi dien tu'");
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.E_WALLET_CHARGE);
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.BANK_CARD_CHARGE);
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.SUPPLIER);
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, Contact.IDEM_SUPPLIER);
		setupContact.clickToDynamicIconByLinerLayout(driver, "com.VCB:id/layoutTenGoiNho");
		setupContact.inputToDynamicInputBox(driver, Contact.CONTACT_NAME_01, Contact.HINT_NAME);
		setupContact.clickToDynamicIconByLinerLayout(driver, "com.VCB:id/layoutMa");
		setupContact.inputToDynamicInputBox(driver, Contact.CONTACT_CARD_NUMBER_01, Contact.CARD_NUMBER_CUSTOMER);

		log.info("TC_09_Step_06: An nut 'Hoan thanh' ");
		setupContact.clickToDynamicButton(driver, Contact.COMPLETE);

		log.info("TC_09_Step_07: An nut 'OK' thong bao luu danh ba thanh cong");
		setupContact.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_09_Step_08: Xac nhan tạo thong tin nguoi huong thanh cong");
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey"), Contact.CONTACT_NAME_01);
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey1"), Contact.CONTACT_CARD_NUMBER_01);
		verifyEquals(setupContact.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvValue"), Contact.CHARGE_TO_CARD);
	}

	@Test
	public void TC_10_XoaDanhBaThe_ViDienTu() {

		log.info("TC_10_Step_01: An nut ba cham ben canh thong tin nguoi huong");
		setupContact.clickToDynamicIconByText(driver, Contact.CONTACT_NAME_01);

		log.info("TC_10_Step_02: An vao phan 'Xoa'");
		setupContact.clickToDynamicIconByLinerLayout(driver, "com.VCB:id/liDelete");

		log.info("TC_10_Step_03: An nut 'Dong y' xoa danh ba");
		setupContact.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_10_Step_04: An nut 'Dong' xoa danh ba thanh cong");
		setupContact.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_10_Step_05: Xac nhan danh ba da bi xoa khoi danh sach");
		verifyTrue((setupContact.isDynamicMessageAndLabelTextUndisplayed(driver, Contact.CONTACT_NAME_01)));
		verifyTrue((setupContact.isDynamicMessageAndLabelTextUndisplayed(driver, Contact.CHARGE_TO_CARD)));
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
