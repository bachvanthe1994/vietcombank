package commons;

import org.testng.annotations.Parameters;

public class Constants {
	public static final Long LONG_TIME = (long) 45;
	public static final Long SHORT_TIME = (long) 5;
	public static final String VND_CURRENCY = "VND";
	public static final String USD_CURRENCY = "USD";
	public static final String EUR_CURRENCY = "EUR";
	public static final double AMOUNT_VND = 100000000;

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
