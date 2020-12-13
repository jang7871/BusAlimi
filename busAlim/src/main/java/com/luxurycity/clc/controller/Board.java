package com.luxurycity.clc.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.luxurycity.clc.dao.*;
import com.luxurycity.clc.vo.*;
import java.util.*;
import com.luxurycity.clc.util.*;

@Controller
@RequestMapping("/board")
public class Board {
	@Autowired
	BoardDao bDao;
	@Autowired
	PageUtil page;
	
	@RequestMapping("/board.clc")
	public ModelAndView board(ModelAndView mv, HttpServletRequest req) {
		int nowPage = 1;
		try {
			nowPage = Integer.parseInt(req.getParameter("nowPage"));
		}catch(Exception e) {}
		
		int total = bDao.boardCnt();
		//페이지 만들고
		PageUtil page = new PageUtil(nowPage,total,5,5);
		//보드리스트 뽑고
		List<BoardVO> blist = bDao.boardList(page);
		
		System.out.println(blist.size()+"##################");
		
		//보드리스트가 없는경우는 없다고 가정함
		mv.addObject("PAGE", page);
		mv.addObject("LIST", blist);
		
		mv.setViewName("board/Board");
		
		
		return mv;
	}
	
	@RequestMapping("/writeproc.clc")
	public ModelAndView writeProc(ModelAndView mv, BoardVO bVO, HttpSession session) {
		int cnt = 0;
		String id = "";
		try {
			id =(String) session.getAttribute("SID");
		}catch(Exception e) {}
		//로그인 안되어있으면 로그인 페이지로
		if(id == null) {
			mv.setViewName("member/login");
			return mv;
		}
		
		try {
			bVO.setSid(id);
			cnt = bDao.addBoard(bVO);
		}catch(Exception e) {}
		
		if(cnt != 1) {
			System.out.println("데이터 안들어감");
		}
		
		mv.setViewName("redirect:/board/board.clc");
		
		
		return mv;
	}
	@RequestMapping("/delproc.clc")
	public ModelAndView delProc(ModelAndView mv, int bno, HttpSession session) {
		int cnt = 0;
		String id = "";
		try {
			id =(String) session.getAttribute("SID");
		}catch(Exception e) {}
		//로그인 안되어있으면 로그인 페이지로
		if(id == null) {
			mv.setViewName("member/login");
			return mv;
		}
		
		try {
			cnt = bDao.delBoard(bno);
		}catch(Exception e) {}
		
		if(cnt != 1) {
			System.out.println("삭제가 안됨..이럴 경우가 있을까");
		}
		
		mv.setViewName("redirect:/board/board.clc");
		
		
		return mv;
	}
	
}
