package commons;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Base {
	SoftAssert softAssertion;
	protected final Logger log;
	protected AndroidDriver<AndroidElement> driver;
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

	@AfterSuite
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

	public AndroidDriver<AndroidElement> openAndroidApp(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws MalformedURLException {
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

	public AndroidDriver<AndroidElement> openAndroidBrowser(String device, String browser) throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
		if (device.equalsIgnoreCase("virtual ")) {
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, "AndroidPixel2");
		} else if (device.equalsIgnoreCase("real")) {
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
		}
		cap.setCapability(MobileCapabilityType.BROWSER_NAME, browser);
		System.setProperty("webdriver.chrome.driver", "E:\\Software\\Copy of eclipse-java-photon-R-win32-x86_64\\Workspace\\APPIUM_DEMO\\lib\\chromedriver.exe");
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
		softAssertion = new SoftAssert();
		boolean pass = true;
		try {
			if (condition == true)
				log.info("===PASSED===");
			else
				log.info("===FAILED===");
			softAssertion.assertTrue(condition);
			softAssertion.assertAll();
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
			softAssertion.assertFalse(condition);
			softAssertion.assertAll();
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

			softAssertion.assertEquals(actual, expected, "Value is not matching!");
			softAssertion.assertAll();
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

	public static String getCurrentYear() {
		DateTime nowUTC = new DateTime(DateTimeZone.UTC);
		return nowUTC.getYear() + "";

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
}
