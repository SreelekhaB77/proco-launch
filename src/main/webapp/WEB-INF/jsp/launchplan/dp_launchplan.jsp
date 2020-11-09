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
<link rel="stylesheet" href="assets/css/smart_wizard_theme_arrows.min.css" type="text/css" />
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
	<input type="hidden" value="" id="dynamicLaunchId">
	<nav class="navbar navbar-inverse navbar-fixed-top container-bg"
		style="position: relative; top: 80px; z-index: 2; background-image: none ! important; border: none ! important;">
		<div class="container-fluid paddR10">
			<div class="navbar-header marginB10">
				<h1 class="SEGOEUIL-font pull-left">
				DP Launch Plan <span>Dashboard</span>
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
								<a href="http://localhost:8083/VisibilityAssetTracker/getAllCompletedLaunchDataDp.htm"> <!-- <div class="launch-icon"></div> -->
										<div class="tab-label-launch">Launch Plannning</div>
								</a>
							</li>
							<li role="presentation" class="active col-md-2 col-sm-8 col-xs-12 launch-icon-inactive">
								<a href="http://localhost:8083/VisibilityAssetTracker/getAllCompletedLaunchDataDp.htm"> <!-- <div class="launch-icon"></div> -->
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

		<form id="launchPlanForm" action="#" method="POST"
			enctype="multipart/form-data">
			<input type="hidden" name="reasonText" id="coereasonText" value="">
			<input type="hidden" name="remarkText" id="coeremarkText" value="">


			<!-- SmartWizard html -->
			<div id="smartwizard">
				<ul>
					<li class="dpSmart"><a href="#step-1" data-tabid="1" id="dplaunchDetailsTab">Launch Details<br /></a></li>
					<li class="dpSmart"><a href="#step-2" data-tabid="2" id="dplaunchBasepackTab">Basepacks<br /></a></li>
					<li class="dpSmart"><a href="#step-3" data-tabid="3" id="dplaunchStoresTab">Launch Build Up<br /></a></li>
					
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
											       	<td><input type='checkbox' class='dpchecklaunch' name='selectDel' value='${launch.launchId}'></td>
											         <td>${launch.launchName}</td>
											         <td>${launch.launchMoc}</td>
											         <td>${launch.updatedDate}</td>
											         <td>${launch.updatedBy}</td>
											       </tr>
											     </c:forEach>
											</tbody>
										</table>
										
										<div class="coebtnclass">
											<!-- <a href="#" class="btn btn-secondary sw-btn-prev disabled prev" disabled> Previous</a> -->
											<!-- <a href="#" class="btn btn-secondary sw-btn-next">Next</a> -->
											<!-- <input type="button" value="Previous" class="btn btn-secondary nxtclassification previousKam" id="dpprevbspack" style="float: left;" /> -->
								<!-- <input type="button" value="Next" class="btn btn-secondary nxtclassification" id="dpnext"
									style="float: right;" /> -->
											<input type="button" onclick="dpLaunch()" value="Launch Details" class="btn btn-secondary nxtclassification" id="dplnchDets" style="float: right;" />
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
									<div class="dp_table">
										<table class="table table-striped table-bordered table-responsive" id="dp_basepack_add">
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
											
											<input type="button" value="Previous" class="btn btn-secondary nxtclassification previousdp" id="dpprevbspack" style="float: left;" />
											<input type="button" onclick="dpSaveBasepacks()" value="Next" class="btn btn-secondary nxtclassification" id="dpbspack" style="float: right;" />
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
									<div class="dpBuildScreen">
										<table class="table table-striped table-bordered table-responsive" id="dp_basepack_launch_build">
											<thead class="thead-dark">
												<tr>
													<!-- <th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Launch Name</span></th> -->
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Launch Name</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">SKU Name</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Basepack Code</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Launch Sell-In Value</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">N+1 Sell-In Value</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">N+2 Sell-In Value</span></th>
													<th class="scr2tr"><span class="proco-btn proco-btn-success table-head">Launch Sell-In CLD</span></th>
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
											 <input type="button" value="Previous" class="btn btn-secondary nxtclassification previousdp" id="dpprevlnchBuild" style="float: left;" />
											<!--   <button type="button" class="btn col-md-2 rightBtn"><span class="glyphicon glyphicon-arrow-down" style="color:yellow"></span>  Download</button> -->
											<form id="dpdownloadbuildbasepck" method="post">
												<button type="button" class="btn col-md-2 rightBtn"
													onClick="javascript: dpdownloadLaunchBasepackTemplate();">
													<span class="glyphicon glyphicon-arrow-down"
														style="color: yellow"></span>Download launch pack template
												</button>
											</form>
										<!-- 	<input type="button" onclick="dpSaveBuildUp()" value="Next" class="btn btn-secondary nxtclassification" id="dpbspack" style="float: right;" /> -->
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
	<script type="text/javascript" src="assets/js/custom/launch/dpLaunch.js"></script> 
	<script src="assets/js/jquery-ui.js"></script>
	<script type="text/javascript" src="assets/js/custom/proco/alert-modal.js"></script>
	<!--  <script type="text/javascript" src="assets/js/jquery.smartWizard.js"></script>  -->
	<script type="text/javascript" src="assets/js/jquery.smartWizard.min.js"></script>
	<script src="assets/js/jquery.dataTables.min.js"></script>
  	<script src="assets/js/dataTables.fixedColumns.min.js"></script> 
    <script src="assets/js/dataTables.bootstrap.min.js"></script>          
	
</body>
</html>
