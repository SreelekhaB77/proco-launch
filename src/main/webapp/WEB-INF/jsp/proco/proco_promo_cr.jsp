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


	
	<script type="text/javascript">
		history.pushState(null, null, '');
		window.addEventListener('popstate', function(event) {
			history.pushState(null, null, '');
		});
	</script>

</head>
<style>
.promo-filter-div{
float: right!important;
width: 30%!important;
margin-top: -75px!important;
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
					<ul class="nav nav-pills">
					<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 proco-craetion-active"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoCr.htm">
								<div class="proco-create-icon"></div>
								<div class="tab-label-proco-create-active">Promo Approval</div>
						</a></li>
						
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoListing.htm">
								<div class="proco-listing-icon"></div>
								<div class="tab-label-proco-create-inactive OpenSans-font">Promo Listing</div>
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
						here to Download Error File: </a>

				</c:if>
			</div>
		</c:if>
		
		<!--Added By Sarin - sprint10 - added code for errorblock with download error file for approvals-->
		<div class="alert alert-success sucess-msg" style="display: none;margin-top:35px;margin-bottom: -23px" id="ProcoApprovalUploadsuccessblock">
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
	
	        <!-- <span>Error while uploading file.</span>-->
	<span></span>
	
	  </div>
		<!--Added By Sarin - ends here - sprint10 - added code for errorblock with download error file for approvals-->	
	
        <div class="alert err-alert-danger error-msg" id="promoSelectErrorMsg" style="display:none;margin-top:35px;" >
             	<button type="button" class="close" data-hide="alert">&times;</button>
                <span>Please select Promotion. </span>
         		</div>
 <!--             <div class="alert succ-alert-success sucess-msg" id="successblock1" style="display:none">
             	<button type="button" class="close" data-hide="alert">&times;</button>
                <span>Promotion Uploaded Successfully</span>
         	</div> -->
	       <div class="proco-creation form-horizontal">
			 	<input type="hidden" id="roleId" value="${roleId}" />
			<!-- <div class="promo-back"><a href="http://34.120.128.205/VisibilityAssetTracker/procoHome.htm"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"> </span></a>Promo Listing</div> -->
			<div class="promo-details" style="padding:10px;"><span style="color:#fff;font-weight:600;">SELECT PROMOS FOR APPROVAL</span></div>
			<form action="http://34.120.128.205/VisibilityAssetTracker/downloadCrPromoListing.htm" method="POST" enctype="multipart/form-data" id="download"> 
			<div class="form-group col-sm-4" style="margin-top: 20px;">
						<label for="unique-id" class="control-label col-md-2">MOC</label>
						<div class="col-md-5">
						<select class="form-control" id="Mocvaluecr" name="Mocvaluecr">
								 <c:forEach items="${mocListCr}" var="mocValueCr">
                                   <option value="${mocValueCr}"><c:out value="${mocValueCr}"></c:out></option>
                                 </c:forEach>
                                 </select>
								 </div>

					</div>
			</form>
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
				<div class="clearfix"></div> 
			</div>
		<form action="http://34.120.128.205/VisibilityAssetTracker/rejectCr.htm" method="POST" enctype="multipart/form-data" id="download">
		<input type="hidden" name="promoIdList" id="promoIdList" value="" />
			<input type="hidden" name="remark" id="remark" value="" />
			<div class="promo-form-details">
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
								<option>ALL BRANDS </option>
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
							<input type="text" class="form-control proco-create-input" id="customerChainL2" name="customerChainL2"
								value="ALL CUSTOMERS" readonly="true"></input>
						</div>

					</div>

					<div class="form-group">
						<label class="control-label col-md-4" for="uom">MODALITY </label>
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
							</select> 
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
			<div class="promo-list table-header-cr">PROMO LIST</div> -->
			
			
			<form>
			 <table id="table-id-promo-list-table" class="table table-striped table-bordered promo-list-table"
				cellspacing="0" style="width: 100%;overflow-x: scroll;display: block;"> 
					<!-- <table id="table-id-promo-list-table" 
					class="table table-striped table-bordered promo-list-table table-responsive" style="width: 100%; ">-->
				<thead>
					<tr>
					    <th><input name="select_all" class="userCheck" value="1" type="checkbox" /></th>
					   <th>PROMO ID</th>
						<!--<th>ORIGINAL ID</th>-->
						<th>START DATE</th>
						<th>END DATE</th>
						<th>MOC</th>
						<th>PPM ACCOUNT</th>
						<th>BASEPACK</th>
						<th>OFFER DESCRIPTION</th>
						<th>OFFER TYPE</th>
						<th>OFFER MODALITY</th>
						<th>GEOGRAPHY</th>
						<th>QUANTITY</th>
						<th>OFFER VALUE</th>
						<th name="stat">STATUS</th>
						<th>INVESTMENT TYPES</th>
						<th>SOL CODE</th>
						<th>PROMOTION MECHANICS</th>
						<th>SOL CODE STATUS</th>
					</tr>
				</thead>
							</table>
			</form>
			<div class="promo-list-btns">
				<div class="col-md-2 col-xs-6">
				</div>
				<div class="col-md-2 col-xs-6">
				</div>
				<div class="col-md-2 col-xs-6">
				</div>
				
							
			
				  <div class="col-md-2 col-xs-6">
					<a href="#" id="approveCr"><button id="approveCrBtn" 
						class="btn promo-primary-btn col-md-10 col-md-offset-1 col-xs-12">APPROVE</button></a>
				</div>
			<!-- 	<div class="col-md-2 col-xs-6">
					<a href="#" id="rejectCr"><button id="rejectCrBtn" 
						class="btn promo-delete-btn col-md-10 col-md-offset-1 col-xs-12">REJECT CR</button></a>
				</div> -->	
			</div>
			
			<!-- Added by Kavitha D for promo download -SPRINT10 -->
			
			<c:if test="${roleId eq 'NCMM'}">
			<div class="download-btn">
				<input type="button" class="btn new-btn-download" value="PROMO DOWNLOAD" onclick="javascript: downloadPromotionFile();"></input>
			</div> 
			</c:if>
			
			<!-- Added by Kavitha D for promo upload -SPRINT10 -->
            <form:form action="#" id="promoApprovalUpload" modelAttribute="PromoCrBean"
                enctype="multipart/form-data" onsubmit="return uploadValidation()">
            <div class="promo-upload">PROMO APPROVAL UPLOAD</div>
            <div class="upload-file">        
                <div class="col-md-4 col-md-offset-4" style="padding: 30px 0px;">
                    <div class="upload-label">
                        <span><i class="glyphicon glyphicon-cloud-upload"></i></span> <span>
                            <b class="SEGOEUIL-font">Promo Approval Upload File</b>
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
                    <span id="uploadErrorMsg" style="display: none; color: red">Please upload .xlsx file</span>
                    <div class="" style="color: #fff; text-align: center">
                       <button id="KAMVolumeUpload" class="btn new-btn-primary">UPLOAD</button>
                    </div>
                </div>
                <div class="clearfix"></div>                
            </div>
        </form:form>
			
	<div id="add-depot" class="modal fade" role="dialog">

      <div class="modal-dialog">
      <div class="modal-content">
		<div class="modal-header proco-modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Reason</h4>
		</div>
		<div class="modal-body">
			<div class="row">
<form id="pwdform" method="post" action="" class="form-horizontal" style="padding: 10px 0;">
					<div class="col-md-12" id="msg-1">
						<div id="msg-error" class="alert err-alert-danger fade in"></div>
						<div id="msg-success" class="alert succ-alert-success fade in"></div>
						<div class="OpenSans-font">
							<div class="form-group">
								<label for="tme_confirmPwd" class="col-sm-5 control-label">Reason</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="reason"
										name="reason" placeholder="Reason"/>
								</div>
							</div>
						</div>
					</div>
					</form>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default chnage-close pull-left"
				data-dismiss="modal" >CLOSE</button>
			<button type="submit" id="SaveDetails"
				class="btn btn-primary  resetBtn  marginR10" onClick="javascript: validateForm();">SAVE</button>
				
		</div>
</div>
</div>
	</div>
	
		<div class="clearfix"></div>
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
		<script type="text/javascript" src="assets/js/custom/proco/promoCr.js"></script>
		
		<script type="text/javascript"
		src="assets/js/custom/proco/alert-modal.js"></script>
		<script type="text/javascript">
		var moc = '${mocJson}';
		var Mocvaluecr = $('#Mocvaluecr').val(); //Moc filter in ncmm by Viswas-SPRINT 10
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

    		  $('#approveCr').on('click',function(){
    			  $('#successblock').hide();
    				var promoIdList = [];
    				$.each($("table.promo-list-table tbody input[name='promoId']:checked"),
    				function() {
    					promoIdList.push($(this).val());
    						});

    				if(promoIdList.length>0){
    				$("#approveCr").attr("href", "http://34.120.128.205/VisibilityAssetTracker/approveCr.htm?promoid="+promoIdList);
    				} else{
        				$('#promoSelectErrorMsg').show().find('span').html('Please select Promotion.');
        				}
      		  });

    		  $('#rejectCr').on('click',function(){
    		    $('#successblock').hide();
  				var promoIdList = [];
  				$.each($("table.promo-list-table tbody input[name='promoId']:checked"),
  				function() {
  					promoIdList.push($(this).val());
  						});

  				if(promoIdList.length>0){
  					$("#rejectCr").attr("data-toggle", "modal");
  	  				$("#rejectCr").attr("href", "data-target=#add-depot");
  				} else{
      				$('#promoSelectErrorMsg').show().find('span').html('Please select Promotion.');
      				}
    		  });

    		 

    		  
				
          });

		</script>
</body>



</body>
</html>