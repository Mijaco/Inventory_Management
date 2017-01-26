<%@include file="../../common/csHeader.jsp"%>

<!-- @author: Ihteshamul Alam, IBCS -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ac/auctionCategoryForm.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Auction Category Form
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Auction Category</h1>
	</div>
	
	<div class="row" style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px; padding-top: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty auctionList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			
			<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
			
			<c:if test="${!empty auctionList}">
				<table id="auctionCategoryList"
					class="table table-striped table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th class="col-md-2 col-sm-2 col-xs-2">Name</th>
							<th>Description</th>
							<th class="col-md-2 col-sm-2 col-xs-2">Remarks</th>
							<th class="col-md-3 col-sm-3 col-xs-3">Action</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${auctionList}" var="auction" varStatus="loop">
							<tr>
								<td>
									<p id="coreName${loop.index}">${auction.name}</p>
									<p id="editName${loop.index}" class="hide">
										<input type="text" class="form-control" id="inputName${loop.index}" value="${auction.name}">
									</p>
									<h5 class="text-danger hide" id="errName${loop.index}"> <strong>Invalid auction category name</strong> </h5>
								</td>
								<td>
									<p id="coreDescription${loop.index}">${auction.description}</p>
									<p id="editDescription${loop.index}" class="hide">
										<input type="text" class="form-control" id="inputDescription${loop.index}" value="${auction.description}">
									</p>
									<h5 class="text-danger hide" id="errDesc${loop.index}"> <strong>Invalid auction description</strong> </h5>
								</td>
								<td>
									<p id="coreRemarks${loop.index}">${auction.remarks}</p>
									<p id="editRemarks${loop.index}" class="hide">
										<input type="text" class="form-control" id="inputRemarks${loop.index}" value="${auction.remarks}">
									</p>
								</td>
								<td class="action-buttons">
									
									<a href="javascript:void(0)" class="btn btn-info btn-xs" onclick="showThis(${auction.id})"
									 style="border-radius: 6px;"><i class="fa fa-fw fa-eye"></i>&nbsp;Show</a>
									 
									<a href="javascript:void(0)" id="edit${loop.index}" class="btn btn-success btn-xs" onclick="editThis(${loop.index})"
									 style="border-radius: 6px;"><i class="fa fa-fw fa-edit"></i>&nbsp;Edit</a>
									 
									 <a href="javascript:void(0)" id="update${loop.index}" class="btn btn-primary btn-xs hide" onclick="updateThis(${auction.id}, ${loop.index})"
									 style="border-radius: 6px;"><i class="fa fa-fw fa-repeat"></i>&nbsp;Update</a>
									 
									<a href="javascript:void(0)" class="btn btn-danger btn-xs" onclick="deleteThis(${auction.id})"
									 style="border-radius: 6px;"><i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete</a>
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

<script>
	
	function showThis(id) {
		var baseURL = $('#contextPath').val();
		var path = baseURL + "/ac/showAuctionCategory.do";
		
		var params = {
				'id' : id
		}
		
		postSubmit(path, params, "POST");
	}
	
	function editThis( index ) {
		$('#edit'+index).addClass('hide');
		
		$('#coreName'+index).addClass('hide');
		$('#editName'+index).removeClass('hide');
		
		$('#coreDescription'+index).addClass('hide');
		$('#editDescription'+index).removeClass('hide');
		
		$('#coreRemarks'+index).addClass('hide');
		$('#editRemarks'+index).removeClass('hide');
		
		$('#update'+index).removeClass('hide');
	}
	
	function updateThis( id, index ) {
		var name = $('#inputName'+index).val();
		var description = $('#inputDescription'+index).val();
		var remarks = $('#inputRemarks'+index).val();
		
		var baseURL = $('#contextPath').val();
		
		var haserror = false;
		
		if( name == null || $.trim( name ) == '' ) {
			$('#errName'+index).removeClass('hide');
			haserror = true;
		} else {
			$('#errName'+index).addClass('hide');
		}
		
		if( description == null || $.trim( description ) == '' ) {
			$('#errDesc'+index).removeClass('hide');
			haserror = true;
		} else {
			$('#errDesc'+index).addClass('hide');
		}
		
		if( haserror == true ) {
			return;
		} else {
			$.ajax({
				url : baseURL + "/ac/updateAuctionCategory.do",
				data : {"id":id, "name":name, "description":description, "remarks":remarks},
				success : function(data) {
					if( data == "success" ) {
						
						alert("Auction category info. is updated.");
						
						$('#update'+index).addClass('hide');
						
						$('#coreName'+index).text( $('#inputName'+index).val() ).removeClass('hide');
						$('#editName'+index).addClass('hide');
						
						$('#coreDescription'+index).text( $('#inputDescription'+index).val() ).removeClass('hide');
						$('#editDescription'+index).addClass('hide');
						
						$('#coreRemarks'+index).text( $('#inputRemarks'+index).val() ).removeClass('hide');
						$('#editRemarks'+index).addClass('hide');
						
						$('#edit'+index).removeClass('hide');
					}
					else {
						alert(data);
					}
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			}); //ajax
		}
	} //update
	
	function deleteThis( id ) {
		if( confirm( "Do you want to delete this Category?" ) == true ) {
			
			var baseURL = $('#contextPath').val();
			var path = baseURL + "/ac/deleteAuctionCategory.do";
			var params = {
					'id' : id
			}
			
			postSubmit(path, params, "POST");
		}
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

<script type="text/javascript">
	$(document).ready(function() {
		$('#auctionCategoryList').DataTable();
		document.getElementById('auctionCategoryList_length').style.display = 'none';
		//document.getElementById('auctionCategoryList_filter').style.display = 'none';
	});
</script>

<%@include file="../../common/ibcsFooter.jsp"%>