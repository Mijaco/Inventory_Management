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
			
			<h1 class="center blue"><em> Update ${storeLedger.ledgerName} Store Ledger Information </em></h1>
			
		</div>		
		
	</div>
	
	<div class="container">
			<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; 
			margin-top: 10px; margin-bottom: 10px;">
		
		<!-- --------------------- -->
		<form method="POST" action="${pageContext.request.contextPath}/settings/update/existingStoreLedger.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
				
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="ledgerName" class="col-sm-5 col-md-5 control-label"> Store Ledger
							Name </label>
						<div class="col-sm-7 col-md-7">
							<input type="text" class="form-control" id="ledgerName" value="${storeLedger.ledgerName }"
								style="border: 0; border-bottom: 2px ridge;" name="ledgerName"/>
						</div>
					</div>
										
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="ledgerCode" class="col-sm-5 control-label"> Store Ledger
							Code </label>
						<div class="col-sm-7 left">						
							<input type="text" class="form-control" style="border: 0; border-bottom: 2px ridge;"
							value="${storeLedger.ledgerCode }" id="ledgerCode" name="ledgerCode">
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">
				
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="active" class="col-sm-5 control-label">Active
							Status </label>
						<div class="col-sm-7 left">						
							
							<c:if test="${storeLedger.active eq true}">
								<input type="checkbox" checked name="active" id="active" />
							</c:if>
							<c:if test="${storeLedger.active eq false}">
								<input type="checkbox" name="active" id="active" />
							</c:if>
							
						</div>
					</div>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
										
					<div class="form-group">
						<label for="remarks" class="col-sm-5 col-md-5 control-label">Remarks
							 </label>
						<div class="col-sm-7 col-md-7">
							<textarea rows="4" cols="" id="remarks" class="form-control"
							maxlength="1000" name="remarks">
							<c:out value="${storeLedger.remarks}"/>
							</textarea>
							<input type="hidden" id="id"
								value="${storeLedger.id }" name="id"/>
						</div>
					</div>

				</div>

				<div class="col-md-12">
					<div class="form-group" style="margin-top: 2em; margin-right: 1em;">
						
						<button type="submit" class="width-20 pull-right btn btn-sm 
						btn-success">
							<i class="ace-icon fa fa-save"></i> 
							<span class="bigger-50">Update</span>
						</button>
						
						<button type="reset" class="width-20 pull-right btn btn-sm" 
						style="margin-right: 10px;">
							<i class="ace-icon fa fa-refresh"></i> 
							<span class="bigger-50">Reset</span>
						</button>
						
					</div>

				</div>

			</div>
		</form>
		
	</div>	
	</div>	
</div>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>