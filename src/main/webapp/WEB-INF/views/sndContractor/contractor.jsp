<%@include file="../common/sndCnHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h2 class="center blue ubuntu-font">
			<em>Welcome to Contractor Management</em>
		</h2>		
	</div>

	<div class="container">
		<div class="row">
			<div class="col-sm-4  col-sm-offset-4"
				style="background-color: white; border: 2px dashed red; border-radius: 10px; margin-top: 10px; min-height: 30vh; display: flex; align-items: center;">
				<div class="col-sm-12 center">
					<h2 class="red center italicized">
						<em>Pending Task</em>
					</h2>

					<a
						href="${pageContext.request.contextPath}/c2ls/requisitionList.do"
						class="btn btn-primary"
						style="display: block; margin-bottom: 5px; border-radius: 8px;">Item
						Requisition Tray <span
						class="badge badge-important white bold">${contRequisitionTray}</span>
					</a> <a
						href="${pageContext.request.contextPath}/c2ls/returnSlip/List.do"
						class="btn btn-primary"
						style="display: block; margin-bottom: 5px; border-radius: 8px;">Item
						Return Slip Tray <span
						class="badge badge-important white bold">${contReturnSlipTray}</span>
					</a>

				</div>
			</div>
		</div>
	</div>
</div>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>