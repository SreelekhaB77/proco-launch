$(document).ready(
		function() {
			
			var today = new Date(),
		    yy = today.getFullYear(),
		    m = today.getMonth(),
		    dd = today.getDate(),
		    mm = dd < 21 ? ( m + 1 ) : ( m + 2 );
			var optionMoc = "";
		    m = dd < 21 ? m : ( m + 1 );
		    for( var i = 1; i <= 6; i++ ){
		        var lastMonth = mm + i > 13 ? ( ( mm + i ) - 13 ) : m + i;
		        lastMonth = ( lastMonth + "" ).length == 1 ? "0" + lastMonth : lastMonth; 
		        var lastYear = mm + i > 13 ? yy + 1 : yy;
		        optionMoc += "<option value='"+lastMonth + "" + lastYear+"'>"+lastMonth + "" + lastYear+"</option>"
		    }
			$('#moc').empty().append(optionMoc);

			multiSelectionForMoc();
			
			$('#msg-success').hide();
			$('#msg-error').hide();
			
			$("[data-hide]").on("click", function() {
                $(this).closest("." + $(this).attr("data-hide")).hide();
              });
			var selectAll = false;
			$('#geography').comboTree({
				source : JSON.parse(geographyData),
				isMultiple : true
			});
			
			// check all function for geographyData
			$( document ).on( "change", ".comboTreeDropDownContainer input[type=checkbox]", function(e){
				e.preventDefault();e.stopPropagation();
			     var target = $( this ),fuLen=$('.ComboTreeItemParent').length-1,selen=$( ".comboTreeDropDownContainer input[type=checkbox]:checked" ).length;
				if( target.parent().text() == "ALL INDIA" ){
						if( target.is( ":checked" ) ){
							$( ".comboTreeDropDownContainer input[type=checkbox]" ).prop( "checked", true );
							$('#geography').val('');
							$('#geography').val('ALL INDIA');return false;
						} else {
							$( ".comboTreeDropDownContainer input[type=checkbox]" ).prop( "checked", false );
							$('#geography').val('');return false;
						}
				} else {
						if( target.is( ":checked" ) ){
							target.closest( "li" ).find( "input" ).prop( "checked", true );
							let sublenf=target.parents('ul:eq(0)').find( "input" ).length, sublenu=target.parents('ul:eq(0)').find( "input:checked" ).length;
							if( sublenf== sublenu){target.parents('ul').parent( "li" ).find( "input:eq(0)" ).prop( "checked", true );}
							var selen=$( ".comboTreeDropDownContainer input[type=checkbox]:checked" ).length;
							var searchIDs = $(".comboTreeDropDownContainer input:checked").parent().map(function(){
							      return $(this).text();
							    }).get();
							$('#geography').val('');
							$('#geography').val(searchIDs.toString());
							if(fuLen==selen){$('.comboTreeDropDownContainer ul li:eq(0)').find( "input:eq(0)" ).prop( "checked", true );
							$('#geography').val('');
							$('#geography').val('ALL INDIA');
							}
							return false;
							
						} else {
							target.closest( "li" ).find( "input" ).prop( "checked", false );
							target.parents('ul').parent( "li" ).find( "input:eq(0)" ).prop( "checked", false );
							$('.comboTreeDropDownContainer ul li:eq(0)').find( "input:eq(0)" ).prop( "checked", false );
							var searchIDs = $(".comboTreeDropDownContainer input:checked").parent().map(function(){
							      return $(this).text();
							    }).get();
							$('#geography').val('');
							$('#geography').val(searchIDs.toString());
						}
					
				}
			});
			
			precheck($('#geography').val());
			
			/*$('.ComboTreeItemParent').first().find('span input').on('change',function(e){
				if( $(this).is(':checked') == true ){
					$('.comboTreeItemTitle input').prop('checked', true);
				}else{$('.comboTreeItemTitle input').prop('checked', false);}
			})
			
			$('.ComboTreeItemParent').find('span input').on('change',function(){
				var firstOption=$('.ComboTreeItemParent').first().find('span input').is(':checked'),
				curPos = $('.ComboTreeItemParen').index(this);
				 console.log(curPos)
			})*/
			

			/*$('#moc').comboTree({
				source : JSON.parse(moc),
				isMultiple : false
			});*/

			$('.comboTreeDropDownContainer ul li span.comboTreeParentPlus').html("+");

			$('#customerChainL1')
					.multiselect(
							{
								includeSelectAllOption : true,
								numberDisplayed : 2,
								/* buttonWidth: '100px',*/
								nonSelectedText : 'ALL CUSTOMERS',
								selectAllText : 'ALL CUSTOMERS',
								onChange : function(option,
										checked, select) {
									
									var selectedOptionsChange = $('#customerChainL1 option:selected');
									if (selectedOptionsChange.length > 0){
										
										selectAll = false;
									}
								},

								onDropdownHide : function(event) {
									var selVals = [];
									var selectedOptions = $('#customerChainL1 option:selected');
									if (selectedOptions.length > 0 && selectAll == false) {
										for (var i = 0; i < selectedOptions.length; i++) {
											selVals
													.push(selectedOptions[i].value);
										}

										var strData = selVals
												.toString();
										$('.switch-dynamic')
												.html(
														'<select class="form-control" name="cust-chain" id="cust-chain" multiple="multiple"><option values="ALL CUSTOMERS">ALL CUSTOMERS</option>');

										getCustChainValues(strData);

									} else {
										$('.switch-dynamic')
												.html(
														'<input type="text" name="cust-chain" class="form-control" id="cust-chain" value="ALL CUSTOMERS" readonly="true">');
									}

								},

								onSelectAll : function() {
								selectAll = true;

								},
								onDeselectAll : function() {
									$('.switch-dynamic')
											.html(
													'<input type="text" class="form-control" name="cust-chain" id="cust-chain" value="ALL CUSTOMERS" readonly="readonly">');
								}

							});
			
			$('#changesMade')
			.multiselect(
					{
						//includeSelectAllOption : true,
						numberDisplayed : 2,
						/* buttonWidth: '100px',*/
						//nonSelectedText : 'ALL CUSTOMERS',
						//selectAllText : 'ALL CUSTOMERS',
						onChange : function(option,
								checked, select) {
							
							var selectedOptionsChange = $('#changesMade option:selected');
							if (selectedOptionsChange.length > 0){
								selectAll = false;
							}
						},

						onDropdownHide : function(event) {
							var selVals = [];
							var selectedOptions = $('#changesMade option:selected');
							if (selectedOptions.length > 0 && selectAll == false) {
								for (var i = 0; i < selectedOptions.length; i++) {
									selVals
											.push(selectedOptions[i].value);
								}

								var strData = selVals
										.toString();
							} 

						},

						onSelectAll : function() {
						selectAll = true;

						},
					});
			
			$("#upload-file").on('click', function(e) {
				// e.preventDefault();
				//console.log(e);

			});

			$('#choose-file').click(function() {
				$("#upload-file").trigger('click');
			});

			$('#upload-file').change(function(event) {
				//console.log(event);
				var files = event.target.files;
				if (files.length > 0) {
					var fileName = files[0].name;
					$('.file-name').html(fileName);
				} else {
					$('.file-name').html("No file chosen");
				}

			});
		     $('.collapse').on('show.bs.collapse', function(){
		    	 $(this).parent().find(".glyphicon-plus").removeClass("glyphicon-plus").addClass("glyphicon-minus");
		    	 }).on('hide.bs.collapse', function(){
		    	 $(this).parent().find(".glyphicon-minus").removeClass("glyphicon-minus").addClass("glyphicon-plus");
		    	 });
			$('table tr td input.basepack-parent').on(
					'blur',
					function() {
						var currEvent = $(this);
						var basepack = currEvent.val();
						var options = "";
						var finalDiv = "";
						if (basepack != '') {
							$.get(
									"getBasepackDetails.htm?basepack="
											+ basepack, function(data) {
										var res = JSON.parse(data);
										currEvent.closest('tr').find('td')
												.eq(2).find('input').val(
														res.category);
										currEvent.closest('tr').find('td')
												.eq(3).find('input').val(
														res.brand);
										currEvent.closest('tr').find('td')
												.eq(4).find('input').val(
														res.basepackDesc);
									});
						}
					});
	//desc changes
			
			$('#promoDesc').bind('click', function(e) {  
				  e.preventDefault();
				  $('#promoDesc').val('');
				var baseValue1 = $('#basepack1').val();
				var baseValue2 = $('#basepack2').val();
				var baseValue3 = $('#basepack3').val();
				var baseValue4 = $('#basepack4').val();
				var baseValue5 = $('#basepack5').val();
				var baseValue6 = $('#basepack6').val();
				var parentDesc = $('#basepackDesc1').val();
				var prntdesc = parentDesc.substring(8);
				var parentDesc2 = $('#basepackDesc2').val();
				var prntdesc2 = parentDesc2.substring(8);
				var parentDesc3 = $('#basepackDesc3').val();
				var prntdesc3 = parentDesc3.substring(8);
				var parentDesc4 = $('#basepackDesc3').val();
				var prntdesc4 = parentDesc3.substring(8);
				var parentDesc5 = $('#basepackDesc3').val();
				var prntdesc5 = parentDesc3.substring(8);
				var parentDesc6 = $('#basepackDesc3').val();
				var prntdesc6 = parentDesc3.substring(8);
				var childBasepack1 = $('#childBasepack1').val();
				var childBasepack2 = $('#childBasepack2').val();
				var childBasepack3 = $('#childBasepack3').val();
				var childBasepack4 = $('#childBasepack4').val();
				var childBasepack5 = $('#childBasepack5').val();
				var childBasepack6 = $('#childBasepack6').val();
				var childBasepackDesc1 = $('#childBasepackDesc1').val();
				var childBasepackDesc2 = $('#childBasepackDesc2').val();
				var childBasepackDesc3 = $('#childBasepackDesc3').val();
				var childBasepackDesc4 = $('#childBasepackDesc4').val();
				var childBasepackDesc5 = $('#childBasepackDesc5').val();
				var childBasepackDesc6 = $('#childBasepackDesc6').val();
				var childdesc1= childBasepackDesc1.substring(8);
				var childdesc2= childBasepackDesc1.substring(8);
				var childdesc3= childBasepackDesc1.substring(8);
				var childdesc4= childBasepackDesc1.substring(8);
				var childdesc5= childBasepackDesc1.substring(8);
				var childdesc6= childBasepackDesc1.substring(8);
				
				var ratio = $('#ratio1').val();
				var ratio2 = $('#ratio2').val();
				var ratio3 = $('#ratio3').val();
				var ratio4 = $('#ratio4').val();
				var ratio5 = $('#ratio5').val();
				var ratio6  = $('#ratio6').val();
				var childRatio1 = $('#childRatio1').val();
				var childRatio2 = $('#childRatio2').val();
				var childRatio3 = $('#childRatio3').val();
				var childRatio4 = $('#childRatio4').val();
				var childRatio5 = $('#childRatio5').val();
				var childRatio6 = $('#childRatio6').val();
				var offerValue =$('#offerValue').val();
				var modality = $('#modality').val();
				var kittingVal = $('#kittingValue').val();
				var offerValDrop = $('#offerValDrop').val();
				var childBasepackDesc1 = $('#childBasepackDesc1').val();
				var chlddesc = childBasepackDesc1.substring(8);
				var childRatio1 = $('#childRatio1').val();
				
					if(baseValue1 != "" && baseValue2 == "" && baseValue3 == "" && baseValue4 == "" && baseValue5 == "" && baseValue6 == "" && childBasepackDesc1 != "" && childBasepackDesc2 == "" && childBasepackDesc3 == "" && childBasepackDesc4 == "" && childBasepackDesc5 == "" && childBasepackDesc6 == ""){
						if(modality == "3" || modality == "7" || modality == "8" || modality == "5" || modality == "11"){
							if(offerValue == '' && offerValDrop == ''){
								$('#promoDesc').val("BUY " + ratio  + ' ' + prntdesc + " And get " + childRatio1  + ' ' + childdesc1 + " free.");
							}
							
							else {
								if(offerValDrop == 'ABS'){
									$('#promoDesc').val("BUY " + ratio  + ' ' + prntdesc + " And get rs. " + offerValue + " off");
								}
								else if(offerValDrop == '%'){
									$('#promoDesc').val("BUY " + ratio  + ' ' + prntdesc + " And get " + offerValue + " % off");
								}
							}
							
				        	}
						 else if(modality == "1" ||modality == "2" || modality == "9" || modality == "10"){
							
								 if(kittingVal == "" || kittingVal == 0){
									 $('#promoDesc').val("BUY " + ratio  + ' ' + prntdesc + " And get  " + childRatio1 + ' ' + childdesc1 + " free");
								 }
								 else if(kittingVal > 0){
									 if(offerValDrop == 'ABS'){
										 $('#promoDesc').val("BUY " + ratio  + ' ' + prntdesc + " And get  " + childRatio1 + ' ' + childdesc1 + " free with rs." + kittingVal + " OFF");
									 }
									 else if(offerValDrop == '%'){
										 $('#promoDesc').val("BUY " + ratio  + ' ' + prntdesc + " And get  " + childRatio1 + ' ' + childdesc1 + " free with " + kittingVal + " % OFF");
									 }
								 }
							 }
							 
						 }
					
					
					else if(baseValue1 != "" && baseValue2 != "" && baseValue3 == "" && baseValue4 == "" && baseValue5 == "" && baseValue6 == "" && childBasepackDesc1 != "" && childBasepackDesc2 == "" && childBasepackDesc3 == "" && childBasepackDesc4 == "" && childBasepackDesc5 == "" && childBasepackDesc6 == ""){
						if(modality == "3" || modality == "7" || modality == "8" || modality == "5" || modality == "11"){
							if(offerValue == '' && offerValDrop == ''){
								$('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + " and get " + childRatio1 + ' ' + childdesc1 + " free.");
							}
							else if(offerValDrop == 'ABS'){
								$('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + " and get rs. " + offerValue + " off.");
							}
							else if(offerValDrop == '%'){
								$('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + " and get rs. " + offerValue + "% off.");
							}
						}
						else if(modality == "1" ||modality == "2" || modality == "9" || modality == "10"){
							 
								 if(kittingVal == "" || kittingVal == 0){
									 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + " and get " + childRatio1 + ' ' + childdesc1 +  " free.");
								 }
								 else if(kittingVal > 0){
									 if(offerValDrop == 'ABS'){ 
										 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + " unit of " + " and get " + childRatio1 + ' ' + childdesc1 +" free with rs." + kittingVal + " off.");
									 }
									 else if(offerValDrop == '%'){
										 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + " unit of " + " and get " + childRatio1 + ' ' + childdesc1 + " free with " + kittingVal + " % off.");
									 }
								 }
							}
					}
					
					else if(baseValue1 != "" && baseValue2 != "" && baseValue3 != "" && baseValue4 == "" && baseValue5 == "" && baseValue6 == "" && childBasepackDesc1 != "" && childBasepackDesc2 != "" && childBasepackDesc3 == "" && childBasepackDesc4 == "" && childBasepackDesc5 == "" && childBasepackDesc6 == ""){
						if(modality == "3" || modality == "7" || modality == "8" || modality == "5" || modality == "11"){
							if(offerValue == '' && offerValDrop == ''){
								$('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2  + " free.");
							}
							else if(offerValDrop == 'ABS'){
								$('#promoDesc').val("Buy " + ratio  + '  unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 +" and get rs. " + offerValue + " off.");
							}
							else if(offerValDrop == '%'){
								$('#promoDesc').val("Buy " + ratio  + '  unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 +  ',' + ratio3 + " " + " unit of " + prntdesc3 + " and get rs. " + offerValue + "% off.");
							}
						}
						else if(modality == "1" ||modality == "2" || modality == "9" || modality == "10"){
							 
								 if(kittingVal == "" || kittingVal == 0){
									 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 +  ',' + ratio3 + " " + " unit of " + prntdesc3 + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 +  " free.");
								 }
								 else if(kittingVal > 0){
									 if(offerValDrop == 'ABS'){ 
										 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 +" free with rs." + kittingVal + " off.");
									 }
									 else if(offerValDrop == '%'){
										 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + " unit of " + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 + " free with " + kittingVal + " % off.");
									 }
								 }
							}
					}
					
					else if(baseValue1 != "" && baseValue2 != "" && baseValue3 != "" && baseValue4 != "" && baseValue5 == "" && baseValue6 == "" && childBasepackDesc1 != "" && childBasepackDesc2 != "" && childBasepackDesc3 != "" && childBasepackDesc4 == "" && childBasepackDesc5 == "" && childBasepackDesc6 == ""){
						if(modality == "3" || modality == "7" || modality == "8" || modality == "5" || modality == "11"){
							if(offerValue == '' && offerValDrop == ''){
								$('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 + '' + childRatio3 + ' ' + childdesc3 + " free.");
							}
							else if(offerValDrop == 'ABS'){
								$('#promoDesc').val("Buy " + ratio  + '  unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 +',' + ratio4 + " " + " unit of " + prntdesc4 + " and get rs. " + offerValue + " off.");
							}
							else if(offerValDrop == '%'){
								$('#promoDesc').val("Buy " + ratio  + '  unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 +  ',' + ratio3 + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + " and get rs. " + offerValue + "% off.");
							}
						}
						else if(modality == "1" ||modality == "2" || modality == "9" || modality == "10"){
							 
								 if(kittingVal == "" || kittingVal == 0){
									 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 +  ',' + ratio3 + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 +  '' + childRatio3 + ' ' + childdesc3 + " free.");
								 }
								 else if(kittingVal > 0){
									 if(offerValDrop == 'ABS'){ 
										 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 +" free with rs." + kittingVal + " off.");
									 }
									 else if(offerValDrop == '%'){
										 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + " unit of " + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 + " free with " + kittingVal + " % off.");
									 }
								 }
							}
					}
					
					else if(baseValue1 != "" && baseValue2 != "" && baseValue3 == "" && baseValue4 == "" && baseValue5 == "" && baseValue6 == "" && childBasepackDesc1 != "" && childBasepackDesc2 != "" && childBasepackDesc3 == "" && childBasepackDesc4 == "" && childBasepackDesc5 == "" && childBasepackDesc6 == ""){
						if(modality == "3" || modality == "7" || modality == "8" || modality == "5" || modality == "11"){
							if(offerValue == '' && offerValDrop == ''){
								$('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + " free.");
							}
							else if(offerValDrop == 'ABS'){
								$('#promoDesc').val("Buy " + ratio  + ' ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + " and get rs. " + offerValue + " off.");
							}
							else if(offerValDrop == '%'){
								$('#promoDesc').val("Buy " + ratio  + ' ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + " and get rs. " + offerValue + "% off.");
							}
						}
						else if(modality == "1" ||modality == "2" || modality == "9" || modality == "10"){
							 
								 if(kittingVal == "" || kittingVal == 0){
									 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 +  " free.");
								 }
								 else if(kittingVal > 0){
									 if(offerValDrop == 'ABS'){ 
										 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + " unit of " + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 +" free with rs." + kittingVal + " off.");
									 }
									 else if(offerValDrop == '%'){
										 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + " unit of " + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + " free with " + kittingVal + " % off.");
									 }
								 }
							}
					}
					
					
					else if(baseValue1 != "" && baseValue2 != "" && baseValue3 != "" && baseValue4 == "" && baseValue5 == "" && baseValue6 == ""&& childBasepackDesc1 != "" && childBasepackDesc2 != "" && childBasepackDesc3 != "" && childBasepackDesc4 == "" && childBasepackDesc5 == "" && childBasepackDesc6 == ""){
						if(modality == "3" || modality == "7" || modality == "8" || modality == "5" || modality == "11"){
							if(offerValue == '' && offerValDrop == ''){
								$('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3 + " free.");
							}
							else if(offerValDrop == 'ABS'){
								$('#promoDesc').val("Buy " + ratio  + ' ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + " and get rs. " + offerValue + " off.");
							}
							else if(offerValDrop == '%'){
								$('#promoDesc').val("Buy " + ratio  + ' ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + " and get rs. " + offerValue + "% off.");
							}
						}
						else if(modality == "1" ||modality == "2" || modality == "9" || modality == "10"){
							 if(offerValDrop == 'ABS'){
								 if(kittingVal == "" || kittingVal == 0){
									 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " unit of " + prntdesc3 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3 + " off.");
								 }
								 else if(kittingVal > 0){
									 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + ' unit of ' + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3 + " free with rs." + kittingVal + " off.");
								 }
							 }
							 else{
								 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2  + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3 + " free with " + kittingVal + "% off.");
							 }
						 }
					}
					
					else if(baseValue1 != "" && baseValue2 != "" && baseValue3 != "" && baseValue4 != "" && baseValue5 != "" && baseValue6 == "" && childBasepackDesc1 != "" && childBasepackDesc2 != "" && childBasepackDesc3 != "" && childBasepackDesc4 != "" && childBasepackDesc5 == "" && childBasepackDesc6 == ""){
						if(modality == "3" || modality == "7" || modality == "8" || modality == "5" || modality == "11"){
							if(offerValue == '' && offerValDrop == ''){
								$('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + ',' + ratio5 + " " + " unit of " + prntdesc5 + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 + '' + childRatio3 + ' ' + childdesc3 + '' + childRatio4 + ' ' + childdesc4 + " free.");
							}
							else if(offerValDrop == 'ABS'){
								$('#promoDesc').val("Buy " + ratio  + '  unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 +',' + ratio4 + " " + " unit of " + prntdesc4 + ',' + ratio5 + " " + " unit of " + prntdesc5 + " and get rs. " + offerValue + " off.");
							}
							else if(offerValDrop == '%'){
								$('#promoDesc').val("Buy " + ratio  + '  unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 +  ',' + ratio3 + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + ',' + ratio5 + " " + " unit of " + prntdesc5 +" and get rs. " + offerValue + "% off.");
							}
						}
						else if(modality == "1" ||modality == "2" || modality == "9" || modality == "10"){
							 
								 if(kittingVal == "" || kittingVal == 0){
									 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 +  ',' + ratio3 + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + ',' + ratio5 + " " + " unit of " + prntdesc5 + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 +  '' + childRatio3 + ' ' + childdesc3 + " free.");
								 }
								 else if(kittingVal > 0){
									 if(offerValDrop == 'ABS'){ 
										 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + ',' + ratio5 + " " + " unit of " + prntdesc5 + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 + '' + childRatio3 + ' ' + childdesc3 + '' + childRatio4 + ' ' + childdesc4  + " free with rs." + kittingVal + " off." );
									 }
									 else if(offerValDrop == '%'){
										 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + " unit of " + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 +'' + childRatio3 + ' ' + childdesc3 +'' + childRatio4 + ' ' + childdesc4 + " free with " + kittingVal + " % off.");
									 }
								 }
							}
					}
					
					else if(baseValue1 != "" && baseValue2 != "" && baseValue3 != "" && baseValue4 != "" && baseValue5 != "" && baseValue6 != "" && childBasepackDesc1 != "" && childBasepackDesc2 != "" && childBasepackDesc3 != "" && childBasepackDesc4 != "" && childBasepackDesc5 != "" && childBasepackDesc6 == ""){
						if(modality == "3" || modality == "7" || modality == "8" || modality == "5" || modality == "11"){
							if(offerValue == '' && offerValDrop == ''){
								$('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + ',' + ratio5 + " " + " unit of " + prntdesc5 + ',' + ratio6 + " " + " unit of " + prntdesc6 + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 + '' + childRatio3 + ' ' + childdesc3 + ' ' + childRatio4 + ' ' + childdesc4 +  ' ' + childRatio5 + ' ' + childdesc5 + " free.");
							}
							else if(offerValDrop == 'ABS'){
								$('#promoDesc').val("Buy " + ratio  + '  unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 +',' + ratio4 + " " + " unit of " + prntdesc4 + ',' + ratio5 + " " + " unit of " + prntdesc5 + ',' + ratio6 + " " + " unit of " + prntdesc6 + " and get rs. " + offerValue + " off.");
							}
							else if(offerValDrop == '%'){
								$('#promoDesc').val("Buy " + ratio  + '  unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 +  ',' + ratio3 + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + ',' + ratio5 + " " + " unit of " + prntdesc5 + ',' + ratio6 + " " + " unit of " + prntdesc6 +" and get rs. " + offerValue + "% off.");
							}
						}
						else if(modality == "1" ||modality == "2" || modality == "9" || modality == "10"){
							 
								 if(kittingVal == "" || kittingVal == 0){
									 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 +  ',' + ratio3 + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + ',' + ratio5 + " " + " unit of " + prntdesc5 + ',' + ratio6 + " " + " unit of " + prntdesc6 + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 +  '' + childRatio3 + ' ' + childdesc3 + " free.");
								 }
								 else if(kittingVal > 0){
									 if(offerValDrop == 'ABS'){ 
										 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + ',' + ratio5 + " " + " unit of " + prntdesc5 +',' + ratio6 + " " + " unit of " + prntdesc6 + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 + '' + childRatio3 + ' ' + childdesc3 + '' + childRatio4 + ' ' + childdesc4  +" free with rs." + kittingVal + " off.");
									 }
									 else if(offerValDrop == '%'){
										 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + ',' + ratio5 + " " + " unit of " + prntdesc5 +',' + ratio6 + " " + " unit of " + prntdesc6 + " and get " + childRatio1 + ' ' + childdesc1 + '' + childRatio2 + ' ' + childdesc2 + ' ' + childRatio3 + ' ' + childdesc3 + ' ' + childRatio4 + ' ' + childdesc4 +  ' ' + childRatio5 + ' ' + childdesc5 + " free with " + kittingVal + " % off.");
									 }
								 }
							}
					}
					
					
					else if(baseValue1 != "" && baseValue2 != "" && baseValue3 != "" && baseValue4 != "" && baseValue5 == "" && baseValue6 == ""&& childBasepackDesc1 != "" && childBasepackDesc2 != "" && childBasepackDesc3 != "" && childBasepackDesc4 != "" && childBasepackDesc5 == "" && childBasepackDesc6 == ""){
						if(modality == "3" || modality == "7" || modality == "8" || modality == "5" || modality == "11"){
							if(offerValue == '' && offerValDrop == ''){
								$('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3 + ',' + childRatio4 + ' ' + childdesc4 + " free.");
							}
							else if(offerValDrop == 'ABS'){
								$('#promoDesc').val("Buy " + ratio  + ' ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + " and get rs. " + offerValue + " off.");
							}
							else if(offerValDrop == '%'){
								$('#promoDesc').val("Buy " + ratio  + ' ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + " and get rs. " + offerValue + "% off.");
							}
						}
						else if(modality == "1" ||modality == "2" || modality == "9" || modality == "10"){
							 if(offerValDrop == 'ABS'){
								 if(kittingVal == "" || kittingVal == 0){
									 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3  + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3+ ',' + childRatio4 + ' ' + childdesc4 + "off.");
								 }
								 else if(kittingVal > 0){
									 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3  + " " + " unit of " + prntdesc3 + ',' + ratio4 + ' unit of ' + prntdesc4 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3+ ',' + childRatio4 + ' ' + childdesc4 + " free with rs." + kittingVal + " off.");
								 }
							 }
							 else if(offerValDrop == '%'){
								 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3  + " " + " unit of " + prntdesc3 + ',' + ratio4  + " unit of " + prntdesc4 + " and get " + childRatio1 + ' ' + chlddesc + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3+ ',' + childRatio4 + ' ' + childdesc4+ " free with " + kittingVal + "% off.");
							 }
						 }
					}
					else if(baseValue1 != "" && baseValue2 != "" && baseValue3 != "" && baseValue4 != "" && baseValue5 != "" && baseValue6 == "" && childBasepackDesc1 != "" && childBasepackDesc2 != "" && childBasepackDesc3 != "" && childBasepackDesc4 != "" && childBasepackDesc5 != "" && childBasepackDesc6 == ""){
						if(modality == "3" || modality == "7" || modality == "8" || modality == "5" || modality == "11"){
							if(offerValue == '' && offerValDrop == ''){
								$('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + ',' + ratio4 + " " + " unit of " + prntdesc4 + ',' + ratio5 + " " + " unit of " + prntdesc5 +  " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3 + ',' + childRatio4 + ' ' + childdesc4 + ',' + childRatio5 + ' ' + childdesc5 + " free.");
							}
							else if(offerValDrop == 'ABS'){
								$('#promoDesc').val("Buy " + ratio  + ' ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + " and get rs. " + offerValue + " off.");
							}
							else if(offerValDrop == '%'){
								$('#promoDesc').val("Buy " + ratio  + ' ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + " and get rs. " + offerValue + "% off.");
							}
						}
						else if(modality == "1" ||modality == "2" || modality == "9" || modality == "10"){
							 if(offerValDrop == 'ABS'){
								 if(kittingVal == "" || kittingVal == 0){
									 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3  + " " + " unit of " + prntdesc3 + ','+ ratio4  + " unit of " + prntdesc4 + ',' + ratio5 + " " + " unit of " + prntdesc5 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3 + ',' + childRatio4 + ' ' + childdesc4 + ',' + childRatio5 + ' ' + childdesc5 + " off.");
								 }
								 else if(kittingVal > 0){
									 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3  + " " + " unit of " + prntdesc3 + ','+ ratio4  + " unit of " + prntdesc4 + ',' + ratio5 + ' unit of ' + prntdesc5 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3 + ',' + childRatio4 + ' ' + childdesc4 + ',' + childRatio5 + ' ' + childdesc5 + " free with rs." + kittingVal + " off.");
								 }
							 }
							 else if(offerValDrop == '%'){
								 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3  + " " + " unit of " + prntdesc3 + ','+ ratio4  + " unit of " + prntdesc4 + ',' + ratio5  + " unit of " + prntdesc5 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3 + ',' + childRatio4 + ' ' + childdesc4 + ',' + childRatio5 + ' ' + childdesc5 + " free with " + kittingVal + "% off.");
							 }
						 }
					}
					else if(baseValue1 != "" && baseValue2 != "" && baseValue3 != "" && baseValue4 != "" && baseValue5 != "" && baseValue6 != "" && childBasepackDesc1 != "" && childBasepackDesc2 != "" && childBasepackDesc3 != "" && childBasepackDesc4 != "" && childBasepackDesc5 != "" && childBasepackDesc6 != ""){
						if(modality == "3" || modality == "7" || modality == "8" || modality == "5" || modality == "11"){
							if(offerValue == '' && offerValDrop == ''){
								$('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3 + " " + " unit of " + prntdesc3 + + ',' + ratio4 + " " + " unit of " + prntdesc4 + ',' + ratio5 + " " + " unit of " + prntdesc5 + ',' + ratio6 + " " + " unit of " + prntdesc6 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3 + ',' + childRatio4 + ' ' + childdesc4 + ',' + childRatio5 + ' ' + childdesc5 + ',' + childRatio6 + ' ' + childdesc6 + " free.");
							}
							else if(offerValDrop == 'ABS'){
								$('#promoDesc').val("Buy " + ratio  + ' ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + " and get rs. " + offerValue + " off.");
							}
							else if(offerValDrop == '%'){
								$('#promoDesc').val("Buy " + ratio  + ' ' + prntdesc + ',' + ratio2 + " " + " unit of " + prntdesc2 + " and get rs. " + offerValue + "% off.");
							}
						}
						else if(modality == "1" ||modality == "2" || modality == "9" || modality == "10"){
							 if(offerValDrop == 'ABS'){
								 if(kittingVal == "" || kittingVal == 0){
									 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3  + " " + " unit of " + prntdesc3 + ','+ ratio4  + "unit of " + prntdesc4 + ',' + ratio5  + " unit of " + prntdesc5 + ',' + ratio6 + " " + " unit of " + prntdesc6 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3 + ',' + childRatio4 + ' ' + childdesc4 + ',' + childRatio5 + ' ' + childdesc5 + ',' + childRatio6 + ' ' + childdesc6 + " free.");
								 }
								 else if(kittingVal > 0){
									 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3  + " " + " unit of " + prntdesc3 + ','+ ratio4  + "unit of " + prntdesc4 + ',' + ratio5  + " unit of " + prntdesc5 + ',' + ratio6 + ' unit of ' + prntdesc6 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3 + ',' + childRatio4 + ' ' + childdesc4 + ',' + childRatio5 + ' ' + childdesc5 + ',' + childRatio6 + ' ' + childdesc6 + " free with rs." + kittingVal + " off.");
								 }
							 }
							 else if(offerValDrop == '%'){
								 $('#promoDesc').val("Buy " + ratio  + ' unit of ' + prntdesc + ", " + ratio2 + " " + " unit of " + prntdesc2 + ',' + ratio3  + " " + " unit of " + prntdesc3 + ','+ ratio4  + "unit of " + prntdesc4 + ',' + ratio5  + " unit of " + prntdesc5 + ',' + ratio6  + " unit of " + prntdesc6 + " and get " + childRatio1 + ' ' + childdesc1 + ',' + childRatio2 + ' ' + childdesc2 + ',' + childRatio3 + ' ' + childdesc3 + ',' + childRatio4 + ' ' + childdesc4 + ',' + childRatio5 + ' ' + childdesc5 + ',' + childRatio6 + ' ' + childdesc6 + " free with " + kittingVal + "% off.");
							 }
						 }
					}
					else if(modality == "3" ||modality == "5" ){
						if(childBasepackDesc1 == "" && childBasepackDesc2 == "" && childBasepackDesc3 == "" && childBasepackDesc4 == "" && childBasepackDesc5 == "" && childBasepackDesc6 == ""){
							ezBSAlert({
								messageText : "Please enter atleast 1 child base pack",
								alertType : "info"
							}).done(function(e) {
								//console.log(e);
							});
						}
					}
					
				});
		});
