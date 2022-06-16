//bharati added for volumn upload file sprint-9 US-1


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

					$("[data-hide]").on("click",function() {
						$(this).closest("."+ $(this).attr("data-hide")).hide();
					});

					var geographySelectedVal = $('#geography').comboTree({
						source : JSON.parse(geographyData),
						isMultiple : false,
						select : function(item) {
							geography = item.title;
							promoTable.draw();
						}
					});

					var selectAll = false;

					var mocSelectedVal = $('#moc').comboTree({
						source : JSON.parse(moc),
						isMultiple : false,
						select : function(item) {
							mocVal = item.title;

						}
					});

					$('.comboTreeDropDownContainer ul li span.comboTreeParentPlus').html("+");

					$('#customerChainL1').multiselect({
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

											if (selectedOptions.length > 0
													&& selectedOptions.length < totalLen) {
												selectAll = false;
											} else if (selectedOptions.length == totalLen) {
												selectAll = true;
											}

											for (var i = 0; i < selectedOptions.length; i++) {
												custChainSelectedData
														.push(selectedOptions[i].value);
											}

											if (selectAll == true) {
												custChainL1 = "ALL";
											} else {
												custChainL1 = custChainSelectedData
														.toString();
											}
											promoTable.draw();
										},

										onDropdownHide : function(event) {

											var selVals = [];
											var selectedOptions = $('#customerChainL1 option:selected');
											if (selectedOptions.length > 0
													&& selectAll == false) {
												for (var i = 0; i < selectedOptions.length; i++) {
													selVals
															.push(selectedOptions[i].value);
												}

												var strData = selVals
														.toString();
												$('.switch-dynamic').html('<select class="form-control" name="cust-chain" id="customerChainL2" multiple="multiple"><option values="ALL CUSTOMERS">ALL CUSTOMERS</option>');

												getCustChainValues(strData);

											} else {
												$('.switch-dynamic').html('<input type="text" name="cust-chain" class="form-control" id="customerChainL2" value="ALL CUSTOMERS" readonly="true">');
											}

										},

										onSelectAll : function() {
											custChainL1 = "ALL";
											selectAll = true;

										},
										onDeselectAll : function() {
											custChainL1 = "ALL";

											$('.switch-dynamic').html('<input type="text" class="form-control" name="cust-chain" id="customerChainL2" value="ALL CUSTOMERS" readonly="readonly">');
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

					promoTable = $('.promo-list-table').DataTable(
									{
                                       /* added for second tab start */
										"bProcessing" : true,
										"bServerSide" : true,
										"lengthChange" : false,
										"searching" : false,
										"ordering" : false,
										"iDisplayLength" : 10,
										"iDisplayStart" : 0,
										"sAjaxSource" : "promoListingPagination.htm",
										"fnServerParams" : function(aoData) {
											aoData.push({
												"name" : "category",
												"value" : category
											}, {
												"name" : "brand",
												"value" : brand
											}, {
												"name" : "basepack",
												"value" : basepack
											}, {
												"name" : "custChainL1",
												"value" : custChainL1
											}, {
												"name" : "custChainL2",
												"value" : custChainL2
											}, {
												"name" : "geography",
												"value" : geography
											}, {
												"name" : "offerType",
												"value" : offerType
											}, {
												"name" : "modality",
												"value" : modality
											}, {
												"name" : "year",
												"value" : year
											}, {
												"name" : "moc",
												"value" : mocVal
											});
										},
										"fnDrawCallback" : function(oSettings) {
											$('table.promo-list-table input[type="checkbox"]').change(function() {
												$('table.promo-list-table input[type="checkbox"]')
														.not(this)
														.prop('checked',false);
												});

										},
										//bharati done changes below columns in sprint-9
										"aoColumns" : [
												/*{
													"mData" : "promo_id",
													"mRender" : function(data,type, full) {
														return '<input type="checkbox" class="visiData" name="promoId" id="promo_id" value="'
																+ data
																+ '" onchange="callThis(this)">';
													}
												},*/
											{
												"mData" : "promo_id",
												"mRender" : function(data,type, full) {
													return '<input type="checkbox" class="visiData" name="promoId" id="promo_id" value="'
															+ data
															+ '">';
												}
											},
												{
													"mData" : "promo_id"
												},
												/*{
													"mData" : "originalId"
												},*/
												{
													"mData" : "startDate"
												},
												{
													"mData" : "endDate"
												},
												{
													"mData" : "moc"
												},
												{
													"mData" : "customer_chain_l1"
												}, 
												/*{
													"mData" : "category"
												},*/ 
												{
													"mData" : "basepack"
												}, {
													"mData" : "offer_desc"
												}, {
													"mData" : "offer_type"
												}, {
													"mData" : "offer_modality"
												}, {
													"mData" : "geography",
												}, {
													"mData" : "quantity",
												}, 
												/*{
													"mData" : "uom",
												},*/
												{
													"mData" : "offer_value",
												}, 
												/*{
													"mData" : "kitting_value",
												},*/
												{
													"mData" : "status",
												},
												/*{
													"mData" : "reason",
												}, {
													"mData" : "remark",
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

					$("#upload-file").on('click', function(e) {
						// e.preventDefault();
						// console.log(e);

					});

					$('#choose-file').click(function() {
						$("#upload-file").trigger('click');
					});
					$('#promo_basepack').on('keyup', function() {
						if ($(this).val() == "") {
							basepack = "All";
							promoTable.draw();
						}
					});
					$('#upload-file').change(function(e) {
						// console.log(e);
						var files = event.target.files;
						if (files.length > 0) {
							var fileName = files[0].name;
							$('.file-name').html(fileName);
						} else {
							$('.file-name').html("No file chosen");
						}

					});

					var categoryTags = basepacks;
					var categoryNewList = [];

					categoryTags = categoryTags.replace('[', '');
					categoryTags = categoryTags.replace(']', '');

					$.each(categoryTags.split(","), function() {
						categoryNewList.push($.trim(this));
					});
					newBasepacks = categoryNewList;
					$('#promo_basepack').autocomplete(
							{
								minLength : 0,
								source : function(request, response) {
									response($.ui.autocomplete.filter(
											newBasepacks,
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
					/*var categoryTags = basepacks;
					var categoryNewList = [];

					categoryTags = categoryTags.replace('[', '');
					categoryTags = categoryTags.replace(']', '');

					$.each(categoryTags.split(","), function() {
						categoryNewList.push($.trim(this));
					});
					newBasepacks = categoryNewList;
					$('#promo_basepack').autocomplete(
							{
								minLength : 0,
								source : function(request, response) {
									response($.ui.autocomplete.filter(
											newBasepacks,
											extractLast(request.term)));
								},
								focus : function() {
									return false;
								},

								select : function(event, ui) {
									basepack = ui.item.value;

								}
							});*/
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

var x = "";
/*function callThis(event) {
//	console.log(event);
	//console.log($(event));
	var input = '<input type="text" />';
	
	if (event.checked) {

		x = event.parentElement.parentElement.children[12].innerText;
		 $(event.parentElement.parentElement.children[12]).empty();
		 $(event.parentElement.parentElement.children[12]).not(event).append("<div class='d-flex'><input class='form-control mr-1 updtquan'  value='"+x+"'><button class='btn btn-primary update' onclick='javascript: updateQuantity();'>Update</button></div>");
	
	} else {
		
		$(event.parentElement.parentElement.children[12]).empty();
		event.parentElement.parentElement.children[12].innerText = x;
	}

}*/



function updateQuantity(){
	

	$(".promo-list-table tbody tr").each(function() {
		var refNumber = $(this).find('td').eq(1).text();
		var quantity = $(this).find('td').eq(12).find('input').val();
	
	$.ajax({
			type : "POST",
			//contentType : "application/json; charset=utf-8",
			url : "updateQuantity.htm?refNumber="+ encodeURIComponent(refNumber)+"&quantity="+encodeURIComponent(quantity),
			//async: false,
			success : function(data) {
				if(data =='success'){
					//$('#msg-success').show();
					 $('#successblock')
      	              .show()
      	              .find('span')
      	              .html('Value saved Successfully !!!');
				}
		console.log(data);
			//var storeCodeData = $.parseJSON(data);
		//	$('#storeCode').empty();
			//$('#suCategoryId').multiselect("destroy");
			
				promoTable.draw();
			},
			error:function(error){
				$('#msg-success').hide();
				}

		});
	});

	}

function getCustChainValues(selVal) {
	$.ajax({
		type : "POST",
		// data: selVal,
		// url : "getCustomerChainL2.htm",
		url : "getCustomerChainL2.htm?customerChainL1="
				+ encodeURIComponent(selVal),
		async : false,
		success : function(data) {
			// console.log(data);
			var smallcData = $.parseJSON(data);
			$('#customerChainL2').empty();
			$('#customerChainL2').multiselect("destroy");
			$.each(smallcData, function(ind, val) {
				$('#customerChainL2').append(
						'<option value="' + val + '">' + val + '</option>');
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
		/* buttonWidth: '100px', */
		nonSelectedText : 'ALL CUSTOMERS',
		selectAllText : 'ALL CUSTOMERS',
		onChange : function(option, checked, select) {
			// console.log(option);
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
function extractLast(term) {
	return split(term).pop();
}
function split(val) {
	return val.split(/,\s*/);
}

/*
 * function downloadPromotions() {
 * 
 * var category = $('#category').val(); var brand=$('#brand').val(); var
 * customerChainL1=$('#customerChainL1').val(); if(customerChainL1!=null){
 * customerChainL1=$('#customerChainL1').val().toString(); }else{
 * customerChainL1="ALL CUSTOMERS" }
 * 
 * var customerChainL2=$('#customerChainL2').val(); if(customerChainL2!=null){
 * customerChainL2=$('#customerChainL2').val().toString(); }else{
 * customerChainL2="ALL CUSTOMERS" } var offerType=$('#offerType').val(); var
 * modality=$('#modality').val(); var promo_basepack=$('#promo_basepack').val();
 * var geography=$('#geography').val(); var year=$('#year').val(); var
 * moc=$('#moc').val();
 * 
 * var dataObj = { category:category, brand:brand, custChainL1:customerChainL1,
 * custChainL2:customerChainL2, offerType:offerType, modality:modality,
 * basepack:promo_basepack, geography:geography, year:year, moc:moc } $.ajax({
 * type: "POST", data: dataObj, url : "downloadPromosForVolumeUpload.htm",
 * success : function(res) { console.log(res); }, error : function(error) {
 * console.log(error) }
 * 
 * });
 * 
 * document.forms[0].action = "downloadPromosForVolumeUpload.htm";
 * document.forms[0].method = "POST"; document.forms[0].submit(); }
 */

function uploadValidation() {
	if ($('#upload-file').val() == "") {
		ezBSAlert({
			messageText : "Please choose a file",
			alertType : "info"
		}).done(function(e) {
			// console.log(e);
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
					// console.log(e);
				});
				return false;
			}
		}
	}
}

//bharati added upload code for volumn Upload for sprint-9

$("#PromoVolumeUpload").click(function (event) {
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
									$("#promoVolumeUpload").submit(function(e) {
										e.preventDefault();
									});
									return false;
								}

							}
						}
						
				        // Get form
				        var form = $('#promoVolumeUpload')[0];
                           var data = new FormData(form);
                        $.ajax({
				            type: "POST",
				            enctype: 'multipart/form-data',
				            url: 'dpVolumeUpload.htm',
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
				                    $('#errorblockVolumeUpload').hide();
				                	$('#ProcoVolumeerrorblockUpload').hide();
				                	$('#ProcoVolumesuccessblock').show().find('span').html(' File Uploaded Successfully !!!');
				                	 
				                }
				                
				                else if(resdata.includes('EXCEL_NOT_UPLOADED')){
				                $('#ProcoVolumeerrorblockUpload').show();
				                $('#ProcoVolumesuccessblock').hide();
				                $('#errorblockVolumeUpload').hide();
				                }
				                else if(resdata.includes('FILE_EMPTY')){
									$('#errorblockVolumeUpload').show().find('span').html('Error While Uploading Empty File');
									$('#ProcoVolumeerrorblockUpload').hide();
									$('#ProcoVolumesuccessblock').hide();
								}
								else if(resdata.includes('CHECK_COL_MISMATCH')){
									$('#errorblockVolumeUpload').show().find('span').html('Please Check Uploaded File');
									$('#ProcoVolumeerrorblockUpload').hide();
									$('#ProcoVolumesuccessblock').hide();
								}else {
									
				                	$('#errorblockVolumeUpload').show().find('span').html('Error While Uploading File');
				                	$('#ProcoVolumesuccessblock').hide();
				                	$('#ProcoVolumeerrorblockUpload').hide();
				                	 
				              	 }
				              
							    $('#promoVolumeUpload')[0].reset();
								$('.file-name').html("No file chosen");
				            },
				            error: function (e) {
				            $('#ProcoVolumesuccessblock').hide();
				                     
				            }
				        });
				       
					    	
				    });