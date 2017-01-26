<%@include file="../inventory/inventoryheader.jsp"%>


<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Open Balance</a> / List
		</h4>
	</div>
	<form method="POST" enctype="multipart/form-data"
		action="${pageContext.request.contextPath}/inventory/saveaverageprice.do">
		
		<div class="col-xs-12 col-sm-6">
			Item Code <input type="text" name="itemCode" />
		</div>
		<div class="col-xs-12 col-sm-6">
			Price <input type="text" name="price" />
		</div>
		<div class="col-xs-12 col-sm-6">
			<button type="submit" id="saveButton"
				style="margin-right: 10px; border-radius: 6px;"
				class="width-20 pull-right btn btn-lg btn-success">
				<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
			</button>
		</div>
	</form>
</div>
<%@include file="../common/ibcsFooter.jsp"%>