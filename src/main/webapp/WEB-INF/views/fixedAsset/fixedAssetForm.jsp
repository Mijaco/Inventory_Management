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
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;"></div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Fixed
			Asset Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div>
			<form id="frm" 
				action="${pageContext.request.contextPath}/fixedAssets/saveManualFixedAsset.do"
				method="POST">
				<!-- Master Info :: start -->
				<div class="table-responsive">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td class="col-xs-2 success">Asset Category</td>
								<td class="col-xs-4 info"><select
									class="form-control assetCat" name="assetCat" id="assetCat"
									style="border: 0; border-bottom: 2px ridge;">
										<!-- <option value="">Select</option> -->
										<option value="G">General Material</option>
										<option value="C">Construction Material</option>
								</select></td>
								<td id="col3" style="display: none;" class="col-xs-2 success">completionDate</td>
								<td id="col4" style="display: none;" class="col-xs-4 info">
									<input class="form-control datepicker-15" type="text"
									id="completeDate" name="completeDate" value=""
									style="border: 0; border-bottom: 2px ridge;" />
								</td>
							</tr>
							<tr>
								<td class="col-xs-2 success">Asset Name</td>
								<td id="col1" class="col-xs-4 info"><select
									class="form-control" id="fixedAssetId"
									style="border: 0; border-bottom: 2px ridge;"
									name="fixedAssetId" onChange="getDepr('fixedAssetId')">
										<option value="">Item Name</option>
										<c:if test="${!empty itemList}">
											<c:forEach items="${itemList}" var="item">
												<option value="${item.itemId}">
													<c:out value="${item.itemName}" /></option>
											</c:forEach>
										</c:if>
								</select></td>
								<td id="col2" style="display: none;"><input
									class="form-control" type="text" id="itemId" name="itemId"
									value="" style="border: 0; border-bottom: 2px ridge;" /></td>
								<td class="col-xs-2 success">Asset Type</td>
								<td class="col-xs-4 info"><select
									class="form-control assetType" name="assetType" id="assetType"
									style="border: 0; border-bottom: 2px ridge;">
										<option value="Operating">Operating</option>
										<option value="Non-Operating">Non-Operating</option>
								</select></td>

							</tr>

							<tr>
								<td class="col-xs-2 success">Model Number</td>
								<td class="col-xs-4 info"><input class="form-control"
									type="text" id="modelNumber" name="modelNumber" value=""
									style="border: 0; border-bottom: 2px ridge;" /></td>
								<td class="col-xs-2 success">Purchase Date/Installation Date</td>
								<td class="col-xs-4 info"><input
									class="form-control datepicker-15" type="text"
									id="purchaseDate" name="purchaseDate" value=""
									style="border: 0; border-bottom: 2px ridge;" /></td>
							</tr>
							<tr>
								<td class="col-xs-2 success">Serial Number</td>
								<td class="col-xs-4 info"><input class="form-control"
									type="text" id="serialNumber" name="serialNumber" value=""
									style="border: 0; border-bottom: 2px ridge;" /></td>
								<td class="col-xs-2 success">Purchase Price/Unit Cost</td>
								<td class="col-xs-4 info">
									<!--  --> <input type="text" id="purchasePrice"
									name="purchasePrice" class="form-control" value="" onBlur="setTotal(this.value)"
									style="border: 0; border-bottom: 2px ridge;" />
								</td>
							</tr>

							<tr>
								<td class="col-xs-2 success">Brand Name</td>
								<td class="col-xs-4 info"><input type="text"
									class="form-control" id="brandName"
									style="border: 0; border-bottom: 2px ridge;" name="brandName"
									value="" /></td>
								<td class="col-xs-2 success">Quantity / Measurement</td>
								<td class="col-xs-4 info"><input type="text" id="quantity"
									style="border: 0; border-bottom: 2px ridge;" name="quantity"
									value="" onBlur="setTotalCost()" /> &emsp;Installation
									Charge&emsp;<input type="text" id="installationCharge"
									style="border: 0; border-bottom: 2px ridge;"
									name="installationCharge" value="" /></td>

								
							</tr>

							<tr>
								<td class="col-xs-2 success">Vandor Name</td>
								<td class="col-xs-4 info"><input type="text"
									class="form-control" id="vandorName"
									style="border: 0; border-bottom: 2px ridge;" name="vandorName"
									value="" /></td>
								<!-- <td class="col-xs-2 success">Division Name</td>
						<td class="col-xs-4 info"><input
								type="text" id="divisionName" name="divisionName"
								value="" /></td> -->
								<td class="col-xs-2 success">Total Price</td>
								<td class="col-xs-4 info"><input class="form-control"
									type="currency"  id="totalPrice" name="totalPrice" value=""
									style="border: 0; border-bottom: 2px ridge;"
									readonly="readonly" /></td>
							</tr>
							<tr>
								<td class="col-xs-2 success">Location Name</td>
								<td class="col-xs-4 info">
								<%-- <select class="form-control"
									name="locationId" id="locationId"
									style="border: 0; border-bottom: 2px ridge;">
										<option value="">Select</option>
										<c:if test="${!empty departments}">
											<c:forEach items="${departments}" var="department">
												<option value="${department.deptId}">
													<c:out value="${department.deptName}" /></option>
											</c:forEach>
										</c:if>
								</select> --%>
								<input
									class="form-control" type="text" id="locationId" name="locationId"
									value="" style="border: 0; border-bottom: 2px ridge;" />
								</td>
								<td class="col-xs-2 success">Zone</td>
								<td class="col-xs-4 info"><input class="form-control"
									type="text" id="zone" name="zone" value=""
									style="border: 0; border-bottom: 2px ridge;" /></td>
							</tr>
							<tr>
								<td class="col-xs-2 success">Life Time</td>
								<td class="col-xs-4 info"><input class="form-control"
									type="text" id="lifeTime" name="lifeTime"
									onBlur="calcDepRate()" value=""
									style="border: 0; border-bottom: 2px ridge;" /></td>
								<td class="col-xs-2 success">Depreciation Rate %</td>
								<td class="col-xs-4 info"><input type="text" id="depreciationRate"
									style="border: 0; border-bottom: 2px ridge;"
									name="depreciationRate" value="" />
								
									 &emsp;Color &emsp;
								<input type="text" id="color" name="color" value=""
									style="border: 0; border-bottom: 2px ridge;" />
									</td>
							</tr>

							<tr class="info">
								<td class="col-xs-2 success" align="center" colspan="4">
								<!--   Non-Operating  -->
								<div id="nonOperating" >
									<input type="radio" id="opt" name="opt" value="vehicle"> <b>Motor Vehicles</b>
									<input type="radio" id="opt" name="opt" value="furniture"><b>Furniture Fixtures</b>
									<input type="radio" id="opt" name="opt" value="communication_equipments"> <b>Communication Equipments</b>
									<input type="radio" id="opt" name="opt" value="computer_peripharels"><b>Computer & Peripharels</b>
									<input type="radio" id="opt" name="opt" value="electric_equipments"> <b>Electric Equipments</b>
									<input type="radio" id="opt" name="opt" value="maintenance_equipments"><b>Maintenance Equipments</b>
									</div>
									<!--  Operating  -->
									<div id="operating">
									<input type="radio" id="opt" name="opt" value="land"> <b>Land</b>
									<input type="radio" id="opt" name="opt" value="building"> <b>Building</b>
									<input type="radio" id="opt" name="opt" value="distribution_line"><b>Distribution Line</b>
									<input type="radio" id="opt" name="opt" value="distribution_equipment"> <b>Distribution Equipment</b>
									</div>
									</td>
							</tr>

							<tr id="row1" style="display: none;">
								<td class="col-xs-2 success">Registration No.</td>
								<td class="col-xs-4 info"><input class="form-control"
									type="text" id="regNo" name="regNo" value=""
									style="border: 0; border-bottom: 2px ridge;" /></td>
								<td class="col-xs-2 success">CC</td>
								<td class="col-xs-4 info">
									<!--  --> <input type="text" id="cc" name="cc" value=""
									style="border: 0; border-bottom: 2px ridge;" />
									&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Seats
									&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="seats"
									name="seats" value=""
									style="border: 0; border-bottom: 2px ridge;" />
								</td>
							</tr>

							<tr id="row2" style="display: none;">
								<td class="col-xs-2 success">Engine No</td>
								<td class="col-xs-4 info"><input type="text"
									class="form-control" id="engineNo"
									style="border: 0; border-bottom: 2px ridge;" name="engineNo"
									value="" /></td>
								<td class="col-xs-2 success">Chacis No</td>
								<td class="col-xs-4 info"><input type="text"
									class="form-control" id="chacisNo"
									style="border: 0; border-bottom: 2px ridge;" name="chacisNo"
									value="" /></td>
							</tr>


							<!-- for Land -->
							<tr id="row3" style="display: none;">
								<td class="col-xs-2 success">Division Name</td>
								<td class="col-xs-4 info"><input class="form-control"
									type="text" id="landDivision"
									style="border: 0; border-bottom: 2px ridge;"
									name="landDivision" value="" /></td>
								<td class="col-xs-2 success">Khatian No</td>
								<td class="col-xs-4 info">
									<!--  --> <input class="form-control" type="text"
									id="khatianNo" style="border: 0; border-bottom: 2px ridge;"
									name="khatianNo" value="" />
								</td>
							</tr>
							<!-- for Building -->
							<tr id="row4" style="display: none;">
								<td class="col-xs-2 success">Division Name</td>
								<td class="col-xs-4 info"><input class="form-control"
									type="text" id="buildingDivision"
									style="border: 0; border-bottom: 2px ridge;"
									name="buildingDivision" value="" /></td>
								<td class="col-xs-2 success"></td>
								<td class="col-xs-4 info">
									<!--  --> 
								</td>
							</tr>
					<!-- for Distribution Equipment -->
							 <tr id="row5" style="display:none;">
						<td class="col-xs-2 success">Sub Category</td>
						<td class="col-xs-4 info">
						<select
									class="form-control subCategory" name="disEqpSubCategory" id="disEqpSubCategory"
									style="border: 0; border-bottom: 2px ridge;" onChange="meter(this.value)">
									<option value="">Select</option>
										<option value="Grid Sub-Station">Grid Sub-Station</option>
										<option value="Sub-Station">Sub-Station</option>
										<option value="Switching-Station">Switching-Station</option>
										<option value="Transformer">Transformer</option>
										<option value="Meter">Meter</option>
										<option value="Tools & Equipment">Tools & Equipment</option>
								</select></td>
						<td class="col-xs-2 success">Project Name</td>
						<td class="col-xs-4 info"><input type="text" class="form-control" id="projectName"
								style="border: 0; border-bottom: 2px ridge;" name="projectName"
								value="" /></td>
						</tr>  
						<tr id="row6" style="display: none;">
								<td class="col-xs-2 success">Division Name</td>
								<td class="col-xs-4 info"><input class="form-control"
									type="text" id="disEqpDivision"
									style="border: 0; border-bottom: 2px ridge;"
									name="disEqpDivision" value="" /></td>
								<td class="col-xs-2 success">Rating</td>
								<td class="col-xs-4 info">
									<input class="form-control"
									type="text" id="rating"
									style="border: 0; border-bottom: 2px ridge;"
									name="rating" value="" />
								</td>
							</tr>
							<tr id="row9" style="display: none;">
							<td class="col-xs-2 success">Tariff Category</td>
						<td class="col-xs-4 info">
						<select
									class="form-control tariffCategory" name="tariffCategory" id="tariffCategory"
									style="border: 0; border-bottom: 2px ridge;">
									<option value="">Select</option>
										<option value="Domestic-A">Domestic-A</option>
										<option value="Non-Residential-D">Non-Residential-D</option>
										<option value="Commercial and Office-E">Commercial and Office-E</option>
										<option value="Constution-2E">Constution-2E</option>
										<option value="Agriculture Pump-B">Agriculture Pump-B</option>
										<option value="Small Industries-C">Small Industries-C</option>
										
										<option value="Medium Voltage-F">Medium Voltage-F</option>
										<option value="High Voltage-H">High Voltage-H</option>
										<option value="REB and PBS (High Voltage)-I">REB and PBS (High Voltage)-I</option>
										<option value="Street Light and Pump-J">Street Light and Pump-J</option>
								</select></td>
						<td class="col-xs-2 success">Meter Type</td>
						<td class="col-xs-4 info">
