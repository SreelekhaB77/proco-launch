 var spinnerWidth = "100";
 var spinnerHeight = "100";
 var isManualClick = false;

 
 $(document).ready(function() {
	 $.ajaxSetup({ cache: false });
	 
	 $('#successblockapp').hide();
		if( window.location.hash != "#step-1" && window.location.hash != '' ){
			window.location = window.location.href.split('#')[0];
		}

		$( document ).on( 'mousedown', '#smartwizard ul.step-anchor a, #smartwizard ul.step-anchor li', function(e){
			isManualClick = true;
		} );
		
		  $('#rejLnh').attr("disabled",true); 	
		  $( "#tmeRejectRemarks" ).keyup(function() {
			  if($('#tmeRejectRemarks').val() != '' ){
				  $('#rejLnh').attr("disabled",false); 
			  }
			  else{
				  $('#rejLnh').attr("disabled",true); 	
			  }
		  });
//previous button 
		
	$('#prevKamin').click(function(){
		 $("#launchEditDetailsTab").click();
		 setTimeout(function(){
			 edtmeTable.draw();
		 }, 300);
		 $('#successblockapp').hide();
	});
	$('#prevKamQue').click(function(){
		 $("#launchEditKamInp").click();
	});
	$('#prevKamMst').click(function(){
		 $("#launchEditQuer").click();
	});
	
	$('#tmeRejCancelBtn').click(function(){
		 $("#apprv").attr("disabled",false); 
		 $("#rjct").attr("disabled",false); 
	});
	
// screen 1 datatable
		// select only 1 checkbox
		$('input[type="checkbox"]').on('change', function() {
			   $('input[type="checkbox"]').not(this).prop('checked', false);
			 
			});
			
			loadTmeLauches('All','All');
			//loadTmeLaunchName('All','All');
	//Q1-sprint UI issues fixes
		//$("#editDet").dataTable().fnDestroy();
  				   // setTimeout(function(){
  						  //  var edtmeTable = $('#editDet').DataTable( {
  									
  							//		"scrollY":       "280px",
  									/*"scrollX":        "300px",
  							        "scrollCollapse": true,*/
  							  //      "paging":         true,
  							   //     "ordering": false,
  							   //     "searching": false,
  							    //	"lengthMenu" : [
  								//		[ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
  									//	[ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ] ],
  							     //   "oLanguage": {
  						             //     "sSearch": '<i class="icon-search"></i>',
  						            //      "oPaginate": {
  						              //        "sNext": "&rarr;",
  						                 //     "sPrevious": "&larr;"
  						                 // },
  						                 // "sLengthMenu": "Records per page _MENU_ ",
  						                 // "sEmptyTable": "No Pending Launch."
  						
  						             // }
  				    	    	//    });
  						    
  						    
  		        //     }, 800 );

  				   
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
										 ]
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
		
//upload kam input
		$("#btnSubmitBasePack").click(function (event) {
			event.preventDefault();
			//var launchId = $("#dynamicLaunchId").val();
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
						$("#launchApprovStatupload").submit(function(e) {
							e.preventDefault();
						});
						return false;
					}

				}
			}
			
	        // Get form
	        var form = $('#launchApprovStatupload')[0];

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
	            url: 'uploadKamRequests.htm',
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
	                if("SUCCESS_FILE" ==  upldatanew.Success) {
	                	 $("#launchEditQuer").click();
	                	 $('#errorblockUpload').hide();
	                	 editLaunchscr();
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
		
	//Q1 sprint feb 2021 kavitha	
	$("#mocCol").on('change', function () {
		$("#kamlaunchDetailsTab").click();
		$("#editDet").dataTable().fnDestroy();
		//kambaseoTable.draw();
		var tmeselectedmoc = $(this).val(); //'All';
		
		//Q2 sprint feb 2021 kavitha - Starts
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "getAlltmeLaunchName.htm?tmeMoc=" + tmeselectedmoc,
			success : function(data) {
				if(data.length > 0){
					var obj = $.parseJSON(data);
					var lstLaunchName = obj.launchNameList;
					var lstLaunchNameArr = [];
					lstLaunchNameArr = JSON.parse(JSON.stringify(lstLaunchName)); //$.parseJSON(lstLaunchName);
					var myOption = $("#launchName");
					myOption.empty();
					myOption.append(new Option('All', 'All'));
					$.each(lstLaunchName, function(key, val){
						myOption.append(new Option(val, val));
					});
				}
			}
		});
		
		var tmeselectedlaunchname = $("#launchName").val();
		//loadTmeLauches(tmeselectedmoc);                    
		//loadTmeLauches(tmeselectedmoc,tmeselectedlaunchname);
		loadTmeLauches(tmeselectedmoc, 'All');
		//Q2 sprint feb 2021 kavitha - Ends
		
		$('#editDet').on('draw.dt', function() {
			 var $empty = $('#editDet').find('.dataTables_empty');
			 if ($empty) $empty.html('Loading Launches..')
		});
			     
   });	
	
	//Q2 sprint feb 2021 kavitha	
	$("#launchName").on('change', function () {
		$("#kamlaunchDetailsTab").click();
		$("#editDet").dataTable().fnDestroy();
		var tmeselectedmoc = $("#mocCol").val(); //'All';
		var tmeselectedlaunchname = $(this).val(); //'All';
		loadTmeLauches(tmeselectedmoc, tmeselectedlaunchname);
		//loadTmeLaunchName(tmeselectedmoc,tmeselectedlaunchname);
		$('#editDet').on('draw.dt', function() {
			 var $empty = $('#editDet').find('.dataTables_empty');
			 if ($empty) $empty.html('Loading Launches..')
		});
    });
	 
});
 
 
 
