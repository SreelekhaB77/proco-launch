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
<link rel="stylesheet" href="assets/css/bootstrapValidator.css">
<!-- <link rel="stylesheet" type="text/css" href="assets/css/bootstrap-multiselect.css">
<link rel="stylesheet" type="text/css" href="assets/css/fileinput.css"> -->
<link rel="stylesheet" href="assets/css/launchstyle.css"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="assets/css/custom-font.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/font-awesome.css">
<link rel="stylesheet"
	href="assets/css/smart_wizard_theme_arrows.css" type="text/css">
<!-- <link rel="stylesheet"
	href="assets/css/smart_wizard_theme_arrows.min.css" type="text/css">
<link rel="stylesheet" href="assets/css/smart_wizard.css"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="assets/css/fixedColumns.bootstrap.min.css"> -->


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
								class="active col-md-2 col-sm-8 col-xs-12 launch-icon-inactive">
								<a href="http://localhost:8083/VisibilityAssetTracker/getAllCompletedLaunchDataKam.htm"> <!-- <div class="launch-icon"></div> -->
									<div class="tab-label-launch">Launch Details</div>
							</a>
							</li>
							<li role="presentation"
								class="active col-md-2 col-sm-8 col-xs-12 launch-icon-active">
								<a href="http://localhost:8083/VisibilityAssetTracker/getApprovalStatusKam.htm"> <!-- <div class="launch-icon"></div> -->
									<div class="tab-label-launch">Approval Status</div>
							</a>
							</li>
							<li role="presentation"
								class="active col-md-2 col-sm-8 col-xs-12 launch-icon-inactive">
								<a href="#"> <!-- <div class="launch-icon"></div> -->
									<div class="tab-label-launch">Performance</div>
							</a>
							</li>
							<li role="presentation"
								class="active col-md-2 col-sm-8 col-xs-12 launch-icon-inactive">
								<a href="#"> <!-- <div class="launch-icon"></div> -->
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
		<%-- <c:if test="${success_file_upload!=null}">
			<div class="alert alert-success sucess-msg" id="successblock"
				style="display: none;">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${success_file_upload}"></c:out>
			</div>
		</c:if> --%>
		<%-- </c:if> --%>
		<%-- <c:if test="${errorMsg!=null}">
			<div class="alert alert-danger sucess-msg" id="errorblock">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${errorMsg}"></c:out>
			</div>
		</c:if> --%>
		<%-- <c:if test="${errorMsg!=null}">
			<div class="alert alert-danger  sucess-msg" id="errorblock"
				style="display: none;">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${errorMsg}"></c:out>
				<a href="#" id="downloadTempFileLink"
					onclick="javascript:downloadCoeErrorFile();">Click here to
					Download Error File:</a>

			</div>
		</c:if> --%>
		<%-- 
		<div class="alert alert-danger marginT15" id="kamerrorblockUpload"
			style="display: none;">
			<button type="button" class="close" data-hide="alert">&times;</button>
			<c:out value="${errorMsgUpload}"></c:out>
			<span> File does not contains any data.</span>
			<!--  <a href="downloadTmeUploadErrorFile.htm" id="downloadTempFileLink">Click here to Download Error File</a> -->
		</div> --%>

		<div id="step-1" class="tab_content" data-blockid="1">
			<div class="card-header" role="tab" id="headingOne">
				<div class="mb-0">
					<a data-toggle="collapse" data-parent="#accordion"
						href="#collapseOne" aria-expanded="false"
						aria-controls="collapseOne" class="collapsed"> <span>CHANGE
							REQUEST</span>
					</a>
				</div>
			</div>

			<div id="collapseOne" class="collapse" role="tabpanel"
				aria-labelledby="headingOne" aria-expanded="false"
				style="height: 0px; display: block;">
				<div class="card-block">
					<div class="child-table">
						<div class="table-responsive detail_table">
							<table class="table table-striped table-bordered"
								id="kambasepack_add" cellspacing="0" cellpadding="0"
								style="width: 100% ! important">
								<thead class="thead-dark">
									<tr>
										<th><span class="proco-btn proco-btn-success table-head">Launch
												Name</span></th>
										<th><span class="proco-btn proco-btn-success table-head">Launch
												MOC</span></th>
										<th><span class="proco-btn proco-btn-success table-head">Account</span></th>
										<th><span class="proco-btn proco-btn-success table-head">Request
												Date</span></th>
										<th><span class="proco-btn proco-btn-success table-head">Changes
												Requested</span></th>
										<th><span class="proco-btn proco-btn-success table-head">KAM
												Remarks</span></th>
										<th><span class="proco-btn proco-btn-success table-head">CMM</span></th>
										<th><span class="proco-btn proco-btn-success table-head">Response
												Date</span></th>
										<th><span class="proco-btn proco-btn-success table-head">Approval
												Status</span></th>
										<th><span class="proco-btn proco-btn-success table-head">CMM
												Remarks</span></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${kamApprovalList}" var="approvalList">
										<tr>
											<td>${approvalList.launchName}</td>
											<td class="mocDate">${approvalList.launchMoc}</td>
											<td>${approvalList.account}</td>
											<td>${approvalList.reqDate}</td>
											<td>${approvalList.changeRequested}</td>
											<td>${approvalList.kamRemarks}</td>
											<td>${approvalList.cmm}</td>
											<td>${approvalList.responseDate}</td>
											<td>${approvalList.approvalStatus}</td>
											<td>${approvalList.cmmRemarks}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

					</div>
				</div>
			</div>
		</div>


		<%@include file="../launchplan/footer.jsp"%>


		<script type="text/javascript" src="assets/js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="assets/js/comboTreePlugin.js"></script>
		<script type="text/javascript"
			src="assets/js/bootstrapValidator.js"></script>
		<script type="text/javascript" src="assets/js/jquery-ui.js"></script>
		<script type="text/javascript" src="assets/js/fileinput.js"></script>
		<script type="text/javascript"
			src="assets/js/bootstrap-multiselect.js"></script>
		<script type="text/javascript"
			src="assets/js/custom/launch/kamLaunchscript.js"></script>
		<script src="assets/js/jquery-ui.js"></script>
		<script type="text/javascript"
			src="assets/js/custom/proco/alert-modal.js"></script>
		<!--  <script type="text/javascript" src="assets/js/jquery.smartWizard.js"></script>  -->
		<script type="text/javascript"
			src="assets/js/jquery.smartWizard.min.js"></script>
		<script src="assets/js/jquery.dataTables.min.js"></script>
		<script src="assets/js/dataTables.fixedColumns.min.js"></script>
		<script src="assets/js/dataTables.bootstrap.min.js"></script>
</body>
</html>
