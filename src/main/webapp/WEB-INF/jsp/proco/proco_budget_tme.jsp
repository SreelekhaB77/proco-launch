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
<style>
.promo-filter-div{
float: right!important;
width: 30%!important;
}

#Mocvalue{
	height:30px!important;
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
							<li role="presentation"
								class="col-md-3 col-sm-6 col-xs-12 create"><a
								href="http://localhost:8083/VisibilityAssetTracker/promoCreation.htm">
									<div class="proco-create-icon"></div>
									<div class="tab-label-proco-create-inactive"
										style="margin-left: -25px;">Promo Creation</div>
							</a></li>
							<li role="presentation"
								class="col-md-3 col-sm-6 col-xs-12 listing"><a
								href="http://localhost:8083/VisibilityAssetTracker/promoListing.htm">
									<div class="proco-listing-icon"></div>
									<div class="tab-label-proco-create-inactive OpenSans-font"
										style="margin-left: -25px;">Promo Listing</div>
							</a></li>
							<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a href="http://localhost:8083/VisibilityAssetTracker/promoDeletion.htm">
								<div class="proco-deletion-icon"></div>
								<div class="tab-label-proco-del-inactive OpenSans-font"style="margin-left: 10px;">Dropped Offer</div>
						</a></li>
							<li role="presentation"
								class="col-md-3 col-sm-6 col-xs-12 budget" style="width: 19%;">
								<a
								href="http://localhost:8083/VisibilityAssetTracker/procoBudgetTme.htm"
								style="width: 260px;">
									<div class="proco-budget-icon "></div>
									<div class="tab-label-proco-budget-active OpenSans-font">Budget Report</div>
							</a>
							</li>
						</c:if>
						</ul>
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
		
		
		<!--  <div class="alert alert-success sucess-msg" style="display: none;margin-top:35px;margin-bottom: -23px" id="ProcoApprovalUploadsuccessblock">
	              <button type="button" class="close" data-hide="alert">&times;</button>
	              <span></span>
	            </div>
	               <div class="alert alert-danger" style="display: none;margin-top:35px;margin-bottom: -23px" id="ProcoApprovalUploaderrorblock">
	                               <button type="button" class="close" data-hide="alert">&times;</button>
	                              
	                                   <span>File contains error...</span>
	                                   <a href="#" id="downloadTempFileLink">Click here to Download Error File</a>
	                           </div>
			<div class="alert alert-danger" style="display: none;margin-top:35px;margin-bottom: -23px" id="errorblockApprovalUpload">
			         <button type="button" class="close" data-hide="alert">&times;</button>
			       
			<span></span>
	    </div> -->
	
	
        <div class="alert err-alert-danger error-msg hide" id="promoSelectErrorMsg" style="margin-top:35px;">
             	<button type="button" class="close" data-hide="alert">&times;</button>
                <span>Please select Promotion. </span>
         		</div>
 <!--            <div class="alert succ-alert-success sucess-msg" id="successblock1" style="display:none">
             	<button type="button" class="close" data-hide="alert">&times;</button>
                <span>Promotion Uploaded Successfully</span>
         	</div> -->
		  <div class="proco-creation form-horizontal">
			<input type="hidden" id="roleId" value="${roleId}" />
			<div class="promo-list table-header-deletion">PROCO BUDGET DOWNLOAD</div>
			
			<form>
			<!--bharati commented uom,kitting value, reason,remark and added last 4 columns in sprint-9 US-2-->
			<table id="table-id-promo-list-table" class="table table-striped table-bordered promo-list-table table-responsive" style="width: 100%;">
				<thead>
					<tr>
						<!-- <th><input name="select_all" class="userCheck" value="1" type="checkbox" /></th> -->
						<th>BUDGET HOLDER</th>
						<th>CATEGORY</th>
						<th>PRODUCT</th>
						<th>CUSTOMER</th>
						<th>FUND TYPE</th>
						<th>ORIGINAL AMOUNT</th>
						<th>ADJUSTED AMOUNT</th>
						<th>REVISED AMOUNT</th>
						<th>UPDATE AMOUNT</th>
						<th>TRANSFER IN</th>
						<th>TRANSFER OUT</th>
						<th>TRANSFER PIPELINE</th>
						<th>TOTAL AMOUNT</th>
						<th>PIPELINE AMOUNT</th>
						<th>COMMITMENT AMOUNT</th>
						<th>REMAINING AMOUNT</th>
						<th>ACTUALS</th>
						<th>ADJUSTMENT AGAINST ACTUALS</th>
						<th>USAGE</th>
						<th>POST CLOSE ACTUAL AMOUNT</th>
						<th>PAST YEAR CLOSED PROMOTIONS AMOUNT</th>
						<th>TIME PHASE</th>
						<th>REPORT DOWNLOADED BY</th>
						<th>REPORT DOWNLOADED DATE</th>
						<th>COE BUDGETREPORT UPLOADED TIMESTAMP</th>
					</tr>
				</thead>
							</table>
			</form>
			<div class="clearfix"></div>
			
			<div class="download-btn">
				<input type="button" class="btn new-btn-download" value="PROMO DOWNLOAD" onclick="javascript: downloadPromotionFile();"></input>
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
		<script type="text/javascript" src="assets/js/custom/proco/promoDownload.js"></script>
		<script type="text/javascript" src="assets/js/custom/proco/alert-modal.js"></script>
		<script type="text/javascript">
		var category = null;
		var budget_holder= null;
		var product = null;
		var Customer= null;
		var fund_type = null;
		var original_amount = null;
		var revised_amount = null;
		var update_amount = null;
		var transfer_in = null;
		var transfer_out = null;
		var transfer_pipeline = null;
		var total_amount = null;
		var pipeline_amount = null;
		var commitment_amount = null;
		var remaining_amount = null;
		var actuals = null;
		var adjustment_against_actuals = null;
		var usage = null;
		var post_close_actual_amount = null;
		var past_year_closed_promotions_amount = null;
		var time_phase = null;
		var report_downloaded_by = null;
		var report_downlaoded_date = null;
		var uploaded_timestamp = null;
		
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