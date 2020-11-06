//MOC DATE LOGIC
var startCutOff = "21"; // 21-5
var endCutOff = "20";   //20-5

var startCutOffNextMOnth = "31";  //21-5
var startCutOffCurrentMonth = "5";  //21-5


var endCutOffCurrentMonth = "31";   //20-5
var endCutOffPreviousMonth = "5";   //20-5

var firstFocusIntoList = true;

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
                       /*$("#visibility_amt").val(num_Of_Stores*total_Amt_Per_Store);*/
                       $("#visibility_amt").val((num_Of_Stores*total_Amt_Per_Store).toFixed(2));

              }

       /*     function IsNumeric(n) {
                     return !isNaN(n);
              }*/

              $(document).ready(function() {
			
				var regionjson = []
			try {
				 regionjson = JSON.parse(RegionListData);
			} catch {
				console.log('Region list issue');
			}

			var rgnPrev = $('#region').val();
			rgnPrev = typeof rgnPrev == 'string' ? rgnPrev.trim() : '';
			$('#region').val(rgnPrev);
			var preSelected = $('#region').val().split(',').map(function(item){return item.trim()}); 
			
			
		                		

			$('#region').comboTree({
		                			source : regionjson,
		                			isMultiple : true,
		                			selected: preSelected
		                		});

			$('.comboTreeDropDownContainer ul li span.comboTreeParentPlus').html("+");

		                		var itemlabels = $( '.comboTreeItemTitle' );
		                		if( preSelected.indexOf("ALL INDIA") != -1){
		                			itemlabels.find( 'input[type=checkbox]' ).prop('checked',true);
			                	} else {
			                		for(var x = 0; x < itemlabels.length; x++) {
										if(preSelected.indexOf($(itemlabels[x]).text().trim())!= -1){
											$(itemlabels[x]).find( 'input[type=checkbox]' ).prop('checked',true);
										}
				                	}
			                	}
			
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
                                        	 
            	  var a = $('#startdate').val();  
              	/*var d=new Date(a.split("/").reverse().join("-"));

              	var dd=d.getDate().toString();
              	var mm=(d.getMonth()+1).toString();
              	var yy=d.getFullYear().toString();
              	var date1 = new Date(mm+"/"+dd+"/"+yy);
              	var datex = new Date();
              	var diffTimex = Math.abs(date1 - datex);
              	var  diffDaysx1 = Math.ceil(diffTimex/ (1000 * 60 * 60 * 24)); 
              	              	
              	if(diffDaysx1 < 30){
            	      $('#visibility_desc').attr('readonly', true);
            	  $('#total_amt_per_store').attr('readonly', true);
            	  $('#num_of_stores').attr('readonly', true);
            	   
            	  }
            	  if(diffDaysx1 < 11){
            	   $('#basepack').attr('readonly', true);
            	   $('#visibility_desc').attr('readonly', true);
             	  $('#total_amt_per_store').attr('readonly', true);
             	  $('#num_of_stores').attr('readonly', true);

            	  }
            	 */
                                        	 $("#account_name").on('change',function(){
                								 var popValue = $('#popValueForStore').text(); 
                								 if(popValue == null || popValue == ''){
                									 popValue = '';
                								 }else{
                									 popValue = $('#popValueForStore').text();
                								 }
                								 								 
                								$.ajax({
                									type : "POST",
                									contentType : "application/json; charset=utf-8",
                						 			url : "findStoreNoPopClass.htm?no=" + $('#account_name').val()+"&pop="+popValue,
                									async: false,
                									success : function(data) {
                										num = parseInt(data);
                										$('#num_of_stores-msg').show().html(
                												"Max no. of store for "+$('#account_name').val()+" & selected Pop-Class is " + num);
                									}
                								})});
                							 
                							 
                							 $('.mutliSelect input[type="checkbox"]').on('click', function() {
                								 var popValue = $('#popValueForStore').text(); 
                								 if(popValue == null || popValue == ''){
                									 popValue = '';
                								 }else{
                									 popValue = $('#popValueForStore').text();
                								 }
                								 								 
                								$.ajax({
                									type : "POST",
                									contentType : "application/json; charset=utf-8",
                									url : "findStoreNoPopClass.htm?no=" + $('#account_name').val()+"&pop="+popValue,
                									async: false,
                									success : function(data) {
                										num = parseInt(data);
                										$('#num_of_stores-msg').show().html(
                												"Max no. of store for "+$('#account_name').val()+" & selected Pop-Class is " + num);
                									}
                								})
                							});
                								
                							
                                        	 
                                        	 var madeBy;
                 			                $("#made_by").on('change',function(){
                 			                	$('#made-by-msg').hide();

                 				                madeBy=  $("#made_by").val();
                 				                if(madeBy == 'CUSTOMER' || madeBy == 'UNBRANDED' ){
                 				                
                 				                    $('#hfs_connectivity').val('NO');
                 				                    $("#hfs_connectivity option[value='YES']").remove(); 
                 				                   $("#hfs_connectivity").closest(".form-group").removeClass("has-error");
                 				                  //  $("#hfs_connectivity").closest(".form-group").find("small").hide();
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
                 			            	  		$("#made_by").val('0');
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
							
							$.validator.addMethod(
								       "regex",
								        function(value, element, regexp) {
								        var check = false;
								        return this.optional(element) || regexp.test(value);
								        },
								        "Please check your input."
								        );
							
							 jQuery.validator.addMethod('account', function (value) {
							        return (value != '0');
							    }, "Please select the account");
							 jQuery.validator.addMethod('select_value', function (value) {
							        return (value != '0');
							    }, "Please select the value");
							 
								  $("#createForm").validate({ 
								    rules: { 
								    	
								    	account_name:{
								    		account: true 
								    	},
								    	hht_tracking:{
								    		required: true 
								    	},
								    	hfs_connectivity:{
								    		select_value: true 
								    	},
								    	asset_type:{
								    		select_value: true 
								    	},
								    	visibility_desc:{
								    		 maxlength: 250,
								    		// regex : /^[\w ]*$/,
								    		 regex : /^[A-Za-z0-9 ]*$/,
								    		 required: true 
								    	},
								    	asset_remark:{
								    		//regex : /^[a-zA-Z ]*$/,
								    		// required: true 
								    	},
								    	num_of_stores:{
								    		regex:/^[1-9][0-9]*$/,
								    		 required: true 
								    	},
								    	total_amt_per_store:{
								    		regex : /^[1-9][0-9]*(?:\.\d{1,2})?$/,
								    		 required: true 
								    	},
								    	mbq:{
								    		regex:/^[0-9][0-9]*$/
								    	},
								    	unit_per_store:{
								    		regex:/^[1-9][0-9]*$/,
								    		 required: true 
								    	},
								    	/*comments:{
								    		//regex : /^[a-zA-Z ]*$/,
								    		 required: true 
								    	},*/
								    	made_by:{
								    		select_value: true 
								    	},
								    	region: { 
								        //minlength: 2, 
								        //regex : /^[a-zA-Z ]*$/,
								        required: true 
								      }, 
								      state: { 
								       // minlength: 2, 
								       // regex : /^[a-zA-Z ]*$/,
								      //  required: true 
								      }, 
								      no_sub_ele:{
								      maxlength: 3,
								      regex :/^[1-9][0-9]*$/,
								      }
								    /*  city: { 
									        minlength: 2, 
									        regex : /^[a-zA-Z ]*$/,
									        required: true 
									      }*/
								      
								      
								     
								    },
								    messages: {
								    	region:{
								    		regex:'Please enter the character'
								    	},
								    	/*state:{
								    		regex:'Please enter the character'
								    	},*/
								    	/*city:{
								    		regex:'Please enter the character'
								    	},*/
								    	comments:{
								    		regex:'Please enter the character'
								    	},
								    	/*asset_remark:{
								    		regex:'Please enter the character'
								    	},*/
								    	unit_per_store:{
								    		regex:'Please enter atleast 1'
								    	},
								    	visibility_desc:{
								    		maxlength: 'Please enter MAX 250 characters & Numbers Only',
								    		 regex :'Please enter characters & Numbers Only'
								    	},
								    	total_amt_per_store:{
								    		regex:'Please enter positive number'
								    	},
								    	num_of_stores:{
								    		regex:'Please enter positive number' 	
								    	},
								    	mbq:{
								    		regex:'Please enter the number'
								    	},
								    	  no_sub_ele:{
										      maxlength: 'Please enter less than 3 characters',
										      regex :'Please enter positive number'
										      }
								    	
								    	
								    }
								  }); 
								  								
													
											$("[data-hide]").on("click", function() {
												$(this).closest("." + $(this).attr("data-hide")).hide();
											});
											
						});
						

	