<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Find a Planner</title>
</head>
<body>
	<form action="findplanners" method="post">
		<h1>Search for a Planner by Company</h1>
		<p>
			<label for="company">Company</label>
			<input id="company" name="company" value="${fn:escapeXml(param.company)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<br/>
	<h1>Matching Planners</h1>
		<table border="1">
			<tr>
				<th>PlannerName</th>
				<th>Company</th>
				<th>Email</th>
				<th>Phone</th>
				<th>Update Phone</th>
			</tr>
			<c:forEach items="${planners}" var="planner" >
				<tr>
					<td><c:out value="${planner.getUserName()}" /></td>
					<td><c:out value="${planner.getCompany()}" /></td>
					<td><c:out value="${planner.getEmail()}" /></td>
					<td><c:out value="${planner.getPhone()}" /></td>
					<td><a href="updatephone?username=<c:out value="${planner.getUserName()}" />">Update</a></td>
				</tr>
			</c:forEach>
		</table>
</body>
</html>