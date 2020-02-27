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
		<title>Contact 19.12.09</title>
		<script>
			$(document).ready(function(){
				$(".goContactRegForm").click(function(){
					checkContactRegForm();
				});
			});
			
			function checkContactRegForm(){
				var contact_name = "[name=contactContentForm] [name=contact_name]";
				var phone = "[name=contactContentForm] [name=phone]";
				var business_area_code = "[name=contactContentForm] [name=business_area_code]";
				
				if(is_empty(contact_name)){	// 만약 이름이 공백 또는 길이가 없으면
					alert("연락처명을 입력해주세요.");	// 경고를 출력한 뒤
					$(contact_name).focus();	// 커서를 이름 입력양식으로 세팅
					
					return;
				}
	
				if(is_empty(phone)){	// 만약 제목이 공백 또는 길이가 없으면
					alert("전화를 입력해주세요.");	// 경고를 출력한 뒤
					$(phone).focus();	// 커서를 제목 입력양식으로 세팅
					
					return;
				}
	
				if(is_empty(business_area_code)){	// 만약 이메일이 공백 또는 길이가 없으면
					alert("사업분야를 선택해주세요.");	// 경고를 출력한 뒤
					
					return;
				}

				$.ajax({
					url : "${ctRoot}/contactRegProc.do"	// 접속할 서버쪽 URL 주소 설정
					, type : "post"	// 전송 방법 설정
					, data : $("[name=contactContentForm]").serialize()	// 서버로 보낼 파라미터명과 파라미터값을 설정
					, success : function(contactRegCnt){	// 서버의 응답을 성공적으로 받았을 경우 실행할 익명함수
						// 매개변수 contactRegCnt에는 입력 행의 개수가 들어옴
						if(contactRegCnt==1){	// 연락처 입력 행 적용 개수가 1개면(insert 성공)
							alert("연락처 등록 성공!");
						} else{	// 그 외에는 경고
							alert("연락처 등록 실패!\n관리자에게 문의 바람.");
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
		<form name="contactContentForm" method="post" action="${ctRoot}/contactRegForm.do">
			<b>[연락처 등록]</b><br>
			<table class="tbcss1" width=500 border=1 cellspacing=0 cellpadding=5>
				<tr>
					<th width=60>연락처명
					<td width=150><input type="text" name="contact_name" class="contact_name"/>
				<tr>
					<th>전화
					<td><input type="text" name="phone" class="phone"/>
				<tr>
					<th>사업분야
					<td>
						<input type="checkbox" name="business_area_code" value="1">IT
						<input type="checkbox" name="business_area_code" value="2">통신
						<input type="checkbox" name="business_area_code" value="3">금융
						<input type="checkbox" name="business_area_code" value="4">기타
			</table><br>
			
			<div style="height:6"></div>
			
			<input type="button" class="goContactRegForm" value="등록">&nbsp;&nbsp;
			<a>[닫기]</a>
		</form>
	</body>
</html>