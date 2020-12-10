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
<link rel="stylesheet" type="text/css" href="assets/css/bootstrap-multiselect.css">
<link rel="stylesheet" type="text/css"	href="assets/css/proco-style.css">
<link rel="stylesheet" type="text/css"	href="assets/css/custom-font.css">
<link rel="stylesheet" type="text/css"	href="assets/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="assets/css/font-awesome.css">

</head>
<body class="Verdana-font">
	<jsp:include page="../proco/proco-header.jsp" />

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
					 <c:if test="${roleId eq 'TME'}"> 
					 <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 create"><a
							href="http://localhost:8083/VisibilityAssetTracker/promoCreation.htm">
								<div class="proco-create-icon"></div>
								<div class="tab-label-proco-create-inactive">Promo Creation</div>
						</a></li>
					<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a
							href="http://localhost:8083/VisibilityAssetTracker/promoListing.htm">
								<div class="proco-listing-icon"></div>
								<div class="tab-label-proco-create-inactive OpenSans-font">Promo
									Listing</div>
						</a></li>
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 proco-del-active"><a href="http://localhost:8083/VisibilityAssetTracker/promoDeletion.htm">
								<div class="proco-deletion-icon"></div>
								<div class="tab-label-proco-create-active OpenSans-font">Dropped Offer</div>
						</a></li>
					 </c:if>
					 
					  <c:if test="${roleId eq 'DP'}">
					  <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 create"><a
							href="http://localhost:8083/VisibilityAssetTracker/promoVolumeUpload.htm">
								<div class="proco-volume-icon"></div>
								<div class="tab-label-proco-volume-inactive">Volume Upload</div>
						</a></li>
						
						<li role="presentation" class="col-md-3 col-sm-6 col-xs-12 disaggregation"><a
							href="http://localhost:8083/VisibilityAssetTracker/promoDisaggregation.htm">
								<div class="proco-disaggregation-icon"></div>
								<div class="tab-label-proco-disaggregation-inactive">Disaggregation</div>
						</a></li>
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a
							href="http://localhost:8083/VisibilityAssetTracker/promoListing.htm">
								<div class="proco-listing-icon"></div>
								<div class="tab-label-proco-create-inactive OpenSans-font">Promo
									Listing</div>
						</a></li>
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 proco-del-active"><a href="http://localhost:8083/VisibilityAssetTracker/promoDeletion.htm">
								<div class="proco-deletion-icon"></div>
								<div class="tab-label-proco-create-active OpenSans-font">Dropped Offer</div>
						</a></li>
					  </c:if>
					   <c:if test="${roleId eq 'KAM'}">
					    <!-- <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 disaggregation"><a
							href="http://localhost:8083/VisibilityAssetTracker/promoDisaggregation.htm">
								<div class="proco-disaggregation-icon"></div>
								<div class="tab-label-proco-disaggregation-inactive">Disaggregation</div>
						</a></li> -->
					 <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 collaboration"><a
							href="http://localhost:8083/VisibilityAssetTracker/promoCollaboration.htm">
								<div class="proco-collaboration-icon"></div>
								<div class="tab-label-proco-collaboration-inactive">Collaboration</div>
						</a></li>
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a
							href="http://localhost:8083/VisibilityAssetTracker/promoListing.htm">
								<div class="proco-listing-icon"></div>
								<div class="tab-label-proco-create-inactive OpenSans-font">Promo
									Listing</div>
						</a></li>
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 proco-del-active"><a href="http://localhost:8083/VisibilityAssetTracker/promoDeletion.htm">
								<div class="proco-deletion-icon"></div>
								<div class="tab-label-proco-create-active OpenSans-font">Dropped Offer</div>
						</a></li>
						</c:if>
						
						<c:if test="${roleId eq 'NCMM'}">
					 <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 create"><a
							href="http://localhost:8083/VisibilityAssetTracker/promoCr.htm">
								<div class="proco-create-icon"></div>
								<div class="tab-label-proco-create-inactive">Promo CR</div>
						</a></li>
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a
							href="http://localhost:8083/VisibilityAssetTracker/promoListing.htm">
								<div class="proco-listing-icon"></div>
								<div class="tab-label-proco-create-inactive OpenSans-font">Promo
									Listing</div>
						</a></li>
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 proco-del-active"><a href="http://localhost:8083/VisibilityAssetTracker/promoDeletion.htm">
								<div class="proco-deletion-icon"></div>
								<div class="tab-label-proco-create-active OpenSans-font">Dropped Offer</div>
						</a></li>
					 </c:if>
					 
					 <c:if test="${roleId eq 'NSCM'}">
					 <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 create"><a
							href="http://localhost:8083/VisibilityAssetTracker/promoCr.htm">
								<div class="proco-create-icon"></div>
								<div class="tab-label-proco-create-inactive">Promo CR</div>
						</a></li>
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a
							href="http://localhost:8083/VisibilityAssetTracker/promoListing.htm">
								<div class="proco-listing-icon"></div>
								<div class="tab-label-proco-create-inactive OpenSans-font">Promo
									Listing</div>
						</a></li>
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 proco-del-active"><a href="http://localhost:8083/VisibilityAssetTracker/promoDeletion.htm">
								<div class="proco-deletion-icon"></div>
								<div class="tab-label-proco-create-active OpenSans-font">Dropped Offer</div>
						</a></li>
					 </c:if>
					 <c:if test="${roleId eq 'COE'}">
					<li role="presentation" class="col-md-3 col-sm-6 col-xs-12 proco-listing-inactive">
					<a href="http://localhost:8083/VisibilityAssetTracker/promoStatusTracker.htm">
								<div class="proco-listing-icon"></div>
								<div class="tab-label-proco-status-inactive OpenSans-font">Promo Status Tracker</div>
						</a></li>
						<li role="presentation" class="col-md-3 col-sm-6 col-xs-12 proco-del-active">
						<a href="http://localhost:8083/VisibilityAssetTracker/promoDeletion.htm" style="margin-top: 10px;margin-left: -23px;">
								<div class="proco-deletion-icon"></div>
								<div class="tab-label-proco-create-active OpenSans-font">Dropped Offer</div>
						</a></li>
					 <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 promo-lib-bg" style="margin-top: 10px;">
							<a href="http://localhost:8083/VisibilityAssetTracker/ProcoMeasureReportUploadPage.htm" id="measHref">
								<div class="proco-Signops-icon"></div>
								<div class="tab-label-proco-Signops-inactive OpenSans-font">Upload Measure Report</div>
							</a>
						</li>  
					 </c:if>
					
						
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
			</div>
		</c:if>
		<c:if test="${errorMsg!=null}">
			<div class="alert err-alert-danger sucess-msg" id="errorblock" style="margin-top:35px;">
				<button type="button" class="close" data-hide="alert">&times;</button> 
				<c:out value="${errorMsg}"></c:out>
				<c:if test="${FILE_STATUS=='ERROR_FILE'}">
					<a href="http://localhost:8083/VisibilityAssetTracker/downloadPromotionEditErrorFile.htm" id="downloadTempFileLink">Click
						here to Download Error File:</a>

				</c:if>
			</div>
		</c:if>
	
        <div class="alert err-alert-danger error-msg" id="promoSelectErrorMsg" style="margin-top:35px;">
             	<button type="button" class="close" data-hide="alert">&times;</button>
                <span>Please select Promotion. </span>
         		</div>
 <!--             <div class="alert succ-alert-success sucess-msg" id="successblock1" style="display:none">
             	<button type="button" class="close" data-hide="alert">&times;</button>
                <span>Promotion Uploaded Successfully</span>
         	</div> -->
		<div class="proco-creation form-horizontal">
			<input type="hidden" id="roleId" value="${roleId}" />
			<!-- <div class="promo-back"><a href="http://localhost:8083/VisibilityAssetTracker/procoHome.htm"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"> </span></a>Promo Listing</div> -->
			<div class="promo-details" style="padding:10px;"><span style="color:#fff;font-weight:600;">SELECT PROMO LISTING</span>
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
		<form action="http://localhost:8083/VisibilityAssetTracker/downloadDeletedPromo.htm" method="POST" enctype="multipart/form-data" id="download">
		<input type="hidden" name="remarkText" id="remarkText" value="" />
			<div class="promo-form-details proco-deletion-form">
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
							<input type="text" class="form-control proco-deletion-input" id="customerChainL2" name="customerChainL2"
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
		</form>
			<div class="promo-list table-header-deletion">PROMO LIST</div>
			<form>
			<table id="table-id-promo-list-table" class="table table-striped table-bordered promo-list-table table-responsive" style="width: 100%;">
				<thead>
					<tr>
						<!-- <th><input name="select_all" class="userCheck" value="1" type="checkbox" /></th> -->
						<th>UNIQUE ID</th>
						<th>ORIGINAL ID</th>
						<th>START DATE</th>
						<th>END DATE</th>
						<th>MOC</th>
						<th>CUSTOMER CHAIN L1</th>
						<th>BASEPACK</th>
						<th>OFFER DESCRIPTION</th>
						<th>OFFER TYPE</th>
						<th>OFFER MODALITY</th>
						<th>GEOGRAPHY</th>
						<th>QUANTITY</th>
						<th>UOM</th>
						<th>OFFER VALUE</th>
						<th>KITTING VALUE</th>
						<th name="stat">STATUS</th>
						<th>REASON</th>
						<th>DELETE REMARK</th>
					</tr>
				</thead>
							</table>
			</form>
			<!--<c:if test="${roleId eq 'TME'}">			
			<div class="promo-list-btns">
				<div class="col-md-3 col-xs-12">
					<a href="#" id="delete_promo"><button
						class="btn promo-delete-btn col-md-10 col-md-offset-1 col-xs-12">DELETE
						PROMO</button></a>
				</div>
				 <div class="col-md-3 col-xs-12">
					<a href="#" id="duplicate_promo">
					<button	class="btn promo-primary-btn col-md-10 col-md-offset-1 col-xs-12" >DUPLICATE PROMO</button></a>
				</div>
				<div class="col-md-3 col-xs-12">
					<a href="#" id="edit_promo"><button
						class="btn promo-primary-btn col-md-10 col-md-offset-1 col-xs-12" >EDIT
						PROMO</button></a>
				</div>
				<div class="col-md-3 col-xs-12">
					<a href="#" id="create_promo"><button
						class="btn promo-primary-btn col-md-10 col-md-offset-1 col-xs-12">CREATE
						PROMO</button></a>
				</div>
				</c:if>	 -->
				<div class="clearfix"></div>
			</div>
			<!-- <p>Choose file -> Submit Selected File -> Import</p>
			<div class="upload-file">
				<div class="col-md-6">
					<div class="form-group">
						<label for="unique-id" class="control-label col-md-3"
							style="text-align: left">Upload new file:</label>
						<div class="col-md-2 col-xs-12">
							<div class="cust-file">
								<button class="btn btn-upload col-xs-12" id="choose-file">Choose
									File</button>
							</div>

							<input type="file" class="form-control" value="" name="file"
								id="upload-file">
						</div>
						<div class="file-name col-md-7 col-xs-12">No file chosen</div>
					</div>
				</div>
				<div class="col-md-6 col-xs-12">
					<div class="pull-right" style="color: #fff;">
						<button class="btn btn-primary col-xs-12">IMPORT</button>
						<button class="btn promo-primary-btn col-xs-12">SUBMIT
							SELECTED FILE</button>
					</div>
				</div>
				<div class="clearfix"></div>
			</div> -->
			<%--  <c:if test="${roleId eq 'TME'}"> --%>
			<div class="download-btn">
				<input type="button" class="btn new-btn-download" value="PROMO DOWNLOAD" onclick="javascript: downloadPromotionFile();"></input>
			</div>
			<%-- </c:if> --%>
			<%-- <c:if test="${roleId eq 'TME'}">
			<form:form action="http://localhost:8083/VisibilityAssetTracker/uploadPromoEdit.htm" id="promoEditUpload"
				method="POST" modelAttribute="VolumeUploadBean"
				enctype="multipart/form-data" onsubmit="return uploadValidation()">
			<div class="promo-upload">PROMO UPLOAD</div>
			<div class="upload-file">
				<div class="col-md-4 col-md-offset-4" style="padding: 30px 0px;">
					<div class="upload-label">
						<span><i class="glyphicon glyphicon-cloud-upload"></i></span> <span>
							<b class="SEGOEUIL-font">Edit Promo File</b>
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

					<div class="" style="color: #fff; text-align: center">
						<button class="btn btn-primary">UPLOAD</button>

					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</form:form>
		</c:if> --%>
			
		</div>
		
		<div id="add-depot" class="modal fade" role="dialog">

      <div class="modal-dialog">
      <div class="modal-content">
		<div class="modal-header proco-modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Reason</h4>
		</div>
		<div class="modal-body">
			<div class="row">
