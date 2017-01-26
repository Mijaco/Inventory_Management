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
	//For end date selection -- Ihteshamul Alam
	$(function() {
		$(".datepicker-14").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$(".datepicker-14").datepicker("hide");
	});
</script>


</head>
<body>
	<!-- <div id="navbar" class="navbar navbar-default navbar-fixed-top" style="background-color: #A24689"> -->
	<div id="navbar" class="navbar navbar-default navbar-fixed-top"
		style="background-color: green;">
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
						${properties['project.centeral.store']}
				</small>
				</a>
			</div>

			<ul class="nav navbar-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Forms <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<%-- <li><a
							href="${pageContext.request.contextPath}/cs/storeTicket/requisitionForm.do">Store
								Ticket Requisiton</a></li>
						<li><a
							href="${pageContext.request.contextPath}/cs/storeTicket/returnForm.do">Store
								Ticket Return</a></li>
						<li><a
							href="${pageContext.request.contextPath}/cs/store/returnSlipForm.do">Return
								Slip</a></li>
						<li><a
							href="${pageContext.request.contextPath}/cs/store/listCsReceive.do">Central
								Store Receiving List</a></li>
						<li><a
							href="${pageContext.request.contextPath}/cs/store/csLedgerBook.do">Ledger
								Book</a></li> 
							<li><a
							href="${pageContext.request.contextPath}/centralStore/rcvPrcess/itemRcvFrom.do">Item
								Receive</a></li> --%>

						<%-- <li><a
							href="${pageContext.request.contextPath}/cs/itemRecieved/procItemRcvFrom.do">Create
								Receiving Report</a></li> --%>

						<li><a
							href="${pageContext.request.contextPath}/workOrder/getForm.do">Create
								Work Order</a></li>
						<sec:authorize ifAllGranted="ROLE_CS_JAM">
							<li><a
								href="${pageContext.request.contextPath}/cs/itemRecieved/procItemRcvFromByWOrder.do">Create
									Receiving Report</a></li>
						</sec:authorize>

						<%-- <li><a
							href="${pageContext.request.contextPath}/getAllocationForm.do">Allocation Table</a></li> --%>

						<li><a
							href="${pageContext.request.contextPath}/getAllocationTableForm.do">Allocation
								Table</a></li>

						<sec:authorize ifAllGranted="ROLE_CS_JAM">
							<li><a
								href="${pageContext.request.contextPath}/cs/vehiclePermission.do">Create
									Vehicle Permission</a></li>
						</sec:authorize>
						<sec:authorize ifAllGranted="ROLE_CS_JAM">
							<li><a
								href="${pageContext.request.contextPath}/cs/khath/transferForm.do">Project
									Transfer Form</a></li>
						</sec:authorize>

					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">List <span class="caret"></span></a>
					<ul class="dropdown-menu">

						<li><a
							href="${pageContext.request.contextPath}/workOrder/list.do">Work
								Order List</a></li>

						<li><a
							href="${pageContext.request.contextPath}/cs/itemRecieved/list.do">Receiving
								Report </a></li>

						<li><a
							href="${pageContext.request.contextPath}/cs/itemRecieved/storeTicketlist.do">Store
								Ticket </a></li>

						<li><a
							href="${pageContext.request.contextPath}/ls/requisitionList.do">Store
								Requisiton</a></li>
						<li><a
							href="${pageContext.request.contextPath}/ls/returnSlip/List.do">Return
								Slip</a></li>

						<li><a
							href="${pageContext.request.contextPath}/cs/itemRecieved/gatePassList.do">Gate
								Pass</a></li>

						<li><a
							href="${pageContext.request.contextPath}/cs/vehiclePermissionList.do">Vehicle
								Permission</a></li>
						<li><a
							href="${pageContext.request.contextPath}/cs/khath/transferedList.do">Project
								Transfered List </a></li>

						<li><a
							href="${pageContext.request.contextPath}/cs/khath/ktList.do">Pending
								Project Transfers </a></li>

						<li><a
							href="${pageContext.request.contextPath}/inventory/wsInventoryReportList.do">Inventory
								Report List</a></li>

					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Settings <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<%-- <li><a
							href="${pageContext.request.contextPath}/cs/store/gatepass.do">Gate
								Pass</a></li>
						<li><a
							href="${pageContext.request.contextPath}/cs/store/listCsRequisition.do">Central
								Store Requisition List</a></li> 
						<li><a
							href="${pageContext.request.contextPath}/common/approvalHierarchyList.do">Approval
								Hierarchy </a></li> --%>
						<li><a
							href="${pageContext.request.contextPath}/settings/list/safetyMargin.do">
								Safety Margin </a></li>

					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Auction<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<%-- <li><a
							href="${pageContext.request.contextPath}/auction/cs/unserviceableList.do">
								Central Store Unserviceable Items</a></li> --%>
						<li class="dropdown-submenu"><a tabindex="-1" href="#">Forms</a>
							<ul class="dropdown-menu">
								<li><a
									href="${pageContext.request.contextPath}/auction/cs/getUnserviceableItems.do">Print
										Obsolete/Condemn Materials</a></li>

								<%-- <li><a
									href="${pageContext.request.contextPath}/ac/auctionWoForm.do">Auction
										Info Entry Form</a></li>

								<li><a
									href="${pageContext.request.contextPath}/auction/condemnForm.do">Condemn
										Data Entry</a></li> --%>
										
								<li><a
									href="${pageContext.request.contextPath}/ac/condemnMaterials.do">
										Delivery of Unservicable Item</a></li>

								<li><a
									href="${pageContext.request.contextPath}/ac/auctionCategoryForm.do">Add
										Auction Category</a></li>
							</ul></li>

						<li class="dropdown-submenu"><a tabindex="-1" href="#">Lists</a>
							<ul class="dropdown-menu">
								<li><a
									href="${pageContext.request.contextPath}/auction/processlist.do">
										Obsolete/Condemn Materials of Stores</a></li>
								<%-- <li><a
									href="${pageContext.request.contextPath}/ac/wolist.do">Auction
										Work Order List</a></li>
								<li><a
									href="${pageContext.request.contextPath}/auction/cc/condemnList.do">Condemn
										List</a></li> --%>
								<li><a
									href="${pageContext.request.contextPath}/ac/auctionDeliveryList.do">Condemn
										Materials Delivery List</a></li>

								<li><a
									href="${pageContext.request.contextPath}/ac/auctionCategoryList.do">Auction
										Category List</a></li>
							</ul></li>

						<%-- <li><a
							href="${pageContext.request.contextPath}/auction/condemnList.do">
								Condemn Data Entry</a></li>	 --%>


					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Archives<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a
							href="${pageContext.request.contextPath}/cs/ledger/balance.do">Current
								Balance</a></li>
						<li><a
							href="${pageContext.request.contextPath}/cs/issued/finalList.do">Item
								Issued</a></li>
						<li><a
							href="${pageContext.request.contextPath}/cs/received/finalList.do">Item
								Received(Return)</a></li>
						<li><a
							href="${pageContext.request.contextPath}/cs/storeTicket/finalList.do">Store
								Ticket</a></li>
						<li class="dropdown-submenu"><a tabindex="-1" href="#">Rejected
								List</a>
							<ul class="dropdown-menu">
								<li><a
									href="${pageContext.request.contextPath}/ls/cs/rejectedRequisitionList.do">Local
										Store Requisition</a></li>

								<li><a
									href="${pageContext.request.contextPath}/cnpd/cs/rejectedRequisitionList.do">Contractor
										Store Requisition</a></li>
							</ul></li>

					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Report<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<!-- <li><a href="#">Receiving Report</a></li>
						<li><a href="#">Return Slip Report</a></li>
						<li><a href="#">Ledger Report</a></li> -->
						<li><a
							href="${pageContext.request.contextPath}/cs/store/receivingReport.do">Receiving
								Report</a></li>
						<li><a
							href="${pageContext.request.contextPath}/cs/store/monthlyReceivingReport.do">Monthly
								Receiving Report Statement</a></li>

						<li class="dropdown-submenu"><a tabindex="-1" href="#">Store
								Requisition Report</a>
							<ul class="dropdown-menu">
								<li><a
									href="${pageContext.request.contextPath}/cs/store/requisitionReport.do">Local
										Store</a></li>
								<li><a
									href="${pageContext.request.contextPath}/cs/store/ssCsRequisitionReport.do">Sub
										Store</a></li>
								<li><a
									href="${pageContext.request.contextPath}/cs/store/wsCsRequisitionReport.do">Workshop</a></li>
								<li><a
									href="${pageContext.request.contextPath}/cs/store/cnPdCsRequisitionReport.do">Contractor(Project)</a></li>
								<li><a
									href="${pageContext.request.contextPath}/cs/store/lsPdCsRequisitionReport.do">Local
										Store (Project)</a></li>
								<li><a
									href="${pageContext.request.contextPath}/report/query/wsCsXfRequisitionReport.do">Damage
										Transformer Requisition</a></li>


							</ul></li>
						<%-- <li><a
							href="${pageContext.request.contextPath}/cs/store/requisitionReport.do">Store
								Requisition Report</a></li> --%>
						<%-- <li><a
							href="${pageContext.request.contextPath}/cs/store/returnSlipReport.do">Return
								Slip Report</a></li> --%>

						<li class="dropdown-submenu"><a tabindex="-1" href="#">Return
								Slip</a>
							<ul class="dropdown-menu">
								<li><a
									href="${pageContext.request.contextPath}/cs/store/returnSlipReportSS.do">Sub
										Store</a></li>
								<li><a
									href="${pageContext.request.contextPath}/cs/store/returnSlipReportLS.do">Local
										Store</a></li>
								<li><a
									href="${pageContext.request.contextPath}/cs/store/returnSlipReportCN.do">Contractor</a></li>
							</ul></li>

						<li><a
							href="${pageContext.request.contextPath}/cs/store/csStoreTicketIssueReport.do">Store
								Ticket Issue Report</a></li>
						<li><a
							href="${pageContext.request.contextPath}/cs/store/csStoreTicketReturnReport.do">Store
								Ticket Return Report</a></li>

						<li class="dropdown-submenu"><a tabindex="-1" href="#">Store
								Ticket Report</a>
							<ul class="dropdown-menu">
								<li><a href="javascript:void(0)">Sub
										Store Requisition</a></li>
								<li><a href="javascript:void(0)">Sub
										Store Return</a></li>
								<li><a href="javascript:void(0)">Local
										Store Requisition</a></li>
								<li><a
									href="${pageContext.request.contextPath}/report/query/lsPdCsRequisitionStoreTicket.do">Local
										Store Requisition (Project)</a></li>
								<li><a href="javascript:void(0)">Local
										Store Return</a></li>
								<li><a href="javascript:void(0)">Contractor
										(Project) Requisition</a></li>
								<li><a href="javascript:void(0)">Contractor
										(Project) Return</a></li>
								<li><a
									href="${pageContext.request.contextPath}/report/query/wsxToCsReqTicket.do">Workshop
										Requisition(Transformer)</a></li>
								<li><a
									href="${pageContext.request.contextPath}/report/query/wsToCsReqTicket.do">Workshop
										Requisition(Materials For Transformer Repair)</a></li>
								<li><a
									href="${pageContext.request.contextPath}/report/query/wsxToCsRetTicket.do">Workshop
										Return(Transformer)</a></li>
								<li><a
									href="${pageContext.request.contextPath}/report/query/wsToCsRetTicket.do">Workshop
										Return (Materials)</a></li>

							</ul></li>

						<li><a
							href="${pageContext.request.contextPath}/cs/store/ledgerReport.do">Ledger
								Report</a></li>
						<%-- <li><a
							href="${pageContext.request.contextPath}/cs/store/stockReport.do">Stock
								Report</a></li> --%>

						<li class="dropdown-submenu"><a tabindex="-1" href="#">Stock
								Report</a>
							<ul class="dropdown-menu">
								<li><a
									href="${pageContext.request.contextPath}/cs/store/stockRevenueReport.do">Revenue</a></li>
								<li><a
									href="${pageContext.request.contextPath}/cs/store/stockProjectReport.do">Projects</a></li>
								<li><a
									href="${pageContext.request.contextPath}/cs/store/stockRevenueNProjectReport.do">Revenue
										and Projects</a></li>
							</ul></li>
						<%-- <li><a
							href="${pageContext.request.contextPath}/cs/store/csVehiclePermissionReport.do">Vehicle
								Permission Report</a></li> --%>
						<li><a
							href="${pageContext.request.contextPath}/cs/vehiclePermissionReport.do">Vehicle
								Permission Report</a></li>

						<li class="dropdown-submenu"><a tabindex="-1" href="#">Gate
								Pass Report</a>
							<ul class="dropdown-menu">
								<li><a
									href="${pageContext.request.contextPath}/cs/store/csGatePassReport.do">Gate
										Pass Report</a></li>
								<li><a
									href="${pageContext.request.contextPath}/report/query/lsPdCsGatePassCS.do">Gate
										Pass Report (Project)</a></li>
							</ul></li>

						<li class="dropdown-submenu"><a tabindex="-1" href="#">Auction
						</a>
							<ul class="dropdown-menu">
								<li><a target="_blank"
									href="${pageContext.request.contextPath}/report/query/cs/auction/condemnMaterials.do">
										Obsolete/Condemn Materials Report</a></li>
								<li><a target="_blank"
									href="${pageContext.request.contextPath}/report/query/cs/auction/deptWiseCondemnMaterials.do">
										Division wise Condemn Materials Report</a></li>
								<li><a
									href="${pageContext.request.contextPath}/report/query/store/auction/storeWiseUnServItemReport.do">Store
										Wise Unserviceable Item </a></li>
							<%-- 	<li><a
									href="${pageContext.request.contextPath}/report/query/store/auction/summeryUnServItemPassReport.do">Summary
										of Unserviceable Item Pass Report (Project)</a></li> --%>
								
							</ul></li>

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
	<!-- -------------------------------------------------------------------------------------- -->