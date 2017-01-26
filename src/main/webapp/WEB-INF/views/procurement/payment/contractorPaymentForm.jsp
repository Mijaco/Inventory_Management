<%@include file="../../common/procurementHeader.jsp"%>
<!-- ----------------------------------------- -->
<!-- Author: Ihteshamul Alam, IBCS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/js/jquery-ui-autocomplete/jquery-ui.min.css">

<style>
textarea {
	resize: none;
}

.ui-autocomplete-input {
	padding-left: -10px !important;
}

.ui-menu-item:hover {
	padding-left: -10px !important;
}

</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="col-md-12">
			<a href="${pageContext.request.contextPath}/cms/contractArchive.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Contractor Payment List
			</a>
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Contractor Payment
			</h1>
		</div>
	</div>

	<div class="col-xs-12"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<form
			action="${pageContext.request.contextPath}/contractorPaymentSave.do"
			method="POST" enctype="multipart/form-data">
			<input type="hidden" id="contextPath"
				value="${pageContext.request.contextPath}">
			<div class="table-responsive">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="col-xs-3 success">Contract No.</td>
							<td class="col-xs-3">
								<input type="text" id="contractNo" name="contractNo" style="width: 100%" required="required">
								<h5 class="text-danger" id="empty-message"></h5>
							</td>
							<td class="col-xs-3 success">Contractor Name</td>
							<td class="col-xs-3">
								<input type="text" id="contractorname" name="contractorname" style="width: 100%" readonly required="required">
							</td>
						</tr>
						
						<tr>
							<td class="col-xs-3 success">Contract Date</td>
							<td class="col-xs-3">
								<input type="text" id="contractdate" name="contractdate" style="width: 100%" readonly required="required">
							</td>
							<td class="col-xs-3 success">Contract Amount</td>
							<td class="col-xs-3">
								<input type="text" id="contractamount" name="contractamount" style="width: 100%" readonly required="required">
							</td>
						</tr>
						
						<tr>
							<td class="col-xs-3 success">Desco Invoice No.</td>
							<td class="col-xs-3">
								<input type="text" id="descoInvNo" name="descoInvNo" style="width: 100%">
							</td>
							<td class="col-xs-3 success">Approved Invoice Document</td>
							<td class="col-xs-3">
								<input type="file" accept="application/pdf" id="contractorAppInvDoc" name="refDoc" style="width: 100%;">
								<!-- <button type="button" style="border-radius: 6px;" class="btn btn-primary btn-xs" onclick="document.getElementById('contractorAppInvDoc').click();"><i class="fa fa-fw fa-file-o"></i>&nbsp; Choose File</button> -->
							</td>
						</tr>
						
						<tr>
							<td class="col-xs-3 success">Payment Date</td>
							<td class="col-xs-3">
								<input type="text" id="paymentDate" name="paymentDate" class="datepicker-15" style="width: 100%" required="required">
							</td>
							<td class="col-xs-3 success">Payment Amount</td>
							<td class="col-xs-3">
								<input type="number" step="0.01" id="paymentAmount" name="paymentAmount" style="width: 100%" required="required">
							</td>
						</tr>
						
						<tr>
							<td class="col-xs-3 success">Payment By</td>
							<td class="col-xs-3">
								<input type="text" id="paymentBy" name="paymentBy" style="width: 100%" required="required">
							</td>
							<td class="col-xs-3 success">Cheque No.</td>
							<td class="col-xs-3">
								<input type="text" id="checkNo" name="checkNo" style="width: 100%">
							</td>
						</tr>
						
						<tr>
							<td class="col-xs-3 success">Bank Name</td>
							<td class="col-xs-3">
								<input type="text" id="bankName" name="bankName" style="width: 100%" required="required">
							</td>
							<td class="col-xs-3 success">Branch</td>
							<td class="col-xs-3">
								<input type="text" id="branchName" name="branchName" style="width: 100%" required="required">
							</td>
						</tr>
						
						<tr>
							<td class="col-xs-3 success">Cheque received by</td>
							<td class="col-xs-3">
								<input type="text" id="checkReceivedBy" name="checkReceivedBy" style="width: 100%" required="required">
							</td>
							<td class="col-xs-3 success">Cheque Document</td>
							<td class="col-xs-3">
								<input type="file" id="descoCheckDoc" name="refDoc" style="width: 100%;" accept="application/pdf">
								<!-- <button type="button" style="border-radius: 6px;" class="btn btn-primary btn-xs" onclick="document.getElementById('descoCheckDoc').click();"><i class="fa fa-fw fa-file-o"></i>&nbsp; Choose File</button> -->
							</td>
						</tr>
						
						<tr>
							<td class="col-xs-3 success">Remarks</td>
							<td class="col-xs-3" colspan="3">
								<textarea style="width: 100%" id="remarks" name="remarks" required="required"></textarea>
							</td>
							<!-- <td class="col-xs-3 success"></td>
							<td class="col-xs-3">
								<input type="text" id="" name="" style="width: 100%">
							</td> -->
						</tr>
					</tbody>
				</table>
			</div>

			<div class="col-sm-12 center" style="margin: 5px 0 15px 0;">
				<button type="submit" class="btn btn-success btn-md" id='saveButton'
					style="border-radius: 6px;">
					<i class="fa fa-fw fa-save"></i>&nbsp;Submit
				</button>
			</div>
			
			<input type="hidden" id="loadcontractinfo">
			
		</form>
	</div>
