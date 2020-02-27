/**
 * 아래에 나오는 클래스의 소속 패키지 경로를 설정하기
 * 모든 자바 클래스 최상단에는 소속 패키지 경로가 먼저 나옴
 */
package com.naver.erp;

/**
 * ContactDAO 인터페이스
 * DAO 클래스를 사용하기 위해 정의한 인터페이스
 * @author Jo
 */
public interface ContactDAO {
	/**
	 * 속성변수 선언
	 */
	String sqlSessionPath = "com.naver.erp.ContactDAO.";
	/**
	 * 메소드 선언
	 */
	int insertContact(ContactDTO contactDTO);	// 연락처 삽입하기 위한 메소드
	int insertContactBusinessArea(String contactDTO);	// 사업분야 삽입하기 위한 메소드
}