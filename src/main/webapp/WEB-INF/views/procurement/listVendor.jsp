<%@include file="../common/procurementHeader.jsp"%>
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
	/* background-color: none;
	width: 100%;
	height: 100%; */
}
</style>
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br>
	<span class="orange">Loading...</span>
</div>
	<div class="container-fluid icon-box"
		style="background-color: #858585;">
		<div class="row"
			style="background-color: white; padding: 10px; padding-left: 20px">
			<h4>
				<a href="#" style="text-decoration: none;">Vendors</a> / List
			</h4>
			<div class="o_form_buttons_edit" style="display: block;">
				<a href="${pageContext.request.contextPath}/createVendor.do"
					style="text-decoration: none;" class="btn btn-primary btn-sm">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					Create Vendor
				</a>
				<button accesskey="D" class="btn btn-info btn-sm" type="button">
					Discard</button>
			</div>
		</div>

		<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
			<!-- --------------------- -->

			<c:if test="${!empty vendorList}">
				<table id="dynamic-table"
					class="table table-striped table-hover">
					<thead>
						<tr>
							<th><input type="checkbox" class="btnSelectAll" /></th>
							<th>Name</th>
							<th>Company</th>
							<th>Address</th>
							<th>Website</th>
							<th>Job Possition</th>
							<!-- <th>Phone</th> -->
							<th>Mobile</th>
							<!-- <th>Fax</th> -->
							<th>Email</th>
							<th>Title</th>
							<th>Language</th>
							<!-- <th>Tag</th> -->
							<th style="width: 110px">Action</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${vendorList}" var="vendor">
							<tr>		
								
							
								<th><input type="checkbox" name="vendor" /></th>
								<td><c:out value="${vendor.name}" /></td>
								<td><c:out value="${vendor.company}" /></td>
								<td><c:out value="${vendor.address}" /></td>
								<td><c:out value="${vendor.website}" /></td>
								<td><c:out value="${vendor.jobpos}" /></td>
								<%-- <td><c:out value="${vendor.phone}" /></td> --%>
								<td><c:out value="${vendor.mobile}" /></td>
								<%-- <td><c:out value="${vendor.fax}" /></td> --%>
								<td><c:out value="${vendor.email}" /></td>
								<td><c:out value="${vendor.title}" /></td>
								<td><c:out value="${vendor.language}" /></td>
								<%-- <td><c:out value="${vendor.tag}" /></td> --%>
								<td>
									<div class="hidden-sm hidden-xs action-buttons">
										<a href="#" data-toggle="modal"
											data-target="#editModal" class="vendorModal"  onclick="editvendor(${vendor.id})"> 
											<i class="ace-icon fa fa-pencil bigger-130"></i>
										</a> 
										
										<!-- <a class="" href="#"> <i class="ace-icon fa fa-trash-o bigger-130"></i> </a> -->
										
										 <a class="red showVendorModal" href="#" data-toggle="modal" data-target="#showModal" class="vendorModal" onclick="showvendor(${vendor.id})"> 
										 	<i  class="glyphicon glyphicon-eye-open"></i>
										</a>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<!-- --------------------- -->

			
			<div id="showModal" class="modal fade showModal" role="dialog">
				<div class="modal-dialog modal-lg">

					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title center"> Vendor Information</h4>
						</div>

						<!-- --------------------- -->
						<div class="row"
							style="background-color: white; padding: 5px; padding-left: 10px; margin-top: 5px; margin-bottom: 5px; margin-left: 15px; margin-right: 5px;">

							<form method="POST"
								action="${pageContext.request.contextPath}/procurement/vendor/save.do"
								commandName="vendor">
								<div class="oe_title">

									<div class="o_radio_item">
										<input class="o_form_radio" type="radio" id="radio42_person"
											onclick="javascript:yesnoChecked();" name="radio42"
											value="person" checked disabled="disabled" /> <label class="o_form_label"
											for="radio42_person" style="margin-right: 10px"><b>Individual
										</b></label> <input class="o_form_radio" type="radio" id="radio42_company"
											name="radio42"
											value="company" disabled="disabled"/> <label class="o_form_label"
											for="radio42_company"><b>Company</b></label>
									</div>


									<input class="o_form_input o_form_field o_form_required"
										id="modal_vendor_name" placeholder="Name" type="text"
										name="name" readonly="readonly"
										style="border: 0; border-bottom: 2px ridge; width: 60%; font-size: 2em" />

									<input class="o_form_input o_form_field o_form_required"
										id="modal_vendor_company" placeholder="Company" type="text"
										name="company" readonly="readonly"
										style="border: 0; border-bottom: 2px ridge; width: 60%; margin-bottom: 20px" />

									<div class="col-md-6">
										<div class="form-group">
											<label for="modal_vendor_address"
												class="col-sm-2 control-label">Address</label>
											<div class="col-sm-10">
												<input type="text" class="form-control"
													id="modal_vendor_address" placeholder="Address 1"
													style="border: 0; border-bottom: 2px ridge;"
													name="address1" readonly="readonly"/> <input type="text" class="form-control"
													placeholder="Address 2" id="modal_vendor_address1"
													style="border: 0; border-bottom: 2px ridge;"
													name="address2" readonly="readonly"/> <input type="text" class="form-control"
													placeholder="Address 3" id="modal_vendor_address2"
													style="border: 0; border-bottom: 2px ridge;"
													name="address3" readonly="readonly"/>
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_website"
												class="col-sm-2 control-label">Website</label>
											<div class="col-sm-10">
												<input type="text" class="form-control" readonly="readonly"
													id="modal_vendor_website" placeholder="www.example.com"
													style="border: 0; border-bottom: 2px ridge;" name="website" />
											</div>
										</div>
									</div>

									<div class="col-md-6">

										<div class="form-group">
											<label for="modal_vendor_jobpos"
												class="col-sm-3 control-label">Job Position</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" readonly="readonly"
													id="modal_vendor_jobpos" placeholder="ex: Programmer"
													style="border: 0; border-bottom: 2px ridge;" name="jobpos">
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_phone"
												class="col-sm-3 control-label">Phone</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" readonly="readonly"
													id="modal_vendor_phone" placeholder="Phone Number"
													style="border: 0; border-bottom: 2px ridge;" name="phone" />
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_mobile"
												class="col-sm-3 control-label">Mobile</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"
													id="modal_vendor_mobile" readonly="readonly"
													style="border: 0; border-bottom: 2px ridge;" name="mobile" />
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_fax" class="col-sm-3 control-label">Fax</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"
													id="modal_vendor_fax" readonly="readonly"
													style="border: 0; border-bottom: 2px ridge;" name="fax" />
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_email"
												class="col-sm-3 control-label">Email</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"
													id="modal_vendor_email" readonly="readonly"
													style="border: 0; border-bottom: 2px ridge;" name="email" />
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_title"
												class="col-sm-3 control-label">Title</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"
													id="modal_vendor_title" readonly="readonly"
													style="border: 0; border-bottom: 2px ridge;" name="title" />
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_language"
												class="col-sm-3 control-label">Language</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"
													id="modal_vendor_language" readonly="readonly"
													style="border: 0; border-bottom: 2px ridge;"
													name="language" />
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_tag" class="col-sm-3 control-label">Tag</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"
													id="modal_vendor_tag" readonly="readonly"
													style="border: 0; border-bottom: 2px ridge;" name="tag" />
											</div>
										</div>
										<input type="text" hidden="true" id="modal_vendor_id"
											name="id" />

									</div>
								</div>								 
							</form>
						</div>
					</div>

				</div>
			</div>

			<!-- --------------------- -->

						
			<div id="editModal" class="modal fade editModal" role="dialog">
				<div class="modal-dialog modal-lg">

					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title center">Edit Vendor</h4>
						</div>

						<!-- --------------------- -->
						<div class="row"
							style="background-color: white; padding: 5px; padding-left: 10px; margin-top: 5px; margin-bottom: 5px; margin-left: 15px; margin-right: 5px;">

							<form method="POST"
								action="${pageContext.request.contextPath}/procurement/vendor/save.do"
								commandName="vendor">
								<div class="oe_title">

									<div class="o_radio_item">
										<input class="o_form_radio" type="radio" id="radio42_person1"
											onclick="javascript:yesnoCheck();" name="radio42"
											value="person" checked> <label class="o_form_label"
											for="radio42_person1" style="margin-right: 10px"><b>Individual
										</b></label> <input class="o_form_radio" type="radio" id="radio42_company"
											onclick="javascript:yesnoCheck();" name="radio42"
											value="company"> <label class="o_form_label"
											for="radio42_company"><b>Company</b></label>
									</div>


									<input class="o_form_input o_form_field o_form_required"
										id="modal_vendor_name1" placeholder="Name" type="text"
										name="name"
										style="border: 0; border-bottom: 2px ridge; width: 60%; font-size: 2em" />

									<input class="o_form_input o_form_field o_form_required"
										id="modal_vendor_company1" placeholder="Company" type="text"
										name="company"
										style="border: 0; border-bottom: 2px ridge; width: 60%; margin-bottom: 20px" />

									<div class="col-md-6">
										<div class="form-group">
											<label for="modal_vendor_address_1"
												class="col-sm-2 control-label">Address</label>
											<div class="col-sm-10">
												<input type="text" class="form-control"
													id="modal_vendor_address_1" placeholder="Address 1"
													style="border: 0; border-bottom: 2px ridge;"
													name="address1" /> <input type="text" class="form-control"
													placeholder="Address 2" id="modal_vendor_address1_1"
													style="border: 0; border-bottom: 2px ridge;"
													name="address2" /> <input type="text" class="form-control"
													placeholder="Address 3" id="modal_vendor_address2_1"
													style="border: 0; border-bottom: 2px ridge;"
													name="address3" />
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_website1"
												class="col-sm-2 control-label">Website</label>
											<div class="col-sm-10">
												<input type="text" class="form-control"
													id="modal_vendor_website1" placeholder="www.example.com"
													style="border: 0; border-bottom: 2px ridge;" name="website" />
											</div>
										</div>

										<div class="form-group" style="margin-top: 20em;">
											<button type="reset" class="width-20 pull-left btn btn-sm" id="mdlRstBtn">
												<i class="ace-icon fa fa-refresh"></i> <span
													class="bigger-50">Reset</span>
											</button>
											<button type="submit" style="margin-left: 10px;"  id="mdlSaveBtn"
												class="width-20 pull-left btn btn-sm btn-success">
												<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Update</span>
											</button>
										</div>

									</div>

									<div class="col-md-6">

										<div class="form-group">
											<label for="modal_vendor_jobpos1"
												class="col-sm-3 control-label">Job Position</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"
													id="modal_vendor_jobpos1" placeholder="ex: Programmer"
													style="border: 0; border-bottom: 2px ridge;" name="jobpos">
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_phone1"
												class="col-sm-3 control-label">Phone</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"
													id="modal_vendor_phone1" placeholder="Phone Number"
													style="border: 0; border-bottom: 2px ridge;" name="phone" />
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_mobile1"
												class="col-sm-3 control-label">Mobile</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"
													id="modal_vendor_mobile1"
													style="border: 0; border-bottom: 2px ridge;" name="mobile" />
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_fax1" class="col-sm-3 control-label">Fax</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"
													id="modal_vendor_fax1"
													style="border: 0; border-bottom: 2px ridge;" name="fax" />
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_email1"
												class="col-sm-3 control-label">Email</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"
													id="modal_vendor_email1"
													style="border: 0; border-bottom: 2px ridge;" name="email" />
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_title1"
												class="col-sm-3 control-label">Title</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"
													id="modal_vendor_title1"
													style="border: 0; border-bottom: 2px ridge;" name="title" />
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_language1"
												class="col-sm-3 control-label">Language</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"
													id="modal_vendor_language1"
													style="border: 0; border-bottom: 2px ridge;"
													name="language" />
											</div>
										</div>

										<div class="form-group">
											<label for="modal_vendor_tag1" class="col-sm-3 control-label">Tag</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"
													id="modal_vendor_tag1"
													style="border: 0; border-bottom: 2px ridge;" name="tag" />
											</div>
										</div>


										<input type="text" hidden="true" id="modal_vendor_id1"
											name="id" />

									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>

			<!-- --------------------- -->
			
		</div>
	</div>

