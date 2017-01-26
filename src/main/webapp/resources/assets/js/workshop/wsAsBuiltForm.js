function contractNoLeaveChange(element) {

	/*var temp = $(element).parent().attr("id");
	 alert(temp);
	var sequence = temp.substr(temp.length - 1)
*/
	//alert(document.getElementById(element).value);
	//alert($(element).closest("div").parent().parent().find('.contractNo').attr('id'));
	var contract=$(element).closest("div").parent().parent().find('.contractNo').attr('id')
	//var e = document.getElementById('' + sequence);
	/*var contractNo = e.options[e.selectedIndex].value;*/
	var contractNo = document.getElementById(contract).value;
	 //alert(contractNo);
	$.ajax({

		url : 'viewJobNoContractNo.do',
		data : "{contractNo:" + contractNo + "}",
		contentType : "application/json",
		success : function(data) {
			var jobList = JSON.parse(data);
			
			/*var jobNos = $('#pndJobMst');*/
			var jobNos = $('#pndJobMst');
			//var selectable = $(".ms-selectable");
			//var jobNos = $(element).closest("div").parent().parent().find(
				//	'.jobNo');
			jobNos.empty();

jobNos.append($("<option></option>").attr("value", '').text(
					'Select Item'));
			$.each(jobList, function(jobNo, jobNo) {
				jobNos.append($("<option></option>").attr("value", this.jobNo)
						.text(this.jobNo));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

$(document)
		.ready(
				function() {
					$(function() {

						$("#jobNo")
								.autocomplete(
										{
											source : function(request, response) {
												 //alert(request.term);
												$
														.ajax({
															url : 'getJobItems.do',
															type : "POST",
															data : {
																jobNo : request.term
															},

															dataType : "json",

															success : function(
																	data) {
																response($
																		.map(
																				data,
																				function(
																						v,
																						i) {
																					return {
																						label : v.jobNo,
																						value : v.jobNo
																					};

																				}));
															}

														});

											},
											response : function(event, ui) {
												if (!ui.content.length) {
													var noResult = {
														value : "",
														label : "No matching your request"
													};
													ui.content.push(noResult);
												}
											},
											select : function(event, ui) {
												// alert(ui.item.label);
												// alert(ui.item.value);
												//var a=document.getElementById("woNumber").value;
												
												window.location.href = 'viewJobItems.do?jobNo='
													+ ui.item.value;
											},
											minLength : 1
										});
					});
				});

 

