<%@include file="../common/csHeader.jsp"%>
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
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Store Ticket Issued</a> / List
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/cs/storeTicket/requisitionForm.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Store Ticket (Issue)
			</a>
			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
					Discard</button> -->
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
			Ticket (Item Issued) List</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		<h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5>
	</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty storeTicketMstIssuedList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty storeTicketMstIssuedList}">
				<div class="col-sm-6 pull-right">
					<form method="POST"
						action="${pageContext.request.contextPath}/cs/returnSlip/searchByRsNo.do">

						<!-- /input-group -->

						<div class="form-group col-sm-9 col-sm-offset-2">
							<input type="text" class="form-control" id="search"
								placeholder="Search by Return Slip No."
								style="border: 0; border-bottom: 2px ridge;" name="rsNo" />
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
							<td style="">Store No</td>
							<td style="">Requisiton No</td>
							<td style="">issuedTo</td>
							<td style="">issuedFor</td>
							<td style="">issuedDate</td>
							<td style="">woNo</td>
							<td style="">woDate</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${storeTicketMstIssuedList}" var="storeTicketMstIssued">
							<tr>

								<td><c:out value="${storeTicketMstIssued.ticketNo}" /></td>
								<td><c:out value="${storeTicketMstIssued.requisitonNo}" /></td>
								<td><c:out value="${storeTicketMstIssued.issuedTo}" /></td>
								<td><c:out value="${storeTicketMstIssued.issuedFor}" /></td>
								<td><c:out value="${storeTicketMstIssued.issuedDate}" /></td>
								<td><c:out value="${storeTicketMstIssued.woNo}" /></td>
								<td><c:out value="${storeTicketMstIssued.woDate}" /></td>

								<td>
									<div class="action-buttons">
										<a
											href="#">
											<i class="ace-icon fa fa-pencil bigger-130"></i>
										</a> <a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="#">
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

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
