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
		<h4>
			<a href="#" style="text-decoration: none;">Received Items</a> / List
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/centralStore/rcvPrcess/itemRcvFrom.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Central Store Receive Form
			</a>
			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
					Discard</button> -->
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty receivedItemList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty receivedItemList}">
				<div class="col-sm-6 pull-right">
					<form method="POST"
						action="#">

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
								placeholder="Search by RR No."
								style="border: 0; border-bottom: 2px ridge;" name="rrNo" />
						</div>
						<div class="col-sm-1">
							<button type="submit"
								class="btn btn-info glyphicon glyphicon-search"></button>
						</div>
					</form>
				</div>
				<table id="centralStorereceiveMstListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th style="">R.R No</th>
							<th style="">Chalan No</th>
							<th style="">Contract No</th>
							<th style="">Received By</th>
							<th style="">Received Date</th>
							<th style="">Action</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${receivedItemList}" var="receivedItem">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/centralStore/rcvPrcess/itemRcvShow.do?receivedReportNo=${receivedItem.receivedReportNo}"
									style="text-decoration: none;"><c:out
											value="${receivedItem.receivedReportNo}" /></a></td>
								<td><c:out value="${receivedItem.chalanNo}" /></td>
								<td><c:out value="${receivedItem.contractNo}" /></td>

								<td><c:out value="${receivedItem.receivedBy}" /></td>
								<td><fmt:formatDate value="${receivedItem.receivedDate}"
										pattern="dd-MM-yyyy" />
								<td>
									<div class="action-buttons">
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/cs/store/centralStoreReceiveEdit.do?id=${receivedItem.receivedReportNo}">
											<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
										</a> <a class="btn btn-success btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/cs/store/centralStoreReceiveShow.do?id=${receivedItem.receivedReportNo}">
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
						$('#centralStorereceiveMstListTable').DataTable();
						document
								.getElementById('centralStorereceiveMstListTable_length').style.display = 'none';
						document
								.getElementById('centralStorereceiveMstListTable_filter').style.display = 'none';

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
