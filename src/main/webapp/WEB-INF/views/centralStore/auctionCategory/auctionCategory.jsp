<%@include file="../../common/csHeader.jsp"%>

<!-- @author: Ihteshamul Alam, IBCS -->

<style>
	textarea {
		resize: none;
	}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ac/auctionCategoryList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Auction Category List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Auction Category</h1>
	</div>
	
	<div class="col-xs-8 col-xs-offset-2" style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<form method="POST" action="${pageContext.request.contextPath}/ac/auctionCategorySave.do"
			id="saveAuctionCategory">
			<div class="table-responsive col-xs-12">
				<table class="table table-bordered table-hover">
					<tbody>
						<tr>
							<td class="col-xs-2 success text-right" style="font-weight: bold;">Name:&nbsp;<strong class='red'>*</strong></td>
							<td class="col-xs-8">
								<input type="text" id="name" name="name" class="form-control">
								<strong class="text-danger name text-center hide" style="font-weight: bold; font-size: 16px;">Invalid Category Name</strong>
							</td>
						</tr>
						<tr>
							<td class="col-xs-2 success text-right" style="font-weight: bold;">Description:</td>
							<td class="col-xs-8">
								<textarea name="description" id="description" class="form-control"></textarea>
								<strong class="text-danger description text-center hide" style="font-weight: bold; font-size: 16px;">Invalid Description</strong>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-xs-12" align="center">
				<button type="button" id="categoryButton" class="btn btn-success" style="border-radius: 6px;">
					<i class="fa fa-fw fa-save"></i>&nbsp;Save
				</button>
			</div>
		</form>
	</div>
</div>

<script>

	$( document ).ready( function() {
		$('#categoryButton').click( function() {
			var haserror = false;
			
			if( $('#name').val() == null || $.trim( $('#name').val() ) == '' ) {
				$('.name').removeClass('hide');
				haserror = true;
			} else {
				$('.name').addClass('hide');
			}
			
			if( $('#description').val() == null || $.trim( $('#description').val() ) == '' ) {
				$('.description').removeClass('hide');
				haserror = true;
			} else {
				$('.description').addClass('hide');
			}
			
			if( haserror == true ) {
				return;
			} else {
				$('#categoryButton').prop('disabled', true);
				$('#saveAuctionCategory').submit();
			}
		});
	});

</script>

<%@include file="../../common/ibcsFooter.jsp"%>