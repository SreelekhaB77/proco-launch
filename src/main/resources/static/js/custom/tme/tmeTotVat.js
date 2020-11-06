$(document).ready(function() {
	$('#uploadLetter,#crossBtnAdd,#crossBtnRep,#crossBtnRep').click(function(){
		$('.brands').empty();
		$('.brands').multiselect("destroy");
		$('brands .btn-group').hide();
	})
	
	$('#showValidation').hide();
	$('#showValidationRep').hide();
	$('#showValidationPost').hide();
	$('#showValidationExtn').hide();
	$('#replace-asset').attr('disabled','disabled');
	$('#postpone-asset').attr('disabled','disabled');
	$('#extend-asset').attr('disabled','disabled');
	
	$("#input-2").change(function() {
		$('#successblock').hide();
		$('#errorblock').hide();
		$('#errMsg').hide();
		$('.validate_upload1').prop('disabled', false);
	});
	
	//for replace upload 
	
	$("#inputReplaceAsset").change(function() {
		$('#successblock').hide();
		$('#errorblock').hide();
		$('#errMsg').hide();
		$('.validate_upload_replace').prop('disabled', false);
	
	});
	
	//drop down for MOC tracking
	
	
	var today = new Date(),
	    yy = today.getFullYear(),
	    m = today.getMonth(),
	    mm = m + 1;

	var moc = [];
	for( var i = 0; i < 6; i++ ){
		if( moc.length == 0 ){
			var lastMonth = mm - 1 == 0 ? 12 : mm;
			lastMonth = ( lastMonth + "" ).length == 1 ? "0" + lastMonth : lastMonth; 
			var lastYear = mm - 1 == 0 ? yy - 1 : yy;
			moc.push( { value : lastMonth + "" + lastYear } );
		} else {
			var lastMonth = mm + i >= 13 ? ( ( mm + i ) - 12 ) : m + i;
			lastMonth = ( lastMonth + "" ).length == 1 ? "0" + lastMonth : lastMonth; 
			var lastYear = mm + i >= 13 ? yy + 1 : yy;
			moc.push( { value :  lastMonth + "" + lastYear } );
		}
	}
		
	var optionclassfi = "";
	optionclassfi += "<option value='Select MOC'>Select</option>";
	$.each(moc, function(key, value) {
		optionclassfi += "<option value='" + value.value
				+ "'>" + value.value + "</option>";
	});
	/*$('#trackMocList').append(optionclassfi);*/
	$('#connectMocList').append(optionclassfi);
	
	
	//for download enable in replace
	
	
$('#connectMocList').change(function(){
		
		var getSelectedVal = $(this).val();
		
		if(getSelectedVal == "Select MOC"){
			
			 $(':input[type="submit"]').prop('disabled', true);
			        		
		}else{
			
			 $(':input[type="submit"]').prop('disabled', false);
		} 
		
		}); 
		//on selection of radio button drop down enabled
		/*$( document ).on("change", '#trackMoc', function () {
			if($( this ).is( ":checked" ) ){
				$('#trackMocList').prop("disabled", false);
				$('#connectMocList').prop("disabled", true);
			}
			else {
				$('#trackMocList').prop("disabled", true);
			}
		});
		
		$( document ).on("change", '#ConnectMoc', function () {
			if($( this ).is( ":checked" ) ){
				$('#connectMocList').prop("disabled", false);
				$('#trackMocList').prop("disabled", true);
			}
			else {
				$('#connectMocList').prop("disabled", true);
			}
		});*/
	
	$('#tme-deleteTotAsset').on('hidden.bs.modal', function () {
		$('#replace-asset').attr('disabled','disabled');
		$('table.totVat-table input[type="checkbox"]').prop('checked', false);
	})
	
	$("#repOtherCheckbox").click(function () {
	     if ($(this).is(":checked")) {
	         $("#optional_asset_rep").show();
	         $("#main_asset_rep").hide();
	     } 
	     else if ($(this).not(":checked")) {
	    	 $("#optional_asset_rep").hide();
			    $("#main_asset_rep").show();
	     }
	});
	$("#chkPassport").click(function () {
	     if ($(this).is(":checked")) {
	         $("#optional_asset").show();
	         $("#main_asset").hide();
	     } 
	     else if ($(this).not(":checked")){
	         $("#optional_asset").hide();
	         $("#main_asset").show();
	     }
	     else{
		$("#optional_asset").hide();
	    $("#main_asset").show();
	     }
	});
	
	$("body").on("change",".fileData",function() {

			var checkboxs = document.getElementsByClassName("fileData");
			
			 $('table.totVat-table input[type="checkbox"]').not(this).prop('checked', false);  
			
			for (var i = 0, l = checkboxs.length; i < l; i++) {
				if (checkboxs[i].checked) {
					$('#replace-asset').attr("disabled", false);
					$('#postpone-asset').attr('disabled',false);
					$('#extend-asset').attr('disabled',false);
					return false;
				}
				else {
					$('#replace-asset').attr('disabled','disabled');
					$('#postpone-asset').attr('disabled','disabled');
					$('#extend-asset').attr('disabled','disabled');
				}
				
			}
	});
	
	$("[data-hide]").on("click",function() {
		 $(this).closest("."+ $(this).attr("data-hide")).hide();
	 });
	
		$('#start_date').datepicker();
		var selectAll = false;
		var selectedBrandList = null;

        $('#change_pswd').on('show.bs.modal', function(e) {
            var link = $(e.relatedTarget);
            $(this).find(".tme-password").load(link.attr("href"));
        });
        $(".newStore,.deleteStore,#coefileupload").hide();
       	 $("#newStore").click(function() {
     	      $(".continueStore,.deleteStore,#catmanFileUpload").hide();
     	      $(".newStore,#coefileupload").show();
     	      storeType="new";
     	      table.draw();
      });
     	$("#closedStore").click(function() {
     	      $(".continueStore,.newStore,#coefileupload,#catmanFileUpload").hide();
     	      $(".deleteStore").show();
     	   storeType="closed";
     	   table.draw();
     	});
     	$("#continuingStores").click(function() {
     	      $(".deleteStore,.newStore,#coefileupload").hide();
     	      $(".continueStore,#catmanFileUpload").show();
     	      storeType="continue";
     	      $('#replace-asset').attr("disabled", "disabled");
     	      table.draw();
     	});
       
       	$(".close").click(function(){
       		$("#successblock").hide();
       	});
       	
       	$("#reset, #crossBtnAdd, #uploadLetter").click(function(){
       		$("#account_modal, #cluster_modal, #format_modal, #godown_modal, .subcat, .brands, .subcat").multiselect("clearSelection");
       		$("#startdate, #enddate, #other_subcat, #other_brands").val('');
       		$('#selstoreCount').val(0);
       		$('#showValidation').hide();
       		$("#assets")[0].selectedIndex = 0;
       		$("#assetdesc")[0].selectedIndex = 0;
       		$('#addOtherCheckbox').prop("checked", false);
       	 $("#add-optional_asset").hide();
         $("#add-main_asset").show();
       	});
       	
       	
       	$('#account').multiselect({
     		includeSelectAllOption: true,
     		buttonWidth: '100%',
     		enableFiltering: true,
     		enableCaseInsensitiveFiltering: true,
     		nonSelectedText: 'ALL ACCOUNTS',
     		onChange : function(option, checked,select) {
     			 var custChainSelectedData = [];
				 var selectedOptionsChange = $('#account option:selected');
				 var totalLen = $('#account option').length;

                    if (selectedOptionsChange.length > 0 && selectedOptionsChange.length < totalLen){
						selectAll = false;
					}else if(selectedOptionsChange.length == totalLen){
						selectAll = true;
					}
				 
                    for (var i = 0; i < selectedOptionsChange.length; i++) {
                    	custChainSelectedData.push(selectedOptionsChange[i].value);

                    }
                    if(selectAll == true){
                    	account = "ALL";
                    }else{
                    	account = custChainSelectedData.toString();
                    }
     			table.draw();
			},
     		onDropdownHide: function(event) {
            },
            onDropdownShow: function(event) {
                var menu = $(event.currentTarget).find(".dropdown-menu");		                       
                menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"});
                		                        
           },
           onSelectAll : function() {
				account = "ALL";
				table.draw();
			},
			onDeselectAll : function() {
				account = "ALL";
				table.draw();
			}
     });
               	
              
			$('#outlet_modal').multiselect({
         		includeSelectAllOption: true,
         		buttonWidth: '100%',
         		nonSelectedText: 'ALL ACCOUNTS',
         		enableFiltering: true,
         		enableCaseInsensitiveFiltering: true,
         		onDropdownHide: function(event) {
         			getcount();
                },
                onDropdownShow: function(event) {
                    var menu = $(event.currentTarget).find(".dropdown-menu");		                       
                    menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"});
                    		                        
               }
         });
			
			$('#format').multiselect({
         		includeSelectAllOption: true,
         		buttonWidth: '100%',
         		nonSelectedText: 'ALL FORMATS',
         		enableCaseInsensitiveFiltering: true,
         		enableFiltering: true,
         		onChange : function(option, checked, select) {
         			 var custChainSelectedData = [];
					 var selectedOptionsChange = $('#format option:selected');
					 var totalLen = $('#format option').length;

	                    if (selectedOptionsChange.length > 0 && selectedOptionsChange.length < totalLen){
							selectAll = false;
						}else if(selectedOptionsChange.length == totalLen){
							selectAll = true;
						}
					 
	                    for (var i = 0; i < selectedOptionsChange.length; i++) {
	                    	custChainSelectedData.push(selectedOptionsChange[i].value);

	                    }
	                    if(selectAll == true){
	                    	format = "ALL";
	                    }else{
	                    	format = custChainSelectedData.toString();
	                    }
         			table.draw();
				},
         		onDropdownHide: function(event) {
                },
                onDropdownShow: function(event) {
                    var menu = $(event.currentTarget).find(".dropdown-menu");		                       
                    menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"});
                    		                        
               },
               onSelectAll : function() {
					format = "ALL";
					table.draw();
				},
				onDeselectAll : function() {
					format = "ALL";
					table.draw();
				}
         });
			
			$('#cluster').multiselect({
         		includeSelectAllOption: true,
         		buttonWidth: '100%',
         		nonSelectedText: 'ALL CLUSTERS',
         		enableFiltering: true,
         		enableCaseInsensitiveFiltering: true,
         		onChange : function(option, checked,
						select) {
         			 var custChainSelectedData = [];
					 var selectedOptionsChange = $('#cluster option:selected');
					 var totalLen = $('#cluster option').length;

	                    if (selectedOptionsChange.length > 0 && selectedOptionsChange.length < totalLen){
							selectAll = false;
						}else if(selectedOptionsChange.length == totalLen){
							selectAll = true;
						}
					 
	                    for (var i = 0; i < selectedOptionsChange.length; i++) {
	                    	custChainSelectedData.push(selectedOptionsChange[i].value);

	                    }
	                    if(selectAll == true){
	                    	cluster = "ALL";
	                    }else{
	                    	cluster = custChainSelectedData.toString();
	                    }
         			table.draw();
				},
         		onDropdownHide: function(event) {
                },
                onDropdownShow: function(event) {
                    var menu = $(event.currentTarget).find(".dropdown-menu");		                       
                    menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"});
                    		                        
               },
               onSelectAll : function() {
					cluster = "ALL";
					table.draw();
				},
				onDeselectAll : function() {
					cluster = "ALL";
					table.draw();
				}
			});
			
			
			
			$('#godown').multiselect({
         		includeSelectAllOption: true,
         		buttonWidth: '100%',
         		//dropRight: true,
         		enableFiltering: true,
         		enableCaseInsensitiveFiltering: true,
         		nonSelectedText: 'ALL GODOWNS',
         		onChange : function(option, checked,
						select) {
         			 var custChainSelectedData = [];
					 var selectedOptionsChange = $('#godown option:selected');
					 var totalLen = $('#godown option').length;

	                    if (selectedOptionsChange.length > 0 && selectedOptionsChange.length < totalLen){
							selectAll = false;
						}else if(selectedOptionsChange.length == totalLen){
							selectAll = true;
						}
					 
	                    for (var i = 0; i < selectedOptionsChange.length; i++) {
	                    	custChainSelectedData.push(selectedOptionsChange[i].value);

	                    }
	                    if(selectAll == true){
	                    	hfsGodownLocation = "ALL";
	                    }else{
	                    	hfsGodownLocation = custChainSelectedData.toString();
	                    }
         			table.draw();
				},
         		onDropdownHide: function(event) {
                },
                onDropdownShow: function(event) {
                    var menu = $(event.currentTarget).find(".dropdown-menu");		                       
                    menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"});
               },
               onSelectAll : function() {
            	   hfsGodownLocation = "ALL";
					table.draw();
				},
				onDeselectAll : function() {
					hfsGodownLocation = "ALL";
					table.draw();
				}
               
         });
			
								
			table= $('.totVat-table').DataTable(
					{
						"ordering" : true,
						"bServerSide" : true,
						"iDisplayLength" : 5,
						"iDisplayStart" : 0,
						"responsive": true,
						"bFilter" : true,
						"scrollX" : "100%",
						"scrollCollapse" : true,
						"bLengthChange" : true,
						"lengthMenu" : 
								
							 [[ 5, 10, 25, 50, 100 ],
							 [ 5, 10, 25, 50, 100 ] ],
							 
							"bInfo" : true,
							"oLanguage" : {
								"sSearch" : '<i class="icon-search"></i>',
								"oPaginate" : {
									"sNext" : "&rarr;",
									"sPrevious" : "&larr;"
								},
								"sLengthMenu" : "Records per page _MENU_ ",
								"sEmptyTable" : "No Pending Visibilities."

							},			

						"sAjaxSource" : "tmeTotVisibilityPagination.htm",
						 "fnServerParams": function(aoData) {
				                aoData.push(
				    	                {"name": "account", "value": account}, 
				    	                {"name": "format", "value": format},
				    	                {"name": "cluster","value": cluster}, 
				    	                {"name": "hfsGodownLocation", "value": hfsGodownLocation},
				    	                {"name": "storeType","value":storeType}
				    	                );
				              }, 
						
				       "aoColumns": [
				    	   {
								"mData" : "tid",
								"mRender" : function(data,
										type, full) {
									return '<input type="checkbox" class="fileData" name="file-name" id="file-name" value='
											+ data + '>';
								}
							},
							{ "mData": "tid" },
				    	   	{ "mData": "asset_type" },
				            { "mData": "asset_description" },
				            { "mData": "subcategory" },
				            { "mData": "brand" },
				            { "mData": "connectivityMoc" },
				            { "mData": "trackingMoc"},
				            { "mData": "start_date"},
				            { "mData": "end_date"},
				            { "mData": "store_count"},
				            { "mData": "skuStatus",
				            		"mRender": function ( data, type, full ) {
					            	 if(data=='YES'){
							            	return '<a href="getSkuDetails.htm?refNo='+full.tid+'" class="modelClick" oncontextmenu="return false" data-toggle="modal" data-target="#myModal">'+data+'</a>';
							            	// return '<a href="catPreview.htm?visi_ref_no='+data+'" class="modelClick" oncontextmenu="return false" data-toggle="modal" data-target="#cat-viewModal">'+data+'</a>';
					            	 }else{
						            	 return 'NO';
						            	 }
				            	 }
				            },
				            
				            { "mData": "skuStatus",
				            	 "mRender": function ( data, type, full ) {
					            	 if(data=='YES'){
							            	return '<a href="downloadSkuDetails.htm?refNo='+full.tid+'">'+data+'</a>';
							            	// return '<a href="catPreview.htm?visi_ref_no='+data+'" class="modelClick" oncontextmenu="return false" data-toggle="modal" data-target="#cat-viewModal">'+data+'</a>';
					            	 }else{
						            	 return 'NO';
						            	 }
										      }
				     		},

				     		{ "mData": "skuDeleteStatus",
				            	 "mRender": function ( data, type, full ) {
					            	 if(data=='YES'){
							            	//return '<a href="deleteSkuDetails.htm?refNo='+full.visi_ref_no+'">'+data+'</a>';
							            	//return "<a href='#' onclick='deletePlan(\"" + full.visi_ref_no + "\")'>\"" + data + "\"</a>";
							            	return "<a href='#' onclick='deleteSku(\"" + full.tid + "\")'>" + data + "\</a>";
							            	// return '<a href="catPreview.htm?visi_ref_no='+data+'" class="modelClick" oncontextmenu="return false" data-toggle="modal" data-target="#cat-viewModal">'+data+'</a>';
					            	 }else{
						            	 return 'NO';
						            	 }

							      		}
				     				},

				            { "mData" : "tid",
							  "mRender" : function(data,type,full) {
									return '<a href="deleteTotAssetRequest.htm?tIds='+ data +'" class="modelClick" oncontextmenu="return false" data-toggle="modal" data-target="#tme-deleteTotAsset"><img src="assets/images/delete.png"></a>';
								}
							}
				        ]
			
		});
//		delete row
		
	/*$('.totVat-table').on("click", "a", function(){
		  console.log($(this).parent());
		  table.row($(this).parents('tr')).remove().draw(false);
	});*/
	
			
			
			
	$('#DataTables_Table_0_length').css({
		'text-align' : 'right',
		'padding' : '21px 10px 0px 15px',
		'float' : 'left'
	});
	$('#DataTables_Table_0_length label').css({
		'color' : '#29290a'
	});

	$(
			$($('#DataTables_Table_0_wrapper .row')[0]).find(
					'.col-sm-6')[0]).css({
		'float' : 'right',
		'padding' : '0 20px 0 0',
		'width' : 'auto'
	});
	$(
			$($('#DataTables_Table_0_wrapper .row')[0]).find(
					'.col-sm-6')[0]).addClass(
			"searchicon-wrapper");

	$(
			$($('#DataTables_Table_0_wrapper .row')[0]).find(
					'.col-sm-6')[0]).append(
			$(".dataTables_filter"));
	$(
			$($('#DataTables_Table_0_wrapper .row')[0]).find(
					'.col-sm-6')[0]).append($(".search-icon"));

	$(
			$($('#DataTables_Table_0_wrapper .row')[0]).find(
					'.col-sm-6')[1]).css({
		'width' : 'auto',
		'float' : 'left',

	});
	$($('#DataTables_Table_0_wrapper .row')[0]).append(	$(".summary-text"));
	$($('#DataTables_Table_0_wrapper .row')[0]).append(	$(".coe-text"));
	$($('#DataTables_Table_0_wrapper .row')[0]).append(	$(".selectAll"));	

	 $('#drop-close-btn').on('click',function(){
		 table.draw();
			$('#confirmDelete').modal('hide');
		});
	
	$('#confirmDelete').find('.modal-footer #confirm').on('click', function(){
    	$('#drop-errorblock').hide();
    	 $('#drop-successblock').hide();
    	var bmi_ref = $("#drop_sku").val();
		  $.ajax({
                url: "deleteSkuDetails.htm",
                type: "post",
                data: 'checkValues=' + bmi_ref,
                cache: false,
                beforeSend: function() {
                	$('.popupLoader').show();
                },
                success: function(data) {
                 // $('#drop-plan').modal('hide');
                  $('.popupLoader').hide();
                  if (data == 1) {
                	  $('#confirm').prop('disabled',true);
                    $('#drop-successblock').show().find('span').html('SKU Deleted Successfully !!!');
                  
                  }
                }
              });
	  });
	
});

