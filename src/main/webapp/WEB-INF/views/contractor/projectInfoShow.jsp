<%@include file="../common/pdHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<!-- @author: Ihteshamul Alam, IBCS -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		 style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/pd/allProjectList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Project List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${descoKhathProjectInfo.khathName}</h1>
	</div>
	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<div class="table-responsive col-sm-12">
			<table class="table table-bordered table-striped">
				<thead>
					<tr>
						<th style="background: #579EC8; color: white; font-weight: normal;">Project Name</th>
						<th style="background: #579EC8; color: white; font-weight: normal;">Source of Fund</th>
						<th style="background: #579EC8; color: white; font-weight: normal;">PD Name</th>
						<th style="background: #579EC8; color: white; font-weight: normal;">Start Date</th>
						<th style="background: #579EC8; color: white; font-weight: normal;">End Date</th>
						<th style="background: #579EC8; color: white; font-weight: normal;">Project Duration</th>
						<th style="background: #579EC8; color: white; font-weight: normal;">Description</th>
						<th style="background: #579EC8; color: white; font-weight: normal;">Remarks</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${descoKhathProjectInfo.khathName}</td>
						<td>${descoKhathProjectInfo.sourceOfFund}</td>
						<td>${descoKhathProjectInfo.pdName}</td>
						<td>${descoKhathProjectInfo.startDate}</td>
						<td>${descoKhathProjectInfo.endDate}</td>
						<td>${descoKhathProjectInfo.duration}</td>
						<td>${descoKhathProjectInfo.description}</td>
						<td>${descoKhathProjectInfo.remarks}</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<%-- <div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit row" style="display: block;">
			<div class="pull-left">
				<h3 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Contractor
				List</h3>
			</div>
			<div class="pull-right" style="padding-right:20px;">
				<a href="${pageContext.request.contextPath}/pnd/contractorForm.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Add New Contractor
			</a>
			</div>
		</div>
	</div> --%>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty contractorList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Sorry!!! No Contractor Found in Database. </i>
					</h2>
				</div>
			</c:if>

			<c:if test="${!empty contractorList}">
				<div class="col-xs-12 table-responsive">
					<div class="table">
						<div style="width: 1800px;">

							<table id="contractorListTable"
								class="table table-striped table-hover table-bordered table-responsive">
								<thead>
									<tr
										style="background: #579EC8; color: white; font-weight: normal;">
										<td style="">Contract No</td>
										<td style="">Contractor Name</td>
										<td style="">Created Date</td>
										<td style="">Address</td>
										<td style="">Project</td>
										<td style="">Tender No</td>
										<td style="">Contract Date</td>
										<td style="">Expired Date</td>
										<!-- <td style="">Representative(s)</td>
										<td style="">Material List</td>
										<td style="">Job(s)</td> -->
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${contractorList}" var="contractor"
										varStatus="loop">
										<tr>
											<td><c:out value="${contractor.contractNo}" /></td>
											<td><c:out value="${contractor.contractorName}" /></td>
											<td><c:out value="${contractor.createdDate}" /></td>
											<td><c:out value="${contractor.address}" /></td>
											<td><c:out value="${contractor.khathName}" /></td>
											<td><c:out value="${contractor.tenderNo}" /></td>
											<td><fmt:formatDate value="${contractor.contractDate}"
													pattern="dd-MM-yyyy" /></td>
											<td><fmt:formatDate
													value="${contractor.updatedValidityDate}"
													pattern="dd-MM-yyyy" /></td>

											<%-- <td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/contRep/finalList.do',{id:'${contractor.id}'},'POST')">
													Representative </a></td>

											<td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/contMats/finalList.do',{id:'${contractor.id}'},'POST')">
													Material List </a></td>

											<td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/jobs/jobDetails.do',{id:'${contractor.id}'},'POST')">
													Job(s) </a></td>
											<td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/jobs/finalList.do',{id:'${contractor.id}'},'POST')">
													Job(s) </a></td> --%>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</c:if>
			<!-- --------------------- -->
		</div>

	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>