package com.luxurycity.clc.service;


import java.util.HashMap;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.servlet.*;

import com.luxurycity.clc.dao.*;
import com.luxurycity.clc.vo.*;

public class MemberService {

		@Autowired
		MemberDao mDao;
		@Autowired
		BookmarkDao bmDao;
		// 회원정보 가져오는 함수
		public void setMyInfo(ModelAndView mv, MemberVO mVO, AvatarVO aVO, HttpSession session) {
			String id = (String) session.getAttribute("SID");
			if(id == null) {
				mv.setViewName("redirect:/main.clc");
				return;
			}
			// 아바타 dir, afile 가져오기
			aVO = mDao.myInfo1(id);
			mVO = mDao.myInfo2(id);
			
			mv.addObject("AVT", aVO);
			mv.addObject("DATA", mVO);
			mv.setViewName("member/MyInfo");
		}
	 
		// 회원정보 수정하는 함수
		public void setMyInfoEdit(ModelAndView mv, MemberVO mVO, HttpSession session) {
			mVO.setId((String) session.getAttribute("SID"));
			int cnt = mDao.myInfoEdit(mVO);
			if(cnt != 1) {
				System.out.println("### 회원정보수정에 실패하였습니다.");
				mv.setViewName("redirect:/member/myinfo.clc");
				return;
			}
			System.out.println("확인용 : " + mVO.getAno());
			
			mv.setViewName("redirect:/member/mypage.clc");
		}
		
		// 회원정보 삭제하는 함수
		public void setMyInfoDel(ModelAndView mv, MemberVO mVO, HttpSession session) {
			mVO.setId((String) session.getAttribute("SID"));
			int cnt = mDao.myInfoDel(mVO);
			if (cnt != 1) {
				mv.setViewName("redirect:/member/myinfo.clc");
				return;
			}
			
			mv.setViewName("redirect:/main.clc");
			return;
		}
		
		public HashMap<String, String> setBookAdd(HashMap<String, Object> map, HttpSession session, BookmarkVO bmVO) {
			HashMap<String, String> map2 = new HashMap<String, String>();
			String result = "";
			// 1. 파라미터 값을 꺼내온다.
			String type = (String) map.get("type");
			// 2. 세션 검사
			String id = (String) session.getAttribute("SID");
			if(id == null) {
				result = "LOGIN";
				map2.put("result", result);
				return map2;
			}
			map.put("id", id);
			// 3. dao 처리
			int cnt = 0;
			cnt = bmDao.bookAdd(map);
			if(cnt == 0) {
				result = "NO";
			} else {
				if(type.equals("bus")) {
					result = "BUSADD";
				} else if(type.equals("station")) {
					result = "STAADD";
				} else if(type.equals("busstation")) {
					result = "BUSSTAADD";
				}
			}
			map2.put("result", result);
			
			return map2;
		}

}