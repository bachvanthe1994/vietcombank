package commons;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.AutoSavingPageObject;
import pageObjects.ChangePasswordPageObject;
import pageObjects.ElectricBillPageObject;
import pageObjects.HomePageObject;
import pageObjects.InboxPageObject;
import pageObjects.InterestRateCalculatePageObject;
import pageObjects.InternetADSLPageObject;
import pageObjects.LandLinePhoneChargePageObject;
import pageObjects.LocationQRCodePageObject;
import pageObjects.LockCardPageObject;
import pageObjects.LogInPageObject;
import pageObjects.LuckyGiftPageObject;
import pageObjects.MobileTopupPageObject;
import pageObjects.NotifyManagementPageObject;
import pageObjects.OnlineTopupPageObject;
import pageObjects.PayBillTelevisionPageObject;
import pageObjects.PostpaidMobileBillPageObject;
import pageObjects.RegisterOnlinePageObject;
import pageObjects.RegisterPageObject;
import pageObjects.SavingTargetPageObject;
import pageObjects.SearchPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.SetupContactPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferIdentiryPageObject;
import pageObjects.TransferLimitPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.TransferMoneyObject;
import pageObjects.TransferMoneyOutSideVCBPageObject;
import pageObjects.TransferMoneyStatusPageObject;
import pageObjects.VCBAutoDebitPageObject;
import pageObjects.VCBCreditCardPaymentObject;
import pageObjects.saving_online.SavingOnlinePageObject;
import pageObjects.sdk.airTicketBooking.DynamicAirTicketBookingObjects;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import pageObjects.sdk.trainTicket.TrainTicketPageObject;
import pageObjects.shopping_online.ShoppingOnlinePageObject;
import vehicalPageObject.VehicalPageObject;

public class PageFactoryManager {

	public static LogInPageObject getLoginPageObject(AppiumDriver<MobileElement> driver) {
		return new LogInPageObject(driver);
	}

	public static HomePageObject getHomePageObject(AppiumDriver<MobileElement> driver) {
		return new HomePageObject(driver);
	}

	public static RegisterPageObject getRegisterPageObject(AppiumDriver<MobileElement> driver) {
		return new RegisterPageObject(driver);
	}

	public static TransferMoneyInVcbPageObject getTransferMoneyInVcbPageObject(AppiumDriver<MobileElement> driver) {
		return new TransferMoneyInVcbPageObject(driver);
	}

	public static TransferMoneyObject getTransferMoneyObject(AppiumDriver<MobileElement> driver) {
		return new TransferMoneyObject(driver);
	}

	public static TransferIdentiryPageObject getTransferIdentiryPageObject(AppiumDriver<MobileElement> driver) {
		return new TransferIdentiryPageObject(driver);
	}

	public static TransferMoneyOutSideVCBPageObject getTransferMoneyOutSideVCBPageObject(
			AppiumDriver<MobileElement> driver) {
		return new TransferMoneyOutSideVCBPageObject(driver);
	}

	public static TransferMoneyCharityPageObject getTransferMoneyCharityPageObject(AppiumDriver<MobileElement> driver) {
		return new TransferMoneyCharityPageObject(driver);
	}

	public static TransactionReportPageObject getTransactionReportPageObject(AppiumDriver<MobileElement> driver) {
		return new TransactionReportPageObject(driver);
	}

	public static TransferMoneyStatusPageObject getTransferMoneyStatusPageObject(AppiumDriver<MobileElement> driver) {
		return new TransferMoneyStatusPageObject(driver);
	}

	public static LuckyGiftPageObject getLuckyGiftPageObject(AppiumDriver<MobileElement> driver) {
		return new LuckyGiftPageObject(driver);
	}

	public static SetupContactPageObject getSetupContactPageObject(AppiumDriver<MobileElement> driver) {
		return new SetupContactPageObject(driver);
	}

	public static VehicalPageObject getVehicalPageObject(AppiumDriver<MobileElement> driver) {
		return new VehicalPageObject(driver);
	}

	public static TrainTicketPageObject getTrainTicketPageObject(AppiumDriver<MobileElement> driver) {
		return new TrainTicketPageObject(driver);
	}

	public static HotelBookingPageObject getHotelBookingPageObject(AppiumDriver<MobileElement> driver) {
		return new HotelBookingPageObject(driver);
	}

