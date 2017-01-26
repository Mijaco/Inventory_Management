<%@include file="../../common/csHeader.jsp"%>

<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
}
</style>

<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Auction List of Obsolete/Condemn Materials</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />

		<div class="alert alert-success hide" id='updateAlert'>
			Successfully send to Administration.</div>
			
			<c:if test="${!foundUnserviceble}">			
			<div class="alert alert-success text-center" id='NotFoundAlert'>
			No Unserviceable Item Is Available under this category.<button onclick="removeThis()" class="btn-small btn-danger  pull-right">x</button></div>
			
			</c:if>
		<div class="alert alert-danger hide" id='updateValidationError'>
			<strong>Failed to send!</strong>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty auctionProcessMstList}">
				<div class="col-sm-12 center">
					<p class="red ubuntu-font">
						<i>No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty auctionProcessMstList}">
				<table id="dataList" style=""
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>ID</td>
							<td>Auction ID</td>
							<td>Auction Category</td>
							<td>Count Date (Up to:)</td>
							<td>Created Date</td>
							<td>Report</td>
							<td>Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${auctionProcessMstList}" var="mst"
							varStatus="loop">
							<tr>
								<td>${mst.id}</td>

								<td>${mst.auctionName}</td>
								<td>${mst.auctionCategory.name}</td>

								<td><fmt:formatDate value="${mst.toDate}"
										pattern="dd-MM-yyyy" /></td>
								<td><fmt:formatDate value="${mst.createdDate}"
										pattern="dd-MM-yyyy hh:mm:ss a" /></td>

								<td class="center">
									<div class="dropdown">
										<button class="btn btn-sm btn-success dropdown-toggle"
											type="button" style="border-radius: 6px;"
											data-toggle="dropdown" aria-haspopup="true"
											aria-expanded="false">
											Report <span class="caret"></span>
										</button>
										<ul class="dropdown-menu" aria-labelledby="">
											<li class=""><a target="_blank"
												href="${pageContext.request.contextPath}/report/cs/auction/divisionWiseCondemnItems.do?id=${mst.id}">
													Division wise condemn Materials</a></li>
											<li class=""><a target="_blank"
												href="${pageContext.request.contextPath}/report/cs/auction/condemnMaterials.do?id=${mst.auctionName}">For
													List of Obsolete/Condemn Materials</a></li>

										</ul>
									</div>
								</td>
								<td class="center">
								<c:choose>
								<c:when test="${mst.cs_to_admin_flag=='0'}">
								<button type="button" class="btn btn-sm btn-primary"
											onclick="sendCsToAdmin(${mst.id})" id="sendBtn${mst.id}"
											style="border-radius: 6px;">
											<i class="fa fa-paper-plane"></i> <span class="bigger-50">Send
												to Admin</span>
										</button>
								<%-- <c:if
										test="${mst.cs_to_admin_flag=='0'}">
										<button type="button" class="btn btn-sm btn-primary"
											onclick="sendCsToAdmin(${mst.id})" id="sendBtn${mst.id}"
											style="border-radius: 6px;">
											<i class="fa fa-paper-plane"></i> <span class="bigger-50">Send
												to Admin</span>
										</button>
									</c:if> --%>
								</c:when>
								<c:otherwise>
								<label class="btn btn-sm btn-default" style="border-radius: 6px;">
											<span class="bigger-50">Already Sent</span>
												</label>
								</c:otherwise>
								</c:choose>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		<!-- --------------------- -->


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
		$('#dataList').DataTable({
			"columnDefs" : [ {
				"targets" : [ 0 ],
				"visible" : false,
				"searchable" : false
			} ],
			"order" : [ [ 0, 'desc' ] ]
		});
		document.getElementById('dataList_length').style.display = 'none';
		// document.getElementById('dataList_filter').style.display = 'none';
	});

	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});

	function removeThis(){
		$('#NotFoundAlert').remove();
	}
	function sendCsToAdmin(id) {
		var contextPath = $('#contextPath').val();
		var path = contextPath + '/auction/cs/sendCsToAdmin.do';
		var params = {
			id : id
		};
		var cDataJsonString = JSON.stringify(params);
		$.ajax({
			url : path,
			data : cDataJsonString,
			contentType : "application/json",
			success : function(data) {
				var result = JSON.parse(data);
				$('#sendBtn'+id).remove();
				$('.alert.alert-success').removeClass('hide');
				$(".alert.alert-success").fadeTo(3000, 500).slideUp(500);		
				
			},
			error : function(data) {
				alert("From JS: Server Error");
			},
			type : 'POST'
		});
	}
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
