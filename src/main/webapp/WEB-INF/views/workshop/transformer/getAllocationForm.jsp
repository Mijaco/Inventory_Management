<%@include file="../../common/wsHeader.jsp"%>
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
			Contractor wise XFormer Allocation Table</h2>
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
			<c:if test="${empty contractorList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty contractorList}">
				<table id="inventoryList" style=""
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td rowspan="2">Work Order No</td>
							<td rowspan="2">Contractor Name</td>
							<td colspan="2">Total Maintenance Qty</td>
							<td colspan="2">Preventive Maintenance Qty</td>
							<td rowspan="2">Remarks</td>
							<td rowspan="2">Action</td>

						</tr>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>1 Phase</td>
							<td>3 Phase</td>
							<td>1 Phase</td>
							<td>3 Phase</td>

						</tr>
					</thead>

					<tbody>
						<c:forEach items="${contractorList}" var="contractor"
							varStatus="loop">
							<tr>
								<td>${contractor.contractNo}</td>
								<td>${contractor.contractorName}</td>
								<td><input type="number" min="0" readonly="readonly"
									id="repairQty1P${loop.index}" style="width: 70px"
									name="repairQty1P" value="0" /></td>
								<td><input type="number" min="0" readonly="readonly"
									id="repairQty3P${loop.index}" style="width: 70px"
									name="repairQty3P" value="0" /></td>
								<td><input type="number" min="0" readonly="readonly"
									id="preventiveQty1P${loop.index}" style="width: 70px"
									name="preventiveQty1P" value="0" /></td>
								<td><input type="number" min="0" readonly="readonly"
									id="preventiveQty3P${loop.index}" style="width: 70px"
									name="preventiveQty3P" value="0" /></td>
								<td><input type="text" id="remarks${loop.index}" readonly="readonly"
									name="remarks" placeholder="Write your remarks" /></td>
								<td>
								<%-- <a class="blue" onclick="enableEditMode(${loop.index})"
									href="javascript:void(0)" title="Enable Edit Mode"> <i
										class="fa fa-2x fa-edit"></i>
								</a> <a class="blue" onclick="updateData(${loop.index})"
									href="javascript:void(0)" title="Update Data"> <i
										class="fa fa-2x fa-save"></i>
								</a> --%>
								<button type="button" id="editBtn${loop.index}"
										onclick="enableEditMode(${loop.index})"
										style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-danger">
										<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">Editable</span>
								</button>
								
								<button type="button"  id="updateBtn${loop.index}"
										onclick="enableUpdateMode(${loop.index})"
										style="border-radius: 6px; display: none;"
										class="width-10  btn btn-sm btn-success">
										<i class="ace-icon fa fa-save"></i> <span class="bigger-30">Update</span>
								</button>
								</td>


							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		<!-- --------------------- -->

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
	
	
	
	function enableEditMode(n){
		 $('#editBtn'+n).css("display", "none");
		 $("#repairQty1P"+n).removeAttr("readonly");
		 $("#repairQty3P"+n).removeAttr("readonly");
		 $("#preventiveQty1P"+n).removeAttr("readonly");
		 $("#preventiveQty3P"+n).removeAttr("readonly");
		 $("#remarks"+n).removeAttr("readonly");
		 $('#updateBtn'+n).css("display", "");
		
	}
	
	function enableUpdateMode(n){		 
		 $('#updateBtn'+n).css("display", "none");		 
		 $("#repairQty1P"+n).attr("readonly","readonly");
		 $("#repairQty3P"+n).attr("readonly","readonly");
		 $("#preventiveQty1P"+n).attr("readonly","readonly");
		 $("#preventiveQty3P"+n).attr("readonly","readonly");
		 $("#remarks"+n).attr("readonly","readonly");
		 $('#editBtn'+n).css("display", "");
	}
	
	function updatePhysicalInventory2(n){
		var id=$('#pk'+n).val();
		var contextPath= $('#contextPath').val();
		// var path= contextPath + '/inventory/updateAnPhysicalInventoryItem.do';
		
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
		
		
	
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
