$(document)
.ready(
		function() {
			
			$('#msg-error').hide();

			enableDatePicker();
			$('#resetBtn').on("click", function(){
				$('#dwn-start-date').datepicker("option", "disabled", false);
				$('#dwn-end-date').datepicker("option", "disabled", false);
				$('#date-moc-error').hide();
				$('#dwn-start-error').hide();
				$('#dwn-end-error').hide();
				$('.moc_err').hide();
				$('#dwn-moc').prop('disabled',false);
				$('#msg-error').hide();

			});
			$('#cancelBtn').on("click", function(){

				$('#dwn-start-date').val('');
				$('#dwn-end-date').val('');
				$('#dwn-start-date').datepicker("option", "disabled", false);
				$('#dwn-end-date').datepicker("option", "disabled", false);
				$('#dwn-moc').val('');
				$('#dwnld-userId').val('');
				$("#dwnld-account").val('');
				$('#date-moc-error').hide();
				$('#dwnld-userStatus').val('');
				$('#dwn-start-error').hide();
				$('#dwn-end-error').hide();
				$('.moc_err').hide();
				$('#msg-error').hide();
				$('#dwn-moc').prop('disabled',false);
				$('#downloadPlans').modal('hide');
			});
			
			$('#crossBtn').on("click", function(){

				$('#dwn-start-date').val('');
				$('#dwn-end-date').val('');
				$('#dwn-start-date').datepicker("option", "disabled", false);
				$('#dwn-end-date').datepicker("option", "disabled", false);
				$('#dwn-moc').val('');
				$('#dwnld-userId').val('');
				$("#dwnld-account").val('');
				$('#date-moc-error').hide();
				$('#dwnld-userStatus').val('');
				$('#dwn-start-error').hide();
				$('#dwn-end-error').hide();
				$('.moc_err').hide();
				$('#msg-error').hide();
				$('#dwn-moc').prop('disabled',false);
				$('#downloadPlans').modal('hide');
			});

			$("[data-hide]").on("click", function() {
				$(this).closest("." + $(this).attr("data-hide")).hide();
			});    




			$('#downloadBtn').click(function(e) {
				e.preventDefault();
				var startDate = $("#dwn-start-date").val();
				var endDate = $("#dwn-end-date").val();
				var moc = $("#dwn-moc").val();   
				var status = $("#dwnld-userStatus").val(); 
				var userId = $("#dwnld-userId").val(); 
				var account = $("#dwnld-account").val(); 
				
				if(startDate != '' && endDate != ''){
					$('#dwn-moc').prop('disabled',true);
					$('#dwn-start-error').hide();
					$('#dwn-end-error').hide();
					$('#date-moc-error').hide();
				}

				if(startDate != ''){
					if(endDate == ''){
						$('#dwn-end-error').show();
						$('#dwn-start-error').hide();
						return false;
					}
				}

				if(endDate != ''){
					if(startDate == ''){
						$('#dwn-start-error').show();
						$('#dwn-end-error').hide();
						return false;

					}

				}
				if(startDate == '' && endDate == '' && moc == ''){
					$('#date-moc-error').show();
					return false;
				}
				if(moc!='' ){
				var regx1=/^(1[0-2]|0[1-9]|1[012])(20\d{2}|19\d{2}|0(?!0)\d|[1-9]\d)+(,((1[0-2]|0[1-9]|1[012])(20\d{2}|19\d{2}|0(?!0)\d|[1-9]\d))+)*$/;
				var regx2=/^(1[0-2]|0[1-9]|1[012])(20\d{2}|19\d{2}|0(?!0)\d|[1-9]\d)$/;
				if(!moc.match(regx1)){
					$('#moc_err').show().html('Moc is not valid');

					return false;
				}
				var arr=moc.split(",")
				if(checkIfArrayIsUnique(arr)!=true)
				{
					$('#moc_err').show().html('Moc should be unique');	
					return false;
				}else{
				
					for(var i=0;i<arr.length;i++){
						if (!arr[i].match(regx2)) {
							$('#moc_err').show().html('Please enter valid moc');					
							return false;					
						}
					}
				}
				}
				$('#moc_err').hide();
				var value = {
						dateStart:startDate,
						dateEnd:endDate,
						moc:moc,
						status:status,
						userName:userId,
						account:account
				}

				//console.log(value);
				$.ajax({
					url: "checkdownloadParamLimitedPlanToMapOutlet.htm",
					type: "post",
					data: value,
					cache: false,
					beforeSend:function(){
						$('.popupLoader').show();
						
					},
					success: function(data) {

						if(data == 0) {
							$('#msg-error').show();
							$("#msg-error span").html("No Record Found."); 
							$('.popupLoader').hide();
						} else {
							$('#msg-error').hide();
							document.forms[0].method = "POST";
							document.forms[0].action = 'downloadParamLimitedPlanToMapOutlet.htm?dateStart='+startDate+'&dateEnd='+endDate+'&moc='+moc+'&status='+status+'&userName='+userId+'&account='+account;
					
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
			var dwnStartDate = $('#dwn-start-date').val();
			var dwnEndDate = $('#dwn-end-date').val();
			if(dwnStartDate != '' && dwnEndDate != ''){
				$('#dwn-moc').val("");
				$('#dwn-moc').prop('disabled',true);
			} 
		} 
	});

	$( ".tme-datepicker-end" ).datepicker({
 
		showOn: "both",
		buttonImage: "assets/images/calender.png",
		buttonImageOnly: true,
		buttonText: "Select date",
		dateFormat: "dd/mm/yy",
		onSelect: function (selected) {
			var dwnStartDate = $('#dwn-start-date').val();
			var dwnEndDate = $('#dwn-end-date').val();
			if(dwnStartDate != '' && dwnEndDate != ''){
				$('#dwn-moc').val("");
				$('#dwn-moc').prop('disabled',true);
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