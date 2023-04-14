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


$(document)
		.ready(
				function() {
					
					$('#msg-success').hide();
					$('#msg-error').hide();
					
					var selectAll = false;
					
					$("[data-hide]").on("click", function() {
		                $(this).closest("." + $(this).attr("data-hide")).hide();
		              });
					
					/*var geographySelectedVal = $('#geography').comboTree({
						source : JSON.parse(geographyData),
						isMultiple : false
						/*select:function(item){
							geography = item.title;
							promoTable.draw();
						}
					});*/
					
					//below commented by bharati for sprint-9
					
					/*var mocSelectedVal = $('#moc').comboTree({
						source : JSON.parse(moc),
						isMultiple : false
						/*select:function(item){
							mocVal = item.title;
							promoTable.draw();
						}*/
					/*});*/

					$(
							'.comboTreeDropDownContainer ul li span.comboTreeParentPlus')
							.html("+");
					
					
					
					/*$('#customerChainL1')
							.multiselect(
									{
										includeSelectAllOption : true,
										numberDisplayed : 2,
										 buttonWidth: '100px', 
										nonSelectedText : 'ALL CUSTOMERS',
										selectAllText : 'ALL CUSTOMERS',
										onChange : function(option, checked,
												select) {
											 var custChainSelectedData = [];
											 var selectedOptionsChange = $('#customerChainL1 option:selected');
											 var totalLen = $('#customerChainL1 option').length;

							                    if (selectedOptionsChange.length > 0 && selectedOptionsChange.length < totalLen){
													selectAll = false;
												}else if(selectedOptionsChange.length == totalLen){
													selectAll = true;
												}
											 
							                    for (var i = 0; i < selectedOptionsChange.length; i++) {
							                    	custChainSelectedData.push(selectedOptionsChange[i].value);

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

									});*/
					
					

					/*$("#upload-file").on('click', function(e) {
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

					});*/
					
					
					
					$('#budget_holder').change(function(){
						budget_holder = $(this).val();
						promoTable.draw();
						});
						$('#category').change(function(){
						category = $(this).val();
						promoTable.draw();
						});

					$('#product').change(function(){
						product = $(this).val();
						promoTable.draw();
						});

					$('#Customer').change(function(){
						Customer = $(this).val();
						promoTable.draw();
						});

					$('#fund_type').change(function(){
						fund_type = $('#modality option:selected').text();
						promoTable.draw();
						});

					$('#original_amount').change(function(){
						original_amount = $(this).val();
						promoTable.draw();
						});
						
						$('#revised_amount').change(function(){
						revised_amount = $(this).val();
						promoTable.draw();
						});
						$('#transfer_in').change(function(){
						transfer_in = $(this).val();
						promoTable.draw();
						});
						$('#transfer_out').change(function(){
						transfer_out = $(this).val();
						promoTable.draw();
						});
					
					/*PromoBudgetListing table pagination */
				       promoTable = $('.promo-list-table').DataTable({

				              /* added for second tab start */
				    	   "bProcessing": true,
				             "bServerSide": true,
				             "scrollY":       "300px",
						        "scrollX":        true,
						        "scrollCollapse": true,
						        "paging":         true,
						        "ordering": false,
						        "searching": true,
						        "select": true,
						    	"lengthMenu" : [
									[ 5, 10, 25, 50, 100 ],
									[ 5, 10, 25, 50, 100 ] ],
						       "oLanguage": {
					                 "sSearch": 'Search by Promoid: ',
					                  "oPaginate": {
					                      "sNext": "&rarr;",
					                      "sPrevious": "&larr;"
					                  },
					                  "sLengthMenu": "Records per page _MENU_ ",
					                  "sEmptyTable": "No Pending Visibilities."
					
					              },
				             "iDisplayLength": 10,
						     "iDisplayStart": 0,
				              "sAjaxSource": "procoLiveBudgetPagination.htm",
				             "fnServerParams": function(aoData) {
				                aoData.push(
				    	               {"name": "budget_holder", "value": budget_holder}, 
				    	                {"name": "category", "value": category},
				    	                {"name": "product","value": product}, 
				    	                {"name": "Customer", "value": Customer}, 
				    	                {"name": "fund_type", "value": fund_type}, 
				    	                {"name": "original_amount", "value": original_amount}, 
				    	                {"name": "revised_amount", "value": revised_amount}, 
				    	                {"name": "update_amount", "value": update_amount}, 
				    	                {"name": "transfer_in","value": transfer_in},
				    	                {"name": "transfer_out","value": transfer_out} 
				    	                );
				              }, 
				              "fnDrawCallback": function(oSettings){
				            	  $('table.promo-list-table input[type="checkbox"]').change(function() {
									    $('table.promo-list-table input[type="checkbox"]').not(this).prop('checked', false);  
									});
				              },
				              "aoColumns": [{
				                  "mData": "promo_id",
				                  "mRender": function(data, type, full) {
					 console.log("data:"+data);
				                    return '<input type="checkbox" class="visiData" name="promoId" id="promo_id" value="'+data+'">';
				                  } 
				                  },
				            	  {
				                    "mData": "budget_holder"
				                  },{
				                    "mData": "category"
				                  },
								  {
				                    "mData": "product"
				                  },{
				                    "mData": "Customer"
				                  },{
				                    "mData": "fund_type"
				                  }, {
				                    "mData": "original_amount"
				                  },{
				                    "mData": "revised_amount"
				                  }, {
				                    "mData": "update_amount"
				                  }, {
				                    "mData": "transfer_in"
				                  }, {
				                    "mData": "transfer_out"
				                  }, {
				                    "mData": "transfer_pipeline",
				                  }, {
				                    "mData": "total_amount",
				                  }, {
				                    "mData": "pipeline_amount",
				                  }, {
				                    "mData": "commitment_amount",
				                  },{
					                 "mData": "remaining_amount",
					              },{
					                 "mData": "actuals",
					              },{
						                 "mData": "adjustment_against_actuals",
						              }
					              ,{
						                 "mData": "usage",
						              },
									  {
						                 "mData": "post_close_actual_amount",
						              } ,{
						                 "mData": "past_year_closed_promotions_amount",
						              } ,{
						                 "mData": "time_phase",
						              } ,{
						                 "mData": "report_downlaoded_date",
						              },{
						                 "mData": "uploaded_timestamp",
						              }
				                ]
				                /* added for second tab end */
							
				            });
				        
				    /*   $('.filter-ref').on('keyup', function() {
				    	   promoTable.columns(0).search(this.value).draw();
						});*/
				       
				       $('#DataTables_Table_0_length').css({
	                       
	                         'padding': '20px 0'
	                     });
	                     $('#DataTables_Table_0_length').css({
	                         'color': '#29290a'
	                     });
	                    
	                     
	                     $($('#DataTables_Table_0_wrapper .row')[0]).after(
	                             $(".summary-text"));

	                     $(
	                     $($('#DataTables_Table_0_wrapper .row')[0]).find(
                       	'.col-sm-6')[1]).addClass(
                       		"promo-filter-div");
	                     /*$(
	    	                     $($('.promo-filter-div')).find(
	                             	'#DataTables_Table_0_filter')).addClass(
	                             		"promo-filter");*/

	                     
	                     $(
	    	                     $($('#DataTables_Table_0_wrapper .row')[0]).find(
	                             	'select')).addClass(
	                             		"promolistselect");
	                     
	                    
	                     $(
	    	                     $($('.promo-filter')).find(
	                             	'input')).addClass(
	                             		"searchicon-wrapper-promolist");
	                    
	                     $('.promo-filter').find(
                       '#DataTables_Table_0_filter').css({
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
				       
				       $('#DataTables_Table_0_length').css({
	                       
	                         'padding': '20px 0'
	                     });
	                     $('#DataTables_Table_0_length').css({
	                         'color': '#29290a'
	                     });
	                    
	                     
	                     $($('#table-id-promo-list-table_wrapper .row')[0]).after(
	                             $(".summary-text"));

	                    /* $(
	                     $($('#table-id-promo-list-table_wrapper .row')[0]).find(
                       	'.col-sm-6')[1]).addClass(
                       		"promo-filter-div");*/
	                     /*$(
	    	                     $($('.promo-filter-div')).find(
	                             	'#table-id-promo-list-table_filter')).addClass(
	                             		"promo-filter");*/

	                     
	                     $(
	    	                     $($('#table-id-promo-list-table_wrapper .row')[0]).find(
	                             	'select')).addClass(
	                             		"promolistselect");
	                     
	                    
	                     /*$(
	    	                     $($('.promo-filter')).find(
	                             	'input')).addClass(
	                             		"searchicon-wrapper-promolist");*/
	                    
	                    /* $('.promo-filter').find(
                       '#table-id-promo-list-table_filter').css({
	                         'float': 'right'
	                     });*/
				       
	                     $('.promo-filter').css({
	                    	 'float': 'right',
	                    	  'padding-left': '10px',
	                    	  'padding-top': '20px',
	                    	    'height': '75px', 
	                    	    'width': 'none',
	                    	    'border-left': '2px #e8e8e8 solid'
	                     });
	                    
	                    
				     /*  var categoryTags = basepacks;
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
					
						
					});*/
						
						/*$('span.comboTreeItemTitle').on('click',function(){
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
							});*/
						$('.promo-list-table tr').each(function() {
						    var customerId = $(this).find("td").eq(16).val();   
						    console.log(customerId);
						});
				});
/*
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

*/
/*
function multiSelectionForCustChain() {
	$('#customerChainL2').multiselect({
		includeSelectAllOption : true,
		numberDisplayed : 2,
		buttonWidth: '100px',
		nonSelectedText : 'ALL CUSTOMERS',
		selectAllText : 'ALL CUSTOMERS',
		onChange : function(option, checked, select) {
			//console.log(option);
			var selVals = [];
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
*/
function extractLast(term){
	return split(term).pop();
}
function split(val){
	return val.split(/,\s*/);
}

function downloadPromotionFile(){
	window.location.assign("procoLiveBudgetDownload.htm");
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

$('#add-depot').on('hidden.bs.modal', function() {
	$('#msg-success').hide();
	$('#msg-error').hide();
	$('#remark').val("");
});

function validateForm(){
	var promoIdList = [];
	var remark=$('#remark').val();
	$('#msg-success').hide();
	$('#msg-error').hide();
	if(remark==""){
		$('#msg-error').html('Please enter remarks.');
		$('#msg-error').show();
		return false;
	}
	$.each($("table.promo-list-table tbody input[name='promoId']:checked"),
	function() {
		promoIdList.push($(this).val());
			});
	document.getElementById('promoIdList').value = promoIdList;
	$('#pwdform').submit();
}


	

    	

