<%@include file="../common/wsHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/closeoutList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Close out List
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Close out Form</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<form
			action="${pageContext.request.contextPath}/closeoutSave.do"
			method="POST" id="myForm">
			<div class="oe_title">
				<div class="col-xs-12">
					<table class="col-xs-12 table">
						<tr class="">
							<td class="col-xs-1 success">From Date</td>
					<td class="col-xs-2">
						<input type="text" id="fromDate" name="startDate" class="form-control datepicker-15" value="${startDate}" />
					</td>
					<td class="col-xs-1 success">To Date</td>
					<td class="col-xs-2">
						<input type="text" id="toDate" name="endDate" class="form-control datepicker-15" value="${endDate}" />
					</td>
					<td class="info">Work Order No:</td>
							<td class=""><input type="text" class="form-control" id="workOrderNo"
								placeholder="" value="${contractNo}" style="border: 0; border-bottom: 2px ridge;"
								name="woNumber"><input
								type="hidden" name="contextPath" id="contextPath"
								value="${pageContext.request.contextPath}">
							</td>
						</tr>

						
					</table>
				</div>
			</div>

			<div style="background: white;">
				<c:if test="${flag==1}">
					 <div class="col-sm-12 center">
						<p class="red">
							<i>Sorry!!! No Data Found in Database. </i>
						</p>
					</div> 
</c:if><c:if test="${!empty jobCardTemplateList}">
						<table id="testTable"
							class="table table-striped table-hover table-bordered">
							<thead>
								<tr
									style="background: #579EC8; color: white; font-weight: normal;">
									<!-- <td style="width:10px;">SL No.</td> -->
									<td style="width:150px;">Item Code</td>
									<td style="width:550px;">Name Of Materials</td>
									<td style="width:150px;">Unit</td>
									<!-- <td style="width:150px;">B.F.</td> -->
									<td style="width:150px;">Purchase by P.cash </td>
									<td style="width:150px;">This Period (Store)</td>
									<td style="width:150px;">Total Q-ty.</td>
									<td style="width:150px;">Materials Consumed as per Annex-A</td>
									<td style="width:150px;">Materials To be Return/Materials in Hand</td>
									<td style="width:150px;">Actualy Return</td>
									<td style="width:150px;">Quantity Short</td>
									<td style="width:150px;">Quantity Excess</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${jobCardTemplateList}" var="jobCardTemplate"
									varStatus="loop">
									<tr><%-- <c:set var="test" value="${loop.index+1}"></c:set> --%>
										<%-- <td>${loop.index+1}</td> --%>
										<td class="col-sm-1 col-xs-12">${jobCardTemplate.itemCode}<input type="hidden" value="${jobCardTemplate.itemCode}" name="itemCode" /></td>
										<td style="width:550px;">${jobCardTemplate.itemName}<input type="hidden" value="${jobCardTemplate.itemName}" name="itemName" /></td>
										<td class="col-sm-1 col-xs-12">${jobCardTemplate.unit}<input type="hidden" value="${jobCardTemplate.unit}" name="uom" /></td>
										<!-- <td class="col-sm-1 col-xs-12"><input style="width:70px;" type="text" value="2" name="balance" /></td> -->
										<td style="width:150px;"><input style="width:70px;" type="number" value="0" id="rcvPurCashQty${loop.index}" name="rcvPurCashQty" onblur="getTotalQ(this)" /></td>
										<td style="width:150px;"><input style="width:70px;" type="number" value="${jobCardTemplate.remainingQty>0?jobCardTemplate.remainingQty:'0.0'}" id="rcvFromStoreQty${loop.index}" name="rcvFromStoreQty" onblur="getTotalQ(this)" /></td>
										<td style="width:150px;"><input style="width:70px;" type="number" value="0" id="totalQty${loop.index}" name="totalQty" readonly="readonly" /></td>
										<td style="width:150px;"><input style="width:70px;" type="number" value="${jobCardTemplate.consumeQty>0?jobCardTemplate.consumeQty:'0.0'}" id="materialsConsume${loop.index}" name="materialsConsume" onblur="getConsume(this)" /></td>
										<td style="width:150px;"><input style="width:80px;" type="number" value="0" id="materialsReturn${loop.index}" name="materialsReturn" readonly="readonly" /></td>
										<td style="width:150px;"><input style="width:80px;" style="width:80px;" type="number" value="0" name="actualReturn" id="actualReturn${loop.index}" onblur="getCompare(this)" />
										<td style="width:150px;"><input style="width:80px;" type="number" value="0" id="qtyShort${loop.index}" name="qtyShort" /></td>
										<td style="width:150px;"><input style="width:70px;" type="number" value="0" id="qtyExcess${loop.index}" name="qtyExcess" /></td>
											
										
									</tr>
								</c:forEach>
							</tbody>
						</table>
				<div class="col-md-12 center" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-12" id="submit">
						<button type="submit"
							style="margin-right: 6px; border-radius: 6px;"
							class="width-10 pull-center btn btn-lg btn-success">Save</button>
					</div>
				</div>
				</c:if>
			</div>
				
		</form>
	</div>

