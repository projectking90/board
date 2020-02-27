<!-- JSP 기술의 한 종류인 Page Directive를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!-- 현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML이고, -->
<!-- 이 문서 안의 데이터는 UTF-8방식으로 인코딩한다라고 설정함 -->
<!-- 현재 이 JSP 페이지는 UTF-8 방식으로 인코딩한다. -->
<!-- UTF-8 인코딩 방식은 한글을 포함 전 세계 모든 문자열을 부호화할 수 있는 방법이다.-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.naver.erp.BoardSearchDTO" %>
<!-- JSP 기술의 한 종류인 Include Directive를 이용하여 common.jsp 파일 내의 소스를 삽입 -->
<%@ include file="/WEB-INF/views/common.jsp" %>
<!DOCTYPE html>

<!-- 로그인 화면 구성하는 HTML 태그 코딩하기 -->
<html>
	<head>
		<meta charset="UTF-8">
		<title>Board List 19.11.25 </title>
		<script>
			/**
				body 태그 안의 소스를 모두 실행한 후에 실행할 자스 코드 설정
			*/
			$(document).ready(function(){
				/**
				* 검색 버튼 누르면 호출되는 함수
				*/
				$(".goSearch").click(function(){
					goSearch();
				});
				
				/**
				* 모두 검색 버튼 누르면 호출되는 함수
				*/
				$(".goSearchAll").click(function(){
					//alert($("[name=boardListForm]").serialize());
					$("[name=boardListForm] :input").each(function(){
						if($(this).is(":checkbox") || $(this).is(":radio")){
							$(this).removeAttr("checked");
						}
						if($(this).is(":text")){
							$(this).removeAttr("value");
						}
					});
					
					/**
					* 선택페이지 번호와 페이지당 보여지는 행의 개수는 비우면 안되므로
					* 기본값을 넣어줌
					* 이게 없으면 DB 연동을 할 수 없이 때문
					*/
					$("[name=orAnd]").val("or");
					$("[name=selectPageNo]").val("1");
					$("[name=rowCntPerPage]").val("10");
					goSearch();
				});
				
				/**
				* 새 글쓰기 누르면 호출되는 함수
				*/
				$(".goBoardRegForm").click(function(){
					goBoardRegForm();
				});

				/**
				* 1개의 게시판 내용물을 보여주는 게시판 상세 보기 화면으로 이동하는 함수 선언
				*/
				$("[class=goBoardContentForm]").click(function(){
					var b_no = $(this).attr("id")
					var str = "b_no=" + b_no + "&" + $("[name=boardListForm]").serialize();
					
					location.replace("${ctRoot}/boardContentForm.do?" + str);
				});
				
				/**
				* name="rowCntPerPage"에 change이벤트가 발생하면 실행할 코드 설정하기
				*/
				$("[name=rowCntPerPage]").change(function(){
					goSearch();
				});
				
				/**
				* 게시글 수에 따라 페이징 처리하는 함수 호출
				*/
				$(".pagingNumber").html(
						getPagingNumber(
							"${boardListAllCnt}"	// 검색 결과 총 행 개수
							, "${boardSearchDTO.selectPageNo}"	// 선택된 현재 페이지 번호
							, "${boardSearchDTO.rowCntPerPage}"	// 페이지 당 출력행의 개수
							, "15"	// 페이지당 보여줄 페이지번호 개수
							, "goSearch()"	// 페이지 번호 클릭 후 실행할 자스코드
				));
				
				/**
				*	게시판 목록 화면으로 이동하는 함수 호출
				*/
				setTableTrBgColor(
					"boardTable"	// 테이블 class 값
					, "${headerColor}"	// 헤더 tr 배경색
					, "${oddColor}"	// 홀수행 배경색
					, "${evenColor}"	// 짝수행 배경색
					, "${mouseOverColor}"	// 마우스 over 시 배경색
				);

				/**
				* 목록보기 선택 시 등록일, 조회수 유지 부분
				*/
				<c:forEach items="${boardSearchDTO.date}" var="date">
					inputData("[name=boardHistoryForm] [name=date]", "${date}");
				</c:forEach>
				<c:forEach items="${boardSearchDTO.count}" var="count">
					inputData("[name=boardHistoryForm] [name=count]", "${count}");
				</c:forEach>
				//$("[name=boardHistoryForm]").hide();	// name=boardHistoryForm 숨김
			});
			
			/**
			* 게시판 목록 화면으로 이동하는 함수 선언
			*/
			function goSearch(){
				var obj = "[name=boardListForm] [name=keyword1]";
				if(is_empty(obj)){	// 만약 키워드가 공백 또는 길이가 없으면
					$(obj).val("");	// 길이 없는 데이터로 세팅
				}	// 공백 상태에서 서버로 전송되면 공백을 가지고 DB를 검색
				// 이 현상을 막기 위해 위의 조건문을 설정
				$(obj).val(removeSpace(obj));	// 키워드 앞뒤 공백 제거 후 다시 넣기

				document.boardListForm.submit();	// name="boardListForm"을 가진 form 태그의 action 값의 URL로 웹서버에 접속하기
			}
			
			/**
			* 게시판 입력 화면으로 이동하는 함수 선언
			*/
			function goBoardRegForm(){
				document.boardHistoryForm.submit();
			}
		</script>
	</head>
	<body><center><br><br><br><br><br><br>
		여기는 게시판 목록화면
		<c:if test="${!empty boardSearchDTO}">
			<form:form method="post" name="boardListForm" commandName="boardSearchDTO" action="${ctRoot}/boardListForm.do">
				<div>
					<!-- 키워드 검색 입력 양식 -->
					[키워드] :
					<form:input path="keyword1" className="keyword1"/>&nbsp;&nbsp;&nbsp;
					<form:select path="orAnd" className="orAnd">
						<form:option value="or" label="or"/>
						<form:option value="and" label="and"/>
					</form:select>&nbsp;&nbsp;&nbsp;
					<form:input path="keyword2" className="keyword2"/>&nbsp;&nbsp;&nbsp;
					<!-- 등록일로 게시판 글을 검색하는 조건 표현 -->
					[등록일] :
					<form:checkbox path="date" value="오늘" label="오늘"/>
					<form:checkbox path="date" value="어제" label="어제"/>
					<form:checkbox path="date" value="이번 달" label="이번 달"/>
					<form:checkbox path="date" value="이번 달 이외" label="이번 달 이외"/>&nbsp;&nbsp;&nbsp;
					<!-- 조회수로 게시판 글을 검색하는 조건 표현 -->
					[조회수] :
					<form:checkbox path="count" value="100" label="100"/>
					<!-- 버튼 표현하기 -->
					<input type="button" class="goSearch" value="검색">&nbsp;
					<!-- <input type="button" class="goSearchAll" value="모두검색">&nbsp; -->
					<form:button type="reset" class="goSearchAll">모두검색</form:button>
					<a class="goBoardRegForm">[새글쓰기]</a>&nbsp;&nbsp;
					<!-- 선택한 페이지번호가 저장되는 입력양식 표현 -->
					<!-- 선택한 페이지번호는 DB 연동시 아주 중요한 역할 -->
					<form:hidden path="selectPageNo" className="selectPageNo"/>
				</div><br>
				<table border=0 width=700>
				<tr>
					<td align=right>
						<!-- EL 문법으로 게시판 검색 총 개수 출력 -->
						<%-- ${boardListAllCnt}는 Controller 클래스 내부에 --%>
						<!-- ModelAndView 객체에 boardListAllCnt라는 키값으로 저장된 -->
						<!-- 데이터를 EL로 표현하여 삽입하라는 의미 -->
						<!-- 한 페이지에서 보이는 행의 개수가 저장되는 입력양식 표현 -->
						<!-- 행의 개수는 DB 연동시 아주 중요한 역할 -->
						[총 개수] : ${boardListAllCnt}&nbsp;&nbsp;&nbsp;&nbsp;
						<form:select path="rowCntPerPage">
							<form:option value="10" lable="10"/>
							<form:option value="15" lable="15"/>
							<form:option value="20" lable="20"/>
							<form:option value="25" lable="25"/>
							<form:option value="30" lable="30"/>
						</form:select> 행보기
			</table>
			</form:form>
		</c:if>
		
		<!-- 페이징 번호를 삽입할 span 태그 선언 -->
		<div>&nbsp;<span class="pagingNumber"></span>&nbsp;</div>
		<div style="height:6"></div>
		
		<!-- 게시판 목록 출력 -->
		<table class="boardTable" border=0 cellspacing=0 cellpadding=5 width=700>
			<tr><th>번호<th>제목<th>글쓴이<th>등록일<th>조회수
			<!-- 사용자 정의 태그인 JSTL Core 태그 중 <forEach>를 사용하여 -->
			<!-- ModelAndView 객체에 boardList 라는 키값으로 저장된 -->
			<!-- List<Map<String, String>> 객체 안의 데이터를 출력 -->
			<%-- <c:forEach> 태그 속성 설명 --%>
			<!-- items : ModelAndView 객체에 저장한 객체키값명, EL문법 사용, List Collection, Array가 들어감 -->
			<!-- var : for문 안에서 사용할 지역변수 -->
			<!-- varStatus : 루프 정보를 담고 있는 LoopTagStatus 객체 -->
			<c:forEach items="${requestScope.boardList}" var="board" varStatus="loopTagStatus">
				<!-- <tr style="cursor:pointer" bgcolor="${loopTagStatus.index%2==0 ? 'white' : '#C0C0C0'}" class="goBoardContentForm${board.b_no}"> -->
				<tr style="cursor:pointer" id="${board.b_no}" class="goBoardContentForm">
					<td align=center>
						<!-- 게시판 검색 목룩 중에 각 행의 역순 일련번호 출력 -->
						${boardListAllCnt-(boardSearchDTO.selectPageNo*boardSearchDTO.rowCntPerPage-boardSearchDTO.rowCntPerPage+1+loopTagStatus.index)+1}
						<!--
							정순 번호 출력 시 아래 코드로 대체 할 것
							${boardSearchDTO.selectPageNo*boardSearchDTO.rowCntPerPage-boardSearchDTO.rowCntPerPage+1+loopTagStatus.index}
						 -->
						 
					<td>
						<!-- 만약 들여쓰기 레벨번호가 0보다 크면 -->
						<!-- 레벨번호 만큼의 &nbsp;&nbsp;를 삽입 -->
						<c:if test="${board.print_level>0}">
							<c:forEach begin="0" end="${board.print_level}">
								&nbsp;&nbsp;
							</c:forEach>
							└▶
						</c:if>
						<!-- 게시판 검색 목록 중에 각 행의 제목 출력 -->
						${board.subject}
					<!-- 게시판 검색 목록 중에 각 행의 글쓴이 출력 -->
					<td align=center>${board.writer}
					<!-- 게시판 검색 목록 중에 각 행의 등록일 출력 -->
					<td align=center>${board.reg_date}
					<!-- 게시판 검색 목록 중에 각 행의 조회수 출력 -->
					<td align=center>${board.readcount}
			</c:forEach>
			<%--
				List<Map<String, String>> list = (List<Map<String, String>>) request.getAttribute("boardList");
			
				for(int i=0; i<list.size(); i++){
					Map<String, String> map = list.get(i);
					
					out.print("<tr>");
					out.print("<td>" + map.get("b_no"));
					out.print("<td>" + map.get("subject"));
					out.print("<td>" + map.get("writer"));
					out.print("<td>" + map.get("reg_date"));
					out.print("<td>" + map.get("readcount"));
				}
			--%>
		</table><br>
		<c:if test="${empty boardList}">
			검색된 데이터가 없습니다.
		</c:if>
		
		<%-- <c:if test="${!empty boardSearchDTO}">
			<form:form method="post" name="boardRegForm" commandName="boardSearchDTO" action="${ctRoot}/boardRegForm.do">
				<form:input path="keyword1" className="keyword1"/>
				<form:input path="orAnd" className="orAnd"/>
				<form:input path="keyword2" className="keyword2"/>
				<form:input path="date" className="date"/>
				<form:input path="count" className="count" label="100"/>
				<form:input path="selectPageNo" className="selectPageNo"/>
				<form:input path="rowCntPerPage" className="rowCntPerPage"/>
				<form:button type="reset">모두지움</form:button>
			</form:form>
		</c:if> --%>
		<!-- 게시판 목록 화면으로 이동하는 form 태그 선언 -->
		<!-- form 태그 내부에서 클라이언트가 보낸 파라미터값을 입력양식에 저장 -->
		<!-- 파라미터값을 꺼내는 방법은 EL 문법을 이용 -->
		<%-- 꺼내는 방법은 EL 문법으로 ${param.속성변수} 또는 ${paramValues.속성변수} --%>
		<form name="boardHistoryForm" method="post" action="${ctRoot}/boardRegForm.do">
			<input type="text" name="selectPageNo" value="${boardSearchDTO.selectPageNo}">
			<input type="text" name="rowCntPerPage" value="${boardSearchDTO.rowCntPerPage}">
			<input type="text" name="keyword1" value="${boardSearchDTO.keyword1}">
			<input type="text" name="orAnd" value="${boardSearchDTO.orAnd}">
			<input type="text" name="keyword2" value="${boardSearchDTO.keyword2}">
			<input type="checkbox" name="date" class="date" value="오늘">
			<input type="checkbox" name="date" class="date" value="어제">
			<input type="checkbox" name="date" class="date" value="이번 달">
			<input type="checkbox" name="date" class="date" value="이번 달 이외">
			<input type="checkbox" name="count" class="count" value="100">
		</form>
	</body>
</html>