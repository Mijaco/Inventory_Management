<%@include file="../common/adminheader.jsp"%>
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
	
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			
			<a href="${pageContext.request.contextPath}/settings/add/newStoreLocationForm.do"	
			style="text-decoration: none;" class="btn btn-success btn-sm"> 
			<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> 
			Add New Store Location
			</a>
		
		</div>		
		
		<h1 class="center blue"	style="margin-top: 0; font-style: italic; 
		font-family: 'Ubuntu Condensed', sans-serif;"> List of Store Location </h1>
	
	</div>

	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		
		<div class="col-sm-12 table-responsive">
		
			<c:if test="${!empty deleteflag}">
				<div class="alert alert-danger" id='deletealert'>
					<strong>Success!</strong> Store Location informations are deleted.
				</div>
			</c:if>
		
			<c:if test="${empty storeLocationList}">
				<div class="col-sm-12 center">
					<p class="green" style="font-size: 16px;">
						<i> You have no Store Location </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty storeLocationList}">
<!-- 				<div class="col-sm-6 pull-right"> -->
<!-- 					<form method="POST"	action="#"> -->
<!-- 						<div class="form-group col-sm-9 col-sm-offset-2"> -->
<!-- 							<input type="text" class="form-control" id="searchByLocationName" -->
<!-- 								placeholder="Search by Location Name" -->
<!-- 								style="border: 0; border-bottom: 2px ridge;" name="searchByLocationName" /> -->
<!-- 						</div> -->
<!-- 						<div class="col-sm-1"> -->
<!-- 							<button type="submit" -->
<!-- 								class="btn btn-info glyphicon glyphicon-search"></button> -->
<!-- 						</div> -->
<!-- 					</form> -->
<!-- 				</div> -->
				<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
				<table id="storeLocationListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr	style="background: #579EC8; color: white; font-weight: normal;">
							<th style="text-align: center;"> Location Name </th>
							<th style="text-align: center;"> Address </th>
							<th style="text-align: center;"> Store Name </th>
							<th style="text-align: center;" class="hide"> Remarks </th>
							<th style="text-align: center;" class="col-xs-3"> Action </th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${storeLocationList}" var="storeLocationList">
							<tr>
								<td><a href="${pageContext.request.contextPath}/settings/show/storeLocation.do?id=${storeLocationList.id}" 
								style="text-decoration: none;">
									<c:out value="${storeLocationList.name}" /></a></td>
								<td><c:out value="${storeLocationList.description}" /></td>
								<td><c:out value="${storeLocationList.storeCode  == 'CS' ? 'Central Store' : 'Sub Store'}"/></td>
								<td class="hide"><c:out value="${storeLocationList.remarks}" /></td>								
								<td style="text-align: center;">
									<div class="action-buttons">										
										<a 	class="btn btn-xs btn-primary" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}
											/settings/update/storeLocation.do?
											id=${storeLocationList.id}">
											<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
										</a>										
										<a 	class="btn btn-xs btn-success" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}
											/settings/show/storeLocation.do?id=${storeLocationList.id}">
											<i class="fa fa-fw fa-eye"></i>&nbsp;View
										</a>
										<a 	class="btn btn-xs btn-danger" style="border-radius: 6px;"
											href="javascript:showConfirmation(${storeLocationList.id})">
											<i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
										</a>
										
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
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
		$(document).ready(
			function() {
				$('#storeLocationListTable').DataTable();
				document.getElementById(
						'storeLocationListTable_length').style.display = 'none';
				//document.getElementById('storeLocationListTable_filter').style.display = 'none';
			});

			function createNewDiv() {
				document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';
			}
		</script>		
	</div>
</div>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
	
	function showConfirmation(id) {
		var response = confirm("Do you want to delete this record?");
		if( response == true ) {
			var path = $('#contextPath').val() + "/settings/delete/storeLocation.do";
			var param = {
				id : id
			}
			//alert(id);
			postSubmit(path, param, 'POST');
		} else {
			//console.log( "null" );
		}
	}
	
	$( document ).ready( function() {
		$(".alert.alert-success").fadeTo(4000, .5).slideUp(500, function() {
			  //  $(".alert.alert-success").alert('close');
		});
		
		$(".alert.alert-danger").fadeTo(4000, .5).slideUp(500, function() {
			  //  $(".alert.alert-danger").alert('close');
		});
	});
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>