function deleteSku(bmi_ref) {
	  $('#drop-errorblock').hide();
 	 $('#drop-successblock').hide();
 	$('#confirm').prop('disabled',false);
    $("#drop_sku").val(bmi_ref);
    $('#confirmDelete').modal("show");	 
  }

function ajaxLoader(w, h) {

    var left = (window.innerWidth / 2) - (w / 2);
    var top = (window.innerHeight / 2) - (h / 2);
    $('.loader').css('display', 'block');
    $('.loading-image').css({
      "left": left,
      "top": top
    });
  }
	function getcount() {
		  var accountsList = encodeURIComponent($("select#account_modal").val());
		  var formatsList = encodeURIComponent($("select#format_modal").val());
		  var clustersList = encodeURIComponent($("select#cluster_modal").val());
		  var godownLocationList = encodeURIComponent($("select#godown_modal").val());
		  $('#selstoreCount').val(0);
		$
				.ajax({
					type : "GET",
					url : "getTmeTotVisibilityStoreCount.htm?accountsList="
							+ accountsList + "&formatsList=" + formatsList
							+ "&clustersList=" + clustersList
							+ "&godownLocationList=" + godownLocationList
							+ "&storeType=" + storeType,
					async : false,
					dataType : "text",
					success : function(response) {
						var storeList = $.parseJSON(response);
						$('.switch-dynamic-outlet')
								.html(
										'<select class="form-control" name="outlets" id="outlet_modal" multiple="multiple"><option values="ALL OUTLETS">ALL OUTLETS</option>');
						$('#storeCount').val(storeList.length);
						$('#outlet_modal').empty();
						$('#outlet_modal').multiselect("destroy");
						$.each(storeList, function(ind, val) {
							$('#outlet_modal').append(
									'<option value="' + val + '">' + val
											+ '</option>');
						});
						multiSelectionForOutlets();
					},
					error : function(xhr, status, error) {
						console
								.log(" xhr.responseText: " + xhr.responseText
										+ " //status: " + status + " //Error: "
										+ error);

					}
				});
	}
	
	function getNewStoresData() {
		if(storeType=="continue"){
			$('#publish').hide();
			$('#discard').hide();
		}
		$.ajax({
					type : "GET",
					url : "getTmeTotVisibilityStoreCountForNew.htm?storeType="+storeType,
					async : false,
					dataType : "text",
					success : function(response) {
						var data = $.parseJSON(response);
						var storeList = data.outlets;
						var accountList = data.accounts;
						var formatList = data.formats;
						var clusterList = data.clusters;
						var hfsGodownList = data.hfsGodowns;
						var isPublish = data.isPublish;
						
						if(storeType == "new" && isPublish == true){
							$('#publish').show();
							$('#publish').prop('disabled', false);
							$('#discard').show();
							$('#discard').prop('disabled', false);
						}else if(storeType == "new" && isPublish == false){
							$('#publish').show();
							$('#publish').attr("disabled", "disabled");
							$('#discard').show();
							$('#discard').attr("disabled", "disabled");
						}
						
						$('#account_modal').empty();
						$('#account_modal').multiselect("destroy");
						$.each(accountList, function(ind, val) {
							$('#account_modal').append(
									'<option value="' + val + '">' + val
											+ '</option>');
						});
						muliSelectionForAccounts();
						
						$('#format_modal').empty();
						$('#format_modal').multiselect("destroy");
						$.each(formatList, function(ind, val) {
							$('#format_modal').append(
									'<option value="' + val + '">' + val
											+ '</option>');
						});
						muliSelectionForFormats();
						
						$('#cluster_modal').empty();
						$('#cluster_modal').multiselect("destroy");
						$.each(clusterList, function(ind, val) {
							$('#cluster_modal').append(
									'<option value="' + val + '">' + val
											+ '</option>');
						});
						muliSelectionForClusters();
						
						$('#godown_modal').empty();
						$('#godown_modal').multiselect("destroy");
						$.each(hfsGodownList, function(ind, val) {
							$('#godown_modal').append(
									'<option value="' + val + '">' + val
											+ '</option>');
						});
						muliSelectionForGodowns();
						
						$('.switch-dynamic-outlet')
								.html(
										'<select class="form-control" name="outlets" id="outlet_modal" multiple="multiple"><option values="ALL OUTLETS">ALL OUTLETS</option>');
						$('#storeCount').val(storeList.length);
						$('#outlet_modal').empty();
						$('#outlet_modal').multiselect("destroy");
						$.each(storeList, function(ind, val) {
							$('#outlet_modal').append(
									'<option value="' + val + '">' + val
											+ '</option>');
						});
						multiSelectionForOutlets();
					},
					error : function(xhr, status, error) {
						console
								.log(" xhr.responseText: " + xhr.responseText
										+ " //status: " + status + " //Error: "
										+ error);

					}
				});
	}


	function getBrands(selVal) {
		$.ajax({
			type : "GET",
			url : "getTmeTotVisibilityBrands.htm?subcategories="+encodeURIComponent(selVal),
			async : false,
			success : function(data) {
				var smallcData = $.parseJSON(data);
				$('.brands').empty();
				$('.brands').multiselect("destroy");
				$.each(smallcData,
						function(ind, val) {
							$('.brands').append(
									'<option value="'+val+'">' + val
											+ '</option>');
						});
				multiSelectionForBrands();
			},
			error : function(error) {
				console.log(error)
			}
	
		});
	
	}
	
	function getBrandsRep(selVal) {
		$.ajax({
			type : "GET",
			url : "getTmeTotVisibilityBrands.htm?subcategories="+encodeURIComponent(selVal),
			async : false,
			success : function(data) {
				var smallcData = $.parseJSON(data);
				$('.brandsValidate').empty();
				$('.brandsValidate').multiselect("destroy");
				$.each(smallcData,
						function(ind, val) {
							if(selectedBrandList.includes(val)){
								$('.brandsValidate').append(
										'<option value="'+val+'" selected>' + val
												+ '</option>');
							}else{
							$('.brandsValidate').append(
									'<option value="'+val+'">' + val
											+ '</option>');
							}
						});
				multiSelectionForBrandsRep();
				selectedBrandList=[];
			},
			error : function(error) {
				console.log(error)
			}
	
		});
	
	}

