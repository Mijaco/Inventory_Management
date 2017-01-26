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
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Store Requisition</a> /
			List
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
		<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue">Progress Report <i> <b>   </b> </i>
		</h1>
		<form
			action="${pageContext.request.contextPath}/searchProgress.do"
			method="POST" id="myForm">
		
		<table class="table table-bordered table-hover">
				<!-- <tr>
					<td colspan="5"
						style="font-weight: normal; font-size: 18px; text-align: center; font-family: 'Ubuntu Condensed', sans-serif;">
						Total Transformer Receive Report</td>
				</tr> -->
				<tr>
					
					<td class="col-xs-1 success">From Date</td>
					<td class="col-xs-2">
						<input type="text" id="fromDate" class="form-control datepicker-13" value="" />
					</td>
					<td class="col-xs-1 success">To Date</td>
					<td class="col-xs-2">
						<input type="text" id="toDate" class="form-control datepicker-13" value="" />
					</td>
					<td class="info">Work Order No:</td>
							<td class=""><input type="text" class="form-control" id="workOrderNo"
								placeholder="" value="" style="border: 0; border-bottom: 2px ridge;"
								name="workOrderNo"><input
								type="hidden" name="contextPath" id="contextPath"
								value="${pageContext.request.contextPath}">
							</td>
					<td class="col-xs-2 center"><div class="col-xs-12 col-sm-12">
						<button type="submit"
							style="margin-right: 6px; border-radius: 6px;"
							class="width-10 pull-center btn btn-lg btn-success">Search</button>
					</div></td>
					
				</tr>

			</table>
			</form>
		</div>
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty wsReceivePreventiveMst}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Congratulation!!! You have no pending task on Transformer
							Receive. </i>
					</h2>
				</div>
			</c:if>

			<div class="col-sm-12 center">
				<h6 class="red">
					<i>${msg}</i>
				</h6>
			</div>

			<c:if test="${!empty wsReceivePreventiveMst}">
<!-- 				<div class="col-sm-6 pull-right"> -->
<!-- 					<form method="POST" -->
<%-- 						action="${pageContext.request.contextPath}/ls/returnSlip/rsSearch.do"> --%>

<!-- 						<div class="form-group col-sm-9 col-sm-offset-2"> -->
<!-- 							<input type="text" class="form-control" id="search" -->
<!-- 								placeholder="Search by Return Slip No." -->
<!-- 								style="border: 0; border-bottom: 2px ridge;" name="returnSlipNo" /> -->
<!-- 						</div> -->
<!-- 						<div class="col-sm-1"> -->
<!-- 							<button type="submit" -->
<!-- 								class="btn btn-info glyphicon glyphicon-search"></button> -->
<!-- 						</div> -->
<!-- 					</form> -->
<!-- 				</div> -->

				<table id="returnSlipListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Receive No</td>
							<td style="">Receive Date</td>
							<td style="">Receive By</td>
							<td style="">Zone</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${wsReceivePreventiveMst}" var="receivePreventiveMst" varStatus="loop">
							<tr>
								<td>
								<form id="myForm${loop.index}" action="${pageContext.request.contextPath}/prev/prevReceiveShowPost.do" method="POST">
								<input type="hidden" name="id" value="${receivePreventiveMst.id}" />
								<input type="hidden" name="returnTo" value="${receivePreventiveMst.referenceNumber}" />								
								<%-- <a
									href="${pageContext.request.contextPath}/ls/returnSlip/pageShow.do?id=${returnSlipMst.id}&returnTo=${returnSlipMst.returnTo}"
									style="text-decoration: none;"><c:out
											value="${returnSlipMst.returnSlipNo}" /></a> --%>
								<a href="#" onclick="document.getElementById('myForm${loop.index}').submit();"> <c:out value="${receivePreventiveMst.noteNumber}" /></a>
									</form>		
											</td>

								<td><fmt:formatDate value="${receivePreventiveMst.receiveDate}"
										pattern="dd-MM-yyyy" /></td>
								
								<td>${receivePreventiveMst.senderDeptName}, LOCAL STORE</td>

								<!-- <td><c:out value="${returnSlipMst.workOrderNo}" /></td>
								<td><fmt:formatDate value="${returnSlipMst.workOrderDate}"
										pattern="dd-MM-yyyy" /></td>  -->
								<td><c:out value="${receivePreventiveMst.zone}" /></td>

								<td>
									<a href="#" onclick="document.getElementById('myForm${loop.index}').submit();"><i class='glyphicon glyphicon-eye-open'></i> </a>
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
	$(function() {
		$("#workOrderNo").autocomplete({
			source : function(request, response) {
				//alert(request.term);
				$.ajax({
					url : 'getwOrderNo.do',
					type : "POST",
					data : {
						wOrderNo : request.term
					},
					dataType : "json",
					success : function(data) {
						response($.map(data, function(v, i) {
							return {
								label : v.contractNo,
								value : v.contractNo
							};
						}));
					}

				});

			},
			/* select : function(event, ui) {
				 alert(ui.item.label);
				window.location.href = 'viewTransformerData.do?contractNo='
						+ ui.item.label/* +'&startDate='+startDate+'&endDate='+endDate ;
			}, */
			minLength : 1
		});
	});
});

	$(document)
			.ready(
					function() {
						$('#returnSlipListTable').DataTable({
							"order" : [ [ 2, "desc" ] ]
						});
						document
								.getElementById('returnSlipListTable_length').style.display = 'none';
						//document.getElementById('returnSlipListTable_filter').style.display = 'none';

					});

	function createNewDiv() {
		document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';

	}
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
