/**
 * 아래에 나오는 클래스의 소속 패키지 경로를 설정하기
 * 모든 자바 클래스 최상단에는 소속 패키지 경로가 먼저 나옴
 */
package com.naver.erp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;

import javax.servlet.http.HttpSession;

/**
 * BoardController 클래스
 * 가상 URL 주소로 접속하면 호출되는 메소드를 소유한 컨트롤러 클래스 선언
 * @Controller를 붙임으로써 컨트롤러 클래스임을 지정
 * @author Jo
 */
@Controller
public class BoardController {
	/**
	 * 속성변수 선언
	 */
	@Autowired
	private BoardService boardService;	// BoardService 인터페이스를 구현받은 객체를 생성해서 저장
	
	/**
	 * 메소드 선언
	 */
	/**
	 * 가상주소 /z_spring/boardListForm.do로 접근하면 호출되는 메소드 선언
	 * @param boardSearchDTO : BoardSearchDTO
	 * @return mav : /boardListForm.do에 맵핑되는 jsp 파일
	 */
	@RequestMapping(value="/boardListForm.do")
	public ModelAndView getBoardList(
			// 파라미터값이 저장되는 BoardSearchDTO 객체를 매개변수로 선언
			// 파라티터명과 BoardSearchDTO 객체의 속성변수명이 같으면 setter 메소드가 작동되어
			// 파라미터값이 속성변수에 저장됨
			// 속성변수명에 대응하는 파라미터명이 없으면 setter 메소드가 작동되지 않음
			// 속성변수명에 대응하는 파라미터명이 있는데 파라미터값이 없으면
			// 속성변수의 자료형에 관계없이 무조건 null 값이 저장
			// 이때 속성변수의 자료형이 기본형일 경우 null 값이 저장될 수 없어 에러가 발생
			// 이런 에러를 피하려면 파라티터값이 기본형이거나 속성변수의 자료형을 String으로 해야함
			// 이런 에러가 발생하면 메소드안의 실행구문은 하나도 실행되지 않음에 주의(예외처리 안됨)
			BoardSearchDTO boardSearchDTO
			, HttpSession session) {
		ModelAndView mav = new ModelAndView();	// ModelAndView 객체 생성
		mav.setViewName("boardListForm.jsp");	// ModelAndView 객체에 호출할 JSP 페이지명을 저장
		
		try {
			String uri = (String)session.getAttribute("uri");
			if(uri==null || uri.equals("boardListForm.do")) {
				session.setAttribute("boardSearchDTO", boardSearchDTO);
			} else {
				boardSearchDTO = (BoardSearchDTO)session.getAttribute("boardSearchDTO");
			}
			session.setAttribute("uri", "boardListForm.do");
			
			int boardListAllCnt = this.boardService.getBoardListAllCnt(boardSearchDTO);	// 게시판 검색 총 개수를 얻기
			int selectPageNo = boardSearchDTO.getSelectPageNo();	// 선택한 페이지 번호
			int rowCntPerPage = boardSearchDTO.getRowCntPerPage();	// 한 페이지에 나올 행 개수
			int startIndex = (selectPageNo * rowCntPerPage) - rowCntPerPage + 1;	// 선택한 페이지에서 나올 시작 번호
			
			if(boardListAllCnt < startIndex) {	// 총 개수가 선택한 페이지에서 나올 시작 번호보다 작을 경우
				boardSearchDTO.setSelectPageNo(1);	// 페이지 번호 1로 변경
			}
			
			List<Map<String, String>> boardList = this.boardService.getBoardList(boardSearchDTO);	// 게시판 검색 목록 얻기
			
			mav.addObject("boardListAllCnt", boardListAllCnt);	// ModelAndView 객체에 검색 개수 저장
			mav.addObject("boardList", boardList);	// ModelAndView 객체에 검색 목록 저장
		} catch(Exception e) {	// try 구문에서 예외가 발생하면 실행할 구문 설정
			System.out.println("<getBoardList 에러발생>");
			System.out.println(e.getMessage());
		}
		
		return mav;	// ModelAndView 객체 리턴
	}
	
	/**
	 * 가상주소 /z_spring/boardRegForm.do로 접근하면 호출되는 메소드 선언
	 * @return mav : /boardRegForm.do에 맵핑되는 jsp 파일
	 */
	@RequestMapping(value="/boardRegForm.do")
	public ModelAndView goBoardRegForm() {
		ModelAndView mav = new ModelAndView();	// ModelAndView 객체 생성
		mav.setViewName("boardRegForm.jsp");	// ModelAndView 객체에 호출할 JSP 페이지명을 저장
		
		return mav;	// ModelAndView 객체 리턴
	}
	
	/**
	 * 가상주소 /z_spring/boardRegProc.do로 접근하면 호출되는 메소드 선언
	 * @param boardSearchDTO : BoardSearchDTO
	 * @return mav : /boardRegProc.do에 맵핑되는 jsp 파일
	 */
	@RequestMapping(
			value="/boardRegProc.do"	// 접속하는 클라이언트의 URL 주소 설정
			, method=RequestMethod.POST	// 접속하는 클라이언트의 파라미터값 전송 방법
			, produces="application/json;charset=UTF-8"	// 응답할 데이터 종류는 json 설정
			)
	@ResponseBody
	public int insertBoard(
			// 파라미터값을 저장할 BoardDTO 객체를 매개변수로 선언
			BoardDTO boardDTO) {
		int boardRegCnt = 0;	// 게시판 입력 적용행의 개수 저장
		try {
			boardRegCnt = this.boardService.insertBoard(boardDTO);	// 게시판 입력하고 게시판 입력 적용행의 개수 얻기
		} catch(Exception e) {	// try 구문에서 예외가 발생하면 실행할 구문 설정
			System.out.println("<insertBoard 에러발생>");
			System.out.println(e.getMessage());
		}
		
		return boardRegCnt;
	}
	
