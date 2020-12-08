package com.luxurycity.clc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.luxurycity.clc.dao.*;
import com.luxurycity.clc.vo.*;

@Controller
@RequestMapping("/member")
public class Member {
	@Autowired
	MemberDao mDao;
	
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
	
}
