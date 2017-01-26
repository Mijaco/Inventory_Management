<%@include file="../common/settingsHeader.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: offwhite;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/common/approvalHierarchyList.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Approval Hierarchy List
			</a>
<!-- 			<button accesskey="D" class="btn btn-info btn-sm" type="button"> Discard </button> -->
			
			<h1 class="center blue"><em> Approval Hierarchy Details Information </em></h1>
			
		</div>		
		
	</div>
	
	<div class="container">
			<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; 
			margin-top: 10px; margin-bottom: 10px;">
		
		<!-- --------------------- -->
		<form method="POST" action="${pageContext.request.contextPath}/settings/update/existingApprovalHierarchy.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
				
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="operationName" class="col-sm-5 control-label"> Operation
							Name </label>
						<div class="col-sm-7">
							<select class="form-control" id="operationName"
								style="border: 0; border-bottom: 2px ridge;" name="operationName">
								<option selected>${approvalHierarchy.operationName}</option>
								<c:if test="${!empty operationList}">
									<c:forEach var="operation" items="${operationList}">
										<option value="${operation.operationName}">
											<c:out value="${operation.operationName}"></c:out>
										</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="stateName" class="col-sm-5 control-label"> State
							Name </label>
						<div class="col-sm-7">
							<select class="form-control" id="stateName"
								style="border: 0; border-bottom: 2px ridge;" name="stateName">
								<option selected>${approvalHierarchy.stateName}</option>
								<c:if test="${!empty stateList}">
									<c:forEach var="state" items="${stateList}">
										<option value="${state.stateName}">
											<c:out value="${state.stateName}"/>
										</option>
									</c:forEach>
								</c:if>
							</select>							
						</div>
					</div>					
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>					
					
					<div class="form-group">
						<label for="stateCode" class="col-sm-5 col-md-5 control-label"> State
							Code </label>
						<div class="col-sm-7 col-md-7">
							<input 	type="text" class="form-control" id="stateCode" 
									value="${approvalHierarchy.stateCode}"
									style="border: 0; border-bottom: 2px ridge;" name="stateCode"/>
							<input type="hidden" id="id"
								value="${approvalHierarchy.id }" name="id"/>
						</div>
					</div>
										
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="roleName" class="col-sm-5 control-label"> Role
							Name </label>
						<div class="col-sm-7">
							<select class="form-control" id="roleName"
								style="border: 0; border-bottom: 2px ridge;" name="roleName">
								<option selected>${approvalHierarchy.roleName}</option>
								<c:if test="${!empty roleList}">
									<c:forEach var="role" items="${roleList}">
										<option value="${role.role}">
											<c:out value="${role.role}"></c:out>
										</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="buttonName" class="col-sm-5 control-label"> Button
							Name </label>
						<div class="col-sm-7">						
							<input type="text" class="form-control" id="buttonName" 
							value="${approvalHierarchy.buttonName}"
							 	style="border: 0; border-bottom: 2px ridge;"
								name="buttonName">
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="active" class="col-sm-5 control-label">Active
							Status </label>
						<div class="col-sm-7 left">																			
							<c:if test="${approvalHierarchy.active eq true}">
								<input type="checkbox" checked name="active" id="active" />
							</c:if>
							<c:if test="${approvalHierarchy.active eq false}">
								<input type="checkbox" name="active" id="active" />
							</c:if>
						</div>
					</div>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="approvalHeader" class="col-sm-5 control-label"> Approval
							Header </label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="approvalHeader"
								value="${approvalHierarchy.approvalHeader}" style="border: 0; border-bottom: 2px ridge;"
								name="approvalHeader">
						</div>
					</div>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="remarks" class="col-sm-5 col-md-5 control-label"> Remarks
							 </label>
						<div class="col-sm-7 col-md-7">							
							<textarea rows="4" cols="" id="remarks" class="form-control"
							maxlength="1000" name="remarks"><c:out value="${approvalHierarchy.remarks}"/></textarea>
						</div>
					</div>

				</div>
				
				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="submit"
							style="border-radius: 6px;"
							class="pull-right btn btn-lg btn-success">
							<i class="fa fa-save"></i>&nbsp;Update
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="reset"
							class="pull-left btn btn-lg btn-danger"
							style="margin-left: 10px; border-radius: 6px;">
							<i class="fa fa-refresh"></i> <span class="bigger-50">Reset</span>
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