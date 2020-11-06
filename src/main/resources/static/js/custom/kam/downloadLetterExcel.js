$(document).ready(function() {
		
	 var url = "kamStorePermissionAccount.htm";
	  
 	 $.ajax({
		        type: "GET",
		        url: url,
		        data:"",
		        contentType: "application/json; charset=utf-8",
		        dataType: "json",
		        success: function (data) {
		        	
		        	$("#upload_format1").multiple = false;
		        	 $.each(data, function (index, value) {
		                 // APPEND OR INSERT DATA TO SELECT ELEMENT.
		                 $('#upload_account1').append('<option value="' + value + '">' + value + '</option>');
		               
		             });
		        	
		        	 $('#upload_account1').multiselect({
	            		 
		             		includeSelectAllOption: true,
		             		buttonWidth: '100%',
		             		//dropRight: true,
		             		nonSelectedText: 'Select Account Name',
		             		onDropdownHide: function(event) {
		             			var selVals = [];
		    					var selectedOptions = $('#upload_account1 option:selected');
		    					if (selectedOptions.length > 0 ) {
		    						for (var i = 0; i < selectedOptions.length; i++) {
		    							selVals.push(selectedOptions[i].value);
		    						}

		    						var strData = selVals.toString();
		    						$('.switch-dynamic-first1')
		    								.html(
		    										'<select class="form-control" name="selectedFormat1" id="upload_format1" multiple="multiple"><option values="ALL FORMATS">ALL FORMATS</option>');

		    						getFormat1(strData);

		    					} else {
		    						$('.switch-dynamic-first1')
		    								.html(
		    										'<input type="text" name="selectedFormat1" class="form-control" id="upload_format1" value="ALL FORMATS" readonly="true">');
		    						$('.switch-dynamic-second1')
    								.html(
    										'<input type="text" name="selectedCluste1r" class="form-control" id="upload_cluster1" value="ALL CLUSTERS" readonly="true">');
		    					}
		                    },
		                    onDropdownShow: function(event) {
		                        var menu = $(event.currentTarget).find(".dropdown-menu");		                       
		                        menu.css("width", "100%");
		                        		                        
		                   }
		             });
		        	// $(".caret").css('float', 'right'); 
		        	        
		        },
		        
		        error: function (msg) {
		            
		            alert(msg.responseText);
		        }
		    });


 	 function getFormat1(selVal) {
 		
 		$.ajax({
		type : "GET",
		url : "kamStorePermissionFormat.htm?selectedAccountName="+encodeURIComponent(selVal),
		async : false,
		success : function(dataOutput) {
			//console.log(data);
			var smallcData = $.parseJSON(dataOutput);
			$('#upload_format1').empty();
			$('#upload_format1').multiselect("destroy");
			$.each(smallcData,
					function(ind, val) {
						$('#upload_format1').append(
								'<option value="'+val+'">' + val
										+ '</option>');
					});
			$('#upload_format1').multiselect({
       		 
         		includeSelectAllOption: true,
         		buttonWidth: '100%',
         		nonSelectedText: 'ALL FORMATS',
         		//dropRight: true,
         		
         		onDropdownHide: function(event) {
         			var selVals = [];
					var selectedOptions = $('#upload_format1 option:selected');
					if (selectedOptions.length > 0 ) {
						for (var i = 0; i < selectedOptions.length; i++) {
							selVals.push(selectedOptions[i].value);
						}

						var strData = selVals.toString();
						$('.switch-dynamic-second1')
								.html(
										'<select class="form-control" name="selectedCluster1" id="upload_cluster1" multiple="multiple"><option values="ALL CLUSTERS">ALL CLUSTERS</option>');

						getCluster1();

					} else {
						$('.switch-dynamic-second1')
								.html(
										'<input type="text" name="selectedCluster1" class="form-control" id="upload_cluster1" value="ALL CLUSTERS" readonly="true">');
					}
					             			
                },
                onDropdownShow: function(event) {
                    var menu = $(event.currentTarget).find(".dropdown-menu");
                                      
                    menu.css("width", "100%");
                                       
                  }             
                
         	 });
			//$(".caret").css('float', 'right'); 
		},
			error : function(error) {
				console.log(error)
			}

 		});
 	 }
	 
 	 function getCluster1() {
   	
   	  var selectedVal = $("select#upload_account1").val();
   	  
   	  selectedAccountName = encodeURI(selectedVal);
   	  
   	  var val = $("select#upload_format1").val();
   	  var selectedFormat = encodeURI(val);
   	  
   	  
   	  $.ajax({
				type: "GET",
				cache:false,
				url: "getKamStorePermissionCluster.htm?selectedAccountName=" + encodeURIComponent(selectedAccountName)+ "&selectedFormat="+ encodeURIComponent(selectedFormat),
				
				success: function(data){
					
					$('#upload_cluster1').empty();
					$('#upload_cluster1').multiselect("destroy");
					
					var myData = JSON.parse(data);
					for(var i = 0; i<myData.length; i++) {
						$('#upload_cluster1').append('<option value="' + myData[i] + '">' +myData[i] + '</option>')
							
					}
					//$("#fileToUpload").show();
					
					 $('#upload_cluster1').multiselect({
	            		 
		             		includeSelectAllOption: true,
		             		buttonWidth: '100%',
		             		nonSelectedText: 'ALL CLUSTERS',
		             		//dropRight: true,
		             	/*	onDropdownHide: function(event) {
		             			var selVals = [];
		    					var selectedOptions = $('#upload_cluster option:selected');
		    					if (selectedOptions.length > 0 ) {
		    						for (var i = 0; i < selectedOptions.length; i++) {
		    							selVals.push(selectedOptions[i].value);
		    						}

		    						var strData = selVals.toString();
		    						$('.switch-dynamic-second')
		    								.html(
		    										'<select class="form-control" name="selectedCluster" id="upload_cluster" multiple="multiple"><option values="Select Cluster">Select Cluster</option>');


		    					} else {
		    						$('.switch-dynamic-second')
		    								.html(
		    										'<input type="text" name="selectedCluster" class="form-control" id="upload_cluster" value="Select Cluster" readonly="true">');
		    					}
		    							    					
		    					var chkedVal =  $('select#upload_account').val();
		                        if(chkedVal){
		                        		$("#file").show();
		             		   }
		                        else {
		                        	$("#file").hide();
		                        }

		    					             			
		                    },*/
		             		 onDropdownShow: function(event) {
			                        var menu = $(event.currentTarget).find(".dropdown-menu");
			                        			                       
			                        menu.css("width", "100%");			                        
			                        
			                }
		             });
					 //$(".caret").css('float', 'right');										
				}
			});  	  
     } 
 	 
 	 
 	$('#downloadStoreList').on('hidden.bs.modal', function() {
		
		
		 $('option', $('#upload_account1')).each(function(element) {
			    $(this).removeAttr('selected').prop('selected', false);
		 });
		 $("#upload_account1").multiselect('refresh');
		 
		 $('option', $('#upload_format1')).each(function(element) {
			    $(this).removeAttr('selected').prop('selected', false);
		 });
		
		 
		 /*$('#upload_format').empty();
		 $('#upload_format').multiselect("refresh");*/
	     
		 $('#upload_format1').empty();
		 $('#upload_format1').multiselect("destroy");
		 
		 $('option', $('#upload_cluster1')).each(function(element) {
			    $(this).removeAttr('selected').prop('selected', false);
		 });
		 $('#upload_cluster1').empty();
		 $('#upload_cluster1').multiselect("destroy");
		 
		 $('.switch-dynamic-first1')
			.html(
					'<input type="text" name="selectedFormat1" class="form-control" id="upload_format1" value="ALL FORMATS" readonly="true">');
		 $('.switch-dynamic-second1')
		 	.html(
			'<input type="text" name="selectedCluster1" class="form-control" id="upload_cluster1" value="ALL CLUSTERS" readonly="true">');
				 
	});
	

});
