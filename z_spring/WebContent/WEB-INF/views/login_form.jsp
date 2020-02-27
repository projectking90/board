<!-- JSP 기술의 한 종류인 Page Directive를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!-- 현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML이고, -->
<!-- 이 문서 안의 데이터는 UTF-8 방식으로 인코딩한다라고 설정함 -->
<!-- 현재 이 JSP 페이지는 UTF-8 방식으로 인코딩한다. -->
<!-- UTF-8 인코딩 방식은 한글을 포함 전 세계 모든 문자열을 부호화할 수 있는 방법이다.-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- JSP 기술의 한 종류인 Include Directive를 이용하여 common.jsp 파일 내의 소스를 삽입 -->
<%@ include file="/WEB-INF/views/common.jsp" %>
<!DOCTYPE html>
<%--
<%
	Logger logger = Logger.getLogger(this.getClass());
%>
--%>
<%-- <c:if test="${!empty msg}">
	<script>
		alert("${msg}");
	</script>
	<c:remove var="msg" scope="session"/>
</c:if> --%>

<!-- 로그인 화면 구성하는 HTML 태그 코딩하기 -->
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login Form 19.11.20 </title>
		<script>
			/**
				body 태그 안의 소스를 모두 실행한 후에 실행할 자스 코드 설정
			*/
			$(document).ready(function(){
				<%-- <% logger.error(""); %> --%>
				/**
					name=loginForm 가진 폼태그 안의 class=login 가진 태그에
					click 이벤트 발생 시 실행할 코드 설정하기
				*/
				$("[name=loginForm] .login").click(function(){
					checkLoginForm();	// 아이디와 암호의 유효성 체크 할 함수 checkLoginForm() 호출
				});
				
				<%-- ********************** --%>
				<%--$(".admin_id").val("abc");
				$(".pwd").val("1234"); --%>
				<%-- ********************** --%>
				inputData("[name=admin_id]", "${cookie.admin_id.value}");	// 클라이언트가 보낸 쿠키에서 아이디 가져와 입력양식에 넣기
				inputData("[name=pwd]", "${cookie.pwd.value}");	// 클라이언트가 보낸 쿠키에서 암호 가져와 입력양식에 넣기

				<c:if test="${!empty cookie.admin_id.value}">	// 클라이언트가 보낸 쿠키에서
					$("[name=is_login]").prop("checked", true);	// 체크된 체크박스 입력양식 데이터를 가져와 넣기
				</c:if>
			});
			
			/**
				로그인 정보 유효성 체크하고 비동기 방식으로 서버와 통신하여
				로그인 정보와 암호의 존재 여부에 따른 자스 코드 실행하기
			*/
			function checkLoginForm(){
				//var admin_id = $('.admin_id').val();	// 입력된 아이디를 가져와 변수에 저장
				//if(admin_id.trim()==""){	// 아이디를 입력 안했을 경우
				if(is_empty(".admin_id")){
					alert("관리자 아이디 입력 요망");	// 경고하고
					$('.admin_id').val("");	// 아이디 입력란을 비우고
					return;	// 함수 중단
				}
				
				//var pwd = $('.pwd').val();	// 입력된 비밀번호를 가져와 변수에 저장
				//if(pwd.trim()==""){	// 암호를 입력 안했을 경우
				if(is_empty(".pwd")){
					alert("관리자 비밀번호 입력 요망");	// 경고하고
					$('.pwd').val("");	// 암호 입력란을 비우고
					return;	// 함수 중단
				}

				/**
				*	현재 화면에서 페이지 이동 없이(비동기 방식으로)
				*	서버쪽 loginProc.do로 접속하여
				*	아이디, 암호 존재 개수를 얻기
				*/
				$.ajax({
					url : "${ctRoot}/loginProc.do"	// 서버 쪽 호출 URL 주소 지정
					, type : "post"	// form 태그 안의 데이터 즉, 파라미터값을 보내는 방법 지정
					, data : $("[name=loginForm]").serialize()	// 서버로 보낼 파라미터명과 파라미터값을 설정
					// 위는 아래처럼도 가능
					// , data : {'admin_id':admin_id, 'pwd':pwd}
					// , data : "admin_id="+admin_id+"&pwd="+pwd
					/**
					*	서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정
					*	익명함수의 매개변수 data에는 서버가 응답한 데이터가 들어옴
					*	현재 data라는 매개변수에는 아이디, 암호의 존재 개수가 들어옴
					*/
					, success : function(admin_idCnt){
						if(admin_idCnt==1){	// 아이디 존개 개수가 1개면 ${ctRoot}/boardList.do 로 이동
							//alert("로그인 성공");
							location.replace("${ctRoot}/boardListForm.do");
						} else if(admin_idCnt==0){	// 아이디 존재 개수가 0개면 경고하기
							alert("아이디, 암호가 존재하지 않음\n재입력 바람");
						} else{	// 그 외의 경우 오류 경고
							alert("서버 오류 발생!\n관리자에게 문의 바람");
						}
					}
					, error : function(){	// 서버의 응답을 못 받았을 경우 실행할 익명함수 설정
						alert("서버 접속 실패!\n관리자에게 문의 바람");
					}
				});
			}
		</script>
	</head>
	<body><center><br><br><br><br><br><br>
		<form name="loginForm" method="post" action="${ctRoot}/boardList.do">
			<b>[로그인]</b>
			
			<div style="height:6"></div>
			
			<table class="tbcss1" border=1 cellpadding=5>
				<tr>
					<td bgcolor="gray">아이디
					<td><input type="text" name="admin_id" class="admin_id" size="20">
				<tr>
					<td bgcolor="gray">암호
					<td><input type="password" name="pwd" class="pwd" size="20">
			</table>
			
			<div style="height:6"></div>

			<table>
				<tr>
					<td><input type="button" value="로그인" class="login">
					<td><input type="checkbox" value="y" name="is_login"> 아이디, 암호 기억
			</table>
		</form>
	</body>
</html>