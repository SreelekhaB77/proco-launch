$(document)
.ready(
		function() {
		
			$('#depot-msg-error').hide();
			enableDatePicker();
			
			
			$('#kamDepotDownloadBtn').click(function(e) {
				e.preventDefault();
			
				var startDate = $("#depot-dwn-start-date").val();
				var endDate = $("#depot-dwn-end-date").val();
				var moc = $("#depot-dwn-moc").val();			 
				var status = $("#depot-dwnld-userStatus").val(); 
						//console.log('inside');		
				if(startDate != '' && endDate != ''){
					$('#depot-dwn-moc').prop('disabled',true);
					$('#depot-dwn-start-error').hide();
					$('#depot-dwn-end-error').hide();
					$('#depot-date-moc-error').hide();
				}

				if(startDate != ''){
					if(endDate == ''){
						$('#depot-dwn-end-error').show();
						$('#depot-dwn-start-error').hide();
						return false;
					}
				}

				if(endDate != ''){
					if(startDate == ''){
						$('#depot-dwn-start-error').show();
						$('#depot-dwn-end-error').hide();
						return false;
					}
				}
				if(startDate == '' && endDate == '' && moc == ''){
					$('#depot-date-moc-error').show();
					return false;
				}
				if(moc!='' ){
				//var regx1=/^(0?[1-9]|1[012])+(,\d+)*$/;				
				//var regx2=/^([1-9]|1[012])$/;
					var regx1=/^(1[0-2]|0[1-9]|1[012])(20\d{2}|19\d{2}|0(?!0)\d|[1-9]\d)+(,((1[0-2]|0[1-9]|1[012])(20\d{2}|19\d{2}|0(?!0)\d|[1-9]\d))+)*$/;
					var regx2=/^(1[0-2]|0[1-9]|1[012])(20\d{2}|19\d{2}|0(?!0)\d|[1-9]\d)$/;
				if(!moc.match(regx1)){
					//console.log('moc invalid1'+moc)
					$('#depot-moc_err').show().html('Moc is not valid');

					return false;
				}
				var arr=moc.split(",")
				if(checkIfArrayIsUnique(arr)!=true)
				{
					$('#depot-moc_err').show().html('Moc should be unique');	
					return false;
				}else{
				
					for(var i=0;i<arr.length;i++){
						if (!arr[i].match(regx2)) {
							//console.log('moc invalid2'+moc)
							$('#depot-moc_err').show().html('Please enter valid moc');					
							return false;					
						}
					}
				}
				}
				$('#depot-moc_err').hide();
				var value = {
						dateStart:startDate,
						dateEnd:endDate,
						moc:moc,
						status:status

				}
				//var value ='dateStart='+startDate+'&dateEnd='+endDate+'&moc='+moc+'&status='+status; 
				$.ajax({
					url: "checkDepoRequest.htm",
					type: "post",
					data: value,
					cache: false,
					beforeSend:function(){
						$('.popupLoader').show();
						
					},
					success: function(data) {
						if(data == 0) {
							$('#depot-msg-error').show();
							$("#depot-msg-error span").html("No Record Found."); 
							$('.popupLoader').hide();
						} else {

							$('#depot-msg-error').hide();
							document.forms[0].method = "POST";
							document.forms[0].action = 'kamDepoDownloadRequest.htm?dateStart='+startDate+'&dateEnd='+endDate+'&moc='+moc+'&status='+status;
							//document.forms[0].action = 'kamDepoDownloadRequest.htm?plan='+value;
							document.forms[0].submit();
							$('.popupLoader').hide();
							return true;
						}
					

					}
				});
			});


		});	

function enableDatePicker() {
	$( ".depot-tme-datepicker-start" ).datepicker({
		//maxDate: "+3M", 
		showOn: "both",
		buttonImage: "assets/images/calender.png",
		buttonImageOnly: true,
		buttonText: "Select date",
		dateFormat: "dd/mm/yy",
		onSelect: function (selected) {

			$(".tme-datepicker-end").datepicker("option", "minDate",selected);
			var dwnStartDate = $('#depot-dwn-start-date').val();
			var dwnEndDate = $('#depot-dwn-end-date').val();
			if(dwnStartDate != '' && dwnEndDate != ''){
				$('#depot-dwn-moc').val("");
				$('#depot-dwn-moc').prop('disabled',true);
			} 
		} 
	});

	$( ".depot-tme-datepicker-end" ).datepicker({
		//maxDate: "+3M", 
		showOn: "both",
		buttonImage: "assets/images/calender.png",
		buttonImageOnly: true,
		buttonText: "Select date",
		dateFormat: "dd/mm/yy",
		onSelect: function (selected) {
			var dwnStartDate = $('#depot-dwn-start-date').val();
			var dwnEndDate = $('#depot-dwn-end-date').val();
			if(dwnStartDate != '' && dwnEndDate != ''){
				$('#depot-dwn-moc').val("");
				$('#depot-dwn-moc').prop('disabled',true);
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

