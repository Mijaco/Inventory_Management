<%@include file="../common/lsHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h2 class="center blue ubuntu-font">
			<em>Welcome to Local Store Management</em>
		</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${department.deptName}</h4>

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
						href="${pageContext.request.contextPath}/ls/csItemRequisitionReceivedPendingList.do"
						class="btn btn-primary"
						style="display: block; margin-bottom: 5px; border-radius: 8px;">Item
						Receiving Tray(CS) <span class="badge badge-important white bold">${itemReceivedTray}</span>
					</a> <a
						href="${pageContext.request.contextPath}/ls/itemRecieved/ssItemRequisitionReceivedPendingList.do"
						class="btn btn-primary"
						style="display: block; margin-bottom: 5px; border-radius: 8px;">Item
						Receiving Tray(SS)<span class="badge badge-important white bold">${ssItemReceivedTray}</span>
					</a> <a href="${pageContext.request.contextPath}/ls/requisitionList.do"
						class="btn btn-primary"
						style="display: block; margin-bottom: 5px; border-radius: 8px;">Item
						Requisition Tray <span class="badge badge-important white bold">${requisitionTray}</span>
					</a> <a href="${pageContext.request.contextPath}/ls/returnSlip/List.do"
						class="btn btn-primary"
						style="display: block; margin-bottom: 5px; border-radius: 8px;">Item
						Return Slip Tray <span class="badge badge-important white bold">${returnSlipTray}</span>
					</a>
					<a href="${pageContext.request.contextPath}/c2ls/requisitionList.do"
						class="btn btn-primary"
						style="display: block; margin-bottom: 5px; border-radius: 8px;">Item
						Requisition (Contractor) Tray <span class="badge badge-important white bold">${contRequisitionTray}</span>
					</a> <a href="${pageContext.request.contextPath}/c2ls/returnSlip/List.do"
						class="btn btn-primary"
						style="display: block; margin-bottom: 5px; border-radius: 8px;">Item
						Return Slip (Contractor) Tray <span class="badge badge-important white bold">${contReturnSlipTray}</span>
					</a>
					 <a
						href="${pageContext.request.contextPath}/c2ls/storeTicketlist.do"
						class="btn btn-primary"
						style="display: block; margin-bottom: 5px; border-radius: 8px;">Store
						Ticket Tray <span class="badge badge-important white bold">${storeTicketTray}</span>
					</a>


				</div>
			</div>
		</div>
	</div>
</div>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>