//code ends here
function validateForm() {
	$('#errorblock').hide();
	var parentTable = $('.parent-table table tbody tr').length;
	var parentTable = $('.parent-table table tr td:eq(1)').filter(function() {
		return $(this).find('input').val() != "";

	});
	// var parentTable = $('.parent-table table tbody tr').length;
	var childTable = $('.child-table table tr td:eq(1)').filter(function() {
		return $(this).find('input').val() != "";

	});
	
	var pare = $( ".parent-table tbody tr" ).filter( function(){
		var firstInput = $(this).find('td:first-child').find('input').val();
		var diff = $(this).find('td:last-child').find('input').val();
		var secondInput = $(this).find('td:eq(2)').find('input').val();
		return  firstInput != "" && secondInput != "PLEASE ENTER CORRECT BASEPACK" && secondInput != "" && diff == "";
		});
	
	var child = $( ".child-table tbody tr" ).filter( function(){
		var firstInput = $(this).find('td:first-child').find('input').val();
		var secondInput = $(this).find('td:eq(2)').find('input').val();
		var diff = $(this).find('td:last-child').find('input').val();
		return  firstInput != "" && secondInput != "PLEASE ENTER CORRECT BASEPACK" && secondInput != "" && diff == "";
		});

	var modality = $('#modality').val();
	var promoDesc= $('#promoDesc').val();
	var geography= $('#geography').val();
	var year= $('#year').val();
	var date=new Date();
	var currentYear=date.getFullYear();
	var moc=$('#moc').val();
	var offerType=$('#offerType').val();
	
	var parentInput = $( ".parent-table tbody tr" ).filter( function(){
		var diff = $(this).find('td:eq(2)').find('input').val();
		return diff != "" && diff == "PLEASE ENTER CORRECT BASEPACK";
		});
	var childInput = $( ".child-table tbody tr" ).filter( function(){
		var diff = $(this).find('td:eq(2)').find('input').val();
		return diff != "" && diff == "PLEASE ENTER CORRECT BASEPACK";
		});

	//console.log(parentTable);
	if (modality == "SELECT") {
		ezBSAlert({
			messageText : "Please select Modality",
			alertType : "info"
		}).done(function(e) {
			//console.log('hello');
		});
		return false;
	} 
	if (offerType == "SELECT") {
		ezBSAlert({
			messageText : "Please select offer type",
			alertType : "info"
		}).done(function(e) {
			//console.log(e);
		});
		return false;
	}
	
	if (geography == "") {
		ezBSAlert({
			messageText : "Please select geography",
			alertType : "info"
		}).done(function(e) {
			//console.log(e);
		});
		return false;
	} 
	
	if (modality == "2" || modality == "5" || modality == "6" || modality == "7" || modality == "8" || modality == "10") {
		var offerValue = $('#offerValue').val();
		var offerValDrop = $('#offerValDrop').val();
		if(offerValue=="" && offerValDrop == ""){
			ezBSAlert({
				messageText : "Please enter Offer value in % or ABS",
				alertType : "info"
			}).done(function(e) {
				//console.log(e);
			});
			return false;
		}
		else if(offerValue!="" && offerValDrop == ""){
			ezBSAlert({
				messageText : "Please select % or ABS",
				alertType : "info"
			}).done(function(e) {
				//console.log(e);
			});
			return false;
		}
	} 

	if (modality == "1" || modality == "3" || modality == "4" || modality == "9") {
		var offerValue = $('#offerValue').val();
		if(offerValue!=""){
			var offerValDrop = $('#offerValDrop').val("");
			ezBSAlert({
				messageText : "Offer value should be blank for selected modality",
				alertType : "info"
			}).done(function(e) {
				//console.log(e);
			});
			return false;
		}

	}

	if ((modality == "1" || modality == "2" || modality == "9" || modality == "10") && (offerType == "GTCP")) {
		ezBSAlert({
			messageText : "Invalid modality for GTCP offer type",
			alertType : "info"
		}).done(function(e) {
			//console.log(e);
		});
		return false;
	}

	if (parentTable.length < 1) {
		ezBSAlert({
			messageText : "Please enter parent basepack",
			alertType : "info"
		}).done(function(e) {
			//console.log(e);
		});
		return false;
	} 
	if (childTable.length == 0) {
		if (modality == "4"
				|| modality == "6" || modality == "9" || modality == "10") {
			ezBSAlert({
				messageText : "Please enter child basepack",
				alertType : "info"
			}).done(function(e) {
				//console.log(e);
			});
			return false;
		}
	}
	
	if(modality=="1" || modality=="2"){
		var kittingValue = $('#kittingValue').val();
		if(kittingValue=="" && childTable.length == 0){
			ezBSAlert({
				messageText : "Please enter Kitting value or child basepack",
				alertType : "info"
			}).done(function(e) {
				//console.log(e);
			});
			return false;
		}
	}
	
	if(modality=="11"){
		var offerValue = $('#offerValue').val();
		if(offerValue=="" && childTable.length == 0 && offerValDrop == ""){
			ezBSAlert({
				messageText : "Please enter offer value with %/ABS or child basepack",
				alertType : "info"
			}).done(function(e) {
				//console.log(e);
			});
			return false;
		}
	}
	
	if (promoDesc == "") {
		ezBSAlert({
			messageText : "Please enter Offer desc",
			alertType : "info"
		}).done(function(e) {
			//console.log(e);
		});
		return false;
	}
	
	if(moc==''){
			ezBSAlert({
				messageText : "Please select moc",
				alertType : "info"
			}).done(function(e) {
				//console.log(e);
			});
			return false;
	}
	
	
	if(parentInput.length > 0){
		parentInput.each(function(ind,val){
			var inpVal = $(this).find('td:eq(2) input').val();
			if(inpVal != "PLEASE ENTER CORRECT BASEPACK"){
			}else{
				ezBSAlert({
					messageText : "PLEASE ENTER CORRECT BASEPACK IN PARENT TABLE",
					alertType : "info"
				}).done(function(e) {
					//console.log(e);
				});
				return false;
			}
		});
	}
	if(childInput.length > 0){
		var flgfail = false;
		childInput.each(function(ind,val){
			var inpVal = $(this).find('td:eq(2) input').val();
			if(inpVal != "PLEASE ENTER CORRECT BASEPACK"){
			}else{
				//$('#add-depot').modal('hide');
				ezBSAlert({
					messageText : "PLEASE ENTER CORRECT BASEPACK IN CHILD TABLE",
					alertType : "info"
				}).done(function(e) {
					//console.log(e);
				});
				
				flgfail = true;	
				return false;
			}
			
		});
		if (flgfail == true){
			return false;
			flgfail = false;
		}
		
		
	}
	
	if(pare.length > 0){
		pare.each(function(ind,val){
			var emptyVal = $(this).find('td:last-child input').val();
			if(emptyVal == ""){
				ezBSAlert({
					messageText : "PLEASE ENTER CORRECT RATIO IN PARENT TABLE",
					alertType : "info"
				}).done(function(e) {
					//console.log(e);
				});
				return false;
			}
			});
	}
	
	if(child.length > 0){
		child.each(function(ind,val){
			var emptyVal = $(this).find('td:last-child input').val();
			if(emptyVal == ""){
				ezBSAlert({
					messageText : "PLEASE ENTER CORRECT RATIO IN CHILD TABLE",
					alertType : "info"
				}).done(function(e) {
					//console.log(e);
				});
				return false;
			}
			});
		return false;
	}
	$('#add-depot').modal('show');
}

