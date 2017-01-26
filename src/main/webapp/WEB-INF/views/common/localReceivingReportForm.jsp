<%@include file="../common/lprrHeader.jsp"%>

<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<!--End of Header -->
<div class="container-fluid icon-box ashid"
	style="background-color: #858585;" id="department_div">
	<div class="col-xs-8 col-xs-offset-2"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<div class="table-responsive col-xs-12">
			<table class="table table-bordered table-hover">
				<tbody>
					<tr>
						<td class="col-xs-2 success text-right" style="font-weight: bold;">Department
							Name: <input type="hidden" id="loginUserDeptId"
							value="${department.id}" />
						</td>
						<td class="col-xs-8"><select class="form-control" name="id"
							id="departmentName">
								<option value="">Select Your Department</option>
								<c:forEach items="${departmentList}" var="dept">
									<option value="${dept.id}">${dept.deptName}</option>
								</c:forEach>
						</select> <strong class="text-danger text-center hide"
							id="invalid_dept_warning"
							style="font-weight: bold; font-size: 16px;">Invalid
								Department!!! Please select correct Department.</strong></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="col-xs-12" align="center">
			<button type="button" id="verifyDepartment"
				class="btn btn-success btn-md" style="border-radius: 6px;">
				<i class="fa fa-fw fa-lock"></i>Verify
			</button>
		</div>

	</div>
</div>
<div class="container-fluid icon-box hide"
	style="background-color: #858585;" id="lp_form_div">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/lrr/lrrList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>&nbsp;Local RR List
			</a>

		</div>
		<div class="col-md-8">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Local
				Receiving Report Form</h2>
			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${department.deptName}</h4>


		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST" id="localReceivingReportForm"
			action="${pageContext.request.contextPath}/lrr/localReceivingReportFormSave.do"
			enctype="multipart/form-data">
			<div class="oe_title">
				
				<input type="hidden" id="deptCode" name="deptCode" value="${department.id}">
				<input type="hidden" value="${pageContext.request.contextPath}"
					id="contextPath">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="contractNo"
							class="col-sm-4 col-md-4 control-label">Invoice/Chalan No:</label>
						<div class="col-sm-8 col-md-8">
							<!-- <input type="text" class="form-control" id="contractNo"
								style="border: 0; border-bottom: 2px ridge;" name="contractNo" required> -->
							<select name="abc" id="invoiceNo" style="width: 100%; border: 0; border-bottom: 2px ridge;">
								<option value="0" selected disabled>Select Contract No.</option>
								<c:if test="${!empty localPurchaseMst}">
									<c:forEach items="${localPurchaseMst}" var="localPurchaseMst">
										<option value="${localPurchaseMst.id}">${localPurchaseMst.localPurchaseNo}</option>
									</c:forEach>
								</c:if>
							</select>
							<input type="hidden" id="ivNo" name="invoiceNo">
							<h6 class="text-danger contractNo hide"><strong>This field is required</strong></h6>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="contractNo"
							class="col-sm-4 col-md-4 control-label">WO/Contract No:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="contractNo"
								style="border: 0; border-bottom: 2px ridge;" name="contractNo" required>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="supplierName"
							class="col-sm-4 col-md-4 control-label">Supplier Name:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control"
								id="supplierName" style="border: 0; border-bottom: 2px ridge;"
								name="supplierName">
						</div>
					</div>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="uploadDoc"
							class="col-sm-4 col-md-4 control-label">PLI Doc. :</label>
						<div class="col-sm-8 col-md-8">
							<input type="file" id="uploadDoc" accept="application/pdf"
								class='form-control'
								style="border: 0; border-bottom: 2px ridge;" name="uploadDoc" />
								<strong class="text-danger uploadDoc hide">Invalid PLI Doc</strong>
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="contractDate"
							class="col-sm-4 col-md-4 control-label">Contract Date:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-15"
								id="contractDate" style="border: 0; border-bottom: 2px ridge;"
								name="contractDate">
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="invoiceDate"
							class="col-sm-4 col-md-4 control-label">Invoice/Chalan Date:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" id="invoiceDate" class='form-control datepicker-15'
								style="border: 0; border-bottom: 2px ridge;" name="invoiceDate" />
						</div>
					</div>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="deliveryDate"
							class="col-sm-4 col-md-4 control-label">Delivery Date:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" id="deliveryDate" class='form-control datepicker-15'
								style="border: 0; border-bottom: 2px ridge;" name="deliveryDate" />
						</div>
					</div>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="pliDate"
							class="col-sm-4 col-md-4 control-label">PLI Date:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" id="pliDate" class='form-control datepicker-15'
								style="border: 0; border-bottom: 2px ridge;" name="pliDate" />
						</div>
					</div>

				</div>
				
		
				<div class="col-md-12 col-xs-12 col-sm-12 table-responsive hide" id="dtlBLock">
					<p style="margin-top: 20px !important; margin-bottom: 10px !important"
						class="col-sm-12 btn btn-primary btn-sm">Receiving Items</p>
					<table class="table table-bordered" id="localPurchaseTbl">
						<thead>
							<tr style="background: #428bca; color: white">
								<th>Item Code</th>
								<th>Item Name</th>
								<th>Unit</th>
								<th>Expected Qty</th>
								<th>Receiving Qty</th>
								<th>Ledger Book</th>
								<th>Page No</th>
								<th>Remarks</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>

				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="button" id="saveButton"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-md btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="reset"
							class="width-20  pull-left btn btn-md btn-danger"
							style="margin-left: 10px; border-radius: 6px;">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button>
					</div>
				</div>
			</div>

		</form>
		<!-- --------------------- -->
	</div>
