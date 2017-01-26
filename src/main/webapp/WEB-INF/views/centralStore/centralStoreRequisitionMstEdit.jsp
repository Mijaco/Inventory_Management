<%@include file="../common/csHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Central Store
				Requisition </a> / Edit
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/cs/store/listCsRequisition.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>Central
				Store Requisition List
			</a> <a
				href="${pageContext.request.contextPath}/cs/store/centralStoreRequisitionShow.do?id=${centralStoreRequisitionMst.id}"
				style="text-decoration: none;" class="btn btn-info btn-sm"> <span
				class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> Show
			</a>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="oe_title">
			<form method="POST" class=""
				action="${pageContext.request.contextPath}/cs/store/centralRequisitionMasterUpdate.do">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="srfNo" class="col-sm-4 control-label">Requisition
							no</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="srfNo"
								value="${centralStoreRequisitionMst.requisitionNo}"
								readonly="readonly" style="border: 0; border-bottom: 2px ridge;"
								name="requisitionNo" />
						</div>
					</div>

					<div class="form-group">
						<label for="identerName" class="col-sm-4 control-label">Identer
							Name </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="identerName"
								value="${centralStoreRequisitionMst.identerName}"
								placeholder="admin"
								style="border: 0; border-bottom: 2px ridge;" name="identerName" />

						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="requisitionDate" class="col-sm-4 control-label">Requisition Date </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="requisitionDate"
								value='<fmt:formatDate value="${centralStoreRequisitionMst.requisitionDate}" pattern="yyyy-mm-dd"/>'
								 style="border: 0; border-bottom: 2px ridge;"
								name="requisitionDate">
						</div>
					</div>
				</div>

				
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="getPassNo" class="col-sm-4 control-label">Gate
							Pass No. </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="getPassNo"
								readonly="readonly"
								value="${centralStoreRequisitionMst.getPassNo}"
								style="border: 0; border-bottom: 2px ridge;" />
						</div>
					</div>


				</div>
				
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="getPassDate" class="col-sm-4 control-label">Gate
							Pass Date. </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="getPassDate"
								
								value='<fmt:formatDate value="${centralStoreRequisitionMst.getPassDate}" pattern="yyyy-mm-dd"/>'
								style="border: 0; border-bottom: 2px ridge;" />
						</div>
					</div>


				</div>

		</div>

		<div class="col-md-12">

			<input type="text" value="${centralStoreRequisitionMst.id}" hidden="true"
				name="id" />
			<%--  <input type="text"
						value='<sec:authentication property="principal.username" />'
						hidden="true" name="modifiedBy" /> --%>
		</div>
		<div class="col-sm-12 center">
			<div class="form-group">
				<button type="submit" class="btn btn-success">
					<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
					Update
				</button>
			</div>
		</div>
		</form>
	</div>
	<!-- --------------------- -->
</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>