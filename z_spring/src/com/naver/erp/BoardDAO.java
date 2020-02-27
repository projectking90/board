/**
 * 아래에 나오는 클래스의 소속 패키지 경로를 설정하기
 * 모든 자바 클래스 최상단에는 소속 패키지 경로가 먼저 나옴
 */
/**
 * 
 */
package com.naver.erp;

import java.util.List;
import java.util.Map;

/**
 * BoardDAO 인터페이스
 * DAO 클래스를 사용하기 위해 정의한 인터페이스
 * @author Jo
 */
public interface BoardDAO {
	/**
	 * 속성변수 선언
	 */
	String sqlSessionPath = "com.naver.erp.BoardDAO.";
	/**
	 * 메소드 선언
	 */
	int getBoardListAllCnt(BoardSearchDTO boardSearchDTO);	// 검색한 게시판 목록 개수 리턴하는 메소드
	List<Map<String, String>> getBoardList(BoardSearchDTO boardSearchDTO);	// 검색한 게시판 목록 리턴하는 메소드
	int upPrintNo(BoardDTO boardDTO);	// 댓글 쓰기 시 게시판 글의 출력 순서번호를 1씩 증가 시킴
	int insertBoard(BoardDTO boardDTO);	// 게시판 입력 적용행의 개수 리턴하는 메소드
	int getBoardCnt(BoardDTO boardDTO);	// 수정/삭제할 게시판의 존재 개수를 얻음
	int getPwdCnt(BoardDTO boardDTO);	// 수정/삭제할 게시판의 비밀번호 존재 개수를 얻음
	int updateBoard(BoardDTO boardDTO);	// 게시판 수정 적용행의 개수 리턴하는 메소드
	int getChildCnt(BoardDTO boardDTO);	// 삭제할 게시판의 자식글 존개 개수를 얻음
	int downPrintNo(BoardDTO boardDTO);	// 삭제될 게시판 이후 글의 출력 순서번호를 1씩 감소 시킴
	int deleteBoard(BoardDTO boardDTO);	// 게시판 삭제 적용행의 개수 리턴하는 메소드
	int updateReadcount(int b_no);	// 1개의 게시판의 조회수를 증가하는 메소드
	BoardDTO getBoardDTO(int b_no);	// 1개의 게시판 글을 리턴하는 메소드
}