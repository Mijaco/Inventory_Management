/**
 * Author: Shimul
 */
function editAction(eq) {
	$('.editbtn'+eq).addClass('hide');
	$('.updatebtn'+eq).removeClass('hide');

	$('.qty'+eq).removeAttr('readonly');
	$('.qty'+eq).css({
		'outline':'2px solid green'
	});
}

function updateAction(eq, dtlId) {

	var oqty = $('.oqty'+eq).val();
	var nqty = $('.qty'+eq).val();

	if( oqty == nqty ) { }
	else {
		var totalCost = $('.amount'+eq).val();
		var mquantity = $('#mquantity').val();
		var mamount = $('#mamount').val();

		var baseURL = $('#contextPath').val();
		$.ajax({
			url : baseURL + "/bgt/updateItemInfo.do",
			data : {"id":dtlId, 'qty':mquantity, "totalCost":mamount, 'unitCost':totalCost},
			success : function(data) {
				if( data == "success" ) { 
					$('.oqty'+eq).val( nqty );
					$('#omquantity').val( mquantity );
					$('#omamount').val( mamount );
					$('.alert.alert-success').removeClass('hide').fadeTo(2000, 500).slideUp(500, function() { });
				}
				else {
					$('.alert.alert-danger').removeClass('hide').fadeTo(2000, 500).slideUp(500, function() { });
				}
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}

	$('.editbtn'+eq).removeClass('hide');
	$('.updatebtn'+eq).addClass('hide');
	$('.qty'+eq).attr('readonly', 'readonly');
	$('.qty'+eq).css({
		'outline':'0'
	});
}

function calcTotal(eq) {
	var qty = $('.qty'+eq).val();
	var rate = parseFloat( $('#rate').val() );
	$('.amount'+eq).val(qty*rate);
	var oqty = $('.oqty'+eq).val();

	var mquantity = parseFloat( $('#omquantity').val() );
	var mamount = parseFloat( $('#omamount').val() );

	var fqty = parseFloat( qty - oqty );
	mquantity = mquantity + fqty;
	mamount = mamount + parseFloat( fqty * rate );

	$('#mquantity').val( mquantity );
	$('#mamount').val( mamount );
}

function deleteItemFromBudget(id) {
	if( confirm( "Do you want to delete this Cost Centre information?" ) == true ) {
		var baseURL = $('#contextPath').val();
		var path = baseURL + "/bgt/deleteItemFromDtl.do";

		var param = {
				'id': id
		}
		postSubmit(path, param, "POST");
	}
}

$(function() {
	$("#myDialogeBox").dialog({
		autoOpen : false,
		closeOnEscape : false,
		modal : true,
		draggable : false,
		resizable : false,
		position : {
			my : "center",
			at : "center",
			of : window
		},
		show : {
			effect : "blind",
			duration : 10
		},
		hide : 'blind',
		width : $(window).width() - 100,
		buttons : [ {
			text : 'Save',
			click : function() {
				// send post request
				$("#addMoreCostCentre").submit();
				// $('#myDialogeBox').empty();
			}
		}, {
			text : 'Close',
				click : function() {
					$(this).dialog("close");
				}
			} ]
		});
	});

//Show modal
function openDialoge() {
	$('#myDialogeBox').removeClass('hide');
	$(".ui-dialog-titlebar").hide();
	$("#myDialogeBox").dialog("open");
}

$(function() {
	$(document)
			.on(
					'click',
					'.btn-add',
					function(e) {
						// e.preventDefault();

						var num = $('.clonedArea').length;
						var newNum = num + 1;

						var controlForm = $('.controls div:first'), currentEntry = $(
								this).parents('.entry:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'myArea' + newNum).addClass(
										'clonedArea')).appendTo(controlForm);

						// set dynamic id on item qty fields

						var mainDiv = document
								.getElementById('myArea' + newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];

						// start of seting id on costCentre fields
						var costCentreDiv = childDiv
								.getElementsByTagName('div')[0], costCentreInput = costCentreDiv
								.getElementsByTagName('select')[0];
						costCentreInput.setAttribute('id', 'costCentre'
								+ newNum);
						// end of seting id on costCentre fields

						// start of seting id on quantity fields
						var quantityDiv = childDiv
								.getElementsByTagName('div')[1], quantityInput = quantityDiv
								.getElementsByTagName('input')[0];
						quantityInput.setAttribute('id',
								'quantity' + newNum);
						// end of seting id on quantity fields

						// start of seting id on cost fields
						var costDiv = childDiv
								.getElementsByTagName('div')[2], costInput = costDiv
								.getElementsByTagName('input')[0];
						costInput.setAttribute('id', 'cost'
								+ newNum);
						// end of seting id on cost fields

						newEntry.find('input').val('');
//						newEntry.find('input[type=number]').val('0');

						/*controlForm
								.find('.entry:not(:last) .btn-add')
								.removeClass('btn-add')
								.addClass('btn-remove')
								.removeClass('btn-success')
								.addClass('btn-danger')
								.html(
										'<span class="glyphicon glyphicon-minus"></span>');*/
					}).on('click', '.btn-remove', function(e) {
				$(this).parents('.entry:first').remove();

				// e.preventDefault();
				return false;
			});
});

function calculateCost(eq) {
	var id = $(eq).attr('id');
	var name = $(eq).attr('name');
	var sequence = id.substr( name.length );
	
	var quantity = $('#quantity'+sequence).val();
	var rate = $('#rate').val();
	$('#cost'+sequence).val( quantity*rate );

//	var mquantity = 0, amount = 0;
	
	/*$('.quantity').each( function() {
		mquantity += parseFloat( $(this).val() );
	});
	
	$('.cost').each( function() {
		amount += parseFloat( $(this).val() );
	});
	
	var mQ = parseFloat( $('#omquantity').val() );
	var mA = parseFloat( $('#omamount').val() );
	
	$('#mquantity').val( mQ + mquantity );
	$('#mamount').val( mA + amount );*/
}