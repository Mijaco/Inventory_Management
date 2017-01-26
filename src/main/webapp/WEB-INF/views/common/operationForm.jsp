<%@include file="../common/adminheader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<!-- @author: Ihteshamul Alam, IBCS -->

<style>
	textarea {
		resize: none;
	}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/settings/list/operation.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Operation List
			</a>

			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				New Operation Entry Form</h1>	
			
		</div>		
		
	</div>
	
	<div class="container">
			<div class="col-sm-8 col-sm-offset-2" style="background-color: white; padding: 10px; padding-left: 20px; 
			margin-top: 10px; margin-bottom: 10px;">
		
		<!-- --------------------- -->
		<form method="POST" action="${pageContext.request.contextPath}/settings/add/newOperationSave.do">
			<div class="col-sm-12 table-responsive">
				<table class="table table-bordered table-hover">
					<tbody>
						<tr>
							<td class="col-sm-3 success text-right" style="font-weight: bold;">Operation Name:&nbsp;<strong class='red'>*</strong></td>
							<td class="col-sm-9">
								<input type="text" class="form-control" id="operationName"
									placeholder="Operation Name" style="border: 0; border-bottom: 2px ridge;"
									name="operationName" required>
							</td>
						</tr>
						<tr>
							<td class="col-sm-3 success text-right" style="font-weight: bold;">Remarks:</td>
							<td class="col-sm-9">
								<textarea id="remarks" class="form-control" name="remarks"></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
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
		</form>
		<!-- --------------------- -->		
	</div>	
	</div>	
</div>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>