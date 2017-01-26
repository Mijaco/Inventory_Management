<%@include file="../../common/wsHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid" style="background-color: #858585;">
<!--  icon-box -->
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Item Received</a> / Show
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/prev/gatePassList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Gate Pass List
			</a>
			<!-- 	<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button> -->
		</div>
		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Gate Pass</h2>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		<h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5>
	</div>
</div>

<div class="row"
	style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
	<!-- --------------------- -->
	<div class="oe_title">

		<table class="col-sm-12 table">
			<tr class="">
				<td class="success">Note No: <input type="hidden"
					value="${transformerRegister.id}" id="id" /> <input
					type="hidden" value="${returnStateCode}" id="returnStateCode" /> <input
					type="hidden" name="contextPath" id="contextPath"
					value="${pageContext.request.contextPath}" />

				</td>

				<td class="info">${transformerRegister.reqNo}</td>
				<td class="success">Created By:</td>
				<td class="info">${transformerRegister.createdBy}</td>
				<td class="success">Created Date:</td>
				<td class="info"><fmt:formatDate
	value="${transformerRegister.createdDate}" pattern="dd-MM-yyyy" /></td>
			</tr>
			<tr class="">
				<td class="success">Work Order No:</td>
				<td class="info">${transformerRegister.contractNo}</td>
				<td class="success">Issue To:</td>
				<td class="info"><strong>
						${transformerRegister.rcvDeptName} </strong></td>
			</tr>
			<tr class="">
				<td class="success">Modified By:</td>
				<td class="info">${transformerRegister.modifiedBy}</td>
				<td class="success">Modified Date:</td>
				<td class="info"><fmt:formatDate
					value="${transformerRegister.modifiedDate}" pattern="dd-MM-yyyy" /></td>
				<td class="success">Present Status:</td>
				<td class="info"><strong> ${currentStatus} </strong></td>
			</tr>

		</table>
	</div>


	<c:if test="${!empty itemRcvApproveHistoryList}">
		<button data-toggle="collapse" data-target="#demo">
			<span class="glyphicon glyphicon-collapse-down"></span>
		</button>
	</c:if>
	 <div id="demo" class="collapse">
		<c:if test="${!empty itemRcvApproveHistoryList}">
			<!-- <div class="" style="font-weight: bold;">Approval History</div> -->

			<c:forEach items="${itemRcvApproveHistoryList}" var="approveHistory">
				<div class=""
						style="font-weight: bold; font-style: italic; text-transform: capitalize">${fn:toLowerCase(approveHistory.approvalHeader)}</div>
					<hr style="margin: 5px 0px 10px 0px" />
				<table class="col-sm-12 table">

					<tr class="">
						<td class="warning col-sm-3"
							style="font-weight: bold; font-size: 1.5em; font-style: italic; text-transform: capitalize">${fn:toLowerCase(approveHistory.approvalHeader)}</td>
						<td class="success col-sm-2" style="text-transform: capitalize">${fn:toLowerCase(approveHistory.stateName)}
							By:</td>
						<td class="success col-sm-2 text-left">
							${approveHistory.cEmpFullName} <c:if
								test="${!empty approveHistory.cDesignation}">
									, ${approveHistory.cDesignation} 
								</c:if> <c:if test="${!empty approveHistory.cEmpId}">
									( ${approveHistory.cEmpId} )
								</c:if>
						</td>
						<td class="info col-sm-2 text-left"
							style="text-transform: capitalize">${fn:toLowerCase(approveHistory.stateName)}
							Date:</td>
						<td class="info col-sm-3"><fmt:formatDate
							value="${approveHistory.createdDate}" pattern="dd-MM-yyyy" /></td>
					</tr>
					<c:if test="${!empty approveHistory.justification}">
						<tr class="">
							<td></td>
							<td class="danger col-sm-1">Comment(s) :</td>
							<td class="danger col-sm-11" colspan="3"
								title="${approveHistory.justification}">${approveHistory.justification}</td>
						</tr>
					</c:if>
				</table>
			</c:forEach>
		</c:if>
	</div> 


	<div style="background: white;">
		<c:if test="${empty transformerRegister}">
			<div class="col-sm-12 center">
				<p class="red">
					<i>Sorry!!! No Data Found in Database. </i>
				</p>
			</div>
		</c:if>
		<c:if test="${!empty transformerRegister}">
			<table id="requisitionListTable"
				class="table table-striped table-hover table-bordered">
				<thead>
					<tr style="background: #579EC8; color: white; font-weight: normal;">
						<td style="">Transformer Serial No</td>
						<td style="">Manufacture Name</td>
						<td style="">Item Code</td>
						<td style="">Description</td>
						<td style="">KVA Rating</td>
						<td style="">Quantity</td>
					</tr>
				</thead>
				<tbody>
						<tr>
							<td>${transformerRegister.transformerSerialNo}</td>
							<td>${transformerRegister.manufacturedName}</td>
							<td>${transformerRegister.itemCode}</td>
							<td>${item.itemName}</td>
							<td>${transformerRegister.kvaRating}</td>
							<td>1</td>
						</tr>
				</tbody>
			</table>
		</c:if>

	</div>



	<!-- <div class="text-center"> -->
	<div class="row">
		<label class="col-xs-2"> <strong>Comment(s) : </strong></label>
		<div class="col-xs-10">
			<textarea required class="form-control" rows="2" cols="" id="justification"></textarea>
		</div>
	</div>
	<div class="col-xs-12">
		<hr />
	</div>
	<div class="row">
		<div class="col-md-4 col-sm-4 text-left">
			<c:if test="${!empty backManRcvProcs}">
				<!-- <button onclick="backToLower()" class="btn btn-warning"
						style="text-decoration: none; border-radius: 6px;">< Back
						To</button> -->
				<div class="dropup pull-left">
					<button class="btn btn-warning dropdown-toggle" type="button"
						style="border-radius: 6px;" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false">
						Back to <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">

						<c:forEach items="${backManRcvProcs}" var="backMan">
							<li class=""><a
								href="Javascript:backToLower(${backMan.stateCode})">For
									${backMan.buttonName}</a></li>
						</c:forEach>
						<!-- <li class=""><a href="Javascript:forwardToUpper()">Action</a></li>
						<li class=""><a href="Javascript:forwardToUpper()">Another
								action</a></li> -->
					</ul>
				</div>
			</c:if>
		</div>
		<div class="col-md-4 col-sm-4 text-center">
			
			<a class="btn btn-primary" href="Javascript:approveing()"
				style="text-decoration: none; border-radius: 6px;">
				${buttonValue} </a>
		</div>
		<div class="col-md-4 col-sm-4 text-right">

			<c:if test="${!empty nextManRcvProcs}">

				<div class="dropup pull-right">
					<button class="btn btn-danger dropdown-toggle" type="button"
						style="border-radius: 6px;" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false">
						Send to <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">

						<c:forEach items="${nextManRcvProcs}" var="nextMan">
							<li class=""><a
								href="Javascript:forwardToUpper(${nextMan.stateCode})">For
									${nextMan.buttonName}</a></li>
						</c:forEach>
						
					</ul>
				</div>
			</c:if>
		</div>
	</div>
	<!-- -------------------------- -->

	<script>

		function forwardToUpper(stateCode) {
			var justification = $('#justification').val();
			var id = $('#id').val();

			//window.location = "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
			window.location = "${pageContext.request.contextPath}/prev/sendToGatePass.do?id="
					+ id
					+ "&justification="
					+ justification
					+ "&stateCode=" + stateCode;
		}

		function backToLower(stateCode) {
			var justification = $('#justification').val();
			var id = $('#id').val();

			//window.location = "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
			window.location = "${pageContext.request.contextPath}/prev/backToGatePass.do?id="
					+ id
					+ "&justification="
					+ justification
					+ "&stateCode=" + stateCode;
		}

		function approveing() {
			var justification = $('#justification').val();
			var id = $('#id').val();

			var returnStateCode = $('#returnStateCode').val();

			window.location = "${pageContext.request.contextPath}/prev/gatePassApproved.do?id="
					+ id
					+ "&justification="
					+ justification
					+ "&return_state=" + returnStateCode;
		}
	</script>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>