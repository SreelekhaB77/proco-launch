 var spinnerWidth = "100";
 var spinnerHeight = "100";
 var isManualClick = false;
 var kambaseoTable;
 var kameditapproveoTable;
 var kamApproveStatusTable;
 var yesno;
 var vssTable,
 	 bldTable,
 	sndTable;
$(document).ready(function() { 
	$.ajaxSetup({ cache: false });
	$('#mockamChange').attr("disabled",true); 
	$('#kamlnchDets').attr("disabled",true);
	$('#rejectLaunch').attr("disabled",true);
	$('#kamerrorblockUpload').hide();
	 $('#targetStoreErrorBlock').hide();
	 $('#storelist-successblock').hide();
	
	if( window.location.hash != "#step-1" && window.location.hash != '' ){
		window.location = window.location.href.split('#')[0];
	}

	$( document ).on( 'mousedown', '#smartwizard ul.step-anchor a, #smartwizard ul.step-anchor li', function(e){
		isManualClick = true;
	} );
// disable reject popup buttton
	
	 $('#KamlanchMocRejectBtn').attr("disabled",true); 	
	  $( "#kamMocRejRemarks" ).keyup(function() {
		  if($('#kamMocRejRemarks').val() != '' ){
			  $('#KamlanchMocRejectBtn').attr("disabled",false); 
		  }
		  else{
			  $('#KamlanchMocRejectBtn').attr("disabled",true); 	
		  }
	  });
// previous buttons
	$('#kamprevbspack').click(function(){
		 $("#kamlaunchDetailsTab").click();
		 $("#kam_basepack_add").dataTable().fnDestroy();
		 setTimeout(function(){
			 kambaseoTable.draw();
		 }, 300);
		 
	});
	$('#kamprevlnVal').click(function(){
		 $("#kamlaunchBasepackTab").click();
		 setTimeout(function(){
			 sndTable.draw();
		 }, 300);
		 $("#kam_basepack_launch_build").dataTable().fnDestroy();
		 
	});
	$('#kamprevstrLst').click(function(){
		 $("#kamlaunchStoresTab").click();
		 setTimeout(function(){
			 bldTable.draw();
		 }, 300);
	});
	$('#kamprevvissi').click(function(){
		 $("#kamlaunchSellInTab").click();
	});
	$('#kamprevdoc').click(function(){
		 $("#kamlaunchVisiTab").click();
		 setTimeout(function(){
			// vssTable.draw();
		 }, 300);
	});
	$('#kamprevmisDet').click(function(){
		 $("#kamlaunchFinBuiUpTab").click();
	});
	$('#kamprevhighDet').click(function(){
		 $("#kamlaunchMissDe").click();
	});
	
	
// screen 1
	$('input[type="checkbox"]').on('change', function() {
	   $('input[type="checkbox"]').not(this).prop('checked', false);
	 
	});
	
	loadKamLauches('All');
	loadApprovalKamLauches('All','All');
	
	//var kamselectedmoc = $('#kamMocCol').val(); //'All';
	// $("#kambasepack_add").dataTable().fnDestroy();
	  // setTimeout(function(){
				 /*
			     kambaseoTable = $('#kambasepack_add').DataTable( {
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
						"sAjaxSource" : "http://localhost:8083/VisibilityAssetTracker/getAllCompletedLaunchKamData.htm",
						  "fnServerParams" : function(aoData) {
								aoData.push({ "name": "kamMoc", "value": kamselectedmoc });
							
							},
							//"aaData": data,
							"aoColumns" : [
									{
									  mData: 'launchId',
									  "mRender": function(data, type, full) {
										  // value="${launch.launchId}">
										return '<input type="checkbox" name="editLaunchscr1KAMLaunch" class="radioln kamLnchDetscr1" onchange = "onChangeChkKamLaunchDetails(this, this.value)" value=' + data + '>';
									  }
									},
									{mData : 'launchName'},
									{mData : 'launchMoc'},
									{mData : 'createdDate'},
									{mData : 'createdBy'}, 
								],
	    	    	    });
			    */
			    
    // }, 800 );
	
	// code for moc in change moc pop up
	$(document).on( "click", "#mockamChange", function(){
	$('#successblock').hide();
		
		//$('#kamMocremarks').val('');
		var checked_field = $( "[name=editLaunchscr1KAMLaunch]:checked" );
		var launchDate = "";
		var launchMocDate = "";
		if( checked_field.length != 0 ){
			var date = checked_field.closest( "tr" ).find( ".mocDate" ).val();
			var launch_date = date.split("/");
			launchMocDate = launch_date[1] + "/" + launch_date[0] + "/" + launch_date[2];
		}
		
		var today = new Date(launchMocDate),
        yy = today.getFullYear(),
        m = today.getMonth(),
        dd = today.getDate(),
        mm = dd < 21 ? ( m + 2 ) : ( m + 3 );
		var option = "<option value=''>Select MOC</option>";
        m = dd < 21 ? m : ( m + 1 );
	    for( var i = 2; i <= 3; i++ ){
            var lastMonth = mm + i > 13 ? ( ( mm + i ) - 13 ) : m + i;
            lastMonth = ( lastMonth + "" ).length == 1 ? "0" + lastMonth : lastMonth; 
            var lastYear = mm + i > 13 ? yy + 1 : yy;
            option += "<option value='"+lastMonth + "" + lastYear+"'>"+lastMonth + "" + lastYear+"</option>"
        }
		$("#paid-kamlaunch-moc").empty().append(option);
		
		loadselectedkamAccounts();  //Sarin Changes Q1Sprint Feb2021
		//loadKamAccounts();  
		
		
	});
	
	//Sarin Changes Q1Sprint Feb2021 - Starts
	
	function loadselectedkamAccounts() {
		var launchId=$('.kamLnchDetscr1:checked').val();
		 $.ajax({
			type : "GET",
			dataType: 'json',
			//contentType : "application/json; charset=utf-8",
			cache : false,
			url : "getUpcomingLaunchMocByLaunchIdsKam.htm?launchId="+launchId,
			async : false,
			success : function(data) {
				//console.log(data);
				var accounts=data.responseData.lisOfAcc;
				
				//Sprint4Changes - Starts
				var upcomingmoc=data.responseData.listOfMoc;
				var upmocoption = "<option value=''>Select MOC</option>";
				for (var i = 0; i < upcomingmoc.length; i++) {
					upmocoption += "<option value='"+upcomingmoc[i] +"'>"+upcomingmoc[i]+"</option>"
				}
				$("#paid-kamlaunch-moc").empty().append(upmocoption);
				//Sprint4Changes - Ends
				
				//accounts=data.responseData.lisOfAcc;
				
				var option = ""; //"<option value='select'>Select Account</option>";
				for (var i = 0; i < accounts.length; i++) {
				  option += "<option value='"+accounts[i] +"'>"+accounts[i]+"</option>"
				}
				console.log(option);
				//$("#paid-kamlaunch-acc").empty().append(option); 
				$('#paid-kamlaunch-acc').empty();
				$('#paid-kamlaunch-acc').multiselect("destroy");
				$("#paid-kamlaunch-acc").empty().append(option); 
				loadKamAccounts();
				
				//alert(parsed);
				/*var parsed = $.parseJSON(data);
				if(parsed<=60){
					$('#add-depot').modal('show');
				}else{
					$('#add-depot').modal('hide');
					$("#createPromoForm").submit();
				}*/
				
			},
			error : function(error) {
				console.log(error)
			}
		});
	}
	//Sarin Changes Q1Sprint Feb2021 - Ends
	

	
	// for download enable in change moc


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
	/*
	 * $( document ).on( 'mouseup', '#smartwizard ul.step-anchor a, #smartwizard
	 * ul.step-anchor li', function(e){
	 * 
	 * isManualClick = true; } );
	 * 
	 * $("#smartwizard").on("showStep", function(e, anchorObject, stepNumber,
	 * stepDirection) { isManualClick = false; });
	 */
	// intiatizing wizard

	$('#wizard').smartWizard({
		transitionEffect : 'slideleft',
		onLeaveStep : leaveAStepCallback,
		onFinish : onFinishCallback,
		enableFinishButton : true
	});

	 $("#smartwizard").on("leaveStep", function(e, anchorObject, stepNumber, stepDirection) {
		console.log(e) ;
	 });
	 
	//upload for screen 4


	 $("#kamlaunchStoreFileUploadBtn").click(function (event) {
	 	event.preventDefault();
	 	var launchId = $( ".kamLnchDetscr1:checked" ).val();
	 	var fileName = $('#kambtnSubmitStrIn').val();
	 	if (fileName == '') {
	 		$('#kamuploadErrorMsg').show().html("Please select a file to upload");
	 		return false;
	 	} else{
			
	 		$('#kamuploadErrorMsg').hide();

	 		var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
	 		if (FileExt != "xlsx") {
	 			if (FileExt != "xls") {
	 				$('#kamuploadErrorMsg').show().html("Please upload .xls or .xlsx file");
	 				$("#kamlaunchStoreUpload").submit(function(e) {
	 					e.preventDefault();
	 				});
	 				return false;
	 			}

	 		}
			

	 	}
	 	
	     // Get form
	     var form = $('#kamlaunchStoreUpload')[0];

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
	         url: launchId+'/uploadLaunchStore.htm',
	         data: data,
	         beforeSend: function() {
	             ajaxLoader(spinnerWidth, spinnerHeight);
	         },
	         processData: false,
	         contentType: false,
	         cache: false,
	         timeout: 600000,
	         success: function (upllnchstrdata) {
	         	// upldatanew = typeof upldata == "string" ? JSON.parse(upldata) :
	 			// upldata;
	         	var errmsg = upllnchstrdata.responseData;
	         	//console.log(upllnchstrdata.responseData);
		
	         	 $('.loader').hide();
			
			
	             if("SUCCESS_FILE" ==  upllnchstrdata) {
					 	
	             	 $("#kamlaunchVisiTab").click();
	             	 $('#kamuploadErrorMsg').hide();
	             	 $('#kamerrorblockUpload').hide();
	             	 $('#targetStoreErrorBlock').hide();
					 //sprint-4 US-5 confirmation msg bharati added
					 //var fileNamesuccess = fileName;
			       //if(fileNamesuccess.includes("Store.Download") && fileName != ''){
			          $('#storelist-successblock').show().find('span').html('Uploaded Successfully !!!');
			         //}
			         //sprint-4 US-5 confirmation msg bharati ended
					  saveBuildUp();
					  //window.location.href = "getAllCompletedLaunchDataKam.htm#step-4";
					   kamgetVissiData();
                     // getlaunchStores();
	             	//bharati added for sprint-7 US-7 Error block for minimum target store
	             }else if(upllnchstrdata.includes('Minimum targeted stores should be')){
	                   $('#targetStoreErrorBlock').show().find('span').html('Please Accept Minimum Target Stores as Confirmed by TME');  
	                   $('#storelist-successblock').hide(); 
	             }
	             else {
	             	$('#kamerrorblockUpload').show();
	             	//$('#kamuploadErrorMsg span').text(errmsg);
	             }
	            //sprint-4 US-5 redirect on same page changes done by bharati
				 window.location.href = storepageURL;
	             $("#btnSubmitBasePack").prop("disabled", false);
				  $('#kamlaunchStoreUpload')[0].reset();
				 
	         },
	         error: function (jqXHR, textStatus, errorThrown) {
	         	// $('#errorblockUpload').find('span').text(jqXHR.responseText);
	             // $("#uploadErrorMsg").show().html(e.responseText);
	            // console.log("ERROR : ", e);
	             $("#btnSubmitBasePack").prop("disabled", true);
	                   
	         }
	     });
		 //bharati added for find current page -url in sprint-7
		  var storepageURL = $(location).attr("href");	
	 });
	 

	
	$("#kamMocCol").on('change', function () {
		$("#kamlaunchDetailsTab").click();
		$("#kambasepack_add").dataTable().fnDestroy();
	//kambaseoTable.draw();
		var kamselectedmoc = $(this).val(); //'All';
		loadKamLauches(kamselectedmoc);	
		//console.log($(this).val());
    });
  
	$('#kambasepack_add').on('draw.dt', function() {
	    var $empty = $('#kambasepack_add').find('.dataTables_empty');
		if ($empty) $empty.html('Loading Launches..')
	});
	
	//Q2 sprint feb 2021
	$("#approvalKamMocCol").on('change', function () {
		$("#kamlaunchDetailsTab").click();
		$(".table.table-striped.table-bordered.custom-mind").dataTable().fnDestroy();
		//kambaseoTable.draw();
		var approvekamselectedStatus = $("#approvalKamStatusCol").val(); //'All';
		var approvekamselectedmoc = $(this).val(); //'All';
		loadApprovalKamLauches(approvekamselectedmoc,approvekamselectedStatus);	
		
		//Q2 sprint-2 Loading launches..
		$('#approvekambasepack_add').on('draw.dt', function() {
		  var $empty = $('#approvekambasepack_add').find('.dataTables_empty');
		  if ($empty) $empty.html('Loading Launches..')
	});
			     
    });
    
    
	
	//Q2 sprint feb 2021
	$("#approvalKamStatusCol").on('change', function () {
		$("#kamlaunchDetailsTab").click();
		$(".table.table-striped.table-bordered.custom-mind").dataTable().fnDestroy();
		//kambaseoTable.draw();
		var approvekamselectedmoc = $('#approvalKamMocCol').val(); //'All';
		var approvekamselectedStatus = $(this).val(); //'All';
		loadApprovalKamLauches(approvekamselectedmoc,approvekamselectedStatus);
		
		
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

	
// change request moc
	function changeLaunchMoc() {
	    //kavitha
		// var kamlnchId = getkamlaunchId();
		
		var kamAcc = ($('#paid-kamlaunch-acc').val()).toString();
		//alert(kamAcc);
		var kamMocremarks = $('#kamMocremarks').val();
		var kammoc = $('#paid-kamlaunch-moc').val();

		
		var kamNewRemark = $('#paid-kamlaunch-moc').val();
		var kamlnchId = parseInt($( ".kamLnchDetscr1:checked" ).val());
		if(kamAcc == ''){
			$('#kamAccErrorMsg').show();
			return false;
		}
		else if(kamMocremarks == ''){
			$('#kamRemakErrorMsg').show();
			return false;
		}
		else if(kammoc == ''){
			$('#kamRemakErrorMsgMoc').show();
			return false;
		}
		else{
			$('#kamAccErrorMsg').hide();
			$('#kamRemakErrorMsg').hide();
			$('#kamRemakErrorMsgMoc').hide();
	    $.ajax({
	        url: 'requestChengeMocByLaunchIdKam.htm',
	        dataType: 'json',
	        type: 'post',
	        contentType: 'application/json',
	        data: JSON.stringify( { "launchId": kamlnchId,"mocToChange" : kamNewRemark, "mocChangeRemark" : kamMocremarks,"mocAccount": kamAcc } ),
	        processData: false,
	        beforeSend: function() {
	            ajaxLoader(spinnerWidth, spinnerHeight);
	        },
	        success: function( changedata, textStatus, jQxhr ){
	        	 $('.loader').hide();
	            console.log("changedata");
	            $('#paidCancelBtn').click();
	          //  kambaseoTable.draw();
	            $('#kamRemakRejErrorMsg').hide();
	            $('#successblock').show().find('span').html(' Request for Launch MOC send Successfully !!!');
	          // $( "#kambasepack_add" ).load( "#kamlaunchDetailsTab.html #kambasepack_add" );  //commented this line by bharati for kam Load table data issue on 1 sep 21
	           /* setTimeout(function(){
	            kambaseoTable.draw();
	            }, 700); */ 
	           $("#kambasepack_add").dataTable().fnDestroy();
	          
	            $('#kamMocremarks').val("");
	            $('#mockamChange').prop("disabled", true);
	         	$('#rejectLaunch').prop("disabled", true);
	         	$('#kamlnchDets').prop("disabled", true);
	            loadKamLauches('ALL');     // added this for kam table load issues by 1 sep 21 bharati
	        },
	         
	        error: function( jqXhr, textStatus, errorThrown ){
	            console.log( errorThrown );
	        }
	    });
	    
	   
		}
		
	}
	

// reject launch
	
function rejectLaunch() {
	// var kamlnchId = getkamlaunchId();
	var kamAcc = ($('#reject-kamlaunch-acc').val()).toString();
	//var kammoc = $('#reject-kamlaunch-moc').val();
	
	var kamMocremarks = $('#kamMocRejRemarks').val();
	
	var kamlnchId = parseInt($( ".kamLnchDetscr1:checked" ).val());
	if(kamMocremarks == ''){
		$('#kamRemakRejErrorMsg').show();
		return false;
	}
	//sprint-4 US-3 Bharati Reject popup UI changes
	else if(kamAcc == ''){
			$('#kamAccErrorMsg').show();
			return false;
		}else{
		$('#kamRemakRejErrorMsg').hide();
		$('#kamAccErrorMsg').hide();
		//$('#kamRemakErrorMsgMoc').hide();
    $.ajax({
        url: 'rejectLaunchByLaunchIdKam.htm',
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify( { "launchId": kamlnchId, "launchRejectRemark" : kamMocremarks, "mocAccount": kamAcc } ),
		
        processData: false,
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
        success: function( rejectdata, textStatus, jQxhr ){
        	 $('.loader').hide();
            console.log("rejectdata");
            $('#paidRejCancelBtn').click();
            //kambaseoTable.draw();
            $('#successblock').show().find('span').html(' Launch Rejected Successfully !!!');
            
           // $( "#kambasepack_add" ).load( "#kamlaunchDetailsTab.html #kambasepack_add" ); //commented this line by bharati for kam Load table data issue on 1 sep 21
           /* setTimeout(function(){
            kambaseoTable.draw();
            }, 700); */ 
            $("#kambasepack_add").dataTable().fnDestroy();
            $('#kamMocRejRemarks').val("");
            $('#mockamChange').prop("disabled", true);
         	$('#rejectLaunch').prop("disabled", true);
         	$('#kamlnchDets').prop("disabled", true);
         	loadKamLauches('ALL');     // added this for kam table load issues by 1 sep 21 bharati
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
	}
	
}

// reject screen 2

function rejectLaunchBaseScreen2() {
    
	// var kamlnchId = getkamlaunchId();
	var kamLanchId = $("#kamLnchDet").val();
	var kamMocremarks = $('#kamMocBasRejRemarks').val();
	var x = document.getElementsByClassName("kamLnchBsckscr2");
	var kamBspklnchId = [];
	for(var i = 0; i < x.length; i++) {
	    if(x[i].checked) {
	    	kamBspklnchId.push(x[i].value);
	    }
	}
//	var kamBspklnchId = $( ".kamLnchBsckscr2:checked" ).val().split(",");
	if(kamMocremarks == ''){
		$('#kamRemakRejBasErrorMsg').show();
		return false;
	}
	else{
		$('#kamRemakRejBasErrorMsg').hide();
    $.ajax({
        url: 'rejectBasepacksByBasepackIdsKam.htm',
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify( { "basePackIds" : kamBspklnchId,"launchId": kamLanchId, "basepackRejectComment" : kamMocremarks } ),
        processData: false,
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
        success: function( rejectdata, textStatus, jQxhr ){
        	 $('.loader').hide();
            console.log("rejectdata");
            if(rejectdata.responseData == "REJECTED SUCCESSFULLY"){
	            $('#baseRejCancelBtn').click();
	            $('#successblock').show().find('span').html(' Basepack Rejected Successfully !!!');
	            $('#kamMocBasRejRemarks').val('');
	            $('#kamRjtbspack').prop("disabled", true);
	            $("#kam_basepack_add").dataTable().fnDestroy();
	           // $( "#kam_basepack_add" ).load( "#kamlaunchBasepackTab.html
				// #kam_basepack_add" );
	            kamLaunch();
            }
            else{
            	alert("Oops, Something went wrong");
            }
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
	}
}

// Launch Details
function kamLaunch() {
    
	// var kamlnchId = getkamlaunchId();
	
	var kamlnchId = $( ".kamLnchDetscr1:checked" ).val();
	
    $.ajax({
        url: 'getAllBasePackByLaunchIdsKam.htm',
       // dataType: 'json',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify( { "launchIds": kamlnchId} ),
        processData: false,
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
        success: function( launchDet, textStatus, jQxhr ){
        	 $('.loader').hide();
           
            $('#kamlaunchBasepackTab').click();
            // screen 2 table chan
            if(launchDet != ''){
			var row = "";
			var detData = JSON.parse(launchDet);
			var lanchDet = detData.responseData.listLaunchDataResponse
			if(lanchDet.length > 0) {
				for (var i = 0; i < lanchDet.length; i++) {
					row += "<tr><td><input type='checkbox' name='editLaunchscr1' class='radioln kamLnchBsckscr2' value='" 
							+ lanchDet[i].basepackId 
							+ "' onchange=\"disableReject(this,'"+lanchDet.length+"')\"></td>" 
							+ "<td><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
					 		+ lanchDet[i].launchName
					 		+"'></td>" 
							+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
							+ lanchDet[i].salesCat
							+"'></td>" 
							+ "<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
							+ lanchDet[i].psaCat
							+"'></td>" 
							+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
							+ lanchDet[i].brand
							+"'></td>" 
							+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode' placeholder='12345' maxlength='5' disabled value= '"
					 		+ lanchDet[i].bpCode
					 		+"'></td>"
							+ "<td><input name='coelaunchbase' type='text' class='form-control validfield descrip' placeholder='Description' maxlength='255' disabled value= '"
					 		+ lanchDet[i].bpDisc
					 		+"'></td>" 
							+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield mrpIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);' disabled value= '"
					 		+ lanchDet[i].mrp
					 		+"'></td>" 
							+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield turIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);' disabled value= '"
					 		+ lanchDet[i].tur
					 		+"'></td>" 
							+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield gsvIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);' disabled value= '"
					 		+ lanchDet[i].gsv
					 		+"'></td>" 
							+ "<td><input name='coelaunchbase' type='text' class='form-control validfield clvIn' placeholder='0000' onkeypress='return isNumberKey(event)' maxlength='4' disabled value= '"
					 		+ lanchDet[i].cldConfig
					 		+"'></td>" 
							+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield grmIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);' disabled value= '"
					 		+ lanchDet[i].grammage
					 		+"'></td>" 
							+ "<td><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
							+ lanchDet[i].classification
							+"'></td></tr>";
				}
			} 
			$('#kam_basepack_add tbody').empty().append(row);
			//$("#kam_basepack_add").dataTable().fnDestroy();
			 setTimeout(function(){
				    sndTable = $('#kam_basepack_add').DataTable( {
							
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
            else{
            	ezBSAlert({
					messageText : "No Launch Available.",
					alertType : "info"
				}).done(function(e) {
					// console.log('hello');
				});
				
				return false;
            }
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
	
}

function onChangeChkKamLaunchDetails(target, launchId) {
	$("#kamLnchDet").val(launchId);
	var inputElems = document.getElementsByName("editLaunchscr1KAMLaunch");
    var count = 0,
		rejectEnable = true,
		changeEnable = true;
		
	var MocLDate = $(target).closest('tr').find('.mocDate').val().split("/"),
	    MocLSplit = MocLDate[1] + "/" + MocLDate[0] + "/" + MocLDate[2],
	    MocLDateObj = new Date(MocLSplit),
	    CurDate     = new Date(),
	    timeDiff  = MocLDateObj - CurDate,
	    days      = timeDiff / (1000 * 60 * 60 * 24);
       
	for (var i=0; i<inputElems.length; i++) {   
		if (inputElems[i].type == "checkbox" && inputElems[i].checked == true) {
			count++;
		}
	}
	
	
	if(count > 0) {
		/*
		if( days >= 15 ){
			rejectEnable = false;
		}
		
		if( days >= 5 ){
			changeEnable = false;
		}*/
		if(changeEnable){
			document.getElementById("mockamChange").removeAttribute("disabled");
		}
		if(rejectEnable) {
			document.getElementById("rejectLaunch").removeAttribute("disabled");
		}
		$('#kamlnchDets').prop('disabled', false);
	} else {
		document.getElementById("mockamChange").setAttribute("disabled","disabled");
		document.getElementById("rejectLaunch").setAttribute("disabled","disabled");
		$('#kamlnchDets').prop('disabled', true);
	}
}

function disableReject(rowObj, lengthOfBpRecords) {
	var inputElems = document.getElementsByName("editLaunchscr1");
    var count = 0;
       
	for (var i=0; i<inputElems.length; i++) {   
		if (inputElems[i].type == "checkbox" && inputElems[i].checked == true) {
			count++;
			
		}
	}
	
	if(count > 0 && count != lengthOfBpRecords) {
		document.getElementById("kamRjtbspack").removeAttribute("disabled");
	} else {
		document.getElementById("kamRjtbspack").setAttribute("disabled","disabled");
	}
}

// 3 screen data show

function kamSaveBasepacks() {
    
	// var kamlnchId = getkamlaunchId();
	
	var kamlnchId = $( ".kamLnchDetscr1:checked" ).val();
	
    $.ajax({
        url: 'getLaunchBuildUpByLaunchIdKam.htm?launchId='+kamlnchId,
        dataType: 'json',
        type: 'get',
        contentType: 'application/json',
       // data: JSON.stringify( { "launchIds": kamlnchId} ),
        processData: false,
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
        success: function( lanchDet, textStatus, jQxhr ){
        	 $('.loader').hide();
           
            $('#kamlaunchStoresTab').click();
            var buildupscreen = lanchDet.responseData.getListLaunchBuildUpData;
            // screen 2 table chan
			var row = "";
			for (var i = 0; i < buildupscreen.length; i++) {
				row += "<tr><td><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
				 		+ ( typeof buildupscreen[i].skuName != "undefined" ? buildupscreen[i].skuName : "" )
				 		+"'></td>" 
						+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
						+ ( typeof buildupscreen[i].basepackCode != "undefined" ? buildupscreen[i].basepackCode : "" )
						+"'></td>" 
						+ "<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
						+ ( typeof buildupscreen[i].launchSellInValue != "undefined" ? buildupscreen[i].launchSellInValue : "" )
						+"'></td>" 
						+"<td class='xsdrop'><input name='coelaunchbase' type='text' class='form-control l1chain' readonly value= '"
						+ ( typeof buildupscreen[i].launchN1SellInVal != "undefined" ? buildupscreen[i].launchN1SellInVal : "" )
						+"'></td>" 
						+"<td><input name='coelaunchbase' type='text' class='form-control validfield baseCode' placeholder='12345' maxlength='5' disabled value= '"
				 		+ ( typeof  buildupscreen[i].launchN2SellInVal != "undefined" ?  buildupscreen[i].launchN2SellInVal : "" )
				 		+"'></td>"
						+ "<td><input name='coelaunchbase' type='text' class='form-control validfield descrip' placeholder='Description' maxlength='255' disabled value= '"
				 		+( typeof  buildupscreen[i].launchSellInCld != "undefined" ?  buildupscreen[i].launchSellInCld : "" )
				 		+"'></td>" 
						+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield mrpIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);' disabled value= '"
				 		+ ( typeof buildupscreen[i].launchN1SellInCld != "undefined" ? buildupscreen[i].launchN1SellInCld : "" )
				 		+"'></td>" 
						+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield turIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);' disabled value= '"
				 		+ ( typeof buildupscreen[i].launchN2SellInCld != "undefined" ? buildupscreen[i].launchN2SellInCld : "" )
				 		+"'></td>" 
						+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield gsvIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);' disabled value= '"
				 		+( typeof buildupscreen[i].launchSellInUnit != "undefined" ? buildupscreen[i].launchSellInUnit : "" )
				 		+"'></td>" 
						+ "<td><input name='coelaunchbase' type='text' class='form-control validfield clvIn' placeholder='0000' onkeypress='return isNumberKey(event)' maxlength='4' disabled value= '"
				 		+ ( typeof buildupscreen[i].launchN1SellInUnit != "undefined" ? buildupscreen[i].launchN1SellInUnit : "" )
				 		+"'></td>" 
						+ "<td class='xsfield'><input name='coelaunchbase' type='text' class='form-control validfield xsfield grmIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);' disabled value= '"
				 		+ ( typeof buildupscreen[i].launchN2SellInUnit != "undefined" ? buildupscreen[i].launchN2SellInUnit : "" )
				 		+"'></td></tr>";
			}
			
			$('#kam_basepack_launch_build tbody').empty().append(row);
			setTimeout(function(){
			     bldTable = $('#kam_basepack_launch_build').DataTable( {
						
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


// screen 4

function saveBuildUp() {
    
	// var kamlnchId = getkamlaunchId();
	
	var kamlnchId = $( ".kamLnchDetscr1:checked" ).val();
	
    $.ajax({
        url: 'getLaunchStoreListByLaunchKam.htm?launchId='+kamlnchId,
        dataType: 'json',
        type: 'get',
        contentType: 'application/json',
        //data: JSON.stringify( { "launchIds": kamlnchId} ),
        processData: false,
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
		
		
        success: function( lanchStrDet, textStatus, jQxhr ){
        	 $('.loader').hide();
           
            $('#kamlaunchSellInTab').click();
            var strLstscreen = lanchStrDet.responseData.listSaveLaunchStoreList;
			
			
            // screen 2 table chan
			var row = "";
			
			for (var i = 0; i < strLstscreen.length; i++) {
				
				//sprint-4 US-6.1 changes added by Bharati 
				row += "<tr><td><input name='kamlaunchname' type='text' class='form-control kamlaunchname' readonly value= '"
				 		+ strLstscreen[i].launchName
				 		+"'></td>" 
						+"<td><input name='kamlaunchmoc' type='text' class='form-control kamlaunchmoc' readonly value= '"
				 		+ strLstscreen[i].mocDate
				 		+"'></td>" 
						+"<td><input name='kamlnchstrl1' type='text' class='form-control kamlnchstrl1' readonly value= '"
				 		+ strLstscreen[i].L1_Chain
				 		+"'></td>" 
						+"<td class='xsdrop'><input name='kamlnchstrl2' type='text' class='form-control kamlnchstrl2' readonly value= '"
						+ strLstscreen[i].L2_Chain
						+"'></td>" 
						+ "<td class='xsdrop'><input name='kamlnchstrstrfmt' type='text' class='form-control kamlnchstrstrfmt' readonly value= '"
						+ strLstscreen[i].StoreFormat
						+"'></td>" 
						+"<td class='xsdrop'><input name='kamlnchstrclstr' type='text' class='form-control kamlnchstrclstr' readonly value= '"
						+ strLstscreen[i].Cluster
						+"'></td>" 
						+"<td><input name='kamlnchstrhulol' type='text' class='form-control validfield kamlnchstrhulol' placeholder='12345' maxlength='5' disabled value= '"
				 		+ strLstscreen[i].HUL_OL_Code
				 		+"'></td>"
						+ "<td><input name='kamlnchstrdesc' type='text' class='form-control validfield kamlnchstrdesc' placeholder='Description' maxlength='255' value= '"
				 		 + strLstscreen[i].Kam_Remarks
				 		+"'></td></tr>";
						
			
			}
			
			 $("#kam_launch_store_table").dataTable().fnDestroy();
			$('#kam_launch_store_table tbody').empty().append(row);
			    setTimeout(function(){
					    var oTable = $('#kam_launch_store_table').DataTable( {
								
								"scrollY":       "280px",
								//added bharati for sprint-4 US-6.1 
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
					                  "sEmptyTable": "No Pending Visibilities."
					
					              }
			    	    	    });
					    
					    
	             }, 800 );
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
	
}

// download for screen 4

function kamdownloadLaunchStoreLst() {
	var launchIdsArr =  $( ".kamLnchDetscr1:checked" ).val();
	
	window.open("/VisibilityAssetTracker/downloadStoreListFile.htm?launchId=" +launchIdsArr);	
}


function kamdownloadLaunValTemplate() {
	var launchIdsArr =  $( ".kamLnchDetscr1:checked" ).val();
	
	/*window.open("/VisibilityAssetTracker/downloadFinalBuildUpKamTemplate.htm?launchId=" +launchIdsArr);	*/
	window.location.assign(launchIdsArr+"/downloadFinalBuildUpKamTemplate.htm");
}

// screen 4 store list save
function kamsaveStoreData() {
    
	// var kamlnchId = getkamlaunchId();
	var launchStrArr = [];
	var kamlnchId = parseInt($( ".kamLnchDetscr1:checked" ).val());
	var rowCount = $('#kam_launch_store_table tbody').find('tr');
	//sprint-4 US-6.1 changes added by Bharati 
	var lname = $('.kamlaunchname').val();
	var lmoc = $('.kamlaunchmoc').val();
	 
	var l1 = $('.kamlnchstrl1').val();
	var l2 = $('.kamlnchstrl2').val();
	var strfrmt = $('.kamlnchstrstrfmt').val();
	var clus = $('.kamlnchstrclstr').val();
	var hulol = $('.kamlnchstrhulol').val();
	var dsc = $('.kamlnchstrdesc').val();
	
	
	for (var i = 0; i < rowCount.length; i++) {
				
			launchStrArr[i] =  {
				"launchName" : lname,
	            "mocDate" : lmoc,
				"L1_Chain" : l1,
	            "L2_Chain" : l2,
	            "StoreFormat" : strfrmt,
	            "Cluster" : clus,
	            "HUL_OL_Code" : hulol,
	            "Kam_Remarks" : dsc,
	            "launchId": kamlnchId
	        }
			
	}
    $.ajax({
        url: 'saveLaunchStores.htm',
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify( { "listOfFinalLaunchStores": launchStrArr } ),
        processData: false,
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
        success: function( lanchStrDet, textStatus, jQxhr ){
        	 $('.loader').hide();
           
            $('#kamlaunchVisiTab').click();
            kamgetVissiData();
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
	
}

// get vissi plan
function kamgetVissiData() {
    
	var kamlnchId = $( ".kamLnchDetscr1:checked" ).val();
	
    $.ajax({
        url: 'getLaunchVisiListByLaunchIdKam.htm?launchId=' +kamlnchId,
        dataType: 'json',
        type: 'get',
        contentType: 'application/json',
       // data: JSON.stringify( { "listOfFinalLaunchStores": launchStrArr } ),
        processData: false,
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
        success: function( launchVissiDet, textStatus, jQxhr ){
        	 $('.loader').hide();
           
         // $('#kamlaunchVisiTab').click();
            var launchVissiDetls = launchVissiDet.responseData.getListLaunchVisiPlanningResponse;
            if(launchVissiDetls == ''){
            	$('#kamVissidown').prop("disabled", true);
            }
            // screen 2 table chan
			var row = "";
			for (var i = 0; i < launchVissiDetls.length; i++) {
				row += "<tr><td><input name='kamvissistrpln' type='text' class='form-control kamvissistrpln' readonly value= '"
				 		+ launchVissiDetls[i].STORES_PLANNED
				 		+"'></td>" 
						+"<td class='xsdrop'><input name='kamvissiAsset1' type='text' class='form-control kamvissiAsset1' readonly value= '"
						+ (launchVissiDetls[i].VISI_ASSET_1 != '-1' ? launchVissiDetls[i].VISI_ASSET_1 : "") 
						+"'></td>" 
						+ "<td class='xsdrop'><input name='kamvissifacing1' type='text' class='form-control kamvissifacing1' readonly value= '"
						+ launchVissiDetls[i].FACING_PER_SHELF_PER_SKU1
						+"'></td>" 
						+"<td class='xsdrop'><input name='kamvissidept1' type='text' class='form-control kamvissidept1' readonly value= '"
						+ launchVissiDetls[i].DEPTH_PER_SHELF_PER_SKU1
						+"'></td>"
						+"<td class='xsdrop'><input name='kamvissiAsset2' type='text' class='form-control kamvissiAsset2' readonly value= '"
						+ (launchVissiDetls[i].VISI_ASSET_2 != '-1' ? launchVissiDetls[i].VISI_ASSET_2 : "" )
						+"'></td>" 
						+ "<td class='xsdrop'><input name='kamvissifacing2' type='text' class='form-control kamvissifacing2' readonly value= '"
						+ launchVissiDetls[i].FACING_PER_SHELF_PER_SKU2
						+"'></td>" 
						+"<td class='xsdrop'><input name='kamvissidept2' type='text' class='form-control kamvissidept2' readonly value= '"
						+ launchVissiDetls[i].DEPTH_PER_SHELF_PER_SKU2
						+"'></td>"
						+"<td class='xsdrop'><input name='kamvissiAsset3' type='text' class='form-control kamvissiAsset3' readonly value= '"
						+ (launchVissiDetls[i].VISI_ASSET_3 != '-1' ? launchVissiDetls[i].VISI_ASSET_3 : "")
						+"'></td>" 
						+ "<td class='xsdrop'><input name='kamvissifacing3' type='text' class='form-control kamvissifacing3' readonly value= '"
						+ launchVissiDetls[i].FACING_PER_SHELF_PER_SKU3
						+"'></td>" 
						+"<td class='xsdrop'><input name='kamvissidept3' type='text' class='form-control kamvissidept3' readonly value= '"
						+ launchVissiDetls[i].DEPTH_PER_SHELF_PER_SKU3
						+"'></td>"
						+"<td class='xsdrop'><input name='kamvissiAsset4' type='text' class='form-control kamvissiAsset4' readonly value= '"
						+ (launchVissiDetls[i].VISI_ASSET_4 != '-1' ? launchVissiDetls[i].VISI_ASSET_4 : "") 
						+"'></td>" 
						+ "<td class='xsdrop'><input name='kamvissifacing4' type='text' class='form-control kamvissifacing4' readonly value= '"
						+ launchVissiDetls[i].FACING_PER_SHELF_PER_SKU4
						+"'></td>" 
						+"<td class='xsdrop'><input name='kamvissidept4' type='text' class='form-control kamvissidept4' readonly value= '"
						+ launchVissiDetls[i].DEPTH_PER_SHELF_PER_SKU4
						+"'></td>"
						+"<td class='xsdrop'><input name='kamvissiAsset5' type='text' class='form-control kamvissiAsset5' readonly value= '"
						+ (launchVissiDetls[i].VISI_ASSET_5 != '-1' ? launchVissiDetls[i].VISI_ASSET_5 : "")
						+"'></td>" 
						+ "<td class='xsdrop'><input name='kamvissifacing5' type='text' class='form-control kamvissifacing5' readonly value= '"
						+ launchVissiDetls[i].FACING_PER_SHELF_PER_SKU5
						+"'></td>" 
						+"<td class='xsdrop'><input name='kamvissidept5' type='text' class='form-control kamvissidept5' readonly value= '"
						+ launchVissiDetls[i].DEPTH_PER_SHELF_PER_SKU5
						+"'></td></tr>";
			}
			
			$('#kamVissiPlanTable tbody').empty().append(row);
			$("#kamVissiPlanTable").dataTable().fnDestroy();
		    setTimeout(function(){
				    vssTable = $('#kamVissiPlanTable').DataTable( {
							
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
				                  "sEmptyTable": "No Visi to be planned."
				
				              }
		    	    	    });
				    
				    
             }, 800 );
			
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
	
}

// download for screen 5 vissi

function downloadLaunchVisiPlanTemplate() {
	var launchIdsArr =  $( ".kamLnchDetscr1:checked" ).val();
	
	window.open("/VisibilityAssetTracker/downloadStoreListFile.htm?launchId=" +launchIdsArr);	
}



// screen 5 vissi table save
function kamSaveVissi() {
    
	// var kamlnchId = getkamlaunchId();
	var launchStrArr = [];
	var kamlnchId = parseInt($( ".kamLnchDetscr1:checked" ).val());
	var rowCount = $('#kamVissiPlanTable tbody').find('tr');
	var strpln = $('.kamvissistrpln').val();
	var visiasset1 = $('.kamvissiAsset1').val();
	var kamvissifacing1 = $('.kamvissifacing1').val();
	var kamvissidept1 = $('.kamvissidept1').val();
	var visiasset2 = $('.kamvissiAsset2').val();
	var kamvissifacing2 = $('.kamvissifacing2').val();
	var kamvissidept2 = $('.kamvissidept2').val();
	var visiasset3 = $('.kamvissiAsset3').val();
	var kamvissifacing3 = $('.kamvissifacing3').val();
	var kamvissidept3 = $('.kamvissidept3').val();
	var visiasset4 = $('.kamvissiAsset4').val();
	var kamvissifacing4 = $('.kamvissifacing4').val();
	var kamvissidept4 = $('.kamvissidept4').val();
	var visiasset5 = $('.kamvissiAsset5').val();
	var kamvissifacing5 = $('.kamvissifacing5').val();
	var kamvissidept5 = $('.kamvissidept5').val();
		
	for (var i = 0; i < rowCount.length; i++) {
				
			launchStrArr[i] =  {
				"storesPlanned" : strpln,
	            "visiAsset1" : visiasset1,
	            "facingPerShelfPerSku1" : kamvissifacing1,
	            "depthPerShelfPerSku1" : kamvissidept1,
	            "visiAsset2" : visiasset2,
	            "facingPerShelfPerSku2" : kamvissifacing2,
	            "depthPerShelfPerSku2" : kamvissidept2,
	            "visiAsset3" : visiasset3,
	            "facingPerShelfPerSku3" : kamvissifacing3,
	            "depthPerShelfPerSku3" : kamvissidept3,
	            "visiAsset4" : visiasset4,
	            "facingPerShelfPerSku4" : kamvissifacing4,
	            "depthPerShelfPerSku4" : kamvissidept4,
	            "visiAsset5" : visiasset5,
	            "facingPerShelfPerSku5" : kamvissifacing5,
	            "depthPerShelfPerSku5" : kamvissidept5,
	            "launchId": kamlnchId
	        }
	}
    $.ajax({
        url: 'saveLaunchVisiListByLaunchIdKam.htm',
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify( { "listOfVisiPlanningLaunch": launchStrArr } ),
        processData: false,
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
        success: function( lanchStrDet, textStatus, jQxhr ){
        	 $('.loader').hide();
           
            $('#kamlaunchFinBuiUpTab').click();
       	 artworkdocCall();
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
	
}

function artworkdocCall() {
    
	var kamlnchId = $( ".kamLnchDetscr1:checked" ).val();
	
    $.ajax({
        url: 'getLaunchDocDetailsByLaunchIdKam.htm?launchId=' +kamlnchId,
        dataType: 'json',
        type: 'get',
        contentType: 'application/json',
       // data: JSON.stringify( { "listOfFinalLaunchStores": launchStrArr } ),
        processData: false,
        beforeSend: function() {
            ajaxLoader(spinnerWidth, spinnerHeight);
        },
        success: function( launchDocDet, textStatus, jQxhr ){
        	 $('.loader').hide();
        	 
        	 var artWorkPackShotsDocName = launchDocDet.responseData.artWorkPackShotsDocName;
        	 yesno = launchDocDet.responseData.sampleShared;
        	 $('#kamartworkdownBtn').val(artWorkPackShotsDocName);
        	 if (yesno == '0'){
			    $('.checkboxDiv').find(':radio[name=samp][value="0"]').prop('checked', true);
//			    $('.checkboxDiv').find(':radio[name=samp][value="1"]').attr('disabled', 'disabled');    
        	 }
			 else{
			    $('.checkboxDiv').find(':radio[name=samp][value="1"]').prop('checked', true);
//			    $('.checkboxDiv').find(':radio[name=samp][value="0"]').attr('disabled', 'disabled');  
			 }
        	 
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
	
}

// download for screen 6 Document (annexure)

function kamdownAnnexureDoc() {
	var launchId =  $( ".kamLnchDetscr1:checked" ).val();
	
	window.location.assign("/VisibilityAssetTracker/"+launchId+"/downloadAnnexureListDataKam.htm");
	//window.open("/VisibilityAssetTracker/"+launchId+"/downloadAnnexureListDataKam.htm"); //Sarin	
}

// download for mdg
function kamuploadmdgDoc() {
	var launchId =  $( ".kamLnchDetscr1:checked" ).val();
	
	window.open("/VisibilityAssetTracker/"+launchId+"/downloadMdgDeckDataKam.htm");	
}

// download for artwork
function kamuploadArtDoc() {
	var launchId =  $( ".kamLnchDetscr1:checked" ).val();
	var artWorkPackShotsDocName = $('#kamartworkdownBtn').val().split(",");
	for(var j = 0; j < artWorkPackShotsDocName.length; j++){
		if(artWorkPackShotsDocName[j] != "") {
			window.open("/VisibilityAssetTracker/"+artWorkPackShotsDocName[j]+"/downloadFileTemplateKam.htm");	
		}
	}
}

function kamsaveLaunchPlandownload() {
    
	var kamlnchId = $( ".kamLnchDetscr1:checked" ).val();
	 var samplekam = $( '[name="samp"]:checked' ).val();
	 if(samplekam == yesno){
		 $('#kamlaunchMissDe').click();
	 }
	 else{
		 $('#kamDocRemark').modal("show");
	 }
	
}

function docSamLaunch() {
	var kamlnchId = $( ".kamLnchDetscr1:checked" ).val();
	 var docSamRemark = $('#kamDocSmplRemarks').val();
	 $.ajax({
	        url: 'updateLaunchSampleShared.htm',
	        dataType: 'json',
	        type: 'post',
	        contentType: 'application/json',
	        data: JSON.stringify( { "launchId": kamlnchId, "remark": docSamRemark } ),
	        processData: false,
	        beforeSend: function() {
	            ajaxLoader(spinnerWidth, spinnerHeight);
	        },
	        success: function( data, textStatus, jQxhr ){
	        	 $('.loader').hide();
	           // console.log("sucessful");
	        	 $('#kamlaunchMissDe').click();
	        	 $('#kamDocRemark').modal("hide");
	        	 $('#kamDocSmplRemarks').val('');
	        },
	        error: function( jqXhr, textStatus, errorThrown ){
	            console.log( errorThrown );
	        }
	    });
}

// screen 5 Kam inpyt details save
function kamHighlightMissingDet() {
    var kamlnchId = parseInt($( ".kamLnchDetscr1:checked" ).val());
	var msnDet = $('#missField').val();
	if(msnDet == ''){
		ezBSAlert({
			messageText : "Please enter missing details",
			alertType : "info"
		}).done(function(e) {
			// console.log('hello');
		});
		return false;
	}
	
  $.ajax({
      url: 'missingDetailsKamInput.htm',
      dataType: 'json',
      type: 'post',
      contentType: 'application/json',
      data: JSON.stringify( { "launchId" : kamlnchId,"missingDetails" : msnDet } ),
      processData: false,
      beforeSend: function() {
          ajaxLoader(spinnerWidth, spinnerHeight);
      },
      success: function( lanchStrDet, textStatus, jQxhr ){
      	 $('.loader').hide();
      //	getMstnTable();
        //  $('#kamlaunchSubTab').click();
      	$('#successblock').show().find('span').html(' Details submitted Successfully !!!');
      },
      error: function( jqXhr, textStatus, errorThrown ){
          console.log( errorThrown );
      } 
  });
	
}


//mstn table
function getMstnTable() {
    var kamlnchId = parseInt($( ".kamLnchDetscr1:checked" ).val());
		
  $.ajax({
      url: 'getMstnClearanceByLaunchIdKam.htm?launchId='+kamlnchId,
      dataType: 'json',
      type: 'get',
      contentType: 'application/json',
     //s data: JSON.stringify( { "launchId" : kamlnchId,"missingDetails" : msnDet } ),
      processData: false,
      beforeSend: function() {
          ajaxLoader(spinnerWidth, spinnerHeight);
      },
      success: function( mstnData, textStatus, jQxhr ){
      	 $('.loader').hide();
      	$('#successblock').hide();
         $('#kamlaunchSubTab').click();
      	 var mstnlen = mstnData.responseData.launchMstnClearanceResponseKams.length;
         var mstn = mstnData.responseData.launchMstnClearanceResponseKams;
			var sellrow = "";
			for (var i = 0; i < mstnlen; i++) {
				sellrow += "<tr><td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].bpCode +"'></td>" 
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].bpDescription+"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].depot+"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].cluster+"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].finalCldN+"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].finalCldN1+"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].finalCldN2+"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].mstnCleared+"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].currentEstimates+"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control' readonly value= '" +
						mstn[i].clearanceDate+"'></td></tr>";
			}
			
			$('#kamMstnClearTable tbody').empty().append(sellrow);
	   
			// Destroy the table
	        // Use $("#dailyNews").DataTable().destroy() for DataTables 1.10.x
			
			// $.fn.dataTable.ext.errMode = 'none';
	         $("#kamMstnClearTable").dataTable().fnDestroy();
	         setTimeout(function(){
	        	 mstnoTable = $('#kamMstnClearTable').DataTable( {
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

//Sarin Changes Q1Sprint Feb2021 - Starts
function loadKamAccounts() {
	$('#paid-kamlaunch-acc').multiselect({
		includeSelectAllOption : true,
		numberDisplayed : 2,
		/* buttonWidth: '100px', */
		nonSelectedText : 'ALL CUSTOMERS',
		selectAllText : 'ALL CUSTOMERS',
		onChange : function(option, checked, select) {
			 var custChainSelectedData = [];
			 var selectedOptionsChange = $('#paid-kamlaunch-acc option:selected');
			 var totalLen = $('#paid-kamlaunch-acc option').length;

				if (selectedOptionsChange.length > 0 && selectedOptionsChange.length < totalLen){
					selectAll = false;
				}else if(selectedOptionsChange.length == totalLen){
					selectAll = true;
				}
			 
				for (var i = 0; i < selectedOptionsChange.length; i++) {
					custChainSelectedData.push(selectedOptionsChange[i].value);

				}
				if(selectAll == true){
					//custChainL1 = "ALL";
				}else{
					//custChainL1 = custChainSelectedData.toString();
				}
		 
			//promoTable.draw();
		},
		onDropdownHide : function(event) {
			
			var selVals = [];
			var selectedOptions = $('#paid-kamlaunch-acc option:selected');
			if (selectedOptions.length > 0 && selectAll == false) {
				for (var i = 0; i < selectedOptions.length; i++) {
					selVals.push(selectedOptions[i].value);
				}
				/*
				var strData = selVals.toString();
				$('.switch-dynamic')
						.html(
								'<select class="form-control" name="cust-chain" id="customerChainL2" multiple="multiple"><option values="ALL CUSTOMERS">ALL CUSTOMERS</option>');

				getCustChainValues(strData);

			} else {
				$('.switch-dynamic')
						.html(
								'<input type="text" name="cust-chain" class="form-control" id="customerChainL2" value="ALL CUSTOMERS" readonly="true">'); */
			}
			
		},
		
		onSelectAll : function() {
			//custChainL1 = "ALL";
			//promoTable.draw();
			selectAll = true;
		},
		onDeselectAll : function() {
			//custChainL1 = "ALL";
			//promoTable.draw();
			/*$('.switch-dynamic')
					.html(
							'<input type="text" class="form-control" name="cust-chain" id="customerChainL2" value="ALL CUSTOMERS" readonly="readonly">');*/
		}

	});
}


function loadKamLauches(kamselectedmoc) {
	kambaseoTable = $('#kambasepack_add').DataTable( {
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
			"sAjaxSource" : "getAllCompletedLaunchKamData.htm",
			  "fnServerParams" : function(aoData) {
			  
					aoData.push({ "name": "kamMoc", "value": kamselectedmoc });
				
				},
				//"aaData": data,
				"aoColumns" : [
						{
						  mData: 'launchId',
						  "mRender": function(data, type, full) {
							return '<input type="checkbox" id="kamcheckbox" name="editLaunchscr1KAMLaunch" class="radioln kamLnchDetscr1" onClick="kamselect()" onchange = "onChangeChkKamLaunchDetails(this, this.value)" value=' + data + '>';
						  }
						},
						{mData : 'launchName'},
						//{mData : 'launchMoc'},
						{
						  mData: 'launchMoc',
						  "mRender": function(data, type, full) {
							return full.launchMoc + '<input type = "hidden" class="mocDate"  value=' + full.launchDate + '>';
						  }
						},
						//Sprint 4 US-2.3 Frontend Changes By bharati 26-AUG
						{mData : 'changedMoc', sWidth: '26%'},
						{mData : 'createdDate'},
						{mData : 'createdBy'}, 
					],
			});
	kambaseoTable.draw();
}
//Sarin Changes Q1Sprint Feb2021 - Ends

function kamselect(){
	$('input[type="checkbox"]').on('change', function() {
		
		   $('input[type="checkbox"]').not(this).prop('checked', false);
		 
		});
}
//Q2 sprint feb 2021 
function loadApprovalKamLauches(approvekamselectedmoc,approvekamselectedStatus) {
	 kameditapproveoTable = $('.table.table-striped.table-bordered.custom-mind').DataTable( {
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
			"sAjaxSource" : "getApprovalStatusMocKam.htm",
			  "fnServerParams" : function(aaData) {
			        
					aaData.push({ "name": "approvalKamMoc", "value": approvekamselectedmoc });
					aaData.push({ "name": "approvalKamStauts", "value": approvekamselectedStatus });
					
				},
				//"aaData": data,
				"aoColumns" : [
						
						{mData : 'launchName'},
						
						{
						  mData: 'launchMoc',
						  "mRender": function(data, type, full) {
							return full.launchMoc + '<input type = "hidden" class="mocDate"  value=' + full.launchDate + '>';
						  }
						},
						{mData : 'account'},
						{mData : 'reqDate'},
						{mData : 'changeRequested'}, 
						{mData : 'kamRemarks'},
						{mData : 'cmm'},
						{mData : 'responseDate'},
						{mData : 'approvalStatus'},
						{mData : 'cmmRemarks'},
						//Q1 sprint-3 user story 2 notification bharati code
						{
						mData : 'launchReadStatus',
						className: 'hide_column',
					    "mRender": function(data, type, full) {
							return '<input type = "hidden" style="visibility:hidden" class="hidden" id="kamapproval1" runat="server" value='+ data +'>';
							
						  }
						
						},
					],
			});
	kameditapproveoTable.draw();
}

//Q1 sprint-3 user story 2 notification bharati code

$(document).ready(function() {

//hide notification when count 0
  var kamVal = $("#NotificationBadge1").html();
  //console.log(kamVal);
  if(kamVal==0){
  $("#NotificationBadge1").hide();
  }

$('#approvekambasepack_add').on('draw.dt', function() {

	 $("#approvekambasepack_add tbody tr").each(function(){
	 var col_val= $(this).find('#kamapproval1').val();
     console.log(col_val);
     if (col_val == "NEW"){
      $(this).addClass('red');  
    } else {
      $(this).addClass('black');
    }
  });
});
//setTimeout( "$('#NotificationBadge1').hide();", 2500);
})


//sprint-4 US-3 reject accounts Bharati changes Aug2021

// code for reject account in change moc pop up
	$(document).on( "click", ".rejectLaunch", function(){
	$('#successblock').hide();
		
		//$('#kamMocremarks').val('');
		var checked_field = $( "[name=editLaunchscr1KAMLaunch]:checked" );
		var launchDate = "";
		//var launchMocDate = "";
		if( checked_field.length != 0 ){
			var date = checked_field.closest( "tr" ).find( ".mocDate" ).val();
			var launch_date = date.split("/");
			launchMocDate = launch_date[1] + "/" + launch_date[0] + "/" + launch_date[2];
		}
		
		//var today = new Date(launchMocDate),
        //yy = today.getFullYear(),
       // m = today.getMonth(),
       // dd = today.getDate(),
      //  mm = dd < 21 ? ( m + 2 ) : ( m + 3 );
	//	var option = "<option value=''>Select MOC</option>";
     //   m = dd < 21 ? m : ( m + 1 );
	  // for( var i = 2; i <= 3; i++ ){
       //     var lastMonth = mm + i > 13 ? ( ( mm + i ) - 13 ) : m + i;
        //    lastMonth = ( lastMonth + "" ).length == 1 ? "0" + lastMonth : lastMonth; 
        //    var lastYear = mm + i > 13 ? yy + 1 : yy;
       //  //   option += "<option value='"+lastMonth + "" + lastYear+"'>"+lastMonth + "" + lastYear+"</option>"
       // }
		//$("#reject-kamlaunch-moc").empty().append(option);
		
		loadselectedkamRejectAccounts();   
		
	});
	
function loadrejectKamAccounts() {
	$('#reject-kamlaunch-acc').multiselect({
		includeSelectAllOption : true,
		numberDisplayed : 2,
		
		nonSelectedText : 'ALL CUSTOMERS',
		selectAllText : 'ALL CUSTOMERS',
		onChange : function(option, checked, select) {
			 var custChainSelectedData = [];
			 var selectedOptionsChange = $('#reject-kamlaunch-acc option:selected');
			 var totalLen = $('#reject-kamlaunch-acc option').length;

				if (selectedOptionsChange.length > 0 && selectedOptionsChange.length < totalLen){
					selectAll = false;
				}else if(selectedOptionsChange.length == totalLen){
					selectAll = true;
				}
			 
				for (var i = 0; i < selectedOptionsChange.length; i++) {
					custChainSelectedData.push(selectedOptionsChange[i].value);

				}
				if(selectAll == true){
					//custChainL1 = "ALL";
				}else{
					//custChainL1 = custChainSelectedData.toString();
				}
		 
			//promoTable.draw();
		},
		onDropdownHide : function(event) {
			
			var selVals = [];
			var selectedOptions = $('#reject-kamlaunch-acc option:selected');
			if (selectedOptions.length > 0 && selectAll == false) {
				for (var i = 0; i < selectedOptions.length; i++) {
					selVals.push(selectedOptions[i].value);
				}
				
			}
			
		},
		
		onSelectAll : function() {
			
			selectAll = true;
		},
		onDeselectAll : function() {
		
		}

	});
}

//Bharati Changes Q1Sprint-4 Aug2021 - Starts
	
	function loadselectedkamRejectAccounts() {
		var launchId=$('.kamLnchDetscr1:checked').val();
		 $.ajax({
			type : "GET",
			dataType: 'json',
			//contentType : "application/json; charset=utf-8",
			cache : false,
			url : "getRejectionAccountIdKam.htm?launchId="+launchId,
			async : false,
			success : function(data) {
				//console.log(data);
				var accounts=data.responseData.lisOfAcc;
				//accounts=data.responseData.lisOfAcc;
				//console.log(accounts);
				
				var option = ""; //"<option value='select'>Select Account</option>";
				for (var i = 0; i < accounts.length; i++) {
				  option += "<option value='"+accounts[i] +"'>"+accounts[i]+"</option>"
				}
				//console.log(option);
				
				$('#reject-kamlaunch-acc').empty();
				$('#reject-kamlaunch-acc').multiselect("destroy");
				$("#reject-kamlaunch-acc").empty().append(option); 
				
				loadrejectKamAccounts();
				
				
				
			},
			error : function(error) {
				console.log(error)
			}
		});
	}
//bharati added for sprint-7 US-7 successblock not display 2nd time 
$('#storelist-successblock .close').click(function(){
   $(this).parent().hide();
});
$('#targetStoreErrorBlock .close').click(function(){
   $(this).parent().hide();
});

//bharati code end here