function multiSelectionForBrands() {
	$('.brands').multiselect({
		includeSelectAllOption : true,
		numberDisplayed : 2,
		buttonWidth: '100%',
		nonSelectedText : 'ALL BRANDS',
		enableFiltering: true,
 		enableCaseInsensitiveFiltering: true,
		onChange : function(option, checked, select) {
			var selVals = [];
			var selectedOptions = $('.brands option:selected');
			if (selectedOptions.length > 0) {
				for (var i = 0; i < selectedOptions.length; i++) {
					selVals.push(selectedOptions[i].value);
				}

				var strData = selVals.toString();

			}

		},
		onDropdownShow: function(event) {
			var menu = $(event.currentTarget).find(".dropdown-menu");		                       
            menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"});  
            		                        
       },
		
		onSelectAll : function() {

		},
		onDeselectAll : function() {

		}

	});
}

function muliSelectionForFormats(){
	$('#format_modal').multiselect({
 		includeSelectAllOption: true,
 		buttonWidth: '100%',
 		nonSelectedText: 'ALL FORMATS',
 		enableFiltering: true,
 		enableCaseInsensitiveFiltering: true,
 		onDropdownHide: function(event) {
 			getcount();
        },
        onDropdownShow: function(event) {
            var menu = $(event.currentTarget).find(".dropdown-menu");		                       
            menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"});
            		                        
       }
 });
}

