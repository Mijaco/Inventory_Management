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
	});
	
	//BD style date selection -- Ashid
	$(function() {
		$(".datepicker-15").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$(".datepicker-15").datepicker("option", "maxDate", new Date());
		$(".datepicker-15").datepicker("hide");
	});
</script>

<style>
	body{
		background-color: #858585;
	}

input[readonly], select, input[readonly='readonly'], input {
	background: #fff !important;
	color: #000 !important;
}
</style>


<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/assets/images/favicon.ico">
<title>Dashboard - ${properties['project.title']}</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<!-- bootstrap & fontawesome -->
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
</style>


<!-- jqurey date-picker  -->

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js">
	
</script>
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


</head>

<body class="no-skin">
	<div id="navbar" class="navbar navbar-default navbar-fixed-top"
		style="background-color: green">
		<script type="text/javascript">
			try {
				ace.settings.check('navbar', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="navbar-container" id="navbar-container">
			<button type="button" class="navbar-toggle menu-toggler pull-left"
				id="menu-toggler" data-target="#sidebar">
				<span class="sr-only">Toggle sidebar</span> <span class="icon-bar"></span>

				<span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>

			<div class="navbar-header pull-left">
				<a href="${pageContext.request.contextPath}/common.do"
					class="navbar-brand"> <small> <i
						class="glyphicon glyphicon-flash"></i>
						${properties['project.admin.panel']}
				</small>
				</a>
			</div>
			
			<ul class="nav navbar-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Forms<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a
							href="${pageContext.request.contextPath}/addNewRole.do">Add New Role</a></li>
						<li><a
							href="${pageContext.request.contextPath}/adminpanel/getUserForm.do">Add New User</a></li>
<%-- 						<li><a
							href="${pageContext.request.contextPath}/pd/createProject.do">Add New
								Project</a></li> --%>
						
						<li><a
							href="${pageContext.request.contextPath}/inventory/showLookUp.do">
								All Lookup</a></li>
								
						<li><a
							href="${pageContext.request.contextPath}/settings/add/newApprovalHierarchyForm.do">
								Approval Hierarchy </a></li>

						<li><a
							href="${pageContext.request.contextPath}/settings/add/newDepartmentForm.do">
								Department </a></li>

						<li><a
							href="${pageContext.request.contextPath}/settings/add/newOperationForm.do">
								Operation </a></li>

						<li><a
							href="${pageContext.request.contextPath}/settings/add/newStateForm.do">
								State </a></li>

<!-- 						<li><a -->
<%-- 							href="${pageContext.request.contextPath}/settings/add/newStoreLedgerForm.do"> --%>
<!-- 								Store Ledger </a></li> -->

						<li><a
							href="${pageContext.request.contextPath}/settings/add/newStoreLocationForm.do">
								Store Location </a></li>
								
						<li><a
							href="${pageContext.request.contextPath}/cc/committeeConvenerForm.do">
								Committee Convener</a></li>
						
					</ul>
					
					<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">List<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<%-- <li><a
							href="${pageContext.request.contextPath}/pd/allProjectList.do">Projects</a></li> --%>
							
						<li><a
							href="${pageContext.request.contextPath}/inventory/lookUpList.do">All
								Lookup</a></li>
						<li><a
							href="${pageContext.request.contextPath}/settings/list/distinctApprovalHierarchy.do">Approval
								Hierarchy</a></li>

						<li><a
							href="${pageContext.request.contextPath}/settings/list/department.do">Department
						</a></li>

						<li><a
							href="${pageContext.request.contextPath}/settings/list/operation.do">
								Operation </a></li>

						<li><a
							href="${pageContext.request.contextPath}/settings/list/state.do">
								State </a></li>

						<!-- 							<li><a -->
						<%-- 								href="${pageContext.request.contextPath}/settings/list/storeLedger.do"> --%>
						<!-- 								Store Ledger </a></li> -->

						<li><a
							href="${pageContext.request.contextPath}/settings/list/storeLocation.do">
								Store Location </a></li>
								
						<li><a
							href="${pageContext.request.contextPath}/cc/committeeConvenerList.do">
								Committee Convener </a></li>

						
					</ul></li>
			</ul>
			
			<div class="navbar-buttons navbar-header pull-right"
				role="navigation">
				<ul class="nav ace-nav">
					<!-- ---------------------------------------------------------------- -->
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

					<!-- ---------------------------------------------------------------- -->
				</ul>
			</div>
		</div>
		<!-- /.navbar-container -->
	</div>
	<div style="padding: 23px;"></div>
	<!-- ------------------------------------------------------------------------------------------------------------------------------------------------------------ -->