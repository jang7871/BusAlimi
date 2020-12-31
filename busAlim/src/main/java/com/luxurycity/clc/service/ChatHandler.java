package com.luxurycity.clc.service;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.luxurycity.clc.controller.Member;

@Component
public class ChatHandler extends TextWebSocketHandler implements WebSocketConfigurer{

	private static List<WebSocketSession> list = new ArrayList<WebSocketSession>();
	public static ArrayList<HashMap<String, String>> requestBox = new ArrayList<HashMap<String, String>>();
	public static ArrayList<HashMap<String, String>> messageBox = new ArrayList<HashMap<String, String>>();

	//클라이언트가 접속 했을 때 호출되는 메소드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		list.add(session);
		System.out.println("* 하나의 클라이언트가 연결됨 ");		
	}
	
	//클라이언트가 메시지를 보냈을 때 호출되는 메소드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 전송된 메시지를 List의 모든 세션에 전송
		String sid = "";
		String reid = "";
		String msg = message.getPayload();
		
		System.out.println("메세지 확인 : " + msg);
		// 친구 요청 
		char mark[] = msg.trim().toCharArray();
		if(mark[0] == '?') {
			// 친구 추가 요청
			sid = msg.substring(1, msg.indexOf(':')).trim();	// 친구요청하는사람
			reid = msg.substring(msg.indexOf(':')+1).trim();	// 친구요청받는사람
			HashMap<String, String> map2 = new HashMap<String, String>();
			map2.put(sid, reid);
			requestBox.add(map2);
			return;
		} else if(mark[0] == '*') {
			// 친구 삭제
			return;
		}
		
		Map<String, Object> map0 = session.getAttributes();
		try {
			sid = (String) map0.get("SID");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 메세지 전달
		if(mark[0] == '/') {
			// 입장
		} else {
			// 메세재 보내기
			reid = msg.substring(0, msg.indexOf('/')).trim();
			msg = msg.substring(msg.indexOf('/')+1).trim();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("sid", sid);
			map.put("reid", reid);
			map.put("msg", msg);
			messageBox.add(map);
		}
		
		for (WebSocketSession s : list) {
			s.sendMessage(new TextMessage(session.getAcceptedProtocol()+" : " + msg));
		}
		
	}
	// 클라이언트의 접속이 해제 되었을 때 호출되는 메소드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("* 클라이언트와 연결 해제됨");
		list.remove(session);
	}
	// 여러 클라이언트의 접속을 허용하는 메소드
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(this, "/chat-ws").setAllowedOrigins("*");
	}
}
