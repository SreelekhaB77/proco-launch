		$(document)
				.ready(
						function() {
							 enableDatePicker();
								$('#hfs-resetBtn').on("click", function(){
									$('#hfs-dwn-start-date').datepicker("option", "disabled", false);
									$('#hfs-dwn-end-date').datepicker("option", "disabled", false);
									$('#hfs-date-moc-error').hide();
									$('#hfs-dwn-start-error').hide();
									$('#hfs-dwn-end-error').hide();
									$('.hfs-moc_err').hide();
									$('#hfs-msg-error').hide();
									$('#hfs-dwn-moc').prop('disabled',false);
								});
								
								$('#hfs-cancelBtn').on("click", function(){
									
									$('#hfs-dwn-start-date').val('');
									$('#hfs-dwn-end-date').val('');
									$('#hfs-dwn-start-date').datepicker("option", "disabled", false);
									$('#hfs-dwn-end-date').datepicker("option", "disabled", false);
									$('#hfs-dwn-moc').val('');
									$('#hfs-dwnld-userId').val('');
									$('#hfs-date-moc-error').hide();
									$('#hfs-dwn-start-error').hide();
									$('#hfs-dwn-end-error').hide();
									$('.hfs-moc_err').hide();
									   $('#hfs-dwn-moc').prop('disabled',false);
									   $('#HFS-Download-Plans').modal('hide');
								});

						$('#hfs-crossBtn').on("click", function(){
									
									$('#hfs-dwn-start-date').val('');
									$('#hfs-dwn-end-date').val('');
									$('#hfs-dwn-start-date').datepicker("option", "disabled", false);
									$('#hfs-dwn-end-date').datepicker("option", "disabled", false);
									$('#hfs-dwn-moc').val('');
									$('#hfs-dwnld-userId').val('');
									$('#hfs-date-moc-error').hide();
									$('#hfs-dwn-start-error').hide();
									$('#hfs-dwn-end-error').hide();
									$('.hfs-moc_err').hide();
									   $('#hfs-dwn-moc').prop('disabled',false);
									   $('#HFS-Download-Plans').modal('hide');
								});

							$("[data-hide]").on("click", function() {
								$(this).closest("." + $(this).attr("data-hide")).hide();
							});    
							$('#hfs-msg-error').hide();
						     $('#hfs-downloadBtn').click(function(e) {
						         e.preventDefault();
						         var startDate = $("#hfs-dwn-start-date").val();
						         var endDate = $("#hfs-dwn-end-date").val();
						         var moc = $("#hfs-dwn-moc").val();     
						         var userId = $("#hfs-dwnld-userId").val(); 
						      
						         if(startDate != '' && endDate != ''){
						        	 $('#hfs-dwn-moc').prop('disabled',true);
						        	 $('#hfs-dwn-start-error').hide();
						        	 $('#hfs-dwn-end-error').hide();
						        	 $('#hfs-date-moc-error').hide();
						         }
						         
						         if(startDate != ''){
						        	 if(endDate == ''){
						        		 $('#hfs-dwn-end-error').show();
						        		 $('#hfs-dwn-start-error').hide();
											return false;
						        		 
						        	 }
						        	 
						         }
						         
						         if(endDate != ''){
						        	 if(startDate == ''){
						        		 $('#hfs-dwn-start-error').show();
						        		 $('#hfs-dwn-end-error').hide();
											return false;
						        		 
						        	 }
						        	 
						         }
										if(startDate == '' && endDate == '' && moc == ''){
										$('#hfs-date-moc-error').show();
										return false;
										}
										if(moc!='' ){
											var regx1=/^(1[0-2]|0[1-9]|1[012])(20\d{2}|19\d{2}|0(?!0)\d|[1-9]\d)+(,((1[0-2]|0[1-9]|1[012])(20\d{2}|19\d{2}|0(?!0)\d|[1-9]\d))+)*$/;
											var regx2=/^(1[0-2]|0[1-9]|1[012])(20\d{2}|19\d{2}|0(?!0)\d|[1-9]\d)$/;
										if(!moc.match(regx1)){
											//console.log('moc invalid1'+moc)
											$('#hfs-moc_err').show().html('Moc is not valid');

											return false;
										}
										var arr=moc.split(",")
										if(checkIfArrayIsUnique(arr)!=true)
										{
											$('#hfs-moc_err').show().html('Moc should be unique');	
											return false;
										}else{
										
											for(var i=0;i<arr.length;i++){
												if (!arr[i].match(regx2)) {
													//console.log('moc invalid2'+moc)
													$('#hfs-moc_err').show().html('Please enter valid moc');					
													return false;					
												}
											}
										}
										}
										$('#hfs-moc_err').hide();

										var value = {
												dateStart:startDate,
												dateEnd:endDate,
												moc:moc,
												userName:userId,
										}
										//console.log(value);
						         $.ajax({
						         url: "checkHFSDownloadRequest.htm",
						         type: "post",
						         data: value,
						         cache: false,
						         beforeSend:function(){
										$('.popupLoader').show();
									},
						         success: function(data) {
						        	 
						        	 if(data == 0) {
						        		$('#hfs-msg-error').show();
						        	    $("#hfs-msg-error span").html("No Record Found."); 
						        	    $('.popupLoader').hide();
						        	 } else {
						        		 $('#hfs-msg-error').hide();
						        			document.forms[0].method = "POST";
						        			document.forms[0].action = 'hfsDownloadPlanRequest.htm?dateStart='+startDate+'&dateEnd='+endDate+'&moc='+moc+'&userName='+userId;
						        			document.forms[0].submit();
						        			$('.popupLoader').hide();
						        			return true;
						        	 }
						         	}
						         });
						    });
							
						    
						});	
		function enableDatePicker() {
			$( ".tme-datepicker-start" ).datepicker({
				//maxDate: "+3M", 
				  showOn: "both",
				  buttonImage: "assets/images/calender.png",
				  buttonImageOnly: true,
				  buttonText: "Select date",
				  dateFormat: "dd/mm/yy",
				  onSelect: function (selected) {
					
	                $(".tme-datepicker-end").datepicker("option", "minDate",selected);
	                 var dwnStartDate = $('#hfs-dwn-start-date').val();
					                                                  var dwnEndDate = $('#hfs-dwn-end-date').val();
					                                                  if(dwnStartDate != '' && dwnEndDate != ''){
					                                                         $('#hfs-dwn-moc').val("");
					                                                         $('#hfs-dwn-moc').prop('disabled',true);
					                                                  }
	            } 
				});

         $( ".tme-datepicker-end" ).datepicker({
				//maxDate: "+3M", 
				  showOn: "both",
				  buttonImage: "assets/images/calender.png",
				  buttonImageOnly: true,
				  buttonText: "Select date",
				  dateFormat: "dd/mm/yy",
				   onSelect: function (selected) {
				                                             var dwnStartDate = $('#hfs-dwn-start-date').val();
				                                                  var dwnEndDate = $('#hfs-dwn-end-date').val();
				                                                  if(dwnStartDate != '' && dwnEndDate != ''){
				                                                         $('#hfs-dwn-moc').val("");
				                                                         $('#hfs-dwn-moc').prop('disabled',true);
				                                                  }
				                                     
				                                     
				                                     }

				});
		}

		  function checkIfArrayIsUnique(arr)  {
   		   var myArray = arr.sort();

   		    for (var i = 0; i < myArray.length; i++) {
   		        if (myArray.indexOf(myArray[i]) !== myArray.lastIndexOf(myArray[i])) { 
   		            return false; 
   		        } 
   		    } 

   		    return true;
   	  }