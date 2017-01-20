<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.service.command.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.httpclient.methods.GetMethod" %>
<%@ page import="org.apache.commons.httpclient.methods.PostMethod" %>
<%@ page import="org.apache.commons.httpclient.HttpClient" %>
<%@ page import="org.apache.commons.httpclient.HttpException" %>
<%
	String resultMessage = null;
	response.getWriter().println();
	resultMessage = request.getAttribute("result").toString();
	//resultMessage = request.getParameter("result");
%>
<%=resultMessage%>