<%--
    Document   : Source
    Created on : Nob 15, 2015, 10:35:33 AM
    Author     : Ahasanul Ashid, IBCS, And Abu Taleb, IBCS
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>${properties['project.title']}</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/resources/assets/images/favicon.ico">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/font-awesome/4.2.0/css/font-awesome.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/fonts/fonts.googleapis.com.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/colorbox.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/ace.min.css"
	class="ace-main-stylesheet" id="main-ace-style" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/custom.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/dataTables.bootstrap.min.css" />

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.2.1.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/ace-extra.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>

<style type="text/css">
.dataTables_wrapper .row:first-child {
	padding-top: 8px;
	padding-bottom: 0px;
	background-color: white;
}

.dataTables_wrapper .row:last-child {
	border-bottom: 1px solid #e0e0e0;
	padding-top: 1px;
	padding-bottom: 12px;
	background-color: white;
}

.navbar .navbar-nav>li>.dropdown-menu>li>a {
	line-height: 1.5;
}

.ui-datepicker {
	background: #333;
	border: 1px solid #555;
	color: #EEE;
}

body {
	background-color: #858585;
}

input[readonly], select, input[readonly='readonly'], input {
	background: #fff !important;
	color: #000 !important;
}
</style>


<!-- jqurey date-picker  -->

<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<!-- Append Grid Js and CSS -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/appendGrid/jquery-ui.structure.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/appendGrid/jquery-ui.theme.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/appendGrid/jquery.appendGrid-1.6.2.css" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/appendGrid/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/appendGrid/jquery-ui-1.11.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/appendGrid/jquery.appendGrid-1.6.2.js"></script>
<!-- Custom JS for CS -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/js/centralStore/csCommon.js"></script>

<script>
	$(function() {
		$(".datepicker-13").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$(".datepicker-13").datepicker("option", "maxDate", new Date());
		$(".datepicker-13").datepicker("hide");
	});

	//For end date selection -- Ihteshamul Alam
	$(function() {
		$(".datepicker-14").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$(".datepicker-14").datepicker("hide");

		//BD style date selection -- Ashid
		$(function() {
			$(".datepicker-15").datepicker({
				dateFormat : 'dd-mm-yy'
			});
			$(".datepicker-15").datepicker("option", "maxDate", new Date());
			$(".datepicker-15").datepicker("hide");
		});
	});
</script>
</head>
<body>
	<div id="navbar" class="navbar navbar-default navbar-fixed-top"
		style="background-color: green">

		<div class="navbar-container" id="navbar-container">
			<button type="button" class="navbar-toggle menu-toggler pull-left"
				id="menu-toggler" data-target="#sidebar">
				<span class="sr-only">Toggle sidebar</span> <span class="icon-bar"></span>
				<span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>

			<div class="navbar-header pull-left">
				<a href="${pageContext.request.contextPath}/common.do"
					class="navbar-brand"> <small> <i
						class="glyphicon glyphicon-home"></i>
						${properties['project.budget']}
				</small>
				</a>
			</div>


			<ul class="nav navbar-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Form <span class="caret"></span></a>
					<ul class="dropdown-menu" style="margin: 0; padding: 0;">
						<li><a
							href="${pageContext.request.contextPath}/budget/addNewBudget.do">Budget</a></li>
						<li><a
							href="${pageContext.request.contextPath}/bgt/costCenterForm.do">Cost
								Centre</a></li>
						<li><a
							href="${pageContext.request.contextPath}/budget/generalItemUploadForm.do">Budget
								for General Items</a></li>
						<li><a
							href="${pageContext.request.contextPath}/bgt/appFormWithSession.do">Budget
								for APP</a></li>
						<li><a
							href="${pageContext.request.contextPath}/bgt/generalBudgetEntryForm.do">General
								Budget Entry</a></li>


					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">List <span class="caret"></span></a>
					<ul class="dropdown-menu" style="margin: 0; padding: 0;">
						<li><a
							href="${pageContext.request.contextPath}/budget/getBudgetList.do">Budget
								List</a></li>
						<li><a
							href="${pageContext.request.contextPath}/budget/getReciveBudgetList.do">Revice Budget List</a>
							</li>
								
						<li><a
							href="${pageContext.request.contextPath}/bgt/costCenterList.do">Cost
								Centre List</a></li>
						<li><a
							href="${pageContext.request.contextPath}/budget/generalItemList.do">Budget
								for General Items</a></li>
								
					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Report <span class="caret"></span></a>
					<ul class="dropdown-menu" style="margin: 0; padding: 0;">
						
						<li><a
							href="${pageContext.request.contextPath}/budget/budgetDetailsRpt.do">Budget
								Details</a></li>	
							<li><a
						href="${pageContext.request.contextPath}/budget/budgetCostSourceWiseRpt.do">Funding Source Wise Budget</a></li>
							<li><a
						href="${pageContext.request.contextPath}/budget/budgetExpCatRpt.do">Expenditure Category Wise Budget </a></li>
							<li><a
						href="${pageContext.request.contextPath}/budget/generalItemReport.do">Budget
							for General Item</a></li>	
						<li><a
							href="${pageContext.request.contextPath}/report/budget/appReport.do">Budget
								for APP</a></li>
						<li><a
							href="${pageContext.request.contextPath}/report/budget/varianceReport.do">Budget
								Variance Report</a></li>
					</ul></li>
			</ul>


			<div class="navbar-buttons navbar-header pull-right"
				role="navigation">
				<ul class="nav ace-nav">
					<li class="light-blue"><%@include
							file="../common/sessionUser.jsp"%>
						<ul
							class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">

							<li><a href="<c:url value="/myProfile.do" />"> <i
									class="fa fa-fw fa-eye"></i> My Profile
							</a></li>

							<li><a href="<c:url value="/logout.do" />"> <i
									class="ace-icon fa fa-power-off"></i> Logout
							</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
		<!-- /.navbar-container -->
	</div>
	<div style="padding: 23px;"></div>