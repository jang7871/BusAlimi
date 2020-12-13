package com.luxurycity.clc.dao;

import java.util.*;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.luxurycity.clc.vo.*;
import com.luxurycity.clc.util.*;

public class BoardDao {
	@Autowired
	SqlSessionTemplate sqlSession;
	//보드 카운트 페이지 토탈 뽑기 
	public int boardCnt() {
		return sqlSession.selectOne("bSQL.boardCnt");
	}
	
	//보드 리스트 뽑기 
	public List<BoardVO> boardList(PageUtil page) {
		return sqlSession.selectList("bSQL.boardList", page);
	}
	//보드 추가하기 - 지우
	public int addBoard(BoardVO bVO) {
		return sqlSession.insert("bSQL.addBoard", bVO);
	}
	//보드 삭제하기 - 지우
	public int delBoard(int bno) {
		return sqlSession.update("bSQL.delBoard", bno);
	}
}
