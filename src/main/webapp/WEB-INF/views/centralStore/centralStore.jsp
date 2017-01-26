<%@include file="../common/csHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->


<div class="container-fluid icon-box" style="background-color: #858585;">

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">		
		<h2 class="center blue" style="margin-top: 0; font-style:italic; 
		font-family: 'Ubuntu Condensed', sans-serif;">Welcome to integrated Central Store Management</h2>
		
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>
			
		<%-- <h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
	</div>
	
	
	<div class="container">
		<div class="row">
			<div class="col-sm-4  col-sm-offset-4" style="background-color: white; 
			border: 2px dashed red; border-radius: 10px;
			margin-top: 10vh; min-height: 30vh; display: flex;  align-items: center;">
				<div class="col-sm-12 center" >
					<h1 class="red center italicized"><em>Pending Task</em></h1>
					
					<a href="${pageContext.request.contextPath}/cs/itemRecieved/list.do" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Item Receiving Tray <span class="badge badge-important white bold">${receivedItemTotal}</span></a>
					
					<a href="${pageContext.request.contextPath}/ls/requisitionList.do" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Item Requisition Tray <span class="badge badge-important white bold">${requisitionTray}</span></a>
					
					<a href="${pageContext.request.contextPath}/ls/returnSlip/List.do" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Item Return Slip Tray <span class="badge badge-important white bold">${returnSlipTray}</span></a>
					
					<a href="${pageContext.request.contextPath}/cs/itemRecieved/storeTicketlist.do" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Store Ticket Tray <span class="badge badge-important white bold">${storeTicketTotal}</span></a>

					
				</div>
			</div>
		</div>
	</div>



</div>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>