package com.luxurycity.clc.dao;

import java.util.*;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
	// 조인아이디체크(지우)
	public int idCheck(String id) {
		return sqlSession.selectOne("mSQL.idCheck", id);
	}
	// 조인아바타(지우)
	public List<AvatarVO> getAvtList() {
		return sqlSession.selectList("mSQL.getAvt");
	}
	// 조인질문(지우)
	public List<FindVO> getQuest() {
		return sqlSession.selectList("mSQL.getQuest");
	}
	// 조인proc_member add(지우)
	@Transactional
	public int addMemb(MemberVO mVO, FindVO fVO) {
		int cnt = 0;
		//멤버 add할떄 find도 add해야하므로 트랜잭션 처리함(동시 조인할 경우 꼬임 방지)
		cnt += sqlSession.insert("mSQL.addMemb", mVO);
		cnt += addFind(fVO);
		
		return cnt;
	}
	// 조인proc_find add(지우)
	public int addFind(FindVO fVO) {
		return sqlSession.insert("mSQL.addFind", fVO);
	}
}