function submitForm(){
	$('#msg-success').hide();
	$('#msg-error').hide();
	var reason = $("#reason").val();
	var remark= $('#remark').val();
	var changesMade= $('#changesMade').val();
	if(reason=="SELECT"){
		$('#msg-error').html('Please select reason.');
		$('#msg-error').show();
		return false;
	}
	
	if(changesMade==null || changesMade.length == 0){
		$('#msg-error').html('Please select atleast 1 changes made option.');
		$('#msg-error').show();
		return false;
	}
	
	if(reason=="OTHERS" && remark==""){
		$('#msg-error').html('Please enter remarks.');
		$('#msg-error').show();
		return false;
	}
	$('#reasonText').val(reason);
	$('#remarkText').val(remark);
	$('#changesMadeText').val(changesMade);
	$("#updatePromoForm").submit();
}
	
function uploadValidation() {
	$('#errorblock').hide();
	if ($('#upload-file').val() == "") {
		ezBSAlert({
			messageText : "Please choose a file",
			alertType : "info"
		}).done(function(e) {
			//console.log(e);
		});
		return false;
	} else if ($('#upload-file').val() != "") {
		var fileName = $('.file-name').text();

		var FileExt = fileName.substring(fileName.lastIndexOf('.') + 1);
		if (FileExt != "xlsx") {
			if (FileExt != "xls") {
				ezBSAlert({
					messageText : "Please upload .xls or .xlsx file",
					alertType : "info"
				}).done(function(e) {
					//console.log(e);
				});
				return false;
			}
		}
	}
}


