$(document)
		.ready(
				function() {
					$("[data-hide]").on("click", function() {
		                $(this).closest("." + $(this).attr("data-hide")).hide();
		              });
					
					var selectAll = false;
					
					var mocTree = $('#moc').comboTree({
						source : JSON.parse(moc),
						isMultiple : false
						
					});

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
											promoTable1.draw();
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
											selectAll = true;
											promoTable1.draw();
										},
										onDeselectAll : function() {
											custChainL1 = "ALL";
											promoTable1.draw();
											$('.switch-dynamic')
													.html(
															'<input type="text" class="form-control" name="cust-chain" id="cust_chainL2" value="ALL CUSTOMERS" readonly="readonly">');
										}

									});
					
					$('#category').change(function(){
						category = $(this).val();
						promoTable1.draw();
						});

					$('#brand').change(function(){
						brand = $(this).val();
						promoTable1.draw();
						});

					$('#offerType').change(function(){
						offerType = $(this).val();
						promoTable1.draw();
						});

					$('#modality').change(function(){
						modality = $('#modality option:selected').text();
						promoTable1.draw();
						});

					$('#year').change(function(){
						year = $(this).val();
						promoTable1.draw();
						});
					
					$("#upload-file").on('click', function(e) {
						// e.preventDefault();
						//console.log(e);

					});

					$('#choose-file').click(function() {
						$("#upload-file").trigger('click');
					});
					
					
					$('#upload-file').change(function(e) {
						//console.log(e);
						var files = event.target.files;
						if (files.length > 0) {
							var fileName = files[0].name;
							$('.file-name').html(fileName);
						} else {
							$('.file-name').html("No file chosen");
						}

					});
					
					//checkbox mocrr-checkbox
					
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
						//newBasepacks = categoryNewList;
						$('#promo_basepack').autocomplete({
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
							promoTable1.draw();
						}
					});
						
						$('#promo_basepack').on('keyup',function(){
							if($(this).val() == ""){
								basepack = "All";
								promoTable1.draw();
							}
						});
						
						$('span.comboTreeItemTitle').on('click',function(){
							//console.log($(this).parent('div[class="comboTreeInputWrapper"]').find('input').attr('id'));
							var geographyTitle;
							var comboTreeinputId = $(this).parents('div[class="comboTreeWrapper"]').find('input').attr('id');
							if(comboTreeinputId == "moc"){
								geographyTitle = mocTree.getSelectedItemsTitle();
								mocVal = geographyTitle;
								promoTable1.draw();
							}
							});
						//bharati added for sprint-9 moc filter US
						$('#Mocvalue').change(function(){
						Mocvalue = $(this).val();
						promoTable1.draw();
						});
						
						/*PromoListing table pagination promoTable1 = */
						promoTable1 = $('.promo-collaboration-table').DataTable({

					              /* added for second tab start */
							 "bProcessing": true,
				             "bServerSide": true,
				             "lengthChange": false,
				             "searching": false,
				             "ordering": false,
				             "iDisplayLength": 10,
						     "iDisplayStart": 0,
					              "sAjaxSource": "collaborationPagination.htm",
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
					    	                 {"name": "moc","value": Mocvalue} //bharati changes this mocVal to MocValue in sprint-9
					    	                );
					               }, 
					              "fnDrawCallback": function(oSettings){
									  //commented by bharati for sprint 9
					            /*	  $('table.promo-collaboration-table input[type="checkbox"]').change(function() {
					            		  disAggregationCount = $('table.promo-collaboration-table input[type="checkbox"]:checked').length;  
										   if(disAggregationCount>0){
											   document.getElementById("download").disabled = false;
										   }else{
											   document.getElementById("download").disabled = true;
										   }
					            	  });
										   */ 
					              },
					              'initComplete': function(settings){
					            	  if($('#promo_id')!=null){
					            		  //$('#promo_id').get(0).checked=true;  
					            	  }
									  //commented by bharati for sprint 9
					            	 /* disAggregationCount = $('table.promo-collaboration-table input[type="checkbox"]:checked').length;  
									   if(disAggregationCount>0){
										   document.getElementById("download").disabled = false;
									   }else{
										   document.getElementById("download").disabled = true;
									   }*/ //commented by bharati for sprint 9
					               },
					              "aoColumns": [{
					                  "mData": "promo_id",
					                  "mRender": function(data, type, full) {
					                    return '<input type="checkbox" class="visiData" name="promoId" id="promo_id" value="'+data+'">';
					                  } 
					                  },{
					                    "mData": "promo_id"
					                  },
                                        {
										"mData" : "start_date"
										},
										{
										"mData" : "end_date"
										}, {
					                    "mData": "moc"
					                  }, {
					                    "mData": "customerChainL2"
					                  },/* {
					                    "mData": "customerChainL2"
					                  },*//* {
					                    "mData": "salesCategory"
					                  }, {
					                    "mData": "brand"
					                  },*/ {
					                    "mData": "basepack"
					                  }, /*{
					                    "mData": "basepackDesc"
					                  },*/ {
					                    "mData": "offer_desc"
					                  },{
					                    "mData": "offer_type"
					                  }, {
					                    "mData": "offer_modality"
					                  }, {
					                    "mData": "geography",
					                  }, {
					                    "mData": "quantity",
					                  },{
					                    "mData": "offer_value",
					                  },/* {
					                    "mData": "kitting_value",
					                  }, {
					                    "mData": "uom",
					                  }, {
					                    "mData": "national",
					                  },*/{
					                    "mData": "status",
					                  },{
						                 "mData": "investment_type",
						              } ,{
						                 "mData": "sol_code",
						              } ,{
						                 "mData": "promotion_mechanics",
						              } ,{
						                 "mData": "sol_code_status",
						              }
					                ]
					                 /*added for second tab end */
					        });
						
				});
//bharati added below function in sprint-9	for download file			
function downloadPromotionFile(){
	//$("#download").submit();  //bharati commented this line for sprint-9 moc filter value pass to download promo file
	var SelectedMoc = $("#Mocvalue").val();
	window.location.assign(SelectedMoc+"/downloadPromoListing.htm");

}

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
				promoTable1.draw();
			}

		},
		onSelectAll : function() {
			custChainL2 = "ALL";
			promoTable1.draw();
		},
		onDeselectAll : function() {
			custChainL2 = "ALL";
			promoTable1.draw();
		}

	});
}
function extractLast(term){
	return split(term).pop();
}
function split(val){
	return val.split(/,\s*/);
}

function uploadValidation() {
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