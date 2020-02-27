/**
 * 아래에 나오는 클래스의 소속 패키지 경로를 설정하기
 * 모든 자바 클래스 최상단에는 소속 패키지 경로가 먼저 나옴
 */
/**
 * 
 */
package com.naver.erp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LoginController 클래스
 * 가상 URL 주소로 접속하면 호출되는 메소드를 소유한 컨트롤러 클래스 선언
 * @Controller를 붙임으로써 컨트롤러 클래스임을 지정
 * @ControllerAdvice를 붙임으로써 동료 메소드가 에러 발생 시 @ExceptionHandler가 붙은 메소드 호출
 * @author Jo
 */
@Controller
@ControllerAdvice
public class LoginController {
	/**
	 * 속성변수 선언
	 */
	/**
	 * @Autowired이 붙은 속성변수에는 인터페이스 자료형을 쓰고
	 * 이 인터페이스를 구현한 클래스를 객체화하여 저장
	 * 이때 LoginService 인터페이스를 구현한 클래스의 이름을 몰라도 관계없음
	 * 1개 존재하기만 하면 됨
	 */
	@Autowired
	private LoginService loginService;	// LoginService 인터페이스를 구현한 클래스를 객체화하여 저장
	
	/**
	 * 메소드 선언
	 */
	/**
	 * 가상주소 /z_spring/loginForm.do로 접근하면 호출되는 메소드 선언
	 * @return mav : /loginForm.do에 맵핑되는 jsp 파일
	 */
	@RequestMapping(
			value="/loginForm.do")
	public ModelAndView loginForm(
			//HttpSession session
			HttpServletRequest request
			) {
		//session.removeAttribute("admin_id");
		ModelAndView mav = new ModelAndView();	// ModelAndView 객체 생성
		
		mav.setViewName("login_form.jsp");	// ModelAndView 객체에 호출할 JSP 페이지명을 저장
		
		return mav;	// ModelAndView 객체 리턴
	}
	
	/**
	 * 가상주소 /z_spring/loginProc.do로 접근하면 호출되는 메소드 선언
	 * 메소드 호출 후 ModelAndView 객체를 리턴하지 않고,
	 * 아이디와 암호의 존재개수를 리턴하도록 설정
	 * @param admin_id : admin_id라는 파라미터명에 해당하는 파라미터 값(아이디)
	 * @param pwd : 암호
	 * @param is_login : 아이디, 암호 저장 체크 여부
	 * @param reponse : HttpServletResponse
	 * @param session : HttpSession 객체
	 * @return admin_idCnt : admin 테이블에 존재하는 로그인 아이디의 존재 개수를 리턴
	 */
	@RequestMapping(
			value="/loginProc.do"	// 접속하는 클라이언트의 URL 주소 설정
			, method=RequestMethod.POST	// 접속하는 클라이언트의 파라미터값 전송 방법
			, produces="application/json;charset=UTF-8"	// 응답할 데이터 종류는 json 설정
	)
	@ResponseBody	// 비동기 방식으로 접속할 때 많이 씀
	public int loginProc(
			@RequestParam(value="admin_id") String admin_id
			, @RequestParam(value="pwd") String pwd
			, @RequestParam(value="is_login", required=false) String is_login
			, HttpServletResponse reponse
			, HttpSession session
			) {
		int admin_idCnt = 0;	// 로그인 아이디, 암호의 존재 개수를 저장하는 변수 선언
		/**
		 * 매개변수에 저장된 파라미터값(아이디, 암호)을 HashMap에 저장
		 * 이렇게 한 군데에 모으는 이유는 서비스 클래스에게 전달할 때
		 * 하나로 전달하기 위함임
		 */
		try {
			Map<String, String> map = new HashMap<String, String>();	// 아이디, 암호를 저장할 Map 컬렉션 선언
			
			map.put("admin_id", admin_id);
			map.put("pwd", pwd);
			
			admin_idCnt = this.loginService.getAdminCnt(map);	// admin이라는 테이블에 존재하는 로그인 아이디의 존재 개수 얻기

			if(admin_idCnt==1) {
				session.setAttribute("admin_id", admin_id);
				
				if(is_login!=null) {	// 쿠키 저장 할 때
					Cookie cookie1 = new Cookie("admin_id", admin_id);	// 아이디를 쿠키에 저장
					cookie1.setMaxAge(60*60*24);	// 쿠키 생명을 하루로 설정
					reponse.addCookie(cookie1);	// response에 쿠키 저장

					Cookie cookie2 = new Cookie("pwd", pwd);	// 암호를 쿠키에 저장
					cookie2.setMaxAge(60*60*24);	// 쿠키 생명을 하루로 설정
					reponse.addCookie(cookie2);	// response에 쿠키 저장
				} else {	// 쿠키 저장 안할 때
					Cookie cookie1 = new Cookie("admin_id", null);	// 아이디를 쿠키에서 지우고
					cookie1.setMaxAge(0);	// 쿠키 생명을 없애고
					reponse.addCookie(cookie1);	// response에 쿠키 저장

					Cookie cookie2 = new Cookie("pwd", null);	// 암호를 쿠키에서 지우고
					cookie2.setMaxAge(0);	// 쿠키 생명을 없애고
					reponse.addCookie(cookie2);	// response에 쿠키 저장
				}
			}
		} catch(Exception e){
			System.out.println("loginProc 메소드 오류");
			System.out.println(e.getMessage());
			admin_idCnt = -1;
		}
		
		return admin_idCnt;
	}
	/*public int loginProc(
			@RequestParam Map<String, String> paramsMap) {
		System.out.println(paramsMap.get("admin_id"));
		System.out.println(paramsMap.get("pwd"));
		
		return 1;
	}
	public int loginProc(
			String admin_id
			, String pwd) {
		System.out.println(admin_id);
		System.out.println(pwd);
		
		return 1;
	}*/
	
	/**
	 * 가상주소 /z_spring/loginForm.do로 접근하면 호출되는 메소드 선언
	 * @param session : HttpSession 객체
	 * @return mav : /loginForm.do에 맵핑되는 jsp 파일
	 */
	@RequestMapping(value="/logout.do")
	public ModelAndView logoutForm(
			HttpSession session
			) {
		/*
		 * session.removeAttribute("admin_id"); // 저장된 로그인 아이디 삭제
		 * session.removeAttribute("uri"); // 저장된 uri 삭제
		 * session.removeAttribute("boardSearchDTO"); // 저장된 boardSearchDTO 삭제
		 */
		session.invalidate();	// session 객체의 수명을 0으로 만듬
		
		ModelAndView mav = new ModelAndView();	// ModelAndView 객체 생성
		
		mav.setViewName("logout.jsp");	// ModelAndView 객체에 호출할 JSP 페이지명을 저장
		
		return mav;	// ModelAndView 객체 리턴
	}
	
	/**
	 * 현재 이 컨트롤러 클래스 내의 @RequestMapping이 붙은 메소드 호출 시 예외 발생하면 호출 되는 메소드
	 * @ExceptionHandler(Exception.class)를 붙여야하며
	 * @param request : HttpServletRequest 객체
	 * @return 호출되는 JSP 페이지명
	 */
	@ExceptionHandler(Exception.class)
	public String handlerException(
			HttpServletRequest request
			) {
		return "logout.jsp";
	}
}