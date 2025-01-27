var spinnerWidth = "100";
var spinnerHeight = "100";
function ajaxLoader(w, h) {

    var left = (window.innerWidth / 2) - (w / 2);
    var top = (window.innerHeight / 2) - (h / 2);
    $('.loader').css('display', 'block');

    $('.loading-image').css({
        "left": left,
        "top": top,
        
    });
}


$(document)
		.ready(
				function() {
					
					$('#msg-success').hide();
					$('#msg-error').hide();
					
					var selectAll = false;
					
					$("[data-hide]").on("click", function() {
		                $(this).closest("." + $(this).attr("data-hide")).hide();
		              });
					
				/*	var geographySelectedVal = $('#geography').comboTree({
						source : JSON.parse(geographyData),
						isMultiple : false
						/*select:function(item){
							geography = item.title;
							promoTable.draw();
						}
					});*/
					
					//below commented by bharati for sprint-9
					
					/*var mocSelectedVal = $('#moc').comboTree({
						source : JSON.parse(moc),
						isMultiple : false
						/*select:function(item){
							mocVal = item.title;
							promoTable.draw();
						}*/
					/*});*/

					
					

					/*$("#upload-file").on('click', function(e) {
						// e.preventDefault();
						//console.log(e);

					});

					$('#choose-file').click(function() {
						$("#upload-file").trigger('click');
					});
					$('#promo_basepack').on('keyup',function(){
						if($(this).val() == ""){
							basepack = "All";
							promoTable.draw();
						}
					});
					$('#upload-file').change(function(e) {
						//console.log(e);
						var files = event.target.files;
						if (files.length > 0) {
							var fileName = files[0].name;
							$('.file-name').html(fileName);
						} else {
							$('.file-name').html("No file chosen");
						}

					});*/
					
					
					
					$('#budget_holder').change(function(){
						budget_holder = $(this).val();
						promoTable.draw();
						});
						$('#category').change(function(){
						category = $(this).val();
						promoTable.draw();
						});

					$('#product').change(function(){
						product = $(this).val();
						promoTable.draw();
						});

					$('#Customer').change(function(){
						Customer = $(this).val();
						promoTable.draw();
						});

					$('#fund_type').change(function(){
						fund_type = $('#modality option:selected').text();
						promoTable.draw();
						});

					$('#original_amount').change(function(){
						original_amount = $(this).val();
						promoTable.draw();
						});
						
						$('#revised_amount').change(function(){
						revised_amount = $(this).val();
						promoTable.draw();
						});
						$('#transfer_in').change(function(){
						transfer_in = $(this).val();
						promoTable.draw();
						});
						$('#transfer_out').change(function(){
						transfer_out = $(this).val();
						promoTable.draw();
						});
						$('#transfer_pipeline').change(function(){
						transfer_pipeline = $(this).val();
						promoTable.draw();
						});
						$('#total_amount').change(function(){
						total_amount = $(this).val();
						promoTable.draw();
						});
						$('#pipeline_amount').change(function(){
						pipeline_amount = $(this).val();
						promoTable.draw();
						});
						$('#pipeline_amount').change(function(){
						pipeline_amount = $(this).val();
						promoTable.draw();
						});
						$('#commitment_amount').change(function(){
						commitment_amount = $(this).val();
						promoTable.draw();
						});
						$('#remaining_amount').change(function(){
						remaining_amount = $(this).val();
						promoTable.draw();
						});
						$('#actuals').change(function(){
						actuals = $(this).val();
						promoTable.draw();
						});
						$('#adjustment_against_actuals').change(function(){
						adjustment_against_actuals = $(this).val();
						promoTable.draw();
						});
						$('#usage').change(function(){
						usage = $(this).val();
						promoTable.draw();
						});
						$('#post_close_actual_amount').change(function(){
						post_close_actual_amount = $(this).val();
						promoTable.draw();
						});
						$('#past_year_closed_promotions_amount').change(function(){
						past_year_closed_promotions_amount = $(this).val();
						promoTable.draw();
						});
						$('#time_phase').change(function(){
						time_phase = $(this).val();
						promoTable.draw();
						});
					$('#report_downlaoded_date').change(function(){
						report_downlaoded_date = $(this).val();
						promoTable.draw();
						});
						$('#uploaded_timestamp').change(function(){
						uploaded_timestamp = $(this).val();
						promoTable.draw();
						});
					/*PromoListing table pagination */
				       promoTable = $('.promo-list-table').DataTable({

				              /* added for second tab start */
				    	   "bProcessing": true,
				             "bServerSide": true,
				             "scrollY":       "200px",
						        "scrollX":        true,
						        "scrollCollapse": true,
						        "paging":         true,
						        "ordering": false,
						        "searching": true,
						        "select": true,
						    	"lengthMenu" : [
									[ 5, 10, 25, 50, 100 ],
									[ 5, 10, 25, 50, 100 ] ],
						       "oLanguage": {
					                 "sSearch": 'Search :',
					                  "oPaginate": {
					                      "sNext": "&rarr;",
					                      "sPrevious": "&larr;"
					                  },
					                  "sLengthMenu": "Records per page _MENU_ ",
					                  "sEmptyTable": "No Pending Visibilities."
					
					              },
				             "iDisplayLength": 10,
						     "iDisplayStart": 0,
				              "sAjaxSource": "procoLiveBudgetPagination.htm",
				             "fnServerParams": function(aoData) {
					
				                aoData.push(
				    	               {"name": "budget_holder", "value": budget_holder}, 
				    	                {"name": "category", "value": category},
				    	                {"name": "product","value": product}, 
				    	                {"name": "Customer", "value": Customer}, 
				    	                {"name": "fund_type", "value": fund_type}, 
				    	                {"name": "original_amount", "value": original_amount}, 
				    	                {"name": "revised_amount", "value": revised_amount}, 
				    	                {"name": "update_amount", "value": update_amount}, 
				    	                {"name": "transfer_in","value": transfer_in},
				    	                {"name": "transfer_out","value": transfer_out} ,
				    	                {"name": "transfer_pipeline","value": transfer_pipeline},
				    	                {"name": "total_amount","value": total_amount},
				    	                {"name": "pipeline_amount","value": pipeline_amount},
				    	                {"name": "commitment_amount","value": commitment_amount},
				    	                {"name": "remaining_amount","value": remaining_amount},
				    	                {"name": "actuals","value": actuals},
				    	                {"name": "adjustment_against_actuals","value": adjustment_against_actuals},
				    	                {"name": "usage","value": usage},
     			    	                {"name": "post_close_actual_amount","value": post_close_actual_amount},
				    	                {"name": "past_year_closed_promotions_amount","value": past_year_closed_promotions_amount},
				    	                {"name": "time_phase","value": time_phase},
				    	                {"name": "report_downlaoded_date","value": report_downlaoded_date},
				    	                {"name": "uploaded_timestamp","value": uploaded_timestamp},


				    	                );
				              }, 
				              "fnDrawCallback": function(oSettings){
				            	  $('table.promo-list-table input[type="checkbox"]').change(function() {
									    $('table.promo-list-table input[type="checkbox"]').not(this).prop('checked', false);  
									});
				              },
				                "fnDrawCallback": function(oSettings){
				            	 /* $('table.promo-list-table input[type="checkbox"]').change(function() {
									    $('table.promo-list-table input[type="checkbox"]').not(this).prop('checked', false);  
									});*/
				              },
				              "aoColumns": [/*{
				                  "mData": "promo_id",
				                  "mRender": function(data, type, full) {
					 console.log("data:"+data);
				                    return '<input type="checkbox" class="visiData" name="promoId" id="promo_id" value="'+data+'">';
				                  } 
				                  },*/
				            	  {
				                    "mData": "budget_holder"
				                  },{
				                    "mData": "category"
				                  },
								  {
				                    "mData": "product"
				                  },{
				                    "mData": "Customer"
				                  },{
				                    "mData": "fund_type"
				                  }, {
				                    "mData": "original_amount"
				                  }, {
				                    "mData": "adjusted_amount"
				                  },{
				                    "mData": "revised_amount"
				                  }, {
				                    "mData": "update_amount"
				                  }, {
				                    "mData": "transfer_in"
				                  }, {
				                    "mData": "transfer_out"
				                  }, {
				                    "mData": "transfer_pipeline",
				                  }, {
				                    "mData": "total_amount",
				                  }, {
				                    "mData": "pipeline_amount",
				                  }, {
				                    "mData": "commitment_amount",
				                  },{
					                 "mData": "remaining_amount",
					              },{
					                 "mData": "actuals",
					              },{
						                 "mData": "adjustment_against_actuals",
						              }
					              ,{
						                 "mData": "usage",
						              },
									  {
						                 "mData": "post_close_actual_amount",
						              } ,{
						                 "mData": "past_year_closed_promotions_amount",
						              } ,{
						                 "mData": "time_phase",
						              } ,{
						                 "mData": "report_downloaded_by",
						              },{
						                 "mData": "report_downlaoded_date",
						              },{
						                 "mData": "uploaded_timestamp",
						              }
				                ]
				                /* added for second tab end */

				            });
				         
				         
				       $('.filter-ref').on('keyup', function() {
				    	   promoTable.columns(0).search(this.value).draw();
						});
				       
				      /* $('#DataTables_Table_0_length').css({
	                       
	                         'padding': '20px 0'
	                     });
	                     $('#DataTables_Table_0_length').css({
	                         'color': '#29290a'
	                     });
	                    
	                     
	                     $($('#DataTables_Table_0_wrapper .row')[0]).after(
	                             $(".summary-text"));

	                     $(
	                     $($('#DataTables_Table_0_wrapper .row')[0]).find(
                       	'.col-sm-6')[1]).addClass(
                       		"promo-filter-div");
	                     $(
	    	                     $($('.promo-filter-div')).find(
	                             	'#DataTables_Table_0_filter')).addClass(
	                             		"promo-filter");

	                     
	                     $(
	    	                     $($('#DataTables_Table_0_wrapper .row')[0]).find(
	                             	'select')).addClass(
	                             		"promolistselect");
	                     
	                    
	                     $(
	    	                     $($('.promo-filter')).find(
	                             	'input')).addClass(
	                             		"searchicon-wrapper-promolist");
	                    
	                     $('.promo-filter').find(
                       '#DataTables_Table_0_filter').css({
	                         'float': 'right'
	                     });
				       
	                     $('.promo-filter').css({
	                    	 'float': 'right',
	                    	  'padding-left': '10px',
	                    	  'padding-top': '20px',
	                    	    'height': '75px', 
	                    	    'width': 'none',
	                    	    'border-left': '2px #e8e8e8 solid'
	                     });*/
				       
				       $('#DataTables_Table_0_length').css({
	                       
	                         'padding': '20px 0'
	                     });
	                     $('#DataTables_Table_0_length').css({
	                         'color': '#29290a'
	                     });
	                    
	                     
	                    $($('#table-id-promo-list-table_wrapper .row')[0]).after(
	                            $(".summary-text"));

	                     $(
	                     $($('#table-id-promo-list-table_wrapper .row')[0]).find(
                       	'.col-sm-6')[1]).addClass(
                       		"promo-filter-div");
	                     $(
	    	                     $($('.promo-filter-div')).find(
	                             	'#table-id-promo-list-table_filter')).addClass(
	                             		"promo-filter");

	                     
	                     $(
	    	                     $($('#table-id-promo-list-table_wrapper .row')[0]).find(
	                             	'select')).addClass(
	                             		"promolistselect");
	                     
	                    
	                     $(
	    	                     $($('.promo-filter')).find(
	                             	'input')).addClass(
	                             		"searchicon-wrapper-promolist");
	                    
	                     $('.promo-filter').find(
                       '#table-id-promo-list-table_filter').css({
	                         'float': 'right'
	                     });
				       
	                     $('.promo-filter').css({
	                    	 'float': 'right',
	                    	  'padding-left': '10px',
	                    	  'padding-top': '20px',
	                    	    'height': '75px', 
	                    	    'width': 'none',
	                    	    'border-left': '2px #e8e8e8 solid'
	                     });
	                    
	                    
				     /*  var categoryTags = basepacks;
						var categoryNewList = [];
						
						categoryTags = categoryTags.replace('[', '');
						categoryTags = categoryTags.replace(']', '');
						
						$.each(categoryTags.split(","), function() {
							categoryNewList.push($.trim(this));
						});
						newBasepacks = categoryNewList;
						$('#promo_basepack').autocomplete({
									minLength : 0,
									source : function(request, response) {
										response($.ui.autocomplete.filter(newBasepacks,
												extractLast(request.term)));
									},
									focus : function() {
										return false;
									},
								
						select : function(event, ui) {
							basepack = ui.item.value;
							promoTable.draw();
						}
					
						
					});*/
						
						
						/*$('.promo-list-table tr').each(function() {
						    var customerId = $(this).find("td").eq(16).val();   
						    console.log(customerId);
						});*/
				});



function downloadPromotionFile(){
	
	window.location.assign("procoLiveBudgetDownload.htm");
}




