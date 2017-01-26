
<%-- <%@include file="../common/procurementHeader.jsp"%> --%>
<%@include file="../common/csHeader.jsp"%>
<style type="text/css">
.btn-add {
	margin-bottom: 20px;
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>
<!-- ---------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="#" onclick="myDialoge()" style="text-decoration: none;"
				class="btn btn-success btn-sm ">&nbsp; Add New Session </a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Allocation
			Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- ------ Form Start ---------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/allocationTab/nextform.do">
			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="deptId" class="col-sm-4 control-label">S&amp;D</label>
						<div class="col-sm-8">
							<select name="deptId" class="form-control" id="deptId"
								style="border: 0; border-bottom: 2px ridge;">
								<option disabled="disabled" selected="selected">---
									select a S&D ---</option>
								<c:forEach items="${departments}" var="department">
									<option value="${department.deptId}">${department.deptName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="descoSessionId"
							class="col-sm-4 col-md-4 control-label align-right">Session</label>
						<div class="col-sm-8 col-md-8">
							<select name="descoSessionId" class="form-control" id="descoSessionId"
								style="border: 0; border-bottom: 2px ridge;">
								<option disabled="disabled" selected="selected">---
									select a Session ---</option>
								<c:forEach items="${descoSessions}" var="descoSession">
									<option value="${descoSession.id}">${descoSession.sessionName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>


				<div class="col-md-12 right" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-12 right">
						<button type="submit"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-lg btn-success right"
							id="saveButton" name="saveButton">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Next</span>
						</button>
					</div>
				</div>

			</div>

			<input
				type="hidden" id="contextPath" name="contextpath"
				value='${pageContext.request.contextPath}' />
		</form>
		<!-- --------------------- -->
	</div>
</div>

<div id="myDialog1" title="Add New Session...">
	<form>
		<div class="col-xs-12">
			<div class="col-xs-3">
				<label>Session Name :</label>
			</div>
			<div class="col-xs-9">
				<input class="form-control" type="text" id="sessionName"
					name="sessionName" />
			</div>
		</div>

		<div class="col-xs-12">
			<div class="col-xs-3">
				<label>Start Date :</label>
			</div>
			<div class="col-xs-9">
				<input class="form-control datepicker-14" type="text" id="startDate"
					name="startDate" readonly="readonly"/>
			</div>
		</div>

		<div class="col-xs-12">
			<div class="col-xs-3">
				<label>End Date :</label>
			</div>
			<div class="col-xs-9">
				<input class="form-control datepicker-14" type="text" id="endDate"
					name="endDate" readonly="readonly"/>
			</div>
		</div>



	</form>
</div>
<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet"/>
<script>
	//location dialog is set hidden
	$(function() {
		$("#myDialog1").dialog({
			autoOpen : false,
			closeOnEscape : false,
			modal : true,
			draggable : false,
			resizable : false,
			position : {
				my : "center",
				at : "center",
				of : window
			},
			show : 'blind',
			hide : 'blind',
			width : 600,
			buttons : {
				"Submit" : function() {
					$(this).dialog("close");
					submitSessionForm();
					$('#myDialog1').empty();

				},
				"Close" : function() {
					$(this).dialog("close");
					$('#myDialog1').empty();
				}
			}
		});

	});

	function submitSessionForm() {
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var sessionName = $("#sessionName").val();
		var contextPath = $("#contextPath").val();
		var cData = {
			sessionName : sessionName,
			startDate : startDate,
			endDate : endDate
		}

		var path = contextPath + "/saveDescoSession.do";

		postSubmit(path, cData, 'POST');
	}

	function myDialoge() {
		$("#myDialog1").dialog("open");
	}
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%-- <script
		src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script> --%>
<%@include file="../common/ibcsFooter.jsp"%>