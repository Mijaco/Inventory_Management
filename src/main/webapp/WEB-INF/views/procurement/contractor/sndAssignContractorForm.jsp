<%@include file="../../common/procurementHeader.jsp"%>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<!-- -------------------End of Header-------------------------- -->
<!-- @author nasrin -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">New
			Contractor Assign For S&amp;D</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST" enctype="multipart/form-data"
			action="${pageContext.request.contextPath}/proc/sndContractorAssignSave.do">

			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}">
			<!-- start for submit  -->
			<div class="oe_title">
				<div class="col-xs-12">

					<div class="form-group">
						<label for="deptId" class="col-sm-2 control-label">S&amp;D
							:</label>
						<div class="col-sm-10">
							<select name="deptId" class="form-control" id="deptId"
								style="border: 0; border-bottom: 2px ridge;">
								<option disabled="disabled" selected="selected">---
									select a S&D ---</option>
								<c:forEach items="${departments}" var="department">
									<option value="${department.id}">${department.deptName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-sm-12">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="contractorId" class="col-sm-2 control-label">Contractor(s)
							:</label>
						<div class="col-sm-10">
							<select name="contractorId" class="form-control"
								id="contractorId" style="border: 0; border-bottom: 2px ridge;">
								<option disabled="disabled" selected="selected">---
									select a S&D ---</option>
								<c:forEach items="${contractorList}" var="contractor">
									<option value="${contractor.id}">${contractor.contractorName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>


				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="submit" id="saveButton"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-lg btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="reset"
							class="width-20  pull-left btn btn-lg btn-danger"
							style="margin-left: 10px; border-radius: 6px;">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button>
					</div>
				</div>
			</div>

		</form>
		<!-- --------------------- -->
	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>