</div>
<script>
$(document).ready(function() {
	$(function() {
		$("#workOrderNo").autocomplete({
			source : function(request, response) {
				//alert(request.term);
				if(document.getElementById('fromDate').value=="" || document.getElementById('fromDate').value==""){
					alert("date field sould not empty");
					document.getElementById('workOrderNo').value="";
					return false;
					}
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
			select : function(event, ui) {
				 //alert(ui.item.label);
				 
				window.location.href = 'viewTransformerData.do?contractNo='
						+ ui.item.label +'&startDate='+document.getElementById('fromDate').value+'&endDate='+document.getElementById('toDate').value;
				//('#fromDate').val();
			},
			minLength : 1
		});
	});
});

function getTotalQ(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);
	var rcvPurCashQty = $('#rcvPurCashQty' + sequence).val().trim();
	var rcvPurCashQtyFloat = parseFloat(rcvPurCashQty).toFixed(2);
	var rcvFromStoreQty = $('#rcvFromStoreQty' + sequence).val().trim();
	var rcvFromStoreQtyFloat = parseFloat(rcvFromStoreQty).toFixed(2);
	// alert(sequence+"--"+matQuantityFloat);
	var totalCost = +((+rcvPurCashQtyFloat) + (+rcvFromStoreQtyFloat)).toFixed(2);
	$('#totalQty' + sequence).val(totalCost);

}

function getConsume(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);
	
	var materialsConsume = $('#materialsConsume' + sequence).val().trim();
	var materialsConsumeFloat = parseFloat(materialsConsume).toFixed(2);
	
	var totalQty = $('#totalQty' + sequence).val().trim();
	var totalQtyFloat = parseFloat(totalQty).toFixed(2);
	
	// alert(sequence+"--"+matQuantityFloat);
	var minCost = (totalQtyFloat - materialsConsumeFloat).toFixed(2);
	$('#materialsReturn' + sequence).val(minCost);

}

function getCompare(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);
	
	var actualReturn = $('#actualReturn' + sequence).val().trim();
	var actualReturnFloat = parseFloat(actualReturn).toFixed(2);
	
	var materialsReturn = $('#materialsReturn' + sequence).val().trim();
	var materialsReturnFloat = parseFloat(materialsReturn).toFixed(2);
	
	// alert(sequence+"--"+matQuantityFloat);
	var min = (materialsReturnFloat - actualReturnFloat).toFixed(2);
	if(materialsReturnFloat > actualReturnFloat){
	$('#qtyShort' + sequence).val(min);
	}else{
	$('#qtyExcess' + sequence).val(min);
	}
}

$(document).ready(
		function() {

			$('#submit').click(function() {
				return validateForm();
				// return detailCheck();
			});
			
			//This will set end date in first row of Contractor Representative(s)
			

			function validateForm() {
				//var contractNo = $('#contractNo').val();
				var endDate = $('#toDate').val();

				var inputVal = new Array(toDate);

				var inputMessage = new Array("To Date");

				$('.error').hide();

				if (inputVal[0] == "") {
					$('#toDate').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[0] + '</span>');
					return false;
				}
				//var row = $('.clonedArea').length;
				//var rowCount = row+1;
				//for(var i=0;i<10;i++){
					//alert("in loop");
					//var quantity = ;	
					//$('#rcvPurCashQty'+i).val(/[^\d]/,'');
					
					
				//}

				return true;
			}

		});
		
function getItem(){	

	var hide=document.getElementById("hide").value;
	var index=hide-1;

	var categoryIdVal=document.getElementById('categoryId['+index+']').value;

	//alert(categoryIdVal);
$.ajax({								
	url : '${pageContext.request.contextPath}/getMeasurement.do',
	data : "{categoryId:"+categoryIdVal+"}",
	contentType : "application/json",
	success : function(d) {	
		var data = JSON.parse(d);				
		   var html = '<tr><th style="text-align:center;background-color:#4CAF50;color:white;">Measurement</th><th style="text-align:center;background-color:#4CAF50;color:white;">Value</th></tr>';
               var len = data.length;

            for ( var i = 0; i < len; i++) {
            	html+='<tr><td>'+ data[i].name+'</td>';
                html += '<td><input type="text" class="form-control" value="" /></td>';
            }

            $('#dd').html(html);
	},
	error : function(data) {
		alert("Server Error");
	},
	type : 'POST'
});
}


</script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>