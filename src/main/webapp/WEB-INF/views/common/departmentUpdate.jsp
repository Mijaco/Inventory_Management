<%@include file="../common/adminheader.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
				Update ${department.deptName} Department Information</h1>
		</div>		
		
	</div>
	
	<div class="container-fluid">
			<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		
		<!-- --------------------- -->
		<form method="POST" action="${pageContext.request.contextPath}/settings/update/existingDepartment.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
				
					<%-- <div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="deptId" class="col-sm-5 control-label">Department
							ID </label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="deptId"
								value="${department.deptId }" style="border: 0; border-bottom: 2px ridge;"
								name="deptId" readonly/>
							<input type="hidden" id="id"
								value="${department.id }" name="id"/>
						</div>
					</div> --%>
					
					<div>
						<input type="hidden"  id="deptId" value="${department.deptId }" name="deptId"/>
						<input type="hidden" id="id" value="${department.id }" name="id"/>
					</div>
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="parent" class="col-sm-5 control-label">Parent
							Department </label>
						<div class="col-sm-7">
							<select class="form-control" id="parent"
								style="border: 0; border-bottom: 2px ridge;" name="parent">
								<option selected>${department.parent}</option>
								<c:if test="${!empty departmentList}">
									<c:forEach var="departmentList" items="${departmentList}">
										<option value="${departmentList.deptId}">
											<c:out value="${departmentList.deptName}"/>
										</option>
									</c:forEach>
								</c:if>
							</select>							
						</div>
					</div>					
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="deptName" class="col-sm-5 col-md-5 control-label">Department
							Name </label>
						<div class="col-sm-7 col-md-7">
							<input type="text" class="form-control" id="deptName" value="${department.deptName }"
								style="border: 0; border-bottom: 2px ridge;" name="deptName"/>
						</div>
					</div>
										
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="deptLevel" class="col-sm-5 control-label"> Department
							Level </label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="deptLevel" value="${department.deptLevel }"
								style="border: 0; border-bottom: 2px ridge;"
								name="deptLevel">
						</div>
					</div>
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="address" class="col-sm-5 control-label">Department
							Address </label>
						<div class="col-sm-7">						
							<input type="text" class="form-control" id="address" value="${department.address }"
							 	style="border: 0; border-bottom: 2px ridge;"
								name="address">
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">
					
					<%-- <div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="active" class="col-sm-5 control-label">Active
							Status </label>
						<div class="col-sm-7 left">													
							<c:if test="${department.active eq true}">
								<input type="checkbox" checked name="active" id="active" />
							</c:if>
							<c:if test="${department.active eq false}">
								<input type="checkbox" name="active" id="active" />
							</c:if>
						</div>
					</div> --%>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="contactPersion" class="col-sm-5 control-label">Contact
							Person </label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="contactPersion"
								value="${department.contactPersion }" style="border: 0; border-bottom: 2px ridge;"
								name="contactPersion">
						</div>
					</div>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="contactNo" class="col-sm-5 col-md-5 control-label">Contact
							Number </label>
						<div class="col-sm-7 col-md-7">
							<input type="text" class="form-control" value="${department.contactNo }"
								id="contactNo" style="border: 0; border-bottom: 2px ridge;"
								name="contactNo" />
						</div>
					</div>					
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="email" class="col-sm-5 col-md-5 control-label">E-mail
							Address </label>
						<div class="col-sm-7 col-md-7">
							<input type="email" class="form-control" value="${department.email }"
								id="email" style="border: 0; border-bottom: 2px ridge;"
								name="email" />
						</div>
					</div>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="remarks" class="col-sm-5 col-md-5 control-label">Remarks
							 </label>
							 <c:set var="remarks" value="${department.remarks}" />
						<div class="col-sm-7 col-md-7">
							<textarea id="remarks" class="form-control" name="remarks">${fn:trim(remarks)}</textarea>
						</div>
					</div>

				</div>

				<div class="col-md-12">
					<div class="form-group" style="margin-top: 2em; margin-right: 1em;" align="center">
						
						<button type="submit" class="btn btn-success btn-lg" style="border-radius: 6px;">
							<i class="ace-icon fa fa-save"></i> 
							<span class="bigger-50">Update</span>
						</button>
						
						<button type="reset" class="btn btn-danger btn-lg" style="border-radius: 6px;"
						style="margin-right: 10px;">
							<i class="ace-icon fa fa-refresh"></i> 
							<span class="bigger-50">Reset</span>
						</button>
						
					</div>

				</div>

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