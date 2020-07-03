package commons;

import org.testng.annotations.Parameters;

public class Constants {
	public static final Long LONG_TIME = (long) 45;
	public static final Long SHORT_TIME = (long) 5;
	
	public static final int MONEY_CHECK_VND = 1000000;
	public static final int MONEY_CHECK_USD = 10;
	public static final int MONEY_CHECK_EUR = 10;

	public static final String VND_CURRENCY = "VND";
	public static final String USD_CURRENCY = "USD";
	public static final String EUR_CURRENCY = "EUR";
	public static final double AMOUNT_VND = 100000000;
	public static final String THREE_BILLION_VND = "3000000000";
	
	public static final String LOWER_MIN_TRANSFER = "9999";
	public static final String HIGHER_MAX_TRANSFER = "100000001";
	public static final String MIN_TRANSFER = "10000";
	public static final String MAX_TRANSFER = "100000000";
	public static final String MAX_TRANSFER_OF_DAY = "100000001";
	
	public static final String BE_BROWSER_CHROME = "chrome";
	public static final String BE_BROWSER_CHROME_HEADELESS = "chromeheadless";
	public static final String BE_BROWSER_VERSION = "83.0.4103.14";
	public static final String BE_URL = "http://10.22.7.91:2021/Package/Index?f=2&c=191";
	public static final String AMOUNT_DEFAULT_MIN_PACKAGE = "100000";
	public static final String AMOUNT_DEFAULT_MAX_PACKAGE = "1000000000000";
	public static final String BE_CODE_PACKAGE = "TESTBUG";
	
	public static final String POSTPAID_MOBILE = "Thanh toán hóa đơn trả sau";
	public static final String PAY_BILL = "Thanh toán hóa đơn";
	public static final String METHOD_OTP = "Method Otp";


	// RUN_CONTINUE_AFTER_STEP_FAIL = true --> testcase still runs next steps after
	// current step is failed
	// RUN_CONTINUE_AFTER_STEP_FAIL = false --> testcase will stop at current step
	// and continue next testcase.
	public static final boolean RUN_CONTINUE_AFTER_STEP_FAIL = false;

	public static String runAfterfail;

	@Parameters({ "runAfterFail" })
	public Constants(String runAfterFailTest) {
		Constants.runAfterfail = runAfterFailTest;

	}

}