function muliSelectionForClusters(){
	$('#cluster_modal').multiselect({
 		includeSelectAllOption: true,
 		buttonWidth: '100%',
 		//dropRight: true,
 		enableFiltering: true,
 		enableCaseInsensitiveFiltering: true,
 		nonSelectedText: 'ALL CLUSTERS',
 		onDropdownHide: function(event) {
 			getcount();
        },
        onDropdownShow: function(event) {
            var menu = $(event.currentTarget).find(".dropdown-menu");		                       
            menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"});
       },
       
	});
}

function muliSelectionForGodowns(){
	$('#godown_modal').multiselect({
 		includeSelectAllOption: true,
 		buttonWidth: '100%',
 		nonSelectedText: 'ALL GODOWNS',
 		enableFiltering: true,
 		enableCaseInsensitiveFiltering: true,
 		onDropdownHide: function(event) {
 			getcount();
        },
        onDropdownShow: function(event) {
            var menu = $(event.currentTarget).find(".dropdown-menu");		                       
            menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"});
            		                        
       }
 });
}

function muliSelectionForAccounts(){
	$('#account_modal').multiselect({
 		includeSelectAllOption: true,
 		buttonWidth: '100%',
 		nonSelectedText: 'ALL ACCOUNTS',
 		enableFiltering: true,
 		enableCaseInsensitiveFiltering: true,
 		onDropdownHide: function(event) {
 			getcount();
 			var selVals = [];
			var selectedOptions = $('#account_modal option:selected');
			if (selectedOptions.length > 0) {
				for (var i = 0; i < selectedOptions.length; i++) {
					selVals.push(selectedOptions[i].value);
				}
				var startDate = getStartDate(selVals);
				var endDate = getEndDate(selVals);
				$('#startdate').val(startDate);
				$('#enddate').val(endDate);
			}else{
				 $('#startdate').val("");
					$('#enddate').val("");
			}
        },
        onDropdownShow: function(event) {
            var menu = $(event.currentTarget).find(".dropdown-menu");		                       
            menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"});
            		                        
       },
       onDeselectAll : function() {
    	   $('#startdate').val("");
			$('#enddate').val("");
		}
 });
}

