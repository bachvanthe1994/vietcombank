
package vnpay.vietcombank.creditCardPayment;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import creaditCardPaymentUI.creaditCardPaymentUI;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import pageObjects.VCBCreditCardPaymentObject;
import vietcombank_test_data.Creadit_Card_Payment_Data;
import vietcombank_test_data.HomePage_Data;

public class Vcb_Flow_Credit_Card_Payment extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private VCBCreditCardPaymentObject vcbACreditCardPayment;
	public String soTaiKhoan = "";
	public String soDuKhaDung = "";
	public String soThe = "";
	public String soTaiKhoanThe = "";
	public String tinhTrangThe = "";
	public String tongSoDaTTTrongKySaoKe = "";
	public String soTienToiThieuPhaiTT = "";
	public String soTienSaoKeConTT = "";
	public String soTienDuNoConPhaiTT = "";
	public String soTienThanhToan = "";
	public String maGiaoDich = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		login.scrollDownToText(driver, HomePage_Data.Home_Text_Elements.CREDIT_CARD_PAYMENT);
		login.clickToDynamicButtonLinkOrLinkText(driver, HomePage_Data.Home_Text_Elements.CREDIT_CARD_PAYMENT);
		login.clickToDynamicAcceptButton(driver, "com.VCB:id/btCancel");
		vcbACreditCardPayment = PageFactoryManager.getVCBCreditCardPaymentPageObject(driver);

	}

	@Test
	public void TC_01_ThanhToanTheTinDung() {

		log.info("TC_01_Step_01: Lay thong tin tai so tai khoan");
		soTaiKhoan = vcbACreditCardPayment.getDynamicTextInDropDownByLable(driver, Creadit_Card_Payment_Data.Tittle.TEXT_STK);

		log.info("TC_01_Step_02: Lay thong tin so du tai khoan");
		soDuKhaDung = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TEXT_SDKD);

		log.info("TC_01_Step_03: Chon so the");
		vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");

		log.info("TC_01_Step_04: verify hien thi man hinh chon so the");
		vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.SELECT_CARD);

		log.info("TC_01_Step_05: get list tai khoan tin dung");
		List<String> listAccount = vcbACreditCardPayment.getListAccount();

		vcbACreditCardPayment.scrollUpToText(driver, listAccount.get(0));

		for (String account : listAccount) {
			vcbACreditCardPayment.scrollDownToText(driver, account);
			log.info("TC_01_Step_06: Chon tai khoan the tin dung:");
			vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, account);
			if (vcbACreditCardPayment.isTextDisplayedInPageSource("Quý khách không còn dư nợ")) {
				log.info("TC_01_Step_07: Click btn Dong y:");
				vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
				log.info("TC_01_Step_08: Click chon so tai khoan the :");
				vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
				continue;
			} else {
				vcbACreditCardPayment.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.TEXT_STK);
				log.info("TC_01_Step_08: Lay thong tin so The");
				soThe = vcbACreditCardPayment.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent1");

				log.info("TC_01_Step_09: Lay thong tin so tai khoan the");
				soTaiKhoanThe = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.SO_TK_THE);

				log.info("TC_01_Step_10: Lay thong tin tinh trang the");
				tinhTrangThe = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.TINH_TRANG_THE);

				log.info("TC_01_Step_11: Lay thong tin so tien thanh toan trong ky sao ke: ");
				tongSoDaTTTrongKySaoKe = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.TONG_SO_TT_SAO_KE);

				log.info("TC_01_Step_12: So tien toi thieu phai thanh toan ");
				soTienToiThieuPhaiTT = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.SO_TIEN_TOI_THIEU_TT);

				log.info("TC_01_Step_13: So tien thanh toan ");
				vcbACreditCardPayment.scrollDownToText(driver, "Số tiền thanh toán");
				soTienSaoKeConTT = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.SO_TIEN_SK_TT);

				log.info("TC_01_Step_14: So tien du no phai thanh toan ");
				soTienDuNoConPhaiTT = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.SO_TIEN_DU_NO_TT);

				log.info("TC_01_Step_15: So tien thanh toan ");
				soTienThanhToan = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.SO_TIEN_TT);

				if (soTienThanhToan.equals("0 VND")) {
					vcbACreditCardPayment.scrollUpToText(driver, creaditCardPaymentUI.TT_GIAO_DICH);

					log.info("TC_01_Step_16: Chon so the ");
					vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
					continue;
				} else {
					break;
				}
			}

		}

		log.info("TC_01_Step_17: Click btn tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_18: Verify hien thi man hinh Xac nhan thong tin ");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, creaditCardPaymentUI.XAC_NHAN_TT));

		log.info("TC_01_Step_19: Xac minh tai khoan nguon: ");
		verifyEquals(soTaiKhoan, vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.TK_NGUON));

		log.info("TC_01_Step_20: Verify so the ");
		verifyEquals(soThe, vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.SO_THE));
		log.info("TC_01_Step_21: Verify so tien thanh toan ");

		verifyEquals(soTienDuNoConPhaiTT, vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.SO_TIEN_TT));

		log.info("TC_01_Step_22: Click btn Tiep tuc ");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_23: Xac minh man hinh  Xac thuc giao dich ");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, creaditCardPaymentUI.XAC_THUC_GD));

		log.info("TC_01_Step_24: Nhap OTP ");
		vcbACreditCardPayment.inputToDynamicOtp(driver, Creadit_Card_Payment_Data.Tittle.OTP_NUMBER, "Tiếp tục");

		log.info("----------TC_01_Step_25 Click tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_26: Xac minh man hinh thong bao giao dich thanh cong");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, creaditCardPaymentUI.GD_THANH_CONG));

		log.info("TC_01_Step_27: Xac minh thong tin so the ");
		verifyEquals(soThe, vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.SO_THE));

		log.info("TC_01_Step_05: Lay thong tin ma giao dich ");
		maGiaoDich = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.MA_GIAO_DICH);
	}

	@Test
	public void TC_02_BaoCaoGiaoDich() {

		log.info("TC_02_Step_01: Click icon home ");
		vcbACreditCardPayment.clickToDynamicImageViewByID(driver, "com.VCB:id/ivHome");

		log.info("------------TC_02_Step_02_Click btn menu");
		vcbACreditCardPayment.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("---------TC_02_Step_03_Click Chon muc bao cao giao dich");
		vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, creaditCardPaymentUI.BAO_CAO_GD);

		log.info("---------TC_02_Step_04_Click Chon cac loai giao dich");
		vcbACreditCardPayment.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llSelectTransType");

		log.info("---------TC_02_Step_05_Scroll to thanh toan the tin dung");
		vcbACreditCardPayment.scrollDownToText(driver, creaditCardPaymentUI.TT_THE_TD);

		log.info("---------TC_02_Step_06_Scroll to thanh toan the tin dung");
		vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, creaditCardPaymentUI.TT_THE_TD);

		log.info("TC_02_Step_07: Verrify tai khoan nguon ");
		verifyEquals(soTaiKhoan, vcbACreditCardPayment.getDynamicTextView("com.VCB:id/tvSelectAcc"));

		log.info("TC_02_Step_08: Click tim kiem ");
		vcbACreditCardPayment.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02_Step_09: Xac minh so tin thanh toan ");
		verifyEquals(soTienThanhToan, vcbACreditCardPayment.getDynamicTextView("com.VCB:id/tvMoney").replace("- ", ""));

		log.info("----------TC_02_Step_10: Verify ban ghi Thanh toan ");
		vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llRoot");

		log.info("-----------TC_02_Step_11: Verofy hien thi man hinh chi tiet bao cao giao dich ");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, creaditCardPaymentUI.CHI_TIET_GD));

		log.info("---------TC_02_Step_12: Verify ma thanh toan ");
		verifyEquals(maGiaoDich, vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.SO_LENH_GD));

		log.info("---------TC_02_Step_13: Verify tai khoan trich no");
		verifyEquals(soTaiKhoan, vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.TK_THE_TRICH_NO));

		log.info("---------TC_02_Step_14: Verify ma thanh toan ");
		verifyEquals(soThe, vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.SO_THE));

		log.info("---------TC_02_Step_15: Verify ma thanh toan ");
		verifyEquals(soTaiKhoanThe, vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.SO_TK_THE));

		log.info("TC_02_Step_16: Verify so tien giao dich");
		String tienSo = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.SO_TIEN_GD);
		int indexTien = tienSo.indexOf("(");
		String subTienSo = tienSo.substring(0, indexTien - 1);
		verifyEquals(soTienThanhToan, subTienSo);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
