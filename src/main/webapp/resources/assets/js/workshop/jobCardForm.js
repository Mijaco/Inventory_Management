function woLeaveChange(element) {
	// alert($(element).val());

	var contextPath = $("#contextPath").val()
	var contractNo = $(element).val();
	$.ajax({
		url : contextPath + '/jobcard/csStoreTicketByWO.do',
		data : "{contractNo: '" + contractNo + "'}",
		contentType : "application/json",
		success : function(data) {
			var storeTicketList = JSON.parse(data);
			var csStoreTicketNos = $('.csStoreTicketNo');
			var csStoreTicketNoDiv = $('.csStoreTicketNoDiv');
			csStoreTicketNoDiv.removeClass('hidden');

			csStoreTicketNos.empty();
			// csStoreTicketNos.removeClass('hidden');

			csStoreTicketNos.append($("<option></option>").attr("value", '')
					.text('Select a Store Ticket'));
			$.each(storeTicketList, function(ticketNo, ticketNo) {
				csStoreTicketNos.append($("<option></option>").attr("value",
						this.ticketNo).text(this.ticketNo));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function csStoreTicketNoLeaveChange(element) {
	var contextPath = $("#contextPath").val()
	var csStoreTicketNo = $(element).val();
	$.ajax({
		url : contextPath + '/jobcard/transformerByST.do',
		data : "{ticketNo: '" + csStoreTicketNo + "'}",
		contentType : "application/json",
		success : function(data) {
			var transformerSerialNoList = JSON.parse(data);
			var transformerSerialNos = $('.transformerSerialNo');
			var transformerSerialNoDiv = $('.transformerSerialNoDiv');
			transformerSerialNoDiv.removeClass('hidden');

			transformerSerialNos.empty();
			// csStoreTicketNos.removeClass('hidden');

			transformerSerialNos.append($("<option></option>")
					.attr("value", '').text('Select a Transformer'));
			$.each(transformerSerialNoList, function(transformerSerialNo,
					transformerSerialNo) {
				transformerSerialNos.append($("<option></option>").attr(
						"value", this.transformerSerialNo).text(
						this.transformerSerialNo));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

/*function transformerLeaveChange(element) {
	var contextPath = $("#contextPath").val()
	var transformerSerialNo = $(element).val();
	$.ajax({
		url : contextPath + '/jobcard/jobCardByTransformerSlNo.do',
		data : "{transformerSerialNo: '" + transformerSerialNo + "'}",
		contentType : "application/json",
		success : function(data) {
			var jobCardTemplateList = JSON.parse(data);

			var table = $('<table></table>').addClass('foo');
			for (i = 0; i < jobCardTemplateList.length; i++) {
				var row = $('<tr> <td>'+ i.itemCode + '</td> </tr>').addClass('bar').text(jobCardTemplateList[i].itemCode);
				table.append(row);
			}
			var jobCardTemplateListDiv = $('#jobCardTemplateList');
			jobCardTemplateListDiv.append(table);
			jobCardTemplateListDiv.removeClass('hidden');

		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}*/

function transformerLeaveChange(element) {	
	//var jobNoDiv = $(".jobNoDiv");
	var transformerSerialNo = $(element).val();
	$("#transformerSerialNo").val(transformerSerialNo);
	//jobNoDiv.removeClass('hidden');
	
}

function checkJobNo() {
	var contextPath = $('#contextPath').val();
	//alert(contextPath);
	var saveButton = $('#saveButton');
	var workOrderDecision = $("#workOrderDecision");
	var jobNo = $('#jobNo').val();
	
	$.ajax({
		url : contextPath + '/jobcard/checkJobNo.do',
		data : {"jobNo":jobNo},
		success : function(data) {
			//var result = JSON.parse(data);
			//alert(data);
			if (data == 'success') {
				saveButton.prop("disabled", false);
				workOrderDecision.removeClass('hide');
				workOrderDecision.removeClass("glyphicon-ok-sign");
				workOrderDecision.removeClass("red");
				workOrderDecision
						.addClass("glyphicon glyphicon-ok-sign green");

			} else {
				workOrderDecision.removeClass("glyphicon-ok-sign");
				workOrderDecision.removeClass("green");
				workOrderDecision.addClass("glyphicon glyphicon-remove-sign red");
				saveButton.prop("disabled", true);
				workOrderDecision.removeClass('hide');
			}
		},
		error : function(data) {
			alert(data);
		},
		type : 'POST'
	});
}