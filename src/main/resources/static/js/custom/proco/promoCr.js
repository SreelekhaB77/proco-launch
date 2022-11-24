$(document)
		.ready(
				function() {
					
					$('#msg-success').hide();
					$('#msg-error').hide();
					
					$('#successblock').hide();
					$('#errorblock').hide();
					
					$("[data-hide]").on("click", function() {
		                $(this).closest("." + $(this).attr("data-hide")).hide();
		              });
					
					var geographySelectedVal = $('#geography').comboTree({
						source : JSON.parse(geographyData),
						isMultiple : false
						/*select:function(item){
							geography = item.title;
							promoTable.draw();
						}*/
					});
					 var selectAll=false;
					
					/*var mocSelectedVal = $('#moc').comboTree({
						source : JSON.parse(moc),
						isMultiple : false
						select:function(item){
							mocVal = item.title;
							promoTable.draw();
						}
					});*/

					$(
							'.comboTreeDropDownContainer ul li span.comboTreeParentPlus').html("+");
					
					
					
					$('#customerChainL1')
							.multiselect(
									{
										includeSelectAllOption : true,
										numberDisplayed : 2,
										/* buttonWidth: '100px', */
										nonSelectedText : 'ALL CUSTOMERS',
										selectAllText : 'ALL CUSTOMERS',
										onChange : function(option, checked,
												select) {
											 var custChainSelectedData = [];
							                    var selectedOptions = $('#customerChainL1 option:selected');
							                    var totalLen = $('#customerChainL1 option').length;
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
											var selectedOptions = $('#customerChainL1 option:selected');
											if (selectedOptions.length > 0 && selectAll == false) {
												for (var i = 0; i < selectedOptions.length; i++) {
													selVals.push(selectedOptions[i].value);
												}

												var strData = selVals.toString();
												$('.switch-dynamic')
														.html(
																'<select class="form-control" name="cust-chain" id="customerChainL2" multiple="multiple"><option values="ALL CUSTOMERS">ALL CUSTOMERS</option>');

												getCustChainValues(strData);

											} else {
												$('.switch-dynamic')
														.html(
																'<input type="text" name="cust-chain" class="form-control" id="customerChainL2" value="ALL CUSTOMERS" readonly="true">');
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
															'<input type="text" class="form-control" name="cust-chain" id="customerChainL2" value="ALL CUSTOMERS" readonly="readonly">');
										}

									});
					
					

					$("#upload-file").on('click', function(e) {
						// e.preventDefault();
						//console.log(e);

					});

					$('#choose-file').click(function() {
						$("#upload-file").trigger('click');
					});
					$('#promo_basepack').on('keyup',function(){
						if($(this).val() == ""){
							basepack = "All";
							promoTable.draw();
						}
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
						//Moc filter in ncmm by viswas -SPRINT 10
						$('#Mocvaluecr').change(function(){
							console.log("moc");
						Mocvaluecr = $(this).val();
						console.log(Mocvaluecr);
						promoTable.draw();
						});
					
					/*PromoListing table pagination */
				       promoTable = $('.promo-list-table').DataTable({

				              /* added for second tab start */
				             "bProcessing": true,
				             "bServerSide": true,
				          /*  "scrollY":       "300px",
						        "scrollX":        true,
						        "scrollCollapse": true,*/
						        "paging":         true,
						        "ordering": false,
						        "searching": true,
						        "select": true,
						    	"lengthMenu" : [
									[ 5, 10, 25, 50, 100 ],
									[ 5, 10, 25, 50, 100 ] ],
						        "oLanguage": {
					                  "sSearch": 'Search by Promoid:',
					                  "oPaginate": {
					                      "sNext": "&rarr;",
					                      "sPrevious": "&larr;"
					                  },
					                  "sLengthMenu": "Records per page _MENU_ ",
					                  "sEmptyTable": "No Pending Visibilities."
					
					              },
				             "iDisplayLength": 10,
						     "iDisplayStart": 0,
				             
				              "sAjaxSource": "promoCrPagination.htm",
				               "fnServerParams": function(aoData) {
				                aoData.push(
				    	                {"name": "category", "value": category}, 
				    	                {"name": "brand", "value": brand},
				    	                {"name": "basepack","value": basepack}, 
				    	                {"name": "custChainL1", "value": custChainL1}, 
				    	                {"name": "custChainL2", "value": custChainL2}, 
				    	                {"name": "geography", "value": geography}, 
				    	                {"name": "offerType", "value": offerType}, 
				    	                {"name": "modality", "value": modality}, 
				    	                {"name": "year","value": year},
				    	                {"name": "moc","value": Mocvaluecr} //Added by viswas-SPRINT 10
				    	                );
				              }, 
				              "fnDrawCallback": function(oSettings){
				            	  $('table.promo-list-table input[type="checkbox"]').change(function() {
									    $('table.promo-list-table input[type="checkbox"]').not(this).prop('checked', false);  
									});
									  $("table.promo-list-table input[name='select_all']").prop('checked',false);
                    	    	  /* Select all checkbox */
                    	    	 $("table.promo-list-table input[name='select_all']").change(function() {
                                      if ($(this).prop("checked")) {
                                        $("table.promo-list-table input:checkbox").prop("checked", true);

                                      } else {
                                        $("table.promo-list-table input:checkbox").prop("checked", false);
                                      }
                                    });
                    	    	  
                    	    	  $("table.promo-list-table input[name='promoId']").change(function() {
                            		  if($("table.promo-list-table tbody input[name='promoId']:checked").length == 1){
                            			    $("#approveCr").find('button').attr("disabled", false);
                            			}
                            			else if($("table.promo-list-table tbody input[name='promoId']:checked").length > 1){
                            			     $("#approveCr").find('button').attr("disabled", true);
                            			}
                    				});
				            	 
				              },
				              "aoColumns": [{
				                  "mData": "promo_id",
				                  "mRender": function(data, type, full) {
				                    return '<input type="checkbox" class="visiData" name="promoId" id="promo_id" value="'+data+'">';
				                  } 
				                  },{
				                    "mData": "promo_id"
				                  },{
				                    "mData": "startDate"
				                  },{
				                    "mData": "endDate"
				                  },{
				                    "mData": "moc"
				                  }, {
				                    "mData": "customer_chain_l1"
				                  },{
				                    "mData": "basepack"
				                  }, {
				                    "mData": "offer_desc"
				                  }, {
				                    "mData": "offer_type"
				                  }, {
				                    "mData": "offer_modality"
				                  }, {
				                    "mData": "geography",
				                  }, {
				                    "mData": "quantity",
				                  },/* {
				                    "mData": "uom",
				                  },*/ {
				                    "mData": "offer_value",
				                  },/*{
					                 "mData": "kitting_value",
					              },*/{
					                 "mData": "status",
					              },/*{
					                 "mData": "reason",
					              },{
					                 "mData": "remark",
					              },{
					                 "mData": "changesMade",
					              },*/{
					              	 "mData": "investmentType",
						           } ,{
						            "mData": "solCode",
						           } ,{
						            "mData": "promotionMechanics",
						           } ,{
						             "mData": "solCodeStatus",
						              }
				                ]
				                /* added for second tab end */

				            });
				             promoTable.columns.adjust().draw();
				       
				       $('.filter-ref').on('keyup', function() {
				    	   promoTable.columns(0).search(this.value).draw();
						});

	                     $('#DataTables_Table_0_length').css({
	                       
	                         'padding': '20px 0'
	                     });
	                     $('#DataTables_Table_0_length').css({
	                         'color': '#29290a'
	                     });
	                    
	                     
	                     $($('#table-id-promo-list-table_wrapper .row')[0]).after(
	                             $(".summary-text"));

	                     $(
	                     $($('#table-id-promo-list-table_wrapper .row')[0]).find(
                         	'.col-sm-6')[1]).addClass(
                         		"promo-filter-div");
	                     $(
	    	                     $($('.promo-filter-div')).find(
	                             	'#table-id-promo-list-table_filter')).addClass(
	                             		"promo-filter");

	                     
	                     $(
	    	                     $($('#table-id-promo-list-table_wrapper .row')[0]).find(
	                             	'select')).addClass(
	                             		"promolistselect");
	                     
	                    
	                     $(
	    	                     $($('.promo-filter')).find(
	                             	'input')).addClass(
	                             		"searchicon-wrapper-promolist");
	                    
	                     $('.promo-filter').find(
                         '#table-id-promo-list-table_filter').css({
	                         'float': 'right'
	                     });
				       
	                     $('.promo-filter').css({
	                    	 'float': 'right',
	                    	  'padding-left': '10px',
	                    	  'padding-top': '20px',
	                    	    'height': '75px', 
	                    	    'width': 'none',
	                    	    'border-left': '2px #e8e8e8 solid'
	                     });
				       var categoryTags = basepacks;
						var categoryNewList = [];
						
						categoryTags = categoryTags.replace('[', '');
						categoryTags = categoryTags.replace(']', '');
						
						$.each(categoryTags.split(","), function() {
							categoryNewList.push($.trim(this));
						});
						newBasepacks = categoryNewList;
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
							promoTable.draw();
						}
					});
						
						
						$('span.comboTreeItemTitle').on('click',function(){
							//console.log($(this).parent('div[class="comboTreeInputWrapper"]').find('input').attr('id'));
							var geographyTitle;
							var comboTreeinputId = $(this).parents('div[class="comboTreeWrapper"]').find('input').attr('id');
							if(comboTreeinputId == "geography"){
							geographyTitle = geographySelectedVal.getSelectedItemsTitle();
							geography = geographyTitle;
							promoTable.draw();
							}else if(comboTreeinputId == "moc"){
								geographyTitle = mocSelectedVal.getSelectedItemsTitle();
								mocVal = geographyTitle;
								promoTable.draw();
							}
							});
						
				});

