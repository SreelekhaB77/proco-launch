
$(document)
		.ready(
				function() {
					$("[data-hide]").on("click", function() {
		                $(this).closest("." + $(this).attr("data-hide")).hide();
		              });
					
					var mocTree = $('#moc').comboTree({
						source : JSON.parse(moc),
						isMultiple : false
						
					});
					
					var selectAll = false;

					$('.comboTreeDropDownContainer ul li span.comboTreeParentPlus').html("+");
				
				  $('#cust_chainL1')
							.multiselect({
								
										includeSelectAllOption : true,
										numberDisplayed : 2,
										nonSelectedText : 'ALL CUSTOMERS',
										selectAllText : 'ALL CUSTOMERS',
										onChange : function(option, checked,
												select) {
											 var custChainSelectedData = [];
							                    var selectedOptions = $('#cust_chainL1 option:selected');
							                    
							                    var totalLen = $('#cust_chainL1 option').length;

							                    if (selectedOptions.length > 0 && selectedOptions.length < totalLen){
													selectAll = false;
												}else if(selectedOptions.length == totalLen){
													selectAll = true;
												}
							                    
							                    for (var i = 0; i < selectedOptions.length; i++) {
							                    	custChainSelectedData.push(selectedOptions[i].value);

							                    }

							                    if(selectAll == true){
							                    	custChainL1 = "ALL";
							                    }else{
							                    	custChainL1 = custChainSelectedData.toString();
							                    }
							                
											promoTable.draw();
										},
										onDropdownHide : function(event) {
											
											var selVals = [];
											var selectedOptions = $('#cust_chainL1 option:selected');
											if (selectedOptions.length > 0 && selectAll == false) {
												for (var i = 0; i < selectedOptions.length; i++) {
													selVals.push(selectedOptions[i].value);
												}

												var strData = selVals.toString();
												$('.switch-dynamic')
														.html(
																'<select class="form-control" name="cust-chain" id="cust_chainL2" multiple="multiple"><option values="ALL CUSTOMERS">ALL CUSTOMERS</option>');

												getCustChainValues(strData);

											} else {
												$('.switch-dynamic')
														.html(
																'<input type="text" name="cust-chain" class="form-control" id="cust_chainL2" value="ALL CUSTOMERS" readonly="true">');
											}

										},

										onSelectAll : function() {
											custChainL1 = "ALL";
											promoTable.draw();
											selectAll = true;
										},
										onDeselectAll : function() {
											custChainL1 = "ALL";
											promoTable.draw();
											$('.switch-dynamic')
													.html(
															'<input type="text" class="form-control" name="cust-chain" id="cust_chainL2" value="ALL CUSTOMERS" readonly="readonly">');
										}

									});
					
					$('#category').change(function(){
						category = $(this).val();
						promoTable.draw();
						});

					$('#brand').change(function(){
						brand = $(this).val();
						promoTable.draw();
						});

					$('#offerType').change(function(){
						offerType = $(this).val();
						promoTable.draw();
						});

					$('#modality').change(function(){
						modality = $('#modality option:selected').text();
						promoTable.draw();
						});

					$('#year').change(function(){
						year = $(this).val();
						promoTable.draw();
						});
					
					/*PromoListing table pagination promoTable = */
					var promoTable = $('.promo-list-table').DataTable({
				    
					     /* added for second tab start */
						 "bProcessing": true,
			             "bServerSide": true,
			             "lengthChange": false,
			             "searching": false,
			             "ordering": false,
			             //sprint-5	select all changes for disaggration tab by bharati
			             "iDisplayLength": 100000 ,
					     "iDisplayStart": 0,
					      "scrollY":       "280px",
					       "scrollX": true,
                          
					    "sAjaxSource": "disaggregationPagination.htm",
				               "fnServerParams": function(aoData) {
								
				                aoData.push(
				    	                {"name": "category", "value": category}, 
				    	                {"name": "brand", "value": brand},
				    	                {"name": "basepack","value": basepack}, 
				    	                {"name": "custChainL1", "value": custChainL1}, 
				    	                {"name": "custChainL2", "value": custChainL2}, 
				    	                {"name": "offerType", "value": offerType}, 
				    	                {"name": "modality", "value": modality}, 
				    	                {"name": "year","value": year},
				    	                {"name": "moc","value": mocVal}
				    	                );
				              }, 
				              "fnDrawCallback": function(oSettings){
				            	var getCount =oSettings.fnRecordsTotal();
				            	  $('#totalCount').text(getCount);
				            	  $('table.promo-list-table input[type="checkbox"]').change(function() {
				            		  
				 						 var checkboxs=document.getElementsByName("mocs");
				 						    var okay=false;
				 						    for(var i=0,l=checkboxs.length;i<l;i++)
				 						    {
				 						        if(checkboxs[i].checked)
				 						        {
				 						            okay=true;
				 						            break;
				 						        }
				 						    }  
				            		  
				            		  /*$('table.promo-disaggregation-table input[type="checkbox"]').not(this).prop('checked', false);*/
									   disAggregationCount = $('table.promo-list-table input[type="checkbox"]:checked').length;  
									   mocrrCheckCount=$('.mocrr-checkbox').find('input:checked').length
									   $('#selectedCount').text(disAggregationCount);
									   if( disAggregationCount==1){
										   $('#addDepot').removeAttr( "disabled", 'disabled' );
									   }else{  
										   $('#addDepot').attr( "disabled",  'disabled' );}
									   //sprint-5 bharati added changes for enable disable disaggregate btn for submitted to kam status 
									   if(okay && disAggregationCount>0){
									   $("#disTable tr:gt(0)").each(function(){
                                       var th = $(this);
                                       if($(th).find("input[name='promoId']").is(":checked")){
                                       var valueOfStatus = $(this).find('td:last-child').text();
                                     
										 if(valueOfStatus=="DIS-AGGREGATION DONE BY DP AND SUBMITTED TO KAM") {
									    document.getElementById("disaggregateBtn").disabled = true;
									 }else{
									  document.getElementById("disaggregateBtn").disabled = false;
									  }
									   }
											});
									   }else{
										   document.getElementById("disaggregateBtn").disabled = true;
									   }
									   
									   //enable dp download and submit to kam sprint-5 US-13,14 btns disable
									  if(disAggregationCount>0){
									  $("#disTable tr:gt(0)").each(function(){
                                       var th = $(this);
                                       if($(th).find("input[name='promoId']").is(":checked")){
                                       var valueOfStatus = $(this).find('td:last-child').text();
                                      
                                         if((valueOfStatus=="DISAGGREGATION DONE BY DP") || (valueOfStatus=="CR 1 - DISAGGREGATION DONE BY DP") || (valueOfStatus=="CR 2 - DISAGGREGATION DONE BY DP") || (valueOfStatus=="ADHOC PROMO - DISAGGREGATION DONE BY DP")){
									   document.getElementById("dpDownload").disabled = false;
									 
									 }else if(valueOfStatus=="DIS-AGGREGATION DONE BY DP AND SUBMITTED TO KAM"){
									  document.getElementById("dpDownload").disabled = false;
									  document.getElementById("disaggregateBtn").disabled = true;
									
									}else{
									   document.getElementById("dpDownload").disabled = true;
									   
									 }
									  }
 });
 
									  }
									  
				            	  });
				              },
							  
						
							   
				              "aoColumns": [{
				                  "mData": "promo_id",
				                  "mRender": function(data, type, full) {
				                    return '<input type="checkbox" class="visiData promo_id_all" name="promoId" id="promo_id" value="'+data+","+full.basepack+'">';
				                  } 
				                  },{
				                    "mData": "promo_id",
				                    "class": "Unique_id"
				                  }, {
				                    "mData": "moc"
				                  }, {
				                    "mData": "customerChainL1"
				                  },/* {
				                    "mData": "customerChainL2"
				                  },*/ {
				                    "mData": "salesCategory"
				                  }, {
				                    "mData": "brand"
				                  }, {
				                    "mData": "basepack",
				                 }, {
				                    "mData": "basepackDesc"
				                  }, {
				                    "mData": "offer_type"
				                  }, {
				                    "mData": "offer_modality"
				                  }, {
				                    "mData": "offer_desc"
				                  }, {
				                    "mData": "offer_value",
				                  }, {
				                    "mData": "kitting_value",
				                  }, {
				                    "mData": "uom",
				                  }, {
				                    "mData": "geography",
				                  }, {
				                    "mData": "plannedQty",
				                  }, {
				                    "mData": "national",
				                  },{
				                    "mData": "status",
				                  }
				                ],
				                /* added for second tab end */
							
				        });
									
					//checkbox mocrr-checkbox
					
					$(document.dc.mocs).change(function() {
						 var checkboxs=document.getElementsByName("mocs");
						    var okay=false;
						    for(var i=0,l=checkboxs.length;i<l;i++)
						    {
						        if(checkboxs[i].checked)
						        {
						            okay=true;
						            break;
						        }
						    }  
						    
						    disAggregationCount = $('table.promo-list-table input[type="checkbox"]:checked').length;  
						    mocrrCheckCount=$('.mocrr-checkbox').find('input:checked').length
							   $('#selectedCount').text(disAggregationCount);
							   
							   if( disAggregationCount==1){
								   $('#addDepot').removeAttr( "disabled", 'disabled' );
							   }else{  
								   $('#addDepot').attr( "disabled",  'disabled' );}
							   
							   if(okay && disAggregationCount>0){
								 //  document.getElementById("disaggregateBtn").disabled = false;
								 //sprint-5 bharati added changes for enable disable disaggregate btn for submitted to kam status 
								 $("#disTable tr:gt(0)").each(function(){
                                       var th = $(this);
                                       if($(th).find("input[name='promoId']").is(":checked")){
                                       var valueOfStatus = $(this).find('td:last-child').text();
                                      
										 if((valueOfStatus=="DIS-AGGREGATION DONE BY DP AND SUBMITTED TO KAM") && (okay)) {
									    document.getElementById("disaggregateBtn").disabled = true;
									 }else{
									 document.getElementById("disaggregateBtn").disabled = false;
									 }
									   }
											});
								   
							   }else{
								   document.getElementById("disaggregateBtn").disabled = true;
								   
							   }
	            	  });
					
				       var categoryTags = basepacks;
						var categoryNewList = [];
						
						categoryTags = categoryTags.replace('[', '');
						categoryTags = categoryTags.replace(']', '');
						
						$.each(categoryTags.split(","), function() {
							categoryNewList.push($.trim(this));
						});
						var newBasepacks = $.map(categoryNewList, function(val,ind){
							return val.substring(0,5);
						});
						//console.log(newBasepacks);
					//added bharati for sprint-5 US-13 multiselect basepack
					for( var i = 0; i <= newBasepacks.length; i++ ){
       
                    $("#promo_basepack").append('<option value="' + newBasepacks[i] + '">' + newBasepacks[i]  + '</option>');
  
                    }
                      multiSelectionForbasepack();
  
 
  //end multiselect code
						//newBasepacks = categoryNewList;
					/*	$('#promo_basepack').autocomplete({
									minLength : 0,
									source : function(request, response) {
										response($.ui.autocomplete.filter(newBasepacks,
												extractLast(request.term)));
									},
									focus : function() {
										return false;
									},
								
						select : function(event, ui) {
							basepack = ui.item.value;
							promoTable.draw();
						}
					});
					
					
						
						$('#promo_basepack').on('keyup',function(){
							if($(this).val() == ""){
								basepack = "All";
								promoTable.draw();
							}
						});*/
						
						$('span.comboTreeItemTitle').on('click',function(){
							//console.log($(this).parent('div[class="comboTreeInputWrapper"]').find('input').attr('id'));
							var geographyTitle;
							var comboTreeinputId = $(this).parents('div[class="comboTreeWrapper"]').find('input').attr('id');
							if(comboTreeinputId == "moc"){
								geographyTitle = mocTree.getSelectedItemsTitle();
								mocVal = geographyTitle;
								promoTable.draw();
							}
							});
				});
				

