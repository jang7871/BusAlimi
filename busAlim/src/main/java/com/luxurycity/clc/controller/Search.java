package com.luxurycity.clc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import com.luxurycity.clc.vo.*;
import com.luxurycity.clc.dao.*;
import com.luxurycity.clc.util.*;

@Controller
@RequestMapping("/search")
public class Search {
	@Autowired
	SearchDao sDao;
	
	@ResponseBody
	@RequestMapping("/busModal.clc")
	public List<RouteVO> busModal(@RequestBody HashMap<String, String> map, RouteVO rVO, PageUtil page) {
		// vo에 키워드넣고
		rVO.setKeyword(map.get("name"));
		// 총 갯수 가져오고 설정하고
		int total = sDao.getBusTotal(rVO.getKeyword());
		page.setTotalCount(total);
		// 현재 페이지 가져오고 설정하고
		page.setNowPage(Integer.parseInt(map.get("nowPage")));
		// pageutil 만들고
		page.setPage(page.getNowPage(), total, 3, 5);
		// vo에 pageutil 저장하고
		rVO.setPage(page);
		// vo 보내고 결과 받고
		List<RouteVO> list = sDao.getBusRellist(rVO);
		for(int i = 0; i < list.size(); i++) {
			list.get(i).setPage(page);
		}
		return list;
	}
	
	@ResponseBody
	@RequestMapping("/staModal.clc")
	public List<StationVO> staModal(@RequestBody HashMap<String, String> map, StationVO sVO, PageUtil page) {
		sVO.setKeyword(map.get("name"));
		int total = sDao.getStaTotal(sVO.getKeyword());
		page.setTotalCount(total);
		page.setNowPage(Integer.parseInt(map.get("nowPage")));
		page.setPage(page.getNowPage(), total, 3, 5);
		sVO.setPage(page);
		List<StationVO> list = sDao.getStaRelList(sVO);
		for(int i = 0; i < list.size(); i++) {
			list.get(i).setPage(page);
		}
		return list;
	}
	
	@ResponseBody
	@RequestMapping("/relationlist.clc")
	public List<RouteVO> relationList(@RequestBody HashMap<String, String> map) {
		// 키워드 보내고 결과 받고
		List<RouteVO> list = sDao.getBusKeyList(map.get("keyword"));
		return list;
	}
	
	@ResponseBody
	@RequestMapping("/starelationlist.clc")
	public List<StationVO> staRelationList(@RequestBody HashMap<String, String> map) {
		// 키워드 보내고 결과 받고
		List<StationVO> list = sDao.getStakeyList(map.get("keyword"));
		return list;
	}
}
