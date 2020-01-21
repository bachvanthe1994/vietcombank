package vnpay.vietcombank.login;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

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

public class Test {
	// for example, smtp.mailgun.org
	private static final String SMTP_SERVER = "smtp.gmail.com";

	private static final String PASSWORD = "Abc12345@";

	private static final String EMAIL_FROM = "vnpay.automation.team@gmail.com";
	private static final String EMAIL_TO = "vnpay.automation.team@gmail.com";

	private static final String EMAIL_SUBJECT = "TestNG Report";
	private static final String EMAIL_TEXT = "Hello Java Mail \n ABC123";

	public static void main(String[] args) throws IOException {
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
			textBoyPart.setText("Download all files to see report");

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
}
