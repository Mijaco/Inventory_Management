<%@include file="../common/wsContractorHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->
<style>
.bold {
	font-weight: bold;
}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/inventory/wsInventoryReportList.do" style="text-decoration: none;"
				class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>Inventory Report List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Inventory Report</h1>
	</div>

	<div class="col-sm-offset-2 col-sm-8"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">

			<form method="post" id="saveInventoryReport"
				action="${pageContext.request.contextPath}/inventory/inventoryReportSave.do">
				<!-- Block :: start -->
				<div class="oe_title">
				<table class="col-sm-12 table">
				
				<tr class="info">
				<td class="col-xs-2 info">Type of Work :</td>
								<td class="col-xs-2 success" align="left" colspan="4"><input
									type="radio" id="typeOfWork" name="typeOfWork" value="Repair Works" checked="checked"> <b>Repair Works</b>
									<input type="radio" id="typeOfWork" name="typeOfWork" value="Preventive Maintenance"> <b>Preventive Maintenance</b>
									</td>
							</tr>
				<tr class="">
				<td class="col-xs-2 success">Transformer Serial No :</td>
								<td class="col-xs-4 info">
								<%-- <select class="form-control transformerSerialNo" id="transformerSerialNo" name="transformerSerialNo">
																		<option value="" disabled selected>Select</option>
																		<c:if test="${!empty transformerRegisterList}">
																			 <c:forEach items="${transformerRegisterList}" var="transformerRegister">
																				<option value="${transformerRegister.transformerSerialNo}">
																					<c:out value="${transformerRegister.transformerSerialNo}" /></option>
																			</c:forEach> 
																		</c:if>
																	</select> --%>
						<input type="text" class="form-control" id="transformerSerialNo"
								style="border: 0; border-bottom: 2px ridge;"
								name="transformerSerialNo" />
						<h5 class="text-danger hide transformerSerialNo"><strong>This field is required</strong></h5>
</td>
								<td class="col-xs-2 success">Received From:</td>
								<td class="col-xs-4 info">
								<input type="text" class="form-control" id="receivedFrom"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="receivedFrom" readonly="readonly"/>
								
</td>
				</tr>
				<tr>
					<td class="success">Rating (KVA):</td>
					<td class="info"><input type="text" class="form-control" id="kvaRating"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="kvaRating" readonly="readonly" /></td>
					<td class="success">Received Date:</td>
					<td class="info">
					<input type="text" id="receivedDate" name="receivedDate" class="form-control" value="" readonly="readonly" />
			 
					</td>
					</tr>
<tr>
					<td class="success">Manufacture By:</td>
					<td class="info"><input type="text" class="form-control" id="manufacturedName"
								value="" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;"
								name="manufacturedName" /></td>
					<td class="success">Last Repair Date(if any)</td>
					<td class="info">
						<input type="text" id="lastRepairDate" name="lastRepairDate" class="form-control datepicker-13" value="" />
					</td>
					</tr>
