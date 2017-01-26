<%@include file="../common/adminheader.jsp"%>
<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
<!-- -------------------End of Header-------------------------- -->
<!-- Author :: Ihteshamul Alam -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/cc/committeeConvenerList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Committee Convener List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Committee Convener</h1>
	</div>
	
	<!-- This block is hidden -->
	<%-- <c:if test="${!empty committeeConvenerList}">
		<div class="hide">
			<select name="" id="" class="ulist">
				<c:forEach items="${committeeConvenerList}" var="committee">
					<option value="${committee.authUser.id}">${committee.authUser.id}</option>
				</c:forEach>
			</select>
		</div>
	</c:if> --%>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		
		<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
		
		<form action="${pageContext.request.contextPath}/cc/saveCommitteeConvener.do" method="POST" class="form-horizontal">
			<div class="col-md-12 table-responsive">
				<table class="table table-bordered table-hover">
					<tbody>
						<tr>
							<td class="col-md-3 text-right success" style="font-weight: bold">Username</td>
							<td>
								<select id="authList" name="authList" class="form-control" style="width: 100%">
									<option value="" selected disabled>-- Select User --</option>
									<c:if test="${!empty authuserList}">
										<c:forEach items="${authuserList}" var="authUser">
											<option value="${authUser.id}">${authUser.name} ${empty authUser.empId?'--': '['.concat(authUser.empId).concat(']')} ${authUser.designation}</option>
										</c:forEach>
									</c:if>
								</select>
							</td>
						</tr>
					</tbody>
				</table>
				
				<table class="table table-bordered table-hover" id="dataList">
					<thead>
						<tr style="background: #579EC8; color: white; font-weight: normal;">
							<th>Name</th>
							<th>Id</th>
							<th>Email</th>
							<th>Designation</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
			<div class="col-md-12" align="center">
				<button class="btn btn-purple hide" id="saveContent" style="border-radius: 6px;"><i class="fa fa-fw fa-save"></i>&nbsp;Save</button>
			</div>
		</form>
		
	</div>
</div>

<script>
	$( document ).ready( function() {
		$('#authList').change( function() {
			var id = $('#authList').val();
			
			//Load Use info from AuthUser
			var baseURL = $('#contextPath').val();
			var params = {
					id : id
			}
			if(id==null || id==''){
				return;
			}			
			var cData = JSON.stringify( params );
			$.ajax({
				url : baseURL + "/cc/loadUserInfo.do",
				data : cData,
				contentType : "application/json",
				success : function(data) {
					var gap = JSON.parse( data );
					var newEmpId = '';
					if(gap.empId!=null){
						newEmpId = gap.empId;
					}
					
					var td1 = '<td>' + gap.name + 
						"<input type='hidden' id='id' name='id' value='"+gap.id+"'>"
					+ '</td>';
					var td2 = '<td>' + newEmpId+ '</td>';
					var td3 = '<td>' + gap.email + '</td>';
					var td4 = '<td>' + gap.designation + '</td>';
					var tr = '<tr>' + td1 + td2 + td3 + td4 + '</tr>';
				
					$('#dataList > tbody').append(tr);
					$("#authList option[value='" + id + "']").remove();
					$('#authList').val("");
					
					$('#saveContent').removeClass('hide');
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
			
		}); //authList onChange
	});
</script>

<%@include file="../common/ibcsFooter.jsp"%>