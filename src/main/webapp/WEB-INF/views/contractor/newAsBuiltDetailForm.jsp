<%@include file="../common/pdHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/asBuilt/newAsBuiltList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> As Built List
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">As Built Detail For (${jobNo})</h2>
		<%--<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		 <h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 10px; margin-top: 10px; margin-bottom: 10px; margin-left: 10px; margin-right: 10px;">
		<!-- --------------------- -->
		<form
			action="${pageContext.request.contextPath}/asBuilt/newAsBuiltReportSave.do"
			method="POST" id="myForm">
				
			<div class="oe_title">
				<div class="col-xs-12">
					<table class="col-xs-12 table">
						<tr class="">

							<td class="info">Work Order No:</td>
							<td class="info" id="contractNo">${contractor.contractNo}<input
								type="hidden" name="woNumber"
								value="${contractor.contractNo}"><input
								type="hidden" name="contextPath" id="contextPath"
								value="${pageContext.request.contextPath}">
								<input
								type="hidden" name="jobNo" id="jobNo"
								value="${jobNo}">
							</td>
							<td class="success">Work Order Date:</td>
							<td class="success"><fmt:formatDate
									value="${contractor.contractDate}"
									pattern="dd-MM-yyyy" /></td>
							<td class="danger">Indentor:</td>
							<td class="danger">${deptName}</td>
						</tr>

						<tr class="">
							<td class="info">Receiver Name:</td>
							<td class="info">
								<input type="text" name="receivedBy" id="receivedBy" style="width: 100%">
							</td>
							<td class="danger">Remarks :</td>
							<td class="danger"><input class="form-control" type="text"
								name="remarks" /></td>

						</tr>
					</table>
				</div>
			</div>
			
			<div style="background: white;">
				<c:if test="${empty asBuiltDtl}">
					<div class="col-sm-12 center">
						<p class="red">
							<i>Sorry!!! No Data Found in Database. </i>
						</p>
					</div>
				</c:if>
					<div class="table-responsive"> 
					<div class="col-xs-12">
					</div>
					</div>
						<table id="testTable"
							class="table table-striped table-hover table-bordered col-xs-12">
							<thead>
								<tr
									style="background: #579EC8; color: white; font-weight: normal;">
									<td style="width:10px;">SL No.</td>
									<td style="width:30px;">Item Code</td>
									<td style="">Name Of Materials</td>
									<td style="">Unit</td>
									<td style="">Consumed</td>
									<!-- Actual Consumed added by taleb req by alamin -->
									<td style="width:100px;">Actual Consumed</td>
									<td style="width:70px;">Re-Use</td>
									<td style="width:80px;">Total</td>
									<td style="">Recovery</td>
									<td style="">Re-Balance</td>
									<td style="">Remarks</td>
								</tr>
								 <tr
									style="background: #579EC8; color: white; font-weight: normal;">
									<td style=""></td>
									<td style=""></td>
									<td style=""></td>
									<td style=""></td>
									<td style=""></td>
									<td style=""></td>
									<td style=""></td>
									<td style=""></td>
									<td style="width:150px;">Serviceable&nbsp;<b>|</b>&nbsp;UnServiceable</td>
									<td style="">Serviceable&nbsp;<b>|</b>&nbsp;UnServiceable</td>
									<td style=""></td>
								</tr> 
							</thead>
							<tbody>
								<c:forEach items="${asBuiltDtl}" var="asBuilt"
									varStatus="loop">
									<tr><%-- <c:set var="test" value="${loop.index+1}"></c:set> --%>
										<td>${loop.index+1}</td>
										<td><c:out value="${asBuilt.itemCode}" /><input	type="hidden" value="${asBuilt.itemCode}" name="itemCode" /></td>
										<td>${asBuilt.itemName} <input	type="hidden" value="${asBuilt.itemName}" name="itemName" /></td>
										<td><c:out value="${asBuilt.uom}" /><input type="hidden" value="${asBuilt.uom}" name="uom" /></td>
										<%-- <td><c:out value="${asBuilt.consume}" /><input type="hidden" value="${asBuilt.consume}" id="consume${loop.index}" name="consume" /></td> --%>
										<td><c:out value="${asBuilt.consume}" /></td>
										<td><input type="number" value="${asBuilt.consume}" style="width:100px;"
										step="0.001" id="consume${loop.index}" name="consume" /></td>
										<td><input style="width:70px;"
											type="number" step="0.001" value="0" name="reUse" onblur="getTotalCost(this)"
											id="reUse${loop.index}" /></td>
										<td><input type="number" step="0.001" value="0" name="total" style="width:80px;"
											id="total${loop.index}" readonly="readonly" /></td>
										<td style="width:150px;"><input style="width:80px;"
											type="number" step="0.001" value="0" name="recServiceable" onblur="calc(this)"
											id="recServiceable${loop.index}" />
									<input style="width:80px;"
											type="number" step="0.001" value="0" name="recUnServiceable" onblur="autoShow(this.id)"
											id="recUnServiceable${loop.index}" /></td>
										<td><input style="width:80px;"
											type="number" step="0.001" value="0" name="reBalServiceable" readonly="readonly"
											id="reBalServiceable${loop.index}" />
									<input style="width:80px;" type="number" step="0.001" value="0" name="reBalUnServiceable"
											id="reBalUnServiceable${loop.index}" readonly="readonly" /></td>
										<td><input class="form-control remarks"
											type="text" name="remarks"
											id="remarks${loop.index}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					
				<div class="col-md-12 center" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-12">
						<button type="submit"
							style="margin-right: 6px; border-radius: 6px;"
							class="width-10 pull-center btn btn-lg btn-success">Save</button>
					</div>
				</div>
			</div>			
		</form>
	</div>

</div>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/asBuiltDetailForm.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>