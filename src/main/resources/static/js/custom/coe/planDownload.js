$(document)
.ready(
		function() {

			$("[data-hide]").on("click", function() {
				$(this).closest("." + $(this).attr("data-hide")).hide();
			});    
			$('#msg-error').hide();
			$('#downloadBtn').click(function(e) {
				e.preventDefault();
				var startDate = $("#dwn-start-date").val();
				var endDate = $("#dwn-end-date").val();
				var moc = $("#dwn-moc").val();     
				var userId = $("#dwnld-userId").val(); 

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
							//console.log('moc invalid2'+moc)
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
						userName:userId,
				}
				//console.log(value);
				$.ajax({
					url: "checkDownloadRequest.htm",
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
							document.forms[0].action = 'coeDashboardDownloadRequest.htm?dateStart='+startDate+'&dateEnd='+endDate+'&moc='+moc+'&userName='+userId;
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
