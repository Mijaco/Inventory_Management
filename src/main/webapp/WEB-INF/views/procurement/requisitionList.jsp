<%@include file="../common/procurementHeader.jsp"%>
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
			<a href="#" style="text-decoration: none;">Procurement
				Requisition</a> / List
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/procurement/requisition/create.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Requisition
			</a>
			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
					Discard</button> -->
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty requisitionMstList}">
				<div class="col-sm-12 center">
					<p class="red"><i>Sorry!!! No Data Found in Database. </i></p>
				</div>
			</c:if>
			<c:if test="${!empty requisitionMstList}">
				<div class="col-sm-6 pull-right">
					<form method="POST"
						action="${pageContext.request.contextPath}/procurement/requisition/searchByPrfNo.do">

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
								placeholder="Search by PRF No."
								style="border: 0; border-bottom: 2px ridge;" name="prfNo" />								
						</div>
						<div class="col-sm-1">
							<button type="submit"
								class="btn btn-info glyphicon glyphicon-search"></button>
						</div>
					</form>
				</div>
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">PRF No</td>
							<td style="">Requisition By</td>
							<td style="">Requisition Date</td>
							<td style="">Justification</td>
							<td style="">Status</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${requisitionMstList}" var="requisitionMst">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/procurement/requisition/show.do?id=${requisitionMst.id}"
									style="text-decoration: none;"><c:out
											value="${requisitionMst.prfNo}" /></a></td>
								<td><c:out value="${requisitionMst.requisitionOfficer}" /></td>
								<td><fmt:formatDate
										value="${requisitionMst.requisitionDate}"
										pattern="dd-MM-yyyy" /></td>

								<td><c:out value="${requisitionMst.justification}" /></td>
								<td><c:out value="${requisitionMst.status}" /></td>

								<td>
									<div class="action-buttons">
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/procurement/requisition/mstEdit.do?id=${requisitionMst.id}">
											<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
										</a> <a class="btn btn-success btn-xs" style="border-radius: 6px;""
											href="${pageContext.request.contextPath}/procurement/requisition/show.do?id=${requisitionMst.id}">
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
						$('#requisitionListTable').DataTable();
						document.getElementById('requisitionListTable_length').style.display = 'none';
						document.getElementById('requisitionListTable_filter').style.display = 'none';

					});
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
