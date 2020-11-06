	function errorMsgHide() {		
		$('#errorMsg').hide();
		
	}
	

	$(document)
			.ready(
					function() {							
						$('#defaultForm')
								.bootstrapValidator(
										{
											//        live: 'disabled',
											message : 'This value is not valid',
											feedbackIcons : {
											//	valid : 'glyphicon glyphicon-ok',
												invalid : 'glyphicon glyphicon-remove',
												validating : 'glyphicon glyphicon-refresh'
											},
											fields : {
												userId : {
													message : 'The userId is not valid',
													validators : {
														notEmpty : {
															message : 'The username is required and cannot be empty'
														},
														/*stringLength : {
															min : 5,
															max : 30,
															message : 'The username must be more than 6 and less than 30 characters long'
														},
														regexp : {
															regexp : /^[a-zA-Z0-9_\.]+$/,
															message : 'The username can only consist of alphabetical, number, dot and underscore'
														},*/
													}
												},

												password : {
													validators : {
														notEmpty : {
															message : 'The password is required and cannot be empty'
														},
														 /* regexp : {
															regexp : /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[$@!%?&]).{6,12}$/,
															message : 'Minimum 6 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet and 1 Number'
														}, */ 
														/*different : {
															field : 'userId',
															message : 'The password cannot be the same as username'
														}*/
													}
												},

											}
										})
					});
