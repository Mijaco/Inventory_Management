<%@include file="../common/ssHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->


<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">		
		<h1 class="center blue"> <em>Welcome to Sub Store Management</em></h1>
	</div>
	
	
	<div class="container">
		<div class="row">
			<div class="col-sm-4  col-sm-offset-4" style="background-color: white; 
			border: 2px dashed red; border-radius: 10px;
			margin-top: 10vh; min-height: 30vh; display: flex;  align-items: center;">
				<div class="col-sm-12 center" >
					<h1 class="red center italicized"><em>Pending Task</em></h1>
					
					<a href="${pageContext.request.contextPath}/ss/itemRecieved/csItemRequisitionReceivedPendingList.do" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Item Receiving Tray <span class="badge badge-important white bold">${itemReceivedTray}</span></a>
					
					<a href="${pageContext.request.contextPath}/ls/requisitionList.do" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Item Requisition Tray <span class="badge badge-important white bold">${requisitionTray}</span></a>
					
					<a href="${pageContext.request.contextPath}/ls/returnSlip/ssList.do" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Item Return Slip Tray <span class="badge badge-important white bold">${returnSlipTray}</span></a>
					
					<a href="${pageContext.request.contextPath}/ss/returnSlip/ListCS.do" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Item Return Slip Tray (to CS) <span class="badge badge-important white bold">${returnSlipTrayCS}</span></a>
					
					<a href="${pageContext.request.contextPath}/ss/storeTicketlist.do" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Store Ticket Tray <span class="badge badge-important white bold">${storeTicketTray}</span></a>

					
				</div>
			</div>
		</div>
	</div>
</div>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>