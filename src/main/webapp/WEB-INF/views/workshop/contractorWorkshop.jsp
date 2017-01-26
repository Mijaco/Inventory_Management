<%@include file="../common/wsContractorHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->


<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Welcome to WorkShop Contractor Management</h2>
	</div>


	<div class="container">
		<%-- <div class="row">
			<div class="col-sm-4  col-sm-offset-4" style="background-color: white; 
			border: 2px dashed red; border-radius: 10px;
			margin-top: 10vh; min-height: 30vh; display: flex;  align-items: center;">
				<div class="col-sm-12 center" >
					<h1 class="red center italicized"><em>Pending Task</em></h1>
					
					<a href="#" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Item Receiving Tray <span class="badge badge-important white bold">0</span></a>
					
					<a href="${pageContext.request.contextPath}/ls/requisitionList.do" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Item Requisition Tray <span class="badge badge-important white bold">${requisitionTray}</span></a>
					
					<a href="${pageContext.request.contextPath}/ls/returnSlip/List.do" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Item Return Slip Tray <span class="badge badge-important white bold">${returnSlipTray}</span></a>
					
					<a href="${pageContext.request.contextPath}/ss/storeTicketlist.do" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Store Ticket Tray <span class="badge badge-important white bold">${storeTicketTray}</span></a>

					
				</div>
			</div>
		</div> --%>
	</div>
</div>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>