</div>

<script>
	var contextPath = $('#contextPath').val();
	var data = [];
	$( document ).ready( function() {
		$( '#loadcontractinfo' ).load(contextPath +"/contractorPaymentLoadContractInfo.do", {}, function(d) {
			var gap = jQuery.parseJSON( d );
			for( var ii in gap ) {
				data.push( gap[ii].contractNo );
			}
		});
		
		$('#contractNo').autocomplete({
		      source: data,
		      response: function(event, ui) {
		            // ui.content is the array that's about to be sent to the response callback.
		            if (ui.content.length === 0) {
		                $("#empty-message").text("No results found");
		                $('#saveButton').attr('disabled', true);
		            } else {
		                $("#empty-message").empty();
		                $('#saveButton').attr('disabled', false);
		            }
		        }
	    }); //autocomplete
	    
	    $('#contractNo').blur( function() {
	    	var counter = 0;
	    	var contractNo = $('#contractNo').val();
	    	
	    	for( var ii = 0; ii < data.length; ii++ ) {
	    		if( data[ii] == contractNo ) {
	    			counter++;
	    			break;
	    		}
	    	}
	    	
	    	if( counter == 0 ) {
	    		
	    		$("#empty-message").text("No results found");
                $('#saveButton').attr('disabled', true);
                
	    	} else {
	    		
	    		$("#empty-message").empty();
                $('#saveButton').attr('disabled', false);
	    		
	    		$('#contractorname').val( '' );
				$('#contractdate').val( '' );
				$('#contractamount').val( '' );
		    	
		    	
		    	var contractNo = $('#contractNo').val();
		    	
		    	var Data = {
		    		"contractNo":contractNo
		    	}
		    	
		    	var jsonData = JSON.stringify( Data );
		    	
		    	$.ajax({
		    		url : contextPath + "/contractorPaymentDetails.do",
		    		data : jsonData,
		    		contentType : "application/json",
		    		success : function(data) {
		    			
		    			if( data == "failed" ) {
		    				alert(data);
		    			} else {
		    				var gap = jQuery.parseJSON( data );
		    				$('#contractorname').val( gap.contractorName );
		    				$('#contractdate').val( gap.contractDate );
		    				$('#contractamount').val( gap.appPurchaseMst.contractAmount );
		    			}
		    		},
		    		error : function(data) {
		    			alert("Server Error");
		    		},
		    		type : 'POST'
		    	});
	    	}
	    });
	});
</script>

<script src="${pageContext.request.contextPath}/resources/assets/js/jquery-ui-autocomplete/jquery-ui.min.js"></script>

<!-- ----------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>