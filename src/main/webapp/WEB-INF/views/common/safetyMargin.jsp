<%-- <%@include file="../common/settingsHeader.jsp"%> --%>
<%-- <%@include file="../inventory/inventoryheader.jsp"%> --%>
<%@include file="../common/csHeader.jsp"%>

<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Re-Order Level Setup</h1>
	</div>

	<script>
	$(document)
	.ready(
			function() {
				$('#khathId').val("${khathId}");
			});
	</script>


	<div class="row"
		style="background-color: white; padding: 10px; margin-top: 10px; margin-bottom: 10px; margin-left: 0px; margin-right: 0px;">
		<div class="form-group" style='margin: 0 auto;'>
			<label for="projectname" class="col-sm-2 col-md-2 control-label">Project
				Name :</label> <input type='hidden'
				value='${pageContext.request.contextPath}' id='contextPath'>
			<input type='hidden' value='${khathId}' id='projectId'>
			<div class="col-sm-8 col-md-8">
				<select class="form-control" name="khathId"
					style="border: 0; border-bottom: 2px ridge;" id='khathId'
					onchange="changeProject()">
					<option disabled selected>Select a project</option>
					<c:if test="${!empty descoKhathList}">
						<c:forEach items="${descoKhathList}" var="descoKhath">
							<c:if test="${descoKhath.khathCode=='SND'}">
								<option value="${descoKhath.id}">
									<c:out value="${descoKhath.khathName}" /></option>
							</c:if>
						</c:forEach>
						<optgroup label="Others">
							<c:forEach items="${descoKhathList}" var="descoKhath">
								<c:if test="${descoKhath.khathCode!='SND'}">
									<option value="${descoKhath.id}">
										<c:out value="${descoKhath.khathName}" /></option>
								</c:if>
							</c:forEach>
						</optgroup>
					</c:if>
				</select>
			</div>
		</div>
		<br> <br>
		<hr>
		<div class="alert alert-success hide">
			<strong>Success!</strong> Re-Order Level is updated.
		</div>
		<div class="alert alert-danger hide">
			<strong>Fail!</strong> Re-Order Level is not updated.
		</div>
		<c:if test="${!empty itemTransactionMstList}">
			<div class="col-xs-12 table-responsive">
				<table class='table table-bordered table-striped'
					id='projectInformation'>
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th class='col-md-1'>Item Code</th>
							<th class='col-md-3' style="width: 100px">Item Name</th>
							<th class='col-md-2'>Project Name</th>
							<th class='col-md-2'>Ledger Name</th>
							<th class='col-md-1'>Safety Margin Qty</th>
							<th class='col-md-1'>Update</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${itemTransactionMstList}" var="productList"
							varStatus="loop">
							<tr>
								<td>${productList.itemCode}</td>
								<td>${productList.itemName}</td>
								<td>${productList.khathName}</td>
								<td>${productList.ledgerName}</td>
								<td><input class="form-control smargin" name="safetyMargin"
									id="safetyMargin${loop.index}" type="text"
									placeholder="safetyMargin"
									style="border: 0; border-bottom: 2px ridge;"
									value="${productList.safetyMargin}"> <input
									type='hidden' class='productid' id='productid${loop.index}'
									value='${productList.id}'></td>
								<td><a class="btn btn-primary btn-xs" style="border-radius: 6px"
									onclick="updateSafetyMargin(${loop.index}, ${productList.id})"
									href="javascript:void(0)" title="Update"> <i
										class="fa fa-fw fa-repeat"></i>&nbsp;Update
								</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<hr>
				<div align='center'>
					<button class='btn btn-success btn-lg' style='border-radius: 6px;' onclick="multipleUpdateSM()">Update
						All</button>
				</div>
			</div>
		</c:if>
	</div>
</div>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/common/safetyMargin.js"></script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
