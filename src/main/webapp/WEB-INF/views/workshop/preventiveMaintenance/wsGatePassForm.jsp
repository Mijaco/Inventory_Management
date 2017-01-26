<%@include file="../../common/wsHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->



<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Central Store Receive</a>
			/ New
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/prev/gatePassList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Gate
				Pass List
			</a>
			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button> -->
		</div>
		<h1 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Gate
			Pass Form</h1>
	</div>
<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">

			<div class="col-sm-6 pull-right">
				<div class="form-group col-sm-9 col-sm-offset-2">
					<select onchange="gatePassDtl(this.value)"
						class="form-control transformerSerialNo" id="transformerSerialNo" name="transformerSerialNo"
						style="border: 0; border-bottom: 2px ridge;">
						<option disabled selected>Transformer Serial No</option>
						<c:if test="${!empty transformerRegisterList}">
							<c:forEach items="${transformerRegisterList}"
								var="transformerRegister">
								<option value="${transformerRegister.transformerSerialNo}">
									<c:out value="${transformerRegister.transformerSerialNo}" /></option>
							</c:forEach>
						</c:if>
					</select> <input type="hidden" name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" />
				</div>
			</div>
		</div>
	</div>
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/prev/saveGatePass.do">
			<div class="oe_title">
		<c:if test="${!empty transformerRegister}">
				<div class="col-md-6 col-sm-6">

					

						<div class="form-group">
							<label for="reqNo" class="col-sm-4 control-label">Note Number</label>
							<div class="col-sm-8">
								${transformerRegister.reqNo}
							</div>
						</div>
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="rcvDeptName" class="col-sm-4 control-label">Issue To</label>
						<div class="col-sm-8">
							${transformerRegister.rcvDeptName}
						</div>
					</div>


				</div>

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="receivedDate" class="col-sm-4 control-label">Received Date
							</label>
						<div class="col-sm-8">
							${transformerRegister.receivedDate}
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="contractNo" class="col-sm-4 control-label">Contract No
							</label>
						<div class="col-sm-8">
						${transformerRegister.contractNo}
							<input type="hidden" value="${transformerRegister.transformerSerialNo}" name="transformerSerialNo" />
						</div>
					</div>
				

				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;"> Gate Pass
							Item(s):</label>

						<div class="col-md-12 col-sm-12 col-xs-12">
							<hr />
							<div class="col-xs-12">
								
									<table id="requisitionListTable"
										class="table table-striped table-hover table-bordered table-responsive"
										style="width: 100%">
										<thead>
											<tr
												style="background: #579EC8; color: white; font-weight: normal;">
												<td style="">Ttansformer Serial No</td>
												<td style="">Manufacture Name</td>
												<td style="">Item Code</td>
												<td style="">Description</td>
												<td style="">KVA Rating</td>
												<td style="">Quantity</td>
											</tr>
										</thead>

										<tbody>
											
												<tr>
												<td>${transformerRegister.transformerSerialNo}</td>
												<td>${transformerRegister.manufacturedName}</td>
												<td>${transformerRegister.itemCode}</td>
												<td>${item.itemName}</td>
												<td>${transformerRegister.kvaRating}</td>
												<td>1</td>
												</tr>
											
										</tbody>
									</table>
							
							</div>
							<hr />
						</div>
					</div>

				</div>

				<div class="col-md-12">
					<div class="form-group" style="margin-top: 2em;">
						
						<button type="submit" style="margin-left: 10px;"
							class="width-20 pull-left btn btn-sm btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>
				</div>
				</c:if>
			</div>
		</form>
		<!-- --------------------- -->
	</div>
</div>
<script>
	function gatePassDtl(transformerSerialNoVal){

    window.location.href = 'getGatePassDetail.do?transformerSerialNo='+transformerSerialNoVal;	
}
</script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>