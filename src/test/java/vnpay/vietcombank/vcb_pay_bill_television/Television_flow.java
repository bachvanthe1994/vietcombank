package vnpay.vietcombank.vcb_pay_bill_television;

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
import pageObjects.PayBillTelevisionPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.PayBillTelevison_Data;

public class Television_flow extends Base {
    AppiumDriver<MobileElement> driver;
    private LogInPageObject login;
    private HomePageObject homePage;
    private PayBillTelevisionPageObject billTelevision;
    private TransactionReportPageObject transReport;
    private String transferTime;
    private String transactionNumber;
    private String payments;
    private String fee;
    private String account;
    private String service;
    private String supplier;
    private String userCode;
    private String dealCode;

    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt)
	    throws IOException, InterruptedException {
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
	billTelevision = PageFactoryManager.getPayBillTelevisionPageObject(driver);

    }

    @Test
    public void TC_01_PhuongThucThanhToanMK() {
	log.info("TC_01_STEP_0: chọn cước truyền hình cap");
	billTelevision.clickToDynamicButtonLinkOrLinkText(driver, "Cước truyền hình cáp");

	log.info("TC_01_STEP_1: kiem tra hien thị mac dinh");
	List<String> data = PayBillTelevison_Data.inputData.CODE_CUSTOMER;
	for (int i = 0; i < data.size(); i++) {
	    log.info("TC_01_STEP_2: điền mã khách hàng");
	    billTelevision.inputIntoEditTextByID(driver, data.get(i), "com.VCB:id/code");

	    log.info("TC_01_STEP_3: chọn tiếp tục");
	    billTelevision.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");
	    if (verifyTrue(billTelevision.isDynamicSuggestedMoneyDisplayed(driver, "com.VCB:id/tvTitleBar")) == true) {
		break;
	    } else {
		log.info("TC_01_STEP_3: chọn đóng");
		billTelevision.clickToDynamicButton(driver, "Đóng");
	    }

	}

	log.info("TC_01_STEP_4: chọn phương thức xác thực");
	billTelevision.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvptxt");

	log.info("TC_01_STEP_4: lấy ra số tiền thanh toán ");
	payments = billTelevision.getMoneyByAccount(driver, "Số tiền thanh toán");

	log.info("TC_01_STEP_5: lấy ra số số phí");
	fee = billTelevision.getMoneyByAccount(driver, "Số tiền phí");

	log.info("TC_01_STEP_6: lấy tài khoản nguồn");
	account = billTelevision.getMoneyByAccount(driver, "Tài khoản nguồn");

	log.info("TC_01_STEP_4: chọn tiếp tục");

    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
//	closeApp();
//	service.stop();
    }

}
