<%-- <%@include file="../common/ibcsHeader.jsp"%> --%>
<%@include file="../common/homeHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<!-- <div class="container-fluid icon-box" style="background-color: #5F9AAC"> -->
<div class="container-fluid icon-box" style="background-color: #000020">
	<div class="row mygrid col-md-offset-2 col-md-8">

		<%-- <div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
				<a class="o_app o_action_app" data-action-id="178"  style="text-decoration: none;"
					data-action-model="ir.actions.act_window" data-menu="154" href="${pageContext.request.contextPath}/centeralStore.do">
					<img class="o_app_web_icon" style="border: 2px solid; border-radius: 10px;" width="70%" height="70%"
					src="${pageContext.request.contextPath}/resources/assets/img/home/centeral_store.png"/>
					 <br>
					<span class="glyphicon-class" style="color: white; font-size: 0.9em;">Central Store</span>
				</a>
			</div> --%>

		<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
			<a class="o_app o_action_app" data-action-id="178"
				style="text-decoration: none;"
				data-action-model="ir.actions.act_window" data-menu="154"
				href="${pageContext.request.contextPath}/cs/itemRecieved/centralStore.do">
				<img class="o_app_web_icon"
				style="border: 2px solid; border-radius: 10px;" width="70%"
				height="70%"
				src="${pageContext.request.contextPath}/resources/assets/img/home/centeral_store.png" />
				<br> <span class="glyphicon-class"
				style="color: white; font-size: 0.9em;">Central Store
					Management</span>
			</a>
		</div>


		<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
			<a class="o_app o_action_app" data-action-id="178"
				style="text-decoration: none;"
				data-action-model="ir.actions.act_window" data-menu="154"
				href="${pageContext.request.contextPath}/subStore.do"> <img
				class="o_app_web_icon"
				style="border: 2px solid; border-radius: 10px;" width="70%"
				height="70%"
				src="${pageContext.request.contextPath}/resources/assets/img/home/sub_store.png" />
				<br> <span class="glyphicon-class"
				style="color: white; font-size: 0.9em;">Sub Store Management</span>
			</a>
		</div>


		<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
			<a class="o_app o_action_app" data-action-id="178"
				style="text-decoration: none;"
				data-action-model="ir.actions.act_window" data-menu="154"
				href="${pageContext.request.contextPath}/localStore.do"> <img
				class="o_app_web_icon"
				style="border: 2px solid; border-radius: 10px;" width="70%"
				height="70%"
				src="${pageContext.request.contextPath}/resources/assets/img/home/local_store.png" />
				<br> <span class="glyphicon-class"
				style="color: white; font-size: 0.9em;">Local Store
					Management</span>
			</a>
		</div>


		<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
			<a class="o_app o_action_app" data-action-id="178"
				style="text-decoration: none;"
				data-action-model="ir.actions.act_window" data-menu="154"
				href="${pageContext.request.contextPath}/workshop.do"> <img
				class="o_app_web_icon"
				style="border: 2px solid; border-radius: 10px;" width="70%"
				height="70%"
				src="${pageContext.request.contextPath}/resources/assets/img/home/ws_contractor.png" />
				<br> <span class="glyphicon-class"
				style="color: white; font-size: 0.9em;">Workshop Management</span>
			</a>
		</div>


		<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
			<a class="o_app o_action_app" data-action-id="178"
				style="text-decoration: none;"
				data-action-model="ir.actions.act_window" data-menu="154"
				href="${pageContext.request.contextPath}/contractorMain.do"> <img
				class="o_app_web_icon"
				style="border: 2px solid; border-radius: 10px;" width="70%"
				height="70%"
				src="${pageContext.request.contextPath}/resources/assets/img/home/contractor.png" />
				<br> <span class="glyphicon-class"
				style="color: white; font-size: 0.9em;">Contractor Management</span>
			</a>
		</div>

		<%-- <div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
				<a class="o_app o_action_app" data-action-id="178"  style="text-decoration: none;"
					data-action-model="ir.actions.act_window" data-menu="154" href="${pageContext.request.contextPath}/contractorSnd.do">
					<img class="o_app_web_icon" style="border: 2px solid; border-radius: 10px;" width="70%" height="70%"
					src="${pageContext.request.contextPath}/resources/assets/img/home/contractor-snd.png"/>
					 <br>
					<span class="glyphicon-class" style="color: white; font-size: 0.9em;">Contractor(SND)</span>
				</a>
			</div> --%>


		<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
			<a class="o_app o_action_app" data-action-id="178"
				style="text-decoration: none;"
				data-action-model="ir.actions.act_window" data-menu="154"
				href="${pageContext.request.contextPath}/procurementMain.do"> <img
				class="o_app_web_icon"
				style="border: 2px solid; border-radius: 10px;" width="70%"
				height="70%"
				src="${pageContext.request.contextPath}/resources/assets/img/home/procurement.png" />
				<br> <span class="glyphicon-class"
				style="color: white; font-size: 0.9em;">Procurement
					Management</span>
			</a>
		</div>


		<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
			<a class="o_app o_action_app" data-action-id="178"
				style="text-decoration: none;"
				data-action-model="ir.actions.act_window" data-menu="154"
				href="${pageContext.request.contextPath}/inventory.do"> <img
				class="o_app_web_icon"
				style="border: 2px solid; border-radius: 10px;" width="70%"
				height="70%"
				src="${pageContext.request.contextPath}/resources/assets/img/home/inventory.png" />
				<br> <span class="glyphicon-class"
				style="color: white; font-size: 0.9em;">Inventory Management</span>
			</a>
		</div>


		<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
			<a class="o_app o_action_app" data-action-id="178"
				style="text-decoration: none;"
				data-action-model="ir.actions.act_window" data-menu="154"
				href="${pageContext.request.contextPath}/fixedAssets.do"> <img
				class="o_app_web_icon"
				style="border: 2px solid; border-radius: 10px;" width="70%"
				height="70%"
				src="${pageContext.request.contextPath}/resources/assets/img/home/FixedAssets.png" />
				<br> <span class="glyphicon-class"
				style="color: white; font-size: 0.9em;">Fixed Assets
					Management</span>
			</a>
		</div>



		<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
			<a class="o_app o_action_app" data-action-id="178"
				style="text-decoration: none;"
				data-action-model="ir.actions.act_window" data-menu="154"
				href="${pageContext.request.contextPath}/budget.do"> <img
				class="o_app_web_icon"
				style="border: 2px solid; border-radius: 10px;" width="70%"
				height="70%"
				src="${pageContext.request.contextPath}/resources/assets/img/home/Budget1.png" />
				<br> <span class="glyphicon-class"
				style="color: white; font-size: 0.9em;">&nbsp;&nbsp;&nbsp;&nbsp;Budget
					&nbsp;&nbsp; &nbsp; Management</span>
			</a>
		</div>


		<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
			<a class="o_app o_action_app" data-action-id="178"
				style="text-decoration: none;"
				data-action-model="ir.actions.act_window" data-menu="154"
				href="${pageContext.request.contextPath}/settings.do"> <img
				class="o_app_web_icon"
				style="border: 2px solid; border-radius: 10px;" width="70%"
				height="70%"
				src="${pageContext.request.contextPath}/resources/assets/img/home/common.png" />
				<br> <span class="glyphicon-class"
				style="color: white; font-size: 0.9em;">Indent for APP</span>
			</a>
		</div>


		<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
			<a class="o_app o_action_app" data-action-id="178"
				style="text-decoration: none;"
				data-action-model="ir.actions.act_window" data-menu="154"
				href="${pageContext.request.contextPath}/adminPanel.do"> <img
				class="o_app_web_icon"
				style="border: 2px solid; border-radius: 10px;" width="70%"
				height="70%"
				src="${pageContext.request.contextPath}/resources/assets/img/home/admin-panel-icon.png" />
				<br> <span class="glyphicon-class"
				style="color: white; font-size: 0.9em;">Admin Panel</span>
			</a>
		</div>



		<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
			<a class="o_app o_action_app" data-action-id="178"
				style="text-decoration: none;"
				data-action-model="ir.actions.act_window" data-menu="154"
				href="${pageContext.request.contextPath}/auction.do"> <img
				class="o_app_web_icon"
				style="border: 2px solid; border-radius: 10px;" width="70%"
				height="70%"
				src="${pageContext.request.contextPath}/resources/assets/img/home/auction.png" />
				<br /> <span class="glyphicon-class"
				style="color: white; font-size: 0.9em;">Auction</span>
			</a>
		</div>		
		
		<div class="col-md-12" style="margin-top: 0px !important; margin-left: -8px !important;">
		
			<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
				<a class="o_app o_action_app" data-action-id="178"  style="text-decoration: none;"
					data-action-model="ir.actions.act_window" data-menu="154" href="${pageContext.request.contextPath}/pnd.do">
					<img class="o_app_web_icon" style="border: 2px solid; border-radius: 10px;" width="70%" height="70%"
					src="${pageContext.request.contextPath}/resources/assets/img/home/pnd.png"/>
					 <br>
					<span class="glyphicon-class" style="color: white; font-size: 0.9em;">Planning &amp; Design Department</span>
				</a>
			</div>
			
			<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
				<a class="o_app o_action_app" data-action-id="178"  style="text-decoration: none;"
					data-action-model="ir.actions.act_window" data-menu="154" href="${pageContext.request.contextPath}/pettycash.do">
					<img class="o_app_web_icon" style="border: 2px solid; border-radius: 10px;" width="70%" height="70%"
					src="${pageContext.request.contextPath}/resources/assets/img/home/petty_cash.png"/>
					 <br>
					<span class="glyphicon-class" style="color: white; font-size: 0.9em;">Petty Cash Management</span>
				</a>
			</div>
			
			<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
				<a class="o_app o_action_app" data-action-id="178"  style="text-decoration: none;"
					data-action-model="ir.actions.act_window" data-menu="154" href="${pageContext.request.contextPath}/lprr.do">
					<img class="o_app_web_icon" style="border: 2px solid; border-radius: 10px;" width="70%" height="70%"
					src="${pageContext.request.contextPath}/resources/assets/img/home/purchase.png"/>
					 <br>
					<span class="glyphicon-class" style="color: white; font-size: 0.9em;">Local Purchase &amp; Local RR</span>
				</a>
			</div>
		
			<div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
				<a class="o_app o_action_app" data-action-id="178"
					style="text-decoration: none;"
					data-action-model="ir.actions.act_window" data-menu="154"
					href="${pageContext.request.contextPath}/reports.do"> <img
					class="o_app_web_icon"
					style="border: 2px solid; border-radius: 10px;" width="70%"
					height="70%"
					src="${pageContext.request.contextPath}/resources/assets/img/home/report.png" />
					<br /> <span class="glyphicon-class"
					style="color: white; font-size: 0.9em;">Reports</span>
				</a>
			</div>
			
			<%-- <div class="col-md-2 col-xs-4 col-sm-3 center-block text-center">
				<a class="o_app o_action_app" data-action-id="178"
					style="text-decoration: none;"
					data-action-model="ir.actions.act_window" data-menu="154"
					href="${pageContext.request.contextPath}/reports.do"> <img
					class="o_app_web_icon"
					style="border: 2px solid; border-radius: 10px;" width="70%"
					height="70%"
					src="${pageContext.request.contextPath}/resources/assets/img/home/report.png" />
					<br /> <span class="glyphicon-class"
					style="color: white; font-size: 0.9em;">Reports</span>
				</a>
			</div> --%>
		</div>

	</div>
</div>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>