<!-- JSP 기술의 한 종류인 Page Directive를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!-- 현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML이고, -->
<!-- 이 문서 안의 데이터는 UTF-8방식으로 인코딩한다라고 설정함 -->
<!-- 현재 이 JSP 페이지는 UTF-8 방식으로 인코딩한다. -->
<!-- UTF-8 인코딩 방식은 한글을 포함 전 세계 모든 문자열을 부호화할 수 있는 방법이다.-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- JSP 페이지에서 사용할 사용자 정의 태그인 JSTL의 C코어 태그 선언 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- JSP 페이지에서 사용할 사용자 정의 태그인 spring 폼 태그 선언 -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<!-- CSS파일 수입 -->
<link href="/z_spring/resources/common.css" rel="stylesheet" type="text/css">
<!-- JQuery 라이브러리 파일 수입 -->
<script src="/z_spring/resources/jquery-1.11.0.min.js" type="text/javascript"></script>
<script src="/z_spring/resources/common.js?ran=<%= Math.random() %>" type="text/javascript"></script>
<%@ page import="org.apache.log4j.*" %>

<!-- JSTL의 C코어 태그를 사용하여 변수들을 선언 -->
<!-- 변수 thColor1 선언하고, 문자열 #EAEAEA 저장 -->
<c:set var="thColor1" value="#EAEAEA"/>
<!-- 변수 headerColor 선언하고, 문자열 #CECECE 저장 -->
<c:set var="headerColor" value="#CECECE"/>
<c:set var="headerColor" value="#84FB84"/>
<!-- 변수 oddColor 선언하고, 문자열 white 저장 -->
<c:set var="oddColor" value="white"/>
<c:set var="oddColor" value="#F0FFF0"/>
<!-- 변수 evenColor 선언하고, 문자열 #EBF0F4 저장 -->
<c:set var="evenColor" value="#EBF0F4"/>
<c:set var="evenColor" value="#D2FFD2"/>
<!-- 변수 mouseOverColor 선언하고, 문자열 #CCE1FF 저장 -->
<c:set var="mouseOverColor" value="#CCE1FF"/>
<c:set var="mouseOverColor" value="#98FB98"/>
<!-- 변수 bodyBgColor1 선언하고, 문자열 #CCE1FF 저장 -->
<c:set var="bodyBgColor1" value="#F6F6F6"/>
<c:set var="bodyBgColor1" value="#F0FFF0"/>
<c:set var="ctRoot" value="/z_spring"/>

<script>
	$(document).ready(function(){
		if(location.href.indexOf("loginForm.do")<0){
			$("body").prepend("<center><input type=button value='로그아웃' onclick='location.replace(\"/z_spring/logout.do\");'></center>");
		}
		
		$("body").attr("bgcolor", "${bodyBgColor1}");
	});
</script>