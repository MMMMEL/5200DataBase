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
<link href="navbar-top.css" rel="stylesheet">

<title>Find a DIYer</title>

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
            <a class="nav-link" href="plannersignup" style="color: #e6f7ff">Planner</a>
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
    <div style="margin-top: 100px" class="container theme-showcase" role="main">
	<form action="finddiyers" method="post">
		<h1 class="h3 mb-3 font-weight-normal" style="color: #e6f7ff">Search for a DIYer by FirstName</h1>
		<p>
			<label for="firstname" class="sr-only">FirstName</label>
			<input id="firstname" name="firstname" value="${fn:escapeXml(param.firstname)}" style="width: 400px" class="form-control" placeholder="First Name" required autofocus>
		</p>
		<p>
			<button
          	class="btn btn-outline-light my-2 my-sm-0" 
          	style="color: #e6f7ff" 
          	type="submit">
          	Search
          	</button>
			<br/><br/><br/>
			<span id="successMessage" style="color: #e6f7ff"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<br/>
	<h1 class="h3 mb-3 font-weight-normal" style="color: #e6f7ff">Matching DIYers</h1>
		<table class="table table-striped table-sm">
			<tr>
				<th style="color: #e6f7ff">UserName</th>
                <th style="color: #e6f7ff">FirstName</th>
                <th style="color: #e6f7ff">LastName</th>
                <th style="color: #e6f7ff">DIYEvents</th>
			</tr>
			<c:forEach items="${diyers}" var="diyer">
				<tr>
					 <td style="color: #e6f7ff"><c:out value="${diyer.getUserName()}" /></td>
					 <td style="color: #e6f7ff"><c:out value="${diyer.getFirstName()}" /></td>
					 <td style="color: #e6f7ff"><c:out value="${diyer.getLastName()}" /></td>
					 <td style="color: #e6f7ff"><a href="findevents?username=<c:out value="${diyer.getUserName()}" />">DIYer's Events</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>