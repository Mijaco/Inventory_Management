<%@include file="../common/faHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->


<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
}

.ui-widget-overlay {
	opacity: .6 !important;
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
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Fixed
			Asset Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
	<div>
		<form
			action="${pageContext.request.contextPath}/fixedAssets/saveDepPolicy.do" method="POST">
			<!-- Master Info :: start -->
			<div class="table-responsive">
				<table class="table table-bordered">
					<tbody>
						 <tr>
							<td class="col-xs-2 success">Depreciation Method</td>
							<td class="col-xs-4 info">
							<select class="form-control depreciationMethod" name="depreciationMethod"
								id="depreciationMethod" style="border: 0; border-bottom: 2px ridge;">
								<option value="Straight-Line">Straight Line</option>
								<option value="Non-Depreciable">Non-Depreciable</option>
								<option value="Declining-Balance">Declining Balance</option>
							</select>
							</td>
							<td class="col-xs-2 success">Depreciation Rate (%)</td>
							 <td class="col-xs-4 info"><input
								type="number" id="depreciationRate" name="depreciationRate" class="form-control"
								value="" style="border: 0; border-bottom: 2px ridge;"/>
								</td> 

						</tr> 

				 <tr>
				 
						<td class="col-xs-2 success">Asset Name</td>
						<td class="col-xs-4 info">
								<select class="form-control" id="assetCode"
								style="border: 0; border-bottom: 2px ridge;" name="assetCode">
																		<option value="">Asset Name</option>
																		<c:if test="${!empty fixedAssetList}">
																			<c:forEach items="${fixedAssetList}" var="fixedAsset">
																				<option value="${fixedAsset.fixedAssetId}">
																					<c:out value="${fixedAsset.fixedAssetName}" /></option>
																			</c:forEach>
																		</c:if>
																	</select>
						</td>
						<td class="col-xs-2 success">Life Time</td>
						<td class="col-xs-4 info"><input class="form-control"
								type="number" id="lifeTime" name="lifeTime"
								value="" style="border: 0; border-bottom: 2px ridge;"/></td>
						</tr>
					<tr>
						<td class="col-xs-2 success">Item Name</td>
						<td class="col-xs-4 info">
							<select class="form-control" id="itemCode"
								style="border: 0; border-bottom: 2px ridge;" name="itemCode">
																		<option value="">Item Name</option>
																		<c:if test="${!empty itemList}">
																			<c:forEach items="${itemList}" var="item">
																				<option value="${item.itemId}">
																					<c:out value="${item.itemName}" /></option>
																			</c:forEach>
																		</c:if>
																	</select></td>
						 <td class="col-xs-2 success">Remarks</td>
						<td class="col-xs-4 info"><input type="text" class="form-control" id="remarks"
								style="border: 0; border-bottom: 2px ridge;" name="remarks"
								value=""/>
							<!--	<select id="sessionStart" name="sessionStart">
  <script>
  var myDate = new Date();
  var year = myDate.getFullYear();
  for(var i = 2000; i < year+1; i++){
	  document.write('<option value="'+i+'">'+i+'</option>');
  }
  </script>
</select>
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;End Session
<select id="sessionEnd" name="sessionEnd">
  <script>
  var myDate = new Date();
  var year = myDate.getFullYear();
  for(var i = 2000; i < year+11; i++){
	  document.write('<option value="'+i+'">'+i+'</option>');
  }
  </script>
</select>
								 --></td>
						</tr>
					
						
						<tr>
						<td class="col-xs-2 info"></td>
						<td class="col-xs-4 info"></td>
						<td class="col-xs-2 info"></td>
						<td class="col-xs-4 info">
						<div class="" id="submit">
							<button type="submit" style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-left btn btn-lg btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
							</button>
						</div>
						<div class="" id="reset">
							<button type="reset"
							class="width-20 pull-left btn btn-lg btn-danger"
							style="margin-left: 10px; border-radius: 6px;">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
							</button>
						</div>
					</td>
						
						</tr> 
						<!--  -->
					</tbody>
				</table>
			</div>
			<!-- Master Info :: end -->
		</form>
	</div>
	</div>
</div>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
	
</script>
<script type="text/javascript"> 

$(document).ready(
		function() {

			$('#assetCode').on('change', function() {
				if($('#assetCode').val()!=""){
				document.getElementById("itemCode").disabled = true;
				}else{document.getElementById("itemCode").disabled = false;}
			});
		});
		
</script>

<script type="text/javascript"> 

$(document).ready(
		function() {
			
			$('#itemCode').on('change', function() {
				if($('#itemCode').val()!=""){
				document.getElementById("assetCode").disabled = true;
				}else{document.getElementById("assetCode").disabled = false;}
			});
		});
</script>
<script type="text/javascript"> 

$(document).ready(
function() {

$('#submit').click(function() {
	return validateForm();
});

function validateForm() {
	
	var depreciationMethod = $('#depreciationMethod').val();
	var depreciationRate = $('#depreciationRate').val();
	var lifeTime = $('#lifeTime').val();
	var sessionStart = $('#sessionStart').val();
	var sessionEnd = $('#sessionEnd').val();
	
	
	 
	
	var inputVal = new Array(depreciationMethod, depreciationRate, lifeTime, sessionStart, sessionEnd);

	var inputMessage = new Array("Depreciation Method", "Depreciation Rate", "Session Start", "Session End", "Life Time");

	$('.error').hide();

	if (inputVal[0] == "") {
		$('#depreciationMethod').after(
				'<span class="error" style="color:red"> Please enter '
						+ inputMessage[0] + '</span>');
		return false;
	}
	if (inputVal[1] == "") {
		$('#depreciationRate').after(
				'<span class="error" style="color:red"> Please enter '
						+ inputMessage[1] + '</span>');
		return false;
	}
	if (inputVal[2] == "") {
		$('#lifeTime').after(
				'<span class="error" style="color:red"> Please enter '
						+ inputMessage[2] + '</span>');
		return false;
	}
	if (inputVal[3] == "") {
		$('#sessionStart').after(
				'<span class="error" style="color:red"> Please enter '
						+ inputMessage[3] + '</span>');
		return false;
	}
	if (inputVal[4] == "") {
		$('#sessionEnd').after(
				'<span class="error" style="color:red"> Please enter '
						+ inputMessage[4] + '</span>');
		return false;
	}

	return true;
}
}); 
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>