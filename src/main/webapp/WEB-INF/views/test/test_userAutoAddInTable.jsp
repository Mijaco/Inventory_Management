<%@include file="../common/settingsHeader.jsp"%>


<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">


		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />

		<div class="center blue" style="margin-top: 10px;">
			<div class="form-group col-sm-3">Add an user :</div>
			<div class="form-group col-sm-6">
				<select class="form-control" id="selectedUser" name=""
					onchange="userChange(this)"
					style="border: 0; border-bottom: 2px ridge;">
					<option value="" selected>Select a user</option>
					<c:if test="${!empty authUserList}">
						<c:forEach items="${authUserList}" var="user">
							<option value="${user.id}">${user.name}[${user.userid}]</option>
						</c:forEach>
					</c:if>
				</select>
			</div>

		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<div class="col-sm-12 table-responsive">
			<table id="dataList" style=""
				class="table table-striped table-hover table-bordered">

				<tr style="background: #579EC8; color: white; font-weight: normal;">
					<td>ID</td>
					<td>User Id</td>
					<td>Name</td>
					<td>Email</td>
					<td>Designation</td>
					<td>Department</td>
				</tr>

			</table>

		</div>
		<!-- --------------------- -->

	</div>
</div>

<script type="text/javascript">
	function userChange(mstId) {
		var contextPath = $('#contextPath').val();
		var id = $('#selectedUser').val();
		var path = contextPath + '/test/addUser.do';
		if (id == "") {
			return;
		}

		$.ajax({

			url : path,
			data : "{id:" + id + "}",
			contentType : "application/json",
			success : function(data) {
				var user = JSON.parse(data);

				var td1 = '<td>' + user.id + '</td>';
				var td2 = '<td>' + user.userid + '</td>';
				var td3 = '<td>' + user.name + '</td>';
				var td4 = '<td>' + user.email + '</td>';
				var td5 = '<td>' + user.designation + '</td>';
				var td6 = '<td>' + user.deptId + '</td>';
				var tr = '<tr>' + td1 + td2 + td3 + td4 + td5 + td6 + '</tr>';

				$('#dataList').append(tr);
				$("#selectedUser option[value='" + id + "']").remove();
				$('#selectedUser').val("");

			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});

	}
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
