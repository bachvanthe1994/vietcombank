package commons;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.LuckyGiftPageObject;
import pageObjects.RegisterPageObject;
import pageObjects.SetupContactPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferIdentiryPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.TransferMoneyObject;
import pageObjects.TransferMoneyOutSideVCBPageObject;
import pageObjects.TransferMoneyStatusPageObject;
import vehicalPageObject.VehicalPageObject;
import pageObjects.sdk.airTicketBooking.DynamicAirTicketBookingObjects;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import pageObjects.sdk.trainTicket.TrainTicketPageObject;

public class PageFactoryManager {

    public static LogInPageObject getLoginPageObject(AndroidDriver<AndroidElement> driver) {
	return new LogInPageObject(driver);
    }

    public static HomePageObject getHomePageObject(AndroidDriver<AndroidElement> driver) {
	return new HomePageObject(driver);
    }

    public static RegisterPageObject getRegisterPageObject(AndroidDriver<AndroidElement> driver) {
	return new RegisterPageObject(driver);
    }

    public static TransferMoneyInVcbPageObject getTransferMoneyInVcbPageObject(AndroidDriver<AndroidElement> driver) {
	return new TransferMoneyInVcbPageObject(driver);
    }

    public static TransferMoneyObject getTransferMoneyObject(AndroidDriver<AndroidElement> driver) {
	return new TransferMoneyObject(driver);
    }

    public static TransferIdentiryPageObject getTransferIdentiryPageObject(AndroidDriver<AndroidElement> driver) {
	return new TransferIdentiryPageObject(driver);
    }

    public static TransferMoneyOutSideVCBPageObject getTransferMoneyOutSideVCBPageObject(AndroidDriver<AndroidElement> driver) {
	return new TransferMoneyOutSideVCBPageObject(driver);
    }

    public static TransferMoneyCharityPageObject getTransferMoneyCharityPageObject(AndroidDriver<AndroidElement> driver) {
	return new TransferMoneyCharityPageObject(driver);
    }

    public static TransactionReportPageObject getTransactionReportPageObject(AndroidDriver<AndroidElement> driver) {
	return new TransactionReportPageObject(driver);
    }

    public static TransferMoneyStatusPageObject getTransferMoneyStatusPageObject(AndroidDriver<AndroidElement> driver) {
	return new TransferMoneyStatusPageObject(driver);
    }

    public static LuckyGiftPageObject getLuckyGiftPageObject(AndroidDriver<AndroidElement> driver) {
	return new LuckyGiftPageObject(driver);
    }

    public static SetupContactPageObject getSetupContactPageObject(AndroidDriver<AndroidElement> driver) {
	return new SetupContactPageObject(driver);
    }

    public static VehicalPageObject getVehicalPageObject(AndroidDriver<AndroidElement> driver) {
	return new VehicalPageObject(driver);
    }

    public static TrainTicketPageObject getTrainTicketPageObject(AndroidDriver<AndroidElement> driver) {
	return new TrainTicketPageObject(driver);
    }

    public static HotelBookingPageObject getHotelBookingPageObject(AndroidDriver<AndroidElement> driver) {
	return new HotelBookingPageObject(driver);
    }

	public static DynamicAirTicketBookingObjects getDynamicAirTicketBooking(AndroidDriver<AndroidElement> driver) {
		return new DynamicAirTicketBookingObjects(driver);

	}
	
	public static FilmTicketBookingPageObject getFilmTicketBookingPageObject(AndroidDriver<AndroidElement> driver) {
		return new FilmTicketBookingPageObject(driver);
	}
	
}