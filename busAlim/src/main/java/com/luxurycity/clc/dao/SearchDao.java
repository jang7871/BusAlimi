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
	//정류소 상세페이지 데이터 꺼내오기
	public List<StationVO> stationDetail(int station_id) {
		return sqlSession.selectList("sSQL.stationDetail", station_id);
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
}
