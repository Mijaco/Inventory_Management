<%@include file="../../common/wsContractorHeader.jsp"%>
<%@ page import="java.util.Date"%>
<jsp:useBean id="now" class="java.util.Date" />

<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
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
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Set Job Wise Item Quantity</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />

		<!-- --------------------- -->
		<div class="alert alert-success hide">
			<strong>Congratulation!</strong> Information is updated successfully.
		</div>
		<div class="alert alert-danger hide">
			<strong>Sorry!</strong> Information update is failed!!!.
		</div>
		<div class="alert alert-danger not_equal_qty hide">
			<strong>Failed!</strong> Job Wise Quantity Must be Equal with Total Issued Quantity!!!.
		</div>
		<!-- --------------------- -->
	</div>


	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<div>
			<input type="hidden" id="contextPath"
				value="${pageContext.request.contextPath}" />
			<input type="hidden" id="requisitionNo" value="${requisitionNo}"/>
		</div>
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty csReqDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty csReqDtlList}">
				<div class="table-responsive">
					<table id="inventoryList"
						class="table table-striped table-hover table-bordered">
						<thead>
							<tr
								style="background: #579EC8; color: white; font-weight: normal;">
								<td>Item Code</td>
								<td>Item Name</td>
								<td>Unit</td>
								<%-- <c:if test="${!empty tRegisterList}">
								<c:forEach items="${tRegisterList}" var="reg" varStatus="loop">
									<td>Remaining Qty&nbsp;&nbsp;(${reg.jobNo}) </td>
								</c:forEach>
							</c:if> --%>
								<c:if test="${!empty tRegisterList}">
									<c:forEach items="${tRegisterList}" var="reg" varStatus="loop">
										<td id="jobNo${loop.index}">${reg.jobNo}</td>
									</c:forEach>
								</c:if>
								<!-- <td style="" width="">Project</td> -->
								<td>Total Issued Qty</td>
								<td>Action</td>

							</tr>
						</thead>

						<tbody>
							<c:forEach items="${csReqDtlList}" var="item" varStatus="loop">
								<tr>
									<td><c:out value="${item.itemCode}" /> <input
										type="hidden" id="id${loop.index}" value="${item.id}" /></td>
									<td><c:out value="${item.itemName}" /></td>
									<td><c:out value="${item.uom}" /></td>
									<%-- <c:forEach items="${tRegisterList}" var="reg" varStatus="pro"> --%>
									<%-- <c:forEach items="${item.remainQty}" var="remQty"
									varStatus="pro">
									<td class="text-right" style="width: 100px"><c:out value="${remQty}" /></td>
								</c:forEach> --%>
									<c:forEach items="${item.remainQty}" var="remQty"
										varStatus="pro">

										<td class="text-right setQty" style="width: 100px"><input
											id="qty_${loop.index}${pro.index}" type="number" step="0.001"
											<c:if test="${remQty <= 0}"> readonly="readonly" </c:if>
											onblur="compareToRemainQty('${loop.index}${pro.index}',${remQty})"
											title="Your Remaining Quantity is : ${remQty}"
											placeholder="${remQty}" value="0.0" />
											<p class="red hidden"
												id="remWarning${loop.index}${pro.index}">Input qty can
												not be greater than remain qty</p> <c:set var="proCount"
												value="${pro.count}" scope="page" /></td>

									</c:forEach>
									<td id="quantityIssued${loop.index}"><fmt:formatNumber
											type="number" minFractionDigits="3" groupingUsed="false"
											value="${item.quantityIssued}" /></td>


									<td><input type="hidden" id="pk${loop.index}" name="pk"
										value="${item.id}" />
										<button type="button" onclick="updateJobCard(${loop.index})"
											id="updatebtn${loop.index}" style="border-radius: 6px;"
											class="width-10  btn btn-sm btn-success">
											<i class="ace-icon fa fa-save"></i> <span class="bigger-30">Update</span>
										</button></td>
								</tr>
								<c:set var="rowCount" value="${loop.count}" scope="page" />
							</c:forEach>
						</tbody>
					</table>
				</div>
				<input type="hidden" value="${proCount}" id="jobCount" />
				<input type="hidden" value="${rowCount}" id="rowCount" />
			</c:if>
		</div>
		<!-- <p style="padding-left: 13px; color: red;">N.B: After updating all items, you must need to click Receive button.</p>
		<div class="col-sm-12 center">
			<button type="button" onclick="receivedRequisitionFromWs()" disabled='disabled' id="receivedReq"
				style="border-radius: 6px;" class="width-10  btn btn-sm btn-success">
				<i class="ace-icon fa fa-download"></i> <span class="bigger-30">Receive</span>
			</button>
		</div>
			-->
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
		$('#inventoryList').DataTable({
			"columnDefs" : [ {
				"width" : "400px",
				"targets" : 1
			}, {
				"targets" : 5,
				"width" : "100px"
			}, ],

			"order" : [ [ 0, 'asc' ] ]
		});
		document.getElementById('inventoryList_length').style.display = 'none';
		// document.getElementById('avgPriceList_filter').style.display = 'none';
	});

	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
	
	
	function countHiddenButton(index) { //This will return how many buttons are hidden
		var counter = 0;
		for( var i = 0; i < index; i++ ) {
			if( $('#updatebtn'+i).hasClass('hide') ) {
				counter++;
			}
		}
		return counter;
	}
	
	
	function updateJobCard(index){
		//alert(index);
		var jobCount = $("#jobCount").val();
		var quantityIssued = parseFloat($("#quantityIssued"+index).text().trim());
		
		//
		var jobCardQtyMap = new Object(); // or var map = {};		

		var totalSetQty = 0.0;
		for(var i = 0; i < jobCount; i++){
			var jobNo = $("#jobNo"+i).text();
			var qty = $("#qty_"+index+i).val();
			totalSetQty += parseFloat(qty.trim());
			jobCardQtyMap[jobNo] = parseFloat(qty.trim()).toFixed(2);
		}
		//alert(quantityIssued +" == "+ totalSetQty);
		if(quantityIssued == totalSetQty){
		
		var rowCount = $('#rowCount').val();
			
		var contextPath= $('#contextPath').val();
		var path= contextPath + '/ws/xf/updateJobWiseItemIssue.do';
		
		var cData = {
				id : $("#id"+index).val(),
				jobCardQtyMap : jobCardQtyMap
			};
		var cDataJsonString = JSON.stringify(cData);
		
		//alert(cDataJsonString);
		$('#updatebtn' + index).attr('disabled', true);
		$.ajax({			
			url : path,
			data : cDataJsonString,
			contentType : "application/json",
			success : function(data) {
				var result = JSON.parse(data);
				if(result=='success'){
					//alert("successfully updated");
					$('#updatebtn'+index).addClass('hide');
					
					$('.alert.alert-success').removeClass('hide');
					$(".alert.alert-success").fadeTo(5000, 1000).slideUp(1000,
							function() {});
					
					var res = countHiddenButton( rowCount );
					if( res == rowCount ) {
						receivedRequisitionFromWs();
						//$('#receivedReq').removeAttr('disabled').removeClass('btn-success').addClass('btn-info');
					}
					
				} else {
					$('.alert.alert-danger').removeClass('hide');
					$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500,
							function() {});
				}
				
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
		
		}else{
			$('.alert.alert-danger.not_equal_qty').removeClass('hide');
			$(".alert.alert-danger.not_equal_qty").fadeTo(5000, 500).slideUp(500,
					function() {});
		}
	}
	
	function compareToRemainQty(index, remQty){
		var qty = parseFloat($("#qty_"+index).val().trim());
		var remQty = parseFloat(remQty);
		if(qty > remQty){
			$("#remWarning"+index).removeClass('hidden');
			$("#qty_"+index).val(0.0);
			$("#qty_"+index).focus();
			//alert($("#remWarning"+index).text());
		}else{
			$("#remWarning"+index).addClass('hidden');
		}		
	}
	
	function receivedRequisitionFromWs(){
		var rowCount = $("#rowCount").val();
		var jobCount = $("#jobCount").val();
		//alert(rowCount+" : "+jobCount);
		
		
		var contextPath= $('#contextPath').val();
		var path= contextPath + '/ws/xf/receivedRequisitionFromWs.do';
		
		var cData = {
				requisitionNo : $("#requisitionNo").val()
			};
		postSubmit(path, cData, 'POST');
	}
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
