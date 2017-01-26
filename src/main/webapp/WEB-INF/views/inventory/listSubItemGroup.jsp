<%@include file="../inventory/inventoryheader.jsp"%>
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
	/* background-color: none;
	width: 100%;
	height: 100%; */
}
</style>
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br>
	<span class="orange">Loading...</span>
</div>
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Item Sub Group</a> / List
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
				<a href="${pageContext.request.contextPath}/inventory/showSubItemGroup.do"
					style="text-decoration: none;" class="btn btn-primary btn-sm">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					Create Item Sub Group
				</a>
				<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
					Discard</button> -->
			</div>
		</div>

		<div class="row" style="background: white;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty itemGroupSubList}">
				<div class="col-sm-12 center">
					<p class="red"><i>Sorry!!! No Data Found in Database. </i></p>
				</div>
			</c:if>
			<c:if test="${!empty itemGroupSubList}">
			<div class="col-sm-6 pull-right">
					<form method="POST"
						action="${pageContext.request.contextPath}/inventory/searchBySubGroupName.do">

						<!-- <div class="input-group">
							<input type="" class="form-control"
								placeholder="Search for..."> <span
								class="input-group-btn">
								<button class="btn btn-default" type="button">Go!</button>
							</span>
						</div> -->
						<!-- /input-group -->

						<div class="form-group col-sm-9 col-sm-offset-2">
							<input type="text" class="form-control" id="search"
								placeholder="Search by Item Sub Group Name."
								style="border: 0; border-bottom: 2px ridge;" name="subGroupNameForSearch" />								
						</div>
						<div class="col-sm-1">
							<button type="submit"
								class="btn btn-info glyphicon glyphicon-search"></button>
						</div>
					</form>
				</div>
				<table id="itemGroupSubList"
					class="table table-striped table-hover table-bordered">
				
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Group Name</td>
							<td style="">Sub Group Name</td>
							<td style="">Active/not</td>
							<td style="">Action</td>
							
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${itemGroupSubList}" var="itemGroupSubList">
							<tr>							
								<%--  <td><a
									href="${pageContext.request.contextPath}/inventory/showItemGroup.do?itemSubGroupId=${itemGroupList.itemGroupName}"
									style="text-decoration: none;"></a></td>  --%>
									<td><c:out value="${itemGroupSubList.itemGroupName}" /></td>
								<td><c:out value="${itemGroupSubList.itemSubGroupName}" /></td>
								<td><c:out value="${itemGroupSubList.itemSubGroupIsActive}" /></td>
								
								<td>
									<div class="action-buttons">
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/inventory/editItemSubGroup.do?itemSubGroupId=${itemGroupSubList.itemSubGroupId}">
											<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
										</a> <a class="btn btn-success btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/inventory/showItemSubGroup.do?itemSubGroupId=${itemGroupSubList.itemSubGroupId}">
											<i class="fa fa-fw fa-eye"></i>&nbsp;Show
										</a>
									</div>
								</td>
								</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<!-- --------------------- -->

			
			

			<!-- --------------------- -->

						
			</div>
			<!-- --------------------- -->
			
		</div>
	</div>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#itemGroupSubList').DataTable();
						document.getElementById('itemGroupSubList_length').style.display = 'none';
						document.getElementById('itemGroupSubList_filter').style.display = 'none';

					});
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

	<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
