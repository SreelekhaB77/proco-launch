var previous;

$('#dropUnique select').focus(function () {
    // Store the current value on focus, before the changes
    previous = this.value;
}).on('change',function(e) { 

					var selectedItem = $(this).val();
					var selectedInd = $(this).closest('tr').index();
					if(selectedItem == ""){
						$('#dropUnique select').each(
								function() {
						$(this).find(
								'option[value="' + previous
										+ '"]').show();
								});
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
													+ '"]').hide();
								}
							});
					
					var arr =selectedItem.split(" / ");
					$.ajax({
						type : "GET",
						contentType : "application/json; charset=utf-8",
						url : "getOutletName.htm?code=" + arr[0],
						success : function(data) {
							
							e.target.parentElement.nextElementSibling.firstChild.value = data;
						}
					});
					}
					$('.kamDashboard-mapOutlet tbody tr').each(
							function() {
								
								var currLoopVal = $(this).find('select').val();
								var currLoopInd = $(this).index();
								if(currLoopVal == ''){
									
									$(this).find('td').eq(3).find('input').val('');
								}else if(currLoopVal == selectedItem){
								
								if(currLoopInd != selectedInd){
								
									$(this).find('td').eq(2).find('select').val('');
									$(this).find('td').eq(3).find('input').val('');
								}
								}
							});
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
