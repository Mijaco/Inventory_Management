<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
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
</style>



<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/js/centralStore/csCommon.js"></script>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

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

<div class="row"
	style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
	<!-- --------------------- -->

	<div class="oe_title">
		<form method="POST" class=""
			action="${pageContext.request.contextPath}/test/afmChartOfAccountsTest/createAccount.do">
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="accountCode" class="col-sm-4 control-label">
						Account Code</label>
					<div class="col-sm-8">
						<input type="hidden" id="contextPath"
							value="${pageContext.request.contextPath}" /> <input type="text"
							class="form-control" id="accountCode" placeholder="account code"
							name="accountCode" value="" />

					</div>
				</div>
			</div>
			<div class="col-sm-12 col-md-12">
				<div class="form-group">
					<label for="accountHeadName" class="col-sm-4 control-label">Account
						Head Name</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="accountHeadName"
							name="accountHeadName" value="" placeholder="Account Head Name" />
					</div>
				</div>
			</div>
			<div class="col-sm-12 col-md-12">
				<div class="form-group">
					<label for="accountHeadType" class="col-sm-4 control-label">Account
						Head Type</label>
					<div class="col-sm-8">
						<select class="form-control" id="accountHeadType"
							name="accountHeadType">
							<option value="Assets" selected>Assets</option>
							<option value="Expences" selected>Expences</option>
							<option value="Income" selected>Income</option>
							<option value="Liability" selected>Liability</option>

						</select>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-12 col-md-12">
				<div class="form-group">
					<label for="noteNo" class="col-sm-4 control-label">Note No.</label>

					<div class="col-sm-8">
						<div class="row">
							<div class="col-md-12">
								<input type="text" class="form-control" id="noteNo"
									name="noteNo" value="" placeholder="Note No." />
							</div>
						</div>

					</div>
				</div>
			</div>

			<div class="col-sm-12 col-md-12">
				<div class="form-group">
					<label for="noteNo" class="col-sm-4 control-label"></label>

					<div class="col-sm-8">

						<div class="row">
							<div class="col-md-12">
								<div class="row">
									<label><input type="radio" id="noteNo1"
										checked="checked" name="noteNo" value="Parent" />Parent</label>
								</div>
								<div class="row">
									<label><input type="radio" id="noteNo2" name="noteNo"
										value="Child" />Child</label>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="clearfix"></div>

			<div class="col-sm-12 col-md-12 parentDiv">
				<div class="form-group">
					<label for="parentAccountHeadId" class="col-sm-4 control-label">Parent
						Account Code</label>
					<div class="col-sm-8">
						<input type="hidden" class="form-control" id="parentAccountHeadId"
							name="parentAccountHeadId" value="" /> <input type="text"
							class="form-control" id="parentAccountHeadCode" value="" />

						<%-- <select class="form-control" id="parentAccountHeadId"
							name="parentAccountHeadId">
							<option value="" selected>Select Parent</option>
							<c:forEach items="${authUserList}" var="user">
							<option value="${user.id}">${user.name}[${user.userid}]</option>
						</c:forEach>
						</select> --%>
					</div>
				</div>
			</div>

			<div class="col-sm-12 col-md-12 parentDiv">
				<div class="form-group">
					<label for="parentAccountHeadId" class="col-sm-4 control-label">Parent
						Account Head</label>
					<div class="col-sm-8">


						<input type="text" readonly class="form-control"
							id="parentAccountHeadName" value="" />

						<%-- <select class="form-control" id="parentAccountHeadId"
							name="parentAccountHeadId">
							<option value="" selected>Select Parent</option>
							<c:forEach items="${authUserList}" var="user">
							<option value="${user.id}">${user.name}[${user.userid}]</option>
						</c:forEach>
						</select> --%>
					</div>
				</div>
			</div>
			<div class="col-sm-12 col-md-12">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">Account Type</label>
					<div class="col-sm-8">
						<div class="row">
							<label><input type="radio" id="AccountIdType1"
								name="AccountIdType" value="Cash Account" />Cash Account</label>
						</div>
						<div class="row">
							<label><input type="radio" id="AccountIdType2"
								name="AccountIdType" value="Bank Head" />Bank Head</label>
						</div>
						<div class="row">
							<label><input type="radio" id="AccountIdType3"
								name="AccountIdType" value="Other" />Other</label>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="isActive" class="col-sm-4 control-label"> Is
						Active</label>
					<div class="col-sm-1 col-md-1">
						<input type="checkbox" class="form-control" id="isActive"
							name="isActive" value="true" />

					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="isActive" class="col-sm-4 control-label">
						Opening Balance</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="openingBalance"
							placeholder="opening balance" name="openingBalance" value="" />

					</div>
				</div>
			</div>

			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="openingDate" class="col-sm-4 control-label">
						Opening Date</label>
					<div class="col-sm-8">
						<!-- <input type="text" class="form-control" id="openingDate"
							placeholder="opening date" name="openingDate" value="" /> -->
						<input type="text" class="form-control datepicker-13"
							id="openingDate" style="border: 0; border-bottom: 2px ridge;"
							name="openingDate" />

					</div>
				</div>
			</div>
			<div class="col-sm-12 center">
				<div class="form-group">
					<button type="submit" class="btn btn-success">
						<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
						Save
					</button>
				</div>
			</div>
		</form>


		<!-- --------------------- -->
	</div>
</div>
<script type="text/javascript">
	$(function() {
		//hide parent related fields on load
		$('.parentDiv').hide();

		//show parent related fields
		$("#noteNo2").on("click", function() {
			$('.parentDiv').show();
		});

		//hide parent related fields
		$("#noteNo1").on("click", function() {
			$('.parentDiv').hide();
		});

		//set parent account's data to fields using ajax
		$("#parentAccountHeadCode").on(
				"blur",
				function() {
					var contextPath = $('#contextPath').val();
					console.log($('#parentAccountHeadCode').val());
					var code = $('#parentAccountHeadCode').val();
					var objCode = {
						accountCode : code
					}
					var str = JSON.stringify(objCode);
					var path = contextPath
							+ '/test/afmChartOfAccountsTest/ajax/findOne.do';
					if (code == "") {
						return;
					}

					$.ajax({
						url : path,
						data : str,
						contentType : "application/json",
						success : function(data) {
							var result = JSON.parse(data);
							if (result) {
								$('#parentAccountHeadName').val(
										result.accountHeadName);
								$('#parentAccountHeadId').val(result.id);
							} else {
								$('#parentAccountHeadName').val("");
								$('#parentAccountHeadId').val("");
							}

						},
						error : function(data) {
							alert("Server Error");
						},
						type : 'POST'
					});

				});

	});
</script>
</head>
</html>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>