$(document).ready(function() {
	$(function() {
		$("#woNumber").autocomplete({
			source : function(request, response) {
				$.ajax({
					url : 'getWOrder.do',
					type : "POST",
					data : {
						woNumber : request.term
					},
					dataType : "json",
					success : function(data) {
						response($.map(data, function(v, i) {
							return {
								label : v.contractNo,
								value : v.contractNo
							};
						}));
					}
				});
			}
		});
	});
});

$(document).ready(function() {
		$(function() {
			$("#templateName").autocomplete({
				source : function(request, response) {
								$.ajax({
									url : 'getTemplates.do',
									type : "POST",
									data : {
										templateName : request.term
									},
									dataType : "json",
									success : function(data) {
										response($.map(data,function(v,i) {
											return {
												label : v.pndNo,
												value : v.id
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
					var w = document
							.getElementById("woNumber").value;
					window.location.href = 'viewTemplateItems.do?eId='
							+ ui.item.value
							+ '&cNo=' + w;
				},
				minLength : 1
			});
		});
});

$(document).ready( function() {
	$('#ConstructionNature').change( function() {
		var value = $('#ConstructionNature').val();
		var contextPath = $('#contextPath').val();
		if( value == "createnew" ) {
			location.href = contextPath+"/job/constructionNature.do";
		}
	});
});
