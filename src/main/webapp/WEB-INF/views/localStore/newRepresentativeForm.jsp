<%@include file="../common/lsHeader.jsp"%>
<!-- ------End of Header------ -->
<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Item Received</a> / Show
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ls/contractorList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Contractor List
			</a>
		</div>
		<h2 class="center blue" style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
		New Representative Form</h2>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		
		<!-- Left Side Part of Form -->
		<form action="${pageContext.request.contextPath}/ls/saveRepresentativeInfo.do"
			method="POST" enctype="multipart/form-data">
			<input type="hidden" name="contractorType" id="contractorType"
				value="SND">
			
			<input type="hidden" name="deptId" id="deptId"
				value="${department.deptId}">
				
			<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
				
			<div class="col-md-6 col-sm-6">
			<div class="form-group">
				<label for="chalanNo" class="col-sm-4 control-label">SND Name:&nbsp;<strong class='red'>*</strong></label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="receivedBy"
						value="${department.deptName}"
						style="border: 0; border-bottom: 2px ridge;" name="receivedBy"
						readonly="readonly">
				</div>
			</div>
			<div class="col-sm-10">&nbsp;&nbsp;</div>
			
			<div class="form-group">
				<label for="representiveName" class="col-sm-4 control-label">Representative Name:&nbsp;<strong class='red'>*</strong></label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="representiveName" required="required"
						style="border: 0; border-bottom: 2px ridge;" name="representiveName">
				</div>
			</div>
			<div class="col-sm-10">&nbsp;&nbsp;</div>
			
			<div class="form-group">
				<label for="designation" class="col-sm-4 control-label">Designation:&nbsp;<strong class='red'>*</strong></label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="designation" required="required"
						style="border: 0; border-bottom: 2px ridge;" name="designation">
				</div>
			</div>
			<div class="col-sm-10">&nbsp;&nbsp;</div>
			
			<div class="form-group">
				<label for="contactNo" class="col-sm-4 control-label">Mobile No:</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="contactNo" required="required"
						style="border: 0; border-bottom: 2px ridge;" name="contactNo">
				</div>
			</div>
			<div class="col-sm-10">&nbsp;&nbsp;</div>
			
			<div class="form-group">
				<label for="startdate" class="col-sm-4 control-label">Start Date:&nbsp;<strong class='red'>*</strong></label>
				<div class="col-sm-8">
					<input type="text" class="form-control datepicker-13" id="listedDate" required="required"
						style="border: 0; border-bottom: 2px ridge;" name="listedDate">
				</div>
			</div>
			<div class="col-sm-10">&nbsp;&nbsp;</div>
			
			<div class="form-group">
				<label for="chalanNo" class="col-sm-4 control-label">Photo:&nbsp;<strong class='red'>*</strong></label>
				<div class="col-sm-8">
					<input type="file" accept="image/*" class="form-control" id="pictures" required="required"
						style="border: 0; border-bottom: 2px ridge;" name="pictures">
				</div>
			</div>
			<div class="col-sm-10">&nbsp;&nbsp;</div>
		</div>
		
		<!-- Right Side Part of Form -->
		<div class="col-md-6 col-sm-6">
			<div class="form-group">
				<label for="contractNo" class="col-sm-4 control-label">Contract No:&nbsp;<strong class='red'>*</strong></label>
				<div class="col-sm-8">
					<select class="form-control" id="contractNo" name="contractNo" required="required"
						style="border: 0; border-bottom: 2px ridge;">
						<option value="0" disabled="disabled" selected="selected">Select a Contract</option>
						<c:forEach items="${contractList}" var="contract">
							<option value="${contract.contractNo}">${contract.contractNo}</option>
						</c:forEach>
					</select>
					<h5 class="text-danger contractNo hide"><strong>Invalid contract no.</strong></h5>	
				</div>
			</div>
			<div class="col-sm-10">&nbsp;&nbsp;</div>
			
			<div class="form-group">
				<label for="userId" class="col-sm-4 control-label">User Id:&nbsp;<strong class='red'>*</strong></label>
				<div class="col-sm-7">
					<input type="text" class="form-control" id="userid" onblur="checkUserId()" required="required"
						style="border: 0; border-bottom: 2px ridge;" name="userId">
				</div>
				<div class="col-sm-1">
					<i id="workOrderDecision-2" style="font-size: 2em;" class=""></i>
				</div>
			</div>
			<div class="col-sm-10">&nbsp;&nbsp;</div>
			
			<div class="form-group">
				<label for="email" class="col-sm-4 control-label">E-mail:&nbsp;<strong class='red'>*</strong></label>
				<div class="col-sm-7">
					<input type="text" class="form-control" id="email" onblur="checkEmail()" required="required"
						style="border: 0; border-bottom: 2px ridge;" name="email">
				</div>
				<div class="col-sm-1">
					<i id="workOrderDecision-1" style="font-size: 2em;" class=""></i>
				</div>
			</div>
			<div class="col-sm-10">&nbsp;&nbsp;</div>
			
			<div class="form-group">
				<label for="address" class="col-sm-4 control-label">Address:&nbsp;<strong class='red'>*</strong></label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="address" required="required"
						style="border: 0; border-bottom: 2px ridge;" name="address">
				</div>
			</div>
			<div class="col-sm-10">&nbsp;&nbsp;</div>
			
			<div class="form-group">
				<label for="endDate" class="col-sm-4 control-label">End Date:&nbsp;<strong class='red'>*</strong></label>
				<div class="col-sm-8">
					<input type="text" class="form-control datepicker-13" id="endDate" required="required"
						style="border: 0; border-bottom: 2px ridge;" name="endDate">
				</div>
			</div>
		</div>
		
		<br><br>
		
		<div class="col-xs-12" align="center">
			<button class="btn btn-success" id="saveButton" type="button" disabled="disabled" style="border-radius: 6px"><i class="fa fa-fw fa-save"></i>&nbsp;Save</button>
			<button class="btn btn-danger" type="reset" style="border-radius: 6px"><i class="fa fa-fw fa-refresh"></i>&nbsp;Reset</button>
		</div>
		
		</form>
	</div>
