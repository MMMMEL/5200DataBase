<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Sign Up</title>
</head>
<body>
<h1>Planner Sign Up</h1>
	<form action="plannersignup" method="post">
		<p>
			<label for="plannername">PlannerName</label>
			<input id="plannername" name="plannername" value="">
		</p>
		<p>
			<label for="password">Password</label>
			<input id="password" name="password" value="">
		</p>
		<p>
			<label for="email">Email</label>
			<input id="email" name="email" value="">
		</p>
		<p>
			<label for="firstname">FirstName</label>
			<input id="firstname" name="firstname" value="">
		</p>
		<p>
			<label for="lastname">LastName</label>
			<input id="lastname" name="lastname" value="">
		</p>
		<p>
			<label for="phone">Phone</label>
			<input id="phone" name="phone" value="">
		</p>
		<p>
			<label for="company">Company</label>
			<input id="company" name="company" value="">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>