<form id="pwdform" method="post" action="http://localhost:8083/VisibilityAssetTracker/deletePromotion.htm" class="form-horizontal" style="padding: 10px 0;">
					<div class="col-md-12" id="msg-1">
						<div id="msg-error" class="alert err-alert-danger fade in"></div>
						<div id="msg-success" class="alert succ-alert-success fade in"></div>
						<input type="hidden" id="promoIdList" name="promoIdList" value=""/>
						<div class="OpenSans-font">
								
							<div class="form-group">
								<label for="tme_confirmPwd" class="col-sm-5 control-label">Remark</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="remark"
										name="remark" placeholder="Remark" maxlength="40">
								</div>
							</div>
						</div>
					</div>
					</form>
			</div>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn btn-default chnage-close pull-left"
				data-dismiss="modal" value="CLOSE"/>
			<input type="button" id="SaveDetails"
				class="btn btn-primary  resetBtn  marginR10" onClick="javascript: validateForm();" VALUE="SAVE"/>
				
		</div>
</div>
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
		<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="assets/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="assets/js/dataTables.bootstrap.min.js"></script>
		<script type="text/javascript" src="assets/js/comboTreePlugin.js"></script>
		<script type="text/javascript" src="assets/js/bootstrap-multiselect.js"></script>
		<script type="text/javascript" src="assets/js/jquery-ui.js"></script>
		<script type="text/javascript" src="assets/js/custom/proco/promoDeletion.js"></script>
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

          $(document).ready(function(){

        		  $('#duplicate_promo').on('click',function(){
        			  $('#successblock').hide();
      				var promoIdList = [];
      				$.each($("table.promo-list-table tbody input[name='promoId']:checked"),
      				function() {
      					promoIdList.push($(this).val());
      						});

      				if(promoIdList.length>0){
      				$("#duplicate_promo").attr("href", "http://localhost:8083/VisibilityAssetTracker/duplicatePromotion.htm?promoid="+promoIdList);
      				} else{
      					$('#promoSelectErrorMsg').show().find('span').html('Please select Promotion.');
          				}
        		  });

        		  $('#edit_promo').on('click',function(){
        			  $('#successblock').hide();
        				var promoIdList = [];
        				$.each($("table.promo-list-table tbody input[name='promoId']:checked"),
        				function() {
        					promoIdList.push($(this).val());
        						});

        				if(promoIdList.length>0){
        				$("#edit_promo").attr("href", "http://localhost:8083/VisibilityAssetTracker/editPromotion.htm?promoid="+promoIdList);
        				} else{
            				$('#promoSelectErrorMsg').show().find('span').html('Please select Promotion.');
            				}
          		  });

        		  $('#delete_promo').on('click',function(){
        		    $('#successblock').hide();
      				var promoIdList = [];
      				$.each($("table.promo-list-table tbody input[name='promoId']:checked"),
      				function() {
      					promoIdList.push($(this).val());
      						});

      				if(promoIdList.length>0){
      				//$("#delete_promo").attr("href", "deletePromotion.htm?promoid="+promoIdList);
      				$("#delete_promo").attr("data-toggle", "modal");
  	  				$("#delete_promo").attr("href", "data-target=#add-depot");
      				} else{
          				$('#promoSelectErrorMsg').show().find('span').html('Please select Promotion.');
          				}
        		  });

        		  $('#create_promo').on('click',function(){
      		        
        			$("#create_promo").attr("href", "http://localhost:8083/VisibilityAssetTracker/promoCreation.htm");
        			
          		  });

        		  
					
              });
	
		</script>
</body>



</body>
</html>