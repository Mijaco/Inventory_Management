<%@include file="../common/wsHeader.jsp"%>
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
		<div class="o_form_buttons_edit" style="display: block;">
			<button type="button" title="Create Manufacture Name"
										onClick="openDialoge1('assetId${status.index}','receiveDate${status.index}','flag${status.index}')">
										<span class="fa fa-plus fa-2x blue"></span> <b>Create Manufacture Name</b>
									</button>
		</div>
		<div class="o_form_buttons_edit" style="display: block;">
			
			<h1 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Transformer ManufactureName List</h1>
		</div>
	</div>

	<div class="row" 
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			
			<input type="hidden" name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" />

			<c:if test="${!empty manufactureNameList}">
				
				<table id="storeRequisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Manufacture Id</td>
							<td style="border-radius: 6px; display: none;">
							<td style="">Manufacture Name</td>
							<td style="">Action</td>
							</tr>
					</thead>

					<tbody>
						<c:forEach items="${manufactureNameList}"
							var="manufactureNames" varStatus="loop">
							<tr>
															
								<td>${manufactureNames.id}<input type="hidden" id="pk${loop.index}" value="${manufactureNames.id}" /></td>
								<td id="td1${loop.index}" style="border-radius: 6px; display: ">${manufactureNames.manufactureName}</td>
								<td id="td2${loop.index}" style="border-radius: 6px; display: none;"><input type="text" id="manufactureName${loop.index}" value="${manufactureNames.manufactureName}" /></td>
								<td class="center">
									<button type="button" id="editBtn${loop.index}"
										onclick="enableEditMode(${loop.index})"
										style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-primary">
										<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">Edit</span>
									</button>

									<button type="button" id="updateBtn${loop.index}"
										onclick="enableUpdateMode(${loop.index})"
										style="border-radius: 6px; display: none;"
										class="width-10  btn btn-sm btn-success">
										<i class="ace-icon fa fa-save"></i> <span class="bigger-30">Update</span>
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<!-- --------------------- -->
		</div>

	</div>
</div>
<div id="myDialogeBox" class="hide">
	<div>
		<form
			action="${pageContext.request.contextPath}/saveManufactureName.do"
			id="manufactureNameForm" method="POST">
			<!-- Master Info :: start -->
			<div class="table-responsive">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="col-xs-2 success">ManufactureName</td>
							<td class="col-xs-6 info"><input type="text" value="" style="width: 270px;"
								id="manufactureName" name="manufactureName" />
								</td>
						</tr>
						
						
					</tbody>
				</table>
			</div>
			<!-- Master Info :: end -->
		</form>
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
						$('#storeRequisitionListTable').DataTable();
						document
								.getElementById('storeRequisitionListTable_length').style.display = 'none';
						// document.getElementById('storeRequisitionListTable_filter').style.display = 'none';

					});

	function createNewDiv() {
		document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';
		/* $(".container-fluid").append("<div>hello</div>"); */
	}
	
	$(function() {
		$("#myDialogeBox").dialog({
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
			show : {
				effect : "blind",
				duration : 10
			},
			hide : 'blind',
			width : $(window).width() - 800,
			height : 100,
			buttons : [ {
				text : 'Save',
				click : function() {
					// send post request
					$("#manufactureNameForm").submit();
					// $('#myDialogeBox').empty();
					//getCountBtn();
				}
			}, {
				text : 'Close',
				click : function() {
					$(this).dialog("close");
				}
			} ]
		});

	});
	
	function openDialoge1() {
		$('#myDialogeBox').removeClass('hide');
		$(".ui-dialog-titlebar").hide();
		$("#myDialogeBox").dialog("open");
	}
	
	function enableEditMode(n) {
		$('#editBtn' + n).css("display", "none");
		$('#td1' + n).css("display", "none");
		$('#td2' + n).css("display", "");
		//$("#manufactureName" + n).removeAttr("readonly");
		$('#updateBtn' + n).css("display", "");

	}

	function enableUpdateMode(n) {
		$('#updateBtn' + n).css("display", "none");
		$('#td1' + n).css("display", "");
		$('#td2' + n).css("display", "none");
		//$("#manufactureName" + n).attr("readonly", "readonly");
		$('#editBtn' + n).css("display", "");
		updateRequisition(n);
	}

	//function updateWsMatsRequisition(n) {
		//qantityValidation(n);	
	//}
	function updateRequisition (n) {
		var id = $('#pk' + n).val();
		var contextPath = $('#contextPath').val();
		var path = contextPath + '/updateManufactureName.do';

		var cData = {
			id : id,
			manufactureName : $("#manufactureName" + n).val(),
		};
		var cDataJsonString = JSON.stringify(cData);
		$.ajax({
			url : path,
			data : cDataJsonString,
			contentType : "application/json",
			success : function(data) {
				var result = JSON.parse(data);
				if (result == 'success') {					
					$('#td1' + n).text($("#manufactureName" + n).val());					
				} 
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}
	
</script>
<!--

//-->

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
