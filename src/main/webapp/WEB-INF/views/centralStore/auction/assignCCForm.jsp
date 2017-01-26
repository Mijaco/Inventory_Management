<%@include file="../../common/auctionHeader.jsp"%>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<input type="hidden" id="contextPath"
	value="${pageContext.request.contextPath}" />

<!-- -------------------End of Header-------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<a href="${pageContext.request.contextPath}/auction/admin/cccList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>CC
				Convener List
			</a>
		</div>
		<h2 class="col-md-8 center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Assign
			Condemn Committee Convener</h2>
	</div>

	<div class="col-md-12"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/auction/addCcConvenerCommittee.do"
			enctype="multipart/form-data">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="condemnMstId"
							class="col-sm-4 control-label text-right">Auction Id : </label>
						<div class="col-sm-8">

							<c:if test="${!empty condemnMstList}">
								<c:forEach items="${condemnMstList}" var="m">
									<c:if test="${m.auctionProcessMst.id==apId}">
										<input type="hidden" name="condemnMstId" value="${apId}"
											id="apId" />
										<span style="font-weight: bold;">${m.auctionProcessMst.auctionName}
											- ${m.auctionCategory.name}</span>
									</c:if>
								</c:forEach>
							</c:if>
							<%-- <select class="form-control auctionName" name="condemnMstId"
								required="required" id="condemnMstId"
								style="border: 0; border-bottom: 2px ridge;">
								<c:if test="${!empty condemnMstList}">
									<option value="">Select an Auction Id</option>
									<c:forEach items="${condemnMstList}" var="m">
										<option value="${m.id}" ${m.auctionProcessMst.id==apId? 'selected':''}>${m.auctionProcessMst.auctionName}
											-- ${m.auctionCategory.name}</option>
									</c:forEach>
								</c:if>
							</select> --%>
						</div>
					</div>

					<%-- <div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="userId"
							class="col-sm-4 col-md-4 control-label text-right">Convener
							Name: </label>
						<div class="col-sm-8">
							<select class="form-control userId" name="userId"
								required="required" id="userId"
								style="border: 0; border-bottom: 2px ridge;">
								<c:if test="${!empty convenerList}">
									<option value="">Select a convener</option>
									<c:forEach items="${convenerList}" var="c">
										<option value="${c.authUser.id}">${c.authUser.name},
											${c.authUser.designation}</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div> --%>




					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="memoNo" class="col-sm-4 control-label text-right">
							Memo No :</label>
						<div class="col-sm-8">
							<input class="form-control" name="memoNo"
								style="border: 0; border-bottom: 2px ridge;" id="memoNo"
								required="required" />
						</div>
					</div>
					
					
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="remarks" class="col-sm-4 control-label text-right">
							Remarks:</label>
						<div class="col-sm-8">
							<input class="form-control" name="remarks"
								style="border: 0; border-bottom: 2px ridge;" id="remarks"
								required="required" />
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="ccDate"
							class="col-sm-4 col-md-4 control-label text-right">Committee Formulation Date:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" readonly="readonly"
								class="form-control datepicker-15" id="ccDate"
								style="border: 0; border-bottom: 2px ridge;" name="ccDate" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;</div>
					<div class="form-group">
						<label for="referenceDoc"
							class="col-sm-4 col-md-4 control-label text-right">Upload
							Condemn Committee Doc. </label>
						<div class="col-sm-8 col-md-8">
							<input type="file" id="referenceDoc" accept="application/pdf"
								class='form-control' name="referenceDoc" />
						</div>
					</div>


				</div>
