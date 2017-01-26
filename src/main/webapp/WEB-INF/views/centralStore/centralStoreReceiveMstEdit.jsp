<%@include file="../common/csHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Central Store receive</a>
			/ Edit
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/csstore/listCsReceive.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>Central
				Store Receive List
			</a> <a
				href="${pageContext.request.contextPath}/cs/store/centralStoreReceiveShow.do?id=${storeRequisitionMst.id}"
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
				action="${pageContext.request.contextPath}/cs/store/centralReceiveMasterUpdate.do">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="rrNo" class="col-sm-4 control-label"> R.R. no</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="rrNo"
								placeholder="Desco/RRno/001" name="rrNo"
								value="${centralStoreReceiveMst.rrNo}"
								style="border: 0; border-bottom: 2px ridge;" />

						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="storeSection" class="col-sm-4 control-label">Store
							Section</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="storeSection"
								value="${centralStoreReceiveMst.storeSection}"
								placeholder="section"
								style="border: 0; border-bottom: 2px ridge;" name="storeSection" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="supplier" class="col-sm-4 control-label">Suppler</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="supplier"
								value="${centralStoreReceiveMst.supplier}"
								placeholder="supplier name"
								style="border: 0; border-bottom: 2px ridge;" name="supplier">
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="workOrder" class="col-sm-4 control-label">Work
							order/contract no</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="workOrder"
								value="${centralStoreReceiveMst.workOrder}"
								placeholder="work order"
								style="border: 0; border-bottom: 2px ridge;" name="workOrder">
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="workDate" class="col-sm-4 control-label">Work
							Order No/Contract Date</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="workDate"
							value='<fmt:formatDate value="${centralStoreReceiveMst.workDate}" pattern="dd-MM-yyyy"/>'
								style="border: 0; border-bottom: 2px ridge;" name="workDate">
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="dateOfDelivery"
							class="col-sm-4 col-md-4 control-label">Date of delivery</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="dateOfDelivery"
							value='<fmt:formatDate value="${centralStoreReceiveMst.dateOfDelivery}" pattern="dd-MM-yyyy"/>'
								style="border: 0; border-bottom: 2px ridge;"
								name="dateOfDelivery" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="challanNo" class="col-sm-4 col-md-4 control-label">Challan
							No</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="challanNo"
								value="${centralStoreReceiveMst.challanNo}"
								style="border: 0; border-bottom: 2px ridge;" name="challanNo"
								placeholder="DESCO/2015-2016/Ch001" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="returnFor" class="col-sm-4 col-md-4 control-label">Landing
							Date</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="landingDate"
								value='<fmt:formatDate value="${centralStoreReceiveMst.landingDate}" pattern="dd-MM-yyyy"/>'
								style="border: 0; border-bottom: 2px ridge;" name="landingDate" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="billOfLanding" class="col-sm-4 col-md-4 control-label">Bill
							of landing</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="billOfLanding"
								value="${centralStoreReceiveMst.billOfLanding}"
								style="border: 0; border-bottom: 2px ridge;"
								name="billOfLanding" />
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="landingDate" class="col-sm-4 col-md-4 control-label">Landing
							Date</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="landingDate"
								value='<fmt:formatDate value="${centralStoreReceiveMst.landingDate}" pattern="dd-MM-yyyy"/>'
								style="border: 0; border-bottom: 2px ridge;" name="landingDate" />
						</div>
					<!-- 	<input type="text" hidden="true" name="id" value="0"> -->
					</div>

				</div>


				<div class="col-md-12">

					<input type="text" value="${centralStoreReceiveMst.id}"
						hidden="true" name="id" /><%--  <input type="text"
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