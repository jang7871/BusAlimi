package com.luxurycity.clc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.WebSocketSession;

import com.luxurycity.clc.dao.*;
import com.luxurycity.clc.service.*;
import com.luxurycity.clc.vo.*;

import java.util.*;
import com.luxurycity.clc.util.*;

@Controller
@RequestMapping("/member")
public class Member {
	@Autowired
	MemberDao mDao;
	@Autowired
	FindDao fDao;
	@Autowired
	BookmarkDao bmDao;
	@Autowired
	MemberService mService;
	@Autowired
	MailSendService mss;	// 인증메일을 보내는 클래스
	@Autowired
	ChatHandler chat;
	@Autowired
	PageUtil page;
	
	private ArrayList<HashMap<String, String>> message = new ArrayList<HashMap<String, String>>();
	@RequestMapping("/login.clc")
	public ModelAndView login(ModelAndView mv) {
		mv.setViewName("member/Login");
		return mv;
	}
	
	@RequestMapping("/loginProc.clc")
	public ModelAndView loginProc(ModelAndView mv, HttpSession session, MemberVO mVO) {
		
		 
		int cnt = 0;
		// 계정 검사
		cnt = mDao.loginCnt(mVO);
		
		// 존재하지 않는 계정이라면
		if(cnt == 0) {
			// 로그인 페이지로 다시 리다이렉트
			mv.setViewName("redirect:/member/login.clc");
			return mv;
		}
		// 존재하는 계정이면
		// 계정에 해당하는 아바타 가져오고
		AvatarVO aVO = mDao.getAvt(mVO.getId());
		// 세션에 아이디 부여하고
		session.setAttribute("SID", mVO.getId());
		// 세션에 아바타 부여하고
		session.setAttribute("AVT", aVO);
		// 메인페이지로 리다이렉트
		mv.setViewName("redirect:/main.clc");
		 
		
		return mv;
	}
	
	@RequestMapping("/logout.clc")
	public ModelAndView logout(ModelAndView mv, HttpSession session) {
		// 아바타와 아이디 세션에서 삭제하고
		session.removeAttribute("SID");
		session.removeAttribute("AVT");
		
		// 뷰 설정하고
		mv.setViewName("redirect:/main.clc");
		return mv;
	}
	@RequestMapping("/idCheck.clc")
	@ResponseBody
	public String idCheck(ModelAndView mv, String id) {
		String result = "OK";
		int cnt = 0 ; 
		try {
			cnt = mDao.idCheck(id);
		}catch(Exception e) {}
		
		if(cnt != 0) {
			//이경우 이용중인 id가 이미 있는거임
			result  = "NO";
		}
		return result;
	}
	@RequestMapping("/join.clc")
	public ModelAndView join(ModelAndView mv) {
		//아바타랑 질문 리스트 넘기기
		mv.addObject("LIST", mDao.getAvtList());
		mv.addObject("QUE", mDao.getQuest());
		
		// 뷰 설정하고
		mv.setViewName("member/Join");
		return mv;
	}
	
	@RequestMapping(path="/mailConfirm.clc")
	@ResponseBody
	public String mailConfirm(MemberVO mVO, HttpServletResponse resp) {
		String authKey = mss.sendAuthMail(mVO.getMail());	// 인증메일 보내고 인증키 받기
		System.out.println("## 인증키 : " + authKey);
		// 쿠키에 인증키 저장
		Cookie authCookie = new Cookie("authKey", authKey);
		authCookie.setPath("/clc/member");
		authCookie.setMaxAge(60*4);	// 쿠키 유효시간 4분
		resp.addCookie(authCookie);
		
		String result = "OK";
		
		return result;
	}
	
