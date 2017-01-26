<%@include file="../inventory/inventoryheader.jsp"%>
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
			Physical Store Inventory as on
			<fmt:formatDate value="${invDate}" pattern="yyyy-MM-dd" />
		</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />

		<!-- --------------------- -->
		<div class="alert alert-success hide">
			<strong>Congratulation!</strong> Information is updated successfully.
		</div>
		<div class="alert alert-danger hide">
			<strong>Sorry!</strong> Information update is failed!!!.
		</div>
		<!-- --------------------- -->
	</div>


	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty inventoryList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty inventoryList}">
				<table id="inventoryList" style="width: 1800px;"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>Item Code</td>
							<td>Item_Name/Description</td>
							<td>Unit</td>
							<c:if test="${!empty projectNameList}">
								<c:forEach items="${projectNameList}" var="projectName"
									varStatus="loop">
									<td>${projectName}</td>
								</c:forEach>
							</c:if>
							<!-- <td style="" width="">Project</td> -->
							<td>Total Qty</td>
							<td>Counted Qty</td>
							<td>Counted Date</td>
							<td>Shortage Qty</td>
							<td>Shortage Value</td>
							<td>Surplus Qty</td>
							<td>Surplus Value</td>
							<td>Percentage</td>
							<td>Remarks/Comments</td>
							<td>Action</td>

						</tr>
					</thead>

					<tbody>
						<c:forEach items="${inventoryList}" var="item" varStatus="loop">
							<tr>
								<td><c:out value="${item.itemCode}" /></td>
								<td><c:out value="${item.description}" /></td>
								<td><c:out value="${item.uom}" /></td>
								<c:forEach items="${item.projectQtyList}" var="projectQty"
									varStatus="pro">
									<td class="text-right" style="width: 100px"><fmt:formatNumber
											type="number" minFractionDigits="3" value="${projectQty.qty}" /></td>
								</c:forEach>
								<td id="totalQty${loop.index}"><fmt:formatNumber type="number" minFractionDigits="3"
										groupingUsed="false" value="${item.totalQty}" /></td>
								<td>
									<input
									type="number" step="0.001" min="0" id="countedQty${loop.index}"
									style="width: 70px" name="countedQty" onblur="adjustSurplusShortage(${loop.index})"
									value="${empty item.countedQty ? '0' : item.countedQty}" />
								</td>
								<td>
									<input
									type="text" class="form-control datepicker-13"
									name="countedDate" style="width: 90px"
									id="countedDate${loop.index}"
									value="<fmt:formatDate type='date' pattern='yyyy-MM-dd' value='${empty item.countedDate ? now :item.countedDate}' />" />

								</td>
								<td>
									<input type="number" step="0.001"
									min="0" id="shortageQty${loop.index}" style="width: 70px"
									name="shortageQty" value="${empty item.shortageQty ? '0' : item.shortageQty}" />
								</td>
								<td>
									<input type="number"
									step="0.001" min="0" id="shortageValue${loop.index}"
									style="width: 70px" name="shortageValue"
									value="${empty item.shortageValue ? '0' : item.shortageValue}" />
								</td>
								<td>
									<input type="number" step="0.001"
									min="0" id="surplusQty${loop.index}" style="width: 70px"
									name="surplusQty" value="${empty item.surplusQty ? '0' : item.surplusQty}" />
								</td>
								<td>
									<input type="number"
									step="0.001" min="0" id="surplusValue${loop.index}"
									style="width: 70px" name="surplusValue"
									value="${empty item.surplusValue ? '0' : item.surplusValue}" />
								</td>
								<td>
									<input type="number" step="0.001"
									min="0" id="percentage${loop.index}" style="width: 70px"
									name="percentage" value="${empty item.percentage ? '0' : item.percentage}" />
								</td>
								<td>
									<input type="text"
									id="remarks${loop.index}" style="" name="remarks"
									value="${item.remarks}" placeholder="Remarks" />
								</td>
								<td><input type="hidden" id="pk${loop.index}" name="pk"
									value="${item.id}" />
									<button type="button"
										onclick="updatePhysicalInventory(${loop.index})"
										style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-success">
										<i class="ace-icon fa fa-save"></i> <span class="bigger-30">Update</span>
									</button></td>


							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		<!-- --------------------- -->

	</div>
</div>

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
	
	function updatePhysicalInventory(n){
		var id=$('#pk'+n).val();
		var countedQty=$('#countedQty'+n).val();
		var countedDate=$('#countedDate'+n).val();
		var shortageQty=$('#shortageQty'+n).val();
		var shortageValue=$('#shortageValue'+n).val();
		var surplusQty=$('#surplusQty'+n).val();
		var surplusValue=$('#surplusValue'+n).val();
		var percentage=$('#percentage'+n).val();
		var remarks=$('#remarks'+n).val();
		var contextPath= $('#contextPath').val();
		var path= contextPath + '/inventory/updateAnPhysicalInventoryItem.do';
		
		var cData = {
				id : id,
				countedQty : countedQty,
				dateText : countedDate,
				shortageQty : shortageQty,
				shortageValue : shortageValue,
				surplusQty : surplusQty,
				surplusValue : surplusValue,
				percentage : percentage,
				remarks : remarks
			};
		var cDataJsonString = JSON.stringify(cData);
		$.ajax({			
			url : path,
			data : cDataJsonString,
			contentType : "application/json",
			success : function(data) {
				var result = JSON.parse(data);
				if(result=='success'){
					$('.alert.alert-success').removeClass('hide');
					$(".alert.alert-success").fadeTo(5000, 500).slideUp(500,
							function() {});
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

	}
	function adjustSurplusShortage(n){
		var totalQty=parseFloat($('#totalQty'+n).text().trim());
		var countedQty=parseFloat($('#countedQty'+n).val());
		
		var shortageQty=0.0;
		var surplusQty=0.0;
		var percentage=0.0;
		
		if(totalQty>countedQty){
			shortageQty=totalQty-countedQty;
			percentage=(shortageQty*100)/totalQty;
			$('#shortageQty'+n).val(parseFloat(shortageQty).toFixed(3));
			$('#percentage'+n).val(parseFloat(percentage).toFixed(2));
			//alert("shortageQty="+shortageQty+" : Percentage="+percentage);
		}
		if(totalQty<countedQty){
			surplusQty=countedQty-totalQty;
			percentage=(surplusQty*100)/totalQty;
			$('#surplusQty'+n).val(parseFloat(surplusQty).toFixed(3));
			$('#percentage'+n).val(parseFloat(percentage).toFixed(2));
			// alert("surplusQty="+surplusQty+" : Percentage="+percentage);
		}
		
		
	}
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
