<%@include file="../../common/procurementHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->

<!-- Author: Shimul, IBCS -->

<style>

	textarea {
		resize: none;
	}

</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/process/procurementProcessForm.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"> </span>
				Procurement Committee Form
			</a>
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				<em> Procurement Committee List </em>
			</h1>
		</div>
	</div>
	
	<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
	
	<div class="container">
		<div class="row"
			style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
			<c:if test="${!empty procCommList}">
				<div class="table-responsive">
					<table class="table table-bordered">
						<thead>
							<tr style="background: #579EC8; color: #fff;">
								<td class="col-xs-3">Name</td>
								<td class="col-xs-2">Is Active?</td>
								<td class="col-xs-5">Remarks</td>
								<td class="col-xs-2">Action</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${procCommList}" var="procCommList"
								varStatus="loop">
								<tr>
									<td>${procCommList.authUser.name}</td>
									<td>${procCommList.active == true ? 'Yes' : 'No'}</td>
									<td>${procCommList.remarks}</td>
									
									<td>
									
									<a href="javascript:void(0)" style="border-radius: 6px"
										class="btn btn-xs btn-primary edit${loop.index}"
										onclick="editProcurementProcess(${procCommList.id})"> <i
											class="fa fa-fw fa-edit"></i>&nbsp; Edit
									</a>
									
									<a href="javascript:void(0)"  style="border-radius: 6px" class="btn btn-xs btn-danger"
										onclick="deleteProcurementProcess(${procCommList.id})"> <i class="fa fa-fw fa-trash-o"></i>&nbsp;
											Delete
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>
	</div>
</div>

<script>
	function editProcurementProcess( id ) {
		var basePath = $('#contextPath').val();
		var path = basePath + "/process/editProcurementProcess.do";
		var param = {
				'id' : id
		}
		postSubmit(path, param, "POST");
	} //update
	
	function deleteProcurementProcess( id ) {
		if( confirm("Do you want to delete this Process?") == true ) {
			var basePath = $('#contextPath').val();
			var path = basePath + "/process/deleteProcurementProcess.do";
			var param = {
					'id' : id
			}
			postSubmit(path, param, "POST");
		}
	} //delete
</script>