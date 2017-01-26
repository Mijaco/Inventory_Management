<%@include file="../common/pettyCashHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<!-- @author: Ihteshamul Alam, IBCS -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/pettycash/pettyCashHeadSetup.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Petty Cash Head Entry Form
			</a>

			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Petty Cash Head List</h1>	
			
		</div>		
		
	</div>
	
	<div class="container">
			<div class="col-sm-10 col-sm-offset-1" style="background-color: white; padding: 10px; padding-left: 20px; 
			margin-top: 10px; margin-bottom: 10px;">
		
		<!-- --------------------- -->
		<div class="col-md-12 table-responsive">
			<c:if test="${empty pettyCashHeadList}">
				<h4 class="text-center" style="color: green;">Sorry! No data found in database</h4>
			</c:if>
			
			<c:if test="${!empty pettyCashHeadList}">
				<table class="table table-bordered" id="pettyCashHeadList">
					<thead>
						<tr>
							<th style="background: #579EC8; color: white; font-weight: normal;">SL. No</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Petty Code</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Petty Head</th>
						</tr>
					</thead>
					
					<%
						int count = 1;
					%>
					
					<tbody>
						<c:forEach items="${pettyCashHeadList}" var="pettyCash">
							<tr>
								<td><% out.print(count); %></td>
								<td>${pettyCash.pettyCashCode}</td>
								<td>${pettyCash.pettyCashHead}</td>
							</tr>
							<%
								count++;
							%>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		<!-- --------------------- -->		
	</div>	
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
		$(document)
				.ready(
						function() {
							$('#pettyCashHeadList').DataTable();
							document
									.getElementById('pettyCashHeadList_length').style.display = 'none';
							//document.getElementById('demandNoteMstListTable_filter').style.display = 'none';
						});
	</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>