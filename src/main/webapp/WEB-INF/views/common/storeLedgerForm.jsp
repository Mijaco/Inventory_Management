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
			
			<h1 class="center blue"><em>New Store Ledger Entry Form</em></h1>
			
		</div>		
		
	</div>
	
	<div class="container">
			<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; 
			margin-top: 10px; margin-bottom: 10px;">
		
		<!-- --------------------- -->
		<form method="POST" action="${pageContext.request.contextPath}/settings/add/newStoreLedgerSave.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
				
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="ledgerName" class="col-sm-5 control-label"> Ledger
							Name </label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="ledgerName"
								placeholder="Ledger Name" style="border: 0; border-bottom: 2px ridge;"
								name="ledgerName"/>
						</div>
					</div>
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="ledgerCode" class="col-sm-5 control-label"> Ledger
							Code </label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="ledgerCode"
								placeholder="Ledger Code" style="border: 0; border-bottom: 2px ridge;"
								name="ledgerCode"/>
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="remarks" class="col-sm-5 col-md-5 control-label">Remarks
							 </label>
						<div class="col-sm-7 col-md-7">
							<textarea rows="4" cols="" id="remarks" class="form-control"
							maxlength="1000" name="remarks"></textarea>
						</div>
					</div>

				</div>

				<div class="col-md-12">
					<div class="form-group" style="margin-top: 2em; margin-right: 1em;">
						
						<button type="submit" class="col-xs-12 width-20 pull-right btn btn-sm 
						btn-success" value="add_more" name="action" >
							<i class="ace-icon fa fa-plus"></i> 
							<span class="bigger-50"> Add More </span>
						</button>						
						<button type="submit" class="col-xs-12 width-20 pull-right btn btn-sm 
						btn-success" value="add" name="action" style="margin-right: 10px;">
							<i class="ace-icon fa fa-save"></i> 
							<span class="bigger-50"> Add </span>
						</button>						
						<button type="reset" class="col-xs-12 width-20 pull-right btn btn-sm" 
						style="margin-right: 10px;">
							<i class="ace-icon fa fa-refresh"></i> 
							<span class="bigger-50"> Reset </span>
						</button>
						
					</div>

				</div>

			</div>
		</form>
		<!-- --------------------- -->		
	</div>	
	</div>	
</div>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>