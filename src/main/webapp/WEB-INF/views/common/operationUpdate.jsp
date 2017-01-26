<%@include file="../common/adminheader.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
				Update ${operation.operationName} Operation Information</h1>
		</div>		
		
	</div>
	
	<div class="container-fluid">
			<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		
		<!-- --------------------- -->
		<form method="POST" action="${pageContext.request.contextPath}/settings/update/existingOperation.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
				
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="operationName" class="col-sm-5 col-md-5 control-label"> Operation
							Name </label>
						<div class="col-sm-7 col-md-7">
							<input type="text" class="form-control" id="operationName" value="${operation.operationName }"
								style="border: 0; border-bottom: 2px ridge;" name="operationName"/>
						</div>
					</div>
														
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="active" class="col-sm-5 control-label">Active
							Status </label>
						<div class="col-sm-7 left">						
							
							<c:if test="${operation.active eq true}">
								<input type="checkbox" checked name="active" id="active" />
							</c:if>
							<c:if test="${operation.active eq false}">
								<input type="checkbox" name="active" id="active" />
							</c:if>
						
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">				
					
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="remarks" class="col-sm-5 col-md-5 control-label">Remarks
							 </label>
						<div class="col-sm-7 col-md-7">
						<c:set var="remarks" value="${operation.remarks}"></c:set>
							<textarea rows="4" cols="" id="remarks" class="form-control"
							maxlength="1000" name="remarks">${fn:trim(remarks)}</textarea>
							<input type="hidden" id="id"
								value="${operation.id}" name="id"/>
						</div>
					</div>

				</div>

				<div class="col-md-12">
					<div class="form-group" style="margin-top: 2em; margin-right: 1em;" align="center">
						
						<button type="submit" class="btn btn-lg btn-success"
							style="border-radius: 6px;">
							<i class="ace-icon fa fa-save"></i> 
							<span class="bigger-50">Update</span>
						</button>
						
						<button type="reset" class="btn btn-lg btn-danger" 
						style="margin-right: 10px; border-radius: 6px;">
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