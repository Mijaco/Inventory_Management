<%@include file="../common/faHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->


<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">		
		<h1 class="center blue"> <em>Welcome to Fixed Assets Management</em></h1>
	</div>
	
	
	<div class="container">
		<div class="row">
			<div class="col-sm-4  col-sm-offset-4" style="background-color: white; 
			border: 2px dashed red; border-radius: 10px;
			margin-top: 10vh; min-height: 30vh; display: flex;  align-items: center;">
				<div class="col-sm-12 center" >
					<h1 class="red center italicized"><em>Pending Task</em></h1>
					
				<%-- <a href="${pageContext.request.contextPath}/wsx/transformer/requisitionList.do" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Item Requisition Tray <span class="badge badge-important white bold">${wsRequisitionTray}</span></a>
					
					<a href="${pageContext.request.contextPath}/wsx/returnSlip/List.do" class="btn btn-primary" style="display: block;
					margin-bottom: 10px; border-radius:8px;">Item Return Slip Tray <span class="badge badge-important white bold">${wsReturnTray}</span></a>
					 --%>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>