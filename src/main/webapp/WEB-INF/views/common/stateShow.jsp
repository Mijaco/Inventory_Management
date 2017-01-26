<%@include file="../common/adminheader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/settings/list/state.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> State List
			</a>

			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				${stateShow.stateName} State Details</h1>
			
		</div>		
		
	</div>
	
	<div class="container-fluid">
			<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		
		<!-- --------------------- -->
		<form method="POST" action="${pageContext.request.contextPath}/settings/update/state.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
				
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label 	for="stateName" class="col-sm-5 control-label" 
								style="vertical-align: middle;" > State 
								Name </label>						
						<div class="col-sm-7">
							<input type="text" class="form-control" id="stateName"
								value="${stateShow.stateName}" style="border: 0; border-bottom: 0px ridge;"
								name="stateName" readonly/>
						</div>
					</div>
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="createdBy" class="col-sm-5 control-label" style="vertical-align: middle;"> Created
							By </label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="createdBy"
								value="${stateShow.createdBy}" style="border: 0; border-bottom: 0px ridge;"
								name="createdBy" readonly/>							
						</div>
					</div>					
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="modifiedBy" class="col-sm-5 col-md-5 control-label" style="vertical-align: middle;"> Last Modified
							By </label>
						<div class="col-sm-7 col-md-7">
							<input type="text" class="form-control" id="modifiedBy" 
							value="${stateShow.modifiedBy}"	style="border: 0; 
							border-bottom: 0px ridge;" name="modifiedBy" readonly/>
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="active" class="col-sm-5 control-label" style="vertical-align: middle;">Active
							Status </label>
						<div class="col-sm-7 left">
							<c:if test="${stateShow.active == true }">
								<input type="text" class="form-control" id="active" value="Active"
							 	style="border: 0; border-bottom: 0px ridge;"
								name="active" readonly>
							</c:if>
							<c:if test="${stateShow.active == false }">
								<input type="text" class="form-control" id="active" value="Inactive"
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
							style="border: 0px ridge;" readonly>${stateShow.remarks}</textarea>
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