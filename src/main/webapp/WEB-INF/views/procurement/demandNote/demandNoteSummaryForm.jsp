<%@include file="../../common/mpsHeader.jsp"%>
<!-- ------End of Header------ -->

<!-- @author: Ihteshamul Alam -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}"/>
		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Requirement Summary</h1>
	</div>

	<div class="container">
	
		<div class="col-xs-8 col-xs-offset-2" style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
			<form method="POST" action="${pageContext.request.contextPath}/mps/dn/generateDemandNoteSummary.do">
				<div class="table-responsive col-xs-12">
					<table class="table table-bordered table-hover">
						<tbody>
							<tr>
								<td class="col-xs-2 success text-right" style="font-weight: bold;">Financial Year:</td>
								<td class="col-xs-8">
									<select class="form-control" name="id" id="sessionName">
										<option value="0">Select Financial Year</option>
										<c:forEach items="${descoSession}" var="sessions">
											<option value="${sessions.id}">${sessions.sessionName}</option>
										</c:forEach>
									</select>
									<strong class="text-danger sessionName text-center hide" style="font-weight: bold; font-size: 16px;">Invalid Financial Year!</strong>
								</td>
							</tr>
							<tr>
								<td class="col-xs-2 success text-right" style="font-weight: bold;">Requirement Type:</td>
								<td class="col-xs-8">
									<select class="form-control" name="annexureType" id="annexureType">
										<option value="">Select Requirement Type</option>
										<option value="1C">System Materials</option>
										<option value="1G">General Items</option>
										<option value="2">Non-Coded Items</option>
										
									</select>
									<strong class="text-danger annexureType text-center hide" style="font-weight: bold; font-size: 16px;">Invalid Requirement Type</strong>
								</td>
							</tr>
							<tr>
								<td class="col-xs-2 success text-right" style="font-weight: bold;">Department:</td>
								<td class="col-xs-8">
									<select class="form-control" name="departmentId" id="department">
										<option value="">Select Department</option>
										<c:forEach items="${departmentList}" var="department">
											<option value="${department.id}">${department.deptName}</option>
										</c:forEach>										
									</select>									
								</td>
							</tr>
							<tr class="hidethis">
								<td class="col-xs-2 success text-right" style="font-weight: bold;">Item Category:</td>
								<td class="col-xs-8">
									<select class="form-control" name="itemCategoryCode" 
									onchange="categoryLeaveChange(this)" id="itemCategory">
										<option value="">Select Category</option>
										<c:forEach items="${itemCategoryList}" var="itemCategory">
											<option value="${itemCategory.categoryId}">${itemCategory.categoryName}</option>
										</c:forEach>										
									</select>									
								</td>
							</tr>
							<!-- <tr>
								<td class="col-xs-2 success text-right"
									style="font-weight: bold;">Item :</td>
								<td class="col-xs-8"><select class="form-control"
									name="item" id="item">
										<option value="">Select Item</option>
								</select></td>
							</tr> -->
						</tbody>
					</table>
				</div>
				<div class="col-xs-12" align="center">
					<button type="button" id="summaryButton" class="btn btn-primary btn-md" style="border-radius: 6px;">
						<i class="fa fa-search"></i> View
					</button>
				</div>
			</form>
		</div>
		
	</div>
</div>

<script>
	$( document ).ready( function() {
		
		$('#annexureType').change( function() {
			if( $('#annexureType').val() == '2' ) {
				$('.hidethis').addClass('hide');
			} else {
				$('.hidethis').removeClass('hide');
			}
		});
		
		
		$('#summaryButton').click( function() {
			var haserror = false;
			
			if( $('#sessionName').val() == null || $.trim( $('#sessionName').val() ) == '0' ) {
				$('.sessionName').removeClass('hide');
				haserror = true;
			} else {
				$('.sessionName').addClass('hide');
			}
			
			if( $('#annexureType').val() == null || $.trim( $('#annexureType').val() ) == '' ) {
				$('.annexureType').removeClass('hide');
				haserror = true;
			} else {
				$('.annexureType').addClass('hide');
			}
			
			if( haserror == true ) {
				return;
			} else {
				$('form').submit();
			}
		});
	});

	function categoryLeaveChange(element) {
		var categoryId = $("#itemCategory").val();
		var contextPath = $("#contextPath").val();
		$
				.ajax({
					url : contextPath
							+ '/cs/itemRecieved/viewInventoryItemCategory.do',
					data : "{categoryId:" + categoryId + "}",
					contentType : "application/json",
					success : function(data) {
						var itemList = JSON.parse(data);
						var itemNames = $("#item");
						itemNames.empty();
						itemNames.append($("<option></option>").attr("value",
								'').text('Select Item'));
						$.each(itemList, function(itemId, itemName) {
							itemNames.append($("<option></option>").attr(
									"value", this.itemId).text(this.itemName));
						});
					},
					error : function(data) {
						alert("Server Error");
					},
					type : 'POST'
				});
	}
</script>

<!-- ------ Footer ------ -->
<%@include file="../../common/ibcsFooter.jsp"%>