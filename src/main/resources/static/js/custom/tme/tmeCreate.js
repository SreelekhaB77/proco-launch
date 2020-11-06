//MOC DATE LOGIC
var startCutOff = "21"; // 21-5
var endCutOff = "20";   //20-5

var startCutOffNextMOnth = "31";  //21-5
var startCutOffCurrentMonth = "5";  //21-5


var endCutOffCurrentMonth = "31";   //20-5
var endCutOffPreviousMonth = "5";   //20-5



var dateDelimeter = "/"


function getMocMonth() {
	var datePickerStart = $("#startdate").val();
	  var datePickerEnd = $("#enddate").val();
	 var abc=calcualteMocMonth(datePickerStart,datePickerEnd);
	 if(abc.length > 0){
         $("#moc").val(abc);
         return true;
 }else{
         $("#moc").val(abc);
         return false;
 }


}

/**
* dd/mm/yyyy
**/
function calcualteMocMonth(startDate, endDate){

    var mocMonth = new Array();
    var fromDateObj = getDateObject(startDate);
    var startDateDay = getDateDay(startDate);
    var toDateObj = getDateObject(endDate);
    var endDateDay = getDateDay(endDate);

    var months = ((toDateObj.getMonth() - fromDateObj.getMonth()) + (12 * (toDateObj.getFullYear() - fromDateObj.getFullYear())));
    if((startDateDay >= startCutOff) && (startDateDay <= startCutOffNextMOnth)) {
        fromDateObj.setMonth((fromDateObj.getMonth()+1));

    } else if((startDateDay <= startCutOffCurrentMonth)) {
       fromDateObj.setMonth((fromDateObj.getMonth()));

    }

    if((endDateDay >= endCutOff) && (endDateDay <= endCutOffCurrentMonth)) {
       toDateObj.setMonth((toDateObj.getMonth()));

    } else if((endDateDay <= endCutOffPreviousMonth)) {
       toDateObj.setMonth((toDateObj.getMonth() -1));
    }
    var totalMonth = months;

     var i = 0;
    while (parseInt(fromDateObj.getFullYear()+ "" + ((fromDateObj.getMonth()+"").length == 1 ? ("0" +fromDateObj.getMonth()) : fromDateObj.getMonth())) <= parseInt(toDateObj.getFullYear()+ "" + ((toDateObj.getMonth()+"").length == 1 ? ("0"+toDateObj.getMonth()) : toDateObj.getMonth()))) {
      mocMonth.push((fromDateObj.getMonth() +1));
      fromDateObj.setMonth((fromDateObj.getMonth() + 1));
      months--;

      if(i > 5) break;

    }
    return mocMonth;
}


/**
*dateVar (dd/mm/yyyy)
**/
function getDateObject(dateVar) {
  var dsplit = dateVar.split(dateDelimeter);
  var dateObject = new Date(dsplit[2], dsplit[1]-1, 1);
  return dateObject;
}


function getDateDay(dateVar) { 
	var dsplit = dateVar.split(dateDelimeter); 
	return parseInt(dsplit[0]);
}


