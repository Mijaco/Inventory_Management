<%@include file="../common/faHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->


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
			
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Fixed Asset List</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">

			<c:if test="${empty fixedAssetReceives}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<input type='hidden' value='${pageContext.request.contextPath}'
				id='contextPath'>
			<c:if test="${!empty fixedAssetReceives}">
				<table id="inventoryLookupItemTable"
					class="table table-striped table-hover table-bordered">
					<thead>

						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							
							<th>Item Id</th>
							<th>Item Name</th>
							<th>Life Time</th>
							<th>Depreciation Rate</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach items="${fixedAssetReceives}"
							var="fixedAssetReceive" varStatus="status">
							<tr>
								 
								<td><a onclick="redirectURL('${fixedAssetReceive.itemId}')"
									href="javascript:void(0)"
									style="text-decoration: none;">${fixedAssetReceive.itemId}</a></td>
								<td>${fixedAssetReceive.itemName}<input type="hidden"
									value="${fixedAssetReceive.itemId}" id="itemId${status.index}" /></td>
								<td>${fixedAssetReceive.itemMaster.lifeTime}<input type="hidden"
									value="${fixedAssetReceive.itemMaster.lifeTime}" id="lifeTime${status.index}" /></td>
								<td>${fixedAssetReceive.itemMaster.depreciationRate}<input type="hidden"
									value="${fixedAssetReceive.itemMaster.depreciationRate}" id="depreciationRate${status.index}" /></td>
								<td>
								<button type="button" title="Edit"
										onClick="openDialoge('itemId${status.index}','lifeTime${status.index}','depreciationRate${status.index}')">
										<span class="fa fa-pencil-square-o fa-2x blue"></span>
									</button>
								</td>
							</tr>


						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>
</div>
<div id="myDialogeBox" class="hide">
	<div>
		<form
			action="${pageContext.request.contextPath}/fixedAssets/editDepRate.do"
			id="editForm" method="POST">
			<!-- Master Info :: start -->
			<div class="table-responsive">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="col-xs-2 success">Life Time</td>
							<td class="col-xs-4 info"><input type="hidden" value=""
								id="assetId" name="itemId" readonly="readonly"/>
								 
								<input type="text"
								class="form-control" id="lifeTime"
								style="border: 0; border-bottom: 2px ridge;"
								name="lifeTime" value="" step="0.001" readonly="readonly" /> 
								 </td>

							<td class="col-xs-2 success">Depreciation Rate</td>
							<td class="col-xs-4 info"><input type="text"
								class="form-control" id="depRate"
								style="border: 0; border-bottom: 2px ridge;"
								name="depreciationRate" value="" step="0.001" /></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Master Info :: end -->
		</form>
	</div>
</div>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
	
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
			width : $(window).width() - 600,
			height : 200,
			buttons : [ {
				text : 'Save',
				click : function() {
					// send post request
					$("#editForm").submit();
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

	function openDialoge(id, lifeTime, depRate) {
		var idd = document.getElementById(id).value;
		$('#assetId').val(idd);
		var lifeTimed = document.getElementById(lifeTime).value;
		$('#lifeTime').val(lifeTimed);
		var depRated = document.getElementById(depRate).value;
		$('#depRate').val(depRated);
		$('#myDialogeBox').removeClass('hide');
		$(".ui-dialog-titlebar").hide();
		$("#myDialogeBox").dialog("open");
	}

</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>
<script>
$(document)
	.ready(
		function() {
			$('#inventoryLookupItemTable').DataTable({
				
				"order" : [ [ 2, 'desc' ] ]
			});
			document.getElementById('inventoryLookupItemTable_length').style.display = 'none';
			//document.getElementById('inventoryLookupItemTable_filter').style.display = 'none';
			
			
			$(".alert.alert-success").fadeTo(4000, .5).slideUp(500, function() {
				  //  $(".alert.alert-success").alert('close');
			});
			
			$(".alert.alert-danger").fadeTo(4000, .5).slideUp(500, function() {
				  //  $(".alert.alert-danger").alert('close');
			});
		});
		
function redirectURL(id) {
	var baseURL = $('#contextPath').val();
	var path = baseURL + "/fixedAssets/depShow.do";
	var param = {
			'itemId' : id
	}
	postSubmit(path, param, "POST");
}
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>