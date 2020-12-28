package com.luxurycity.clc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

import javax.servlet.http.HttpSession;

import com.luxurycity.clc.vo.*;
import com.luxurycity.clc.dao.*;
import com.luxurycity.clc.util.*;
import com.luxurycity.clc.service.*;

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
	@RequestMapping("/stationdetail.clc")
	public ModelAndView stationDetail(ModelAndView mv, StationVO sVO, HttpSession session) {
		int station_id = sVO.getStation_id();
		System.out.println(station_id);
		List<StationVO> slist = sDao.stationDetail(station_id);
		//리스트 길이가 0이면 잘못된거니까 다시 메인으로 이동시킨다
		if(slist.size() == 0) {
			mv.setViewName("redirect:/main.clc");
		}else {
			mv.setViewName("search/StationDetail");
		}

		ArrayList<HashMap> map = stationArrInfo(mv,station_id);
//		mv.addObject("SDATA", sVO);
		// 4. 세션에 아이디가 존재할 경우 해당 즐겨찾기를 가져온다.
		String sid = (String) session.getAttribute("SID");
		if(sid != null) {
			BookmarkVO bmVO = new BookmarkVO();
			bmVO.setStation_id(station_id);
			bmVO.setId(sid);
			List<BookmarkVO> list = sDao.getStaBookmark(bmVO);
			if(list.size() > 0) {
				mv.addObject("BOOKMARK", list);
			}
		}
		mv.addObject("MAP", map);
		mv.addObject("LIST", slist);
		return mv;
	}
	@RequestMapping("/searchrouteoption.clc")
	public ArrayList<StationVO> searchRouteOption(ModelAndView mv, StationVO sVO) {
		MakeRouteOption opt = new MakeRouteOption(mv, sVO);
		ArrayList<StationVO> option = opt.getOption();
		/*
		List<StationVO> list = sDao.getBusOption(sVO);
		ArrayList<StationVO> arr = new ArrayList<StationVO>();
		
		if(list.size() == 0) {
			mv.setViewName("redirect:/main.clc");
		}else {
			mv.setViewName("search/RouteOption");
		}
		for(StationVO VO: list) {
			System.out.println(VO.getStart_id() + "####################" + VO.getEnd_id() + "#########################" + VO.getRoute_id());
			StationVO tVO = sDao.getSearchInfo(VO);
			arr.add(tVO);
		}
		mv.addObject("LIST", arr);
		*/
		return option;
	}
	
	@RequestMapping("/stationArrInfo.clc")
	@ResponseBody
	public ArrayList<HashMap> stationArrInfo(ModelAndView mv, int staid) {
		GetArrInfoByRouteAllList rlist = new GetArrInfoByRouteAllList();
		ArrayList<HashMap> map= rlist.GetArrInfoByRouteAllList(staid);
		
		return map;
	}// 오키 이제 map으로 반환되는거 확인했음 이중구조로 잘나옴 이제 main.js에서 ajax처리해서 form태그 만들어서 넘기는거 할꺼임
			
			
	@RequestMapping("/busdetail.clc")
	public ModelAndView busDetail(ModelAndView mv, @ModelAttribute RouteVO rVO, HttpSession session) {
//		System.out.println(rVO.toString());
		// 1. 노선아이디 값을 가져온다.
		int route_id = rVO.getRoute_id();
		// 2. 해당 정보를 가져온다.
		List<RouteVO> rlist = sDao.busDetail(rVO);

		// 3. 리스트 길이가 0이면 잘못된거니까 다시 메인으로 이동시킨다
//		int peek = 0, npeek = 0;
		if(rlist.size() == 0) {
			mv.setViewName("redirect:/main.clc");
		}else {
			mv.setViewName("search/BusDetail");
			//이떄 peek 정보를 꺼내서 써야 편하다
			// peek 정보는 정보 버튼을 누르면 나오는 모달창에 보여주기로 한다.
//			peek = rlist.get(0).getPeek_alloc();
//			npeek = rlist.get(0).getNpeek_alloc();
		}
		// 4. 세션에 아이디가 존재할 경우 해당 즐겨찾기를 가져온다.
		String sid = (String) session.getAttribute("SID");
		if(sid != null) {
			BookmarkVO bmVO = new BookmarkVO();
			bmVO.setRoute_id(route_id);
			bmVO.setId(sid);
			List<BookmarkVO> list = sDao.getBusBookmark(bmVO);
			if(list.size() > 0) {
				mv.addObject("BOOKMARK", list);
			}
		}

//		mv.addObject("PEEK", peek);
//		mv.addObject("NPEEK", npeek);
//		mv.addObject("INFO", rVO);
		mv.addObject("ROUTE", rlist);
		return mv;
	}

	@ResponseBody
	@RequestMapping("/findBookmarkProc.clc")
	public List<BookmarkVO> findBookmarkProc(@RequestBody HashMap<String, String> map, HttpSession session, BookmarkVO bmVO) {
//		List<BookmarkVO> list = new ArrayList<BookmarkVO>();
		// 1. 세션 검사
		String sid = (String) session.getAttribute("SID");
		if(sid == null) {
			List<BookmarkVO> list = null;
			return list;
		}
		// 2. vo에 아이디와 값 세팅
		bmVO.setId(sid);
		if(map.get("type").equals("route")) {
			bmVO.setRoute_id(Integer.parseInt(map.get("data")));
		} else if(map.get("type").equals("station")){
			bmVO.setStation_id(Integer.parseInt(map.get("data")));
		}
//		System.out.println(bmVO.getStation_id());
		List<BookmarkVO> list = sDao.getBusStaBookmark(bmVO);
//		System.out.println(list.size());
		return list;
	}	
	
	@RequestMapping("/mapSearch.clc")
	public ModelAndView mapSearch(ModelAndView mv) {
		mv.setViewName("search/mapSearch");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/mapRelList.clc")
	public List mapRelList(@RequestBody HashMap<String, Object> map, RouteVO rVO, StationVO sVO, PageUtil page) {
		// 검색 키워드, 검색유형(버스, 정류소), nowPage 가져오기
		String keyword = (String) map.get("keyword");
		String type = (String) map.get("type");
		int nowPage = (int) map.get("nowPage");
		
		// 검색유형이 '버스'인 경우와 '정류소'인 경우로 나눠서 처리
		List list = null;
		
		if(type.equals("bus")) {
			rVO.setKeyword(keyword);
			int total = sDao.getBusTotal(keyword);
			page.setTotalCount(total);
			page.setNowPage(nowPage);
			page.setPage(page.getNowPage(), total, 200, 5);
			rVO.setPage(page);
			list = sDao.getBusRellist(rVO);
			for(int i = 0; i < list.size(); i++) {
				((RouteVO) list.get(i)).setPage(page);
			}
		} else if(type.equals("station")) {
			sVO.setKeyword(keyword);
			int total = sDao.getStaTotal(keyword);
			page.setTotalCount(total);
			page.setNowPage(nowPage);
			page.setPage(page.getNowPage(), total, 200, 5);
			sVO.setPage(page);
			list = sDao.getStaRelList(sVO);
			for(int i = 0; i < list.size(); i++) {
				((StationVO) list.get(i)).setPage(page);
			}			
		}
		
		return list;
	}
	
	@ResponseBody
	@RequestMapping("/mapStationDetail.clc")
	public List<StationVO> mapStationDetail(@RequestBody HashMap<String, Integer> map) {
		int station_id = map.get("station_id");
		List<StationVO> list = sDao.stationDetail(station_id);
//		StationVO sVO = list.get(0);
		
		return list;
	}
	
	@ResponseBody
	@RequestMapping("/mapAroundStation.clc")
	public List<StationVO> mapAroundStation(@RequestBody HashMap<String, Double> map) {
		// 중심좌표 정보 보내고 주변 정류소 리스트 받는다.
		List<StationVO> list = sDao.getMapArroundStation(map);
		
		return list;
	}
	
	@ResponseBody
	@RequestMapping("/mapRouteDetail.clc")
	public List<RouteVO> mapRouteDetail(@RequestBody HashMap<String, Integer> map, RouteVO rVO){
		rVO.setDistrict_cd(map.get("district_cd"));
		rVO.setRoute_id(map.get("route_id"));
		
		List<RouteVO> list = sDao.busDetail(rVO);
		
		return list;
	}
	
}
