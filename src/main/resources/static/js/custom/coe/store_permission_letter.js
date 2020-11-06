$(document).ready(function(){
	//alert("loaded");
	
	//update date of expiry

	
	
	//download csv file
	
	$('#download').on('click',function(){
		var visiPlanList = [];
		var fileList;
				$.each($("input[name='file-name']:checked"),
				function() {
						visiPlanList.push($(this).val());
						});
				fileList = visiPlanList.toString();
				if(visiPlanList.length>0){
					//$('#coe-storepermission-form').append('<input type="hidden" id="file" name="file" value=fileList/>');
					$('#file').val(fileList);
					document.forms['coe-storepermission-form'].method = "GET";
					document.forms['coe-storepermission-form'].action = "downloadCoeStorePermissionLetter.htm";
					document.forms['coe-storepermission-form'].submit();
				}
	   });
	

	$('#downloadExcel').on('click',function(){
		var visiPlanList = [];
		var dataArr=[];
		var fileList;
		var acc;
				$.each($("input[name='file-name']:checked"),
				function() {
						visiPlanList.push($(this).val());
						});
				fileList = visiPlanList.toString();
				$.each($("#current_summ tr.selected"),function(){ //get each tr which has selected class
			        dataArr.push($(this).find('td').eq(3).text()); //find its first td and push the value
			        //dataArr.push($(this).find('td:first').text()); You can use this too
			    });
				acc=dataArr.toString();
				if(visiPlanList.length>0){
					//$('#coe-storepermission-form').append('<input type="hidden" id="file" name="file" value=fileList/>');
					$('#file').val(fileList);
					$('#acc').val(acc);
					document.forms['coe-storepermission-form'].method = "GET";
					document.forms['coe-storepermission-form'].action = "downloadCoeStorePermissionData.htm";
					document.forms['coe-storepermission-form'].submit();
				}
	   });

				  				  
});
	
