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

<title>Create Your Event</title>

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
    <div style="margin-top: 100px"class="container theme-showcase" role="main">
	<h1 class="h3 mb-3 font-weight-normal" style="color: #e6f7ff">Create Event</h1>
	<form action="createevent" method="post" class="form-signin">
		<p>
			<label for="username" class="sr-only">UserName</label>
			<input id="username" name="username" value="${fn:escapeXml(param.username)}"  style="width: 400px" class="form-control" placeholder="UserName" required autofocus>
		</p>
		<p>
			<label for="theme" class="sr-only">Theme (Business, Casual, Holiday, Family)</label>
			<input id="theme" name="theme" value="" style="width: 400px" class="form-control" placeholder="Theme: Business, Casual, Holiday, Family" required>
		</p>
		<p>
			<label for="musicId" class="sr-only">Music ID</label>
			<input id="musicId" name="musicId" value="" style="width: 400px" class="form-control" placeholder="Music" required>
		</p>
		<p>
			<label for="movieId" class="sr-only">Movie ID</label>
			<input id="movieId" name="movieId" value="" style="width: 400px" class="form-control" placeholder="Movie" required>
		</p>
		<p>
			<label for="wineId" class="sr-only">Wine ID</label>
			<input id="wineId" name="wineId" value="" style="width: 400px" class="form-control" placeholder="Wine" required>
		</p>
		<p>
			<label for="restaurantId" class="sr-only">Restaurant ID</label>
			<input id="restaurantId" name="restaurantId" value="" style="width: 400px" class="form-control" placeholder="Restaurant" required>
		</p>		
		<p>
			<label for="giftId" class="sr-only">Gift ID</label>
			<input id="giftId" name="giftId" value="" style="width: 400px" class="form-control" placeholder="Gift" required>
		</p>
		<p>
			<label for="description" class="sr-only">Description</label>
			<input id="description" name="description" value="" style="width:500px;height:100px;" class="form-control" placeholder="Description" required>
		</p>
		
		<p>
			<button
          	class="btn btn-outline-light my-2 my-sm-0" 
          	style="color: #e6f7ff" 
          	type="submit">
          	Create
          	</button>
		</p>
	</form>
	<p>
		<span id="successMessage" style="color: #e6f7ff"><b>${messages.success}</b></span>
	</p>
	</div>
</body>
</html>