function precheck( _string ){
	var spans = $( ".comboTreeDropDownContainer .comboTreeItemTitle" )
	readyString  = _string.split( "," );
	for( var i = 0; i < spans.length; i++  ){
	  if( readyString.indexOf( $( spans[i] ).text().trim() ) != -1 ){
			$( spans[i] ).find( "input:first" ).prop( "checked", true );
			$( spans[i] ).find( "input:first" ).trigger( "change" );
	  }
	}
}


function getCustChainValues(selVal) {
	$.ajax({
		type : "POST",
	//	data: selVal,
		//url : "getCustomerChainL2.htm",
		url : "getCustomerChainL2.htm?customerChainL1="+selVal,
		async : false,
		success : function(data) {
			//console.log(data);
			var smallcData = $.parseJSON(data);
			$('#cust-chain').empty();
			$('#cust-chain').multiselect("destroy");
			$.each(smallcData,
					function(ind, val) {
						$('#cust-chain').append(
								'<option value="'+val+'">' + val
										+ '</option>');
					});
			multiSelectionForCustChain();
		},
		error : function(error) {
			console.log(error)
		}

	});

}

//multiselect for start date moc
function multiSelectionForMoc() {
	$('#moc').multiselect({
		includeSelectAllOption : true,
		numberDisplayed : 2,
		/* buttonWidth: '100px',*/
		nonSelectedText : 'Select All MOC',
		selectAllText : 'Select All MOC',
		onChange : function(option, checked, select) {
			//console.log(option);
			var selVals = [];
			var selectedOptions = $('#moc option:selected');
			if (selectedOptions.length > 0) {
				for (var i = 0; i < selectedOptions.length; i++) {
					selVals.push(selectedOptions[i].value);
				}

				var strData = selVals.toString();

			}

		},
		onSelectAll : function() {

		},
		onDeselectAll : function() {

		}

	});
}

