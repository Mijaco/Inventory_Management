$(document)
		.ready(
				function() {
					$('#csStoreTicketMstListTable').DataTable({
						"order" : [ [ 0, "desc" ] ],
						"columnDefs" : [ {
							"targets" : [ 0 ],
							"visible" : false,
							"searchable" : false
						} ]
					});
					document.getElementById('csStoreTicketMstListTable_length').style.display = 'none';
					//document.getElementById('csStoreTicketMstListTable_filter').style.display = 'none';

				});

function createNewDiv() {
	document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';

}

function openStoreTicket(path, operationId, flag, storeTicketType, ticketNo) {
	var params = {
		operationId : operationId,
		flag : flag,
		storeTicketType : storeTicketType,
		ticketNo : ticketNo
	}

	postSubmit(path, params, 'POST');

}