
//bharati added for sprint-9 US-1


var spinnerWidth = "100";
 var spinnerHeight = "100";
function ajaxLoader(w, h) {

    var left = (window.innerWidth / 2) - (w / 2);
    var top = (window.innerHeight / 2) - (h / 2);
    $('.loader').css('display', 'block');

    $('.loading-image').css({
        "left": left,
        "top": top,
        
    });
}

//bharati code end here

$(document).ready(function() {

 		
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
			//bharati commented below line in sprint-9
			//precheck($('#geography').val());
			
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

			$('#customerChainL1').multiselect(
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
										$('.switch-dynamic').html('<input type="text" name="cust-chain" class="form-control" id="cust-chain" value="ALL CUSTOMERS" readonly="true">');
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
			
			$('#changesMade').multiselect(
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
			
			$('table tr td input.basepack-child').on(
					'blur',
					function() {
						var currEvent = $(this);
						var basepack = currEvent.val();
						var options = "";
						var finalDiv = "";
						if (basepack != '') {
							$.get(
									"getChildBasepackDetails.htm?basepack="
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
				var offerValue =$('#offerValue').val();
				var kittingDrop = $( "#kittingValDrop" ).val();
				var kittingVal = $('#kittingValue').val();
				var offerValDrop = $('#offerValDrop').val();
				var modality = $('#modality').val();

				
				var desc = "Buy ";
				
				var childExist = false;
				
				var parents  = $( "#collapseOne tbody tr" ),
				parenttr = $(""),
				childs  = $( "#collapseTwo tbody tr" ),
				childtr = $(""),
				bdesc = "";
					for( var i = 0; i < parents.length; i++ ){
						parenttr = $( parents[i] );
						if( parenttr.find( ".basepack-parent" ).val().trim() != "" && parenttr.find( ".basepack-cat" ).val().trim() != "PLEASE ENTER CORRECT BASEPACK" ){
							
							desc += ( i != 0 ? ", " : '' ) + parenttr.find( ".basepack-ratio" ).val() + " units of ";
							bdesc = parenttr.find( ".basepack-desc" ).val().split(  ":" );
							if( bdesc.length == 2 ){
								desc += bdesc[1];
							}
							/*  */
						}
					}
					
					
					
					for( var i = 0; i < childs.length; i++ ){
						childtr = $( childs[i] );
						if( childtr.find( ".basepack-child" ).val().trim() != "" && childtr.find( ".child-basepack-cat" ).val().trim() != "PLEASE ENTER CORRECT BASEPACK" ){
							childExist = true;
							desc += " and get " + childtr.find( ".child-basepack-ratio" ).val() + " units of ";
							bdesc = childtr.find( ".child-basepack-desc" ).val().split(  ":" );
							if( bdesc.length == 2 ){
								desc += bdesc[1];
							}
						}
					}
					
				if( modality == "3" || modality == "7" || modality == "8" || modality == "5" || modality == "11"){
				if( baseValue1.trim() != '' ){
										
					if( childExist ){
						desc += ' free';
					}else{
						if( offerValue.trim() != '' ){
							if( offerValDrop == "ABS" ){
								desc += ' and get Rs' +  offerValue + " off ";
							} else {
								desc += ' and get ' +  offerValue + "% off";
							}
						}
						else if(offerValue.trim() == '' || offerValDrop == ''){
							ezBSAlert({
								messageText : "Please enter offervalue and choose % or ABS",
								alertType : "info"
							}).done(function(e) {
								//console.log(e);
							});
						}
						else{
							desc;
						}
					}
					
					if(  !( modality == "3" ||  modality == "5" ) && ( !childExist && ( offerValue.trim() == '' || offerValDrop == '' ) ) ) {
						ezBSAlert({
							messageText : "Please enter child basepack or offer value",
							alertType : "info"
						}).done(function(e) {
							//console.log(e);
						});
						desc = "";
					}
					
					$( "#promoDesc" ).val( desc );
					}
				
				} 
				if( modality == "1" || modality == "2" || modality == "9" || modality == "10"){
					if( baseValue1.trim() != '' ){
											
						if( childExist ){
							desc += ' free';
						}else{
							if( kittingVal.trim() != '' ){
								if( offerValue.trim() == '' ){
									desc += ' and get ';
								} /*else {
									desc += 'off ';
								}*/
								
								if( kittingDrop == "ABS" ){
									desc += "Rs " + kittingVal + " off.";
								} else {
									desc += kittingVal + "% off";
								}
								
							}
							else if(kittingVal.trim() == '' || kittingDrop == '' ){
								ezBSAlert({
									messageText : "Please enter kitting value and choose % or ABS",
									alertType : "info"
								}).done(function(e) {
									//console.log(e);
								});
							}
						}
						
							
							
							if(  !childExist &&  ( kittingVal.trim() == '' || kittingDrop == '' ) ){
								ezBSAlert({
									messageText : "Please enter child basepack or kitting value with choose % or ABS",
									alertType : "info"
								}).done(function(e) {
									//console.log(e);
								});
								desc = "";

							}
						} else {
							desc = "";
						}
					$( "#promoDesc" ).val( desc );
				}
				
				
				 if( baseValue1.trim() == '' ) {
						ezBSAlert({
							messageText : "Please enter atleast 1 parent base pack",
							alertType : "info"
						}).done(function(e) {
							//console.log(e);
						});
						$( "#promoDesc" ).val( "" );
					}
				
				
				});
				
				
		});

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
		if(offerValue=="" && offerValDrop==""){
			ezBSAlert({
				messageText : "Please enter Offer value in % or ABS",
				alertType : "info"
			}).done(function(e) {
				//console.log(e);
			});
			return false;
		} else if(offerValue != "" && offerValDrop==""){
			ezBSAlert({
				messageText : "Please select % or ABS in drop down",
				alertType : "info"
			}).done(function(e) {
				//console.log(e);
			});
			return false;
		} 
		
		else if(offerValue != "" && isNaN(offerValue)  && offerValDrop==""){
			ezBSAlert({
				messageText : "Offer value should be numeric.Please select % or ABS",
				alertType : "info"
			}).done(function(e) {
				//console.log(e);
			});
			return false;
		}
		
	} 
	if (modality == "1" || modality == "4" || modality == "9") {
		var offerValue = $('#offerValue').val();
		var offerValDrop = $('#offerValDrop').val();
		if(offerValue!=""){
			$('#offerValDrop').val("");
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
		if (modality == "4" || modality == "6" || modality == "9" || modality == "10") {
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
		}else if(kittingValue != "" && isNaN(kittingValue)){
			ezBSAlert({
				messageText : "Kitting value should be numeric.",
				alertType : "info"
			}).done(function(e) {
				//console.log(e);
			});
			return false;
		}
	}
	
	if(modality=="11"){
		var offerValue = $('#offerValue').val();
		var offerValDrop = $('#offerValDrop').val();
		if(offerValue=="" && childTable.length == 0 && offerValDrop==""){
			ezBSAlert({
				messageText : "Please enter offer value in % or ABS or child basepack",
				alertType : "info"
			}).done(function(e) {
				//console.log(e);
			});
			return false;
		} else if(offerValue != "" && isNaN(offerValue) && offerValDrop==""){
			ezBSAlert({
				messageText : "Offer value should be numeric.Please select % or ABS",
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
				messageText : "Please select start/end date",
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
		childInput.each(function(ind,val){
			var inpVal = $(this).find('td:eq(2) input').val();
			if(inpVal != "PLEASE ENTER CORRECT BASEPACK"){
			}else{
				ezBSAlert({
					messageText : "PLEASE ENTER CORRECT BASEPACK IN CHILD TABLE",
					alertType : "info"
				}).done(function(e) {
					//console.log(e);
				});
				return false;
			}
		});
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
				//return false;
			}
			});
		return false;
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
	}
	
	var moc=$('#moc').val();
	    $('#successblock').hide();
	    $.ajax({
			type : "GET",
			//contentType : "application/json; charset=utf-8",
			cache : false,
			url : "getDifferenceInDays.htm?moc="+moc,
			success : function(data) {
				//console.log(data);
				var parsed = $.parseJSON(data);
				//alert(parsed);
				if(parsed<=60){
					$('#add-depot').modal('show');
				}else{
					$('#add-depot').modal('hide');
					$("#createPromoForm").submit();
				}
			},
			error : function(error) {
				console.log(error)
			}
		});
}

function submitForm(){
	$('#msg-success').hide();
	$('#msg-error').hide();
	var reason = $("#reason").val();
	var remark= $('#remark').val();
	if(reason=="SELECT"){
		$('#msg-error').html('Please select reason.');
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
	//$("#createPromoForm").submit();
	document.getElementById("createPromoForm").submit();
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

//bharati commented below code for sprint-9
/*function precheck( _string ){
	var spans = $( ".comboTreeDropDownContainer .comboTreeItemTitle" )
	readyString  = _string.split( "," );
	for( var i = 0; i < spans.length; i++  ){
	  if( readyString.indexOf( $( spans[i] ).text().trim() ) != -1 ){
			$( spans[i] ).find( "input:first" ).prop( "checked", true );
			$( spans[i] ).find( "input:first" ).trigger( "change" );
	  }
	}
}*/



function getCustChainValues(selVal) {
	$.ajax({
		type : "POST",
	//	data: selVal,
		//url : "getCustomerChainL2.htm",
		url : "getCustomerChainL2.htm?customerChainL1="+encodeURIComponent(selVal),
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

//bharati added code for sprint-9 Us-1


			$('#choose-file1').click(function() {
				$("#upload-file1").trigger('click');
			});

			$('#upload-file1').change(function(event) {
				
				var files = event.target.files;
				if (files.length > 0) {
					var fileName = files[0].name;
					$('.file-name1').html(fileName);
				} else {
					$('.file-name1').html("No file chosen");
				}

			});
			
			$('#choose-file2').click(function() {
				$("#upload-file2").trigger('click');
			});

			$('#upload-file2').change(function(event) {
				
				var files = event.target.files;
				if (files.length > 0) {
					var fileName = files[0].name;
					$('.file-name2').html(fileName);
				} else {
					$('.file-name2').html("No file chosen");
				}

			});

//upload code start for regular template

$("#ProcoRegularFileUpload").click(function (event) {
						event.preventDefault();
						var templateType = 'Regular';
						var fileName = $('#upload-file').val();
						if (fileName == '') {
							$('#uploadErrorMsg').show().html("Please select a file to upload");
							return false;
						} else {
							$('#uploadErrorMsg').hide();
							var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
							if (FileExt != "xlsx") {
								if (FileExt != "xls") {
									$('#uploadErrorMsg').show().html("Please upload .xls or .xlsx file");
									$("#promoCreateRegularUpload").submit(function(e) {
										e.preventDefault();
									});
									return false;
								}

							}
						}
						
				        // Get form
				        var form = $('#promoCreateRegularUpload')[0];
                           var data = new FormData(form);
                        $.ajax({
				            type: "POST",
				            enctype: 'multipart/form-data',
				            url: 'createCRBean.htm?template=' +templateType ,
				            data: data,
				            processData: false,
				            contentType: false,
				            cache: false,
				            timeout: 600000,
				            beforeSend: function() {
                                ajaxLoader(spinnerWidth, spinnerHeight);
                            },
							
				            success: function (resdata) {
				            	//console.log(resdata);
				            	
				            	 $('.loader').hide();
				            	if(resdata.includes('EXCEL_UPLOADED')) {
					console.log(resdata);// Mayur added the changes
				                    $('#errorblockUpload').hide();
				                	$('#ProcoRegularerrorblockUpload').hide();
				                	$('#Procosuccessblock').show().find('span').html(' File Uploaded Successfully !!!');
				                	 
				                }
				                
				                else if(resdata.includes('EXCEL_NOT_UPLOADED')){
					console.log(resdata);// Mayur added the changes
				                $('#ProcoRegularerrorblockUpload').show();
				                $('#Procosuccessblock').hide();
				                $('#errorblockUpload').hide();
				                }
				                else if(resdata.includes('FILE_EMPTY')){
									$('#errorblockUpload').show().find('span').html('Error While Uploading Empty File');
									$('#ProcoRegularerrorblockUpload').hide();
									$('#Procosuccessblock').hide();
								}
								else if(resdata.includes('CHECK_COL_MISMATCH')){
									$('#errorblockUpload').show().find('span').html('Please Check Uploaded File');
									$('#ProcoRegularerrorblockUpload').hide();
									$('#Procosuccessblock').hide();
								}
								else{
									console.log(resdata);// Mayur added the changes
				                	$('#errorblockUpload').show().find('span').html('Error While Uploading File');
				                	$('#Procosuccessblock').hide();
				                	$('#ProcoRegularerrorblockUpload').hide();
				                	 
				              	 }
				              //  window.location.href = storepageURL;
							    $('#promoCreateRegularUpload')[0].reset();
								$('.file-name').html("No file chosen");
				            },
				            error: function (e) {
				            $('#Procosuccessblock').hide();
				             // console.log("Error");        
				            }
				        });
				       
					     //var storepageURL = $(location).attr("href");
					    	
				    });
					
					
					//code start for cr upload
					$("#procoCRUploadBtn").click(function (event) {
						event.preventDefault();
						var templateType = 'CR';
						var fileName = $('#upload-file1').val();
						if (fileName == '') {
							$('#uploadcrErrorMsg').show().html("Please select a file to upload");
							return false;
						} else {
							$('#uploadcrErrorMsg').hide();
							var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
							if (FileExt != "xlsx") {
								if (FileExt != "xls") {
									$('#uploadcrErrorMsg').show().html("Please upload .xls or .xlsx file");
									$("#promoCreateCrUpload").submit(function(e) {
										e.preventDefault();
									});
									return false;
								}

							}
						}
						
				        // Get form
				        var form = $('#promoCreateCrUpload')[0];
                           var data = new FormData(form);
                        $.ajax({
				            type: "POST",
				            enctype: 'multipart/form-data',
				            url: 'createCRBean.htm?template=' +templateType ,
				            data: data,
				            processData: false,
				            contentType: false,
				            cache: false,
				            timeout: 600000,
				            beforeSend: function() {
                                ajaxLoader(spinnerWidth, spinnerHeight);
                            },
							
				            success: function (resdata) {
				            	 $('.loader').hide();
				            if(resdata.includes('EXCEL_UPLOADED')) {
				                    $('#errorblockUpload').hide();
				                	$('#ProcoRegularerrorblockUpload').hide();
				                	$('#Procosuccessblock').show().find('span').html(' File Uploaded Successfully !!!');
				                	 
				                }
				                
				                else if(resdata.includes('EXCEL_NOT_UPLOADED')){
				                $('#ProcoRegularerrorblockUpload').show();
				                $('#Procosuccessblock').hide();
				                $('#errorblockUpload').hide();
				                }
				                else if(resdata.includes('FILE_EMPTY')){
									$('#errorblockUpload').show().find('span').html('Error While Uploading Empty File');
									$('#ProcoRegularerrorblockUpload').hide();
									$('#Procosuccessblock').hide();
								}
								else if(resdata.includes('CHECK_COL_MISMATCH')){
									$('#errorblockUpload').show().find('span').html('Please Check Uploaded File');
									$('#ProcoRegularerrorblockUpload').hide();
									$('#Procosuccessblock').hide();
								}
								else{
									//console.log("Error");
				                	$('#errorblockUpload').show().find('span').html('Error While Uploading File');
				                	$('#Procosuccessblock').hide();
				                	$('#ProcoRegularerrorblockUpload').hide();
				                	 
				              	 }
				                
				                // window.location.href = storepageURL;    
				                 $('#promoCreateCrUpload')[0].reset();
								 $('.file-name1').html("No file chosen");
				            },
				            error: function (e) {
				            $('#Procosuccessblock').hide();
				             // console.log("Error");        
				            }
				        });
				       
					     // var storepageURL = $(location).attr("href");
					    	
				    });
					
					//code start for new entries upload
					$("#uploadnewEntriesBtn").click(function (event) {
						event.preventDefault();
						var templateType = 'new';
						var fileName = $('#upload-file2').val();
						if (fileName == '') {
							$('#uploadNewErrorMsg').show().html("Please select a file to upload");
							return false;
						} else {
							$('#uploadNewErrorMsg').hide();
							var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
							if (FileExt != "xlsx") {
								if (FileExt != "xls") {
									$('#uploadNewErrorMsg').show().html("Please upload .xls or .xlsx file");
									$("#promoCreateNewEntriesUpload").submit(function(e) {
										e.preventDefault();
									});
									return false;
								}

							}
						}
						
				        // Get form
				        var form = $('#promoCreateNewEntriesUpload')[0];
                           var data = new FormData(form);
                        $.ajax({
				            type: "POST",
				            enctype: 'multipart/form-data',
				            url: 'createCRBean.htm?template=' +templateType ,
				            data: data,
				            processData: false,
				            contentType: false,
				            cache: false,
				            timeout: 600000,
				            beforeSend: function() {
                                ajaxLoader(spinnerWidth, spinnerHeight);
                            },
							
				            success: function (resdata) {
				            	//console.log(resdata);
				            	
				            	 $('.loader').hide();
				            	 if(resdata.includes('EXCEL_UPLOADED')) {
				                    $('#errorblockUpload').hide();
				                	$('#ProcoRegularerrorblockUpload').hide();
				                	$('#Procosuccessblock').show().find('span').html(' File Uploaded Successfully !!!');
				                	 
				                }
				                
				                else if(resdata.includes('EXCEL_NOT_UPLOADED')){
				                $('#ProcoRegularerrorblockUpload').show();
				                $('#Procosuccessblock').hide();
				                $('#errorblockUpload').hide();
				                }
				                else if(resdata.includes('FILE_EMPTY')){
									$('#errorblockUpload').show().find('span').html('Error While Uploading Empty File');
									$('#ProcoRegularerrorblockUpload').hide();
									$('#Procosuccessblock').hide();
								}
								else if(resdata.includes('CHECK_COL_MISMATCH')){
									$('#errorblockUpload').show().find('span').html('Please Check Uploaded File');
									$('#ProcoRegularerrorblockUpload').hide();
									$('#Procosuccessblock').hide();
								}
								else{
									//console.log("Error");
				                	$('#errorblockUpload').show().find('span').html('Error While Uploading File');
				                	$('#Procosuccessblock').hide();
				                	$('#ProcoRegularerrorblockUpload').hide();
				                	 
				              	 }
				                 //window.location.href = storepageURL;    
				                 $('#promoCreateNewEntriesUpload')[0].reset();
								 $('.file-name2').html("No file chosen");
				            },
				            error: function (e) {
				            $('#Procosuccessblock').hide();
				             // console.log("Error");        
				            }
				        });
				       
					     // var storepageURL = $(location).attr("href");
					    	
				    });

//bharati code end here for sprint-9 US_1
