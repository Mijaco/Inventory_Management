
	function generateReport(reportName, param){
		var birtAddress = $('#birtAddr').val();
		var dynamicRptUrl = '?__report=desco/procurement/requisition_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_PRF_NO=';
		var reportUrl = birtAddress + dynamicRptUrl+ param;
		$('#requisitionReportFrame').attr('src', reportUrl);
	}