function updatePrice() {
			var unit_Per_Store = $("#unit_per_store").val();
			  var total_Amt_Per_Store = $("#total_amt_per_store").val();
			  var num_Of_Stores = $("#num_of_stores").val();

			  $("#total_num_of_asset").val(unit_Per_Store*num_Of_Stores);
			 // $("#visibility_amt").val(num_Of_Stores*total_Amt_Per_Store);
			  $("#visibility_amt").val((num_Of_Stores*total_Amt_Per_Store));
		}

	/*	function IsNumeric(n) {
			return !isNaN(n);
		}*/

		$(document)
				.ready(
						function() {	
							
							
			var regionjson = []
			try {
				 regionjson = JSON.parse(RegionListData);
			} catch {
				console.log('Region list issue');
			}

			

		
			$('#region').comboTree({
				source : regionjson,
				isMultiple : true
			});

			$('.comboTreeDropDownContainer ul li span.comboTreeParentPlus').html("+");


			$( document ).on( "change", ".comboTreeDropDownContainer input[type=checkbox]", function(e){
				e.preventDefault();e.stopPropagation();
			     var target = $( this ),fuLen=$('.ComboTreeItemParent').length-1,selen=$( ".comboTreeDropDownContainer input[type=checkbox]:checked" ).length;
				if( target.parent().text() == "ALL INDIA" || target.parent().text() == "BHARAT" ){
						if( target.is( ":checked" ) ){
							$( ".comboTreeDropDownContainer input[type=checkbox]" ).prop( "checked", true );
							$('#region').val('');
							$('#region').val(target.parent().text());return false;
						} else {
							$( ".comboTreeDropDownContainer input[type=checkbox]" ).prop( "checked", false );
							$('#region').val('');return false;
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
							$('#region').val('');
							$('#region').val(searchIDs.toString());
							
							if($('.comboTreeDropDownContainer li:not(:last):not(:first) input[type=checkbox]:checked').length == $('.comboTreeDropDownContainer li:not(:last):not(:first) input[type=checkbox]').length){
								$('.comboTreeDropDownContainer ul li:eq(0)').find( "input:eq(0)" ).prop( "checked", true );
								$('#region').val('');
								$('#region').val('ALL INDIA');
							}
							return false;
							
						} else {
							target.closest( "li" ).find( "input" ).prop( "checked", false );
							target.parents('ul').parent( "li" ).find( "input:eq(0)" ).prop( "checked", false );
							$('.comboTreeDropDownContainer ul li:eq(0)').find( "input:eq(0)" ).prop( "checked", false );
							$('.comboTreeDropDownContainer ul li:last').find( "input:eq(0)" ).prop( "checked", false );
							var searchIDs = $(".comboTreeDropDownContainer input:checked").parent().map(function(){
							      return $(this).text();
							    }).get();
							$('#region').val('');
							$('#region').val(searchIDs.toString());
						}
					
				}
			});
														
							
			                var madeBy;
			                $("#made_by").on('change',function(){
			                	$('#made-by-msg').hide();
			        		
			        
			        	
			                madeBy=  $("#made_by").val();
			                if(madeBy == 'CUSTOMER' || madeBy == 'UNBRANDED' ){
			                
			                    $('#hfs_connectivity').val('NO');
			                    $("#hfs_connectivity option[value='YES']").remove(); 
			                    $("#hfs_connectivity").closest(".form-group").removeClass("has-error").addClass("has-success");
			                    $("#hfs_connectivity").closest(".form-group").find("small").hide();
			                   }
			                else{
			                	
			                	
			                	if($("#hfs_connectivity option[value='YES']").length > 0){
			                		
			                		   return false;
			                	
			                	
			                	    }
			                	    else{
			                	    	
			                	    	$("#hfs_connectivity option:last").before("<option value='YES'>YES</option>");
			                	 
			                	    }
			                	}
			                
			                 
			                
			                });

			             $("#hfs_connectivity").on('change',function(){
			                	$('#hfs-connectivity-msg').hide();
			                	hfsValue =$("#hfs_connectivity").val();
			                	/*if(hfsValue=='YES'){
			                	$("#made_by").val('');
			                	}*/
			                	

			                	});

							$('#moc_month_table').find("tr[id='moc_month']").find(
							"td[id='moc_month_td']")
							.each(
									function() {
										// console.log(i++ + ": " + $(this).html())
										var dateVal = $(this).html();
										var monthNames = [ "Jan", "Feb", "Mar", "Apr",
												"May", "Jun", "Jul", "Aug", "Sep",
												"Oct", "Nov", "Dec" ];
										var date = new Date(dateVal);
										var day = date.getDate();
										var monthIndex = date.getMonth();
										var year = date.getFullYear();
										// $(this).html(year+"=="+day+"=="+monthIndex+1
										// + "-" + monthNames[monthIndex])
										$(this).html(day + "-" + monthNames[day - 1])
									});


							var nowDate = new Date();
							var today = new Date(nowDate.getFullYear(), nowDate.getMonth(), nowDate.getDate(), 0, 0, 0, 0);
							var enddate = new Date(nowDate.getFullYear(), (nowDate.getMonth()+3), nowDate.getDate(), 0, 0, 0, 0);

							$("#unit_per_store, #total_amt_per_store, #num_of_stores").keyup(function() {
								updatePrice();
							});

							 $('#createForm')
									.bootstrapValidator(
											{
												message : 'This value is not valid',
												feedbackIcons : {
													//valid : 'glyphicon glyphicon-ok',
													//invalid : 'glyphicon glyphicon-remove',
													validating : 'glyphicon glyphicon-refresh'
												},
												fields : {
													/*visi_ref_no : {
														message : 'Not valid',
														validators : {
															notEmpty : {
																message : 'Required'
															},
															regexp : {
																regexp : /^[0-9]*(?:\.\d{1,2})?$/,
																message : 'Please enter a integer value'
															}
														}
													},*/
													depo_Split : {
														validators : {
															notEmpty : {
																message : 'Please enter a value'
															}
														}
													},
													account_name : {
														validators : {
															notEmpty : {
																message : 'Please enter a value'
															},
															 callback: {
											                       
										                        }
														}
													},
													
													/*moc : {
														validators : {
															notEmpty : {
																message : 'MOC cannot be blank'
															}
														}
													},*/
													region : {
														validators : {
															notEmpty : {
																message : 'Please enter a value'
															},
															   stringLength: {
																   max:1000
															   },/*regexp : {
																	regexp : /^[a-zA-Z ]*$/,
																   //regexp :/^(?=.*[a-zA-Z])[+()a-zA-Z]+$/,
																	message : 'Please enter a character'
																}*/
															
														}
													},
													state : {
														validators : {
															/*notEmpty : {
																message : 'Please enter a value'
															},*/
															   stringLength: {
																   max:60
															   },/*regexp : {
																	regexp : /^[a-zA-Z ]*$/,
																	message : 'Please enter a character'
																}*/
														}
													},
													city : {
														validators : {
															   stringLength: {
																   max:60
															   },/*regexp : {
																	regexp : /^[a-zA-Z ]*$/,
																	message : 'Please enter a character'
																}*/
														}
													},
													hht_tracking : {
														validators : {
															notEmpty : {
																message : 'Please enter a value'
															}
														}
													},
													hfs_connectivity : {
														validators : {
															callback: {
									                            callback: function(value, validator, $field) {
									                                if (value === '') {
									                                	 return {
									                                         valid: false,
									                                         message: 'Please enter a value'
									                                     };
									                                }
									                                return {
									                                    valid: true,       // or true
									                                   
									                                }
									                            
									                            }
														}
									                    
														}
													},
													/*basepack : {
														validators : {
															notEmpty : {
																message : 'Please enter a value'
															},
															   stringLength: {
																   max:250
															   },
																regexp : {
																//	regexp :/^(\s*\d{5}\s*)(,\s*\d{5}\s*)*,?\s*$/,
																//regexp : /^[\d,]+$/
																	//regexp:/[0-9]{5}[,]([0-9]{5})?$/
																	 regexp :/^(\s*\d{5}\s*)(,\s*\d{5}\s*)*,?\s*$/
																}


														}
													},*/
													/*basepack_desc : {
														validators : {
															stringLength : {
																max : 200,
															  message:
															},
															notEmpty : {
																message : 'Please enter a value'
															}
														}
													},*/
													asset_type : {
														validators : {
															notEmpty : {
																message : 'Please enter a value'
															},
															 callback: {
											                       
										                        }
														}
													},
													visibility_desc : {
														validators : {
															stringLength : {
																max : 250,
															 message: 'Please enter Max 250 characters',
															},
															notEmpty : {
																message : 'Please enter a value'
															},
															regexp : {
															//	regexp : /^[\w ]*$/,
																regexp : /^[A-Za-z0-9 ]*$/,
																message : 'Please enter characters and numbers only'
															}
														}
													},
													/*category : {
														validators : {
															notEmpty : {
																message : 'Please enter a value'
															}
														}
													},
*/													asset_remark : {
														validators : {
															/*notEmpty : {
																message : 'Please enter a value'
															},*/
															   stringLength: {
																   max:100
															   },/*regexp : {
																	regexp : /^[a-zA-Z ]*$/,
																	message : 'Please enter a character'
																}*/
														}
													},
													num_of_stores : {
														validators : {
															notEmpty : {
																message : 'Please enter a value'
															},
															regexp : {
															//	regexp : /^[0-9]*(?:\.\d{1,2})?$/,
																regexp:/^[1-9][0-9]*$/,
																message :  'Please enter positive number'
															},
															   stringLength: {
																   max:12
															   }
														}
													},
													total_amt_per_store : {
														validators : {
															notEmpty : {
																message : 'Please enter a value'
															},
															regexp : {
																regexp : /^[1-9][0-9]*(?:\.\d{1,2})?$/,
																message : 'Please enter positive number'
															}
														}
													},
													unit_per_store : {
														validators : {
															notEmpty : {
																message : 'Please enter a value'
															},
															regexp : {
																//regexp : /^[0-9]*(?:\.\d{1,2})?$/,
																regexp:/^[1-9][0-9]*$/,
																message :  'Please enter atleast 1'
															},
															   stringLength: {
																   max:12
															   }
														}
													},
													mbq : {
														validators : {
															
															regexp : {
															
																regexp:/^[0-9][0-9]*$/,
															},
															   stringLength: {
																   max:9
															   }
														}
													},
													no_sub_ele : {
														validators : {					
																regexp : {			
																regexp:/^[1-9][0-9]*$/,
															},
															   stringLength: {
																   max:3
															   }
														}
													},
													comments : {
														validators : {
															/*notEmpty : {
																message : 'Please enter a value'
															},*/
															   stringLength: {
																   max:200
															   },/*regexp : {
																	regexp : /^[a-zA-Z ]*$/,
																	message : 'Please enter a character'
																}*/
														}
													},
													made_by : {
														validators : {
															callback: {
									                            callback: function(value, validator, $field) {
									                                if (value === '') {
									                                	 return {
									                                         valid: false,
									                                         message: 'Please enter a value'
									                                     };
									                                }
									                                return {
									                                    valid: true,       // or true
									                                   
									                                }
									                            
									                            }
														}
													}
												}
												}
												});											

											$("[data-hide]").on("click", function() {
												$(this).closest("." + $(this).attr("data-hide")).hide();
											});
											

						});

	
