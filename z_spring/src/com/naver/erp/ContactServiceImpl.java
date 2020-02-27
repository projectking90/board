/**
 * 아래에 나오는 클래스의 소속 패키지 경로를 설정하기
 * 모든 자바 클래스 최상단에는 소속 패키지 경로가 먼저 나옴
 */
package com.naver.erp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ContactServiceImpl 클래스
 * 서비스 클래스
 * 서비스 클래스에는 @Service와 @Transactional를 붙임
 * @Service : 서비스 클래스 임을 지정하고 bean 태그로 자동 등록됨
 * @Transactional : 서비스 클래스의 메소드 내부에서 일어나는 모든 작업에는 트랜잭션이 걸림
 * @author Jo
 */
@Service
public class ContactServiceImpl implements ContactService {
	/**
	 * 속성변수 선언
	 */
	/**
	 * @Autowired이 붙은 속성변수에는 인터페이스 자료형을 쓰고
	 * 이 인터페이스를 구현한 클래스를 객체화하여 저장
	 * 이때 ContactDAO 인터페이스를 구현한 클래스의 이름을 몰라도 관계없음
	 * 1개 존재하기만 하면 됨
	 */
	@Autowired
	private ContactDAO contactDAO;	// @Autowired에 의해 ContactDAO라는 인터페이스를 구현한 클래스를 객체화하여 저장
	/**
	 * 연락처 정보를 삽입하는 메소드
	 */
	@Override
	@Transactional
	public int insertContact(ContactDTO contactDTO) {
		int insertContact = this.contactDAO.insertContact(contactDTO);
		int insertBusinessArea=0;
		
		for(String bac : contactDTO.getBusiness_area_code()) {
			insertBusinessArea += this.contactDAO.insertContactBusinessArea(bac);
		}
		System.out.println("Service insertContact : " + insertContact);
		System.out.println("Service insertBusinessArea : " + insertBusinessArea);
		return insertContact;
	}
}