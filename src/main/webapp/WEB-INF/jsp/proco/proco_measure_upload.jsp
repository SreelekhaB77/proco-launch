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
   line-height: 2.9!important;
  font-size: 1.4em!important;
}
.nav-pills>li+li {
margin-left:0px!important;
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
					
					<li role="presentation" class="col-md-3 col-sm-6 col-xs-12 proco-listing-inactive">
						<a href="https://vat.hulcd.com/VisibilityAssetTracker/promoStatusTracker.htm">
								<div class="proco-listing-icon"></div>
								<div class="tab-label-proco-status-inactive OpenSans-font">Promo Status Tracker</div>
						</a></li>
						
						<li role="presentation" class="col-md-3 col-sm-6 col-xs-12 listing">
						<a href="https://vat.hulcd.com/VisibilityAssetTracker/promoDeletion.htm" style="margin-top: 10px;margin-left: -23px;">
								<div class="proco-deletion-icon"></div>
								<div class="tab-label-proco-del-inactive OpenSans-font">Dropped Offer</div>
						</a></li>
						
						<li role="presentation"	class="col-md-3 col-sm-6 col-xs-12 promo-lib-bg-active">
							<a href="https://vat.hulcd.com/VisibilityAssetTracker/ProcoMeasureReportUploadPage.htm" style="margin-top: 10px;">
								<div class="proco-Signops-icon"></div>
								<div class="tab-label-proco-Signops-active OpenSans-font">Upload Measure Report</div>
							</a>
						</li>
						<li role="presentation"	class="col-md-3 col-sm-6 col-xs-12 promo-ppm-inactive" style="margin-top: 10px;width:23%">
							<a href="https://vat.hulcd.com/VisibilityAssetTracker/ProcoPpmCoeRemarks.htm">
								<div class="proco-ppm-icon"></div>
								<div class="tab-label-proco-ppm-inactive OpenSans-font">PPM Upload</div>
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
					<a href="https://vat.hulcd.com/VisibilityAssetTracker/downloadPromotionEditErrorFile.htm" id="downloadTempFileLink">Click
						here to Download Error File:</a>

				</c:if>
			</div>
		</c:if>
		
		<!--bharati added code for errorblock and successblock for sprint-9 US-15-->
	                 				<div class="alert alert-success sucess-msg" style="display: none;margin-top:35px;" id="ppmsuccessblock">
		                                 <button type="button" class="close" data-hide="alert">&times;</button>
		                                 <span></span>
	                                </div>
                                   
									<div class="alert alert-danger" style="display: none;margin-top:35px;" id="ppmerrorblockUpload">
		                            <button type="button" class="close" data-hide="alert">&times;</button>
		
		                           <!-- <span>Error while uploading file.</span>-->
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
			
				
		
		
		 <div class="promo-measure-upload">PROCO MEASURE REPORT UPLOAD</div>
							<div class="row">
								<div class="col-md-6 col-sm-6 ddd">
								<!--bharati changes this below form for sprint-9 US-15 ppminkageupload-->
									<form id="coeStatusMeasFileUpload" class="form-horizontal" action="#" enctype="multipart/form-data" name="coeStatusFileUpload">
		
									<div class="launchupload-parent">
										<div class="proco-measure-upload-wrapper">
											<div style="text-align: center; color: #878787;">
			
												<h2 class="SEGOEUIL-font">Measure Report Upload File</h2>
												<div class="upload-image">
													<i class="fa fa-upload" aria-hidden="true"></i>
												</div>
			
												<div class="upload-max-size">Maximum Upload File Size
													:10MB</div>
												<span id="uploadErrorMeaMsg" style="display: none; color: red"></span>
												
												<div class="input-group upload-status-files">
													<input id="uploadmeasscre" name="file" type="file" class="file">
												</div>
												<input class="validate_upload btn marginT10 new-btn-primary" type="submit" id="btnSubmitBasePack1" value="Upload" />
												<!--<a href="https://vat.hulcd.com/VisibilityAssetTracker/downloadSampleMeasureReport.htm" class="validate_upload btn marginT10 new-btn-download" id="btnSubmitBasePack">Promo Measure Template</a>-->
												<!--bharati commented above line and added below line for measure report download in sprint-9-->
											<a href="https://vat.hulcd.com/VisibilityAssetTracker/promoMeasureDownloadTemplate.htm" class="validate_upload btn marginT10 new-btn-download" id="btnSubmitBasePack">Promo Measure Template</a>
											</div>
										</div>
									</div>
								</form> 
							</div>
							<div class="col-md-6 col-sm-6 ddd">
							<!--bharati commented this form form sprint-9 moc changes-->
							 	<!--<form style="margin-top:45px;" action="https://vat.hulcd.com/VisibilityAssetTracker/downloadMeasureReport.htm" id="download-measure-report" method="POST" onsubmit="downloadMeasureReport(event)">
							 		<div class="launchupload-parent">
										<div class="proco-measure-download-wrapper">
											<h2 class="SEGOEUIL-font">Measure Report Download File</h2>
											<div class="upload-image">
												<i class="fa fa-download" aria-hidden="true"></i>
											</div>
											<div class="">
												<input placeholder="Select MOC" autocomplete="off" id="moc-filter" name="moc-filter" class="form-control" />
												<input id="MocYear" name="MocYear" type="hidden" class="form-control" />
												<input id="MocMonth" name="MocMonth" type="hidden" class="form-control" />
											</div>
											<input class="validate_dowload btn marginT10 new-btn-download" type="submit" value="Download" />
										</div>
									</div>
								</form>-->
								
								<form style="margin-top:45px;" action="https://vat.hulcd.com/VisibilityAssetTracker/dpMesureDownloadBasedOnMoc.htm" id="download-measure-report" method="POST">
							 		<div class="launchupload-parent">
										<div class="proco-measure-download-wrapper">
											<h2 class="SEGOEUIL-font">Measure Report Download File</h2>
											<div class="upload-image">
												<i class="fa fa-download" aria-hidden="true"></i>
											</div>
											<p id="selectMsgMoc" style="display: none; color: red; margin-left: 17px;">Please Select the MOC.</p>
											<div class="form-group col-sm-12" style="margin-top: 0px;">
											
						<label for="unique-id" class="control-label col-md-2" style="margin-top:9px;">MOC</label>
						<div class="col-md-9">
						<select class="form-control" id="Mocvalue1" name="Mocvalue1">
							</select>
								 </div>
                         </div>
					<input class="validate_dowload btn marginT10 new-btn-download" style="margin-top:0px;" onclick="javascript: downloadMeasureReport();" type="button"  value="Download" />
										</div>
									</div>
								</form>
							</div>
						</div>

					
					
					
	<%-- <form:form action="https://vat.hulcd.com/VisibilityAssetTracker/uploadProcoMeasureReport.htm" id="coeStatusMeasFileUpload"
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