$(document)
.ready(
		function() {

			$('#msg-error').hide();

			enableDatePicker();
			$('#resetBtn').on("click", function(){
				$('#dwn-start-date2').datepicker("option", "disabled", false);
				$('#dwn-end-date2').datepicker("option", "disabled", false);
				$('#date-moc-error').hide();
				$('#dwn-start-error').hide();
				$('#dwn-end-error').hide();
				$('.moc_err').hide();
				$('#dwn-moc2').prop('disabled',false);
				$('#msg-error').hide();
				$('#level-error').hide();

			});
			$('#cancelBtn').on("click", function(){

				$('#dwn-start-date2').val('');
				$('#dwn-end-date2').val('');
				$('#dwn-start-date2').datepicker("option", "disabled", false);
				$('#dwn-end-date2').datepicker("option", "disabled", false);
				$('#dwn-moc2').val('');
				$('#dwnld-userLevel').val('');
				$('#dwnld-userId').val('');
				$('#dwnld-userStatus').val('');
				$('#date-moc-error').hide();
				$('#dwn-start-error').hide();
				$('#dwn-end-error').hide();
				$('.moc_err').hide();
				$('#msg-error').hide();
				$('#level-error').hide();
				$('#dwn-moc2').prop('disabled',false);
				/*  setTimeout(function(){

									   }, 3000); */
				$('#downloadAsset').modal('hide');
			});
			
				$('#crossBtn').on("click", function(){
				
				$('#dwn-start-date2').val('');
				$('#dwn-end-date2').val('');
				$('#dwn-start-date2').datepicker("option", "disabled", false);
				$('#dwn-end-date2').datepicker("option", "disabled", false);
				$('#dwn-moc2').val('');
				$('#dwnld-userLevel').val('');
				$('#dwnld-userId').val('');
				$('#dwnld-userStatus').val('');
				$('#date-moc-error').hide();
				$('#dwn-start-error').hide();
				$('#dwn-end-error').hide();
				$('.moc_err').hide();
				$('#level-error').hide();
				   $('#dwn-moc2').prop('disabled',false);
				  /*  setTimeout(function(){
					  
				   }, 3000); */
				   $('#downloadAsset').modal('hide');
			});

			$("[data-hide]").on("click", function() {
				$(this).closest("." + $(this).attr("data-hide")).hide();
			});    




			$('#downloadBtn2').click(function(e) {
				e.preventDefault();
				
				
				var startDate = $("#dwn-start-date2").val();
				var endDate = $("#dwn-end-date2").val();
				var moc = $("#dwn-moc2").val();
				var level = $("#dwnld-userLevel2").val();    
				
				//console.log(startDate + " "+ endDate  + " "+ moc  + " "+ level);
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
				if(startDate == ''  && endDate == '' && moc == '' && level == '' ){
					$('#date-moc-error').show();
					$('#level-error').show();
					return false;
				}
				if(startDate == ''  && endDate == '' && moc == '' && level != '' ){
					$('#date-moc-error').show();
					//$('#level-error').show();
					return false;
				}
				
				//console.log(startDate +endDate );
				if(moc!='' ){
			//	var regx1=/^(0?[1-9]|1[012])+(,\d+)*$/;				
			//	var regx2=/^([1-9]|1[012])$/;
					var regx1=/^(1[0-2]|0[1-9]|1[012])(20\d{2}|19\d{2}|0(?!0)\d|[1-9]\d)+(,((1[0-2]|0[1-9]|1[012])(20\d{2}|19\d{2}|0(?!0)\d|[1-9]\d))+)*$/;
					var regx2=/^(1[0-2]|0[1-9]|1[012])(20\d{2}|19\d{2}|0(?!0)\d|[1-9]\d)$/;
				if(!moc.match(regx1)){
					//console.log('moc invalid1'+moc)
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
						//	console.log('moc invalid2'+moc)
							$('#moc_err').show().html('Please enter valid moc');					
							return false;					
						}
					}
				}
				}
				$('#moc_err').hide();
				 if(level == ''){
					$('#level-error').show();
					return false;
				}
				var value = {
						dateStart:startDate,
						dateEnd:endDate,
						moc:moc,
						level:level,
						

				}
				// var value ='dateStart='+startDate+'&dateEnd='+endDate+'&moc='+moc+'&level='+level+'&status='+status+'&userName='+userId; 
				//console.log(value);
				$.ajax({
					url: "checkDownloadAssetRequest.htm",
					type: "POST",
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
							//document.forms[0].action = 'downloadPlanRequest.htm?dateStart='+startDate+'&dateEnd='+endDate+'&moc='+moc+'&level='+level+'&status='+status+'&userName='+userId;
							document.forms[0].action = 'downloadAssetRequest.htm?plan='+value;
							document.forms[0].submit();
							$('.popupLoader').hide();
							return true;
						}
					}
				});
			});


		});	

function checkIfArrayIsUnique(arr)  {
	   var myArray = arr.sort();

	    for (var i = 0; i < myArray.length; i++) {
	        if (myArray.indexOf(myArray[i]) !== myArray.lastIndexOf(myArray[i])) { 
	            return false; 
	        } 
	    } 

	    return true;
}
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
			var dwnStartDate = $('#dwn-start-date2').val();
			var dwnEndDate = $('#dwn-end-date2').val();
			if(dwnStartDate != '' && dwnEndDate != ''){
				$('#dwn-moc2').val("");
				$('#dwn-moc2').prop('disabled',true);
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
		onSelect: function (selected) {
			var dwnStartDate = $('#dwn-start-date2').val();
			var dwnEndDate = $('#dwn-end-date2').val();
			if(dwnStartDate != '' && dwnEndDate != ''){
				$('#dwn-moc2').val("");
				$('#dwn-moc2').prop('disabled',true);
			}
		}
	});
}

function disableDateOnChangeMOC(){
	var dwnMoc = $('#dwn-moc2').val();
	if(dwnMoc != ''){
		$('#dwn-start-date2').val("");
		$('#dwn-end-date2').val("");
		$('#dwn-start-date2').prop('disabled',true);
		$('#dwn-end-date2').prop('disabled',true);
		$('.tme-datepicker-start').datepicker('disable');
		$('.tme-datepicker-end').datepicker('disable');
	}
}