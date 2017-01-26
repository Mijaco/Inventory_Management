<%@include file="../../../common/wsContractorHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/cnws/requisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Store
				Requisition List
			</a>
		</div>

		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
			Requisition Form-1</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		<h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/cnws/storeRequisitionNext.do">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="identerDesignation" class="col-sm-4 control-label">Indentor:
						</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="identerDesignation"
								value="${deptName}" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;"
								name="identerDesignation" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="receivedBy" class="col-sm-4 control-label">Receiver
							Name:</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="receivedBy"
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="receivedBy" value="${authUser.name}"> 
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="contractNo" class="col-sm-4 col-md-4 control-label">Work
							Order No :</label>
						<div class="col-sm-8 col-md-8">
							<input class="form-control" type="text" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;" id="contractNo"
								value="${contractor.contractNo}" name="contractNo" />
						</div>
					</div>


					<div class="col-sm-10">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="contractDate" class="col-sm-4 col-md-4 control-label">Work
							Order Date :</label>
						<div class="col-sm-8 col-md-8">
							<input class="form-control" type="text" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;" id="contractDate"
								value='<fmt:formatDate value="${contractor.contractDate}" pattern="dd-MM-yyyy"/>'
								name="contractDate" />
						</div>
					</div>


				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="jobCardMst" class="col-sm-4 control-label">Job Card No:</label>
						<div class="col-sm-8">
							<select multiple="multiple" id="jobCardMst" name="jobNoIdList"
								name="my-select[]">
								<c:if test="${!empty jobCardMstList}">
									<c:forEach items="${jobCardMstList}" var="jobCardMst">
										<option value="${jobCardMst.id}">
											<c:out value="${jobCardMst.jobCardNo}" /></option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

					<div>
						<p class="red center">** Click the left box item(s) for select
							job(s).</p>
					</div>
				</div>

				<div class="col-md-12 col-sm-12">

					<!-- body -->

				</div>
				<c:if test="${!empty jobCardMstList}">
					<div class="col-md-12" style="padding-top: 15px;">
						<div class="col-xs-12 col-sm-12">
							<button type="submit"
								style="margin-right: 6px; border-radius: 6px;"
								class="width-10 pull-right btn btn-lg btn-success">Next
							</button>
						</div>
					</div>
				</c:if>

			</div>

		</form>
		<!-- --------------------- -->
	</div>
</div>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/pndContractor/jquery.multi-select.js"></script>
<script type="text/javascript">
	$('#jobCardMst').multiSelect({
		selectableHeader : "<div class='custom-header'>Selectable items</div>",
		selectionHeader : "<div class='custom-header'>Selection items</div>",
		// selectableFooter: "<div class='custom-header'>Selectable footer</div>",
		// selectionFooter: "<div class='custom-header'>Selection footer</div>",
		keepOrder : true
	});
</script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../../common/ibcsFooter.jsp"%>