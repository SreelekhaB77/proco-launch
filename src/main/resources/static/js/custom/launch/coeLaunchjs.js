 var spinnerWidth = "100";
 var spinnerHeight = "100";
 var isManualClick = false;

$(document).ready(function() { 
	$.ajaxSetup({ cache: false });
//prev button
	$('#coeprevbspack').click(function(){
		
		$('#coebasepack_add').dataTable().fnDestroy();
	    $('#coelaunchDetailsTab').click();
	});
	$('#coeprevlnchbuld').click(function(){
		
		$('#coe_basepack_add').dataTable().fnDestroy();
		$('#coe_basepack_launch_build').dataTable().fnDestroy();
	    $('#coelaunchBasepackTab').click();
	});
	$('#coeprevlsttrck').click(function(){
		 $('#list_track_table').dataTable().fnDestroy();
		 $('#coe_basepack_launch_build').dataTable().fnDestroy();
	    $('#coelaunchStoresTab').click();
	});
	$('#coeprevmstn').click(function(){
	    $('#coelaunchSellInTab').click();
	});
	$('#coeprevdoc').click(function(){
	    $('#coelaunchVisiTab').click();
	});
	$('#coeprevfinal').click(function(){
	    $('#coelaunchFinBuiUpTab').click();
	});
	
	loadCoeLauches('All');
	if( window.location.hash != "#step-1" && window.location.hash != '' ){
		window.location = window.location.href.split('#')[0];
	}

	$( document ).on( 'mousedown', '#smartwizard ul.step-anchor a, #smartwizard ul.step-anchor li', function(e){
		isManualClick = true;
	} );
	
	$( document ).on( 'click mousedown', '#smartwizard ul.step-anchor a, #smartwizard ul.step-anchor li', function(e){
		e.preventDefault();
		e.stopImmediatePropagation();
		return false;
	} );
	
	
	 $("#coebasepack_add").dataTable().fnDestroy();
	    setTimeout(function(){
			    var oTable = $('#coebasepack_add').DataTable( {
						
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
						transitionSpeed : '400',
						onLeaveStep: preventNavStep
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

	 $("#smartwizard").on("leaveStep", function(e, anchorObject, stepNumber, stepDirection) {
		console.log(e) 
	 })
	//Q2 sprint feb 2021 kavitha	
	$("#mocCol").on('change', function () {
		//$("#kamlaunchDetailsTab").click();
		$("#coebasepack_add").dataTable().fnDestroy();
		//kambaseoTable.draw();
		var coeselectedmoc = $(this).val(); //'All';
		loadCoeLauches(coeselectedmoc);
		$('#coebasepack_add').on('draw.dt', function() {
			  var $empty = $('#coebasepack_add').find('.dataTables_empty');
			  if ($empty) $empty.html('Loading Launches..')
		});
			     
    });	
	
});
	function leaveAStepCallback(obj) {
		var step_num = obj.attr('rel');
		return validateSteps(step_num);
	}
	
	function preventNavStep(Obj){
		console.log( Obj );
	}

	function onFinishCallback() {
		if (validateAllSteps()) {
			$('form').submit();
		}
	}
	
function CoeLaunch(){
	
	 $("#coelaunchBasepackTab").click();
}

//get 1 screen ID
function getlaunchId(){
	var idList = $( ".coechecklaunch:checked" ),
	ids = [];
	for( var i =0 ; i < idList.length; i++ ){
		ids.push( $( idList[i] ).val() );
	}
	return ids;
}

//Get Second screen data

function CoeLaunch(){
             
            
             var launchIdsArr =  getlaunchId().toString();
             var chk = $('#coebasepack_add').find('input.coechecklaunch')
             if($(".coechecklaunch").is(":checked")){
             // console.log("true");
            
             $.ajax({
                 url: 'getAllBasePackByLaunchIds.htm',
                 dataType: 'json',
                 type: 'post',
                 contentType: 'application/json',
                 data:  JSON.stringify({ launchIds: launchIdsArr }),
                 processData: false,
                 beforeSend: function() {
                     ajaxLoader(spinnerWidth, spinnerHeight);
                 },
                 success: function( coedata, textStatus, jQxhr ){
                 	 $('.loader').hide();
                     console.log(coedata);
                     //$('#successblockUpload').show().find('span').html(' Launch Created Successfully !!!');
                     $("#coelaunchBasepackTab").click();
                   //screen 2 table
 					var row = "";
 					for (var i = 0; i < coedata.length; i++) {
 						row += "<tr><td><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
						 		+ coedata[i].launchName
						 		+"'></td>" 
 								+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
 								+ coedata[i].salesCat
 								+"'></td>" 
 								+ "<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
 								+ coedata[i].psaCat
 								+"'></td>" 
 								+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
 								+ coedata[i].brand
 								+"'></td>" 
 								+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode' placeholder='12345' maxlength='5' disabled value= '"
						 		+ coedata[i].bpCode
						 		+"'></td>"
 								+ "<td><input name='coelaunchbase' type='text' class='form-control validfield descrip' placeholder='Description' maxlength='255' disabled value= '"
						 		+ coedata[i].bpDisc
						 		+"'></td>" 
 								+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield mrpIn' placeholder='0000' disabled value= '"
						 		+ coedata[i].mrp
						 		+"'></td>" 
 								+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield turIn' placeholder='0000' disabled value= '"
						 		+ coedata[i].tur
						 		+"'></td>" 
 								+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield gsvIn' placeholder='0000' disabled value= '"
						 		+ coedata[i].gsv
						 		+"'></td>" 
 								+ "<td><input name='coelaunchbase' type='text' class='form-control validfield clvIn' placeholder='0000' maxlength='4' disabled value= '"
						 		+ coedata[i].cldConfig
						 		+"'></td>" 
 								+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield grmIn' placeholder='0000' disabled value= '"
						 		+ coedata[i].grammage
						 		+"'></td>" 
 								+ "<td><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
 								+ coedata[i].classification
 								+"'></td></tr>";
 					}
 					
 					$('#coe_basepack_add tbody').empty().append(row);
 					//$.fn.dataTable.ext.errMode = 'none';
 				    $("#coe_basepack_add").dataTable().fnDestroy();
 				    setTimeout(function(){
 						    var oTable = $('#coe_basepack_add').DataTable( {
 									
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
 						    
 						    
 		             }, 1200 );
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
					//console.log('hello');
				});
				return false;
         	}
}

//2nd screen download

function coedownloadLaunchBasepackTemplate() {
	var launchIdsArr =  getlaunchId().toString();
	
	window.location.assign("/VisibilityAssetTracker/"+launchIdsArr+"/downloadLaunchCoeBasepackTemplate.htm");  //Sarin
	//window.open("/VisibilityAssetTracker/"+launchIdsArr+"/downloadLaunchCoeBasepackTemplate.htm");
	//window.location.assign(launchIdsArr+"/downloadFinalBuildUpKamTemplate.htm");
	
}

//get screen 3 data

function coeSaveBasepacks(){
	
	var launchIdsArr =  getlaunchId().toString();
	 $.ajax({
         url: 'getAllCompletedFinalLaunchData.htm',
         dataType: 'json',
         type: 'post',
         contentType: 'application/json',
         data:  JSON.stringify({ launchIds: launchIdsArr }),
         processData: false,
         beforeSend: function() {
             ajaxLoader(spinnerWidth, spinnerHeight);
         },
         success: function( coedata, textStatus, jQxhr ){
         	 $('.loader').hide();
             console.log(coedata);
             //$('#successblockUpload').show().find('span').html(' Launch Created Successfully !!!');
             $("#coelaunchStoresTab").click();
	    var row = "";
			for (var i = 0; i < coedata.responseData.listLaunchCoeFinalPageResponse.length; i++) {
				row += "<tr><td><input name='coelaunchbase' type='text' class='form-control lanchnm' readonly value= '"
				 		+ coedata.responseData.listLaunchCoeFinalPageResponse[i].launchName
				 		+"'></td>" 
						+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control skunam' readonly value= '"
						+ coedata.responseData.listLaunchCoeFinalPageResponse[i].skuName
						+"'></td>" 
						+ "<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control bspckcd' readonly value= '"
						+ coedata.responseData.listLaunchCoeFinalPageResponse[i].basepackCode
						+"'></td>" 
						+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control moc' readonly value= '"
						+ coedata.responseData.listLaunchCoeFinalPageResponse[i].moc
						+"'></td>" 
						+"<td><input name='coelaunchbase' type='text' class='form-control validfield sellinval' disabled value= '"
				 		+ coedata.responseData.listLaunchCoeFinalPageResponse[i].sellInValN
				 		+"'></td>"
				 		+"<td><input name='coelaunchbase' type='text' class='form-control validfield sellinvaln1'disabled value= '"
				 		+ coedata.responseData.listLaunchCoeFinalPageResponse[i].sellInValN1
				 		+"'></td>"
				 		+"<td><input name='coelaunchbase' type='text' class='form-control validfield sellinval2' maxlength='5' disabled value= '"
				 		+ coedata.responseData.listLaunchCoeFinalPageResponse[i].sellInValN2
				 		+"'></td>"
						+ "<td><input name='coelaunchbase' type='text' class='form-control validfield sellinvalcld' maxlength='255' disabled value= '"
				 		+ coedata.responseData.listLaunchCoeFinalPageResponse[i].sellInCldN
				 		+"'></td>" 
				 		+ "<td><input name='coelaunchbase' type='text' class='form-control validfield sellinvalcld1' maxlength='255' disabled value= '"
				 		+ coedata.responseData.listLaunchCoeFinalPageResponse[i].sellInCldN1
				 		+"'></td>" 
				 		+ "<td><input name='coelaunchbase' type='text' class='form-control validfield sellinvalcld2' maxlength='255' disabled value= '"
				 		+ coedata.responseData.listLaunchCoeFinalPageResponse[i].sellInCldN2
				 		+"'></td>" 
						+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield sellinvalunit' disabled value= '"
				 		+ coedata.responseData.listLaunchCoeFinalPageResponse[i].sellInUnitN
				 		+"'></td>" 
				 		+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield sellinvalunitn1' disabled value='"
				 		+ coedata.responseData.listLaunchCoeFinalPageResponse[i].sellInUnitN1
				 		+"'></td>" 
				 		+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield sellinvalunitn2' disabled value= '"
				 		+ coedata.responseData.listLaunchCoeFinalPageResponse[i].sellInUnitN2
				 		+"'></td></tr>";
			}
			
				$('#coe_basepack_launch_build tbody').empty().append(row);

				//$.fn.dataTable.ext.errMode = 'none';
		    $("#coe_basepack_launch_build").dataTable().fnDestroy();
		    setTimeout(function(){
				    var oTable = $('#coe_basepack_launch_build').DataTable( {
							
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

//download screen 3 
function coedownloadLaunchBuildTemplate() {
	var launchIdsArr =  getlaunchId().toString();
	
	window.location.href = "/VisibilityAssetTracker/"+launchIdsArr+"/downloadLaunchBuildUpCoeTemplate.htm"
	
}

//get screen 4 data

function saveBuildUp(){
	
	var launchIdsArr =  getlaunchId().toString();
	 $.ajax({
         url: 'getAllCompletedListingTracker.htm',
         dataType: 'json',
         type: 'post',
         contentType: 'application/json',
         data:  JSON.stringify({ launchIds: launchIdsArr }),
         processData: false,
         beforeSend: function() {
             ajaxLoader(spinnerWidth, spinnerHeight);
         },
         success: function( coelstdata, textStatus, jQxhr ){
         	 $('.loader').hide();
             if(coelstdata.responseData.Error != undefined){
            	 ezBSAlert({
 					messageText : "Please check details again",
 					alertType : "info"
 				}).done(function(e) {
 					//console.log('hello');
 				});
 				return false;
             }
             else{
             //$('#successblockUpload').show().find('span').html(' Launch Created Successfully !!!');
             $("#coelaunchSellInTab").click();
            var row = "";
			for (var i = 0; i < coelstdata.responseData.listOfCoeClusterResponse.length; i++) {
				row += "<tr><td class='coelist'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
			 		+ coelstdata.responseData.listOfCoeClusterResponse[i].launchName
			 		+"'></td>" +"<td class='coelist'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
						+ coelstdata.responseData.listOfCoeClusterResponse[i].launchMoc
						+"'></td>" 
						+ "<td class='coelist'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
						+ coelstdata.responseData.listOfCoeClusterResponse[i].skuName
						+"'></td>" 
						+"<td class='coelist'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
						+ coelstdata.responseData.listOfCoeClusterResponse[i].basepackCode
						+"'></td>" 
						+"<td class='coelist'><input name='coelaunchbase' type='text' class='form-control validfield baseCode' maxlength='5' disabled value= '"
				 		+ coelstdata.responseData.listOfCoeClusterResponse[i].account
				 		+"'></td>"
							+ "<td class='coelist'><input name='coelaunchbase' type='text' class='form-control validfield descrip' maxlength='255' disabled value= '"
				 		+ coelstdata.responseData.listOfCoeClusterResponse[i].cluster
				 		+"'></td></tr>";
			}
			
			$('#list_track_table tbody').empty().append(row);
				
				//$.fn.dataTable.ext.errMode = 'none';
		    $("#list_track_table").dataTable().fnDestroy();
		    setTimeout(function(){
				    var oTable = $('#list_track_table').DataTable( {
							
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
		    $('#list_track_table_info').css({
                'text-align': 'left',
                'padding': '20px 0'
            });
             }
			},
			error: function( jqXhr, textStatus, errorThrown ){
			    console.log( errorThrown );
			}
			});
}

//screen 4 download
function coedownloadListTemplate() {
	var launchIdsArr =  getlaunchId().toString();
	
	window.location.assign("/VisibilityAssetTracker/"+launchIdsArr+"/downloadLaunchListingTrackerCoeTemplate.htm");  //Sarin prod changes
	//window.open("/VisibilityAssetTracker/"+launchIdsArr+"/downloadLaunchListingTrackerCoeTemplate.htm");
	
}


//screen 5 save

function nextListTrackTable(){
	
	// $("#coelaunchVisiTab").click();
	var launchIdsArr =  getlaunchId().toString();
	 $.ajax({
      url: 'getMstnClearanceByLaunchIdCoe.htm',
      dataType: 'json',
      type: 'post',
      contentType: 'application/json',
      data:   JSON.stringify({ launchIds: launchIdsArr }),
      processData: false,
      beforeSend: function() {
          ajaxLoader(spinnerWidth, spinnerHeight);
      },
      success: function( mstnData, textStatus, jQxhr ){
      	 $('.loader').hide();
      	$('#successblock').hide();
         $('#coelaunchVisiTab').click();
      	 var mstnlen = mstnData.responseData.launchMstnClearanceResponseCoeList.length;
         var mstn = mstnData.responseData.launchMstnClearanceResponseCoeList;
			var sellrow = "";
			for (var i = 0; i < mstnlen; i++) {
				sellrow += "<tr><td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].launchName +"'></td>" 
						+"<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].bpCode +"'></td>" 
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].bpDescription+"'></td>"
						+"<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].launchMoc +"'></td>" 
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].depot +"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].cluster +"'></td>"
						+"<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].account +"'></td>" 
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].finalCldN +"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].finalCldN1 +"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].finalCldN2 +"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].mstnCleared +"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].currentEstimates +"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].clearanceDate +"'></td></tr>";
			}
			
			$('#mstnClearance tbody').empty().append(sellrow);
	   
			// Destroy the table
	        // Use $("#dailyNews").DataTable().destroy() for DataTables 1.10.x
			
			// $.fn.dataTable.ext.errMode = 'none';
	         $("#mstnClearance").dataTable().fnDestroy();
	         setTimeout(function(){
	        	 mstnoTable = $('#mstnClearance').DataTable( {
	        		 		"scrollX": true,
				    		"scrollY": "280px",
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
				    
	    	}, 800);  
        
      },
      error: function( jqXhr, textStatus, errorThrown ){
          console.log( errorThrown );
      }
  });
}

//screen 5 download
function coedownloadMstnClrTemplate() {
	var launchIdsArr =  getlaunchId().toString();
	
	window.open("/VisibilityAssetTracker/"+launchIdsArr+"/downloadMstnClearanceTemplateCoe.htm");
	
}
function coeMstnClear(){
	
	 $("#coelaunchFinBuiUpTab").click();
	 
}
//download Annexure 
function coedownloadAnnex() {
	var launchIdsArr =  getlaunchId();
	for(var i=0; i<launchIdsArr.length; i++){
		//window.open( "/VisibilityAssetTracker/"+launchIdsArr+"/downloadAnnexureListDataCoe.htm" );
		//window.open(launchIdsArr[i]+"/downloadAnnexureListDataCoe.htm");
		window.location.assign(launchIdsArr[i]+"/downloadAnnexureListDataCoe.htm");  //Sarin changes
	}
	
}

//download Artwork
function coedownloadArtWork() {
	var launchIdsArr =  getlaunchId();
	$.ajax({
        type: "get",
        url: "getLaunchArtworkPackshotsDocument.htm?launchId="+launchIdsArr,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
        success: function (strdata) {
        	for(var i = 0; i < strdata.responseData.length; i++) {
        		var files = strdata.responseData[i].split(",");
        		for(var j = 0; j < files.length; j++) {
        			if(files[j] != "") {
        				window.open(files[j]+"/downloadFileTemplateCoe.htm");
        			}
        		}
        	}
        	 $('.loader').hide();
        	// console.log(strdata.storeCount);
        	 $('#storecount').val(strdata.storeCount);
        }
     });
	
}

//download MDG deck 
function coedownloadMdgDeckTemplate() {
	var launchIdsArr =  getlaunchId();
	for(var i = 0; i< launchIdsArr.length; i++){
		//window.open(launchIdsArr[i]+"/downloadMdgDeckDataCoe.htm");
		window.location.assign(launchIdsArr[i]+"/downloadMdgDeckDataCoe.htm"); //Sarin changes
	}
	
}

//download last screen final store 
function coedownloadFinalStrLst() {
	var launchIdsArr =  getlaunchId();
	var launchIdsStr = launchIdsArr.join();
	//for(var i=0; i<=launchIdsArr.length; i++){
		window.location.href = "/VisibilityAssetTracker/"+launchIdsStr+"/downloadLaunchStoreListCoe.htm"
//	}
	
}
//last screen save	

function coeDocument(){
	
	var launchIdsArr =  getlaunchId();
	var LaunchStr    = launchIdsArr.join();
	 $.ajax({
         url: ""+LaunchStr+'/getLaunchStoreListCoe.htm',
         dataType: 'json',
         type: 'get',
         contentType: 'application/json',
         //data:  JSON.stringify({ launchIds: LaunchStr }),
         processData: false,
         beforeSend: function() {
             ajaxLoader(spinnerWidth, spinnerHeight);
         },
         success: function( coestrlstdata, textStatus, jQxhr ){
         	 $('.loader').hide();
             console.log(coestrlstdata);
             $("#coelaunchSubTab").click();
           if(coestrlstdata.responseData === ''){
            	 ezBSAlert({
 					messageText : "Please check details again",
 					alertType : "info"
 				}).done(function(e) {
 					//console.log('hello');
 				});
 				return false;
             }
             else{
             //$('#successblockUpload').show().find('span').html(' Launch Created Successfully !!!');
             $("#coelaunchSubTab").click();
            var row = "";
			/*
			for (var i = 0; i < coestrlstdata.responseData.listOfCoeLaunchStoreListResponse.length; i++) {
				row += "<tr><td><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
			 		+ coestrlstdata.responseData.listOfCoeLaunchStoreListResponse[i].launchName
			 		+"'></td>" 
						+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
						+ coestrlstdata.responseData.listOfCoeLaunchStoreListResponse[i].skuName
						+"'></td>" 
						+ "<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
						+ coestrlstdata.responseData.listOfCoeLaunchStoreListResponse[i].basepackCode
						+"'></td>" 
						+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
						+ coestrlstdata.responseData.listOfCoeLaunchStoreListResponse[i].moc
						+"'></td>" 
						+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode' maxlength='5' disabled value= '"
			 		+ coestrlstdata.responseData.listOfCoeLaunchStoreListResponse[i].sellInValN
			 		+"'></td>"
						+ "<td><input name='coelaunchbase' type='text' class='form-control validfield descrip' maxlength='255' disabled value= '"
			 		+ coestrlstdata.responseData.listOfCoeLaunchStoreListResponse[i].sellInCld
			 		+"'></td>" 
			 		+"<td><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
			 		+ coestrlstdata.responseData.listOfCoeLaunchStoreListResponse[i].launchName
			 		+"'></td>" 
					+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
					+ coestrlstdata.responseData.listOfCoeLaunchStoreListResponse[i].skuName
					+"'></td>" 
					+ "<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
					+ coestrlstdata.responseData.listOfCoeLaunchStoreListResponse[i].basepackCode
					+"'></td>" 
					+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
					+ coestrlstdata.responseData.listOfCoeLaunchStoreListResponse[i].moc
					+"'></td>" 
					+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode' maxlength='5' disabled value= '"
			 		+ coestrlstdata.responseData.listOfCoeLaunchStoreListResponse[i].sellInVal
			 		+"'></td></tr>";
			}
			*/
			
			
				for (var i = 1; i < coestrlstdata.responseData.length; i++) {
					row += "<tr><td><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
			 		+ coestrlstdata.responseData[i][0]
			 		+"'></td>" 
					
					+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode'  disabled value= '"
			 		+ coestrlstdata.responseData[i][1]
			 		+"'></td>"
				
					+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode'  disabled value= '"
			 		+ coestrlstdata.responseData[i][2]
			 		+"'></td>"
				
					+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode'  disabled value= '"
			 		+ coestrlstdata.responseData[i][3]
			 		+"'></td>"
					
					+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode'  disabled value= '"
			 		+ coestrlstdata.responseData[i][5]
			 		+"'></td>"
				
					+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode'  disabled value= '"
			 		+ coestrlstdata.responseData[i][4]
			 		+"'></td>"
					
					+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode'  disabled value= '"
			 		+ coestrlstdata.responseData[i][6]
			 		+"'></td>"
				
					+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode'  disabled value= '"
			 		+ coestrlstdata.responseData[i][7]
			 		+"'></td>"
				
					+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode'  disabled value= '"
			 		+ coestrlstdata.responseData[i][8]
			 		+"'></td>"
				
					+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode'  disabled value= '"
			 		+ coestrlstdata.responseData[i][9]
			 		+"'></td>"
						
					+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode'  disabled value= '"
			 		+ coestrlstdata.responseData[i][10]
			 		+"'></td>"
					
					+"</tr>";
				}
			
				$('#coefinalTable tbody').empty().append(row);
				 $("#coefinalTable").dataTable().fnDestroy();
				setTimeout(function(){
					var oTable = $('#coefinalTable').DataTable( {"searching": false, "paging":true, "ordering": false,"scrollY":  "280px", })
            	}, 300)
			 }
			},
			error: function( jqXhr, textStatus, errorThrown ){
			    console.log( errorThrown );
			}
			});
}

//screen 7 last screen download
/*function coedownloadMstnClrTemplate() {
	var launchIdsArr =  getlaunchId().toString();
	
	window.open("/VisibilityAssetTracker/"+launchIdsArr+"/downloadLaunchCoeBasepackTemplate.htm");
	
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

//Q1 sprint feb 2021 kavitha starts
function loadCoeLauches(coeselectedmoc) {
	coeeoTable = $('#coebasepack_add').DataTable( {
		"scrollY":       "280px",
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
				  

			  },
			"sAjaxSource" : "getAllCoeMOCData.htm",
			  "fnServerParams" : function(aoData) {
					aoData.push({ "name": "coeMoc", "value": coeselectedmoc });
				},
				//"aaData": data,
				"aoColumns" : [
						{
						  mData: 'launchId',
						  "mRender": function(data, type, full) {
							return '<input type="checkbox" name="editLaunchscr1" class="editlaunchsel" onClick="tmeselect()" value=' + data + '>';
						  }
						},
						{mData : 'launchName'},
						//{mData : 'launchMoc'},
						{
						  mData: 'launchMoc',
						  //"mRender": function(data, type, full) {
							//return full.launchMoc + '<input type = "hidden" class="mocDate"  value=' + full.launchDate + '>';
						  //}
						},
						{mData : 'createdDate'},
						{mData : 'createdBy'},
						{mData : 'accountName'},
					],
			});
	coeeoTable.draw();
}
