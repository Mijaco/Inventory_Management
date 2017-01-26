<%@include file="../../common/settingsHeader.jsp"%>
<!--End of Header -->
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-12" style="display: inline;">
			<input type="hidden" id="contextPath"
				value="${pageContext.request.contextPath}" /> <a
				href="${pageContext.request.contextPath}/mps/dn/demandNote1.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Requirement Form (Coded Item)
			</a> <a href="${pageContext.request.contextPath}/mps/dn/demandNote3.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Requirement Form (Non-Coded Item)
			</a>

		</div>
		<br>
		<div class="col-md-12">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				List of Requirement Note by ${department.deptName}</h2>

		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty demandNoteMstList}">
				<div class="col-sm-12 center">
					<p class="green">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>

			<c:if test="${!empty demandNoteMstList}">
				<table id="demandNoteMstListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Requirement Note No</td>
							<td style="">Financial Year</td>
							<td style="">Requirement Date</td>
							<td style="">Requirement Type</td>
							<td style="">Attachment</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${demandNoteMstList}" var="demandNoteMst">
							<tr>
								<td><a href="javascript:void(0)" class="blue"
									onclick="view(${demandNoteMst.id})">${demandNoteMst.demandNoteNo}
								</a></td>
								<td>${demandNoteMst.descoSession.sessionName}</td>
								<td><fmt:formatDate value="${demandNoteMst.demandDate}"
										pattern="dd-MM-yyyy" /></td>

								<td>${demandNoteMst.annexureType=='1'?'Coded Items':'Non-Coded Items'}</td>
								<td><c:if test="${!empty demandNoteMst.referenceDoc}">
										<form target="_blank"
											action="${pageContext.request.contextPath}/demandNote/downloadDemandNote.do"
											method="POST">
											<input type="hidden" value="${demandNoteMst.referenceDoc}"
												name="referenceDoc" />
											<button type="submit" class="fa fa-file-pdf-o red center"
												aria-hidden="true" style="font-size: 1.5em;"></button>
										</form>
									</c:if></td>

								<td>
									<div class="action-buttons center">										
										<button class="btn btn-primary btn-xs"
											onclick="view(${demandNoteMst.id})"
											style="border-radius: 4px;" id="viewDemandNote">
											<i class="fa fa-fw fa-eye"></i>&nbsp;Show
										</button>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
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
	$(document).ready(function() {
		$('#demandNoteMstListTable').DataTable({
			"order" : [ [ 2, "desc" ] ],
			"bLengthChange" : false
		});
	});
	

	function view(id) {			
		var contextPath=$("#contextPath").val();
		var cData = {"id" : id}
		var path=contextPath+'/mps/dn/demandNoteShow.do';	
		postSubmit(path, cData, 'POST');		
	}
</script>


<!-- Footer -->
<%@include file="../../common/ibcsFooter.jsp"%>