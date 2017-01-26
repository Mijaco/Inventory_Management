<%@include file="../common/settingsHeader.jsp"%>
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

<div class="container-fluid icon-box" style="background-color: offwhite;">
	
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			
			<a href="${pageContext.request.contextPath}/settings/add/newStoreLedgerForm.do"	
			style="text-decoration: none;" class="btn btn-success btn-sm"> 
			<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> 
			Add New Store Ledger
			</a>
		
		</div>		
		
		<h1 class="center blue"	style="margin-top: 0; font-style: italic; 
		font-family: 'Ubuntu Condensed', sans-serif;"> List of Store Ledger </h1>
	
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty storeLedgerList}">
				<div class="col-sm-12 center">
					<p class="green" style="font-size: 16px;">
						<i> You have no Store Ledger </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty storeLedgerList}">
				<div class="col-sm-6 pull-right">
					<form method="POST"	action="#">
						<div class="form-group col-sm-9 col-sm-offset-2">
							<input type="text" class="form-control" id="searchByLedgerCode"
								placeholder="Search by Ledger Code"
								style="border: 0; border-bottom: 2px ridge;" name="searchByLedgerCode" />
						</div>
						<div class="col-sm-1">
							<button type="submit"
								class="btn btn-info glyphicon glyphicon-search"></button>
						</div>
					</form>
				</div>
				<table id="storeLedgerListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr	style="background: #579EC8; color: white; font-weight: normal;">
							<th style="text-align: center;"> Ledger Code </th>
							<th style="text-align: center;"> Ledger Name </th>
							<th style="text-align: center;"> Status </th>
							<th style="text-align: center;"> Remarks </th>
							<th style="text-align: center;"> Action </th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${storeLedgerList}" var="storeLedgerList">
							<tr>
								<td><a href="${pageContext.request.contextPath}/settings/show/storeLedger.do?id=${storeLedgerList.id}" 
								style="text-decoration: none;">
									<c:out value="${storeLedgerList.ledgerCode}" /></a></td>
								<td><c:out value="${storeLedgerList.ledgerName}" /></td>
								<td><c:out value="${storeLedgerList.active}"/></td>
								<td><c:out value="${storeLedgerList.remarks}" /></td>								
								<td style="text-align: center;">
									<div class="action-buttons">										
										<a 	class="blue"
											href="${pageContext.request.contextPath}
											/settings/update/storeLedger.do?
											id=${storeLedgerList.id}">
											<i class="ace-icon fa fa-pencil bigger-130"></i>
										</a>										
										<a 	class="green" 
											href="${pageContext.request.contextPath}
											/settings/show/storeLedger.do?id=${storeLedgerList.id}">
											<i class="glyphicon glyphicon-eye-open"></i>
										</a>										
										<%-- <a 	class="red"
											href="${pageContext.request.contextPath}
											/settings/delete/storeLedger.do?
											id=${storeLedgerList.id}">
											<i class="ace-icon fa fa-trash-o bigger-130"></i>
										</a> --%>
										
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		
		<script type="text/javascript">
		$(document).ready(
			function() {
				$('#storeLedgerListTable').DataTable();
				document.getElementById(
						'storeLedgerListTable_length').style.display = 'none';
				document.getElementById(
						'storeLedgerListTable_filter').style.display = 'none';
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
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>