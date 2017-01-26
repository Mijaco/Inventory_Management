<%@include file="../common/wsHeader.jsp"%>
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
		
		<div class="o_form_buttons_edit" style="display: block;">
			
			<h1 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Transformer Close Out
				 List</h1>
		</div>
	</div>

	<div class="row" 
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			
			

			<c:if test="${!empty transformerCloseOutMstList}">
				
				<table id="storeRequisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Close Out No</td>
							<td style="">Contract No</td>
							<td style="">Start Date</td>
							<td style="">End Date</td>
					</thead>

					<tbody>
						<c:forEach items="${transformerCloseOutMstList}"
							var="transformerCloseOutMst">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/transformerCloseOutDtl.do?id=${transformerCloseOutMst.id}"
									style="text-decoration: none;"><c:out
											value="${transformerCloseOutMst.xCloseoutNo}" /></a></td>	
											<td><c:out
											value="${transformerCloseOutMst.woNumber}" /></td>								
								<td><fmt:formatDate
										value="${transformerCloseOutMst.startDate}"
										pattern="dd-MM-yyyy" /></td>
								<td><fmt:formatDate
										value="${transformerCloseOutMst.endDate}"
										pattern="dd-MM-yyyy" /></td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<!-- --------------------- -->
		</div>

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
	$(document)
			.ready(
					function() {
						$('#storeRequisitionListTable').DataTable();
						document
								.getElementById('storeRequisitionListTable_length').style.display = 'none';
						// document.getElementById('storeRequisitionListTable_filter').style.display = 'none';

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
