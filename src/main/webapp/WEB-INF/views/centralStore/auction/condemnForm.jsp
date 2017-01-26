<%@include file="../../common/auctionHeader.jsp"%>

<!-- @author: Ahasanul Ashid, IBCS -->


<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h2 class="center blue ubuntu-font"
			style="margin-top: 10px;">
			Auction List</h2>
	</div>

	<div class="col-xs-8 col-xs-offset-2"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<form method="POST"
			action="${pageContext.request.contextPath}/auction/saveCondemnItem.do">
			<div class="table-responsive col-xs-12">
				<table class="table table-bordered table-hover">
					<tbody>
						<tr>
							<td class="col-xs-3 success text-right"
								style="font-weight: bold;">Auction Id & Category:</td>
							<td class="col-xs-7"><select class="form-control" name="id"
								required="required" style="border: 0; border-bottom: 2px ridge;">
									<c:if test="${!empty auctionProcessMstList}">
										<option value="">Select</option>
										<c:forEach items="${auctionProcessMstList}" var="mst">
											<option value="${mst.id}">${mst.auctionName} -- ${mst.auctionCategory.name}</option>
										</c:forEach>
									</c:if>
							</select></td>
						</tr>
					
					</tbody>
				</table>
			</div>
			<div class="col-xs-12" align="center">
				<button type="submit" id="categoryButton" class="btn btn-md btn-success"
					style="border-radius: 6px;">
					<i class="fa fa-forward"></i>&nbsp;Next
				</button>
			</div>
		</form>
	</div>
</div>



<%@include file="../../common/ibcsFooter.jsp"%>