function multiSelectionForCustChain() {
	$('#cust-chain').multiselect({
		includeSelectAllOption : true,
		numberDisplayed : 2,
		/* buttonWidth: '100px',*/
		nonSelectedText : 'ALL CUSTOMERS',
		selectAllText : 'ALL CUSTOMERS',
		onChange : function(option, checked, select) {
			//console.log(option);
			var selVals = [];
			var selectedOptions = $('#cust-chain option:selected');
			if (selectedOptions.length > 0) {
				for (var i = 0; i < selectedOptions.length; i++) {
					selVals.push(selectedOptions[i].value);
				}

				var strData = selVals.toString();

			}

		},
		onSelectAll : function() {

		},
		onDeselectAll : function() {

		}

	});
}

function downloadPromotionFileTemplate(){
	$.get("downloadPromotionTemplateFile.htm");
}

/*function getReason() {
	$('#msg-success').hide();
	$('#msg-error').hide();
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		cache : false,
		url : "getReasonListForEdit.htm",
		success : function(data) {
			var branch = $.parseJSON(data);
			console.log(branch);
			$('#reason').empty();
			$('#reason').append("<option value=SELECT>SELECT</option>");
			$.each(branch,
					function(key, value) {
						$('#reason').append(
								"<option value='" + value + "'>" + value
										+ "</option>");
					});
		},
		error : function(error) {
			console.log(error)
		}
	});
}*/

$('#add-depot').on('hidden.bs.modal', function() {
	$('#msg-success').hide();
	$('#msg-error').hide();
	$('#remark').attr("disabled", 'disabled');
	var listBox = document.getElementById("changesMade");
    listBox.selectedIndex = -1;
});

function enableRemark(){
	$('#msg-success').hide();
	$('#msg-error').hide();
	var reason = $("#reason").val();
	if(reason=="OTHERS"){
		$('#remark').removeAttr( "disabled", 'disabled' );
	}else{
		$('#remark').attr( "disabled",  'disabled' );
	}
}



