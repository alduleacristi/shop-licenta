package com.siemens.ctbav.intership.shop.util.operator;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {

	static final String username = "deliutzzz_23@yahoo.com",
			password = "alinnuts";
	static String smtpServ = "smtp.mail.yahoo.com";

	public static void sendLink(String to, String subject, String mess, boolean isHtml) throws AddressException, MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpServ);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		Message message = new MimeMessage(session);

		
		
		// else pentru timp sa vad
		message.setFrom(new InternetAddress(username));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
	//	if(!isHtml)
		message.setSubject(subject);
		if(isHtml)
			message.setContent(mess,"text/html");
		message.setText(mess);
		Transport.send(message);

		System.out.println("Done");

	}
}
