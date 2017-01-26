<%@include file="../common/adminheader.jsp"%>
<!-- ---------------------End of Header-------------------------- -->

<!-- @author: Ihteshamul Alam, IBCS -->

<style>
	textarea {
		resize: none;
	}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/inventory/lookUpList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				All Lookup List
			</a> <a
				href="${pageContext.request.contextPath}/inventory/showLookupById.do?id=${selectLookup.id}"
				style="text-decoration: none;" class="btn btn-info btn-sm"> <span
				class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> Show
			</a>
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				All Lookup Edit</h1>
		</div>
	</div>
	<div class="col-md-8 col-md-offset2"
		style="background-color: white; padding: 10px; padding-left: 60px; margin-top: 10px; margin-bottom: 10px; margin-left: 15%; margin-right: 15%;">
		
		<form method="POST" action="${pageContext.request.contextPath}/inventory/lookupUpdate.do">
			<div class="table-responsive">
				<table class="table table-bordered table-hover">
					<tr>
						<td class="col-xs-3 success text-right" style="font-weight: bold;">Parent:</td>
						<td class="col-xs-9">
							<select name="parentName" class="form-control" style="border: 0; border-bottom: 2px ridge;">
								<option selected>${selectLookup.parentName}</option>
								<c:if test="${!empty parentList}">
									<c:forEach items="${parentList}" var="parentList">

										<option value="${parentList.title}">
											<c:out value="${parentList.title}" />
										</option>
									</c:forEach>
								</c:if>
							</select>
						</td>
					</tr>
					
					<tr>
						<td class="col-xs-3 success text-right" style="font-weight: bold;">Title:</td>
						<td class="col-xs-9">
							<input type="text" class="form-control" id="titleForChild"
								style="border: 0; border-bottom: 2px ridge;"
								name="title" value="${selectLookup.title}">
						</td>
					</tr>
					
					<tr>
						<td class="col-xs-3 success text-right" style="font-weight: bold;">Keyword:</td>
						<td class="col-xs-9">
							<input type="text" class="form-control" id="title"
								style="border: 0; border-bottom: 2px ridge;"
								name="keyword" value="${selectLookup.keyword}">
						</td>
					</tr>
					
					<tr>
						<td class="col-xs-3 success text-right" style="font-weight: bold;">Remarks:</td>
						<td class="col-xs-9">
							<textarea class="form-control" id="title"
								style="border: 0; border-bottom: 2px ridge;"
								name="remarks">${selectLookup.remarks}</textarea>
						</td>
					</tr>
					
					<tr>
						<td class="col-xs-3 success text-right" style="font-weight: bold;">Is Active?</td>
						<td class="col-xs-9">
							<div class="checkbox">
								<label>
									<c:choose>
										<c:when test="${selectLookup.isActiveCheck==1}">
											<input type="checkbox" checked="checked" name="isActive"
												value="${selectLookup.isActiveCheck}">
										</c:when>
										<c:when test="${selectLookup.isActiveCheck==0}">
											<input type="checkbox" name="isActive"
												value="${selectLookup.isActiveCheck}">
										</c:when>
									</c:choose> Active
								</label>
							</div>
							<input type="text" value="42" hidden="true" name="lookupId" />
							
						</td>
					</tr>
				</table>
				<div class="col-md-12" style="padding-top: 15px;" align="center">
					<button type="submit" class="btn btn-lg btn-success" style="border-radius: 6px;">
						<i class="fa fa-fw fa-save"></i> <span class="bigger-50">Update</span>
					</button>
				</div>
			</div>
		</form>
	</div>
</div>


























<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>