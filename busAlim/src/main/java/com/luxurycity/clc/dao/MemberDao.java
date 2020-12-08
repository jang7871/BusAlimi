package com.luxurycity.clc.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.luxurycity.clc.vo.*;

public class MemberDao {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	// 로그인 검사 전담 처리 함수(영선)
	public int loginCnt(MemberVO mVO) {
		return sqlSession.selectOne("mSQL.loginCnt", mVO);
	}
	
	// 메인에 표시되는 아바타 데이터 가져오기 전담 처리 함수(영선)
	public AvatarVO getAvt(String id) {
		return sqlSession.selectOne("mSQL.getMembAvt", id);
	}
}
