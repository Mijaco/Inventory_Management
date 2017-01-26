<%@include file="../common/settingsHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: offwhite;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/settings/list/storeLedger.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Store Ledger List
			</a>
			<button accesskey="D" class="btn btn-info btn-sm" type="button"> Discard </button>
			
			<h1 class="center blue"><em>${storeLedgerShow.ledgerName} Store Ledger Details</em></h1>
			
		</div>		
		
	</div>
	
	<div class="container">
			<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; 
			margin-top: 10px; margin-bottom: 10px;">
		
		<!-- --------------------- -->
		<form method="POST" action="${pageContext.request.contextPath}/settings/update/storeLedger.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
				
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label 	for="ledgerName" class="col-sm-5 control-label" 
								style="vertical-align: middle;" > Store Ledger 
								Name </label>						
						<div class="col-sm-7">
							<input type="text" class="form-control" id="ledgerName"
								value="${storeLedgerShow.ledgerName}" style="border: 0; border-bottom: 0px ridge;"
								name="ledgerName" readonly/>
						</div>
					</div>
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="createdBy" class="col-sm-5 control-label" style="vertical-align: middle;"> Created
							By </label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="createdBy"
								value="${storeLedgerShow.createdBy}" style="border: 0; border-bottom: 0px ridge;"
								name="createdBy" readonly/>							
						</div>
					</div>					
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="modifiedBy" class="col-sm-5 col-md-5 control-label" style="vertical-align: middle;"> Last Modified
							By </label>
						<div class="col-sm-7 col-md-7">
							<input type="text" class="form-control" id="modifiedBy" 
							value="${storeLedgerShow.modifiedBy}"	style="border: 0; 
							border-bottom: 0px ridge;" name="modifiedBy" readonly/>
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="ledgerCode" class="col-sm-5 col-md-5 control-label" style="vertical-align: middle;"> Store Ledger
							Code </label>
						<div class="col-sm-7 col-md-7">
							<input type="text" class="form-control" id="ledgerCode" 
							value="${storeLedgerShow.ledgerCode}"	style="border: 0; 
							border-bottom: 0px ridge;" name="ledgerCode" readonly/>
						</div>
					</div>
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="active" class="col-sm-5 control-label" style="vertical-align: middle;">Active
							Status </label>
						<div class="col-sm-7 left">
							<c:if test="${storeLedgerShow.active == true }">
								<input type="text" class="form-control" id="active" value="Active"
							 	style="border: 0; border-bottom: 0px ridge;"
								name="active" readonly>
							</c:if>
							<c:if test="${storeLedgerShow.active == false }">
								<input type="text" class="form-control" id="active" value="Active"
							 	style="border: 0; border-bottom: 0px ridge;"
								name="active" readonly>
							</c:if>
						</div>
					</div>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="remarks" class="col-sm-5 col-md-5 control-label" style="vertical-align: middle;"> Remarks
							 </label>
						<div class="col-sm-7 col-md-7">
							<textarea class="form-control" rows="4px" cols="auto" 
							style="border: 0px ridge;" readonly>
								<c:out value="${storeLedgerShow.remarks}"/>
							</textarea>
						</div>
					</div>

				</div>

				<!-- <div class="col-md-12">
					<div class="form-group" style="margin-top: 2em; margin-right: 1em;">						
						<button class="width-20 pull-right btn btn-sm 
						btn-success" >
							<i class="ace-icon fa fa-save"></i> 
							<span class="bigger-50">Update</span>
						</button>						
					</div>
				</div> -->
				
			</div>
		</form>
		<!-- --------------------- -->		
	</div>	
	</div>	
</div>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>