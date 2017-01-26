<%@include file="../../common/budgetHeader.jsp"%>
<!--End of Header -->
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<style>
.ui-datepicker table {
	display: none;
}

.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit  col-md-2">
			<%-- <a
				href="${pageContext.request.contextPath}/bgt/appFormWithSession.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create APP Package
			</a> --%>
		</div>
		<div class="col-md-8 ">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Budget of APP for ${descoSession.sessionName}</h2>

		</div>

		<div class="col-sm-12 text-right">
			<input type="hidden" id="contextPath"
				value="${pageContext.request.contextPath}" />
			<!-- --------------------- -->
			<div class="alert alert-success hide">
				<strong>Congratulation!</strong> Information is updated
				successfully.
			</div>
			<div class="alert alert-danger hide">
				<strong>Sorry!</strong> Information update is failed!!!.
			</div>
			<!-- --------------------- -->
		</div>

	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px 0;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty procurementPackageMstList}">
				<div class="col-sm-12 center">
					<p class="green">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>


			<c:if test="${!empty procurementPackageMstList}">
				<table id="procurementPackageMstListTable" style="width: 2400px;"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">

							<td style="">Package Name</td>
							<td style="">Quantity (nos)</td>
							<td style="">Procurement Method</td>
							<td style="">Type of tender</td>

							<td style="">Approving Authority</td>
							<td style="">Est. Cost (Million)</td>
							<td style="">Annexure</td>
							<td style="">Invitation of Tender</td>
							<!-- Preparation of document and  -->

							<td style="">Evaluation of Tender</td>
							<td style="">Award of contract</td>
							<td style="">Tentative Completion Date</td>


							<td style="">Budget of Current Session (Million)</td>
							<td style="">Budget of Next Session (Million)</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${procurementPackageMstList}" varStatus="loop"
							var="procurementPackageMst">
							<tr>
								<td>${procurementPackageMst.packageName}<input
									type="hidden" id="pk${loop.index}"
									value="${procurementPackageMst.id}" />
								</td>
								<td>${procurementPackageMst.procurementQty}</td>
								<td>${procurementPackageMst.procMethod}</td>
								<td>${procurementPackageMst.procType=='ssse'? 'Single Stage Single Envelope':'Single Stage Two Envelope'}</td>


								<td>${procurementPackageMst.approvingAuth}</td>
								<td>${procurementPackageMst.estimatedCost}</td>
								<td><a href="javascript:void(0)" class="blue"
									onclick="postSubmit('${pageContext.request.contextPath}/mps/procurementPackageShow.do',{id:'${procurementPackageMst.id}'},'POST')">
										${procurementPackageMst.annexureNo} </a></td>
								<td>${procurementPackageMst.prepDocInvTender}</td>

								<td>${procurementPackageMst.evaluationOfTender}</td>
								<td>${procurementPackageMst.awardOfContract}</td>
								<td>${procurementPackageMst.tentativeCompletion}</td>


								<td><input type="number" name="currentSessionBudget"
									id="currentSessionBudget${loop.index}" readonly="readonly"
									value="${empty procurementPackageMst.currentSessionBudget?'0':procurementPackageMst.currentSessionBudget}" /></td>
								<td><input type="number" name="nextSessionBudget"
									id="nextSessionBudget${loop.index}" readonly="readonly"
									value="${empty procurementPackageMst.nextSessionBudget?'0':procurementPackageMst.nextSessionBudget}" /></td>
								<td><c:if test="${empty procurementPackageMst.confirm}">
										<button type="button" id="editBtn${loop.index}"
											onclick="enableEditMode(${loop.index})"
											style="border-radius: 6px;"
											class="width-10  btn btn-sm btn-danger">
											<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">Edit</span>
										</button>

										<button type="button" id="updateBtn${loop.index}"
											onclick="enableUpdateMode(${loop.index})"
											style="border-radius: 6px; display: none;"
											class="width-10  btn btn-sm btn-success">
											<i class="ace-icon fa fa-save"></i> <span class="bigger-30">Update</span>
										</button>

									</c:if></td>

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
	$(function() {
		$('.date-picker_my')
				.datepicker(
						{
							changeMonth : true,
							changeYear : true,
							defaultDate : null,
							autoclose : true,
							showButtonPanel : true,
							dateFormat : 'M yy',
							onClose : function(dateText, inst) {
								function isDonePressed() {
									return ($('#ui-datepicker-div')
											.html()
											.indexOf(
													'ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all ui-state-hover') > -1);
								}

								if (isDonePressed()) {
									$(this).datepicker(
											'setDate',
											new Date(inst.selectedYear,
													inst.selectedMonth, 1));
								}

							},
							beforeShow : function(input, inst) {

								inst.dpDiv.addClass('month_year_datepicker');
								var monthNames = [ "Jan", "Feb", "Mar", "Apr",
										"May", "Jun", "Jul", "Aug", "Sep",
										"Oct", "Nov", "Dec" ];

								if ((datestr = $(this).val()).length > 0) {
									datestr = datestr.split(" ");
									year = datestr[1];
									month = monthNames.indexOf(datestr[0]);
									$(this).datepicker('option', 'defaultDate',
											new Date(year, month, 1));
									$(this).datepicker('setDate',
											new Date(year, month, 1));
									$(".ui-datepicker-calendar").hide();
								}
							}
						});
	});

	$(document).ready(function() {
		$('#procurementPackageMstListTable').DataTable({
			"order" : [ [ 2, "desc" ] ],
			"bLengthChange" : false
		});

	});
	
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
	
	
	
	function enableEditMode(n){
		 $('#editBtn'+n).css("display", "none");
		 $('#updateBtn'+n).css("display", "");
		 
		 /* $("#prepDocInvTender"+n).removeAttr("readonly");
		 $("#evaluationOfTender"+n).removeAttr("readonly");
		 $("#awardOfContract"+n).removeAttr("readonly");
		 $("#tentativeCompletion"+n).removeAttr("readonly"); */
		 $("#currentSessionBudget"+n).removeAttr("readonly");
		 $("#nextSessionBudget"+n).removeAttr("readonly");
		 
		
		
	}
	
	function enableUpdateMode(n){		 
		 $('#updateBtn'+n).css("display", "none");	
		 $('#editBtn'+n).css("display", "");		 
		      
		 /* $("#prepDocInvTender"+n).attr("readonly","readonly");
		 $("#evaluationOfTender"+n).attr("readonly","readonly");
		 $("#awardOfContract"+n).attr("readonly","readonly");
		 $("#tentativeCompletion"+n).attr("readonly","readonly"); */
		 $("#currentSessionBudget"+n).attr("readonly","readonly");
		 $("#nextSessionBudget"+n).attr("readonly","readonly");
		
		 updateAPP(n);
	}
	
	function updateAPP(n){
		var id=$('#pk'+n).val();
		var contextPath= $('#contextPath').val();
		var path= contextPath + '/bgt/updateAPP.do';
		
		 $("#nextSessionBudget"+n).attr("readonly","readonly");
		var cData = {
				id : id,
				/* prepDocInvTender :   $("#prepDocInvTender"+n).val(),
				evaluationOfTender :  $("#evaluationOfTender"+n).val(),
				awardOfContract : $("#awardOfContract"+n).val(),
				tentativeCompletion :  $("#tentativeCompletion"+n).val(), */				
				currentSessionBudget :  $("#currentSessionBudget"+n).val(),				
				nextSessionBudget :  $("#nextSessionBudget"+n).val()
			};
		var cDataJsonString = JSON.stringify(cData);
		$.ajax({			
			url : path,
			data : cDataJsonString,
			contentType : "application/json",
			success : function(data) {
				var result = JSON.parse(data);
				if(result=='success'){
					$('.alert.alert-success').removeClass('hide');
					$(".alert.alert-success").fadeTo(5000, 500).slideUp(500);
				} else {
					$('.alert.alert-danger').removeClass('hide');
					$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500);
				}
				
			},
			error : function(data) {
				alert("Server Error");
				$('.alert.alert-danger').removeClass('hide');
				$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500);
			},
			type : 'POST'
		});

	}
		
</script>


<!-- Footer -->
<%@include file="../../common/ibcsFooter.jsp"%>