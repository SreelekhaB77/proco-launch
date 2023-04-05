<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MT PLANNING</title>
</head>
<body>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<!--  <link rel="icon" href="../../favicon.ico"> -->

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css"	href="assets/css/bootstrap-multiselect.css">
<link rel="stylesheet" type="text/css"	href="assets/css/proco-style.css">
<link rel="stylesheet" type="text/css"	href="assets/css/custom-font.css">

<link rel="stylesheet" type="text/css" 	href="assets/css/fileinput.css" />

<link rel="stylesheet" type="text/css"	href="assets/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="assets/css/font-awesome.css">
<style>
footer {
	position: initial !important;
}
.launchupload-parent {
   
    min-height: 330px!important;
}
.proco-measure-upload-wrapper h2, .proco-measure-download-wrapper h2 {
font-size: 22px!important;
}
.promo-lib-bg-active a {
   
    line-height: 2.9!important;
    font-size: 1.5em!important;
	
}
.tab-label-proco-status-inactive {
   line-height: 1.4!important;
  font-size: 1.5em!important;
}
</style>
</head>
<body class="Verdana-font">
	<jsp:include page="../proco/proco-header.jsp" />
	<div class="loader">
		<center style="margin-top: 202px;">
			<img class="loading-image" src="assets/images/spinner.gif"
				alt="loading..." style="height: 150px; width: auto;">
		</center>
	</div>

	<nav class="navbar navbar-inverse navbar-fixed-top container-bg"
		style="position:relative;top: 115px;z-index: 2;background-image: none! important;
    border: none! important;background: #F6F3F3;
    padding: 4px 0px 20px 0px;">
	<div class="container-fluid paddR10">
		<div class="navbar-header marginB10">
			<h1 class="pull-left" style="color: #000000;">
				Promotion Planning Tool
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
			<div class="">
				<div class="row">
					<ul class="nav nav-pills">
					
					<li role="presentation" class="col-md-3 col-sm-6 col-xs-12 proco-listing-inactive" style="margin-left:-15px; margin-right:-20px;">
						<a href="http://34.120.128.205/VisibilityAssetTracker/promoStatusTracker.htm">
								<div class="proco-listing-icon"></div>
								<div class="tab-label-proco-status-inactive OpenSans-font">Promo Status Tracker</div>
						</a></li>
						
						<li role="presentation" class="col-md-3 col-sm-6 col-xs-12 listing" style= "width:234px;">
						<a href="http://34.120.128.205/VisibilityAssetTracker/promoDeletion.htm" style="margin-top: 10px;margin-left: -18px;">
								<div class="proco-deletion-icon"></div>
								<div class="tab-label-proco-del-inactive OpenSans-font">Dropped Offer</div>
						</a></li>
						
						<li role="presentation"	class="col-md-3 col-sm-6 col-xs-12 promo-lib-bg" style="margin-top: 10px; margin-right: 15px;">
							<a href="http://34.120.128.205/VisibilityAssetTracker/ProcoMeasureReportUploadPage.htm" >
								<div class="proco-Signops-icon"></div>
								<div class="tab-label-proco-Signops-inactive OpenSans-font">Upload Measure Report</div>
							</a>
						</li>
						<li role="presentation"	class="col-md-3 col-sm-6 col-xs-12 promo-ppm-active"  style="margin-top: 10px;width:18%">
							<a href="http://34.120.128.205/VisibilityAssetTracker/ProcoPpmCoeRemarks.htm">
								<div class="proco-ppm-icon"></div>
								<div class="tab-label-proco-ppm-active OpenSans-font">PPM Upload</div>
							</a>
						</li>
					<!----	<li role="presentation"	class="col-md-3 col-sm-6 col-xs-12 timeline" style="margin-top: 10px;width:19%">
							<a href="http://34.120.128.205/VisibilityAssetTracker/procoTimeline.htm"style="width: 220px;">
								<div class="proco-timeline-icon "></div>
								<div class="tab-label-proco-timeline-inactive OpenSans-font">Promo Timeline</div>
							</a>
						</li> -->
						<li role="presentation"	class="col-md-3 col-sm-6 col-xs-12 budget" style="width:19%;margin-top: 10px;">
							<a href="http://34.120.128.205/VisibilityAssetTracker/procoBudget.htm" style="width:247px;">
								<div class="proco-budget-icon "></div>
								<div class="tab-label-proco-budget-inactive OpenSans-font">BUDGET REPORT</div>
							</a>
						</li>
					</ul>
				</div>
				<div class="row">
					<ul class="nav nav-pills">
						<li role="presentation"	class="col-md-3 col-sm-6 col-xs-12 visibility-bg"  style="margin-top: 10px;width:21%">
							<a href="http://34.120.128.205/VisibilityAssetTracker/procovisibilityUpload.htm" style="margin-left: 6%;">
								<div class="proco-ppm-icon"></div>
								<div class="tab-label-visi-upload-inactive OpenSans-font">Visibility Upload</div>
							</a>
						</li>
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 ab-creation"
							style="margin-top: 10px; width: 19%"><a
							href="http://34.120.128.205/VisibilityAssetTracker/procoABCreationPage.htm">
								<div class="proco-ppm-icon"></div>
								<div class="tab-label-ab-creation-upload-inactive OpenSans-font">AB Creation Report
									Upload</div>
						</a>
						</li>
					</ul>
				</div>
					
						
			</div>


		</div>
		<!--/.navbar-collapse -->
	</div>
	</nav>

	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="container-fluid container-bg middle-section">
	
	<c:if test="${FILE_STATUS=='SUCCESS_FILE'}">
			<div class="alert succ-alert-success sucess-msg" id="successblock"
				style="display: block" style="margin-top:35px;">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${success}"></c:out>
			</div>
		</c:if>
		<c:if test="${errorMsg!=null}">
			<div class="alert err-alert-danger sucess-msg" id="errorblock" style="margin-top:35px;">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${errorMsg}"></c:out>
				<c:if test="${FILE_STATUS=='ERROR_FILE'}">
					<a href="http://34.120.128.205/VisibilityAssetTracker/downloadPromotionEditErrorFile.htm" id="downloadTempFileLink">Click
						here to Download Error File:</a>

				</c:if>
			</div>
		</c:if>
		
		
	                 				<div class="alert alert-success sucess-msg" style="display: none;margin-top:35px;" id="ppmcoesuccessblock">
		                                 <button type="button" class="close" data-hide="alert">&times;</button>
		                                 <span></span>
	                                </div>
                                   
									<div class="alert alert-danger" style="display: none;margin-top:35px;" id="ppmcoeerrorblockUpload">
		                            <button type="button" class="close" data-hide="alert">&times;</button>
		
		                         
								   <span></span>
		
	                               </div>
								  
		
		<!--bharati code end here for sprint-9-->
		
		<div class="alert err-alert-danger" id="MocDownloadErorblockUpload" style="display:none; margin-top:35px;" data-hide="alert">
			  <a href="#" class="close new-close-btn" aria-label="close">&times;</a>
			  <strong>Error! </strong> <span>Please select the MOC.</span>
		</div>
               	
         	<div class="alert succ-alert-success alert-dismissible" id="successblockUpload" style="display:none;margin-top:35px;">
			  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			  <strong>Success! </strong> File uploaded successfully.
			</div>
			<div class="alert err-alert-danger" id="erorblockUpload" style="display:none;margin-top:35px;" data-hide="alert">
			  <a href="#" class="close" aria-label="close">&times;</a>
			  <strong>Error! </strong> <span>File does not contain any data.</span>
			</div>
		<div class="proco-creation form-horizontal">
			<input type="hidden" id="roleId" value="${roleId}" />
			
		</div>
			
				
		
		
		 <div class="promo-measure-upload">PPM COE REMARKS UPLOAD</div>
							<div class="row">
								<div class="col-md-6 col-sm-6 ddd">
								<!--bharati changes this below form for sprint-9 US-15 ppminkageupload-->
									<form id="ppmCoeRemarkUpload" class="form-horizontal" action="#" enctype="multipart/form-data" name="coeStatusFileUpload">
		
									<div class="launchupload-parent">
										<div class="proco-measure-upload-wrapper">
											<div style="text-align: center; color: #878787;">
			
												<h2 class="SEGOEUIL-font">PPM COE Remarks Upload</h2>
												<div class="upload-image">
													<i class="fa fa-upload" aria-hidden="true"></i>
												</div>
			
												<div class="upload-max-size">Maximum Upload File Size
													:4MB</div>
												<span id="uploadppmErrorMeaMsg" style="display: none; color: red"></span>
												
												<div class="input-group upload-status-files">
													<input id="uploadppmCoeRemark" name="file" type="file" class="file">
												</div>
												<input class="validate_upload btn marginT10 new-btn-primary" type="submit" id="btnSubmitPpmCoeRemark" value="Upload" />
												
											<a href="http://34.120.128.205/VisibilityAssetTracker/ppmCoeRemarksDownloadTemplate.htm" class="validate_upload btn marginT10 new-btn-download" id="btnSubmitBasePack">PPM COE Remarks Download Template</a>
											</div>
										</div>
									</div>
								</form> 
							</div>
							<div class="col-md-6 col-sm-6 ddd">
							<form style="margin-top:45px;" action="http://34.120.128.205/VisibilityAssetTracker/dpMesureDownloadBasedOnMoc.htm" id="download-measure-report" method="POST">
							 		<div class="launchupload-parent">
										<div class="proco-measure-download-wrapper">
											<h2 class="SEGOEUIL-font">PPM Download</h2>
											<div class="upload-image">
												<i class="fa fa-download" aria-hidden="true"></i>
											</div>
											<p id="selectppmMsgMoc" style="display: none; color: red; margin-left: 17px;">Please Select the MOC.</p>
											<div class="form-group col-sm-12" style="margin-top: 0px;">
											
						<label for="unique-id" class="control-label col-md-2" style="margin-top:9px;">MOC</label>
						<div class="col-md-9">
						<select class="form-control" id="ppmMocvalue" name="ppmMocvalue">
							</select>
								 </div>
                         </div>
					<input class="validate_dowload btn marginT10 new-btn-download" style="margin-top:0px;" onclick="javascript: downloadPpmReport();" type="button"  value="Download" />
										</div>
									</div>
								</form>
							</div>
						</div>

					
					
					
	<%-- <form:form action="http://34.120.128.205/VisibilityAssetTracker/uploadProcoMeasureReport.htm" id="coeStatusMeasFileUpload"
				method="POST" modelAttribute="VolumeUploadBean"
				enctype="multipart/form-data" onsubmit="return uploadMeasureValidation()">
			<div class="promo-upload">PROMO MEASURE UPLOAD</div>
			<div class="upload-file">
				<div class="col-md-4 col-md-offset-4" style="padding: 30px 0px;">
					<div class="upload-label">
						<span><i class="glyphicon glyphicon-cloud-upload"></i></span> <span>
							<b class="SEGOEUIL-font">Upload Promo Measure File</b>
						</span>
					</div>
					<div class="upload-group">


						<div class="cust-file" style="float: right;">
							<span class="btn btn-upload " id="choose-file">Choose File</span>
						</div>

						<input type="file" class="form-control" value="" name="file" id="upload-ms-file">

						<div class="file-ms-name" style="line-height: 2.3">No file chosen</div>
					</div>

					<div class="" style="color: #fff; text-align: center">
						<button class="btn btn-primary">UPLOAD</button>

					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</form:form> --%>
		</div>
		<jsp:include page="../proco/proco-footer.jsp" />

		<!-- Bootstrap core JavaScript
    ================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<!-- Latest compiled and minified JavaScript -->
		<script src="assets/js/jquery-1.11.3.min.js"></script>
		<script src="assets/js/jquery.1.4.4.js"></script>
		<script type="text/javascript">
		var jQuery_1_4_4 = $.noConflict(true);
		</script>
		<script src="assets/js/bootstrap.min.js"></script>
			<script src="assets/js/jquery.dataTables.min.js"></script>
		<script src="assets/js/dataTables.bootstrap.min.js"></script>
		<script type="text/javascript" src="assets/js/comboTreePlugin.js"></script>
		<script type="text/javascript" src="assets/js/bootstrap-multiselect.js"></script>
		<script type="text/javascript" src="assets/js/jquery-ui.js"></script>
		<script type="text/javascript" src="assets/js/fileinput.js"></script>
		<script type="text/javascript" src="assets/js/custom/proco/promoStatusTracker.js"></script>
		<script type="text/javascript" src="assets/js/comboTreePlugin.js"></script>
		<script type="text/javascript"
		src="assets/js/custom/proco/alert-modal.js"></script>
		<script type="text/javascript">

		var MeasureUploadResponse = '${MeasureUploadResponse}';
		if(MeasureUploadResponse.trim() != "" && MeasureUploadResponse != "null"){
			displayUploadStatus(MeasureUploadResponse);
		}
		
		var DownloadMocList = '${DownloadMocList}';
		var moc = '${mocJson}';
		moc = moc == "" ? "[]" : moc;
		var geographyData = '${geographyJson}';
		geographyData = geographyData == "" ? "[]" : geographyData;
		var basepacks = '${basepacks}';
		var newBasepacks = null;
		var custChainL2 = null;
		/* Global variables */
		var category = null;
		var brand = null;
		var basepack = null;
		var custChainL1 = null;
		var promoId = null;
		var offerType = null;
		var modality = null;
		var year = null;
		var geography = null;
		var mocVal = null;
		jQuery.fn.dataTableExt.oApi.fnPagingInfo = function(oSettings) {
              return {
                  "iStart": oSettings._iDisplayStart,
                  "iEnd": oSettings.fnDisplayEnd(),
                  "iLength": oSettings._iDisplayLength,
                  "iTotal": oSettings.fnRecordsTotal(),
                  "iFilteredTotal": oSettings.fnRecordsDisplay(),
                  "iPage": oSettings._iDisplayLength === -1 ?
                      0 : Math.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength),
                  "iTotalPages": oSettings._iDisplayLength === -1 ?
                      0 : Math.ceil(oSettings.fnRecordsDisplay() / oSettings._iDisplayLength)
              };
          };

		</script>
</body>
</body>
</html>