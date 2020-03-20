package vnpay.vietcombank.saving_target;

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
import pageObjects.SavingTargetPageObject;
import vietcombank_test_data.InterestRateCalculatePage_Data;
import vietcombank_test_data.SavingTarget_Data;

public class Saving_Target_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private SavingTargetPageObject savingTarget;

	private double interestMoney, totalMoney;
	private String rate;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone",
			"pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName, String phone, String pass, String opt)
			throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

	}

	@Test
	public void TC_01_MucTieuTietKiem_Flow() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Chon tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step_02: Mo sub-menu Ho tro");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hỗ trợ");
		home.scrollDownToText(driver, "Tra cứu");

		log.info("TC_01_Step_03: An vao phan Dat muc tieu tiet kiem");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đặt mục tiêu tiết kiệm");

		log.info("TC_01_Step_04: Hien thi man hinh Muc tieu tiet kiem");
		savingTarget = PageFactoryManager.getSavingTargetPageObject(driver);
		verifyEquals(savingTarget.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"),
				SavingTarget_Data.VALIDATE.SAVING_TARGET_TITLE);

		log.info("TC_01_Step_05: Chon muc tieu tiet kiem");
		savingTarget.clickToDynamicButtonLinkOrLinkText(driver, SavingTarget_Data.VALIDATE.SAVING_TARGET_DROPDOWN_TEXT);
		savingTarget.clickToDynamicButtonLinkOrLinkText(driver, SavingTarget_Data.DATA.SAVING_TARGET_LEARNING);

		log.info("TC_01_Step_06: Nhap so tien can tich luy");
		savingTarget.inputToDynamicInputBox(driver, SavingTarget_Data.DATA.SAVING_MONEY_VND,
				SavingTarget_Data.VALIDATE.SAVING_MONEY_TEXT);

		log.info("TC_01_Step_07: Chon Ky han / Lai Suat");
		savingTarget.clickToDynamicButtonLinkOrLinkText(driver, SavingTarget_Data.VALIDATE.TERM_RATE_DROPDOWN_TEXT);
		rate = savingTarget.getFirstOptionInDynamicListElements(driver, "com.VCB:id/tvContent");
		savingTarget.clickToDynamicButtonLinkOrLinkText(driver, rate);

		log.info("TC_01_Step_08: Chon thoi gian can tich luy");
		savingTarget.clickToDynamicButtonLinkOrLinkText(driver,
				SavingTarget_Data.VALIDATE.ACCUMULATION_TIME_DRODDOWN_TEXT);
		savingTarget.clickToDynamicButtonLinkOrLinkText(driver, SavingTarget_Data.DATA.ACCUMULATION_TIME_12_MONTHS);

		log.info("TC_01_Step_09: An button Ket qua");
		verifyEquals(savingTarget.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Kết quả");
		savingTarget.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_10: Xac nhan hien thi man hinh Ket Qua");
		verifyEquals(savingTarget.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"),
				SavingTarget_Data.VALIDATE.SAVING_TARGET_RESULT_TITLE);

		log.info("TC_01_Step_11: Xac nhan hien thi label Luu y");
		verifyTrue(savingTarget.isDynamicMessageAndLabelTextDisplayed(driver,
				SavingTarget_Data.VALIDATE.SAVING_TARGET_RESULT_TITLE_HEAD));

		log.info("TC_01_Step_12: Xac nhan hien thi dung so tien gui dinh ky");
		verifyEquals(savingTarget.getDynamicTextByLabel(driver, "Số tiền gửi định kỳ"),
				addCommasToLong(SavingTarget_Data.DATA.SAVING_MONEY_VND) + " VND");

		log.info("TC_01_Step_13: Xac nhan hien thi dung so tien gop hang thang");
		verifyEquals(savingTarget.getDynamicTextByLabel(driver, "Số tiền góp hàng tháng"),
				addCommasToDouble(
						convertAvailableBalanceCurrentcyToDouble(SavingTarget_Data.DATA.SAVING_MONEY_VND) / 12 + "")
						+ " VND");

		log.info("TC_01_Step_14: Xac nhan hien thi dung  tien lai");
		double a = convertMoneyToDouble(InterestRateCalculatePage_Data.DATA.VND_MONEY, "VND");
		double b = convertMoneyToDouble(getSplitStringIndex(rate, "/", 1), "VND") / 100;
		interestMoney = a * b;
		verifyEquals(savingTarget.getDynamicTextByLabel(driver, "Tổng số tiền lãi"),
				addCommasToDouble(interestMoney + "").replace(".00", "") + " VND");

		log.info("TC_01_Step_15: Xac nhan hien thi dung tong tien");
		totalMoney = convertAvailableBalanceCurrentcyOrFeeToLong(InterestRateCalculatePage_Data.DATA.VND_MONEY)
				+ interestMoney;
		verifyEquals(savingTarget.getDynamicTextByLabel(driver, "Tổng số tiền nhận được"),
				addCommasToDouble(totalMoney + "").replace(".00", "") + " VND");

		log.info("TC_01_Step_16: An button Dat muc tieu moi");
		verifyEquals(savingTarget.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Đặt mục tiêu mới");
		savingTarget.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_17: Xac nhan hien thi man hinh Muc tieu tiet kiem");
		verifyEquals(savingTarget.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"),
				SavingTarget_Data.VALIDATE.SAVING_TARGET_TITLE);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