function getCustChainValues(selVal) {
	$.ajax({
		type : "POST",
//		data: selVal,
		//url : "getCustomerChainL2.htm",
		url : "getCustomerChainL2.htm?customerChainL1="+encodeURIComponent(selVal),
		async : false,
		success : function(dataOutput) {
			//console.log(data);
			var smallcData = $.parseJSON(dataOutput);
			$('#cust_chainL2').empty();
			$('#cust_chainL2').multiselect("destroy");
			$.each(smallcData,
					function(ind, val) {
						$('#cust_chainL2').append(
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



function multiSelectionForCustChain() {
	$('#cust_chainL2').multiselect({
		includeSelectAllOption : true,
		numberDisplayed : 2,
		nonSelectedText : 'ALL CUSTOMERS',
		selectAllText : 'ALL CUSTOMERS',
		onChange : function(option, checked, select) {
			//console.log(option);
			var selVals = [];
			var selectedOptions = $('#cust_chainL2 option:selected');
			if (selectedOptions.length > 0) {
				for (var i = 0; i < selectedOptions.length; i++) {
					selVals.push(selectedOptions[i].value);
				}
				custChainL2 = selVals.toString();
				promoTable.draw();
			}

		},
		onSelectAll : function() {
			custChainL2 = "ALL";
			promoTable.draw();
		},
		onDeselectAll : function() {
			custChainL2 = "ALL";
			promoTable.draw();
		}

	});
}
function extractLast(term){
	return split(term).pop();
}
function split(val){
	return val.split(/,\s*/);
}

//sprint5 select all changes added by bharati sep-21
$('#disTable').on('draw.dt', function() {
$('#select_all_promo').prop('checked', false);
$('input[type="checkbox"]', '#disTable').prop('checked', false);

});
$("#select_all_promo").click(function () {
      $('#disTable tbody input[type="checkbox"]').prop('checked', this.checked);
 });
 
 

 
//Bharati added code for basepack multiselection in sprint-5 US-13
function multiSelectionForbasepack() {
	$('#promo_basepack').multiselect({
		includeSelectAllOption : true,
		numberDisplayed : 3,
	nonSelectedText : 'SELECT BASEPACK',
		selectAllText : 'SELECT ALL BASEPACK',
		onChange : function(option, checked, select) {
			
			var selVals = [];
			var selectedOptions = $('#promo_basepack option:selected');
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

//Bharati added code for sprint-5 US-14 download disaggreated by dp

function DisagreegatedExcelDownload() {
	
	var DisagreegatedID =  getdisagreegatedUQId().toString(); 
	
	window.location.href = "/VisibilityAssetTracker/"+DisagreegatedID+"/downloadDisaggregatedByDP.htm"
	
}
function getdisagreegatedUQId(){

	 DiagreegatedUniqueId = []; 
	$("#disTable tr:gt(0)").each(function(){
        var th = $(this);
        
        if($(th).find("input[name='promoId']").is(":checked")){
            
           //var UniqueId_Val = $(th).find("td:eq(1)").text();
          var UniqueId_Val = $(this).find('td.Unique_id').text();
          
            DiagreegatedUniqueId.push(UniqueId_Val);
            
        }
    });
	
	return DiagreegatedUniqueId;    

}


//bharati added code for submit to kam US-13 in sprint-5
function promosSubmitToKam() {
		
		 $.ajax({
		 	url : "disagregatedPromoskamsubmission.htm",
			 dataType: 'json',
             type: 'post',
             contentType: 'application/json',
             processData: false,
        
             success: function(res, textStatus, jQxhr ){
         
               alert("Submitted To KAM Successfully !!!");
               window.location.reload();
          
           },
			  error: function(error, textStatus, jQxhr ){
               alert("Error while doing KAM submission");
          }
		  });
		
	  }


