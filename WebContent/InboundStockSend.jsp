<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<script>

//window.onload = callTest();

function callTest(){
	document.getElementById("call1").submit();

}
</script>
<body>
success!!

<form id="call1" action="http://localhost:9080/delegateAndroidDao/inboundStock2Magento" method="post">  
<!--  <form id="call1" action="https://wmsapi.isecommerce.co.kr:3133/recvApi" method="post">-->

<input type="button" value="전송" onclick="callTest()" />
<p>
<p>

<textarea id="msg" form="call1" name="msg" rows="30" cols="50" >

</textarea>


</form>
</body>
</html>