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
	href="${pageContext.request.contextPath}/resources/assets/css/multi-select.css" />
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
	href="${pageContext.request.contextPath}/resources/assets/css/common.css" />
	
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

body {
	background-color: #858585;
}

input[readonly], select, input[readonly='readonly'], input {
	background: #fff !important;
	color: #000 !important;
}
</style>


<!-- jqurey date-picker -->
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
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

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/js/centralStore/csCommon.js"></script>
<!-- Javascript -->
<style type="text/css">
.ui-datepicker {
	background: #333;
	border: 1px solid #555;
	color: #EEE;
}
</style>

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
	
	//For Bd Style end date selection -- Ihteshamul Alam
	$(function() {
		$(".datepicker-21").datepicker({
			dateFormat : 'dd-mm-yy'
		});	
		$(".datepicker-21").datepicker("hide");
	});
	
	$(function() {
		$(".datepicker-18").datepicker({ 
	        dateFormat: 'yy',
	        changeMonth: false,
	        changeYear: true,
	        showButtonPanel: true,

	        onClose: function(dateText, inst) {  
	            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
	            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
	            $(this).val($.datepicker.formatDate('yy', new Date(year, 1)));
	        }
	    });
		$(".datepicker-18").datepicker("hide");
	});
</script>


</head>
<body>
	<!-- <div id="navbar" class="navbar navbar-default" style="background-color: #A24689"> -->
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
						${properties['project.workshop']}
				</small>
				</a>
			</div>

			<ul class="nav navbar-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Forms<span class="caret"></span></a>
					<ul class="dropdown-menu">
					
						<li><a
							href="${pageContext.request.contextPath}/ws/xf/contractorForm.do">New
								Contractor (WORKSHOP)</a></li>
						<li><a
							href="${pageContext.request.contextPath}/ws/lookupItemForm.do">Job
								Card Lookup Item Form</a></li>
								<li><a
							href="${pageContext.request.contextPath}/transformer/register/transformerRegisterEntry.do">Transformer
								Register Entry</a></li>
								<li><a
							href="${pageContext.request.contextPath}/prev/receiveForm.do">Transformer Receive For Preventive Maintenance</a></li>
							
								<li><a href="${pageContext.request.contextPath}/prev/gatePassForm.do">Create Gate Pass For Preventive Maintenance Item</a></li>

							<!-- Meter Testing Register Removed by Taleb -->
							<%-- <li><a
							href="${pageContext.request.contextPath}/mt/meterTestingForm.do">Meter
								Testing Register Form</a></li> --%>
							<li><a
							href="${pageContext.request.contextPath}/targetForm.do">
								Repair &amp; Maintenance Target Form</a></li>
							<li><a
							href="${pageContext.request.contextPath}/closeoutForm.do">Transformer Close out Form</a></li>
									
						<%-- <li class="dropdown-submenu"><a tabindex="-1" href="javascript:void(0)">Meter Test Report Form</a>
							<ul class="dropdown-menu">
								<li><a
									href="${pageContext.request.contextPath}/mtr/htCtMeterTestReportForm.do">HT-CT Meter Test Report Form</a></li>
										
							</ul></li> --%>

					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">List<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a
							href="${pageContext.request.contextPath}/ws/xf/contractorList.do">Contractor
								List</a></li>
						<li><a
							href="${pageContext.request.contextPath}/ws/xf/wsCnXFormerAllocation.do">Contractor
								XFormer Allocation</a></li>
								<li><a
							href="${pageContext.request.contextPath}/wsx/transformer/requisitionList.do">Store
								Requisition (Transformer)</a></li>
						<li><a
							href="${pageContext.request.contextPath}/inventory/wsInventoryReportList.do">Inventory Report List</a></li>
						
						<li><a
							href="${pageContext.request.contextPath}/ws/requisitionList.do">Store
								Requisition (Transformer Repairing Materials)</a></li>
								
						<li><a
							href="${pageContext.request.contextPath}/wsx/returnSlip/List.do">Return
								Slip(Transformer)</a></li>
						
						<li><a
							href="${pageContext.request.contextPath}/wsm/returnSlip/List.do">Return
								Slip(Transformer Repairing Materials)</a></li>
						<li><a
							href="${pageContext.request.contextPath}/ws/storeTicketlist.do">Store
								Ticket List</a></li>					

						<li><a
							href="${pageContext.request.contextPath}/ws/xf/getPendingTTRList.do">Transformer
								Test Report (Pending)</a></li>
						<%-- <li><a
							href="${pageContext.request.contextPath}/cnws/requisitionList.do">Requisition List</a></li> --%>
						<li><a
							href="${pageContext.request.contextPath}/jobcard/jobList.do">Job
								Card List</a></li>
						<li><a
							href="${pageContext.request.contextPath}/jobcard/approvedJobList.do">Approved Job
								Card List</a></li>
								<li><a
							href="${pageContext.request.contextPath}/prev/prevReceiveList.do">Transformer Receive For Preventive Maintenance</a></li>
						
							<li><a href="${pageContext.request.contextPath}/ws/jobCardLookupItemList.do">Job Card Lookup Item List</a></li>
							<li><a
							href="${pageContext.request.contextPath}/ws/asBuilt/asBuiltList.do">As Built Report</a></li>
							<li><a
							href="${pageContext.request.contextPath}/prev/gatePassList.do">Gate Pass List</a></li>
					<li><a
							href="${pageContext.request.contextPath}/ws/progressList.do"> Progress List</a></li>
					<li><a
									href="${pageContext.request.contextPath}/transformerCloseOutList.do">Transformer Close Out List</a></li>
						<li><a
									href="${pageContext.request.contextPath}/manufactureNameList.do">ManufactureName List</a></li>
						</ul></li>
					
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Meter Test Report<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li class="dropdown-submenu"><a tabindex="-1" href="#">Forms</a>
							<ul class="dropdown-menu">
								<li><a
									href="${pageContext.request.contextPath}/mtr/htCtMeterTestReportForm.do">New HT-CT
										Meter Test Report Form</a></li>

								<li><a
									href="${pageContext.request.contextPath}/mtr/ltCtMeterTestReportForm.do">New LT-CT
										Meter Test Report Form</a></li>

								<li><a
									href="${pageContext.request.contextPath}/mtr/wcnuMeterTestReport.do">Whole
										Current Meter (New/Used) Meter Test Report Form</a></li>
								
								<li><a
									href="${pageContext.request.contextPath}/mtr/usedMeterTestReport.do">Used (Old) Meter Test Report Form</a></li>
								
								<li><a
									href="${pageContext.request.contextPath}/mtr/spareCtPtMeterTestReport.do">Spare CT/PT Test Report Form</a></li>
								
							</ul>
						</li>
						
						<li class="dropdown-submenu"><a tabindex="-1" href="#">List</a>
							<ul class="dropdown-menu">
							  	<li><a
									href="${pageContext.request.contextPath}/mtr/meterTestReportList.do">Meter Test Report List</a></li>
							
								<li><a
									href="${pageContext.request.contextPath}/mtr/meterTestReportFinalList.do">Meter Test Report Final List</a></li>
								</ul>
						</li>
					</ul>
				</li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Archives <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a
							href="${pageContext.request.contextPath}/ws/xf/registerRepair.do">Transformer
								Register List (Repair)</a></li>
					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Reports <span class="caret"></span></a>
					<ul class="dropdown-menu">
                          <li><a
							href="${pageContext.request.contextPath}/jobcard/mnjobCardReport.do">Job Card Report</a></li>
					 <li><a
							href="${pageContext.request.contextPath}/report/xReturnReport.do">Transformer Return Report</a></li>
					 <li><a
							href="${pageContext.request.contextPath}/report/totalXRcvReport.do">Total Transformer Receive Report</a></li>
					 <li><a
							href="${pageContext.request.contextPath}/report/totalXReturnReport.do">Total Transformer Return Report</a></li>
					 <li><a
							href="${pageContext.request.contextPath}/report/totalXExistReport.do">Total Transformer Existing Report</a></li>
					 <li><a
							href="${pageContext.request.contextPath}/report/totalXBillPaidReport.do">Total Transformer Bill Paid Report</a></li>
					 <li><a
							href="${pageContext.request.contextPath}/report/progressReport.do">Progress Report</a></li>
					<li><a
							href="${pageContext.request.contextPath}/report/xTestReport.do">Transformer Test Report</a></li>
					<li><a
							href="${pageContext.request.contextPath}/report/xCloseOut.do">Transformer CloseOut Report</a></li>
							
					<li><a
							href="${pageContext.request.contextPath}/report/monthlyMeterTestingReport.do">Monthly Meter Testing Report</a></li>
							
			</ul></li>
					
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Help <span class="caret"></span></a>
					<ul class="dropdown-menu">
                          <li><a
							href="${pageContext.request.contextPath}/ws/wsWorkFlow.do">Step by step Work Flow</a></li>
					 
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