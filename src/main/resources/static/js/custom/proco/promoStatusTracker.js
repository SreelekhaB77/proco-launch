
 var spinnerWidth = "100";
  var spinnerHeight = "100";
 
$(document).ready(function() {
	
	$(document).on("click", "#btnSubmitBasePack1, #btnSubmitBasePack, .validate_dowload", function(){
        $("#successblock, #errorblock, #successblockUpload, #erorblockUpload").hide();
	});
	
	//proco measure report

	$(document).on("submit", "#coeStatusMeasFileUpload", function(){
		var fileName = $('#uploadmeasscre').val();
		if (fileName == '') {
			$('#uploadErrorMsg').show().html("Please select a file to upload");
			e.preventDefault();
			return false;
		} else {
			$('#uploadErrorMsg').hide();
			var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
			if (FileExt.toLowerCase() != "xls" && FileExt.toLowerCase() != "xlsx" ) {
	
				$('#uploadErrorMsg').show().html(
						"Please upload .xlsx/.xls file");
				e.preventDefault();
				return false;
	
			}
		}
	});
	
	/*** Convert db data into tree structure */
	//bharati commented this below part in sprint-9 for moc filter
	/*try {
		var mocRawList = JSON.parse(DownloadMocList);
		var treeStruct = {};
		for( var i = 0; i < mocRawList.length; i++){
		    if( typeof mocRawList[i]['mocYear'] != 'undefined' ){           
	            var splitMocYear = typeof mocRawList[i]['mocYear'] == "string" ? mocRawList[i]['mocYear'].split(/,?\s+/) : [];
	            for(var n = 0; n < splitMocYear.length; n++){
	                if( typeof treeStruct[splitMocYear[n]] == 'undefined'){
	                    var splitMoc = typeof mocRawList[i]['mocMonth'] == "string" ? mocRawList[i]['mocMonth'].split(/,?\s+/) : [];
	                    treeStruct[splitMocYear[n]] = splitMoc;
	                } else {
	                    var splitMoc = typeof mocRawList[i]['mocMonth'] == "string" ? mocRawList[i]['mocMonth'].split(/,?\s+/) : [];
	                    for(var j = 0; j < splitMoc.length; j++ ){
	                        if(treeStruct[splitMocYear[n]].indexOf(splitMoc[j])== -1 && j == 0){
	                            treeStruct[splitMocYear[n]].push(splitMoc[j]);
	                        }
	                    }
	                }
	            }
		    }
	    }
	
		
		var treeStructMoc = [],
			Qkey = "",
			loopKey = "";
		for(var key in treeStruct){
			treeStructMoc.push({"title": key, "subs" : [] });
			for(var i = 0; i < treeStruct[key].length; i++){
				Qkey = "";
				loopKey = treeStruct[key][i].trim();
				if( loopKey == "MOC01" || loopKey == "MOC1" ||
				    loopKey == "MOC02" || loopKey == "MOC2" ||
					loopKey == "MOC03" || loopKey == "MOC3" ){
						Qkey = "Quarter 1";
				} else if( loopKey == "MOC04" || loopKey == "MOC4" ||
				    	   loopKey == "MOC05" || loopKey == "MOC5" ||
						   loopKey == "MOC06" || loopKey == "MOC6" ){
						Qkey = "Quarter 2";
				}  else if( loopKey == "MOC07" || loopKey == "MOC7" ||
				    	   loopKey == "MOC08" || loopKey == "MOC8" ||
						   loopKey == "MOC09" || loopKey == "MOC9" ){
						Qkey = "Quarter 3";
				}  else if( loopKey == "MOC10" ||
				    	   loopKey == "MOC11" ||
						   loopKey == "MOC12" ){
						Qkey = "Quarter 4";
				} 
				var subQtrrInd = 0;
				if(treeStructMoc[treeStructMoc.length - 1].subs.length == 0){
					treeStructMoc[treeStructMoc.length - 1].subs = [{'title': Qkey, "subs": []}];
				} else {
					var exist = false;
					for(var p = 0; p < treeStructMoc[treeStructMoc.length - 1].subs.length; p++){
						if(treeStructMoc[treeStructMoc.length - 1].subs[p].title == Qkey && !exist){
							exist = true;
							subQtrrInd = p;
						}
					}
					if(!exist){
						treeStructMoc[treeStructMoc.length - 1].subs.push({'title': Qkey, "subs": []});
						subQtrrInd = treeStructMoc[treeStructMoc.length - 1].subs.length - 1;
					}
				}
				treeStructMoc[treeStructMoc.length - 1].subs[subQtrrInd].subs.push({'title': loopKey, "subs": []});
			}
		}
		
		
		/** Intiate moc combotree */
		/*$('#moc-filter').comboTree({
			id: 'moc-filter-selector',
			source : treeStructMoc,
			isMultiple : true
		});
	
	} catch (ex){
		console.log(ex);
	}*/
	
	
	/***** */
	//bharati added below code for moc bind options in sprint-9
	
	$.ajax({
		type : "GET",
		contentType : "application/json; charset=utf-8",
		cache : false,
		url : "downloadDPMOC.htm",
		success : function(data) {
			var Mocvalue1 = $.parseJSON(data);
			//console.log(branch);
			$('#Mocvalue1').empty();
			$('#Mocvalue1').append("<option value=SELECT>SELECT MOC</option>");
			$.each(Mocvalue1,
					function(key, value) {
						$('#Mocvalue1').append(
								"<option value='" + value + "'>" + value
										+ "</option>");
					});
		},
		error : function(error) {
			console.log(error)
		}
	});
	
	
	//bharati code end here
	
	$( document ).on( "change", "#comboTreemoc-filterDropDownContainer input[type=checkbox]", function(e){
				e.preventDefault();e.stopPropagation();
			     var target = $( this ),fuLen=$('.ComboTreeItemParent').length-1,selen=$( "#comboTreemoc-filterDropDownContainer input[type=checkbox]:checked" ).length;
				if( target.is( ":checked" ) ){
					
					
					var elems = $( "#comboTreemoc-filterDropDownContainer ul:first li" ).filter(function(val){ return $(this).parents("ul").length == 1 });
					for(var p = 0; p < elems.length; p++){
						if($(elems[p]).find(target).length == 0) {
							$(elems[p]).find("input").prop("checked", false);
						}
					}
				  
					
					target.closest( "li" ).find( "input" ).prop( "checked", true );
					let sublenf=target.closest('ul').find( "input" ).length, sublenu=target.closest('ul').find( "input:checked" ).length;
					if( sublenf== sublenu){
						target.closest('ul').closest('ul').last().parent( "li" ).find( "input:eq(0)" ).prop( "checked", true );
					}
					var selen=$( "#comboTreemoc-filterDropDownContainer input[type=checkbox]:checked" ).length;
					var searchIDs = $("#comboTreemoc-filterDropDownContainer input:checked").parent().map(function(){
					      return $(this).text();
					}).get();
					$('#moc-filter').val('');
					$('#moc-filter').val(searchIDs.toString());
					
				} else {
					target.closest( "li" ).find( "input" ).prop( "checked", false );
					target.parents('ul').parent( "li" ).find( "input:eq(0)" ).prop( "checked", false );
					$('#comboTreemoc-filterDropDownContainer ul li:eq(0)').find( "input:eq(0)" ).prop( "checked", false );
					var searchIDs = $("#comboTreemoc-filterDropDownContainer input:checked").parent().map(function(){
					      return $(this).text();
					}).get();
					$('#moc-filter').val('');
					$('#moc-filter').val(searchIDs.toString());
				}
			});
	
	
	$.ajaxSetup({ cache: false });
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
				var selectAll = false;
					
					var mocSelectedVal = $('#moc').comboTree({
						source : JSON.parse(moc),
						isMultiple : false
						/*select:function(item){
							mocVal = item.title;
							promoTable.draw();
						}*/
					});

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
					
					//upload file for measure report
					
					
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
					
					$('#promoIds').change(function(){
						promoId = $(this).val();
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
						
					
						//bharati code end here for sprint-9
						
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
					                  "sSearch": 'Search by Promoid:',
					                  "oPaginate": {
					                      "sNext": "&rarr;",
					                      "sPrevious": "&larr;"
					                  },
					                  "sLengthMenu": "Records per page _MENU_ ",
					                  "sEmptyTable": "No Pending Promos."
					
					              },
				             "iDisplayLength": 10,
						     "iDisplayStart": 0,
				              "sAjaxSource": "promoStatusPagination.htm",
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
				    	                {"name": "moc","value": Mocvalue},//bharati changes this mocVal to MocValue in sprint-9
				    	                {"name": "promoId","value": promoId}
				    	                );
				              }, 
				              "fnDrawCallback": function(oSettings){
				            	  $('table.promo-list-table input[type="checkbox"]').change(function() {
									    $('table.promo-list-table input[type="checkbox"]').not(this).prop('checked', false);  
									});
				              },
				              "aoColumns": [{
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
			                  }, /*{
			                    "mData": "customer_chain_l1"
			                  },*/
							  {
			                    "mData": "customerChainL2"
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
				              },*/
							  {
				                 "mData": "remark",
				              },/*{
				                 "mData": "changeDate",
				              },{
				                 "mData": "changesMade",
				              },*/{
				                 "mData": "userId",
				              },{
				                 "mData": "investmentType",
				              } ,{
				                 "mData": "solCode",
				              } ,{
				                 "mData": "solCodeDescription",
				              },{
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
						//upload for status tracker
						
						$('#chooseStatus-file').click(function() {
							$("#uploadStatus-file").trigger('click');
						});
						
						$('#uploadStatus-file').change(function(e) {
							//console.log(e);
							var files = event.target.files;
							if (files.length > 0) {
								var fileName = files[0].name;
								$('.file-status-name').html(fileName);
							} else {
								$('.file-status-name').html("No file chosen");
							}

						});
						$('.promo-lib-bg').on('click', 'a', function () {
						   // alert($(this).html());
						    window.location.replace("/VisibilityAssetTracker/proco_measure_upload.jsp");
						})
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
	window.location.assign(SelectedMoc+"/downloadPromoStatusTracker.htm");
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




function uploadStatusValidation() {
	if ($('#uploadStatus-file').val() == "") {
		ezBSAlert({
			messageText : "Please choose a file",
			alertType : "info"
		}).done(function(e) {
			// console.log(e);
		});
		return false;
	} else if ($('#uploadStatus-file').val() != "") {
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



function uploadArtDoc(isExistingUpload) {
	//var launchId = $("#dynamicLaunchId").val();
	var fileName = $('#uploadsecscre').val();
	if (fileName == '') {
		$('#uploadErrorMsg').show().html("Please select a file to upload");
		return false;
	} else {
		$('#uploadErrorMsg').hide();
		var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
		if (FileExt.toLowerCase() != "xls" && FileExt.toLowerCase() != "xlsx" ) {

			$('#uploadErrorMsg').show().html(
					"Please upload .xlsx/.xls file");
			/*
			 * $("#annexureFileUpload").submit(function(e) { e.preventDefault();
			 * });
			 */
			return false;

		}
		/*
		 * var fileName = $( "#artfile" )[0].files[0].name.substr(0, $(
		 * "#artfile" )[0].files[0].name.indexOf( "." ) );
		 * 
		 * if( fileName.indexOf( 'Basepack_Code_' ) != 0 ){
		 * $('#uploadartErrorMsg').show().html("Please enter correct file
		 * name"); return false; }
		 * 
		 * var anuxName = fileName.substr( ( 'Basepack_Code_'.length ),
		 * fileName.length );
		 * 
		 * if( anuxName != $( "#artfile" ).val() ){
		 * $('#uploadartErrorMsg').show().html("Please enter correct file
		 * name"); return false; }
		 */
	}

	// Get form
	var form = $('#coeStatusFileUpload')[0];

	// Create an FormData object
	var data = new FormData(form);

	// If you want to add an extra field for the FormData
	// data.append("CustomField", "This is some extra data, testing");

	// disabled the submit button
	// $("#btnSubmitBasePack").prop("disabled", false);

	$.ajax({
		type : "POST",
		enctype : 'multipart/form-data',
		url : 'uploadPromoStatusTracker.htm',
		data : data,
		processData : false,
		contentType : false,
		cache : false,
		timeout : 600000,
		beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
		success : function(data) {
			$('.loader').hide();
			//console.log("sucess");
			
			if(data == "File Upload is Successful"){
				promoTable.draw();
			//	window.location.href ="promoStatusTracker.htm";
			// $("#launchstrFileUploadBtn").prop("disabled", false);
				
					 $('#successblockUpload').show();
				$('#coeStatusFileUpload')[0].reset();
			//	 $('.file-caption-name').show();
				$('#erorblockUpload').hide();
			}
			else if ( data == "Error while uploading file"){
				$('#successblockUpload').hide();
				$('#erorblockUpload').show().find('span').html("Error while uploading file" + "<button type='button' class='close' data-hide='alert'></button>");
				$('#coeStatusFileUpload')[0].reset();
			}
			else if(data == "File is Empty"){
				$('#successblockUpload').hide();
				$('#erorblockUpload').show().find('span').html("File is Empty" + "<button type='button' class='close' data-hide='alert'>&times;</button>");
				$('#coeStatusFileUpload')[0].reset();
			}
			else if(data == "File Size Exceeds"){
				$('#successblockUpload').hide();
				$('#erorblockUpload').show().find('span').html("File Size Exceeds" + "<button type='button' class='close' data-hide='alert'></button>");
				$('#coeStatusFileUpload')[0].reset();
			}
			else if(data == "File does not contain relevant data"){
				$('#successblockUpload').hide();
				$('#erorblockUpload').show().find('span').html("File does not contain relevant data" + "<button type='button' class='close' data-hide='alert'>&times;</button>");
				$('#coeStatusFileUpload')[0].reset();
			}
			else if(data == "File contain the empty rows so please remove the empty rows"){
				$('#successblockUpload').hide();
				$('#erorblockUpload').show().find('span').html("File contain the empty rows so please remove the empty rows" + "<button type='button' class='close' data-hide='alert'>&times;</button>");
				$('#coeStatusFileUpload')[0].reset();
			}
			else if(data == "Column count is not match with expected"){
				$('#successblockUpload').hide();
				$('#erorblockUpload').show().find('span').html("Column count is not match with expected" + "<button type='button' class='close' data-hide='alert'></button>");
				$('#coeStatusFileUpload')[0].reset();
			}
			else if(data == "File movement process is UnSuccessful"){
				$('#successblockUpload').hide();
				$('#erorblockUpload').show().find('span').html("File movement process is UnSuccessful" + "<button type='button' class='close' data-hide='alert'>&times;</button>");
				$('#coeStatusFileUpload')[0].reset();
			}
			
		},
		error : function(e) {
			$("#uploadErrorMsg").text(e.responseText);
			// console.log("ERROR : ", e);
			// $("#launchstrFileUploadBtn").prop("disabled", true);

		}
	});

}




function uploadMeas(isExistingUpload) {
	//var launchId = $("#dynamicLaunchId").val();
	var fileName = $('#uploadmeasscre').val();
	if (fileName == '') {
		$('#uploadErrorMsg').show().html("Please select a file to upload");
		return false;
	} else {
		$('#uploadErrorMsg').hide();
		var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
		if (FileExt.toLowerCase() != "xls" && FileExt.toLowerCase() != "xlsx" ) {

			$('#uploadErrorMsg').show().html(
					"Please upload .xlsx/.xls file");
			
			return false;

		}
		
	}

	// Get form
	var form = $('#coeStatusMeasFileUpload')[0];

	// Create an FormData object
	var data = new FormData(form);

	
	$.ajax({
		type : "POST",
		enctype : 'multipart/form-data',
		url : 'uploadProcoMeasureReport.htm',
		data : data,
		processData : false,
		contentType : false,
		cache : false,
		timeout : 600000,
		beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
		success : function(data) {
			$('.loader').hide();
			//console.log("sucess");
			displayUploadStatus(data);
		},
		error : function(e) {
			$("#uploadErrorMsg").text(e.responseText);
			// console.log("ERROR : ", e);
			// $("#launchstrFileUploadBtn").prop("disabled", true);

		}
	});
	
	

}

function displayUploadStatus(data){
	data = decodeURI(data);
	if(data == "File Upload is Successful"){
		//promoTable.draw();
	//	window.location.href ="promoStatusTracker.htm";
	// $("#launchstrFileUploadBtn").prop("disabled", false);
		
			 $('#successblockUpload').show();
		$('#coeStatusMeasFileUpload')[0].reset();
	//	 $('.file-caption-name').show();
		$('#erorblockUpload').hide();
	}
	else if ( data == "Error while uploading file"){
		$('#successblockUpload').hide();
		$('#erorblockUpload').show().find('span').html("Error while uploading file" + "<a href='downloadProcoMeasureReportErrorFile.htm' id='downloadStstTempFileLink'>Click here to Download Measure Report Error File:</a>" + "<button type='button' class='close' data-hide='alert'></button>");
		$('#coeStatusMeasFileUpload')[0].reset();
	}
	else if(data == "File is Empty"){
		$('#successblockUpload').hide();
		$('#erorblockUpload').show().find('span').html("File is Empty" + "<button type='button' class='close' data-hide='alert'>&times;</button>");
		$('#coeStatusMeasFileUpload')[0].reset();
	}
	else if(data == "File Size Exceeds"){
		$('#successblockUpload').hide();
		$('#erorblockUpload').show().find('span').html("File Size Exceeds" + "<button type='button' class='close' data-hide='alert'></button>");
		$('#coeStatusMeasFileUpload')[0].reset();
	}
	else if(data == "File does not contain relevant data"){
		$('#successblockUpload').hide();
		$('#erorblockUpload').show().find('span').html("File does not contain relevant data" + "<button type='button' class='close' data-hide='alert'>&times;</button>");
		$('#coeStatusMeasFileUpload')[0].reset();
	}
	else if(data == "File contain the empty rows so please remove the empty rows"){
		$('#successblockUpload').hide();
		$('#erorblockUpload').show().find('span').html("File contain the empty rows so please remove the empty rows" + "<button type='button' class='close' data-hide='alert'>&times;</button>");
		$('#coeStatusMeasFileUpload')[0].reset();
	}
	else if(data == "Column count is not match with expected"){
		$('#successblockUpload').hide();
		$('#erorblockUpload').show().find('span').html("Column count is not match with expected" + "<button type='button' class='close' data-hide='alert'></button>");
		$('#coeStatusMeasFileUpload')[0].reset();
	}
	else if(data == "File movement process is UnSuccessful"){
		$('#successblockUpload').hide();
		$('#erorblockUpload').show().find('span').html("File movement process is UnSuccessful" + "<button type='button' class='close' data-hide='alert'>&times;</button>");
		$('#coeStatusMeasFileUpload')[0].reset();
	}
}


/*function abc(event) {
	  event.preventDefault();
	  var href = event.currentTarget.getAttribute('href')
	  window.location.href = 'proco_measure_upload.jsp';
	}*/
//loader

function ajaxLoader(w, h) {

    var left = (window.innerWidth / 2) - (w / 2);
    var top = (window.innerHeight / 2) - (h / 2);
    $('.loader').css('display', 'block');

    $('.loading-image').css({
        "left": left,
        "top": top,
        
    });
}

/**
 * Todo: Download moc measure report
 * Validation: MOC not null	
 */
function downloadMeasureReport(e){
	var MocVal = $("#moc-filter").val();
	if(MocVal.trim() == ""){
		e.preventDefault();
		$("#MocDownloadErorblockUpload").show();
		return false;
	}
	
	var spltMoc  = MocVal.split(','),
		MocMonth = "",
		MocYear  = "";
		for( var i = 0; i < spltMoc.length; i++ ){
			if(spltMoc[i].indexOf('MOC') != -1){
				if(MocMonth != ""){
					MocMonth += ',';
				}
				MocMonth += spltMoc[i];
			}
		}
		
		$("#MocYear").val($("#comboTreemoc-filterDropDownContainer input:checked").parents("ul").last().find("li:first input:first").parent().text());
		$("#MocMonth").val(MocMonth);
		
	
}
//bharati added code for PPlinkage upload in sprint-9 US-15
	
					$("#btnSubmitBasePack1").click(function (event) {
						event.preventDefault();
						
						var fileName = $('#uploadmeasscre').val();
						if (fileName == '') {
							$('#uploadErrorMeaMsg').show().html("Please select a file to upload");
							return false;
						} else {
							$('#uploadErrorMeaMsg').hide();
							var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
							if (FileExt != "xlsx") {
								if (FileExt != "xls") {
									$('#uploadErrorMeaMsg').show().html("Please upload .xls or .xlsx file");
									$("#coeStatusMeasFileUpload").submit(function(e) {
										e.preventDefault();
									});
									return false;
								}

							}
						}
						
				        // Get form
				        var form = $('#coeStatusMeasFileUpload')[0];
                           var data = new FormData(form);
                        $.ajax({
				            type: "POST",
				            enctype: 'multipart/form-data',
				            url: 'ppminkageupload.htm' ,
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
				                    $('#ppmerrorblockUpload').hide();
				                	$('#ppmsuccessblock').show().find('span').html(' File Uploaded Successfully !!!');
				                	 
				                }
				                
				                else if(resdata.includes('EXCEL_NOT_UPLOADED')){
				               $('#ppmsuccessblock').hide();
				                $('#ppmerrorblockUpload').show().find('span').html(' File Contains Error ');
				                }
								 else if(resdata.includes('File Size Exceeds')){
									 $('#ppmerrorblockUpload').show().find('span').html('File Size Limit Exceeded');
                                     $('#ppmsuccessblock').hide();
								 }
				                else if(resdata.includes('FILE_EMPTY')){
									$('#ppmerrorblockUpload').show().find('span').html('Error While Uploading Empty File');
									$('#ppmsuccessblock').hide();
								}
								else if(resdata.includes('CHECK_COL_MISMATCH')){
									$('#ppmerrorblockUpload').show().find('span').html('Please Check Uploaded File');
									$('#ppmsuccessblock').hide();
								}else if(resdata.includes('Column count is not match with expected')){
									$('#ppmerrorblockUpload').show().find('span').html('Please Upload correct File');
									$('#ppmsuccessblock').hide();
								}
								else{
									
				                	$('#ppmerrorblockUpload').show().find('span').html('Error While Uploading File');
				                	$('#ppmsuccessblock').hide();
				                	
				                	 
				              	 }
				                    
				                 $('#coeStatusMeasFileUpload')[0].reset();
								 //$('.file-name').html("No file chosen");
				            },
				            error: function (e) {
				            $('#ppmsuccessblock').hide();
				                 
				            }
				        });
				       
					    
					    	
				    });
		//bharati added below function for sprint-9
	function downloadMeasureReport(){
	//$("#download").submit();  //bharati commented this line for sprint-9 moc filter value pass to download promo file
	var SelectedMoc = $("#Mocvalue1").val();
	                 if(SelectedMoc == 'SELECT'){
							$('#selectMsgMoc').show();
						     
						}else{
							$('#selectMsgMoc').hide();
							window.location.assign("dpMesureDownloadBasedOnMoc.htm?moc="+SelectedMoc);
						}
	
}
					//bharati code end here for sprint-9
					
//bharati added sprint-9 ppm coe remark upload changes	
function downloadPpmReport(){
	//$("#download").submit();  //bharati commented this line for sprint-9 moc filter value pass to download promo file
	var SelectedppmMoc = $("#ppmMocvalue").val();
	                 if(SelectedppmMoc == 'SELECT'){
							$('#selectppmMsgMoc').show();
						     
						}else{
							$('#selectppmMsgMoc').hide();
							window.location.assign("dpMesureDownloadBasedOnMoc.htm?moc="+SelectedppmMoc);
						}
	
}


$("#btnSubmitPpmCoeRemark").click(function (event) {
						event.preventDefault();
						
						var fileName = $('#uploadppmCoeRemark').val();
						if (fileName == '') {
							$('#uploadppmErrorMeaMsg').show().html("Please select a file to upload");
							return false;
						} else {
							$('#uploadppmErrorMeaMsg').hide();
							var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
							if (FileExt != "xlsx") {
								if (FileExt != "xls") {
									$('#uploadppmErrorMeaMsg').show().html("Please upload .xls or .xlsx file");
									$("#ppmCoeRemarkUpload").submit(function(e) {
										e.preventDefault();
									});
									return false;
								}

							}
						}
						
				        // Get form
				        var form = $('#ppmCoeRemarkUpload')[0];
                           var data = new FormData(form);
                        $.ajax({
				            type: "POST",
				            enctype: 'multipart/form-data',
				            url: 'ppmcoeremarksupload.htm' ,
				            data: data,
				            processData: false,
				            contentType: false,
				            cache: false,
				            timeout: 600000,
				            beforeSend: function() {
                                ajaxLoader(spinnerWidth, spinnerHeight);
                            },
							
				            success: function (ModRes) {
				            	//console.log(resdata);
				            	
				            	 $('.loader').hide();
				            	 if(ModRes.includes('EXCEL_UPLOADED')) {
				                    $('#ppmcoeerrorblockUpload').hide();
				                	$('#ppmcoesuccessblock').show().find('span').html(' File Uploaded Successfully !!!');
				                	 
				                }
				                
				                else if(ModRes.includes('EXCEL_NOT_UPLOADED')){
				               $('#ppmcoesuccessblock').hide();
				                $('#ppmcoeerrorblockUpload').show().find('span').html(' File Contains Error ');
				                }
								 else if(ModRes.includes('File Size Exceeds')){
									 $('#ppmcoeerrorblockUpload').show().find('span').html('File Size Limit Exceeded');
                                     $('#ppmcoesuccessblock').hide();
								 }
				                else if(ModRes.includes('FILE_EMPTY')){
									$('#ppmcoeerrorblockUpload').show().find('span').html('Error While Uploading Empty File');
									$('#ppmcoesuccessblock').hide();
								}
								else if(ModRes.includes('CHECK_COL_MISMATCH')){
									$('#ppmcoeerrorblockUpload').show().find('span').html('Please Check Uploaded File');
									$('#ppmcoesuccessblock').hide();
								}else if(ModRes.includes('Column count is not match with expected')){
									$('#ppmcoeerrorblockUpload').show().find('span').html('Please Upload correct File');
									$('#ppmcoesuccessblock').hide();
								}
								else{
									
				                	$('#ppmcoeerrorblockUpload').show().find('span').html('Error While Uploading File');
				                	$('#ppmcoesuccessblock').hide();
				                	
				                	 
				              	 }
				                    
				                 $('#ppmCoeRemarkUpload')[0].reset();
								 //$('.file-name').html("No file chosen");
				            },
				            error: function (e) {
				            $('#ppmcoesuccessblock').hide();
				                 
				            }
				        });
				       
					    
					    	
				    });
