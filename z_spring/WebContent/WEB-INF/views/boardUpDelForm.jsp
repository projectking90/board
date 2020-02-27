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
		<title>Board Update/Delete Form 2019.12.02</title>
		<script>
			$(document).ready(function(){
				/**
				* 만약에 상세보기할 게시판 글이 없으면 경고 이전 화면으로 이동
				*/
				<c:if test="${empty boardDTO}">
						alert("삭제된 게시글");
						document.boardListForm.submit();
				</c:if>
				
				/**
				* 수정 버튼 클릭 시
				*/
				$(".checkBoardUpdateForm").click(function(){
					boardUpDelForm($(this).attr("id"));
				});

				/**
				* 삭제 버튼 클릭 시
				*/
				$(".checkBoardDeleteForm").click(function(){
					boardUpDelForm($(this).attr("id"));
				});
				
				/**
				* 목록 보기 버튼 클릭 시
				*/
				$(".backList").click(function(){
					document.boardListForm.submit();
				})
				
				/**
				* 목록보기 선택 시 등록일, 조회수 유지 부분
				*/
				<c:forEach items="${paramValues.date}" var="date">
					$("[name=boardListForm] [name=date]").filter("[value='${date}']").prop("checked", true);
				</c:forEach>
				<c:forEach items="${paramValues.count}" var="count">
					$("[name=boardListForm] [name=count]").filter("[value='${count}']").prop("checked", true);
				</c:forEach>
				$("[name=boardListForm]").hide();	// name=boardListForm 숨김
			});
			
			function boardUpDelForm(upDel){
				var flag = false;
				var upDelFlag = $("[name=boardRegForm] [name=upDel]");
				
				if(upDel=="Up"){
					flag = checkBoardRegForm();
					upDelFlag.val("Up");
					
					if(flag==true && confirm("수정?")==false){
						return;
					}
				} else if(upDel=="Del"){
					flag = checkBoardPwdForm();
					upDelFlag.val("Del");
					
					if(flag==true && confirm("삭제?")==false){
						return;
					}
				}
				
				if(flag==true){
					/**
					* 현재 화면에서 페이지 이동 없이 서버쪽 "${ctRoot}/boardRegProc.do"을 호출하여
					* 게시판 글을 수정하고, 게시판 수정 행 적용 개수를 받기
					*/
					$.ajax({
						url : "${ctRoot}/boardUpDelProc.do"	// 접속할 서버쪽 URL 주소 설정
						, type : "post"	// 전송 방법 설정
						, data : $("[name=boardRegForm]").serialize()	// 서버로 보낼 파라미터명과 파라미터값을 설정
						, success : function(upDelCnt){	// 서버의 응답을 성공적으로 받았을 경우 실행할 익명함수
							// 매개변수 upDelCnt에는 수정/삭제 행의 개수가 들어옴
							if(upDel=="Up"){
								if(upDelCnt==1){	// 게시판 수정 행 적용 개수가 1개면(update 성공)
									alert("게시판 글 수정 성공!");
									location.replace("${ctRoot}/boardListForm.do");
								} else if(upDelCnt==-1){
									alert("게시판 글이 삭제되어 수정 불가!");
									location.replace("${ctRoot}/boardListForm.do");
								} else if(upDelCnt==-2){
									alert("비밀번호 틀림!");
								} else{	// 그 외에는 경고
									alert("게시판 글 수정 실패!\n관리자에게 문의 바람.");
								}
							} else if(upDel=="Del"){
								if(upDelCnt==1){	// 게시판 삭제 행 적용 개수가 1개면(delete 성공)
									alert("게시판 글 삭제 성공!");
									location.replace("${ctRoot}/boardListForm.do");
								} else if(upDelCnt==-1){
									alert("게시판 글 이미 삭제!");
									location.replace("${ctRoot}/boardListForm.do");
								} else if(upDelCnt==-2){
									alert("비밀번호 틀림!");
								} else if(upDelCnt==-3){
									alert("댓글이 있어 삭제 불가!");
								} else{	// 그 외에는 경고
									alert("게시판 글 삭제 실패!\n관리자에게 문의 바람.");
								}
							}
						}
						, error : function(){	// 서버의 응답을 못 받았을 경우 실행할 익명함수 설정
							alert("서버 접속 실패");
						}
					});
				}
			}
			
			/**
			* 게시판 등록 화면에서 입력된 데이터의 유효성 체크 함수 선언
			*/
			function checkBoardRegForm(){
				var name = "[name=boardRegForm] [name=writer]";
				var subject = "[name=boardRegForm] [name=subject]";
				var email = "[name=boardRegForm] [name=email]";
				var content = "[name=boardRegForm] [name=content]";
				var flag = true;
				
				if(is_empty(name)){	// 만약 이름이 공백 또는 길이가 없으면
					alert("이름을 입력해주세요.");	// 경고를 출력한 뒤
					$(name).focus();	// 커서를 이름 입력양식으로 세팅
					flag = false;
					
					return flag;
				}

				if(is_empty(subject)){	// 만약 제목이 공백 또는 길이가 없으면
					alert("제목을 입력해주세요.");	// 경고를 출력한 뒤
					$(subject).focus();	// 커서를 제목 입력양식으로 세팅
					flag = false;
					
					return flag;
				}

				if(is_empty(email)){	// 만약 이메일이 공백 또는 길이가 없으면
					alert("이메일을 입력해주세요.");	// 경고를 출력한 뒤
					$(email).focus();	// 커서를 이메일 입력양식으로 세팅
					flag = false;
					
					return flag;
				}
				
				if(is_valid_email(email)==false){
					alert("이메일 형식을 벗어남");
					flag = false;
					
					return flag;
				}

				if(is_empty(content)){	// 만약 내용이 공백 또는 길이가 없으면
					alert("내용을 입력해주세요.");	// 경고를 출력한 뒤
					$(content).focus();	// 커서를 내용 입력양식으로 세팅
					flag = false;
					
					return flag;
				}
				
				flag = checkBoardPwdForm();
				
				return flag;
			}
			
			function checkBoardPwdForm(){
				var pwd = "[name=boardRegForm] [name=pwd]";
				var flag = true;
				
				if(is_empty(pwd)){	// 만약 비밀번호를 공백 또는 길이가 없으면
					alert("비밀번호를 입력해주세요.");	// 경고를 출력한 뒤
					$(pwd).focus();	// 커서를 비밀번호 입력양식으로 세팅
					flag = false;
					
					return flag;
				}
				
				if(is_valid_pattern(
						pwd
						, /^[\d]{4}$/)==false){
					alert("암호는 숫자 4개를 입력");
					flag = false;
					
					return flag;
				}
				
				return flag;
			}
		</script>
	</head>
	<body><center><br>
		<!-- 게시판 등록 화면을 출력하는 form 태그 -->
		<%--
		<form method="post" name="boardRegForm" action="${ctRoot}/boardRegProc.do">
			<b>[글 수정/삭제]</b><br>
			<table class="tbcss1" border=1 bordercolor="gray" cellspacing=0 cellpadding=5 align=center>
				<tr>
					<th bgcolor="gray">이름
					<td><input type="text" size="10" maxlength="10" name="writer" value="${boardDTO.writer}">
				<tr>
					<th bgcolor="gray">제 목
					<td><input type="text" size="40" maxlength="50" name="subject" value="${boardDTO.subject}">
				<tr>
					<th bgcolor="gray">이메일
					<td><input type="text" size="40" maxlength="30" name="email" value="${boardDTO.email}">
				<tr>
					<th bgcolor="gray">내 용
					<td><textarea name="content" rows="13" cols="40" maxlength="500">${boardDTO.content}</textarea>
				<tr>
					<th bgcolor="gray">비밀번호
					<td><input type="password" size="6" maxlength="4" name="pwd">
			</table><br>
			
			<table><tr height=4><td></table>
			
			<input type="button" class="checkBoardUpdateForm" value="수정">
			<input type="button" class="checkBoardDeleteForm" value="삭제">
			<input type="reset" value="다시작성">
			<input type="button" class="backList" value="목록보기">
			
			<table><tr height=4><td></table>
			
			<input type="hidden" name="b_no" value="${boardDTO.b_no}">
		</form><br>
		--%>
		<!-- Spring Form 태그를 사용하여 html의 form 태그와 입력양식 관련 태그를 출력 -->
		<!-- 이때 DTO 객체나 Map 객체 등과 연결하면 이 객체 안의 데이터가 자동으로 입력양식에 삽입 -->
		<c:if test="${!empty boardDTO}">
			<form:form method="post" name="boardRegForm" commandName="boardDTO" action="${ctRoot}/boardRegProc.do">
				<b>[글 수정/삭제]</b><br>
				<table class="tbcss1" border=1 bordercolor="gray" cellspacing=0 cellpadding=5 align=center>
					<tr>
						<th bgcolor="gray">이름
						<td><form:input path="writer" className="writer"/>
						<!-- 위 Spring Form 태그는 아래처럼 변화 됨 -->
						<%--<input id="writer" name="writer" className="writer" type="text" value="글쓴이114"/> --%>
					<tr>
						<th bgcolor="gray">제 목
						<td><form:input path="subject" className="subject"/>
					<tr>
						<th bgcolor="gray">이메일
						<td><form:input path="email" className="email"/>
					<tr>
						<th bgcolor="gray">내 용
						<td><form:textarea path="content" className="content"/>
					<tr>
						<th bgcolor="gray">비밀번호
						<td><form:password path="pwd" className="pwd"/>
				</table><br>
				
				<table><tr height=4><td></table>
				
				<input type="hidden" name="upDel" value="Up">
				<input type="hidden" name="b_no" value="${boardDTO.b_no}">
				
				<input type="button" id="Up" class="checkBoardUpdateForm" value="수정">
				<input type="button" id="Del" class="checkBoardDeleteForm" value="삭제">
				<input type="reset" value="다시작성">
				<input type="button" class="backList" value="목록보기">
			</form:form>
		</c:if>
		<!-- 게시판 목록 화면으로 이동하는 form 태그 선언 -->
		<!-- form 태그 내부에서 클라이언트가 보낸 파라미터값을 입력양식에 저장 -->
		<!-- 파라미터값을 꺼내는 방법은 EL 문법을 이용 -->
		<%-- 꺼내는 방법은 EL 문법으로 ${param.속성변수} 또는 ${paramValues.속성변수} --%>
		<form name="boardListForm" method="post" action="${ctRoot}/boardListForm.do">
			<input type="text" name="selectPageNo" value="${param.selectPageNo}">
			<input type="text" name="rowCntPerPage" value="${param.rowCntPerPage}">
			<input type="text" name="keyword1" value="${param.keyword1}">
			<input type="checkbox" name="date" class="date" value="오늘">
			<input type="checkbox" name="date" class="date" value="어제">
			<input type="checkbox" name="date" class="date" value="이번 달">
			<input type="checkbox" name="date" class="date" value="이번 달 이외">
			<input type="checkbox" name="count" class="count" value="100">
		</form>
	</body>
</html>