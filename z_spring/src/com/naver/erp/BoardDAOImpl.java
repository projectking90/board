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

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * BoardDAOImple 클래스
 * DAO 클래스
 * @Repository를 붙임으로써 DAO 클래스임을 지정하게되고,
 * bean 태그로 자동 등록됨
 * @author Jo
 */
@Repository
public class BoardDAOImpl implements BoardDAO {
	/**
	 * 속성변수 선언
	 */
	@Autowired
	private SqlSessionTemplate sqlSession;	// SqlSessionTemplate 객체를 생성하고 저장

	/**
	 * 메소드 선언
	 */
	/**
	 * 검색한 게시판 목록 개수 리턴하는 메소드
	 * @param boardSearchDTO : 게시판 DTO
	 * @return boardListAllCnt : 검색한 게시판 목록 개수
	 */
	@Override
	public int getBoardListAllCnt(BoardSearchDTO boardSearchDTO) {
		int boardListAllCnt = this.sqlSession.selectOne(
				sqlSessionPath + "getBoardListAllCnt"	// 실행할 SQL 구문의 위치 지정
				, boardSearchDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		
		return boardListAllCnt;
	}

	/**
	 * 검색한 게시판 목록 리턴하는 메소드
	 * @param boardSearchDTO : 게시판 DTO
	 * @return boardList : 검색한 게시판 목록
	 */
	@Override
	public List<Map<String, String>> getBoardList(BoardSearchDTO boardSearchDTO) {
		List<Map<String, String>> boardList = this.sqlSession.selectList(
				sqlSessionPath + "getBoardList"	// 실행할 SQL 구문의 위치 지정
				, boardSearchDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		
		return boardList;
	}

	/**
	 * 댓글 쓰기 시 게시판 글의 출력 순서번호를 1씩 증가 시킴
	 * @param boardSearchDTO : 게시판 DTO
	 * @return increPrintNo : 수정된 행의 개수
	 */
	@Override
	public int upPrintNo(BoardDTO boardDTO) {
		int upPrintNo = this.sqlSession.update(
				sqlSessionPath + "upPrintNo"	// 실행할 SQL 구문의 위치 지정
				, boardDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		
		return upPrintNo;
	}

	/**
	 * 입력 적용행의 개수 리턴하는 메소드
	 * @param boardDTO : 게시판 DTO
	 * @return boardRegCnt : 입력 적용행의 개수
	 */
	@Override
	public int insertBoard(BoardDTO boardDTO) {
		int boardRegCnt = this.sqlSession.insert(
				sqlSessionPath + "insertBoard"	// 실행할 SQL 구문의 위치 지정
			, boardDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
			);
		
		return boardRegCnt;
	}

	/**
	 * 수정/삭제할 게시판의 존재 개수를 얻음
	 * @param boardDTO : 게시판 DTO
	 * @return boardCnt : 수정/삭제할 게시판 개수
	 */
	@Override
	public int getBoardCnt(BoardDTO boardDTO) {
		int boardCnt = this.sqlSession.selectOne(
				sqlSessionPath + "getBoardCnt"	// 실행할 SQL 구문의 위치 지정
				, boardDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		
		return boardCnt;
	}

	/**
	 * 수정 적용행의 개수 리턴하는 메소드
	 * @param boardDTO : 게시판 DTO
	 * @return boardRegCnt : 수정 적용행의 개수
	 */
	@Override
	public int updateBoard(BoardDTO boardDTO) {
		int boardUpRes = this.sqlSession.insert(
				sqlSessionPath + "updateBoard"	// 실행할 SQL 구문의 위치 지정
				, boardDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		
			return boardUpRes;
	}

	/**
	 * 수정/삭제할 게시판의 비밀번호 존재 개수를 얻음
	 * @param boardDTO : 게시판 DTO
	 * @return pwdCnt : 수정/삭제할 게시판 비밀번호 존재 개수
	 */
	@Override
	public int getPwdCnt(BoardDTO boardDTO) {
		int pwdCnt = this.sqlSession.selectOne(
				sqlSessionPath + "getPwdCnt"	// 실행할 SQL 구문의 위치 지정
				, boardDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		
		return pwdCnt;
	}

	/**
	 * 삭제할 게시판의 자식글 존개 개수를 얻음
	 * @param boardDTO : 게시판 DTO
	 * @return childCnt : 삭제할 게시판의 자식글 존재 개수
	 */
	@Override
	public int getChildCnt(BoardDTO boardDTO) {
		int childCnt = this.sqlSession.selectOne(
				sqlSessionPath + "getChildCnt"	// 실행할 SQL 구문의 위치 지정
				, boardDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		
		return childCnt;
	}

	/**
	 * 삭제될 게시판 이후 글의 출력 순서번호를 1씩 감소 시킴
	 * @param boardDTO : 게시판 DTO
	 * @return upPintNoCnt : 삭제될 게시판 이후 글의 출력 순서번호를 1씩 감소 성공 여부
	 */
	@Override
	public int downPrintNo(BoardDTO boardDTO) {
		int downPrintNo = this.sqlSession.update(
				sqlSessionPath + "downPrintNo"	// 실행할 SQL 구문의 위치 지정
				, boardDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		
		return downPrintNo;
	}

	/**
	 * 삭제 적용행의 개수 리턴하는 메소드
	 * @param boardDTO : 게시판 DTO
	 * @return boardRegCnt : 삭제 적용행의 개수
	 */
	@Override
	public int deleteBoard(BoardDTO boardDTO) {
		int boardDelRes = this.sqlSession.insert(
				sqlSessionPath + "deleteBoard"	// 실행할 SQL 구문의 위치 지정
				, boardDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		
		return boardDelRes;
	}

	/**
	 * 1개의 게시판 글의 조회수를 증가시키는 메소드
	 * @param b_no : 조회수를 수정하려는 게시판의 PK
	 * @return readcount : 조회수 수정 성공 여부
	 */
	@Override
	public int updateReadcount(int b_no) {
		int readcount = this.sqlSession.update(
				sqlSessionPath + "updateReadcount"	// 실행할 SQL 구문의 위치 지정
				, b_no	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		
		return readcount;
	}

	/**
	 * 1개의 게시판 글을 리턴하는 메소드
	 * @param b_no : 보려고하는 게시판의 PK
	 * @return boardDTO : 1개의 게시판 글
	 */
	@Override
	public BoardDTO getBoardDTO(int b_no) {
		BoardDTO boardDTO = this.sqlSession.selectOne(
				sqlSessionPath + "getBoardDTO"	// 실행할 SQL 구문의 위치 지정
			, b_no	// 실행할 SQL 구문에서 사용할 데이터 지정
			);
	
		return boardDTO;
	}
}