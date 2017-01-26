<%@include file="../../common/wsHeader.jsp"%>
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
		<div class="o_form_buttons_edit" style="display: block;">

			<h1 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Transformer
				Register List</h1>
			<div class="col-sm-4 pull-right">
				<div class="form-group col-sm-9 col-sm-offset-2">
					<select onchange="getTransformerList(this.value)"
						class="form-control reqNo" id="reqNo" name="reqNo"
						style="border: 0; border-bottom: 2px ridge;">
						<option disabled selected>Requisition No</option>
						<c:if test="${!empty transformerRegisterList}">
							<c:forEach items="${transformerRegisterList}"
								var="transformerRegister">
								<option value="${transformerRegister.reqNo}">
									<c:out value="${transformerRegister.reqNo}" /></option>
							</c:forEach>
						</c:if>
					</select> <input type="hidden" name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" />
				</div>
			</div>
			<div class="col-md-12">
				<div class="alert alert-success hide">Data updated
					successfully.</div>
				<div class="alert alert-danger hide">Failed to update Data.</div>
			</div>

		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<%-- <c:if test="${empty transformerRegisterList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Congratulation!!! You have no pending task</i>
					</h2>
				</div>
			</c:if> --%>

			<div class="col-sm-12 center">
				<h6 class="red">
					<i>${msg}</i>
				</h6>
			</div>


			<c:if test="${!empty transformerRegList}">
				<table id="transformerRegisterListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<!-- <td style="width: 30px;">Serial No</td> -->
							<td style="">X-Former Serial No</td>
							<td style="">Manufacture Name</td>
							<td style="">Manufacture Year</td>
							<td style="">KVA Rating</td>
							<td style="">Phase</td>
							<td style="">Received Date</td>
							<td style="">Requisition No/Reference No</td>
							<td style="">Ticket No</td>
							<td style="">Test Date</td>
							<td style="">Job No.</td>
							<td style="">Return Date</td>
							<td style="">Return Slip No</td>
							<td style="">Ticket No / Gate Pass No</td>
							<td style="">Bill No</td>
							<td style="">Remarks</td>
							<td style="">Status</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${transformerRegList}" var="transformerReg"
							varStatus="status">
							<tr>
								<%-- <td style="width: 30px;"><c:out value="${status.index+1} ." /><input type="hidden" name="id" id="id" value="${transformerReg.id}" /></td> --%>
								<td style=""><input style="width: 120px" type="text"
									readonly="readonly"
									value="${transformerReg.transformerSerialNo}"
									name="transformerSerialNo"
									id="transformerSerialNo${status.index}" /><input type="hidden"
									name="id" id="id${status.index}" value="${transformerReg.id}" /></td>
								<td style="">
								<select class="form-control manufactureName" name="manufactureName"
																		id="manufacturedName${status.index}">
																		<c:if test="${!empty manufactureNames}">
																	<c:if test="${!empty transformerReg.manufacturedName}">
																		<option value="${transformerReg.manufacturedName}">${transformerReg.manufacturedName}</option>
																	</c:if>
																	<c:if test="${empty transformerReg.manufacturedName}">
																		<option value="">Select</option>
																	</c:if>
																		
																			<c:forEach items="${manufactureNames}" var="manufactureName">
																				<option value="${manufactureName.manufactureName}">
																					<c:out value="${manufactureName.manufactureName}" /></option>
																			</c:forEach>
																		</c:if>
																	</select>
								</td>
								<td style=""><input style="width: 90px" type="text"
									readonly="readonly" value="${transformerReg.manufacturedYear}"
									name="manufacturedYear" id="manufacturedYear${status.index}" /></td>
								<td style=""><input style="width: 90px" type="text"
									readonly="readonly" value="${transformerReg.kvaRating}"
									name="kvaRating" id="kvaRating${status.index}" /></td>
								<td style="">${transformerReg.transformerType}</td>
								<td style=""><input style="width: 90px" type="text"
									class="form-control datepicker-15" readonly="readonly"
									value="<fmt:formatDate value="${transformerReg.receivedDate}"
													pattern="dd-MM-yyyy" />" name="receivedDate"
									id="receivedDate${status.index}" /></td>
								<td style=""><c:out value="${transformerReg.reqNo}" /></td>
								<td style=""><c:out value="${transformerReg.ticketNo}" /></td>
								<td style=""><input style="width: 90px" type="text"
									class="form-control" readonly="readonly"
									value="${transformerReg.testDate}" name="testDate"
									id="testDate${status.index}" /></td>
								<td style=""><input style="width: 120px" type="text"
									readonly="readonly" value="${transformerReg.jobNo}"
									name="jobNo" id="jobNo${status.index}" /></td>
								<td style=""><input style="width: 90px"
									class="form-control" type="text"
									readonly="readonly" value="${transformerReg.returnDate}"
									name="returnDate" id="returnDate${status.index}" /></td>
								<td style=""><input style="width: 160px" type="text"
									readonly="readonly" value="${transformerReg.returnSlipNo}"
									name="returnSlipNo" id="returnSlipNo${status.index}" /></td>
								<td style=""><input style="width: 160px" type="text"
									readonly="readonly" value="${transformerReg.returnTicketNo}"
									name="returnTicketNo" id="returnTicketNo${status.index}" /></td>
								
								
								<td style=""><input type="text" readonly="readonly"
									value="${transformerReg.billNo}" name="billNo"
									id="billNo${status.index}" /></td>
								<td style=""><input type="text" readonly="readonly"
									value="${transformerReg.remarks}" name="remarks"
									id="remarks${status.index}" /></td>
								<td style=""><c:if
										test="${!empty transformerReg.returnSlipNo}">Completed</c:if></td>
								<td>
									<button type="button" id="editBtn${status.index}"
										onclick="enableEditMode(${status.index})"
										style="border-radius: 6px;"
										class="width-10 btn btn-sm btn-danger">
										<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">Editable</span>
									</button>
									<button type="button" id="updateBtn${status.index}"
										onclick="enableUpdateMode(${status.index})"
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
			<!-- --------------------- -->
		</div>

	</div>
