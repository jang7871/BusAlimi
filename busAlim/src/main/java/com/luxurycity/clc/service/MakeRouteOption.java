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
	StationVO ssVO;
	String start_nm, end_nm;
	public MakeRouteOption(){
		
	}
	public ArrayList<StationVO> MakeRouteOptions(StationVO sVO) {
		//이름이 같은 모든 정류소id를 가져와줌(출발)
		ArrayList<StationVO> option = new ArrayList<StationVO>();
		ArrayList<Integer> startidlist = new ArrayList<Integer>(sDao.getStartId(sVO));
		
		//이름이 같은 모든 정류소id를 가져와줌(도착)
		ArrayList<Integer> endidlist = new ArrayList<Integer>(sDao.getEndId(sVO));
		System.out.println(startidlist.size() +"   #############   " + endidlist.size());
		for(Integer startnum: startidlist) {
			for(Integer endnum: endidlist) { // 공유하는 정류소이름을 모두 돌려주어 모든 경우의수를 실행하기위한 반복문..
				
				try {
				ArrayList<StationVO> ssvo = getStartList(startnum);
				ArrayList<ArrayList<StationVO>> start = getStartListRoute(ssvo);
				ArrayList<Integer> stotal = getStartListRouteTotal(ssvo);
				
				ArrayList<StationVO> esvo = getEndList(endnum);
				ArrayList<ArrayList<StationVO>> end = getEndListRoute(esvo);
				ArrayList<Integer> etotal = getEndListRouteTotal(esvo);
				
					ArrayList<StationVO> tmp = getRouteOption(start, end, stotal, etotal);
					System.out.println(tmp.size());
					option.addAll(tmp);				
				}catch(Exception e) {
					System.out.println("어디선가 널인경우인거 같아.." + startnum + "||||" + endnum);
				}
			}
		}
		
		for(int i = 0 ; i < option.size(); i++) {
			if(option.get(i).getStr_order_cnt()<=0) {
				option.remove(i);
			}
		}
		ArrayList<StationVO> finaloption;
		try {
			//중복을 제거 해줌 - 왜냐하면 한버스가 두개이상 나오는 경우가 있음.
			finaloption = getFilter(option);
			finaloption = getFilter2(finaloption);
			return finaloption;
			 
		}catch(Exception e) {
			System.out.println("option이 null인경우");
			return null;
		}
	}
	//검색결과 필터 함수
	public ArrayList<StationVO> getFilter(ArrayList<StationVO> option){
		ArrayList<StationVO> tmp1 = new ArrayList<StationVO>();
		ArrayList<StationVO> tmp2 = new ArrayList<StationVO>();
		for(StationVO sVO: option) {
			if(sVO.getTransfer_nm() != null) {
				
				sVO.setStr_order_cnt(sVO.getStr_order_cnt() + 2);				
			}
//			try {
//				String nm = sVO.getTransfer_nm();
//				
//			}catch(Exception e) {
//				
//			}
			tmp1.add(sVO);
		}
		tmp1.sort(new Comparator<StationVO>() {

			@Override
			public int compare(StationVO v1, StationVO v2) {

				int v1cnt = v1.getStr_order_cnt();
				int v2cnt = v2.getStr_order_cnt();
				return Integer.compare(v1cnt,  v2cnt); 
			}
			
		});
		int tmp3 = tmp1.size() > 10 ? 10 : tmp1.size();
		for(int i = 0 ; i< tmp3; i++) {
			tmp2.add(tmp1.get(i));
		}
		return tmp2;
	}
	//검색결과 필터 함수2
	public ArrayList<StationVO> getFilter2(ArrayList<StationVO> option){
		ArrayList<StationVO> tmp1 = new ArrayList<StationVO>();
		String tstr1 = "";
		String tstr2 = "";
		loop:
		for(StationVO sVO: option) {
			if(sVO.getTransfer_nm() != null) {
				if(tstr1.contentEquals(sVO.getRoute_list()[0]) &&  tstr2.equals(sVO.getRoute_list()[1])) {
					continue loop;
				}
				
				tstr1 = sVO.getRoute_list()[0];
				tstr2 = sVO.getRoute_list()[1];
						
			}
			
			tmp1.add(sVO);
		}
		return tmp1;
	}
	//출발지 경유 버스리스트를 담고있는 배열 반환해주는 함수
	public ArrayList<StationVO> getStartList(int startnum){
		ArrayList<StationVO> ssvo = new ArrayList<StationVO>();
		try {
			ssvo = (ArrayList<StationVO>) sDao.getStartList(startnum);
			this.start_nm = ssvo.get(0).getStart_nm();
			
		}catch(Exception e) {
			System.out.println("ssvo가 널");
		}
//		this.start_nm = ssvo.get(0).getStart_nm();
		return ssvo;
	}
	//도착지 경유 버스리스트를 담고있는 배열 반환해주는 함수
	public ArrayList<StationVO> getEndList(int endnum){
		ArrayList<StationVO> esvo = new ArrayList<StationVO>();
		try {
			esvo = (ArrayList<StationVO>) sDao.getEndList(endnum);
			this.end_nm = esvo.get(0).getEnd_nm();
			
		}catch(Exception e) {
			System.out.println("esvo가 널");
		}

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

		System.out.println("!@#%" + etotal.toString());

		return etotal;
	}
	//루트를 반환해주는 함수
	public ArrayList<StationVO> getRouteOption(ArrayList<ArrayList<StationVO>> start, ArrayList<ArrayList<StationVO>> end
			, ArrayList<Integer> stotal, ArrayList<Integer> etotal){

		ArrayList<StationVO> tmp = new ArrayList<StationVO>();
		//환승이 없는경우
		for(int i = 0 ; i < start.size(); i++) {
			for(int j = 0 ; j < end.size(); j++) {
//				System.out.println("여기가 진짜 중요하다 지우야    " + start.get(i).get(0).getRoute_nm() + "###########" + end.get(j).get(0).getRoute_nm() );
				if(start.get(i).get(0).getRoute_id() == end.get(j).get(0).getRoute_id()) {
					System.out.println("#######하번에 가는 버스 이름" + start.get(i).get(0).getRoute_nm());
					StationVO svo = new StationVO();
					int tmpcnt = end.get(j).get(0).getStr_order() - start.get(i).get(0).getStr_order() + 2; 
					svo.setStr_order_cnt(tmpcnt);
					svo.setStart_nm(start_nm);
					svo.setEnd_nm(end_nm);
					svo.setRoute_nm(start.get(i).get(0).getRoute_nm());
					tmp.add(svo);
				}
				//1번환승 솔루션
				for(int x = 0 ; x < stotal.get(i); x++) {
					loop:
						for(int y = 0; y < etotal.get(j); y++) {
							if(start.get(i).get(x).getStation_id() == end.get(j).get(y).getStation_id()) {
								StationVO svo = new StationVO();
								svo.setStart_nm(start_nm);
								svo.setEnd_nm(end_nm);
								String[] route_list = new String[2];
								route_list[0] = start.get(i).get(x).getRoute_nm();
								route_list[1] = end.get(j).get(y).getRoute_nm();
								//출발에서 환승까지 걸리는 정거장수
								int tmp1 = x+1;
								int tmp2 = y+1;
								svo.setStr_order_cnt(tmp1 + tmp2);
								if(route_list[0].equals(route_list[1])) {
									continue loop;
								}else {
									svo.setRoute_list(route_list);
									String transfer_nm = sDao.getTransfernm(start.get(i).get(x).getStation_id());
									svo.setTransfer_nm(transfer_nm);
									tmp.add(svo);								
								}
							}
						}
				}
			}
		}
		return tmp;

	}
}