<div class="col-md-12 col-sm-12">
						<div class="form-group" style="margin-top: 1em;">
							<p class="col-sm-12 btn btn-primary btn-sm">Committee Member Assign</p>
							<div class="col-xs-12 table-responsive">
								<div class="table">
									<div style="width: 1700px;">
										<div class="col-xs-12">
											<div class="form-group col-xs-4">
												<b>Person</b>
											</div>
											<div class="form-group col-xs-3">
												<b>Member Type</b>
											</div>
											
										</div>

										<div class="col-xs-12">
											<div class="row">
												<div class="control-group" id="fields">
													<div class="controls">
														<div class="aaa">
															<div class="col-xs-12 entry" id="myArea0">
																<div class="row">
																	<div class="form-group col-xs-4">
																		<select class="form-control category" id="category0"
																			onchange="categoryLeaveChange(this)" name="userId"
																			style="border: 0; border-bottom: 2px ridge;">
																			<option selected>--Select Member--</option>
																			<c:if test="${!empty convenerList}">
																				<%-- <c:forEach items="${categoryList}" var="category">
																					<option value="${category.categoryId}">${category.categoryId}
																						- ${category.categoryName}</option>
																				</c:forEach> --%>
																				<c:forEach items="${convenerList}" var="c">
																					<option value="${c.authUser.id}">${c.authUser.name},
																						${c.authUser.designation}</option>
																				</c:forEach>
																			</c:if>
																		</select>
																	</div>

																	<div class="form-group col-xs-3">
																		<input type="hidden" name="description"
																			id="description0" class="description" /> 
																			<!-- <select disabled
																			class="form-control itemName" id="itemName0"
																			onchange="itemLeaveChange(this)" name="itemName"
																			style="border: 0; border-bottom: 2px ridge;"> -->
																			<select class="form-control itemName" id="itemName0"
																			onchange="itemLeaveChange(this)" name="memberType"
																			style="border: 0; border-bottom: 2px ridge;">
																			<option value="Secretary" >Secratory</option>
																			<option value="Chairman" >Chairman</option>
																			<option value="Member" selected>Member</option>
																		</select>
																	</div>
																	
																	
																	<div class="form-group col-xs-1">
																		<button class="btn btn-success btn-add" type="button">
																			<span class="glyphicon glyphicon-plus"></span>
																		</button>
																		<button class="btn btn-danger btn-remove"
																			type="button">
																			<span class="glyphicon glyphicon-minus"></span>
																		</button>

																	</div>
																	
																	
																</div>

															</div>
														</div>
														<!-- ---------------------- -->


													</div>
												</div>
											</div>
										</div>
										<hr />

									</div>
								</div>
							</div>
							

						</div>

					</div>
				<div class="col-md-12"
					style="padding-top: 15px; padding-bottom: 10px; margin: 15px 0;">
					<div class="col-xs-12 col-sm-6">
						<button type="submit" id="submitBtn"
							style="margin-right: 10px; border-radius: 6px;"
							class="pull-right btn btn-sm btn-primary"">
							<i class="fa fa-paper-plane"></i> Send to Convener
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="reset"
							class="width-20  pull-left btn btn-sm btn-danger"
							style="margin-left: 10px; border-radius: 6px;">
							<i class="ace-icon fa fa-refresh"></i> Reset
						</button>
					</div>
				</div>
			</div>

		</form>
		<!-- --------------------- -->
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		var apId = $('#apId').val();
		if (typeof (apId) == "undefined" || apId == null || apId == '') {
			$('#submitBtn').attr('disabled', true);
		}

	});
	
	$(document)
	.on(
			'click',
			'.btn-add',
			function(e) {
				var num = $('.clonedArea').length;
				var newNum = num + 1;
				var controlForm = $('.controls div:first'), currentEntry = $(
						this).parents('.entry:first'), newEntry = $(
						currentEntry.clone().attr('id',
								'myArea' + newNum).addClass(
								'clonedArea')).appendTo(controlForm);

				var mainDiv = document
						.getElementById('myArea' + newNum), childDiv = mainDiv
						.getElementsByTagName('div')[0];
				// start of setting id on category fields
				var categoryDiv = childDiv.getElementsByTagName('div')[0], categoryInput = categoryDiv
						.getElementsByTagName('select')[0];
				categoryDiv.setAttribute('disabled', '');
				categoryInput.setAttribute('id', 'category' + newNum);

				// end of setting id on category fields

				// start of setting id on description and itemName
				var itemNameDiv = childDiv.getElementsByTagName('div')[1], descriptionInput = itemNameDiv
						.getElementsByTagName('input')[0], itemNameInput = itemNameDiv
						.getElementsByTagName('select')[0];
				descriptionInput.setAttribute('id', 'description'
						+ newNum);
				itemNameInput.setAttribute('id', 'itemName' + newNum);
				// end of setting id on description and itemName
				
			})
	.on(
			'click',
			'.btn-remove',
			function(e) {
				if ($('.entry').length > 1) {				

					$(this).parents('.entry:first').remove();
					return false;
				}

			});

</script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>