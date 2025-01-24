package com.nothing.fox.mail.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nothing.fox.mail.ImailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("publicEmailApiServer")  // Public Email API Server
public class PublicEmailAPIServer implements ImailService {

	@Value("${mail.provider}")
	private String mailProvider;

	@Override
	public void sendEmail(String toEmail, String subject, String body) {
		// TODO Auto-generated method stub
		
		log.info("inside sendEmail");
		
		log.info("toEmail = {} , subject = {} , body = {}",toEmail,subject,body);


		switch (mailProvider) {

		case "TWILIO":

			break;
		case "SENDGRID":
			break;

		case "AMAZON_SES":
			break;

		default:

		}

	}

}
