<%@include file="../common/wsHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->


<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">		
		<h3 class="center blue"> <em>Welcome to Workshop Management</em></h3>
	</div>
	
	
	<div class="container">
		<div class="row">
			<div class="col-sm-4  col-sm-offset-4" style="background-color: white; 
			border: 2px dashed red; border-radius: 10px;
			margin-top: 20px; min-height: 30vh; display: flex;  align-items: center;">
				<div class="col-sm-12" >
					<h4 class="red center italicized"><em>Pending Task</em></h4>
					
				<a href="${pageContext.request.contextPath}/wsx/transformer/requisitionList.do" class="btn btn-primary" style="display: block;
					margin-bottom: 5px; border-radius:8px; text-align: left;">Transformer Requisition Tray <span class="badge badge-important white bold">${wsXRequisitionTray}</span></a>
					
				<a href="${pageContext.request.contextPath}/ws/requisitionList.do" class="btn btn-primary" style="display: block;
					margin-bottom: 5px; border-radius:8px; text-align: left;">Transformer Materials Requisition Tray <span class="badge badge-important white bold">${wsMatRequisitionTray}</span></a>
					
				<a href="${pageContext.request.contextPath}/prev/prevReceiveList.do" class="btn btn-primary" style="display: block;
					margin-bottom: 5px; border-radius:8px; text-align: left;">Item Receive For Preventive Tray <span class="badge badge-important white bold">${wsPrevItemReceiveTray}</span></a>
					
					<a href="${pageContext.request.contextPath}/wsx/returnSlip/List.do" class="btn btn-primary" style="display: block;
					margin-bottom: 5px; border-radius:8px; text-align: left;">Transformer Return Slip Tray <span class="badge badge-important white bold">${wsXReturnTray}</span></a>
					<a href="${pageContext.request.contextPath}/wsm/returnSlip/List.do" class="btn btn-primary" style="display: block;
					margin-bottom: 5px; border-radius:8px; text-align: left;">Transformer Materials Return Slip Tray <span class="badge badge-important white bold">${wsReturnTray}</span></a>
					<a href="${pageContext.request.contextPath}/jobcard/jobList.do" class="btn btn-primary" style="display: block;
					margin-bottom: 5px; border-radius:8px; text-align: left;">Job Card Tray <span class="badge badge-important white bold">${wsJobCardTray}</span></a>
					<a href="${pageContext.request.contextPath}/ws/xf/getPendingTTRList.do" class="btn btn-primary" style="display: block;
					margin-bottom: 5px; border-radius:8px; text-align: left;">XFormer Test Tray <span class="badge badge-important white bold">${wsXFormerTestTray}</span></a>
					
				</div>
			</div>
		</div>
	</div>
</div>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>