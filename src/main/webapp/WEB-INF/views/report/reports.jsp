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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>${properties['project.title']}</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<!-- bootstrap & fontawesome -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/font-awesome/4.2.0/css/font-awesome.min.css" />

<!-- page specific plugin styles -->

<!-- text fonts -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/fonts/fonts.googleapis.com.css" />

<!-- page specific plugin styles -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/colorbox.min.css" />


<!-- ace styles -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/ace.min.css"
	class="ace-main-stylesheet" id="main-ace-style" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/custom.css" />

<!--[if lte IE 9]>
			<link rel="stylesheet" href="assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
		<![endif]-->

<!--[if lte IE 9]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

<!-- inline styles related to this page -->

<!-- ace settings handler -->
<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.2.1.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/ace-extra.min.js"></script>

<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

<!--[if lte IE 8]>
		<script src="assets/js/html5shiv.min.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->

<!-- page specific plugin scripts -->
<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>

</head>
<body>

	<div id="navbar" class="navbar navbar-default"
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
						${properties['project.reports']}
				</small>
				</a>
			</div>

			<ul class="nav navbar-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Central Store <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a
							href="${pageContext.request.contextPath}/report/receivingReport.do">Receiving
								Report</a></li>
								
                        <li><a
							href="${pageContext.request.contextPath}/report/monthlyReceivingReport.do">Monthly Receiving
								Report Statement</a></li>
						<li><a
							href="${pageContext.request.contextPath}/report/requisitionReport.do">Store
								Requisition Report</a></li>
						<li><a
							href="${pageContext.request.contextPath}/report/returnSlipReport.do">Return
								Slip Report</a></li>
						<li><a
							href="${pageContext.request.contextPath}/report/csStoreTicketIssueReport.do">Store
								Ticket Issue Report</a></li>
						<li><a
							href="${pageContext.request.contextPath}/report/csStoreTicketReceiveReport.do">Store
								Ticket Receive Report</a></li>
						<li><a
							href="${pageContext.request.contextPath}/report/csStoreTicketReturnReport.do">Store
								Ticket Return Report</a></li>
						<li><a
							href="${pageContext.request.contextPath}/report/ledgerReport.do">Ledger
								Report</a></li>
						<li><a
							href="${pageContext.request.contextPath}/report/stockReport.do">Stock
								Report</a></li>
						<li><a
							href="${pageContext.request.contextPath}/report/csVehiclePermissionReport.do">Vehicle
								Permission Report</a></li>
						<li><a
							href="${pageContext.request.contextPath}/report/gatePassReport.do">Gate
								Pass Report</a></li>
					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Sub Store <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a
							href="${pageContext.request.contextPath}/report/conToSsRequisitionReport.do">Requisition Report (Con To Ss)</a></li>
							<li><a
							href="${pageContext.request.contextPath}/report/conToSsRequisitionStoreTicketReport.do">Requisition Store Ticket Report (Con To Ss)</a></li>
							<li><a
							href="${pageContext.request.contextPath}/report/conToSsReturnSlipReport.do">Return Slip Report (Con To Ss)</a></li>
														<li><a
							href="${pageContext.request.contextPath}/report/conToSsReturnStoreTicketReport.do">Return Store Ticket Report (Con To Ss)</a></li>
							
							<li><a
							href="${pageContext.request.contextPath}/report/lsToSsGatePassReport.do">Gate Pass Report (Ls To Ss)</a></li>
							
													<li><a
							href="${pageContext.request.contextPath}/report/requisitionReportLsToSs.do">Requisition Report (Ls To Ss)</a></li>
							<li><a
							href="${pageContext.request.contextPath}/report/lsToSsReturnSlipReport.do">Return Slip Report (Ls To Ss)</a></li>
							<li><a
							href="${pageContext.request.contextPath}/report/lsToSsReturnStoreTicketReport.do">Return Store Ticket Report (Ss To Cs)</a></li>
														<li><a
							href="${pageContext.request.contextPath}/report/ssToCsReturnStoreTicketReport.do">Sub Store Ledger Report</a></li>

					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Local Store <span class="caret"></span></a>
					<ul class="dropdown-menu">
                    <li><a
				    href="${pageContext.request.contextPath}/report/lsGtPassReport.do">Gate Pass Report (Con To Ls)</a></li>
				    <li><a
				    href="${pageContext.request.contextPath}/report/lsRequisitionReport.do">Requisition Report (Con To Ls)</a></li>
				    <li><a
				    href="${pageContext.request.contextPath}/report/requisitionStoreTicketReport.do">Requisition Store Ticket Report (Con To Ls)</a></li>
                   <li><a
				    href="${pageContext.request.contextPath}/report/lsReturnSlipReport.do">Return Slip Report (Con To Ls)</a></li>
				    <li><a
				    href="${pageContext.request.contextPath}/report/returnStoreTicketReport.do">Return Store Ticket Report (Con To Ls)</a></li>
				    <li><a
				    href="${pageContext.request.contextPath}/report/lsLedgerReport.do">Local Store Ledger Report</a></li>
					</ul></li>
					
					<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Workshop <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<!--<li><a href="#">Incoming Products</a></li>  -->
						<!--<li><a href="#">Vendor Bills</a></li>  -->
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Contractor <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/report/conMaterialReport.do">Material 
						List Report</a></li>
						<li><a href="${pageContext.request.contextPath}/report/conJobReport.do">Job 
						List Report</a></li>
					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Procurement <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#">Incoming Products</a></li>
						<li><a href="#">Vendor Bills</a></li>
					</ul></li>

            		<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Inventory <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/report/averageControlReport.do">Item wise Evg Price Report</a></li>
						<li><a href="${pageContext.request.contextPath}/report/storeWiseItemReport.do">Store wise item Report</a></li>
						<!--<li><a href="#">Vendor Bills</a></li>-->
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

	<!-- -------------------------------------------------------------------------------------- -->
	<div class="container-fluid icon-box"
		style="background-color: #000020;"></div>

	<!-- -------------------------------------------------------------------------------------- -->

	<div class=""
		style="background-color: green; padding: 15px; color: white">
		<div class="center-block text-center">
			<div class="">
				<span class="bigger-60"> <span>&copy; </span> 2015, DESCO.
					All rights reserved.
				</span> &nbsp; &nbsp; &nbsp; &nbsp; Design & Developed By <span> <a
					href="http://www.ibcs-primax.com" target="_blank"
					style="color: white; text-decoration: none; font-style: italic; font-weight: 500;">
						IBCS-PRiMAX Software (Bangladesh) Ltd. </a>
				</span>
			</div>
		</div>
	</div>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>

</body>
</html>