<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<center>

	<p>测试中文</p>
	<h2>Hello World!</h2>
	<h3>
		<a href="hello?name=bobo.huang">点击跳转</a>
	</h3>
	<shiro:user>
		<h3>
		欢迎【<shiro:principal/>】登录，<a href="logout">注销</a>
		</h3>
	</shiro:user>
</center>
</body>
</html>
