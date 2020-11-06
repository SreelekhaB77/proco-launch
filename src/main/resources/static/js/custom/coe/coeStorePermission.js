/* global variables */
var userName = null;
var mocValue = null;
var oTable;
jQuery.fn.dataTableExt.oApi.fnPagingInfo = function(oSettings) {
	return {
		"iStart" : oSettings._iDisplayStart,
		"iEnd" : oSettings.fnDisplayEnd(),
		"iLength" : oSettings._iDisplayLength,
		"iTotal" : oSettings.fnRecordsTotal(),
		"iFilteredTotal" : oSettings.fnRecordsDisplay(),
		"iPage" : oSettings._iDisplayLength === -1 ? 0 : Math
				.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength),
		"iTotalPages" : oSettings._iDisplayLength === -1 ? 0 : Math
				.ceil(oSettings.fnRecordsDisplay() / oSettings._iDisplayLength)
	};
};

$(document)
		.ready(
				function() {
					
					$("[data-hide]").on('click', function() {
						$(this).closest("." + $(this).attr("data-hide")).hide();
					});

					var sample1 = [];
					var index;
					var statusText;
					//	$('.alert-success').hide();

					$('.modelClick').on("contextmenu", function(e) {
						return false;
					});

					var date = new Date();
					var table = $('.coe-storeTable')
							.DataTable(
									{
										"lengthChange" : false,
										"ordering" : true,
										"bServerSide" : true,
										"iDisplayLength" : 5,
										"iDisplayStart" : 0,

										"fnDrawCallback" : function(nRow,
												aData, iDisplayIndex,
												iDisplayIndexFull) {

											var currentDateVal = $(
													".coe-dashboardTable tbody input[type='text']").val();

											if (currentDateVal == "undefined"|| currentDateVal == null) {
												$(".expdate").val("");
											}
										},

										"bFilter" : true,

										"scrollX" : "100%",
										"scrollCollapse" : true,
										"bLengthChange" : true,
										"lengthMenu" : [
												[ 5, 10, 25, 50, 100],
												[ 5, 10, 25, 50, 100 ] ],

										"sAjaxSource" : "coeStorePermissionPagination.htm",
										"fnServerParams" : function(aoData) {
											aoData.push({
												"name" : "userName",
												"value" : userName
											});
										},

										"aoColumns" : [

												{
													"mData" : "file_name",
													"mRender" : function(data,
															type, full) {
														return '<input type="checkbox" class="fileData" name="file-name" id="file-name" value='
																+ data + '>';
													}
												},
												{
													"mData" : "file_name"
												},
												{
													"mData" : "user_id"
												},
												{
													"mData" : "account_name"
												},
												{
													"mData" : "upload_date"
												},
												{
													"mData" : "current_date_of_expiry"
												},
												{
													"mData" : "status"
												},

												{
													"mData" : "",
													"mRender" : function(data,
															type, full) {
														return '<input type="text"  disabled class="expdate" name="expire-date" value='
																+ data + '>';

													}
												},

										]
									});

					$("#example-select-all").click(	function() {
										if ($(this).prop('checked')) {
											var cols = table.column(0).nodes(), state = this.checked;

											$('input[name="expire-date"]').prop('disabled', false);

											$('input[name="expire-date"]').datepicker({
																inline : true,
																onSelect : function() {
																	$('input[name="expire-date"]').removeClass('expdate hasDatepicker');
																}
															});

											$('#update-date').attr("disabled",false);
											$("#download").attr("disabled",false);
											$("#downloadExcel").attr("disabled", false);
											for (var i = 0; i < cols.length; i += 1) {
												cols[i]
														.querySelector("input[type='checkbox']").checked = state;
											}
										} else {
											var cols = table.column(0).nodes();
											$('#update-date').attr("disabled",true);
											$("#download").attr("disabled",true);
											$("#downloadExcel").attr("disabled", true);

											$('input[name="expire-date"]').prop('disabled', true);

											for (var i = 0; i < cols.length; i += 1) {
												cols[i].querySelector("input[type='checkbox']").checked = false;
											}
										}

									});

					$("body").on("change",".fileData",function() {

										var checkboxs = document.getElementsByClassName("fileData");
										var okay = false;
										for (var i = 0, l = checkboxs.length; i < l; i++) {
											if (checkboxs[i].checked) {
												$('#update-date').attr("disabled", false);
												$("#download").attr("disabled",false);
												$("#downloadExcel").attr("disabled", false);
												break;
											}
										}

										var rows, checked;
										rows = $('.coe-storeTable').find('tbody tr');
										checked = $(this).prop('checked');
										if (checked) {
											$('#update-date').attr("disabled",false);
											$("#download").attr("disabled",false);
											$("#downloadExcel").attr("disabled", false);
										} else {
											$('#update-date').attr('disabled',true);
											$("#download").attr("disabled",true);
											$("#downloadExcel").attr("disabled", true);
										}

										var cols = table.column(6).nodes();
										var checkbox = $(this);
										var row = checkbox.closest('tr');
										var inputText = $('input[name="expire-date"]',row);
										inputText.removeClass('hasDatepicker');
										if (checkbox.is(':checked')) {

											inputText.datepicker({
														inline : true,
														onSelect : function() {
															inputText.removeClass('expdate hasDatepicker');
														}
													});
											inputText.prop('disabled', false);

										} else {
											inputText.attr('disabled','disabled');
											inputText.datepicker("disable");

										}
									});

					var selected = [];

					$('.coe-storeTable tbody').on('click', 'tr', function() {
						var id = this.id;
						var index = $.inArray(id, selected);
						if (index === -1) {
							selected.push(id);
						} else {
							selected.splice(index, 1);
						}
						$(this).toggleClass('selected');
					});

					// post updated data into the db
					$('#update-date').click(function() {
										
										$("#errorblock1").css("display", "none");
										$("#successblock1").css("display", "none");
										var updateList = [];
										var val = $(":input[type=text]:not([disabled='disabled'])");

										var chkVal;
										var date;
										var data = [];
										var i = 0;

										var acntName = $("input[name='file-name']:checked");

										$(acntName).each(function() {

											chkVal = $(this).val();

											updateList.push({
												filename : chkVal
											});
										});

										$(val).each(function(el, val) {
											updateList[i].date = val.value;
											i++;
										});

										for (var i = 0; i < updateList.length; i++) {
											var obj=updateList[i];
											if(obj.date==""){
												//alert('Please select date to update');
												$("#errorblock1").html("Please select date to update");
												$("#errorblock1").show("dfsdfsdf");
												return false;
											}
										}
										
										var updateList1 = { updateList };
										
										var mydata = JSON.stringify(updateList1);
										$.ajax({
													type : 'POST',
													//contentType : 'application/json',
													url : 'updateStorePermissionExpiryDate.htm',
													data : {myData:mydata},
													success : function(msg) {
														//alert(msg);
														if(msg=='SUCCESS'){
															$('#successblock1').html('<p>Expiry date updated successfully</p><button type="button" class="close" data-hide="alert" style="position: relative;bottom: 20px;;">&times;</button>');
															$('.userCheck').attr('checked',false);
															$('#successblock1').show();
															$("#errorblock1").css("display", "none");
															table.ajax.reload();
															$('#update-date').attr('disabled',true);
															$("#download").attr("disabled",true);
															$("#downloadExcel").attr("disabled", true);
															$(".close").click(function(){
															    $("#successblock1").hide();
															});
															
														}else{
															$('#errorblock1').html('Error while updating expiry date');
															$('#errorblock1').show();
															$("#successblock1").css("display", "none");
															$('#update-date').attr('disabled',true);
															$("#download").attr("disabled",true);
															$("#downloadExcel").attr("disabled", true);
														}
													}
												});

									});

					$('.filter-ref').on('keyup', function() {
						oTable.columns(1).search(this.value).draw();
					});
					$('.visiData').click(function() {
										if ($(".visiData").length == $(".visiData:checked").length) {
											$("#example-select-all").attr(
													"checked", "checked");
										} else {
											$("#example-select-all").removeAttr("checked");
										}
									});

					$('.coe-dashboardTable tr th:eq(1)').on('click',function() {
								$(".filter-ref").fadeIn();
								$('.coe-dashboardTable tr th:eq(1) span').hide();

							});

					$('.filter-ref').on('mouseleave blur', function() {
						if ($(this).val() == '') {
							$(this).hide();
							$('.coe-dashboardTable tr th:eq(1) span').show();
						} else {
							$(this).show();
						}
					});

					$('#DataTables_Table_0_length').css({
						'text-align' : 'right',
						'padding' : '21px 10px 0px 15px',
						'float' : 'left'
					});
					$('#DataTables_Table_0_length label').css({
						'color' : '#29290a'
					});

					$(
							$($('#DataTables_Table_0_wrapper .row')[0]).find(
									'.col-sm-6')[0]).css({
						'float' : 'right',
						'padding' : '0 20px 0 0',
						'width' : 'auto'
					});
					$(
							$($('#DataTables_Table_0_wrapper .row')[0]).find(
									'.col-sm-6')[0]).addClass(
							"searchicon-wrapper");

					$(
							$($('#DataTables_Table_0_wrapper .row')[0]).find(
									'.col-sm-6')[0]).append(
							$(".dataTables_filter"));
					$(
							$($('#DataTables_Table_0_wrapper .row')[0]).find(
									'.col-sm-6')[0]).append($(".search-icon"));

					$(
							$($('#DataTables_Table_0_wrapper .row')[0]).find(
									'.col-sm-6')[1]).css({
						'width' : 'auto',
						'float' : 'left',

					});
					$($('#DataTables_Table_0_wrapper .row')[0]).append(
							$(".summary-text"));
					$($('#DataTables_Table_0_wrapper .row')[0]).append(
							$(".coe-text"));
					$($('#DataTables_Table_0_wrapper .row')[0]).append(
							$(".selectAll"));

					$('.selectAll').on('click',function() {

								if ($("input:checkbox").prop("checked")) {
									$("input:checkbox").prop("checked", false);
									$(this).text("Select All");
									$('.coe-dasboardBtn button').prop('disabled', true);

								} else {
									$("input:checkbox").prop("checked", true);
									$(this).text("Unselect All");
									$('.coe-dasboardBtn button').prop('disabled', false);
								}
							});

					$('#DataTables_Table_0_filter').on('keyup',
							'input[type="search"]', function() {
								$('#approve').prop('disabled', true);
								$('#reject').prop('disabled', true);

							});

					$('#DataTables_Table_0_length').on('change', 'select',
							function() {
								$('#approve').prop('disabled', true);
								$('#reject').prop('disabled', true);
							});

					$("#input-1").change(function() {
						$('#successblock').hide();
						$('#errorblock').hide();
						$('#errMsg').hide();
						$('.validate_upload').prop('disabled', false);
					});

				});

function coeStorePermissionFileUpload() {
	var fileName = $("#input-1").val();
	if (fileName == '') {
		$('#uploadErrorMsg').show().html("Please select a file to upload");
		return false;
	} else {
		var FileExt = fileName.substr(fileName.lastIndexOf('.') + 1);
		if (FileExt != "xlsx") {
			if (FileExt != "xls") {
				$('#uploadErrorMsg').show();
				$("#coefileupload").submit(function(e) {
					e.preventDefault();
				});
				return;
			}

		}
		document.forms['coefileupload'].method = "POST";
		document.forms['coefileupload'].action = "uploadStorePermissionExpiryDates.htm";
		document.forms['coefileupload'].submit();
	}
}