	@RequestMapping("/joinProc.clc")
	public ModelAndView joinProc(ModelAndView mv, HttpSession session, HttpServletRequest req, 
							HttpServletResponse resp, FindVO fVO, MemberVO mVO) {
		// 쿠키에서 인증키값을 골라낸다.
		String authCode = "";
		Cookie[] cookies = req.getCookies();
		for(Cookie cookie : cookies) {
			String authKey = cookie.getName();
			
			if(authKey.equals("authKey")) {
				authCode = cookie.getValue();
				break;
			}
		}
				
		// 인증키 검사
		String inAuthKey = req.getParameter("inAuthKey");
		if(!inAuthKey.equals(authCode)) {
			// 인증키가 다를 경우 바로 함수 종료.
			System.out.println("### 가입이 안된 이유 : 인증코드가 다릅니다. ###");
			mv.setViewName("member/Join");
			return mv;
		}
		
		int cnt = 0;
		//멤버 테이블 및 파인드 테이블에 인서트하기
		try {
			cnt = mDao.addMemb(mVO, fVO);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if(cnt == 2) {
			//이경우 정상적으로 두 테이블에 다들어감
			session.setAttribute("SID", mVO.getId());
			mv.setViewName("redirect:/main.clc");
		}else {;
			mv.setViewName("member/Join");
		}
		
		// 쿠키 삭제
		Cookie authCookie = new Cookie("authKey", null);
		authCookie.setMaxAge(0);
		resp.addCookie(authCookie);
		 
		// 뷰 설정하고
		return mv;
	}	
	
	@RequestMapping("/findpage.clc")
	public ModelAndView findPage(ModelAndView mv, String findType) {
		mv.addObject("TYPE", findType);
		
		if(findType.equals("pwCk")) {
			// 비밀번호 확인 질문 리스트
			List<FindVO> list = fDao.getQuestList();
			mv.addObject("LIST", list);
		}
		mv.setViewName("member/FindPage");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/findidpageproc.clc")
	public HashMap<String, String> findIdPageProc(String mail) {
		HashMap<String, String> map = new HashMap<String, String>();

		// 메일로 아이디 여부 검사하고
		int cnt = fDao.idCount(mail);
		if(cnt == 0) {
			// 일치하는 아이디가 없을 경우
			map.put("result", "NO");
		} else {
			// 일치하는 아이디가 있을 경우
			String id = fDao.findId(mail);
			map.put("result", "OK");
			map.put("id", id);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/findpwpageproc.clc")
	public String findPwPageProc(@RequestBody FindVO fVO) {
		String result = "NO";
		int cnt = 0;
		cnt = fDao.findPw(fVO);
		if(cnt != 0) {
			result = "OK";
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/editpwproc.clc")
	public String editPwProc(@RequestBody FindVO fVO) {
		String result = "NO";
		int cnt = 0;
		cnt = fDao.editPw(fVO);
		if(cnt != 0) {
			result = "OK";
		}
		return result;
	}
	
	@RequestMapping("/mypage.clc")
	public ModelAndView myPage(ModelAndView mv, HttpSession session, PageUtil page, BookmarkVO bmVO) {
		// 세션 검사
		String id = (String) session.getAttribute("SID");
		Object obj = session.getAttribute("AVT");
		if(id == null || obj == null) {
			session.removeAttribute("SID");
			session.removeAttribute("AVT");
			mv.setViewName("redirect:/member/login.clc");
			return mv;
		}
		// id vo에 셋팅
		bmVO.setId(id);
		// 즐겨찾기 갯수(갯수가 더 많은 쪽) 계산하고 pageutil에 셋팅
		int total = bmDao.getTotal(id);
		page.setTotalCount(total);
		// pageutil 페이지 설정하고 vo에 셋팅
		page.setPage();
		bmVO.setPage(page);
		// dao 처리하고 결과 받고
		List<BookmarkVO> slist = bmDao.getStationList(bmVO);
		List<BookmarkVO> blist = bmDao.getBusList(bmVO);
		// 데이터 뷰에 심고
		mv.addObject("BLIST", blist);
		mv.addObject("SLIST", slist);
		mv.addObject("PAGE", page);
		
		mv.setViewName("member/MyPage");
		return mv;
	}
	
	@RequestMapping("/bookdelproc.clc")
	public ModelAndView bookDelProc(ModelAndView mv, HttpSession session, HttpServletRequest req, int nowPage) {
		// 세션 검사
		String id = (String) session.getAttribute("SID");
		Object obj = session.getAttribute("AVT");
		if(id == null || obj == null) {
			session.removeAttribute("SID");
			session.removeAttribute("AVT");
			mv.setViewName("redirect:/member/login.clc");
			return mv;
		}
		// 파라미터 받아오고 정수형으로 변환하고
		String[] sbmno = req.getParameterValues("dellist");
		int[] bmno = new int[sbmno.length];
		for(int i = 0; i < bmno.length; i++) {
			bmno[i] = Integer.parseInt(sbmno[i]);
		}
		
		// dao 처리하고 결과 받고
		int cnt = 0;
		cnt = bmDao.delBookmark(bmno);
		mv.setViewName("redirect:/member/mypage.clc?nowPage=" + nowPage);
		return mv;
	}
	
	
	@RequestMapping("/myinfo.clc")
	public ModelAndView myInfo(ModelAndView mv, MemberVO mVO, AvatarVO aVO, HttpSession session) {
		mService.setMyInfo(mv, mVO, aVO, session);
		return mv;
	}
	
	
	 
	@RequestMapping("/myinfoedit.clc")
	public ModelAndView myInfoEdit(ModelAndView mv, MemberVO mVO, HttpSession session) {
		mService.setMyInfoEdit(mv, mVO, session);
		return mv;
	}
  @RequestMapping("/myinfodel.clc")
	public ModelAndView myInfoDel(ModelAndView mv, MemberVO mVO, HttpSession session) {
		mService.setMyInfoDel(mv, mVO, session);
		return mv;
	}
	@ResponseBody
	@RequestMapping("bookaddproc.clc")
	public HashMap<String, String> bookAddproc(@RequestBody HashMap<String, Object> map, HttpSession session, BookmarkVO bmVO) {
		HashMap<String, String> map2 = mService.setBookAdd(map, session, bmVO);
		return map2;
	}
	
	@ResponseBody
	@RequestMapping("/bookdelprocAjax.clc")
	public HashMap<String, String> bookDelProcAjax(@RequestBody HashMap<String, Integer> map, HttpSession session) {
		HashMap<String, String> map2 = new HashMap<String, String>();
		
		String id = (String) session.getAttribute("SID");
		if(id == null) {
			map2.put("result", "LOGIN");
			return map2;
		}
		
		int cnt = 0;
		cnt = bmDao.delBookmark(map.get("bmno"));
		if(cnt == 0) {
			map2.put("result", "NO");
		} else {
			map2.put("result", "OK");
		}
		return map2;

	}
	
	// 친구 추가 및 삭제 요청
	@ResponseBody
	@RequestMapping("/add_delfriend.clc")
	public HashMap<String, String> add_delFriend(@RequestBody HashMap<String, String> map, HttpSession session) {
		String sid = (String) session.getAttribute("SID");
		map.put("id", sid);
		mDao.addFriend(map);
		
		HashMap<String, String> list = new HashMap<String, String>();
		
		list.put("result", "OK");
		
		int cnt = 0;
		try {
			cnt = Integer.parseInt(map.get("cnt"));
		} catch(Exception e) {}
		
		if(cnt == 0) {
			System.out.println("친구"+map.get("frid")+"를 추가합니다.");
		} else if(cnt != 0) {
			System.out.println("친구"+map.get("frid")+"를 삭제합니다.");	
		}
		
		
		return list;
	}
	
	// 친구 목록 리스트 요청
	@RequestMapping("/friendlist.clc")
	public ModelAndView getFriendList(ModelAndView mv, HttpSession session, HttpServletRequest req) {
		// 세션 검사
		String sid = "";
		
		try {
			sid = (String) session.getAttribute("SID");
			if(sid == null || sid.length() == 0) {
				mv.setViewName("redirect:/member/login.clc");
				return mv;
			} else {
				// 나에게 메세지가 왔는지 확인하기
				ArrayList<HashMap<String, String>> list = chat.messageBox;
				
				int cnt = 0;
				for(int i = 0; i < chat.messageBox.size(); i++) {
					cnt = mDao.addMsg(chat.messageBox.get(i));
				}
				
				if(cnt != 0) {
					System.out.println("메세지 전달 성공.");
				}
				// messageBox 비우기
				for(int i = chat.messageBox.size(); i > 0; i--) {
					chat.messageBox.remove(0);
				}
				
				// 메세지 불러오기
				List<MessageVO> msgList = mDao.getMsgList(sid);
				if(msgList.size() != 0) {
					mv.addObject("MESSAGE", msgList);
				}

 			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 친구 요청 유뮤 검사
		int cnt = 0;
		for(int i = 0; i < chat.requestBox.size(); i++) {
			HashMap<String, String> map = chat.requestBox.get(i);
			
			String value = "";	// 친구요청받는사람
			Set set = map.keySet();
			Iterator iter = set.iterator();
			while(iter.hasNext()) {
				String key = (String) iter.next();	// 친구요청하는사람
				value = map.get(key);	
				
				if(sid.equals(value)) {
					cnt += 1;
					String reqq = key + "님의 친구 요청을 수락하시겠습니까?";
					MessageVO msgVO = new MessageVO();
					msgVO.setMessage(reqq);
					msgVO.setId(key);
					msgVO.setReid(value);
					mv.addObject("msgVO", msgVO);
					break;
				}
			}
			
			
			if(cnt != 0) {
				chat.requestBox.remove(i);
				break;				
			}
		}
		// 페이징
		int nowPage = 1;
		try {
			nowPage = Integer.parseInt(req.getParameter("nowPage"));
		} catch(Exception e) {}
		
		int total = mDao.getMsgCnt(sid);
		
		page = new PageUtil(nowPage, total, 5, 5);

		// 친구 목록 불러오기
		List<MemberVO> friend = mDao.getfriendList(sid);
		
		mv.addObject("PAGE", page);
		mv.addObject("FRIEND", friend);
		mv.setViewName("member/FriendList");
		return mv;
	}
	
	// 친구 요쳥 수락 처리
	@RequestMapping(path="/response.clc")
	public ModelAndView response(ModelAndView mv, FriendVO frndVO) {
		System.out.println("## 함수 실행");
		int cnt = 0;
		try {
			cnt = mDao.addFri(frndVO);
		} catch(Exception e) {
			e.printStackTrace();
		} 
		mv.setViewName("member/FriendList");
		return mv;
	}
	
	// 메세지 확인 처리 요청
	@ResponseBody
	@RequestMapping(path="/msgCheck.clc", method=RequestMethod.POST)
	public HashMap<String, String> msgCheck(@RequestBody HashMap<String, String> map) {
		String result = "";
		
		int msgnoo = 0;
		int cnt = 0;
		try {
			msgnoo = Integer.parseInt(map.get("msgno"));
			cnt = mDao.msgCheck(msgnoo);
		} catch(Exception e) {}
		
		if(cnt != 0) {
			result = "OK";
		}

		map.put("result", result);
		return map;
	}
	
	// 메세지 삭제 요청
	@ResponseBody
	@RequestMapping(path="/msgDel.clc", method=RequestMethod.POST)
	public HashMap<String, String> msgDel(@RequestBody HashMap<String, String> map) {
		String result = "";
		
		int msgnoo = 0;
		int cnt = 0;
		try {
			msgnoo = Integer.parseInt(map.get("msgno"));
			cnt = mDao.msgDel(msgnoo);
		} catch(Exception e) {}
		
		if(cnt != 0) {
			result = "OK";
		}

		map.put("result", result);
		return map;
	}

}