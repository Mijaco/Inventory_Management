<%@include file="../../common/auctionHeader.jsp"%>



<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h2 class="center blue ubuntu-font"
			style="margin-top: 10px;">
			Auction List</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />

		<div class="alert alert-success hide" id='updateAlert'>
			<strong>Success!</strong> Information is updated.
		</div>
		<div class="alert alert-danger hide" id='updateValidationError'>
			<strong>Update Failed!</strong> 
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty condemnMstList}">
				<div class="col-sm-12 center">
					<p class="red ubuntu-font">
						<i>No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty condemnMstList}">
				<table id="dataList" style=""
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>ID</td>
							<td>Auction Name</td>							
							<td>Auction Category</td>
							<td>Count Date (Up to:)</td>
							<td>Store Report</td>
							<td>Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${condemnMstList}" var="mst" varStatus="loop">
							<tr>
								<td>${mst.id}</td>

								<td>${mst.auctionProcessMst.auctionName}</td>
								<td>${mst.auctionCategory.name}</td>
								<td><fmt:formatDate value="${mst.auctionProcessMst.toDate}"
										pattern="dd-MM-yyyy" /></td>
										
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
												href="${pageContext.request.contextPath}/report/cs/auction/divisionWiseCondemnItems.do?id=${mst.auctionProcessMst.id}">
													Division wise condemn Materials</a></li>
											<li class=""><a target="_blank"
												href="${pageContext.request.contextPath}/report/cs/auction/condemnMaterials.do?id=${mst.auctionProcessMst.auctionName}">For
													List of Obsolete/Condemn Materials</a></li>

										</ul>
									</div>
								</td>
								
	
								<td class="center">
									<button type="button" id="reportBtn${mst.id}"
										onclick="viewCondemReportForm(${mst.id})"
										style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-success">
										<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">Edit</span>
									</button>

									<!-- <button type="button" class="btn btn-sm btn-primary"
										style="border-radius: 6px;">
										<i class="fa fa-paper-plane"></i> <span class="bigger-50">Send
											to Admin</span>
									</button> -->
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
	
	
	function viewCondemReportForm(id){
			var contextPath= $('#contextPath').val(); 
			var path= contextPath + '/auction/cc/createCcReport.do';
			var param = {id : id};	
			postSubmit(path, param, 'GET');
		}
	
		
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
