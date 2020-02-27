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
		<title>Board Read 19.11.29</title>
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
				* 댓글 쓰기 화면으로 이동
				*/
				$(".goBoardRegForm").click(function(){
					document.boardChildRegForm.submit();
				});
				
				/**
				* 게시판 수정 화면으로 이동
				*/
				$(".goBoardUpDelForm").click(function(){
					document.boardUpDelForm.submit();
				});
				
				/**
				* 게시판 목록 화면으로 이동
				*/
				$(".backList").click(function(){
					document.boardListForm.submit();
				});
				
				/**
				* 목록보기 선택 시 등록일, 조회수 유지 부분
				*/
				<c:forEach items="${paramValues.date}" var="date">
					$("[name=boardUpDelForm] [name=date]").filter("[value='${date}']").prop("checked", true);
				</c:forEach>
				<c:forEach items="${paramValues.count}" var="count">
					$("[name=boardUpDelForm] [name=count]").filter("[value='${count}']").prop("checked", true);
				</c:forEach>
				$("[name=boardUpDelForm]").hide();	// name=boardUpDelForm 숨김
				
				<c:forEach items="${paramValues.date}" var="date">
					$("[name=boardChildRegForm] [name=date]").filter("[value='${date}']").prop("checked", true);
				</c:forEach>
				<c:forEach items="${paramValues.count}" var="count">
					$("[name=boardChildRegForm] [name=count]").filter("[value='${count}']").prop("checked", true);
				</c:forEach>
				$("[name=boardChildRegForm]").hide();	// name=boardChildRegForm 숨김

				<c:forEach items="${paramValues.date}" var="date">
					$("[name=boardListForm] [name=date]").filter("[value='${date}']").prop("checked", true);
				</c:forEach>
				<c:forEach items="${paramValues.count}" var="count">
					$("[name=boardListForm] [name=count]").filter("[value='${count}']").prop("checked", true);
				</c:forEach>
				$("[name=boardListForm]").hide();	// name=boardUpDelForm 숨김
			});
			
			/**
			* 게시판 수정 화면으로 이동하는 함수 선언
			*/
			function goBoardUpDelForm(){
				// name=boardUpDelForm을 가진 form 태그의 action 값을 URL로 서버에 접속
				document.boardUpDelForm.submit();
			}
		</script>
	</head>
	<body><center><br>
		<!-- 1개의 게시판 내용을 출력하고 게시판 등록 화면으로 이동하는 form 태그 선언 -->
		<!-- ModelAndView 객체에 boardDTO라는 키값으로 저장된 -->
		<!-- BoardDTO 객체의 속성변수 안의 데이터를 꺼내어 출력 -->
		<%-- 꺼내는 방법은 EL 문법으로 ${boardDTO.속성변수} --%>
		<form class="boardContentForm" name="boardContentForm" method="post" action="${ctRoot}/boardRegForm.do">
			<input type="hidden" name="b_no" value="${boardDTO.b_no}}">
			<b>[글 상세 보기]</b><br>
			<table class="tbcss1" width=500 border=1 bordercolor=#DDDDDD cellspacing=0 cellpadding=5 align=center>
				<tr align=center>
					<th bgcolor="gray" width=60>글 번호
					<td width=150>${boardDTO.b_no}
					<th bgcolor="gray" width=60>조회수
					<td width=150>${boardDTO.readcount}
				<tr align=center>
					<th bgcolor="gray" width=60>작성자
					<td width=150>${boardDTO.writer}
					<th bgcolor="gray" width=60>작성일
					<td width=150>${boardDTO.reg_date}
				<tr align=center>
					<th bgcolor="gray">글제목
					<td width=150 colspan=3>${boardDTO.subject}
				<tr>
					<th bgcolor="gray">글내용
					<td width=150 colspan=3>
						<textarea name="content" rows="13" cols="45" style="border:0;resize:none;" readonly>${boardDTO.content}</textarea>
			</table><br>
			<table><tr height=3><td></table>
			<input type="button" class="goBoardRegForm" value="댓글쓰기">&nbsp;&nbsp;
			<input type="button" class="goBoardUpDelForm" value="수정/삭제">&nbsp;&nbsp;
			<input type="button" class="backList" value="목록보기">
		</form>
		
		<!-- 수정/삭제 화면으로 이동하기 위한 form 태그 선언 -->
		<form name="boardUpDelForm" method="post" action="${ctRoot}/boardUpDelForm.do">
			<!-- 게시판 상세보기 화면을 구성하는 글의 고유번호를 hidden 태그에 저장 -->
			<!-- 수정/삭제를 하려면 현재 글의 고유번호를 알아야하기 때문 -->
			<input type="text" name="b_no" value="${boardDTO.b_no}">
			<input type="text" name="selectPageNo" value="${param.selectPageNo}">
			<input type="text" name="rowCntPerPage" value="${param.rowCntPerPage}">
			<input type="text" name="keyword1" value="${param.keyword1}">
			<input type="checkbox" name="date" class="date" value="오늘">
			<input type="checkbox" name="date" class="date" value="어제">
			<input type="checkbox" name="date" class="date" value="이번 달">
			<input type="checkbox" name="date" class="date" value="이번 달 이외">
			<input type="checkbox" name="count" class="count" value="100">
		</form>
		
		<!-- 댓글쓰기 화면으로 이동하기 위한 form 태그 선언 -->
		<form name="boardChildRegForm" method="post" action="${ctRoot}/boardRegForm.do">
			<!-- 댓글쓰기 하려면 현재 글의 고유번호를 알아야하기 때문 -->
			<input type="text" name="b_no" value="${boardDTO.b_no}">
			<input type="text" name="selectPageNo" value="${param.selectPageNo}">
			<input type="text" name="rowCntPerPage" value="${param.rowCntPerPage}">
			<input type="text" name="keyword1" value="${param.keyword1}">
			<input type="checkbox" name="date" class="date" value="오늘">
			<input type="checkbox" name="date" class="date" value="어제">
			<input type="checkbox" name="date" class="date" value="이번 달">
			<input type="checkbox" name="date" class="date" value="이번 달 이외">
			<input type="checkbox" name="count" class="count" value="100">
		</form>
		
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