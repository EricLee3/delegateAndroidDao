<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="com.service.sampleSelect" %>
<%@ page import="com.service.command.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.httpclient.methods.GetMethod" %>
<%@ page import="org.apache.commons.httpclient.methods.PostMethod" %>
<%@ page import="org.apache.commons.httpclient.HttpClient" %>
<%@ page import="org.apache.commons.httpclient.HttpException" %>
<%@page import="java.net.URLDecoder"%>
<%
	sampleSelect jsonDAO	= sampleSelect.getInstance();

	String command	= StringUtil.nullTo(request.getParameter("command"),"");

	String resultMessage = null;
	String strBrandNm = null;
	String strOutboundDate = null;
	
	if (command.equals("LoadData"))  {
		resultMessage = jsonDAO.selectBrand();
	} else if (command.equals("LoadStock"))  {
		strBrandNm = new String(request.getParameter("brandNm").getBytes("8859_1"), "UTF-8");
		resultMessage = jsonDAO.selectStock(strBrandNm);
	} else if (command.equals("LoadOutboundOrder"))  {
		strBrandNm = new String(request.getParameter("brandNm").getBytes("8859_1"), "UTF-8");
		strOutboundDate = request.getParameter("outboundDate");
		resultMessage = jsonDAO.selectOutbound(strBrandNm);
	}
	
%>
<%=resultMessage%>