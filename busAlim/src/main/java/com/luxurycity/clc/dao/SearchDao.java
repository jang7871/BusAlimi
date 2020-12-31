package com.luxurycity.clc.dao;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import com.luxurycity.clc.vo.*;
import org.springframework.transaction.annotation.Transactional;

public class SearchDao {
	@Autowired
	SqlSessionTemplate sqlSession;
	// 버스 검색 결과 총 갯수 전담 처리 함수
	public int getBusTotal(String keyword) {
		return sqlSession.selectOne("sSQL.busRelTotal", keyword);
	}
	// 버스 연관데이터(모달창에 띄우는 것) 가져오기 전담 처리 함수
	public List<RouteVO> getBusRellist(RouteVO rVO){
		return sqlSession.selectList("sSQL.busRelList", rVO);
	}
	// 정류소 검색 결과 총 갯수 전담 처리 함수
	public int getStaTotal(String keyword) {
		return sqlSession.selectOne("sSQL.staRelTotal", keyword);
	}
	// 정류소 연관데이터(모달창에 띄우는 것) 가져오기 전담 처리 함수
	public List<StationVO> getStaRelList(StationVO sVO) {
		return sqlSession.selectList("sSQL.staRelList", sVO);
	}
	// 버스 연관검색어 가져오기 전담 처리 함수
	public List<RouteVO> getBusKeyList(String keyword){
		return sqlSession.selectList("sSQL.busKeyList", keyword);
	}
	// 정류소 연관검색어 가져오기 전담 처리 함수
	public List<StationVO> getStakeyList(String keyword){
		return sqlSession.selectList("sSQL.staKeyList", keyword);
	}
	//서울정류소 해당 정류소 도착 버스및 ord정보 가져오기
	public List<StationVO> getSeoulArrive(int station_id) {
		return sqlSession.selectList("sSQL.getseoularrive", station_id);
	}
	//정류소 상세페이지 데이터 꺼내오기
	public List<StationVO> stationDetail(int station_id) {
		return sqlSession.selectList("sSQL.stationDetail", station_id);
	}
	//정류소 지역 꺼내오기
	public int getDistrict(int station_id) {
		return sqlSession.selectOne("sSQL.getdistrict", station_id);
	}
	//버스 상세페이지 데이터 꺼내오기
	public List<RouteVO> busDetail(RouteVO rVO) {
		return sqlSession.selectList("sSQL.busDetail", rVO);
	}
	// 버스 해당 즐겨찾기 찾는 전담 처리 함수
	public List<BookmarkVO> getBusBookmark(BookmarkVO bmVO) {
		return sqlSession.selectList("sSQL.busBookmark", bmVO);
	}
	// 정류소 해당 즐겨찾기 찾는 전담 처리 함수
	public List<BookmarkVO> getStaBookmark(BookmarkVO bmVO) {
		return sqlSession.selectList("sSQL.staBookmark", bmVO);
	}
	// 버스 + 정류소 해당 즐겨찾기 찾는 전담 처리 함수
	public List<BookmarkVO> getBusStaBookmark(BookmarkVO bmVO) {
		return sqlSession.selectList("sSQL.busStaBookmark", bmVO);
	}
	// 길찾기 전담 처리 함수
	public List<StationVO> getBusOption(StationVO sVO) {
		return sqlSession.selectList("sSQL.searchbusoption", sVO);
	}
	// 길찾기 전담 처리 함수
	public StationVO getSearchInfo(StationVO sVO) {
		return sqlSession.selectOne("sSQL.searchrouteinfo", sVO);
	}
	// 출발지 경유 버스 리스트가져오기

	public List<StationVO> getStartList(int start_id) {
		
		return sqlSession.selectList("sSQL.getstartlist", start_id);

	}
	// 출발지 경유 버스 별 경로정보 가져오기
	public List<StationVO> getStartListRoute(StationVO sVO) {
		return sqlSession.selectList("sSQL.getstartlistroute", sVO);
	}
	// 도착지 경유 버스 리스트 가져오기

	public List<StationVO> getEndList(int end_id) {
		return sqlSession.selectList("sSQL.getendlist", end_id);
	}
	// 도착지 경유 버스 별 경로정보 가져오기
	public List<StationVO> getEndListRoute(StationVO sVO) {
		return sqlSession.selectList("sSQL.getendlistroute", sVO);
	}
	// 출발지 경유 버스 별 경로정보 가져오기
	public StationVO getStartListRouteTotal(StationVO sVO) {
		return sqlSession.selectOne("sSQL.getstartlistroutetotal", sVO);
	}
	// 도착지 경유 버스 별 경로정보 토탈가져오기
	public StationVO getEndListRouteTotal(StationVO sVO) {
		return sqlSession.selectOne("sSQL.getendlistroutetotal", sVO);
	}
	// 도착지 경유 버스 별 경로정보 토탈가져오기
	public String getTransfernm(int station_id) {
		return sqlSession.selectOne("sSQL.gettransfernm", station_id);
	}

	// 친구 정보 가져오기
	public List<MemberVO> getFriendList(HashMap<String, String> map) {
		return sqlSession.selectList("sSQL.friendKeyList", map);
	}
	// 중심좌표 기준 근접 정류소 리스트 가져오는 전담 처리 함수
	public List<StationVO> getMapArroundStation(HashMap<String, Double> map) {
		return sqlSession.selectList("sSQL.getMapArroundStation", map);
	}
	public List<Integer> getStartId(StationVO sVO) {
		return sqlSession.selectList("sSQL.getstart_id", sVO);
	}
	public List<Integer> getEndId(StationVO sVO) {
		return sqlSession.selectList("sSQL.getend_id", sVO);

	}
}
