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
<link rel="stylesheet" type="text/css" href="assets/css/fileinput.css">
<link rel="stylesheet" href="assets/css/jquery-ui.css">
<link rel="stylesheet" href="assets/css/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="assets/css/bootstrap-multiselect.css">
<link rel="stylesheet" type="text/css" href="assets/css/fileinput.css" />
<link rel="stylesheet" href="assets/css/launchstyle.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="assets/css/custom-font.css">
<link rel="stylesheet" type="text/css" href="assets/css/font-awesome.css">
<link rel="stylesheet" href="assets/css/smart_wizard_theme_arrows.css" type="text/css" />
<link rel="stylesheet" href="assets/css/smart_wizard.css" type="text/css" />
<!-- <link rel="stylesheet" type="text/css" href="assets/css/fixedColumns.bootstrap.min.css"> -->


<script type="text/javascript">
	history.pushState(null, null, '');
	window.addEventListener('popstate', function(event) {
		history.pushState(null, null, '');
	});
	
</script>
</head>

<body class="OpenSans-font">
	<div class="loader">
		<center>
			<img class="loading-image" src="assets/images/spinner.gif"
				alt="loading..." style="height: 150px; width: auto;">
		</center>
	</div>
	<!-- <input type="hidden" value="" id="dynamicLaunchId"> -->
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
					<span class="sr-only">Toggle navigation</span> 
					<span class="icon-bar"></span> <span class="icon-bar"></span> 
					<span class="icon-bar"></span>
				</button>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<div class="container-fluid container-bg">
					<div class="row">
						<ul class="nav nav-pills">

							<li role="presentation" class="active col-md-3 col-sm-8 col-xs-12 launch-icon-active">
								<a href="http://localhost:8083/VisibilityAssetTracker/getAllCompletedLaunchData.htm"> <!-- <div class="launch-icon"></div> -->
										<div class="tab-label-launch">Launch Plannning</div>
								</a>
							</li>
							<li role="presentation" class="active col-md-3 col-sm-8 col-xs-12 launch-icon-inactive">
								<a href="http://localhost:8083/VisibilityAssetTracker/getAllCompletedLaunchData.htm"> <!-- <div class="launch-icon"></div> -->
									<div class="tab-label-launch">Performance</div>
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
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${success_file_upload}"></c:out>
			</div>
		</c:if>
		<%-- </c:if> --%>
		<%-- <c:if test="${errorMsg!=null}">
			<div class="alert alert-danger sucess-msg" id="errorblock">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${errorMsg}"></c:out>
			</div>
		</c:if> --%>
		<c:if test="${errorMsg!=null}">
			<div class="alert alert-danger  sucess-msg" id="errorblock">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${errorMsg}"></c:out>
				<a href="#" id="downloadTempFileLink"
					onclick="javascript:downloadCoeErrorFile();">Click here to
					Download Error File:</a>

			</div>
		</c:if>

		<!-- <form id="launchPlanForm" action="#" method="POST"
			enctype="multipart/form-data"> -->
			<!-- <input type="hidden" name="reasonText" id="coereasonText" value="">
			<input type="hidden" name="remarkText" id="coeremarkText" value=""> -->

			<!-- SmartWizard html -->
			<div id="smartwizard">
				<ul>
					<li><a href="#step-1" data-tabid="1" id="coelaunchDetailsTab">Launch Details<br /></a></li>
					<li><a href="#step-2" data-tabid="2" id="coelaunchBasepackTab">Basepacks<br /></a></li>
					<li><a href="#step-3" data-tabid="3" id="coelaunchStoresTab">Launch Build Up<br /></a></li>
					<li><a href="#step-4" data-tabid="4" id="coelaunchSellInTab">Listing Tracker<br /></a></li>
					<li><a href="#step-5" data-tabid="5" id="coelaunchVisiTab">MSTN Clearance<br /></a></li>
					<li><a href="#step-6" data-tabid="6" id="coelaunchFinBuiUpTab">Documents<br /></a></li>
					<li><a href="#step-7" data-tabid="7" id="coelaunchSubTab">Final Store List<br /></a></li>
				</ul>


				<span style="clear: both; display: block;">&nbsp;</span>

				<div id="accordion" class="tab_container" role="tablist" aria-multiselectable="true">

					<div id="step-1" class="tab_content" data-blockid="1">
						<div class="card-header" role="tab" id="headingOne">
							<div class="mb-0">
								<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne" class="collapsed"> <span>LAUNCH DETAILS</span>
								</a>
							</div>
						</div>

						<div id="collapseOne" class="collapse" role="tabpanel" aria-labelledby="headingOne" aria-expanded="false" style="height: 0px; display: block;">
							<div class="card-block">
								<div class="child-table">
									<div class="table-responsive detail_table">
									<div class="col-md-3">
                                            <label for="sel1" class="userlist-space custom-label-align"  >MOC:</label>
                                             <select id="mocCol" class="form-control custom-select-align input-sm">
                                                        <option value="All">All</option>
                                                            <c:forEach items="${coemoclist}" var="mocVal">
                                                                    <option value="${mocVal}"><c:out value="${mocVal}"></c:out></option>
                                                            </c:forEach>
                                             </select></div>
										<table class="table table-striped table-bordered" id="coebasepack_add" cellspacing="0" cellpadding="0" style="width: 100% ! important">
											<thead class="thead-dark">
												<tr>
													<th class="th-align"><span class="proco-btn proco-btn-success table-head"><input type="checkbox" id="selectAll" class="main" style="float:left;"/> Select</span></th>
													<th style="width:auto !important"><span class="proco-btn proco-btn-success table-head">Launch Name</span></th>
													<th style="width:auto !important"><span class="proco-btn proco-btn-success table-head">Launch MOC</span></th>
													<th style="width:auto !important"><span class="proco-btn proco-btn-success table-head">Submitted Date</span></th>
													<th style="width:auto !important"><span class="proco-btn proco-btn-success table-head">CMM</span></th>
													<th style="width:auto !important"><span class="proco-btn proco-btn-success table-head">Account Names</span></th>
													
												
												</tr>
											</thead>
										<!--	<tbody>
												 <c:forEach items="${listOfLaunch}" var="launch">
											       <tr>
											       	<td><input type='checkbox' class='coechecklaunch' name='selectDel' value='${launch.launchId}'></td>
											         <td>${launch.launchName}</td>
											         <td>${launch.launchMoc}</td>
											         <td>${launch.createdDate}</td>
											         <td>${launch.createdBy}</td>
											         <td><textarea rows="3" cols="75">${launch.accountName}</textarea></td>
											        
											       </tr>
											     </c:forEach>
											</tbody>-->
										</table>
										
										<div class="coebtnclass">
											<input type="button" onclick="CoeLaunch()" value="Launch Details" class="btn btn-secondary nxtclassification" id="coelnchDets" style="float: right;" />
										</div>
											<div class="coebtnclass">
											<input type="button"  value="Download" onClick="javascript: coedownloadLaunchBuildTemplate();" class="btn btn-secondary nxtclassification" id="coelnchDets" style="float: left;" />
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
								<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo"
									id="parentCollapse"> <span>LAUNCH BASEPACK</span>
								</a>
							</div>
						</div>
						<div id="collapseTwo" class="collapse" role="tabpanel"
							aria-labelledby="headingTwo" aria-expanded="false"
							style="display: block;">
							<div class="card-block">
								<div class="child-table">
									<div class="coe_base_table">
										<table class="table table-striped table-bordered" id="coe_basepack_add">
											<thead class="thead-dark">
												<tr>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Launch Name</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Sales Category</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">PSA Category</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Brand</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Basepack Code</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Basepack Description</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">MRP</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">TUR</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">GSV</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">CLD Configuration</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Grammage</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Classification</span></th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
										<div class="btnclass">
											<input type="button" value="Previous" class="btn btn-secondary nxtclassification previousCoe" id="coeprevbspack" style="float: left;" />
											
											<!--   <button type="button" class="btn col-md-2 rightBtn"><span class="glyphicon glyphicon-arrow-down" style="color:yellow"></span>  Download</button> -->
											<form id="downloadcoebasepck" method="post">
												<button type="button" class="btn col-md-2 rightBtn"
													onClick="javascript: coedownloadLaunchBasepackTemplate();">
													<span class="glyphicon glyphicon-arrow-down" style="color: yellow"></span>Download Basepack Details
												</button>
											</form>
											<input type="button" onclick="coeSaveBasepacks()" value="Next" class="btn btn-secondary nxtclassification" id="coebspack" style="float: right;" />
										</div>
																			
									</div>

								</div>
							</div>
						</div>
					
					</div>
					<div class="tab_content" data-blockid="3" id="step-3">
						<div class="card-header" role="tab" id="headingOne">
							<div class="">
								<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false" aria-controls="collapseOne" class="collapsed"> <span>LAUNCH BUILD UP</span>
								</a>
							</div>
						</div>
						<div id="collapseOne" class="collapse" role="tabpanel"
							aria-labelledby="headingOne" aria-expanded="false"
							style="height: 0px; display: block;">
							<div class="card-block">
							
								<div class="child-table">
									<div class="coe_launch_bsk">
										<table class="table table-striped table-bordered" id="coe_basepack_launch_build">
											<thead class="thead-dark">
												<tr>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Launch Name</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">SKU Name</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Basepack Code</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">MOC</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Sell-In Value</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Sell-In Value N1</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Sell-In Value N2</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Sell-In CLD</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Sell-In CLD N1</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Sell-In CLD N2</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Sell-In Units</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Sell-In Units N1</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Sell-In Units N2</span></th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
										<div class="btnclass">
											<input type="button" value="Previous" class="btn btn-secondary nxtclassification previousCoe" id="coeprevlnchbuld" style="float: left;" />
											
											<!--   <button type="button" class="btn col-md-2 rightBtn"><span class="glyphicon glyphicon-arrow-down" style="color:yellow"></span>  Download</button> -->
											<form id="downloadbuildbasepck" method="post">
												<button type="button" class="btn col-md-2 rightBtn"
													onClick="javascript: coedownloadLaunchBuildTemplate();">
													<span class="glyphicon glyphicon-arrow-down"
														style="color: yellow"></span>Download Launch BuildUp Data
												</button>
											</form>
											<input type="button" onclick="saveBuildUp()" value="Next" class="btn btn-secondary nxtclassification" id="bspack" style="float: right;" />
										</div>
										<!-- <div class="btnclass">
											<a href="#" class="btn btn-secondary sw-btn-prev disabled prev" disabled> Previous</a>
											<a href="#" class="btn btn-secondary sw-btn-next">Next</a>
											<input type="button" onclick="saveBuildUp()" value="Next" class="btn btn-secondary nxtclassification" id="bspack" style="float: right;" />
										</div> -->
										
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="card thirdCard tab_content" data-blockid="4" id="step-4">
						<div class="card-header" role="tab" id="headingOne">
							<div class="">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne" class="collapsed"> <span>LISTING TRACKER</span>
								</a>
							</div>
						</div>
						<div id="collapseOne" class="collapse" role="tabpanel" aria-labelledby="headingOne" aria-expanded="false" style="height: 0px; display: block;">
							<div class="card-block">
									<div class="child-table">
									<div class="coe_table">
										<table class="table table-striped table-bordered MyriadPro-Regular-font coeLaunchdisplay dataTable" id="list_track_table" cellpadding="0" style="width: 100% ! important">
											<thead class="thead-dark">
												<tr>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Launch Name</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Launch MOC</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">SKU Name</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Basepack Code</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Account</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Cluster</span></th>
																							
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
										
									</div>
								</div>
						
							<div class="btnclass">
							<input type="button" value="Previous" class="btn btn-secondary nxtclassification previousCoe" id="coeprevlsttrck" style="float: left;" />
									<form id="downloadListpck" method="post">
												<button type="button" class="btn col-md-2 rightBtn"
													onClick="javascript: coedownloadListTemplate();">
													<span class="glyphicon glyphicon-arrow-down"
														style="color: yellow"></span>Download Listing
												</button>
											</form>
								 
								
								 <input type="button" onclick="nextListTrackTable()" value="Next" class="btn btn-secondary nxtclassification" id="sellinNext" style="float: right;" /> 
							</div>
							
					</div>
				</div>
		</div>
					<div class="card forthCard tab_content" data-blockid="5" id="step-5">
						<div class="card-header" role="tab" id="headingOne">
							<div class="">
								<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne" class="collapsed"> <span>MSTN CLEARANCE</span>
								</a>
							</div>
						</div>
						<div id="collapseOne" class="collapse" role="tabpanel" aria-labelledby="headingOne" aria-expanded="false" style="height: 0px; display: block;">
							<div class="card-block">
								<div class="child-table">
									<div>
									<table id="mstnClearance" class="table table-striped table-bordered nowrap order-column row-border smt-dashboardTable table-responsive" style="width:100%">
											<thead class="thead-dark">
												<tr>
													<th><span class="proco-btn proco-btn-success table-head">Launch Name</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Basepack Code</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Basepack Desription</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Launch Moc</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Depot</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Cluster</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Account</span></th>
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
								</div>
							</div>
								</div>
							
										<div  class="btnclass">
											<input type="button" value="Previous" class="btn btn-secondary nxtclassification previousCoe" id="coeprevmstn" style="float: left;" />
											 <!--  <form id="downloadMstnpck" method="post"> -->
													<button type="button" class="btn col-md-2 rightBtn" onClick="coedownloadMstnClrTemplate()">
														<span class="glyphicon glyphicon-arrow-down" style="color: yellow"></span>Download MSTN Clearance
													</button>
												<!-- </form> -->
											<input type="button" onclick="coeMstnClear()" value="Next" class="btn btn-secondary nxtclassification" id="mstnNext" style="float: right;" />
											
										</div>
							</div>
					
				</div>
					
					<div class="card fifthCard tab_content" data-blockid="6" id="step-6">
						<div class="card-header" role="tab" id="headingOne">
							<div class="">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne" class="collapsed"> <span>DOCUMENTS</span>
								</a>
							</div>
						</div>
						<div id="collapseOne" class="collapse" role="tabpanel" aria-labelledby="headingOne" aria-expanded="false" style="height: 0px; display: block;">
							<div class="card-block">
								<div class="child-table">

									<div class="alert alert-danger marginT15" id="coeuploaddErrorMsg"	style="display: none;">
										<button type="button" class="close" data-hide="alert">&times;</button>
										<span>Please upload .xls or .xlsx file</span>
									</div>

									<div class="upload-parent">
										<div class="col-md-12 ddd">
											<div class="download-block">
											 <div class="alert alert-success sucess-msg" id="successblockUpload">
								                  <button type="button" class="close" data-hide="alert">&times;</button>
								                  <%-- <c:out value="${success}"></c:out> --%>
								                      <span></span>
								          	 </div>

												<!-- <form action="#" method="POST" enctype="multipart/form-data" id="coeannexFileUpload" class="form-horizontal" name="coeannexFileUpload"> -->

													<div class="form-group row">
														<div class="input-group upload-file">
														
															<label class="col-sm-2 col-md-3 control-label coeAnnSubLabel">Annexure</label>
															 <div class="col-sm-10 col-md-4 coesubUpload">
																<!-- <input class="validate_upload btn marginT10 btn-primary" id="coeAnnexureUploadBtn" type="button" value="Download" onclick="coeuploadAnnexureDoc('0')"></input> -->
																<button type="button" class="btn col-md-12 rightBtn" onclick="coedownloadAnnex()">
																	<span class="glyphicon glyphicon-arrow-down" style="color: yellow"></span> Download Annexure template
																</button>
															</div> 
															
														</div>

													</div>
													<!-- </form> -->
													
													<!-- <form action="#" method="POST" enctype="multipart/form-data" id="coeartworkFileUpload"	class="form-horizontal" name="coeartworkFileUpload"> -->
													<div class="form-group row">
														<div class="input-group upload-file">
														
															<label class="col-sm-2 col-md-3 control-label coeArtsubLabel">Artwork packshots</label>
															<!-- <div class="col-sm-10 col-md-6 subfileDiv">
															<span id="coeuploadartErrorMsg" style="display: none; color: red">Please upload .xls or .xlsx file</span>
																<input id="artfile" name="file" type="file" class="file" size="400">
															</div> -->
															<div class="col-sm-10 col-md-4 subUpload">
																<!-- <input class="validate_upload btn marginT10 btn-primary" id="coeartworkUploadBtn" onclick="coeuploadArtDoc('0')"  type="button" value="Upload"></input> -->
																<button type="button" class="btn col-md-12 rightBtn" onclick="coedownloadArtWork()">
																<span class="glyphicon glyphicon-arrow-down" style="color: yellow"></span> Download Artwork packshots template
															</button>
															</div>
														</div>

													</div>
													<!-- </form> -->
													
													<!-- <form action="#" method="POST" enctype="multipart/form-data" id="tmemdgFileUpload"	class="form-horizontal" name="tmeFileUploadBean"> -->
													<div class="form-group row">
														<div class="input-group upload-file">
														
															<label class="col-sm-2 col-md-3 control-label coeMdgsubLabel">MDG deck</label>
															<!-- <div class="col-sm-10 col-md-6 subfileDiv">
															<span id="uploadmdgErrorMsg" style="display: none; color: red">Please upload .xls or .xlsx file</span>
																<input id="mdgfile" name="file" type="file" class="file col-md-8" size="40">
															</div> -->
															<div class="col-sm-10 col-md-4 subUpload">
																<!-- <input class="validate_upload btn marginT10 btn-primary" id="mdgUploadBtn" type="button" value="Upload" onclick="uploadmdgDoc('0')"></input> -->
																<button type="button" class="btn col-md-12 rightBtn" onclick="coedownloadMdgDeckTemplate()">
																	<span class="glyphicon glyphicon-arrow-down" style="color: yellow"></span> Download MDG template
																</button>
															</div>
														</div>

													</div>
												<!-- </form> -->
												
											</div>
										</div>
									</div>
									<div class="btnclass" style="margin-bottom: 60px;">
											<input type="button" value="Previous" class="btn btn-secondary nxtclassification previousCoe" id="coeprevdoc" style="float: left;" />
											<input type="button" onclick="coeDocument()" value="Next" class="btn btn-secondary nxtclassification" id="docNext" style="float: right;" />
								 	</div>
									
								</div>
							</div>
					</div>
				</div>
					<div class="card sixthCard tab_content" data-blockid="7" id="step-7">
						<div class="card-header" role="tab" id="headingOne">
							<div class="">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne" class="collapsed"> <span>FINAL PLANNING</span>
								</a>
							</div>
						</div>
						<div id="collapseOne" class="collapse" role="tabpanel" aria-labelledby="headingOne" aria-expanded="false" style="height: 0px; display: block;">
							<div class="card-block">
								<div class="child-table">
									<div class="table-responsive">
										<table class="table table-striped table-bordered" id="coefinalTable">
											<thead class="thead-dark">
												<tr>

													<th><span class="proco-btn proco-btn-success table-head">Launch name</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Basepack Code</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Basepack Description</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Launch MOC</span></th>
													<th><span class="proco-btn proco-btn-success table-head">L1 Chain</span></th>
													<th><span class="proco-btn proco-btn-success table-head">L2 Chain</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Depot</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Store Format</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Cluster</span></th>
													<th><span class="proco-btn proco-btn-success table-head">HUL OL Code</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Customer Code</span></th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
												</tr>
												<tr>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
												</tr>
												<tr>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
													<td><input name='coelaunchbase' type='text' class='form-control l1chain'  value= ''></td>
												</tr>
											</tbody>
										</table>
										
										<div class="btnclass">
												<input type="button" value="Previous" class="btn btn-secondary nxtclassification previousCoe" id="coeprevfinal" style="float: left;" />
											<!--  <input type="button" onclick="coesaveFinalBuildUpData()" value="Next" class="btn btn-secondary nxtclassification" id="coefinalnext" style="float: right;" />-->
									 	<form id="downloadFinalStrLst" method="post">
												<button type="button" class="btn col-md-2 rightBtn"
													onClick="javascript: coedownloadFinalStrLst();">
													<span class="glyphicon glyphicon-arrow-down"
														style="color: yellow"></span>Download Store List
												</button>
											</form>
									 	
									 	</div>

									</div>
								</div>

							</div>

						</div>

						</div>
					</div>
		<!-- </form> -->
</div>
	</div>
	<%@include file="../launchplan/footer.jsp"%>


	<script type="text/javascript" src="assets/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="assets/js/comboTreePlugin.js"></script>
	<script type="text/javascript" src="assets/js/bootstrapValidator.js"></script>
	<script type="text/javascript" src="assets/js/jquery-ui.js"></script>
	<script type="text/javascript" src="assets/js/fileinput.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap-multiselect.js"></script>
	<script type="text/javascript" src="assets/js/custom/launch/coeLaunchjs.js"></script> 
	<script src="assets/js/jquery-ui.js"></script>
	<script type="text/javascript" src="assets/js/custom/proco/alert-modal.js"></script>
	<!--  <script type="text/javascript" src="assets/js/jquery.smartWizard.js"></script>  -->
	<script type="text/javascript" src="assets/js/jquery.smartWizard.min.js"></script>
	<script src="assets/js/jquery.dataTables.min.js"></script>
  <!-- 	<script src="assets/js/dataTables.fixedColumns.min.js"></script>  -->
    <script src="assets/js/dataTables.bootstrap.min.js"></script>          
	
</body>
</html>
