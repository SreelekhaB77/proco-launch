<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="../launchplan/lauchheader.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Lanuch Plan</title>

<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/fileinput.css">
<link rel="stylesheet" href="assets/css/jquery-ui.css">
<link rel="stylesheet" href="assets/css/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap-multiselect.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/fileinput.css" />
<link rel="stylesheet" href="assets/css/launchstyle.css"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="assets/css/custom-font.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/font-awesome.css">
<link rel="stylesheet"
	href="assets/css/smart_wizard_theme_arrows.css" type="text/css" />

<link rel="stylesheet" href="assets/css/smart_wizard.css" type="text/css" />
<link rel="stylesheet" type="text/css" 	href="assets/css/fixedColumns.bootstrap.min.css">
<script type="text/javascript">
	history.pushState(null, null, '');
	window.addEventListener('popstate', function(event) {
		history.pushState(null, null, '');
	});
</script>
</head>
<!--Q1 sprint-3 user story 1 notification bharati code start-->
<style>
.red{
  color:red!important;
}
.black{
  color:black!important;;
}

</style>
<!--Q1 sprint-3 user story 1 notification bharati code end-->

<body class="OpenSans-font">
	<div class="loader">
		<center>
			<img class="loading-image" src="assets/images/spinner.gif"
				alt="loading..." style="height: 150px; width: auto;">
		</center>
	</div>
	<input type="hidden" value="" id="dynamicLaunchId">
	<nav class="navbar navbar-inverse navbar-fixed-top container-bg"
		style="position: relative; top: 120px; z-index: 2; background-image: none ! important; border: none ! important;">
		<div class="container-fluid paddR10">
			<div class="navbar-header marginB10">
				<h1 class="SEGOEUIL-font pull-left">
					Launch Planning Tool 
				</h1>
				<button type="button" class="navbar-toggle collapsed pull-right"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<div class="container-fluid container-bg">
					<div class="row">
						<ul class="nav nav-pills">

							<li role="presentation"
								class="active col-md-3 col-sm-8 col-xs-12 launch-icon-inactive">
								<a href="http://localhost:8083/VisibilityAssetTracker/getLaunchPlanPage.htm"> <!-- <div class="launch-icon"></div> -->
									<div class="tab-label-launch">Launch Plannning</div>
							</a>
							</li>
							<li role="presentation"
								id=" note-group" class="active col-md-3 col-sm-8 col-xs-12 launch-icon-active">
								<a href="http://localhost:8083/VisibilityAssetTracker/getAllLaunchData.htm"> <!-- <div class="launch-icon"></div> -->
									<div class="tab-label-launch">Edit & Approve <span id="NotificationBadge" class="notification-number"></span>
  </span></div>
							</a>
							</li>
							<li role="presentation"
								class="active col-md-3 col-sm-8 col-xs-12 launch-icon-inactive">
								<a href="http://localhost:8083/VisibilityAssetTracker/getLaunchPlanPage.htm"> <!-- <div class="launch-icon"></div> -->
									<div class="tab-label-launch">Performance</div>
							</a>
							</li>
							<li role="presentation"
								class="active col-md-3 col-sm-8 col-xs-12 launch-icon-inactive">
								<a href="http://localhost:8083/VisibilityAssetTracker/getLaunchPlanPage.htm"> <!-- <div class="launch-icon"></div> -->
									<div class="tab-label-launch">Timelines</div>
							</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</nav>
	<div class="container-fluid container-bg middle-section">
		<%-- 	<c:if test="${success!=null}"> --%>
		<div class="alert alert-success sucess-msg" id="successblock">
			<button type="button" class="close" data-hide="alert">&times;</button>
			<%-- <c:out value="${success}"></c:out> --%>
			<span></span>
		</div>
		<c:if test="${success_file_upload!=null}">
			<div class="alert alert-success sucess-msg" id="successblock"
				style="display: block">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<c:out value="${success_file_upload}"></c:out>
			</div>
		</c:if>
		<div class="alert alert-success alert-dismissible" id="successblockapp" style="display:none;">
		    <button type="button" class="close" data-dismiss="alert">&times;</button>
		    <strong>Success!</strong><span></span>
		  </div>
		<c:if test="${errorMsg!=null}">
			<div class="alert alert-danger  sucess-msg" id="errorblock">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${errorMsg}"></c:out>
				<a href="#" id="downloadTempFileLink"
					onclick="javascript:downloadCoeErrorFile();">Click here to
					Download Error File:</a>
			</div>
		</c:if>

		<form id="launchPlanForm" action="#" method="POST"
			enctype="multipart/form-data">

			<!-- SmartWizard html -->
			<div id="smartwizard">
				<ul class="tmeLaunchUi">
					<li><a href="#step-1" data-tabid="1" title="Launch Details"
						id="launchEditDetailsTab">Edit Details<br /></a></li>
					<li><a href="#step-2" data-tabid="2" title="Basepacks"
						id="launchEditKamInp">Kam Inputs<br /></a></li>
					<li><a href="#step-3" data-tabid="3" title="Launch Stores"
						id="launchEditQuer">Queries Answered<br /></a></li>
					<li><a href="#step-4" data-tabid="4" title="Sell in"
						id="launchEditMstnClear">MSTN Clearance<br /></a></li>

				</ul>


				<span style="clear: both; display: block;">&nbsp;</span>

				<div id="accordion" class="tab_container" role="tablist"
					aria-multiselectable="true">

					<div id="step-1" class="tab_content" data-blockid="1">
						<div class="card-header" role="tab" id="headingOne">
							<div class="mb-0">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne" class="collapsed"> <span>EDIT
										DETAILS</span>
								</a>
							</div>
						</div>

						<div id="collapseOne" class="collapse" role="tabpanel"
							aria-labelledby="headingOne" aria-expanded="false"
							style="height: 0px; display: block;">
							<div class="card-block">
								<div class="child-table">
									<div class="detail_table">
									
									<div class="col-md-3">
                                            <label for="sel1" class="userlist-space custom-label-align"  >MOC:</label>
                                             <select id="mocCol" class="form-control custom-select-align input-sm">
                                                        <option value="All">All</option>
                                                            <c:forEach items="${tmemoclist}" var="mocVal">
                                                                    <option value="${mocVal}"><c:out value="${mocVal}"></c:out></option>
                                                            </c:forEach>
                                             </select></div>
									
									<div class="col-md-3">
                                            <label for="sel1" class="userlist-space tme-label-align">Launch name:</label>
                                             <select id="launchName" class="form-control tme-select-align">
                                                        <option value="All">All</option>
                                                            <c:forEach items="${tmeLaunchNamelist}" var="mocVal">
                                                                    <option value="${mocVal}"><c:out value="${mocVal}"></c:out></option>
                                                            </c:forEach>
                                             </select></div>
										<table
											class="table table-striped table-bordered table-responsive"
											id="editDet" cellspacing="0" cellpadding="0"
											style="width: 100% ! important">
											<thead class="thead-dark">
											<!--sprint-4 US 2.1 Tme Original MOC col Aug-2021 By Bharati-->
												<tr>
													<th><span
														class="proco-btn proco-btn-success table-head">Select</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Launch
															Name</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Launch
															MOC</span></th>
															<th><span
														class="proco-btn proco-btn-success table-head">Changed MOC</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Status</span></th>
												</tr>
											</thead>
											<input type="hidden" id="editlaunchname" value="${editlaunch.launchName}">
											<input type="hidden" id="editlaunchmoc" value="${editlaunch.launchMoc}">
											
										    <!--<tbody>
												<c:forEach items="${listOfLaunchData}" var="editlaunch">
													<tr>
														<td><input type="checkbox" name="editLaunchscr1"
															class="editlaunchsel" value="${editlaunch.launchId}"></td>
														<td>${editlaunch.launchName}</td>
														<td>${editlaunch.launchMoc}</td>
														<td>${editlaunch.launchFinalStatus}</td>

													</tr>
												</c:forEach>
											</tbody>-->
										</table>

										<div class="coebtnclass">
											<!-- <a href="#" class="btn btn-secondary sw-btn-prev disabled prev" disabled> Previous</a> -->
											<!-- <a href="#" class="btn btn-secondary sw-btn-next">Next</a> -->
											<input type="button" onclick="editLaunchscrone()"
												value="Launch Details"
												class="btn btn-secondary nxtclassification"
												id="editlnchDets" style="float: right;" /> <input
												type="button" onclick="editLaunchscr()" value="Next"
												class="btn btn-secondary nxtclassification nxtbtn"
												id="editlnchDetsNext"
												style="float: right; margin-right: 10px;" />
										</div>

									</div>

								</div>


							</div>
						</div>
					</div>
					<div id="step-2" data-blockid="2" class="tab_content">
						<!-- class="card secondCard hide_tab" id="launch_basepack" -->
						<div class="card-header" role="tab" id="headingOne">
							<div class="mb-0">
								<a class="collapsed" data-toggle="collapse"
									data-parent="#accordion" href="#collapseTwo"
									aria-expanded="false" aria-controls="collapseTwo"
									id="parentCollapse"> <span>CHANGE REQUEST</span>
								</a>
							</div>
						</div>
						<div id="collapseTwo" class="collapse" role="tabpanel"
							aria-labelledby="headingOne" aria-expanded="false"
							style="display: block;">
							<div class="card-block">
								<div class="child-table">
									<div class="chngRe">
										<table
											class="table table-striped table-bordered table-responsive"
											id="changeRequesTable">
											<thead class="thead-dark">
												<tr>
													<th class="scr2tr"><span
														class="proco-btn proco-btn-success table-head">Select</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Launch
															Name</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Launch
															MOC</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Account</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Name</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Changes
															Requested</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">KAM
															Remarks</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Request
															Date</span></th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
										<div class="btnclass">
											
											<input type="button" value="Previous" class="btn btn-secondary nxtclassification prvBtn" id="prevKamin" style="float: left; margin-right: 10px;" /> 
											<input type="button" onclick="approveRequest()" value="Approve" class="btn btn-secondary nxtclassification" id="apprv" style="float: left;"
												disabled="disabled" /> 
											<input type="button"onclick="rejectRequest()" value="Reject" class="btn btn-secondary nxtclassification" id="rjct" style="float: left; margin-left: 10px;"
												disabled="disabled" /> 
											<input type="button" onclick="nextQueryAns()" value="Next" class="btn btn-secondary nxtclassification" style="float: right; margin-right: 10px;" />
											<form id="downloadEditReq" method="post">
												<button type="button" class="btn col-md-2 rightBtn"
													style="margin-right: 10px;"
													onClick="javascript: downloadEditLaunchRequest();"
													id="downloadEditLaunchRequestId" disabled="disabled">
													<span class="glyphicon glyphicon-arrow-down"
														style="color: #ffffff;"></span>Download Requests
												</button>
											</form>
										</div>

									</div>

								</div>
							</div>
						</div>
						<form id="launchApprovStatupload" class="form-horizontal"
							action="#" modelAttribute="LAUNCHFileUploadBean"
							enctype="multipart/form-data" name="launchfileupload">
							<div class="launchupload-parent">
								<div class="col-md-12 col-sm-12 ddd">
									<div style="text-align: center; color: #000000;">

										<h2 class="SEGOEUIL-font">Upload Approval Status</h2>
										<div class="upload-image">
											<img src="assets/images/upload-icon-n-body.png" alt="" />
										</div>

										<div class="upload-max-size">Maximum Upload File Size
											:2MB</div>
										<span id="uploadErrorMsg" style="display: none; color: red">Please
											upload .xls or .xlsx file</span>
										<div class="input-group upload-file">
											<input id="uploadsecscre" name="file" type="file"
												class="file">
										</div>
										<button class="validate_upload btn marginT10 new-btn-primary"
											id="btnSubmitBasePack">Upload Approval Status</button>

									</div>
								</div>

							</div>

						</form>
					</div>
					<div class="tab_content" data-blockid="3" id="step-3">
						<div class="card-header" role="tab" id="headingOne">
							<div class="">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne" class="collapsed"> <span>QUERIES
										ANSWERED</span>
								</a>
							</div>
						</div>
						<div id="collapseOne" class="collapse" role="tabpanel"
							aria-labelledby="headingOne" aria-expanded="false"
							style="height: 0px; display: block;">
							<div class="card-block">
								<div class="child-table">
									<div class="queDiv">
										<table
											class="table table-striped table-bordered table-responsive"
											id="querAnsTable">
											<thead class="thead-dark">
												<tr>
													<th><span
														class="proco-btn proco-btn-success table-head">Launch
															Name</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Launch
															MOC</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Account</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Name</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Request
															Date</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Changes
															Requested</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">KAM
															Remarks</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Response
															Date</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Approval
															status</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Remarks</span></th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
										<div class="btnclass">
											<input type="button" value="Previous"
												class="btn btn-secondary nxtclassification" id="prevKamQue"
												style="float: left;" /> <input type="button"
												onclick="querAnsSave()" value="Next"
												class="btn btn-secondary nxtclassification" id="qurans"
												style="float: right;" />
											<!-- <input type="button" onclick="rejectRequest()" value="Reject" class="btn btn-secondary nxtclassification" id="rjct" style="float: right;" />
											<form id="downloadEditReq" method="post">
												<button type="button" class="btn col-md-2 rightBtn" onClick="javascript: downloadEditLaunchRequest();">
													<span class="glyphicon glyphicon-arrow-down" style="color: yellow"></span>Download Requests
												</button>
											</form> -->
										</div>

									</div>

								</div>

							</div>

							<!-- <div class="btnclass">
									</div> -->
						</div>
						<!-- <form id="launchstrfileupload" class="form-horizontal" action="#" modelAttribute="LAUNCHFileUploadBean" enctype="multipart/form-data" name="launchstrfileupload">
							<div class="launchupload-parent basepcupload-parent">
								<div class="col-md-12 col-sm-12 ddd">
									<div style="text-align: center; color: #878787;">

										<h2 class="SEGOEUIL-font">Upload Launchpacks</h2>
										<div class="upload-image">
											<img src="assets/images/upload-icon-n-body.png" alt="" />
										</div>

										<div class="upload-max-size">Maximum Upload File Size:2MB</div>
										<span id="uploadclsErrorMsg" style="display: none; color: red">Please
											upload .xls or .xlsx file</span>
										<div class="input-group upload-file">
											<input id="uploadcls" name="file" type="file" class="file">
										</div>
										<button class="validate_upload btn marginT10 btn-primary" id="launchstrFileUploadBtn">Upload cluster</button>

									</div>
								</div>

							</div>

						</form> -->
					</div>
					<div class="card thirdCard tab_content" data-blockid="4" id="step-4">
						<div class="card-header" role="tab" id="headingOne">
							<div class="">
								<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false" aria-controls="collapseOne" class="collapsed"> <span>MSTN CLEARANCE</span>
								</a>
							</div>
						</div>
						<div id="collapseOne" class="collapse" role="tabpanel"
							aria-labelledby="headingOne" aria-expanded="false"
							style="height: 0px; display: block;">
							<div class="card-block">
								<div class="child-table">
									<div>

										<table id="mstnEditClearTable" class="table table-striped table-bordered nowrap order-column row-border table-responsive" style="width: 100%">
											<thead class="thead-dark">
												<tr>
													<th><span class="proco-btn proco-btn-success table-head">Launch Name</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Basepack Code</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Basepack Desription</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Launch Moc</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Depot</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Cluster</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Final CLD N</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Final CLD N1</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Final CLD N2</span></th>
													<th><span class="proco-btn proco-btn-success table-head">MSTN Cleared</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Current Estimates</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Clearance Date</span></th>
												</tr>
											</thead>
											<tbody>
												
											
											</tbody>
										</table>
										<div class="btnclass">
											<input type="button" value="Previous" class="btn btn-secondary nxtclassification" id="prevKamMst" style="float: left;" />
										</div>
									</div>
								</div>
							</div>

						</div>

					</div>
				</div>


			</div>
		</form>

	</div>


	<!-- modal for Approve TME remars -->
		<div id="approveRequestTme" class="modal fade" role="dialog"
			data-backdrop="static" data-keyboard="false" style="display: none;">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="popupLoader" style="display: none;">
					<img class="loading-image1" src="assets/images/spinner.gif"
						alt="loading..."
						style="height: 75px; width: auto; margin-top: 70px;">
				</div>
				<div class="modal-content tmeedit-custom-popup-width">


					<div class="paid-visi">
						<div class="modal-header"
							style="background-color: #3B5999; color: #fff;">
							<button type="button" class="close" id="paidcrossBtn"
								data-dismiss="modal">X</button>
							<h4 class="modal-title">Approve Request(s)</h4>
						</div>
						<form action="#" id="rejRemarkForm" method="POST"
							class="form-horizontal" style="padding: 10px 0;">

							<div class="modal-body">
								<div class="row">

									<div class="col-md-12">

										<div id="msg-error" class="alert alert-danger fade in"
											style="display: none;">
											<span></span>
											<div id="kamMoc-error" class="cust-error error"
												style="text-align: center; padding-bottom: 10px;">Please
												enter remarks</div>
											<button type="button" class="close" data-hide="alert">x</button>
										</div>

										<div class="OpenSans-font">

											<div class="form-group">
												<label for="downloadmoc" class="col-sm-4 control-label">TME
													Remarks:</label> <span id="tmeRemakRejErrorMsg"
													style="display: none; color: red; margin-left: 17px;">Please
													enter remarks.</span>
												<div class="col-sm-6 offset-col-md-2">
												<span id="kamRemakErrorMsg"
													style="display: none; color: red; margin-left: 17px;">Please
													enter remarks.</span>
													<input name='kamRemark' type='text'
														class='form-control validfield' id="tmeRemarks">
												</div>

											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button"
									class="btn btn-default pull-right resetBtn new-btn-primary"
									data-dismiss="modal" data-hide="alert" id="apprCancelBtn">Close</button>
								<button type="button" onclick='approveKamInputs()'
									id="tmeRemrs"
									class="btn pull-right resetBtn marginR10"
									style="background-color: #3B5999 !important; height: 33px; margin-right: 8px; color: #fff;">Approve</button>
							</div>

						</form>

					</div>
				</div>
			</div>
		</div>

	<!-- <div class="modal fade" id="rejectRequestTme">
		<div class="modal-dialog">
			<div class="modal-content">

				Modal Header
				<div class="modal-header">
					<h4 class="modal-title">Reject Request(s)</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				Modal body
				<div class="modal-body">
					<div class="form-group">
						<label for="comment">TME Remarks:</label>
						<textarea class="form-control" rows="5" id="tmeRejectRemarks"></textarea>
					</div>
				</div>

				Modal footer
				<div class="modal-footer">
					<button type="button" class="btn btn-success" data-dismiss="modal" id="rejLnh"
						onclick='rejectKamInputs()'>Reject</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
				</div>

			</div>
		</div>
	</div> -->
	
	<!-- modal for reject TME remars -->
		<div id="rejectRequestTme" class="modal fade" role="dialog"
			data-backdrop="static" data-keyboard="false" style="display: none;">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="popupLoader" style="display: none;">
					<img class="loading-image1" src="assets/images/spinner.gif"
						alt="loading..."
						style="height: 75px; width: auto; margin-top: 70px;">
				</div>
				<div class="modal-content tmeedit-custom-popup-width">


					<div class="paid-visi">
						<div class="modal-header"
							style="background-color: #3B5999; color: #fff;">
							<button type="button" class="close" id="paidcrossBtn"
								data-dismiss="modal">X</button>
							<h4 class="modal-title">Reject Request(s)</h4>
						</div>
						<form action="#" id="rejRemarkForm" method="POST"
							class="form-horizontal" style="padding: 10px 0;">

							<div class="modal-body">
								<div class="row">

									<div class="col-md-12">

										<div id="msg-error" class="alert alert-danger fade in"
											style="display: none;">
											<span></span>
											<div id="kamMoc-error" class="cust-error error"
												style="text-align: center; padding-bottom: 10px;">Please
												enter remarks</div>
											<button type="button" class="close" data-hide="alert">�</button>
										</div>

										<div class="OpenSans-font">

											<div class="form-group">
												<label for="downloadmoc" class="col-sm-4 control-label">TME
													Remarks:</label> <span id="kamRemakRejErrorMsg"
													style="display: none; color: red; margin-left: 17px;">Please
													enter remarks.</span>
												<div class="col-sm-6 offset-col-md-2">
												<span id="tmerejRemakErrorMsg"
													style="display: none; color: red; margin-left: 17px;">Please
													enter remarks.</span>
													<input name='kamRemark' type='text'
														class='form-control validfield' id="tmeRejectRemarks">
												</div>

											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button"
									class="btn btn-default pull-right resetBtn new-btn-primary"
									data-dismiss="modal" data-hide="alert" id="tmeRejCancelBtn">Close</button>
								<button type="button" onclick='rejectKamInputs()'
									id="tmeRemrs"
									class="btn pull-right resetBtn marginR10"
									style="background-color: #3B5999 !important; height: 33px; margin-right: 8px; color: #fff;">Reject</button>
							</div>

						</form>

					</div>
				</div>
			</div>
		</div>
	

	<%@include file="../launchplan/footer.jsp"%>


	<script type="text/javascript" src="assets/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
	<!-- <script type="text/javascript" src="assets/js/comboTreePlugin.js"></script> -->
	<script type="text/javascript" src="assets/js/bootstrapValidator.js"></script>
	<script type="text/javascript" src="assets/js/jquery-ui.js"></script>
	<script type="text/javascript" src="assets/js/fileinput.js"></script>
	<script type="text/javascript"
		src="assets/js/bootstrap-multiselect.js"></script>
	<script type="text/javascript"
		src="assets/js/custom/launch/tmescripteditapprove.js"></script>
	<script src="assets/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="assets/js/custom/proco/alert-modal.js"></script>
	<!-- <script type="text/javascript" src="assets/js/jquery.smartWizard.js"></script> -->
	<script type="text/javascript"
		src="assets/js/jquery.smartWizard.min.js"></script>
	<script src="assets/js/jquery.dataTables.min.js"></script>
	<!-- <script src="assets/js/dataTables.fixedColumns.min.js"></script>  -->
	<script src="assets/js/dataTables.bootstrap.min.js"></script>
	<script type="text/javascript">
	
 /*  var data = '${geographyJson}';
  var clus = '${geographyJson}';
  var custmr = '${CustomerJson}';
  var bool; */
 
	
  </script>
</body>
</html>

