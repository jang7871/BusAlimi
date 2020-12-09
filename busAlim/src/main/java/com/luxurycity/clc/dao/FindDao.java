package com.luxurycity.clc.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.luxurycity.clc.vo.*;
import java.util.*;

public class FindDao {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	// 메일과 일치하는 아이디 갯수 전담 처리 함수
	public int idCount(String mail) {
		return sqlSession.selectOne("fSQL.idCount", mail);
	}
	
	// 아이디 찾기 전담 처리 함수
	public String findId(String mail) {
		return sqlSession.selectOne("fSQL.findId", mail);
	}
	
	// 비밀번호 질문 리스트 가져오기 전담 처리 함수
	public List<FindVO> getQuestList(){
		return sqlSession.selectList("fSQL.questList");
	}
	
	// 비밀번호 찾기 전담 처리 함수
	public int findPw(FindVO fVO) {
		return sqlSession.selectOne("fSQL.findPw", fVO);
	}
	
	// 비밀번호 변경 전담 처리 함수
	public int editPw(FindVO fVO) {
		return sqlSession.update("fSQL.editPw", fVO);
	}
}
