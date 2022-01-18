<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="../launchplan/lauchheader.jsp"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<script>var launch_edit_initial_load = true;var arrstrfrmt = '';var arrcuststrfrmt = '';var strfrmt = '';var custstrfrmt=''; var edtscn = true;</script>
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
<link rel="stylesheet"	href="assets/css/smart_wizard_theme_arrows.css" type="text/css" />

<link rel="stylesheet" href="assets/css/smart_wizard.css" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="assets/css/fixedColumns.bootstrap.min.css">


</head>

<c:if test="${AllEditData != ''}">
	<body class="OpenSans-font" onload="setData()">
</c:if>
<c:if test="${AllEditData == ''}">
	<body class="OpenSans-font" onload="newLaunch()">
</c:if>

<div class="loader">
	<center>
		<img class="loading-image" src="assets/images/spinner.gif"
			alt="loading..." style="height: 150px; width: auto;">
	</center>
</div>
<input type="hidden" value="" id="dynamicLaunchId">
<input type="hidden" value="" id="isEdit">
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
							class="active col-md-3 col-sm-8 col-xs-12 launch-icon-active">
							<a href="https://vat.hulcd.com/VisibilityAssetTracker/getLaunchPlanPage.htm"> <!-- <div class="launch-icon"></div> -->
								<div class="tab-label-launch">Launch Plannning</div>
						</a>
						</li>
						<li role="presentation"
							class="active col-md-3 col-sm-8 col-xs-12 launch-icon-inactive">
							<a href="https://vat.hulcd.com/VisibilityAssetTracker/getAllLaunchData.htm"> <!-- <div class="launch-icon"></div> -->
								<div class="tab-label-launch">Edit & Approve <span id="NotificationBadge" class="notification-number"></span></div>
						</a>
						</li>
						<li role="presentation"
							class="active col-md-3 col-sm-8 col-xs-12 launch-icon-inactive">
							<a href="https://vat.hulcd.com/VisibilityAssetTracker/getLaunchPlanPage.htm"> <!-- <div class="launch-icon"></div> -->
								<div class="tab-label-launch">Performance</div>
						</a>
						</li>
						<li role="presentation"
							class="active col-md-3 col-sm-8 col-xs-12 launch-icon-inactive">
							<a href="https://vat.hulcd.com/VisibilityAssetTracker/getLaunchPlanPage.htm"> <!-- <div class="launch-icon"></div> -->
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
	<!--bharati added code for sucessblock and error block for sprint-6 US-5-->
	                 
                                    <div class="alert alert-danger marginT15" style="display: none;" id="sellinerrorblockUpload">
                                        <button type="button" class="close" data-hide="alert">&times;</button>
                                       
                                            <span>File contains error...</span>
                                            <a href="#" onClick="downloadLaunchSellInErrorTemplate();" id="downloadTempFileLink">Click here to Download Error File</a>
                                    </div>
									
									<!--bharati changes end here-->
	
	                                <!--bharati added code for errorblock with download error file for sprint-7 US-7-->
	                 
                                    <div class="alert alert-danger marginT15" style="display: none;" id="launchStoreerrorblockUpload">
                                        <button type="button" class="close" data-hide="alert">&times;</button>
                                       
                                            <span>File contains error...</span>
                                            <a href="#" onClick="downloadLaunchStoreErrorTemplate();" id="downloadTempFileLink">Click here to Download Error File</a>
                                    </div>
									<div class="alert alert-danger marginT15" style="display: none;" id="launchStoreErrorFileForStoreFormat">
                                        <button type="button" class="close" data-hide="alert">&times;</button>
                                       
                                            <span>File contains error...</span>
                                            <a href="#" onClick="downloadLaunchStoreErrorTemplateForStoreFormat();" id="downloadTempFileLink">Click here to Download Error File</a>
                                    </div>
									<div class="alert alert-danger marginT15" style="display: none;" id="launchvalidateZero">
                                        <button type="button" class="close" data-hide="alert">&times;</button>
                                         <span></span>
                                    </div>
									<!--bharati changes end here-->
	<%-- </c:if> --%>
	<%-- <c:if test="${errorMsg!=null}">
			<div class="alert alert-danger sucess-msg" id="errorblock">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${errorMsg}"></c:out>
			</div>
		</c:if> --%>
	<%-- <c:if test="${errorMsg!=null}">
			<div class="alert alert-danger  sucess-msg" id="errorblock">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${errorMsg}"></c:out>
				<a href="#" id="downloadTempFileLink"
					onclick="javascript:downloadCoeErrorFile();">Click here to	Download Error File:</a>
			</div>
		</c:if> --%>

	<div class="alert alert-danger marginT15" id="errorblockUpload">
		<button type="button" class="close" data-hide="alert">&times;</button>
		<%-- <c:out value="${errorMsgUpload}"></c:out> --%>
		<span>Error while uploading file.</span>
		<!--  <a href="downloadTmeUploadErrorFile.htm" id="downloadTempFileLink">Click here to Download Error File</a> -->
	</div>
	

	<form id="launchPlanForm" action="#" method="POST"
		enctype="multipart/form-data">
		<input type="hidden" name="reasonText" id="reasonText" value="">
		<input type="hidden" name="remarkText" id="remarkText" value="">


		<!-- SmartWizard html -->
		<div id="smartwizard">
			<ul class="tmeLaunchUi">
				<li><a href="#step-1" data-tabid="1" title="Launch Details"
					id="launchDetailsTab">Launch Details<br /></a></li>
				<li><a href="#step-2" data-tabid="2" title="Basepacks"
					id="launchBasepackTab">Basepacks<br /></a></li>
				<li><a href="#step-3" data-tabid="3" title="Launch Stores"
					id="launchStoresTab">Launch Stores<br /></a></li>
				<li><a href="#step-4" data-tabid="4" title="Sell in"
					id="launchSellInTab">Sell in<br /></a></li>
				<li><a href="#step-5" data-tabid="5" title="Visi Planning"
					id="launchVisiTab">Visi Planning<br /></a></li>
				<li><a href="#step-6" data-tabid="6" title="Final Build Up"
					id="launchFinBuiUpTab">Final Build Up<br /></a></li>
				<li><a href="#step-7" data-tabid="7" title="Submit"
					id="launchSubTab">Submit<br /></a></li>
			</ul>


			<span style="clear: both; display: block;">&nbsp;</span>

			<div id="accordion" class="tab_container" role="tablist"
				aria-multiselectable="true">

				<div id="step-1" class="tab_content" data-blockid="1">
					<div class="card-header" role="tab" id="headingOne">
						<div class="mb-0">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapseOne" aria-expanded="false"
								aria-controls="collapseOne" class="collapsed"> <span>LAUNCH
									DETAILS</span>
							</a>
						</div>
					</div>

					<div id="collapseOne" class="collapse" role="tabpanel"
						aria-labelledby="headingOne" aria-expanded="false"
						style="height: 0px; display: block;">
						<div class="card-block">
							<form action="https://vat.hulcd.com/VisibilityAssetTracker/saveLaunchDetails.htm" method="POST"
								enctype="multipart/form-data" id="tmeLaunchBean"
								class="form-horizontal" name="tmeLaunchBean"
								onClick="javascript: submitLaunchForm();">
								<div class="table-responsive">
									<div class="container-fluid">

										<div class="row launch_row">
											<div class="col-md-6">
												<label>Launch Name</label>
											</div>
											<div class="col-md-2">
												<input id="launch_name" name="launch_name" type="text"
													class="form-control basepack-child"
													placeholder="Please enter name"
													data-error-msg="This field is required" maxlength="255"><span
													id="errorname"></span>
											</div>
										</div>

										<div class="row launch_row">
											<div class="col-md-6">
												<label>Launch MOC</label>
											</div>
											<div class="col-md-2">
												<select id="mocMonth" name="mocMonth" readonly="true"
													class="form-control"
													style="background-color: #fff !important;"
													onchange='getDateFromMoc(this.value);'>
												</select> <input type="hidden" id="startdate" name="startdate"
													class="form-control" />
											</div>
											<div class="col-md-2"></div>
										</div>

										<div class="row launch_row">
											<div class="col-md-6">
												<label>Nature of Launch</label>
											</div>
											<div class="col-md-2">
												<select id="launch_nature" class="form-control"
													onchange="getlaunchNatureDetails(this);">

												</select>
											</div>
											<div class="col-md-2 geodiv">
												<input type="text" class="form-control" id="editGeography"
													path="geography" />

											</div>
											<div class="col-md-2 custchain">
												<form:select class="form-control" id="customerChainL1"
													multiple="multiple" path="customerChainL1">
													<c:forEach var="item" items="${customerChainL1}">
														<option value="${item}">${item}</option>
													</c:forEach>
												</form:select>
											</div>
											<div class="col-md-2 inputdiv">
												<input id="launch_input" name="launch_input" type="text"
													class="form-control" value="">
											</div>
											<div class="col-md-2 formatdiv">
												<!-- <input id="format_input" name="format_input" type="text" class="form-control" value=""> -->
												<select id="format_input" class="form-control">
													<option value="">Select Format</option>
													<c:forEach items="${storeFormat}" var="store">

														<option value="${store}">${store}</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-md-2 towndiv">
												<!-- <input id="town_input" name="format_input" type="text" class="form-control" value=""> -->
												<select id="town_input" class="form-control">
													<option value="">Select Town</option>
													<c:forEach items="${townSpcific}" var="town">

														<option value="${town}">${town}</option>
													</c:forEach>
												</select>


											</div>
										</div>
										<div class="row launch_row">
											<div class="col-md-6">
												<label>Estimated launch business case (Numeric only)
												</label>
											</div>
											<div class="col-md-2">
												<input id="launch_business" name="launch_business"
													type="text" class="form-control basepack-child"
													onkeypress="return validateFloatKeyPress(this,event);">
											</div>
										</div>
										<div class="row launch_row">
											<div class="col-md-6">
												<label>Launch Category (Numeric only)</label>
											</div>
											<div class="col-md-2">
												<input id="launch_category" name="launch_category"
													type="text" class="form-control basepack-child"
													onkeypress="return validateFloatKeyPress(this,event);">
											</div>
										</div>

										<div class="row launch_row">
											<div class="col-md-6">
												<label>Launch Classification</label>
											</div>
											<div class="col-md-2">
												<input id="launch_classifiction" name="launch_base3"
													type="text" class="form-control basepack-child"
													style="color: #b1a23e;" readonly>
											</div>
										</div>

										<!-- <input type="button" value="check" id="chck" class="btn btn-success"/> -->

									</div>
								</div>

								<div class="prevnext" style="width: 65.75%;">

									<input type="button" value="Next"
										class="btn btn-secondary nxtclassification"
										onclick="saveLaunchDetails();" id="lnchdetail"
										style="float: right;" />
								</div>
							</form>
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
								id="parentCollapse"> <span>LAUNCH BASEPACK</span>
							</a>
						</div>
					</div>
					<div id="collapseTwo" class="collapse" role="tabpanel"
						aria-labelledby="headingTwo" aria-expanded="false"
						style="display: block;">
						<div class="card-block">
							<div class="child-table">
								<div class="table-responsive">
									<table class="table" id="basepack_add">
										<thead class="thead-dark">
											<tr>
												<th class="scr2tr"><span
													class="proco-btn proco-btn-success table-head">Select</span></th>
												<th class="scr2tr"><span
													class="proco-btn proco-btn-success table-head">Sales
														Category</span></th>
												<th class="scr2tr"><span
													class="proco-btn proco-btn-success table-head">PSA
														Category</span></th>
												<th class="scr2tr"><span
													class="proco-btn proco-btn-success table-head">Brand</span></th>
												<th class="scr2tr"><span
													class="proco-btn proco-btn-success table-head">Basepack
														Code</span></th>
												<th class="scr2tr"><span
													class="proco-btn proco-btn-success table-head">Basepack
														Description</span></th>
												<th class="scr2tr"><span
													class="proco-btn proco-btn-success table-head">MRP</span></th>
												<th class="scr2tr"><span
													class="proco-btn proco-btn-success table-head">TUR</span></th>
												<th class="scr2tr"><span
													class="proco-btn proco-btn-success table-head">GSV</span></th>
												<th class="scr2tr"><span
													class="proco-btn proco-btn-success table-head">CLD
														Configuration</span></th>
												<th class="scr2tr"><span
													class="proco-btn proco-btn-success table-head">Grammage</span></th>
												<th class="scr2tr"><span
													class="proco-btn proco-btn-success table-head">Classification</span></th>
											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
									<div class="btnclass">
										<button type="button" class="btn col-md-1 leftBtn"
											id="addbasepack">
											<span class="glyphicon glyphicon-plus" style="color: #ffffff;"></span>
											Add Basepack
										</button>
										<button type="button" class="btn col-md-1 leftBtn"
											id="delbasepack">
											<span class="glyphicon glyphicon-minus" style="color: #ffffff;"></span>
											Remove Basepack
										</button>

										<!--   <button type="button" class="btn col-md-2 rightBtn"><span class="glyphicon glyphicon-arrow-down" style="color:yellow"></span>  Download</button> -->
										<form id="downloadbasepck" method="post">
											<button type="button" class="btn col-md-3 rightBtn"
												onClick="javascript: downloadLaunchBasepackTemplate();">
												<span class="glyphicon glyphicon-arrow-down"
													style="color: #ffffff;"></span>Download launch pack template
											</button>
										</form>
										<input type="button" value="Previous"
											class="btn btn-secondary nxtclassification previousTme"
											id="prevbspack" style="float: left;" /> <input type="button"
											onclick="saveBasepacks()" value="Next"
											class="btn btn-secondary nxtclassification" id="bspack"
											style="float: right;" />
									</div>
									<!-- <div class="btnclass">
											<a href="#" class="btn btn-secondary sw-btn-prev disabled prev" disabled> Previous</a>
											 <a href="#" class="btn btn-secondary sw-btn-next">Next</a>
											
										</div> -->

								</div>

							</div>
						</div>
					</div>
					<form id="launchfileupload" class="form-horizontal" action="#"
						modelAttribute="LAUNCHFileUploadBean"
						enctype="multipart/form-data" name="launchfileupload">
						<div class="launchupload-parent">
							<div class="col-md-12 col-sm-12 ddd">
								<div style="text-align: center; color: #000000;">

									<h2 class="SEGOEUIL-font">Upload Base Packs</h2>
									<div class="upload-image">
										<img src="assets/images/upload-icon-n-body.png" alt="" />
									</div>

									<div class="upload-max-size">Maximum Upload File Size
										:2MB</div>
									<span id="uploadErrorMsg" style="display: none; color: red">Please
										upload .xls or .xlsx file</span>
									<div class="input-group upload-file">
										<input id="uploadsecscre" name="file" type="file" class="file">
									</div>
									<button class="validate_upload btn marginT10 new-btn-primary"
										id="btnSubmitBasePack">Upload Base Packs</button>

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
								aria-controls="collapseOne" class="collapsed"> <span>LAUNCH
									CLUSTER AND ACCOUNT</span>
							</a>
						</div>
					</div>
					<div id="collapseOne" class="collapse" role="tabpanel"
						aria-labelledby="headingOne" aria-expanded="false"
						style="height: 0px; display: block;">
						<div class="card-block">
							<div class="col-md-12">
								<div class="OpenSans-font">
									<div class="form-group col-md-6">
										<label class="col-sm-4 control-label">Select Cluster:<span
											class="astrick">*</span></label>
										<div class="col-sm-8 offset-col-md-2 switch-dynamic-first">

											<!-- <form:input type="text" class="form-control" value="ALL INDIA" id="editGeography" path="geography" />  -->
											<input type="text" class="form-control" id="cluster"
												path="geography" /> <label><input type="checkbox"
												id="custmsel" />Custom selection for stores</label>

											<!-- <input type="text" class="form-control assetDesc" value="" id="cluster" name="assetDesc" placeholder="Select Cluster"> -->
										</div>
									</div>
									<div class="form-group col-md-6">
										<label class="col-sm-4 control-label">Select Account:<span
											class="astrick">*</span></label>
										<div class="col-sm-8 offset-col-md-2 switch-dynamic-first">
											<input type="text" class="form-control"
												id="customerChainL1Thrscn" path="customerChainL1Thrscn" />
										</div>
									</div>

									<div class="form-group col-md-12 optiondiv">
										<div class="form-group col-md-6 radio1">

											<label class="col-sm-4 control-label"><input
												type="radio" id="radio1" name="strfrmt" class="radioln"
												value="Select Store Format">Select Store Format:<span
												class="astrick">*</span></label>
											<div
												class="col-sm-8 offset-col-md-2 switch-dynamic-first-custstrfrmt">
												<!-- <form:select value="" method="POST" class="form-control strclass" id="custstrfrmt" multiple="multiple" name="assetDesc" path="getLaunchStores" disabled>
													
												</form:select>  -->
												<form:input type="text" class="form-control"
													id="cust-chain-store" multiple="multiple"
													value="SELECT STORE FORMAT" readonly="true" disabled />
											</div>
											<!-- <div class="col-md-8 switch-dynamic-first-radio-cst">
												
											</div> -->

										</div>
										<div class="form-group col-md-6 radio2">
											<label class="col-sm-4 control-label"><input
												type="radio" id="radio2" name="strfrmt" class="radioln"
												value="Select Customer Store Format"> Select
												Customer Store Format:<span class="astrick">*</span></label>
											<div
												class="col-sm-8 offset-col-md-2 switch-dynamic-first-radio-cststr">
												<!-- <form:select value="" class="form-control strclass" id="selcustfrmt" multiple="multiple" name="assetDesc" placeholder="Select Account" path="selcustfrmt" disabled>
												</form:select> -->
												<form:input type="text" class="form-control"
													id="cust-chain-cst-store" multiple="multiple"
													value="SELECT CUSTOMER STORE FORMAT" readonly="true" />
											</div>

										</div>
									</div>
									<div class="form-group col-md-6">
										<label class="col-sm-4 control-label">Total number of
											Stores selected for launch</label>
										<div class="col-sm-8 offset-col-md-2 switch-dynamic-first">
											<input type="text" class="form-control assetDesc" value="0"
												id="storecount" name="assetDesc" disabled>
										</div>
									</div>
									
								<!--bharati added for sprint-7 US-7 -->
									<div class="form-group col-md-6">
										<label class="col-sm-4 control-label">Please upload minimum target stores for the launch<span
											class="astrick">*</span></label>
										<div class="col-sm-8 offset-col-md-2 switch-dynamic-first">
											<input type="text" class="form-control assetDesc" value="0"
												id="tmeapprovedstorecount" name="assetDesc" disabled>
										</div>
									</div>
									<div class="form-group col-md-6 col-md-offset-6" style="margin-bottom: 3px">
									<label class="col-sm-12 control-label" style="font-size: 11px;color: #e41604;">To enter minimum target store- Download any one of the files below , enter value in Column G and upload</label>
									</div>
									
									<div class="form-group col-md-12">

										<button type="button" class="btn col-md-3 rightBtn"
											onClick="downloadLaunchClusterTemplate();">
											<span class="glyphicon glyphicon-arrow-down"
												style="color: #ffffff;"></span>Download Customer Store
											Template
										</button>
										<button type="button" class="btn col-md-3 rightBtn"
											onClick="downloadLaunchStrClusterTemplate();">
											<span class="glyphicon glyphicon-arrow-down"
												style="color: #ffffff;"></span>Download Store Format Template
										</button>
										<!-- <a href="#" class="btn btn-secondary sw-btn-prev disabled prev" disabled>Previous</a> -->
										<input type="button" value="Previous"
											class="btn btn-secondary nxtclassification previousTme"
											id="prevClust" style="float: left;" /> <input type="button"
											onclick="saveLunchstrs()" value="Next"
											class="btn btn-secondary nxtclassification launchStoreNext" id="lnchstr"
											style="float: right;" disabled />

									</div>
								</div>

							</div>

						</div>

						<!-- <div class="btnclass">
									</div> -->
					</div>
					<div class="col-md-6 col-sm-6">
						<form id="launchstrfileupload" class="form-horizontal" action="#"
							enctype="multipart/form-data" name="launchstrfileupload">
							<div
								class="launchupload-parent basepcupload-parent col-md-12 col-sm-12">
								<div class="col-md-12 col-sm-12 ddd">
									<div style="text-align: center; color: #000000;">

										<h2 class="SEGOEUIL-font">Upload Store format</h2>
										<div class="upload-image">
											<img src="assets/images/upload-icon-n-body.png" alt="" />
										</div>

										<div class="upload-max-size">Maximum Upload File
											Size:2MB</div>
										<span id="uploadclsErrorMsg" style="display: none; color: red">Please
											upload .xls or .xlsx file</span>
										<div class="input-group upload-file">
											<input id="uploadcls" name="file" type="file" class="file">
										</div>
										<button class="validate_upload btn marginT10 new-btn-primary"
											id="launchstrFileUploadBtn">Upload Store format</button>

									</div>
								</div>

							</div>

						</form>

					</div>
					<div class="col-md-6 col-sm-6">
						<form id="launchCuststrfileupload" class="form-horizontal"
							action="#" enctype="multipart/form-data"
							name="launchCuststrfileupload">
							<div
								class="launchupload-parent basepcupload-parent col-md-12 col-sm-12">
								<div class="col-md-12 col-sm-12 ddd">
									<div style="text-align: center; color: #000000;">

										<h2 class="SEGOEUIL-font">Upload Customer Store format</h2>
										<div class="upload-image">
											<img src="assets/images/upload-icon-n-body.png" alt="" />
										</div>

										<div class="upload-max-size">Maximum Upload File
											Size:2MB</div>
										<span id="uploadcustclsErrorMsg"
											style="display: none; color: red">Please upload .xls
											or .xlsx file</span>
										<div class="input-group upload-file">
											<input id="uploadclsCust" name="file" type="file"
												class="file">
										</div>
										<button class="validate_upload btn marginT10 new-btn-primary"
											id="launchCuststrFileUploadBtn">Upload Customer
											Store format</button>

									</div>
								</div>

							</div>

						</form>

					</div>
				</div>
				<div class="card thirdCard tab_content" data-blockid="4" id="step-4">
					<div class="card-header" role="tab" id="headingOne">
						<div class="">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapseOne" aria-expanded="false"
								aria-controls="collapseOne" class="collapsed"> <span>LAUNCH
									BUILDUP</span>
							</a>
						</div>
					</div>
					<div id="collapseOne" class="collapse" role="tabpanel"
						aria-labelledby="headingOne" aria-expanded="false"
						style="height: 0px; display: block;">
						<div class="card-block">
							<div class="child-table">
								<div>

									<table id="sellinTable"
										class="table table-striped table-bordered nowrap order-column row-border smt-dashboardTable table-responsive"
										style="width: 100%">
										<thead class="thead-dark">
											<tr>
												<th>L1 Chain</th>
												<th>L2 Chain</th>
												<th>Store Format</th>
												<th>Store Planned</th>
												<th class="basepackdesc-1">Basepack Description 1
													sell-in</th>
												<th class="basepackdesc-2">Basepack Description 2
													sell-in</th>
												<th class="basepackdesc-3">Basepack Description 3
													sell-in</th>
												<th class="basepackdesc-4">Basepack Description 4
													sell-in</th>
												<th class="basepackdesc-5">Basepack Description 5
													sell-in</th>
												<th class="basepackdesc-6">Basepack Description 6
													sell-in</th>
												<th class="basepackdesc-7">Basepack Description 7
													sell-in</th>
												<th class="basepackdesc-8">Basepack Description 8
													sell-in</th>
												<th class="basepackdesc-9">Basepack Description 9
													sell-in</th>
												<th class="basepackdesc-10">Basepack Description 10
													sell-in</th>
												<th class="basepackdesc-11">Basepack Description 11
													sell-in</th>
												<th class="basepackdesc-12">Basepack Description 12
													sell-in</th>
												<th class="basepackdesc-13">Basepack Description 13
													sell-in</th>
												<th class="basepackdesc-14">Basepack Description 14
													sell-in</th>
												<th>Rotations</th>
												<th>Uplift n+1</th>
												<th>Uplift n+2</th>

											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>

								</div>

							</div>
						</div>

						<div>
							<button type="button" class="btn col-md-2 rightBtn"
								onclick="downloadLaunchSellInTemplate()">
								<span class="glyphicon glyphicon-arrow-down"
									style="color: #ffffff"></span> Download sell-in template
							</button>
							<!-- <a href="#" class="btn btn-secondary sw-btn-prev disabled prev">Previous</a> -->
							<input type="button" value="Previous"
								class="btn btn-secondary nxtclassification previousTme"
								id="prevsellIn" style="float: left;" /> <input type="button"
								onclick="saveSellinData()" value="Next"
								class="btn btn-secondary nxtclassification" id="sellinNext"
								style="float: right;" />
						</div>
						<!-- 	<div class="btnclass">
									
									
						 </div> -->
						<form id="launchsellinfileupload" class="form-horizontal"
							action="#" modelAttribute="LAUNCHFileUploadBean"
							enctype="multipart/form-data" name="launchstrfileupload">
							<div class="launchupload-parent strupload-parent">
								<div class="col-md-12 col-sm-12 ddd">
									<div style="text-align: center; color: #000000;">

										<h2 class="SEGOEUIL-font">Upload Sell Ins</h2>
										<div class="upload-image">
											<img src="assets/images/upload-icon-n-body.png" alt="" />
										</div>

										<div class="upload-max-size">Maximum Upload File
											Size:2MB / SKU, Rotation and Uplift are mandatory to be filled</div>
											
										<span id="uploadSellInErrorMsg"
											style="display: none; color: red">Please upload .xls
											or .xlsx file</span>
										<div class="input-group upload-file">
											<input id="uploadSellIn" name="file" type="file" class="file">
										</div>
										<button class="validate_upload btn marginT10 new-btn-primary"
											id="launchsellinFileUploadBtn">Upload Sell Ins</button>

									</div>
								</div>

							</div>

						</form>
					</div>
				</div>

				<div class="card forthCard tab_content" data-blockid="5" id="step-5">
					<div class="card-header" role="tab" id="headingOne">
						<div class="">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapseOne" aria-expanded="false"
								aria-controls="collapseOne" class="collapsed"> <span>LAUNCH
									VISI PLANNING</span>
							</a>
						</div>
					</div>
					<div id="collapseOne" class="collapse" role="tabpanel"
						aria-labelledby="headingOne" aria-expanded="false"
						style="height: 0px; display: block;">
						<div class="card-block">
							<div class="child-table">
								<div>
									<table id="visiplan"
										class="table table-striped table-bordered nowrap order-column row-border smt-dashboardTable table-responsive"
										style="width: 100%">
										<thead class="thead-dark">
											<tr>
												<th><span
													class="proco-btn proco-btn-success table-head">Select</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Account</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Format</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Stores
														Available</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Stores
														planned</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Visi
														asset 1</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Facings
														per shelf per SKU</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Depth
														per shelf per SKU</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Visi
														asset 2</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Facings
														per shelf per SKU</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Depth
														per shelf per SKU</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Visi
														asset 3</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Facings
														per shelf per SKU</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Depth
														per shelf per SKU</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Visi
														asset 4</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Facings
														per shelf per SKU</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Depth
														per shelf per SKU</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Visi
														asset 5</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Facings
														per shelf per SKU</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Depth
														per shelf per SKU</span></th>
											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
								</div>
							</div>
						</div>

						<!-- <div class="btnclass">
											<a href="#" class="btn btn-secondary sw-btn-prev disabled prev" disabled>Previous</a>
											
									 	</div> -->
						<div>

							<button type="button" class="btn col-md-3 leftBtn"
								id="noVissiPlan">
								<span class="glyphicon glyphicon-remove-sign"
									style="color: #ffffff;"></span>No visi to be planned
							</button>
							<!-- <button type="button" class="btn col-md-2 rightBtn">
												<span class="glyphicon glyphicon-arrow-up" style="color: yellow"></span> UPLOAD
											</button> -->

							<button type="button" class="btn col-md-2 rightBtn"
								onclick="downloadLaunchVisiPlanTemplate()">
								<span class="glyphicon glyphicon-arrow-down"
									style="color: #ffffff"></span> Download
							</button>
							<input type="button" value="Previous"
								class="btn btn-secondary nxtclassification previousTme"
								id="prevvissi" style="float: left;" /> <input type="button"
								onclick="saveVisiPlan()" value="Next"
								class="btn btn-secondary nxtclassification" id="visiPlanNext"
								style="float: right;" />
						</div>
					</div>
					<!-- </div>
								</div> -->

					<div class="col-md-12 col-sm-12">
						<form id="launchVisiPlanUpload" class="form-horizontal" action="#"
							modelAttribute="LAUNCHFileUploadBean"
							enctype="multipart/form-data" name="launchVisifileupload">
							<div class="launchupload-parent visiupload-parent">
								<div class="col-md-12 col-sm-12 ddd">
									<div style="text-align: center; color: #000000;">

										<h2 class="SEGOEUIL-font">Upload Visi Plans</h2>
										<div class="upload-image">
											<img src="assets/images/upload-icon-n-body.png" alt="" />
										</div>
										<div class="upload-max-size">Maximum Upload File
											Size:2MB</div>
										<span id="uploadVisiPlanErrorMsg"
											style="display: none; color: red">Please upload .xls
											or .xlsx file</span>
										<div class="input-group upload-file">
											<input id="uploadVisiPlan" name="file" type="file"
												class="file">
										</div>
										<button class="validate_upload btn marginT10 new-btn-primary"
											id="launchVisiPlanFileUploadBtn">Upload Visi Plan</button>

									</div>
								</div>

							</div>

						</form>
					</div>
				</div>

				<div class="card fifthCard tab_content" data-blockid="6" id="step-6">
					<div class="card-header" role="tab" id="headingOne">
						<div class="">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapseOne" aria-expanded="false"
								aria-controls="collapseOne" class="collapsed"> <span>FINAL
									PLANNING</span>
							</a>
						</div>
					</div>
					<div id="collapseOne" class="collapse" role="tabpanel"
						aria-labelledby="headingOne" aria-expanded="false"
						style="height: 0px; display: block;">
						<div class="card-block">
							<div class="child-table">
								<div class="tme_final">
									<table class="table" id="finalTable">
										<thead class="thead-dark">
											<tr>

												<th><span
													class="proco-btn proco-btn-success table-head">SKU
														name</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Basepack
														Code</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Launch
														Sell-in value</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">N+1
														sell-in value</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">N+2
														sell-in value</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Launch
														Sell-in CLDs</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">N+1
														Sell-in CLDs</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">N+2
														Sell-in CLDs</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">Launch
														Sell-in units</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">N+1
														sell-in Sell-in units</span></th>
												<th><span
													class="proco-btn proco-btn-success table-head">N+2
														sell-in Sell-in units</span></th>
											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
									<!-- <div>
											
										</div> -->
									<div class="btnclass">
										<!-- <a href="#" class="btn btn-secondary sw-btn-prev disabled prev" disabled>Previous</a> -->
										<button type="button" class="btn col-md-2 rightBtn"
											onclick="downloadFinalPlan()">
											<span class="glyphicon glyphicon-arrow-down"
												style="color: #ffffff"></span> Download Launch Plan
										</button>
										<input type="button" value="Previous"
											class="btn btn-secondary nxtclassification previousTme"
											id="prevfinal" style="float: left;" /> <input type="button"
											onclick="saveFinalBuildUpData()" value="Next"
											class="btn btn-secondary nxtclassification" id="finalnext"
											style="float: right;" />
									</div>

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
								aria-controls="collapseOne" class="collapsed"> <span>SUBMIT</span>
							</a>
						</div>
					</div>
					<div id="collapseOne" class="collapse" role="tabpanel"
						aria-labelledby="headingOne" aria-expanded="false"
						style="height: 0px; display: block;">
						<div class="card-block">
							<div class="child-table">

								<div class="alert alert-danger marginT15" id="uploaddErrorMsg"
									style="display: none;">
									<button type="button" class="close" data-hide="alert">&times;</button>
									<span>Please upload .xls or .xlsx file</span>
								</div>

								<div class="upload-parent">
									<div class="col-md-12 ddd">
										<div class="download-block">
											<div class="alert alert-success sucess-msg"
												id="successblockUpload">
												<button type="button" class="close" data-dismiss="alert">&times;</button>
												<%-- <c:out value="${success}"></c:out> --%>
												<span></span>
											</div>
											
											<!---bharati added this error block for annexeerrorblock in sprint-7 dec-21 file size exceeded error-->
	                                           <div class="alert alert-danger marginT15" id="annexerrorblockUpload" style="display:none">
		                                       <button type="button" class="close" data-hide="alert">&times;</button>
		
		                                       <span> </span>
		
	                                          </div>
	                                         <!--bharati code end here-->

											<form action="#" method="POST" enctype="multipart/form-data"
												id="tmeannexFileUploadBean" class="form-horizontal"
												name="tmeFileUploadBean">

												<div class="form-group row">
													<div class="input-group upload-file">

														<label class="col-sm-2 col-md-3 control-label subLabel">Annexure</label>
														<div class="col-sm-10 col-md-6 subfileDiv">
															<span id="uploadannErrorMsg"style="display: none; color: red">Please upload.xls or .xlsx file</span> 
																<input id="annexfile" name="file" type="file" class="file" size="400">
																<span class="exmp">Please upload file with name (Annexure_launchname)</span>
																<span id="annxr" style="display:none;"></span>
														</div>
														<div class="col-sm-10 col-md-1 subUpload">
															<input class="validate_upload btn marginT10 new-btn-primary"
																id="annexureUploadBtn" type="button" value="Upload"
																onclick="uploadAnnexureDoc('0')"></input>
														</div>
													</div>

												</div>
											</form>

											<form action="#" method="POST" enctype="multipart/form-data"
												id="tmeartworkFileUpload" class="form-horizontal"
												name="tmeFileUploadBean">
												<div class="form-group row">
													<div class="input-group upload-file">

														<label class="col-sm-2 col-md-3 control-label subLabel">Artwork
															packshots</label>
														<div class="col-sm-10 col-md-6 subfileDiv">
															<span id="uploadartErrorMsg"
																style="display: none; color: red">Please upload
																.xls or .xlsx file</span> <input id="artfile" name="file"
																type="file" class="file" size="400" multiple="multiple">
																<span class="exmp">Please upload file with name (basepacID_launchname) eg. 10011_launch</span>
																<span id="baseart" style="display:none;"></span>
														</div>
														<div class="col-sm-10 col-md-1 subUpload">
															<input class="validate_upload btn marginT10 new-btn-primary"
																id="artworkUploadBtn" onclick="uploadArtDoc('0')"
																type="button" value="Upload"></input>
														</div>
													</div>

												</div>
											</form>

											<form action="#" method="POST" enctype="multipart/form-data"
												id="tmemdgFileUpload" class="form-horizontal"
												name="tmeFileUploadBean">
												<div class="form-group row">
													<div class="input-group upload-file">

														<label class="col-sm-2 col-md-3 control-label subLabel">MDG
															deck</label>
														<div class="col-sm-10 col-md-6 subfileDiv">
															<span id="uploadmdgErrorMsg"
																style="display: none; color: red">Please upload
																.xls or .xlsx file</span> <input id="mdgfile" name="file"
																type="file" class="file col-md-8" size="40">
																<span class="exmp">Please upload file with name (MDG_deck_launchname)</span>
																<span id="mdglnc" style="display: none;" ></span>
														</div>
														<div class="col-sm-10 col-md-1 subUpload">
															<input class="validate_upload btn marginT10 new-btn-primary"
																id="mdgUploadBtn" type="button" value="Upload"
																onclick="uploadmdgDoc('0')"></input>
																
														</div>
													</div>

												</div>
											</form>
											<div
												class="custom-control form-group custom-checkbox custom-control-inline col-sm-12 col-md-12 sampleMainDiv">
												<div
													class="custom-control form-group custom-checkbox custom-control-inline col-sm-2 col-md-3 offset-col-md-2 sampleDiv">

													<label class="custom-control-label sample"
														for="defaultInline1">Samples shared</label>
												</div>

												<div class="custom-control checkboxDiv">
													<div
														class="custom-control custom-checkbox custom-control-inline col-sm-2 col-md-1">
														<input type="radio" name="sampleYesorno"
															class="custom-control-input" id="sampleYes" value="1"><label
															class="custom-control-label" for="sampleYes"> Yes</label>
													</div>


													<div
														class="custom-control custom-checkbox custom-control-inline  col-sm-2 col-md-1">
														<input type="radio" name="sampleYesorno"
															class="custom-control-input" id="sampleNo" value="0" checked><label
															class="custom-control-label" for="sampleNo"> No</label>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>

								<div class="btnclass">
									<!-- <button type="button" class="btn col-md-2 rightBtn" onclick="downloadFinalPlanTemplate()">
												<span class="glyphicon glyphicon-arrow-down" style="color: yellow"></span> Download
											</button> -->
								</div>
							</div>

						</div>

						<div class="btnclass">
							<!-- 	<a href="#" class="btn btn-secondary sw-btn-prev disabled prev" disabled>Previous</a> -->
							<input type="button" value="Previous"
								class="btn btn-secondary nxtclassification previousTme"
								id="prevdoc" style="float: left;" /> <input type="button"
								value="Save Launch Plan"
								class="btn btn-secondary nxtclassification"
								onclick="saveLaunchPlanSucess()" id="visiPlanNext"
								style="float: right;" />
						</div>
					</div>
				</div>
			</div>
	</form>

