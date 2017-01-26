<%@include file="../common/adminheader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<!-- @author: Ihteshamul Alam, IBCS -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/settings/list/storeLocation.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Store Location List
			</a>
<!-- 			<button accesskey="D" class="btn btn-info btn-sm" type="button"> Discard </button> -->

			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				New Store Location Entry Form</h1>		
		</div>		
		
	</div>
	
	<div class="container">
			<div class="col-sm-8 col-sm-offset-2" style="background-color: white; padding: 10px; padding-left: 20px; 
			margin-top: 10px; margin-bottom: 10px;">
		
		<!-- --------------------- -->
		<form role="form" method="post" action="${pageContext.request.contextPath}/settings/add/newStoreLocationSave.do">
			<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
			<input type="hidden" id="parentId" name="parentId" value="0">
			<div class="col-sm-12 table-responsive">
				<table class="table table-hover table-bordered">
					<tbody>
						<tr>
							<td class="col-sm-3 success" align="right" style="font-weight: bold;">Store Code:&nbsp;<strong class='red'>*</strong></td>
							<td class="col-sm-7">
								<select class="form-control" style="border: 0; border-bottom: 1px solid #000;" name="storeCode" id="storeCode" required>
									<option disabled selected>Select Store Code</option>
									<option value="CS">Central Store</option>
									<option value="SS">Sub Store</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="col-sm-3 success" align="right" style="font-weight: bold;">Parent Location:</td>
							<td class="col-sm-7">
								<select class="form-control" style="border: 0; border-bottom: 1px solid #000;" name="parentLocation" id="parentLocation">
									<option value="0">Select Parent Location</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="col-sm-3 success" align="right" style="font-weight: bold;">Godown:</td>
							<td class="col-sm-7">
								<select class="form-control" style="border: 0; border-bottom: 1px solid #000;" name="godown" id="godown">
									<option value="0">Select Godown</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="col-sm-3 success" align="right" style="font-weight: bold;">Floor:</td>
							<td class="col-sm-7">
								<select class="form-control" style="border: 0; border-bottom: 1px solid #000;" name="floor" id="floor">
									<option value="0">Select Floor</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="col-sm-3 success" align="right" style="font-weight: bold;">Block:</td>
							<td class="col-sm-7">
								<select class="form-control" style="border: 0; border-bottom: 1px solid #000;" name="block" id="block">
									<option value="0">Select Block</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="col-sm-3 success" align="right" style="font-weight: bold;">Rack:</td>
							<td class="col-sm-7">
								<select class="form-control" style="border: 0; border-bottom: 1px solid #000;" name="rack" id="rack">
									<option value="0">Select Rack</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="col-sm-3 success" align="right" style="font-weight: bold;">Location Name:</td>
							<td class="col-sm-7">
								<input type="text" class="form-control" style="border: 0; border-bottom: 1px solid #000;" name="name" id="name" required="required">
							</td>
						</tr>
						<tr>
							<td class="col-sm-3 success" align="right" style="font-weight: bold;">Address:</td>
							<td class="col-sm-7">
								<input type="text" class="form-control" style="border: 0; border-bottom: 1px solid #000;" name="description" id="description" required="required">
							</td>
						</tr>
						<tr>
							<td class="col-sm-3 success" align="right" style="font-weight: bold;">Remarks:</td>
							<td class="col-sm-7">
								<input type="text" class="form-control" style="border: 0; border-bottom: 1px solid #000;" name="remarks" id="remarks">
							</td>
						</tr>
					</tbody>
				</table>
				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="button" id="saveButton"
							style="margin-right: 10px; border-radius: 6px;"
							class="pull-right btn btn-lg btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="reset" class="pull-left btn btn-lg btn-danger"
							style="margin-left: 10px; border-radius: 6px;">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button>
					</div>
				</div>
			</div>
		</form>
		<!-- --------------------- -->		
	</div>	
	</div>	
</div>


