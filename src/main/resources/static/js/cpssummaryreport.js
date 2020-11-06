var spinnerWidth = "100";
var spinnerHeight = "100";


function connectivityExecutionDetails(date) {
 		   // var emptyData = 'No Data Available';
 		    $.ajax({
 		        type: 'POST',
 		        url: 'cpsSummaryReport.htm',
 		        data: {
 		            mocDate: date
 		        },
 		       beforeSend:function(){
					 ajaxLoader(spinnerWidth,spinnerHeight);
 		    	  
				},
 		       statusCode: {
 		          404: function() {
 		        	 window.location.pathname = "/VisibilityAssetTracker/logoutForm.htm";
 		          }
 		      },
 		       /* async: false, 	*/	      
 		        success: function(response) {
 		        	$('.loader').hide();
 		            try {
 		            	 		            	
 		                var strResponse = jQuery.parseJSON(response);
 		                var overAllLen = strResponse.cpsOverAll.length;
 		                var exceptionLen = strResponse.cpsException.length;
 		                var notTrackedLen = strResponse.cpsNotTracked.length;
 		                var str = "";
 		                var overAllStr = "";
 		                var clusterStr = "";
 		                var categoryStr = "";
 		                
 		                if (overAllLen > 0) {		                	
 		              
 		                   
 		                  var header = [];
 		                  header = strResponse.cpsOverAll[0];
	                      
 		                   var headerStr =  '<table id ="accountIDS" class="table table-striped table-bordered nowrap order-column row-border no-footer DTFC_Cloned tme_editTable dataTable" >'+
									'<thead class="thead-dark"><tr>';
 		                   var a = header.indexOf("TOTAL");
							  for (i = 0; i < header.length; i++) { 
								  headerStr = headerStr + '<th>' + header[i] + '</th>';
			                    }
							  headerStr =  headerStr + '</tr></thead>';
							  
 		                  $('#account-table').html(headerStr);
 		                   
 		                   $('#account-table table').append('<tbody>'); 		                	
 		                    for (var count = 1; count < overAllLen; count++) {
 		                        overAllStr = overAllStr + '<tr>';
 		                        var abc = [];
 		                        abc = strResponse.cpsOverAll[count];
 		                       for (i = 0; i < abc.length; i++) { 
 		                      //  overAllStr = overAllStr + '<td>' + abc[i] + '</td>';
 		                    	   
 		                    	  if(a >= i){
 		                    		 overAllStr = overAllStr + '<td>' + abc[i] + '</td>';
 		                    	   }else{
 		                    		  overAllStr = overAllStr + '<td>' + abc[i]*100 + '%</td>'; 
 		                    	   }
 		                    }
 		                        overAllStr = overAllStr + '</tr>';
 		                    }

 		                        
 		               $('#account-table table tbody').append(overAllStr + '</tbody></table>');
 		               getDataTable('accountIDS');
 		               }       
 		               
 		              if (exceptionLen > 0) {		                	
 	 		              
		                   
 		                  var header = [];
 		                  header = strResponse.cpsException[0];
	                      
 		                   var headerStr =  '<table id ="clusterIDS" class="table table-striped table-bordered nowrap order-column row-border no-footer DTFC_Cloned tme_editTable dataTable" >'+
									'<thead class="thead-dark"><tr>';
							
 		                   var a = header.indexOf("TOTAL");
 		                   
							  for (i = 0; i < header.length; i++) { 
								  headerStr = headerStr + '<th>' + header[i] + '</th>';
			                    }
							  headerStr =  headerStr + '</tr></thead>';
							  
 		                  $('#cluster-table').html(headerStr);
 		                   
 		                   $('#cluster-table table').append('<tbody>'); 		                	
 		                    for (var count = 1; count < exceptionLen; count++) {
 		                        clusterStr = clusterStr + '<tr>';
 		                        var abc = [];
 		                        abc = strResponse.cpsException[count];
 		                       for (i = 0; i < abc.length; i++) {
 		                    	   if(a >= i){
 		                    		  clusterStr = clusterStr + '<td>' + abc[i] + '</td>';
 		                    	   }else{
 		                    		  clusterStr = clusterStr + '<td>' + abc[i]*100 + '%</td>'; 
 		                    	   }
 		                        
 		                    }
 		                        clusterStr = clusterStr + '</tr>';
 		                    }

 		                        
 		               $('#cluster-table table tbody').append(clusterStr + '</tbody></table>');
 		               getDataTable('clusterIDS');
 		               }  
 		              
 		              
 		             if (notTrackedLen > 0) {		                	
 	 		              
		                   
		                  var header = [];
		                  header = strResponse.cpsNotTracked[0];
	                      
		                   var headerStr =  '<table id ="categoryIDS" class="table table-striped table-bordered nowrap order-column row-border no-footer DTFC_Cloned tme_editTable dataTable" >'+
									'<thead class="thead-dark"><tr>';
		                   var a = header.indexOf("TOTAL");
							  for (i = 0; i < header.length; i++) { 
								  headerStr = headerStr + '<th>' + header[i] + '</th>';
			                    }
							  headerStr =  headerStr + '</tr></thead>';
							  
		                  $('#category-table').html(headerStr);
		                   
		                   $('#category-table table').append('<tbody>'); 		                	
		                    for (var count = 1; count < notTrackedLen; count++) {
		                        categoryStr = categoryStr + '<tr>';
		                        var abc = [];
		                        abc = strResponse.cpsNotTracked[count];
		                       for (i = 0; i < abc.length; i++) { 
		                    	   
		                    	   if(a >= i){
		                    		   categoryStr = categoryStr + '<td>' + abc[i] + '</td>';
	 		                    	   }else{
	 		                    		  categoryStr = categoryStr + '<td>' + abc[i]*100 + '%</td>'; 
	 		                    	   }
		                    }
		                        categoryStr = categoryStr + '</tr>';
		                    }

		                        
		               $('#category-table table tbody').append(categoryStr + '</tbody></table>');
		               getDataTable('categoryIDS');
		               }   

 		            } catch (err) {
 		            	
 		            	console.log(err);
 		            }
 		        }

 		    });
 		}

