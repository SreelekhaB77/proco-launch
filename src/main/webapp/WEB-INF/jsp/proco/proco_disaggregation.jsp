<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Date,java.util.Calendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<!--  <link rel="icon" href="../../favicon.ico"> -->
<title>MT PLANNING</title>

<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/jquery-ui.css">

<link rel="stylesheet" type="text/css"
	href="assets/css/dataTables.bootstrap.css">
<link rel="stylesheet" href="assets/css/custom-font.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap-multiselect.css">
<!-- <link rel="stylesheet" type="text/css"
	href="assets/css/font-awesome.css"> -->

<link rel="stylesheet" type="text/css"
	href="assets/css/proco-style.css">

	<style>
	#addDepot{color:#fff;}
	input[type=number]::-webkit-inner-spin-button,
	input[type=number]::-webkit-outer-spin-button { 
  -webkit-appearance: none; 
  margin: 0; 
}
	</style>

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

						 <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 create"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoVolumeUpload.htm">
								<div class="proco-volume-icon"></div>
								<div class="tab-label-proco-volume-inactive">Volume Upload</div>
						</a></li>

						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 proco-disaggregation-active"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoDisaggregation.htm">
								<div class="proco-disaggregation-icon"></div>
								<div class="tab-label-proco-disaggregation-active">Disaggregation</div>
						</a></li>
						
						
						<!-- <li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 collaboration"><a
							href="http://34.120.128.205/VisibilityAssetTracker/promoCollaboration.htm">
								<div class="proco-collaboration-icon"></div>
								<div class="tab-label-proco-collaboration-inactive">Collaboration</div>
						</a></li> -->

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
		<c:if test="${success!=null}">
			<div class="alert alert-success sucess-msg" id="successblock"
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
					<a href="http://34.120.128.205/VisibilityAssetTracker/downloadPromotionErrorFile.htm" id="downloadTempFileLink">Click
						here to Download Error File:</a>

				</c:if>
			</div>
		</c:if>
	<!--Sprint-5 US-14 successblock msg--> 	
