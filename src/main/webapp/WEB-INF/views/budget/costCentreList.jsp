<%@include file="../common/budgetHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->

<!-- @author: Ihteshamul Alam -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<a href="${pageContext.request.contextPath}//bgt/costCenterForm.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span>&nbsp;Cost Centre Form
			</a>			
		</div>	
		<h1  class="center blue col-md-8"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Cost Centre List</h1>
	</div>
	
	<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
	
	<div class="container">
		<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 40px;">
			<c:if test="${empty costCentreList}">
				<h4 class="text-success" align="center">No data found in database.</h4>
			</c:if>
			<c:if test="${!empty costCentreList}">
				<table class="table table-bordered" id="costCentreList">
					<thead>
						<tr style="background: #579EC8; color: white; font-weight: normal;">
							<th>Code</th>
							<th>Name</th>
							<th>Dept. Code</th>
							<th>Remarks</th> 
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${costCentreList}" var="costCentre">
							<tr>
								<td>${costCentre.costCentreCode}</td>
								<td>${costCentre.costCentreName}</td>
								<td>${costCentre.deptCode}</td>
								<td>${costCentre.remarks}</td>
								<td>
									<a class="btn btn-danger btn-xs" style="border-radius: 6px;" onclick="deleteCostCentre(${costCentre.id})">
										<i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
									</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
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

<script type="text/javascript">
	$(document).ready(function() {
		$('#costCentreList').DataTable({			
			"order" : [ [ 0, 'asc' ] ],
			"bLengthChange" : false
		});
		
	});

	function deleteCostCentre(id) {
		if( confirm( "Do you want to delete this Cost Centre?" ) == true ) {
			var baseURL = $('#contextPath').val();
			var path = baseURL + "/bgt/deleteCostCenter.do";
			var param = {
					'id' : id
			}
			postSubmit(path, param, "POST");
		}
	}
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>