function multiSelectionForOutlets() {
	$('#outlet_modal').multiselect({
		includeSelectAllOption : true,
		numberDisplayed : 2,
		buttonWidth: '100%',
		nonSelectedText : 'ALL OUTLETS',
		enableFiltering: true,
 		enableCaseInsensitiveFiltering: true,
		onChange : function(option, checked, select) {
			var selVals = [];
			var selectedOptions = $('#outlet_modal option:selected');
			if (selectedOptions.length > 0) {
				for (var i = 0; i < selectedOptions.length; i++) {
					selVals.push(selectedOptions[i].value);
				}
				var strData = selVals.toString();
			}
			$('#selstoreCount').val(selVals.length);
		},
		onDropdownShow: function(event) {
			var menu = $(event.currentTarget).find(".dropdown-menu");		                       
            menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"});  
       },
		
		onSelectAll : function() {
			$('#selstoreCount').val($('#outlet_modal option:selected').length);
		},
		onDeselectAll : function() {
			$('#selstoreCount').val(0);
		}

	});
}

function multiSelectionForBrandsRep() {
	$('.brandsValidate').multiselect({
		includeSelectAllOption : true,
		numberDisplayed : 2,
		buttonWidth: '100%',
		nonSelectedText : 'ALL BRANDS',
		enableFiltering: true,
 		enableCaseInsensitiveFiltering: true,
		onChange : function(option, checked, select) {
			var selVals = [];
			var selectedOptions = $('.brandsValidate option:selected');
			if (selectedOptions.length > 0) {
				for (var i = 0; i < selectedOptions.length; i++) {
					selVals.push(selectedOptions[i].value);
				}

				var strData = selVals.toString();

			}

		},
		onDropdownShow: function(event) {
			var menu = $(event.currentTarget).find(".dropdown-menu");		                       
            menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"});                      
       },
		onSelectAll : function() {

		},
		onDeselectAll : function() {

		}

	});
}


function downloadAssetPlanningDetails(){
	document.getElementById("getAssetPlanningDetails").action = "downloadAssetPlanningDetails.htm";
	document.getElementById("getAssetPlanningDetails").submit(); 
}

function downloadUpdatedBaseFile(){
	var connectMoc = $('#connectMocList').val();
	var trackMoc = $('#trackMocList').val();
	document.getElementById("updatedBaseDownloadForm").action = "downloadUpdatedBaseFile.htm";
	document.getElementById("updatedBaseDownloadForm").submit(); 
}

function downloadReplaceAssetTemplate(){
	document.getElementById("getAssetPlanningDetails").action = "downloadReplaceAssetFile.htm";
	document.getElementById("getAssetPlanningDetails").submit(); 
}

function downloadClosedStoreList(){
	document.getElementById("getAssetPlanningDetails").action = "downloadCloseStoreList.htm";
	document.getElementById("getAssetPlanningDetails").submit(); 
}

function downloadNewStoreList(){
	document.getElementById("getAssetPlanningDetails").action = "downloadNewStoreList.htm";
	document.getElementById("getAssetPlanningDetails").submit(); 
}

function publishToContinue(){
	document.getElementById("addAsset").action = "publishAssetsToContinue.htm";
	document.getElementById("addAsset").submit(); 
}

function discardAssets(){
	document.getElementById("addAsset").action = "discardAssets.htm";
	document.getElementById("addAsset").submit(); 
}


$(document).ready(function(){
	 $("#addOtherCheckbox").click(function () {
	     if ($(this).is(":checked")) {
	         $("#add-optional_asset").show();
	         $("#add-main_asset").hide();
	     } 
	     else if ($(this).not(":checked")){
	         $("#add-optional_asset").hide();
	         $("#add-main_asset").show();
	     }
	     else{
		$("#optional_asset").hide();
	    $("#main_asset").show();
	     }
	});
	 
	$('.tme-datepicker').datepicker(
            	{
                   showOn : "both",
                   dateFormat : "dd/mm/yy",
                   minDate : "+40d",
                   //maxDate: "+3m",
                   buttonImage : "assets/images/calender.png",
                   buttonImageOnly : true,
                   buttonText : "Select date",
                   changeMonth : false,
                   numberOfMonths : 1,
                   onSelect : function(selectedDate) {
                          var date=$(this).datepicker("getDate");
                  		  var d = date.getDate()+1;
                          date.setDate(d); 
                    }

            });

	 $('.tme-datepickerend').datepicker({
                    defaultDate : "+1M",
                    showOn : "both",
                    dateFormat : "dd/mm/yy",
                    buttonImage : "assets/images/calender.png",
                    buttonImageOnly : true,
                    buttonText : "Select date",
                    changeMonth : false,
                    numberOfMonths : 1,
                    beforeShow : function() {
                           if ($('.selectedStartDate:visible').val() !== ''){
                        	   var currentTime = $('.selectedStartDate:visible').datepicker( "getDate" );
                        	   var minDate = new Date(currentTime.getFullYear(), currentTime.getMonth() + 1, currentTime.getDate() -1);
                                  $(this).datepicker('option', 'minDate', minDate);
                           }
                    }, 
                    onSelect : function(selectedDate) {
                           if($('.startdate').val() == ""){
                         	  $('.enddate').val('');
         					  return false;
         					}
                           
                    }
             });
	 
	
	 $('#add-asset-modal').on('shown.bs.modal', function() {
		 getNewStoresData();
	    });
	 
	 $('#myModal').on('shown.bs.modal', function() {
		 $('#replace-asset').attr("disabled", "disabled");
	    });
	 
	 $('#reset').on('hidden.bs.modal', function() {
	    	$("#addAsset")[0].selectedIndex = 0;
	  });
		$('.subcat').multiselect({
      		includeSelectAllOption: true,
      		buttonWidth: '100%',
      		nonSelectedText: 'ALL CATEGORIES',
      		enableFiltering: true,
     		enableCaseInsensitiveFiltering: true,
      		onChange: function(e) {
      			var val = e[0].value;
      		},
      		onDropdownHide: function(event) {
					var selVals = [];
					var selectedOptions = $('.subcat option:selected');
					if (selectedOptions.length > 0) {
						for (var i = 0; i < selectedOptions.length; i++) {
							selVals.push(selectedOptions[i].value);
						}

						var strData = selVals.toString();
						$('.switch-dynamic').html('<select class="form-control brands" name="brands" id="brands" multiple="multiple"><option values="ALL BRANDS">ALL BRANDS</option></select>');
						getBrands(strData);

					} else {
						$('.switch-dynamic').html('<input type="text" name="brands" class="form-control brands" id="brands" value="ALL BRANDS" readonly="true">');
					}

				
             },
             onDropdownShow: function(event) {
            	 var menu = $(event.currentTarget).find(".dropdown-menu");		                       
                 menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"});                        
            }
		});
	 
	 $("#Reset-Replace,#crossBtnRep").on('click', function() {
		 $('.replaceSubcat,.brands').multiselect("clearSelection");
		 $('#repOtherCheckbox').prop("checked", false);
		 $("#optional_asset_rep").hide();
         $("#main_asset_rep").show();
         $("#assetsRep")[0].selectedIndex = 0;
         $("#assetsRepdesc")[0].selectedIndex = 0;
		
	  });
	 
	 
	 $("#input-1").change(function() {
			$('#successblock').hide();
			$('#errorblock').hide();
			$('.validate_upload').prop('disabled', false);
		});
	 		 
});


