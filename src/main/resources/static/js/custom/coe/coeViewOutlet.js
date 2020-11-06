		$('#dropUnique select').on(
				'change',
				function(e) { //$dropdown2.empty().append($dropdown1.find('option').clone());
					var selectedItem = $(this).val();
					$('#dropUnique select').removeClass('abc');
					$(this).addClass('abc');
					$('#dropUnique select').each(
							function() {
								if ($(this).hasClass("abc")) {
									return true;
								} else {
									$(this).find(
											'option[value="' + selectedItem
													+ '"]').remove();
								}
							})
				});
		var row_index;
		var col_index;
		$('td').click(function() {
			row_index = $(this).parent().index('tr');
			col_index = $(this).index('tr:eq(' + row_index + ') td');
		});

		var arr = $('#tbl > tbody > tr').map(function() {
			return $(this).children().map(function() {
				return $(this);
			});
		});
		function onChange(code) {
			var codeObj = code;
			var selectBox = code;
			var selected = selectBox.options[selectBox.selectedIndex].value;
			$.ajax({
				type : "GET",
				contentType : "application/json; charset=utf-8",
				url : "getOutletName.htm?code=" + selected,
				success : function(data) {
					var jObj = jQuery(codeObj);
					jObj.parent('td').next('td').children('input').val(data);
				}
			});
		}
 $(document).ready(function() {
	$('#kamtrackmodal').on('show.bs.modal', function (e) {
		var link = $(e.relatedTarget);
		   $(this).find(".kam-track-preview").load(link.attr("href"));
		});
	
});
