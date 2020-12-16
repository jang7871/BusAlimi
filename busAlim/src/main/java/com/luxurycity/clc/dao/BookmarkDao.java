package com.luxurycity.clc.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import com.luxurycity.clc.vo.*;

public class BookmarkDao {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	// 정류소 즐겨찾기 정보 가져오는 전담처리 함수
	public List<BookmarkVO> getStationList(BookmarkVO bmVO) {
		return sqlSession.selectList("bmSQL.stationList", bmVO);
	}
	
	// 버스 즐겨찾기 정보 가져오는 전담처리 함수
	public List<BookmarkVO> getBusList(BookmarkVO bmVO) {
		return sqlSession.selectList("bmSQL.busList", bmVO);
	}
	
	// 즐겨찾기 갯수(페이징 처리용) 가져오는 전담처리 함수
	public int getTotal(String id) {
		return sqlSession.selectOne("bmSQL.totalCnt", id);
	}
	
	// 즐겨찾기 삭제 전담 처리 함수
	public int delBookmark(int[] bmno) {
		int cnt = 0;
		for(int i = 0; i < bmno.length; i++) {
			cnt += sqlSession.update("bmSQL.delBookmark", bmno[i]);
		}
		return cnt;
	}
	// 즐겨찾기 삭제 전담 처리 함수(하나만)
	public int delBookmark(int bmno) {
		return sqlSession.update("bmSQL.delBookmark", bmno);
	}

	// 즐겨찾기 추가 전담 처리 함수
	public int bookAdd(HashMap map) {
		int cnt = 0;
		cnt = sqlSession.insert("bmSQL.bookAdd", map);
		return cnt;
	}
}