// Get Second screen data

 function editLaunchscr(){
	  
   
    $.ajax({
        url: 'getLaunchKamInputs.htm',
        dataType: 'json',
        type: 'GET',
        contentType: 'application/json',
        processData: false,
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
        success: function( editKamINdata, textStatus, jQxhr ){
        	 $('.loader').hide();
        	 
            console.log(editKamINdata);
            $("#launchEditKamInp").click();
            $('#rejectRequestTme').modal('hide');
           //eset $('#successblockapp').show().find('span').html(' Launch approved Successfully !!!');
            var kamIn = editKamINdata.launchKamInputsResponses;
           
            var row = "";
            if(kamIn.length > 0) {
            	for (var i = 0; i < kamIn.length; i++) {
    				row += "<tr><td><input type='checkbox' name='checkChangeRequest' class='radioln kamLnchBsckscr2' value='' onchange = 'onChangeChk()'>" +
    						"<input type='hidden' value ='"+kamIn[i].reqId+"' id='reqId"+i+"'></td>" +
    						"<td><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
    				 		+ kamIn[i].launchName
    				 		+"'></td>" 
    						+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
    						+ kamIn[i].launchMoc
    						+"'></td>" 
    						+ "<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
    						+ kamIn[i].account
    						+"'></td>" 
    						+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
    						+ kamIn[i].name
    						+"'></td>" 
    						+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode' placeholder='12345' maxlength='5' disabled value= '"
    				 		+ kamIn[i].changesRequired
    				 		+"'></td>"
    						+ "<td><input name='coelaunchbase' type='text' class='form-control validfield descrip' placeholder='Description' maxlength='255' disabled value= '"
    				 		+ kamIn[i].kamRemarks
    				 		+"'></td>" 
    						+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield mrpIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);' disabled value= '"
    				 		+ kamIn[i].requestDate
    				 		+"'></td></tr>";
    			}
            	$("#downloadEditLaunchRequestId").prop("disabled",false);
            	
            } else {
            	row += "<tr><td colspan='8'> No data found</td></tr>";
            	$("#downloadEditLaunchRequestId").prop("disabled",true);
            	$("#apprv").prop("disabled",true);
            	$("#rjct").prop("disabled",true);
            	
            }
			
			
			$('#changeRequesTable tbody').empty().append(row);
			
			//$("#changeRequesTable").dataTable().fnDestroy();
			    setTimeout(function(){
					    var oTable = $('#changeRequesTable').DataTable( {
					    	"scrollY":       "300px",
					    		"retrieve": true,
								"paging": true,
						        "ordering": false,
						        "searching": false,
						        
						        "lengthMenu" : [
									[ 10, 25, 50, 100 ],
									[  10, 25, 50, 100 ] ],
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
					    
					    //oTable.draw();
						 /*$('#dataTables_scrollHeadInner').css({
			                 'width': '100%',
			                 
			             });
						 
						 $('#changeRequesTable').css({
			                 'width': '100%',
			                 
			             });*/
						 
					    
	             }, 1200 );
			    
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
  
}
 
 
 function editLaunchscrone(){
	  // var launchIdsArr = getDplaunchId().toString();
     var chk =  $('#editDet').find('input.editlaunchsel:checked').val();
     if($(".editlaunchsel").is(":checked")){
     // console.log("true");
    	 $( "body" ).append( '<form id="tmesubmmitformtogetprevdata" action="getEditLaunchDetails.htm"><input name="launchId" value="'+chk+'"></form>' );
         $( "#tmesubmmitformtogetprevdata" ).submit();
     /*
		 * $.ajax({ url: 'getEditLaunchDetails.htm?launchId='+chk, dataType:
		 * 'json', type: 'GET', contentType: 'application/json', processData:
		 * false, beforeSend: function() { ajaxLoader(spinnerWidth,
		 * spinnerHeight); }, success: function( editDetdata, textStatus, jQxhr ){
		 
		 * $('.loader').hide(); console.log(editDetdata); //
		 * $('#successblockUpload').show().find('span').html(' Launch // Created
		 * Successfully !!!'); // $("#launchEditKamInp").click();
		 * window.location.href = "getLaunchPlanPage.htm";
		 * $('#mocMonth').val(editDetdata.moc); }, error: function( jqXhr,
		 * textStatus, errorThrown ){ console.log( errorThrown ); } });
		 */
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
 
 function downloadEditLaunchRequest() {
		window.location.assign("downloadKamInputs.htm");
	}
 
function approveRequest() {
	$("#approveRequestTme").modal();
} 

function onChangeChk() {
	var inputElems = document.getElementsByName("checkChangeRequest");
    var count = 0;
       
	for (var i=0; i<inputElems.length; i++) {   
		if (inputElems[i].type == "checkbox" && inputElems[i].checked == true) {
			count++;
		}
	}
	
	if(count > 0) {
		$('#apprv').prop('disabled', false);
		$('#rjct').prop('disabled', false);
	} else {
		$('#apprv').prop('disabled', true);
		$('#rjct').prop('disabled', true);
	}
}

function approveKamInputs() {
	var inputElems = document.getElementsByName("checkChangeRequest");
    var count = 0;
    var reqIds = [];
       
	for (var i=0; i<inputElems.length; i++) {    
	if (inputElems[i].type == "checkbox" && inputElems[i].checked == true) {
			count++;
			reqIds.push(inputElems[i].nextSibling.value);
		}
	}
	
	if(count > 0) {
		var set1 = new Set(reqIds);
		var arrOfReqIds = Array.from(set1).toString();
		var tmeRemarks = $("#tmeRemarks").val();
		if(tmeRemarks.trim().length > 0) {
			 $.ajax({
		         url: 'acceptKamInputs.htm',
		         dataType: 'json',
		         type: 'POST',
		         contentType: 'application/json',
		         processData: false,
		         data: JSON.stringify( { "acceptRemark": tmeRemarks, "reqIds" : arrOfReqIds} ),
		         beforeSend: function() {
		             ajaxLoader(spinnerWidth, spinnerHeight);
		         },
		         success: function( editDetdata, textStatus, jQxhr ){
		         	 $('.loader').hide();
		         	$('#kamRemakErrorMsg').hide();
		         	 editLaunchscr();
		         	 $('#approveRequestTme').modal('hide');
		         	 $('#successblockapp').show().find('span').html(' Request Approved Successfully !!!');
		         	
		         	 $('#apprv').prop("disabled", true);
		         	$('#rjct').prop("disabled", true);
		         	$('#tmeRemarks').val("");
		         	
		         },
		         error: function( jqXhr, textStatus, errorThrown ){
		             console.log( errorThrown );
		         }
		     });
		 } else {
			 $('#kamRemakErrorMsg').show();
				return false; 
		 }
	} else {
		ezBSAlert({
			messageText : "Please select a request",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	}
	
}

function rejectRequest() {
	$("#rejectRequestTme").modal();
	
	 $('#apprv').prop("disabled", true);
  	$('#rjct').prop("disabled", true);
} 
function querAnsSave() {
	$("#launchEditMstnClear").click();
} 


function rejectKamInputs() {
	var inputElems = document.getElementsByName("checkChangeRequest");
    var count = 0;
    var reqIds = [];
    
	for (var i=0; i<inputElems.length; i++) {    
		if (inputElems[i].type == "checkbox" && inputElems[i].checked == true) {
			count++;
			reqIds.push(inputElems[i].nextSibling.value);
		}
	}
	
	if(count > 0) {
		var set1 = new Set(reqIds);
		var arrOfReqIds = Array.from(set1).toString();
		var tmeRemarks = $("#tmeRejectRemarks").val();
		if(tmeRemarks.trim().length > 0) {
			 
			 $.ajax({
		         url: 'rejectKamInputs.htm',
		         dataType: 'json',
		         type: 'POST',
		         contentType: 'application/json',
		         processData: false,
		         data: JSON.stringify( { "rejectRemark": tmeRemarks, "reqIds" : arrOfReqIds} ),
		         beforeSend: function() {
		             ajaxLoader(spinnerWidth, spinnerHeight);
		         },
		         success: function( editDetdata, textStatus, jQxhr ){
		         	 $('.loader').hide();
		         	 $('#tmerejRemakErrorMsg').hide();
		         	 if(editDetdata == "Rejected Successfully"){
		         	  //location.reload(true);
		         		$('#successblockapp').show().find('span').html(' Request Rejected Successfully !!!');
		         	 editLaunchscr();
		         	$('#tmeRejectRemarks').val("");
		         	 }
		         },
		         error: function( jqXhr, textStatus, errorThrown ){
		             console.log( errorThrown );
		         }
		     });
		 } else {
			 $('#tmerejRemakErrorMsg').show();
				return false; 
		 }
	} else {
		ezBSAlert({
			messageText : "Please select a request",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	}
	
}
	
function querAnsSave(){
	
	// $("#coelaunchVisiTab").click();
//	var launchIdsArr =  getlaunchId().toString();
	 $.ajax({
      url: 'getAllMstnClearanceTme.htm',
      dataType: 'json',
      type: 'get',
      contentType: 'application/json',
    //  data:  data,
      processData: false,
      beforeSend: function() {
          ajaxLoader(spinnerWidth, spinnerHeight);
      },
      success: function( mstnData, textStatus, jQxhr ){
      	 $('.loader').hide();
      	$('#successblock').hide();
         $('#launchEditMstnClear').click();
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
			
			$('#mstnEditClearTable tbody').empty().append(sellrow);
	   
			// Destroy the table
	        // Use $("#dailyNews").DataTable().destroy() for DataTables 1.10.x
			
			// $.fn.dataTable.ext.errMode = 'none';
	      //   $("#mstnEditClearTable").dataTable().fnDestroy();
	         setTimeout(function(){
	        	 mstnoTable = $('#mstnEditClearTable').DataTable( {
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


function nextQueryAns() {
	$('#successblockapp').hide();
	 $.ajax({
         url: 'getLaunchQueriesAnswered.htm',
         dataType: 'json',
         type: 'GET',
         contentType: 'application/json',
         processData: false,
         beforeSend: function() {
             ajaxLoader(spinnerWidth, spinnerHeight);
         },
         success: function( qurdata, textStatus, jQxhr ){
         	 $('.loader').hide();
         	$("#launchEditQuer").click();
         	
         	 var kamQur= qurdata.listOfKamQueriesAnswered;
             
             var row = "";
            if(kamQur.length > 0) {
     			for (var i = 0; i < kamQur.length; i++) {
     				row += "<tr><td><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
     				 		+ kamQur[i].launchName
     				 		+"'></td>" 
     						+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
     						+ kamQur[i].launchMoc
     						+"'></td>" 
     						+ "<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
     						+ kamQur[i].account
     						+"'></td>" 
     						+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
     						+ kamQur[i].Name
     						+"'></td>" 
     						+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode' placeholder='12345' maxlength='5' disabled value= '"
     				 		+ kamQur[i].reqDate
     				 		+"'></td>"
     						+ "<td><input name='coelaunchbase' type='text' class='form-control validfield descrip' placeholder='Description' maxlength='255' disabled value= '"
     				 		+ kamQur[i].changesRequired
     				 		+"'></td>" 
     						+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield mrpIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);' disabled value= '"
     				 		+ kamQur[i].kamRemarks
     				 		+"'></td>" 
     				 		+ "<td><input name='coelaunchbase' type='text' class='form-control validfield descrip' placeholder='Description' maxlength='255' disabled value= '"
     				 		+ kamQur[i].responseDate
     				 		+"'></td>" 
     				 		+ "<td><input name='coelaunchbase' type='text' class='form-control validfield descrip' placeholder='Description' maxlength='255' disabled value= '"
     				 		+ kamQur[i].approvalStatus
     				 		+"'></td>" 
     				 		+ "<td><input name='coelaunchbase' type='text' class='form-control validfield descrip' placeholder='Description' maxlength='255' disabled value= '"
     				 		+ kamQur[i].tmeRemarks
     				 		+"'></td></tr>";
     			}
            } else {
            	row += "<tr><td colspan='10'></td></tr>";
            }
 			
 			$('#querAnsTable tbody').empty().append(row);
 			
 			$("#querAnsTable").dataTable().fnDestroy();
		    setTimeout(function(){
				    var oTable = $('#querAnsTable').DataTable( {
							
							"scrollY":       "280px",
					        "scrollX":        "300px",
					        "scrollCollapse": true,
					        "paging":         true,
					        "ordering": false,
					        "searching": false,
					    	"lengthMenu" : [
								[ 10, 25, 50, 100 ],
								[ 10, 25, 50, 100 ] ],
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

//Q1 sprint feb 2021 kavitha starts
function loadTmeLauches(tmeselectedmoc, tmeselectedlaunchname) {
	tmebaseoTable = $('#editDet').DataTable( {
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
			"sAjaxSource" : "getAllLaunchtmeData.htm",
			  "fnServerParams" : function(aoData) {
			       
					aoData.push({ "name": "tmeMoc", "value": tmeselectedmoc });
					aoData.push({ "name": "tmeLaunchName", "value": tmeselectedlaunchname });
					 
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
						{mData : 'launchFinalStatus'},
						 
					],
			});
	tmebaseoTable.draw();
}
//Q1 sprint feb 2021 kavitha 
function tmeselect(){
	$('input[type="checkbox"]').on('change', function() {
		
		   $('input[type="checkbox"]').not(this).prop('checked', false);
		 
		});
}

//Q2 sprint Feb 2021 kavitha starts
/*
function loadTmeLaunchName(tmeselectedmoc,tmeselectedlaunchname) {
	tmeLaunchNameTable = $('#editDet').DataTable( {
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
			"sAjaxSource" : "getAlltmeLaunchName.htm",
			  "fnServerParams" : function(aaData) {
			      
				  aaData.push({ "name": "tmeMoc", "value": tmeselectedmoc });
					aaData.push({ "name": "tmeLaunchName", "value": tmeselectedlaunchname });
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
						{mData : 'launchFinalStatus'},
						 
					],
			});
	tmeLaunchNameTable.draw();
}
*/

//Q1 sprint-3 userstory 1 TME Notification and color Bharati Code
$(document).ready( function() {      
$('#editDet').on('draw.dt', function() {
                var itemCont = 0;      
                var requestUri = "getLaunchKamInputs.htm";
                    
   
        $.ajax({      
        url: requestUri,      
        dataType: 'json',
        type: 'GET',
        contentType: 'application/json',
        processData: false,      
        headers: { "Accept": "text/plain" },    
        
        success: function(data, textStatus, jqXHR) {
          
            var kamInput = data.launchKamInputsResponses;
            if(kamInput.length == 0){
            $("#NotificationBadge").hide();
            }else{
            
           $("#NotificationBadge").html(kamInput.length);
           }
		   
 
      $("#editDet tbody tr").each(function(){
	 var launchName_val = $(this).find('td:eq(1)').html();
	 var launchMoc_val = $(this).find('td:eq(2)').html();
   
   
         if(kamInput.length > 0) {
            	for (var i = 0; i < kamInput.length; i++) {     	
           var launchMatchName 	= kamInput[i].launchName;
           var launchMatchMoc = kamInput[i].launchMoc;
     
if(launchMatchName == launchName_val && launchMatchMoc == launchMoc_val) {

$(this).addClass('red'); 
}
}
}
});
    },     
        error: function (data) {      
            console.log(data);      
        }      
    });      
});
});

		 