</div>

<script>

	function createNewDiv() {
		document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';

	}
</script>
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
		$('#transformerRegisterListTable').DataTable({
			"columnDefs" : [ {
				"width" : "400px",
				"targets" : 1
			}, {
				"targets" : 5,
				"width" : "100px"
			}, ],

			"order" : [ [ 0, 'asc' ] ],
			 "bLengthChange": false
		});
	});

	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
	
	
	
	function enableEditMode(n){
		 $('#editBtn'+n).css("display", "none");
		 $("#transformerSerialNo"+n).removeAttr("readonly");
		 $("#manufacturedName"+n).removeAttr("readonly");
		 $("#manufacturedYear"+n).removeAttr("readonly");
		 $("#kvaRating"+n).removeAttr("readonly");
		 $("#receivedDate"+n).removeAttr("readonly");
		 $("#billNo"+n).removeAttr("readonly");
		 $("#remarks"+n).removeAttr("readonly");
		 $('#updateBtn'+n).css("display", "");
		
	}
	
	function enableUpdateMode(n){		 
		 $('#updateBtn'+n).css("display", "none");		
		 $("#transformerSerialNo"+n).attr("readonly","readonly");
		 $("#manufacturedName"+n).attr("readonly","readonly");
		 $("#manufacturedYear"+n).attr("readonly","readonly");
		 $("#kvaRating"+n).attr("readonly","readonly");
		 $("#receivedDate"+n).attr("readonly","readonly"); 
		 $("#testDate"+n).attr("readonly","readonly");
		 $("#jobNo"+n).attr("readonly","readonly");
		 $("#returnDate"+n).attr("readonly","readonly"); 
		 $("#returnSlipNo"+n).attr("readonly","readonly");
		 $("#returnTicketNo"+n).attr("readonly","readonly");
		 $("#billNo"+n).attr("readonly","readonly");
		 $("#remarks"+n).attr("readonly","readonly");
		 $('#editBtn'+n).css("display", "");
		 updateWsCnAllocation(n);
	}
	
	function updateWsCnAllocation(n){
		var id=$('#pk'+n).val();
		var contextPath= $('#contextPath').val();
		var path= contextPath + '/transformer/register/transformerRegisterUpdate.do';
		 
		var cData = {
				id : $("#id"+n).val(),
				transformerSerialNo : $("#transformerSerialNo"+n).val(),
				manufacturedName : $("#manufacturedName"+n).val(),
				manufacturedYear : $("#manufacturedYear"+n).val(),				
				kvaRating : $("#kvaRating"+n).val(),
				receivedDate : $("#receivedDate"+n).val(),				
				testDate : $("#testDate"+n).val(),
				jobNo : $("#jobNo"+n).val(),
				returnDate : $("#returnDate"+n).val(),
				returnSlipNo : $("#returnSlipNo"+n).val(),
				returnTicketNo : $("#returnTicketNo"+n).val(),
				billNo : $("#billNo"+n).val(),					
				remarks : $("#remarks"+n).val()
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
				$('.alert.alert-danger').removeClass('hide');
				$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500);
			},
			type : 'POST'
		});

	}
		
		
	
</script>
<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>
<script>
function getTransformerList(reqNoVal){
    //var encrypted = CryptoJS.AES.encrypt(reqNoVal, "Secret Passphrase");
 
    //alert("key=="+encrypted.key);        // 74eb593087a982e2a6f5dded54ecd96d1fd0f3d44a58728cdcd40c55227522223
   // alert("iv=="+encrypted.iv);         // 7781157e2629b094f0e3dd48c4d786115
   // alert("salt=="+encrypted.salt);       // 7a25f9132ec6a8b34
   // alert("ciphertext=="+encrypted.ciphertext); // 73e54154a15d1beeb509d9e12f1e462a0
 
    //alert(encrypted);            // U2FsdGVkX1+iX5Ey7GqLND5UFUoV0b7rUJ2eEvHkYqA=
    	
    	//var login = 'ABCD';
//var key = crypto.CryptoJS.enc.Hex.parse('0123456789012345');
//var ive  = crypto.CryptoJS.enc.Hex.parse('0123456789012345');

//var encrypted = crypto.CryptoJS.AES.encrypt(reqNoVal, key, {iv: ive});

//var encrypted = CryptoJS.AES.encrypt(reqNoVal, "Secret Passphrase");
//alert("encrypted==="+encrypted+"encrypted ciphertext==="+encrypted.ciphertext);
//var decrypted = CryptoJS.AES.decrypt(encrypted, "Secret Passphrase");
//var plainText = decrypted.toString(CryptoJS.enc.Utf8)
//alert("plainText==="+plainText);


    	//alert(encrypted);
  /*   window.location.href = 'getTransformerList.do?key='
		+ encrypted.key+'&iv='+encrypted.iv+'&salt='+encrypted.salt+'&ciphertext='+encrypted.ciphertext+'&encrypted='+encrypted; */
    window.location.href = 'getTransformerList.do?reqNo='+reqNoVal;	
}
</script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/workshop/aes.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/workshop/mode-ecb-min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/workshop/wsTransformerRegisterEntry.js"></script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
