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

<title>Make Reservation</title>

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
    <div style="margin-top: 100px" class="container theme-showcase" role="main">
	<form action="makereservation" method="post">
		<h1 class="h3 mb-3 font-weight-normal" style="color: #e6f7ff">Make a Reservation</h1>
		<p>
			<label for="plannername" class="sr-only">PlannerName</label>
			<input id="plannername" name="plannername" value="${fn:escapeXml(param.plannername)}" style="width: 400px" class="form-control" placeholder="Planner Name" required autofocus>
		</p>
		<p>
			<label for="diyername" class="sr-only">UserName</label>
			<input id="diyername" name="diyername" value="" style="width: 400px" class="form-control" placeholder="UserName" required>
		</p>
		<p>
			<label for="year" class="sr-only">Year</label>
			<input id="year" name="year" value="" style="width: 400px" class="form-control" placeholder="Year" required>
		</p>
		<p>
			<label for="month" class="sr-only">Month</label>
			<input id="month" name="month" value="" style="width: 400px" class="form-control" placeholder="Month" required>
		</p>
		<p>
			<label for="day" class="sr-only">Day</label>
			<input id="day" name="day" value="" style="width: 400px" class="form-control" placeholder="Day" required>
		</p>
		<p>
			<button
          	class="btn btn-outline-light my-2 my-sm-0" 
          	style="color: #e6f7ff" 
          	type="submit">
          	Reserve
          	</button>
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage" style="color: #e6f7ff"><b>${messages.success}</b></span>
	</p>
</body>
</html>