</div>

<script>
 	
 	function isCheckEmail( email ) {
 		if( email == null || email == '' ) {
 			return false;
 		} else {
 			$('#chkEmail').load( contextPath + '/adminpanel/checkEmail.do', {"email":email}, function(d) {
 	 			if( d == 'success' ) {
 	 				return true;
 	 			} else {
 	 				return false;
 	 			}
 	 		});
 		}
 	}
 	
 	function isCheckUserId( userid ) {
 		if( userid == null || userid == '' ) {
 			return false;
 		} else {
 			$('#chkUserId').load( contextPath + '/adminpanel/checkUserId.do', {"userid":userid}, function(d) {
 	 			if( d == 'success' ) {
 	 				return true;
 	 			} else {
 	 				return false;
 	 			}
 	 		});
 		}
 	}
 
 	function checkEmail() {
 		var email = $('#email').val();
 		var saveButton = $("#saveButton");

		var contextPath = $('#contextPath').val();
		var workOrderDecision = $("#workOrderDecision-1");
		
		$.ajax({
			url : contextPath + '/adminpanel/checkEmail.do',
			data : {"email":email},
			success : function(data) {
				var result = data;
				if (result == 'success') {
					if( $('#workOrderDecision-2').hasClass("glyphicon-ok-sign") ) {
						saveButton.prop("disabled", false);
					}
					
					workOrderDecision.removeClass();
					workOrderDecision
							.addClass("glyphicon glyphicon-ok-sign green");

				} else {
					saveButton.prop("disabled", true);
					workOrderDecision.removeClass();
					workOrderDecision
							.addClass("glyphicon glyphicon-remove-sign red big");
				}
			},
			error : function(data) {
				alert(data);
			},
			type : 'POST'
		});
 	}
 	
 	function checkUserId() {
 		var userid = $('#userid').val();
 		var saveButton = $("#saveButton");

		var contextPath = $('#contextPath').val();

		var workOrderDecision = $("#workOrderDecision-2");
		
		$.ajax({
			url : contextPath + '/adminpanel/checkUserId.do',
			data : {"userid":userid},
			success : function(data) {
				var result = data;
				if (result == 'success') {
					if( $('#workOrderDecision-1').hasClass("glyphicon-ok-sign") ) {
						saveButton.prop("disabled", false);
					}
					workOrderDecision.removeClass();
					workOrderDecision
							.addClass("glyphicon glyphicon-ok-sign green");

				} else {
					saveButton.prop("disabled", true);
					workOrderDecision.removeClass();
					workOrderDecision
							.addClass("glyphicon glyphicon-remove-sign red big");
				}
			},
			error : function(data) {
				alert(data);
			},
			type : 'POST'
		});
 	}
 	
 	$(document).ready( function() {
 		$('#saveButton').click( function() {
 			var haserror = false;
 			
 			var userid = $('#userid').val();
 			var email = $('#email').val();
 			
 			if( isCheckEmail( email ) ) {
 				haserror = true;
 			}
 			
 			if( isCheckUserId( userid ) ) {
 				haserror = true;
 			}
 			
 			if( $('#contractNo').val() == null || $.trim( $('#contractNo').val() ) == '0' ) {
 				$('.contractNo').removeClass('hide');
 				haserror = true;
 			}  else {
 				$('.contractNo').addClass('hide');
 			}
 			
 			if( haserror == true ) {
 				return;
 			} else {
 				$('form').submit();
 			}
 		});
 	});
 	
 </script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>