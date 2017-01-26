<%@include file="../common/adminheader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/settings/list/department.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Department List
			</a>
			
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				${departmentShowById.deptName} Department Details</h1>
		</div>		
		
	</div>
	
	<div class="container-fluid">
			<div class="row" style="background-color: white; padding: 10px; margin: 10px">
		
		<!-- --------------------- -->
		<form method="GET" action="${pageContext.request.contextPath}/settings/update/department.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
				
					<%-- <div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
							<label 	for="deptId" class="col-sm-5 control-label" 
								style="vertical-align: middle;" >Department 
								ID </label>
						
						
						<div class="col-sm-7">
							<input type="text" class="form-control" id="deptId"
								value="${departmentShowById.deptId}" style="border: 0; border-bottom: 0px ridge;"
								name="deptId" readonly/>
							<input type="hidden" id="id"
								value="${departmentShowById.id }" name="id"/>
						</div>
					</div> --%>
					
					<div>
							<input type="hidden" id="deptId"
								value="${departmentShowById.deptId}"  name="deptId"/>
							<input type="hidden" id="id" value="${departmentShowById.id }" name="id"/>
					</div>
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="parent" class="col-sm-5 control-label" style="vertical-align: middle;">Parent
							Department </label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="parent"
								value="${departmentShowById.parent}" style="border: 0; border-bottom: 0px ridge;"
								name="parent" readonly/>							
						</div>
					</div>					
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="deptName" class="col-sm-5 col-md-5 control-label" style="vertical-align: middle;">Department
							Name </label>
						<div class="col-sm-7 col-md-7">
							<input type="text" class="form-control" id="deptName" 
							value="${departmentShowById.deptName}"	style="border: 0; 
							border-bottom: 0px ridge;" name="deptName" readonly/>
						</div>
					</div>
										
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="deptLevel" class="col-sm-5 control-label" style="vertical-align: middle;">Department
							Level </label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="deptLevel" value="${departmentShowById.deptLevel}"
								style="border: 0; border-bottom: 0px ridge;" name="deptLevel" readonly>
						</div>
					</div>
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="address" class="col-sm-5 control-label" style="vertical-align: middle;">Department
							Address </label>
						<div class="col-sm-7">						
							<input type="text" class="form-control" id="address" value="${departmentShowById.address}"
							 	style="border: 0; border-bottom: 0px ridge;"
								name="address" readonly>
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">
					
					<%-- <div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="active" class="col-sm-5 control-label" style="vertical-align: middle;">Active
							Status </label>
						<div class="col-sm-7 left">
							<c:if test="${departmentShowById.active == true }">
								<input type="text" class="form-control" id="active" value="Active"
							 	style="border: 0; border-bottom: 0px ridge;"
								name="active" readonly>
							</c:if>
							<c:if test="${departmentShowById.active == false }">
								<input type="text" class="form-control" id="active" value="Inactive"
							 	style="border: 0; border-bottom: 0px ridge;"
								name="active" readonly>
							</c:if>
						</div>
					</div> --%>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="contactPersion" class="col-sm-5 control-label" style="vertical-align: middle;">Contact
							Person </label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="contactPersion"
								value="${departmentShowById.contactPersion }" style="border: 0; 
								border-bottom: 0px ridge;" name="contactPersion" readonly>
						</div>
					</div>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="contactNo" class="col-sm-5 col-md-5 control-label" style="vertical-align: middle;">Contact
							Number </label>
						<div class="col-sm-7 col-md-7">
							<input type="text" class="form-control" value="${departmentShowById.contactNo }"
								id="contactNo" style="border: 0; border-bottom: 0px ridge;"
								name="contactNo" readonly/>
						</div>
					</div>					
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="email" class="col-sm-5 col-md-5 control-label" style="vertical-align: middle;">E-mail
							Address </label>
						<div class="col-sm-7 col-md-7">
							<input type="email" class="form-control" value="${departmentShowById.email }"
								id="email" style="border: 0; border-bottom: 0px ridge;"
								name="email" readonly />
						</div>
					</div>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="remarks" class="col-sm-5 col-md-5 control-label" style="vertical-align: middle;">Remarks
							 </label>
						<div class="col-sm-7 col-md-7">
							<textarea class="form-control" rows="4px" cols="auto" 
							style="border: 0px ridge;" readonly>${departmentShowById.remarks}</textarea>
						</div>
					</div>

				</div>

				<!-- <div class="col-md-12">
					<div class="form-group" style="margin-top: 2em; margin-right: 1em;">						
						<button class="width-20 pull-right btn btn-sm 
						btn-success" >
							<i class="ace-icon fa fa-save"></i> 
							<span class="bigger-50"> Update </span>
						</button>						
					</div>
				</div> -->
				
			</div>
		</form>
		<!-- --------------------- -->
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script type="text/javascript">
		jQuery(document).ready(){
			alert('hi!!');
			//if($('#deptId').val()){
				//$('#deptId').prop('readonly',true);
			//}
		}
		</script>
		
	</div>	
	</div>	
</div>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>