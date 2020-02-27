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
public class ContactController {
	/**
	 * 속성변수 선언
	 */
	@Autowired
	private ContactService contactService;	// ContactService 인터페이스를 구현받은 객체를 생성해서 저장
	
	/**
	 * 메소드 선언
	 */
	/**
	 * 가상주소 /contactRegForm.do로 접근하면 호출되는 메소드 선언
	 * @param boardSearchDTO : BoardSearchDTO
	 * @return mav : /contactRegForm.do에 맵핑되는 jsp 파일
	 */
	@RequestMapping(value="/contactRegForm.do")
	public ModelAndView getBoardList() {
		ModelAndView mav = new ModelAndView();	// ModelAndView 객체 생성
		mav.setViewName("contactRegForm.jsp");	// ModelAndView 객체에 호출할 JSP 페이지명을 저장
		
		return mav;	// ModelAndView 객체 리턴
	}
	
	/**
	 * 가상주소 /contactRegProc.do로 접근하면 호출되는 메소드 선언
	 * @param contactDTO : ContactDTO
	 * @return mav : /contactRegProc.do에 맵핑되는 jsp 파일
	 */
	@RequestMapping(value="/contactRegProc.do")
	@ResponseBody
	public int insertContact(
			ContactDTO contactDTO) {
		int insertContact = this.contactService.insertContact(contactDTO);
		System.out.println("Controller insertContact : " + insertContact);
		return insertContact;
	}
}