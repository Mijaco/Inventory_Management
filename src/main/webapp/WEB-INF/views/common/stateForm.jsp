<%@include file="../common/adminheader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<style>
	textarea {
		resize: none;
	}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/settings/list/state.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> State List
			</a>
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				New State Entry Form</h1>	
		</div>		
		
	</div>
	
	<div class="container">
			<div class="col-md-8 col-md-offset-2" style="background-color: white; padding: 10px; padding-left: 20px; 
			margin-top: 10px; margin-bottom: 10px;">
		
		<!-- --------------------- -->
		<form method="POST" action="${pageContext.request.contextPath}/settings/add/newStateSave.do">
			<div class="col-sm-12 table-responsive">
				<table class="table table-bordered table-hover">
					<tr>
						<td class="col-xs-3 success text-right" style="font-weight: bold;">State Name:&nbsp;<strong class='red'>*</strong></td>
						<td class="col-xs-9">
							<input type="text" class="form-control" id="stateName"
								placeholder="State Name" style="border: 0; border-bottom: 2px ridge;"
								name="stateName" required>
						</td>
					</tr>
					<tr>
						<td class="col-xs-3 success text-right" style="font-weight: bold;">Remarks:</td>
						<td class="col-xs-9">
							<textarea id="remarks" class="form-control"name="remarks"></textarea>
						</td>
					</tr>
				</table>
				<div class="col-md-12" style="padding-top: 15px;" align="center">
					<button type="reset" class="btn btn-lg btn-danger"
						style="border-radius: 6px;">
						<i class="fa fa-refresh"></i> <span class="bigger-50">Reset</span>
					</button>

					<button type="submit"
						style="border-radius: 6px; margin-left: 10px;"
						class="btn btn-lg btn-success" value="add" name="action">
						<i class="fa fa-save"></i>&nbsp;Add
					</button>
					<button type="submit" value="add_more" name="action"
						style="border-radius: 6px; margin-left: 10px;"
						class="btn btn-lg btn-success">
						<i class="fa fa-plus"></i>&nbsp;Add More
					</button>
				</div>
			</div>
		</form>
		<!-- --------------------- -->		
	</div>	
	</div>	
</div>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>