<%--
    Document   : Source
    Created on : Nob 15, 2015, 10:35:33 AM
    Author     : Ahasanul Ashid, IBCS, And Abu Taleb, IBCS
--%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>
<%
	String getURL = request.getRequestURL().toString();
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Bootstrap 101 Template</title>

<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="resources/css/style.css" rel="stylesheet">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<style>

input[readonly], select, input[readonly='readonly'], input {
	background: #fff !important;
	color: #000 !important;
}
</style>

</head>
<body>
	<!-- principal comes from Spring Security -->
	<sec:authentication property="principal" var="anonymousUser" />


	<c:if test="${anonymousUser eq 'anonymousUser' }">

		<%
			response.sendRedirect("auth/login.do");
		%>

	</c:if>
	<c:if test="${anonymousUser ne 'anonymousUser' }">

		<span id="text-invitation">Welcome <b> <sec:authentication
					property="principal.username" />&nbsp;&nbsp;
		</b></span>
	</c:if>
	|&nbsp;&nbsp;
	<a href="<c:url value="/logout.do" />">Logout</a>
	<hr />
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand"
					href="${pageContext.request.contextPath}/common.do">SpringAcl.com</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="active"><a
						href="${pageContext.request.contextPath}/common.do">Home</a></li>

					<sec:authorize access="hasPermission(#employee, 'READ')"
						var="empread" />
					<sec:authorize access="hasPermission(#employee, 'WRITE')"
						var="empwrite" />
					<c:choose>
						<c:when test="${empread or empwrite}">

							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown" href="#">Employee <span class="caret"></span></a>
								<ul class="dropdown-menu">
									<li>
										<!-- #employee comes from servlet-context.xml --> 
										<sec:authorize
											access="hasPermission(#employee, 'READ')">
											<a href="${pageContext.request.contextPath}/employees.do">List
												of Employees</a>
										</sec:authorize>
									</li>

									<li>
										<!-- #employee comes from servlet-context.xml --> <sec:authorize
											access="hasPermission(#employee, 'WRITE')">
											<a href="${pageContext.request.contextPath}/add.do">Add
												Employee</a>
										</sec:authorize>
									</li>
								</ul></li>
						</c:when>
					</c:choose>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Page 2 <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">Page 2-1</a></li>
							<li><a href="#">Page 2-2</a></li>
							<li><a href="#">Page 2-3</a></li>
						</ul></li>

					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Page 3 <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">Page 3-1</a></li>
							<li><a href="#">Page 3-2</a></li>
							<li><a href="#">Page 3-3</a></li>
						</ul></li>

				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#"><span class="glyphicon glyphicon-user"></span>
							Sign Up</a></li>
					<li><a href="#"><span class="glyphicon glyphicon-log-in"></span>
							Login</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div style="padding: 23px;"></div>
	<hr />