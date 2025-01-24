package com.nothing.fox.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class MailResponse {
	
	private String status;
	
	private String message;

}
