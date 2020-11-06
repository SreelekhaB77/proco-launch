/**
 * DP Launch script
 */

var spinnerWidth = "100";
var spinnerHeight = "100";
var isManualClick = false;

 $(document).ready(function() {
	 $.ajaxSetup({ cache: false });
	 
	//previous button
	 
	 $('#dpprevbspack').click(function(){
		 $('#dp_basepack_add').dataTable().fnDestroy();
		 $("#dplaunchDetailsTab").click();
	});
	 $('#dpprevlnchBuild').click(function(){
		 $("#dp_basepack_launch_build").dataTable().fnDestroy();
		 $("#dplaunchBasepackTab").click();
	});
	 $('#dpnext').click(function(){
		 $("#dplaunchBasepackTab").click();
	});
	 
	
	 if( window.location.hash != "#step-1" && window.location.hash != '' ){
			window.location = window.location.href.split('#')[0];
		}

		$( document ).on( 'mousedown', '#smartwizard ul.step-anchor a, #smartwizard ul.step-anchor li', function(e){
			isManualClick = true;
		} );
		
		
		 var oTable = $('#coebasepack_add').DataTable( {
			 scrollY:       "300px",
	    		"destroy": true,  
		        "paging":  true,
		        "ordering": false,
		        "searching": false,
		    	"lengthMenu" : [
					[ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
					[ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ] ],
		        "oLanguage": {
	                  "sSearch": '<i class="icon-search"></i>',
	                  "sEmptyTable": "No Pending Launch.",
	                  "oPaginate": {
	                      "sNext": "&rarr;",
	                      "sPrevious": "&larr;"
	                  },
	                  "sLengthMenu": "Records per page _MENU_ ",
	                  
	
	              }
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
 });
 
 
// get 1 screen ID
 function getDplaunchId(){
 	var idList = $( ".dpchecklaunch:checked" ),
 	ids = [];
 	for( var i =0 ; i < idList.length; i++ ){
 		ids.push( $( idList[i] ).val() );
 	}
 	return ids;
 }

 // Get Second screen data

 function dpLaunch(){
              
             
              var launchIdsArr =  getDplaunchId().toString();
              var chk = $('#coebasepack_add').find('input.coechecklaunch')
              if($(".dpchecklaunch").is(":checked")){
              // console.log("true");
             
              $.ajax({
                  url: 'getAllBasePackByLaunchIdsDp.htm',
                  dataType: 'json',
                  type: 'post',
                  contentType: 'application/json',
                  data:  JSON.stringify({ launchIds: launchIdsArr }),
                  processData: false,
                  beforeSend: function() {
                      ajaxLoader(spinnerWidth, spinnerHeight);
                  },
                  success: function( dpBasedata, textStatus, jQxhr ){
                  	 $('.loader').hide();
                      console.log(dpBasedata);
                      // $('#successblockUpload').show().find('span').html('
						// Launch Created Successfully !!!');
                      $("#dplaunchBasepackTab").click();
                    // screen 2 table
  					var row = "";
  					var dpBase = dpBasedata.responseData.listLaunchDataResponse;
  					for (var i = 0; i < dpBase.length; i++) {
  						row += "<tr><td><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"+ dpBase[i].launchName
  								+"'></td>" 
  								+"<td><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
 						 		+ dpBase[i].salesCat
 						 		+"'></td>" 
  								+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
  								+ dpBase[i].psaCat
  								+"'></td>" 
  								+ "<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
  								+ dpBase[i].brand
  								+"'></td>" 
  								+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
  								+ dpBase[i].bpCode
  								+"'></td>" 
  								+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode' placeholder='12345' maxlength='5' disabled value= '"
 						 		+ dpBase[i].bpDisc
 						 		+"'></td>"
  								+ "<td><input name='coelaunchbase' type='text' class='form-control validfield descrip' placeholder='Description' maxlength='255' disabled value= '"
 						 		+ dpBase[i].mrp
 						 		+"'></td>" 
  								+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield mrpIn' placeholder='0000' disabled value= '"
 						 		+ dpBase[i].tur
 						 		+"'></td>" 
  								+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield turIn' placeholder='0000' disabled value= '"
 						 		+ dpBase[i].gsv
 						 		+"'></td>" 
  								+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield gsvIn' placeholder='0000' disabled value= '"
 						 		+ dpBase[i].cldConfig
 						 		+"'></td>" 
  								+ "<td><input name='coelaunchbase' type='text' class='form-control validfield clvIn' placeholder='0000' maxlength='4' disabled value= '"
 						 		+ dpBase[i].grammage
 						 		+"'></td>" 
  								+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield grmIn' placeholder='0000' disabled value= '"
 						 		+ dpBase[i].classification
 						 		+"'></td></tr>";
  					}
  					
  					$('#dp_basepack_add tbody').empty().append(row);
  					// $.fn.dataTable.ext.errMode = 'none';
  				    $("#dp_basepack_add").dataTable().fnDestroy();
  				    setTimeout(function(){
  						    var oTable = $('#dp_basepack_add').DataTable( {
  									
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
                  },
                  error: function( jqXhr, textStatus, errorThrown ){
                      console.log( errorThrown );
                  }
              });
              }
          	else{
          		ezBSAlert({
 					messageText : "Please select launch",
 					alertType : "info"
 				}).done(function(e) {
 					// console.log('hello');
 				});
 				return false;
          	}
 }
 
 
// Get 3rd screen data

 function dpSaveBasepacks(){
              
             
              var launchIdsArr =  getDplaunchId().toString();
             // var dpBuildchk =
				// $('#dp_basepack_add').find('input.dpcheckBase').toString();
             /*
				 * var idList = $( ".dpcheckBase:checked" ), ids = []; for( var
				 * i =0 ; i < idList.length; i++ ){ ids.push( $( idList[i]
				 * ).val() ); } var newID = ids.toString();
				 */
                            
              
              if(launchIdsArr.length > 0){
              // console.log("true");
             
              $.ajax({
                  url: 'getAllFinalBuildUpByLaunchIdsDp.htm',
                  dataType: 'json',
                  type: 'post',
                  contentType: 'application/json',
                  data:  JSON.stringify({ launchIds: launchIdsArr }),
                  processData: false,
                  beforeSend: function() {
                      ajaxLoader(spinnerWidth, spinnerHeight);
                  },
                  success: function( dpLnchBuilddata, textStatus, jQxhr ){
                  	 $('.loader').hide();
                      console.log(dpLnchBuilddata);
                      // $('#successblockUpload').show().find('span').html('
						// Launch Created Successfully !!!');
                      $("#dplaunchStoresTab").click();
                    // screen 2 table
  					var row = "";
  					var dpBuild = dpLnchBuilddata.responseData.launchDpFinalBuildUpResponse;
  					for (var i = 0; i < dpBuild.length; i++) {
  						row += "<tr><td><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
 						 		+ dpBuild[i].launchName
 						 		+"'></td> <td><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
 						 		+ dpBuild[i].skuName
 						 		+"'></td>" 
  								+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
  								+ dpBuild[i].basepackCode
  								+"'></td>" 
  								+ "<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
  								+ dpBuild[i].launchSellInValueN
  								+"'></td>" 
  								+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
  								+ dpBuild[i].launchSellInValueN1
  								+"'></td>" 
  								+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode' placeholder='12345' maxlength='5' disabled value= '"
 						 		+ dpBuild[i].launchSellInValueN2
 						 		+"'></td>"
  								+ "<td><input name='coelaunchbase' type='text' class='form-control validfield descrip' placeholder='Description' maxlength='255' disabled value= '"
 						 		+ dpBuild[i].launchSellInCldValueN
 						 		+"'></td>" 
  								+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield mrpIn' placeholder='0000' disabled value= '"
 						 		+ dpBuild[i].launchSellInCldValueN1
 						 		+"'></td>" 
  								+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield turIn' placeholder='0000' disabled value= '"
 						 		+ dpBuild[i].launchSellInCldValueN2
 						 		+"'></td>" 
  								+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield gsvIn' placeholder='0000' disabled value= '"
 						 		+ dpBuild[i].launchSellInUnitsN
 						 		+"'></td>" 
  								+ "<td><input name='coelaunchbase' type='text' class='form-control validfield clvIn' placeholder='0000' maxlength='4' disabled value= '"
 						 		+ dpBuild[i].launchSellInUnitsN1
 						 		+"'></td>" 
  								+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield grmIn' placeholder='0000' disabled value= '"
 						 		+ dpBuild[i].launchSellInUnitsN2
 						 		+"'></td></tr>";
  					}
  					
  					$('#dp_basepack_launch_build tbody').empty().append(row);
  					// $.fn.dataTable.ext.errMode = 'none';
  				    $("#dp_basepack_launch_build").dataTable().fnDestroy();
  				    setTimeout(function(){
  						    var oTable = $('#dp_basepack_launch_build').DataTable( {
  									
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
                  },
                  error: function( jqXhr, textStatus, errorThrown ){
                      console.log( errorThrown );
                  }
              });
              }
          	else{
          		ezBSAlert({
 					messageText : "Please select launch",
 					alertType : "info"
 				}).done(function(e) {
 					// console.log('hello');
 				});
 				return false;
          	}
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
 
 function dpdownloadLaunchBasepackTemplate() {
	 var launchIdsArr =  getDplaunchId().toString();
		
		window.open("/VisibilityAssetTracker/"+launchIdsArr+"/downloadFinalBuildUpTemplateDp.htm");
 }
 