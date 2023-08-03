 
var spinnerWidth = "100";
 var spinnerHeight = "100";
var tirggerAccountCall = true;
var skuCountSellInPack = 1,
	isFileOneUploaded = false,
	isFileTwoUploaded = false,
	isFileThreeUploaded = false;
// var strOldCount = $('#storecount').val();
var totalStoreCount = 0;
var optionJsonLaunch = "";
var optionJsonclassfi = "";
var isManualClick = false;
var fnloTable,
	selloTable,
	visioTable;
$(document).ready(function() {
	$.ajaxSetup({ cache: false });
	$('#errorblockUpload').hide();
	$('#sellinerrorblockUpload').hide();
	$('#annexerrorblockUpload').hide();
	//next-2 lines added for sprint-7 US-7
	$('#launchStoreerrorblockUpload').hide();
	$('#launchStoreErrorFileForStoreFormat').hide();
	
	 $("[data-hide]").on("click", function() {
         $(this).closest("." + $(this).attr("data-hide")).hide();
     });
	 
	if( window.location.hash != "#step-1" && window.location.hash != '' ){
		window.location = window.location.href.split('#')[0];
	}

	$( document ).on( 'mousedown', '#smartwizard ul.step-anchor a, #smartwizard ul.step-anchor li', function(e){
		isManualClick = true;
	} );
	
	$('#noVissiPlan').click(function(){
		var launchId = $("#dynamicLaunchId").val();
		$.ajax({
		    url: launchId+'/saveLaunchNoVisiPlan.htm',
		    dataType: 'json',
		    type: 'get',
		    contentType: 'application/json',
		    processData: false,
		    beforeSend: function() {
	            ajaxLoader(spinnerWidth, spinnerHeight);
	        },
		    success: function( data, textStatus, jQxhr ){
		    	 $("#launchFinBuiUpTab").click();
				 getfinalPlan();
			 },
		    error: function( jqXhr, textStatus, errorThrown ){
		        console.log( errorThrown );
		    }
		 
		});
		
	});
	
	$('#prevbspack').click(function(){
		 $("#launchDetailsTab").click();
	});
	
	$('#prevClust').click(function(){
		 $("#launchBasepackTab").click();
		 
		 
	});
	$('#prevsellIn').click(function(){
		 $("#launchStoresTab").click();
	});
	$('#prevvissi').click(function(){
		 $("#launchSellInTab").click();
		// $("#visiplan").dataTable().fnDestroy();
		// $("#sellinTable").dataTable().fnDestroy();
		 
		 setTimeout(function(){
			 edit_sellinTable.draw();
			 visioTable.draw();
			 
		 }, 300);
	});
	$('#prevfinal').click(function(){
		 $("#launchVisiTab").click();
		 setTimeout(function(){
			 edit_visiTable.draw();
			 selloTable.draw();
		 }, 300);
	});
	$('#prevdoc').click(function(){
		 $("#launchFinBuiUpTab").click();
		 setTimeout(function(){
		  edit_finaltable.draw();
		  fnloTable.draw();
		 }, 300);
	});
	
	
	// $('#custstrfrmt').multiselect();
	 $("#file").change(function() {
         $('#successblockUpload').hide();
     });
	 $('.switch-dynamic-first-custstrfrmt').find('button').prop("disabled", true);
	 $('.switch-dynamic-first-radio-cststr').find('button').prop("disabled", true);
	// screen 3 checkbox condition
	
	$('#custmsel').click(function () {
		customSelectionStore();
	}); 
	
	$( document ).on("change", '#radio1', function () {
		if( $( this ).is( ":checked" ) ){
			$( '#cust-chain-store' ).next().find( 'button.multiselect ' ).removeAttr( "disabled" );
			$( '#cust-chain-cst-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
		} else {
			$( '#cust-chain-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
		}
	}); 
	
	$( document ).on("change", '#radio2', function () {
		if( $( this ).is( ":checked" ) ){
			$( '#cust-chain-cst-store' ).next().find( 'button.multiselect ' ).removeAttr( "disabled" );
			$( '#cust-chain-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
		} else {
			$( '#cust-chain-cst-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
		}
	}); 

	 $("[data-hide]").on("click", function() {
	        $( '#cust-chain-cst-store' ).next().find( 'button.multiselect ' ).removeAttr( "disabled", true );
     });

	// hiddden input screen 1
	$('.geodiv').css('display', 'none');
	$('.custchain').css('display', 'none');
	$('.inputdiv').css('display', 'none');
	$('.formatdiv').css('display', 'none');
	$('.towndiv').css('display', 'none');
	
	
	$('#editGeography').comboTree({
		source : JSON.parse(data),
		isMultiple : true
	});
	var instance = $('#cluster').comboTree({
		source : JSON.parse(data),
		isMultiple : true,
		cascadeSelect:false
	});
	$('#customerChainL1Thrscn').comboTree({
		source : JSON.parse(custmr),
		isMultiple : true,
		
	});
	
		
	// check all function for geographyData
	$( document ).on( "change", "#comboTreeeditGeographyDropDownContainer input[type=checkbox]", function(e){
		if( tirggerAccountCall ){
			e.preventDefault();e.stopPropagation();
			
		     var target = $( this ),fuLen=$('.ComboTreeItemParent').length-1,selen=$( "#comboTreeeditGeographyDropDownContainer input[type=checkbox]:checked" ).length;
			if( target.parent().text() == "ALL INDIA" ){
				// debugger;
				
					if( target.is( ":checked" ) ){
						$( "#comboTreeeditGeographyDropDownContainer input[type=checkbox]" ).prop( "checked", true );
						$('#editGeography').val('');
						$('#editGeography').val('ALL INDIA');return false;
					} else {
						$( "#comboTreeeditGeographyDropDownContainer input[type=checkbox]" ).prop( "checked", false );
						$('#editGeography').val('');return false;
					}
			} else {
					if( target.is( ":checked" ) ){
						target.closest( "li" ).find( "input" ).prop( "checked", true );
						let sublenf=target.parents('ul:eq(0)').find( "input" ).length, sublenu=target.parents('ul:eq(0)').find( "input:checked" ).length;
						if( sublenf== sublenu){target.parents('ul').parent( "li" ).find( "input:eq(0)" ).prop( "checked", true );}
						var selen=$( "#comboTreeeditGeographyDropDownContainer input[type=checkbox]:checked" ).length;
						var searchIDs = $("#comboTreeeditGeographyDropDownContainer input:checked").parent().map(function(){
						      return $(this).text();
						    }).get();
						$('#editGeography').val('');
						$('#editGeography').val(searchIDs.toString());
						if(fuLen==selen){$('#comboTreeeditGeographyDropDownContainer ul li:eq(0)').find( "input:eq(0)" ).prop( "checked", true );
						$('#editGeography').val('');
						$('#editGeography').val('ALL INDIA');
						}
						return false;
						
					} else {
						target.closest( "li" ).find( "input" ).prop( "checked", false );
						target.parents('ul').parent( "li" ).find( "input:eq(0)" ).prop( "checked", false );
						$('#comboTreeeditGeographyDropDownContainer ul li:eq(0)').find( "input:eq(0)" ).prop( "checked", false );
						var searchIDs = $("#comboTreeeditGeographyDropDownContainer input:checked").parent().map(function(){
						      return $(this).text();
						    }).get();
						$('#editGeography').val('');
						$('#editGeography').val(searchIDs.toString());
					}
				
			}
		}
	});
	
	// check all function for cluster screen 3
	$( document ).on( "change", "#comboTreeclusterDropDownContainer input[type=checkbox]", function(e){
		
			e.preventDefault();e.stopPropagation();
			// debugger;
		     var target = $( this ),fuLen=$('.ComboTreeItemParent').length-1,selen=$( "#comboTreeclusterDropDownContainer input[type=checkbox]:checked" ).length;
			if( target.parent().text() == "ALL INDIA" ){
					if( target.is( ":checked" ) ){
						$( "#comboTreeclusterDropDownContainer input[type=checkbox]" ).prop( "checked", true );
						$('#cluster').val('');
						$('#cluster').val('ALL INDIA');
					} else {
						$( "#comboTreeclusterDropDownContainer input[type=checkbox]" ).prop( "checked", false );
						$('#cluster').val('');
					}
			} else {
					if( target.is( ":checked" ) ){
						target.closest( "li" ).find( "input" ).prop( "checked", true );
						let sublenf=target.parents('ul:eq(0)').find( "input" ).length, sublenu=target.parents('ul:eq(0)').find( "input:checked" ).length;
						if( sublenf== sublenu){target.parents('ul').parent( "li" ).find( "input:eq(0)" ).prop( "checked", true );}
						var selen=$( "#comboTreeclusterDropDownContainer input[type=checkbox]:checked" ).length;
						var searchIDs = $("#comboTreeclusterDropDownContainer input:checked").parent().map(function(){
						      return $(this).text();
						    }).get();
						$('#cluster').val('');
						$('#cluster').val(searchIDs.toString());
						if(fuLen==selen){$('#comboTreeclusterDropDownContainer ul li:eq(0)').find( "input:eq(0)" ).prop( "checked", true );
						$('#cluster').val('');
						$('#cluster').val('ALL INDIA');
						}
						// return false;
						
					} else {
						target.closest( "li" ).find( "input" ).prop( "checked", false );
						target.parents('ul').parent( "li" ).find( "input:eq(0)" ).prop( "checked", false );
						$('#comboTreeclusterDropDownContainer ul li:eq(0)').find( "input:eq(0)" ).prop( "checked", false );
						var searchIDs = $("#comboTreeclusterDropDownContainer input:checked").parent().map(function(){
						      return $(this).text();
						    }).get();
						$('#cluster').val('');
						$('#cluster').val(searchIDs.toString());
					}
				
			}
			if( tirggerAccountCall ){
			var clusters = [];
			
			$("#comboTreeclusterDropDownContainer input:checked").each(function(i,e){
				clusters.push( $( this ).parent().text() );
			});
			
			if( $('#cluster').val() == "" ){
				$('#cluster').val('ALL INDIA');
				// return false;
			}
			
			/* if( clusters.length != 0 ){ */
				$.ajax({
			        type: "post",
			        url: "getStoreDataOnClus.htm",
			        contentType: "application/json; charset=utf-8",
			        dataType: "json",
			        beforeSend: function() {
			            ajaxLoader(spinnerWidth, spinnerHeight);
			        },
			       
			        data: JSON.stringify({"cluster" : clusters.join(","), "account" : $( "#customerChainL1Thrscn" ).val(), "launchId" : $("#dynamicLaunchId").val(), "IscustomstoreformatChecked" : $('#custmsel').prop('checked') }),  //Sarin Changes - Added last parameter Q1Sprint Feb2021
			        success: function (clsdata) {
			        	 $('.loader').hide();
			        	 // console.log(clsdata);
			        	// $('#customerChainL1Thrscn').val(clsdata.custCluster);
			        	
			        	// preAllCheck(
						// "comboTreecustomerChainL1ThrscnDropDownContainer",
						// clsdata.custCluster );
			        	var preSelectedAccount = $( "#customerChainL1Thrscn" ).val();
			        	
			        	 var optionJsonsrt;
				          
			        	 $('#cust-chain-store').empty();
			 			$('#cust-chain-store').multiselect("destroy");
			             $.each(clsdata.listOfStores, function (a, br) {
			               
			                 optionJsonsrt += "<option value='" + br + "'>" + br + "</option>";
			 			
			             });
			             $('#cust-chain-store').append(optionJsonsrt);
			             multiSelectionForCustChainStore();
			             
			              var optionJsonlnsrt; 
			              $('#cust-chain-cst-store').empty();
							$('#cust-chain-cst-store').multiselect("destroy");
				            $.each(clsdata.listCustomerStore, function (a, br) {
				            	optionJsonlnsrt += "<option value='" + br + "'>" + br + "</option>";
				     		});
				            
				            $('#cust-chain-cst-store').append(optionJsonlnsrt);
				            multiSelectionForCustStore();
			          
			        	
			             $('#storecount').val(clsdata.storeCount);
			             customSelectionStore();
			        	}
			     });
			/* } */
		}
		
	});
	
	// check all function for Customer
	$( document ).on( "change", "#comboTreecustomerChainL1ThrscnDropDownContainer input[type=checkbox]", function(e){
		
			e.preventDefault();
			e.stopPropagation();
			// debugger;
		     var target = $( this ),fuLen=$('.ComboTreeItemParent').length-1,selen=$( "#comboTreecustomerChainL1ThrscnDropDownContainer input[type=checkbox]:checked" ).length;
			if( target.parent().text() == "ALL CUSTOMERS" ){
				if( target.is( ":checked" ) ){
						$( "#comboTreecustomerChainL1ThrscnDropDownContainer input[type=checkbox]" ).prop( "checked", true );
						$('#customerChainL1Thrscn').val('');
						$('#customerChainL1Thrscn').val('ALL CUSTOMERS');
					} else {
						$( "#comboTreecustomerChainL1ThrscnDropDownContainer input[type=checkbox]" ).prop( "checked", false );
						$('#customerChainL1Thrscn').val('');
					}
			} else {
					if( target.is( ":checked" ) ){
						target.closest( "li" ).find( "input" ).prop( "checked", true );
						let sublenf=target.parents('ul:eq(0)').find( "input" ).length, sublenu=target.parents('ul:eq(0)').find( "input:checked" ).length;
						if( sublenf== sublenu){target.parents('ul').parent( "li" ).find( "input:eq(0)" ).prop( "checked", true );}
						var selen=$( "#comboTreecustomerChainL1ThrscnDropDownContainer input[type=checkbox]:checked" ).length;
						var searchIDs = $("#comboTreecustomerChainL1ThrscnDropDownContainer input:checked").parent().map(function(){
						      return $(this).text();
						    }).get();
						$('#customerChainL1Thrscn').val('');
						$('#customerChainL1Thrscn').val(searchIDs.toString());
						
						
						if(fuLen==selen){$('#comboTreecustomerChainL1ThrscnDropDownContainer ul li:eq(0)').find( "input:eq(0)" ).prop( "checked", true );
						$('#customerChainL1Thrscn').val('');
						$('#customerChainL1Thrscn').val('ALL CUSTOMERS');
						}
						// return false;
						
					} else {
						target.closest( "li" ).find( "input" ).prop( "checked", false );
						target.parents('ul').parent( "li" ).find( "input:eq(0)" ).prop( "checked", false );
						$('#comboTreecustomerChainL1ThrscnDropDownContainer ul li:eq(0)').find( "input:eq(0)" ).prop( "checked", false );
						var searchIDs = $("#comboTreecustomerChainL1ThrscnDropDownContainer input:checked").parent().map(function(){
						      return $(this).text();
						    }).get();
						$('#customerChainL1Thrscn').val('');
						$('#customerChainL1Thrscn').val(searchIDs.toString());
					}
				}
			if( tirggerAccountCall ){
			var arrToPush = [];
			$("#comboTreecustomerChainL1ThrscnDropDownContainer>ul>.ComboTreeItemParent>span>input:checked").each(function(i,e){
				var custCluster = $("#customerChainL1Thrscn").val();
				var clustCustArr = custCluster.split(",");
				arrToPush = [];
				for (var i = 0; i < clustCustArr.length; i++) {
					
					var accObj = { "account" :clustCustArr[i] };
					if(clustCustArr[i].includes(":")) {
						arrToPush.push(accObj);
					}
				}
			});
			if( arrToPush.length == 0 ){
				// return false;
				$('#customerChainL1Thrscn').val('ALL CUSTOMERS');
			}
			$.ajax({
		        type: "post",
		        url: "getStoreDataOnClus.htm",
		        contentType: "application/json; charset=utf-8",
		        dataType: "json",
		        
		        beforeSend: function() {
		            ajaxLoader(spinnerWidth, spinnerHeight);
		        },
		       
		        data: JSON.stringify({"account" : $( "#customerChainL1Thrscn" ).val(), "cluster" : $( "#cluster" ).val(), "launchId" : $("#dynamicLaunchId").val(), "IscustomstoreformatChecked" : $('#custmsel').prop('checked')  }),  //Sarin Changes - Added last parameter Q1Sprint Feb2021
		        success: function (acdata) {
		        	 $('.loader').hide();
		        	// console.log(acdata);
		        	 var optionJsonsrt;
		          
		        	 $('#cust-chain-store').empty();
		 			$('#cust-chain-store').multiselect("destroy");
		             $.each(acdata.listOfStores, function (a, br) {
		               
		                 optionJsonsrt += "<option value='" + br + "'>" + ( br == '' ? "&nbsp;" : br ) + "</option>";
		 			
		             });
		             $('#cust-chain-store').append(optionJsonsrt);
		             multiSelectionForCustChainStore();
		             
		              var optionJsonlnsrt; 
		              $('#cust-chain-cst-store').empty();
						$('#cust-chain-cst-store').multiselect("destroy");
			            $.each(acdata.listCustomerStore, function (a, br) {
			            	optionJsonlnsrt += "<option value='" + br + "'>" + ( br == '' ? "&nbsp;" : br )  + "</option>";
			     		});
			            
			            $('#cust-chain-cst-store').append(optionJsonlnsrt);
			            multiSelectionForCustStore();
		           
		             $('#storecount').val(acdata.storeCount);
		             customSelectionStore();
		        	}
		     });

		}
	});
	

	
	$('.comboTreeDropDownContainer ul li span.comboTreeParentPlus').html("+");
	instance.getSelectedItemsTitle();

	instance.getSelectedItemsId();

	/* multiselect */
	
	$('#customerChainL1').multiselect(
			{
				includeSelectAllOption : true,
				numberDisplayed : 2,
				/* buttonWidth: '100px', */
				nonSelectedText : 'ALL CUSTOMERS',
				selectAllText : 'ALL CUSTOMERS',
				onChange : function(option,
						checked, select) {
					
					var selectedOptionsChange = $('#customerChainL1 option:selected');
					if (selectedOptionsChange.length > 0){
						
						selectAll = false;
					}
				},

				onDropdownHide : function(event) {
					var selVals = [];
					var selectedOptions = $('#customerChainL1 option:selected');
					if (selectedOptions.length > 0 && selectAll == false) {
						for (var i = 0; i < selectedOptions.length; i++) {
							selVals.push(selectedOptions[i].value);
						}

						var strData = selVals.toString();
						$('.switch-dynamic').html('<select class="form-control" name="cust-chain" id="cust-chain" multiple="multiple"><option values="ALL CUSTOMERS">ALL CUSTOMERS</option>');


					} else {
						$('.switch-dynamic').html('<input type="text" name="cust-chain" class="form-control" id="cust-chain" value="ALL CUSTOMERS" readonly="true">');
					}

				},

				onSelectAll : function() {
				selectAll = true;

				},
				onDeselectAll : function() {
					$('.switch-dynamic').html(
									'<input type="text" class="form-control" name="cust-chain" id="cust-chain" value="ALL CUSTOMERS" readonly="readonly">');
				}

			});
	
	// multiselect store format
	$('#custstrfrmt').multiselect(
			{
				includeSelectAllOption : true,
				numberDisplayed : 2,
				 // buttonWidth: '474px',
				nonSelectedText : 'Select store format',
				selectAllText : 'Select store format',
				onChange : function(option,
						checked, select) {
					
					var selectedOptionsChange = $('#custstrfrmt option:selected');
					if (selectedOptionsChange.length > 0){
						
						selectAll = false;
					}
				},
				
				onDropdownHide : function(event) {
					var custselVals = [];
					var selectedOptions = $('#custstrfrmt option:selected');
					if (selectedOptions.length > 0 && selectAll == false) {
						for (var i = 0; i < selectedOptions.length; i++) {
							custselVals.push(selectedOptions[i].value);
						}

						var strData = custselVals.toString();
						$('.switch-dynamic-first-radio-cst').html('<select class="form-control" name="cust-chain-store" id="cust-chain-store" multiple="multiple"><option values="SELECT ALL STORE FORMAT">SELECT ALL STORE FORMAT</option>');


					} else {
						$('.switch-dynamic-first-radio-cst').html('<input type="text" name="cust-chain-store" class="form-control" id="cust-chain-store" value="SELECT ALL STORE FORMAT">');
					}

				},

				onSelectAll : function() {
				selectAll = true;

				},
				onDeselectAll : function() {
					$('.switch-dynamic-first-radio-cst').html(
									'<input type="text" class="form-control" name="cust-chain" id="cust-chain-store" value="SELECT ALL STORE FORMAT" readonly="readonly">');
				}

			});
	
	// multiselect customer store format
	$('#selcustfrmt').multiselect(
			{
				includeSelectAllOption : true,
				numberDisplayed : 2,
				 // buttonWidth: '474px',
				nonSelectedText : 'Select store format',
				selectAllText : 'Select store format',
				onChange : function(option,
						checked, select) {
					
					var selectedOptionsChange = $('#selcustfrmt option:selected');
					if (selectedOptionsChange.length > 0){
						
						selectAll = false;
					}
				},

				onDropdownHide : function(event) {
					var selcstfrVals = [];
					var selectedOptions = $('#selcustfrmt option:selected');
					if (selectedOptions.length > 0 && selectAll == false) {
						for (var i = 0; i < selectedOptions.length; i++) {
							selcstfrVals.push(selectedOptions[i].value);
						}

						var strData = selcstfrVals.toString();
						$('.switch-dynamic-first-radio-cststr').html('<select class="form-control" name="cust-chain-cst-store" id="cust-chain-cst-store" multiple="multiple"><option values="SELECT CUSTOMER STORE FORMAT">SELECT CUSTOMER STORE FORMAT</option>');


					} else {
						$('.switch-dynamic-first-radio-cststr').html('<input type="text" name="cust-chain-cst-store" class="form-control" id="cust-chain-cst-store" value="SELECT CUSTOMER STORE FORMAT">');
					}

				},

				onSelectAll : function() {
				selectAll = true;

				},
				onDeselectAll : function() {
					$('.switch-dynamic-first-radio-cststr').html(
									'<input type="text" class="form-control" name="cust-chain" id="cust-chain-cst-store" value="ALL CUSTOMERS" readonly="readonly">');
				}

			});
	
	$('#launch_category, #launch_business').on('blur', function() {
		var busLaunch = $('#launch_business').val();
		var launchCat = $('#launch_category').val();
		var result = (busLaunch / launchCat)*100;
		if(busLaunch != '' && launchCat != ''){
			if(parseInt(busLaunch) >= parseInt(launchCat)){
				ezBSAlert({
					messageText : "Value in launch business should be smaller than launch category",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				
				return false;
			}
			else{
				
				if(busLaunch == '' || launchCat == ''){
					$('#launch_classifiction').val('');
				}
				else if(result >= 3){
				$('#launch_classifiction').val("Gold");
				}
				else if(result >= 1 ){// && result >= 1
					$('#launch_classifiction').val("Silver");
				}
				else if(result < 1){
					$('#launch_classifiction').val("Bronze");
				}
			}
		}
		
	});
	
	
	$('.tme-datepicker').datepicker(
            {
                   showOn : "both",
                   dateFormat : "dd/mm/yy",
                   minDate : 0,
                   maxDate: "+6M",
                   buttonImage : "assets/images/calender.png",
                   buttonImageOnly : true,
                   buttonText : "Select date",
                   changeMonth : false,
                   numberOfMonths : 1,
                   onSelect: function() {// for 1 screen moc
                	   var mocmon =$('.tme-datepicker').val().split("/");
                	   if( !isNaN( mocmon[0] ) && 21 <= parseInt( mocmon[0] ) ){
                		   mocmon[1] = !isNaN( mocmon[1] ) ? parseInt( mocmon[1] ) + 1 : mocmon[1];
                		   mocmon[1] = (mocmon[1] + "").length == 1 ? "0" + mocmon[1] : mocmon[1];
                	   }
                	   var newmoc =  mocmon[1] + "" + mocmon[2];
                	   $('#mocMonth').val(newmoc);
                   }
              });
	
	
	// var optionJsonLaunch = "";

					// screen 2 table
					var row = "";
					//var counter = 1;
					for (var i = 0; i <= 3; i++) {
						row += "<tr><td><input type='checkbox' class='checklaunch' name='selectDel' id= 'bsk"+ [i]								
								+ "' value='selectDel'></td>" 
								+"<td class='xsdrop'><select class='form-control validfield salescat' onchange='salecat(this)'>"
								+ optionJsonLaunch
								+ "</select></td><td class='xsdrop'><select class='form-control validfield psacatgr' onchange='brandOnPsacat(this)'>" 
								+ "</select></td><td class='xsdrop'><select class='form-control validfield brand'>"
								+ "</select></td><td><input name='childBasepackDesc1' type='text' class='form-control validfield baseCode' onblur='getBasepackCode(this)' onkeypress='return isNumberKey(event)' placeholder='12345' maxlength='5'></td>"
								+ "<td><input name='childRatio1' type='text' class='form-control validfield descrip' placeholder='Description' onblur='descValidation(this);' maxlength='255'></td>"
								+ "<td class='xsfield'><input name='childRatio1' type='text' class='form-control validfield xsfield mrpIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);'></td>"
								+ "<td class='xsfield'><input name='childRatio1' type='text' class='form-control validfield xsfield turIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);'></td>"
								+ "<td class='xsfield'><input name='childRatio1' type='text' class='form-control validfield xsfield gsvIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);'></td>"
								+ "<td><input name='childRatio1' type='text' class='form-control validfield clvIn' placeholder='0000' onkeypress='return isNumberKey(event)' maxlength='4'></td>"
								+ "<td class='xsfield'><input name='childRatio1' type='text' class='form-control validfield xsfield grmIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);'></td>"
								+ "<td><select class='form-control validfield classfDrop'>" 
								+ "</select></td></tr>";
					}
					
					$('#basepack_add tbody').empty().append(row);
					$('.tab_content').hide();
					$('.tab_content:first').show();
					$('.tabs li:first').addClass('active');
					$('.tabs li a').prop('disabled', true);
					$('#launch_base3').val("");

					// new code

					// $('.progress li.disabled').prop('disabled', true);

					$("#addbasepack").click(function() {
						
						 $("#basepack_add tbody").append("<tr><td><input type='checkbox' class='checklaunch' name='selectDel' value='selectDel'></td>" 
								+"<td class='xsdrop'><select class='form-control validfield salescat' onchange='salecat(this)'>"
								+ optionJsonLaunch
								+ "</select></td><td class='xsdrop'><select class='form-control validfield psacatgr' onchange='brandOnPsacat(this)'>" 
								+ "</select></td><td class='xsdrop'><select class='form-control validfield'>"
								+ "</select></td><td><input name='childBasepackDesc1' type='text' class='form-control validfield baseCode' onblur='getBasepackCode(this)' onkeypress='return isNumberKey(event)' placeholder='12345' maxlength='5'></td>"
								+ "<td><input name='childRatio1' type='text' class='form-control validfield' placeholder='Description' onblur='descValidation(this);' maxlength='255'></td>"
								+ "<td class='xsfield'><input name='childRatio1' type='text' class='form-control validfield xsfield' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);'></td>"
								+ "<td class='xsfield'><input name='childRatio1' type='text' class='form-control validfield xsfield' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);'></td>"
								+ "<td class='xsfield'><input name='childRatio1' type='text' class='form-control validfield xsfield' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);'></td>"
								+ "<td><input name='childRatio1' type='text' class='form-control validfield' placeholder='0000' onkeypress='return isNumberKey(event)' maxlength='4'></td>"
								+ "<td class='xsfield'><input name='childRatio1' type='text' class='form-control validfield xsfield' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);'></td>"
								+ "<td><select class='form-control validfield classfDrop'>" 
								+ optionJsonclassfi
								+ "</select></td></tr>");
						 var lastRow = $("#basepack_add tr:last");
						// textEvents(lastRow);
						// $(lastRow).find("td input").on("keyup",
						// enableSubmit);
					});

					$('#delbasepack').click(function(){
						$('.checklaunch:checked').each(function () {
						    $(this).closest('tr').remove();
						});
					});
					
					$(".downloadButton").click(function() {
								/*
								 * var href = $('.downloadLink').attr('href');
								 * window.location.href = href;
								 */

								$('a[href$=".pdf"]').attr('download', '').attr(
										'target', '_blank');
							});

				
					
					/**
					 * ************************************************************************steps
					 * JS*****************************************************************
					 */
					$('#smartwizard').smartWizard(
									{
										selected : 0, // Initial selected
										// step, 0 = first step
										keyNavigation : false, // Enable/Disable
										// keyboard
										// navigation(left
										// and right
										// keys are used
										// if enabled)
										autoAdjustHeight : true, // Automatically
										// adjust
										// content
										// height
										cycleSteps : false, // Allows to cycle
										// the navigation of
										// steps
										backButtonSupport : true, // Enable
										// the back
										// button
										// support
										useURLhash : true, // Enable selection
										// of the step based
										// on url hash
										lang : { // Language variables
											next : 'Next',
											previous : 'Previous'
										},
										toolbarSettings : {
											toolbarPosition : 'bottom', // none,
											// top,
											// bottom,
											// both
											toolbarButtonPosition : 'right', // left,
											// right
											showNextButton : false, // show/hide
											// a Next
											// button
											showPreviousButton : false, // show/hide
											// a
											// Previous
											// button
											toolbarExtraButtons : [
													$('<button></button>')
															.text('Finish')
															.addClass('btn btn-info')
															.on('click',function() {
																alert('Finsih button click');
															 }),
													$('<button></button>')
															.text('Cancel')
															.addClass('btn btn-danger')
															.on('click',function() {
																alert('Cancel button click');
															}) ]
										},
										anchorSettings : {
											anchorClickable : true, // Enable/Disable
											// anchor
											// navigation
											enableAllAnchors : true, // Activates
											// all
											// anchors
											// clickable
											// all
											// times
											markDoneStep : true, // add done
											// css
											enableAnchorOnDoneStep : true
										// Enable/Disable the done steps
										// navigation
										},
										contentURL : null, // content url,
										// Enables Ajax
										// content loading.
										// can set as data
										// data-content-url
										// on anchor
										disabledSteps : [], // Array Steps
										// disabled
										errorSteps : [], // Highlight step
										// with errors
										theme : 'arrows',
										transitionEffect : 'fade', // Effect on
										// navigation,
										// none/slide/fade
										transitionSpeed : '400'
									});
					
					var wentInAlready = false;
					$("#smartwizard").on("leaveStep", function(e, anchorObject, stepNumber, stepDirection) {
						if( isManualClick ){
							isManualClick = false;
							e.preventDefault();
							return false;
						}
					});
					$( document ).on( 'mouseup', '#smartwizard ul.step-anchor a, #smartwizard ul.step-anchor li', function(e){
						
						isManualClick = true;
					} );
					
					$("#smartwizard").on("showStep", function(e, anchorObject, stepNumber, stepDirection) {
						isManualClick = false;
				    });

					// intiatizing wizard

					$('#wizard').smartWizard({
						transitionEffect : 'slideleft',
						onLeaveStep : leaveAStepCallback,
						onFinish : onFinishCallback,
						enableFinishButton : true
					});
					function leaveAStepCallback(obj) {
						var step_num = obj.attr('rel');
						return validateSteps(step_num);
					}

					function onFinishCallback() {
						if (validateAllSteps()) {
							$('form').submit();
						}
					}

					/*
					 * $("#basepack_add tbody tr").each(function(i, e) {
					 * $(this).find("td input").on("keyup", enableSubmit);
					 * textEvents($(this)); });
					 */

					var jsonNature = [ {
						nature : "General",
						value : "General"
					}, {
						nature : "Regional",
						value : "Regional"
					}, {
						nature : "Customer Specific",
						value : "Customer Specific"
					}, {
						nature : "Format Specific",
						value : "Format Specific"
					}, {
						nature : "Town Specific",
						value : "Town Specific"
					}, {
						nature : "Others",
						value : "Others"
					} ];

					$("#launch_nature").empty();
					var optionJsonscreenOne = "";
					optionJsonscreenOne += "<option value=''>Select Nature</option>";
					$.each(jsonNature, function(key, value) {
						optionJsonscreenOne += "<option value='" + value.value
								+ "'>" + value.nature + "</option>";
					});
					$("#launch_nature").append(optionJsonscreenOne);
					
					
			// second screen upload
										
					$("#btnSubmitBasePack").click(function (event) {
						event.preventDefault();
						var launchId = $("#dynamicLaunchId").val();
						var fileName = $('#uploadsecscre').val();
						if (fileName == '') {
							$('#uploadErrorMsg').show().html("Please select a file to upload");
							return false;
						} else {
							$('#uploadErrorMsg').hide();
							var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
							if (FileExt != "xlsx") {
								if (FileExt != "xls") {
									$('#uploadErrorMsg').show().html("Please upload .xls or .xlsx file");
									$("#launchfileupload").submit(function(e) {
										e.preventDefault();
									});
									return false;
								}

							}
						}
						
				        // Get form
				        var form = $('#launchfileupload')[0];

						// Create an FormData object
				        var data = new FormData(form);

				 		// If you want to add an extra field for the FormData
						// data.append("CustomField", "This is some extra data,
						// testing");

						// disabled the submit button
				       // $("#btnSubmitBasePack").prop("disabled", false);
				       
				    	
				        $.ajax({
				            type: "POST",
				            enctype: 'multipart/form-data',
				            url: launchId+'/uploadLaunchBasepack.htm',
				            data: data,
				            beforeSend: function() {
                                ajaxLoader(spinnerWidth, spinnerHeight);
                            },
				            processData: false,
				            contentType: false,
				            cache: false,
				            timeout: 600000,
				            success: function (upldata) {
				            	upldatanew = typeof upldata == "string" ?  JSON.parse(upldata) : upldata;
				            	var errmsg = upldatanew.responseData;
				            	 $('.loader').hide();
				                if("SUCCESS_FILE" ==  errmsg) {
				                	 $("#launchStoresTab").click();
				                	 $('#errorblockUpload').hide();
				                	 getlaunchStores();
				                }
				                else {
				                	$('#errorblockUpload').show();
				                	$('#errorblockUpload span').text(errmsg);
				                }
				               
				                $("#btnSubmitBasePack").prop("disabled", false);
				            },
				            error: function (jqXHR, textStatus, errorThrown) {
				            	// $('#errorblockUpload').find('span').text(jqXHR.responseText);
				                // $("#uploadErrorMsg").show().html(e.responseText);
				               // console.log("ERROR : ", e);
				                $("#btnSubmitBasePack").prop("disabled", true);
				                      
				            }
				        });
					    	
				    });
					
					
					// 3rd screen upload
					
					$("#launchstrFileUploadBtn").click(function (event) {
						event.preventDefault();
						var launchId = $("#dynamicLaunchId").val();
						var fileName = $('#uploadcls').val();
						if (fileName == '') {
							$('#uploadclsErrorMsg').show().html("Please select a file to upload");
							return false;
						} else {
							$('#uploadclsErrorMsg').hide();
							var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
							if (FileExt != "xlsx") {
								if (FileExt != "xls") {
									$('#uploadclsErrorMsg').show().html("Please upload .xls or .xlsx file");
									$("#launchstrfileupload").submit(function(e) {
										e.preventDefault();
									});
									return false;
								}

							}
						}
						
				        // Get form
				        var form = $('#launchstrfileupload')[0];

						// Create an FormData object
				        var data = new FormData(form);

				 		// If you want to add an extra field for the FormData
						// data.append("CustomField", "This is some extra data,
						// testing");

						// disabled the submit button
				       // $("#btnSubmitBasePack").prop("disabled", false);
				       
				    	
				        $.ajax({
				            type: "POST",
				            enctype: 'multipart/form-data',
				            url: launchId+'/uploadLaunchClusterForStoreForm.htm',
				            data: data,
				            processData: false,
				            contentType: false,
				            cache: false,
				            timeout: 600000,
				            beforeSend: function() {
                                ajaxLoader(spinnerWidth, spinnerHeight);
                            },
				            success: function (lnchStrdata) {
				            	/*
								 * upllnstdatanew = typeof lnchStrdata ==
								 * "string" ? JSON.parse(lnchStrdata) :
								 * lnchStrdata; var errmsg =
								 * lnchStrdata.responseData.split("java.lang.Exception:")[2];
								 * console.log(lnchStrdata.responseData);
								 */
				            	lnchStrdatanew = typeof lnchStrdata == "string" ?  JSON.parse(lnchStrdata) : lnchStrdata;
				            	var errmsg = lnchStrdatanew.Error;
				            	 $('.loader').hide();
				            	
				               if(lnchStrdata.includes('SUCCESS_FILE')) {
				                	 $("#launchSellInTab").click();
				                	 $('#errorblockUpload').hide();
				                	 //next 4 lines  added bharati for sprint-7 US-7
				                	 $('#launchStoreErrorFileForStoreFormat').hide();
				                	 $('#launchvalidateZero').hide();
				                	 $('#successblock').show().find('span').html(' File Uploaded Successfully !!!');
				                	 getSellInData();
				                	  var custStoreSuccvalue = lnchStrdatanew.Success;
				            	      var SuccesCustStoreFormatSplit = custStoreSuccvalue.split(/(\d+)/);
				            	
				                	  $('#tmeapprovedstorecount').val(SuccesCustStoreFormatSplit[1]);
				                	  $('.launchStoreNext').prop('disabled', false);
				                }
				                //bharati added this condition for download customer store error file US-7 In sprint-7
				                else if(lnchStrdata.includes('File Upload is UnSuccessful')){
				                $('#launchStoreErrorFileForStoreFormat').show();
				                $('.launchStoreNext').prop('disabled', true);
				                $('#successblock').hide();
				                $('#launchvalidateZero').hide();
				                } else if(lnchStrdata.includes('Total TME Targeted Stores has to be')){
				                 $('#launchvalidateZero').show().find('span').html('Please Enter Minimum One Target Store');
				                 $('.launchStoreNext').prop('disabled', true);
				                 $('#successblock').hide();
				                 $('#launchStoreErrorFileForStoreFormat').hide();
				                }
				                 else if(lnchStrdata.includes('Minimum stores for Total Stores has to be grater than 0')){
				                 $('#launchvalidateZero').show().find('span').html('Please Upload Minimum Stores for Total Stores Greater than 0');
				                 $('.launchStoreNext').prop('disabled', true);
				                 $('#successblock').hide();
				                  $('#launchStoreErrorFileForStoreFormat').hide();
				                } 
				                else {
				                	$('#errorblockUpload').show();
				                	 $('#launchStoreErrorFileForStoreFormat').hide();
				                	 $('#launchvalidateZero').hide();
				                	$('#successblock').hide();
		                         }
				                 window.location.href = storepageURL;    // bharati added for sprint-7 US-7 stay on same page
				                 $("#launchstrFileUploadBtn").prop("disabled", false);
				                 $('#launchstrfileupload')[0].reset();
				            },
				            error: function (e) {
				            	// $('#errorblockUpload').find('span').text(e.responseText);
				               // console.log("ERROR : ", e);
				            	// $('#errorblockUpload').find('span').text(e.responseText);
				                $("#launchstrFileUploadBtn").prop("disabled", true);
				                $('#successblock').hide();
				                      
				            }
				        });
				        //bharati added for find current pageurl in sprint-7
					      var storepageURL = $(location).attr("href");
					    	
				    });
					
					$("#launchCuststrFileUploadBtn").click(function (event) {
						event.preventDefault();
						var launchId = $("#dynamicLaunchId").val();
						var fileName = $('#uploadclsCust').val();
						if (fileName == '') {
							$('#uploadcustclsErrorMsg').show().html("Please select a file to upload");
							return false;
						} else {
							$('#uploadcustclsErrorMsg').hide();
							var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
							if (FileExt != "xlsx") {
								if (FileExt != "xls") {
									$('#uploadcustclsErrorMsg').show().html("Please upload .xls or .xlsx file");
									$("#launchCuststrfileupload").submit(function(e) {
										e.preventDefault();
									});
									return false;
								}

							}
						}
						
				        // Get form
				        var form = $('#launchCuststrfileupload')[0];

						// Create an FormData object
				        var data = new FormData(form);

				 		// If you want to add an extra field for the FormData
						// data.append("CustomField", "This is some extra data,
						// testing");

						// disabled the submit button
				       // $("#btnSubmitBasePack").prop("disabled", false);
				       
				    	
				        $.ajax({
				            type: "POST",
				            enctype: 'multipart/form-data',
				            url: launchId+'/uploadLaunchClusterForCustStoreForm.htm',
				            data: data,
				            processData: false,
				            contentType: false,
				            cache: false,
				            timeout: 600000,
				            beforeSend: function() {
                                ajaxLoader(spinnerWidth, spinnerHeight);
                            },
				            success: function (lnchStrdata) {
				            	/*
								 * upllnstdatanew = typeof lnchStrdata ==
								 * "string" ? JSON.parse(lnchStrdata) :
								 * lnchStrdata; var errmsg =
								 * lnchStrdata.responseData.split("java.lang.Exception:")[2];
								 * console.log(lnchStrdata.responseData);
								 */
				            	lnchStrdatanew = typeof lnchStrdata == "string" ?  JSON.parse(lnchStrdata) : lnchStrdata;
				            	var errmsg = lnchStrdatanew.Error;
				            	 $('.loader').hide();
				            	 if(lnchStrdata.includes('SUCCESS_FILE')) {
				                	 $("#launchSellInTab").click();
				                	 $('#errorblockUpload').hide();
				                	 //next 4 lines  added bharati for sprint-7 US-7
				                	 $('#launchvalidateZero').hide();
				                	 $('#launchStoreerrorblockUpload').hide();
				                	 $('#successblock').show().find('span').html(' File Uploaded Successfully !!!');
				                	  getSellInData();
				                	  var custStoreSuccvalue = lnchStrdatanew.Success;
				            	      var SuccesCustStoreFormatSplit = custStoreSuccvalue.split(/(\d+)/);
				            	      $('#tmeapprovedstorecount').val(SuccesCustStoreFormatSplit[1]);
				                	  $('.launchStoreNext').prop('disabled', false);
				                }
				                //bharati added this condition for download customer store error file US-7 In sprint-7
				                else if(lnchStrdata.includes('File Upload is UnSuccessful')){
				                $('#launchStoreerrorblockUpload').show();
				                $('.launchStoreNext').prop('disabled', true);
				                $('#successblock').hide();
				                $('#launchvalidateZero').hide();
				                }else if(lnchStrdata.includes('Total TME Targeted Stores has to be')){
				                 $('#launchvalidateZero').show().find('span').html('Please Enter Minimum One Target Store');
				                 $('.launchStoreNext').prop('disabled', true);
				                 $('#successblock').hide();
				                 $('#launchStoreerrorblockUpload').hide();
				                }else if(lnchStrdata.includes('Minimum stores for Total Stores has to be grater than 0')){
				                 $('#launchvalidateZero').show().find('span').html('Please Upload Minimum Stores for Total Stores Greater than 0');
				                 $('.launchStoreNext').prop('disabled', true);
				                 $('#successblock').hide();
				                 $('#launchStoreerrorblockUpload').hide();
				                } 
				                else{
				                	$('#errorblockUpload').show();
				                	$('#successblock').hide();
				                	$('#launchStoreerrorblockUpload').hide();
				                	 $('#launchvalidateZero').hide();
				              	 }
				                
				                 window.location.href = storepageURL;    // bharati added for sprint-7 US-7 stay on same page
				                $("#launchstrFileUploadBtn").prop("disabled", false);
				                $('#launchCuststrfileupload')[0].reset();
				            },
				            error: function (e) {
				            	// $('#errorblockUpload').find('span').text(e.responseText);
				               // console.log("ERROR : ", e);
				            	// $('#errorblockUpload').find('span').text(e.responseText);
				                $("#launchstrFileUploadBtn").prop("disabled", true);
				                $('#successblock').hide();
				                      
				            }
				        });
				        //bharati added for find current page -url in sprint-7
					      var storepageURL = $(location).attr("href");
					    	
				    });
					
					// 4th screen upload
					//bharati done changes in below function for sprint-6 US-5
					$("#launchsellinFileUploadBtn").click(function (event) {
						event.preventDefault();
						var launchId = $("#dynamicLaunchId").val();
						var fileName = $('#uploadSellIn').val();
						if (fileName == '') {
							$('#uploadSellInErrorMsg').show().html("Please select a file to upload");
							return false;
						} else {
							$('#uploadSellInErrorMsg').hide();
							var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
							if (FileExt != "xlsx") {
								if (FileExt != "xls") {
									$('#uploadSellInErrorMsg').show().html("Please upload .xls or .xlsx file");
									$("#launchsellinfileupload").submit(function(e) {
										e.preventDefault();
									});
									return false;
								}

							}
						}
						
				        // Get form
				        var form = $('#launchsellinfileupload')[0];

						// Create an FormData object
				        var data = new FormData(form);

				 					       
				    	if(launchId == "") {
				    		alert("Please create a launch plan first");
				    	} else {
					        $.ajax({
					            type: "POST",
					            enctype: 'multipart/form-data',
					            url: launchId+'/uploadLaunchSellIn.htm',
					            data: data,
					            processData: false,
					            contentType: false,
					            cache: false,
					            timeout: 600000,
					            beforeSend: function() {
                                    ajaxLoader(spinnerWidth, spinnerHeight);
                                },
					            success: function (sellIndata) {
					            	sellIndatanew = typeof sellIndata == "string" ?  JSON.parse(sellIndata) : sellIndata;
					            	// var errmsg = lnchStrdatanew.Error;
					            	 $('.loader').hide();
					                if("SUCCESS_FILE" == sellIndatanew.Success) {
					                	 $("#launchVisiTab").click();
					                	 $('#errorblockUpload').hide();
										 $('#sellinerrorblockUpload').hide(); 
										 $('#successblock').show().find('span').html(' File Uploaded Successfully !!!');  //added
		                                  //getvisiPlan();  //bharati commented this line
		                                  getSellInData();
		                                  window.location.href = pageURL;
					                }
					                else if(sellIndata.includes('Tried uploading Wrong Data')){
					                    $('#sellinerrorblockUpload').show(); 
									}else if(sellIndata.includes('Error')){
									   $('#errorblockUpload').show();
									}
					                
					                $("#launchsellinFileUploadBtn").prop("disabled", false);
					            },
					            error: function (e) {
									 $("#uploadSellInErrorMsg").text(e.responseText);
									
					               // console.log("ERROR : ", e);
					                $("#launchsellinFileUploadBtn").prop("disabled", true);
					                      
					            }
					           
					        });
					        //bharati added for find current page -url
					         var pageURL = $(location).attr("href");
                                       
				    	}
					    	
				    });
					
				
					$("#launchVisiPlanFileUploadBtn").click(function (event) {
						event.preventDefault();
						var launchId = $("#dynamicLaunchId").val();
						var fileName = $('#uploadVisiPlan').val();
						if (fileName == '') {
							$('#uploadVisiPlanErrorMsg').show().html("Please select a file to upload");
							return false;
						} else {
							$('#uploadVisiPlanErrorMsg').hide();
							var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
							if (FileExt != "xlsx") {
								if (FileExt != "xls") {
									$('#uploadVisiPlanErrorMsg').show().html("Please upload .xls or .xlsx file");
									$("#launchVisiPlanUpload").submit(function(e) {
										e.preventDefault();
									});
									return false;
								}

							}
						}
						
				        // Get form
				        var form = $('#launchVisiPlanUpload')[0];

						// Create an FormData object
				        var data = new FormData(form);

				    	
				        $.ajax({
				            type: "POST",
				            enctype: 'multipart/form-data',
				            url: launchId+'/uploadLaunchVisiPlaning.htm',
				            data: data,
				            processData: false,
				            contentType: false,
				            cache: false,
				            timeout: 600000,
				            beforeSend: function() {
                                ajaxLoader(spinnerWidth, spinnerHeight);
                            },
				            success: function (vissidata) {
				            	 $('.loader').hide();
				            	 var finalData = JSON.parse(vissidata);
				                if("SUCCESS_FILE" == finalData.Success) {
				                	 $('#errorblockUpload').hide();
				                	 $("#launchFinBuiUpTab").click();
				                	 getfinalPlan();
				                }
				                else if(vissidata.includes('Error')){
				                	// $('#errorblockUpload').show();
				                	// alert(JSON.parse(vissidata).Error);
				                	$('#errorblockUpload').show();
				                }
				                $("#launchVisiPlanFileUploadBtn").prop("disabled", false);
				            },
				            error: function (e) {
				                $("#uploadVisiPlanErrorMsg").text(e.responseText);
				               // console.log("ERROR : ", e);
				                $("#launchVisiPlanFileUploadBtn").prop("disabled", true);
				                      
				            }
				        });
					    	
				    });
					
					// 3 screen checkbox onchange ajax call
					
					$( "#custmsel" ).on('change', function(){  
						//if ($(this).prop('checked')==false){ //Sarin Changes - Commented Q1Sprint Feb2021

						var launchId = $("#dynamicLaunchId").val();
						
						$.ajax({
					        type: "post",
					        url: "getStoreDataOnClus.htm",
					        contentType: "application/json; charset=utf-8",
					        dataType: "json",
					        beforeSend: function() {
					            ajaxLoader(spinnerWidth, spinnerHeight);
					        },
					       
					        data: JSON.stringify({"cluster" : $( "#cluster" ).val(), "account" : $( "#customerChainL1Thrscn" ).val() , "launchId" : $("#dynamicLaunchId").val(), "IscustomstoreformatChecked" : $(this).prop('checked') }),  //Sarin Changes - Added last parameter Q1Sprint Feb2021
					        success: function (clssdata) {
					        	 $('.loader').hide();
					        			          
					        	
					             $('#storecount').val(clssdata.storeCount);
					        	}
					     });
						//$( "#storecount" ).val( totalStoreCount );
						//}
					});
					
					// Launch Create MOC
					var today = new Date();
					// today.setDate(today.getDate() + 45); 

			       var yy = today.getFullYear(),
			        m = today.getMonth(),
			        dd = today.getDate(),
			        mm = dd < 21 ? ( m + 1 ) : ( m + 2 );
					var option = "<option value=''>Select MOC</option>";
			        m = dd < 21 ? m : ( m + 1 );
				    for( var i = 1; i <= 6; i++ ){
	                    var lastMonth = mm + i > 13 ? ( ( mm + i ) - 13 ) : m + i;
	                    lastMonth = ( lastMonth + "" ).length == 1 ? "0" + lastMonth : lastMonth; 
	                    var lastYear = mm + i > 13 ? yy + 1 : yy;
	                    option += "<option value='"+lastMonth + "" + lastYear+"'>"+lastMonth + "" + lastYear+"</option>"
	                }
					$("#mocMonth").empty().append(option);
					
					
//					$("#mocMonth").append($('<option>',{value:"${launchMoc}")).text(value);
					
					
			});
			
function getDateFromMoc(currentMoc) {
	//console.log(currentMoc);
	$("#startdate").val("20/"+currentMoc.substring(0,2)+"/"+currentMoc.substring(2));
}


// annexure upload
function uploadAnnexureDoc(isExistingUpload) {
	var launchId = $("#dynamicLaunchId").val();
	var fileName = $('#annexfile').val();
	if (fileName == '') {
		$('#uploadannErrorMsg').show().html("Please select a file to upload");
		return false;
	} else {
		$('#uploadannErrorMsg').hide();
		$('#annxr').hide();
		
		var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
		if (FileExt.toLowerCase() != "xlsx" && FileExt.toLowerCase() != "xls" && FileExt.toLowerCase() != "pdf" &&
				FileExt.toLowerCase() != "docx" && FileExt.toLowerCase() != "pptx") {
			
				$('#uploadannErrorMsg').show().html("Please upload .xls/.xlsx/.pdf/.docx/.pptx file");
				$("#annexureFileUpload").submit(function(e) {
					e.preventDefault();
				});
				return false;
			
		}
		
	}
	
    // Get form
    var form = $('#tmeannexFileUploadBean')[0];

	// Create an FormData object
    var data = new FormData(form);

		// If you want to add an extra field for the FormData
	// data.append("CustomField", "This is some extra data, testing");

	// disabled the submit button
   // $("#btnSubmitBasePack").prop("disabled", false);
   
	
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: launchId+'/1/uploadAnnexureDoc.htm',
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
        success: function (data) {
        	 $('.loader').hide();
            if("SUCCESS_FILE" == data) {
            	isFileOneUploaded = true;
            	$('#errorblockUpload').hide();
            	$('#annexerrorblockUpload').hide();  //bharati added for sprint-7
            	$('#annxr').hide();
            	 $('#successblockUpload').show().find('span').html(' Files Uploaded Successfully !!!');
            	 $( '#annexfile' ).parent().removeClass( 'btn-primary' ).addClass('btn-success').find( 'i' ).attr( 'class', 'glyphicon glyphicon-ok' ).parent().find( 'span' ).html( "&nbsp;" );

            }  //bharati added this else block for annex file size in sprint-7 Dec-21 
            else if(data.includes('File size')){
				$('#annexerrorblockUpload').show().find('span').html("File Size Exceeded");
				                	
				} else {
            	$('#successblockUpload').hide();
            	$('#errorblockUpload').show();
            	$('#annexerrorblockUpload').hide();  //bharati added for sprint-7
     		}
            return false;
           // $("#launchstrFileUploadBtn").prop("disabled", false);
        },
        error: function (e) {
            $("#uploadErrorMsg").text(e.responseText);
           // console.log("ERROR : ", e);
          // $("#annexureUploadBtn").prop("disabled", true);
                  
        }
    });
    	
}

// artwork upload
function uploadArtDoc(isExistingUpload) {
	var launchId = $("#dynamicLaunchId").val();
	var fileName = $('#artfile').val();
	if (fileName == '') {
		$('#uploadartErrorMsg').show().html("Please select a file to upload");
		return false;
	} else {
		$('#uploadartErrorMsg').hide();
		$('#uploadartErrorMsg').hide();
		
		var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
		if (FileExt.toLowerCase() != "png" && FileExt.toLowerCase() != "jpg" && FileExt.toLowerCase() != "jpeg"
				&& FileExt.toLowerCase() != "pdf" && FileExt.toLowerCase() != "docx" && FileExt.toLowerCase() != "pptx") {

			$('#uploadartErrorMsg').show().html(
					"Please upload .png/.jpg/.jpeg/.pdf/.docx/.pptx file");
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
	var form = $('#tmeartworkFileUpload')[0];

	// Create an FormData object
	var data = new FormData(form);

	// If you want to add an extra field for the FormData
	// data.append("CustomField", "This is some extra data, testing");

	// disabled the submit button
	// $("#btnSubmitBasePack").prop("disabled", false);

	$.ajax({
		type : "POST",
		enctype : 'multipart/form-data',
		url : launchId + '/' + 'uploadArtWorkDoc.htm',
		data : data,
		processData : false,
		contentType : false,
		cache : false,
		timeout : 600000,
		beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
		success : function(data) {
			$('#uploadartErrorMsg').hide();
			$('#errorblockUpload').hide();
			$('#annexerrorblockUpload').hide();  //bharati added for sprint-7
			 $('.loader').hide();
			if ("SUCCESS_FILE" == data) {
				isFileTwoUploaded = true;
				$('#uploadartErrorMsg').hide();
				$('#mdglnc').hide();
				
				$('#successblockUpload').show().find('span').html(
						' Files Uploaded Successfully !!!');
				$( '#artfile' ).parent().removeClass( 'btn-primary' ).addClass('btn-success').find( 'i' ).attr( 'class', 'glyphicon glyphicon-ok' ).parent().find( 'span' ).html( "&nbsp;" );

			}  //bharati added this else block for annex file size in sprint-7 Dec-21 
			 else if(data.includes('File size')){
				$('#annexerrorblockUpload').show().find('span').html("File Size Exceeded");
				                	
				} else {
				$('#successblockUpload').hide();
				$('#errorblockUpload').show();
				$('#annexerrorblockUpload').hide();  //bharati added for sprint-7
			}
			return false;
			// $("#launchstrFileUploadBtn").prop("disabled", false);
		},
		error : function(e) {
			$("#uploadErrorMsg").text(e.responseText);
			// console.log("ERROR : ", e);
			// $("#launchstrFileUploadBtn").prop("disabled", true);

		}
	});

}

// MDG file upload
function uploadmdgDoc(isExistingUpload) {
	var launchId = $("#dynamicLaunchId").val();
	var fileName = $('#mdgfile').val();
	if (fileName == '') {
		$('#uploadmdgErrorMsg').show().html("Please select a file to upload");
		return false;
	} else {
		$('#uploadmdgErrorMsg').hide();
		var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
		if (FileExt.toLowerCase() != "xlsx" && FileExt.toLowerCase() != "xls" && FileExt.toLowerCase() != "pdf" && FileExt.toLowerCase() != "docx" && FileExt.toLowerCase() != "pptx") {
			
				$('#uploadmdgErrorMsg').show().html("Please upload .xls/.xlsx/.pdf/.docx/.pptx file");
				/*
				 * $("#annexureFileUpload").submit(function(e) {
				 * e.preventDefault(); });
				 */
				return false;
			
		}
		/*
		 * var fileName = $( "#mdgfile" )[0].files[0].name.substr(0, $(
		 * "#mdgfile" )[0].files[0].name.indexOf( "." ) );
		 * 
		 * if( fileName.indexOf( 'MDG_deck_' ) != 0 ){
		 * $('#uploadmdgErrorMsg').show().html("Please enter correct file
		 * name"); return false; }
		 * 
		 * var anuxName = fileName.substr( ( 'MDG_deck_'.length ),
		 * fileName.length );
		 * 
		 * if( anuxName != $( "#launch_name" ).val() ){
		 * $('#uploadmdgErrorMsg').show().html("Please enter correct file
		 * name"); return false; }
		 */
	}
	
    // Get form
    var form = $('#tmemdgFileUpload')[0];

	// Create an FormData object
    var data = new FormData(form);

		// If you want to add an extra field for the FormData
	// data.append("CustomField", "This is some extra data, testing");

	// disabled the submit button
   // $("#btnSubmitBasePack").prop("disabled", false);
   
	
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: launchId+'/1/uploadMdgDoc.htm',
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
        success: function (data) {
        	 $('.loader').hide();
            if("SUCCESS_FILE" == data) {
            	$('#errorblockUpload').hide();
            	$('#annexerrorblockUpload').hide();   //bharati added for sprint-7
            	isFileThreeUploaded = true;
            	 $('#successblockUpload').show().find('span').html(' Files Uploaded Successfully !!!');
            	 $( '#mdgfile' ).parent().removeClass( 'btn-primary' ).addClass('btn-success').find( 'i' ).attr( 'class', 'glyphicon glyphicon-ok' ).parent().find( 'span' ).html( "&nbsp;" );

            }   //bharati added this else block for annex file size in sprint-7 Dec-21 
            else if(data.includes('File size')){
				$('#annexerrorblockUpload').show().find('span').html("File Size Exceeded");
				                	
				} else  {
            	$('#successblockUpload').hide();
            	$('#errorblockUpload').show();
            	$('#annexerrorblockUpload').hide(); //bharati added for sprint-7
			}
            return false;
           // $("#launchstrFileUploadBtn").prop("disabled", false);
        },
        error: function (e) {
            $("#uploadErrorMsg").text(e.responseText);
           // console.log("ERROR : ", e);
           // $("#launchstrFileUploadBtn").prop("disabled", true);
                  
        }
    });
    	
}
	function precheck( _string ){
		tirggerAccountCall = false;
		var spans = $( "#comboTreeeditGeographyDropDownContainer .comboTreeItemTitle" )
		readyString  = _string.split( "," );
		for( var i = 0; i < spans.length; i++  ){
		  if( readyString.indexOf( $( spans[i] ).text().trim() ) != -1 ){
				$( spans[i] ).find( "input:first" ).prop( "checked", true );
				$( spans[i] ).find( "input:first" ).trigger( "change" );
		  }
		}
		tirggerAccountCall = true;
		$( $("#comboTreeeditGeographyDropDownContainer li .comboTreeItemTitle input:checked" )[0]).trigger( "change" );
	}
	
	function preAllCheck( ID, _string ){
		var spans = $( "#"+ID+" .comboTreeItemTitle" )
		readyString  = _string.split( "," );
		tirggerAccountCall = false;
		for( var i = 0; i < spans.length; i++  ){
		  if( readyString.indexOf( $( spans[i] ).text().trim() ) != -1 ){
			  
				$( spans[i] ).find( "input:first" ).prop( "checked", true );
		  }
		}
		tirggerAccountCall = true;
		$( $("#"+ID+" li .comboTreeItemTitle input:checked" )[0]).trigger( "change" );
	}
	
	function precheck3rdScreenClus( _string, flg ){
		var spans = $( "#comboTreeclusterDropDownContainer .comboTreeItemTitle" );
		flg = typeof flg == 'undefined' ? false : flg;
		if( !flg ){
			tirggerAccountCall = false;
		}
		readyString  = _string.split( "," );
		
		for( var i = 0; i < spans.length; i++  ){
		  if( readyString.indexOf( $( spans[i] ).text().trim() ) != -1 ){
				$( spans[i] ).find( "input:first" ).prop( "checked", true );
				$( spans[i] ).find( "input:first" ).trigger( 'change' );
		  }
		}
		if( !flg ){
			tirggerAccountCall = true;
		
		}
		$( $( "#comboTreeclusterDropDownContainer li .comboTreeItemTitle input:checked" )[0]).trigger( "change" );
	}
	
	function precheckCustomer( _string, flg ){
		flg = typeof flg == 'undefined' ? false : flg;
		if( !flg ){
			tirggerAccountCall = false;
		}
		var spans = $( "#comboTreecustomerChainL1ThrscnDropDownContainer .comboTreeItemTitle" )
		readyString  = _string.split( "," );
		for(var x = 0; x < readyString.length; x++) {
			readyString[x] = readyString[x].trim();
		}
		for( var i = 0; i < spans.length; i++  ){
		  if( readyString.indexOf( $( spans[i] ).text().trim() ) != -1 ){
				$( spans[i] ).find( "input:first" ).prop( "checked", true );
				$( spans[i] ).find( "input:first" ).trigger( "change" );
		  }
		}
		if( !flg ){
			tirggerAccountCall = true;
		}
		$( $( "#comboTreecustomerChainL1ThrscnDropDownContainer li .comboTreeItemTitle input:checked" )[0]).trigger( "change" );
	}

function multiSelectionForCustChain() {
	$('#cust-chain').multiselect({
		includeSelectAllOption : true,
		numberDisplayed : 2,
		/* buttonWidth: '100px', */
		nonSelectedText : 'ALL CUSTOMERS',
		selectAllText : 'ALL CUSTOMERS',
		onChange : function(option, checked, select) {
			// console.log(option);
			var selVals = [];
			var selectedOptions = $('#cust-chain option:selected');
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

function multiSelectionForCustChainStore() {
	$('#cust-chain-store').multiselect({
		
		includeSelectAllOption : true,
		numberDisplayed : 2,
		/* buttonWidth: '100px', */
		nonSelectedText : 'Select store format',
		selectAllText : 'Select store format',
		onChange : function(option, checked, select) {
			// console.log(option);
			var custCluster = $("#customerChainL1Thrscn").val();
			var clustCustArr = custCluster.split(",");
			var arrToPush = [];
						
			if( custCluster == "" ){
				$( "#storecount" ).val( "0" );
				return false;
			}
			
			for (var i = 0; i < clustCustArr.length; i++) {
				
				if(!clustCustArr[i].includes(":")) {
					arrToPush.push(clustCustArr[i]);
				}
			}
			
			var custselVals = [];
			var strData;
			var selectedOptions = $('#cust-chain-store option:selected');
			// var strOldCount = $('#storecount').val();
			if (selectedOptions.length >= 0) {
				/*
				 * for (var i = 0; i < selectedOptions.length; i++) {
				 * custselVals.push(selectedOptions[i].value); }
				 */
				 $(selectedOptions).each(function(index, selectedOptions){
					 custselVals.push([$(this).val()]);
			        });

				strData = custselVals.toString();
			
				$.ajax({
		            type: "post",
		            url: "getStoreDataOnStore.htm",
		            contentType: "application/json; charset=utf-8",
		            dataType: "json",
		            beforeSend: function() {
		                ajaxLoader(spinnerWidth, spinnerHeight);
		            },
		            data: JSON.stringify( { "custStoreFormat": "", "storeFormat": strData, "accountList":$( "#customerChainL1Thrscn" ).val(), "cluster" : $( "#cluster" ).val(), "launchId" : $("#dynamicLaunchId").val(), "IscustomstoreformatChecked" : $('#custmsel').prop('checked') } ),  //Sarin Changes - Added last parameter Q1Sprint Feb2021
		            success: function (strdata) {
		            	 $('.loader').hide();
		            	// console.log(strdata.storeCount);
		            	 $('#storecount').val(strdata.storeCount);
		            }
		         });
			}
			else{
				
				$.ajax({
			        type: "post",
			        url: "getStoreDataOnClus.htm",
			        contentType: "application/json; charset=utf-8",
			        dataType: "json",
			        beforeSend: function() {
			            ajaxLoader(spinnerWidth, spinnerHeight);
			        },
			       
			        data: JSON.stringify({"cluster" : $( "#cluster" ).val(), "account" : $( "#customerChainL1Thrscn" ).val() , "launchId" : $("#dynamicLaunchId").val(), "IscustomstoreformatChecked" : $('#custmsel').prop('checked') }),  //Sarin Changes - Added last parameter Q1Sprint Feb2021
			        success: function (clssdata) {
			        	 $('.loader').hide();
			        			          
			        	
			             $('#storecount').val(clssdata.storeCount);
			        	}
			     });
				$( "#storecount" ).val( totalStoreCount );
			}
			// var strData =
			// document.getElementById("cust-chain-store").nextSibling.children[0].getAttribute("title");
		},
		onSelectAll : function() {

			var custCluster = $("#customerChainL1Thrscn").val();
			var clustCustArr = custCluster.split(",");
			var arrToPush = [];
						
			if( custCluster == "" ){
				$( "#storecount" ).val( "0" );
				return false;
			}
			
			for (var i = 0; i < clustCustArr.length; i++) {
				
				if(!clustCustArr[i].includes(":")) {
					arrToPush.push(clustCustArr[i]);
				}
			}
			
			var custselVals = [];
			var strData;
			var selectedOptions = $('#cust-chain-store option:selected');
			// var strOldCount = $('#storecount').val();
			if (selectedOptions.length >= 0) {
				/*
				 * for (var i = 0; i < selectedOptions.length; i++) {
				 * custselVals.push(selectedOptions[i].value); }
				 */
				 $(selectedOptions).each(function(index, selectedOptions){
					 custselVals.push([$(this).val()]);
			        });

				strData = custselVals.toString();
				
				$.ajax({
		            type: "post",
		            url: "getStoreDataOnStore.htm",
		            contentType: "application/json; charset=utf-8",
		            dataType: "json",
		            beforeSend: function() {
		                ajaxLoader(spinnerWidth, spinnerHeight);
		            },
		            data: JSON.stringify( { "custStoreFormat": "", "storeFormat": strData, "accountList":$( "#customerChainL1Thrscn" ).val(), "cluster" : $( "#cluster" ).val(), "launchId" : $("#dynamicLaunchId").val(), "IscustomstoreformatChecked" : $('#custmsel').prop('checked') } ),  //Sarin Changes - Added last parameter Q1Sprint Feb2021
		            success: function (strdata) {
		            	 $('.loader').hide();
		            	// console.log(strdata.storeCount);
		            	 $('#storecount').val(strdata.storeCount);
		            }
		         });
			}

		},
		onDeselectAll : function() {
			var custCluster = $("#customerChainL1Thrscn").val();
			var clustCustArr = custCluster.split(",");
			var arrToPush = [];
						
			if( custCluster == "" ){
				$( "#storecount" ).val( "0" );
				return false;
			}
			
			for (var i = 0; i < clustCustArr.length; i++) {
				
				if(!clustCustArr[i].includes(":")) {
					arrToPush.push(clustCustArr[i]);
				}
			}
			
			var custselVals = [];
			var strData;
			var selectedOptions = $('#cust-chain-store option:selected');
			$.ajax({
		        type: "post",
		        url: "getStoreDataOnClus.htm",
		        contentType: "application/json; charset=utf-8",
		        dataType: "json",
		        beforeSend: function() {
		            ajaxLoader(spinnerWidth, spinnerHeight);
		        },
		       
		        data: JSON.stringify({"cluster" : $( "#cluster" ).val(), "account" : $( "#customerChainL1Thrscn" ).val() , "launchId" : $("#dynamicLaunchId").val(), "IscustomstoreformatChecked" : $('#custmsel').prop('checked') }),  //Sarin Changes - Added last parameter Q1Sprint Feb2021
		        success: function (clssdata) {
		        	 $('.loader').hide();
		        			          
		        	
		             $('#storecount').val(clssdata.storeCount);
		        	}
		     });
		}

	});
}


function multiSelectionForCustStore() {
	$('#cust-chain-cst-store').multiselect({
		includeSelectAllOption : true,
		numberDisplayed : 2,
		/* buttonWidth: '100px', */
		nonSelectedText : 'Select customer store format',
		selectAllText : 'Select customer store format',
		onChange : function(option, checked, select) {
			// console.log(option);
			var custCluster = $("#customerChainL1Thrscn").val();
			var clustCustArr = custCluster.split(",");
			var arrToPush = [];
			for (var i = 0; i < clustCustArr.length; i++) {
				
				if(!clustCustArr[i].includes(":")) {
					arrToPush.push(clustCustArr[i]);
				}
			}
			
			var strData;
			var selectedOptions = $('#cust-chain-cst-store option:selected'),
				rawString = "";
			if (selectedOptions.length > 0) {
				for (var i = 0; i < selectedOptions.length; i++) {
					rawString += selectedOptions[i].value+'~';
				}

				strData = rawString.slice(0, -1);

				$.ajax({
		            type: "post",
		            url: "getStoreDataOnStore.htm",
		            contentType: "application/json; charset=utf-8",
		            dataType: "json",
		            data: JSON.stringify( { "custStoreFormat": strData, "storeFormat": "", "accountList": $( "#customerChainL1Thrscn" ).val(), "cluster" : $( "#cluster" ).val(), "launchId" : $("#dynamicLaunchId").val(), "IscustomstoreformatChecked" : $('#custmsel').prop('checked')  } ),  //Sarin Changes - Added last parameter Q1Sprint Feb2021
		            beforeSend: function() {
		                ajaxLoader(spinnerWidth, spinnerHeight);
		            },
		            success: function (strnwdata) {
		            	 $('.loader').hide();
		            	// console.log(strnwdata.storeCount);
		            	 $('#storecount').val(strnwdata.storeCount);
		            }
		         });
			}
			else{
				$.ajax({
			        type: "post",
			        url: "getStoreDataOnClus.htm",
			        contentType: "application/json; charset=utf-8",
			        dataType: "json",
			        beforeSend: function() {
			            ajaxLoader(spinnerWidth, spinnerHeight);
			        },
			       
			        data: JSON.stringify({"cluster" :  $( "#cluster" ).val(), "account" : $( "#customerChainL1Thrscn" ).val() , "launchId" : $("#dynamicLaunchId").val(), "IscustomstoreformatChecked" : $('#custmsel').prop('checked') }),  //Sarin Changes - Added last parameter Q1Sprint Feb2021
			        success: function (clssdata) {
			        	 $('.loader').hide();
			        			          
			        	
			             $('#storecount').val(clssdata.storeCount);
			        	}
			     });
				$( "#storecount" ).val( totalStoreCount );
				 // strData ="";
			}
	
		},
		onSelectAll : function() {
			
			var custCluster = $("#customerChainL1Thrscn").val();
			var clustCustArr = custCluster.split(",");
			var arrToPush = [];
			for (var i = 0; i < clustCustArr.length; i++) {
				
				if(!clustCustArr[i].includes(":")) {
					arrToPush.push(clustCustArr[i]);
				}
			}
			
			var strData;
			var selectedOptions = $('#cust-chain-cst-store option:selected'),
				rawString = "";
			if (selectedOptions.length > 0) {
				for (var i = 0; i < selectedOptions.length; i++) {
					rawString += selectedOptions[i].value+'~';
				}

				strData = rawString.slice(0, -1);

				$.ajax({
		            type: "post",
		            url: "getStoreDataOnStore.htm",
		            contentType: "application/json; charset=utf-8",
		            dataType: "json",
		            data: JSON.stringify( { "custStoreFormat": strData, "storeFormat": "", "accountList": $( "#customerChainL1Thrscn" ).val(), "cluster" : $( "#cluster" ).val(), "launchId" : $("#dynamicLaunchId").val(), "IscustomstoreformatChecked" : $('#custmsel').prop('checked')  } ),  //Sarin Changes - Added last parameter Q1Sprint Feb2021
		            beforeSend: function() {
		                ajaxLoader(spinnerWidth, spinnerHeight);
		            },
		            success: function (strnwdata) {
		            	 $('.loader').hide();
		            	// console.log(strnwdata.storeCount);
		            	 $('#storecount').val(strnwdata.storeCount);
		            }
		         });
			}

		},
		onDeselectAll : function() {
			var custCluster = $("#customerChainL1Thrscn").val();
			var clustCustArr = custCluster.split(",");
			var arrToPush = [];
			for (var i = 0; i < clustCustArr.length; i++) {
				
				if(!clustCustArr[i].includes(":")) {
					arrToPush.push(clustCustArr[i]);
				}
			}
			
			var strData;
			var selectedOptions = $('#cust-chain-cst-store option:selected'),
				rawString = "";
			if (selectedOptions.length > 0) {
				for (var i = 0; i < selectedOptions.length; i++) {
					rawString += selectedOptions[i].value+'~';
				}

				strData = rawString.slice(0, -1);
		}
			$.ajax({
		        type: "post",
		        url: "getStoreDataOnClus.htm",
		        contentType: "application/json; charset=utf-8",
		        dataType: "json",
		        beforeSend: function() {
		            ajaxLoader(spinnerWidth, spinnerHeight);
		        },
		       
		        data: JSON.stringify({"cluster" :  $( "#cluster" ).val(), "account" : $( "#customerChainL1Thrscn" ).val() , "launchId" : $("#dynamicLaunchId").val(), "IscustomstoreformatChecked" : $('#custmsel').prop('checked') }),  //Sarin Changes - Added last parameter Q1Sprint Feb2021
		        success: function (clssdata) {
		        	 $('.loader').hide();
		        			          
		        	
		             $('#storecount').val(clssdata.storeCount);
		        	}
		     });
		}
	});
}



function getlaunchNatureDetails(thisObj) {

		
	var nat = $('#launch_nature').val();
	if(nat == "Regional"){
		
		$('.geodiv').css('display', 'block');
		$('.custchain').css('display', 'none');
		$('.inputdiv').css('display', 'none');
		$('.formatdiv').css('display', 'none');
		$('.towndiv').css('display', 'none');
	}
	else if(nat == "Customer Specific"){
		$('.custchain').css('display', 'block');
		$('.geodiv').css('display', 'none');
		$('.inputdiv').css('display', 'none');
		$('.formatdiv').css('display', 'none');
		$('.towndiv').css('display', 'none');
	}
	else if(nat == "Others"){
		$('.inputdiv').css('display', 'block');
		$('.custchain').css('display', 'none');
		$('.geodiv').css('display', 'none');
		$('.formatdiv').css('display', 'none');
		$('.towndiv').css('display', 'none');
	}
	else if(nat == "Format Specific"){
		$('.formatdiv').css('display', 'block');
		$('.custchain').css('display', 'none');
		$('.geodiv').css('display', 'none');
		$('.inputdiv').css('display', 'none');
		$('.towndiv').css('display', 'none');
	}
	else if(nat == "Town Specific"){
		$('.formatdiv').css('display', 'none');
		$('.custchain').css('display', 'none');
		$('.geodiv').css('display', 'none');
		$('.inputdiv').css('display', 'none');
		$('.towndiv').css('display', 'block');
	}
	
	else{
		$('.custchain').css('display', 'none');
		$('.geodiv').css('display', 'none');
		$('.inputdiv').css('display', 'none');
		$('.formatdiv').css('display', 'none');
		$('.towndiv').css('display', 'none');
	}
}


function salecat(val){
	
	$.ajax({
            type: "POST",
            url: "getPsaCategory.htm?salesCategory=" + val.value,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            beforeSend: function() {
                ajaxLoader(spinnerWidth, spinnerHeight);
            },
            success: function (psacat) {
            	 $('.loader').hide();
                var psadata = psacat;
                $(val.parentNode.nextSibling.firstElementChild).empty();
                var optionJsonpsa =  "<option value='Select PSA Category'>Select PSA Category</option>";
                $.each(psadata, function (a, psa) {
                   
                    optionJsonpsa += "<option value='" + psa.psaCategory + "'>" + psa.psaCategory + "</option>";
					
                });
                $(val.parentNode.nextSibling.firstElementChild).append(optionJsonpsa);
            }
	
        });
}


function brandOnPsacat(branval){
	var branvalStr = encodeURIComponent(branval.value);
	var salesCatStr = encodeURIComponent($(branval).closest('tr').find('select.salescat').val());
	$.ajax({
            type: "POST",
            url: "getBrandOnPsaCat.htm?psaCategory="+branvalStr+"&salesCategory="+salesCatStr,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            beforeSend: function() {
                ajaxLoader(spinnerWidth, spinnerHeight);
            },
            success: function (bran) {
            	 $('.loader').hide();
                var brand = bran;
                $(branval.parentNode.nextSibling.firstElementChild).empty();
                var optionJsonbrand = "<option value='Select Brand'>Select Brand</option>";
               
                $.each(brand, function (a, br) {
                   optionJsonbrand += "<option value='" + br.brand + "'>" + br.brand + "</option>";
				});
                $(branval.parentNode.nextSibling.firstElementChild).append(optionJsonbrand);
            }
	});
}

function validateFloatKeyPress(el, evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    var number = el.value.split('.');
    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    // just one dot
    if(number.length>1 && charCode == 46){
         return false;
    }
    // get the carat position
    var caratPos = getSelectionStart(el);
    var dotPos = el.value.indexOf(".");
    if( caratPos > dotPos && dotPos>-1 && (number[1].length > 1)){
        return false;
    }
    return true;
}

// thanks: http://javascript.nwbox.com/cursor_position/
function getSelectionStart(o) {
	if (o.createTextRange) {
		var r = document.selection.createRange().duplicate()
		r.moveEnd('character', o.value.length)
		if (r.text == '') return o.value.length
		return o.value.lastIndexOf(r.text)
	} else return o.selectionStart
}

function downloadLaunchBasepackTemplate() {
	window.location.assign("downloadLaunchBasepackTemplate.htm");
}

function downloadFinalPlan() {
	var launchId = $("#dynamicLaunchId").val();
	window.location.assign(launchId+"/downloadFinalBuildUpTemplate.htm");
}

function downloadLaunchStrClusterTemplate() {
	var regionCluster = $("#cluster").val();
	var launchId = $("#dynamicLaunchId").val();    //bharati added sprint-7
	var customerChainL1Thrscn = $("#customerChainL1Thrscn").val();
	// var storeFormat = $("#custstrfrmt").val();
	//bharati added for to get clusters values in sprint-7 US-7 
	var custselValsForStore = [];
	var selStoreFormat;
			var selectedOptions = $('#cust-chain-store option:selected');
			
			if (selectedOptions.length >= 0) {
				
				 $(selectedOptions).each(function(index, selectedOptions){
					 custselValsForStore.push([$(this).val()]);
			        });

				var addsinqt = custselValsForStore.toString();
				 selStoreFormat = '\'' + addsinqt.split(',').join('\',\'') + '\'';
				}
				
	var customerSelForStore = [];
	var CustStoreFormat;
			var selectedOptions = $('#cust-chain-cst-store option:selected');
			
			if (selectedOptions.length >= 0) {
				
				 $(selectedOptions).each(function(index, selectedOptions){
					 customerSelForStore.push([$(this).val()]);
			        });

				var addsinqt = customerSelForStore.toString();
				 CustStoreFormat = '\'' + addsinqt.split(',').join('\',\'') + '\'';
				}
	//bharati code end here
	var customerStoreFormat = $("#cust-chain-cst-store").val();
	var storeFormat = document.getElementById("cust-chain-store").nextElementSibling.firstElementChild.getAttribute('title');
	var temp = 0;
	if(regionCluster.trim() == "") {
		ezBSAlert({
			messageText : "Cluster can not be blank",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	} else {
		temp++;
	}
	if(customerChainL1Thrscn.trim() == "") {
		ezBSAlert({
			messageText : "Account can not be blank",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	} else {
		temp++;
	}
	if(document.getElementById("custmsel").checked == true) {
		if((storeFormat.trim() == "") && (customerStoreFormat.trim() == "")) {
			ezBSAlert({
				messageText : "please choose atleast 1 redio button",
				alertType : "info"
			}).done(function(e) {
				// console.log('hello');
			});
			return false;
		} else {
			temp++;
		}	
	} else {
		temp++;
	}
	
	if(document.getElementById("radio1").checked == false) {
		storeFormat = "";
	}
	
	if(temp >= 3) {
		$.ajax({
		    url: 'downloadClusterTemplateforStoreformat.htm',
		    type: 'post',
		    contentType: 'application/json',
		    dataType : 'JSON',
		    beforeSend: function() {
                ajaxLoader(spinnerWidth, spinnerHeight);
            },
		    data : JSON.stringify( 
			{ 
				"regionCluster": regionCluster, 
				"l1l2Cluster": customerChainL1Thrscn,
				"storeFormat" : storeFormat,
				"custStoreFormat" : CustStoreFormat,
				"LaunchId" : launchId,                             //bharati added for sprint-7 US-7
				"SelStoreFormat": selStoreFormat                   //bharati added for sprint-7 US-7
				                     
				
			}),
		    success: function( data, textStatus, jQxhr ){
		    	 $('.loader').hide();
		    	window.open(data.FileToDownload+"/downloadFileTemplate.htm");
		    },
		    error: function( jqXhr, textStatus, errorThrown ){
		        console.log( errorThrown );
		    }
		});
	} else {
		$('#errorblockUpload').show();
	}
	
}

function downloadLaunchClusterTemplate() {
	var regionCluster = $("#cluster").val();
	var launchId = $("#dynamicLaunchId").val();    //bharati added sprint-7
	var customerChainL1Thrscn = $("#customerChainL1Thrscn").val();
	//var storeFormat = $("#custstrfrmt").val();
	// var customerStoreFormat = $("#cust-chain-cst-store").val();
	//bharati added for to got store format values in sprint-7 US-7 
	var custselVals = [];
	var selStoreFormat;
			var selectedOptions = $('#cust-chain-store option:selected');
			
			if (selectedOptions.length >= 0) {
				
				 $(selectedOptions).each(function(index, selectedOptions){
					 custselVals.push([$(this).val()]);
			        });

				var addsinqt = custselVals.toString();
				 selStoreFormat = '\'' + addsinqt.split(',').join('\',\'') + '\'';
				}
	//console.log(selStoreFormat);
	//bharati code end here
	var custStoreFormat =[]; 
	var storeFormat=[];
	var strfrmt='';
	var cstmstrfrmt='';
	var temp = 0;
	if(regionCluster.trim() == "") {
		ezBSAlert({
			messageText : "Cluster can not be blank",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
		
	} else {
		temp++;
	}
	if(customerChainL1Thrscn.trim() == "") {
		ezBSAlert({
			messageText : "Account can not be blank",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	} else {
		temp++;
	}
	// if(document.getElementById("custmsel").checked == true) {
		
		
		if(document.getElementById('custmsel').checked){
			if(document.getElementById("radio1").checked) {
				// strfrmt =$('#custstrfrmt').val();
				
				$('.switch-dynamic-first-custstrfrmt').find('ul li').find('label.checkbox input:checked').each(function (i) {
								
								if($(this).val() == 'multiselect-all'){

								}
								else{				
									storeFormat.push($(this).val());
				                    
								}
				
				});
				strfrmt = storeFormat.toString();
				if(strfrmt == ''){
					ezBSAlert({
						messageText : "Please select value from drop down",
						alertType : "info"
					}).done(function(e) {
						// console.log('hello');
					});
					return false;
				}
				
			} else if(document.getElementById("radio2").checked) {
				// cstmstrfrmt = $('#cust-chain-cst-store').val();
				
				$('.switch-dynamic-first-radio-cststr').find('ul li').find('label.checkbox input:checked').each(function (i) {
					if($(this).val() == 'multiselect-all'){

					}
					else{		
						custStoreFormat.push($(this).val());
					}
				 });

				 cstmstrfrmt = custStoreFormat.toString();
					// console.log(x);
				if(cstmstrfrmt == ''){
					ezBSAlert({
						messageText : "Please select value from drop down",
						alertType : "info"
					}).done(function(e) {
						// console.log('hello');
					});
					return false;
				}
			} 
		
		if((storeFormat == "") && (cstmstrfrmt == "")) {
			ezBSAlert({
				messageText : "Please choose atleast 1 radio button",
				alertType : "info"
			}).done(function(e) {
				// console.log('hello');
			});
			return false;
		} else {
			temp++;
		}	
	} else {
		temp++;
	}
	
	if(document.getElementById("radio2").checked == false) {
		cstmstrfrmt = "";
	}
	if(temp >= 3) {
		$.ajax({
		    url: 'downloadClusterTempforCustStoreformat.htm',
		    type: 'post',
		    contentType: 'application/json',
		    dataType : 'JSON',
		    beforeSend: function() {
                ajaxLoader(spinnerWidth, spinnerHeight);
            },
		    data : JSON.stringify( 
			{ 
				"regionCluster": regionCluster, 
				"l1l2Cluster": customerChainL1Thrscn,
				"storeFormat" : "",
				"custStoreFormat" : cstmstrfrmt,
				"LaunchId" : launchId,                             //bharati added for sprint-7 US-7
				"SelStoreFormat": selStoreFormat                      //bharati added for sprint-7 US-7
			}),
		    success: function( data, textStatus, jQxhr ){
		    	 $('.loader').hide();
		    	window.open(data.FileToDownload+"/downloadFileTemplate.htm");
		    },
		    error: function( jqXhr, textStatus, errorThrown ){
		        console.log( errorThrown );
		    }
		});
	} else {
		$('#errorblockUpload').show();
	}
	
}


function downloadLaunchSellInTemplate() {
	var launchId = $("#dynamicLaunchId").val();
	
	$.ajax({
	    url: 'downloadSellInTemplate.htm',
	    type: 'post',
	    contentType: 'application/json',
	    dataType : 'JSON',
	    beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
	    data : JSON.stringify( { "launchId": launchId } ),
	    success: function( data, textStatus, jQxhr ){
	    	 $('.loader').hide();
	    	window.location.assign(data.FileToDownload+"/downloadFileTemplate.htm");
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	       // console.log( errorThrown );
	    }
	});
}

function downloadLaunchVisiPlanTemplate() {
	var launchVisiPlanArr = [];
	var launchId = $("#dynamicLaunchId").val();
	var rowCount = $('#visiplan tbody').find('tr');
	var chk = document.getElementsByClassName("checkvissi");
	var temp = 0;
	
		for (var i = 0; i < rowCount.length; i++) {
		
				var account = rowCount[i].children[1].children[0].value;
				var format = rowCount[i].children[2].children[0].value;
				var storeFormat = rowCount[i].children[3].children[0].value;
				var storesPlanned = rowCount[i].children[4].children[0].value;
				var visiPlanned1 = "";
				var visiPlanned2 = "";
				var visiPlanned3 = "";
				var visiPlanned4 = "";
				var visiPlanned5 = "";
				
				var face1 = $( $( rowCount[i] ).find( ".face-self" )[0] ).val(),
				face2 = $( $( rowCount[i] ).find( ".face-self" )[1] ).val(),
				face3 = $( $( rowCount[i] ).find( ".face-self" )[2] ).val(),
				face4 = $( $( rowCount[i] ).find( ".face-self" )[3] ).val(),
				face5 = $( $( rowCount[i] ).find( ".face-self" )[4] ).val(),
				depth1 = $( $( rowCount[i] ).find( ".depth-self" )[0] ).val(),
				depth2 = $( $( rowCount[i] ).find( ".depth-self" )[1] ).val(),
				depth3 = $( $( rowCount[i] ).find( ".depth-self" )[2] ).val(),
				depth4 = $( $( rowCount[i] ).find( ".depth-self" )[3] ).val(),
				depth5 = $( $( rowCount[i] ).find( ".depth-self" )[4] ).val();
				
				
				var jsonData = {
						"account" : account,
				        "format" : format,
				        "storesAvailable" : parseInt(storeFormat),
				        "storesPlanned" : parseInt(storesPlanned),
				        "visiAsset1" : visiPlanned1,
				        "visiAsset2" : visiPlanned2,
				        "visiAsset3" : visiPlanned3,
				        "visiAsset4" : visiPlanned4,
				        "visiAsset5" : visiPlanned5,
				        "facingsPerShelf1" : face1,
						"depthPerShelf1"   : depth1,
						"facingsPerShelf2" : face2,
						"depthPerShelf2" 	 : depth2,
						"facingsPerShelf3" : face3,
						"depthPerShelf3"   : depth3,
						"facingsPerShelf4" : face4,
						"depthPerShelf4"   : depth4,
						"facingsPerShelf5" : face5,
						"depthPerShelf5"  : depth5      	
				}
				launchVisiPlanArr.push(jsonData);			
		}
	
	$.ajax({
	    url: 'downloadVisiPlanTemplate.htm',
	    type: 'post',
	    contentType: 'application/json',
	    dataType : 'JSON',
	    beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
	    data : JSON.stringify( {  "launchId": launchId, "listOfVisiPlans": launchVisiPlanArr } ),
	    success: function( data, textStatus, jQxhr ){
	    	 $('.loader').hide();
	    	window.open(data.FileToDownload+"/downloadFileTemplate.htm");
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	       // console.log( errorThrown );
	    }
	});
}

function maxLengthCheck(object) {
	if (object.value.length > object.maxLength)
		object.value = object.value.slice(0, object.maxLength)
 
}

// Script written by Anshuman to save launch details
function saveLaunchDetails() {
	var launchName = $("#launch_name").val().trim();
	var launchMonth = $("#startdate").val().trim();
	var mocMonth = $("#mocMonth").val().trim();
	var launchNature = $("#launch_nature").val();
	var launchCat = $("#launch_category").val().trim();
	var launchBus = $("#launch_business").val().trim();
	var launchClass = $("#launch_classifiction").val();
	var cutspec = document.getElementById("customerChainL1").nextSibling.children[0].getAttribute("title");
	var formspe = $('#format_input').val();
	var townspe = $('#town_input').val();
	var othrspe = $('#launch_input').val();
	var geo = $('#editGeography').val(); 
	var launchId = $("#dynamicLaunchId").val();
	var launchNature2 = "";
	
	if($('#launch_nature').val() == 'Customer Specific'){
		launchNature2 = cutspec;
		}
	else if($('#launch_nature').val() == 'Format Specific'){
		launchNature2 = formspe;
		}
	else if($('#launch_nature').val() == 'Regional'){
		launchNature2 = geo;
	}
	else if($('#launch_nature').val() == 'Town Specific'){
		launchNature2 = townspe;
		}
	else if($('#launch_nature').val() == 'Others'){
		launchNature2 = othrspe;
		}
	if(launchName == "" || launchName == null) {
		/*
		 * $('#launch_name').css({
		 * 
		 * "border" : "1px solid red",
		 * 
		 * "background" : "#FFCECE"
		 * 
		 * });
		 */
		ezBSAlert({
			messageText : "Please enter launch name",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	} else if(launchMonth == "" || launchMonth == null) {
		ezBSAlert({
			messageText : "Please select launch date",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	} else if(mocMonth == "" || mocMonth == null) {
		ezBSAlert({
			messageText : "Please select launch MOC",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	} else if(launchNature == "") {
		
		ezBSAlert({
			messageText : "Please select launch nature",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	} else if(launchBus == "" || launchBus == null) {
		
		ezBSAlert({
			messageText : "Estimated Launch business case can not be blank",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	}  else if(launchCat == "" || launchCat == null) {
		
		ezBSAlert({
			messageText : "Category Size can not be blank",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	} else if(launchClass == "" || launchClass == null) {
		
		ezBSAlert({
			messageText : "Launch Classification can not be blank",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	} else {
		if(launchNature == "General") {
			launchNature2 = "";
		}
		else if(launchNature == "Others") {
			var launchInput = $("#launch_input").val().trim();
			if(launchInput == "" || launchBus == null) {
				
				ezBSAlert({
					messageText : "Launch Input can not be blank",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			} 
		}
		else if(launchNature != ""){
		
		
			if(launchNature == 'Format Specific'){
				if(formspe == ''){
					ezBSAlert({
						messageText : "Please select format",
						alertType : "info"
					}).done(function(e) {
						// console.log('hello');
					});
					return false;
				}
			}
			else if(launchNature == 'Town Specific'){
				if(townspe == ''){
					ezBSAlert({
						messageText : "Please select town",
						alertType : "info"
					}).done(function(e) {
						// console.log('hello');
					});
					return false;
				}
			}
		}
		
		var values = $('.multiselect').serialize();
	   // console.log (values);
		
		$.ajax({
		    url: 'saveLaunchDetails.htm',
		    dataType: 'json',
		    type: 'post',
		    contentType: 'application/json',
		    data: JSON.stringify( {
		    	"launchId" : launchId,
		    	"launchName" : launchName,
		    	"launchDate" : launchMonth,
		    	"launchNature" : launchNature,
		    	"launchNature2" : launchNature2,
		    	"launchBusinessCase" : launchBus,
		    	"categorySize" : launchCat,
		    	"classification" : launchClass,
		    	"launchMoc" : mocMonth
		    } ),
		    processData: false,
		    beforeSend: function() {
                ajaxLoader(spinnerWidth, spinnerHeight);
            },
		    success: function( data, textStatus, jQxhr ){
		    	 $('.loader').hide();
		    	 if(data.Error != undefined) {
		    		 ezBSAlert({
							messageText : "Launch Name already exists. Please select unique name",
							alertType : "info"
						}).done(function(e) {
							// console.log('hello');
						});
						return false;

		    	 } else {
			    	$("#dynamicLaunchId").val(data.launchId);
			        $('#response pre').html( JSON.stringify( data ) );
			        $("#launchBasepackTab").click();
		    	 }
		    },
		    error: function( jqXhr, textStatus, errorThrown ){
		        console.log( errorThrown );
		    }
		});
		
		$.ajax({
            type: "POST",
            url: "getSalesCatOnBasepack.htm",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            beforeSend: function() {
                ajaxLoader(spinnerWidth, spinnerHeight);
            },
            success: function (salescat) {
            	 $('.loader').hide();
                var saleCat = salescat.responseData.SalesCategory;
                var classfi = salescat.responseData.BpClassification;
				optionJsonLaunch = "<option value='Select Sales Category'>Select Sales Category</option>";
                $.each(saleCat, function (i, item) {
                   // console.log(item);
                    // $("#Suppliers").append("<option value='" + item.Id + "'>"
					// + item.SupplierName + "</option>");
                   
						optionJsonLaunch += "<option value='" + item + "'>" + item + "</option>";
						
					
                });
                $('.salescat').append(optionJsonLaunch);
                
                
                optionJsonclassfi = "<option value='Select Classification'>Select Classification</option>";
                $.each(classfi, function (i, clitem) {
                   // console.log(item);
                    // $("#Suppliers").append("<option value='" + item.Id + "'>"
					// + item.SupplierName + "</option>");
                   
                	optionJsonclassfi += "<option value='" + clitem + "'>" + clitem + "</option>";
						
					
                });
                $('.classfDrop').append(optionJsonclassfi);
            }
            
        });
	}
}

// save for 2nd screen
function saveBasepacks() {
	
	var chk = document.getElementsByClassName("checklaunch");
	var launchBasePacksArr = [];
	var temp = 0;
	var is_checked_one = false;
	var is_check_not_choose = false;
	for (var i = 0; i < chk.length; i++) {
		var chkObj = chk[i];
		
		if(chkObj.checked) {
			is_checked_one = true;
			// if(temp == 1){
			var row = chkObj.parentElement.parentElement;
			var salesCat = row.children[1].children[0].value;
			var psaCat = row.children[2].children[0].value;
			var brand = row.children[3].children[0].value;
			var baseCode = row.children[4].children[0].value;
					
			var baseDisc = row.children[5].children[0].value;
			var mrp = row.children[6].children[0].value;
			var tur = row.children[7].children[0].value;
			var gsv = row.children[8].children[0].value;
			var clvConfig = row.children[9].children[0].value;
			if(clvConfig == '0') {
				alert("CLD can not be zero");
				return false;
			}
			var gram = row.children[10].children[0].value;
			var classification = row.children[11].children[0].value;
			var launchId = $("#dynamicLaunchId").val();
			if(salesCat == "Select Sales Category") {
				ezBSAlert({
					messageText : "Please select Sales Category",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
			if(psaCat == "Select PSA Category") {
				ezBSAlert({
					messageText : "Please select psa Category",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
			if(brand == "Select Brand") {
				ezBSAlert({
					messageText : "Please select brand",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
			if(baseCode == "") {
				ezBSAlert({
					messageText : "Please enter basepack code",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
			if(baseDisc == "") {
				ezBSAlert({
					messageText : "Please enter basepack  description",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
			if(mrp == "") {
				ezBSAlert({
					messageText : "Please enter mrp",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
			if(tur == "") {
				ezBSAlert({
					messageText : "Please enter tur",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
			if(gsv == "") {
				ezBSAlert({
					messageText : "Please enter gsv",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
			if(clvConfig == "") {
				ezBSAlert({
					messageText : "Please enter clv config",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
			if(gram == "") {
				ezBSAlert({
					messageText : "Please enter grammmage",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
			if(classification == "Select Classification") {
				ezBSAlert({
					messageText : "Please select classification",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
			else{
			var jsonData = {
					"psaCategory":psaCat,
		        	"brand" : brand,
		        	"code" : baseCode,
		        	"description" : baseDisc,
		        	"mrp" : mrp,
		        	"tur" : tur,
		        	"gsv" : gsv,
		        	"cldConfig" : clvConfig,
		        	"grammage" : gram,
		        	"classification" : classification,
		        	"salesCategory" : salesCat
			}
			launchBasePacksArr.push(jsonData);
			}
			temp++;
		
	} else {
		var row = chkObj.parentElement.parentElement;
		var salesCat = row.children[1].children[0].value;
		if( salesCat != "Select Sales Category" ){
			is_check_not_choose = true;
		}
	}
		
	}
	
	if ( !is_checked_one ) {
		ezBSAlert({
			messageText : "Please select basepack",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	}
	
	$.ajax({
	    url: 'saveLaunchBasepack.htm',
	    dataType: 'json',
	    type: 'post',
	    contentType: 'application/json',
	    data: JSON.stringify( { "launchId": launchId, "listBasePacks": launchBasePacksArr } ),
	    processData: false,
	    beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
	    success: function( data, textStatus, jQxhr ){
	    	 $('.loader').hide();
	    	 $("#launchStoresTab").click();
	    	 $('#errorblockUpload').hide();
	    	 getlaunchStores();	 
	    	 $("#custmsel").prop("checked", false);
	    	 $( '#radio1', '#radio2' ).prop("checked", false);
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	        console.log( errorThrown );
	    }
	});
}
function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}
  
function getlaunchStores() {
	var launchId = $("#dynamicLaunchId").val();
	$.ajax({
	    url: 'getLaunchStores.htm?launchId='+launchId,
	    dataType: 'json',
	    type: 'get',
	    contentType: 'application/json',
	    processData: false,
	    beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
            tirggerAccountCall = false;
        },
	    success: function( data, textStatus, jQxhr ){
	    	 $('.loader').hide();
	    	// console.log(data);
	    	// $('#cluster').val(data.regionCluster);
	    	
	    	$('#storecount').val(data.storeCount);
	    	// $('#customerChainL1Thrscn').val(data.customerData);
	    	precheckCustomer(data.customerData, true);
	    	if( typeof data.launchNature != 'undefined' && data.launchNature == "Regional" ){
	    		precheck3rdScreenClus(data.launchNature2, true );
	    	} else {
	    		var spans = $( "#comboTreeclusterDropDownContainer .comboTreeItemTitle" );
	    		spans.find( "input" ).prop( "checked", true );
	    		spans.find( "input" ).trigger( 'change' );
	    		$( $( "#comboTreeclusterDropDownContainer li .comboTreeItemTitle input:checked" )[0]).trigger( "change" );
	    	}
	    	
	    	/* $('#custstrfrmt').val(data.customerStoreFormat); */
	    	var lnchfrmt = data.customerStoreFormat;
	    		
	     	 // $('#selcustfrmt').empty();
	            var optionJsonlnsrt = ""; /*
											 * = "<option value=''>SELECT
											 * CUSTOMER STORE FORMAT</option>";
											 */
	            $('#cust-chain-cst-store').empty();
				$('#cust-chain-cst-store').multiselect("destroy");
	            $.each(lnchfrmt, function (a, br) {
	               // console.log(br);
	                // $("#Suppliers").append("<option value='" + item.Id + "'>"
	     			// + item.SupplierName + "</option>");
	               
	                optionJsonlnsrt += "<option value='" + br + "'>" + ( br == '' ? '&nbsp;' : br ) + "</option>";
	     		
	            });
	            
	            $('#cust-chain-cst-store').append(optionJsonlnsrt);
	            multiSelectionForCustStore();
            
	        var selcustfrmt = data.launchFormat;
            
           // $('#custstrfrmt').empty();
            
            var optionJsonsrt; /* = "<option value=''>SELECT STORE FORMAT</option>"; */
            $('#cust-chain-store').empty();
			$('#cust-chain-store').multiselect("destroy");
            $.each(selcustfrmt, function (a, br) {
               // console.log(br);
                // $("#Suppliers").append("<option value='" + item.Id + "'>"
				// + item.SupplierName + "</option>");
               
                optionJsonsrt += "<option value='" + br + "'>" + ( br == '' ? '&nbsp;' : br ) + "</option>"
			
            });
            $('#cust-chain-store').append(optionJsonsrt);
            multiSelectionForCustChainStore();
            
            $( '#radio1, #radio2' ).attr( "disabled", true );
            $( '#cust-chain-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
            $( '#cust-chain-cst-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
            
            
            tirggerAccountCall = true;
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	        console.log( errorThrown );
	    }
	 
	});
}

function getlaunchStoresEdit() {
	var launchId = $("#dynamicLaunchId").val();
	$('#storecount').val(data.totalStoresToLaunch);
}

// save for 3rd screen
function saveLunchstrs() {
	
	$('#errorblockUpload').hide();
	var launchId = $("#dynamicLaunchId").val();
	var launchstrArr = [];
	var temp = 0;
	var clust = $('#cluster').val();
	var account = $('#customerChainL1Thrscn').val();
	var strfrmt='';
	var cstmstrfrmt='';
	var strcount = $('#storecount').val();
	var customstoresel = $('#custmsel').prop('checked') ?  'Yes' : 'No';  //Sarin Changes - Added for Custome Store Selection - Q1Sprint Feb2021
	
	if(document.getElementById('custmsel').checked){
		if(document.getElementById("radio1").checked) {
			// strfrmt =$('#custstrfrmt').val();
			var x =[]; 
			$('.switch-dynamic-first-custstrfrmt').find('ul li').find('label.checkbox input:checked').each(function (i) {
				
				if($(this).val() == 'multiselect-all'){

				}
				else{				
					x.push($(this).val());
				}
			 });
			var strfrmt = x.toString();
			if(strfrmt == ''){
				ezBSAlert({
					messageText : "Please select value from drop down",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
			
		} else if(document.getElementById("radio2").checked) {
			// cstmstrfrmt = $('#cust-chain-cst-store').val();
			var y =[]; 
			$('.switch-dynamic-first-radio-cststr').find('ul li').find('label.checkbox input:checked').each(function (i) {
				if($(this).val() == 'multiselect-all'){

				}
				else{		
				y.push($(this).val());
				}
			 });

			var cstmstrfrmt = y.toString();
				// console.log(x);
			if(cstmstrfrmt == ''){
				ezBSAlert({
					messageText : "Please select value from drop down",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
		} else {
			ezBSAlert({
				messageText : "Please select format",
				alertType : "info"
			}).done(function(e) {
				// console.log('hello');
			});
			return false;
		}
	}
	if(strcount == '0' || strcount == ''){
		ezBSAlert({
			messageText : "Total number of Stores is 0 for selected account and cluster combination",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	}
	var jsonData = {
			"launchId":launchId,
        	"clusterString" : clust,
        	"accountString" : account,
        	"storeFormat" : strfrmt,
        	"customerStoreFormat" : cstmstrfrmt,
        	"totalStoresToLaunch" : strcount,
        	"launchPlanned" : "Yes",
        	"includeAllStoreFormats" : customstoresel,  //Sarin Changes - Added for custom store selection Q1Sprint Feb2021
        	
	}
	
	
	launchstrArr.push(jsonData);
	
	$.ajax({
	    url: 'saveLaunchClustersAndAccounts.htm',
	    dataType: 'json',
	    type: 'post',
	    contentType: 'application/json',
	    data: JSON.stringify(jsonData),
	    processData: false,
	    beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
	    success: function( data, textStatus, jQxhr ){
	    	 
	    	 $("#launchSellInTab").click();
	    	 setTimeout(function(){ 
	    		 getSellInData();
	    		 
	    	 }, 1200);
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	        console.log( errorThrown );
	    }
	});
}


function getSellInData() {
	var launchId = $("#dynamicLaunchId").val();
	$.ajax({
	    url: 'getLaunchSellIn.htm?launchId='+launchId,
	    dataType: 'json',
	    type: 'get',
	    contentType: 'application/json',
	    processData: false,
	    beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
	    success: function( sellInData, textStatus, jQxhr ){
	    	
            $('.loader').hide();
	    	// $("#launchSellInTab").click();
	    	// $('.l1chain').val(data.l1chain);
	    	//console.log(data);
	    	// 4th screen table
	    	totalBasepacksCreated = sellInData.responseData.basepacksCreated.length;
	    	
	    	skuCountSellInPack = sellInData.responseData.basepacksCreated;
	    		    	
            var len = sellInData.responseData.sellInRecords.length;
            var sellInData = sellInData.responseData.sellInRecords;
			var sellrow = "";
			for (var i = 0; i < len; i++) {
				sellrow += "<tr><td><input name='childRatio1' type='text' class='form-control l1chain' onkeypress='return validateFloatKeyPress(this,event);' id='lonech"+i+"' readonly value= '"
					 	+sellInData[i].l2Chain
					 	+"'></td>"
						+"<td><input name='childRatio1' type='text' class='form-control l2chain'onkeypress='return validateFloatKeyPress(this,event);' id='ltwoch"+i+"' readonly value= '" +
						sellInData[i].l1Chain+"'></td>" 
						+"<td><input name='childRatio1' type='text' class='form-control storefrmt' onkeypress='return validateFloatKeyPress(this,event);' id='strfmt"+i+"' readonly value= '" +
						sellInData[i].storeFormat+"'></td>" 
						+ "<td><input name='childRatio1' type='text' class='form-control storesplan' id='strpln"+i+"' readonly value= '" +
						sellInData[i].storesPlanned+"'></td>"
						//+ "<td ><input name='childRatio1' data-field='base_1' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);'></td>" //Commented & Added below By Sarin - Sprint4 Aug2021
						+ "<td ><input name='childRatio1' data-field='base_1' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].SKU1_SELLIN + "'></td>"
						+ "<td ><input name='childRatio1' data-field='base_2' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].SKU2_SELLIN + "'></td>"
						+ "<td><input name='childRatio1' data-field='base_3' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].SKU3_SELLIN + "'></td>"
						+ "<td ><input name='childRatio1' data-field='base_4' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].SKU4_SELLIN + "'></td>"
						+ "<td ><input name='childRatio1' data-field='base_5' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].SKU5_SELLIN + "'></td>"
						+ "<td><input name='childRatio1' data-field='base_6' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].SKU6_SELLIN + "'></td>"
						+ "<td><input name='childRatio1' data-field='base_7' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].SKU7_SELLIN + "'></td>" 
						+ "<td><input name='childRatio1' data-field='base_8' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].SKU8_SELLIN + "'></td>"
						+ "<td><input name='childRatio1' data-field='base_9' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].SKU9_SELLIN + "'></td>"
						+ "<td><input name='childRatio1' data-field='base_10' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].SKU10_SELLIN + "'></td>"
						+ "<td><input name='childRatio1' data-field='base_11' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].SKU11_SELLIN + "'></td>"
						+ "<td><input name='childRatio1' data-field='base_12'  type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].SKU12_SELLIN + "'></td>"
						+ "<td><input name='childRatio1' data-field='base_13' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].SKU13_SELLIN + "'></td>"
						+ "<td><input name='childRatio1' data-field='base_14' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].SKU14_SELLIN + "'></td>"
						+ "<td><input name='childRatio1' data-field='rotations' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].ROTATIONS + "'></td>"
						+ "<td><input name='childRatio1' data-field='upliftone' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].UPLIFT_N1 + "'></td>"
					    + "<td><input name='childRatio1' data-field='uplifttwo' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value='" + sellInData[i].UPLIFT_N2 + "'></td></tr>";
			}
			
			
			if($( "#sellinTable_wrapper" ).length != 0){
				$( "#sellinTable_wrapper" ).replaceWith( '<table id="sellinTable" class="table table-striped table-bordered nowrap order-column row-border smt-dashboardTable table-responsive" style="width:100%"> <thead class="thead-dark"> <tr> <th >L1 Chain</th> <th>L2 Chain</th> <th>Store Format</th> <th>Store Planned</th> <th class="basepackdesc-1">'+( typeof skuCountSellInPack[0] != "undefined" ? skuCountSellInPack[0] : "" )+'</th> <th class="basepackdesc-2">'+( typeof skuCountSellInPack[1] != "undefined" ? skuCountSellInPack[1] : "" )+'</th> <th class="basepackdesc-3">'+( typeof skuCountSellInPack[2] != "undefined" ? skuCountSellInPack[2] : "" )+'</th> <th class="basepackdesc-4">'+( typeof skuCountSellInPack[3] != "undefined" ? skuCountSellInPack[3] : "" )+'</th> <th class="basepackdesc-5">'+( typeof skuCountSellInPack[4] != "undefined" ? skuCountSellInPack[4] : "" )+'</th> <th class="basepackdesc-6">'+( typeof skuCountSellInPack[5] != "undefined" ? skuCountSellInPack[5] : "" )+'</th> <th class="basepackdesc-7">'+( typeof skuCountSellInPack[6] != "undefined" ? skuCountSellInPack[6] : "" )+'</th> <th class="basepackdesc-8">'+( typeof skuCountSellInPack[7] != "undefined" ? skuCountSellInPack[7] : "" )+'</th> <th class="basepackdesc-9">'+( typeof skuCountSellInPack[8] != "undefined" ? skuCountSellInPack[8] : "" )+'</th> <th class="basepackdesc-10">'+( typeof skuCountSellInPack[9] != "undefined" ? skuCountSellInPack[9] : "" )+'</th> <th class="basepackdesc-11">'+( typeof skuCountSellInPack[10] != "undefined" ? skuCountSellInPack[10] : "" )+'</th> <th class="basepackdesc-12">'+( typeof skuCountSellInPack[11] != "undefined" ? skuCountSellInPack[11] : "" )+'</th> <th class="basepackdesc-13">'+( typeof skuCountSellInPack[12] != "undefined" ? skuCountSellInPack[12] : "" )+'</th> <th class="basepackdesc-14">'+( typeof skuCountSellInPack[13] != "undefined" ? skuCountSellInPack[13] : "" )+'</th> <th>Rotations</th> <th>Uplift n+1</th> <th>Uplift n+2</th> </tr></thead> <tbody></tbody> </table>' );
			} else {
				$( "#sellinTable" ).replaceWith( '<table id="sellinTable" class="table table-striped table-bordered nowrap order-column row-border smt-dashboardTable table-responsive" style="width:100%"> <thead class="thead-dark"> <tr> <th >L1 Chain</th> <th>L2 Chain</th> <th>Store Format</th> <th>Store Planned</th> <th class="basepackdesc-1">'+( typeof skuCountSellInPack[0] != "undefined" ? skuCountSellInPack[0] : "" )+'</th> <th class="basepackdesc-2">'+( typeof skuCountSellInPack[1] != "undefined" ? skuCountSellInPack[1] : "" )+'</th> <th class="basepackdesc-3">'+( typeof skuCountSellInPack[2] != "undefined" ? skuCountSellInPack[2] : "" )+'</th> <th class="basepackdesc-4">'+( typeof skuCountSellInPack[3] != "undefined" ? skuCountSellInPack[3] : "" )+'</th> <th class="basepackdesc-5">'+( typeof skuCountSellInPack[4] != "undefined" ? skuCountSellInPack[4] : "" )+'</th> <th class="basepackdesc-6">'+( typeof skuCountSellInPack[5] != "undefined" ? skuCountSellInPack[5] : "" )+'</th> <th class="basepackdesc-7">'+( typeof skuCountSellInPack[6] != "undefined" ? skuCountSellInPack[6] : "" )+'</th> <th class="basepackdesc-8">'+( typeof skuCountSellInPack[7] != "undefined" ? skuCountSellInPack[7] : "" )+'</th> <th class="basepackdesc-9">'+( typeof skuCountSellInPack[8] != "undefined" ? skuCountSellInPack[8] : "" )+'</th> <th class="basepackdesc-10">'+( typeof skuCountSellInPack[9] != "undefined" ? skuCountSellInPack[9] : "" )+'</th> <th class="basepackdesc-11">'+( typeof skuCountSellInPack[10] != "undefined" ? skuCountSellInPack[10] : "" )+'</th> <th class="basepackdesc-12">'+( typeof skuCountSellInPack[11] != "undefined" ? skuCountSellInPack[11] : "" )+'</th> <th class="basepackdesc-13">'+( typeof skuCountSellInPack[12] != "undefined" ? skuCountSellInPack[12] : "" )+'</th> <th class="basepackdesc-14">'+( typeof skuCountSellInPack[13] != "undefined" ? skuCountSellInPack[13] : "" )+'</th> <th>Rotations</th> <th>Uplift n+1</th> <th>Uplift n+2</th> </tr></thead> <tbody></tbody> </table>' );
			}
			
			$('#sellinTable tbody').html(sellrow);
			
			// Destroy the table
	        // Use $("#dailyNews").DataTable().destroy() for DataTables 1.10.x
			
			// $.fn.dataTable.ext.errMode = 'none';
	         $("#sellinTable").dataTable().fnDestroy();
	         setTimeout(function(){
			selloTable = $('#sellinTable').DataTable({
				
				 	scrollY:       "300px",
			        scrollX:        true,
			        "scrollCollapse": true,
			        //"paging":         true,  //Commented & Added below By Sarin - Sprint4 Aug2021
			        "paging":         false, //Added By Sarin - Sprint4 Aug2021
			        "ordering": false,
			        searching: false,
			    	"lengthMenu" : [
						[ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
						[ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ] ],
			        "oLanguage": {
		                  "sSearch": '<i class="icon-search"></i>',
		                  "oPaginate": {
		                      "sNext": "&rarr;",
		                      "sPrevious": "&larr;"
		                  },
		                  "sLengthMenu": "Records per page _MENU_ ",
		                  "sEmptyTable": "No Pending Launch."
		
		              },
			       fixedColumns:   {
			            leftColumns: 4
			        },
			        "columnDefs": [
			            {
			                "targets": [ 4 ],
			                "visible": totalBasepacksCreated >= 1
			            },
			            {
			                "targets": [ 5 ],
			                "visible": totalBasepacksCreated >= 2
			            },
			            {
			                "targets": [ 6 ],
			                "visible": totalBasepacksCreated >= 3
			            },
			            {
			                "targets": [ 7 ],
			                "visible": totalBasepacksCreated >= 4
			            },
			            {
			                "targets": [ 8 ],
			                "visible": totalBasepacksCreated >= 5
			            },
			            {
			                "targets": [ 9 ],
			                "visible": totalBasepacksCreated >= 6
			            },
			            {
			                "targets": [ 10 ],
			                "visible": totalBasepacksCreated >= 7
			            },
			            {
			                "targets": [ 11 ],
			                "visible": totalBasepacksCreated >= 8
			            },
			            {
			                "targets": [ 12 ],
			                "visible": totalBasepacksCreated >= 9
			            },
			            {
			                "targets": [ 13 ],
			                "visible": totalBasepacksCreated >= 10
			            },
			            {
			                "targets": [ 14 ],
			                "visible": totalBasepacksCreated >= 11
			            },
			            {
			                "targets": [ 15 ],
			                "visible": totalBasepacksCreated >= 12
			            },
			            {
			                "targets": [ 16 ],
			                "visible": totalBasepacksCreated >= 13
			            },
			            {
			                "targets": [ 17 ],
			                "visible": totalBasepacksCreated >= 14
			            }
			        ]
 	    	    });
			
			 $('#sellinTable_info').css({
                 'text-align': 'left',
                 
             });
             $('#sellinTable_info').css({
                 'color': '#29290a'
             });
             $($('#sellinTable_info .row')[0]).after(
                     $(".summary-text"));

             $($('#sellinTable_info .row')[0]).css({
                 'float': 'right'
             });
             $(
                 $($('#sellinTable_info .row')[0]).find(
                     '.col-sm-6')[0]).css({
                         'width': 'auto',
                         'float': 'left'
             });
             $(
                 $($('#sellinTable_info .row')[0]).find(
                     '.col-sm-6')[1]).addClass(
                 "searchicon-wrapper");
             $(
                 $($('#sellinTable_info .row')[0]).find(
                     '.col-sm-6')[1]).after($(".search-icon"));

             $('#DataTables_Table_0_length').css({
                 'text-align': 'right',
                 'padding': '20px 0'
             });
             $('#DataTables_Table_0_length label').css({
                 'color': '#29290a'
             });
	    	}, 1200);  
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	        console.log( errorThrown );
	    }
	});

}

// save for 4th screen
function saveSellinData() {
	 $('#successblock').hide();      //added bharati for hide success msg on visiplan tab
	var launchSellinArr = [];
	var launchId = $("#dynamicLaunchId").val();
	var rowCount = $('#sellinTable tbody').find('tr');
	var errFlg = false;
	for (var i = 0; i < rowCount.length; i++) {
		if( typeof launchSellinArr[i] == 'undefined' ){
			launchSellinArr[i] = {};
		}
		
		
		
		launchSellinArr[i] =  {
				"l1Chain" : rowCount[i].children[0].children[0].value,
	            "l2Chain" : rowCount[i].children[1].children[0].value,
	            "storeFormat" : rowCount[i].children[2].children[0].value,
	            "storesPlanned" : rowCount[i].children[3].children[0].value,
	            "sku1" : $( rowCount[i] ).find( '[data-field=base_1]' ).val(),
	            "sku2" : $( rowCount[i] ).find( '[data-field=base_2]' ).val(),
	            "sku3" : $( rowCount[i] ).find( '[data-field=base_3]' ).val(),
	            "sku4" : $( rowCount[i] ).find( '[data-field=base_4]' ).val(),
	            "sku5" : $( rowCount[i] ).find( '[data-field=base_5]' ).val(),
	            "sku6" : $( rowCount[i] ).find( '[data-field=base_6]' ).val(),
	            "sku7" : $( rowCount[i] ).find( '[data-field=base_7]' ).val(),
	            "sku8" : $( rowCount[i] ).find( '[data-field=base_8]' ).val(),
	            "sku9" : $( rowCount[i] ).find( '[data-field=base_9]' ).val(),
	            "sku10" : $( rowCount[i] ).find( '[data-field=base_10]' ).val(),
	            "sku11" :$( rowCount[i] ).find( '[data-field=base_11]' ).val(),
	            "sku12" : $( rowCount[i] ).find( '[data-field=base_12]' ).val(),
	            "sku13" : $( rowCount[i] ).find( '[data-field=base_13]' ).val(),
	            "sku14" : $( rowCount[i] ).find( '[data-field=base_14]' ).val(),
	            "rotations" : $( rowCount[i] ).find( '[data-field=rotations]' ).val(),
	            "upliftN1" : $( rowCount[i] ).find( '[data-field=upliftone]' ).val(),
	            "upliftN2" : $( rowCount[i] ).find( '[data-field=uplifttwo]' ).val()
		}
		
		
		if($( rowCount[i] ).find( '[data-field=rotations]' ).val() == ''){
			ezBSAlert({
				messageText : "Please fill rotation",
				alertType : "info"
			}).done(function(e) {
				// console.log('hello');
			});
			return false;
		}
	
		if( $( rowCount[i] ).find( '[data-field=upliftone]' ).val() == '' || $( rowCount[i] ).find( '[data-field=uplifttwo]' ).val() == ''){
			ezBSAlert({
				messageText : "Please fill uplift",
				alertType : "info"
			}).done(function(e) {
				// console.log('hello');
			});
			return false;
		}
		
		for( var z = 1; z <= skuCountSellInPack; z++ ){
			if( $( rowCount[i] ).find( '[data-field=base_'+z+']' ).length != 0  && errFlg == false ){
				if(  $( rowCount[i] ).find( '[data-field=base_'+z+']' ).val() == '' ){
					ezBSAlert({
						messageText : "Please fill basepack description",
						alertType : "info"
					}).done(function(e) {
					});
					errFlg = true;
				} else {
					launchSellinArr[i]['sku'+z] = $( rowCount[i] ).find( '[data-field=base_'+z+']' ).val();
				}
			}
			
		}
		
		
	
}
	
	if( errFlg ){
		return false;
	}
	
	$.ajax({
	    url: 'saveLaunchSellIn.htm',
	    dataType: 'json',
	    type: 'post',
	    contentType: 'application/json',
	    data: JSON.stringify( { "launchId": launchId, "listOfSellIns": launchSellinArr } ),
	    processData: false,
	    beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
	    success: function( data, textStatus, jQxhr ){
	    	 $('.loader').hide();
	    	 $("#launchVisiTab").click();
	    	 $("#visiplan").dataTable().fnDestroy(); 
	    	 getvisiPlan();
	    	// visioTable.draw();
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	        console.log( errorThrown );
	    }
	});
}

// save for 4th screen

function saveVisiPlan() {
	var listOfVisiPlans = [];
	var launchId = $("#dynamicLaunchId").val();
	var rowCount = $('#visiplan tbody').find('tr');
	var chk = document.getElementsByClassName("checkvissi");
	var temp = 0;
	var launchVisiPlanArr = [];
	if($('.checkvissi').is(":checked")) {
		for (var i = 0; i < rowCount.length; i++) {
			if(rowCount[i].children[0].children[0].checked == true) {
				var account = rowCount[i].children[1].children[0].value;
				var format = rowCount[i].children[2].children[0].value;
				var storeFormat = rowCount[i].children[3].children[0].value;
				var storesPlanned = rowCount[i].children[4].children[0].value;
				var visiPlanned1 = rowCount[i].children[5].children[0].value;
				var visiPlanned2 = rowCount[i].children[8].children[0].value;
				var visiPlanned3 = rowCount[i].children[11].children[0].value;
				var visiPlanned4 = rowCount[i].children[14].children[0].value;
				var visiPlanned5 = rowCount[i].children[17].children[0].value;
			/*
			 * var facingPerShelfSku =
			 * rowCount[i].children[10].children[0].value; var depthPerShelfSku =
			 * rowCount[i].children[11].children[0].value;
			 */
				
				var face1 = $( $( rowCount[i] ).find( ".face-self" )[0] ).val(),
					face2 = $( $( rowCount[i] ).find( ".face-self" )[1] ).val(),
					face3 = $( $( rowCount[i] ).find( ".face-self" )[2] ).val(),
					face4 = $( $( rowCount[i] ).find( ".face-self" )[3] ).val(),
					face5 = $( $( rowCount[i] ).find( ".face-self" )[4] ).val(),
					depth1 = $( $( rowCount[i] ).find( ".depth-self" )[0] ).val(),
					depth2 = $( $( rowCount[i] ).find( ".depth-self" )[1] ).val(),
					depth3 = $( $( rowCount[i] ).find( ".depth-self" )[2] ).val(),
					depth4 = $( $( rowCount[i] ).find( ".depth-self" )[3] ).val(),
					depth5 = $( $( rowCount[i] ).find( ".depth-self" )[4] ).val();
				if(storesPlanned == ''){
					ezBSAlert({
						messageText : "Please fill stores planned",
						alertType : "info"
					}).done(function(e) {
						// console.log('hello');
					});
					return false;
				}
				else if(parseInt(storesPlanned) > parseInt(storeFormat)){
					ezBSAlert({
						messageText : "Stores planned should not be greater than stores available.",
						alertType : "info"
					}).done(function(e) {
						// console.log('hello');
					});
					return false;
				}
				
				if(visiPlanned1 == '-1' && visiPlanned2 == '-1' && visiPlanned3 == '-1' && visiPlanned4 == '-1' && visiPlanned5 == '-1'){
					ezBSAlert({
						messageText : "Please select vissi asset",
						alertType : "info"
					}).done(function(e) {
						// console.log('hello');
					});
					return false;
				}else if(( visiPlanned1 != '-1' && face1 == '' && depth1 == '' ) ||
						( visiPlanned2 != '-1' && face2 == '' && depth2 == '' ) || 
						( visiPlanned3 != '-1' && face3 == '' && depth3 == '' ) || 
						( visiPlanned4 != '-1' && face4 == '' && depth4 == '' ) || 
						( visiPlanned5 != '-1' && face5 == '' && depth5 == '' )){		
					
						ezBSAlert({
							messageText : "Please fill facing and depth",
							alertType : "info"
						}).done(function(e) {
							// console.log('hello');
						});
						return false;
					
				}
				var jsonData = {
					"account" : account,
			        "format" : format,
			        "storesAvailable" : storeFormat,
			        "storesPlanned" : storesPlanned,
			        "visiAsset1" : visiPlanned1,
			        "visiAsset2" : visiPlanned2,
			        "visiAsset3" : visiPlanned3,
			        "visiAsset4" : visiPlanned4,
			        "visiAsset5" : visiPlanned5,
			        "facingsPerShelf1" : face1,
					"depthPerShelf1"   : depth1,
					"facingsPerShelf2" : face2,
					"depthPerShelf2" 	 : depth2,
					"facingsPerShelf3" : face3,
					"depthPerShelf3"   : depth3,
					"facingsPerShelf4" : face4,
					"depthPerShelf4"   : depth4,
					"facingsPerShelf5" : face5,
					"depthPerShelf5"  : depth5
				}
				launchVisiPlanArr.push(jsonData);
			}
		}
	} else {
		for (var i = 0; i < rowCount.length; i++) {
			var account = rowCount[i].children[1].children[0].value;
			var format = rowCount[i].children[2].children[0].value;
			var storeFormat = rowCount[i].children[3].children[0].value;
			var storesPlanned = rowCount[i].children[4].children[0].value;
			var visiPlanned1 = rowCount[i].children[5].children[0].value;
			var visiPlanned2 = rowCount[i].children[8].children[0].value;
			var visiPlanned3 = rowCount[i].children[11].children[0].value;
			var visiPlanned4 = rowCount[i].children[14].children[0].value;
			var visiPlanned5 = rowCount[i].children[17].children[0].value;
			/*
			 * var facingPerShelfSku =
			 * rowCount[i].children[10].children[0].value; var depthPerShelfSku =
			 * rowCount[i].children[11].children[0].value;
			 */
			var face1 = $( $( rowCount[i] ).find( ".face-self" )[0] ).val(),
			face2 = $( $( rowCount[i] ).find( ".face-self" )[1] ).val(),
			face3 = $( $( rowCount[i] ).find( ".face-self" )[2] ).val(),
			face4 = $( $( rowCount[i] ).find( ".face-self" )[3] ).val(),
			face5 = $( $( rowCount[i] ).find( ".face-self" )[4] ).val(),
			depth1 = $( $( rowCount[i] ).find( ".depth-self" )[0] ).val(),
			depth2 = $( $( rowCount[i] ).find( ".depth-self" )[1] ).val(),
			depth3 = $( $( rowCount[i] ).find( ".depth-self" )[2] ).val(),
			depth4 = $( $( rowCount[i] ).find( ".depth-self" )[3] ).val(),
			depth5 = $( $( rowCount[i] ).find( ".depth-self" )[4] ).val();
			
			if(storesPlanned == ''){
				ezBSAlert({
					messageText : "Please fill stores planned",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
			else if(parseInt(storesPlanned) > parseInt(storeFormat)){
				ezBSAlert({
					messageText : "Stores planned should not be greater than stores available.",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}
			if(visiPlanned1 == '-1' && visiPlanned2 == '-1' && visiPlanned3 == '-1' && visiPlanned4 == '-1' && visiPlanned5 == '-1'){
				ezBSAlert({
					messageText : "Please select vissi asset",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false;
			}else if(( visiPlanned1 != '-1' && face1 == '' && depth1 == '' ) ||
					( visiPlanned2 != '-1' && face2 == '' && depth2 == '' ) || 
					( visiPlanned3 != '-1' && face3 == '' && depth3 == '' ) || 
					( visiPlanned4 != '-1' && face4 == '' && depth4 == '' ) || 
					( visiPlanned5 != '-1' && face5 == '' && depth5 == '' )){
				
					ezBSAlert({
						messageText : "Please fill facing and depth",
						alertType : "info"
					}).done(function(e) {
						// console.log('hello');
					});
					return false;
				
			}
		
			var jsonData = {
					"account" : account,
			        "format" : format,
			        "storesAvailable" : storeFormat,
			        "storesPlanned" : storesPlanned,
			        "visiAsset1" : visiPlanned1,
			        "visiAsset2" : visiPlanned2,
			        "visiAsset3" : visiPlanned3,
			        "visiAsset4" : visiPlanned4,
			        "visiAsset5" : visiPlanned5,
			        "facingsPerShelf1" : face1,
					"depthPerShelf1"   : depth1,
					"facingsPerShelf2" : face2,
					"depthPerShelf2" 	 : depth2,
					"facingsPerShelf3" : face3,
					"depthPerShelf3"   : depth3,
					"facingsPerShelf4" : face4,
					"depthPerShelf4"   : depth4,
					"facingsPerShelf5" : face5,
					"depthPerShelf5"  : depth5
			}
			launchVisiPlanArr.push(jsonData);
		}
	}
	
	
	$.ajax({
	    url: 'saveLaunchVisiPlan.htm',
	    dataType: 'json',
	    type: 'post',
	    contentType: 'application/json',
	    data: JSON.stringify( { "launchId": launchId, "listOfVisiPlans": launchVisiPlanArr } ),
	    processData: false,
	    beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
	    success: function( data, textStatus, jQxhr ){
	    	 $('.loader').hide();
	    	 $("#launchFinBuiUpTab").click();
	    	 if(data.Error != undefined) {
	    		 ezBSAlert({
						messageText : "Please fill the correct details",
						alertType : "info"
					}).done(function(e) {
						// console.log('hello');
					});
					return false;

	    	 }
	    	// getvisiPlan();
	    	 $("#finalTable").dataTable().fnDestroy();
	    	 getfinalPlan();
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	        console.log( errorThrown );
	    }
	});
}

// get for vissi screen

function getvisiPlan() {
	var launchId = $("#dynamicLaunchId").val();
	$.ajax({
	    url: 'getLaunchVisiPlan.htm?launchId='+launchId,
	    dataType: 'json',
	    type: 'get',
	    contentType: 'application/json',
	    processData: false,
	    beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
	    success: function( data, textStatus, jQxhr ){
	    	setTimeout(function(){
	    	 $('.loader').hide();
	    	   var len = data.listOfVisiPlans.length;
	           var vissiAsset = data.listOfAssetType;
				var visirow = "";
				var optionJsonvissi = "<option value='-1'>Select Visi Asset</option>";
				$.each(vissiAsset, function (a, br) {
		         	optionJsonvissi += "<option value='" + br + "'>" + br + "</option>";
		     	});
				for (var i = 0; i < len; i++) {
					visirow += "<tr><td><input type='checkbox' class='checkvissi' name='selectDel' value='selectDel'></td>" 
							+"<td><input name='childRatio1' type='text' class='form-control l1chain' onkeypress='return validateFloatKeyPress(this,event);' readonly value= '"
						 	+ data.listOfVisiPlans[i].account
						 	+"'></td>"
							+"<td><input name='childRatio1' type='text' class='form-control l2chain' onkeypress='return validateFloatKeyPress(this,event);' readonly value= '" 
							+ data.listOfVisiPlans[i].format +"'></td>" 
							+"<td><input name='childRatio1' type='text' class='form-control storefrmt' onkeypress='return validateFloatKeyPress(this,event);' readonly value= '" +
							 data.listOfVisiPlans[i].storeAvailable+"'></td>" 
							+ "<td><input name='childRatio1' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);'></td>"
							+ "<td><select class='form-control validfield salescat'>"+optionJsonvissi+"</select></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield face-self' onkeypress='return validateFloatKeyPress(this,event);'></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield depth-self' onkeypress='return validateFloatKeyPress(this,event);'></td>"
							+ "<td><select class='form-control validfield salescat'>"+optionJsonvissi+"</select></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield face-self' onkeypress='return validateFloatKeyPress(this,event);'></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield depth-self' onkeypress='return validateFloatKeyPress(this,event);'></td>"
							+ "<td><select class='form-control validfield salescat'>"+optionJsonvissi+"</select></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield face-self' onkeypress='return validateFloatKeyPress(this,event);'></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield depth-self' onkeypress='return validateFloatKeyPress(this,event);'></td>"
							+ "<td><select class='form-control validfield salescat'>"+optionJsonvissi+"</select></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield face-self' onkeypress='return validateFloatKeyPress(this,event);'></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield depth-self' onkeypress='return validateFloatKeyPress(this,event);'></td>"
							+ "<td><select class='form-control validfield salescat'>"+optionJsonvissi+"</select></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield face-self' onkeypress='return validateFloatKeyPress(this,event);'></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield depth-self' onkeypress='return validateFloatKeyPress(this,event);'></td></tr>";
				}
				$('#visiplan tbody').empty().append(visirow);
				
				 $("#visiplan").dataTable().fnDestroy();
				 visioTable = $('#visiplan').DataTable( {
					
				 	scrollY:       "300px",
			        scrollX:        true,
			        "scrollCollapse": true,
			        "paging":         true,
			        "ordering": false,
			        "responsive": true,
			        "bAutoWidth": true,
			        searching: false,
			    	"lengthMenu" : [
						[ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
						[ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ] ],
			        "oLanguage": {
		                  "sSearch": '<i class="icon-search"></i>',
		                  "oPaginate": {
		                      "sNext": "&rarr;",
		                      "sPrevious": "&larr;"
		                  },
		                  "sLengthMenu": "Records per page _MENU_ ",
		                  "sEmptyTable": "No Pending Launch."
		
		              },
			     
 	    	    });
			
			 $('#visiplan').css({
                 'text-align': 'left',
                 
             });
             $('#visiplan').css({
                 'color': '#29290a'
             });
             $($('#visiplan .row')[0]).after(
                     $(".summary-text"));

             $($('#visiplan .row')[0]).css({
                 'float': 'right'
             });
             $(
                 $($('#visiplan .row')[0]).find(
                     '.col-sm-6')[0]).css({
                         'width': 'auto',
                         'float': 'left'
             });
             $(
                 $($('#visiplan .row')[0]).find(
                     '.col-sm-6')[1]).addClass(
                 "searchicon-wrapper");
             $(
                 $($('#visiplan .row')[0]).find(
                     '.col-sm-6')[1]).after($(".search-icon"));

             $('#DataTables_Table_0_length').css({
                 'text-align': 'right',
                 'padding': '20px 0'
             });
             $('#DataTables_Table_0_length label').css({
                 'color': '#29290a'
             });
	    	}, 1200);    	
	    	
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	        console.log( errorThrown );
	    }
	 
	});
}


function getfinalPlan() {
	var launchId = $("#dynamicLaunchId").val();
	$("#finalTable").dataTable().fnDestroy();  //Added By Sarin - sprint4Aug2021
	$.ajax({
	    url: 'getLaunchFinalPlan.htm?launchId='+launchId,
	    dataType: 'json',
	    type: 'get',
	    contentType: 'application/json',
	    processData: false,
	    beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
	    success: function( finaldata, textStatus, jQxhr ){
	    	//console.log(finaldata);
	    	 $('.loader').hide();
	    		
				/*
				 * var optionJsonvissi = "<option value='-1'>Select Visi Asset</option>";
				 * $.each(vissiAsset, function (a, br) { optionJsonvissi += "<option
				 * value='" + br + "'>" + br + "</option>"; });
				 */
				
				if(finaldata.Error != undefined) {
		    		 ezBSAlert({
							messageText : "Please check the values",
							alertType : "info"
						}).done(function(e) {
							// console.log('hello');
						});
						return false;
				}
				else{
					var len = finaldata.listOfFinalPlans.length;
		    		finaldata = finaldata.listOfFinalPlans;
					var finalrow = "";
				for (var i = 0; i < len; i++) {
					finalrow += "<tr><td><input name='skuname' type='text' class='form-control validfield' value='" +
					finaldata[i].skuName	+"' readonly></td>"
							+ "<td><input name='basecd' type='text' class='form-control validfield' value='" +
							finaldata[i].basepackCode +"' readonly></td>"
							+ "<td><input name='lnchsellval' type='text' class='form-control validfield' value='" +
							( typeof finaldata[i].launchSellInValue != "undefined" ? finaldata[i].launchSellInValue : "" ) +"' readonly></td>"
							+ "<td><input name='nsellval' type='text' class='form-control validfield' value='" +
							( typeof finaldata[i].launchN1SellInVal != "undefined" ? finaldata[i].launchN1SellInVal : "" ) +"' readonly></td>"
							+ "<td><input name='n2sellval' type='text' class='form-control validfield' value='" +
							( typeof finaldata[i].launchN2SellInVal != "undefined" ? finaldata[i].launchN2SellInVal : "" ) +"' readonly></td>"
							+ "<td><input name='lnchsellincld' type='text' class='form-control validfield' value='" +
							( typeof finaldata[i].launchSellInCld != "undefined" ? finaldata[i].launchSellInCld : "" ) +"' readonly></td>"
							+ "<td><input name='skuname' type='text' class='form-control validfield' value='" +
							( typeof finaldata[i].launchN1SellInCld != "undefined" ? finaldata[i].launchN1SellInCld : "" ) +"' readonly></td>"
							+ "<td><input name='skuname' type='text' class='form-control validfield' value='" +
							( typeof finaldata[i].launchN2SellInCld != "undefined" ? finaldata[i].launchN2SellInCld : "" ) +"' readonly></td>"
							+ "<td><input name='skuname' type='text' class='form-control validfield' value='" +
							( typeof finaldata[i].launchSellInUnit != "undefined" ? finaldata[i].launchSellInUnit : "" ) +"' readonly></td>"
							+ "<td><input name='skuname' type='text' class='form-control validfield' value='" +
							( typeof finaldata[i].launchN1SellInUnit != "undefined" ? finaldata[i].launchN1SellInUnit : "" )	+"' readonly></td>"
							+ "<td><input name='skuname' type='text' class='form-control validfield' value='" +
							( typeof finaldata[i].launchN2SellInUnit != "undefined" ? finaldata[i].launchN2SellInUnit : "" ) +"' readonly></td></tr>";
				}
				
				$('#finalTable tbody').empty().append(finalrow);
				 //$("#finalTable").dataTable().fnDestroy();  //Commented By Sarin - sprint4Aug2021
				    setTimeout(function(){
						     fnloTable = $('#finalTable').DataTable( {
									
									"scrollY":       "280px",
							        "scrollX":        true,
							        "scrollCollapse": true,
							        "paging":         true,
							        "ordering": false,
							        "searching": false,
							    	"lengthMenu" : [
										[ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
										[ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ] ],
							        "oLanguage": {
						                  "sSearch": '<i class="icon-search"></i>',
						                  "oPaginate": {
						                      "sNext": "&rarr;",
						                      "sPrevious": "&larr;"
						                  },
						                  "sLengthMenu": "Records per page _MENU_ ",
						                  "sEmptyTable": "No Pending Launch."
						
						              }
				    	    	    });
						    
						    
		             }, 800 );
				}
				  	
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	        console.log( errorThrown );
	    }
	 
	});
}
// 2nd screen basepack code
/*
 * function basepackValidation(eve){ isNumberKey(event); //getBasepackCode(); }
 */
function getBasepackCode(obj){
	
		var currEvent = $(obj);
		var basepack = currEvent.val();
		// check for duplicate value in basepack code
		
		var basePackCodeValues = [];
		var found = false;
		$('#basepack_add tbody tr').each(function () {
		        $projectName = $(this).find('.baseCode').val();
		        if($.trim($projectName).length > 0){
		        if(basePackCodeValues.indexOf($projectName) >= 0){
		            found = true;
		        }else{
		            basePackCodeValues.push($projectName);
		        }
		if(found){
		return false;
		}
		        }

		});
		if(found){
			ezBSAlert({
    			messageText : "Please enter unique basepack code",
    			alertType : "info"
    		}).done(function(e) {
    			// console.log('hello');
    		});
    		return false;
		}
	$.ajax({
	    url: 'getBasePackData.htm?basepackCode=' +basepack,
	    dataType: 'json',
	    type: 'get',
	    contentType: 'application/json',
	    processData: false,
	    beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
	    success: function( response, b){
	    	 $('.loader').hide();
	    	 // $("#launchStoresTab").click();
	    	var data = response;
	    	/*
			 * $(obj).closest('tr').find("td:nth-child(6)
			 * input").val(data.bpDesc); console.log(response);
			 */
			if(basepack !=''){
			if(data.bpCode != basepack){
		           ezBSAlert({
    			messageText : basepack +' ' +"Basepack is not added in Unified Product Master",
    			alertType : "info"
    		}).done(function(e) {
				$(obj).closest('tr').find("td:nth-child(5) input").val('');
    		});
    		return false;
		    }
		    }
	    	if(data.bpCode == basepack){
		    	$(obj).closest('tr').find("td:nth-child(6) input").val(data.bpDesc);
		    	$(obj).closest('tr').find("td:nth-child(12) select").val(data.bpClassification == "" ?  'Select Classification' : data.bpClassification);
		    	/*
				 * $(obj).closest('tr').find("td:nth-child(7)
				 * input").val(data.bpMrp);
				 * $(obj).closest('tr').find("td:nth-child(8)
				 * input").val(data.bpTur);
				 * $(obj).closest('tr').find("td:nth-child(9)
				 * input").val(data.bpGsv);
				 * $(obj).closest('tr').find("td:nth-child(10)
				 * input").val(data.bpCldConfig);
				 * $(obj).closest('tr').find("td:nth-child(11)
				 * input").val(data.bpGrammage);
				 * $(obj).closest('tr').find("td:nth-child(6)
				 * input").val(data.bpDesc);
				 */

	    	// console.log(response);
	    	}
	    	
	    	else{
	    		$(obj).closest('tr').find("td:nth-child(6) input").val('');
	    		$(obj).closest('tr').find("td:nth-child(12) select").val('Select Classification');
		    	/*
				 * $(obj).closest('tr').find("td:nth-child(7) input").val('');
				 * $(obj).closest('tr').find("td:nth-child(8) input").val('');
				 * $(obj).closest('tr').find("td:nth-child(9) input").val('');
				 * $(obj).closest('tr').find("td:nth-child(10) input").val('');
				 * $(obj).closest('tr').find("td:nth-child(11) input").val('');
				 */
	    	}
	    	// desc(bcode.bpDesc);
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	        console.log( errorThrown );
	    }
	});
}

// description validation
function descValidation(){
var basePackDescValues = [];
var found = false;
$('#basepack_add tbody tr').each(function () {
        $descName = $(this).find('.descrip').val();
        if($.trim($descName).length > 0){
        if(basePackDescValues.indexOf($descName) >= 0){
            found = true;
        }else{
        	basePackDescValues.push($descName);
        }
if(found){
return false;
}
        }

});
if(found){
	ezBSAlert({
		messageText : "Please enter unique basepack description",
		alertType : "info"
	}).done(function(e) {
		// console.log('hello');
	});
	return false;
	$(this).find('.descrip').val('');
}

}

// save for 2nd last screen
function saveFinalBuildUpData() {
    
    var listOfFinalBuildUps = [];
    var launchId = $("#dynamicLaunchId").val();
	var countTd = 0;
    var rowCount = $('#finalTable tbody').find('tr');
    for (var i = 0; i < rowCount.length; i++) {
        var skuname = rowCount[i].children[0].children[0].value;
        var baspckcd = rowCount[i].children[1].children[0].value;
        var lnchsellinval = rowCount[i].children[2].children[0].value;
        var n1sellinval = rowCount[i].children[3].children[0].value;
        var n2sellinval = rowCount[i].children[4].children[0].value;
        var lnchsellcld = rowCount[i].children[5].children[0].value;
        var n1sellincld = rowCount[i].children[6].children[0].value;
        var n2sellincld = rowCount[i].children[7].children[0].value;
        var lnchsellinunit = rowCount[i].children[8].children[0].value;
        var n1sellinunit = rowCount[i].children[9].children[0].value;
        var n2sellinunit = rowCount[i].children[10].children[0].value;
		
		
		//bharati added this in sprint-9 issues in launch calculation
		
		     if(lnchsellinval=='' && n1sellinval=='' && n2sellinval=='' && lnchsellcld=='' && n1sellincld=='' && n2sellincld=='' && lnchsellinunit ==''&& n1sellinunit=='' && n2sellinunit=='' ){
				countTd++;
			 
			 }else if(lnchsellinval=='0.0' && n1sellinval=='0.0' && n2sellinval=='0.0' && lnchsellcld=='0.0' && n1sellincld=='0.0' && n2sellincld=='0.0' && lnchsellinunit=='0.0'&& n1sellinunit=='0.0' && n2sellinunit=='0.0' ){
				countTd++;
				
			}else if(lnchsellinval=='0' && n1sellinval=='0' && n2sellinval=='0' && lnchsellcld=='0' && n1sellincld=='0' && n2sellincld=='0' && lnchsellinunit=='0'&& n1sellinunit=='0' && n2sellinunit=='0' ){
				countTd++;
		}
		
      //bharati code end here
    var jsonData = {
            "skuName" : skuname,
            "basepackCode" : baspckcd,
            "launchSellInValue" : lnchsellinval,
            "launchSellInValueN1" : n1sellinval,
            "launchSellInValueN2" : n2sellinval,
            "launchSellInValueClds" : lnchsellcld,
            "launchSellInValueCldsN1" : n1sellincld,
            "launchSellInValueCldsN2" : n2sellincld,
            "launchSellInUnits" : lnchsellinunit,
            "launchSellInUnitsN1" : n1sellinunit,
            "launchSellInUnitsN2" : n2sellinunit
        }
    listOfFinalBuildUps.push(jsonData);
}
//bharati added this condition for launch issue in sprint-9
 if(countTd == rowCount.length ){
				ezBSAlert({
					messageText : "Error while calculating Final buildup, Please contact support team",
					alertType : "info"
				}).done(function(e) {
					
				});
				return false;

             }
			   //bharati code end here
     
    $.ajax({
        url: 'saveLaunchFinal.htm',
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify( { "launchId": launchId, "listOfFinalBuildUps": listOfFinalBuildUps } ),
        processData: false,
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
        success: function( data, textStatus, jQxhr ){
        	 $('.loader').hide();
            console.log("sucessful");
            
            $("#launchSubTab").click();
          // $('#successblock').show();
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
}



// save for submit screen

function saveLaunchPlanSucess() {
    var launchId = $("#dynamicLaunchId").val();
    var sample = $( '[name="sampleYesorno"]:checked' ).val();
	    if( sample == "" || typeof sample == 'undefined'  ){
		   ezBSAlert({
				messageText : "Please select sample shared field",
				alertType : "info"
			}).done(function(e) {
				// console.log('hello');
			});
			return false;
	   }
	  if(sample == "1"){
		   if(!isFileOneUploaded || !isFileTwoUploaded || !isFileThreeUploaded){
			   ezBSAlert({
					messageText : "Please upload Document",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				return false; 
		   }
	  }
	  if(sample == "1" || sample ==  "0"){
	    	sample = parseInt(sample);
	  } else {
	  		sample = 0;
	  }

    $.ajax({
        url: 'saveLaunchSubmit.htm',
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify( { "launchId": launchId, "isSampleShared": sample } ),
        processData: false,
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
        success: function( data, textStatus, jQxhr ){
        	 $('.loader').hide();
           // console.log("sucessful");
            $('#successblockUpload').show().find('span').html(' Launch Created Successfully !!!');
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
}
// loader

function ajaxLoader(w, h) {

    var left = (window.innerWidth / 2) - (w / 2);
    var top = (window.innerHeight / 2) - (h / 2);
    $('.loader').css('display', 'block');

    $('.loading-image').css({
        "left": left,
        "top": top,
        
    });
}

function customSelectionStore(){
	if( $( "#custmsel" ).is( ':checked' ) ){
		$( "#radio1, #radio2" ).removeAttr( 'disabled' );
		$( '#cust-chain-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
		$( '#cust-chain-cst-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
	} else {
		$( "#radio1, #radio2" ).attr( 'disabled', true );
		$( '#radio1' ).prop('checked', false);
		$( '#radio2' ).prop('checked', false);
	}
	
	
	if( $( '#radio1' ).is( ":checked" ) ){
		$( '#cust-chain-store' ).next().find( 'button.multiselect ' ).removeAttr( "disabled" );
		$( '#cust-chain-cst-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
	} else {
		$( '#cust-chain-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
	}

	if( $( "#radio2" ).is( ":checked" ) ){
		$( '#cust-chain-cst-store' ).next().find( 'button.multiselect ' ).removeAttr( "disabled" );
		$( '#cust-chain-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
	} else {
		$( '#cust-chain-cst-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
	}
	
}

//bharati done changes for sprint-6 US-5 download error file

function downloadLaunchSellInErrorTemplate() {
	var launchId = $("#dynamicLaunchId").val();
	
	$.ajax({
	    url: 'downloadErrorSellInTemplate.htm',
	    type: 'post',
	    contentType: 'application/json',
	    dataType : 'JSON',
	    beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
	    data : JSON.stringify( { "launchId": launchId } ),
	    success: function( data, textStatus, jQxhr ){
	    	 $('.loader').hide();
	    	window.location.assign(data.FileToDownload+"/downloadFileTemplate.htm");
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	       // console.log( errorThrown );
	    }
	});
}
//bharati added changes for sprint-7 US-7 download error file for customer store format
function downloadLaunchStoreErrorTemplate() {
	var launchId = $("#dynamicLaunchId").val();
	
	$.ajax({
	    url: 'downloadErrorClusterTempforCustStoreformat.htm',
	    type: 'post',
	    contentType: 'application/json',
	    dataType : 'JSON',
	    beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
	    data : JSON.stringify( { "launchId": launchId } ),
	    success: function( data, textStatus, jQxhr ){
	    	 $('.loader').hide();
	    	window.location.assign(data.FileToDownload+"/downloadFileTemplate.htm");
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	       // console.log( errorThrown );
	    }
	});
}

//bharati added code for download store format error file for sprint-7 US-7
function downloadLaunchStoreErrorTemplateForStoreFormat() {
	var launchId = $("#dynamicLaunchId").val();
	
	$.ajax({
	    url: 'downloadErrorClusterTempforStoreformat.htm',
	    type: 'post',
	    contentType: 'application/json',
	    dataType : 'JSON',
	    beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
	    data : JSON.stringify( { "launchId": launchId } ),
	    success: function( data, textStatus, jQxhr ){
	    	 $('.loader').hide();
	    	window.location.assign(data.FileToDownload+"/downloadFileTemplate.htm");
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	       // console.log( errorThrown );
	    }
	});
}