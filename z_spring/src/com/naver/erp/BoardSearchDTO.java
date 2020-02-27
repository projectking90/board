/**
 * 아래에 나오는 클래스의 소속 패키지 경로를 설정하기
 * 모든 자바 클래스 최상단에는 소속 패키지 경로가 먼저 나옴
 */
/**
 * 
 */
package com.naver.erp;

/**
 * BoardSearchDTO 클래스
 * @author Jo
 */
public class BoardSearchDTO {
	/**
	 * 속성변수 선언
	 */
	private String keyword1;	// 키워드1
	private String[] date;	// 날짜 체크박스 선택
	private String[] count;	// 조회수 체크박스 선택
	private int selectPageNo;	// 선택 페이지
	private int rowCntPerPage;	// 한번에 보여줄 행 개수
	private String orAnd;	// orAnd 연산자 선택
	private String keyword2;	// 키워드2
	
	/**
	 * 생성자 선언
	 */
	public BoardSearchDTO() {
		this.setSelectPageNo(1);
		this.setRowCntPerPage(10);
		this.setOrAnd("or");
	}
	
	/**
	 * 접근자, 설정자 선언
	 */
	/**
	 * keyword1 접근자
	 * @return keyword1 : 키워드1
	 */
	public String getKeyword1() {
		return keyword1;
	}
	/**
	 * keyword1 설정자
	 * @param keyword1 : 키워드1
	 */
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	/**
	 * date 접근자
	 * @return date : 체크박스 선택
	 */
	public String[] getDate() {
		return date;
	}
	/**
	 * date 설정자
	 * @param date : 체크박스 선택
	 */
	public void setDate(String[] date) {
		this.date = date;
	}
	/**
	 * count 접근자
	 * @return count : 조회수
	 */
	public String[] getCount() {
		return count;
	}
	/**
	 * count 설정자
	 * @param count : 조회수
	 */
	public void setCount(String[] count) {
		this.count = count;
	}
	/**
	 * selectPageNo 접근자
	 * @return selectPageNo : 선택 페이지
	 */
	public int getSelectPageNo() {
		return selectPageNo;
	}
	/**
	 * selectPageNo 설정자
	 * @param selectPageNo : 선택 페이지
	 */
	public void setSelectPageNo(int selectPageNo) {
		this.selectPageNo = selectPageNo;
	}
	/**
	 * rowCntPerPage 접근자
	 * @return rowCntPerPage : 한번에 보여줄 행 개수
	 */
	public int getRowCntPerPage() {
		return rowCntPerPage;
	}
	/**
	 * rowCntPerPage 설정자
	 * @param rowCntPerPage : 한번에 보여줄 행 개수
	 */
	public void setRowCntPerPage(int rowCntPerPage) {
		this.rowCntPerPage = rowCntPerPage;
	}

	/**
	 * orAnd 접근자
	 * @return orAnd : orAnd 연산
	 */
	public String getOrAnd() {
		return orAnd;
	}

	/**
	 * orAnd 설정자
	 * @param orAnd : orAnd 연산
	 */
	public void setOrAnd(String orAnd) {
		this.orAnd = orAnd;
	}

	/**
	 * keyword2 접근자
	 * @return keyword2 : 키워드2
	 */
	public String getKeyword2() {
		return keyword2;
	}

	/**
	 * keyword2 설정자
	 * @param keyword2 : 키워드2
	 */
	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}
}