function forwardToUpper(stateCode, userid) {
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		var contextPath=$("#contextPath").val();
		var justification = $('#justification').val();
		var jobPkId = $('#jobPkId').val();
		var cData = {
			pndNo : jobPkId,			
			justification : justification,
			stateCode : stateCode,
			userid : userid
		}		
		var path=contextPath+"/job/sendTo.do";		
		postSubmit(path, cData, 'POST');
	}
}

function backToLower(stateCode, userid) {
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		var contextPath=$("#contextPath").val();
		var justification = $('#justification').val();
		var jobPkId = $('#jobPkId').val();
		var cData = {
			pndNo : jobPkId,			
			justification : justification,
			stateCode : stateCode,
			userid : userid
		}		
		var path=contextPath+"/job/backTo.do";		
		postSubmit(path, cData, 'POST');
	}
}

function approvedJobAssign(){
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		var contextPath=$("#contextPath").val();
		var justification = $('#justification').val();
		var jobPkId = $('#jobPkId').val();
		var cData = {
			pndNo : jobPkId,			
			justification : justification
		}		
		var path=contextPath+"/job/assignJobApproved.do";		
		postSubmit(path, cData, 'POST');
	}
}