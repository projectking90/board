<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String uid = request.getParameter("uid");
	String pwd = request.getParameter("pwd");
	
	if(uid.equals("abc") && pwd.equals("123")){
		out.print("<script>alert('로그인 성공');</script>");
	} else{
		out.print("<script>alert('로그인 실패'); location.replace('loginForm.do');</script>");
	}
%>
<center>환영<br>
<a href="loginForm.do">[로그인 화면 이동]</a></center>