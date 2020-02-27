/**
 * 아래에 나오는 클래스의 소속 패키지 경로를 설정하기
 * 모든 자바 클래스 최상단에는 소속 패키지 경로가 먼저 나옴
 */
/**
 * 
 */
package com.naver.erp;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * LoginServiceImpl 클래스
 * 서비스 클래스
 * 서비스 클래스에는 @Service와 @Transactional를 붙임
 * @Service : 서비스 클래스 임을 지정하고 bean 태그로 자동 등록됨
 * @Transactional : 서비스 클래스의 메소드 내부에서 일어나는 모든 작업에는 트랜잭션이 걸림
 * @author Jo
 */
@Service
public class LoginServiceImpl implements LoginService {
	/**
	 * 속성변수 선언
	 */
	/**
	 * @Autowired이 붙은 속성변수에는 인터페이스 자료형을 쓰고
	 * 이 인터페이스를 구현한 클래스를 객체화하여 저장
	 * 이때 LoginDAO 인터페이스를 구현한 클래스의 이름을 몰라도 관계없음
	 * 1개 존재하기만 하면 됨
	 */
	@Autowired
	private LoginDAO loginDAO;	// @Autowired에 의해 LoginDAO라는 인터페이스를 구현한 클래스를 객체화하여 저장
	
	/**
	 * 메소드 선언
	 */
	/**
	 * 로그인 정보의 개수를 리턴
	 * @return adminCnt : 아이디, 암호 존재 개수
	 */
	@Override
	public int getAdminCnt(Map<String, String> admin_id_pwd) {
		/**
		 * LoginDAOImpl 객체의 getAdminCnt 메소드를 호출하여 아이디, 암호의 존재개수 얻기
		 */
		int adminCnt = this.loginDAO.getAdminCnt(admin_id_pwd);
		return adminCnt;
	}
}