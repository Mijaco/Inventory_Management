<%@include file="../common/wsHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ws/requisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> 
				Repair & Maintenance Target List
			</a> <input type="hidden" id="contextPath"
				value="${pageContext.request.contextPath}" />
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Target set</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/targetSave.do">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
			
					<div class="form-group">
						<label for="workOrderNo" class="col-sm-4 control-label">Contract No :</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="workOrderNo"
								placeholder="" value="" style="border: 0; border-bottom: 2px ridge;"
								name="workOrderNo">
								<%-- <select>
  <option th:each="state : ${T(com.ibcs.desco.common.model.Constrants.State).values()}" th:value="${state}" th:text="${state.displayName}"></option>
</select> --%>
						</div>
					</div>
					
				</div>
				</div>
				
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Target add:</label>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1600px;">
									<hr />
									<div class="col-xs-12">
										<div class="form-group col-sm-1 col-xs-12">
											<b>repairTarget1P</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>repairTarget3P</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>preventiveTarget1P</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>preventiveTarget3P</b>
										</div>
						
										<div class="form-group col-sm-1 col-xs-12">
											<b>Target Date</b>
										</div>
						
										<div class="form-group col-sm-1 col-xs-12">
											<b>Target Month</b>
										</div>
						
										<div class="form-group col-sm-1 col-xs-12">
											<b>Remarks</b>
										</div>
										
									</div>

									<div class="col-xs-12">
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls">
													<div class="aaa">
														<div class="col-xs-12 entry" id="myArea0">
															<div class="row">
																
																
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control repairTarget1P" id="repairTarget1P0"
																		name="repairTarget1P" type="text"
																		placeholder="Enter repairTarget1P"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control repairTarget3P" id="repairTarget3P0" name="repairTarget3P"
																		type="text" placeholder="Enter repairTarget3P"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control preventiveTarget1P" id="preventiveTarget1P0" name="preventiveTarget1P"
																		type="text" placeholder="Enter preventiveTarget1P"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control preventiveTarget3P" id="preventiveTarget3P0" name="preventiveTarget3P"
																		type="text" placeholder="Enter preventiveTarget3P"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control datepicker-15 targetDate"
																		name="targetDate" type="text" placeholder="Set date"
																		id="targetDate0" onchange="getMonth(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control strTargetMonth"
																		name="strTargetMonth" type="text"
																		id="strTargetMonth0"
																		style="border: 0; border-bottom: 2px ridge;" />											
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control remarks"
																		name="remarks" type="text"
																		id="remarks0"
																		style="border: 0; border-bottom: 2px ridge;" />											
																</div>
																
																<div class="form-group col-sm-2 col-xs-3">
																	<button class="btn btn-success btn-add" type="button">
																		<span class="glyphicon glyphicon-plus"></span>
																	</button>
																	
																	<button class="btn btn-danger btn-remove" type="button">
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


				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6" id="submit">
						<button type="submit" id="saveButton"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-lg btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="reset"
							class="width-20  pull-left btn btn-lg btn-danger"
							style="margin-left: 10px; border-radius: 6px;">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button>
					</div>
				</div>
			</div>

		</form>
		<!-- --------------------- -->
	</div>
</div>



<script
	src="${pageContext.request.contextPath}/resources/assets/js/workshop/target.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>