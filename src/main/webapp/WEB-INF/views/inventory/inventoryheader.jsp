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
<meta charset="utf-8" />
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/resources/assets/images/favicon.ico">
<title>${properties['project.title']}</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />


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

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/runnable.css" />


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
	src="${pageContext.request.contextPath}/resources/assets/js/jquery-ui.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.validate.min.js"></script>


<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

<script
	src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>

<!-- jqurey date-picker -->
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"> </script>
<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>


<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/js/centralStore/csCommon.js"></script>

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

body{
	background-color: #858585;
}

input[readonly], select, input[readonly='readonly'], input {
	background: #fff !important;
	color: #000 !important;
}

</style>


</head>
<body>
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
						class="glyphicon glyphicon-home"></i>
						${properties['project.inventory']}
				</small>
				</a>
			</div>


			<ul id="main-nav-menu" class="nav navbar-nav">

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Forms <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a
							href="${pageContext.request.contextPath}/inventory/showitemgroup.do">Add
								Item Category </a></li>

						<li><a
							href="${pageContext.request.contextPath}/inventory/showInventoryItem.do">Add
								New Item </a></li>
						<!-- single item opening balance insert successful 
						only but not client verified 
						** comment by Abu Taleb-->
						<%-- <li><a
							href="${pageContext.request.contextPath}/inventory/createOpeningBalance.do">Opening
								Balance</a></li> --%>
								
						
						<li><a
							href="${pageContext.request.contextPath}/inventory/getPhysicalStoreInventoryForm.do">
								Physical Store Inventory</a></li>
								
						<li><a
							href="${pageContext.request.contextPath}/inventory/physicalStoreInventory.do">
								Physical Store Inventory Process</a></li>
						<!-- Safty margin menu is moved to cs setting as per request by Tanzir vi -->
						<%-- <li><a
							href="${pageContext.request.contextPath}/settings/list/safetyMargin.do">
								Safety Margin </a></li> --%>

					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">List <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li class=""><a
							href="${pageContext.request.contextPath}/inventory/listItemgroup.do">Item
								Group</a></li>

						<li class=""><a
							href="${pageContext.request.contextPath}/inventory/listInventoryItem.do">Inventory
								Item</a></li>

						<!-- single item opening balance insert successful 
						only but not client verified 
						** comment by Abu Taleb-->
						<%-- <li><a
							href="${pageContext.request.contextPath}/inventory/listOpeningBalance.do">Opening
								Balance</a></li> --%>

						<li><a
							href="${pageContext.request.contextPath}/inventory/averagePriceList.do">Item
								Average Price</a></li>

						<li><a
							href="${pageContext.request.contextPath}/inventory/receivedReportList.do">Received
								Report (Price Set)</a></li>

						<%-- <li class=""><a
							href="${pageContext.request.contextPath}/inventory/listSubItemgroup.do">Item
								Sub Group</a></li>
						
						<li><a
							href="${pageContext.request.contextPath}/inventory/lookUpList.do">All
								Lookup</a></li>  --%>
					</ul></li>



				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Report <span class="caret"></span></a>
					<ul class="dropdown-menu" style="margin: 0; padding: 0;">
						<!--<li><a href="#">Inventory</a></li>-->
						<li><a
							href="${pageContext.request.contextPath}/inventory/averageControlReport.do">Item
								wise Evg Price Report</a></li>
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

		<!-- <div id="dynamic-menu"></div> -->

	</div>
	<div style="padding: 23px;"></div>




	<!-- -------------------------------------------------------------------------------------- -->