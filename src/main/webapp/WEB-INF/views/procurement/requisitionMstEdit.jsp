<%@include file="../common/procurementHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Procurement
				Requisition</a> / Edit
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/procurement/requisition/list.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Requisition List
			</a> <a
				href="${pageContext.request.contextPath}/procurement/requisition/show.do?id=${requisitionMst.id}"
				style="text-decoration: none;" class="btn btn-info btn-sm"> <span
				class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> Show
			</a>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->

		<div class="oe_title">

			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="prfNo" class="col-sm-4 control-label">PRF No.</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="prfNo"
							value="${requisitionMst.prfNo}" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;" name="prfNo" />

					</div>
				</div>
				<div class="col-sm-10">&nbsp;&nbsp;</div>
				<div class="form-group">
					<label for="requisitionOfficer" class="col-sm-4 control-label">Requisition
						By</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="requisitionOfficer"
							value="${requisitionMst.requisitionOfficer}" readonly="readonly"
							placeholder="admin" style="border: 0; border-bottom: 2px ridge;"
							name="requisitionOfficer" />
					</div>
				</div>
				<div class="col-sm-10">&nbsp;&nbsp;</div>
				<div class="form-group">
					<label for="requisitionTo" class="col-sm-4 control-label">Requisition
						To</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="requisitionTo"
							value="${requisitionMst.requisitionTo}" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;" name="requisitionTo" />
					</div>
				</div>

			</div>


			<div class="col-md-6 col-sm-6">

				<div class="form-group">
					<label for="requisitionDate"
						class="col-sm-4 col-md-4 control-label">Req. Date</label>
					<div class="col-sm-8 col-md-8">
						<input type="text" class="form-control" id="requisitionDate"
							readonly="readonly"
							value='<fmt:formatDate value="${requisitionMst.requisitionDate}" pattern="dd-MM-yyyy"/>'
							style="border: 0; border-bottom: 2px ridge;"
							name="requisitionDate" />
					</div>
				</div>
				<div class="col-sm-10">&nbsp;&nbsp;</div>
				<div class="form-group">
					<label for="departmentFrom" class="col-sm-4 col-md-4 control-label">Requisition
						From</label>
					<div class="col-sm-8 col-md-8">
						<input type="text" class="form-control" id="departmentFrom"
							value="${requisitionMst.departmentFrom}" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;"
							name="departmentFrom" />
					</div>
				</div>
				<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
				<div class="form-group">
					<label for="status" class="col-sm-4 col-md-4 control-label">Status</label>
					<div class="col-sm-8 col-md-8">
						<input type="text" class="form-control" id="status"
							value="${stateName}" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;" name="status" />
					</div>
				</div>
			</div>

				<div class="col-md-12">
					<label for="justification" class="col-sm-4 control-label">Justification
						:</label>
					<div class="form-group col-sm-12">
						<textarea rows="3" id="justification" class="form-control"
							maxlength="1000" name="justification">${requisitionMst.justification}</textarea>
					</div>
					<input type="text" value="${requisitionMst.id}" hidden="true"
						name="id" />
						
					<input type="text" value='<sec:authentication property="principal.username" />'
					hidden="true" name="modifiedBy"/>
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