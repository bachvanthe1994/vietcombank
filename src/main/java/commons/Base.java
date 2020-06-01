package commons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.HasSettings;
import io.appium.java_client.MobileElement;
import io.appium.java_client.Setting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Base {
	SoftAssert softAssertion;
	protected final Logger log;
	protected AppiumDriver<MobileElement> driver;

	private String workingDir = System.getProperty("user.dir");
	public AppiumDriverLocalService service;
	private String arriveDay;
	private URL serviceURl;
	
	private static Sheets sheetService;
	private static String APPICATION_LINK = "Google sheet";
	private static String SPREADSHEET_ID = "KHjEPUiG5zQtwr_VXrsbI7ezNztcqYaHXx7lLgeI";

	protected Base() {
		log = Logger.getLogger(getClass());

	}

	public AppiumDriver<MobileElement> getDriver() {
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

//	@AfterSuite
	public void sendEmail() throws IOException {
		String SMTP_SERVER = "smtp.gmail.com";
		String PASSWORD = "Abc12345@";
		String EMAIL_FROM = "vnpay.automation.team@gmail.com";
		String EMAIL_TO = "vnpay.automation.team@gmail.com";

		String EMAIL_SUBJECT = "TestNG Report";

		System.out.println("Preparing to send email");
		Properties prop = new Properties();
		prop.put("mail.smtp.host", SMTP_SERVER);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.port", "587");
		String workingDir = System.getProperty("user.dir");

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAIL_FROM, PASSWORD);
			}
		});
		Message msg = new MimeMessage(session);

		try {

			msg.setFrom(new InternetAddress(EMAIL_FROM));

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO, false));

			msg.setSubject(EMAIL_SUBJECT);

			Multipart emailContent = new MimeMultipart();
			MimeBodyPart textBoyPart = new MimeBodyPart();
			textBoyPart.setText("Download all files to see the report");

			MimeBodyPart attachfile = new MimeBodyPart();
			attachfile.attachFile(workingDir + "\\test-output\\Vietcombank\\Run test case on Android 9.html");
			MimeBodyPart xmlFile1 = new MimeBodyPart();
			xmlFile1.attachFile(workingDir + "\\test-output\\Vietcombank\\Run test case on Android 9.xml");
			MimeBodyPart xmlFile2 = new MimeBodyPart();
			xmlFile2.attachFile(workingDir + "\\test-output\\Vietcombank\\testng-failed.xml");
			emailContent.addBodyPart(textBoyPart);
			emailContent.addBodyPart(attachfile);
			emailContent.addBodyPart(xmlFile1);
			emailContent.addBodyPart(xmlFile2);
			msg.setContent(emailContent);
			msg.setSentDate(new Date());

			Transport.send(msg);
			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public AppiumDriver<MobileElement> openAndroidApp(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();

		if (deviceType.equalsIgnoreCase("androidVirtual")) {
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			cap.setCapability("appPackage", appPackage);
			cap.setCapability("appActivity", appActivities);
		} else if (deviceType.equalsIgnoreCase("androidReal")) {
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			cap.setCapability("udid", udid);
			cap.setCapability("appPackage", appPackage);
			cap.setCapability("appActivity", appActivities);
//			cap.setCapability("appWaitPackage", "com.google.android.packageinstaller");
//			cap.setCapability("appWaitActivity", "com.android.packageinstaller.permission.ui.GrantPermissionsActivity");
			cap.setCapability("appWaitDuration", 60000);

		}
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");

		driver = new AndroidDriver<>(new URL(service.getUrl().toString()), cap);
		((HasSettings) driver).setSetting(Setting.NORMALIZE_TAG_NAMES, true);
		System.out.println(((HasSettings) driver).getSettings());
		driver.manage().timeouts().implicitlyWait(Constants.LONG_TIME, TimeUnit.SECONDS);

		return driver;

	}

	public AppiumDriver<MobileElement> openIOSApp(String deviceName, String udid, String url) throws MalformedURLException {
		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("xcodeSigningId", "iPhone Developer");
		caps.setCapability(MobileCapabilityType.UDID, udid);
		caps.setCapability("bundleId", "com.vcbmb.enterprise");

		caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.3.1");
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
		driver = new IOSDriver(new URL(url), caps);
		((HasSettings) driver).setSetting(Setting.NORMALIZE_TAG_NAMES, true);
		driver.manage().timeouts().implicitlyWait(Constants.LONG_TIME, TimeUnit.SECONDS);
		return driver;
	}

	public AppiumDriver<MobileElement> openGlobalSetting(String deviceName, String udid, String url) throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		cap.setCapability("uid", udid);
		cap.setCapability("appPackage", "com.android.settings");
		cap.setCapability("appActivity", "com.android.settings.Settings");
		driver = new AndroidDriver<>(new URL(url), cap);

		driver.manage().timeouts().implicitlyWait(Constants.LONG_TIME, TimeUnit.SECONDS);

		return driver;

	}

	public void closeApp() {
		driver.quit();
	}

	public AppiumDriver<MobileElement> openAndroidBrowser(String device, String browser, String platformVersion) throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
		if (device.equalsIgnoreCase("virtual")) {
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, "AndroidPixel2");
		} else if (device.equalsIgnoreCase("real")) {
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
			cap.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
			cap.setCapability("platformName", "Android");
			cap.setCapability("platformVersion", platformVersion);
			cap.setCapability("noReset", "true");
		}
		cap.setCapability("appPackage", "com.android.chrome");
		cap.setCapability("appActivity", "com.google.android.apps.chrome.Main");
		AppiumDriver<MobileElement> driver = new AppiumDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return driver;

	}

	public AppiumDriverLocalService startServer() throws IOException, InterruptedException {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.toLowerCase().contains("window")) {
			Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		} else if (osName.toLowerCase().contains("mac")) {
			try {
				Runtime.getRuntime().exec("killall node");
				System.out.println("Appium server stopped.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Thread.sleep(2000);
		service = AppiumDriverLocalService.buildDefaultService();
		service.start();
		System.out.println("Server" + service.getUrl());

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
		softAssertion = new SoftAssert();
		boolean pass = true;
		try {
			if (condition == true)
				log.info("===PASSED===");
			else
				log.info("===FAILED===");
//			softAssertion.assertTrue(condition);
//			softAssertion.assertAll();
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
		softAssertion = new SoftAssert();
		boolean pass = true;
		try {
			if (condition == false)
				log.info("===PASSED===");
			else
				log.info("===FAILED===");
//			softAssertion.assertFalse(condition);
//			softAssertion.assertAll();

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
		softAssertion = new SoftAssert();
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
//
//			softAssertion.assertEquals(actual, expected, "Value is not matching!");
//			softAssertion.assertAll();

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

	public static int getCurrentDayOfWeek(LocalDate localDate) {
		DayOfWeek dayOfWeek = DayOfWeek.from(localDate);
		int val = dayOfWeek.getValue();
		return val;
	}

	public static String getCurrentDay() {
		DateTime nowUTC = new DateTime(DateTimeZone.UTC);
		int day = nowUTC.getDayOfMonth();
		if (day < 10) {
			String day1 = "0" + day;
			return day1;
		} else {
			return day + "";
		}

	}

	public static String getCurrenMonth() {
		DateTime nowUTC = new DateTime(DateTimeZone.UTC);
		int month = nowUTC.getMonthOfYear();
		if (month < 10) {
			String month1 = "0" + month;
			return month1;
		} else {
			return month + "";
		}
	}

	public static String getCurrenMonth2() {
		DateTime nowUTC = new DateTime(DateTimeZone.UTC);
		int month = nowUTC.getMonthOfYear();
		return month + "";
	}

	public static String getCurrentYear() {
		DateTime nowUTC = new DateTime(DateTimeZone.UTC);
		return nowUTC.getYear() + "";

	}

	public String convertMonthVietNamese(String month) {
		switch (month) {
		case "01":
			return "Tháng 1";
		case "02":
			return "Tháng 2";
		case "03":
			return "Tháng 3";
		case "04":
			return "Tháng 4";
		case "05":
			return "Tháng 5";
		case "06":
			return "Tháng 6";
		case "07":
			return "Tháng 7";
		case "08":
			return "Tháng 8";
		case "09":
			return "Tháng 9";
		case "10":
			return "Tháng 10";
		case "11":
			return "Tháng 11";
		case "12":
			return "Tháng 12";
		default:
			return "";
		}
	}

	public String convertDayOfWeekVietNamese(int day) {
		switch (day) {
		case 1:
			return "Thứ 2";
		case 2:
			return "Thứ 3";
		case 3:
			return "Thứ 4";
		case 4:
			return "Thứ 5";
		case 5:
			return "Thứ 6";
		case 6:
			return "Thứ 7";
		case 7:
			return "Chủ nhật";
		default:
			return "";
		}
	}

	public String convertDayOfWeekVietNamese2(int day) {
		switch (day) {
		case 1:
			return "Thứ hai";
		case 2:
			return "Thứ ba";
		case 3:
			return "Thứ tư";
		case 4:
			return "Thứ năm";
		case 5:
			return "Thứ sáu";
		case 6:
			return "Thứ bảy";
		case 7:
			return "Chủ nhật";
		default:
			return "";
		}
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
		DecimalFormat decim = new DecimalFormat("#.##");
		Double amount1 = Double.parseDouble(decim.format(amount));

		return amount1;
	}

	public String addCommasToDouble(String number) {
		String m = "";
		try {
			double amount = Double.parseDouble(number);
			m = String.format("%,.2f", amount);
		} catch (Exception e) {

		}

		return m;
	}

	public static String addCommasToLong(String number) {
		String m = "";
		try {
			if (number.equals("0")) {
				m = "0";
			} else {
				long amount = Long.parseLong(number);
				m = String.format("%,d", amount);
			}

		} catch (Exception e) {

		}

		return m;
	}

	public String getForwardDate(long days) {
		LocalDate now = LocalDate.now();
		LocalDate thirtyDay = now.plusDays(days);

		int year = thirtyDay.getYear();

		int month = thirtyDay.getMonthValue();
		String month1;
		if (month < 10) {
			month1 = "0" + month;
		} else {
			month1 = month + "";
		}
		int day = thirtyDay.getDayOfMonth();
		String day1;

		if (day < 10) {
			day1 = "0" + day;
		} else {
			day1 = day + "";
		}
		System.out.println(day1 + "/" + month1 + "/" + year);
		return day1 + "/" + month1 + "/" + year;

	}

	public String getBackwardDate(long days) {
		LocalDate now = LocalDate.now();
		LocalDate thirtyDay = now.minusDays(days);

		int year = thirtyDay.getYear();

		int month = thirtyDay.getMonthValue();
		String month1;
		if (month < 10) {
			month1 = "0" + month;
		} else {
			month1 = month + "";
		}
		int day = thirtyDay.getDayOfMonth();
		String day1;

		if (day < 10) {
			day1 = "0" + day;
		} else {
			day1 = day + "";
		}
		System.out.println(day1 + "/" + month1 + "/" + year);
		return day1 + "/" + month1 + "/" + year;

	}

	public String getForwardMonthAndForwardDay(long months, long days) {
		LocalDate now = LocalDate.now();
		LocalDate dayLocal = now.plusDays(days);
		LocalDate monthLocal = now.plusMonths(months);

		int year = dayLocal.getYear();
		int month = monthLocal.getMonthValue();
		int monthNow = now.getMonthValue();
		int day = dayLocal.getDayOfMonth();

		String month1;
		if (month < 10) {
			month1 = "0" + month;
		} else {
			month1 = month + "";
		}

		String day1;
		if (day < 10) {
			day1 = "0" + day;
		} else {
			day1 = day + "";
		}

		String year1;
		if (month < monthNow) {
			year1 = year + 1 + "";
		} else {
			year1 = year + "";
		}

		return day1 + "/" + month1 + "/" + year1;

	}

	public String getForwardMonthAndForwardDayFolowDate(String date, long months, long days) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate now = LocalDate.parse(date, dateTimeFormatter);

		LocalDate dayLocal = now.plusDays(days);
		LocalDate monthLocal = now.plusMonths(months);

		int year = dayLocal.getYear();
		int month = monthLocal.getMonthValue();
		int monthNow = now.getMonthValue();
		int day = dayLocal.getDayOfMonth();

		String month1;
		if (month < 10) {
			month1 = "0" + month;
		} else {
			month1 = month + "";
		}

		String day1;
		if (day < 10) {
			day1 = "0" + day;
		} else {
			day1 = day + "";
		}

		String year1;
		if (month < monthNow) {
			year1 = year + 1 + "";
		} else {
			year1 = year + "";
		}

		return day1 + "/" + month1 + "/" + year1;

	}

	public String getForwardYear(long years) {
		LocalDate now = LocalDate.now();
		LocalDate futureYear = now.plusYears(years);

		int year = futureYear.getYear();

		int month = futureYear.getMonthValue();
		String month1;
		if (month < 10) {
			month1 = "0" + month;
		} else {
			month1 = month + "";
		}
		int day = futureYear.getDayOfMonth();
		String day1;

		if (day < 10) {
			day1 = "0" + day;
		} else {
			day1 = day + "";
		}

		System.out.println(day1 + "/" + month1 + "/" + year);
		return day1 + "/" + month1 + "/" + year;

	}

	public String getBackWardDay(long days) {
		LocalDate now = LocalDate.now();
		LocalDate date = now.minusDays(days);

		int day = date.getDayOfMonth();
		return day + "";
	}

	// Back năm về quá khứ so với năm hiện tại
	public String getBackWardYear(long years) {
		LocalDate now = LocalDate.now();
		LocalDate date = now.minusYears(years);

		int year = date.getYear();
		return year + "";
	}

	public String getCurentMonthAndYearPlusDays(long days) {
		LocalDate now = LocalDate.now();
		LocalDate date = now.plusDays(days);
		int month = date.getMonthValue();
		int year = date.getYear();
		return "THÁNG" + " " + month + " " + year;
	}

	public String getCurentMonthAndYearPlusMonth(long Months) {
		LocalDate now = LocalDate.now();
		LocalDate date = now.plusMonths(Months);
		int month = date.getMonthValue();
		int year = date.getYear();
		return "THÁNG" + " " + month + " " + year;
	}

	public String getCurentMonthAndYearMinusDays(long days) {
		LocalDate now = LocalDate.now();
		LocalDate date = now.minusDays(days);
		int month = date.getMonthValue();
		int year = date.getYear();
		return "THÁNG" + " " + month + " " + year;
	}

	public String convertMonthFomatTH(String month) {
		switch (month) {
		case "01":
			return "TH 1";
		case "02":
			return "TH 2";
		case "03":
			return "TH 3";
		case "04":
			return "TH 4";
		case "05":
			return "TH 5";
		case "06":
			return "TH 6";
		case "07":
			return "TH 7";
		case "08":
			return "TH 8";
		case "09":
			return "TH 9";
		case "10":
			return "TH 10";
		case "11":
			return "TH 11";
		case "12":
			return "TH 12";
		default:
			return "";
		}
	}

	public String convertMonthFomatTh(String month) {
		switch (month) {
		case "01":
			return "Th1";
		case "02":
			return "Th2";
		case "03":
			return "Th3";
		case "04":
			return "Th4";
		case "05":
			return "Th5";
		case "06":
			return "Th6";
		case "07":
			return "Th7";
		case "08":
			return "Th8";
		case "09":
			return "Th9";
		case "10":
			return "Th10";
		case "11":
			return "Th11";
		case "12":
			return "Th12";
		default:
			return "";
		}
	}

	public String convertDayOfWeekVietNameseFull(int day) {
		switch (day) {
		case 1:
			return "Thứ hai";
		case 2:
			return "Thứ ba";
		case 3:
			return "Thứ tư";
		case 4:
			return "Thứ năm";
		case 5:
			return "Thứ sáu";
		case 6:
			return "Thứ bảy";
		case 7:
			return "Chủ nhật";
		default:
			return "";
		}
	}

	public String getForWardDay(long days) {
		LocalDate now = LocalDate.now();
		LocalDate date = now.plusDays(days);
		int day = date.getDayOfMonth();
		return day + "";
	}

	public String getCurentMonthAndYearPlusDays1(long days) {
		LocalDate now = LocalDate.now();
		LocalDate date = now.plusDays(days);
		int month = date.getMonthValue();
		int year = date.getYear();
		return "THÁNG" + " " + month + " " + year;
	}

	public String getCurentMonthAndYearMinusDays1(long days) {
		LocalDate now = LocalDate.now();
		LocalDate date = now.minusDays(days);
		int month = date.getMonthValue();
		int year = date.getYear();
		return "THÁNG" + " " + month + " " + year;
	}

	public long canculateAvailableBalances(long surPlus, long money, long transactionFree) {
		return surPlus - money - transactionFree;
	}

	public double canculateAvailableBalancesCurrentcy(double surPlus, double money, double transactionFree) {
		double scale = Math.pow(10, 2);
		return Math.round((surPlus - money - transactionFree) * scale) / scale;

	}

	public double convertAvailableBalanceCurrentcyToDouble(String money) {
		double result = 0;
		try {
			result = Double.parseDouble(money.replaceAll("[^\\.0123456789]", ""));
		} catch (Exception e) {

		}
		return result;
	}

	public long convertAvailableBalanceCurrentcyOrFeeToLong(String money) {
		long result = 0;
		try {
			if (money.contentEquals("Không mất phí")) {
				result = 0;
			} else {
				result = Long.parseLong(money.replaceAll("[^\\.0123456789]", ""));
			}

		} catch (Exception e) {

		}
		return result;
	}

	public String convertEURO_USDToVNeseMoney(String money, String currentcy) {
		String result = "";
		try {
			result = String.format("%,d", Math.round(Double.parseDouble(money) * Double.parseDouble(currentcy))) + " VND";
		} catch (Exception e) {

		}
		return result;
	}

	public String convertEURO_USDToVNDMoney(String money, String currency) {
		String result = "";
		try {
			result = String.format("%,.2f", (Double.parseDouble(money) * Double.parseDouble(currency)));
			if (result.contains(".00")) {
				result = result.replace(".00", "");
			}
		} catch (Exception e) {
		}
		return result;
	}

//
	public double convertVNeseMoneyToEUROOrUSD(String money, String currentcy) {
		double result = 0;
		try {
			double scale = Math.pow(10, 2);
			result = Math.round((Double.parseDouble(money) / (Double.parseDouble(currentcy)) * scale)) / scale;
		} catch (Exception e) {

		}
		return result;
	}

	public String convertVNDToEUROOrUSD(String money, String currency) {
		String result = "";
		try {
			result = String.format("%,.2f", (Double.parseDouble(money) / Double.parseDouble(currency)));
		} catch (Exception e) {

		}
		return result;
	}

	public String getCurrentcyMoney(String money) {
		String result = "";
		try {
			result = money.split("~")[1].replaceAll("[^\\.0123456789]", "");
		} catch (Exception e) {
			result = "0";
		}
		return result;
	}

	public String getSplitStringIndex(String stringText, String splitText, int index) {
		String result = "";
		try {
			result = stringText.split(splitText)[index].replaceAll("[^\\.0123456789]", "");
		} catch (Exception e) {
			result = "0";
		}
		return result;
	}

	public String getRawSplitStringIndex(String stringText, String splitText, int index) {
		String result = "";
		try {
			result = stringText.split(splitText)[index];
		} catch (Exception e) {
			result = "0";
		}
		return result;
	}

	public String getCounterPlus(String stringText, int i) {
		String result = "";
		int total = 0;
		try {
			total = (Integer.parseInt(stringText) + i);
			if (total > 9) {
				result = total + "";
			} else {
				result = "0" + total;
			}
		} catch (Exception e) {
			result = "0";
		}
		return result;
	}

	public String convertDateTimeIgnoreHHmmss(String stringDate) {
		String result = "";
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			result = formatter2.format(formatter1.parse(stringDate));
		} catch (Exception e) {

		}
		return result;

	}

	public String convertTransferTimeToReportDateTime(String stringDate) {
		String result = "";
		try {
			result = stringDate.split(" ")[3];
		} catch (Exception e) {

		}
		return result;

	}

	public String convertVietNameseStringToString(String vietnameseString) {
		String temp = Normalizer.normalize(vietnameseString, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	// Viet hoa chu cai dau
	public String capitalizeString(String string) {
		char[] chars = string.toLowerCase().toCharArray();
		boolean found = false;
		for (int i = 0; i < chars.length; i++) {
			if (!found && Character.isLetter(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other
																									// chars here
				found = false;
			}
		}
		return String.valueOf(chars);
	}

	// Kiem tra thoi gian hien thi co dung format hay khong
	public static boolean validateDateFormat(String strDate, String dateFormat) {
		if (strDate.trim().equals("")) {
			return true;
		} else {
			SimpleDateFormat sdfrmt = new SimpleDateFormat(dateFormat);
			sdfrmt.setLenient(false);
			try {
				Date javaDate = sdfrmt.parse(strDate);
			} catch (ParseException e) {
				return false;
			}
			return true;
		}
	}

	public boolean checkDateLessThanNow(String date) {
		boolean result = true;
		try {
			SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

			Date date1 = formatDate.parse(date);

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date now = new Date();

			Date date2 = formatDate.parse(dateFormat.format(now).toString());

			if (date1.after(date2)) {
				result = false;
			}

			if (date1.before(date2)) {
				result = true;
			}

			if (date1.equals(date2)) {
				result = false;
			}

		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return result;

	}
	
	public static Credential authorize() throws GeneralSecurityException, IOException {

		InputStream in = Base.class.getResourceAsStream("/credentials.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));
		List<String> scope = Arrays.asList(SheetsScopes.SPREADSHEETS);

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), clientSecrets, scope).setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens"))).setAccessType("offline").build();

		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

		return credential;

	}

	public static Sheets getSheetService() throws GeneralSecurityException, IOException {
		Credential credential = authorize();
		return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential).setApplicationName(APPICATION_LINK).build();

	}

	public static String getDataInCell(int rowNumberOfData) throws GeneralSecurityException, IOException {
		String a = null;
		sheetService = getSheetService();
		String range = "DATA_DEV!C5:D60";
		ValueRange response = sheetService.spreadsheets().values().get(SPREADSHEET_ID, range).execute();
		List<List<Object>> values = response.getValues();
		if (values == null || values.isEmpty()) {
			System.out.println("No data found");
		} else {
			// Số dòng trên bảng
			List row = values.get(rowNumberOfData);
			String[] array = row.toString().trim().replace("]", "").split(",");
			// Vị trí của item trên từng row ( column)
			a = array[1].trim();

		}
		return a;

	}

}