function getDataTable(id) {
		$('#'+id).dataTable({
		  "bPaginate": false,
		    "bSort": false,
		    "bFilter": false,
		  "autoWidth": true,
		    "scrollY": "260px",
		    dom: 'Bfrtip',

          "bInfo": false,
         // "dom": 'l<"toolbar">frtip',
          "scrollCollapse": true,
          "oLanguage": {
              "sSearch": '<i class="icon-search"></i>',
              "oPaginate": {
                  "sNext": "&rarr;",
                  "sPrevious": "&larr;"
              },
              "sLengthMenu": "Records per page _MENU_ ",
              "sEmptyTable": "No Data Available."

          },
          drawCallback: function() {
        	    var hasRows = this.api().rows({ filter: 'applied' }).data().length > 0;
        	    $('.exportbtn')[0].style.visibility = hasRows ? 'visible' : 'hidden'
        	  }
  	  });
		/*if ( $(".dataTables_empty").length == 0 ) {
			$(".exportbtn").prop('disabled', true);
		}
		else {
			$(".exportbtn").prop('disabled', false);
		}*/
	}
	function implementationBar(id){
		  $('#'+id+' tbody tr').each(function() {
           var get2ndColumnVal = $(this).find('td').eq(1).find('input').val();

          $(this).find('td').eq(1).html('<div class="implementation-bg" ></div><div style="float:left;max-width:0%;line-height:25px;">' + get2ndColumnVal + '%</div>');


           var currTd = $(this).find('td').eq(1).find('.implementation-bg');


           $(currTd).animate({
               width: get2ndColumnVal + '%',
           }, {
               duration: 800,
               easing: "linear"
                  
           });
       });
	}
	function downloadKamExportData()
	{
		document.forms['downloadKamSummaryReport'].method = "POST";
		document.forms['downloadKamSummaryReport'].action = "downloadCpsSummaryReport.htm";
		document.forms['downloadKamSummaryReport'].submit();
		
	}

  function ajaxLoader(w,h){
		 
	  	var left = (window.innerWidth/2) - (w/2);
	  	var top = (window.innerHeight/2) - (h/2);
	  	$('.loader').css('display','block'); 
	  	//$('#loading-image').removeProp('right');
	  	$('.loading-image').css({ "left": left, "top": top});
	  }


$(document).ready(function() {
	  var activeMoc = '${activeMoc}';
	  $('#paid-moc').val(activeMoc).attr('selected', 'selected');
	//document.title='Summary Report';
    connectivityExecutionDetails('');    
    
    $('#paid-moc').on('change', function() {
	        var connectivityExecutionSelected = $(this).val();
	        connectivityExecutionDetails(connectivityExecutionSelected);
	    });
	 
	});
