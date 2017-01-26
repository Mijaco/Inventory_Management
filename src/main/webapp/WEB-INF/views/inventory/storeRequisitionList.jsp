<%@include file="../inventory/inventoryheader.jsp"%>
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
		<h4>
			<a href="#" style="text-decoration: none;">Store
				Requisition</a> / List
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/storeRequisitionCreate.do" onclick="createNewDiv()"
				style="text-decoration: none;" target="_blank" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Store Requisition
			</a>
			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
					Discard</button> -->
		</div>
	</div>

	<div style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty storeRequisitionMstList}">
				<div class="col-sm-12 center">
					<p class="red"><i>Sorry!!! No Data Found in Database. </i></p>
				</div>
			</c:if>
			<c:if test="${!empty storeRequisitionMstList}">
				<div class="col-sm-6 pull-right">
					<form method="POST"
						action="${pageContext.request.contextPath}/inventory/searchBySrfNo.do">

						<!-- <div class="input-group">
							<input type="" class="form-control"
								placeholder="Search for..."> <span
								class="input-group-btn">
								<button class="btn btn-default" type="button">Go!</button>
							</span>
						</div> -->
						<!-- /input-group -->

						<div class="form-group col-sm-9 col-sm-offset-2">
							<input type="text" class="form-control" id="search"
								placeholder="Search by SRF No."
								style="border: 0; border-bottom: 2px ridge;" name="srfNo" />								
						</div>
						<div class="col-sm-1">
							<button type="submit"
								class="btn btn-info glyphicon glyphicon-search"></button>
						</div>
					</form>
				</div>
				<table id="storeRequisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">SRF No</td>
							<td style="">Requisition By</td>
							<td style="">Requisition Date</td>
							<td style="">Justification</td>
							<td style="">Status</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${storeRequisitionMstList}" var="storeRequisitionMst">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/inventory/storeRequisitionShow.do?id=${storeRequisitionMst.id}"
									style="text-decoration: none;"><c:out
											value="${storeRequisitionMst.srfNo}" /></a></td>
								<td><c:out value="${storeRequisitionMst.requisitionOfficer}" /></td>
								<td><fmt:formatDate
										value="${storeRequisitionMst.requisitionDate}"
										pattern="dd-MM-yyyy" /></td>

								<td><c:out value="${storeRequisitionMst.justification}" /></td>
								<td><c:out value="${storeRequisitionMst.status}" /></td>

								<td>
									<div class="action-buttons">
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/inventory/requisitionStoreEdit.do?id=${storeRequisitionMst.id}">
											<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
										</a> <a class="btn btn-success btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/inventory/storeRequisitionShow.do?id=${storeRequisitionMst.id}">
											<i class="fa fa-fw fa-eye"></i>&nbsp;Show
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

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#storeRequisitionListTable').DataTable();
						document.getElementById('storeRequisitionListTable_length').style.display = 'none';
						document.getElementById('storeRequisitionListTable_filter').style.display = 'none';

					});
	
	function createNewDiv(){
		document.getElementsByClassName("container-fluid").innerHTML='<object type="type/html" data="home.html" ></object>';
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