<div class="alert alert-success alert-dismissible" id="storelist-successblock" style="display:none;">
		                 <button type="button" class="close" data-dismiss="alert">&times;</button>
		                 <strong> Promos </strong><span></span>
		                 </div>

		<form action="http://34.120.128.205/VisibilityAssetTracker/disaggregatePromos.htm" method="POST" name="dc">
			<div class="proco-creation form-horizontal">

				<div class="promo-details" style="padding: 10px;">
					<span class="" style="color: #fff; font-weight:600;">DETAILS OF THE PROMO
						PLAN VOLUME DISAGGREGATION</span>
					<!--  <span class="pull-right promo-uom">
                <div class="col-md-12">
            <div class="form-group" style="    margin-bottom: 1px;">
                <label class="control-label col-md-6" for="uom">UNIQUE ID SEARCH</label>
                <div class="col-md-6" style="padding:0px;">
                <input type="text" class="form-control" id="promo_id" style="width:70%;">
                    <div class="search-field" ><img src="assets/images/main-search.png"alt=""></div>
                </div>
                
                </div>
                </div>
            </span> -->
					<!--     <div class="clearfix"></div> -->
				</div>

				<div class="promo-form-details proco-disaggregation-form" style="margin-bottom: 10px;">
					<div class="row">
						<div class="col-md-12">
							<div class="col-md-2">
								<div class="form-group">
									<label for="unique-id" class="control-label col-md-12">BASEPACK
										DETAILS</label>

								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
									<label for="unique-id" class="control-label col-md-4">BRAND</label>
									<div class="col-md-8">
										<select class="form-control" id="brand">
											<option>ALL BRANDS</option>
											<c:forEach var="brand" items="${brands}">
												<option value="${brand}">${brand}</option>
											</c:forEach>
										</select>
									</div>

								</div>

							</div>
							<div class="col-md-3">
								<div class="form-group">
									<label for="unique-id" class="control-label col-md-4">BASEPACK
										CODE</label>
									<div class="col-md-8">
										<!--<input type="text" class="form-control" value=""
											multiple="multiple" id="promo_basepack" placeholder="ALL PRODUCTS">-->
											<select class="form-control" id="promo_basepack"
											multiple="multiple">
											
										</select>
									</div>

								</div>
							</div>
							<!--  <div class="col-md-3">
                             <div class="form-group">
               <label for="unique-id" class="control-label col-md-4">BASEPACK DESC</label>
               <div class="col-md-8">
              <textarea class="form-control" ></textarea>
               </div>
               
               </div>
                
                   </div> -->
							<div class="col-md-3">
								<div class="form-group">
									<label for="unique-id" class="control-label col-md-4">CATEGORY</label>
									<div class="col-md-8">
										<select class="form-control" id="category">
											<option>ALL CATEGORIES</option>
											<c:forEach var="category" items="${categories}">
												<option value="${category}">${category}</option>
											</c:forEach>
										</select>
									</div>

								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-12">
							<div class="col-md-2">
								<div class="form-group">
									<label for="unique-id" class="control-label col-md-12">CUSTOMER
										DETAILS</label>

								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label col-md-4" for="uom">CUST
										CHAIN (L1)</label>
									<div class="col-md-8">
										<select class="form-control" id="cust_chainL1"
											multiple="multiple">
											<c:forEach var="item" items="${customerChainL1}">
												<option value="${item}">${item}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label col-md-4" for="uom">CUST
										CHAIN (L2)</label>
									<div class="col-md-8 switch-dynamic">
										<!--   <select class="form-control" id="cust_chainL2" multiple="multiple">
                    <option>ALL CUSTOMERS</option>
                    </select> -->
										<input type="text" class="form-control proco-disaggregate-input" value="ALL CUSTOMERS"
											id="cust_chainL2" disabled="true" placeholder="ALL CUSTOMERS">
									</div>

								</div>
							</div>
							<div class="col-md-3">
								<div class="disaggre-details">
									<h5>DISAGGREGATION</h5>

									<div class="dis-agg-txt">
										<p>TOTAL PROMOS</p>
										<p>PROMOS FOR DISAGGREGATION</p>
									</div>
									<div class="dis-agg-val">
										<p>
											<span style="padding: 0px 15px;">:</span><span
												id="totalCount">0</span>
										</p>
										<p>
											<span style="padding: 0px 15px;">:</span> <span
												id="selectedCount">0</span>
										</p>
									</div>
								</div>

							</div>

						</div>
					</div>


					<div class="row">
						<div class="col-md-12">
							<div class="col-md-2">

								<div class="form-group">
									<label for="unique-id" class="control-label col-md-12">OFFER
										DETAILS</label>

								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label col-md-4" for="uom">OFFER
										TYPE</label>
									<div class="col-md-8">
										<select class="form-control" id="offerType">
											<option>ALL TYPES</option>
											<c:forEach var="item" items="${offerTypes}">
												<option value="${item}">${item}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label col-md-4" for="uom">MODALITY</label>
									<div class="col-md-8">
										<select class="form-control" id="modality">
											<option>ALL MODALITIES</option>
											<c:forEach var="item" items="${modality}">
												<option value="${item.key}">${item.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-3"></div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-12">
							<div class="col-md-2">
								<div class="form-group">
									<label for="unique-id" class="control-label col-md-12">SELECT
										MOC's FOR SALE:</label>

								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label col-md-4" for="uom">YEAR</label>
									<div class="col-md-8">
										<select class="form-control" id="year">
											<option>ALL YEAR</option>
											<c:forEach var="item" items="${years}">
												<option value="${item}">${item}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label col-md-4" for="uom">MOC/QTR</label>
									<div class="col-md-8">
										<!--  <select class="form-control" id="moc">
                    <option>FULL YEAR</option>
                    </select> -->
										<input type="text" class="form-control" value="FULL YEAR"
											id="moc" placeholder="FULL YEAR">
									</div>
								</div>
							</div>
							<!--   <div class="col-md-3">
                <input type="button" value="DISAGGREGATION">
                </div> -->
						</div>
					</div>
					<div class="row">
						<div class="col-md-9">
							<div class="col-md-9 col-md-offset-4">
								<div class="checkbox mocrr-checkbox">
									<%
									Date d = new Date();
									int month = d.getMonth()+1;
									int year = Integer.valueOf(new String(Calendar.getInstance().get(Calendar.YEAR) + "").substring(2, 4));
									String[] kk = new String[12];
									int c = 0;
									for (int i = month; i <= 12; i++) {
										kk[c] = "MOC " + (i + 1) + "'" + (year - 1);
										if (i == 12) {
											for (int j = 0; j < month; j++) {
												kk[c] = "MOC " + (j + 1) + "'" + year;
												c++;
											}
										}
										c++;
									}
									%>
									<div class="col-md-2 col-xs-6">
										<%
											for (int i = 0; i < 3; i++) {
										%>
										<div class="checkbox">
											<label><input type="checkbox" name="mocs"
												value="<%=kk[i]%>"><%=kk[i]%></label>
										</div>
										<%
											}
										%>
									</div>

									<div class="col-md-2 col-xs-6">
										<%
											for (int i = 3; i < 6; i++) {
										%>
										<div class="checkbox">
											<label><input type="checkbox" name="mocs"
												value="<%=kk[i]%>"><%=kk[i]%></label>
										</div>
										<%
											}
										%>
									</div>

									<div class="col-md-2 col-xs-6">
										<%
											for (int i = 6; i < 9; i++) {
										%>
										<div class="checkbox">
											<label><input type="checkbox" name="mocs"
												value="<%=kk[i]%>"><%=kk[i]%></label>
										</div>
										<%
											}
										%>
									</div>

									<div class="col-md-2 col-xs-6">
										<%
											for (int i = 9; i < 12; i++) {
										%>
										<div class="checkbox">
											<label><input type="checkbox" name="mocs"
												value="<%=kk[i]%>"><%=kk[i]%></label>
										</div>
										<%
											}
										%>
									</div>
									<!-- <div class="col-md-2 col-xs-6">
										<div class="checkbox">
											<label><input type="checkbox" name="l"
												value="L3M">L3M</label>
										</div>
										<div class="checkbox">
											<label><input type="checkbox" name="l"
												value="L6M">L6M</label>
										</div>
										<div class="checkbox">
											<label><input type="checkbox" name="l"
												value="L2L">L2L</label>
										</div>
									</div> -->
								</div>
							</div>
						</div>
						<div class="col-md-2">
							<div class="promo-create-btn pull-right">
								<button class="btn btn-primary" id="disaggregateBtn"
									disabled="disabled">DISAGGREGATE</button>
									<input class="btn btn-primary" id="addDepot"  data-toggle="modal" data-target="#add-depot" type="button" value="ADD DEPOT" onClick="javascript: getBranch();">
							<input class="btn btn-primary" style="margin-top: 8px;margin-right: 130px;" id="SubmitKamBtn"
									disabled="disabled" type="button" value="SUBMIT TO KAM">
									<input class="btn btn-primary" style="margin-top: -33px;" id="dpDownload"
									disabled="disabled"  type="button" value="DP DOWNLOAD" onClick="javascript:DisagreegatedExcelDownload();">
									
							</div>
						</div>
					</div>

					<div class="clearfix"></div>

				</div>
				

				<table
					class="table table-striped table-bordered promo-list-table" id="disTable"
					cellspacing="0" style="width: 1975px!important;overflow-x: hidden;display: block;">
					<thead>
						<tr>
							<th>
							<!--sprint 5 select all changes for disaggration tab by bharati-->
								<input type="checkbox" id="select_all_promo" class="promo_id_all" name="checkAll" value="" /> 
							</th>
							<th>UNIQUE ID</th>
							<th>MOC</th>
							<th>CUSTOMER CHAIN L1</th>
							<!-- <th>CUSTOMER CHAIN L2</th> -->
							<th>SALES CATEGORY</th>
							<th>BRAND</th>
							<th>BASE PACK</th>
							<th>BASEPACK DESCRIPTION</th>
							<th>OFFER TYPE</th>
							<th>OFFER MODALITY</th>
							<th>OFFER DISC</th>
							<th>OFFER VALUE</th>
							<th>KITTING VALUE</th>
							<th>UOM</th>
							<th>GEOGRAPHY</th>
							<th>PLANNED QTY</th>
							<th>NATIONAL %</th>
							<th style="width:108px!important">STATUS</th>
						</tr>
					</thead>
				</table>
							
			</div>
		</form>
		<!-- <div class="dis-aggre" style="margin-top:15px;"><textarea class="form-control" rows="4" placeholder="DISAGGREGATION:"></textarea></div> -->

	</div>
	<jsp:include page="../proco/proco-footer.jsp" />
<div class="container">
<div id="add-depot" class="modal fade" role="dialog">

      <div class="modal-dialog">
      <div class="modal-content">
		<div class="modal-header proco-disagrrate-modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Add Depot</h4>
		</div>
		<div class="modal-body">
			<div class="row">
<form id="pwdform" method="post" action="" class="form-horizontal" style="padding: 10px 0;">
					<div class="col-md-12" id="msg-1">
						<div id="msg-error" class="alert err-alert-danger fade in"></div>
						<div id="msg-success" class="alert succ-alert-success fade in"></div>
						<div class="OpenSans-font">
								<div class="form-group">
								<label for="tme_oldPassword" class="col-sm-5 control-label">Branch Code</label>
								<div class="col-sm-6">
									<select class="form-control" id="branch" onChange="javascript: getCluster();">
											<option>SELECT</option>
										</select>
								</div>
							</div>
							<div class="form-group">
								<label for="tme_newPassword" class="col-sm-5 control-label">Cluster Code</label>
								<div class="col-sm-6">
									<select class="form-control" id="cluster" onChange="javascript: getDepot();" disabled>
										</select>
								</div>
							</div>
								<div class="form-group">
									<label for="unique-id" class="control-label col-sm-5">Depot</label>
									<div class="col-sm-6" id="depotDiv">
										<select class="form-control" id="depot" disabled>
										</select>
									</div>
								</div>
							
							<div class="form-group">
								<label for="tme_confirmPwd" class="col-sm-5 control-label">Quantity</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="quantity"
										name="Quantity" placeholder="Quantity" onkeypress="return onlyNos(event,this);">
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
	</div>
	
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<!-- Latest compiled and minified JavaScript -->
	<script type="text/javascript" src="assets/js/jquery-1.11.3.min.js"></script>

	<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="assets/js/comboTreePlugin.js"></script>
	<script type="text/javascript" src="assets/js/bootstrapValidator.js"></script>
	<script type="text/javascript" src="assets/js/jquery-ui.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap-multiselect.js"></script>
	<script type="text/javascript" src="assets/js/custom/proco/promoDisaggregation.js"></script>
	<script type="text/javascript" src="assets/js/custom/proco/addDepot.js"></script>
	<script type="text/javascript" src="assets/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="assets/js/dataTables.bootstrap.min.js"></script>


	<script type="text/javascript">
	
		var moc = '${mocJson}';
		var basepacks = '${basepacks}';

		var category = null;
		var brand = null;
		var basepack = null;
		var custChainL1 = null;
		var custChainL2 = null;
		var offerType = null;
		var modality = null;
		var year = null;
		var mocVal = null;
		var promoTable = null;

		var getCount = null;
		var disAggregationCount = null;

		$(document).ready(function() {
			/*   $('.promo-list-table').DataTable({
			      "bFilter": false,
			       "bLengthChange": false,
			     "bPaginate": false,
			      "bInfo": false,
			      "scrollX" : "100%"
			      
			  }); */
			$("#upload-file").on('click', function(e) {
				// e.preventDefault();
				console.log(e);

			});

			$('#choose-file').click(function() {
				$("#upload-file").trigger('click');
			});

			$('#upload-file').change(function(e) {
				console.log(e);
				var files = event.target.files;
				if (files.length > 0) {
					var fileName = files[0].name;
					$('.file-name').html(fileName);
				} else {
					$('.file-name').html("No file chosen");
				}

			});
		});
	</script>
	
</body>
</html>