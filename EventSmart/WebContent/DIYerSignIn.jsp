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

<title>User Sign In</title>

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
	<h1 class="h3 mb-3 font-weight-normal" style="color: #e6f7ff">Please Sign In</h1>
	<form action="diyersignin" method="post" class="form-signin">
		<p>
			<label for="username" class="sr-only">UserName</label>
			<input id="username" name="username" value="" style="width: 400px" class="form-control" placeholder="UserName" required autofocus>
		</p>
		<p>
			<label for="password" class="sr-only">Password</label>
			<input id="password" name="password" value="" style="width: 400px" class="form-control" placeholder="Password" type="password" required>
		</p>
		<p>
			<button
          	class="btn btn-outline-light my-2 my-sm-0" 
          	style="color: #e6f7ff" 
          	type="submit">
          	Log In
          	</button>
		</p>
	</form>
	<p>
		<span id="successMessage" style="color: #e6f7ff"><b>${messages.success}</b></span>
	</p>
	<br/>
	<div <c:if test="${messages.notSignin}">style="display:none"</c:if>>
	<h1 class="h3 mb-3 font-weight-normal" style="color: #e6f7ff">My Profile</h1>
		<table class="table table-striped table-sm">
			<tr>
				<th style="color: #e6f7ff">UserName</th>
				<th style="color: #e6f7ff">FirstName</th>
				<th style="color: #e6f7ff">LastName</th>
				<th style="color: #e6f7ff">Phone</th>
				<th style="color: #e6f7ff">Update Phone</th>
				<th style="color: #e6f7ff">My Events</th>
				<th style="color: #e6f7ff">My Reservations</th>
			</tr>
			<tr>
				<td style="color: #e6f7ff"><c:out value="${diyer.getUserName()}" /></td>
				<td style="color: #e6f7ff"><c:out value="${diyer.getFirstName()}" /></td>
				<td style="color: #e6f7ff"><c:out value="${diyer.getLastName()}" /></td>
				<td style="color: #e6f7ff"><c:out value="${diyer.getPhone()}" /></td>
				<td style="color: #e6f7ff"><a href="updatephone?username=<c:out value="${diyer.getUserName()}" />">Update</a></td>
				<td style="color: #e6f7ff"><a href="findevents?username=<c:out value="${diyer.getUserName()}" />">My Events</a></td>
				<td style="color: #e6f7ff"><a href="diyerreservation?diyername=<c:out value="${diyer.getUserName()}" />">My Reservations</a></td>
			</tr>
		</table>
		<div id="createEvent"><a class="btn btn-sm btn-outline-secondary" href="createevent?username=<c:out value="${diyer.getUserName()}" />" style="color: #e6f7ff">Create an Event</a></div>
		<br>
		<div id="cancelAccount"><a class="btn btn-sm btn-outline-secondary" href="diyerdelete?username=<c:out value="${diyer.getUserName()}" />" style="color: #e6f7ff">Cancel Account</a></div>
	</div>
	</div>
</body>
</html>