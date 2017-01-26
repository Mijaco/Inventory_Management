<%@include file="../../common/csHeader.jsp"%>
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
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px;">
		<!-- ------ Form Start ---------- -->
		<form method="POST" id="allocationForm"
			action="${pageContext.request.contextPath}/allocationTable/nextform.do">
			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}">
			<div class="col-md-8 col-md-offset-2">
			
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td class="col-xs-3 text-right" style="font-weight:bold;">Category:&nbsp;<strong class='red'>*</strong></td>
						<td class="col-xs-8">
							<select class="form-control category" id="category"
								onchange="categoryLeaveChange(this)" name="category"
								style="border: 0; border-bottom: 2px ridge;">
									<option disabled selected value="0">Category</option>
									<c:if test="${!empty categoryList}">
										<c:forEach items="${categoryList}" var="category">
											<option value="${category.categoryId}">
												${category.categoryId} - ${category.categoryName}</option>
										</c:forEach>
									</c:if>
							</select>
							<strong class="text-danger category hide">This field is required</strong>
						</td>
					</tr>
					<tr>
						<td class="col-xs-3 text-right" style="font-weight:bold;">Item Name:&nbsp;<strong class='red'>*</strong></td>
						<td class="col-xs-8">
							<select
								id="itemName" name="itemCode" class="form-control itemName"
								style="border: 0; border-bottom: 2px ridge;">
									<option disabled selected value="0">Item Name</option>
							</select>
							<strong class="itemCode hide text-danger">This field is required</strong>
						</td>
					</tr>
					<tr>
						<td class="col-xs-3 text-right" style="font-weight:bold;">Session:&nbsp;<strong class='red'>*</strong></td>
						<td class="col-xs-8">
							<select name="descoSessionId" class="form-control" id="descoSessionId"
								style="border: 0; border-bottom: 2px ridge;">
								<option value="0" disabled="disabled" selected="selected">---
									select a Session ---</option>
								<c:forEach items="${descoSessions}" var="descoSession">
									<option value="${descoSession.id}">${descoSession.sessionName}</option>
								</c:forEach>
							</select>
							<strong class="text-danger hide descoSession">This field is required</strong>
						</td>
					</tr>
				</tbody>
			</table>

			<div class="col-xs-12 col-sm-12" align="center">
				<button type="button"
					style="margin-right: 10px; border-radius: 6px;"
					class="width-20 btn btn-lg btn-success"
					id="saveButton" name="saveButton">
					<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Next</span>
				</button>
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
	
	function categoryLeaveChange(element) {
		var id = $(element).attr('id');
		var name = $(element).attr('name');
		var sequence = id.substr(name.length);

		var categoryId = $("#" + id).val();
		
		var contextPath = $("#contextPath").val();

		$.ajax({

			url : contextPath+'/allocationTable/viewInventoryItemCategory.do',
			data : "{categoryId:" + categoryId + "}",
			contentType : "application/json",
			success : function(data) {
				var itemList = JSON.parse(data);
				var itemNames = $(element).closest("div").parent().parent().find(
						'.itemName');

				itemNames.empty();
				
				var initOptn = "<option value='"+0+"' disabled selected>" + "Select Item" + "</option>";

				itemNames.append(initOptn);
				$.each(itemList, function(id, itemName) {
					itemNames.append($("<option></option>").attr("value", this.itemId)
							.text(this.itemName+" ["+this.itemId+"]"));
				});
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}
	
	$(document).ready( function() {
		$('#saveButton').click( function() {
			var haserror = false;
			if( $('#category').val() == null || $.trim( $('#category').val() ) == '0' ) {
				$('.category').removeClass('hide');
				haserror = true;
			} else {
				$('.category').addClass('hide');
			}
			
			if( $('#itemName').val() == null || $.trim( $('#itemName').val() ) == '0' ) {
				$('.itemCode').removeClass('hide');
				haserror = true;
			} else {
				$('.itemCode').addClass('hide');
			}
			
			if( $('#descoSessionId').val() == null || $.trim( $('#descoSessionId').val() ) == '0' ) {
				$('.descoSession').removeClass('hide');
				haserror = true;
			} else {
				$('.descoSession').addClass('hide');
			}
			
			if( haserror == true ) {
				return;
			} else {
				$('#saveButton').prop('disabled', true);
				$('#allocationForm').submit();
			}
		});
	});
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%-- <script
		src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script> --%>
<%@include file="../../common/ibcsFooter.jsp"%>