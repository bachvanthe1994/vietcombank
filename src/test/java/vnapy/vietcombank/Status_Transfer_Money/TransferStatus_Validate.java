package vnapy.vietcombank.Status_Transfer_Money;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyStatusPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.StatusTransfer_Data;

public class TransferStatus_Validate extends Base {
    AndroidDriver<AndroidElement> driver;
    private LogInPageObject login;
    private TransferMoneyStatusPageObject transferStatus;

    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
	startServer();
	log.info("Before class: Mo app ");
	driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

	login = PageFactoryManager.getLoginPageObject(driver);
	transferStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);

	log.info("Before class: Click Allow Button");
	login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

	log.info("TC_00_Step_1: chon tiep tuc");
	login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

	log.info("TC_00_Step_2: chon tiep tuc");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_00_Step_3: chon tiep tuc");
	login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

	log.info("TC_00_Step_4: chon tiep tuc");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_00_Step_5: chon tiep tuc");
	login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

	log.info("TC_00_Step_6: chon tiep tuc");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_00_Step_7: chon tu choi");
	login.clickToDynamicButton(driver, "TỪ CHỐI");

    }

//    @Test
    public void TC_01_KiemTraMenuTrangThaiLenhChuyenTienvaManHinhTrangThaiLenhChuyenTien() {
	log.info("TC_01_STEP_0: chon chuyển tiền nhận bằng CMT");
	transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

	log.info("TC_01_STEP_1: kiem tra hien thị mac dinh");
	verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvNote")));

	log.info("TC_01_Step_2 : Click  nut Back");
	transferStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
    }

//    @Test
    public void TC_02_KiemTraHinhThucChuyen() {
	log.info("TC_01_STEP_0: chon chuyển tiền nhận bằng CMT");
	transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

	log.info("TC_01_STEP_1: kiem tra hien thị mac dinh");
	verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvNote")));

	log.info("TC_01_STEP_1: chon trang thai lênh chuyển tiền");
	transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

	log.info("TC_01_STEP_1: kiem tra hien thị mac dinh truong chon trang thai lenh chuyen tien");
	List<String> statusList = transferStatus.getListStatusTransfer(driver, "Hình thức chuyển");
	verifyEquals("abc", statusList);

//	log.info("TC_01_Step_2 : Click  nut Back");
//	transferStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
    }

//    @Test
    public void TC_03_KiemTraChonHinhThucChuyen() {
	log.info("TC_01_STEP_0: chon chuyển tiền nhận bằng CMT");
	transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

	log.info("TC_01_STEP_1: kiem tra hien thị mac dinh");
	verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvNote")));

	log.info("TC_01_STEP_1: chon trang thai lênh chuyển tiền");
	transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

	log.info("TC_01_STEP_1: chon chuyen tien ngay tuong lai");
	transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

	log.info("TC_01_STEP_1: kiem tra hien thi");
	verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSelectTransType")));

	log.info("TC_01_Step_2 : Click  nut Back");
	transferStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
    }

//    @Test
    public void TC_04_KiemTraLabelLuuY() {
	log.info("TC_01_STEP_0: chon chuyển tiền nhận bằng CMT");
	transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

	log.info("TC_01_STEP_1: kiem tra hien thị mac dinh");
	verifyEquals(StatusTransfer_Data.Message.HOME_MESSAGE, transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvNote"));

	log.info("TC_01_Step_2 : Click nut Back");
	transferStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
    }

    @Test
    public void TC_05_KiemTraChuyenTienKhongThanhCong() {
	log.info("TC_01_STEP_0: chon chuyển tiền nhận bằng CMT");
	transferStatus.clickToDynamicButtonLinkOrLinkText(driver, "Trạng thái lệnh chuyển tiền");

	log.info("TC_01_STEP_0: chon ngay bat dau");
	transferStatus.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvFromDate");

	log.info("TC_01_Step_2 : Click nut Back");
	transferStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
//	closeApp();
//	service.stop();
    }

}
