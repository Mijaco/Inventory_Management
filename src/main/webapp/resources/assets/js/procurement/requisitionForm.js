var today = new Date();
var dd = today.getDate();
var mm = today.getMonth() + 1; // January is 0!
var yyyy = today.getFullYear();

if (dd < 10) {
	dd = '0' + dd
}
if (mm < 10) {
	mm = '0' + mm
}
// var today = dd+'/'+mm+'/'+yyyy;
var today = yyyy + '-' + mm + '-' + dd;

$(requisitionDate).val(today);

$(function() {
	$(document).on(
			'click',
			'.btn-add',
			function(e) {
				// e.preventDefault();

				var num = $('.clonedArea').length;
				var newNum = num + 1;

				var controlForm = $('.controls div:first'), currentEntry = $(
						this).parents('.entry:first'), newEntry = $(
						currentEntry.clone().attr('id', 'myArea' + newNum)
								.addClass('clonedArea')).appendTo(controlForm);

				newEntry.find('input').val('');
				controlForm.find('.entry:not(:last) .btn-add').removeClass(
						'btn-add').addClass('btn-remove').removeClass(
						'btn-success').addClass('btn-danger').html(
						'<span class="glyphicon glyphicon-minus"></span>');
			}).on('click', '.btn-remove', function(e) {
		$(this).parents('.entry:first').remove();

		// e.preventDefault();
		return false;
	});
});

function itemLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1)
	// $(element).closest("div").parent().parent().find('.itemCode').val(new
	// Date().getSeconds());
	// $(element).closest("div").parent().parent().find('.uom').val(new
	// Date().getSeconds());

	$(element).closest("div").parent().parent().find('.itemName').attr('id',
			sequence);
	var e = document.getElementById('' + sequence);
	var item_id = e.options[e.selectedIndex].value;
	$
			.ajax({
				//url : 'procurement/requisition/viewInventoryItem.do',
				url : 'viewInventoryItem.do',
				data : "{id:" + item_id + "}",
				contentType : "application/json",
				success : function(data) {
					var inventoryItem = JSON.parse(data);
					$(element).closest("div").parent().parent().find(
							'.itemCode').val(
							inventoryItem.itemId);
					$(element).closest("div").parent().parent().find('.uom')
							.val(inventoryItem.unitCode);
					$(element).closest("div").parent().parent().find(
							'.itemNameHideField').val(
							inventoryItem.itemName);
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
}
