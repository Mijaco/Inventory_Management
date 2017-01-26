<%@include file="../common/adminheader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/settings/list/operation.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Operation List
			</a>
			
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				${operationShow.operationName} Operation Details</h1>
		</div>		
		
	</div>
	
	<div class="container-fluid">
			<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		
		<!-- --------------------- -->
		<form method="POST" action="${pageContext.request.contextPath}/settings/update/operation.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
				
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
							<label 	for="operationName" class="col-sm-5 control-label" 
								style="vertical-align: middle;" > Operation 
								Name </label>
						
						
						<div class="col-sm-7">
							<input type="text" class="form-control" id="operationName"
								value="${operationShow.operationName}" style="border: 0; border-bottom: 0px ridge;"
								name="operationName" readonly/>
						</div>
					</div>
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="createdBy" class="col-sm-5 control-label" style="vertical-align: middle;"> Created
							By </label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="createdBy"
								value="${operationShow.createdBy}" style="border: 0; border-bottom: 0px ridge;"
								name="createdBy" readonly/>							
						</div>
					</div>					
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="modifiedBy" class="col-sm-5 col-md-5 control-label" style="vertical-align: middle;"> Last Modified
							By </label>
						<div class="col-sm-7 col-md-7">
							<input type="text" class="form-control" id="modifiedBy" 
							value="${operationShow.modifiedBy}"	style="border: 0; 
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
							<c:if test="${operationShow.active == true }">
								<input type="text" class="form-control" id="active" value="Active"
							 	style="border: 0; border-bottom: 0px ridge;"
								name="active" readonly>
							</c:if>
							<c:if test="${operationShow.active == false }">
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
							style="border: 0px ridge;" readonly>${operationShow.remarks}</textarea>
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