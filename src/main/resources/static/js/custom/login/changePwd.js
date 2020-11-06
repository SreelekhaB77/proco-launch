		$(document)
				.ready(
						function() {
							$('#msg-success').hide();
							$('#msg-error').hide();
						     $('#validateBtn').click(function(e) {
						         e.preventDefault();
						         var userId = $("#userId").val();
						         var oldPassword = $("#tme_oldPassword").val();
						         var newPassword = $("#tme_newPassword").val();
						         var value ='userId='+userId+'&current_password='+oldPassword+'&new_password='+newPassword; 
						         $.ajax({
						         url: "tmeChangePassword.htm",
						         type: "post",
						         data: value,
						         cache: false,
						         success: function(data) {
						        	 $('#msg-success').hide();
										$('#msg-error').hide();
						        	    var argsList = data.split(",");
						        	    if(argsList[0]==1){
						        	 
						        	    	data=data.substring(2,data.length);
						        	    	$('#msg-error').show();
						        	    	$("#msg-error").html(data);   
						        	    	/*window.setTimeout(function() {
						        	    	    $(".alert").fadeTo(1500, 0).slideUp(500, function(){
						        	    	        $(this).remove(); 
						        	    	    });
						        	    	}, 5000);*/
						        	    }
						        	    else if(argsList[0]==3){
						        	    	data=data.substring(2,data.length);
						        	    			$('#msg-error').show();
						        	    			$("#msg-error").html(data);
									        	    	window.setTimeout(function() {
									        	    	    $(".alert").fadeTo(1500, 0).slideUp(500, function(){
									        	    	        $(this).remove(); 
									        	    	    });
									        	    	}, 5000);
									        	    //	$("#tme_oldPassword").val();
									        	    	//$("#tme_newPassword").val();
									        
						        	    }
						        	    else{    
						        	
						        	    	data=data.substring(2,data.length);
						        	    	$('#msg-success').show();
						        	    	$("#msg-success").html(data);
						        	    	/*window.setTimeout(function() {
						        	    	    $(".alert").fadeTo(1500, 0).slideUp(500, function(){
						        	    	        $(this).remove(); 
						        	    	    });
						        	    	}, 5000);*/
						        	    }
						         }
						         });
						    });
							
							$('#pwdform')
									.bootstrapValidator(
											{
												message : 'This value is not valid',
												feedbackIcons : {
												//	valid : 'glyphicon glyphicon-ok',
													//invalid : 'glyphicon glyphicon-remove',
													validating : 'glyphicon glyphicon-refresh'
												},
												fields : {
													current_password : {
														validators : {
															notEmpty : {
																message : 'The password is required and cannot be empty'
															}, regexp : {
																regexp : /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[$@!%?&]).{6,12}$/,
																message : 'Minimum 6 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet,1 Special character and 1 Number'
													}
														}
														
													},
													new_password : {
														validators : {
															notEmpty : {
																message : 'The password is required and cannot be empty'
															},
															
															 regexp : {
																	regexp : /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[$@!%?&]).{6,12}$/,
																	message : 'Minimum 6 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet,1 Special character and 1 Number'
														}
														}
													},
													confirm_password : {
														validators : {
															notEmpty : {
																message : 'The confirm password is required and cannot be empty'
															},
															identical : {
																field : 'new_password',
																message : 'The password and its confirm are not the same'
															}
														}
													}

												}
											});
							$('#change_pswd').on('hidden.bs.modal', function (e) {
								  $(this)
								    .find("input,textarea,select")
								       .val('')
								       .end()
								    .find("input[type=checkbox], input[type=radio]")
								       .prop("checked", "")
								       .end();
								});
							$('.change-pwd form input').on('keyup',function(){
								var oldpwd = $('#tme_oldPassword').val();
								var newpwd = $('#tme_newPassword').val();
								var confirmpwd = $('#tme_confirmPwd').val();	
								 if(oldpwd == "" || newpwd == "" || confirmpwd == ""){
								
										$('#validateBtn').prop('disabled',true);
									}
								else if(oldpwd != "" && newpwd != "" && confirmpwd != ""){
									
									$('#validateBtn').prop('disabled',false);
								}
							});
							
							$('#change_pswd').on('hidden.bs.modal', function(){
						        $(this).removeData('bs.modal');
						        $('#pwdform').bootstrapValidator('resetForm', true);
						    });

						});	


