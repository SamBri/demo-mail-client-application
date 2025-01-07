package com.nothing.fox.utils;

import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmailServer {

	final static String fromEmail = "lunarhive@gmail.com"; // requires valid gmail id
	final static String password = "4thjan2024@#$"; // correct password for gmail id


	@Value("${mail.smtp.host}")
	private  String mailServer;
	@Value("${mail.smtp.port}")
	private  String mailPort;
	@Value("${mail.smtp.auth}")
	private  String allowAuth;
	@Value("${mail.smtp.starttls.enable}")
	private  String allowTls;

	
	
	


	private  Properties prepareProperties() {
		Properties props = new Properties();

		log.info("mail.smtp.host :: {}", mailServer);
		log.info("mail.smtp.port :: {}", mailPort);
		log.info("mail.smtp.auth :: {}", allowAuth);
		log.info("mail.smtp.starttls.enable :: {}", allowTls);

		props.put("mail.smtp.host", mailServer); // SMTP Host
		props.put("mail.smtp.port", mailPort); // TLS Port
		props.put("mail.smtp.auth", allowAuth); // enable authentication
		props.put("mail.smtp.starttls.enable", allowTls); // enable STARTTLS
		props.put("mail.smtp.socketFactory.port", mailPort); //SSL Port
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
			return props;
	}

	private  Authenticator prepareAuthentication() {

		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};

		return auth;

	}

	/**
	 * Utility method to send simple HTML email
	 * 
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public  void sendEmail(String toEmail, String subject, String body) {
		Session session = Session.getInstance(prepareProperties(), prepareAuthentication());

		try {
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

			msg.setReplyTo(InternetAddress.parse(toEmail, false));

			msg.setSubject(subject, "UTF-8");

			msg.setText(body, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			System.out.println("Message is ready");
			Transport.send(msg);

			System.out.println("EMail Sent Successfully!!");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}