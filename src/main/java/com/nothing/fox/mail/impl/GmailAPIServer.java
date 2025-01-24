package com.nothing.fox.mail.impl;

import org.springframework.stereotype.Component;

import com.nothing.fox.mail.ImailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("gmailApiServer") // Gmail API server
public class GmailAPIServer implements ImailService {

	@Override
	public void sendEmail(String toEmail, String subject, String body) {

		log.info("inside sendEmail");
		
		log.info("toEmail = {} , subject = {} , body = {}",toEmail,subject,body);


	}

}