$('#add-depot').on('hidden.bs.modal', function() {
	$('#msg-success').hide();
	$('#msg-error').hide();
	$('#reason').val("");
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
			$('#customerChainL2').empty();
			$('#customerChainL2').multiselect("destroy");
			$.each(smallcData,
					function(ind, val) {
						$('#customerChainL2').append(
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
	$('#customerChainL2').multiselect({
		includeSelectAllOption : true,
		numberDisplayed : 2,
		/* buttonWidth: '100px',*/
		nonSelectedText : 'ALL CUSTOMERS',
		selectAllText : 'ALL CUSTOMERS',
		onChange : function(option, checked, select) {
			console.log(option);
			//var selVals = [];
			var selectedOptions = $('#customerChainL2 option:selected');
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

function downloadPromotionFile(){
	/*$("#download").submit();*/
	var SelectedMoc = $("#Mocvalue").val();
	window.location.assign(SelectedMoc+"/downloadPromoListing.htm");
	
}

function validateForm(){
	var promoIdList = [];
	var remark=$('#reason').val();
	document.getElementById('remark').value = remark;
	$.each($("table.promo-list-table tbody input[name='promoId']:checked"),
	function() {
		promoIdList.push($(this).val());
			});
	document.getElementById('promoIdList').value = promoIdList;
	$("#download").submit();
}

