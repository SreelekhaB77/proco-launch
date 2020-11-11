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
<link rel="stylesheet" type="text/css"
	href="assets/css/fileinput.css">
<link rel="stylesheet" href="assets/css/jquery-ui.css">
<link rel="stylesheet" href="assets/css/bootstrapValidator.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap-multiselect.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/fileinput.css">
<link rel="stylesheet" href="assets/css/launchstyle.css"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="assets/css/custom-font.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/font-awesome.css">
<link rel="stylesheet"	href="assets/css/smart_wizard_theme_arrows.css" type="text/css">

<link rel="stylesheet" href="assets/css/smart_wizard.css" type="text/css">
<link rel="stylesheet" type="text/css"
	href="assets/css/fixedColumns.bootstrap.min.css">


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
	<input type="hidden" value="" id="kamLnchDet">
	
	<nav class="navbar navbar-inverse navbar-fixed-top container-bg"
		style="position: relative; top: 80px; z-index: 2; background-image: none ! important; border: none ! important;">
		<div class="container-fluid paddR10">
			<div class="navbar-header marginB10">
				<h1 class="SEGOEUIL-font pull-left">
					KAM Launch Plan Dashboard
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
								class="active col-md-2 col-sm-8 col-xs-12 launch-icon-active">
								<a href="http://localhost:8083/VisibilityAssetTracker/getAllCompletedLaunchDataKam.htm"> <!-- <div class="launch-icon"></div> -->
									<div class="tab-label-launch">Launch Details</div>
							</a>
							</li>
							<li role="presentation"
								class="active col-md-2 col-sm-8 col-xs-12 launch-icon-inactive">
								<a href="http://localhost:8083/VisibilityAssetTracker/getApprovalStatusKam.htm"> <!-- <div class="launch-icon"></div> -->
									<div class="tab-label-launch">Approval Status</div>
							</a>
							</li>
							<li role="presentation"
								class="active col-md-2 col-sm-8 col-xs-12 launch-icon-inactive">
								<a href="http://localhost:8083/VisibilityAssetTracker/getAllCompletedLaunchDataKam.htm"> <!-- <div class="launch-icon"></div> -->
									<div class="tab-label-launch">Performance</div>
							</a>
							</li>
							<li role="presentation"
								class="active col-md-2 col-sm-8 col-xs-12 launch-icon-inactive">
								<a href="http://localhost:8083/VisibilityAssetTracker/getAllCompletedLaunchDataKam.htm"> <!-- <div class="launch-icon"></div> -->
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
		
		<div class="alert alert-success alert-dismissible" id="successblock" style="display:none;">
		    <button type="button" class="close" data-dismiss="alert">&times;</button>
		    <strong>Success!</strong><span></span>
		  </div>
		<c:if test="${success_file_upload!=null}">
			<div class="alert alert-success sucess-msg" id="successblock"
				style="display: block">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${success_file_upload}"></c:out>
			</div>
		</c:if>
		
		<c:if test="${errorMsg!=null}">
			<div class="alert alert-danger  sucess-msg" id="errorblock">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${errorMsg}"></c:out>
				<a href="#" id="downloadTempFileLink"
					onclick="javascript:downloadCoeErrorFile();">Click here to
					Download Error File:</a>

			</div>
		</c:if>

		<div class="alert alert-danger marginT15" id="kamerrorblockUpload">
			<button type="button" class="close" data-hide="alert">&times;</button>
			<%-- <c:out value="${errorMsgUpload}"></c:out> --%>
			<span> File does not contains any data.</span>
		</div>
		<!-- modal for change moc -->
		<div id="kammocChange" class="modal fade" role="dialog"
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
							style="background-color: #E95912; color: #fff;">
							<button type="button" class="close" id="paidcrossBtn" data-dismiss="modal">X</button>
							<h4 class="modal-title">Proposed New Launch MOC</h4>
						</div>
						<form action="#" id="mocChange" method="POST"
							class="form-horizontal" style="padding: 10px 0;">

							<div class="modal-body">
								<div class="row">

									<div class="col-md-12">

										<div id="msg-error" class="alert alert-danger fade in"
											style="display: none;">
											<span></span>
											<div id="date-moc-error" class="cust-error error"
												style="text-align: center; padding-bottom: 10px;">Please
												enter remarks</div>
											<button type="button" class="close" data-hide="alert">X</button>
										</div>

										<div class="OpenSans-font">
											<div class="form-group">
												<label for="downloadmoc" class="col-sm-4 control-label">Moc:</label>
												<div class="col-sm-6 offset-col-md-2">
													<span id="kamRemakErrorMsgMoc"
													style="display: none; color: red; margin-left: 17px;">Please
													select MOC.</span>
													<select type="text" class="form-control"
														id="paid-kamlaunch-moc" name="moc">
														<!-- <option value="Select">Select</option> -->
														
													</select>

												</div>

											</div>
											<div class="form-group">
												<label for="downloadmoc" class="col-sm-4 control-label">Enter
													Remarks:</label> <span id="kamRemakErrorMsg"
													style="display: none; color: red; margin-left: 17px;">Please
													enter remarks.</span>
												<div class="col-sm-6 offset-col-md-2">
													<input name='kamRemark' type='text'
														class='form-control validfield' id="kamMocremarks">
												</div>

											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button"
									class="btn new-btn-primary pull-right resetBtn"
									data-dismiss="modal" data-hide="alert" id="paidCancelBtn">CLOSE</button>
								<button type="button" id="KamlanchMocBtn"
									onclick="changeLaunchMoc()"
									class="btn pull-right resetBtn marginR10"
									style="background-color: #3B5999 !important; height: 33px; margin-right: 8px; color: #fff;">Submit</button>
							</div>

						</form>

					</div>
				</div>
			</div>
		</div>

		<!-- modal for reject moc -->
		<div id="kammocReject" class="modal fade" role="dialog"
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
							style="background-color: #E95912; color: #fff;">
							<button type="button" class="close" id="paidcrossBtn"
								data-dismiss="modal">X</button>
							<h4 class="modal-title">Reject Launch Details</h4>
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
												<label for="downloadmoc" class="col-sm-4 control-label">Enter
													Remarks:</label> <span id="kamRemakRejErrorMsg"
													style="display: none; color: red; margin-left: 17px;">Please
													enter remarks.</span>
												<div class="col-sm-6 offset-col-md-2">
													<input name='kamRemark' type='text'
														class='form-control validfield' id="kamMocRejRemarks">
												</div>

											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button"
									class="btn new-btn-primary pull-right resetBtn"
									data-dismiss="modal" data-hide="alert" id="paidRejCancelBtn" >Close</button>
								<button type="button" onclick="rejectLaunch()"
									id="KamlanchMocRejectBtn"
									class="btn pull-right resetBtn marginR10"
									style="background-color: #3B5999 !important; height: 33px; margin-right: 8px; color: #fff;">Submit</button>
							</div>

						</form>

					</div>
				</div>
			</div>
		</div>

		<!-- modal for reject moc screen 2 -->
		<div id="kammocRejectBasePck" class="modal fade" role="dialog"
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
							style="background-color: #028681; color: #fff;">
							<button type="button" class="close" id="paidcrossBtn"
								data-dismiss="modal">�</button>
							<h4 class="modal-title">Reject Basepack(s)</h4>
						</div>
						<form action="#" id="rejBaseRemarkForm" method="POST"
							class="form-horizontal" style="padding: 10px 0;">

							<div class="modal-body">
								<div class="row">

									<div class="col-md-12">

										<div id="msg-error" class="alert alert-danger fade in"
											style="display: none;">
											<span></span>
											<div id="date-moc-error" class="cust-error error"
												style="text-align: center; padding-bottom: 10px;">Please
												select date or moc</div>
											<button type="button" class="close" data-hide="alert">�</button>
										</div>

										<div class="OpenSans-font">

											<div class="form-group">
												<label for="downloadmoc" class="col-sm-4 control-label">Enter
													Remarks:</label> <span id="kamRemakRejBasErrorMsg"
													style="display: none; color: red; margin-left: 17px;">Please
													enter remarks.</span>
												<div class="col-sm-6 offset-col-md-2">
													<input name='kamBasRemark' type='text'
														class='form-control validfield' id="kamMocBasRejRemarks">
												</div>

											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button"
									class="btn btn-default pull-right resetBtn"
									data-dismiss="modal" data-hide="alert" id="baseRejCancelBtn">CLOSE</button>
								<button type="button" onclick="rejectLaunchBaseScreen2()"
									id="KamlanchMocRejectBtnscrn2"
									class="btn pull-right resetBtn marginR10"
									style="background-color: #028681 !important; height: 33px; margin-right: 8px; color: #fff;">Submit</button>
							</div>

						</form>

					</div>
				</div>
			</div>
		</div>
		
		<!-- modal for kam documents remarks -->
		<div id="kamDocRemark" class="modal fade" role="dialog"
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
							style="background-color: #028681; color: #fff;">
							<button type="button" class="close" id="paidcrossBtn"
								data-dismiss="modal">�</button>
							<h4 class="modal-title">Remarks for change in documents sample</h4>
						</div>
						<form action="#" id="docSampRemarkForm" method="POST"
							class="form-horizontal" style="padding: 10px 0;">

							<div class="modal-body">
								<div class="row">

									<div class="col-md-12">

										<div id="msg-error" class="alert alert-danger fade in"
											style="display: none;">
											<span></span>
											<div id="kamDoc-error" class="cust-error error" style="text-align: center; padding-bottom: 10px;">Please enter remarks</div>
											<button type="button" class="close" data-hide="alert">�</button>
										</div>

										<div class="OpenSans-font">

											<div class="form-group">
												<label for="downloadmoc" class="col-sm-4 control-label">Enter Remarks:</label> <span id="kamRemakRejErrorMsg"
													style="display: none; color: red; margin-left: 17px;">Please enter remarks.</span>
												<div class="col-sm-6 offset-col-md-2">
													<input name='kamRemark' type='text' class='form-control validfield' id="kamDocSmplRemarks">
												</div>

											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button"
									class="btn btn-default pull-right resetBtn"
									data-dismiss="modal" data-hide="alert" id="paidRejCancelBtn">CLOSE</button>
								<button type="button" onclick="docSamLaunch()" id="KamDocSamBtn" class="btn pull-right resetBtn marginR10"
									style="background-color: #028681 !important; height: 33px; margin-right: 8px; color: #fff;">Submit</button>
							</div>

						</form>

					</div>
				</div>
			</div>
		</div>
		
		
		<!-- <form id="launchPlanForm" action="#" method="POST"
			enctype="multipart/form-data"> -->

			<!-- SmartWizard html -->
			<div id="smartwizard">
				<ul class="kamLaunchUl">
					<li><a href="#step-1" data-tabid="1" id="kamlaunchDetailsTab"
						title="Launch Details">Launch Details<br /></a></li>
					<li><a href="#step-2" data-tabid="2" id="kamlaunchBasepackTab"
						title="Basepacks">Basepacks<br /></a></li>
					<li><a href="#step-3" data-tabid="3" id="kamlaunchStoresTab"
						title="Launch Values">Launch Value<br /></a></li>
					<li><a href="#step-4" data-tabid="4" id="kamlaunchSellInTab"
						title="Launch Store List">Launch Store List<br /></a></li>
					<li><a href="#step-5" data-tabid="5" id="kamlaunchVisiTab"
						title="Vissi Planning">Visi Planning<br /></a></li>
					<li><a href="#step-6" data-tabid="6" id="kamlaunchFinBuiUpTab"
						title="Documents">Documents<br /></a></li>
					<li><a href="#step-7" data-tabid="7" id="kamlaunchMissDe"
						title="Highlight Missing Details">Highlight Missing Details<br /></a></li>
					<li><a href="#step-8" data-tabid="8" id="kamlaunchSubTab"
						title="MSTN Clearance">MSTN Clearance<br /></a></li>
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
								<div class="child-table">
									<div class="detail_table">
										<table class="table table-striped table-bordered"
											id="kambasepack_add" cellspacing="0" cellpadding="0"
											style="width: 100% ! important">
											<thead class="thead-dark">
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
														class="proco-btn proco-btn-success table-head">Submitted
															Date</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">CMM</span></th>

												</tr>
											</thead>
											<tbody>
												<c:forEach items="${listOfLaunch}" var="launch">
													<tr>
														<td><input type="checkbox" name="editLaunchscr1KAMLaunch"
															class="radioln kamLnchDetscr1" onchange = 'onChangeChkKamLaunchDetails(this, this.value)' value="${launch.launchId}"></td>
														<td>${launch.launchName}</td>
														<td>${launch.launchMoc}<input type = "hidden" class="mocDate" value = '${launch.launchDate}'></td>
														<td>${launch.createdDate}</td>
														<td>${launch.createdBy}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>

										<div class="btnclass">
											<input type="button"class="btn btn-secondary nxtclassification" id="mockamChange" data-toggle="modal"
												data-target="#kammocChange" disabled value="Change Launch MOC"/>
											<input type="button" onclick="kamLaunch()" value="Launch Details" class="btn btn-secondary nxtclassification" id="kamlnchDets"
												style="float: right;" disabled />
											<input type="button" class="btn btn-secondary nxtclassification" id="rejectLaunch" data-toggle="modal"
												data-target="#kammocReject" disabled value="Reject Launch"/>
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
									id="parentCollapse"> <span>LAUNCH BASEPACK</span>
								</a>
							</div>
						</div>
						<div id="collapseTwo" class="collapse" role="tabpanel"
							aria-labelledby="headingTwo" aria-expanded="false"
							style="display: block;">
							<div class="card-block">
								<div class="child-table">
									<div class="kam_base">
										<table class="table table-striped table-bordered"
											id="kam_basepack_add">
											<thead class="thead-dark">
												<tr>
													<th class="scr2tr"><span
														class="proco-btn proco-btn-success table-head">Select</span></th>
													<th class="scr2tr"><span
														class="proco-btn proco-btn-success table-head">Launch
															Name</span></th>
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

											<!--   <button type="button" class="btn col-md-2 rightBtn"><span class="glyphicon glyphicon-arrow-down" style="color:yellow"></span>  Download</button> -->
											<!-- <form id="downloadcoebasepck" method="post">
												<button type="button" class="btn col-md-2 rightBtn"
													onClick="javascript: coedownloadLaunchBasepackTemplate();">
													<span class="glyphicon glyphicon-arrow-down" style="color: yellow"></span>Download Basepack Details
												</button>
											</form> -->
											<input type="button" onclick="kamSaveBasepacks()"
												value="Next" class="btn btn-secondary nxtclassification"
												id="kamSavebspack" style="float: right;" />
											<!-- <input type="button" onclick="kamRejectBasepacks()" value="Reject Basepack" class="btn btn-secondary nxtclassification" id="kamRjtbspack" style="float: left;" /> -->
											<input type="button" value="Previous" class="btn btn-secondary nxtclassification previousKam" id="kamprevbspack" style="float: left;" />
											<!-- <a href="#"
												class="btn map OpenSans-font marginR10 modelClick nxtclassification"
												id="kamRjtbspack" data-toggle="modal"
												data-target="#kammocRejectBasePck" disabled>Reject Basepack</a> -->
											<input type="button" class="btn btn-secondary nxtclassification" id="kamRjtbspack" data-toggle="modal"
												data-target="#kammocRejectBasePck" disabled value="Reject Basepack"/>
										</div>

									</div>

								</div>
							</div>
						</div>

					</div>
					<div class="tab_content" data-blockid="3" id="step-3">
						<div class="card-header" role="tab" id="headingOne">
							<div class="">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne" class="collapsed"> <span>Launch Value</span>
								</a>
							</div>
						</div>
						<div id="collapseOne" class="collapse" role="tabpanel"
							aria-labelledby="headingOne" aria-expanded="false"
							style="height: 0px; display: block;">
							<div class="card-block">

								<div class="child-table">
									<div class="buldtabl">
										<table
											class="table table-striped table-bordered dataTable table-responsive"
											id="kam_basepack_launch_build">
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
										<div class="btnclass">

											<input type="button" value="Previous" class="btn btn-secondary nxtclassification previousKam" id="kamprevlnVal" style="float: left;" />
											<button type="button" class="btn col-md-2 rightBtn" onclick="kamdownloadLaunValTemplate()">
											<span class="glyphicon glyphicon-arrow-down"style="color: yellow"></span> Download template
											</button>
											<input type="button" onclick="saveBuildUp()" value="Next" class="btn btn-secondary nxtclassification" id="bspack" style="float: right;" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="card thirdCard tab_content" data-blockid="4"
						id="step-4">
						<div class="card-header" role="tab" id="headingOne">
							<div class="">
								<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne" class="collapsed"> <span>Launch Store List</span>
								</a>
							</div>
						</div>
						<div id="collapseOne" class="collapse" role="tabpanel"
							aria-labelledby="headingOne" aria-expanded="false"
							style="height: 0px; display: block;">
							<div class="card-block">
								<div class="child-table">
									<div class="kam_table_str">
										<table class="table table-striped table-bordered dataTable table-responsive" id="kam_launch_store_table">
											<thead class="thead-dark">
												<tr>
													<th class="kamstrscr2tr"><span class="proco-btn proco-btn-success table-head">L1 Chain</span></th>
													<th class="kamstrscr2tr"><span class="proco-btn proco-btn-success table-head">L2 Chain</span></th>
													<th class="kamstrscr2tr"><span class="proco-btn proco-btn-success table-head">Store Format</span></th>
													<th class="kamstrscr2tr"><span class="proco-btn proco-btn-success table-head">Cluster</span></th>
													<th class="kamstrscr2tr"><span class="proco-btn proco-btn-success table-head">HUL OL Code</span></th>
													<th class="kamstrscr2tr"><span class="proco-btn proco-btn-success table-head">Kam Remarks</span></th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>

									</div>
								</div>

								<div>
									 <button type="button" class="btn col-md-2 rightBtn" onclick="kamdownloadLaunchStoreLst()">
										<span class="glyphicon glyphicon-arrow-down" style="color: yellow"></span> Download Store List
									</button> 
									<input type="button" value="Previous" class="btn btn-secondary nxtclassification previousKam" id="kamprevstrLst" style="float: left;" />
									<input type="button" onclick="kamsaveStoreData()" value="Next" class="btn btn-secondary nxtclassification" id="kamlnchstrNext" style="float: right;" />
								</div>

							</div>
						</div>

					<form id="kamlaunchStoreUpload" class="form-horizontal" action="#" enctype="multipart/form-data" name="kamlaunchStoreUpload">
						<div class="launchupload-parent kam_upload_screenfour">
							<div class="col-md-12 col-sm-12 ddd">
								<div style="text-align: center; color: #878787;">

									<h2 class="SEGOEUIL-font">Upload Revised List</h2>
									<div class="upload-image">
										<img src="assets/images/upload-icon-n-body.png" alt="" />
									</div>

									<div class="upload-max-size">Maximum Upload File Size :2MB</div>
									<span id="uploadErrorMsg" style="display: none; color: red">Please upload .xls or .xlsx file</span>
									<div class="input-group upload-file">
										<input id="kambtnSubmitStrIn" name="file" type="file" class="file">
									</div>
									<button class="validate_upload btn marginT10 btn-primary" id="kamlaunchStoreFileUploadBtn">Upload Revised List</button>

								</div>
							</div>

						</div>

					</form>
		</div>

					<div class="card forthCard tab_content" data-blockid="5"
						id="step-5">
						<div class="card-header" role="tab" id="headingOne">
							<div class="">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne" class="collapsed"> <span>Vissi
										Plan</span>
								</a>
							</div>
						</div>
						<div id="collapseOne" class="collapse" role="tabpanel"
							aria-labelledby="headingOne" aria-expanded="false"
							style="height: 0px; display: block;">
							<div class="card-block">
								<div class="child-table">
									<div>
										<table id="kamVissiPlanTable"
											class="table table-striped table-bordered nowrap order-column row-border smt-dashboardTable table-responsive"
											style="width: 100%">
											<thead class="thead-dark">
												<tr>
													<th><span
														class="proco-btn proco-btn-success table-head">Stores
															Planned</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Visi
															Asset 1</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Facing
															per self per SKU</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Depth
															per self per SKU</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Visi
															Asset 2</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Facing
															per self per SKU</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Depth
															per self per SKU</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Visi
															Asset 3</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Facing
															per self per SKU</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Depth
															per self per SKU</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Visi
															Asset 4</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Facing
															per self per SKU</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Depth
															per self per SKU</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Visi
															Asset 5</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Facing
															per self per SKU</span></th>
													<th><span
														class="proco-btn proco-btn-success table-head">Depth
															per self per SKU</span></th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div>
								<!-- <button type="button" class="btn col-md-2 rightBtn" id="kamVissidown"
									onclick="downloadLaunchVisiPlanTemplate()">
									<span class="glyphicon glyphicon-arrow-down"
										style="color: yellow"></span> Download
								</button> -->

								<input type="button" value="Previous" class="btn btn-secondary nxtclassification previousKam" id="kamprevvissi" style="float: left;" />
								<input type="button" onclick="kamSaveVissi()" value="Next"
									class="btn btn-secondary nxtclassification" id="kamVissiNext"
									style="float: right;" />
							</div>
						</div>
						<!-- </div>
								</div> -->
					<!-- <div id="kamvisilaunchDiv">
						<form id="kamlaunchVisiPlanUpload" class="form-horizontal"
							action="#" modelAttribute="LAUNCHFileUploadBean"
							enctype="multipart/form-data" name="launchVisifileupload">
							<div class="launchupload-parent kamvisiupload-parent">
								<div class="col-md-12 col-sm-12 ddd">
									<div style="text-align: center; color: #878787;">

										<h2 class="SEGOEUIL-font">Upload Visi Store List</h2>
										<div class="upload-image">
											<img src="assets/images/upload-icon-n-body.png" alt="" />
										</div>

										<div class="upload-max-size">Maximum Upload File
											Size:2MB</div>
										<span id="kamuploadVisiPlanErrorMsg"
											style="display: none; color: red">Please upload .xls
											or .xlsx file</span>
										<div class="input-group upload-file">
											<input id="kamuploadVisiPlan" name="file" type="file"
												class="file">
										</div>
										<button class="validate_upload btn marginT10 btn-primary"
											id="kamlaunchVisiPlanFileUploadBtn">Upload Visi
											Store List</button>

									</div>
								</div>
							</div>
						</form>
						</div> -->
					</div>

					<div class="card forthCard tab_content" data-blockid="6"
						id="step-6">
						<div class="card-header" role="tab" id="headingOne">
							<div class="">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne" class="collapsed"> <span>Documents</span>
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
												<div class="alert alert-success sucess-msg" id="successblockUpload">
													<button type="button" class="close" data-hide="alert">&times;</button>
													<%-- <c:out value="${success}"></c:out> --%>
													<span></span>
												</div>

												<form action="#" method="POST" enctype="multipart/form-data"
													id="kamannexFileUploadBean" class="form-horizontal"
													name="tmeFileUploadBean">

													<div class="form-group row">
														<div class="input-group upload-file">

															<label class="col-sm-2 col-md-3 control-label subLabel" style="text-align:end;">Annexure</label>
															<!-- <div class="col-sm-10 col-md-6 subfileDiv">
															<span id="kamuploadannErrorMsg" style="display: none; color: red">Please upload .xls or .xlsx file</span>
																<input id="kamannexfile" name="file" type="file" class="file" size="400">
															</div> -->
															<div class="col-sm-10 col-md-4 kamsubUpload">
																<!-- <input class="validate_upload btn marginT10 rightBtn col-sm-12 col-md-12" id="kamannexureUploadBtn" type="button" value="Download" onclick="kamdownAnnexureDoc()"></input> -->
																<button type="button" class="btn col-md-12 rightBtn"
																	onclick="kamdownAnnexureDoc()">
																	<span class="glyphicon glyphicon-arrow-down"
																		style="color: yellow"></span> Download Annexure
																	template
																</button>
															</div>
														</div>

													</div>
												</form>

												<form action="#" method="POST" enctype="multipart/form-data"
													id="kamartworkFileUpload" class="form-horizontal"
													name="tmeFileUploadBean">
													<div class="form-group row">
														<div class="input-group upload-file">

															<label class="col-sm-2 col-md-3 control-label subLabel">Artwork
																packshots</label>
															<!-- <div class="col-sm-10 col-md-6 subfileDiv">
															<span id="kamuploadartErrorMsg" style="display: none; color: red">Please upload .xls or .xlsx file</span>
																<input id="kamartfile" name="file" type="file" class="file" size="400" multiple="multiple">
															</div> -->
															<div class="col-sm-10 col-md-4 kamsubUpload">
																<input class="btn" id="kamartworkdownBtn" type="hidden"
																	value=""></input>
																<button type="button" class="btn col-md-12 rightBtn"
																	onclick="kamuploadArtDoc()">
																	<span class="glyphicon glyphicon-arrow-down"
																		style="color: yellow"></span> Download Artwork
																	template
																</button>
															</div>
														</div>

													</div>
												</form>

												<form action="#" method="POST" enctype="multipart/form-data"
													id="kammdgFileUpload" class="form-horizontal"
													name="tmeFileUploadBean">
													<div class="form-group row">
														<div class="input-group upload-file">

															<label class="col-sm-2 col-md-3 control-label subLabel">MDG
																deck</label>
															<!-- <div class="col-sm-10 col-md-6 subfileDiv">
															<span id="kamuploadmdgErrorMsg" style="display: none; color: red">Please upload .xls or .xlsx file</span>
																<input id="kammdgfile" name="file" type="file" class="file col-md-8" size="40">
															</div> -->
															<div class="col-sm-10 col-md-4 kamsubUpload">
																<!-- <input class="validate_upload btn marginT10 rightBtn col-sm-12 col-md-12" id="kammdgUploadBtn" type="button" value="Download" onclick="kamuploadmdgDoc()"></input> -->
																<button type="button" class="btn col-md-12 rightBtn"
																	onclick="kamuploadmdgDoc()">
																	<span class="glyphicon glyphicon-arrow-down"
																		style="color: yellow"></span> Download MDG template
																</button>
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
														<div class="custom-control custom-checkbox custom-control-inline col-sm-2 col-md-1" style="text-align:right;">
															<input type="radio" class="custom-control-input"
																name="samp" id="kamsampleYes" value="1"><label
																class="custom-control-label" for="defaultInline2">
																Yes</label>
														</div>


														<div
															class="custom-control custom-checkbox custom-control-inline  col-sm-2 col-md-1">
															<input type="radio" class="custom-control-input"
																name="samp" id="kamsampleNo" value="0"><label
																class="custom-control-label" for="defaultInline3">
																No</label>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

								</div>

							</div>

							<div class="btnclass">
								<!-- 	<a href="#" class="btn btn-secondary sw-btn-prev disabled prev" disabled>Previous</a> -->
								<input type="button" value="Previous" class="btn btn-secondary nxtclassification previousKam" id="kamprevdoc" style="float: left;" />
								<input type="button" value="Next" class="btn btn-secondary nxtclassification" onclick="kamsaveLaunchPlandownload()" id="kamDocNext" style="float: right;" />
							</div>
						</div>

					</div>

					<div class="card fifthCard tab_content" data-blockid="7"
						id="step-7">
						<div class="card-header" role="tab" id="headingOne">
							<div class="">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne" class="collapsed"> <span>Highlight
										Missing Details</span>
								</a>
							</div>
						</div>
						<div id="collapseOne" class="collapse" role="tabpanel"
							aria-labelledby="headingOne" aria-expanded="false"
							style="height: 0px; display: block;">
							<div class="card-block">
								<div class="child-table">
									<div class="col-md-12 ddd">
										<div class="download-block">
											<div class="alert alert-success sucess-msg"
												id="successblockUpload">
												<button type="button" class="close" data-hide="alert">&times;</button>
												<%-- <c:out value="${success}"></c:out> --%>
												<span></span>
											</div>
	
     											<div class="row" style="margin-top: 2%;">
												<div class="col-xs-6">
													<form class="form-horizontal">
														<div class="form-group">
															<label for="nameField" class="col-xs-4">Missing
																Details</label>
															<div class="col-xs-8">
																<input type="text" class="form-control" id="missField"
																	placeholder="Add missing details" />
															</div>
														</div>
													</form>
												</div>
											</div>
										</div>
										<div class="btnclass">
											<!-- <a href="#" class="btn btn-secondary sw-btn-prev disabled prev" disabled>Previous</a> -->
											<input type="button" value="Previous" class="btn btn-secondary nxtclassification previousKam" id="kamprevmisDet" style="float: left;" />
											
											<input type="button" value="Submit"	onclick="kamHighlightMissingDet()" class="btn btn-secondary nxtclassification col-xs-4 col-md-2"
												id="misdocNext" style="float: right;" />
												<input type="button" value="Next" class="btn btn-secondary nxtclassification" onclick="getMstnTable()" id="kamMstnNext" style="float: right; margin-right: 10px;" />
										</div>
										
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="card sixthCard tab_content" data-blockid="8"
						id="step-8">
						<div class="card-header" role="tab" id="headingOne">
							<div class="">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne" class="collapsed"> <span>MSTN Clearance</span>
								</a>
							</div>
						</div>
						<div id="collapseOne" class="collapse" role="tabpanel"
							aria-labelledby="headingOne" aria-expanded="false"
							style="height: 0px; display: block;">
							<div class="card-block">
								<div class="child-table">
									<div class="mstnta">
										<table class="table table-striped table-bordered table-responsive" id="kamMstnClearTable">
											<thead class="thead-dark">
												<tr>
													<th><span class="proco-btn proco-btn-success table-head">Basepack Code</span></th>
													<th><span class="proco-btn proco-btn-success table-head">Basepack Desription</span></th>
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

									</div>
									<div class="btnclass">
										
										<input type="button" value="Previous" class="btn btn-secondary nxtclassification previousKam" id="kamprevhighDet" style="float: left;" />
										<!-- <input type="button" onclick="kamsaveFinalScreen()" value="Submit" class="btn btn-secondary nxtclassification" id="coefinalnext" style="float: right;" /> -->
									</div>

								</div>
							</div>


						</div>

					</div>


				</div>
		<!-- </form> -->

	</div>
	<%@include file="../launchplan/footer.jsp"%>


	<script type="text/javascript" src="assets/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="assets/js/comboTreePlugin.js"></script>
	<script type="text/javascript" src="assets/js/bootstrapValidator.js"></script>
	<script type="text/javascript" src="assets/js/jquery-ui.js"></script>
	<script type="text/javascript" src="assets/js/fileinput.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap-multiselect.js"></script>
	<script type="text/javascript" src="assets/js/custom/launch/kamLaunchscript.js"></script>
	<script src="assets/js/jquery-ui.js"></script>
	<script type="text/javascript" src="assets/js/custom/proco/alert-modal.js"></script>
	<!--  <script type="text/javascript" src="assets/js/jquery.smartWizard.js"></script>  -->
	<script type="text/javascript" src="assets/js/jquery.smartWizard.min.js"></script>
	<script src="assets/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/dataTables.fixedColumns.min.js"></script>
	<script src="assets/js/dataTables.bootstrap.min.js"></script>

</body>
</html>
