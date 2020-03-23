package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.TransferInVCBRecurrent;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Transfer_Money_Recurrent_Validation_Part_7 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private TransferMoneyInVcbPageObject transferRecurrent;
	private HomePageObject homePage;

	TransferInVCBRecurrent info = new TransferInVCBRecurrent(Account_Data.Valid_Account.ACCOUNT2, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "Mật khẩu đăng nhập");

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

		transferRecurrent = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_00_01_Click Chuyen tien trong ngan hang");
		homePage.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_00_02_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_00_03_Chon tai khoan nguon");
		transferRecurrent.scrollUpToText(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_00_04_Nhap tai khoan dich");
		transferRecurrent.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/ chọn tài khoản thụ hưởng");

		log.info("TC_00_05_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info.frequencyNumber);

		log.info("TC_00_06_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info.money, "Số tiền");

		log.info("TC_00_07_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.fee);

		log.info("TC_00_08_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.note, "Thông tin giao dịch", "3");

		log.info("TC_00_09_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_00_10_Chon phuong thuc xac thuc");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_00_11_Click Tiep tuc man hinh xac nhan thong tin");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_00_12_Nhap password");
		transferRecurrent.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_00_13_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_00_14_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

	}

	@Test
	public void TC_01_KiemTraButton_LuuAnh() {
		log.info("TC_01_01_Click nut Luu anh");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Lưu ảnh");

		log.info("TC_01_02_Kiem tra message thong bao da luu anh");
		verifyEquals(transferRecurrent.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), "Ảnh đã lưu trong thư viện");

		log.info("TC_01_03_Kiem tra nut Dong");
		verifyTrue(transferRecurrent.isDynamicButtonByTextDisplayed(driver, "Đóng"));

		log.info("TC_01_04_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");

	}

	@Test
	public void TC_02_KiemTraButton_LuuThuHuong() {
		log.info("TC_02_01_Click nut Luu thu huong");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Lưu thụ hưởng");

		log.info("TC_02_02_Kiem tra man hinh Luu thu huong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Lưu thụ hưởng"));

		log.info("TC_02_03_Kiem tra loai chuyen tien");
		verifyEquals(transferRecurrent.getDynamicTextInDropDownByHeader(driver, "Thông tin người chuyển", "2"), "Chuyển tiền trong Vietcombank");

		log.info("TC_02_04_Kiem tra ten nguoi thu huong");
		verifyEquals(transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin người chuyển", "2"), "NGUYEN NGOC TOAN");

		log.info("TC_02_05_Kiem tra so tai khoan");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, info.destinationAccount));

		log.info("TC_02_06_Click nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Lưu thụ hưởng");

	}

	@Test
	public void TC_03_KiemTraButton_ThucHienGiaoDichMoi() {
		log.info("TC_03_01_Click Thuc hien giao dich moi");
		login.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_03_02_Kiem tra man hinh Chuyen tien trong VCB đã reset thong tin");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_03_03_Kiem tra title 'Chuyen tien trong Vietcombank' ");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền trong Vietcombank"));

		log.info("TC_03_04_Kiem tra label Thong tin nguoi chuyen ");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Thông tin người chuyển"));

		log.info("TC_03_05_Kiem tra label Thong tin nguoi huong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Thông tin người hưởng"));

		log.info("TC_03_06_Kiem tra tan suat");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Tần suất"));

		log.info("TC_03_07_Kiem tra Thong tin giao dich");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Thông tin giao dịch"));

		log.info("TC_03_08_Kiem tra link Han muc");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Hạn mức"));

		transferRecurrent.scrollDownToText(driver, "Thông tin giao dịch");

		log.info("TC_03_09_Kiem tra textbox so tien");
		verifyTrue(transferRecurrent.isDynamicTextInInputBoxDisPlayed(driver, "Số tiền"));

		log.info("TC_03_10_Kiem tra combo Phi giao dich");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Phí giao dịch người chuyển trả"));

		log.info("TC_03_11_Kiem tra button Tiep tuc");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Tiếp tục"));

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
