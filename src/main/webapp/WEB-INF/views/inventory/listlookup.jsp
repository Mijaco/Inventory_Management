<%@include file="../common/adminheader.jsp"%>
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
}
</style>
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/inventory/showLookUp.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Lookup
			</a>
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				All Lookup List</h1>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px 10px 20px 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty lookUpList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty lookUpList}">
				<!-- 			<div class="col-sm-6 pull-right"> -->
				<!-- 					<form method="POST" -->
				<%-- 						action="${pageContext.request.contextPath}/inventory/searchByKeyword.do"> --%>

				<!-- 						<div class="input-group">
<!-- 							<input type="" class="form-control" -->
				<!-- 								placeholder="Search for..."> <span -->
				<!-- 								class="input-group-btn"> -->
				<!-- 								<button class="btn btn-default" type="button">Go!</button> -->
				<!-- 							</span> -->
				<!-- 						</div> -->
				<!-- 						/input-group -->

				<!-- 						<div class="form-group col-sm-9 col-sm-offset-2"> -->
				<!-- 							<input type="text" class="form-control" id="search" -->
				<!-- 								placeholder="Search by Keyword." -->
				<!-- 								style="border: 0; border-bottom: 2px ridge;" name="keywordForSearch" />								 -->
				<!-- 						</div> -->
				<!-- 						<div class="col-sm-1"> -->
				<!-- 							<button type="submit" -->
				<!-- 								class="btn btn-info glyphicon glyphicon-search"></button> -->
				<!-- 						</div> -->
				<!-- 					</form> -->
				<!-- 				</div> -->
				<table id="lookupList"
					class="table table-striped table-hover table-bordered">

					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Parent</td>
							<td style="">Keyword</td>
							<td style="">Title</td>
							<!-- <td style="">Remarks</td> -->
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${lookUpList}" var="lookUpList">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/inventory/showLookupById.do?id=${lookUpList.id}"
									style="text-decoration: none;"><c:out
											value="${lookUpList.parentName}" /></a></td>
								<td><c:out value="${lookUpList.keyword}" /></td>
								<td><c:out value="${lookUpList.title}" /></td>
								<%-- <td><c:out value="${lookUpList.remarks}" /></td> --%>
								<td align="center">
									<div class="action-buttons">
										<a class="btn btn-xs btn-primary" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/inventory/editLookup.do?id=${lookUpList.id}">
											<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
										</a> <a class="btn btn-xs btn-success" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/inventory/showLookupById.do?id=${lookUpList.id}">
											<i class="fa fa-fw fa-eye"></i>&nbsp;View
										</a>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>


		</div>
		<!-- --------------------- -->

	</div>
</div>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#lookupList').DataTable({			
			"order" : [ [ 2, "desc" ] ],
			"bLengthChange" : false
			
		});
	});

	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>