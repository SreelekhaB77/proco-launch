<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
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
<link rel="stylesheet" type="text/css"	href="assets/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="assets/css/dataTables.bootstrap.css">
<link rel="stylesheet" href="assets/css/custom-font.css">
<link rel="stylesheet" type="text/css"	href="assets/css/bootstrap-multiselect.css">
<link rel="stylesheet" type="text/css" href="assets/css/font-awesome.css">
<link rel="stylesheet" type="text/css"	href="assets/css/proco-style.css">

</head>
<body class="Verdana-font">
	<jsp:include page="../proco/proco-header.jsp" />
	<nav class="navbar navbar-inverse navbar-fixed-top container-bg"
		style="position: relative; top: 115px; z-index: 2; background-image: none ! important; border: none ! important; background: #F6F3F3; padding: 4px 0px 20px 0px;">
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

							<!-- <li role="presentation"
								class="col-md-3 col-sm-6 col-xs-12 disaggregation"><a
								href="https://vat.hulcd.com/VisibilityAssetTracker/promoDisaggregation.htm">
									<div class="proco-disaggregation-icon"></div>
									<div class="tab-label-proco-disaggregation-inactive">Disaggregation</div>
							</a></li> -->
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 proco-collaboration-active"><a
							href="https://vat.hulcd.com/VisibilityAssetTracker/promoCollaboration.htm">
								<div class="proco-collaboration-icon"></div>
								<div class="tab-label-proco-collaboration-active">Collaboration</div>
						</a></li>

						 <li role="presentation" class="col-md-3 col-sm-6 col-xs-12 listing">
						 <a	href="https://vat.hulcd.com/VisibilityAssetTracker/promoListing.htm">
									<div class="proco-listing-icon"></div>
									<div class="tab-label-proco-create-inactive OpenSans-font">Promo Listing</div>
						 </a></li>
						
						<li role="presentation"	class="col-md-3 col-sm-6 col-xs-12 listing">
						<a href="https://vat.hulcd.com/VisibilityAssetTracker/promoDeletion.htm">
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
			<div class="alert succ-alert-success sucess-msg" id="successblock" style="margin-top:35px;">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${success}"></c:out>
			</div>
		</c:if>
		<c:if test="${errorMsg!=null}">
			<div class="alert err-alert-danger sucess-msg" id="errorblock" style="margin-top:35px;">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${errorMsg}"></c:out>
				<c:if test="${FILE_STATUS=='ERROR_FILE'}">
					<a href="https://vat.hulcd.com/VisibilityAssetTracker/downloadKamErrorFile.htm?level=${level}" id="downloadTempFileLink">Click
						here to Download Error File:</a>

				</c:if>
			</div>
		</c:if>


		<div class="proco-creation form-horizontal">

			<div class="promo-details" style="padding: 10px;">
				<span class="" style="color: #fff;font-weight:600;">DETAILS OF THE PROMO KAM COLLABORATION</span>
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

			<form action="https://vat.hulcd.com/VisibilityAssetTracker/downloadPromosForKamUpload.htm" method="POST"
				enctype="multipart/form-data">

				<div class="promo-form-details proco-collab-form" style="margin-bottom: 10px;">
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
										<input type="text" class="form-control" value=""
											id="promo_basepack" placeholder="ALL PRODUCTS">
									</div>

								</div>
							</div>
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
										<input type="text" class="form-control proco-collab-input" value="ALL CUSTOMERS"
											id="cust_chainL2" disabled="true" placeholder="ALL CUSTOMERS">
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
									<label for="unique-id" class="control-label col-md-12">CHOSE
										MOCs</label>

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
							<div class="col-md-3"></div>
						</div>
					</div>


					<div class="clearfix"></div>

				</div>

				<table id="promoTable"
					class="table table-striped table-bordered promo-collaboration-table"
					style="width: 100%; overflow-x: scroll; display: block;">
					<thead>
						<tr>
							<th>
								<!-- <input name="select_all" class="userCheck" value="1" id="example-select-all" type="checkbox" /> -->
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
							<th>STATUS</th>
						</tr>
					</thead>
				</table>
				<div class="download-btn">
					<select id="level" class="btn btn-primary" name="level"
						style="color: white; background-color: #3B5999;">
						<option selected="selected">L1 Depot level</option>
						<option>L2 Depot level</option>
					</select> <input id="download" type="submit" class="btn btn-primary"
						value="KAM DOWNLOAD" disabled="disabled"></input>
				</div>
			</form>
			<form:form action="https://vat.hulcd.com/VisibilityAssetTracker/uploadKam.htm" id="kamUpload" method="POST"
				modelAttribute="L1CollaborationBean" enctype="multipart/form-data"
				onsubmit="return uploadValidation()">
				<div class="promo-upload">KAM COLLABORATION</div>
				<div class="upload-file">
					<div class="col-md-4 col-md-offset-4" style="padding: 30px 0px;">
						<div class="upload-label">
							<span><i class="glyphicon glyphicon-cloud-upload"></i></span> <span>
								<b class="SEGOEUIL-font">KAM Upload File</b>
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
							<select id="level" class="btn btn-primary" name="level"
								style="color: white; background-color: #3B5999;">
								<option selected="selected">L1 Depot level</option>
								<option>L2 Depot level</option>
							</select>
							<button class="btn new-btn-primary">UPLOAD</button>
							
						</div>
					</div>

					<div class="clearfix"></div>
				</div>
			</form:form>

			<%-- <div class="col-md-12">
				<div class="col-md-3">
					<table class="table table-bordered" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>Customer Chain L1</th>
								<th>Geography</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${bean.customerChainL1}</td>
								<td>${bean.geography}</td>
							</tr>
						</tbody>
					</table>

					<table class="table table-bordered" cellspacing="0"
						style="margin-top: 15px;">
						<thead style="background: #458CCA;">
							<tr>
								<th style="text-align: center; color: #ffffff;">MOCs
									Considered</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${bean.mocs}</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="col-md-9 col-md-offset-0">
					<div class="form-group">
						<div class="col-md-3">
							<select class="form-control">
								<option>L1 Depot Split</option>
								<!-- <option selected="selected">L2 Depot Split</option> -->
							</select>
						</div>
						<div class="col-md-3 col-md-offset-2">
							<button class="btn"
								style="background: #458CCA; padding: 5px 40px; color: #fff;">RESET</button>
						</div>
					</div>
					<!-- <div class="form-group">
						<div class="col-md-6">
							<select class="form-control">
								<option>L1-L2 %</option>
								<option selected="selected">L2-Depot %</option>
							</select>
						</div>
					</div> -->
					<table class="table table-bordered col-table" cellspacing="0"
						style="margin-top: 15px;">
						<thead>
							<tr>
								<th colspan="2" rowspan="3" border="0"><button class="btn"
								style="background: #458CCA; padding: 5px 40px; color: #fff;">Save L1 Depot Split</button> </th>
								<th>Branch</th>
								<c:forEach var="item" items="${bean.depotList}">
											<th>${item.branch}</th>
								</c:forEach>
							</tr>
							<tr>
								<th>Cluster</th>
								<c:forEach var="item" items="${bean.depotList}">
											<th>${item.cluster}</th>
								</c:forEach>
							</tr>
							<tr>
								<th>State</th>
								<c:forEach var="item" items="${bean.depotList}">
											<th>${item.state}</th>
								</c:forEach>
							</tr>
							<tr>
								<th></th>
								<th>L2 Split</th>
								<th>Depot Split</th>
								<c:forEach var="item" items="${bean.depotList}">
											<th>${item.depot}</th>
								</c:forEach>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${bean.customerChainL1}_L1</td>
								<td>Total Qty</td>
								<td>${bean.totalQty}</td>
								<c:forEach var="item" items="${bean.depotList}">
											<td>${item.totalQty}</td>
								</c:forEach>
							</tr>
							<tr>
								<td></td>
								<td>System %</td>
								<td>${bean.systemPer}</td>
								<c:forEach var="item" items="${bean.depotList}">
											<td>${item.systemPer}</td>
								</c:forEach>
							</tr>
							<tr>
								<td></td>
								<td>KAM %</td>
								<td>${bean.kamPer}</td>
								<c:forEach var="item" items="${bean.depotList}">
											<td>${item.kamPer}</td>
								</c:forEach>
							</tr>
							<tr>
								<td></td>
								<td>Revised Qty</td>
								<td>${bean.revisedQty}</td>
								<c:forEach var="item" items="${bean.depotList}">
											<td>${item.revisedQty}</td>
								</c:forEach>
							</tr>
						</tbody>
					</table>
				</div>
			</div> --%>


		</div>
		<jsp:include page="../proco/proco-footer.jsp" />


		<!-- Bootstrap core JavaScript
    ================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<!-- Latest compiled and minified JavaScript -->
		<script type="text/javascript" src="assets/js/jquery-1.11.3.min.js"></script>

		<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="assets/js/jquery-ui.js"></script>
		<script type="text/javascript"
			src="assets/js/jquery.dataTables.min.js"></script>
		<script src="assets/js/dataTables.bootstrap.min.js"></script>
		<script type="text/javascript" src="assets/js/comboTreePlugin.js"></script>
		<script type="text/javascript"
		src="assets/js/custom/proco/alert-modal.js"></script>
		<script type="text/javascript"
			src="assets/js/bootstrap-multiselect.js"></script>
		<script type="text/javascript"
			src="assets/js/custom/proco/promoCollaboration.js"></script>
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
			var promoTable1 = null;

			/*  $(document).ready(function() {
				 $('.promo-disaggregation-table').DataTable({
					"bFilter" : false,
					"bLengthChange" : false,
					"bPaginate" : false,
					"bInfo" : false,
					"scrollX" : "100%"

				});
			});  */
		</script>
</body>

</html>