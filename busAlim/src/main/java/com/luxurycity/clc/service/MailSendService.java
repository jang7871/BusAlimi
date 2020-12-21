package com.luxurycity.clc.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.luxurycity.clc.util.MailUtil;

public class MailSendService {

	private int size;

	@Autowired
	private JavaMailSenderImpl mailSender;
	
	// 인증키 생성
	private String getKey(int size) {
		this.size = size;
		return getAuthCode();
	}
	
	// 인증코드 난수 발생
	private String getAuthCode() {
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		int num = 0;
		
		while(buffer.length() < size) {
			num = random.nextInt(10);	// random.nextInt(int num) : 0 ~ (num-1)중 1개의 난수를 선택한다.
			buffer.append(num);
		}
		
		return buffer.toString();
	}
	
	// 인증메일 보내기
	public String sendAuthMail(String mail) {
		String authKey = getKey(6);
		StringBuffer htmlContent = new StringBuffer();
		htmlContent.append("<h1>[이메일인증]<h1>").append("<p>인증번호 :" + authKey + "</p>");
		
		try {
			MailUtil sendMail = new MailUtil(mailSender);
			sendMail.setSubject("LuxuryCity 회원가입 이메일 인증");	// 이메일 제목
			sendMail.setText(htmlContent.toString());			// 이메일 내용
			sendMail.setFrom("luxuryCity.busalimi", "관리자");	// 서버 이메일 , 이름
			sendMail.setTo(mail);								// 클라이언트 이메일
			sendMail.send();									// 메일 보내기
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return authKey;
	}
}