/*$("#replace-asset").on("click", function() {*/
function replacepostext(){
	var chkedVal = $('input[name="file-name"]:checked').val();
	
	 var params = {
			 		"tid": chkedVal,
			 		"account": account, 
			     "format": format,
			     "cluster": cluster, 
			     "hfsGodownLocation": hfsGodownLocation,
			     "storeType":storeType };
	
	$.ajax({
			type: "GET",
			url: "replaceTotAssetRequest.htm?" + $.param(params),
			async: false,
			success:function(data) {
				var replaceData = JSON.parse(data);
				var replaceSubCat  =  replaceData.subCatList;
				var selectedSubCatList  =  replaceData.selectedSubCatList;
				selectedBrandList = replaceData.selectedBrandList;
				var allOutlets = replaceData.allOutlets;
				var repOutlets = replaceData.repOutlets;
				var selectedAssetType=replaceData.selectedAssetType;
				var selectedAssetDesc=replaceData.selectedAssetDesc;
				
				$('#assetsRep').val(selectedAssetType);
				$('#assetsRepdesc').val(selectedAssetDesc);
				
				$("#tid").val(replaceData.tid);
				$(".selectedAccount").val(replaceData.account);
				$(".selectedFormat").val(replaceData.format);
				$(".selectedCluster").val(replaceData.cluster);
				$(".selectedGodown").val(replaceData.godownLocation);
				$("#startDateRep").val(replaceData.startDate);
				$("#connectMoc").val(replaceData.connectivityMoc);
				$(".selectedtrack").val(replaceData.trackingMoc);
				$(".endDateValidate").val(replaceData.endDate);
				$("#replace-modal").modal('show');
				
				$('#rep_storeCount').val(allOutlets.length);
				
				$('#repOutlets').empty();
				$('#repOutlets').multiselect("destroy");
				
				 $.each(allOutlets, function(k,v) {
					 if(repOutlets.includes(v)){
						 $('#repOutlets').append('<option value="' + v + '" selected>' + v + '</option>');
					 }else{
						 $('#repOutlets').append('<option value="' + v + '">' + v + '</option>');
					 }
				});	
				
				$('#repOutlets').multiselect({
			 		includeSelectAllOption: true,
			 		buttonWidth: '100%',
			 		nonSelectedText: 'ALL OUTLETS',
			 		enableFiltering: true,
			 		enableCaseInsensitiveFiltering: true,
			 		onChange : function(option, checked, select) {
			 			var selVals = [];
    					var selectedOptions = $('#repOutlets option:selected');
    					if (selectedOptions.length > 0 ) {
    						for (var i = 0; i < selectedOptions.length; i++) {
    							selVals.push(selectedOptions[i].value);
    						}
    						var strData = selVals.toString();
    					}
    					$('#rep_selstoreCount').val(selVals.length);
					},
			 		onDropdownHide: function(event) {
			 			
			        },
			        onDropdownShow: function(event) {
			            var menu = $(event.currentTarget).find(".dropdown-menu");		                       
			            menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"});
			            		                        
			       },
			       onSelectAll : function() {
			    	   $('#rep_selstoreCount').val($('#repOutlets option:selected').length);
					},
					onDeselectAll : function() {
						  $('#rep_selstoreCount').val(0);
					}
			 });
				
				var selVals = [];
				var selectedOptions = $('#repOutlets option:selected');
				if (selectedOptions.length > 0 ) {
					for (var i = 0; i < selectedOptions.length; i++) {
						selVals.push(selectedOptions[i].value);
					}
					var strData = selVals.toString();
				}
				$('#rep_selstoreCount').val(selVals.length);
				
				
				$('.replaceSubcat').empty();
				$('.replaceSubcat').multiselect("destroy");
				 $.each(replaceSubCat, function(k,v) {
					 if(selectedSubCatList.includes(v)){
						 $('.replaceSubcat').append('<option value="' + v + '" selected>' + v + '</option>');
					 }else{
						 $('.replaceSubcat').append('<option value="' + v + '">' + v + '</option>');
					 }
				});	
				 
	        	 $('.replaceSubcat').multiselect({
	             		includeSelectAllOption: true,
	             		buttonWidth: '100%',
	             		nonSelectedText: 'ALL CATEGORIES',
	             		enableFiltering: true,
	             		enableCaseInsensitiveFiltering: true,
	             		onDropdownHide: function(event) {
	             		var selVals = [];
	    					var selectedOptions = $('.replaceSubcat option:selected');
	    					if (selectedOptions.length > 0 ) {
	    						for (var i = 0; i < selectedOptions.length; i++) {
	    							selVals.push(selectedOptions[i].value);
	    						}
	    						var strData = selVals.toString();
	    						$('.switch-dynamic-rep').html('<select class="form-control brandsValidate" name="brandsRep" id="brandsRep" multiple="multiple"><option values="ALL BRANDS">ALL BRANDS</option>');
	    						getBrandsRep(strData);

	    					}
	    					else {
	    						$('.switch-dynamic-rep').html('<input type="text" name="brandsRep" class="form-control brandsValidate" id="brandsRep" value="ALL BRANDS" readonly="true">');
	    					}
	                    },
	                    onDropdownShow: function(event) {
	                    	 var menu = $(event.currentTarget).find(".dropdown-menu");		                       
	                         menu.css({"width": "100%", "overflow-y": "scroll", "height": "250px"}); 
	                        		                        
	                   }
	             });	
	        	 
	        	 	var selVals = [];
					var selectedOptions = $('.replaceSubcat option:selected');
					if (selectedOptions.length > 0 ) {
						for (var i = 0; i < selectedOptions.length; i++) {
							selVals.push(selectedOptions[i].value);
						}
						var strData = selVals.toString();
						$('.switch-dynamic-rep').html('<select class="form-control brandsValidate" name="brandsRep" id="brandsRep" multiple="multiple"><option values="ALL BRANDS">ALL BRANDS</option>');
						getBrandsRep(strData);

					}
					else {
						$('.switch-dynamic-rep').html('<input type="text" name="brandsRep" class="form-control brandsValidate" id="brandsRep" value="ALL BRANDS">');
					}
	        	 
	        	 
			},
			error:function(e) {
				
			}
	});
}



/*replace asset modal*/

