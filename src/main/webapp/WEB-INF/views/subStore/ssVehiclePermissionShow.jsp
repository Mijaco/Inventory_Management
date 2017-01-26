<%@include file="../common/ssHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- -------------------End of Header-------------------------- -->
<div class="container-fluid icon-box" style="background-color: offwhite;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ss/vehiclePermissionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm">
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Vehicle Permission List
			</a>
		</div>
		<h1 class="center blue" style="margin-top: 0px;">Vehicle Permission</h1>
	</div>
	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		<div class="oe_title">
			<input type="hidden" value="${vehiclePermissionShow.id}" name="id">
			<table class="table">
				<tr class="">
					<td class="success" style="vertical-align: middle;">Vehicle Permission Slip No:</td>
					
				
					<td class="info">
					<input type="text" style="width: 100%"
					value="${vehiclePermissionShow.slipNo}" id="slipNo" 
					readonly="readonly"/>
					</td>					
					<td class="success" style="vertical-align: middle;">Contractor/S &amp; D Name:</td>
					<td class="info"><input type="text" style="width: 100%"
					value="${vehiclePermissionShow.contractorName}" 
					id="contractorName" readonly="readonly"/>
					</td>					
				</tr>
				
				<tr class="">
					<td class="success" style="vertical-align: middle;">Entry Purpose:</td>
					<td class="info"><input type="text" style="width: 100%"
					value="${vehiclePermissionShow.entryPurpose}" id="entryPurpose" 
					readonly="readonly"/>
					</td>					
					<td class="success" style="vertical-align: middle;">Entry Time:</td>
					<td class="info">
					<input type="datetime" style="width: 100%;"					
					 id="entryTime" value='<fmt:formatDate
						value="${vehiclePermissionShow.entryTime}" pattern="dd-MM-yyyy" />' readonly/>
					</td>					
				</tr>
				
				<tr class="">
					<td class="success" style="vertical-align: middle;">Vehicle Type:</td>
					<td class="info"><input type="text" style="width: 100%"
					value="${vehiclePermissionShow.vehicleType}" id="vehicleType" 
					readonly="readonly"/>
					</td>					
					<td class="success" style="vertical-align: middle;">Vehicle No:</td>
					<td class="info"><input type="text" style="width: 100%"
					value="${vehiclePermissionShow.vehicleNumber}" 
					id="vehicleNumber" readonly="readonly"/>
					</td>					
				</tr>
				
				<tr class="">
					<td class="success" style="vertical-align: middle;">Driving License No:</td>
					<td class="info"><input type="text" style="width: 100%"
					value="${vehiclePermissionShow.drivingLicenceNo}" id="drivingLicenceNo" 
					readonly="readonly"/>
					</td>					
					<td class="success" style="vertical-align: middle;">Requisition By:</td>
					<td class="info"><input type="text" style="width: 100%"
					value="${vehiclePermissionShow.requisitionBy}" 
					id="requisitionBy" readonly="readonly"/>
					<%-- <input type="hidden" value="${returnState}"
						id="returnState" /> --%>
					</td>
										
				</tr>
				
			</table>
		</div>
		
		<!-- Comment or Remarks -->
		<div class="row">
			<label class="col-xs-1"> <strong>Remarks:&nbsp;<span class='red'>*</span></strong></label>
			<div class="col-xs-11">
				<textarea class="form-control" rows="4" cols="" id="remarks"></textarea>
				<strong class="remarks text-danger hide">This field is required</strong>
			</div>
		</div>
		
		<div class="col-xs-12">
			<hr />
		</div>
		
		<!-- Initiate and Send Forward Button -->
		<div class = "row">
			<div class="col-md-4 col-sm-4 text-left">
			<%-- <c:if test="${!empty backManRcvProcs}">
				<div class="dropup pull-left">
					
					<button class = "btn btn-primary dropdown-toggle" type = "button"
							style="border-radius: 6px;" data-toggle =  "dropdown"
							aria-haspopup="true" aria-expanded="false">
							Back To<span class="caret"></span>
					</button>
					
					<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
						<c:forEach items="${backManRcvProcs}" var="backManRcvProcs">
							<li class="">
								<a href="Javascript:backToLower(${backManRcvProcs.stateCode})">
								For ${backManRcvProcs.buttonName}</a>
							</li>
						</c:forEach>
					</ul>
															
				</div>
			</c:if> --%>
			</div>
			
			<div class="col-md-4 col-sm-4 text-center">
				<button class="btn btn-primary" onclick="approveing()" id="saveButton"
					style="text-decoration: none; border-radius: 6px;">
					${buttonValue} </button>
			</div>
			
			<div class="col-md-4 col-sm-4 text-right">
				<%-- <c:if test="${!empty nextManRcvProcs}">
					<div class="dropup pull-right">
						<button class="btn btn-danger dropdown-toggle" type="button"
							style="border-radius: 6px;" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">
							Send to <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
							<c:forEach items="${nextManRcvProcs}" var="nextManRcvProcs">
								<li class=""><a href="Javascript:forwardToUpper(${nextManRcvProcs.stateCode})">
								For ${nextManRcvProcs.buttonName}</a></li>
							</c:forEach>
						</ul>
					</div>
				</c:if> --%>
			</div>			
		</div>
		
		<hr />
		
		<script>
			function editItem(id){
				$.ajax({
					url : '${pageContext.request.contextPath}/ss/vehiclePermission.do',
					data : "{id:"+id+"}",
					contentType : "application/json",
					success : function(data) {
						var item = JSON.parse(data);
						$("#modal_contractorName").val(item.contractorName);
						$("#modal_entryPurpose").val(item.entryPurpose);
						$("#modal_vehicleType").val(item.vehicleType);
						$("#modal_vehicleNumber").val(item.vehicleNumber);
						$("#modal_drivingLicenceNo").val(item.drivingLicenceNo);
						$("#modal_requisitionBy").val(item.requisitionBy);
					},
					error : function(data){
						alert("Server Error");
					},
					type : 'POST'
				});
			}
		</script>

		<script>
			function forwardToUpper(stateCode){
				if( $('#remarks').val() == null || $.trim( $('#remarks').val() ) == '' ) {
					$('.remarks').removeClass('hide');
					$('#remarks').focus();
					return;
				} else {
					var remarks = $('#remarks').val();
					var slipNo = $('#slipNo').val();
					window.location = "${pageContext.request.contextPath}/ss/vehiclePermissionSendTo.do?slipNo="+slipNo+"&remarks"+remarks;
				}
				
			}
			
			function backToLower(stateCode){
				if( $('#remarks').val() == null || $.trim( $('#remarks').val() ) == '' ) {
					$('.remarks').removeClass('hide');
					$('#remarks').focus();
					return;
				} else {
					var remarks = $('#remarks').val();
					var slipNo = $('#slipNo').val();
					window.location = "${pageContext.request.contextPath}/ss/vehiclePermissionBackTo.do?slipNo="+slipNo+"&remarks="+remarks;
				}
				
			}
			
			function approveing(){
				if( $('#remarks').val() == null || $.trim( $('#remarks').val() ) == '' ) {
					$('.remarks').removeClass('hide');
					$('#remarks').focus();
					return;
				} else {
					$('#saveButton').prop('disabled', true);
					$('.remarks').addClass('hide');
					var remarks = $('#remarks').val();
					var slipNo = $('#slipNo').val();
					/* var returnStateCode = $('#returnStateCode').val(); */
					//var returnStateCode = "";
					window.location = "${pageContext.request.contextPath}/ss/vehiclePermissionSubmitApproved.do?slipNo="+slipNo+"&remarks="+remarks;
				}
				
			}
		</script>		
		
	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>