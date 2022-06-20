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

</head>
<!--bharati added below style for sprint-9 moc filter changes -->
<style>
.promo-filter-div{
float: right!important;
width: 30%!important;
margin-top: -75px!important;
}

#Mocvalue{
	height:30px!important;
}
.ddd{
	margin-bottom:5%!important;
}
@media only screen and (max-width: 767px) {
	.promo-filter-div{
    width: 60%!important;
    margin-top: -85px!important;	
}
}

</style>
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
		<div class="navbar-header" style="margin-bottom:17px;">
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
					
					<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 proco-tracker-active"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoStatusTracker.htm">
								<div class="proco-tracker-icon"></div>
								<div class="tab-label-proco-create-active OpenSans-font">Promo Status Tracker</div>
						</a></li>
						
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a href="http://34.120.128.205/VisibilityAssetTracker/promoDeletion.htm">
								<div class="proco-deletion-icon"></div>
								<div class="tab-label-proco-del-inactive OpenSans-font">Dropped Offer</div>
						</a></li>
						
						  <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 promo-lib-bg">
							<a href="http://34.120.128.205/VisibilityAssetTracker/ProcoMeasureReportUploadPage.htm" id="measHref" >
								<div class="proco-Signops-icon"></div>
								<div class="tab-label-proco-Signops-inactive OpenSans-font">Upload Measure Report</div>
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
				style="display: block;margin-top:35px;">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${success}"></c:out>
				<span></span>
			</div>
		</c:if>
		<c:if test="${errorMsg!=null}">
			<div class="alert err-alert-danger sucess-msg" id="errorblock" style="margin-top:35px;" >
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${errorMsg}"></c:out>
				<c:if test="${FILE_STATUS=='ERROR_FILE'}">
					<a href="http://34.120.128.205/VisibilityAssetTracker/downloadPromotionEditErrorFile.htm" id="downloadTempFileLink">Click
						here to Download Error File:</a>

				</c:if>
			</div>
		</c:if>
	
        <div class="alert err-alert-danger error-msg" id="promoSelectErrorMsg" style="display:none">
             	<button type="button" class="close" data-hide="alert">&times;</button>
                <span>Please select Promotion. </span>
         		</div>
 <!--             <div class="alert succ-alert-success sucess-msg" id="successblock1" style="display:none">
             	<button type="button" class="close" data-hide="alert">&times;</button>
                <span>Promotion Uploaded Successfully</span>
         	</div> -->
         	
         	<%-- <div class="alert alert-success sucess-msg"	id="successblockUpload" style="display:none">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<c:out value="${success}"></c:out>
				<span></span>
			</div> --%>
			
			<div class="alert succ-alert-success" id="successblockUpload" style="display:none;" data-hide="alert">
			  <a href="#" class="close" aria-label="close">&times;</a>
			  <strong>Success! </strong> File uploaded successfully.
			</div>
			<div class="alert err-alert-danger" id="erorblockUpload" style="display:none;" data-hide="alert">
			  <a href="#" class="close" aria-label="close">&times;</a>
			  <strong>Error! </strong> <span>File does not contain any data.</span>
			</div>
		<div class="proco-creation form-horizontal">
			<input type="hidden" id="roleId" value="${roleId}" />
			<!-- <div class="promo-back"><a href="http://34.120.128.205/VisibilityAssetTracker/procoHome.htm"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"> </span></a>Promo Listing</div> -->
			<!--<div class="promo-details" style="padding:10px;"><span style="color:#fff;font-weight:700;">PROMO STATUS TRACKER</span>--><!--bharati commented in sprint-9-->
				<!-- <span class="promo-detail-txt"><b>SELECT PROMO LISTING</b></span> <span
					class="pull-right promo-uom">
					<div class="col-md-12">
						<div class="form-group" style="margin-bottom: 1px;">
							<label class="control-label col-md-5" for="uom">SEARCH</label>
							<div class="col-md-7" style="padding: 0px;">
								<input type="text" class="form-control" id=""
									style="width: 70%;">
								<div class="search-field">
									<img src="assets/images/main-search.png" alt="">
								</div>
							</div>

						</div>
					</div>
				</span>
				<div class="clearfix"></div> -->
			</div>
			<!--bharati comment this below form in sprint-9-->
		<!--<form action="http://34.120.128.205/VisibilityAssetTracker/downloadPromoStatusTracker.htm" method="POST" enctype="multipart/form-data" id="download">
			<div class="promo-form-details proco-tracker-form">
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
					<div class="form-group">
						<label class="control-label col-md-4" for="uom">PROMO ID</label>
						<div class="col-md-8">
							<select class="form-control" id="promoIds" name="promoIds">
								<option>ALL PROMOS</option>
								<c:forEach var="item" items="${promoIds}">
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
							<input type="text" class="form-control proco-tracker-input" id="customerChainL2" name="customerChainL2"
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
						<label for="unique-id" class="control-label col-md-4">BASE
							PACK</label>
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
						<!--	<input type="text" class="form-control" value="ALL INDIA" name="geography"
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
		</form>-->
			<div class="promo-list table-header-tracker">PROMO LIST</div>
			<!--bharati added this below form for moc filter-->
			<form action="http://localhost:8083/VisibilityAssetTracker/downloadPromoStatusTracker.htm" method="POST" enctype="multipart/form-data" id="download">
			<div class="form-group col-sm-4" style="margin-top: 20px;">
						<label for="unique-id" class="control-label col-md-2">MOC</label>
						<div class="col-md-5">
						<select class="form-control" id="Mocvalue" name="Mocvalue">
								 <c:forEach items="${mocList}" var="mocValue">
                                   <option value="${mocValue}"><c:out value="${mocValue}"></c:out></option>
                                 </c:forEach>
                                 </select>
								 </div>

					</div>
			</form>
			<form>
			<!--bharati removed below columns form table in sprint-9-->
			<table class="table table-striped table-bordered promo-list-table table-responsive" cellspacing="0"  style="width: 100%;">
				<thead>
					<tr>
						<th>PROMO ID</th>
						<!--<th>ORIGINAL ID</th>-->
						<th>START DATE</th>
						<th>END DATE</th>
						<th>MOC</th>
						<!--<th>CUSTOMER CHAIN L1</th>--> <!--bharati rename this to ppm account in sprint-9-->
						<th>PPM ACCOUNT</th>
						<!--<th>CUSTOMER CHAIN L2</th>-->
						<th>BASEPACK</th>
						<th>OFFER DESCRIPTION</th>
						<th>OFFER TYPE</th>
						<th>OFFER MODALITY</th>
						<th>GEOGRAPHY</th>
						<th>QUANTITY</th>
						<!--<th>UOM</th>-->
						<th>OFFER VALUE</th>
						<!--<th>KITTING VALUE</th>-->
						<th>STATUS</th>
						<!--<th>REASON FOR EDIT</th>-->
						<th>REMARK</th>
						<!--<th>CHANGE DATE</th>
						<th>CHANGES MADE</th>-->
						<th>USER ID</th>
						<th>INVESTMENT TYPES</th>
						<th>SOL CODE</th>
						<th>SOL CODE DESCRIPTION</th>
						<th>PROMOTION MECHANICS</th>
						<th>SOL CODE STATUS</th>
					</tr>
				</thead>
							</table>
			</form>
			
				<div class="clearfix"></div>
			</div>
			<div class="download-btn">
				<input type="button" class="btn new-btn-download" value="PROMO DOWNLOAD" onclick="javascript: downloadPromotionFile();"></input>
			</div>
		<%-- <div class="promo-upload">PROMO UPLOAD</div>		
			<form action="#" method="POST" enctype="multipart/form-data" id="coeStatusFileUpload" class="form-horizontal" name="tmeFileUploadBean">
											
												<div class="upload-file">
													<div class="col-md-4 col-md-offset-4" style="padding: 30px 0px;">
														<div class="upload-label">
															<span><i class="glyphicon glyphicon-cloud-upload"></i></span> <span>
																<b class="SEGOEUIL-font">Status Upload File</b>
															</span>
														</div>
														<div class="col-sm-10 col-md-6 subfileDiv">
															<span id="uploadartErrorMsg"
																style="display: none; color: red">Please upload
																.xls or .xlsx file</span> <input id="artfile" name="file"
																type="file" class="file" size="400" multiple="multiple">
																
																<span id="baseart" style="display:none;"></span>
														</div>
														<div class="col-sm-10 col-md-1 subUpload">
															<input class="validate_upload btn marginT10 new-btn-primary"
																id="artworkUploadBtn" onclick="uploadArtDoc('0')"
																type="button" value="Upload"></input>
														</div>
													</div>

												</div>
											</form> --%>
						
						
						<div class="col-md-6 col-sm-6 ddd">
								<div class="procoupload-parent" style="min-height: 275px !important;">
									<div style="text-align: center; color: #878787;">

										<h2 class="SEGOEUIL-font">Customer Portal Status Download</h2>
										<div class="upload-image">
											<i class="fa fa-download" aria-hidden="true"></i>
										</div>
	
										<form style="display: inline-block;" action="http://34.120.128.205/VisibilityAssetTracker/downloadCustomerPortalPromoStatusTracker.htm" method="post">
											<input type="submit" class="btn btn-primary" value="Download" ></input>
										</form>
								</div>
							</div>
						</div>
	 
						<div class="col-md-6 col-sm-6 ddd">
							<form id="coeStatusFileUpload" class="form-horizontal" action="#" 
								enctype="multipart/form-data" name="coeStatusFileUpload">
								<div class="procoupload-parent" style="min-height: 275px !important;">
									<div style="text-align: center; color: #878787;">
										<h2 class="SEGOEUIL-font">Promo Status Upload File</h2>
										<div class="upload-image">
											<!-- <img src="assets/images/upload-icon-n-body.png" alt="" /> -->
											<i class="fa fa-upload" aria-hidden="true"></i>
										</div>
										<div class="upload-max-size">Maximum Upload File Size
											:2MB</div>
										<span id="uploadErrorMsg" style="display: none; color: red">Please
											upload .xls or .xlsx file</span>
										<div class="input-group upload-status-files">
											<input id="uploadsecscre" name="file" type="file" class="file">
										</div>
										<input class="validate_upload btn marginT10 new-btn-primary" onclick="uploadArtDoc('0')" type="button"
											id="btnSubmitBasePack" value="Upload" />
									</div>
								</div>
							</form>
						</div>
						
											
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
		<script type="text/javascript"
		src="assets/js/custom/proco/alert-modal.js"></script>
		<script type="text/javascript">
		var moc = '${mocJson}';
		var Mocvalue = $('#Mocvalue').val(); //bharati added in sprint-9 for moc filter
		var geographyData = '${geographyJson}';
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