</div>
<%@include file="../launchplan/footer.jsp"%>


<script type="text/javascript" src="assets/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="assets/js/comboTreePlugin.js"></script>
<script type="text/javascript" src="assets/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="assets/js/jquery-ui.js"></script>
<script type="text/javascript" src="assets/js/fileinput.js"></script>
<script type="text/javascript" src="assets/js/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="assets/js/custom/launch/launchscript.js"></script>
<script src="assets/js/jquery-ui.js"></script>
<script type="text/javascript" src="assets/js/custom/proco/alert-modal.js"></script>
<!-- <script type="text/javascript" src="assets/js/jquery.smartWizard.js"></script> -->
<script type="text/javascript" src="assets/js/jquery.smartWizard.min.js"></script>
<script src="assets/js/jquery.dataTables.min.js"></script>
<script src="assets/js/dataTables.fixedColumns.min.js"></script>
<script src="assets/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript">


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


	/*$("#annexfile, #annexureUploadBtn, #artworkUploadBtn, #artfile, #mdgfile, #mdgUploadBtn").attr('disabled', true);*/

	/*$(document).on('change', '[name="sampleYesorno"]', function(){
		if($('[name="sampleYesorno"]:checked').val() == '1'){
			$("#annexfile, #annexureUploadBtn, #artworkUploadBtn, #artfile, #mdgfile, #mdgUploadBtn").removeAttr('disabled');
			$("#tmemdgFileUpload .btn.btn-primary.btn-file").removeAttr('disabled');
			$("#tmeartworkFileUpload .btn.btn-primary.btn-file").removeAttr('disabled');
			$("#tmeannexFileUploadBean .btn.btn-primary.btn-file").removeAttr('disabled');
		} else {
			$("#annexfile, #annexureUploadBtn, #artworkUploadBtn, #artfile, #mdgfile, #mdgUploadBtn").attr('disabled', true);
			$("#tmemdgFileUpload .btn.btn-primary.btn-file").attr('disabled', true);
			$("#tmeartworkFileUpload .btn.btn-primary.btn-file").attr('disabled', true);
			$("#tmeannexFileUploadBean .btn.btn-primary.btn-file").attr('disabled', true);
		}
	});*/
	
  var data = '${geographyJson}';
  var clus = '${geographyJson}';
  var custmr = '${CustomerJson}';
  var bool;
  var edit_visiTable = "",
  edit_oTable = "",
  edit_finaltable = "",
  edit_sellinTable = "";
	history.pushState(null, null, '');
	window.addEventListener('popstate', function(event) {
		history.pushState(null, null, '');
	});

