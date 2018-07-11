<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Find a DIYer</title>
</head>
<body>
	<form action="finddiyers" method="post">
		<h1>Search for a DIYer by FirstName</h1>
		<p>
			<label for="firstname">FirstName</label>
			<input id="firstname" name="firstname" value="${fn:escapeXml(param.firstname)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<br/>
	<h1>Matching DIYers</h1>
		<table border="1">
			<tr>
				<th>UserName</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>DIYEvents</th>
			</tr>
			<c:forEach items="${diyers}" var="diyer">
				<tr>
					 <td><c:out value="${diyer.getUserName()}" /></td>
					 <td><c:out value="${diyer.getFirstName()}" /></td>
					 <td><c:out value="${diyer.getLastName()}" /></td>
					 <td><a href="diyevents?username=<c:out value="${diyer.getUserName()}" />"></a></td>
				</tr>
			</c:forEach>
		</table>
</body>
</html>