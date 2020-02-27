/**
 * 아래에 나오는 클래스의 소속 패키지 경로를 설정하기
 * 모든 자바 클래스 최상단에는 소속 패키지 경로가 먼저 나옴
 */
/**
 * 
 */
package com.naver.erp;

import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * LoginDAOImple 클래스
 * DAO 클래스
 * @Repository를 붙임으로써 DAO 클래스임을 지정하게되고,
 * bean 태그로 자동 등록됨
 * @author Jo
 */
@Repository
public class LoginDAOImpl implements LoginDAO {
	/**
	 * 속성변수 선언
	 */
	@Autowired
	private SqlSessionTemplate sqlSession;	// SqlSessionTemplate 클래스를 객체화하여 저장
	
	/**
	 * 메소드 선언
	 */
	/**
	 * 로그인 아이디, 암호의 존재 개수를 리턴하는 메소드
	 * @return : 로그인 존재 개수를 리턴
	 */
	@Override
	public int getAdminCnt(Map<String, String> admin_id_pwd) {
		/**
		 * SqlSessionTemplate 객체의 selectOne 메소드를 호출하여
		 * 검색 SQL 구문을 실행하여 1행 n열의 데이터를 얻음
		 */
		int adminCnt = this.sqlSession.selectOne(
				sqlSessionPath + "getAdminCnt"
				// xml 파일 중에 <mapper namespace=com.naver.erp.LoginDAO>가 있는
				// mapper 태그의 내부에 id=getAdminCnt를 가진 태그 내부의 SQL 구문을 실행
				, admin_id_pwd
				// SQL 구문을 실행할 때 참여할 데이터가 저장된 HashMap 객체
				);
		
		return adminCnt;
	}
}