</div>

<script>
	
	function compareStock( id ) {
		var purchasedQty = $('#purchasedQty'+id).val();
		var requiredQty = $('#requiredQty'+id).val();
		
		if( parseFloat( requiredQty ) > parseFloat( purchasedQty ) ) {
			$('#requiredQty'+id).val( $('#purchasedQty'+id).val() );
		}
	}

	$( document ).ready( function() {
		
		$('#invoiceNo').change( function() {
			
			$('#localPurchaseTbl').find('tbody > tr').remove();

			$('#ivNo').val( $( 'option:selected', '#invoiceNo' ).text() );
			
			var contractNo = $('option:selected','#invoiceNo').text();
			var baseURL = $('#contextPath').val();
			
			var params = {
				localPurchaseNo : contractNo
			}

			var cDataJsonString = JSON.stringify(params);
			
			$.ajax({
				url : baseURL + "/lrr/localRRInfoByContractNo.do",
				data : cDataJsonString,
				contentType : "application/json",
				success : function(data) {
					var gap = jQuery.parseJSON( data );
					//alert( gap[0].supplierName );
					
					var dd = gap.supplyDate.split('-');
					var slyDate = dd[2] + '-' + dd[1] + '-' + dd[0];
					
					var cc = gap.purchaseDate.split('-');
					var purDate = cc[2] + '-' + cc[1] + '-' + cc[0];
					
					
					$('#deliveryDate').val( slyDate );
					$('#contractDate').val( purDate );
					$('#supplierName').val( gap.supplierName );
					$('#contractNo').val( gap.purchaseOrderNo );
					/* alert( gap.localPurchaseDtl[0].totalCost ); */
					var sequence = 0;
					$('#dtlBLock').removeClass('hide');
					for( var i in gap.localPurchaseDtl ) {
						var optn =
						"<tr>" +
							"<td>"+ gap.localPurchaseDtl[i].itemCode +
								"<input type='hidden' name='itemCode' id='itemCode"+sequence+"' value='"+ gap.localPurchaseDtl[i].itemCode +"'>"+
							"</td>" +
							
							"<td>"+ gap.localPurchaseDtl[i].itemName +
								"<input type='hidden' name='description' id='description"+sequence+"' value='"+ gap.localPurchaseDtl[i].itemName +"'>"+
							"</td>" +
							
							"<td>"+ gap.localPurchaseDtl[i].uom +
								"<input type='hidden' name='uom' id='uom"+sequence+"' value='"+ gap.localPurchaseDtl[i].uom +"'>"+
							"</td>" +
							
							"<td>"+ gap.localPurchaseDtl[i].receivedQty +
								"<input type='hidden' name='purchasedQty' id='purchasedQty"+sequence+"' value='"+ gap.localPurchaseDtl[i].receivedQty +"'>"+
							"</td>" +
							
							"<td>"+
								"<input type='number' class='requiredQty' onblur='compareStock("+sequence+")' step='0.001' id='requiredQty"+sequence+"' name='requiredQty' style='width: 100%;'>"+
								"<h6 class='text-danger hide errorQt' id='errorQty"+sequence+"'> <strong>Required Qty. Can't 0</strong> </h6>"+
							"</td>" +
							
							"<td>" +
								"<input type='text' id='ledgerBook"+sequence+"' name='ledgerBook' style='width: 100%;'>"+	
							"</td>" +
							
							"<td>" +
								"<input type='text' id='pageNo"+sequence+"' name='pageNo' style='width: 100%;'>"+	
							"</td>" +
							
							"<td>" +
								"<input type='text' id='remarks"+sequence+"' name='remarks' style='width: 100%;'>"+	
							"</td>" +
						"</tr>";
						
						sequence++;
					}
					$('#localPurchaseTbl').find('tbody').append(optn);
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
			
		}); //contractNo on change
		
		$('#saveButton').click( function() {
			var haserror = false, counter = 0;
			if( $('#uploadDoc').val() == null || $.trim( $('#uploadDoc').val() ) == '' ) {
				$('.uploadDoc').removeClass('hide');
				haserror = true;
			} else {
				$('.uploadDoc').addClass('hide');
			}
			
			if( $('#contractNo').val() == null || $.trim( $('#contractNo').val() ) == '0' ) {
				$('.contractNo').removeClass('hide');
				haserror = true;
			} else {
				$('.contractNo').addClass('hide');
			}
			
			$('.requiredQty').each( function() {
				if( $(this).val() == null || $.trim( $(this).val() ) == '' || $.trim( $(this).val() ) == '0' || $.trim( $(this).val() ) == '0.0' || $.trim( $(this).val() ) == '0.00' || $.trim( $(this).val() ) == '0.000' ) {
					
					var id = this.id;
					var name = this.name;
					var seq = id.substr( name.length );
					$('#errorQty'+seq).removeClass('hide');
					counter++;
				} else {
					
					var id = this.id;
					var name = this.name;
					var seq = id.substr( name.length );
					$('#errorQty'+seq).addClass('hide');
				}
			}); // jQuery .each
			
			if( counter > 0 ) {
				haserror = true;
			}
			
			if( haserror == true ) {
				return;
			} else {
				$('#localReceivingReportForm').submit();
			}
		}); //saveButton Click
	});
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/common/localReceivingReportForm.js"></script>

<!-- Start of Footer  -->
<%@include file="../common/ibcsFooter.jsp"%>