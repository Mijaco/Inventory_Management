<%@include file="../common/adminheader.jsp"%>
<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
<!-- -------------------End of Header-------------------------- -->
<!-- Author :: Ihteshamul Alam -->

<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
	/* background-color: none;
	width: 100%;
	height: 100%; */
}
</style>
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br>
	<span class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/cc/committeeConvenerForm.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Committee Convener Form
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Committee Convener</h1>
	</div>
	
	<!-- This block is hidden -->
	<%-- <c:if test="${!empty committeeConvenerList}">
		<div class="hide">
			<select name="" id="" class="ulist">
				<c:forEach items="${committeeConvenerList}" var="committee">
					<option value="${committee.authUser.id}">${committee.authUser.id}</option>
				</c:forEach>
			</select>
		</div>
	</c:if> --%>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		
		<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
		
		<c:if test="${!empty committeeConvener}">
			<div class="col-md-12 table-responsive">
				<table class="table table-bordered table-hover" id="committeeConvener">
					<thead>
						<tr style="background: #579EC8; color: white; font-weight: normal;">
							<th>Name</th>
							<th>Id</th>
							<th>Email</th>
							<th>Employee Id</th>
							<th>Designation</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${committeeConvener}" var="committee" varStatus="loop">
							<tr>
								<td>${committee.authUser.name}</td>
								<td>${committee.authUser.userid}</td>
								<td>${committee.authUser.email}</td>
								<td>${committee.authUser.empId}</td>
								<td>${committee.authUser.designation}</td>
								<td>
									<a href="javascript:void(0)" class="btn btn-danger btn-sm" style="border-radius: 6px;"
										onclick="deleteThis(${committee.id})"
									><i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		
	</div>
</div>

<script>

	function deleteThis( id ) {
		if( confirm( "Do you want to delete this User from Committee Converner?" ) == true ) {
			var contextPath = $('#contextPath').val();
			
			var path = contextPath + "/cc/deleteUserFromCommitteeConvener.do";
			
			var params = {
					'id' : id
			}
			
			postSubmit(path, params, "POST");
		}
	}

</script>

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
		$('#committeeConvener').DataTable();
		document.getElementById('committeeConvener_length').style.display = 'none';
		//document.getElementById('committeeConvener_filter').style.display = 'none';
	});
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<%@include file="../common/ibcsFooter.jsp"%>