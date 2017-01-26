<%@include file="../common/lsHeader.jsp"%>
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 40%;
	z-index: 9999;
	/* background-color: none;
	width: 100%;
	height: 100%; */
}

.ubuntu-font {
	font-family: 'Ubuntu Condensed', sans-serif;
}
</style>
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Store Requisition</a> /
			List
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">			
			<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
				Requisition List</h2>

			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

			
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty centralStoreRequisitionMstList}">
				<div class="col-sm-12 center">
					<h2 class="green ubuntu-font">
						<i>Congratulation!!! You have no pending task on Store
							Requisition. </i>
					</h2>
				</div>
			</c:if>

			<div class="col-sm-12 center">
				<h6 class="red">
					<i>${msg}</i>
				</h6>
			</div>

			<c:if test="${!empty centralStoreRequisitionMstList}">
				<!-- 				<div class="col-sm-6 pull-right"> -->
				<!-- 					<form method="POST" -->
				<%-- 						action="${pageContext.request.contextPath}/ls/requisitionSearch.do"> --%>

				<!-- 						<div class="form-group col-sm-9 col-sm-offset-2"> -->
				<!-- 							<input type="text" class="form-control" id="search" -->
				<!-- 								placeholder="Search by Requisition No." -->
				<!-- 								style="border: 0; border-bottom: 2px ridge;" -->
				<!-- 								name="requisitionNo" /> -->
				<!-- 						</div> -->
				<!-- 						<div class="col-sm-1"> -->
				<!-- 							<button type="submit" -->
				<!-- 								class="btn btn-info glyphicon glyphicon-search"></button> -->
				<!-- 						</div> -->
				<!-- 					</form> -->
				<!-- 				</div> -->
				<input type="hidden" id="contextPath"
					value="${pageContext.request.contextPath}">
				<table id="centralStoreRequisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Requisition No</td>
							<td style="">Indenter Name</td>
							<td style="">Requisition Date</td>
							<td style="">Requisition To</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${centralStoreRequisitionMstList}"
							var="centralStoreRequisitionMst">
							<tr>
								<td><a
									onclick="redirectURL(${centralStoreRequisitionMst.id}, '${centralStoreRequisitionMst.requisitionTo}')"
									href="javascript:void(0)" style="text-decoration: none;"><c:out
											value="${centralStoreRequisitionMst.requisitionNo}" /></a></td>
								<td><c:out
										value="${centralStoreRequisitionMst.identerDesignation}" /></td>
								<td><fmt:formatDate
										value="${centralStoreRequisitionMst.requisitionDate}"
										pattern="dd-MM-yyyy hh:mm:ss a" /></td>

								<td>${centralStoreRequisitionMst.requisitionTo == 'ss' ? 'Sub Store' : 'Central Store'}</td>
								<td>
									<div class="action-buttons">
										<%-- <a
											href="${pageContext.request.contextPath}/ls/storeRequisitionEdit.do?id=${centralStoreRequisitionMst.id}">
											<i class="ace-icon fa fa-pencil bigger-130"></i>
										</a> --%>
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											onclick="redirectURL(${centralStoreRequisitionMst.id}, '${centralStoreRequisitionMst.requisitionTo}')"
											href="javascript:void(0)"> <i class="fa fa-fw fa-eye"></i>&nbsp;Show
										</a>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<!-- --------------------- -->
		</div>

	</div>
</div>

<script>
	function redirectURL( id, requisitionTo ) {
		var baseURL = $('#contextPath').val();
		var path = baseURL + "/ls/storeRequisitionShow.do";
		var param = {
				'id' : id,
				'requisitionTo' : requisitionTo
		}
		postSubmit(path, param, "POST");
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
	$(document)
			.ready(
					function() {
						$('#centralStoreRequisitionListTable').DataTable({
							"order" : [ [ 2, "desc" ] ]
						});
						document
								.getElementById('centralStoreRequisitionListTable_length').style.display = 'none';
						//document.getElementById('centralStoreRequisitionListTable_filter').style.display = 'none';

					});

	function createNewDiv() {
		document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';
		/* $(".container-fluid").append("<div>hello</div>"); */
	}
</script>
<!--

//-->

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
