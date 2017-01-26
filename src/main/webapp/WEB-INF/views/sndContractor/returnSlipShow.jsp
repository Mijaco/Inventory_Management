<%@include file="../common/sndCnHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Item Received</a> / Show
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/c2ls/returnSlip/List.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Return Slip List
			</a>
		</div>
		<h2 class="center blue" style="margin-top: 0px;">Return Slip</h2>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Return Slip No: <input type="hidden"
						value="${returnSlipMst.returnSlipNo}" id="returnSlipNo" /> <input
						type="hidden" value="${returnStateCode}" id="returnStateCode" />
						<input type="hidden" value="${returnSlipMst.uuid}" id="uuid" /> <input
						type="hidden" name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" />

					</td>

					<td class="info">${returnSlipMst.returnSlipNo}</td>
					<td class="success">Created By:</td>
					<td class="info">${returnSlipMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
						value="${returnSlipMst.createdDate}" pattern="dd-MM-yyyy hh:mm:ss a" /></td>
				</tr>
				<tr class="">
					<td class="success">Work Order No:</td>
					<td class="info">${returnSlipMst.workOrderNo}</td>
					<td class="success">Work Order Date:</td>
					<td class="info"><fmt:formatDate
						value="${returnSlipMst.workOrderDate}" pattern="dd-MM-yyyy" /></td>
					<td class="success">Returned To:</td>
					<td class="info"><strong>
							${returnSlipMst.returnTo} </strong></td>
				</tr>
				<%-- <tr class="">					
					<td class="success">Current Status:</td>
					<td class="info"><strong> ${currentStatus} </strong></td>
				</tr> --%>

			</table>
		</div>

		<c:if test="${!empty approveHistoryList}">
			<button data-toggle="collapse" data-target="#demo">
				<span class="glyphicon glyphicon-collapse-down"></span>
			</button>
		</c:if>

		<div id="demo" class="collapse">
			<c:if test="${!empty approveHistoryList}">
				<!-- <div class="" style="font-weight: bold;">Approval History</div> -->

				<c:forEach items="${approveHistoryList}" var="approveHistory">
					<%-- <div class=""
						style="font-weight: bold; font-style: italic; text-transform: capitalize">${fn:toLowerCase(approveHistory.approvalHeader)}</div>
					<hr style="margin: 5px 0px 10px 0px" /> --%>
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
									value="${approveHistory.createdDate}"
									pattern="dd-MM-yyyy" /></td>
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
			<c:if test="${empty returnSlipDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty returnSlipDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">New Serviceable</td>
							<td style="">Recovery Serviceable</td>
							<td style="">UnServiceable</td>
							<td style="">Total Return</td>
							<td style="">Remarks</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${returnSlipDtlList}" var="returnSlipDtl"
							varStatus="loop">
							<tr>

								<td><c:out value="${returnSlipDtl.itemCode}" /></td>
								<td><c:out value="${returnSlipDtl.description}" /></td>
								<td><c:out value="${returnSlipDtl.uom}" /> <input
									type="hidden" value="${returnSlipDtl.totalReturn}" /></td>
								<td><input type="number" style="width:100px;"
									value="${returnSlipDtl.qtyNewServiceable}"
									id="qtyNewServiceable${loop.index}"
									onblur="setTotalQuantity(this)" /></td>
								<td><input type="number" style="width:100px;"
									value="${returnSlipDtl.qtyRecServiceable}"
									id="qtyRecServiceable${loop.index}"
									onblur="setTotalQuantity(this)" /></td>
								<td><input type="number" style="width:100px;"
									value="${returnSlipDtl.qtyUnServiceable}"
									id="qtyUnServiceable${loop.index}"
									onblur="setTotalQuantity(this)" /></td>
								<td><input type="number" style="width:80px;"
									value="${returnSlipDtl.totalReturn}" readOnly="readOnly"
									id="totalReturn${loop.index}" /> <input type="hidden"
									value="${returnSlipDtl.id}" readOnly="readOnly"
									id="dtlId${loop.index}" /></td>

								<%-- <td><c:out value="${returnSlipDtl.qtyNewServiceable}" /></td>
								<td><c:out value="${returnSlipDtl.qtyRecServiceable}" /></td>
								<td><c:out value="${returnSlipDtl.qtyUnServiceable}" /></td>
								<td><c:out value="${returnSlipDtl.totalReturn}" /></td> --%>

								<td><c:out value="${returnSlipDtl.remarks}" /></td>
								<td>
									<div class="action-buttons">
										<a href="javascript:void(0)" id="rtnslipdtla${loop.index}" class="btn btn-primary btn-xs"
										style="border-radius:4px;"
											onclick="editItem(this)"> <i
											class="fa fa-fw fa-repeat"></i>&nbsp;Update
										</a>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

		</div>



		<!-- <div class="text-center"> -->
		<div class="row">
			<label class="col-xs-2"> <strong>Comment(s):&nbsp;<span class='red'>*</span></strong></label>
			<div class="col-xs-10">
				<textarea class="form-control" rows="2" cols="" id="justification"></textarea>
				<strong class="justification text-danger hide">This field is required</strong>
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
				<%-- <a class="btn btn-primary"
					href="${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedSubmitApproved.do?receivedReportNo=${centralStoreRequisitionMst.rrNo}"
					style="text-decoration: none; border-radius: 6px;">
					${buttonValue} </a> --%>
				<button type="button" class="btn btn-primary" onclick="approveing()"
					style="text-decoration: none; border-radius: 6px;" id="buttonApproving">
					${buttonValue} </button>
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
							<!-- <li class=""><a href="Javascript:forwardToUpper()">Action</a></li>
						<li class=""><a href="Javascript:forwardToUpper()">Another
								action</a></li> -->
						</ul>
					</div>
				</c:if>
			</div>
		</div>
		<!-- -------------------------- -->

		<script>
			function editItem(element) {
				var rowid = $(element).attr('id');
				var sequence = rowid.substr(rowid.length - 1);

				var id = $('#dtlId' + sequence).val().trim();

				var qtyNewServiceable = $('#qtyNewServiceable' + sequence)
						.val().trim();
				var qtyNewServiceableFloat = parseFloat(qtyNewServiceable)
						.toFixed(3);

				var qtyRecServiceable = $('#qtyRecServiceable' + sequence)
						.val().trim();
				var qtyRecServiceableFloat = parseFloat(qtyRecServiceable)
						.toFixed(3);

				var qtyUnServiceable = $('#qtyUnServiceable' + sequence).val()
						.trim();
				var qtyUnServiceableFloat = parseFloat(qtyUnServiceable)
						.toFixed(3);

				var totalReturn = $('#totalReturn' + sequence).val().trim();
				var totalReturnFloat = parseFloat(totalReturn).toFixed(3);

				var cData = {
					id : id,
					qtyNewServiceable : qtyNewServiceableFloat,
					qtyRecServiceable : qtyRecServiceableFloat,
					qtyUnServiceable : qtyUnServiceableFloat,
					totalReturn : totalReturnFloat
				}
				var cDataJsonString = JSON.stringify(cData);
				$.ajax({
					type : "POST",
					contentType : "application/json; charset=utf-8",
					url : $('#contextPath').val()
							+ "/c2ls/editReturnSlipDtl.do",
					data : cDataJsonString,
					success : function(data) {
						//Now updated RR Dtl qty for that item
						alert(data);
					},
					dataType : "json"
				});

			}

			function setTotalQuantity(element) {
				var id = $(element).attr('id');
				var sequence = id.substr(id.length - 1);

				var qtyNewServiceable = $('#qtyNewServiceable' + sequence)
						.val().trim();
				var qtyNewServiceableFloat = parseFloat(qtyNewServiceable)
						.toFixed(3);

				var qtyRecServiceable = $('#qtyRecServiceable' + sequence)
						.val().trim();
				var qtyRecServiceableFloat = parseFloat(qtyRecServiceable)
						.toFixed(3);

				var qtyUnServiceable = $('#qtyUnServiceable' + sequence).val()
						.trim();
				var qtyUnServiceableFloat = parseFloat(qtyUnServiceable)
						.toFixed(3);

				var x = parseFloat(qtyNewServiceableFloat);
				var y = parseFloat(qtyRecServiceableFloat);
				var z = parseFloat(qtyUnServiceableFloat);

				var totalReturn = (x + y + z).toFixed(3);
				$('#totalReturn' + sequence).val(totalReturn);

			}

			function forwardToUpper(stateCode) {
				if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
					$('.justification').removeClass('hide');
					$('#justification').focus();
					return;
				} else {
					var justification = $('#justification').val();
					var returnSlipNo = $('#returnSlipNo').val();

					//window.location = "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
					window.location = "${pageContext.request.contextPath}/c2ls/returnSlip/sendTo.do?returnSlipNo="
							+ returnSlipNo
							+ "&justification="
							+ justification
							+ "&stateCode=" + stateCode;
				}
				
			}

			function backToLower(stateCode) {
				if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
					$('.justification').removeClass('hide');
					$('#justification').focus();
					return;
				} else {
					var justification = $('#justification').val();
					var returnSlipNo = $('#returnSlipNo').val();

					//window.location = "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
					window.location = "${pageContext.request.contextPath}/c2ls/returnSlip/backTo.do?returnSlipNo="
							+ returnSlipNo
							+ "&justification="
							+ justification
							+ "&stateCode=" + stateCode;
				}
				
			}

			function approveing() {
				if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
					$('.justification').removeClass('hide');
					$('#justification').focus();
					return;
				} else {
					$('#buttonApproving').prop('disabled', true);
					var justification = $('#justification').val();
					var returnSlipNo = $('#returnSlipNo').val();

					var returnStateCode = $('#returnStateCode').val();

					window.location = "${pageContext.request.contextPath}/c2ls/itemReturnSlipApproved.do?returnSlipNo="
							+ returnSlipNo
							+ "&justification="
							+ justification
							+ "&return_state=" + returnStateCode;
				}
				
			}
		</script>
	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>