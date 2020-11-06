 		var tidValue = null;
 		var maxValue = 0;
 		var maxCount = 0;
 		var nextVal = 1;
 		var displayGlobalVar = 1;
 		var prevflag = false;
 		var nextflag = false;
 		//var nextAndPreviousEvent = false;
 		$(document).ready(function() {

 		    document.onkeydown = checkKey;
 		    document.onkeypress = checkKey;
 		    connectivityDetails('TODAY');
		    connectivityExecutionDetails('TODAY');
 		    $('a[data-slide="prev"]').click(function() {
 		       //nextAndPreviousEvent = true;
 		       // nextVal = nextVal - 1;
 		    	displayGlobalVar = displayGlobalVar -1;
 		        if (displayGlobalVar <= 1) {
 		            $('.carousel-control.left').hide();
 		        }
 		        if (maxCount != displayGlobalVar) {
 		            $('.carousel-control.right').show();

 		        }
 		        getImageGalarry(tidValue, displayGlobalVar);
 		        $('#ModalCarousel').carousel('prev');
 		    });

 		    $('a[data-slide="next"]').click(function() {
 		    	//nextAndPreviousEvent = true;
 		        //nextVal = nextVal + 1;
 		        
 		       displayGlobalVar = displayGlobalVar + 1;
 		        if (displayGlobalVar > 1) {
 		            $('.carousel-control.left').show();
 		        }
 		        if (maxCount == displayGlobalVar) {
 		            $('.carousel-control.right').hide();

 		        }
 		        getImageGalarry(tidValue, displayGlobalVar);
 		        $('#ModalCarousel').carousel('next');
 		    });


 		    
 		    $('#connectivity').on('change', function() {
 		        var connectivitySelected = $(this).val();
 		        RGraph.ObjectRegistry.Clear();
 		        connectivityDetails(connectivitySelected);
 		    });
 		    $('#connectivityExecution').on('change', function() {
 		        var connectivityExecutionSelected = $(this).val();
 		        connectivityExecutionDetails(connectivityExecutionSelected);
 		    });

 		});

 		function checkKey(e) {

 		    e = e || window.event;

 		    if (e.keyCode == '38') {
 		        // up arrow
 		    } else if (e.keyCode == '40') {
 		        // down arrow
 		    } else if (e.keyCode == '37') {
 		        nextflag = false;
 		        if ($('#ModalCarousel').is(':visible')) {
 		        	displayGlobalVar = displayGlobalVar - 1;
 		            if (displayGlobalVar == 0) {
 		            	displayGlobalVar = 1;
 		            }
 		            if (displayGlobalVar == 1) {
 		                $('.carousel-control.left').hide();
 		                if (prevflag == false) {
 		                    getImageGalarry(tidValue, displayGlobalVar);
 		                   displayGlobalVar = 1;
 		                    prevflag = true;
 		                }


 		            }
 		            if (maxCount != displayGlobalVar) {
 		                $('.carousel-control.right').show();

 		            }

 		            if (prevflag == false) {
 		                getImageGalarry(tidValue, displayGlobalVar);
 		                prevflag = false;
 		            }

 		            $('#ModalCarousel').carousel('prev');

 		        }

 		        // left arrow
 		    } else if (e.keyCode == '39') {
 		        prevflag = false;
 		        if ($('#ModalCarousel').is(':visible')) {
 		        	displayGlobalVar = displayGlobalVar + 1;
 		            if (displayGlobalVar > maxCount) {
 		            	displayGlobalVar = maxCount;
 		            }
 		            if (displayGlobalVar > 1) {
 		                $('.carousel-control.left').show();

 		            }
 		            if (maxCount == displayGlobalVar) {
 		                $('.carousel-control.right').hide();
 		                if (nextflag == false) {
 		                    getImageGalarry(tidValue, displayGlobalVar);
 		                   displayGlobalVar = maxCount;
 		                    nextflag = true;
 		                }

 		            }
 		            if (nextflag == false) {
 		                getImageGalarry(tidValue, displayGlobalVar);
 		                nextflag = false;
 		            }
 		            $('#ModalCarousel').carousel('next');
 		        }
 		        // right arrow
 		    }

 		}

 		function connectivityExecutionDetails(date) {
 		   // var emptyData = 'No Data Available';
 		    $.ajax({
 		        type: 'POST',
 		        url: 'dashboardExecutionAJAX.htm',
 		        data: {
 		            mocDate: date
 		        },
 		       statusCode: {
 		          404: function() {
 		        	 window.location.pathname = "/VisibilityAssetTracker/trackPage.htm";
 		          }
 		      },
 		        async: false,

 		        success: function(response) {
 		            try {
 		                var strResponse = jQuery.parseJSON(response);
 		                var accountLen = strResponse.accountDetails.length;
 		                var clusterLen = strResponse.clusterDetails.length;
 		                var categoryLen = strResponse.categoryDetails.length;
 		                var str = "";
 		                var accStr = "";
 		                var categoryStr = "";
 		                /* account table data */
 		                $('#account-table').html('<table id ="accountIDS" class="table table-striped table-bordered tme_editTable dataTable" >'+
											'<thead><tr><th style="width:15%">Account</th>'+
											'<th style="width:55%">Implementation%</th><th style="width:15%">N-1</th><th style="width:15%">N-2</th>'+
											'</tr></thead>');
 		              
 		                $('#account-table table').append('<tbody>');
 		                
 		                if (accountLen > 0) {
 		                    for (var count = 0; count < accountLen; count++) {

 		                        accStr = accStr + '<tr>';
 		                        accStr = accStr + '<td>' + strResponse.accountDetails[count].account + '</td><td><input type="hidden" value="' + strResponse.accountDetails[count].currentMOC + '"></td>';

 		                        if (strResponse.accountDetails[count].previousMOC > 0) {
 		                            accStr = accStr + '<td><div class="l3m-value-green">' + strResponse.accountDetails[count].previousMOC + '%</div></td>';
 		                        } else if(strResponse.accountDetails[count].previousMOC == "NA"){
 		                        	accStr = accStr + '<td><div class="">' + strResponse.accountDetails[count].previousMOC + '</div></td>';
 		                        } else {
 		                            accStr = accStr + '<td><div class="l3m-value-red">' + strResponse.accountDetails[count].previousMOC + '%</div></td>';
 		                        }
 		                        if (strResponse.accountDetails[count].lastThirdMOC > 0) {
 		                            accStr = accStr + '<td><div class="l3m-value-green">' + strResponse.accountDetails[count].lastThirdMOC + '%</div></td>';
 		                        }else if(strResponse.accountDetails[count].lastThirdMOC == "NA"){
 		                        	accStr = accStr + '<td><div class="">' + strResponse.accountDetails[count].lastThirdMOC + '</div></td>';
 		                        }else {
 		                            accStr = accStr + '<td><div class="l3m-value-red">' + strResponse.accountDetails[count].lastThirdMOC + '%</div></td>';
 		                        }

 		                        accStr = accStr + '</tr>';
 		                    }

 		                } /*else {

 		                    accStr = accStr + '<tr>';
 		                    accStr = accStr + '<td colspan="4">' + emptyData + '</td>';
 		                    accStr = accStr + '</tr>';
 		                }    */            
 		               $('#account-table table tbody').append(accStr + '</tbody></table>');
 		               getDataTable('accountIDS');
 		              implementationBar('accountIDS');
 		               /* cluster table data */	               
 		               $('#cluster-table').html('<table id ="clusterIDS" class="table table-striped table-bordered tme_editTable dataTable">'+
								'<thead><tr><th style="width:15%">Cluster</th><th style="width:55%">Implementation%</th>'+
									'<th style="width:15%">N-1</th><th style="width:15%">N-2</th></tr></thead>');
							
 		              $('#cluster-table table').append('<tbody>');							              
 		                if (clusterLen > 0) {
 		                    for (var i = 0; i < clusterLen; i++) {
 		                        str = str + '<tr>';
 		                        str = str + '<td>' + strResponse.clusterDetails[i].cluster + '</td>';
 		                        str = str + '<td><input type="hidden" value="' + strResponse.clusterDetails[i].currentMOC + '"></td>';
 		                        if (strResponse.clusterDetails[i].previousMOC > 0) {
 		                            str = str + '<td><div class="l3m-value-green">' + strResponse.clusterDetails[i].previousMOC + '%</div></td>';
 		                        }else if(strResponse.clusterDetails[i].previousMOC == "NA"){
 		                        	str = str + '<td><div class="">' + strResponse.clusterDetails[i].previousMOC + '</div></td>';
 		                        }else {
 		                            str = str + '<td><div class="l3m-value-red">' + strResponse.clusterDetails[i].previousMOC + '%</div></td>';
 		                        }
 		                        if (strResponse.clusterDetails[i].lastThirdMOC > 0) {
 		                            str = str + '<td><div class="l3m-value-green">' + strResponse.clusterDetails[i].lastThirdMOC + '%</div></td>';
 		                        }else if(strResponse.clusterDetails[i].lastThirdMOC == "NA"){
 		                        	str = str + '<td><div class="">' + strResponse.clusterDetails[i].lastThirdMOC + '</div></td>';
 		                        }else {
 		                            str = str + '<td><div class="l3m-value-red">' + strResponse.clusterDetails[i].lastThirdMOC + '%</div></td>';
 		                        }

 		                        str = str + '</tr>';

 		                    }
 		                }/* else {

 		                    str = str + '<tr>';
 		                    str = str + '<td colspan="4">' + emptyData + '</td>';
 		                    str = str + '</tr>';
 		                }*/
 		                
 		               $('#cluster-table table tbody').append(str + '</tbody></table>');
 		               getDataTable('clusterIDS');
 		              implementationBar('clusterIDS');
 		               /* Category table data */
 		               $('#category-table').html('<table id ="categoryIDS" class="table table-striped table-bordered tme_editTable dataTable" >'+
								'<thead><tr><th style="width:15%">Category</th><th style="width:55%">Implementation%</th>'+
									'<th style="width:15%">N-1</th><th style="width:15%">N-2</th></tr></thead>');
 		              $('#category-table table').append('<tbody>');
 		                if (categoryLen > 0) {
 		                    for (var j = 0; j < categoryLen; j++) {
 		                        categoryStr = categoryStr + '<tr>';
 		                        categoryStr = categoryStr + '<td>' + strResponse.categoryDetails[j].category + '</td><td><input type="hidden" value="' + strResponse.categoryDetails[j].currentMOC + '"></td>';

 		                        if (strResponse.categoryDetails[j].previousMOC > 0) {
 		                            categoryStr = categoryStr + '<td><div class="l3m-value-green">' + strResponse.categoryDetails[j].previousMOC + '%</div></td>';
 		                        }else if(strResponse.categoryDetails[j].previousMOC == "NA"){
 		                        	categoryStr = categoryStr + '<td><div class="">' + strResponse.categoryDetails[j].previousMOC + '</div></td>';
 		                        } else {
 		                            categoryStr = categoryStr + '<td><div class="l3m-value-red">' + strResponse.categoryDetails[j].previousMOC + '%</div></td>';
 		                        }
 		                        if (strResponse.categoryDetails[j].lastThirdMOC > 0) {
 		                            categoryStr = categoryStr + '<td><div class="l3m-value-green">' + strResponse.categoryDetails[j].lastThirdMOC + '%</div></td>';
 		                        }else if(strResponse.categoryDetails[j].lastThirdMOC == "NA"){
 		                        	categoryStr = categoryStr + '<td><div class="">' + strResponse.categoryDetails[j].lastThirdMOC + '</div></td>';
 		                        } else {
 		                            categoryStr = categoryStr + '<td><div class="l3m-value-red">' + strResponse.categoryDetails[j].lastThirdMOC + '%</div></td>';
 		                        }

 		                        categoryStr = categoryStr + '</tr>';

 		                    }
 		                } /*else {


 		                    categoryStr = categoryStr + '<tr>';
 		                    categoryStr = categoryStr + '<td colspan="4">' + emptyData + '</td>';
 		                    categoryStr = categoryStr + '</tr>';
 		                }	*/	                
 		                $('#category-table table tbody').append(categoryStr + '</tbody></table>');		              	             
 		                getDataTable('categoryIDS');
 		               implementationBar('categoryIDS');
 		              

 		            } catch (err) {}
 		        }

 		    });
 		}

 		function connectivityDetails(date) {


 		    $.ajax({
 		        type: 'POST',
 		        url: 'dashboardAJAX.htm',
 		        data: {
 		            mocDate: date
 		        },
 		       statusCode: {
  		          404: function() {
  		        	 window.location.pathname = "/VisibilityAssetTracker/trackPage.htm";
  		          }
  		      },
 		        success: function(response) {
 		            try {
 		            	var strResponse = jQuery.parseJSON(response);
 		            	if($.isEmptyObject(strResponse)){
 		            	
 	 	 		        		$('#catman-last-month').html('<div><p style="font-size:50px;color:rgba(0, 0, 0, 0.64)">NA</p></div>')
 	 	 		        		$('#catman-l2-month').html('<div><p style="font-size:50px;color:rgba(0, 0, 0, 0.64)">NA</p></div>')
 	 	 		        		$('#brand-last-month').html('<div><p style="font-size:50px;color:rgba(0, 0, 0, 0.64)">NA</p></div>')
 	 	 		        		$('#brand-l2-month').html('<div><p style="font-size:50px;color:rgba(0, 0, 0, 0.64)">NA</p></div>')
 	 	 		        		
 	 	 		        		var canvas = document.getElementById("catman-graph");
 	 	 		        	  var context = canvas.getContext("2d");
 	 	 		        	  context.fillStyle = "rgba(0, 0, 0, 0.64)";
 	 	 		        	  context.font = "normal 50px arial";
 	 	 		        	  context.fillText("NA", 100, 100);
 	 	 		        	var canvas = document.getElementById("brand-graph");
	 	 		        	  var context = canvas.getContext("2d");
	 	 		        	  context.fillStyle = "rgba(0, 0, 0, 0.64)";
	 	 		        	  context.font = "normal 50px arial";
	 	 		        	  context.fillText("NA", 100, 100);
 	 	 		        		
 	 	 		        	
 		            	}
 		            	
 		            	
 		            	else{
 	 		        		
 		                
 		                connectivityGraph(['catman-graph', 'brand-graph'], [strResponse.catmanCurrentPer, strResponse.brandCurrentMoc]);

 		                /* CATMAN last month up&down */
 		                if (strResponse.catmanPreviousMoc > 0) {
 		                    $('#catman-last-month').html('<div class="up"><p>' + strResponse.catmanPreviousMoc + '%</p></div>');
 		                } else {
 		                    $('#catman-last-month').html('<div class="down"><p>' + strResponse.catmanPreviousMoc + '%</p></div>');
 		                }
 		                /* CATMAN L2M up&down */
 		                if (strResponse.catmanLastThirdMoc > 0) {
 		                    $('#catman-l2-month').html('<div class="up"><p>' + strResponse.catmanLastThirdMoc + '%</p></div>');
 		                } else {
 		                    $('#catman-l2-month').html('<div class="down"><p>' + strResponse.catmanLastThirdMoc + '%</p></div>');
 		                }
 		                /* BRAND last month up&down */
 		                if (strResponse.brandPreviousMoc > 0) {
 		                    $('#brand-last-month').html('<div class="up"><p>' + strResponse.brandPreviousMoc + '%</p></div>');

 		                } else {
 		                    $('#brand-last-month').html('<div class="down"><p>' + strResponse.brandPreviousMoc + '%</p></div>');
 		                }
 		                /* BRAND L2M up&down */
 		                if (strResponse.brandLastThirdMoc > 0) {
 		                    $('#brand-l2-month').html('<div class="up"><p>' + strResponse.brandLastThirdMoc + '%</p></div>');
 		                } else {
 		                    $('#brand-l2-month').html('<div class="down"><p>' + strResponse.brandLastThirdMoc + '%</p></div>');
 		                }
 		            	}

 		            } catch (err) {}

 		        }
 		    });

 		}

 		function getImageGalarry(data, nextVal) {

 		    $('#ModalCarousel div.carousel-inner div').remove();
 		    tidValue = data;
 		   $("#zipDownloadId").attr("href", "downloadImages.htm?data="+tidValue);
 		    $.ajax({
 		        type: 'POST',
 		        async: false,
 		        
 		        url: 'imageAJAXCall.htm',
 		        data: {
 		            tid: data,
 		            nextRow: nextVal
 		        },
 		       cache: false,
 		        
 		        /*beforeSend: function() {
 		            ajaxLoader(spinnerWidth, spinnerHeight);
 		        },*/
 		        success: function(response) {
 		        	
 		        	
 		            var data = jQuery.parseJSON(response);
 		            var imgLen = data.imageDataModels.length;
 		          

 		            if(imgLen > 0){
 		            	 maxValue = data.imageDataModels[0].rowNum;
 			            maxCount = data.imageDataModels[0].count;
 		            	$("img[alt='gallery_download']").show();
 		            	$('.carousel-control.right').show();
 		           
 		            try {
 		                for (var i = 0; i < imgLen; i++) { 
 		                	$('#ModalCarousel div.carousel-inner').append('<div class="item"><img src="data:image/png;base64,' + data.imageDataModels[i].imageString + '" alt="' + data.imageDataModels[i].storeName + '">' +
 		                        '<div class="carousel-caption"><p>' + data.imageDataModels[i].storeCode + '</p><p>' + data.imageDataModels[i].storeName + '</p><p>' + data.imageDataModels[i].townName + '</p></div></div>');

 		                }
 		                nextVal = maxValue;
 		               displayGlobalVar = nextVal;
 		            } catch (err) {

 		            }
 		            
 		            }else{
 		            	
 		            	$('#ModalCarousel div.carousel-inner').append('<div class="item"><img src="assets/images/no-thumb.jpg" alt="No Imge" style="width:100%;"></div>');
 		            	$('.carousel-control.right').hide();
 		            	$('.carousel-control.left').hide();
 		            	$("img[alt='gallery_download']").hide();
 		            }
 		            
 		           $('#ModalCarousel div.carousel-inner div:first-child').addClass('active');
 		 		  $('#ModalCarousel').modal('show'); 
 		 		  if(nextVal == 1){
 		 			$('.carousel-control.left').hide();
 		 		  }
 		 		  if (maxCount == displayGlobalVar) {
		                $('.carousel-control.right').hide();
		                
	            	}
 		           
 		        	
 		        },
 		        error: function(error) {


 		        }
 		        

 		    });
 		   
 		}

 		/* Connectivity code */

 		function getGraphColors(graphColorVal) {
 		    var changedGraphColor = null;
 		    if (graphColorVal > 0) {
 		        changedGraphColor = '#2e6da4';
 		    } 
 		    return changedGraphColor;
 		}

 		function connectivityGraph(id, data) {
 		    for (var i = 0; i < data.length; i++) {
 		        var pie = new RGraph.Pie({
 		            id: id[i],
 		            data: [v = data[i], 100 - v],
 		            options: {
 		                variant: 'donut',
 		                variantDonutWidth: 30,
 		                colors: [getGraphColors(data[i]), 'white'],
 		                strokestyle: 'transparent',
 		                shadow: true
 		            }
 		        }).roundRobin({
 		            frames: 60
 		        }).on('draw', function(obj) {
 		            var width = 5;

 		            for (var i = 0; i < 360; i += 10) {
 		                RGraph.path2(

 		                    obj.centerx,
 		                    obj.centery,
 		                    obj.radius + 2,
 		                    RGraph.degrees2Radians(i - (width / 2)),
 		                    RGraph.degrees2Radians(i + (width / 2)),
 		                    obj.centerx,
 		                    obj.centery
 		                );
 		            }

 		            if (text) {
 		                text.text = parseInt(v * obj.get('effect.roundrobin.multiplier')) + '%';
 		            }
 		        });


 		        var text = new RGraph.Drawing.Text({
 		            id: id[i],
 		            x: pie.centerx,
 		            y: pie.centery,
 		            text: pie.data[i] + '%',
 		            options: {
 		                font: 'Arial',
 		                size: 30,
 		                halign: 'center',
 		                'valign': 'center',
 		                colors: [getGraphColors(data[i])]
 		            }
 		        }).draw();

 		    }
 		}
 		function getDataTable(id) {
 			$('#'+id).dataTable({
      		  "bPaginate": false,
      		    "bSort": false,
      		    "bFilter": false,
      		  "autoWidth": true,
      		    "scrollY": "260px",
      		  
                  "bInfo": false,
                  "dom": 'l<"toolbar">frtip',
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
          	  });
 			
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