<script>
	$(".btnSelectAll").click(function() {
		if ($("input[name='vendor']").prop('checked') == true) {
			$("input[name='vendor']").prop('checked', false);
		} else {
			$("input[name='vendor']").prop('checked', true);
		}
	});
</script>


<script type="text/javascript">
	$(function() {
		$("#dynamic-table").DataTable({
			restoreposive: true
		});
	});
</script>


<script>
	function showvendor(id){			
		
		$.ajax({								
			url : '${pageContext.request.contextPath}/procurement/vendor/viewVendorInfo.do',
			data : "{id:"+id+"}",
			contentType : "application/json",
			success : function(data) {									
				var vendor = JSON.parse(data);	
					$("#modal_vendor_name").val(vendor.name);
					$("#modal_vendor_company").val(vendor.company);
					$("#modal_vendor_address").val(vendor.address1);
					$("#modal_vendor_address1").val(vendor.address2);
					$("#modal_vendor_address2").val(vendor.address3);
					$("#modal_vendor_website").val(vendor.website);
					$("#modal_vendor_jobpos").val(vendor.jobpos);
					$("#modal_vendor_mobile").val(vendor.mobile);
					$("#modal_vendor_phone").val(vendor.phone);
					$("#modal_vendor_fax").val(vendor.fax);
					$("#modal_vendor_email").val(vendor.email);
					$("#modal_vendor_title").val(vendor.title);
					$("#modal_vendor_tag").val(vendor.tag);
					$("#modal_vendor_language").val(vendor.language);										
					
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}

</script>
	
<script>
		function yesnoCheck() {
			if (document
					.getElementById('radio42_person1').checked) {
				document.getElementById('modal_vendor_company1').value = "";
				document.getElementById('modal_vendor_company1').style.visibility = 'visible';
			} else {
				document.getElementById('modal_vendor_company1').value = "";
				document.getElementById('modal_vendor_company1').style.visibility = 'hidden';
			}
		}
</script>

<script>
	function editvendor(id){							
		$.ajax({
			url : '${pageContext.request.contextPath}/procurement/vendor/viewVendorInfo.do',
			data : "{id:"+id+"}",
			contentType : "application/json",
			success : function(data) {									
				var vendor = JSON.parse(data);									
					$("#modal_vendor_name1").val(vendor.name);
					$("#modal_vendor_company1").val(vendor.company);
					$("#modal_vendor_address_1").val(vendor.address1);
					$("#modal_vendor_address1_1").val(vendor.address2);
					$("#modal_vendor_address2_1").val(vendor.address3);
					$("#modal_vendor_website1").val(vendor.website);
					$("#modal_vendor_jobpos1").val(vendor.jobpos);
					$("#modal_vendor_mobile1").val(vendor.mobile);
					$("#modal_vendor_phone1").val(vendor.phone);
					$("#modal_vendor_fax1").val(vendor.fax);
					$("#modal_vendor_email1").val(vendor.email);
					$("#modal_vendor_title1").val(vendor.title);
					$("#modal_vendor_tag1").val(vendor.tag);
					$("#modal_vendor_language1").val(vendor.language);		
					$("#modal_vendor_id1").val(vendor.id);										
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}			
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

	<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
