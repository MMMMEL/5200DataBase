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

<title>Find a Planner</title>

</head>
<body style="background-image: url(https://images.unsplash.com/photo-1415025148099-17fe74102b28?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=1d2697309ae275b27d03270feb9d73e6&auto=format&fit=crop&w=2736&q=80)">
	<nav class="navbar navbar-expand-md">
      <a class="navbar-brand" style="color: #e6f7ff; font-size: 200%">EventSmart Planner</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link" href="plannersignup" style="color: #e6f7ff">SignUp</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="diyersignup" style="color: #e6f7ff">Not a Planner?</a>
          </li>
        </ul>
        <form class="form-inline mt-2 mt-md-0" action="findplanners" method="post">
          <input id="company" name="company" class="form-control mr-sm-2" type="text" placeholder="Search a Planner" aria-label="Search" value="${fn:escapeXml(param.company)}">
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
	<form action="findplanners" method="post">
		<h1 class="h3 mb-3 font-weight-normal" style="color: #e6f7ff">Search for a Planner by Company</h1>
		<p>
			<label for="company" class="sr-only">Company</label>
			<input id="company" name="company" value="${fn:escapeXml(param.company)}" style="width: 400px" class="form-control" placeholder="UserName" required autofocus>
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
	<h1 style="color: #e6f7ff">Matching Planners</h1>
		<table class="table table-striped table-sm">
			<tr>
				<th style="color: #e6f7ff">PlannerName</th>
				<th style="color: #e6f7ff">Company</th>
				<th style="color: #e6f7ff">Email</th>
				<th style="color: #e6f7ff">Phone</th>
				<th style="color: #e6f7ff">Liked</th>
				<th style="color: #e6f7ff">Make Reservation</th>
				<th style="color: #e6f7ff">Like This Planner?</th>
			</tr>
			<c:forEach items="${planners}" var="planner" >
				<tr>
					<td style="color: #e6f7ff"><c:out value="${planner.getUserName()}" /></td>
					<td style="color: #e6f7ff"><c:out value="${planner.getCompany()}" /></td>
					<td style="color: #e6f7ff"><c:out value="${planner.getEmail()}" /></td>
					<td style="color: #e6f7ff"><c:out value="${planner.getPhone()}" /></td>
					<td id="voteNum" style="color: #e6f7ff"><c:out value="${planner.getLiked()}" /></td>
					<td style="color: #e6f7ff"><a href="makereservation?plannername=<c:out value="${planner.getUserName()}" />">Reserve</a></td>
					<td>
						<button
			          	class="btn btn-outline-light my-2 my-sm-0" 
			          	style="color: #e6f7ff" 
			          	type="button"
			          	onclick="window.location.href='voteplanner?plannername=<c:out value="${planner.getUserName()}" />'">
			          	Vote
			          	</button>
			          	<!--  -->
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<!-- Bootstrap -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>
	<script src="js/vote.js"></script>
</body>
</html>