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
<meta http-equiv="pragma" content="no-cache">
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
<link rel="stylesheet" type="text/css" href="assets/css/fixedColumns.bootstrap.min.css">


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

							<li role="presentation" class="active col-md-2 col-sm-8 col-xs-12 launch-icon-active">
								<a href="http://localhost:8083/VisibilityAssetTracker/getAllCompletedLaunchDataSc.htm"> <!-- <div class="launch-icon"></div> -->
										<div class="tab-label-launch">Launch Plannning</div>
								</a>
							</li>
							<li role="presentation" class="active col-md-2 col-sm-8 col-xs-12 launch-icon-inactive">
								<a href="http://localhost:8083/VisibilityAssetTracker/getAllCompletedLaunchDataSc.htm"> <!-- <div class="launch-icon"></div> -->
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
		
		<div class="alert alert-success sucess-msg" id="successblock">
			<button type="button" class="close" data-hide="alert">&times;</button>
			<%-- <c:out value="${success}"></c:out> --%>
			<span></span>
		</div>
		
		<div class="alert alert-danger marginT15" id="errorblockUpload">
					<button type="button" class="close" data-hide="alert">&times;</button>
					<%-- <c:out value="${errorMsgUpload}"></c:out> --%>
					<span>Error while uploading file.</span>
					<!--  <a href="http://localhost:8083/VisibilityAssetTracker/downloadTmeUploadErrorFile.htm" id="downloadTempFileLink">Click here to Download Error File</a> -->
				</div>
		

		<form id="launchPlanForm" action="#" method="POST"
			enctype="multipart/form-data">
			

			<!-- SmartWizard html -->
			<div id="smartwizard">
				<ul>
					<li><a href="#step-1" data-tabid="1" id="sclaunchDetailsTab">Launch Details<br /></a></li>
					<li><a href="#step-2" data-tabid="2" id="sclaunchBasepackTab">Basepacks<br /></a></li>
					<li><a href="#step-3" data-tabid="3" id="sclaunchStoresTab">Launch Build Up<br /></a></li>
					<li><a href="#step-4" data-tabid="4" id="sclaunchVisiTab">MSTN Clearance<br /></a></li>
					<li><a href="#step-5" data-tabid="5" id="sclaunchFinBuiUpTab">Update Estimates<br /></a></li>
					<!-- <li><a href="#step-6" data-tabid="6" id="coelaunchSubTab">Final Store List<br /></a></li> -->
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
										<table class="table table-striped table-bordered" id="coebasepack_add" cellspacing="0" cellpadding="0" style="width: 100% ! important">
											<thead class="thead-dark">
												<tr>
													<th><span class="proco-btn proco-btn-success table-head">Select</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Launch Name</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Launch MOC</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Submitted Date</span></th>
													<th><span class="proco-btn proco-btn-success table-head">CMM</span></th>
												
												</tr>
											</thead>
											<tbody>
												 <c:forEach items="${listOfLaunch}" var="launch">
											       <tr>
											       	<td><input type='checkbox' class='scchecklaunch' name='scchecklaunch' value='${launch.launchId}' onchange= 'enableScLaunchButtons(this)'></td>
											         <td>${launch.launchName}</td>
											         <td>${launch.launchMoc}</td>
											         <td>${launch.createdDate}</td>
											         <td>${launch.createdBy}</td>
											       </tr>
											     </c:forEach>
											</tbody>
										</table>
										
										<div class="coebtnclass">
											
											<input type="button" onclick="scLaunch()" value="Launch Details" class="btn btn-secondary nxtclassification" id="sclnchDets" style="float: right;" disabled="disabled" />
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
									<div class="scbasetable">
										<table class="table table-striped table-bordered" id="sc_basepack_add">
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
											
											 <!--  <button type="button" class="btn col-md-2 rightBtn"><span class="glyphicon glyphicon-arrow-down" style="color:yellow"></span>  Download</button>
											<form id="downloadcoebasepck" method="post">
												<button type="button" class="btn col-md-2 rightBtn"
													onClick="javascript: coedownloadLaunchBasepackTemplate();">
													<span class="glyphicon glyphicon-arrow-down" style="color: yellow"></span>Download Basepack Details
												</button>
											</form> -->
											<input type="button" value="Previous" class="btn btn-secondary nxtclassification previoussc" id="scprevbspack" style="float: left;" />
											<input type="button" onclick="scSaveBasepacks()" value="Next" class="btn btn-secondary nxtclassification" id="coebspack" style="float: right;" />
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
									<div class="launchtablesc">
										<table class="table table-striped table-bordered" id="sc_basepack_launch_build">
											<thead class="thead-dark">
												<tr>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Launch Name</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">SKU Name</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Basepack Code</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Launch Sell-In Value</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">N+1 Sell-In Value</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">N+2 Sell-In Value</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Launch Sell-In CLDs</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">N+1 Sell-In CLDs</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">N+2 Sell-In CLDs</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Launch Sell-In Units</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">N+1 Sell-In Units</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">N+2 Sell-In Units</span></th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
										<div class="btnclass">
											
											<!--   <button type="button" class="btn col-md-2 rightBtn"><span class="glyphicon glyphicon-arrow-down" style="color:yellow"></span>  Download</button> -->
											<form id="scdownloadbuildbasepck" method="post">
												<button type="button" class="btn col-md-2 rightBtn"
													onClick="javascript: scdownloadLaunchBuildUpTemplate();">
													<span class="glyphicon glyphicon-arrow-down"
														style="color: yellow"></span>Download launch build up
												</button>
											</form>
											<input type="button" value="Previous" class="btn btn-secondary nxtclassification previoussc" id="scprevlnchBulid" style="float: left;" />
											<input type="button"  value="Next" class="btn btn-secondary nxtclassification" id="scbspack" style="float: right;" onclick="getmstnData()"/>
										</div>
										
									</div>
								</div>
							</div>
						</div>
					</div>
			
					<div class="card forthCard tab_content" data-blockid="4" id="step-4">
						<div class="card-header" role="tab" id="headingOne">
							<div class="">
								<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne" class="collapsed"> <span>MSTN CLEARANCE</span>
								</a>
							</div>
						</div>
						<div id="collapseOne" class="collapse" role="tabpanel" aria-labelledby="headingOne" aria-expanded="false" style="height: 584px !important; display: block;">
							<div class="card-block">
								<div class="child-table">
									<div>
									<table id="mstnClearance" class="table table-striped table-bordered nowrap order-column row-border smt-dashboardTable table-responsive" style="width:100%">
											<thead class="thead-dark">
												<tr>
													<!-- <th><span class="proco-btn proco-btn-success table-head">Channel</span></th> -->
													<th><span class="proco-btn proco-btn-success table-head">Launch Name</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Launch MOC</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Basepack Code</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Basepack Desription</span></th>
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
								
							
										<div>
											<input type="button" value="Previous" class="btn btn-secondary nxtclassification previoussc" id="scprevmstn" style="float: left;" />
											
											
											<button type="button" class="btn col-md-2 rightBtn" onclick="scdownloadLaunchmstn()">
												<span class="glyphicon glyphicon-arrow-down" style="color: yellow"></span> Download
											</button>
											<!-- <input type="button" value="Next" class="btn btn-secondary nxtclassification" id="scmstnNext" style="float: right;" /> -->
										</div>
							</div>
							</div>
							<form id="launchmstnfileupload" class="form-horizontal" action="#" modelAttribute="LAUNCHFileUploadBean" enctype="multipart/form-data" name="launchfileupload">
										<div class="launchupload-parent">
											<div class="col-md-12 col-sm-12 ddd">
												<div style="text-align: center; color: #878787;">
				
													<h2 class="SEGOEUIL-font">Upload MSTN</h2>
													<div class="upload-image">
														<img src="assets/images/upload-icon-n-body.png" alt="" />
													</div>
				
													<div class="upload-max-size">Maximum Upload File Size:2MB</div>
													<span id="uploadErrorMsg" style="display: none; color: red">Please upload .xls or .xlsx file</span>
													<div class="input-group upload-file">
														<input id="uploadmstn" name="file" type="file" class="file">
													</div>
													<button class="validate_upload btn marginT10 btn-primary" id="btnUploadmstn">Upload MSTN</button>
				
												</div>
											</div>

										</div>

					</form>
				</div>
					
					<div class="card fifthCard tab_content" data-blockid="5" id="step-5">
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

									<div class="alert alert-danger marginT15" id="scuploaddErrorMsg"	style="display: none;">
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

												<form action="#" method="POST" enctype="multipart/form-data" id="scannexFileUpload" class="form-horizontal" name="scannexFileUpload">

													<div class="form-group row">
														<div class="input-group upload-file">
														
															<label class="col-sm-2 col-md-3 control-label subLabel">Annexure</label>
															<div class="col-sm-10 col-md-6 subfileDiv">
															<span id="uploadannErrorMsg" style="display: none; color: red">Please upload .xls or .xlsx file</span>
																<input id="coeannexfile" name="file" type="file" class="file" size="400">
															</div>
															<div class="col-sm-10 col-md-1 subUpload">
																<input class="validate_upload btn marginT10 btn-primary" id="scAnnexureUploadBtn" type="button" value="Upload" onclick="scuploadAnnexureDoc('0')"></input>
															</div>
														</div>

													</div>
													</form>
													
													<form action="#" method="POST" enctype="multipart/form-data" id="scartworkFileUpload"	class="form-horizontal" name="scartworkFileUpload">
													<div class="form-group row">
														<div class="input-group upload-file">
														
															<label class="col-sm-2 col-md-3 control-label subLabel">Artwork packshots</label>
															<div class="col-sm-10 col-md-6 subfileDiv">
															<span id="coeuploadartErrorMsg" style="display: none; color: red">Please upload .xls or .xlsx file</span>
																<input id="artfile" name="file" type="file" class="file" size="400">
															</div>
															<div class="col-sm-10 col-md-1 subUpload">
																<input class="validate_upload btn marginT10 btn-primary" id="scartworkUploadBtn" onclick="scuploadArtDoc('0')"  type="button" value="Upload"></input>
															</div>
														</div>

													</div>
													</form>
													
													<form action="#" method="POST" enctype="multipart/form-data" id="scmdgFileUpload"	class="form-horizontal" name="scFileUploadBean">
													<div class="form-group row">
														<div class="input-group upload-file">
														
															<label class="col-sm-2 col-md-3 control-label subLabel">MDG deck</label>
															<div class="col-sm-10 col-md-6 subfileDiv">
															<span id="uploadmdgErrorMsg" style="display: none; color: red">Please upload .xls or .xlsx file</span>
																<input id="mdgfile" name="file" type="file" class="file col-md-8" size="40">
															</div>
															<div class="col-sm-10 col-md-1 subUpload">
																<input class="validate_upload btn marginT10 btn-primary" id="scUploadBtn" type="button" value="Upload" onclick="scuploadmdgDoc('0')"></input>
															</div>
														</div>

													</div>
												</form>
												
											</div>
										</div>
									</div>
									<div class="btnclass">
											<input type="button" value="Previous" class="btn btn-secondary nxtclassification previoussc" id="scprevdoc" style="float: left;" />
											<!-- <input type="button" onclick="scDocument()" value="Next" class="btn btn-secondary nxtclassification" id="scdocNext" style="float: right;" /> -->
								 	</div>
								
								</div>
							</div>
					</div>
				</div>
				
					</div>
		</form>
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
	<script type="text/javascript" src="assets/js/custom/launch/scLaunch.js"></script>
	<script src="assets/js/jquery-ui.js"></script>
	<script type="text/javascript" src="assets/js/custom/proco/alert-modal.js"></script>
	<!--  <script type="text/javascript" src="assets/js/jquery.smartWizard.js"></script>  -->
	<script type="text/javascript" src="assets/js/jquery.smartWizard.min.js"></script>
	<script src="assets/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/dataTables.fixedColumns.min.js"></script>
	<script src="assets/js/dataTables.bootstrap.min.js"></script>       
	
</body>
</html>