	/**
	 * 가상주소 /z_spring/boardUpProc.do로 접근하면 호출되는 메소드 선언
	 * @param boardSearchDTO : BoardSearchDTO
	 * @return mav : /boardUpProc.do에 맵핑되는 jsp 파일
	 */
	@RequestMapping(
			value="/boardUpDelProc.do"	// 접속하는 클라이언트의 URL 주소 설정
			, method=RequestMethod.POST	// 접속하는 클라이언트의 파라미터값 전송 방법
			, produces="application/json;charset=UTF-8"	// 응답할 데이터 종류는 json 설정
			)
	@ResponseBody
	public int boardUpDelProc(
			// 파라미터값을 저장할 BoardDTO 객체를 매개변수로 선언
			BoardDTO boardDTO
			, @RequestParam(value="upDel") String upDel) {
		int boardUpDelCnt = 0;	// 게시판 입력/수정 적용행의 개수 저장
		
		try {
			if(upDel.equals("Up")) {
				boardUpDelCnt = this.boardService.updateBoard(boardDTO);
			} else if(upDel.equals("Del")) {
				boardUpDelCnt = this.boardService.deleteBoard(boardDTO);
			}
		} catch(Exception e) {	// try 구문에서 예외가 발생하면 실행할 구문 설정
			System.out.println("<boardUpDelProc 에러발생>");
			System.out.println(e.getMessage());
		}
		
		return boardUpDelCnt;
	}
	
	/**
	 * 가상주소 /z_spring/boardContentForm.do로 접근하면 호출되는 메소드 선언
	 * @RequestParam(value="b_no") b_no : b_no 파라미터명에 해당하는 파라미터값 저장
	 * @return mav : /boardContentForm.do에 맵핑되는 jsp 파일
	 */
	@RequestMapping(value="/boardContentForm.do")
	public ModelAndView goBoardContentForm(
			// 게시판 PK 번호가 매개변수로 들어오므로 매개변수 자료형은 int로 함
			// String으로 받아도 문제 없음
			@RequestParam(value="b_no") int b_no
			, HttpSession session) {
		ModelAndView mav = new ModelAndView();	// ModelAndView 객체 생성
		mav.setViewName("boardContentForm.jsp");	// ModelAndView 객체에 호출할 JSP 페이지명을 저장
		
		try {
			session.setAttribute("uri", "boardContentForm.do");
			BoardDTO boardDTO = this.boardService.getBoardDTO(b_no);	// 게시판 상세 보기 글 얻기
			
			mav.addObject("boardDTO", boardDTO);	// ModelAndView 객체에 검색 목록 저장
		} catch(Exception e) {	// try 구문에서 예외가 발생하면 실행할 구문 설정
			System.out.println("<goBoardContentForm 에러발생>");
			System.out.println(e.getMessage());
		}
		
		return mav;	// ModelAndView 객체 리턴
	}
	
	/**
	 * 가상주소 /z_spring/boardUpDelForm.do로 접근하면 호출되는 메소드 선언
	 * @RequestParam(value="b_no") b_no : b_no 파라미터명에 해당하는 파라미터값 저장
	 * @return mav : /boardUpDelForm.do에 맵핑되는 jsp 파일
	 */
	@RequestMapping(
			value="/boardUpDelForm.do"	// 접속하는 클라이언트의 URL 주소 설정
			, method=RequestMethod.POST	// 접속하는 클라이언트의 파라미터값 전송 방법
			)
	public ModelAndView goBoardUpDelForm(
			// 게시판 PK 번호가 매개변수로 들어오므로 매개변수 자료형은 int로 함
			// String으로 받아도 문제 없음
			@RequestParam(value="b_no") int b_no
			, HttpSession session) {
		ModelAndView mav = new ModelAndView();	// ModelAndView 객체 생성
		mav.setViewName("boardUpDelForm.jsp");	// ModelAndView 객체에 호출할 JSP 페이지명을 저장
		
		try {
			session.setAttribute("uri", "boardUpDelForm.do");
			/**
			 * 수정/삭제할 1개의 게시판 글 정보 얻기
			 * BoardServiceImple 객체의 getBoardDTO_without_upReadcount 메소드를 호출하여 얻음
			 */
			BoardDTO boardDTO = this.boardService.getBoardDTO_without_upReadcount(b_no);	// 조회수 증가를 뺀 게시판 상세 보기 글 얻기
			
			mav.addObject("boardDTO", boardDTO);	// ModelAndView 객체에 검색 목록 저장
		} catch(Exception e) {	// try 구문에서 예외가 발생하면 실행할 구문 설정
			System.out.println("<goBoardUpDelForm 에러발생>");
			System.out.println(e.getMessage());
		}
		
		return mav;	// ModelAndView 객체 리턴
	}
}