package commons;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Base {
	protected final Logger log;
	private AndroidDriver<AndroidElement> driver;
	private AndroidDriver<AndroidElement> driver2;

	private String workingDir = System.getProperty("user.dir");
	public AppiumDriverLocalService service;

	protected Base() {
		log = Logger.getLogger(getClass());

	}

	public AndroidDriver<AndroidElement> getDriver() {
		return driver;

	}

	@BeforeSuite
	public void deleteAllFile() {
		System.out.println("-----------START DELETE ALL FILE -------");
		deleteAllFileInFolder();
		System.out.println("-----------END DELETE ALL FILE -------");
	}

	public void deleteAllFileInFolder() {
		try {
			String pathFolderDownload = workingDir + "//ReportNGScreenShots";
			File file = new File(pathFolderDownload);
			File[] listOfFiles = file.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					new File(listOfFiles[i].toString()).delete();
				}
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	public void closeSMSApp() {
		driver2.quit();
	}

	public AndroidDriver<AndroidElement> openAndroidApp(String deviceType, String deviceName, String udid, String url,
			String appActivities, String appPackage, String appName) throws MalformedURLException {
		File file = new File("src/test/resources");
		File appFile = new File(file, appName);
		DesiredCapabilities cap = new DesiredCapabilities();

		if (deviceType.equalsIgnoreCase("virtual")) {
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			cap.setCapability("appPackage", appPackage);
			cap.setCapability("appActivity", appActivities);
		} else if (deviceType.equalsIgnoreCase("real")) {
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			cap.setCapability("uid", udid);
			cap.setCapability("appPackage", appPackage);
			cap.setCapability("appActivity", appActivities);
			cap.setCapability("appWaitPackage", "com.google.android.packageinstaller");
			cap.setCapability("appWaitActivity", "com.android.packageinstaller.permission.ui.GrantPermissionsActivity");

		} else if (deviceType.equalsIgnoreCase("browserStack")) {
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			cap.setCapability("uid", udid);
			cap.setCapability("appPackage", appPackage);
			cap.setCapability("appActivity", appActivities);

		}

		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		cap.setCapability(MobileCapabilityType.APP, appFile.getAbsolutePath());

		driver = new AndroidDriver<>(new URL(url), cap);

		driver.manage().timeouts().implicitlyWait(Constants.LONG_TIME, TimeUnit.SECONDS);

		return driver;

	}

	public void closeApp() {
		driver.quit();
	}

	public AndroidDriver<AndroidElement> openAndroidBrowser(String device, String browser)
			throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
		if (device.equalsIgnoreCase("virtual ")) {
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, "AndroidPixel2");
		} else if (device.equalsIgnoreCase("real")) {
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
		}
		cap.setCapability(MobileCapabilityType.BROWSER_NAME, browser);
		System.setProperty("webdriver.chrome.driver",
				"E:\\Software\\Copy of eclipse-java-photon-R-win32-x86_64\\Workspace\\APPIUM_DEMO\\lib\\chromedriver.exe");
		AndroidDriver<AndroidElement> driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return driver;

	}

	public AppiumDriverLocalService startServer() throws IOException, InterruptedException {
		boolean trueStatus = checkIfServerIsRunnning(4723);
		if (!trueStatus) {
			service = AppiumDriverLocalService.buildDefaultService();
			service.start();
		} else {
			Runtime.getRuntime().exec("taskkill /F /IM node.exe");
			Thread.sleep(2000);
			service = AppiumDriverLocalService.buildDefaultService();
			service.start();
		}
		return service;
	}

	public static boolean checkIfServerIsRunnning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);

			serverSocket.close();
		} catch (IOException e) {
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

	public static int randomNumber() {

		Random random = new Random();
		int randomNumber = random.nextInt(999999);
		return randomNumber;

	}

	protected boolean checkPassed(boolean condition) {
		boolean pass = true;
		try {
			if (condition == true)
				log.info("===PASSED===");
			else
				log.info("===FAILED===");
			Assert.assertTrue(condition);
		} catch (Throwable e) {
			;
			pass = false;
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyTrue(boolean condition) {
		return checkPassed(condition);
	}

	protected boolean checkFailed(boolean condition) {
		boolean pass = true;
		try {
			if (condition == false)
				log.info("===PASSED===");
			else
				log.info("===FAILED===");
			Assert.assertFalse(condition);
		} catch (Throwable e) {
			;
			pass = false;
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyFailure(boolean condition) {
		return checkFailed(condition);
	}

	protected boolean checkEquals(Object actual, Object expected) {
		boolean pass = true;
		boolean status;
		try {
			if (actual instanceof String && expected instanceof String) {
				actual = actual.toString().trim();
				log.info("Actual = " + actual);
				expected = expected.toString().trim();
				log.info("Expected = " + expected);
				status = (actual.equals(expected));
			} else {
				status = (actual == expected);
			}

			log.info("Compare value = " + status);
			if (status) {
				log.info("===PASSED===");
			} else {
				log.info("===FAILED===");
			}
			Assert.assertEquals(actual, expected, "Value is not matching!");
		} catch (Throwable e) {
			;
			pass = false;
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyEquals(Object actual, Object expected) {
		return checkEquals(actual, expected);
	}

	public String getCurrentDay() {
		DateTime nowUTC = new DateTime(DateTimeZone.UTC);
		int day = nowUTC.getDayOfMonth();
		if (day < 10) {
			String day1 = "0" + day;
			return day1;
		} else {
			return day + "";
		}

	}

	public String getCurrenMonth() {
		DateTime nowUTC = new DateTime(DateTimeZone.UTC);
		int month = nowUTC.getMonthOfYear();
		if (month < 10) {
			String month1 = "0" + month;
			return month1;
		} else {
			return month + "";
		}
	}

	public String getCurrenMonthWithout0() {
		DateTime nowUTC = new DateTime(DateTimeZone.UTC);
		int month = nowUTC.getMonthOfYear();

		return month + "";
	}

	public String getCurrentYear() {
		DateTime nowUTC = new DateTime(DateTimeZone.UTC);
		return nowUTC.getYear() + "";

	}

	public String getTheLastDayOfCurrentMonth() {
		String[] partsOfToday = getTheLastDateOfMonth().toString().split("-");
		String lastDayOfThisMonth = Integer.parseInt(partsOfToday[2]) + "";
		return lastDayOfThisMonth;
	}

	public LocalDate getTheLastDateOfMonth() {
		String date = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
		LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/M/yyyy"));
		return convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));

	}

	public String getArriveMonth() {
		String currentDay = getCurrentDay();
		String arriveMonth = getCurrenMonthWithout0();
		if (currentDay == getTheLastDayOfCurrentMonth() & getCurrenMonth() != "12") {
			arriveMonth = Integer.parseInt(getCurrenMonth()) + 1 + "";
		} else if (currentDay == getTheLastDayOfCurrentMonth() & getCurrenMonth() == "12") {
			arriveMonth = "1";
		}

		return arriveMonth;

	}

	public String getArriveYear() {
		String currentDay = getCurrentDay();
		String arriveYear = getCurrentYear();
		if (currentDay == getTheLastDayOfCurrentMonth() & getCurrenMonth() == "12") {
			arriveYear = Integer.parseInt(getCurrentYear()) + 1 + "";
		}
		return arriveYear;
	}

	public String getNextDay() {
		String nextDay = Integer.parseInt(getCurrentDay()) + 1 + "";
		String currentDay = getCurrentDay();
		if (currentDay == getTheLastDayOfCurrentMonth() & getCurrenMonth() != "12") {
			nextDay = "1";
		}
		return nextDay;
	}

	public long convertMoneyToLong(String money, String currency) {
		money = money.replaceAll(" " + currency, "");
		money = money.replaceAll(",", "");
		long m = Long.parseLong(money);
		return m;
	}

	public double convertMoneyToDouble(String money, String currency) {
		money = money.replaceAll(" " + currency, "");
		money = money.replaceAll(",", "");
		double amount = Double.parseDouble(money);
		return amount;
	}

	public String addCommasToDouble(String number) {
		double amount = Double.parseDouble(number);
		String m = String.format("%,.2f", amount);
		return m;
	}

	public static String addCommasToLong(String number) {
		long amount = Long.parseLong(number);
		String m = String.format("%,d", amount);
		return m;
	}

}
