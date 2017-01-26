/**
 * Author: Shimul
 */

$( document ).ready( function() {
	$('#saveButton').click( function() {
		
		var haserror = false;
		
		if( $('#sessionName').val() == null || $.trim( $('#sessionName').val() ) == '0' ) {
			$('.sessionName').removeClass('hide');
			haserror = true;
		} else {
			$('.sessionName').addClass('hide');
		}
		
		if( $('#iName').val() == null || $.trim( $('#iName').val() ) == '0' ) {
			$('.itName').removeClass('hide');
			haserror = true;
		} else {
			$('.itName').addClass('hide');
		}
		
		if( $('#rate').val() == null || $.trim( $('#rate').val() ) == '' ) {
			$('.rate').removeClass('hide');
			haserror = true;
		} else {
			$('.rate').addClass('hide');
		}
		
		var qCounter = 0;
		$('.quantity').each( function() {
			if( $(this).val() == null || $.trim( $(this).val() ) == '' ) {
				qCounter++;
			}
		});
		
		if( qCounter > 0 ) {
			haserror = true;
			$('.qtError').each( function() {
				$(this).removeClass('hide');
			});
			
		} else {
			
			$('.qtError').each( function() {
				$(this).addClass('hide');
			});
			
		}
		
		if( haserror == true ) {
			return;
		} else {
			var id = $('#sessionName').val();
			var itemCode = $('#itemCode').val();
			
			var baseURL = $('#contextPath').val();
			$.ajax({
				url : baseURL + "/bgt/checkItemSession.do",
				data : {"id":id, "itemCode":itemCode},
				success : function(data) {
					if( data == "success" ) { 
						if( $('.itemCode').hasClass('hide') == false ) {
							$('.itemCode').addClass('hide');	
						}
						$('form').submit();
					}
					else {
						$('.itemCode').removeClass('hide');
					}
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			}); //ajax
		}
	});
});

function categoryLeaveChange(element) {
	
	$('#itemCode').val('');
	$('#uom').val('');

	var categoryId = $('#category').val();
	// alert(categoryId);
	var baseURL = $('#contextPath').val();
	$.ajax({
		
		url : baseURL + '/bgt/loadItemList.do',
		data : "{categoryId:" + categoryId + "}",
		contentType : "application/json",
		success : function(data) {
			var itemList = JSON.parse(data);
			var itemNames = $(element).closest("div").parent().parent().find(
					'.itemName');

			itemNames.empty();

			itemNames.append($("<option></option>").attr("value", '').text(
					'Select Item'));
			$.each(itemList, function(id, itemName) {
				itemNames.append($("<option></option>").attr("value", this.id)
						.text(this.itemName));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
} //category on change

function itemLeaveChange(element) {
	if( $('.itemCode').hasClass('hide') == false ) {
		$('.itemCode').addClass('hide');	
	}
	var id = $('#iName').val();
	var baseURL = $('#contextPath').val();
	$.ajax({
		url : baseURL + '/bgt/viewInventoryItem.do',
		data : "{id:" + id + "}",
		contentType : "application/json",
		success : function(data) {
			var inventoryItem = JSON.parse(data);
			$('#itemCode').val( inventoryItem.itemId );
			$('#uom').val( inventoryItem.unitCode );
			$('#itemName').val( $('option:selected', '#iName').text() );
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
} //item on change

function calculateCost(eq) {
	var id = $(eq).attr('id');
	var name = $(eq).attr('name');
	var sequence = id.substr( name.length );
	
	var quantity = $('#quantity'+sequence).val();
	var rate = $('#rate').val();
	$('#cost'+sequence).val( quantity*rate );

	var mquantity = 0, amount = 0;
	
	$('.quantity').each( function() {
		mquantity += parseFloat( $(this).val() );
	});
	
	$('.cost').each( function() {
		amount += parseFloat( $(this).val() );
	});
	
	$('#mquantity').val( mquantity );
	$('#amount').val( amount );
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