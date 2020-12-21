package com.luxurycity.clc.util;

import java.io.UnsupportedEncodingException;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtil {
	// 메일을 보내는 기능을 가진 클래스
	private JavaMailSender mailSender;	
	// 메일의 틀을 제공해주는 클래스
	private MimeMessage message;		 
	// 메일에 제목, 내용, 수신자메일, 발신자메일을 기록하는 기능을 가진 클래스
	private MimeMessageHelper messageHelper;	
	
	public MailUtil(JavaMailSender mailSender) throws MessagingException {
		// 할 일
		// MimeMessageHelper 를 만들어준다.
		this.mailSender = mailSender;
		message = this.mailSender.createMimeMessage();
		messageHelper = new MimeMessageHelper(message, true, "UTF-8");
	}
	
	public void setSubject(String subject) throws MessagingException {
		// 할 일
		// 제목 입력 : 필수
		messageHelper.setSubject(subject);
	}
	
	public void setText(String htmlContent) throws MessagingException {
		// 할 일
		// 내용 입력 : 필수
		// 두 번째 인자 true는 html을 사용하겠다는 뜻이다.
		messageHelper.setText(htmlContent, true);
	}
	
	public void setFrom(String fromMail, String personal) throws UnsupportedEncodingException, MessagingException {
		// 할 일
		// 발신자메일, 발신자이름 입력 : 필수
		messageHelper.setFrom(fromMail, personal);
	}
	
	public void setTo(String mail) throws MessagingException {
		// 할 일
		// 수신자메일 입력
		messageHelper.setTo(mail);
	}
	
	public void addInline(String contentId, DataSource dataSource) throws MessagingException {
		// 할 일
		// 메일에 이미지를 삽입 : 선택사항.
		/*
		 	이 부분은 아직 사용하지 않는다. 
		 	필요할 때 검색해서 사용할 예정.
		 */
		messageHelper.addInline(contentId, dataSource);
	}
	
	public void send() {
		// 할 일
		// 메일을 보낸다.
		mailSender.send(message);
	}
}
