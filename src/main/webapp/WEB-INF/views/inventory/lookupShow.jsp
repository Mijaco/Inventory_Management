<%@include file="../common/adminheader.jsp"%>
<!-- ---------------------End of Header-------------------------- -->


  
<div class="container-fluid icon-box" style="background-color: #858585;">
		<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Lookup</a> / Show
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/lookUpList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				All Lookup List
			</a> <a
				href="${pageContext.request.contextPath}/inventory/editLookup.do?id=${selectLookup.id}"
				style="text-decoration: none;" class="btn btn-info btn-sm"> <span
				class="ace-icon fa fa-pencil bigger-130" aria-hidden="true"></span> Edit
			</a>
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				All Lookup Show</h1>
		</div>
	</div>
	<div class="col-xs-8 col-xs-offset-2"
		style="background-color: white; padding: 10px; padding-left: 60px; margin-top: 10px; margin-bottom: 10px; margin-left: 15%; margin-right: 15%;">
		<form method="POST"
			action="${pageContext.request.contextPath}/inventory/lookupUpdate.do">
			<div class="table-responsive">
				<table class="table table-bordered table-hover">
					<tbody>
						<tr>
							<td class="col-xs-3 success text-right" style="font-weight: bold;">Parent:</td>
							<td class="col-xs-9">
								<input type="text" class="form-control" id="parentId" readonly="readonly"
									style="border: 0; border-bottom: 2px ridge;"  name="parentId" value="${selectLookup.parentId}">
							</td>
						</tr>
					
						<tr>
							<td class="col-xs-3 success text-right" style="font-weight: bold;">Title:</td>
							<td class="col-xs-9">
								<input type="text" class="form-control" id="titleForChild" readonly="readonly"
									style="border: 0; border-bottom: 2px ridge;"  name="titleForChild" value="${selectLookup.title}">
							</td>
						</tr>
					
						<tr>
							<td class="col-xs-3 success text-right" style="font-weight: bold;">Keyword:</td>
							<td class="col-xs-9">
								<input type="text" class="form-control" id="title" readonly="readonly"
									style="border: 0; border-bottom: 2px ridge;"
									name="keyword"  value="${selectLookup.keyword}"/>
							</td>
						</tr>
					
						<tr>
							<td class="col-xs-3 success text-right" style="font-weight: bold;">Remarks:</td>
							<td class="col-xs-9">
								<input type="text" class="form-control" id="title" readonly="readonly"
									style="border: 0; border-bottom: 2px ridge; "
									name = "remarks" value="${selectLookup.remarks}" />
							</td>
						</tr>
					
						<tr>
							<td class="col-xs-3 success text-right" style="font-weight: bold;">Is Active?</td>
							<td class="col-xs-9">
								<div class="checkbox">
									<label>
										<c:choose>
											<c:when test="${selectLookup.isActiveCheck==1}">
												<input type="checkbox" checked="checked"  disabled="disabled"
													name="isActive" value="${selectLookup.isActiveCheck}">
											</c:when>
											<c:when test="${selectLookup.isActiveCheck==0}">
												<input type="checkbox"  name="isActive"
													value="${selectLookup.isActiveCheck}" disabled="disabled">
											</c:when>	
										</c:choose>Active
									</label>
								</div>
								<input type="hidden" value="${selectLookup.id}" name="selectLookupId">
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
	</div>
</div>


























<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>