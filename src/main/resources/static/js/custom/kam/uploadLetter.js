$(document).ready(function() {
		
	 var url = "kamStorePermissionAccount.htm";
	  
 	 $.ajax({
		        type: "GET",
		        url: url,
		        data:"",
		        contentType: "application/json; charset=utf-8",
		        dataType: "json",
		        success: function (data) {
		        	
		        	$("#upload_format").multiple = false;
		        	 $.each(data, function (index, value) {
		                 // APPEND OR INSERT DATA TO SELECT ELEMENT.
		                 $('#upload_account').append('<option value="' + value + '">' + value + '</option>');
		               
		             });
		        	
		        	 $('#upload_account').multiselect({
	            		 
		             		includeSelectAllOption: true,
		             		buttonWidth: '100%',
		             		//dropRight: true,
		             		nonSelectedText: 'ALL ACCOUNTS',
		             		onDropdownHide: function(event) {
		             			var selVals = [];
		    					var selectedOptions = $('#upload_account option:selected');
		    					if (selectedOptions.length > 0 ) {
		    						for (var i = 0; i < selectedOptions.length; i++) {
		    							selVals.push(selectedOptions[i].value);
		    						}

		    						var strData = selVals.toString();
		    						$('.switch-dynamic-first')
		    								.html(
		    										'<select class="form-control" name="selectedFormat" id="upload_format" multiple="multiple"><option values="ALL FORMATS">ALL FORMATS</option>');

		    						getFormat(strData);

		    					} else {
		    						$('.switch-dynamic-first')
		    								.html(
		    										'<input type="text" name="selectedFormat" class="form-control" id="upload_format" value="ALL FORMATS" readonly="true">');
		    						$('.switch-dynamic-second')
    								.html(
    										'<input type="text" name="selectedCluster" class="form-control" id="upload_cluster" value="ALL CLUSTERS" readonly="true">');
		    					}
		    					
		    
		    					var chkedVal =  $('select#upload_account').val();
		                        if(chkedVal){
		                        		$("#file").show();
		             		   }
		                        else {
		                        	$("#file").hide();
		                        	$("#uploadErrorMsg").hide();
		                        }

		    					             			
		                    },
		                    onDropdownShow: function(event) {
		                        var menu = $(event.currentTarget).find(".dropdown-menu");		                       
		                        menu.css("width", "100%");
		                        		                        
		                   }
		             });
		        	 //$(".caret").css('float', 'right'); 
		        	        
		        },
		        
		        error: function (msg) {
		            
		            alert(msg.responseText);
		        }
		    });


 	 function getFormat(selVal) {
 		
 		$.ajax({
		type : "GET",
		url : "kamStorePermissionFormat.htm?selectedAccountName="+encodeURIComponent(selVal),
		async : false,
		success : function(dataOutput) {
			//console.log(data);
			var smallcData = $.parseJSON(dataOutput);
			$('#upload_format').empty();
			$('#upload_format').multiselect("destroy");
			$.each(smallcData,
					function(ind, val) {
						$('#upload_format').append(
								'<option value="'+val+'">' + val
										+ '</option>');
					});
			$('#upload_format').multiselect({
       		 
         		includeSelectAllOption: true,
         		buttonWidth: '100%',
         		nonSelectedText: 'ALL FORMATS',
         		//dropRight: true,
         		
         		onDropdownHide: function(event) {
         			var selVals = [];
					var selectedOptions = $('#upload_format option:selected');
					if (selectedOptions.length > 0 ) {
						for (var i = 0; i < selectedOptions.length; i++) {
							selVals.push(selectedOptions[i].value);
						}

						var strData = selVals.toString();
						$('.switch-dynamic-second')
								.html(
										'<select class="form-control" name="selectedCluster" id="upload_cluster" multiple="multiple"><option values="ALL CLUSTERS">ALL CLUSTERS</option>');

						getCluster();

					} else {
						$('.switch-dynamic-second')
								.html(
										'<input type="text" name="selectedCluster" class="form-control" id="upload_cluster" value="ALL CLUSTERS" readonly="true">');
					}
										
					var chkedVal =  $('select#upload_account').val();
                    if(chkedVal){
                    	//$("#upload-file-type").show();
                    	$("#file").show();
         		   }
                    else {
                    	$("#file").hide();
                    	//$("#upload-file-type").show();
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
	 
 	 function getCluster() {
   	
   	  var selectedVal = $("select#upload_account").val();
   	  
   	  selectedAccountName = encodeURI(selectedVal);
   	  
   	  var val = $("select#upload_format").val();
   	  var selectedFormat = encodeURI(val);
   	  
   	  
   	  $.ajax({
				type: "GET",
				cache:false,
				url: "getKamStorePermissionCluster.htm?selectedAccountName=" + encodeURIComponent(selectedAccountName)+ "&selectedFormat="+ encodeURIComponent(selectedFormat),
				
				success: function(data){
					
					$('#upload_cluster').empty();
					$('#upload_cluster').multiselect("destroy");
					
					var myData = JSON.parse(data);
					for(var i = 0; i<myData.length; i++) {
						$('#upload_cluster').append('<option value="' + myData[i] + '">' +myData[i] + '</option>')
							
					}
					//$("#fileToUpload").show();
					
					 $('#upload_cluster').multiselect({
	            		 
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

	
	
/*$('#upload_account')
	.multiselect(
			{
				includeSelectAllOption : true,
				numberDisplayed : 2,
				 buttonWidth: '100px', 
				nonSelectedText : 'Select Account',
				
			
				onDropdownHide : function(event) {
					
					var selVals = [];
					var selectedOptions = $('#upload_account option:selected');
					if (selectedOptions.length > 0 && selectAll == false) {
						for (var i = 0; i < selectedOptions.length; i++) {
							selVals.push(selectedOptions[i].value);
						}

						var strData = selVals.toString();
						$('.switch-dynamic-first')
								.html(
										'<select class="form-control" name="selectedFormat" id="upload_format" multiple="multiple"><option values="Select Format">Select Format</option>');

						getCustChainValues(strData);

					} else {
						$('.switch-dynamic-first')
								.html(
										'<input type="text" name="selectedFormat" class="form-control" id="upload_format" value="Select Format" readonly="true">');
					}

				}

  });

	*/
	
	$('.modal-body').css('height',$( window ).height()*0.5);
	
	
	$('#uploadLetter').on('hidden.bs.modal', function() {
		
		
		 $('option', $('#upload_account')).each(function(element) {
			    $(this).removeAttr('selected').prop('selected', false);
		 });
		 $("#upload_account").multiselect('refresh');
		 
		 $('option', $('#upload_format')).each(function(element) {
			    $(this).removeAttr('selected').prop('selected', false);
		 });
		
		 
		 /*$('#upload_format').empty();
		 $('#upload_format').multiselect("refresh");*/
	     
		 $('#upload_format').empty();
		 $('#upload_format').multiselect("destroy");
		 
		 $('option', $('#upload_cluster')).each(function(element) {
			    $(this).removeAttr('selected').prop('selected', false);
		 });
		 $('#upload_cluster').empty();
		 $('#upload_cluster').multiselect("destroy");
		 
		 $('.switch-dynamic-first')
			.html(
					'<input type="text" name="selectedFormat" class="form-control" id="upload_format" value="ALL FORMATS" readonly="true">');
		 $('.switch-dynamic-second')
		 	.html(
			'<input type="text" name="selectedCluster" class="form-control" id="upload_cluster" value="ALL CLUSTERS" readonly="true">');
				 
	});
	
	
	$('#uploadLetter').on('show.bs.modal', function () {
		$('.modal-body').css('height',$( window ).height()*0.4);
		$("#file").hide();
		$("#uploadErrorMsg").hide();
		//$("#upload-file-type").hide();
		 
	});
		
   $("#upload-file").on("click",function(e){
	   e.preventDefault();
	   var image = document.getElementById("myfile").value;
       
       // if(image!=''){
     	   
             var checkimg = image.toLowerCase();
             
             var allowedExtensions = /(\.jpg|\.pdf)$/i;
             
             if (!checkimg.match(allowedExtensions)) {
                 
             	$("#uploadErrorMsg").show();
                 return false;
              }
              
               else if(FileSize > 2 )  {
            	   var img = document.getElementById("upload-file"); 
                   
                   var FileSize = file.files[0].size / 1024 / 1024;
             	   $("#uploadErrorfilesize").show();
             	  return false;
                }
               else {
            	   $("#tmefileupload").submit();
            	   return true;
               }               
         //}
   });	
   
   
   $("#download").on("click", function() {
	   
	   	$("#tmefiledownload").submit();
	   
   });
   		
		/*var url = "kamStorePermissionAccount.htm";
		  
	 	 $.ajax({
			        type: "GET",
			        url: url,
			        data:"",
			        contentType: "application/json; charset=utf-8",
			        dataType: "json",
			        success: function (data) {
			        	
			        	 $.each(data, function (index, value) {
			                 // APPEND OR INSERT DATA TO SELECT ELEMENT.
			                 $('#account').append('<option value="' + value + '">' + value + '</option>');
			               
			             });
			        	 $('#account').multiple = true;
			        	 $('#account').multiselect({
		            		 
			             		includeSelectAllOption: true,
			             		buttonWidth: '264px',
			             		dropRight: true,
			             		nonSelectedText: 'Select Account Name',
			             		onDropdownHide: function(event) {
			             			selectformat();
			                    },
			                    onDropdownShow: function(event) {
			                        var menu = $(event.currentTarget).find(".dropdown-menu");
			                        
			                        
			                       
			                       
			                        menu.css("width", 264);
			                        
			                        
			                      }
			             });
			        	 $(".caret").css('float', 'right'); 
			        	 
			        	 //getFormat();
			        
			        },
			        
			        error: function (msg) {
			            
			            alert(msg.responseText);
			        }
			    });
	 	 	 	*/
		 	 
	 	/* function getFormat() {
	 		$('select #upload_format').multiple = true;
	 		$("#upload_format").prop("multiple","multiple");
	 		 var AccountName = $('select#upload_account').val();
	    	  
	    	  console.log(AccountName);
	    	  
	    	  $.ajax({
					type: "GET",
					cache:false,
					url: "kamStorePermissionFormat.htm?selectedAccountName="+AccountName,
					//data:selectedAccountName,
					success: function(data){
						
						var myData = JSON.parse(data);
						for(var i = 0; i<myData.length; i++) {
							$('#upload_format').append('<option value="' + myData[i] + '">' +myData[i] + '</option>')
								
						}
						
						$('#upload_format').multiple = true;
						 
						$('#upload_format').multiselect({
		            		 
			             		includeSelectAllOption: true,
			             		buttonWidth: '264px',
			             		nonSelectedText: 'Select Format',
			             		dropRight: true,
			             		onDropdownHide: function(event) {
			             			getCluster();
			                    },
			                    onDropdownShow: function(event) {
			                        var menu = $(event.currentTarget).find(".dropdown-menu");
			                        
			                       
			                       
			                        menu.css("width", 264);
			                        
			                        
			                      }
			                    
			             	 });
						$(".caret").css('float', 'right'); 
						}
				});
	 	 	 
	    }
	  	      */
	 	function selectformat() {
	 		$('select #upload_format').multiple = true;
	 		$("#format").prop("multiple","multiple");
	 		 var AccountName = $('select#account').val();
	    	  
	    	  console.log(AccountName);
	    	  
	    	  $.ajax({
					type: "GET",
					cache:false,
					url: "kamStorePermissionFormat.htm?selectedAccountName="+encodeURIComponent(AccountName),
					//data:selectedAccountName,
					success: function(data){
						
						var myData = JSON.parse(data);
						for(var i = 0; i<myData.length; i++) {
							$('#format').append('<option value="' + myData[i] + '">' +myData[i] + '</option>')
								
						}
						
						$('#format').multiple = true;
						 
						$('#format').multiselect({
		            		 
			             		includeSelectAllOption: true,
			             		buttonWidth: '264px',
			             		nonSelectedText: 'ALL FORMATS',
			             		dropRight: true,
			             		onDropdownHide: function(event) {
			             			Cluster();
			                    },
			                    onDropdownShow: function(event) {
			                        var menu = $(event.currentTarget).find(".dropdown-menu");
			                        
			                       
			                       
			                        menu.css("width", 264);
			                        
			                        
			                      }
			                    
			             	 });
						//$(".caret").css('float', 'right'); 
						}
				});
	 	 	 
	    }
	 	 
	    	  
	      function Cluster() {
	    	  $("#cluster").prop("multiple","multiple");
	    	  var selectedVal = $("select#account").val();
	    	  
	    	  selectedAccountName= encodeURI(selectedVal);
	    	  
	    	  var val = $("select#format").val();
	    	  var selectedFormat = encodeURI(val);
	    	  
	    	  
	    	  $.ajax({
					type: "GET",
					cache:false,
					url: "getKamStorePermissionCluster.htm?selectedAccountName=" + encodeURIComponent(selectedAccountName)+ "&selectedFormat="+ encodeURIComponent(selectedFormat),
					
					success: function(data){
						
						var myData = JSON.parse(data);
						for(var i = 0; i<myData.length; i++) {
							$('#cluster').append('<option value="' + myData[i] + '">' +myData[i] + '</option>')
								
						}
						$("#fileToUpload").show();
						//$("#upload_cluster").multiple = true;
						 $('#cluster').multiselect({
		            		 
			             		includeSelectAllOption: true,
			             		buttonWidth: '264px',
			             		nonSelectedText: 'ALL CLUSTERS',
			             		dropRight: true,
			             		 onDropdownShow: function(event) {
				                        var menu = $(event.currentTarget).find(".dropdown-menu");
				                        
				                       
				                       
				                        menu.css("width", 264);
				                        
				                        
				                      }
			             });
						 //$(".caret").css('float', 'right'); 
						
					}
					});
	    	  
	      }
	      
     /* function getCluster() {
    	  $("#upload_cluster").prop("multiple","multiple");
    	  var selectedVal = $("select#upload_account").val();
    	  
    	  selectedAccountName= encodeURI(selectedVal);
    	  
    	  var val = $("select#upload_format").val();
    	  var selectedFormat = encodeURI(val);
    	  
    	  
    	  $.ajax({
				type: "GET",
				cache:false,
				url: "getKamStorePermissionCluster.htm?selectedAccountName=" + selectedAccountName+ "&selectedFormat="+ selectedFormat,
				
				success: function(data){
					
					var myData = JSON.parse(data);
					for(var i = 0; i<myData.length; i++) {
						$('#upload_cluster').append('<option value="' + myData[i] + '">' +myData[i] + '</option>')
							
					}
					$("#fileToUpload").show();
					//$("#upload_cluster").multiple = true;
					 $('#upload_cluster').multiselect({
	            		 
		             		includeSelectAllOption: true,
		             		buttonWidth: '264px',
		             		nonSelectedText: 'Select Cluster',
		             		dropRight: true,
		             		 onDropdownShow: function(event) {
			                        var menu = $(event.currentTarget).find(".dropdown-menu");
			                        
			                       
			                       
			                        menu.css("width", 264);
			                        
			                        
			                      }
		             });
					 $(".caret").css('float', 'right'); 
					
				}
			});
    	  
      	}    
   */
	   function downloadFile(fileName, csv) {
	
	       if (navigator.userAgent.indexOf('MSIE') !== -1
	           || navigator.appVersion.indexOf('Trident/') > 0) {
	
	           var IEwindow = window.open("", "", "Width=0px; Height=0px");
	           IEwindow.document.write('sep=,\r\n' + csv);
	           IEwindow.document.close();
	           IEwindow.document.execCommand('SaveAs', true, fileName);
	           IEwindow.close();
	       }
	       else {
	           var aLink = document.createElement('a');
	           var evt = document.createEvent("MouseEvents");
	           evt.initMouseEvent("click", true, true, window,
	               0, 0, 0, 0, 0, false, false, false, false, 0, null);
	           aLink.download = fileName;
	           aLink.href = 'data:text/csv;charset=UTF-8,' + encodeURIComponent(csv);
	           aLink.dispatchEvent(evt);
	       }
	   }
  
	   jQuery(".downloadCSV").on('click', function (e) {
	
	       jQuery.ajax({
	           type: "POST",
	           data: {},
	           url: 'downloadStorePermissionLetterList.htm',
	           dataType: 'json',
	           success: function (json) {
	
	               var csv = JSON.parse(json.replace(/"([\w]+)":/g, function ($0, $1) { return ('"' + $1.toLowerCase() + '":'); }));
	
	               downloadFile('download.csv', encodeURIComponent(csv));
	           }
	       });
	
	   });
	            
          
});