<tr>
					<td class="success">Manufacture Year:</td>
					<td class="info"><input type="text" class="form-control" id="manufacturedYear"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="manufacturedYear" readonly="readonly" /></td>
					<td class="success"></td>
					<td class="info">
					</td>
					</tr>
			</table>
			<!-- megger result -->
			<b>Megger Result</b>
							<table class="col-sm-12 table">
				
			
				<tr class="">
				<td class="col-xs-2 success">HT/LT :</td>
				<td class="col-xs-4 info" colspan="4">
								<input type="text" class="form-control" id="meggerResultHtLt"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="meggerResultHtLt" />
								
				</td>
				</tr>
				<tr class="">
				<td class="col-xs-2 success">LT/E :</td>
				<td class="col-xs-4 info" colspan="4">
								<input type="text" class="form-control" id="meggerResultLtE"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="meggerResultLtE" />
								
				</td>
				</tr>
				<tr class="">
				<td class="col-xs-2 success">HT/E :</td>
				<td class="col-xs-4 info" colspan="4">
								<input type="text" class="form-control" id="meggerResultHtE"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="meggerResultHtE" />
								
				</td>
				</tr>
				<tr class=""><td class="col-xs-2 success">&nbsp;</td><td class="col-xs-2 success" align="center">AB</td><td class="col-xs-2 success" align="center">BC</td><td class="col-xs-2 success" align="center">CA</td></tr>
				<tr class="">
				<td class="col-xs-2 success">LT/LT :</td>
				<td class="col-xs-2 info">
								<input type="text" class="form-control" id="meggerResultLtLtAb"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="meggerResultLtLtAb" />
								
				</td>
				<td class="col-xs-2 info">
								<input type="text" class="form-control" id="meggerResultLtLtBc"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="meggerResultLtLtBc" />
								
				</td>
				
				<td class="col-xs-2 info">
								<input type="text" class="form-control" id="meggerResultLtLtCa"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="meggerResultLtLtCa" />
								
				</td>
				</tr>
				<tr class="">
				<td class="col-xs-2 success">HT/HT :</td>
				<td class="col-xs-2 info">
								<input type="text" class="form-control" id="meggerResultHtHtAb"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="meggerResultHtHtAb" />
								
				</td>
				<td class="col-xs-2 info">
								<input type="text" class="form-control" id="meggerResultHtHtBc"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="meggerResultHtHtBc" />
								
				</td>
				
				<td class="col-xs-2 info">
								<input type="text" class="form-control" id="meggerResultHtHtCa"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="meggerResultHtHtCa" />
								
				</td>
				</tr>
						</table>
			<!-- megger result -->
			<table class="col-sm-12 table">
				<tr class="">
				<th class="col-xs-4 success">Item Description</th>
				<th class="col-xs-1 success">Unit</th>
				<th class="col-xs-2 success">Standard Quantity</th>
				<th class="col-xs-2 success">Useable</th>
				<th class="col-xs-2 success">Un-Useable</th>
				<th class="col-xs-2 success">Not Found</th>
				<th class="col-xs-2 success">Total Found</th>
				<th class="col-xs-2 success">Remarks(If any)</th>
				</tr>
				<c:if test="${!empty wsInventoryLookUpItemList}">
				<c:forEach items="${wsInventoryLookUpItemList}"
					var="wsInventoryLookUpItem" varStatus="loop">
			
				<tr class="">
				<td class="col-xs-2 success"><c:out value="${wsInventoryLookUpItem.itemName}"></c:out><input type="hidden" id="itemName${loop.index}"
								value="${wsInventoryLookUpItem.itemName}" name="itemName" />
				</td>
				<td class="col-xs-2 success"><c:out value="${wsInventoryLookUpItem.unit}"></c:out><input type="hidden" id="unit${loop.index}"
								value="${wsInventoryLookUpItem.unit}" name="unit" />
				</td>
				<td class="col-xs-2 success"><input type="number" id="standardQuantity${loop.index}"
								value="6" class="form-control"
								style="border: 0; border-bottom: 2px ridge;"
								name="standardQuantity" onblur="setFound(this)" />
				</td>
				<td class="col-xs-2 success"><input type="number" id="useableQuantity${loop.index}"
								value="4" class="form-control"
								style="border: 0; border-bottom: 2px ridge;"
								name="useableQuantity" onblur="setFound(this)" />
				</td>
				<td class="col-xs-2 success"><input type="number" id="unUseableQuantity${loop.index}"
								value="" class="form-control"
								style="border: 0; border-bottom: 2px ridge;"
								name="unUseableQuantity" onblur="setFound(this)" />
				</td>
				<td class="col-xs-2 success"><input type="number" id="notFound${loop.index}"
								value="" class="form-control"
								style="border: 0; border-bottom: 2px ridge;"
								name="notFound" />
				</td>
				<td class="col-xs-2 success"><input type="number" id="totalFound${loop.index}"
								value="" class="form-control"
								style="border: 0; border-bottom: 2px ridge;"
								name="totalFound" />
				</td>
				<td class="col-xs-3 success"><input type="text" id="remarks${loop.index}"
								value="" class="form-control"
								style="border: 0; border-bottom: 2px ridge;"
								name="remarks" />
				</td>
				</tr>
				</c:forEach>
			</c:if>
			<tr>
			<td>Painting of tank required</td>
			<td colspan="7">
			<input
									type="radio" id="isPaintingRequired" name="isPaintingRequired" value="YES"> <b>Yes</b>
									<input type="radio" id="isPaintingRequired" name="isPaintingRequired" value="NO"> <b>NO</b>
									
			</td>
			
			</tr>
				</table>
				
				<div class="col-md-12" align="center">
					<button type="button" id="saveButton"
						style="margin-right: 10px; border-radius: 6px;"
						class="width-20 pull-left btn btn-lg btn-success">
						<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
					</button>
					<button type="reset"
						class="width-20 pull-left btn btn-lg btn-danger"
						style="margin-left: 10px; border-radius: 6px;">
						<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
					</button>
				</div>
			</div>			
				<!-- Block :: end -->
			</form>
		</div>
	</div>

