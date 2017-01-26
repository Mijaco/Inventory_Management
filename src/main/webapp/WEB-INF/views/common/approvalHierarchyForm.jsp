<%@include file="../common/adminheader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/settings/list/distinctApprovalHierarchy.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Approval Hierarchy List
			</a>
<!-- 			<button accesskey="D" class="btn btn-info btn-sm" type="button"> Discard </button> -->
			
			<h1 class="center blue"><em> New Approval Hierarchy Entry Form </em></h1>
			
		</div>		
		
	</div>
	
	<div class="container-fluid">
			<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		
		<!-- --------------------- -->
		<form method="POST" action="${pageContext.request.contextPath}/settings/add/newApprovalHierarchySave.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
				
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="operationName" class="col-sm-5 control-label"> Operation
							Name:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-7">
							<select class="form-control category" style="border: 0; border-bottom: 2px ridge;"
								name="operationName" required>
								<option disabled selected>--- Select a Operation ---</option>
								<c:if test="${!empty operationList}">
									<c:forEach items="${operationList}" var="operationList">
										<option>
											<c:out value="${operationList.operationName}"></c:out>
										</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="stateName" class="col-sm-5 control-label"> State
							Name:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-7">
							<select class="form-control" id="stateName"
								style="border: 0; border-bottom: 2px ridge;" name="stateName" required>
								<option disabled selected>--- Select a State ---</option>
								<c:if test="${!empty stateList}">
									<c:forEach var="state" items="${stateList}">
										<option><c:out value="${state.stateName}"></c:out></option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>			
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="stateCode" class="col-sm-5 col-md-5 control-label"> State
							Code:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-7 col-md-7">
							<input type="text" class="form-control" id="stateCode" placeholder="State Code"
								style="border: 0; border-bottom: 2px ridge;" name="stateCode" required/>
						</div>
					</div>
										
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="roleName" class="col-sm-5 control-label"> Role
							Name:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-7">
							<select class="form-control" id="roleName"
								style="border: 0; border-bottom: 2px ridge;" name="roleName" required>
								<option disabled selected>--- Select a Role ---</option>
								<c:if test="${!empty roleList}">
									<c:forEach var="role" items="${roleList}">
										<option><c:out value="${role.role}"></c:out></option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					
					

				</div>

				<div class="col-md-6 col-sm-6">
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="address" class="col-sm-5 control-label"> Button
							Name:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="buttonName" 
								placeholder="Write Button Name" required
							 	style="border: 0; border-bottom: 2px ridge;" name="buttonName">
						</div>
					</div>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="approvalHeader" class="col-sm-5 control-label"> Approval
							Header:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="approvalHeader"
								placeholder="Approval Header" style="border: 0; border-bottom: 2px ridge;"
								name="approvalHeader" required/>
						</div>
					</div>
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="remarks" class="col-sm-5 col-md-5 control-label"> Remarks
							</label>
						<div class="col-sm-7 col-md-7">
							<textarea rows="4" cols="" id="remarks" class="form-control"
							maxlength="1000" name="remarks"></textarea>
						</div>
					</div>					
					
				</div>

<!-- 				<div class="col-xs-12"> -->
<!-- 					<div class="form-group" style="margin-top: 2em; margin-right: 1em;">						 -->
<!-- 						<button type="submit" style="border-radius: 6px" class="col-xs-12 width-20 pull-right btn btn-lg btn-success" value="add_more" name="action" > -->
<!-- 							<i class="ace-icon fa fa-plus"></i>  -->
<!-- 							<span class="bigger-50"> Add More </span> -->
<!-- 						</button>						 -->
<!-- 						<button type="submit" style="border-radius: 6px" class="col-xs-12 width-20 pull-right btn btn-lg btn-success" value="add" name="action" style="margin-right: 10px;"> -->
<!-- 							<i class="ace-icon fa fa-save"></i>  -->
<!-- 							<span class="bigger-50"> Add </span> -->
<!-- 						</button>						 -->
<!-- 						<button type="reset" style="border-radius: 6px" class="col-xs-12 width-20 pull-right btn btn-lg"  -->
<!-- 						style="margin-right: 10px;"> -->
<!-- 							<i class="ace-icon fa fa-refresh"></i>  -->
<!-- 							<span class="bigger-50"> Reset </span> -->
<!-- 						</button>												 -->
<!-- 					</div> -->
<!-- 				</div> -->

					<div class="col-md-12" style="padding-top: 15px;" align="center">
						<button type="reset" class="btn btn-lg btn-danger"
							style="border-radius: 6px;" name="action">
							<i class="fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button>

						<button type="submit" value="add"
							style="border-radius: 6px; margin-left: 10px;"
							class="btn btn-lg btn-success" name="action">
							<i class="fa fa-save"></i>&nbsp;Add
						</button>
						<button type="submit" value="add_more" name="action"
							style="border-radius: 6px; margin-left: 10px;"
							class="btn btn-lg btn-success">
							<i class="fa fa-plus"></i>&nbsp;Add More
						</button>

					</div>

				</div>
		</form>
		<!-- --------------------- -->
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script type="text/javascript">
		
		$( document ).ready( function() {
			//
		});
		</script>
		
	</div>	
	</div>	
</div>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>