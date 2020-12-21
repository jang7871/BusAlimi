package com.luxurycity.clc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.luxurycity.clc.dao.MemberDao;
import com.luxurycity.clc.dao.SearchDao;
import com.luxurycity.clc.vo.StationVO;

import java.util.*;
public class MakeRouteOption {
	@Autowired
	SearchDao sDao;
	
	String start_nm, end_nm;
	ArrayList<StationVO> option;
	public MakeRouteOption(ModelAndView mv, StationVO sVO) {
		System.out.println(sVO.getStart_id() + "#################");
		ArrayList<StationVO> ssvo = getStartList(sVO);
		ArrayList<ArrayList<StationVO>> start = getStartListRoute(ssvo);
		ArrayList<Integer> stotal = getStartListRouteTotal(ssvo);
		ArrayList<StationVO> esvo = getEndList(sVO);
		ArrayList<ArrayList<StationVO>> end = getEndListRoute(esvo);
		ArrayList<Integer> etotal = getEndListRouteTotal(esvo);
		try {
			option = getRouteOption(start, end, stotal, etotal);			
		}catch(Exception e) {
			option.add(sVO);
		}
		
	}
	
	public ArrayList<StationVO> getOption() {
		return option;
	}


	//출발지 경유 버스리스트를 담고있는 배열 반환해주는 함수
	public ArrayList<StationVO> getStartList(StationVO sVO){
		ArrayList<StationVO> ssvo = new ArrayList<StationVO>();
		ssvo = (ArrayList<StationVO>) sDao.getStartList(sVO);
		this.start_nm = ssvo.get(0).getStart_nm();
		return ssvo;
	}
	//도착지 경유 버스리스트를 담고있는 배열 반환해주는 함수
	public ArrayList<StationVO> getEndList(StationVO sVO){
		ArrayList<StationVO> esvo = new ArrayList<StationVO>(sDao.getEndList(sVO));
		this.end_nm = esvo.get(0).getEnd_nm();
		return esvo;
	}
	//출발지 경유 버스별 경로정보를 가지는 이중배열 반환해주는 함수
	public ArrayList<ArrayList<StationVO>> getStartListRoute(ArrayList<StationVO> ssvo){
		ArrayList<ArrayList<StationVO>> saarr = new ArrayList<ArrayList<StationVO>>();
		for(StationVO sVO: ssvo) {
			ArrayList<StationVO> info = new ArrayList<StationVO>(sDao.getStartListRoute(sVO));
			saarr.add(info);
		}
		return saarr;
	}
	//출발지 경유 버스별 경로정보 토탈 배열을 반환해주는 함수
	public ArrayList<Integer> getStartListRouteTotal(ArrayList<StationVO> ssvo){
		ArrayList<Integer> stotal = new ArrayList<Integer>();
		for(StationVO sVO: ssvo) {
			int total = sDao.getStartListRouteTotal(sVO).getCnt();
			stotal.add(total);
		}
		return stotal;
	}
	//도착지 경유 버스별 경로정보를 가지는 이중배열 반환해주는 함수
	public ArrayList<ArrayList<StationVO>> getEndListRoute(ArrayList<StationVO> esvo){
		ArrayList<ArrayList<StationVO>> eaarr = new ArrayList<ArrayList<StationVO>>();
		for(StationVO sVO: esvo) {
			ArrayList<StationVO> info = new ArrayList<StationVO>(sDao.getEndListRoute(sVO));
			eaarr.add(info);
		}
		return eaarr;
	}
	//출발지 경유 버스별 경로정보 토탈 배열을 반환해주는 함수
	public ArrayList<Integer> getEndListRouteTotal(ArrayList<StationVO> esvo){
		ArrayList<Integer> etotal = new ArrayList<Integer>();
		for(StationVO sVO: esvo) {
			int total = sDao.getEndListRouteTotal(sVO).getCnt();
			etotal.add(total);
		}
		return etotal;
	}
	//루트를 반환해주는 함수
	public ArrayList<StationVO> getRouteOption(ArrayList<ArrayList<StationVO>> start, ArrayList<ArrayList<StationVO>> end
			, ArrayList<Integer> stotal, ArrayList<Integer> etotal){
		ArrayList<StationVO> option = new ArrayList<StationVO>();
		//환승이 없는경우
		for(int i = 0 ; i < start.size(); i++) {
			for(int j = 0 ; j < end.size(); j++) {
				if(start.get(i).get(0).getRoute_id() == end.get(j).get(0).getRoute_id()) {
					StationVO svo = new StationVO();
					svo.setStart_nm(start_nm);
					svo.setEnd_nm(end_nm);
					svo.setRoute_nm(start.get(i).get(0).getRoute_nm());
					option.add(svo);
				}
			}
		}
		//한번 환승하는 경우
		for(int i = 0 ; i < start.size(); i++) {
			for(int j = 0 ; j < end.size(); j++) {
				for(int x = 0 ; x < stotal.get(i); x++) {
					for(int y = 0; y < etotal.get(j); y++) {
						if(start.get(i).get(x).getStation_id() == end.get(j).get(y).getStation_id()) {
							StationVO svo = new StationVO();
							svo.setStart_nm(start_nm);
							svo.setEnd_nm(end_nm);
							String[] route_list = new String[2];
							route_list[0] = start.get(i).get(x).getRoute_nm();
							route_list[1] = end.get(j).get(y).getRoute_nm();
							svo.setRoute_list(route_list);
							String transfer_nm = sDao.getTransfernm(start.get(i).get(x).getStation_id());
							svo.setTransfer_nm(transfer_nm);
							option.add(svo);
						}
					}
				}
			}
		}
		return option;
	}
}
