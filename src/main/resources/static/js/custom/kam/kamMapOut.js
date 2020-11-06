		$('#dropUnique select').on(
				'change',
				function(e) { //$dropdown2.empty().append($dropdown1.find('option').clone());
					var selectedItem = $(this).val();
					
					if(selectedItem == ""){
						return false;
					}else{
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
							});
					}
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
	/*	function onChange(code) {
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
		}*/
		function onChange(code) {
			var codeObj = code;
			var selectBox = code;
			//console.log("selectBox"+selectBox);
			
			var selected = selectBox.options[selectBox.selectedIndex].value;
			var arr =selected.split(" / ");
			//console.log("arrr"+arr);
			$.ajax({
				type : "GET",
				contentType : "application/json; charset=utf-8",
				url : "getOutletName.htm?code=" + arr[0],
				success : function(data) {
					var jObj = jQuery(codeObj);
					jObj.parent('td').next('td').children('input').val(data);
				}
			});
		}
	

var initiallength= $(".kamDashboard-mapOutlet tr select").length;
		
		$('.hul_outlet_code').on('change', function() {
		
		
		var scheduled = $(".kamDashboard-mapOutlet tr select").filter(function() {
		   return $(this).val() == "";
		});
		
		var selectedLen = scheduled.length;
		
		
	if( selectedLen == initiallength){
$('#submit_Outlet').prop('disabled',true);
	}else{
		$('#submit_Outlet').prop('disabled',false);
	}
	
		
		});