/**
/ * SC Launch script
 */

var spinnerWidth = "100";
var spinnerHeight = "100";
var isManualClick = false;
var bscaddTable,
	scTable;

$(document).ready(function() {
	$.ajaxSetup({ cache: false });
	// prev button
	$('#scprevbspack').click(function(){
		 $("#sclaunchDetailsTab").click();
		 $('#sc_basepack_add').dataTable().fnDestroy();
		 setTimeout(function(){
			 bscaddTable.draw();
		 }, 300);
	});
	$('#scprevlnchBulid').click(function(){
		 $("#sclaunchBasepackTab").click();
		 $('#sc_basepack_launch_build').dataTable().fnDestroy();
	});
	$('#scprevmstn').click(function(){
		 $("#sclaunchStoresTab").click();
		 $('#successblock').hide();
	});
	$('#scprevdoc').click(function(){
		 $("#sclaunchVisiTab").click();
	});
	
	/*$('#scmstnNext').click(function(){
		 $("#sclaunchFinBuiUpTab").click();
	});*/
	
	 $("[data-hide]").on("click", function() {
         $(this).closest("." + $(this).attr("data-hide")).hide();
     });
	  bscaddTable = $('#coebasepack_add').DataTable( {
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
	  //upload mstn
	
 $("#btnUploadmstn").click(function (event) {
			event.preventDefault();
	  var fileName = $('#uploadmstn').val();
		if (fileName == '') {
			$('#uploadErrorMsg').show().html("Please select a file to upload");
			return false;
		} else {
			$('#uploadErrorMsg').hide();
			var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
			if (FileExt != "xlsx") {
				if (FileExt != "xls") {
					$('#uploadErrorMsg').show().html("Please upload .xls or .xlsx file");
					$("#uploadErrorMsg").submit(function(e) {
						e.preventDefault();
					});
					return false;
				}

			}
		}

		// Get form
		var form = $('#launchmstnfileupload')[0];

		// Create an FormData object
		var data = new FormData(form);

		// If you want to add an extra field for the FormData
		// data.append("CustomField", "This is some extra data, testing");

		// disabled the submit button
		// $("#btnSubmitBasePack").prop("disabled", false);

		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url :  'uploadMstnClearanceByLaunchIdSc.htm',
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
				 $('.loader').hide();
				 var msdata = JSON.parse(data);
				 $("#launchmstnfileupload")[0].reset()
				if ("SUCCESS_FILE" == msdata.Success) {
				//	$("#sclaunchFinBuiUpTab").click();
					//isFileTwoUploaded = true;
					 $("#mstnClearance").dataTable().fnDestroy();
					$('#uploadartErrorMsg').hide();
					//$('#mdglnc').hide();
					getmstnData();
					$('#successblock').show().find('span').html(' Files Uploaded Successfully !!!');
					//$( '#artfile' ).parent().removeClass( 'btn-primary' ).addClass('btn-success').find( 'i' ).attr( 'class', 'glyphicon glyphicon-ok' ).parent().find( 'span' ).html( "&nbsp;" );

				} else if( typeof msdata.Error == "string" && msdata.Error != "" ) {
					$('#successblock').hide();
					$('#errorblockUpload span').html(msdata.Error.replace('java.lang.Exception: ', ""));
					$('#errorblockUpload').show();
				}
				//return false;
				// $("#launchstrFileUploadBtn").prop("disabled", false);
			},
			error : function(e) {
				$("#uploadErrorMsg").text(e.responseText);
				// console.log("ERROR : ", e);
				// $("#launchstrFileUploadBtn").prop("disabled", true);

			}
		});
});
	  
	 
	
	if( window.location.hash != "#step-1" && window.location.hash != '' ){
		window.location = window.location.href.split('#')[0];
	}

	$( document ).on( 'mousedown', '#smartwizard ul.step-anchor a, #smartwizard ul.step-anchor li', function(e){
		isManualClick = true;
	} );
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
									$('<button></button>').text('Finish')
											.addClass('btn btn-info')
											.on('click', function() {
												alert('Finsih button click');
											}),
									$('<button></button>').text('Cancel')
											.addClass('btn btn-danger')
											.on('click', function() {
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

function enableScLaunchButtons(obj) {
	var checked_field = $("[name=scchecklaunch]:checked");
	if (checked_field.length != 0) {
		document.getElementById("sclnchDets").removeAttribute("disabled");
	} else {
		document.getElementById("sclnchDets").setAttribute("disabled",
				"disabled");
	}
}

function scLaunch() {
	var checked_field = $("[name=scchecklaunch]:checked");
	var launchDate = "";
	var launchMocDate = "";
	if (checked_field.length != 0) {
		var launchIds = [];
		for (var i = 0; i < checked_field.length; i++) {
			launchIds.push(checked_field[i].value);
		}
		var set1 = new Set(launchIds);
		var commaSepLaunchIds = Array.from(set1).toString();
		if (commaSepLaunchIds.length != 0) {
			$.ajax({
				url : 'getAllBasePackByLaunchIdsSc.htm',
				type : 'POST',
				contentType : 'application/json',
				dataType : 'JSON',
				data : JSON.stringify({
					'launchIds' : commaSepLaunchIds
				}),
				beforeSend : function() {
					ajaxLoader(spinnerWidth, spinnerHeight);
				},
				success : function(data, textStatus, jQxhr) {
					$('.loader').hide();
					setBasepacksForSc(data);
				},
				error : function(jqXhr, textStatus, errorThrown) {
					console.log(errorThrown);
				}
			});
		} else {
			alert("No launch Selected");
		}
	}

}

function setBasepacksForSc(data) {
	$("#sclaunchBasepackTab").click();
	var statusResp = data.status.code;
	if(parseInt(statusResp) < 0) {
		alert("Something went wrong");
	} else {
		var responseData = data.responseData.listLaunchDataResponse;
		$("#sc_basepack_add tbody").empty();
		var row = "";
		for(var i = 0; i < responseData.length; i++) {
			row += "<tr> <td>"+responseData[i].launchName+"</td> <td>"+responseData[i].salesCat+" </td>" +
					"<td>"+responseData[i].psaCat+" </td><td>"+responseData[i].brand+" </td><td>"+
					responseData[i].bpCode+" </td><td>"+responseData[i].bpDisc+" </td><td>"+
					responseData[i].mrp+" </td><td> "+responseData[i].tur+"</td><td>"+ 
					responseData[i].gsv+" </td><td>"+responseData[i].cldConfig+" </td><td> "+
					responseData[i].grammage+"</td><td>"+responseData[i].classification+" </td></tr>";
		}
		$("#sc_basepack_add tbody").append(row);
		setTimeout(function(){
		 scTable = $('#sc_basepack_add').DataTable( {
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
		}, 500);
	}
}

function scSaveBasepacks() {
	var checked_field = $("[name=scchecklaunch]:checked");
	var launchDate = "";
	var launchMocDate = "";
	if (checked_field.length != 0) {
		var launchIds = [];
		for (var i = 0; i < checked_field.length; i++) {
			launchIds.push(checked_field[i].value);
		}
		var set1 = new Set(launchIds);
		var commaSepLaunchIds = Array.from(set1).toString();
		if (commaSepLaunchIds.length != 0) {
			$.ajax({
				url : 'getAllFinalBuildUpByLaunchIdsSc.htm',
				type : 'POST',
				contentType : 'application/json',
				dataType : 'JSON',
				data : JSON.stringify({
					'launchIds' : commaSepLaunchIds
				}),
				beforeSend : function() {
					ajaxLoader(spinnerWidth, spinnerHeight);
				},
				success : function(data, textStatus, jQxhr) {
					$('.loader').hide();
					setBuildUpForSc(data);
				},
				error : function(jqXhr, textStatus, errorThrown) {
					console.log(errorThrown);
				}
			});
		} else {
			alert("No launch Selected");
		}
	}

}

function setBuildUpForSc(data) {
	$("#sclaunchStoresTab").click();
	var statusResp = data.status.code;
	if(parseInt(statusResp) < 0) {
		alert("Something went wrong");
	} else {
		var responseData = data.responseData.launchScFinalBuildUpResponse;
		$("#sc_basepack_launch_build tbody").empty();
		var row = "";
		if(responseData.length > 0) {
			for(var i = 0; i < responseData.length; i++) {
				row += "<tr> <td>"+responseData[i].launchName+"</td> <td>"+responseData[i].skuName+" </td>" +
						"<td>"+responseData[i].basepackCode+" </td><td>"+responseData[i].launchSellInValueN+" </td><td>"+
						responseData[i].launchSellInValueN1+" </td><td>"+responseData[i].launchSellInValueN2+" </td><td>"+
						responseData[i].launchSellInCldValueN+" </td><td> "+responseData[i].launchSellInCldValueN1+"</td><td>"+ 
						responseData[i].launchSellInCldValueN2+" </td><td>"+responseData[i].launchSellInUnitsN+" </td><td> "+
						responseData[i].launchSellInUnitsN1+"</td><td>"+responseData[i].launchSellInUnitsN2+" </td></tr>";
			}
		} else {
			row += "<tr> <td></td></tr>";
		}
		$("#sc_basepack_launch_build tbody").append(row);
		 setTimeout(function(){
		 var sclnTable = $('#sc_basepack_launch_build').DataTable( {
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
		 }, 1200);

	}
}

function scdownloadLaunchBuildUpTemplate() {
	var checked_field = $("[name=scchecklaunch]:checked");
	
	if (checked_field.length != 0) {
		var launchIds = [];
		for (var i = 0; i < checked_field.length; i++) {
			launchIds.push(checked_field[i].value);
		}
		var set1 = new Set(launchIds);
		var commaSepLaunchIds = Array.from(set1).toString();
		window.location.assign(commaSepLaunchIds+"/downloadFinalBuildUpTemplateSc.htm");
	} else {
		alert("No Launch Selected");
	}
}


/// clearance table

function getmstnData() {
	//var launchId = $("#dynamicLaunchId").val();
	var checked_field = $("[name=scchecklaunch]:checked");
	
	if (checked_field.length != 0) {
		var launchIds = [];
		for (var i = 0; i < checked_field.length; i++) {
			launchIds.push(checked_field[i].value);
		}
		var set1 = new Set(launchIds);
		var commaSepLaunchIds = Array.from(set1).toString();
		if (commaSepLaunchIds.length != 0) {
			$.ajax({
			    url: 'getAllMstnClearanceByLaunchIdsSc.htm',
			    dataType: 'json',
			    data : JSON.stringify({
					'launchIds' : commaSepLaunchIds
				}),
			    type: 'post',
			    contentType: 'application/json',
			    processData: false,
			    beforeSend: function() {
		            ajaxLoader(spinnerWidth, spinnerHeight);
		        },
			    success: function( mstnData, textStatus, jQxhr ){
			    	
		            $('.loader').hide();
		            $("#sclaunchVisiTab").click();
			    				    	   	
		            var mstnlen = mstnData.responseData.launchScMstnClearanceResponseList.length;
		            var mstn = mstnData.responseData.launchScMstnClearanceResponseList;
					var sellrow = "";
					for (var i = 0; i < mstnlen; i++) {
						sellrow += "<tr><td><input name='childRatio1' type='text' class='form-control l1chain' readonly value= '"
							 	+ mstn[i].launchName
							 	+"'></td>"
								+"<td><input name='childRatio1' type='text' class='form-control l2chain' readonly value= '" +
								mstn[i].launchMoc +"'></td>" 
								+"<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
								mstn[i].basepackCode+"'></td>" 
								+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
								mstn[i].basepackDesc+"'></td>"
								+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
								mstn[i].depot+"'></td>"
								+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
								mstn[i].cluster+"'></td>"
								+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
								mstn[i].account+"'></td>"
								+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
								mstn[i].finalCldN+"'></td>"
								+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
								mstn[i].finalCldN1+"'></td>"
								+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
								mstn[i].finalCldN2+"'></td>"
								+ "<td class='mstnInp'><input name='childRatio1' type='text' class='form-control' value= '" +
								(typeof mstn[i].mstnCleared != "undefined" ? mstn[i].mstnCleared : "" ) +"'></td>"
								
								+ "<td class='mstnInp'><input name='childRatio1' type='text' class='form-control' value= '" +
								(typeof mstn[i].currentEstimates != "undefined" ? mstn[i].currentEstimates : "" )+"'></td>"
								+ "<td class='mstnInp'><input name='childRatio1' type='text' class='form-control mstnInp' value= '" +
								(typeof mstn[i].clearanceDate != "undefined" ? mstn[i].clearanceDate : "" )+"'></td></tr>";
					}
					
					$('#mstnClearance tbody').empty().append(sellrow);
					 $('#mstnClearance .mstnInp').each(function(){
						 var mstIp =  $(this).find('input').val();
						 if(mstIp != ''){
						 $(this).find('input').prop("readonly", true);
						 }else{
						 $(this).find('input').prop("readonly", false);
						 }

						 });
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
	}

}

//download mstn
function scdownloadLaunchmstn() {
	//var launchId = $("#dynamicLaunchId").val();
	
var checked_field = $("[name=scchecklaunch]:checked");
	
		var launchIds = [];
		for (var i = 0; i < checked_field.length; i++) {
			launchIds.push(checked_field[i].value);
		}
		var set1 = new Set(launchIds);
		var commaSepLaunchIds = Array.from(set1).toString();
		
	window.location.assign(commaSepLaunchIds+"/downloadMstnClearanceTemplateSc.htm");
}

//upload for mstn

//function mstnupload(isExistingUpload) {
	//var launchId = $("#dynamicLaunchId").val();
	/*var checked_field = $("[name=scchecklaunch]:checked");
	var launchIds = [];
	for (var i = 0; i < checked_field.length; i++) {
		launchIds.push(checked_field[i].value);
	}
	var set1 = new Set(launchIds);
	var commaSepLaunchIds = Array.from(set1).toString();*/
	
	//

//}



function ajaxLoader(w, h) {

    var left = (window.innerWidth / 2) - (w / 2);
    var top = (window.innerHeight / 2) - (h / 2);
    $('.loader').css('display', 'block');

    $('.loading-image').css({
        "left": left,
        "top": top,
        
    });
}