</div>



<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">

<script type="text/javascript">
	$(document).ready(function() {
		$(function() {
			$("#transformerSerialNo").autocomplete({
				source : function(request, response) {
					$.ajax({
						url : '${pageContext.request.contextPath}/getTransformerNo.do',
						type : "POST",
						data : {
							transformerSerialNo : request.term
						},
						dataType : "json",
						success : function(data) {
							response($.map(data, function(v, i) {
								return {
									label : v.transformerSerialNo,
									value : v.transformerSerialNo
								};
							}));
						}

					});

				},
				select : function(event, ui) {
					getTransformerData(ui.item.label);
				},
				minLength : 1
			});
		});
		
		//Added by: Ihteshamul Alam
		$('#saveButton').click( function() {
			var haserror = false;
			
			if( $('#transformerSerialNo').val() == null || $.trim( $('#transformerSerialNo').val() ) == "" ) {
				$('.transformerSerialNo').removeClass('hide');
				alert('Transformer serial no. is required');
				haserror = true;
			} else {
				$('.transformerSerialNo').addClass('hide');
			}
			
			if( haserror == true ) {
				return;
			} else {
				$('#saveInventoryReport').submit();
			}
		});
	});

	function getTransformerData(transformerNo) {
		//alert(itemName);
		$.ajax({
			type : "post",
			url : '${pageContext.request.contextPath}/getTransformerData.do',
			async : false,
			data : 'transformerNo=' + transformerNo,
			success : function(response) {
				var s = response.split(",");
				$("#receivedFrom").val(s[0]);
				$("#kvaRating").val(s[1]);
				$("#manufacturedName").val(s[2]);
				$("#manufacturedYear").val(s[3]);
				$("#receivedDate").val(s[4]);
			},
			error : function() {
			}
		});

		return true;
	}
	
	function setFound(element) {
		var id = $(element).attr('id');
		var name = $(element).attr('name');
		var sequence = id.substr(name.length);
		
		var standardQuantity = $('#standardQuantity' + sequence).val().trim();
		var standardQuantityFloat = parseFloat(standardQuantity).toFixed(2);
		
		var useableQuantity = $('#useableQuantity' + sequence).val().trim();
		var useableQuantityFloat = parseFloat(useableQuantity).toFixed(2);
		
		var unUseableQuantity = $('#unUseableQuantity' + sequence).val().trim();
		var unUseableQuantityFloat = parseFloat(unUseableQuantity).toFixed(2);
		
		var totalCost = +((+useableQuantityFloat) + (+unUseableQuantityFloat)).toFixed(2);
		$('#notFound' + sequence).val(standardQuantityFloat-totalCost);
		$('#totalFound' + sequence).val(totalCost);
	}

	/* function setTotalFound(element) {
		var id = $(element).attr('id');
		var name = $(element).attr('name');
		var sequence = id.substr(name.length);
		
		var useableQuantity = $('#useableQuantity' + sequence).val().trim();
		var useableQuantityFloat = parseFloat(useableQuantity).toFixed(2);
		
		var unUseableQuantity = $('#unUseableQuantity' + sequence).val().trim();
		var unUseableQuantityFloat = parseFloat(unUseableQuantity).toFixed(2);
		
		var totalCost = ((+useableQuantityFloat) + (+unUseableQuantityFloat)).toFixed(2);
		$('#totalFound' + sequence).val(totalCost);
	} */

</script>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>