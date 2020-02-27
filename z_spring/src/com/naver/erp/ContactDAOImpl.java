/**
 * 아래에 나오는 클래스의 소속 패키지 경로를 설정하기
 * 모든 자바 클래스 최상단에는 소속 패키지 경로가 먼저 나옴
 */
package com.naver.erp;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ContactDAOImpl 클래스
 * DAO 클래스
 * @Repository를 붙임으로써 DAO 클래스임을 지정하게되고,
 * bean 태그로 자동 등록됨
 * @author Jo
 */
@Repository
public class ContactDAOImpl implements ContactDAO{
	/**
	 * 속성변수 선언
	 */
	@Autowired
	private SqlSessionTemplate sqlSession;	// SqlSessionTemplate 객체를 생성하고 저장

	/**
	 * 메소드 선언
	 */
	/**
	 * 연락처 삽입하기 위한 메소드
	 * @param contactDTO : ContactDTO
	 * @return
	 */
	@Override
	public int insertContact(ContactDTO contactDTO) {
		int insertContact = this.sqlSession.insert(
				sqlSessionPath + "insertContact"
					, contactDTO
				);
				
		System.out.println("DAO insertContact : " + insertContact);
		return insertContact;
	}

	/**
	 * 사업분야 삽입하기 위한 메소드
	 * @param contactDTO : ContactDTO
	 * @return
	 */
	@Override
	public int insertContactBusinessArea(String bac) {
		int insertContactBusinessArea = this.sqlSession.insert(
				sqlSessionPath + "insertContactBusinessArea"
				, bac
			);
		System.out.println("DAO insertContactBusinessArea : " + insertContactBusinessArea);
		return insertContactBusinessArea;
	}
}