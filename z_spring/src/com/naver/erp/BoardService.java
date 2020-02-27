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
 * BoardService 인터페이스
 * 서비스 클래스를 사용하기 위해 정의한 인터페이스
 * @author Jo
 */
public interface BoardService {
	int getBoardListAllCnt(BoardSearchDTO boardSearchDTO);	// 검색한 게시판 목록 개수 리턴하는 메소드
	List<Map<String, String>> getBoardList(BoardSearchDTO boardSearchDTO);	// 검색한 게시판 목록 리턴하는 메소드
	int insertBoard(BoardDTO boardDTO);	// 게시판 입력 적용행의 개수 리턴하는 메소드
	int updateBoard(BoardDTO boardDTO);	// 게시판 수정 적용행의 개수 리턴하는 메소드
	int deleteBoard(BoardDTO boardDTO);	// 게시판 삭제 적용행의 개수 리턴하는 메소드
	BoardDTO getBoardDTO(int b_no);	// 1개의 게시판 글을 리턴하는 메소드
	BoardDTO getBoardDTO_without_upReadcount(int b_no);	// 조회수 증가를 뺀 1개의 게시판 글을 리턴하는 메소드
}