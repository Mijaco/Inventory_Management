$(document)
		.ready(
				function() {
					$('#projectInformation').DataTable();
					document.getElementById('projectInformation_length').style.display = 'none';
					// document.getElementById('projectInformation_filter').style.display = 'none';
				});

function multipleUpdateSM() {

	var pkList = [];
	$(".productid").each(function() {
		var th = $(this);
		pkList.push(th.val());
	});

	var safetyMarginList = [];
	$(".smargin").each(function() {
		var th = $(this);
		safetyMarginList.push(th.val());
	});

	var path = $('#contextPath').val() + "/settings/update/allSafetyMargin.do";
	var param = {
		pkList : pkList,
		safetyMarginList : safetyMarginList
	}
	postSubmit(path, param, 'POST');
}

function updateSafetyMargin(index, id) {
	var safetyMargin = $("#safetyMargin" + index).val();
	var path = $('#contextPath').val() + "/settings/update/safetyMargin.do";
	var params = {
		id : id,
		safetyMargin : safetyMargin
	}
	// postSubmit(path, params ,'POST');

	var cDataJsonString = JSON.stringify(params);
	$.ajax({
		url : path,
		data : cDataJsonString,
		contentType : "application/json",
		success : function(data) {
			var pData = JSON.parse(data);
			if (pData == 'success') {
				$('.alert.alert-success').removeClass('hide');
				$(".alert.alert-success").fadeTo(5000, .5).slideUp(500, function(){
				  //  $(".alert.alert-success").alert('close');
				});
			} else {
				$('.alert.alert-danger').removeClass('hide');
				$(".alert.alert-danger").fadeTo(5000, .5).slideUp(500, function(){
				  //  $(".alert.alert-danger").alert('close');
				});
			}
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}
function changeProject() {
	var khathId = $('#khathId').val();
	var path = $('#contextPath').val() + '/settings/viewProjectInformation.do';
	var params = {
		'khathId' : khathId
	}
	postSubmit(path, params, 'POST');
}
