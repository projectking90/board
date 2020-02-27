/**
 * 아래에 나오는 클래스의 소속 패키지 경로를 설정하기
 * 모든 자바 클래스 최상단에는 소속 패키지 경로가 먼저 나옴
 */
/**
 * 
 */
package com.naver.erp;

/**
 * BoardDTO 클래스
 * Board 테이블 DTO
 * @author Jo
 */
public class BoardDTO {
	/**
	 * 속성변수 선언
	 */
	private int b_no;	// 게시판 번호
	private String subject;	// 제목
	private String writer;	// 작성자
	private String reg_date;	// 등록일
	private int readcount;	// 조회수
	private String content;	// 내용
	private String pwd;	// 암호
	private String email;	// 이메일
	private int group_no;	// 그룹번호
	private int pring_no;	// 출력순서
	private int pring_level;	// 출력 레벨
	
	/**
	 * 접근자, 설정자 선언
	 */
	/**
	 * b_no 접근자
	 * @return b_no : 게시판 번호
	 */
	public int getB_no() {
		return b_no;
	}
	/**
	 * b_no 설정자
	 * @param b_no : 게시판 번호
	 */
	public void setB_no(int b_no) {
		this.b_no = b_no;
	}
	/**
	 * subject 접근자
	 * @return subject : 제목
	 */
	public String getSubject() {
		return subject.replaceAll("<", "&lt;");
	}
	/**
	 * subject 설정자
	 * @param subject : 제목
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * writer 접근자
	 * @return writer : 작성자
	 */
	public String getWriter() {
		return writer.replaceAll("<", "&lt;");
	}
	/**
	 * writer 설정자
	 * @param writer : 작성자
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}
	/**
	 * reg_date 접근자
	 * @return reg_date : 등록일
	 */
	public String getReg_date() {
		return reg_date;
	}
	/**
	 * reg_date 설정자
	 * @param reg_date : 등록일
	 */
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	/**
	 * readcount 접근자
	 * @return readcount : 조회수
	 */
	public int getReadcount() {
		return readcount;
	}
	/**
	 * readcount 설정자
	 * @param readcount : 조회수
	 */
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	/**
	 * content 접근자
	 * @return content : 내용
	 */
	public String getContent() {
		return content.replaceAll("<", "&lt;");
	}
	/**
	 * content 설정자
	 * @param content : 내용
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * pwd 접근자
	 * @return pwd : 암호
	 */
	public String getPwd() {
		return pwd;
	}
	/**
	 * pwd 설정자
	 * @param pwd : 암호
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * email 접근자
	 * @return email : 이메일
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * email 설정자
	 * @param email : 이메일
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * group_no 접근자
	 * @return group_no : 그룹번호
	 */
	public int getGroup_no() {
		return group_no;
	}
	/**
	 * group_no 설정자
	 * @param group_no : 그룹번호
	 */
	public void setGroup_no(int group_no) {
		this.group_no = group_no;
	}
	/**
	 * pring_no 접근자
	 * @return pring_no : 출력순서
	 */
	public int getPring_no() {
		return pring_no;
	}
	/**
	 * pring_no 설정자
	 * @param pring_no : 출력순서
	 */
	public void setPring_no(int pring_no) {
		this.pring_no = pring_no;
	}
	/**
	 * pring_level 접근자
	 * @return pring_level : 출력순서
	 */
	public int getPring_level() {
		return pring_level;
	}
	/**
	 * pring_level 설정자
	 * @param pring_level : 출력순서
	 */
	public void setPring_level(int pring_level) {
		this.pring_level = pring_level;
	}
}