	public static DynamicAirTicketBookingObjects getDynamicAirTicketBooking(AppiumDriver<MobileElement> driver) {
		return new DynamicAirTicketBookingObjects(driver);

	}

	public static FilmTicketBookingPageObject getFilmTicketBookingPageObject(AppiumDriver<MobileElement> driver) {
		return new FilmTicketBookingPageObject(driver);
	}

	public static LockCardPageObject LockCardPageObject(AppiumDriver<MobileElement> driver) {
		return new LockCardPageObject(driver);
	}

	public static RegisterOnlinePageObject getRegisterOnlinePageObject(AppiumDriver<MobileElement> driver) {
		return new RegisterOnlinePageObject(driver);
	}

	public static SavingOnlinePageObject getSavingOnlinePageObject(AppiumDriver<MobileElement> driver) {
		return new SavingOnlinePageObject(driver);
	}

	public static MobileTopupPageObject getMobileTopupPageObject(AppiumDriver<MobileElement> driver) {
		return new MobileTopupPageObject(driver);
	}

	public static OnlineTopupPageObject getOnlineTopupPageObject(AppiumDriver<MobileElement> driver) {
		return new OnlineTopupPageObject(driver);
	}

	public static AutoSavingPageObject getAutoSavingPageObject(AppiumDriver<MobileElement> driver) {
		return new AutoSavingPageObject(driver);
	}

	public static VCBAutoDebitPageObject getVCBAutoDebitPageObject(AppiumDriver<MobileElement> driver) {
		return new VCBAutoDebitPageObject(driver);
	}

	public static InterestRateCalculatePageObject getInterestRateCalculatePageObject(
			AppiumDriver<MobileElement> driver) {
		return new InterestRateCalculatePageObject(driver);
	}

	public static SavingTargetPageObject getSavingTargetPageObject(AppiumDriver<MobileElement> driver) {
		return new SavingTargetPageObject(driver);

	}

	public static ChangePasswordPageObject getChangePasswordPageObject(AppiumDriver<MobileElement> driver) {
		return new ChangePasswordPageObject(driver);
	}

	public static PostpaidMobileBillPageObject getPostpaidMobileBillPageObject(AppiumDriver<MobileElement> driver) {
		return new PostpaidMobileBillPageObject(driver);
	}

	public static TransferLimitPageObject getTransferLimitPageObject(AppiumDriver<MobileElement> driver) {
		return new TransferLimitPageObject(driver);
	}

	public static InternetADSLPageObject getInternetADSLPageObject(AppiumDriver<MobileElement> driver) {
		return new InternetADSLPageObject(driver);
	}

	public static LandLinePhoneChargePageObject getLandLinePhoneChargePageObject(AppiumDriver<MobileElement> driver) {
		return new LandLinePhoneChargePageObject(driver);
	}

	public static SearchPageObject getSearchPageObject(AppiumDriver<MobileElement> driver) {
		return new SearchPageObject(driver);
	}

	public static ElectricBillPageObject getElectricBillPageObject(AppiumDriver<MobileElement> driver) {
		return new ElectricBillPageObject(driver);
	}

	public static NotifyManagementPageObject getNotifyManagementPageObject(AppiumDriver<MobileElement> driver) {
		return new NotifyManagementPageObject(driver);
	}

	public static InboxPageObject getInboxPageObject(AppiumDriver<MobileElement> driver) {
		return new InboxPageObject(driver);
	}

	public static PayBillTelevisionPageObject getPayBillTelevisionPageObject(AppiumDriver<MobileElement> driver) {
		return new PayBillTelevisionPageObject(driver);
	}
	
	public static ShoppingOnlinePageObject getShoppingOnlinePageObject(AppiumDriver<MobileElement> driver) {
		return new ShoppingOnlinePageObject(driver);
	}
	
	public static LocationQRCodePageObject getLocationQRCodePageObject(AppiumDriver<MobileElement> driver) {
		return new LocationQRCodePageObject(driver);
	}
	
	public static VCBCreditCardPaymentObject getVCBCreditCardPaymentPageObject(AppiumDriver<MobileElement> driver) {
		return new VCBCreditCardPaymentObject(driver);
	}
	
	public static SettingVCBSmartOTPPageObject getLocationSettingVCBSmartOTPPageObject(AppiumDriver<MobileElement> driver) {
		return new SettingVCBSmartOTPPageObject(driver);
	}
	
	
	
}