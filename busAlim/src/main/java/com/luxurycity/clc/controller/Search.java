package com.luxurycity.clc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@Autowired
	MakeRouteOption routeOption;
	
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
//		if(sVO.getDistrict_cd() == 0) {
//			int district_cd = sDao.getDistrict(station_id);
//			sVO.setDistrict_cd(district_cd);
//		}
//		int district = sVO.getDistrict_cd();
		List<StationVO> slist = sDao.stationDetail(station_id);
		//리스트 길이가 0이면 잘못된거니까 다시 메인으로 이동시킨다
		if(slist.size() == 0) {
			mv.setViewName("redirect:/main.clc");
		}else {
			mv.setViewName("search/StationDetail");
		}
		List<StationVO> llist = sDao.getSeoulArrive(station_id);
		ArrayList<HashMap> map = stationArrInfo(mv,station_id, llist);
		System.out.println(map.size()+"실시간 정보 데이터 가져온 수#####");
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

	
	@RequestMapping("/stationArrInfo.clc")
	@ResponseBody
	public ArrayList<HashMap> stationArrInfo(ModelAndView mv, int staid, List<StationVO> llist) {
		GetArrInfoByRouteAllList rlist = new GetArrInfoByRouteAllList();
		ArrayList<HashMap> map= rlist.GetArrInfoByRouteAllList(staid, llist);
		
		return map;
	}
	
	@RequestMapping("/searchrouteoption.clc")
	public ModelAndView searchRouteOption(ModelAndView mv, StationVO sVO) {
		System.out.println(sVO.toString());
		ArrayList<StationVO> option = routeOption.MakeRouteOptions(sVO);
		/*
		List<StationVO> list = sDao.getBusOption(sVO);
		ArrayList<StationVO> arr = new ArrayList<StationVO>();
		
		if(list.size() == 0) {
			mv.setViewName("redirect:/main.clc");
		}else {
		}
		for(StationVO VO: list) {
			System.out.println(VO.getStart_id() + "####################" + VO.getEnd_id() + "#########################" + VO.getRoute_id());
			StationVO tVO = sDao.getSearchInfo(VO);
			arr.add(tVO);
		}
		*/
		mv.setViewName("search/RouteOption");
		mv.addObject("LIST", option);
		return mv;
	}
	
			
			
	@RequestMapping("/busdetail.clc")
	public ModelAndView busDetail(ModelAndView mv, @ModelAttribute RouteVO rVO, HttpSession session) {
//		System.out.println(rVO.toString());
		// 1. 노선아이디 값을 가져온다.
		int route_id = rVO.getRoute_id();
		int district_cd = rVO.getDistrict_cd();
		System.out.println("####여기야여기" + rVO.toString());
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
		ArrayList<Integer> arrivelist = busArrInfo(mv, route_id, district_cd);
		System.out.println(arrivelist.size() + "||" + arrivelist.toString());
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
		mv.addObject("ARRIVE", arrivelist);
		mv.addObject("ROUTE", rlist);
		return mv;
	}
	@RequestMapping("/busArrInfo.clc")
	public ArrayList<Integer> busArrInfo(ModelAndView mv, int routeid, int district) {
		GetArrInfoByRouteList rlist = new GetArrInfoByRouteList();
		ArrayList<Integer> list= rlist.GetInfoByRouteList(routeid, district);
		
		return list;
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
	

	// 친구 이름 검색
	@ResponseBody
	@RequestMapping(path="/searchfriend.clc", method=RequestMethod.POST)
	public List<MemberVO> searchFriend(@RequestBody HashMap<String, String> map, HttpSession session) {
		String sid = (String) session.getAttribute("SID");
		if(sid == null || sid.length() == 0) {
			List<MemberVO> list = null;
			return list;
		}
		
		map.put("sid", sid);
		List<MemberVO> list = sDao.getFriendList(map);
		
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