<c:if test="${AllEditData != ''}"> 
	function setData() {
		edtscn = true;
		$("#isEdit").val("true");
		launch_edit_initial_load = true;
		var moc_ds2 = ${moc};
		var allLancunData = ${AllEditData};
		var launchDetails = allLancunData.LAUNCH_DETAILS;
		var launchId = launchDetails.launchId;
		var mocLaunch = launchDetails.launchMoc;
		var launchNam = launchDetails.launchName;
		var launchNtr = launchDetails.launchNature;
		var launchNature2 = launchDetails.launchNature2;
		var launchbusiness = launchDetails.launchBusinessCase;
		var categorySize = launchDetails.categorySize;
		var classification = launchDetails.classification;
		var launchDate = launchDetails.launchDate;
		//var launchName = "<c:out value="${launchNam}"/>";
		$('#launch_name').val(launchNam);
		$('#dynamicLaunchId').val(launchId);
	//	var launchMoc = "<c:out value="${launchMoc}"/>";
		$('#mocMonth').val(mocLaunch);
		$('#startdate').val(launchDate);
	//var nature1 = "<c:out value="${launchNature}"/>";
		$('#launch_nature').val(launchNtr);
		$('#launch_business').val(launchbusiness);
		$('#launch_category').val(categorySize);
		$('#launch_classifiction').val(classification);
		//var nrt2 = "<c:out value="${launchNature2}"/>";
		if(launchNtr == 'Regional'){
			 $('.geodiv').css('display', 'block');
			 $('#editGeography').val(launchNature2);
		}
		else if(launchNtr == 'Customer Specific'){
			 $('.custchain').css('display', 'block');
			 //$('#customerChainL1').val(launchNature2);
			 $('#customerChainL1').multiselect("select",launchNature2);
		}
		else if(launchNtr == 'Format Specific'){
			 $('.formatdiv').css('display', 'block');
			 $('#format_input').val(launchNature2);
		}
		else if(launchNtr == 'Town Specific'){
			$('.towndiv').css('display', 'block');
			 $('#town_input').val(launchNature2);
			
		}

	if(allLancunData.LAUNCH_BASEPACKS != null) {
		$('#launchBasepackTab').click();
		var launchBase = allLancunData.LAUNCH_BASEPACKS;
		var len = launchBase.length;

		$.ajax({
            type: "POST",
            url: "https://vat.hulcd.com/VisibilityAssetTracker/getSalesCatOnBasepack.htm",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            beforeSend: function() {
                ajaxLoader(spinnerWidth, spinnerHeight);
            },
            success: function (salescat) {
            	 $('.loader').hide();
                var saleCat = salescat.responseData.SalesCategory;
                var classfi = salescat.responseData.BpClassification;
                
				optionJsonLaunch = "<option value='Select Sales Category'>Select Sales Category</option>";
                $.each(saleCat, function (i, item) {
                   // console.log(item);
                    // $("#Suppliers").append("<option value='" + item.Id + "'>"
					// + item.SupplierName + "</option>");
                   
						optionJsonLaunch += "<option value='" + item + "'>" + item + "</option>";
				});
                
                optionJsonclassfi =  "<option value='Select Classification'>Select Classification</option>";
                $.each(classfi, function (i, item) {
                	optionJsonclassfi += "<option value='" + item + "'>" + item + "</option>";
				 });
               


                $('#basepack_add tbody').empty();
        		for (var i = 0; i < len; i++) {
        				row = "<tr><td><input type='checkbox' class='checklaunch' name='selectDel' id= 'bsk"+ i								
        						+ "' value='selectDel'></td><td class='xsdrop'><select class='form-control validfield salescat' onchange='salecat(this)' id='salesCat"+i+"'>"
        						+ optionJsonLaunch
        						+ "</select></td><td class='xsdrop'><select class='form-control validfield psacatgr' onchange='brandOnPsacat(this)' id='psaCat"+i+"'>" 
        						+ "</select></td><td class='xsdrop'><select class='form-control validfield brand' id='brand"+i+"'>"
        						+ "</select></td><td><input name='childBasepackDesc1' type='text' class='form-control validfield baseCode' onblur='getBasepackCode(this)' onkeypress='return isNumberKey(event)' placeholder='12345' maxlength='5' id='bpCode"+i+"'></td>"
        						+ "<td><input name='childRatio1' type='text' class='form-control validfield descrip' placeholder='Description' onblur='descValidation(this);' maxlength='255' id='desc"+i+"'></td>"
        						+ "<td class='xsfield'><input name='childRatio1' type='text' class='form-control validfield xsfield mrpIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);' id='mrp"+i+"'></td>"
        						+ "<td class='xsfield'><input name='childRatio1' type='text' class='form-control validfield xsfield turIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);' id='tur"+i+"'></td>"
        						+ "<td class='xsfield'><input name='childRatio1' type='text' class='form-control validfield xsfield gsvIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);' id='gsv"+i+"'></td>"
        						+ "<td><input name='childRatio1' type='text' class='form-control validfield clvIn' placeholder='0000' onkeypress='return isNumberKey(event)' maxlength='4' id='cldcon"+i+"'></td>"
        						+ "<td class='xsfield'><input name='childRatio1' type='text' class='form-control validfield xsfield grmIn' placeholder='0000' onkeypress='return validateFloatKeyPress(this,event);' id='gram"+i+"'></td>"
        						+ "<td><select class='form-control validfield classfDrop' id='classf"+i+"'>" + optionJsonclassfi + 
        						+ "</select></td></tr>";
        				$('#basepack_add tbody').append(row);
        				$('#salesCat'+i).val(launchBase[i].salesCat);

        				setPsaCat(launchBase[i].salesCat,i);
        				function setPsaCat(salesCat,count) {
        					
        					$.ajax({
        			            type: "POST",
        			            url: "https://vat.hulcd.com/VisibilityAssetTracker/getPsaCategory.htm?salesCategory=" +salesCat,
        			            contentType: "application/json; charset=utf-8",
        			            dataType: "json",
        			            beforeSend: function() {
        			                ajaxLoader(spinnerWidth, spinnerHeight);
        			            },
        			            success: function (psacat) {
        			            	 $('.loader').hide();
        			                
        			                var optionJsonpsa =  "<option value='Select PSA Category'>Select PSA Category</option>";
        			                $.each(psacat, function (a, psa) {
        			                   
        			                    optionJsonpsa += "<option value='" + psa.psaCategory + "'>" + psa.psaCategory + "</option>";
        								
        			                });
        			                $("#psaCat"+count).append(optionJsonpsa);
        			                $('#psaCat'+count).val(launchBase[count].psaCat);
        			                setBrand(launchBase[count].psaCat,launchBase[count].brand,count, launchBase[count].salesCat);

        			                function setBrand(psaCat,brandJ,countJ, salesCatVal) {
        			                	var branvalStr = encodeURIComponent(psaCat);
        			                	var salesCatStr = encodeURIComponent(salesCatVal);
                						$.ajax({
                				            type: "POST",
                				            url: "https://vat.hulcd.com/VisibilityAssetTracker/getBrandOnPsaCat.htm?psaCategory="+branvalStr+"&salesCategory="+salesCatStr,
                				            contentType: "application/json; charset=utf-8",
                				            dataType: "json",
                				            beforeSend: function() {
                				                ajaxLoader(spinnerWidth, spinnerHeight);
                				            },
                				            success: function (brand) {
                				            	 $('.loader').hide();
                				               
                				                var optionJsonbrand = "<option value='Select Brand'>Select Brand</option>";
                				               
                				                $.each(brand, function (a, br) {
                				                   optionJsonbrand += "<option value='" + br.brand + "'>" + br.brand + "</option>";
                								});
                				                $("#brand"+countJ).append(optionJsonbrand);
                				                $("#brand"+countJ).val(brandJ);
                				            }
                					});
                    					
                    				}
        			            }
        			            
        			        });
	
        				}
        				$('#salesCat'+i).val(launchBase[i].salesCat);
        				var launchBaseId = launchBase[i].basepackId;
        				var chkln = $('#bsk'+i).val(launchBaseId);
        				$(chkln).prop('checked',true);
        				$('#bpCode'+i).val(launchBase[i].bpCode);
        				$('#desc'+i).val(launchBase[i].bpDisc);
        				$('#mrp'+i).val(launchBase[i].mrp);
        				$('#tur'+i).val(launchBase[i].tur);
        				$('#gsv'+i).val(launchBase[i].gsv);
        				$('#cldcon'+i).val(launchBase[i].cldConfig);
        				$('#gram'+i).val(launchBase[i].grammage);
        				$('#classf'+i).val(launchBase[i].classification);
        		}
              }
        });
		/* salecat(val);
		brandOnPsacat(branval); */
		
	}


	if(allLancunData.LAUNCH_CLUSTER != null) {
		$("#launchStoresTab").click();
		var clusterData = allLancunData.LAUNCH_CLUSTER;
		 strfrmt = clusterData.Store_Format.trim();
		 custstrfrmt = clusterData.Customer_Store_Format.trim();
		$("#customerChainL1Thrscn").val(clusterData.account_string);
		
		
		precheckCustomer(clusterData.account_string, true);
		$("#cluster").val(clusterData.Cluster);
		precheck3rdScreenClus(clusterData.Cluster, true );
		var strfrmt = allLancunData.LAUNCH_CLUSTER.Store_Format;
		var custstrfrmt = allLancunData.LAUNCH_CLUSTER.Customer_Store_Format;
			if(strfrmt != ""|| custstrfrmt != ""){
				$("#custmsel").prop("checked", true);
				$( "#radio1, #radio2" ).removeAttr( 'disabled' );
				if(strfrmt != ""){
					$( '#radio1' ).prop("checked", true);
					$( '#cust-chain-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
					//$( '#cust-chain-cst-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
					$('#cust-chain-cst-store').multiselect("select",strfrmt);
				}
				else if(custstrfrmt != ""){
					$( '#radio2' ).prop("checked", true);
					$( '#cust-chain-cst-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
					}
				
			}else{
				$( "#radio1, #radio2" ).attr( 'disabled', true );
				$( '#cust-chain-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
				$( '#cust-chain-cst-store' ).next().find( 'button.multiselect ' ).attr( "disabled", true );
				}
		
		$('#storecount').val(clusterData.totalStoresToLaunch);
		
	}
	
	if(allLancunData.LAUNCH_SELL_INS != null) {
		$("#launchSellInTab").click();
		
		var launchsell = allLancunData.LAUNCH_SELL_INS.launchSellInEditResponse;
		var lensell = launchsell.length;
		totalBasepacksCreated = allLancunData.LAUNCH_SELL_INS.basepacksCreated.length; 
		var base = allLancunData.LAUNCH_SELL_INS.basepacksCreated;
		//getSellInData();
		var rowCount = $('#sellinTable tbody').find('tr');
		$('#sellinTable tbody').empty();
		var sellrow = "";
		for(var selli = 0; selli < lensell; selli++){	
			//rowCount[i].find($('#lonech'+i)).val(launchsell[i].L1_CHAIN);
			
			sellrow += "<tr><td><input name='childRatio1' type='text' class='form-control l1chain' onkeypress='return validateFloatKeyPress(this,event);' id='lonech"+selli+"' readonly value= '"
					 	+ launchsell[selli].L1_CHAIN +"'></td>"
						+"<td><input name='childRatio1' type='text' class='form-control l2chain'onkeypress='return validateFloatKeyPress(this,event);' id='ltwoch"+selli+"' readonly value= '" 
						+ launchsell[selli].L2_CHAIN +"'></td>"
						+"<td><input name='childRatio1' type='text' class='form-control storefrmt' onkeypress='return validateFloatKeyPress(this,event);' id='strfmt"+selli+"' readonly value= '"
						+ launchsell[selli].STORE_FORMAT +"'></td>"
						+ "<td><input name='childRatio1' type='text' class='form-control storesplan' id='strpln"+selli+"' readonly value= '"
						+ launchsell[selli].STORES_PLANNED +"'></td>"
						+ "<td ><input name='childRatio1' data-field='base_1' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].SKU1_SELLIN +"'></td>"
						+ "<td ><input name='childRatio1' data-field='base_2' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].SKU2_SELLIN +"'></td>"
						+ "<td><input name='childRatio1' data-field='base_3' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].SKU3_SELLIN +"'></td>"
						+ "<td ><input name='childRatio1' data-field='base_4' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].SKU4_SELLIN +"'></td>"
						+ "<td ><input name='childRatio1' data-field='base_5' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].SKU5_SELLIN +"'></td>"
						+ "<td><input name='childRatio1' data-field='base_6' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].SKU6_SELLIN +"'></td>"
						+ "<td><input name='childRatio1' data-field='base_7' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].SKU7_SELLIN +"'></td>" 
						+ "<td><input name='childRatio1' data-field='base_8' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].SKU8_SELLIN +"'></td>"
						+ "<td><input name='childRatio1' data-field='base_9' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].SKU9_SELLIN +"'></td>"
						+ "<td><input name='childRatio1' data-field='base_10' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].SKU10_SELLIN +"'></td>"
						+ "<td><input name='childRatio1' data-field='base_11' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].SKU11_SELLIN +"'></td>"
						+ "<td><input name='childRatio1' data-field='base_12'  type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].SKU12_SELLIN +"'></td>"
						+ "<td><input name='childRatio1' data-field='base_13' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].SKU13_SELLIN +"'></td>"
						+ "<td><input name='childRatio1' data-field='base_14' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].SKU14_SELLIN +"'></td>"
						+ "<td><input name='childRatio1' data-field='rotations' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].ROTATIONS +"'></td>"
						+ "<td><input name='childRatio1' data-field='upliftone' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].UPLIFT_N1 +"'></td>"
					    + "<td><input name='childRatio1' data-field='uplifttwo' type='text' class='form-control validfield' onkeypress='return validateFloatKeyPress(this,event);' value= '"
						+ launchsell[selli].UPLIFT_N2 +"'></td></tr>";
			
		}
		if($( "#sellinTable" ).length != 0){
			$( "#sellinTable" ).replaceWith( '<table id="sellinTable" class="table table-striped table-bordered nowrap order-column row-border smt-dashboardTable table-responsive" style="width:100%"> <thead class="thead-dark"> <tr> <th >L1 Chain</th> <th>L2 Chain</th> <th>Store Format</th> <th>Store Planned</th> <th class="basepackdesc-1">'+( typeof base[0] != "undefined" ? base[0] : "" )+'</th> <th class="basepackdesc-2">'+( typeof base[1] != "undefined" ? base[1] : "" )+'</th> <th class="basepackdesc-3">'+( typeof base[2] != "undefined" ? base[2] : "" )+'</th> <th class="basepackdesc-4">'+( typeof base[3] != "undefined" ? base[3] : "" )+'</th> <th class="basepackdesc-5">'+( typeof base[4] != "undefined" ? base[4] : "" )+'</th> <th class="basepackdesc-6">'+( typeof base[5] != "undefined" ? base[5] : "" )+'</th> <th class="basepackdesc-7">'+( typeof base[6] != "undefined" ? base[6] : "" )+'</th> <th class="basepackdesc-8">'+( typeof base[7] != "undefined" ? base[7] : "" )+'</th> <th class="basepackdesc-9">'+( typeof base[8] != "undefined" ? base[8] : "" )+'</th> <th class="basepackdesc-10">'+( typeof base[9] != "undefined" ? base[9] : "" )+'</th> <th class="basepackdesc-11">'+( typeof base[10] != "undefined" ? base[10] : "" )+'</th> <th class="basepackdesc-12">'+( typeof base[11] != "undefined" ? base[11] : "" )+'</th> <th class="basepackdesc-13">'+( typeof base[12] != "undefined" ? base[12] : "" )+'</th> <th class="basepackdesc-14">'+( typeof base[14] != "undefined" ? base[14] : "" )+'</th> <th>Rotations</th> <th>Uplift n+1</th> <th>Uplift n+2</th> </tr></thead> <tbody></tbody> </table>' );
		} else {
			$( "#sellinTable" ).replaceWith( '<table id="sellinTable" class="table table-striped table-bordered nowrap order-column row-border smt-dashboardTable table-responsive" style="width:100%"> <thead class="thead-dark"> <tr> <th >L1 Chain</th> <th>L2 Chain</th> <th>Store Format</th> <th>Store Planned</th> <th class="basepackdesc-1">'+( typeof base[0] != "undefined" ? base[0] : "" )+'</th> <th class="basepackdesc-2">'+( typeof base[1] != "undefined" ? base[1] : "" )+'</th> <th class="basepackdesc-3">'+( typeof base[2] != "undefined" ? base[2] : "" )+'</th> <th class="basepackdesc-4">'+( typeof base[3] != "undefined" ? base[3] : "" )+'</th> <th class="basepackdesc-5">'+( typeof base[4] != "undefined" ? base[4] : "" )+'</th> <th class="basepackdesc-6">'+( typeof base[5] != "undefined" ? base[5] : "" )+'</th> <th class="basepackdesc-7">'+( typeof base[6] != "undefined" ? base[6] : "" )+'</th> <th class="basepackdesc-8">'+( typeof base[7] != "undefined" ? base[7] : "" )+'</th> <th class="basepackdesc-9">'+( typeof base[8] != "undefined" ? base[8] : "" )+'</th> <th class="basepackdesc-10">'+( typeof base[9] != "undefined" ? base[9] : "" )+'</th> <th class="basepackdesc-11">'+( typeof base[10] != "undefined" ? base[10] : "" )+'</th> <th class="basepackdesc-12">'+( typeof base[11] != "undefined" ? base[11] : "" )+'</th> <th class="basepackdesc-13">'+( typeof base[12] != "undefined" ? base[12] : "" )+'</th> <th class="basepackdesc-14">'+( typeof base[13] != "undefined" ? base[13] : "" )+'</th> <th>Rotations</th> <th>Uplift n+1</th> <th>Uplift n+2</th> </tr></thead> <tbody></tbody> </table>' );
		}
		$('#sellinTable tbody').html(sellrow);
		
		// $("#sellinTable").dataTable().fnDestroy();
        setTimeout(function(){
		edit_sellinTable = $('#sellinTable').DataTable({
			
			 	scrollY:       "300px",
		        scrollX:        true,
		        "scrollCollapse": true,
		        //"paging":         true,  //Commented & Added below By Sarin - Sprint4 Aug2021
		        "paging":         false,  //Added By Sarin - Sprint4 Aug2021
		        "autoWidth": false,
		        "ordering": false,
		        searching: false,
		    	"lengthMenu" : [
					[ 10, 25, 50, 100 ],
					[ 10, 25, 50, 100 ] ],
		        "oLanguage": {
	                  "sSearch": '<i class="icon-search"></i>',
	                  "oPaginate": {
	                      "sNext": "&rarr;",
	                      "sPrevious": "&larr;"
	                  },
	                  "sLengthMenu": "Records per page _MENU_ ",
	                  "sEmptyTable": "No Pending Launch."
	
	              },
		       fixedColumns:   {
		            leftColumns: 4
		        },
		        "columnDefs": [
		            {
		                "targets": [ 4 ],
		                "visible": totalBasepacksCreated >= 1
		            },
		            {
		                "targets": [ 5 ],
		                "visible": totalBasepacksCreated >= 2
		            },
		            {
		                "targets": [ 6 ],
		                "visible": totalBasepacksCreated >= 3
		            },
		            {
		                "targets": [ 7 ],
		                "visible": totalBasepacksCreated >= 4
		            },
		            {
		                "targets": [ 8 ],
		                "visible": totalBasepacksCreated >= 5
		            },
		            {
		                "targets": [ 9 ],
		                "visible": totalBasepacksCreated >= 6
		            },
		            {
		                "targets": [ 10 ],
		                "visible": totalBasepacksCreated >= 7
		            },
		            {
		                "targets": [ 11 ],
		                "visible": totalBasepacksCreated >= 8
		            },
		            {
		                "targets": [ 12 ],
		                "visible": totalBasepacksCreated >= 9
		            },
		            {
		                "targets": [ 13 ],
		                "visible": totalBasepacksCreated >= 10
		            },
		            {
		                "targets": [ 14 ],
		                "visible": totalBasepacksCreated >= 11
		            },
		            {
		                "targets": [ 15 ],
		                "visible": totalBasepacksCreated >= 12
		            },
		            {
		                "targets": [ 16 ],
		                "visible": totalBasepacksCreated >= 13
		            },
		            {
		                "targets": [ 17 ],
		                "visible": totalBasepacksCreated >= 14
		            }
		        ]
	    	    });
        }, 800);
		
	}

	if(allLancunData.LAUNCH_VISI_PLANNING != null) {
		$("#launchVisiTab").click();
		var launchvisi = allLancunData.LAUNCH_VISI_PLANNING;
		var lenvisi = launchvisi.length;

		var launchId = $("#dynamicLaunchId").val();
		if(lenvisi > 0){                                   
		$.ajax({
		    url: 'https://vat.hulcd.com/VisibilityAssetTracker/getLaunchVisiPlan.htm?launchId='+launchId,
		    dataType: 'json',
		    type: 'get',
		    contentType: 'application/json',
		    processData: false,
		    beforeSend: function() {
	            ajaxLoader(spinnerWidth, spinnerHeight);
	        },
		    success: function( data, textStatus, jQxhr ){
		    	 var len = data.listOfVisiPlans.length;
		           var vissiAsset = data.listOfAssetType;
		    	var optionJsonvissi = "<option value='-1'>Select Visi Asset</option>";
					$.each(vissiAsset, function (a, br) {
			         	optionJsonvissi += "<option value='" + br + "'>" + br + "</option>";
			     	});

					var visirow = "";
					$('#visiplan tbody').empty();
					for(var visi = 0; visi < lenvisi; visi++){
						visirow = "<tr><td><input type='checkbox' class='checkvissi' name='selectDel' value='selectDel'></td>" 
							+"<td><input name='childRatio1' type='text' class='form-control l1chain' onkeypress='return validateFloatKeyPress(this,event);' id='acc"+visi+"' readonly value= '"
							+ launchvisi[visi].ACCOUNT +"'></td>"
							+"<td><input name='childRatio1' type='text' class='form-control l2chain' onkeypress='return validateFloatKeyPress(this,event);' id='frmt"+visi+"' readonly value= '" 
							+ launchvisi[visi].FORMAT +"'></td>" 
							+"<td><input name='childRatio1' type='text' class='form-control storefrmt' onkeypress='return validateFloatKeyPress(this,event);' id='stravlb"+visi+"' readonly value= '" 
							+ launchvisi[visi].STORES_AVAILABLE+"'></td>" 
							+ "<td><input name='childRatio1' type='text' class='form-control validfield' id='strplan"+visi+"' onkeypress='return validateFloatKeyPress(this,event);' value= '" 
							+ launchvisi[visi].STORES_PLANNED +"'></td>"
							+ "<td><select class='form-control validfield salescat' id='visiasstone"+visi+"' selected='selected'>"
							+ optionJsonvissi+"</select></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield face-self' id='facone"+visi+"' onkeypress='return validateFloatKeyPress(this,event);' value= '" 
							+ launchvisi[visi].FACING_PER_SHELF_PER_SKU1 +"'></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield depth-self' id='deptone"+visi+"' onkeypress='return validateFloatKeyPress(this,event);' value= '" 
							+ launchvisi[visi].DEPTH_PER_SHELF_PER_SKU1 +"'></td>"
							+ "<td><select class='form-control validfield salescat' id='visiassettwo"+visi+"'>"+optionJsonvissi+"</select></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield face-self' id='facetwo"+visi+"' onkeypress='return validateFloatKeyPress(this,event);' value= '" 
							+ launchvisi[visi].FACING_PER_SHELF_PER_SKU2 +"'></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield depth-self' id='depttwo"+visi+"' onkeypress='return validateFloatKeyPress(this,event);' value= '" 
							+ launchvisi[visi].FACING_PER_SHELF_PER_SKU2 +"'></td>"
							+ "<td><select class='form-control validfield salescat' id='visiassetthree"+visi+"'>"+optionJsonvissi+"</select></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield face-self' id='facethree"+visi+"' onkeypress='return validateFloatKeyPress(this,event);' value= '" 
							+ launchvisi[visi].FACING_PER_SHELF_PER_SKU3 +"'></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield depth-self' id='deptthree"+visi+"' onkeypress='return validateFloatKeyPress(this,event);' value= '" 
							+ launchvisi[visi].FACING_PER_SHELF_PER_SKU3 +"'></td>"
							+ "<td><select class='form-control validfield salescat' id='visiassetfour"+visi+"'>"+optionJsonvissi+"</select></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield face-self' id='facefour"+visi+"' onkeypress='return validateFloatKeyPress(this,event);' value= '" 
							+ launchvisi[visi].FACING_PER_SHELF_PER_SKU4 +"'></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield depth-self' id='deptfour"+visi+"' onkeypress='return validateFloatKeyPress(this,event);' value= '" 
							+ launchvisi[visi].FACING_PER_SHELF_PER_SKU4 +"'></td>"
							+ "<td><select class='form-control validfield salescat' id='visiassetfive"+visi+"'>"+optionJsonvissi+"</select></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield face-self' id='facefive"+visi+"' onkeypress='return validateFloatKeyPress(this,event);' value= '" 
							+ launchvisi[visi].FACING_PER_SHELF_PER_SKU5 +"'></td>"
							+ "<td><input name='childRatio1' type='text' class='form-control validfield depth-self' id='deptfive"+visi+"' onkeypress='return validateFloatKeyPress(this,event);' value= '" 
							+ launchvisi[visi].FACING_PER_SHELF_PER_SKU5 +"'></td></tr>";

						$('#visiplan tbody').append(visirow);

						$('#visiasstone'+visi).val(launchvisi[visi].VISI_ASSET_1);
						$('#visiassettwo'+visi).val(launchvisi[visi].VISI_ASSET_2);
						$('#visiassetthree'+visi).val(launchvisi[visi].VISI_ASSET_3);
						$('#visiassetfour'+visi).val(launchvisi[visi].VISI_ASSET_4);
						$('#visiassetfive'+visi).val(launchvisi[visi].VISI_ASSET_5);
					}

		    
	       
				setTimeout(function(){
					edit_visiTable = $('#visiplan').DataTable( {
						
					 	scrollY:       "300px",
				        scrollX:        true,
				        "scrollCollapse": true,
				        "paging":         true,
				        "ordering": false,
				        "autoWidth": false,
				        searching: false,
				    	"lengthMenu" : [
							[ 10, 25, 50, 100 ],
							[ 10, 25, 50, 100 ] ],
				        "oLanguage": {
			                  "sSearch": '<i class="icon-search"></i>',
			                  "oPaginate": {
			                      "sNext": "&rarr;",
			                      "sPrevious": "&larr;"
			                  },
			                  "sLengthMenu": "Records per page _MENU_ ",
			                  "sEmptyTable": "No Pending Launch."

			              },
				     
			 	    });
				},800);
					 $('#visiplan').css({
			             'text-align': 'left',
			             
			         });
			         $('#visiplan').css({
			             'color': '#29290a'
			         });
			         $($('#visiplan .row')[0]).after(
			                 $(".summary-text"));

			         $($('#visiplan .row')[0]).css({
			             'float': 'right'
			         });
			         $(
			             $($('#visiplan .row')[0]).find(
			                 '.col-sm-6')[0]).css({
			                     'width': 'auto',
			                     'float': 'left'
			         });
			         $(
			             $($('#visiplan .row')[0]).find(
			                 '.col-sm-6')[1]).addClass(
			             "searchicon-wrapper");
			         $(
			             $($('#visiplan .row')[0]).find(
			                 '.col-sm-6')[1]).after($(".search-icon"));

			         $('#DataTables_Table_0_length').css({
			             'text-align': 'right',
			             'padding': '20px 0'
			         });
			         $('#DataTables_Table_0_length label').css({
			             'color': '#29290a'
			         });
			},
		    error: function( jqXhr, textStatus, errorThrown ){
		        console.log( errorThrown );
		    }
		 
		});
		}
		 else{
			 setTimeout(function(){
				 edit_visiTable = $('#visiplan').DataTable( {
						
					 	scrollY:       "300px",
				        scrollX:        true,
				        "scrollCollapse": true,
				        "paging":         true,
				        "ordering": false,
				        "autoWidth": false,
				        searching: false,
				    	"lengthMenu" : [
							[ 10, 25, 50, 100 ],
							[ 10, 25, 50, 100 ] ],
				        "oLanguage": {
			                  "sSearch": '<i class="icon-search"></i>',
			                  "oPaginate": {
			                      "sNext": "&rarr;",
			                      "sPrevious": "&larr;"
			                  },
			                  "sLengthMenu": "Records per page _MENU_ ",
			                  "sEmptyTable": "No visi to be planned."

			              },
				     
			 	    });

			 }, 800);
			 $('#visiplan').css({
	             'text-align': 'left',
	             
	         });
	         $('#visiplan').css({
	             'color': '#29290a'
	         });
	         $($('#visiplan .row')[0]).after(
	                 $(".summary-text"));

	         $($('#visiplan .row')[0]).css({
	             'float': 'right'
	         });
	         $(
	             $($('#visiplan .row')[0]).find(
	                 '.col-sm-6')[0]).css({
	                     'width': 'auto',
	                     'float': 'left'
	         });
	         $(
	             $($('#visiplan .row')[0]).find(
	                 '.col-sm-6')[1]).addClass(
	             "searchicon-wrapper");
	         $(
	             $($('#visiplan .row')[0]).find(
	                 '.col-sm-6')[1]).after($(".search-icon"));

	         $('#DataTables_Table_0_length').css({
	             'text-align': 'right',
	             'padding': '20px 0'
	         });
	         $('#DataTables_Table_0_length label').css({
	             'color': '#29290a'
	         });
	        }
	
	}

	if(allLancunData.LAUNCH_FINAL_BUILDUP != null) {
		$("#launchFinBuiUpTab").click();
		var launchfinalbuld = allLancunData.LAUNCH_FINAL_BUILDUP;
		var lenbuild = launchfinalbuld.length;
		var finalrow = "";
		for(var finbu = 0; finbu < lenbuild; finbu++){
			
		finalrow += "<tr><td><input name='skuname' type='text' class='form-control validfield' id='skunam"+finbu+"' value='" 
				+ launchfinalbuld[finbu].skuName +"' readonly></td>"
				+ "<td><input name='basecd' type='text' class='form-control validfield' id='bscode"+finbu+"' value='"
				+ launchfinalbuld[finbu].basepackCode +"' readonly></td>"
				+ "<td><input name='lnchsellval' type='text' class='form-control validfield' id='lnchsellval"+finbu+"' value='" 
				+ launchfinalbuld[finbu].launchSellInValue +"' readonly></td>"
				+ "<td><input name='nsellval' type='text' class='form-control validfield' id='lnchsellN1val"+finbu+"' value='"
				+ launchfinalbuld[finbu].launchN1SellInVal +"' readonly></td>"
				+ "<td><input name='n2sellval' type='text' class='form-control validfield' id='lnchsellN2val"+finbu+"' value='"
				+ launchfinalbuld[finbu].launchN2SellInVal +"' readonly></td>"
				+ "<td><input name='lnchsellincld' type='text' class='form-control validfield' id='lnchsellcldval"+finbu+"' value='"
				+ launchfinalbuld[finbu].launchSellInCld +"' readonly></td>"
				+ "<td><input name='skuname' type='text' class='form-control validfield' id='lnchsellcldN1val"+finbu+"' value='" 
				+ launchfinalbuld[finbu].launchN1SellInCld +"' readonly></td>"
				+ "<td><input name='skuname' type='text' class='form-control validfield' id='lnchsellcldN2val"+finbu+"' value='" 
				+ launchfinalbuld[finbu].launchN2SellInCld +"' readonly></td>"
				+ "<td><input name='skuname' type='text' class='form-control validfield' id='lnchsellunitval"+finbu+"' value='"
				+ launchfinalbuld[finbu].launchSellInUnit +"' readonly></td>"
				+ "<td><input name='skuname' type='text' class='form-control validfield' id='lnchsellunitN1val"+finbu+"' value='" 
				+ launchfinalbuld[finbu].launchN1SellInUnit +"' readonly></td>"
				+ "<td><input name='skuname' type='text' class='form-control validfield' id='lnchsellunitN2val"+finbu+"' value='" 
				+ launchfinalbuld[finbu].launchN2SellInUnit +"' readonly></td></tr>";
		}
		$('#finalTable tbody').empty().append(finalrow);
		
		setTimeout(function(){
		edit_finaltable = $('#finalTable').DataTable( {
						
						"scrollY":       "280px",
				        "scrollX":        true,
				        "scrollCollapse": true,
				        "paging":         true,
				        "autoWidth": false,
				        "ordering": false,
				        "searching": false,
				    	"lengthMenu" : [
							[ 10, 25, 50, 100 ],
							[ 10, 25, 50, 100 ] ],
				        "oLanguage": {
			                  "sSearch": '<i class="icon-search"></i>',
			                  "oPaginate": {
			                      "sNext": "&rarr;",
			                      "sPrevious": "&larr;"
			                  },
			                  "sLengthMenu": "Records per page _MENU_ ",
			                  "sEmptyTable": "No Pending Launch."
			
			              }
	    	    	    });
		}, 800);
			    
	}
	if(allLancunData.LAUNCH_SUBMIT != null) {
		$("#launchSubTab").click();
		var launchsubmt = allLancunData.LAUNCH_SUBMIT;
		var anxr = launchsubmt.annexureDocName;
		var artwr = launchsubmt.artWorkPackShotsDocName;
		var mdg = launchsubmt.mdgDeckDocName;
		var sampl = launchsubmt.sampleShared;
		isFileOneUploaded = true,
		isFileTwoUploaded = true,
		isFileThreeUploaded = true;
		
		$('#annxr').show().html(anxr);
		$('#baseart').show().html(artwr);
		$('#mdglnc').show().html(mdg);
		
		if(sampl == "0"){
			$('#sampleNo').prop("checked", true);
		}
		if(sampl == "1"){
			$('#sampleYes').prop("checked", true);
		}
	}

/* 	$(document).on('click', function(){
		launch_edit_initial_load = false; 
		$( $( "#comboTreeclusterDropDownContainer li .comboTreeItemTitle input:checked" )[0]).trigger( "change" );
		$( "#comboTreecustomerChainL1ThrscnDropDownContainer input[type=checkbox]" ).trigger("change"); 
		});
	
	setTimeout(function(){
		
		
		
	}, 1800); */
	//edtscn = true;
}
</c:if>  


function newLaunch() {

	edtscn = false;
	launch_edit_initial_load = false;
	$("#isEdit").val("false");
}

//Q1 sprint-3 userstory 1 Notification TME Bharati Code
$(document).ready( function() {      
                var itemCont = 0;      
                var requestUri = "getLaunchKamInputs.htm";    
    
        $.ajax({      
        url: requestUri,      
        dataType: 'json',
        type: 'GET',
        contentType: 'application/json',
        processData: false,      
        headers: { "Accept": "text/plain" },    
        
        success: function(data, textStatus, jqXHR) {
           //console.log(data);
            var kamInput = data.launchKamInputsResponses;
            if(kamInput.length == 0){
            $("#NotificationBadge").hide();
            }else{
            //sprint-3 notification count
           $("#NotificationBadge").html(kamInput.length);
           }

        },     
        error: function (data) {      
            console.log(data);      
        }      
    });      
}); 
//Q1 sprint-3 userstory 1 Notification TME Bharati Code end
</script>
</body>
</html>
