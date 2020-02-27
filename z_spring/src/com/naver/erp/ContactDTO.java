/**
 * 아래에 나오는 클래스의 소속 패키지 경로를 설정하기
 * 모든 자바 클래스 최상단에는 소속 패키지 경로가 먼저 나옴
 */
package com.naver.erp;

/**
 * ContactDTO 클래스
 * Contact 정보를 담고 있는 DTO
 * @author Jo
 */
public class ContactDTO {
	int contact_no;	// 연락처 순서
	String contact_name;	// 연락처명
	String phone;	// 전화
	String[] business_area_code;	// 사업분야
	
	/**
	 * 접근자, 설정자 선언
	 */
	/**
	 * contact_no 접근자
	 * @return contact_no : 연락처 순서
	 */
	public int getContact_no() {
		return contact_no;
	}
	/**
	 * contact_no 설정자
	 * @param contact_no : 연락처 순서
	 */
	public void setContact_no(int contact_no) {
		this.contact_no = contact_no;
	}
	/**
	 * contact_name 접근자
	 * @return contact_name : 연락처명
	 */
	public String getContact_name() {
		return contact_name;
	}
	/**
	 * contact_name 설정자
	 * @param contact_name : 연락처명
	 */
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	/**
	 * phone 접근자
	 * @return phone : 전화
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * phone 설정자
	 * @param phone : 전화
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * phone 접근자
	 * @return business_area_code : 사업분야
	 */
	public String[] getBusiness_area_code() {
		return business_area_code;
	}
	/**
	 * phone 설정자
	 * @param business_area_code : 사업분야
	 */
	public void setBusiness_area_code(String[] business_area_code) {
		this.business_area_code = business_area_code;
	}
}