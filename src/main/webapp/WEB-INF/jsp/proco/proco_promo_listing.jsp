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
<link rel="stylesheet" type="text/css" href="multiselect/css/jquery.multiselect.css"></head>
<link rel="stylesheet" href="https://phpcoder.tech/multiselect/css/jquery.multiselect.css">


<!--bharati added below style for sprint-9 moc filter changes -->
<style>
.promo-filter-div{
float: right!important;
width: 40%!important;
margin-top: -66px!important;
padding-right: 135px;
}

#Mocvalue{
	height:30px!important;
}
.table-header-listing {
    background: #035597 !important;
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

	<nav class="navbar navbar-inverse navbar-fixed-top container-bg"
		style="position:relative;top: 115px;z-index: 2;background-image: none! important;
    border: none! important;background: #F6F3F3;
    padding: 4px 0px 20px 0px;">
	<div class="container-fluid paddR10">
		<div class="navbar-header marginB10">
			<h1 class="pull-left" style="color: #000000;">
				Promotion Planning Tool
			</h1>
			<button type="button" class="navbar-toggle collapsed pull-right" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
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
							href="http://34.120.128.205/VisibilityAssetTracker/promoCreation.htm">
								<div class="proco-create-icon"></div>
								<div class="tab-label-proco-create-inactive"style="margin-left: -30px;">Promo Creation</div>
						</a></li>
					 </c:if>
					 
					  <c:if test="${roleId eq 'DP'}">
					  <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 create"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoVolumeUpload.htm">
								<div class="proco-volume-icon"></div>
								<div class="tab-label-proco-volume-inactive"style="margin-left: -30px;">Volume Upload</div>
						</a></li>
						<!--bharati commented disaggregation tab in sprint-9--> 
						<!--<li role="presentation" class="col-md-3 col-sm-6 col-xs-12 disaggregation"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoDisaggregation.htm">
								<div class="proco-disaggregation-icon"></div>
								<div class="tab-label-proco-disaggregation-inactive">Disaggregation</div>
						</a></li>-->
						
					  </c:if>
					   <c:if test="${roleId eq 'KAM'}">
					    <!-- <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 disaggregation"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoDisaggregation.htm">
								<div class="proco-disaggregation-icon"></div>
								<div class="tab-label-proco-disaggregation-inactive">Disaggregation</div>
						</a></li> -->
					 <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 collaboration"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoCollaboration.htm">
								<div class="proco-collaboration-icon"></div>
								<div class="tab-label-proco-collaboration-inactive"style="margin-left: -30px;">Collaboration</div>
						</a></li>
						</c:if>
						
						<c:if test="${roleId eq 'NCMM'}">
					 <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 create"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoCr.htm">
								<div class="proco-create-icon"></div>
								<div class="tab-label-proco-create-inactive"style="margin-left: -30px;">Promo Approval</div>
						</a></li>
					 </c:if>
					 
					 	<c:if test="${roleId eq 'SC'}">
					 <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 create"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoApproveSc.htm">
								<div class="proco-create-icon"></div>
								<div class="tab-label-proco-create-inactive"style="margin-left: -30px;">Promo Approval</div>
						</a></li>
					 </c:if>
					 
					 <c:if test="${roleId eq 'NSCM'}">
					 <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 create"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoCr.htm">
								<div class="proco-create-icon"></div>
								<div class="tab-label-proco-create-inactive"style="margin-left:-25px;">Promo Approval</div>
						</a></li>
					 </c:if>
						
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 proco-listing-active"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoListing.htm">
								<div class="proco-listing-icon"></div>
								<div class="tab-label-proco-create-active OpenSans-font"style="margin-top: 12px; " >Promo Listing</div>
						</a></li>
						
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a href="http://34.120.128.205/VisibilityAssetTracker/promoDeletion.htm">
								<div class="proco-deletion-icon"></div>
								<div class="tab-label-proco-del-inactive OpenSans-font"style="margin-left: 10px;">Dropped Offer</div>
						</a></li>
						
					<li role="presentation"
								class="col-md-3 col-sm-6 col-xs-12 budget" style="width: 19%;">
								<a
								href="http://34.120.128.205/VisibilityAssetTracker/procoBudgetTme.htm"
								style="width: 247px;">
									<div class="proco-budget-icon "></div>
									<div class="tab-label-proco-budget-inactive OpenSans-font">Budget Report</div>
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
	
        <div class="alert err-alert-danger error-msg" id="promoSelectErrorMsg" style="display:none;margin-top:35px;">
             	<button type="button" class="close" data-hide="alert">&times;</button>
                <span>Please select Promotion. </span>
         		</div>
 <!--             <div class="alert succ-alert-success sucess-msg" id="successblock1" style="display:none">
             	<button type="button" class="close" data-hide="alert">&times;</button>
                <span>Promotion Uploaded Successfully</span>
         	</div> -->
		<div class="proco-creation form-horizontal">
			<input type="hidden" id="roleId" value="${roleId}" />
			<!--bharati commented below div line for sprint-9-->
			<!-- <div class="promo-back"><a href="http://34.120.128.205/VisibilityAssetTracker/procoHome.htm"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"> </span></a>Promo Listing</div> -->
			<!--<div class="promo-details" style="padding:10px 10px;"><span style="color:#fff;font-weight:600;">SELECT PROMO LISTING</span>
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
			<!--</div>-->
			<!--bharati change below end point downloadPromoFromListing.htm to downloadPromoListing.htm for promo list download in sprint-9-->
			<!--bharati commented below categories in form for sprint-9-->  
		<!--<form action="http://34.120.128.205/VisibilityAssetTracker/downloadPromoListing.htm" method="POST" enctype="multipart/form-data" id="download">
		<input type="hidden" name="remarkText" id="remarkText" value="" />
			<div class="promo-form-details proco-listing-form">
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
							<input type="text" class="form-control proco-listing-input" id="customerChainL2" name="customerChainL2"
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
							<!--<input type="text" class="form-control" value="ALL INDIA" name="geography"
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
			<div class="promo-list table-header-listing">PROMO LIST</div>
			
			<!--bharati added below form for sprint-9 moc changes-->
			<form action="http://34.120.128.205/VisibilityAssetTracker/downloadPromoListing.htm" method="POST" enctype="multipart/form-data" id="download">
				 <!--viswas added below Startdate and Enddate filters for sprint-11-->
				 <div class="form-group col-sm-4">
				<label class="control-label col-md-4" for="uom" >START DATE:</label>
                <div class="col-md-6">
                <input type='date' name="startDate1" class="form-control" id="startDate1" onChange="promoStartDate1()" autocomplete="off" >
               
                               
                    
                      </div>
                  </div>	 <div class="form-group col-sm-4"style="margin-left:28px;">
				<label class="control-label col-md-4" for="uom">END DATE:</label>
                <div class="col-md-6">
               <input type='date' name="endDate1" class="form-control" id="endDate1" onChange="promoEndDate1()"  autocomplete="off" />               
               
                 </div>
                 </div>	
			<div class="form-group col-sm-4" style="margin-top: 20px;">
						<label for="unique-id" class="control-label col-md-3"style="margin-left:10px;">MOC</label>
						<div class="col-md-6" style="margin-left: 25px;" >
						<select class="form-control" id="Mocvalue"  name="Mocvalue" onChange="dateDisable()" multiple>
								   <c:forEach items="${mocList}" var="mocValue">
                                   <option value="${mocValue}"><c:out value="${mocValue}">
                                   </c:out></option>
                                 </c:forEach>
                                 </select>
								 </div>
					</div>
					<div class="download-btn">
							<input type="button" class="btn new-btn-download" value="Reset" onclick="javascript: resetdata();" style="margin-top: -50px;"></input>
					</div>
					 <div class="form-group col-sm-4" style="margin-top: 20px; margin-left:2px">
                    <label class="control-label col-md-3" for="uom">BASEPACK</label>
                    <div class="col-md-6" style="margin-left:15px;">
                    <select class="form-control" id="ProcoBasepack" name="ProcoBasepack" multiple>
                    <c:forEach  items="${procoBasepacks}" var="procoBasepack">
                    <option value="${procoBasepack}">${procoBasepack}</option>
                 </c:forEach>
                </select>
                  </div>
                 </div>
                <div class="form-group col-sm-4" style="margin-top: 20px; margin-left: 57px; ">
                <label class="control-label col-md-3" for="uom" style="text-align:left; padding:0px">PPM ACCOUNT</label>
				<div class="col-md-6" style="margin-left:-12px;">
				<select class="form-control" id="PpmAccount" name="PpmAccount" multiple>
				<c:forEach items="${ppmAccountList}" var="ppmAccount" >
				<option value="${ppmAccount}">${ppmAccount}</option>
				</c:forEach>
				</select>
				</div>
				</div>
				
				<div class="form-group col-sm-4" style="margin-top: 20px; margin-left:-25px; ">
				<label class="control-label col-md-3"  "for="uom">CLUSTER</label>
				<div class="col-md-6">
				<select class="form-control" id="ProcoClusterList" name="ProcoClusterList" multiple>
				<c:forEach items="${procoClusterList}" var="procoCluster" >
				<option value="${procoCluster}">${procoCluster}</option>
				</c:forEach>
				</select>
				</div>
				</div>
				<div class="form-group col-sm-4"style="margin-left:16px">
				<label class="control-label col-md-3"style="margin-left:-16px" for="uom">CHANNEL</label>
				<div class="col-md-6"style="margin-left:16px">
				<select class="form-control" id="ProcoChannelList" name="ProcoChannelList" multiple>
				<c:forEach items="${procoChannelList}" var="procoChannel" >
				<option value="${procoChannel}">${procoChannel}</option>
				</c:forEach>
				</select>
				</div>
				</div>
                
			
               			
				
			</form>
			<form>
			</form>
			<form>
			<!--bharati commented uom,kitting value, reason,remark and added last 4 columns in sprint-9 US-2-->
			<table id="table-id-promo-list-table" class="table table-striped table-bordered promo-list-table table-responsive" style="width: 100%;">
				<thead>
					<tr>
						<th><input name="select_all" class="userCheck" value="1" type="checkbox" /></th>
						<th>PROMO ID</th>
						<!--<th>ORIGINAL ID</th>-->
						<th>START DATE</th>
						<th>END DATE</th>
						<th>MOC</th>
						<!--<th>CUSTOMER CHAIN L1</th>--> <!--bharati rename this to ppm account in sprint-9-->
						<th>PPM ACCOUNT</th>
						<th>BASEPACK</th>
						<th>OFFER DESCRIPTION</th>
						<th>OFFER TYPE</th>
						<th>OFFER MODALITY</th>
						<th>GEOGRAPHY</th>
						<th>QUANTITY</th>
						<th>OFFER VALUE</th>
						<th name="stat">STATUS</th>
						<th>CREATED BY</th>
						<th>CREATED DATE</th>
						<th>REMARKS</th>
						<th>INVESTMENT TYPES</th>
						<th>SOL CODE</th>
						<th>PROMOTION MECHANICS</th>
						<th>SOL CODE STATUS</th>
					</tr>
				</thead>
							</table>
			</form>
			<!--bharati commented below 4 btns and upload part in sprint-9 US-2-->
			<!--<c:if test="${roleId eq 'TME'}">			
			<div class="promo-list-btns">
				<div class="col-md-3 col-xs-12">
					<a href="#" id="delete_promo"><button
						class="btn promo-delete-btn col-md-10 col-md-offset-1 col-xs-12">DELETE PROMOS</button></a>
				</div>
				<div class="col-md-3 col-xs-12">
					<a href="#" id="duplicate_promo"><button
						class="btn promo-primary-btn col-md-10 col-md-offset-1 col-xs-12" >DUPLICATE PROMO</button></a>
				</div>
				<div class="col-md-3 col-xs-12">
					<a href="#" id="edit_promo"><button
						class="btn promo-primary-btn col-md-10 col-md-offset-1 col-xs-12" disabled>EDIT PROMO</button></a>
				</div>
				<div class="col-md-3 col-xs-12">
					<a href="#" id="create_promo"><button
						class="btn promo-primary-btn col-md-10 col-md-offset-1 col-xs-12">CREATE
						PROMO</button></a>
				</div>
				</c:if>	-->
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
			 <c:if test="${roleId eq 'TME'}">
			<div class="download-btn">
				<input type="button" class="btn new-btn-download" value="PROMO DOWNLOAD" onclick="javascript: downloadPromotionFile();"></input>
			</div>
			</c:if>
			<!-- Added by Kavitha D SPRINT 10 -->
			<c:if test="${roleId eq 'NCMM'}">
			<div class="download-btn">
				<input type="button" class="btn new-btn-download" value="PROMO DOWNLOAD" onclick="javascript: downloadPromotionFile();"></input>
			</div>
			</c:if>
			
			<!-- Added by Kavitha D SPRINT 10 -->
			<c:if test="${roleId eq 'SC'}">
			<div class="download-btn">
				<input type="button" class="btn new-btn-download" value="PROMO DOWNLOAD" onclick="javascript: downloadPromotionFile();"></input>
			</div>
			</c:if>
			
			<!--bharati added comment for below code in sprint-9-->
			<!--<c:if test="${roleId eq 'TME'}">
			<form:form action="http://34.120.128.205/VisibilityAssetTracker/uploadPromoEdit.htm" id="promoEditUpload"
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

						<input type="file" class="form-control" value="" name="file" id="upload-file">

						<div class="file-name" style="line-height: 2.3">No file chosen</div>
					</div>

					<div class="" style="color: #fff; text-align: center">
						<button class="btn new-btn-primary">UPLOAD</button>

					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</form:form>
		</c:if>-->
			
		</div>
		
		<div id="add-depot"  class="modal fade" role="dialog">

      <div class="modal-dialog">
      <div class="modal-content">
		<div class="modal-header proco-modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Reason</h4>
		</div>
		<div class="modal-body">
			<div class="row">
<form id="pwdform" method="post" action="http://34.120.128.205/VisibilityAssetTracker/deletePromotion.htm" class="form-horizontal" style="padding: 10px 0;">
					<div class="col-md-12" id="msg-1">
						<div id="msg-error" class="alert err-alert-danger fade in"></div>
						<div id="msg-success" class="alert succ-alert-success fade in"></div>
						<input type="hidden" id="promoIdList" name="promoIdList" value=""/>
						<div class="OpenSans-font">
							<span id="suredel">Are you sure you want to delete selected promos?</span>	
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
		<script type="text/javascript" src="assets/js/custom/proco/promoListing.js"></script>
		<script type="text/javascript" src="assets/js/custom/proco/alert-modal.js"></script>
		<script type="text/javascript" src="multiselect/js/jquery.multiselect.js"></script>
		<script src="https://phpcoder.tech/multiselect/js/jquery.multiselect.js"></script>
		<script type="text/javascript">
		var moc = "${mocJson}";
		var Mocvalue = $('#Mocvalue').val(); //bharati added in sprint-9 for moc filter
		var ProcoBasepack = $('#ProcoBasepack').val();
        var PpmAccount = $('#PpmAccount').val();
        var ProcoClusterList = $('#ProcoClusterList').val();
        var ProcoChannelList = $('#ProcoChannelList').val();//viswas added in sprint-11 for filters
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
		//var mocVal = null;
		var startDate1 = $('#fromDate').val();
		var endDate1 = $('#toDate').val();//viswas added in sprint-11 for filters
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
        	  $('#Mocvalue').multiselect({
        		  columns: 1,
        		  //nSelectedText: 'selected', 
        		  //nonSelectedText: 'SELECT BASEPACK',
        		  search: true
        		  });
        	  
        	  $('#ProcoBasepack').multiselect({
        		  columns: 1,
        		  nSelectedText: 'selected', 
        		  nonSelectedText: 'SELECT BASEPACK',
        		  search: true
        		  });
        	  $('#PpmAccount').multiselect({
        		  columns: 1,
        		  nSelectedText: 'selected', 
        		  nonSelectedText: 'SELECT PPM ACCOUNT',
        		  search: true
        		  });
        	  $('#ProcoClusterList').multiselect({
        		  columns: 1,
        		  nSelectedText: 'selected', 
        		  nonSelectedText: 'SELECT CLUSTER',
        		  search: true
        		  });
        	  $('#ProcoChannelList').multiselect({
        		  columns: 1,
        		  nSelectedText: 'selected', 
        		  nonSelectedText: 'SELECT CHANNEL',
        		  search: true
        		  });
        	 
        		  $('#duplicate_promo').on('click',function(){
        			  $('#successblock').hide();
      				var promoIdList = [];
      				$.each($("table.promo-list-table tbody input[name='promoId']:checked"),
      				function() {
      					promoIdList.push($(this).val());
      						});

      				if(promoIdList.length>0){
      				$("#duplicate_promo").attr("href", "http://34.120.128.205/VisibilityAssetTracker/duplicatePromotion.htm?promoid="+promoIdList);
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
        				$("#edit_promo").attr("href", "http://34.120.128.205/VisibilityAssetTracker/editPromotion.htm?promoid="+promoIdList);
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
      				  $("#delete_promo").attr("data-toggle", "modal");
  	  				$("#delete_promo").attr("href", "data-target=#add-depot");
      				//$("#delete_promo").attr("href", "http://34.120.128.205/VisibilityAssetTracker/deletePromotion.htm?promoid="+promoIdList);
      				
      				
      			/* 	$.ajax({
                url: "checkDropDate.htm",
                type: "post",
                data: 'checkValues=' + promoIdList,
                async:false,
                beforeSend: function() {
                //	$('.popupLoader').show();
                },
                success: function(data) {
                	
                /*   if (data == '') { */
                	  $("#delete_promo").attr("data-toggle", "modal");
    	  				$("#delete_promo").attr("href", "data-target=#add-depot");
                /*   }
                  else{
                		$('#promoSelectErrorMsg').show().find('span').html('You cannot delete this promo.');
                  } */
              /*   },

                error: function(msg){
              	 // console.log(msg);
                }
              
              });  */
      		} 
				
			else{
    				$('#promoSelectErrorMsg').show().find('span').html('Please select Promotion.');
    				}
       		  });

        		  $('#create_promo').on('click',function(){
      		        
        			$("#create_promo").attr("href", "http://34.120.128.205/VisibilityAssetTracker/promoCreation.htm");
        			
          		  });

        		  
					
              });
		</script>
</body>



</body>
</html>