function validate(){
	
	var startDate=$('#startdate').val();
	var endDate=$('#enddate').val();
	var subCatLen=$('#subcat option:selected').length;
	var brandLen=$('#brands option:selected').length;
	var otherSubCat=$("#other_subcat").val();
	var otherBrand=$("#other_brands").val();
	
	var assetdesc=$("#assetdesc").val();
	var asset=$('#assets').val();
	var storeCount=$('#storeCount').val();
	var selstoreCount=$('#selstoreCount').val();
	var isOther=$('#addOtherCheckbox').is(":checked");
	
	$('#storeTypeForm').val(storeType);
	
	 if(isOther){
			 if(selstoreCount>0 && asset!="" && asset!="SELECT"  && startDate !="" && endDate !="" && otherSubCat !="" && otherBrand!="" && assetdesc != "SELECT"){
				 $('#addAsset').submit();
			 }
			 else{
					$('#showValidation').show();
					$('.modal-body').click(function(){
						$('#showValidation').hide();
					});
					return false;
					
				}
		 }else{
			 if(selstoreCount>0 && asset!="" && asset!="SELECT"  && startDate !="" && endDate !="" && subCatLen>0 && brandLen>0  && assetdesc != "SELECT"){
				 $('#addAsset').submit();
			 }
			 else{
				 	$('#showValidation').show();
				 		$('.modal-body').click(function(){
				 			$('#showValidation').hide();
				 		});
		return false;
		
	}}
 };
 

 function triggerAjax(url, params, method){
	 method = typeof method == 'undefined' ? "POST" : method;
	 $.ajax({
		  type: method,
		  url: url,
		  data: params,
		  success: function(data){
			  data = JSON.parse( data )
			  $('#crossBtnRep').click();
			  var msg = "";
			  $( '.user-notify-on-screen' ).remove();
			  
			  if( data.status == "success" ){
				  msg = '<div class="alert alert-success sucess-msg user-notify-on-screen" id="successblock" style="display:block"> <button type="button" class="close" data-hide="alert">&times;</button>'+ data.msg +'</div>';
			  } else {
				  msg = '<div class="alert alert-danger sucess-msg user-notify-on-screen" id="errorblock"> <button type="button" class="close" data-hide="alert">&times;</button>'+ data.msg +'</div>';
			  }
			  
			  $( $( ".tab-pane.active" )[0] ).prepend( msg );
		  }
	});
 }
 
 
 function getFormData(){
	 var fields = $( "#repAsset input, #repAsset select" ),
	 	 field = $(),
	 	 params = {};
	 for( var i = 0; i < fields.length; i++ ){
		 field = $( fields[i] );
		 if( field.is( "select" ) ){
			 if( field.attr( "multiple" ) == "multiple" ){
				 params[field.attr( "name" )] = field.val() != null ? field.val().join(',') : [];
			 } else {
				 params[field.attr( "name" )] = field.val();
			 }
		 } else if( field.is( '[type="checkbox"]' ) ) {
			 params[field.attr( "name" )] = field.is(":checked") ? "yes" : "no";
		 } else  {
			 params[field.attr( "name" )] = field.val();
		 }
		 
		 if( params["repOtherCheckbox"] == "yes" ){
			 params['subCatRep'] = params['other_subCat_rep'];
			 params['brandsRep'] = params['other_brands_rep'];
		 }
	 }
	 return params;
 }
 
 function validateReplace() {
	 var selectedAccount=$(".selectedAccount").val();
	var asset = $('#assetsRep').val();
	var startDate=$('.startDateValidate').val();
	var endDate=$('.endDateValidate').val();
	var subCatLen=$('#subCatRep option:selected').length;
	var brandLen=$('#brandsRep option:selected').length;
	var otherSubCat=$("#other_subCat_rep").val();
	var otherBrand=$("#other_brands_rep").val();
	var selectedOutlets=$('#rep_selstoreCount').val();
	var replacebtn = $('#repAsset_submit').text();
	var assetsRepdesc=$("#assetsRepdesc").val(); 
	var assetsRep =$('#assetsRep').val();
	var isOther=$('#repOtherCheckbox').is(":checked");
	
	if (isOther) {
		if (selectedAccount !="" && otherSubCat!="" && otherBrand !="" && assetsRepdesc !="SELECT" && startDate !="" && endDate != "" && selectedOutlets>0) {
			//$('#repAsset').submit();
			
			var param = getFormData();
				param["btnName"] = "replacebtn";
			triggerAjax("replaceTmeTotVisibilityAsset.htm", param );
			$('#replace-modal').modal('hide');
		} else {
			$('#showValidationRep').show();
			$('.modal-body').click(function() {
				$('#showValidationRep').hide();
			});
			return false;

		}
	} else {
		if (selectedAccount !="" && assetsRepdesc != "SELECT" && subCatLen > 0 && brandLen > 0 && startDate !="" && endDate != "" && selectedOutlets>0) {
			//$('#repAsset').submit();
			
			

			var param = getFormData();
				param["btnName"] = "replacebtn";
			
			triggerAjax("replaceTmeTotVisibilityAsset.htm", param );
			$('#replace-modal').modal('hide');
		} else {
			$('#showValidationRep').show();
			$('.modal-body').click(function() {
				$('#showValidationRep').hide();
			});
			return false;

		}
	}
}
 //validation for postpone asset
 function validatePostpone() {
		var asset = $('#assetsRep').val();
		var startDate=$('.startDateValidate').val();
		var endDate=$('.endDateValidate').val();
		var subCatLen=$('#subCatRep option:selected').length;
		var brandLen=$('#brandsRep option:selected').length;
		var otherSubCat=$("#other_subCat_rep").val();
		var otherBrand=$("#other_brands_rep").val();
		var postAsset = $('#postAsset_submit').text();
		var selectedOutlets=$('#rep_selstoreCount').val();
		var assetsRepdesc=$("#assetsRepdesc").val(); 
		var isOther=$('#repOtherCheckbox').is(":checked");
		
	
			if (startDate !="" && endDate != "" && selectedOutlets>0 ) {//&& selectedOutlets>0  
				//$('#repAsset').submit();
				var param = getFormData();
					param["btnName"] = "postAsset";
				
				triggerAjax("replaceTmeTotVisibilityAsset.htm", param );
				$('#replace-modal').modal('hide');
			} else {
				$('#showValidationRep').show();
				$('.modal-body').click(function() {
					$('#showValidationRep').hide();
				});
				return false;

			}
		
	}
 
