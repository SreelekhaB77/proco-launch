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
<%
	String errorMsg = (String) request.getAttribute("errorMsg");
	String successMsg = (String) request.getAttribute("success");
%>
<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css"	href="assets/css/bootstrap-multiselect.css">
<link rel="stylesheet" type="text/css"	href="assets/css/proco-style.css">
<link rel="stylesheet" type="text/css"	href="assets/css/custom-font.css">
<link rel="stylesheet" type="text/css"	href="assets/css/jquery-ui.css">

<script type="text/javascript">
	history.pushState(null, null, '');
	window.addEventListener('popstate', function(event) {
		history.pushState(null, null, '');
	});
</script>
</head>
<body>
	<jsp:include page="../proco/proco-header.jsp" />
	
	<div class="loader">
	<center>
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
					<ul class="nav nav-pills nav-no-margin">

						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 proco-volume-active"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoVolumeUpload.htm">
								<div class="proco-volume-icon"></div>
								<div class="tab-label-proco-volume-active">Volume Upload</div>
						</a></li>
						
						<li role="presentation"
								class="col-md-3 col-sm-6 col-xs-12 disaggregation"><a
								href="http://34.120.128.205/VisibilityAssetTracker/promoDisaggregation.htm">
									<div class="proco-disaggregation-icon"></div>
									<div class="tab-label-proco-disaggregation-inactive">Disaggregation</div>
							</a></li>
						
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoListing.htm">
								<div class="proco-listing-icon"></div>
								<div class="tab-label-proco-listing-inactive OpenSans-font">Promo Listing</div>
						</a></li>

						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a href="http://34.120.128.205/VisibilityAssetTracker/promoDeletion.htm">
								<div class="proco-deletion-icon"></div>
								<div class="tab-label-proco-del-inactive OpenSans-font">Dropped Offer</div>
						</a></li>

					</ul>
				</div>
			</div>


		</div>
		<!--/.navbar-collapse -->
	</div>
	</nav>
	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="container-fluid container-bg middle-section">
	<div class="alert alert-success sucess-msg" id="successblock" style="display:none;margin-top:35px;">
			<button type="button" class="close" data-hide="alert">&times;</button>
			<%-- <c:out value="${success}"></c:out> --%>
			<span></span>
	</div>
	<c:if test="${FILE_STATUS=='SUCCESS_FILE'}">
			<div class="alert succ-alert-success sucess-msg" id="successblock"  style="margin-top:35px;">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${success}"></c:out>
			</div>
		</c:if>
		<c:if test="${errorMsg!=null}">
			<div class="alert err-alert-danger sucess-msg" id="errorblock"  style="margin-top:35px;" >
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${errorMsg}"></c:out>
				<c:if test="${FILE_STATUS=='ERROR_FILE'}">
					<a href="http://34.120.128.205/VisibilityAssetTracker/downloadPromotionVolumeErrorFile.htm" id="downloadTempFileLink">Click
						here to Download Error File:</a>

				</c:if>
			</div>
		</c:if>
	<!--bharati added code for errorblock with download error file for volume upload sprint-9-->
	                 				<div class="alert alert-success sucess-msg" style="display: none;margin-top:35px;margin-bottom: -23px" id="ProcoVolumesuccessblock">
		                                 <button type="button" class="close" data-hide="alert">&times;</button>
		                                 <span></span>
	                                </div>
                                    <div class="alert alert-danger" style="display: none;margin-top:35px;margin-bottom: -23px" id="ProcoVolumeerrorblockUpload">
                                        <button type="button" class="close" data-hide="alert">&times;</button>
                                       
                                            <span>File contains error...</span>
                                            <a href="http://34.120.128.205/VisibilityAssetTracker/downloadPromotionErrorFilensp.htm" id="downloadTempFileLink">Click here to Download Error File</a>
                                    </div>
									<div class="alert alert-danger" style="display: none;margin-top:35px;margin-bottom: -23px" id="errorblockVolumeUpload">
		                            <button type="button" class="close" data-hide="alert">&times;</button>
		
		                           <!-- <span>Error while uploading file.</span>-->
								   <span></span>
		
	                               </div>
								  
		
		<!--bharati code end here for sprint-9-->	
	
		<div class="proco-creation form-horizontal">
			<!--   <div class="promo-back"><a href="Home.html"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"> </span></a>Promo Volume Upload</div> -->
			<div class="promo-details" style="margin-top: 20px;">
				<span class="promo-detail-txt promo-vol-left"><b>SELECT PROMO VOLUME UPLOAD</b></span>

			</div>
			<form action="http://34.120.128.205/VisibilityAssetTracker/downloadPromosForVolumeUpload.htm" method="POST" enctype="multipart/form-data">
			<div class="promo-form-details proco-volume-form">
				<div class="col-md-4">
					<div class="form-group">
						<label for="unique-id" class="control-label col-md-4">CATEGORY</label>
						<div class="col-md-8">
							<select class="form-control" id="category" name="category">
								<option>ALL CATEGORIES</option>
								<c:forEach var="category" items="${categories}">
									<option value="${category}">${category}</option>
								</c:forEach>
							</select>
						</div>

					</div>
					<div class="form-group">
						<label class="control-label col-md-4" for="uom">CUST CHAIN
							(L1)</label>
						<div class="col-md-8">
							<select class="form-control" id="customerChainL1" name="customerChainL1"
								multiple="multiple">
								<c:forEach var="item" items="${customerChainL1}">
									<option value="${item}">${item}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label class="control-label col-md-4" for="uom">OFFER TYPE</label>
						<div class="col-md-8">
							<select class="form-control" id="offerType" name="offerType">
								<option>ALL TYPES</option>
								<c:forEach var="item" items="${offerTypes}">
									<option value="${item}">${item}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>

				<div class="col-md-4">

					<div class="form-group">
						<label for="unique-id" class="control-label col-md-4">BRAND</label>
						<div class="col-md-8">
							<select class="form-control" id="brand" name="brand">
								<option>ALL BRANDS</option>
								<c:forEach var="brand" items="${brands}">
									<option value="${brand}">${brand}</option>
								</c:forEach>
							</select>
						</div>

					</div>
					<div class="form-group">
						<label class="control-label col-md-4" for="uom">L2-CUST
							CHAIN (s)</label>
						<div class="col-md-8 switch-dynamic">
							<input type="text" class="form-control proco-volume-input" id="customerChainL2" name="customerChainL2"
								value="ALL CUSTOMERS" readonly="true"></input>
						</div>

					</div>

					<div class="form-group">
						<label class="control-label col-md-4" for="uom">MODALITY</label>
						<div class="col-md-8">
							<select class="form-control" id="modality" name="modality">
								<option>ALL MODALITIES</option>
								<c:forEach var="item" items="${modality}">
									<option value="${item.key}">${item.value}</option>
								</c:forEach>
							</select>
						</div>
					</div>


				</div>

				<div class="col-md-4">

					<div class="form-group">
						<label for="unique-id" class="control-label col-md-4">BASE PACK</label>
						<div class="col-md-8">
							<input type="text" id="promo_basepack" class="form-control" name="promoBasepack"
								placeholder="ALL PRODUCTS" />
						</div>

					</div>

					<div class="form-group">
						<label class="control-label col-md-4" for="uom">GEOGRAPHY</label>
						<div class="col-md-8">
							<!-- <select class="form-control" id="">
								<option>ALL INDIA</option>
							</select> -->
							<input type="text" class="form-control" value="ALL INDIA" name="geography"
								id="geography" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-4" for="uom">YEAR</label>
						<div class="col-md-3">
							<select class="form-control" id="year" name="year">
								<option>ALL YEARS</option>
								<c:forEach var="item" items="${years}">
									<option value="${item}">${item}</option>
								</c:forEach>
							</select>
						</div>

						<label class="control-label col-md-2" for="uom">MOC</label>
						<div class="col-md-3">
							<input type="text" class="form-control" value="FULL YEAR" name="moc"
								id="moc" />
						</div>
					</div>
				</div>

				<div class="clearfix"></div>
			</div>
			<!--bharati commented this btn in sprint-9-->
			<!--<div class="download-btn">
				<input type="submit" class="btn new-btn-download" value="PROMO DOWNLOAD"></input>
			</div>-->
			</form>
			<!--bharati added this below btn for dp volumn download in sprint-9-->
				<form:form action="http://localhost:8083/VisibilityAssetTracker/downloadDpVolume.htm" id="getPromoVolumnUploadTemplate"
				method="GET" modelAttribute="VolumeUploadBean"
				enctype="multipart/form-data" style="text-align:center">
				<button class="btn new-btn-download" style="margin: 10px;float: right;color:#fff;">PROMO DOWNLOAD</button>
			</form:form>
			<div class="clearfix"></div>
			<!--bharati changes end for sprint-9-->
			<!-- Listing table -->
			<div class="promo-list table-header-volume">PROMO LIST</div>
			
			<table class="table table-striped table-bordered promo-list-table"
				style="width: 100%;overflow-x: scroll;display: block;">
				<thead>
				<!--bharati commented this below columns for dp volumn listing in sprint-9-->
					<tr>
						<th>ACTION</th>
						<th>PROMO ID</th>
						<!--<th>ORIGINAL ID</th>-->
						<th>START DATE</th>
						<th>END DATE</th>
						<th>MOC</th>
						<th>CUSTOMER CHAIN L1</th>
						<!--<th>SALES CATEGORY</th>-->
						<th>BASEPACK</th>
						<th>OFFER DESCRIPTION</th>
						<th>OFFER TYPE</th>
						<th>OFFER MODALITY</th>
						<th>GEOGRAPHY</th>
						<th>QUANTITY</th>
						<!--<th>UOM</th>-->
						<th>OFFER VALUE</th>
						<!--<th>KITTING VALUE</th>-->
						<th name="stat">STATUS</th>
						<!--<th>REASON</th>
						<th>REMARK</th>-->
						<!--bharati addedbelow columns in sprint-9-->
						<th>INVESTMENT TYPES</th>
						<th>SOL CODE</th>
						<th>PROMOTION MECHANICS</th>
						<th>SOL CODE STATUS</th>
					</tr>
				</thead>
			</table>
			<!--Bharati change below url for dp volumn upload in sprint-9-->
			<%--<form:form action="http://34.120.128.205/VisibilityAssetTracker/uploadPromoVolume.htm" id="promoVolumeUpload"
				method="POST" modelAttribute="VolumeUploadBean"
				enctype="multipart/form-data" onsubmit="return uploadValidation()">--%>
				
			<form:form action="#" id="promoVolumeUpload" modelAttribute="VolumeUploadBean" 
				enctype="multipart/form-data" onsubmit="return uploadValidation()">
				
			<div class="promo-upload">PROMO UPLOAD</div>
			<div class="upload-file">
			
			
				<div class="col-md-4 col-md-offset-4" style="padding: 30px 0px;">
					<div class="upload-label">
						<span><i class="glyphicon glyphicon-cloud-upload"></i></span> <span>
							<b class="SEGOEUIL-font">Volume Upload File</b>
						</span>
					</div>
					<div class="upload-group">


						<div class="cust-file" style="float: right;">
							<span class="btn btn-upload " id="choose-file">Choose
									File</span>
						</div>

						<input type="file" class="form-control" value="" name="file"
							id="upload-file">

						<div class="file-name" style="line-height: 2.3">No file
							chosen</div>
					</div>
                    <span id="uploadErrorMsg" style="display: none; color: red">Please upload .xls or .xlsx file</span>
					<div class="" style="color: #fff; text-align: center">
						<button id="PromoVolumeUpload" class="btn new-btn-primary">UPLOAD</button>

					</div>
				</div>

				<div class="clearfix"></div>
			</div>
		</form:form>
              
		</div>
		
	</div>
	<jsp:include page="../proco/proco-footer.jsp" />
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<!-- Latest compiled and minified JavaScript -->
	<script src="assets/js/jquery-1.11.3.min.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="assets/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="assets/js/dataTables.bootstrap.min.js"></script>
	<script type="text/javascript" src="assets/js/comboTreePlugin.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap-multiselect.js"></script>
	<script type="text/javascript" src="assets/js/jquery-ui.js"></script>
	<script type="text/javascript" src="assets/js/custom/proco/promoVolumeUpload.js"></script>
	<script type="text/javascript" src="assets/js/custom/proco/alert-modal.js"></script>
	
	<script type="text/javascript">
		var moc = '${mocJson}';
		var geographyData = '${geographyJson}';
		var basepacks = '${basepacks}';
		var newBasepacks = null;
		var custChainL2 = null;
		/* Global variables */
		var category = null;
		var brand = null;
		var basepack = null;
		var custChainL1 = null;

		var offerType = null;
		var modality = null;
		var year = null;
		var geography = null;
		var mocVal = null;
	</script>
</body>
</html>