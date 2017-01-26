<%-- <!-- Including heder.jsp -->
<%@include file="../common/adminheader.jsp"%>
<h1>Error Page</h1>
<p>Application has encountered an error. Please contact support on
	...</p>
</body>
</html> --%>


<%--
    Document   : Source
    Created on : April 04, 2016, 4:03:40 PM
    Author     : @Nasrin, IBCS
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/resources/assets/images/favicon.ico">
<title>${properties['project.title']}</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<!--multi select  -->


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css" />


<style type="text/css">
.error {
	font-size: 16px;
	color: white;
	font-style: italic;
}
</style>
</head>
<body>
	<div class="container-fluid icon-box"
		style="background-color: #858585;">
		<div class="col-md-offset-3 col-md-6">
			<div class="">
				<img style="width: 100%"
					src="${pageContext.request.contextPath}/resources/assets/images/404.png"
					alt="" />
				<p class="error">
					<strong>Massage: </strong>${errorMsg}.</p>
			</div>
		</div>
	</div>


	<%@include file="../common/ibcsFooter.jsp"%>