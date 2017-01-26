<%@include file="../../common/auctionHeader.jsp"%>

<input type="hidden" id="contextPath"
	value="${pageContext.request.contextPath}" />

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h3 class="center blue ubuntu-font"
			style="margin-top: 5px; font-style: italic;">Division wise List
			of Condemn Materials Found Physically</h3>
		<h4 class="center ubuntu-font" style="margin-top: 0;">Auction id:
			${condemnMst.auctionProcessMst.auctionName}</h4>

		<h4 class="center blue ubuntu-font" style="margin-top: 5px;">
			${condemnMst.auctionCategory.name}
			(${condemnMst.auctionCategory.description})</h4>

		<div class="alert alert-success hide" id='updateAlert'>
			<strong>Success!</strong> Information is updated.
		</div>
		<div class="alert alert-danger hide" id='updateValidationError'>
			<strong>Update Failed!</strong> Quantity can not greater than Ledger.
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty beanList}">
				<div class="col-sm-12 center">
					<p class="red ubuntu-font">
						<i>No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty beanList}">
				<table id="dataList" style="width: 2100px"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>SL</td>
							<td>Item Code</td>
							<td style="width: 150px;">Description</td>
							<td>Unit</td>
							<td>Agar</td>
							<td>Rup</td>
							<td>Moni</td>
							<td>Pall</td>
							<td>Shah</td>
							<td>Kaf</td>
							<td>Gul</td>
							<td>Joar</td>
							<td>Bad</td>
							<td>Bari</td>
							<td>Utt(E)</td>
							<td>Utt(W)</td>
							<td>Ton(E)</td>
							<td>Ton(W)</td>
							<td>D.Khan</td>
							<td>U.Khan</td>
							<td>S.Store</td>
							<td>C.Store</td>
							<td>Total</td>
							<td>Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${beanList}" var="bean" varStatus="loop">
							<tr id="row${loop.index+1}">
								<td>${loop.index+1}</td>

								<td>${bean.itemMaster.itemId}</td>
								<td>${bean.itemMaster.itemName}</td>
								<td>${bean.itemMaster.unitCode}</td>

								<td>
									<!-- Agar: 52060 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52060'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />
										</c:if>
									</c:forEach>
								</td>
								<td>
									<!-- Rup: 52055 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52055'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />
										</c:if>
									</c:forEach>
								</td>
								<td>
									<!-- Moni: 52020 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52020'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />
										</c:if>
									</c:forEach>
								</td>
								<td>
									<!-- Pall: 52025 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52025'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />
										</c:if>
									</c:forEach>
								</td>
								<td>
									<!-- Shah: 52050 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52050'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />

										</c:if>
									</c:forEach>
								</td>
								<td>
									<!-- Kaf: 52015 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52015'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />
										</c:if>
									</c:forEach>
								</td>

								<td>
									<!-- Gul: 52010 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52010'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />
										</c:if>
									</c:forEach>
								</td>
								<td>
									<!-- Joar: 52080 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52080'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />
										</c:if>
									</c:forEach>
								</td>
								<td>
									<!-- Bad: 52070 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52070'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />

										</c:if>
									</c:forEach>
								</td>

								<td>
									<!-- Bari: 52030 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52030'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />
										</c:if>
									</c:forEach>
								</td>
								<td>
									<!-- Utt(E): 52005 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52005'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />
										</c:if>
									</c:forEach>
								</td>
								<td>
									<!-- Utt(W): 52075 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52075'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />
										</c:if>
									</c:forEach>
								</td>

								<td>
									<!-- Ton(E): 52040 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52040'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />
										</c:if>
									</c:forEach>
								</td>
								<td>
									<!-- Ton(W): 52045 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52045'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />

										</c:if>
									</c:forEach>
								</td>
								<td>
									<!-- D.Khan: 52035 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52035'}">
										 	<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />										 
										 </c:if>
									</c:forEach>
								</td>

								<td>
									<!-- U.Khan: 52065 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '52065'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />

										</c:if>
									</c:forEach>
								</td>
								<td>
									<!-- S.Store: 510 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '510'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />

										</c:if>
									</c:forEach>
								</td>
								<td>
									<!-- C.Store: 505 --> <c:forEach items="${bean.dtlList}"
										var="dtl">
										<c:if test="${dtl.departments.deptId == '505'}">
											<input type="number" value="${dtl.condemnQty}" step="0.001"
												onblur="setTotalQty(${loop.index+1}, this)" data-remarks='${dtl.remarks}'
												style="width: 70px;" class="conDtl-pk" id="${dtl.id}"  />
											<input type="hidden" value="${dtl.qty}" id="qty${dtl.id}" />
										</c:if>
									</c:forEach>
								</td>
								<td id="totalQty${loop.index+1}">
								${bean.totalQty}
								</td>


								<td class="center">
								<c:choose>
								<c:when test="${condemnMst.auctionProcessMst.cc_to_admin_flag == '0'}">
								<button type="button" id="updateBtn${loop.index+1}"
											onclick="update(${loop.index+1})" style="border-radius: 6px;"
											class="width-10  btn btn-sm btn-success">
											<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">Update</span>
										</button>
								</c:when>
								<c:otherwise>
								<label class="btn btn-default">Already Updated</label>
								</c:otherwise>
								</c:choose>
								<%-- <c:if
										test="${condemnMst.auctionProcessMst.cc_to_admin_flag == '0'}">
										<button type="button" id="updateBtn${loop.index+1}"
											onclick="update(${loop.index+1})" style="border-radius: 6px;"
											class="width-10  btn btn-sm btn-success">
											<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">Update</span>
										</button>
									</c:if> --%>
								
									</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		<!-- --------------------- -->
		<div class="col-md-12 center"
			style="padding-top: 15px; margin-top: 5px;">
			<div class="col-sm-offset-5 col-sm-2">

				<c:if test="${condemnMst.auctionProcessMst.cc_to_admin_flag =='0'}">
					<button type="button" class="btn btn-md btn-primary"
						onclick="sendToAdmin(${condemnMst.id})"
						style="margin-left: 10px; border-radius: 6px;">
						<i class="fa fa-paper-plane"></i> <span class="bigger-50">Send
							to Admin</span>
					</button>
				</c:if>

				<c:if test="${condemnMst.auctionProcessMst.cc_to_admin_flag =='1'}">
					<a class="btn btn-md btn-primary" target="_blank"
						style="margin-left: 10px; border-radius: 6px;"
						href="${pageContext.request.contextPath}/report/cc/auction/ccReport.do?id=${condemnMst.id}">
						Condemn Report</a>
				</c:if>

			</div>
		</div>

	</div>
