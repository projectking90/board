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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * BoardServiceImple 클래스
 * 서비스 클래스
 * 서비스 클래스에는 @Service와 @Transactional를 붙임
 * @Service : 서비스 클래스 임을 지정하고 bean 태그로 자동 등록됨
 * @Transactional : 서비스 클래스의 메소드 내부에서 일어나는 모든 작업에는 트랜잭션이 걸림
 * @author Jo
 */
@Service
@Transactional
public class BoardServiceImpl implements BoardService {
	/**
	 * 속성변수 선언
	 */
	@Autowired
	private BoardDAO boardDAO;	// BoardDAO 인터페이스를 구현한 클래스를 객체화해서 저장

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
		/**
		 * BoardDAO 인터페이스를 구현한 객체의 getBoardListAllCnt 메소드를 호출하여
		 * 검색한 게시판 목록 개수를 얻음
		 */
		int boardListAllCnt = this.boardDAO.getBoardListAllCnt(boardSearchDTO);
		
		return boardListAllCnt;
	}

	/**
	 * 검색한 게시판 목록 리턴하는 메소드
	 * @param boardSearchDTO : 게시판 DTO
	 * @return boardList : 검색한 게시판 목록
	 */
	@Override
	public List<Map<String, String>> getBoardList(BoardSearchDTO boardSearchDTO) {
		List<Map<String, String>> boardList = this.boardDAO.getBoardList(boardSearchDTO);
		
		return boardList;
	}

	/**
	 * 입력 적용행의 개수 리턴하는 메소드
	 * @param boardDTO : 게시판 DTO
	 * @return boardRegCnt : 입력 적용행의 개수
	 */
	@Override
	public int insertBoard(BoardDTO boardDTO) {
		if(boardDTO.getB_no() != 0) {
			int increPrintNo = this.boardDAO.upPrintNo(boardDTO);
		}
		
		int boardRegCnt = this.boardDAO.insertBoard(boardDTO);
		
		return boardRegCnt;
	}
	
	/**
	 * 수정 적용행의 개수 리턴하는 메소드
	 * @param boardDTO : 게시판 DTO
	 * @return boardRegCnt : 수정 적용행의 개수
	 */
	@Override
	public int updateBoard(BoardDTO boardDTO) {
		int boardCnt = this.boardDAO.getBoardCnt(boardDTO);	// 수정할 게시판의 존재 개수를 얻음
		if(boardCnt==0) {
			return -1;
		}
		
		int pwdCnt = this.boardDAO.getPwdCnt(boardDTO);	// 수정할 게시판의 비밀번호 존재 개수를 얻음
		if(pwdCnt==0) {
			return -2;
		}
		
		int updateCnt = this.boardDAO.updateBoard(boardDTO);	// 게시판 수정 명령한 후 수정 적용행의 개수를 얻음
		
		return updateCnt;
	}

	/**
	 * 삭제 적용행의 개수 리턴하는 메소드
	 * @param boardDTO : 게시판 DTO
	 * @return boardRegCnt : 삭제 적용행의 개수
	 */
	@Override
	public int deleteBoard(BoardDTO boardDTO) {
		int boardCnt = this.boardDAO.getBoardCnt(boardDTO);	// 삭제할 게시판의 존재 개수를 얻음
		if(boardCnt==0) {
			return -1;
		}
		
		int pwdCnt = this.boardDAO.getPwdCnt(boardDTO);	// 삭제할 게시판의 비밀번호 존재 개수를 얻음
		if(pwdCnt==0) {
			return -2;
		}
		
		int childCnt = this.boardDAO.getChildCnt(boardDTO);	// 삭제할 게시판의 자식글 존개 개수를 얻음
		if(childCnt>0) {
			return -3;
		}
		
		int upPrintNoCnt = this.boardDAO.downPrintNo(boardDTO);	// 삭제될 게시판 이후 글의 출력 순서번호를 1씩 감소 시킴
		int deleteCnt = this.boardDAO.deleteBoard(boardDTO);	// 게시판 삭제 명령 후 삭제 적용행의 개수를 얻음
		
		return deleteCnt;
	}

	/**
	 * 1개의 게시판 글을 리턴하는 메소드
	 * @param b_no : 보려고하는 게시판의 PK
	 * @return boardDTO : 1개의 게시판 글
	 */
	@Override
	public BoardDTO getBoardDTO(int b_no) {
		int readcount = this.boardDAO.updateReadcount(b_no);
		BoardDTO boardDTO;
		
		if(readcount > 0) {
			boardDTO = this.boardDAO.getBoardDTO(b_no);
		} else {
			boardDTO = null;
			System.out.println("updateReadcount 실패");
		}
		
		return boardDTO;
	}

	/**
	 * 조회수 증가를 뺀 1개의 게시판 글을 리턴하는 메소드
	 * @param b_no : 보려고하는 게시판의 PK
	 * @return boardDTO : 1개의 게시판 글
	 */
	@Override
	public BoardDTO getBoardDTO_without_upReadcount(int b_no) {
		BoardDTO boardDTO;
		
		boardDTO = this.boardDAO.getBoardDTO(b_no);	// getBoardDTO를 호출하여 조회수 증가 없이 1개 게시판 글 얻음
		
		return boardDTO;	// 1개 게시판 글이 저장된 BoardDTO 객체 리턴
	}
}