<select
									class="form-control meterType" name="meterType" id="meterType"
									style="border: 0; border-bottom: 2px ridge;">
									<option value="">Select</option>
										<option value="Single Phase">Single Phase</option>
										<option value="Three Phase">Three Phase</option>
								</select>						
</td>
						</tr>
							 <!--  --> 
						<!-- for Distribution Line -->
						
							<tr id="row7" style="display: none;">
								<td class="col-xs-2 success">Division Name</td>
								<td class="col-xs-4 info"><input class="form-control"
									type="text" id="disLineDivision"
									style="border: 0; border-bottom: 2px ridge;"
									name="disLineDivision" value="" /></td>
								<td class="col-xs-2 success">Sub Category</td>
								<td class="col-xs-4 info">
								<select
									class="form-control subCategory" name="disLineSubCategory" id="disLineSubCategory"
									style="border: 0; border-bottom: 2px ridge;" onChange="getAdditionLength(this.value)">
									<option value="">Select</option>
										<option value="11 KV Under Ground Cable Line">11 KV Under Ground Cable Line</option>
										<option value="33 KV Under Ground Cable Line">33 KV Under Ground Cable Line</option>
										<option value=".4 KV Overhead Line">.4 KV Overhead Line</option>
										<option value="11 KV Overhead Line">11 KV Overhead Line</option>
										<option value="33 KV Overhead Line">33 KV Overhead Line</option>
								</select>
								</td>
								
							</tr>
							<tr id="row8" style="display: none;">
								<td class="col-xs-2 success">Opening Length</td>
								<td class="col-xs-4 info"><input class="form-control"
									type="text" id="openingLength"
									style="border: 0; border-bottom: 2px ridge;"
									name="openingLength" value="" readonly="readonly" /></td>
								<td class="col-xs-2 success">Addition Length</td>
								<td class="col-xs-4 info"><input class="form-control"
									type="text" id="additionLength"
									style="border: 0; border-bottom: 2px ridge;"
									name="additionLength" value="" />
								</td>
									
							</tr>
						 <!--  --> 
							<tr>

								<td class="col-xs-2 success">Remarks</td>
								<td class="col-xs-4 info"><input type="text"
									class="form-control" id="remarks"
									style="border: 0; border-bottom: 2px ridge;" name="remarks"
									value="" /></td>
								<td class="col-xs-2 success"></td>
								<td class="col-xs-4 info"></td>
							</tr>
							<tr>
								<td class="col-xs-2 info"></td>
								<td class="col-xs-4 info"></td>
								<td class="col-xs-2 info"></td>
								<td class="col-xs-4 info">
									<div class="" id="submit">
										<button type="submit"
											style="margin-right: 10px; border-radius: 6px;"
											class="width-20 pull-left btn btn-lg btn-success">
											<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
										</button>
									</div>
									<div class="" id="reset">
										<button type="reset"
											class="width-20 pull-left btn btn-lg btn-danger"
											style="margin-left: 10px; border-radius: 6px;">
											<i class="ace-icon fa fa-refresh"></i> <span
												class="bigger-50">Reset</span>
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

 function meter(m){
	
	if(m=="Meter")
		{
		$("#row9").show();
		}else{
			$("#row9").hide();
		}
} 

