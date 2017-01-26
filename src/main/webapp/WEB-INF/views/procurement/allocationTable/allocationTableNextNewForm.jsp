<%@include file="../../common/csHeader.jsp"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->
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
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Allocation
			Table For : ${allocationTableList[0].itemName}(${allocationTableList[0].itemCode})</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Session
			: ${descoSession.sessionName}</h4>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="alert alert-success hide">
			<!-- <a href="javascript:void(0)" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
			<strong>Success!</strong> Information is updated.
		</div>
		<div class="alert alert-danger hide">
			<!--<a href="#" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
			<strong>Sorry!</strong>  Information update is failed.
		</div>
		<!-- --------------------- -->
		<%-- <form
			action="${pageContext.request.contextPath}/allocation/requisitionLimitSave.do"
			method="POST" id="myForm"> --%>
		<div>
			<div class="hidden">
				<input type="hidden" name="descoSessionId"
					value="${descoSession.id}" /> <input type="hidden" name="itemCode"
					value="${itemMaster.itemId}" /> <input id="contextPath"
					type="hidden" value="${pageContext.request.contextPath}" />
			</div>

			<div style="background: white;">
				<c:if test="${empty allocationTableList}">
					<div class="col-sm-12 center">
						<p class="red">
							<i>Sorry!!! No Data Found in Database. </i>
						</p>
					</div>
				</c:if>
				<c:if test="${!empty allocationTableList}">
					<table id="allocationTable"
						class="table table-striped table-hover table-bordered">
						<thead>
							<tr
								style="background: #579EC8; color: white; font-weight: normal;">
								<!-- <td style="">Item Code</td>
								<td style="">Description</td> -->
								<td style="">S&amp;D Name</td>								
								<td style="">isUnlimited</td>
								<td style="">Requisition Limit</td>
								<td style="">Withdrawal Quantity</td>
								<td style="">Unit</td>
								<td style="">Action</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${allocationTableList}" var="allocationTable"
								varStatus="loop">
								<tr>
									
									<%-- <td><c:out value="${allocationTable.itemCode}" /> </td> 
									<td><c:out value="${allocationTable.itemName}" /></td> --%>		
									<td><c:out value="${allocationTable.sndName}" /> 												
									<input
										type="hidden" name="allocationId${loop.index}"
										value="${allocationTable.id}" />
										
									<input
										type="hidden" name="itemCode" id="itemCode${loop.index}"
										value="${allocationTable.itemCode}" />
										
									<input
										type="hidden" name="sndCode" id="sndCode${loop.index}"
										value="${allocationTable.sndCode}" />
										
									</td>
									<td class="center"><select name="unlimitedReq"
										id="unlimitedReq${loop.index}"
										class="form-control unlimitedReq">
											<c:choose>
												<c:when test="${allocationTable.unlimited==true}">
													<option value="1" selected="selected">Yes</option>
													<option value="0">No</option>
												</c:when>
												<c:otherwise>
													<option value="1">Yes</option>
													<option value="0" selected="selected">No</option>
												</c:otherwise>
											</c:choose>
									</select> 
									</td>
									<td><input type="number" step="0.01"
										name="requisitionLimitQty"
										id="requisitionLimitQty${loop.index}"
										value="${allocationTable.requisitionLimit}" /></td>
									<td class="center">${allocationTable.usedQuantity}</td>
									<td class="center">${allocationTable.uom}</td>
									<td class="center"><a href="#" class="btn btn-success btn-sm center" style="border-radius:4px;"
										onclick="singleUpdate(${allocationTable.id}, ${loop.index})">Update</a></td>
									</tr>
								</c:forEach>
							</tbody>
					</table>
				</c:if>
				<!-- <div class="col-md-12 center" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-12">
						<button type="submit"
							style="margin-right: 6px; border-radius: 6px;"
							class="width-10 pull-center btn btn-lg btn-success">Save</button>
					</div>
				</div> -->
			</div>
		</div>
		<!-- </form> -->
	</div>

</div>

<!-- ----------------- data table start ---------------- -->
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
						$('#allocationTable').DataTable();
						document.getElementById('allocationTable_length').style.display = 'none';
						//document.getElementById('allocationTable_filter').style.display = 'none';
					});

	$(window).load(function() {
		$('.loader').fadeOut('slow');
		//$('.container-fluid.icon-box').removeClass('hide');
	});
	
	function singleUpdate(id, index){
		var unlimitedReq = $('#unlimitedReq'+index).val();
		var requisitionLimitQty = $('#requisitionLimitQty'+index).val();
		//var itemCode = $('#itemCode'+index).val();
		//var sndCode = $('#sndCode'+index).val();
		var unlimited = true;
		if(unlimitedReq == 1){
			unlimited = true;
		}else{
			unlimited = false;
		}
		
		var cData = {
				id : id,
				unlimited : unlimited,
				requisitionLimit : requisitionLimitQty
			}
			$.ajax({
				url : $("#contextPath").val() + '/allocation/requisitionLimitUpdate.do',
				data : JSON.stringify(cData),
				contentType : "application/json",
				success : function(data) {
					var cData = JSON.parse(data);
					// alert(cData);	
					$('.alert.alert-success').removeClass('hide');
					$(".alert.alert-success").fadeTo(5000, 500).slideUp(500,
							function() {
								// $(".alert.alert-success").alert('close');
							});
				},
				error : function(data) {
					var cData = JSON.parse(data);
					// alert(cData);
				},
				type : 'POST'
			});
		
	}
</script>
<!-- --------------------------------------------------- -->
<%-- <script
	src="${pageContext.request.contextPath}/resources/assets/js/pndContractor/pdContractorRequisitionForm.js"></script> --%>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>