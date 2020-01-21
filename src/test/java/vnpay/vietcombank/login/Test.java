package vnpay.vietcombank.login;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Test {
	// for example, smtp.mailgun.org
	private static final String SMTP_SERVER = "smtp.gmail.com";

	private static final String PASSWORD = "tathianh";

	private static final String EMAIL_FROM = "tathianh.3k11@gmail.com";
	private static final String EMAIL_TO = "anhtt@vnpay.vn";

	private static final String EMAIL_SUBJECT = "Test Send Email via SMTP";
	private static final String EMAIL_TEXT = "Hello Java Mail \n ABC123";

	public static void main(String[] args) {
		System.out.println("Preparing to send email");
		Properties prop = new Properties();
		prop.put("mail.smtp.host", SMTP_SERVER);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.port", "587");

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

			msg.setText(EMAIL_TEXT);

			msg.setSentDate(new Date());

			Transport.send(msg);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
}