<script>

	$( document ).ready( function() {
		//$('#storeCode').val('0');
		
		//Save Button Click
// 		$('#saveButton').click( function() {
// 			var parentId = $('#parentId').val();
// 			alert(parentId);
// 		});
		
		/*storeCode Change*/
		$( '#storeCode' ).change( function() {
			var storeCode = $('#storeCode').val();
			var contextPath = $('#contextPath').val();
			$.ajax({
				url : contextPath + '/settings/loadParentLocation.do',
				data : "{storeCode:" + storeCode + "}",
				contentType : "application/json",
				success : function(data) {
					var parentLocList = JSON.parse(data);
					
					var initOption = "<option value="+ 0 +">" + "Select Parent Location" + "</option>";
					$('#parentLocation').empty().append(initOption);
					
					for( var index in parentLocList ) {
						var buildOption = "<option value="+ parentLocList[index].id +">" + parentLocList[index].name + "</option>";
						$('#parentLocation').append( buildOption );
					}
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
		}); //storeCode change :: end
		
		
		//parentLocation change
		
		$('#parentLocation').change( function() {
			
			var parentId = $('#parentLocation').val();
			
			if( parentId > 0 ) {
				$('#parentId').val(parentId);
			} else {
				$('#parentId').val('0');
			}
			
			var contextPath = $('#contextPath').val();
			$.ajax({
				url : contextPath + '/settings/loadgodown.do',
				data : "{parentId:" + parentId + "}",
				contentType : "application/json",
				success : function(data) {
					var godownList = JSON.parse(data);
					
					var initOption = "<option value="+ 0 +">" + "Select Godown" + "</option>";
					$('#godown').empty().append(initOption);
					
					for( var index in godownList ) {
						var buildOption = "<option value="+ godownList[index].id +">" + godownList[index].name + "</option>";
						$('#godown').append( buildOption );
					}
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
			
		}); //parentLocation change :: end
		
		//godown change
		$('#godown').change( function() {
			var parentId = $('#godown').val();
			
			if( parentId > 0 ) {
				$('#parentId').val(parentId);
			} else if( $('#parentLocation').val() > 0 ) {
				$('#parentId').val( $('#parentLocation').val() );
			} else {
				$('#parentId').val('0');
			}
			
			var contextPath = $('#contextPath').val();
			$.ajax({
				url : contextPath + '/settings/loadfloor.do',
				data : "{parentId:" + parentId + "}",
				contentType : "application/json",
				success : function(data) {
					var floorList = JSON.parse(data);
					
					var initOption = "<option value="+ 0 +">" + "Select Floor" + "</option>";
					$('#floor').empty().append(initOption);
					
					for( var index in floorList ) {
						var buildOption = "<option value="+ floorList[index].id +">" + floorList[index].name + "</option>";
						$('#floor').append( buildOption );
					}
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
		}); //godown change :: end
		
		//floor change
		$('#floor').change( function() {
			var parentId = $('#floor').val();
			
			if( parentId > 0 ) {
				$('#parentId').val(parentId);
			} else if( $("#godown").val() > 0 ) {
				$('#parentId').val( $('#godown').val() );
			} else if( $('#parentLocation').val() > 0 ) {
				$('#parentId').val( $('#parentLocation').val() );
			} else {
				$('#parentId').val('0');
			}
			
			var contextPath = $('#contextPath').val();
			$.ajax({
				url : contextPath + '/settings/loadblock.do',
				data : "{parentId:" + parentId + "}",
				contentType : "application/json",
				success : function(data) {
					var blockList = JSON.parse(data);
					
					var initOption = "<option value="+ 0 +">" + "Select Block" + "</option>";
					$('#block').empty().append(initOption);
					
					for( var index in blockList ) {
						var buildOption = "<option value="+ blockList[index].id +">" + blockList[index].name + "</option>";
						$('#block').append( buildOption );
					}
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
		}); //floor change :: end
		
		//block change
		$('#block').change( function() {
			var parentId = $('#block').val();
			
			if( parentId > 0 ) {
				$('#parentId').val(parentId);
			} else if( $('#floor').val() > 0 ) {
				$('#parentId').val( $('#floor').val() );
			} else if( $('#godown').val() > 0 ) {
				$('#parentId').val( $('#godown').val() );
			} else if( $('#parentLocation').val() > 0 ) {
				$('#parentId').val($('#parentLocation').val());
			} else {
				$('#parentId').val('0');
			}
			
			var contextPath = $('#contextPath').val();
			$.ajax({
				url : contextPath + '/settings/loadrack.do',
				data : "{parentId:" + parentId + "}",
				contentType : "application/json",
				success : function(data) {
					var rackList = JSON.parse(data);
					
					var initOption = "<option value="+ 0 +">" + "Select Rack" + "</option>";
					$('#rack').empty().append(initOption);
					
					for( var index in rackList ) {
						var buildOption = "<option value="+ rackList[index].id +">" + rackList[index].name + "</option>";
						$('#rack').append( buildOption );
					}
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
		}); //block change :: end
		
		//rack change
		$('#rack').change( function() {
			var parentId = $('#rack').val();
			
			if( parentId > 0 ) {
				$('#parentId').val(parentId);
			} else if( $('#block').val() > 0 ) {
				$('#parentId').val($('#block').val());
			} else if( $('#floor').val() > 0 ) {
				$('#parentId').val($('#floor').val());
			} else if( $('#godown').val() > 0 ) {
				$('#parentId').val($('#godown').val());
			} else if( $('#parentLocation').val() > 0 ) {
				$('#parentId').val($('#parentLocation').val());
			} else {
				$('#parentId').val('0');
			}
		}); // rack change :: end
		
		$('#saveButton').click( function() {
			var haserror = false;
			
			if( haserror == true ) {
				return;
			} else {
				$('#saveButton').prop('disabled', true);
				$('form').submit();
			}
		});
		
	}); //jQuery document block :: end

</script>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>