//validation for Extended asset
 function validateExt() {
		var asset = $('#assetsRep').val();
		var startDate=$('.startDateValidate').val();
		var endDate=$('.endDateValidate').val();
		var subCatLen=$('#subCatRep option:selected').length;
		var brandLen=$('#brandsRep option:selected').length;
		var otherSubCat=$("#other_subCat_rep").val();
		var otherBrand=$("#other_brands_rep").val();
		var extendasset = $('#extend-asset').text();
		repOutlets
		var selectedOutlets=$('#rep_selstoreCount').val();
		
		 var assetsRepdesc=$("#assetsRepdesc").val(); 
		 var isOther=$('#repOtherCheckbox').is(":checked");
		
		
			if (endDate != "" && selectedOutlets>0 ) {//&& selectedOutlets>0  
				//$('#repAsset').submit();
				var param = getFormData();
				param["btnName"] = "extendasset";
				
				
				triggerAjax("replaceTmeTotVisibilityAsset.htm", param );
				$('#replace-modal').modal('hide');
			} else {
				$('#showValidationRep').show();
				$('.modal-body').click(function() {
					$('#showValidationRep').hide();
				});
				return false;

			}
		
	}
 
 function uploadNewTotStoreList() {
		$('#errorblock').hide();
		var fileName = $("#input-1").val();
		if (fileName == "") {
			ezBSAlert({
				messageText : "Please choose a file",
				alertType : "info"
			}).done(function(e) {
				//console.log(e);
			});
			return false;
		} else if (fileName != "") {
			var FileExt = fileName.substring(fileName.lastIndexOf('.') + 1);
			if (FileExt != "xlsx") {
				if (FileExt != "xls") {
					ezBSAlert({
						messageText : "Please upload .xls or .xlsx file",
						alertType : "info"
					}).done(function(e) {
					});
					return false;
				}
			}
		}
		document.forms['coefileupload'].method = "POST";
		document.forms['coefileupload'].action = "uploadNewTotStoreList.htm";
		document.forms['coefileupload'].submit();
} 
 
 function downloadSkuTemplate(){
	 	$('#showValidation').hide();
		$('#successblock').hide();
		$('#errorblock').hide();
		document.forms[0].method = "GET";
		document.forms[0].action = "downloadSkuTemplateFile.htm";
		document.forms[0].submit();
		return true;
	}
 function downloadTotTemplate(){
	 	$('#showValidation').hide();
		$('#successblock').hide();
		$('#errorblock').hide();
		document.forms[0].method = "GET";
		document.forms[0].action = "downloadTotTemplateFile.htm";
		document.forms[0].submit();
		return true;
	}

 function catmanUploadTOTSkuDetails() {
		var fileName = $("#input-2").val();
		if (fileName == '') {
			$('#uploadErrorMsg1').show().html("Please select a file to upload");
			return false;
		} else {
			var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
			if (FileExt != "xlsx") {
				if (FileExt != "xls") {

					$('#uploadErrorMsg1').show();
					$("#catmanFileUpload").submit(function(e) {
						e.preventDefault();
					});
					return;
				}

			}
			document.catmanFileUpload.method = "POST";
			document.catmanFileUpload.action = "uploadTOTSkuDetails.htm";
			document.catmanFileUpload.submit();
		}
}
 
 function catmanUploadReplaceAsset() {
		var fileName = $("#inputReplaceAsset").val();
		if (fileName == '') {
			$('#uploadRepErrorMsg1').show().html("Please select a file to upload");
			return false;
		} else {
			var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
			if (FileExt != "xlsx") {
				if (FileExt != "xls") {

					$('#uploadRepErrorMsg1').show();
					$("#catmanFileUploadReplace").submit(function(e) {
						e.preventDefault();
					});
					return;
				}

			}
			document.catmanFileUploadReplace.method = "POST";
			document.catmanFileUploadReplace.action = "UploadReplaceAssetFile.htm";
			document.catmanFileUploadReplace.submit();
		}
}
 
 function catmanUploadReplaceAsset() {
		var fileName = $("#inputReplaceAsset").val();
		if (fileName == '') {
			$('#uploadRepErrorMsg1').show().html("Please select a file to upload");
			return false;
		} else {
			var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
			if (FileExt != "xlsx") {
				if (FileExt != "xls") {

					$('#uploadRepErrorMsg1').show();
					$("#catmanFileUploadReplace").submit(function(e) {
						e.preventDefault();
					});
					return;
				}

			}
			document.catmanFileUploadReplace.method = "POST";
			document.catmanFileUploadReplace.action = "uploadTOTSkuDetails.htm";
			document.catmanFileUploadReplace.submit();
		}
}
 
 function downloadTOTSkuErrorDetails() {
	 $('#showValidation').hide();
		$('#successblock').hide();
		$('#errorblock').hide();
		document.forms[0].method = "GET";
		document.forms[0].action = "downloadTOTSkuDetailsErrorFile.htm";
		document.forms[0].submit();
		return true;
	}
 
 
 function downloadTOTErrorDetails() {
	 $('#showValidation').hide();
		$('#successblock').hide();
		$('#errorblock').hide();
		document.forms[0].method = "GET";
		document.forms[0].action = "downloadTOTErrorDetailsFile.htm";
		document.forms[0].submit();
		return true;
	}
	function getStartDate(accountType){
	 var flag;

	/* for(var a = 0; a < accountType.length; a++) {
		  if(accountType[a] == 'NMT'){
			  flag = 1;
			  break; 
		  }
		  else if(accountType[a] == 'ADIT' || accountType[a] == 'ADIH' || accountType[a] == 'RELI' || accountType[a] == 'SPEN'){
			  flag = 2;
			  break; 
		  }
		}*/
	 
	 if(accountType.includes("NMT")){
		 flag = 1;
	 }else if(accountType.includes("ADIH") || accountType.includes("ADIT") || accountType.includes("RELI") || accountType.includes("SPEN")){
		 flag = 2;
	 }
	 	var today = new Date();
	 	var dateStr;
		if(flag == 1){
			var date = today.getDate();
			if(date >= 21){
				today.setMonth( today.getMonth() + 1);
			}
			var month = today.getMonth()+1;
			var year = today.getFullYear();
			dateStr = 21;
			
			if(month <= 9){
				dateStr += "/0" + month;
			}else{
				dateStr += "/" + month;
			}
			dateStr += "/" + year;
		}else if(flag == 2){
			var date = today.getDate();
			if(date >= 26){
				today.setMonth( today.getMonth() + 1);
			}
			var month = today.getMonth()+1;
			var year = today.getFullYear();
			dateStr = 26;
			
			if(month <= 9){
				dateStr += "/0" + month;
			}else{
				dateStr += "/" + month;
			}
			dateStr += "/" + year;
		}else{
			var date = today.getDate();
			if(date>=1){
				today.setMonth( today.getMonth() + 1);
			}
			var month = today.getMonth()+1;
			var year = today.getFullYear();
			dateStr = '01';
			
			if(month <= 9){
				dateStr += "/0" + month;
			}else{
				dateStr += "/" + month;
			}
			dateStr += "/" + year;
		}
		
		return dateStr;	
 }
 
 
 function getEndDate(accountType){
	 var flag;
	 /*for(var a = 0; a < accountType.length; a++) {
		  if(accountType[a] == 'NMT'){
			  flag = 1;
			  break; 
		  }
		  else if(accountType[a] == 'ADIT' || accountType[a] == 'ADIH' || accountType[a] == 'RELI' || accountType[a] == 'SPEN'){
			  flag = 2;
			  break; 
		  }
		}*/
	 
	 if(accountType.includes("NMT")){
		 flag = 1;
	 }else if(accountType.includes("ADIH") || accountType.includes("ADIT") || accountType.includes("RELI") || accountType.includes("SPEN")){
		 flag = 2;
	 }
	 	var today = new Date();
		today.setMonth( today.getMonth() + 1);
	 	var dateStr;
	 	if(flag == 1){
			var date = today.getDate();
			if(date > 20){
				today.setMonth( today.getMonth() + 1);
			}
			var month = today.getMonth()+1;
			var year = today.getFullYear();
			dateStr = 20;
			
			if(month <= 9){
				dateStr += "/0" + month;
			}else{
				dateStr += "/" + month;
			}
			dateStr += "/" + year;
		}else if(flag == 2){
			var date = today.getDate();
			if(date > 25){
				today.setMonth( today.getMonth() + 1);
			}
			var month = today.getMonth()+1;
			var year = today.getFullYear();
			dateStr = 25;
			
			if(month <= 9){
				dateStr += "/0" + month;
			}else{
				dateStr += "/" + month;
			}
			dateStr += "/" + year;
		}else{
			month=today.getMonth()+1;
			var year = today.getFullYear();
			var lastDay = new Date(year, month, 0).getDate();
			
			//var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
			dateStr = lastDay;
			
			if(month <= 9){
				dateStr += "/0" + month;
			}else{
				dateStr += "/" + month;
			}
			dateStr += "/" + year;
		}
		
		return dateStr;	
}
//account Change get MOC
	var num;
	function getNoOfStore() {
		$('#num_of_stores-msg').hide();
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "findStoreNo.htm?no=" + encodeURIComponent($('#account_name').val()),
			async: false,
			success : function(data) {
				num = parseInt(data);
			}
		});
	}
 