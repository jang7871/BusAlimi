package com.luxurycity.clc.dao;

import java.util.*;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.luxurycity.clc.vo.*;

public class SearchDao {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	//정류소 상세페이지 데이터 꺼내오기(지우)
	public List<StationVO> stationDetail(int station_id) {
		return sqlSession.selectList("sSQL.stationDetail", station_id);
	}
	//버스 상세페이지 데이터 꺼내오기(지우)
	public List<RouteVO> busDetail(int route_id) {
		return sqlSession.selectList("sSQL.busDetail", route_id);
	}
	
}
