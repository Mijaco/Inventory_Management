<%@include file="../../common/csHeader.jsp"%>

<!-- @author: Ihteshamul Alam, IBCS -->

<style>

	.vertical-alignment-helper {
   		display:table;
   		height: 100%;
   		width: 100%;
   		pointer-events:none; /* This makes sure that we can still click outside of the modal to close it */
	}
	
	.vertical-align-center {
   		/* To center vertically */
   		display: table-cell;
   		vertical-align: middle;
   		pointer-events:none;
	}
	
	.modal-content {
   		/* Bootstrap sets the size of the modal in the modal-dialog class, we need to inherit it */
   		width:inherit;
   		height:inherit;
  	 		/* To center horizontally */
   		margin: 0 auto;
   		pointer-events: all;
	}
		
</style>

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
			<input type="hidden" id="mstId" value="${auctionCategoryList.id}">
			<c:if test="${!empty auctionCategoryList}">
				<table class="table table-bordered table-striped">
					<tbody>
						<tr>
							<td class="col-md-2 col-sm-2 col-xs-2 success">Name:</td>
							<td class="col-md-10">${auctionCategoryList.name}</td>
						</tr>
						<tr>
							<td class="col-md-2 col-sm-2 col-xs-2 success">Description</td>
							<td class="col-md-10">${auctionCategoryList.description}</td>
						</tr>
					</tbody>
				</table>
			</c:if>
		</div>

		<div class="col-md-12 col-xs-12 col-sm-12" style="margin-bottom: 5px !important;">
			<button type="button" class="btn btn-success btn-sm"
				style="border-radius: 6px;" data-backdrop="static"
				data-keyboard="false" data-toggle="modal" data-target="#editModal">
				<i class="fa fa-fw fa-plus"></i>&nbsp;Add Item Category
			</button>
		</div>

		<div class="col-sm-12 table-responsive">
			
			<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
			
			<table id="itemCategoryList" id="auctionCategoryRefList"
				class="table table-striped table-bordered">
				<thead>
					<tr style="background: #579EC8; color: white; font-weight: normal;">
						<th class="col-md-1 col-sm-2 col-xs-2">Category Id</th>
						<th class="col-md-7 col-sm-2 col-xs-2">Category Name</th>
						<th class="col-md-2 col-sm-3 col-xs-3">Item Type</th>
						<th class="col-sm-1">Action</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${!empty auctionCategoryReferenceList}">
						<c:forEach items="${auctionCategoryReferenceList}" var="referenceList">
							<tr>
								<td>${referenceList.itemCategory.categoryId}
									<input type="hidden" class="itemCode" value="${referenceList.itemCategory.categoryId}">
								</td>
								<td>${referenceList.itemCategory.categoryName}</td>
								<td>${referenceList.itemCategory.itemType == 'C' ? 'Construction Item' : 'General Item'}</td>
								<td>
									<a href="javascript:void(0)" class="btn btn-danger btn-sm"
									style="border-radius: 6px;" onclick="deleteThis(${auctionCategoryList.id}, ${referenceList.id})">
										<i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
									</a>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<!-- --------------------- -->
		</div>
		
		<div class="hide">
			<select name="" id="" class="form-control">
				<c:forEach items="${itemCategorySelected}" var="it">
					<option class="itemCategorySelected" value="${it.itemCategory.categoryId}">${it.itemCategory.categoryId}</option>
				</c:forEach>
			</select>
		</div>

	</div>
</div>

<div class="modal fade" tabindex="-1" role="dialog" id="editModal">
  <div class="vertical-alignment-helper">
  	<div class="modal-dialog vertical-align-center" style="width: 1200px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Sample Modal</h4>
      </div>
      <div class="modal-body">
      	<div class="col-md-12 col-sm-12 col-xs-12">
        	<table class="table table-bordered">
        		<tbody>
        			<tr>
        				<td class="success text-right">Item Category</td>
        				<td>
        					<select id="itemCategory" class="form-control" style="width: 100%;">
								<option value="0" selected disabled>Select Item
									Category</option>
								<c:forEach items="${itemCategoryList}" var="category">
									<option value="${category.categoryId}">${category.categoryId} - ${category.categoryName}</option>
								</c:forEach>
							</select>
        				</td>
        			</tr>
        		</tbody>
        	</table>
        </div> <hr />
	</div>
    <div class="modal-footer">
    	<button type="button" class="btn btn-success btn-sm" style="border-radius: 6px;" id="updateMstInfo">
        	<i class="fa fa-fw fa-repeat"></i>&nbsp;Update</button>
        <button id="closeBtn" type="button" class="btn btn-danger btn-sm"  style="border-radius: 6px;" data-dismiss="modal">
        	<i class="fa fa-fw fa-times"></i>&nbsp;Close</button>
    </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
  </div>
</div><!-- /.modal -->

<script>

	function deleteThis( mstId, dtlId ) {
		
		if( confirm( "Do you want to delete this Category Reference?" ) == true ) {
			var baseURL = $('#contextPath').val();
			var path = baseURL + "/ac/deleteAuctionCategoryReference.do";
			var params = {
					'mstId' : mstId,
					'id'	: dtlId
			}
			
			postSubmit( path, params, "POST" );
		}
	}

	$( document ).ready( function() {
		$('.itemCode').each( function() {
			$("#itemCategory option[value='" + $(this).val() + "']").remove();
		}); //Remove Existing item from Select Box
		
		var data = "";
		$('.itemCategorySelected').each( function() {
			$("#itemCategory option[value='" + $(this).val() + "']").remove();
			//console.log("Data ::" + $(this).val() );
		}); //Remove existing items in DB from select box
		
		//console.log(data);
		
		$('#updateMstInfo').click( function() {
			var id = $('#mstId').val();
			var categoryId = $('#itemCategory').val();
			var baseURL = $('#contextPath').val();
			var path = baseURL + "/ac/addAuctionCategoryReference.do";
			var params = {
					'id' : id,
					'categoryId' : categoryId
			}
			
			postSubmit(path, params, "POST");
		});
	});
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
	$(document).ready(function() {
		$('#auctionCategoryRefList').DataTable({
	        "order": [[ 0, "asc" ]]
	    });
		//document.getElementById('auctionCategoryReferenceList_length').style.display = 'none';
		//document.getElementById('auctionCategoryReferenceList_filter').style.display = 'none';
	});
</script>

<%@include file="../../common/ibcsFooter.jsp"%>