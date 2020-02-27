<!-- JSP 기술의 한 종류인 Page Directive를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!-- 현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML이고, -->
<!-- 이 문서 안의 데이터는 UTF-8방식으로 인코딩한다라고 설정함 -->
<!-- 현재 이 JSP 페이지는 UTF-8 방식으로 인코딩한다. -->
<!-- UTF-8 인코딩 방식은 한글을 포함 전 세계 모든 문자열을 부호화할 수 있는 방법이다.-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- JSP 기술의 한 종류인 Include Directive를 이용하여 common.jsp 파일 내의 소스를 삽입 -->
<%@ include file="/WEB-INF/views/common.jsp" %>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>New Board Write</title>
		<script>
			$(document).ready(function(){
				/**
				* 새 글쓰기 버튼 선택 시
				*/
				$(".checkBoardRegForm").click(function(){
					checkBoardRegForm();
				});
				/**
				* 목록보기 버튼 선택 시
				*/
				$(".backList").click(function(){
					document.boardListForm.submit();
				});
			});
			
			/**
			* 게시판 등록 화면에서 입력된 데이터의 유효성 체크 함수 선언
			*/
			function checkBoardRegForm(){
				var name = "[name=boardRegForm] [name=writer]";
				var subject = "[name=boardRegForm] [name=subject]";
				var email = "[name=boardRegForm] [name=email]";
				var content = "[name=boardRegForm] [name=content]";
				var pwd = "[name=boardRegForm] [name=pwd]";
				
				if(is_empty(name)){	// 만약 이름이 공백 또는 길이가 없으면
					alert("이름을 입력해주세요.");	// 경고를 출력한 뒤
					$(name).focus();	// 커서를 이름 입력양식으로 세팅
					
					return;
				}

				if(is_empty(subject)){	// 만약 제목이 공백 또는 길이가 없으면
					alert("제목을 입력해주세요.");	// 경고를 출력한 뒤
					$(subject).focus();	// 커서를 제목 입력양식으로 세팅
					
					return;
				}

				if(is_empty(email)){	// 만약 이메일이 공백 또는 길이가 없으면
					alert("이메일을 입력해주세요.");	// 경고를 출력한 뒤
					$(email).focus();	// 커서를 이메일 입력양식으로 세팅
					
					return;
				}
				
				if(is_valid_email(email)==false){
					alert("이메일 형식을 벗어남");
					return;
				}

				if(is_empty(content)){	// 만약 내용이 공백 또는 길이가 없으면
					alert("내용을 입력해주세요.");	// 경고를 출력한 뒤
					$(content).focus();	// 커서를 내용 입력양식으로 세팅
					
					return;
				}

				if(is_empty(pwd)){	// 만약 비밀번호를 공백 또는 길이가 없으면
					alert("비밀번호를 입력해주세요.");	// 경고를 출력한 뒤
					$(pwd).focus();	// 커서를 비밀번호 입력양식으로 세팅
					
					return;
				}
				
				if(is_valid_pattern(
						pwd
						, /^[\d]{4}$/)==false){
					alert("암호는 숫자 4개를 입력");
					return;
				}
				
				if(confirm("저장?")==false){
					return;
				}
				
				/**
				* 현재 화면에서 페이지 이동 없이 서버쪽 "${ctRoot}/boardRegProc.do"을 호출하여
				* 게시판 글을 입력하고, 게시판 입력 행 적용 개수를 받기
				*/
				$.ajax({
					url : "${ctRoot}/boardRegProc.do"	// 접속할 서버쪽 URL 주소 설정
					, type : "post"	// 전송 방법 설정
					, data : $("[name=boardRegForm]").serialize()	// 서버로 보낼 파라미터명과 파라미터값을 설정
					, success : function(boardRegCnt){	// 서버의 응답을 성공적으로 받았을 경우 실행할 익명함수
						// 매개변수 boardRegCnt에는 입력 행의 개수가 들어옴
						if($("[name=boardRegForm] [name=b_no]").val() == 0){
							if(boardRegCnt==1){	// 게시판 입력 행 적용 개수가 1개면(insert 성공)
								alert("게시판 글 등록 성공!");
								location.replace("${ctRoot}/boardListForm.do");
							} else{	// 그 외에는 경고
								alert("게시판 글 등록 실패!\n관리자에게 문의 바람.");
							}
						} else{
							if(boardRegCnt==1){	// 댓글 입력 행 적용 개수가 1개면(insert 성공)
								alert("댓글 등록 성공!");
								location.replace("${ctRoot}/boardListForm.do");
							} else{	// 그 외에는 경고
								alert("댓글 등록 실패!\n관리자에게 문의 바람.");
							}
						}
					}
					, error : function(){	// 서버의 응답을 못 받았을 경우 실행할 익명함수 설정
						alert("서버 접속 실패");
					}
				});
			}
		</script>
	</head>
	<body><center><br>
		<!-- 게시판 등록 화면을 출력하는 form 태그 -->
		<form method="post" name="boardRegForm" action="${ctRoot}/boardRegProc.do">
			<!-- <b>${empty param.b_no ? "[새 글쓰기]" : "[댓글 쓰기]"}</b><br>  -->
			<c:choose>
				<c:when test="${empty param.b_no}">
					<b>[새 글쓰기]</b><br>
				</c:when>
				<c:otherwise>
					<b>[댓글 쓰기]</b><br>
				</c:otherwise>
			</c:choose>
			<table class="tbcss1" border=1 bordercolor=gray cellspacing=0 cellpadding=5 align=center>
				<tr>
					<th bgcolor="gray">이름
					<td><input type="text" size="10" maxlength="10" name="writer">
				<tr>
					<th bgcolor="gray">제 목
					<td><input type="text" size="40" maxlength="50" name="subject">
				<tr>
					<th bgcolor="gray">이메일
					<td><input type="text" size="40" maxlength="30" name="email">
				<tr>
					<th bgcolor="gray">내 용
					<td><textarea name="content" rows="13" cols="40" maxlength="500"></textarea>
				<tr>
					<th bgcolor="gray">비밀번호
					<td><input type="password" size="6" maxlength="4" name="pwd">
			</table><br>
			<input type="button" class="checkBoardRegForm" value="저장">
			<input type="reset" value="다시작성">
			<input type="button" class="backList" value="목록보기">
			
			<input type="hidden" name="b_no" value="${(empty param.b_no) ? 0 : param.b_no}">
		</form>
	</body>
</html>