/* $(document).ready(function() {
	$("disEqpSubCategory").change(function() {
		alert($(this).val());
		if ($(this).val() == "Meter") {
			
			$("#row9").show();
		}else{
			$("#row9").hide();
		}
	});
}); */
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("input:radio").click(function() {
			if ($(this).val() == "vehicle") {
				$("#row3").hide();
				 $("#row4").hide();
				$("#row1").show();
				$("#row2").show();
				$("#row5").hide();
				$("#row6").hide();
				$("#row7").hide();
				$("#row8").hide();
			} else if ($(this).val() == "land") {
				$("#row1").hide();
				$("#row2").hide();
				$("#row3").show();
				$("#row4").hide();
				$("#row5").hide();
				$("#row6").hide();
				$("#row7").hide();
				$("#row8").hide();
				/*  $("#row4").show(); */
			} else if ($(this).val() == "building") {
				$("#row1").hide();
				$("#row2").hide();
				$("#row4").show();
				$("#row3").hide();
				$("#row5").hide();
				$("#row6").hide();
				$("#row7").hide();
				$("#row8").hide();
				/*  $("#row4").show(); */
			}else if ($(this).val() == "distribution_equipment") {
				$("#row1").hide();
				$("#row2").hide();
				$("#row3").hide();
				$("#row4").hide();
				$("#row5").show();
				$("#row6").show();
				$("#row7").hide();
				$("#row8").hide();
				/*  $("#row4").show(); */
			}else if ($(this).val() == "distribution_line") {
				$("#row1").hide();
				$("#row2").hide();
				$("#row3").hide();
				$("#row4").hide();
				$("#row5").hide();
				$("#row6").hide();
				$("#row7").show();
				$("#row8").show();
				/*  $("#row4").show(); */
			}else if ($(this).val() == "furniture" || $(this).val() == "communication_equipments" || $(this).val() == "computer_peripharels" || $(this).val() == "electric_equipments" || $(this).val() == "maintenance_equipments") {
				$("#row1").hide();
				$("#row2").hide();
				$("#row3").hide();
				$("#row4").hide();
				$("#row5").hide();
				$("#row6").hide();
				$("#row7").hide();
				$("#row8").hide();
				/* $("#row4").hide(); */
			}
		});
	});

	$(document).ready(function() {
		$("#assetCat").change(function() {
			if ($(this).val() == "G") {
				$("#col2").hide();
				$("#col3").hide();
				$("#col4").hide();

				$("#col1").show();
			} else if ($(this).val() == "C") {

				$("#col1").hide();
				$("#col2").show();
				$("#col3").show();
				$("#col4").show();

			}
		});
	});
