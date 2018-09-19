<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="signin.css" rel="stylesheet">

<title>Find DIY Event</title>

</head>
<body style="background-image: url(https://images.unsplash.com/photo-1415025148099-17fe74102b28?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=1d2697309ae275b27d03270feb9d73e6&auto=format&fit=crop&w=2736&q=80)">
	<nav class="navbar navbar-expand-md">
      <a class="navbar-brand" style="color: #e6f7ff; font-size: 200%">EventSmart</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href="diyersignup" style="color: #e6f7ff">SignUp <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="diyersignin" style="color: #e6f7ff">SignIn</a>
          </li>
          <li class="nav-item">
            <a class="nav-link disabled" href="plannersignup" style="color: #e6f7ff">Planner</a>
          </li>
        </ul>
        <form class="form-inline mt-2 mt-md-0" action="finddiyers" method="post">
          <input id="firstname" name="firstname" class="form-control mr-sm-2" type="text" placeholder="Search a Friend" aria-label="Search" value="${fn:escapeXml(param.firstname)}">
          <button
          	class="btn btn-outline-light my-2 my-sm-0" 
          	style="color: #e6f7ff" 
          	type="submit">
          	Search
          	</button>
        </form>
      </div>
    </nav>
    <div style="margin-top: 100px"class="container theme-showcase" role="main">
	<form action="findevents" method="post">
		<h1 style="color: #e6f7ff">Search for an Event by UserName</h1>
		<p>
			<label for="username" class="sr-only">UserName</label>
			<input id="username" name="username" value="${fn:escapeXml(param.username)}" style="width: 400px" class="form-control" placeholder="UserName">
		</p>
		<p>
			<button
          	class="btn btn-outline-light my-2 my-sm-0" 
          	style="color: #e6f7ff" 
          	type="submit">
          	Find
          	</button>
			<br/><br/><br/>
			<span id="successMessage" style="color: #e6f7ff"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<br/>
	<h1 style="color: #e6f7ff">Matching Events</h1>
		<table class="table table-striped table-sm" style="color: #e6f7ff">
			<tr>
				<th>Event ID</th>
                <th>Theme</th>
                <th>Description</th>
                <th>List ID</th>
                <th>Created by User</th>
                <th>Update</th>
                <th>Delete</th>
                <th>Comments</th>
			</tr>
			<c:forEach items="${events}" var="diyEvent">
				<tr>
					 <td><c:out value="${diyEvent.getEventID()}" /></td>
					 <td><c:out value="${diyEvent.getTheme()}" /></td>
					 <td><c:out value="${diyEvent.getDescription()}" /></td>
					 <td><c:out value="${diyEvent.getListID()}" /></td>
					 <td><c:out value="${diyEvent.getUsername()}" /></td>
					 <td><a href="updateevents?eventid=<c:out value="${diyEvent.getEventID()}"/>">Update</a></td>
					 <td><a href="deleteevents?eventid=<c:out value="${diyEvent.getEventID()}"/>">Delete</a></td>
					 <td><a href="findcomments?eventid=<c:out value="${diyEvent.getEventID()}"/>">Get Comments</a></td>
					 
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>