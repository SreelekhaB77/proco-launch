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
					
					var geographySelectedVal = $('#geography').comboTree({
						source : JSON.parse(geographyData),
						isMultiple : false
						/*select:function(item){
							geography = item.title;
							promoTable.draw();
						}*/
					});
					
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
						
						//bharati added for sprint-9 moc filter US
						$('#Mocvalue').change(function(){
						Mocvalue = $(this).val();
						promoTable.draw();
						});
					
					/*PromoListing table pagination */
				       promoTable = $('.promo-list-table').DataTable({

				              /* added for second tab start */
				    	   "bProcessing": true,
				             "bServerSide": true,
				             "scrollY":       "200px",
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
					                  "sSearch": 'Search by PromoID:',
					                  "oPaginate": {
					                      "sNext": "&rarr;",
					                      "sPrevious": "&larr;"
					                  },
					                  "sLengthMenu": "Records per page _MENU_ ",
					                  "sEmptyTable": "No Pending Visibilities."
					
					              },
				             "iDisplayLength": 10,
						     "iDisplayStart": 0,
				              "sAjaxSource": "promoDeletePagination.htm",
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
				    	                {"name": "moc","value": Mocvalue} //bharati changes this mocVal to MocValue in sprint-9
				    	                );
				              }, 
				              "fnDrawCallback": function(oSettings){
				            	  $('table.promo-list-table input[type="checkbox"]').change(function() {
									    $('table.promo-list-table input[type="checkbox"]').not(this).prop('checked', false);  
									});
				              },
				              "aoColumns": [/*{
				                  "mData": "promo_id",
				                  "mRender": function(data, type, full) {
				                    return '<input type="checkbox" class="visiData" name="promoId" id="promo_id" value="'+data+'">';
				                  } 
				                  },*/
				            	  {
				                    "mData": "promo_id"
				                  },/*{
				                    "mData": "originalId"
				                  },*/
								  {
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
				                  }, /*{
				                    "mData": "uom",
				                  },*/ {
				                    "mData": "offer_value",
				                  },/*{
					                 "mData": "kitting_value",
					              },*/{
					                 "mData": "status",
					              },/*{
						                 "mData": "reason",
						              }
					              ,{
						                 "mData": "remark",
						              }*/
									  {
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
				       	//promoTable.columns.adjust().draw();
				       
				       $('.filter-ref').on('keyup', function() {
				    	   promoTable.columns(0).search(this.value).draw();
						});
				       
				      /* $('#DataTables_Table_0_length').css({
	                       
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
	                     $(
	    	                     $($('.promo-filter-div')).find(
	                             	'#DataTables_Table_0_filter')).addClass(
	                             		"promo-filter");

	                     
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
	                     });*/
				       
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
						/*$('.promo-list-table tr').each(function() {
						    var customerId = $(this).find("td").eq(16).val();   
						    console.log(customerId);
						});*/
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
function extractLast(term){
	return split(term).pop();
}
function split(val){
	return val.split(/,\s*/);
}

function downloadPromotionFile(){
	//$("#download").submit();  //bharati commented this line for sprint-9 moc filter value pass to download promo file
	var SelectedMoc = $("#Mocvalue").val();
	window.location.assign(SelectedMoc+"/downloadDeletedPromo.htm");
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

$("#KAMVolumeUpload").click(function (event) {
	event.preventDefault();
	
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
				$("#promoApprovalUpload").submit(function(e) {
					e.preventDefault();
				});
				return false;
			}

		}
	}
	
    // Get form
    var form = $('#promoApprovalUpload')[0];
       var data = new FormData(form);
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: 'promoDroppedOfferUpload.htm',
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
                $('#errorblockApprovalUpload').hide();
            	//$('#ProcoVolumeerrorblockUpload').hide();
            	$('#ProcoApprovalUploadsuccessblock').show().find('span').html(' File Uploaded Successfully !!!');
            	 
            }
            
            else if(resdata.includes('EXCEL_NOT_UPLOADED')){
	            $('#errorblockApprovalUpload').show();
	            $('#ProcoApprovalUploadsuccessblock').hide();
	            //$('#errorblockVolumeUpload').hide();
            }
            else if(resdata.includes('FILE_EMPTY')){
				$('#errorblockApprovalUpload').show().find('span').html('Error While Uploading Empty File');
				//$('#ProcoVolumeerrorblockUpload').hide();
				$('#ProcoApprovalUploadsuccessblock').hide();
			}
			else if(resdata.includes('CHECK_COL_MISMATCH')){
				$('#errorblockApprovalUpload').show().find('span').html('Please Check Uploaded File');
				//$('#ProcoVolumeerrorblockUpload').hide();
				$('#ProcoApprovalUploadsuccessblock').hide();
			}
			//Added by kajal G for empty rows in excel in SPRINT-12
			else if (resdata.includes('EMPTY_ROW')) {
				$('#errorblockApprovalUpload').show().find('span').html('File having some empty rows, Please remove empty rows while uploading');
				//$('#ProcoVolumeerrorblockUpload').hide();
				$('#ProcoApprovalUploadsuccessblock').hide();
			}
			else {
				
            	$('#errorblockApprovalUpload').show().find('span').html('Error While Uploading File');
            	$('#ProcoApprovalUploadsuccessblock').hide();
            	//$('#ProcoVolumeerrorblockUpload').hide();
            	 
          	 }
          
		    $('#promoApprovalUpload')[0].reset();
			$('.file-name').html("No file chosen");
        },
        error: function (e) {
        $('#ProcoApprovalUploadsuccessblock').hide();
                 
        }
    });
   
    	
});

