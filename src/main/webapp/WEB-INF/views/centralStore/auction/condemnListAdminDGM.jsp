<%@include file="../../common/auctionHeader.jsp"%>

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

		<h2 class="center blue ubuntu-font" style="margin-top: 10px;">
			Auction List</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />

		<div class="alert alert-success hide" id='updateAlert'>
			<strong>Success!</strong> Information is updated.
		</div>
		<div class="alert alert-danger hide" id='updateValidationError'>
			<strong>Update Failed!</strong> Quantity can not greater than Ledger.
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
							<td>Count Date (Up to:)</td>
							<td>Auction Category</td>
							<td>Store Report</td>
							<td>Condemn Committee</td>
							<td>Auction Committee</td>
							<td>Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${condemnMstList}" var="mst" varStatus="loop">
							<tr>
								<td>${mst.id}</td>

								<td>${mst.auctionProcessMst.auctionName}</td>

								<td><fmt:formatDate value="${mst.auctionProcessMst.toDate}"
										pattern="dd-MM-yyyy" /></td>
								<td>${mst.auctionCategory.name}</td>
								
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
								<%-- mst.auctionProcessMst.admin_to_cc_flag----${mst.auctionProcessMst.admin_to_cc_flag} --%>
								<c:choose>
								<c:when test="${mst.auctionProcessMst.admin_to_cc_flag=='0'  &&  mst.auctionProcessMst.active == true}">
								<button type="button"
											onclick="assignCC(${mst.auctionProcessMst.id})"
											style="border-radius: 6px;"
											class="width-10  btn btn-sm btn-primary">
											<i class="ace-icon fa fa-check-square-o"></i> <span
												class="bigger-30">Assign</span>
										</button>
								</c:when>
								<c:otherwise>
								<label class="btn">Assigned</label>
								</c:otherwise>
								</c:choose>
								<%-- <c:if
										test="${mst.auctionProcessMst.admin_to_cc_flag=='0'}">
										<button type="button"
											onclick="assignCC(${mst.auctionProcessMst.id})"
											style="border-radius: 6px;"
											class="width-10  btn btn-sm btn-primary">
											<i class="ace-icon fa fa-check-square-o"></i> <span
												class="bigger-30">Assign</span>
										</button>
									</c:if> --%>
									
									</td>
								<td class="center">
								<c:choose>
								<c:when test="${mst.auctionProcessMst.cc_to_admin_flag=='1' && mst.auctionProcessMst.admin_to_auction_flag=='0'  &&  mst.auctionProcessMst.active == true }">
								<button type="button"
											onclick="assignAC(${mst.auctionProcessMst.id})"
											style="border-radius: 6px;"
											class="width-10  btn btn-sm btn-primary">
											<i class="ace-icon fa fa-check-square-o"></i> <span
												class="bigger-30">Assign</span>
										</button>
								</c:when>
								<c:when test="${mst.auctionProcessMst.admin_to_auction_flag=='0' }">
								<label class="btn btn-success">Assigned</label>
								</c:when>
								<c:otherwise>
								<label class="btn btn-default">Not Ready</label>
								</c:otherwise>
								</c:choose>
								<%-- mst.auctionProcessMst.cc_to_admin_flag ---- ${mst.auctionProcessMst.cc_to_admin_flag}--<br>
								mst.auctionProcessMst.admin_to_auction_flag ---- ${mst.auctionProcessMst.admin_to_auction_flag}--
								 --%><%-- <c:if
										test="${mst.auctionProcessMst.cc_to_admin_flag eq '1' && mst.auctionProcessMst.admin_to_auction_flag eq '1' }">
										<button type="button"
											onclick="assignAC(${mst.auctionProcessMst.id})"
											style="border-radius: 6px;"
											class="width-10  btn btn-sm btn-primary">
											<i class="ace-icon fa fa-check-square-o"></i> <span
												class="bigger-30">Assign</span>
										</button>
									</c:if> --%>
									
									</td>
									<td>
									<c:choose>
									<c:when test="${mst.auctionProcessMst.cc_to_admin_flag=='1' && mst.auctionProcessMst.admin_to_auction_flag=='1' && mst.auctionProcessMst.auction_wo_flag eq '1' &&  mst.auctionProcessMst.active == true}">
										<form action="${pageContext.request.contextPath}/ac/finalClose.do"
											method="POST">
											<input type="hidden" value="${mst.auctionProcessMst.id}"
												name="id" />
											<button type="submit" style="border-radius: 6px;" class="btn btn-success btn-xs"><i class="fa fa-fw fa-power-off"></i>&nbsp; close</button>
										</form>
										</c:when>
										
										<c:when test="${mst.auctionProcessMst.cc_to_admin_flag=='1' && mst.auctionProcessMst.admin_to_auction_flag=='1' && mst.auctionProcessMst.auction_wo_flag eq '1' &&  mst.auctionProcessMst.active == false}">
										<label class="btn btn-default">closed</label>
										</c:when>
										<c:otherwise>
										<label class="btn btn-default">Not Ready</label>
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
	
	
	function assignCC(id){
			var contextPath= $('#contextPath').val(); 
			var path= contextPath + '/auction/addCcConvener.do';
			var param = {apId : id};	
			postSubmit(path, param, 'GET');
		}
	function assignAC(id){
		var contextPath= $('#contextPath').val(); 
		var path= contextPath + '/auction/addAcConvener.do';
		var param = {apId : id};	
		postSubmit(path, param, 'GET');
	}
		
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
