package com.luxurycity.clc.service;

import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.luxurycity.clc.dao.*;
import com.luxurycity.clc.vo.*;

//버스 도착정보 api 첫번쨰 꺼 map으로 반환해주는 클래스
public class GetArrInfoByRouteAllList {
	@Autowired
	SearchDao sDao;
	//xml 요청 url 만드는 함수
	public ArrayList<HashMap> GetArrInfoByRouteAllList(int stationId, List<StationVO> llist) {
		
		ArrayList<HashMap> nodeinfo  = new ArrayList<HashMap>();
		ArrayList<HashMap> seoulinfo  = new ArrayList<HashMap>();
		try {
			nodeinfo = GetArrInfoByRouteGList(stationId);			
			ArrayList<StationVO> list = new ArrayList<StationVO>(llist);
			seoulinfo = GetArrInfoByRouteSList(list);
			nodeinfo.addAll(seoulinfo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return nodeinfo;
	}
	//경기도 실시간 도착정보 데이터 반환해주는 함수
	public ArrayList<HashMap> GetArrInfoByRouteGList(int stationId) {
		
		ArrayList<HashMap> nodeinfo  = new ArrayList<HashMap>();
		ArrayList<String> keyarr = getKeyArr(); //태그 이름들 모아놈
		try {
			String url = makeURL(stationId);
			// 여긴 그냥 만들어주는 객체및 함수임 필요할때 복붙하면 됨
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			
			doc.getDocumentElement().normalize();
			System.out.println("Root Element : "+doc.getDocumentElement().getNodeName());
			
			//파싱할 태그이름 선언.. 아래 for문은 선언되는 태그 자식들을 뽑는거
			NodeList nodeList = doc.getElementsByTagName("busArrivalList"); // 파싱할 태그 선택
			System.out.println("파싱할 리스트 수 : "+ nodeList.getLength()); // 몇번 나와야하는지 출력
			
			for(int tmp =0; tmp<nodeList.getLength(); tmp++) {
				HashMap<String, Object> map = new HashMap();
				Node nNode = nodeList.item(tmp);
				String routeid = "";
				if(nNode.getNodeType()==Node.ELEMENT_NODE) {
					
					Element element = (Element) nNode;
					
					for(int i = 0 ; i < keyarr.size(); i++) {
						map.put(keyarr.get(i), getTagValue(keyarr.get(i), element));
						routeid = getTagValue("routeId", element);
					}//map하나 완성하고
					
					nodeinfo.add(map);
				}
			}//for
		}catch(Exception e) {
			
		}
		return nodeinfo;
	}
	//서울 실시간 도착정보 반환해주는 함수
	public ArrayList<HashMap> GetArrInfoByRouteSList(ArrayList<StationVO> slist) {
		
		ArrayList<HashMap> nodeinfo  = new ArrayList<HashMap>();
		ArrayList<String> keyarr = getSeoulKeyArr(); //태그 이름들 모아놈
		try {
			for(StationVO sVO : slist) {
				
				String url = makeSeoulURL(sVO.getStation_id(), sVO.getRoute_id(), sVO.getStr_order());
				// 여긴 그냥 만들어주는 객체및 함수임 필요할때 복붙하면 됨
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(url);
				
				doc.getDocumentElement().normalize();
				System.out.println("Root Element : "+doc.getDocumentElement().getNodeName());
				
				//파싱할 태그이름 선언.. 아래 for문은 선언되는 태그 자식들을 뽑는거
				NodeList nodeList = doc.getElementsByTagName("itemList"); // 파싱할 태그 선택
				for(int tmp =0; tmp<nodeList.getLength(); tmp++) {
					HashMap<String, Object> map = new HashMap();
					Node nNode = nodeList.item(tmp);
					if(nNode.getNodeType()==Node.ELEMENT_NODE) {
						
						Element element = (Element) nNode;
						
						for(int i = 0 ; i < keyarr.size(); i++) {
							map.put(keyarr.get(i), getTagValue(keyarr.get(i), element));
						}//map하나 완성하고
						
						nodeinfo.add(map);
					}
				}//for
			}
		}catch(Exception e) {
			
		}
		return nodeinfo;
	}
	//키 만들어주는 함수  노가다밖에 생각이 안나서 노가다함
	public ArrayList<String> getKeyArr(){
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("flag");
		arr.add("locationNo1");
		arr.add("locationNo2");
		arr.add("lowPlate1");
		arr.add("lowPlate2");
		arr.add("plateNo1");
		arr.add("plateNo2");
		arr.add("predictTime1");
		arr.add("predictTime2");
		arr.add("remainSeatCnt1");
		arr.add("remainSeatCnt2");
		arr.add("routeId");
		arr.add("staOrder");
		arr.add("stationId");
		return arr;
	}
	//서울키 만들어주는 함수  노가다밖에 생각이 안나서 노가다함
	public ArrayList<String> getSeoulKeyArr(){
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("arrmsg1");
		arr.add("arrmsg2");
		arr.add("busRouteId");
		arr.add("busType1");
		arr.add("busType2");
		arr.add("reride_Num1");
		arr.add("reride_Num2");
		return arr;
	}
	private static String getTagValue(String tag, Element ele) {

        NodeList nodeList = ele.getElementsByTagName(tag).item(0).getChildNodes();

        Node nValue = (Node) nodeList.item(0);

        if(nValue == null) {

            return null;

        }

        return nValue.getNodeValue();

    }
	//경기 도착정보 요청 메세지
	public String makeURL(int stationId) {
		String key = "Yw3zPCyzMoX2VB0yMPfZgip2qIHGaFLGT5RuJ9gtVFGvzjbuNNZa5qB5DFUm%2BNMe%2B0kHhUWAYIH1j0BK%2Fdj6MQ%3D%3D";
		String url = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice/station";
		
		url += "?serviceKey=" + key + "&stationId=" + stationId;
		
		return url;
	}
	//서울 도착정보 요청 메세지
	public String makeSeoulURL(int stationId, int route_id, int str_order) {
		String key = "Yw3zPCyzMoX2VB0yMPfZgip2qIHGaFLGT5RuJ9gtVFGvzjbuNNZa5qB5DFUm%2BNMe%2B0kHhUWAYIH1j0BK%2Fdj6MQ%3D%3D";
		String url = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRoute";
		
		url += "?ServiceKey=" + key + "&stId=" + stationId + "&busRouteId=" + route_id + "&ord=" + str_order;
		
		return url;
	}
	/*
	public void routeDetail(ModelAndView mv, RedirectView rv, int stationId) {
		String key = "Yw3zPCyzMoX2VB0yMPfZgip2qIHGaFLGT5RuJ9gtVFGvzjbuNNZa5qB5DFUm%2BNMe%2B0kHhUWAYIH1j0BK%2Fdj6MQ%3D%3D";
		String url = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice/station";
		rv.setUrl(url + "?serviceKey=" + key + "&stationId=" + stationId);
		mv.addObject("RID", routeId);
		mv.addObject("KEY", key);
		mv.addObject("URL", url);
		mv.setViewName("search/redirect");
		mv.setView(rv);
		return;
	}
	*/
}