</script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {

						$('#submit').click(function() {
							return validateForm();
						});

						function validateForm() {

							var fixedAssetName = $('#fixedAssetName').val();
							var purchaseDate = $('#purchaseDate').val();
							var purchasePrice = $('#purchasePrice').val();
							var quantity = $('#quantity').val();
							var totalPrice = $('#totalPrice').val();
							var locationId = $('#locationId').val();

							var regNo = $('#regNo').val();
							var cc = $('#cc').val();
							var seats = $('#seats').val();
							var engineNo = $('#engineNo').val();
							var chacisNo = $('#chacisNo').val();

							var divisionName = $('#divisionName').val();
							var khatianNo = $('#khatianNo').val();

							var lifeTime = $('#lifeTime').val();
							var depreciationRate = $('#depreciationRate').val();
							/* var measurement = $('#measurement').val(); */

							//
							//alert(rowCount);
							var inputVal = new Array(fixedAssetName,
									purchaseDate, purchasePrice, locationId,
									lifeTime, depreciationRate, regNo,
									cc, seats, engineNo, chacisNo, quantity,
									totalPrice, divisionName, khatianNo/* , measurement */);

							var inputMessage = new Array("Fixed Asset Name",
									"Purchase Date", "Purchase Price",
									"location Id", "Life Time",
									"Depreciation Rate", "Registration No",
									"CC", "seats", "engineNo", "chacisNo",
									"Quantity", "Total Price", "divisionName",
									"khatianNo"/* , "measurement" */);

							$('.error').hide();

							if (inputVal[0] == "") {
								$('#fixedAssetName').after(
										'<span class="error" style="color:red"> Please enter '
												+ inputMessage[0] + '</span>');
								return false;
							}
							/* if (inputVal[1] == "") {
								$('#purchaseDate').after(
										'<span class="error" style="color:red"> Please enter '
												+ inputMessage[1] + '</span>');
								return false;
							} */
							if (inputVal[2] == "" || inputVal[2] == null) {
								$('#purchasePrice').after(
										'<span class="error" style="color:red"> Please enter '
												+ inputMessage[2] + '</span>');
								return false;
							}
							if (inputVal[3] == "") {
								$('#locationId').after(
										'<span class="error" style="color:red"> Please enter '
												+ inputMessage[3] + '</span>');
								return false;
							}
							if (inputVal[4] == "") {
								$('#lifeTime').after(
										'<span class="error" style="color:red"> Please enter '
												+ inputMessage[4] + '</span>');
								return false;
							}
							if (inputVal[5] == "") {
								$('#depreciationRate').after(
										'<span class="error" style="color:red"> Please enter '
												+ inputMessage[5] + '</span>');
								return false;
							}
							if (document.getElementById("opt").checked == true) {

								if (document.getElementById("opt").value == "vehicle") {

									if (inputVal[6] == "") {
										$('#regNo').after(
												'<span class="error" style="color:red"> Please enter '
														+ inputMessage[6]
														+ '</span>');
										return false;
									}

									if (inputVal[7] == "") {
										$('#cc').after(
												'<span class="error" style="color:red"> Please enter '
														+ inputMessage[7]
														+ '</span>');
										return false;
									}
									if (inputVal[8] == "") {
										$('#seats').after(
												'<span class="error" style="color:red"> Please enter '
														+ inputMessage[8]
														+ '</span>');
										return false;
									}
									if (inputVal[9] == "") {
										$('#engineNo').after(
												'<span class="error" style="color:red"> Please enter '
														+ inputMessage[9]
														+ '</span>');
										return false;
									}
									if (inputVal[10] == "") {
										$('#chacisNo').after(
												'<span class="error" style="color:red"> Please enter '
														+ inputMessage[10]
														+ '</span>');
										return false;
									}
								}
							}

							// if(document.getElementById("opt").checked == true){	
							// if(document.getElementById("opt").value == "land") {
							// alert(document.getElementById("opt").value);
							/* if (inputVal[9] == "") {
								$('#divisionName').after(
										'<span class="error" style="color:red"> Please enter '
												+ inputMessage[9] + '</span>');
								return false;
							}
							 if (inputVal[10] == "") {
								$('#khatianNo').after(
										'<span class="error" style="color:red"> Please enter '
												+ inputMessage[10] + '</span>');
								return false;
							} */
							/* if (inputVal[11] == "") {
								$('#measurement').after(
										'<span class="error" style="color:red"> Please enter '
												+ inputMessage[11] + '</span>');
								return false;
							}  */
							//	} 
							//	} 
							if (inputVal[11] == "") {
								$('#quantity').after(
										'<span class="error" style="color:red"> Please enter '
												+ inputMessage[11] + '</span>');
								return false;
							}
							if (inputVal[12] == "") {
								$('#totalPrice').after(
										'<span class="error" style="color:red"> Please enter '
												+ inputMessage[12] + '</span>');
								return false;
							}
							return true;
						}
					});
	
	function setTotal(value) {

		$('#quantity').val(1);
		$('#totalPrice').val(value);

	}

	function setTotalCost() {

		var purchasePrice = $('#purchasePrice').val().trim();
		var purchasePriceFloat = parseFloat(purchasePrice).toFixed(2);
		var quantity = $('#quantity').val().trim();
		var quantityFloat = parseFloat(quantity).toFixed(2);
		var totalCost = (purchasePriceFloat * quantityFloat).toFixed(2);
		$('#totalPrice').val(totalCost);

	}
	function calcDepRate() {

		var purchasePrice = $('#purchasePrice').val().trim();
		var purchasePriceFloat = parseFloat(purchasePrice).toFixed(2);
		var lifeTime = $('#lifeTime').val().trim();
		var lifeTimeFloat = parseFloat(lifeTime).toFixed(2);
		var depValue = purchasePriceFloat / lifeTimeFloat;
		var depRate = (depValue * 100) / purchasePriceFloat;

		$('#depreciationRate').val(depRate.toFixed(3));

	}
	
	function calcLifeTime() {
		var purchasePrice = $('#purchasePrice').val().trim();
		var purchasePriceFloat = parseFloat(purchasePrice).toFixed(2);
		var depRate = $('#depreciationRate').val().trim();
		var depRateFloat = parseFloat(depRate).toFixed(2);
		var depValue = (purchasePriceFloat * depRateFloat) / 100;
		var lifeTimeValue = purchasePriceFloat / depValue;
		$('#lifeTime').val(lifeTimeValue);

	}
	
	
	function getDepr(id) {
		var itemId = document.getElementById(id).value;

		$.ajax({
			type : "post",
			url : 'getDepr.do',
			async : false,
			data : 'itemId=' + itemId,
			success : function(response) {
				var s = response.split(",");

				$("#lifeTime").val(s[0]);
				$("#depreciationRate").val(s[1]);
			},
			error : function() {
			}
		});

		return true;
	}
	function getAdditionLength(value){
		alert(value);
		var purchaseDate = document.getElementById('purchaseDate').value;
		var disLineDivision = document.getElementById('disLineDivision').value;
		//alert(value+"===="+purchaseDate);
		$.ajax({
			type : "post",
			url : 'getAdditionLength.do',
			async : false,
			data : 'distributionLine=' + value + '&purchaseDate=' + purchaseDate + '&disLineDivision=' + disLineDivision,
			success : function(response) {
				//var s = response.split(",");
//alert(response);
				$("#additionLength").val(response);
			},
			error : function() {
			}
		});

		return true;		
	}
	
	
	
	$(document).ready(calFact);
	function calFact(){
		$("#operating").show();
		$("#nonOperating").hide();	
	}
	
	/*  $(document).ready(function() {
			$("#frm").onload(function() {
					$("#operating").hide();
					$("#nonOperating").hide();
			});
		}); */
	 
	 $(document).ready(function() {
			$("#assetType").change(function() {
				if ($(this).val() == "Operating") {
					$("#nonOperating").hide();
					$("#operating").show();
				} else if ($(this).val() == "Non-Operating") {

					$("#operating").hide();
					$("#nonOperating").show();

				}
			});
		});
	 
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$(function() {
			$("#itemId").autocomplete({
				source : function(request, response) {
					$.ajax({
						url : 'getItemName.do',
						type : "POST",
						data : {
							itemName : request.term
						},
						dataType : "json",
						success : function(data) {
							response($.map(data, function(v, i) {
								return {
									label : v.itemName,
									value : v.itemName
								};
							}));
						}

					});

				},
				select : function(event, ui) {
					getItemData(ui.item.label);
				},
				minLength : 1
			});
		});
	});
	
	$(document).ready(function() {
		$(function() {
			$("#locationId").autocomplete({
				source : function(request, response) {
					$.ajax({
						url : 'getLocationName.do',
						type : "POST",
						data : {
							locationName : request.term
						},
						dataType : "json",
						success : function(data) {
							response($.map(data, function(v, i) {
								return {
									label : v.deptName,
									value : v.deptName
								};
							}));
						}

					});

				},
				/* select : function(event, ui) {
					getItemData(ui.item.label);
				}, */
				minLength : 1
			});
		});
	});
	
	function getItemData(itemName) {
		//alert(itemName);
		$.ajax({
			type : "post",
			url : 'getItemData.do',
			async : false,
			data : 'itemName=' + itemName,
			success : function(response) {
				var s = response.split(",");
				$("#lifeTime").val(s[0]);
				$("#depreciationRate").val(s[1]);
			},
			error : function() {
			}
		});

		return true;
	}
</script>
<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-2.0.2.min.js"></script> -->
<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/js/fixedAssets/jquery-2.0.2.min.js"></script>
	--%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/js/fixedAssets/jquery.editable.select.min.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>