</div>


<!-- Button trigger modal -->
<button type="button" id="btn-modal" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
</button>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span onclick="setVal()" aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Please add a reason for item shortage</h4>
      </div>
      <div class="modal-body">
        <h4 class="red" id="myModalLabelMsg"></h4>
        <input type="text" id="input_remark" class="form-control">
        <input type="hidden" id="input_rowId" class="form-control">
        <input type="hidden" id="input_element" class="form-control">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default"  onclick="setVal()"  data-dismiss="modal">Ok</button>
      </div>
    </div>
  </div>
</div>


<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#dataList').DataTable({
			"columnDefs" : [ {
				"targets" : [ 0 ],
				"visible" : true,
				"searchable" : false
			} ],
			"order" : [ [ 0, 'asc' ] ],
			 "paging": false
		});
		
	});
	
	 function setTotalQty(rowId, element){
		 $('#input_rowId').val(rowId);
		 $('#input_element').val($(element).attr("id"));
		 $('#input_remark').val("");
		 $('#btn-modal').click();
		 
       };
       function setVal(){
    	   setTotalQty2();
    	  
       } 
       
	function setTotalQty2(){
		var rowId = $('#input_rowId').val();
		var element = $('#input_element').val();
		var msg = $('#input_remark').val();
		var id=element;
		var sQty=parseFloat($('#qty'+id).val());
		var cQty=parseFloat($('#'+id).val()); 
		
		if(cQty<sQty){
			//var msg = prompt("Please add a reason for item shortage.", remarks);
		    if (msg != null) {		
		    	console.log("message "+msg);
		        $('#'+id).attr('data-remarks', msg);
		    }		    
		    		  
		    if ($('#input_remark').val() != null) {	
		    	console.log("value found");
		        $('#'+id).attr('data-remarks', msg);
		    }
		}else{
			$('#'+id).attr('data-remarks', '');
		}
		
		var total = 0.0;		
		$('#row'+rowId+' .conDtl-pk').each( function() {					
			var condemnQty=$(this).val();
			total+=parseFloat(condemnQty);
		});		
		$('#totalQty'+rowId).text(total.toFixed(3));
	}
	function update(rowId){
		var contextPath= $('#contextPath').val();
		var path= contextPath + '/auction/cc/updateCondemnReport.do';		
		var objList = [];
		
		$('#row'+rowId+' .conDtl-pk').each( function() {
			var id=$(this).attr("id");			
			var condemnQty=$(this).val();
			var qty=$('#qty'+id).val();
			var comments=$('#'+id).attr("data-remarks");	
			var obj={
					id: id,
					qty: qty,
					condemnQty:condemnQty,
					remarks: comments
			};
			objList.push(obj);			
		});
		
		var params = {dtlList: objList};		
		var cDataJsonString = JSON.stringify(params);	
		//alert(cDataJsonString);
		$.ajax({			
			url : path,
			data : cDataJsonString,
			contentType : "application/json",
			success : function(data) {
				var result = JSON.parse(data);
				$('.alert.alert-success').removeClass('hide');
				$(".alert.alert-success").fadeTo(5000, 500).slideUp(500);				
			},
			error : function(data) {
				alert("From JS: Server Error");
			},
			type : 'POST'
		});
	}
	
	function sendToAdmin(id){
		var contextPath= $('#contextPath').val();
		var path= contextPath + '/auction/cc/sendToAdmin.do';
		var param={id:id};
		postSubmit(path, param, 'POST');
	}	
	
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
