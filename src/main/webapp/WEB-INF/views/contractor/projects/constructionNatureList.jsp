<%@include file="../../common/pdHeader.jsp"%>
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
			<a href="${pageContext.request.contextPath}/job/constructionNature.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Add New Construction Nature
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Construction Nature List</h1>
	</div>
	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		<div class="alert alert-success hide">
			<!-- <a href="javascript:void(0)" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
			<strong>Success!</strong> Constructor Nature is updated.
		</div>
		<div class="alert alert-danger hide">
			<!--<a href="#" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
			<strong>Fail!</strong> Constructor Nature is not updated.
		</div>
		
		<input type='hidden'
				value='${pageContext.request.contextPath}' id='contextPath'>
		<div class="col-md-12 table-responsive">
			<c:if test="${empty constructionNatureList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			
			<c:if test="${!empty constructionNatureList}">
				<table id="constructionNatureList"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>Serial No.</td>
							<td>Construction Nature</td>
							<td>Created Date</td>
							<td>Update</td>
						</tr>
					</thead>
					<% int serial = 1; %>
					<tbody>
						<c:forEach items="${constructionNatureList}"
							var="constructionNature" varStatus="loop">
							<tr>
								<td align="center"><%out.print(serial); %></td>
								<td>
									<input type="text" value="${constructionNature.name}" name="name"
									id="name${loop.index}" style="border: 0; border-bottom: 2px ridge; width: 100%">
								 </td>
								<td><fmt:formatDate
									value="${constructionNature.createdDate}" pattern="dd-MM-yyyy" /></td>
								<td align="center"> <a class="btn btn-success btn-xs" style="border-radius:6px;" onclick="updateConstructionNature(${loop.index}, ${constructionNature.id})" href="javascript:void(0)" title="Update"> <i class="glyphicon glyphicon-ok"></i> &nbsp; Update</a></td>
							</tr>
							<% serial++; %>
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
<script>
$(document)
	.ready(
		function() {
			$('#constructionNatureList').DataTable();
			document.getElementById('constructionNatureList_length').style.display = 'none';
			document.getElementById('constructionNatureList_filter').style.display = 'none';
		});

	function updateConstructionNature(index, id) {
		var name = $("#name" + index).val();
		var path = $('#contextPath').val() + "/job/update/ConstructionNature.do";
		//alert(name);
		var params = {
				id : id,
				name : name
			}
			// postSubmit(path, params ,'POST');

			var cDataJsonString = JSON.stringify(params);
			$.ajax({
				url : path,
				data : cDataJsonString,
				contentType : "application/json",
				success : function(data) {
					var pData = JSON.parse(data);
					if (pData == 'success') {
						$('.alert.alert-success').removeClass('hide');
						$(".alert.alert-success").fadeTo(2000, 500).slideUp(500, function(){
						    $(".alert.alert-success").alert('close');
						});
					} else {
						$('.alert.alert-danger').removeClass('hide');
						$(".alert.alert-danger").fadeTo(2000, 500).slideUp(500, function(){
						    $(".alert.alert-danger").alert('close');
						});
					}
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
	}
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>