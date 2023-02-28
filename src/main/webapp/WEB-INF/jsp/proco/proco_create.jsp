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
<link rel="stylesheet" href="assets/css/jquery-ui.css">
<link rel="stylesheet" href="assets/css/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="assets/css/bootstrap-multiselect.css">
<link rel="stylesheet" type="text/css" href="assets/css/proco-style.css">
<link rel="stylesheet" type="text/css" href="assets/css/custom-font.css">
<link rel="stylesheet" type="text/css" href="assets/css/font-awesome.css">

<script type="text/javascript">
	history.pushState(null, null, '');
	window.addEventListener('popstate', function(event) {
		history.pushState(null, null, '');
	});
</script>

</script>
<style>
/*bharati added below changes in sprint-9 US-1*/
.sample-upload-file {
	background: #3B5999;
	padding: 7px!important;
	font-size: 14px!important;
	margin-top: 20px;
	color: #fff;
	border-radius: 3px;
	border:1px solid #000!important;
	
}
.upload-file {
    height: 290px;
    padding: 10px!important;
}
.upload-group {
    
	margin:20px 0px!important;
}
.upload-div{
	color: #fff; text-align: center;margin-top:5px;
}
</style>
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
					<ul class="nav nav-pills">

						<li role="presentation" class="col-md-3 col-sm-6 col-xs-12 proco-craetion-active"><a href="http://localhost:8083/VisibilityAssetTracker/promoCreation.htm">
								<div class="proco-create-icon"></div>
								<div class="tab-label-proco-create-active" style="padding-top: 15px;">Promo Creation</div>
						</a></li>
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a
							href="http://localhost:8083/VisibilityAssetTracker/promoListing.htm">
								<div class="proco-listing-icon"></div>
								<div class="tab-label-proco-create-inactive OpenSans-font"style="margin-left: -30px; ">Promo Listing</div>
						</a></li>

						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a href="http://localhost:8083/VisibilityAssetTracker/promoDeletion.htm">
								<div class="proco-deletion-icon"></div>
								<div class="tab-label-proco-del-inactive OpenSans-font"style="margin-left: 10px;">Dropped Offer</div>
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
		<input type="hidden" id="roleId" value="${roleId}" />
		<c:if test="${FILE_STAUS=='SUCCESS_FILE'}">
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
				<c:if test="${FILE_STAUS=='ERROR_FILE'}">
					<a href="http://localhost:8083/VisibilityAssetTracker/downloadPromotionErrorFile.htm" id="downloadTempFileLink">Click
						here to Download Error File:</a>

				</c:if>
			</div>
		</c:if>
		
		<!--bharati added code for errorblock with download error file for sprint-9 US-1-->
	                 				<div class="alert alert-success sucess-msg" style="display: none;margin-top:35px;margin-bottom: -23px" id="Procosuccessblock">
		                                 <button type="button" class="close" data-hide="alert">&times;</button>
		                                 <span></span>
	                                </div>
                                    <div class="alert alert-danger" style="display: none;margin-top:35px;margin-bottom: -23px" id="ProcoRegularerrorblockUpload">
                                        <button type="button" class="close" data-hide="alert">&times;</button>
                                       
                                            <span>File contains error...</span>
                                            <a href="http://localhost:8083/VisibilityAssetTracker/downloadPromotionErrorFilensp.htm" id="downloadTempFileLink">Click here to Download Error File</a>
                                    </div>
									<div class="alert alert-danger" style="display: none;margin-top:35px;margin-bottom: -23px" id="errorblockUpload">
		                            <button type="button" class="close" data-hide="alert">&times;</button>
		
		                           <!-- <span>Error while uploading file.</span>-->
								   <span></span>
		
	                               </div>
								  
		
		<!--bharati code end here for sprint-9-->							
									
		<%-- <c:if test="${FILE_STATUS=='ERROR_FILE'}">
			<div class="alert alert-danger sucess-msg" id="errorblock">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<a href="#" id="downloadTempFileLink"
					onclick="javascript:downloadCoeErrorFile();">Click here to
					Download Error File:</a>

			</div>
		</c:if> --%>

		<div class="proco-creation form-horizontal">

			<!-- <div class="promo-back">
				<a href="http://localhost:8083/VisibilityAssetTracker/procoHome.htm"><span
					class="glyphicon glyphicon-arrow-left" aria-hidden="true"> </span></a>Promotion
				Creation
			</div> -->
			<!--bharati commented below create form for sprint-9 -->
			<!--<form:form action="http://localhost:8083/VisibilityAssetTracker/createPromotion.htm"
				modelAttribute="CreatePromotionBean" enctype="multipart/form-data"
				method="POST" id="createPromoForm">
				
				<input type="hidden" name="reasonText" id="reasonText" value="" />
			<input type="hidden" name="remarkText" id="remarkText" value="" />
			
				<div class="promo-details ">
					<span class="promo-detail-txt col-xs-12"><b>DETAILS OF THE PROMO PLAN</b></span> <span class="pull-right promo-uom">
						<div class="form-group" style="margin-bottom: 1px;">
							<label class="control-label col-md-3" for="uom">UOM</label>
							<div class="col-md-9">
								<form:select class="form-control" id="" path="uom">
									<option>Singles</option>
									<option>Combies</option>
								</form:select>
							</div>
						</div>

					</span>
					<div class="clearfix"></div>
				</div>

				<div class="promo-form-details">
					<div class="col-md-4">
						<div class="form-group">
						<label for="unique-id" class="control-label col-md-4"></label> 
							<div class="col-md-8">
								 <form:input type='hidden' id="startdate" path="startDate" name="start_date" placeholder="START DATE"	readonly="true"	class="form-control" />
								<form:input type='hidden' name="end_date" path="endDate" id="enddate" readonly="true" placeholder="END DATE" class="form-control" />
								 <!-- <select id="mocCrdate" name="mocCredate" class="form-control" multiple="multiple">
													</select> -->
													
								<%-- <form:input type="text" class="form-control" id="mocCrdate"
									value="Select MOC" readonly="true" path="customerChainL2" /> --%>
							
							<!--</div>
							<%-- <div class="col-md-4">
								<form:input type='text' name="end_date" path="endDate" id="enddate" readonly="true" placeholder="END DATE"
									 class="form-control tme-datepickerend" style="background-color:#fff!important;" />
							</div> --%>
						<!--</div>
						
						<div class="form-group">
							<label for="unique-id" class="control-label col-md-4">OFFER VALUE</label>
							<div class="col-md-4">
								<form:input type="text" class="form-control" value="" name="offerValue"
									id="offerValue" path="offerValue" />
							</div>
							<div class="col-md-4">
								
								<form:select class="form-control" id="offerValDrop" path="offerValDrop">
									<option value="">SELECT</option>
									<option value="%">%</option>
									<option value="ABS">ABS</option>
								</form:select>
							</div>

						</div>
						<div class="form-group">
							<label class="control-label col-md-4" for="uom">OFFER TYPE</label>
							<div class="col-md-8">
								<form:select class="form-control" id="offerType" path="offerType">
									<option>SELECT</option>
									<c:forEach var="item" items="${offerTypes}">
									<c:choose>
									<c:when test="${CreatePromotionBean.offerType == item}">
									  <option value="${item}" selected >${item}</option>
									</c:when>
									<c:otherwise>
									 <option value="${item}">${item}</option>
									</c:otherwise>
									</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>

					<div class="col-md-4">

						<div class="form-group">
							<label class="control-label col-md-4" for="uom">MOC</label>
							<div class="col-md-8">
								<%-- <form:input type="text" class="form-control" id="moc" readonly="true"
									path="moc" name="moc" /> --%>
								<form:select id="moc" name="moc" path="moc" class="form-control" multiple="multiple">
								</form:select>--%>
								<!-- <select class="form-control" id="">
								<option>Q1</option>
							</select> -->
						<!--	</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4" for="uom">MODALITY</label>
							<div class="col-md-8">
								<!-- <input type="text" class="form-control" value="ALL MODALITY" id="modality"> -->
								<%--<form:select class="form-control" id="modality" path="modality">
								<!--	<option>SELECT</option>
									<c:forEach var="item" items="${modality}">
									<c:choose>
									<c:when test="${CreatePromotionBean.modality == item.value}">
									  <option value="${item.key}" selected >${item.value}</option>
									</c:when>
									<c:otherwise>
									 <option value="${item.key}">${item.value}</option>
									</c:otherwise>
									</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<!--<div class="form-group">
							<label class="control-label col-md-4" for="uom">GEOGRAPHY</label>
							<div class="col-md-8">
								<!-- <select class="form-control" id="geography">
						
						</select> -->
						<%--	<c:choose>
							<c:when test="${duplicate eq true }">
						<%-- 	<form:input type="text" class="form-control" value="ALL INDIA" disabled="true"
									id="editGeography" path="geography" /> --%>
						<%--	<form:input type="text" class="form-control" 
									id="editGeography" path="geography" readonly="true" />
							</c:when>
							<c:otherwise>
							<form:input type="text" class="form-control" value="ALL INDIA" 
									id="geography" path="geography" />
							</c:otherwise>
							</c:choose>--%>

								<!-- <select class="form-control" id="">
								<option>ALL INDIA</option>
							</select> -->
							<!--</div>
						</div>

					</div>

					<div class="col-md-4">
						<div class="form-group">
							<label for="unique-id" class="control-label col-md-4">KITTING
								VALUE</label>
							<div class="col-md-4">
								<form:input type="text" class="form-control" value=""
									id="kittingValue" path="kittingValue" />
							</div>
							<div class="col-md-4">
								
								<select class="form-contro" id="kittingValDrop" path="kittingValDrop" style="height:30px;">
									<option value="">SELECT</option>
									<option value="%">%</option>
									<option value="ABS">ABS</option>
								</select>
							</div>
						</div>
						


						<div class="form-group">
							<label class="control-label col-md-4" for="uom">L1-CUST
								CHAIN (P)</label>
							<div class="col-md-8">
								<form:select class="form-control" id="customerChainL1"
									multiple="multiple" path="customerChainL1">
									<c:forEach var="item" items="${customerChainL1}">
										<option value="${item}">${item}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4" for="uom">L2-CUST
								CHAIN (s)</label>
							<div class="col-md-8 switch-dynamic">
								<form:input type="text" class="form-control proco-create-input" id="cust-chain"
									value="ALL CUSTOMERS" readonly="true" path="customerChainL2" />
							</div>

						</div>
					</div>

					<div class="clearfix"></div>

				</div>
				<div id="accordion" role="tablist" aria-multiselectable="true">
					<div class="card">
						<div class="card-header" role="tab" id="headingOne">
							<div class="mb-0">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseOne" aria-expanded="false"
									aria-controls="collapseOne"> <span>PARENT GROUP
										ITEMS</span><span class="glyphicon glyphicon-plus plus-minus-icon"></span>
								</a>
							</div>
						</div>
						<div id="collapseOne" class="collapse" role="tabpanel"
							aria-labelledby="headingOne">
							<div class="card-block">
								<div class="parent-table">
									<div class="table-responsive">
										<table class="table">
											<thead>
												<tr>
													<th></th>
													<th><span class="proco-btn proco-btn-success">PARENT</span></th>
													<th><span class="proco-btn proco-btn-success">CATEGORY</span></th>
													<th><span class="proco-btn proco-btn-success">BRAND</span></th>
													<th><span class="proco-btn proco-btn-success">PARENT
															DESCRIPTION</span></th>
													<th><span class="proco-btn proco-btn-success">RATIO</span></th>
												</tr>
											</thead>

											<tbody>
												<tr>
													<td class="serial-no"><div>1</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="basepack1"
															path="basepack1" /></td>
													<td><form:input type="text" class="form-control basepack-cat"
															path="category1" readonly="true" /></td>
													<td><form:input type="text" class="form-control basepack-brnd"
															path="brand1" readonly="true" /></td>
													<td><form:input type="text" class="form-control basepack-desc"
															path="basepackDesc1" readonly="true" /></td>
													<td><form:input type="text" class="form-control basepack-ratio"
															path="ratio1" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>2</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="basepack2"
															path="basepack2" /></td>
													<td><form:input type="text" class="form-control basepack-cat"
															path="category2" readonly="true" /></td>
													<td><form:input type="text" class="form-control basepack-brnd"
															path="brand2" readonly="true" /></td>
													<td><form:input type="text" class="form-control basepack-desc"
															path="basepackDesc2" readonly="true" /></td>
													<td><form:input type="text" class="form-control basepack-ratio"
															path="ratio2" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>3</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="basepack3"
															path="basepack3" /></td>
													<td><form:input type="text" class="form-control basepack-cat"
															path="category3" readonly="true" /></td>
													<td><form:input type="text" class="form-control  basepack-brnd"
															path="brand3" readonly="true" /></td>
													<td><form:input type="text" class="form-control basepack-desc"
															path="basepackDesc3" readonly="true" /></td>
													<td><form:input type="text" class="form-control basepack-ratio"
															path="ratio3" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>4</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="basepack4"
															path="basepack4" /></td>
													<td><form:input type="text" class="form-control basepack-cat"
															path="category4" readonly="true" /></td>
													<td><form:input type="text" class="form-control  basepack-brnd"
															path="brand4" readonly="true" /></td>
													<td><form:input type="text" class="form-control basepack-desc"
															path="basepackDesc4" readonly="true" /></td>
													<td><form:input type="text" class="form-control basepack-ratio"
															path="ratio4" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>5</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="basepack5"
															path="basepack5" /></td>
													<td><form:input type="text" class="form-control basepack-cat"
															path="category5" readonly="true" /></td>
													<td><form:input type="text" class="form-control  basepack-brnd"
															path="brand5" readonly="true" /></td>
													<td><form:input type="text" class="form-control basepack-desc"
															path="basepackDesc5" readonly="true" /></td>
													<td><form:input type="text" class="form-control basepack-ratio"
															path="ratio5" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>6</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="basepack6"
															path="basepack6" /></td>
													<td><form:input type="text" class="form-control basepack-cat" 
															path="category6" readonly="true" /></td>
													<td><form:input type="text" class="form-control  basepack-brnd"
															path="brand6" readonly="true" /></td>
													<td><form:input type="text" class="form-control basepack-desc"
															path="basepackDesc6" readonly="true" /></td>
													<td><form:input type="text" class="form-control basepack-ratio"
															path="ratio6" /></td>
												</tr>
											</tbody>

										</table>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="card">
						<div class="card-header" role="tab" id="headingTwo">
							<div class="mb-0">
								<a class="collapsed" data-toggle="collapse"
									data-parent="#accordion" href="#collapseTwo"
									aria-expanded="false" aria-controls="collapseTwo"
									id="parentCollapse"> <span>CHILD GROUP ITEMS</span> <span
									class="glyphicon glyphicon-plus plus-minus-icon"></span>
								</a>
							</div>
						</div>
						<div id="collapseTwo" class="collapse" role="tabpanel"
							aria-labelledby="headingTwo">
							<div class="card-block">
								<div class="child-table">
									<div class="table-responsive">
										<table class="table">
											<thead>
												<tr>
													<th></th>
													<th><span class="proco-btn proco-btn-warning">CHILD</span></th>
													<th><span class="proco-btn proco-btn-warning">CATEGORY</span></th>
													<th><span class="proco-btn proco-btn-warning">BRAND</span></th>
													<th><span class="proco-btn proco-btn-warning">CHILD
															DESCRIPTION</span></th>
													<th><span class="proco-btn proco-btn-warning">RATIO</span></th>
												</tr>
											</thead>

											<tbody>
												<tr>
													<td class="serial-no"><div>1</div></td>
													<td><form:input type="text"
															class="form-control basepack-child" id="childBasepack1"
															path="childBasepack1" /></td>
													<td><form:input type="text" class="form-control child-basepack-cat"
															path="childCategory1" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-brand"
															path="childBrand1" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-desc"
															path="childBasepackDesc1" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-ratio"
															path="childRatio1" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>2</div></td>
													<td><form:input type="text"
															class="form-control basepack-child" id="childBasepack2"
															path="childBasepack2" /></td>
													<td><form:input type="text" class="form-control child-basepack-cat"
															path="childCategory2" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-brand"
															path="childBrand2" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-desc"
															path="childBasepackDesc2" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-ratio"
															path="childRatio2" /></td>
												</tr>

												<tr>
													<td class="serial-no"><div>3</div></td>
													<td><form:input type="text"
															class="form-control basepack-child" id="childBasepack3"
															path="childBasepack3" /></td>
													<td><form:input type="text" class="form-control child-basepack-cat"
															path="childCategory3" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-brand"
															path="childBrand3" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-desc"
															path="childBasepackDesc3" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-ratio"
															path="childRatio3" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>4</div></td>
													<td><form:input type="text"
															class="form-control basepack-child" id="childBasepack4"
															path="childBasepack4" /></td>
													<td><form:input type="text" class="form-control child-basepack-cat"
															path="childCategory4" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-brand"
															path="childBrand4" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-desc"
															path="childBasepackDesc4" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-ratio"
															path="childRatio4" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>5</div></td>
													<td><form:input type="text"
															class="form-control basepack-child" id="childBasepack5"
															path="childBasepack5" /></td>
													<td><form:input type="text" class="form-control child-basepack-cat"
															path="childCategory5" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-brand"
															path="childBrand5" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-desc"
															path="childBasepackDesc5" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-ratio"
															path="childRatio5" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>6</div></td>
													<td><form:input type="text"
															class="form-control basepack-child" id="childBasepack6"
															path="childBasepack6" /></td>
													<td><form:input type="text" class="form-control child-basepack-cat"
															path="childCategory6" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-brand"
															path="childBrand6" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-desc"
															path="childBasepackDesc6" readonly="true" /></td>
													<td><form:input type="text" class="form-control child-basepack-ratio"
															path="childRatio6" /></td>
												</tr>
											</tbody>

										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="third-block">
					<div class="col-md-8">
						<div class="form-group">
							<label for="unique-id" class="control-label col-md-4">3RD
								PARTY DESC</label>
							<div class="col-md-8">
								<form:input type="text" class="form-control" value=""
									path="thirdPartyDesc" />
							</div>

						</div>
					</div>
					<div class="col-md-4">

						<div class="form-group">
							<label for="unique-id" class="control-label col-md-4">RATIO</label>
							<div class="col-md-8">
								<form:input type="text" class="form-control" value=""
									path="thirdPartyRatio" />
							</div>

						</div>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="promo-desc">PROMO DESCRIPTION</div>
				<form:textarea class="form-control" rows="3" placeholder="PROMO DESCRIPTION HERE..." path="promoDesc" id="promoDesc"></form:textarea>
				<div class="promo-create-btn">
					<!-- <button class="btn btn-primary" id="createPromo">CREATE PROMO</button> -->
					<!--bharati disable below btn in sprint-9 -->
					<!--<input class="btn new-btn-primary" type="button" id="createBtn" value="CREATE PROMO" onClick="javascript: validateForm();" disabled></input>
				</div>
			</form:form>-->
			
			<!--bharati commented part end here for sprint-9-->
<!--bharati commented below code for sprint-9-->
			<!--<form:form action="http://localhost:8083/VisibilityAssetTracker/uploadPromoCreation.htm" id="promoCreateUpload"
				method="POST" modelAttribute="CreatePromotionBean"
				enctype="multipart/form-data" >
				<div class="promo-upload">PROMO UPLOAD</div>
				<div class="upload-file">
					<div class="col-md-4 col-md-offset-4" style="padding: 30px 0px;">
						<div class="upload-label">
							<span><i class="glyphicon glyphicon-cloud-upload"></i></span> <span>
								<b class="SEGOEUIL-font">Upload File</b>
							</span>
						</div>
						<div class="upload-group">

							<div class="cust-file">
								<span class="btn btn-upload " id="choose-file">Choose
									File</span>
							</div>

							<input type="file" class="form-control" value="" name="file"
								id="upload-file">

							<div class="file-name" style="line-height: 2.3">No file
								chosen</div>
						</div>

						<div class="" style="color: #fff; text-align: center">
							<button class="btn new-btn-primary">UPLOAD</button>

						</div>
			</form:form>
			<!-- <div class="sample-upload-file">
				 <span><i class="glyphicon glyphicon-cloud-download"></i></span> <span>
					<a href="http://localhost:8083/VisibilityAssetTracker/downloadPromotionTemplateFile.htm" style="color: #ffffff"
					id="downloadTemplate" class="modelClick"><b
						class="SEGOEUIL-font">Click here to download the Template file</b></a>
				</span>
			</div> -->
			<!--<form:form action="http://localhost:8083/VisibilityAssetTracker/downloadPromotionTemplateFile.htm" id="getPromoTemplate"
				method="GET" modelAttribute="CreatePromotionBean"
				enctype="multipart/form-data">
				<button class="sample-upload-file" style="color: #ffffff; width: 552px;">Click here to download the Template file</button>
			</form:form>
			
		</div>-->
		<!--bharati added below uploads and downloads code for sprint-9 US-1-->
		<div class="col-lg-12">
		<div class="promo-upload" style="margin-bottom:20px;">PROMO UPLOAD</div>
			<div class="col-lg-4 col-xs-12 col-sm-4">
				
				<div class="upload-file">
				<form:form action="#" id="promoCreateRegularUpload" modelAttribute="CreatePromotionBean" enctype="multipart/form-data">
					
						<div class="upload-label">
							<span><i class="glyphicon glyphicon-cloud-upload"></i></span> <span>
								<b class="SEGOEUIL-font">Upload Regular File</b>
							</span>
						</div>
						
						<div class="upload-group">
                        
							<div class="cust-file">
								<span class="btn btn-upload " id="choose-file">Choose
									File</span>
							</div>
                                  
							<input type="file" class="form-control" value="" name="file"
								id="upload-file">

							<div class="file-name" style="line-height: 2.3">No file
								chosen</div>
							
						</div>
                         <span id="uploadErrorMsg" style="display: none; color: red">Please upload .xls or .xlsx file</span>
						<div class="upload-div">
							<button id="ProcoRegularFileUpload" class="btn new-btn-primary">UPLOAD</button>

						</div>
						
			</form:form>
			
			<form:form action="http://localhost:8083/VisibilityAssetTracker/downloadPromotionRegularTemplateFile.htm" id="getPromoRegularTemplate"
				method="GET" modelAttribute="CreatePromotionBean"
				enctype="multipart/form-data" style="text-align:center">
				<button class="sample-upload-file" style="color: #ffffff;width:330px;">Download the Regular Template file</button>
			</form:form>
			
		</div>
		</div>
		
			<div class="col-lg-4 col-xs-12 col-sm-4">
				
				<div class="upload-file">
				<form:form action="#" id="promoCreateCrUpload" modelAttribute="CreatePromotionBean" enctype="multipart/form-data" >
					
						<div class="upload-label">
							<span><i class="glyphicon glyphicon-cloud-upload"></i></span> <span>
								<b class="SEGOEUIL-font">Upload CR File</b>
							</span>
						</div>
						<div class="upload-group">

							<div class="cust-file">
							
								<span class="btn btn-upload " id="choose-file1">Choose
									File</span>
							</div>

							<input type="file" class="form-control" value="" name="file"
								id="upload-file1">

							<div class="file-name1" style="line-height: 2.3">No file
								chosen</div>
						</div>
                       <span id="uploadcrErrorMsg" style="display: none; color: red">Please upload .xls or .xlsx file</span>
						<div class="upload-div">
							<button id="procoCRUploadBtn" class="btn new-btn-primary">UPLOAD</button>

						</div>
						
			</form:form>
			
			<form:form action="http://localhost:8083/VisibilityAssetTracker/downloadPromotionCrTemplateFile.htm" id="getPromoCrTemplate"
				method="GET" modelAttribute="CreatePromotionBean"
				enctype="multipart/form-data" style="text-align:center">
				<button class="sample-upload-file" style="color: #ffffff;width:330px;">Download the CR Template file</button>
			</form:form>
			</div>
		</div>
		<div class="col-lg-4 col-xs-12 col-sm-4">
				
				<div class="upload-file">
				<form:form action="#" id="promoCreateNewEntriesUpload" modelAttribute="CreatePromotionBean" enctype="multipart/form-data" >
					
						<div class="upload-label">
							<span><i class="glyphicon glyphicon-cloud-upload"></i></span> <span>
								<b class="SEGOEUIL-font">Upload New Entries File</b>
							</span>
						</div>
						<div class="upload-group">

							<div class="cust-file">
								<span class="btn btn-upload " id="choose-file2">Choose
									File</span>
							</div>

							<input type="file" class="form-control" value="" name="file"
								id="upload-file2">

							<div class="file-name2" style="line-height: 2.3">No file
								chosen</div>
						</div>
                       <span id="uploadNewErrorMsg" style="display: none; color: red">Please upload .xls or .xlsx file</span>
						<div class="upload-div">
							<button id="uploadnewEntriesBtn" class="btn new-btn-primary">UPLOAD</button>

						</div>
						
			</form:form>
			
			<form:form action="http://localhost:8083/VisibilityAssetTracker/downloadPromotionNewTemplateFile.htm" id="getPromoNewEntriesTemplate"
				method="GET" modelAttribute="CreatePromotionBean"
				enctype="multipart/form-data" style="text-align:center">
				<button class="sample-upload-file" style="color: #ffffff;width:330px;">Download the New Entries Template file</button>
			</form:form>
			</div>
		</div>
		<!--bharati code end here-->
		</div>
		<div class="clearfix"></div>
		<div id="add-depot" class="modal fade" role="dialog">
      <div class="modal-dialog">
      <div class="modal-content">
		<div class="modal-header proco-modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Reason</h4>
		</div>
		<div class="modal-body">
			<div class="row">
<form id="pwdform" method="post" action="" class="form-horizontal">
					<div class="col-md-12" id="msg-1">
						<div id="msg-error" class="alert err-alert-danger fade in"></div>
						<div id="msg-success" class="alert succ-alert-success fade in"></div>
						<div class="OpenSans-font">
								<div class="form-group">
								<label for="tme_oldPassword" class="col-sm-5 control-label">Reason</label>
								<div class="col-sm-6">
									<select class="form-control" id="reason" onChange="javascript: enableRemark();">
									<option value="SELECT">SELECT</option>
									<c:forEach var="item" items="${reasonList}">
										<option value="${item}">${item}</option>
									</c:forEach>
									
										</select>
								</div>
								</div>
							
							<div class="form-group">
								<label for="tme_confirmPwd" class="col-sm-5 control-label">Remark</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="remark"
										name="remark" placeholder="Remark" maxlength="40" disabled="disabled">
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
			<!-- <button type="submit" id="SaveDetails"
				class="btn btn-primary  resetBtn  marginR10" onClick="javascript: submitForm();">SAVE</button> -->
				<input type="button" id="createBtn" value="SAVE" class="btn btn-primary  resetBtn  marginR10" onClick="javascript: submitForm();"></input>
				
		</div>
</div>
</div>
	</div>
	</div>
	<jsp:include page="../proco/proco-footer.jsp" />
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<!-- Latest compiled and minified JavaScript -->
	<script type="text/javascript" src="assets/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="assets/js/comboTreePlugin.js"></script>
	<script type="text/javascript" src="assets/js/bootstrapValidator.js"></script>
	<script type="text/javascript" src="assets/js/jquery-ui.js"></script>
	<script type="text/javascript" src="assets/js/custom/proco/createPromo.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap-multiselect.js"></script>
	<script type="text/javascript" src="assets/js/custom/proco/alert-modal.js"></script>
	<script type="text/javascript">
		var geographyData = '${geographyJson}';
		//var moc = '${mocJson}';

		var bool;
	function getMocMonth(selectedDate){
		var d1=$('#startdate').val();
		var d2=selectedDate;
		$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : "http://localhost:8083/VisibilityAssetTracker/getMocMonthForProco.htm?startDate=" + $('#startdate').val()+"&endDate="+$('#enddate').val(),
		success : function(data) {
			if(data.length>0){
			$('#moc').val(data);
			bool=true;
			}else{
				bool =false;
				}
			return bool;
		}
	});
		}
		
		
		$(document).ready(function(){
		

			$('.tme-datepicker').datepicker(
                    {
                           showOn : "both",
                           dateFormat : "dd/mm/yy",
                           minDate : 0,
                           maxDate: "+180D",
                           buttonImage : "assets/images/calender.png",
                           buttonImageOnly : true,
                           buttonText : "Select date",
                           changeMonth : false,
                           numberOfMonths : 1,
                           /* beforeShow : function() {
                                  $(this).datepicker('option', 'maxDate',
                                                $('.tme-datepickerend').val());
                           }, */
                           onSelect : function(selectedDate) {
                        	  getMocMonth();
                                  //$('#startdate-msg').hide();
                                  $(this).val(selectedDate);
                                  if($('#enddate').val() != ""){
					  					getMocMonth(selectedDate);
									}
                                  var date=$(this).datepicker("getDate");
                          		  var d = date.getDate()+1;
                                  date.setDate(d); 
                                  var dateText = $.datepicker.formatDate("dd/mm/yy", date);
                                  $('.tme-datepickerend').datepicker("option", "minDate",dateText);
                              
                           }

                    });

			 $('.tme-datepickerend').datepicker(
                     {
                            defaultDate : "+1M",
                            maxDate:"+211D",
                            showOn : "both",
                            dateFormat : "dd/mm/yy",
                            buttonImage : "assets/images/calender.png",
                            buttonImageOnly : true,
                            buttonText : "Select date",
                            changeMonth : false,
                            numberOfMonths : 1,
                            beforeShow : function() {
                                   if ($('.tme-datepicker').val() === '')
                                          $(this).datepicker('option', 'minDate', 0);
                            }, 
                            onSelect : function(selectedDate) {
                                   //$('#enddate-msg').hide();
                                   if($('#startdate').val() == ""){
                                 	  $('#enddate').val('');
                                 	  //$('#enddate-msg').show().html('Please select start date first.');
                 					  return false;
                 					}
                                  
                                   $(this).val(selectedDate);
                                  getMocMonth();
                                   var data =getMocMonth(selectedDate);
                                   if (data == false) {
                                          //$('#moc-msg').show();
                                          $('#createPromo').prop('disabled', true);
                                   } else {
                                          //$('#moc-msg').hide();
                                          $('#createPromo').prop('disabled', false);
                                   }
                            }
                     });

            


			if('${duplicate}'){
			var chainL1 = '${CreatePromotionBean.customerChainL1}';
			var arrChainL1 = chainL1.split(',');
			for(var i=0;i<arrChainL1.length;i++){
			$('#customerChainL1').multiselect('select',arrChainL1[i]);
			}
			
			if (arrChainL1.length > 0) {
				
				$('.switch-dynamic').html('<select class="form-control" name="cust-chain" id="cust-chain" multiple="multiple"><option values="ALL CUSTOMERS">ALL CUSTOMERS</option>');

				getCustChainValues(arrChainL1.toString());

			} else {
				$('.switch-dynamic').html('<input type="text" name="cust-chain" class="form-control" id="cust-chain" value="ALL CUSTOMERS" readonly="true">');
			}
			var chainL2 = '${CreatePromotionBean.customerChainL2}';
			var arrChainL2 = chainL2.split(',');
			for(var i=0;i<arrChainL2.length;i++){
			$('#cust-chain').multiselect('select',arrChainL2[i]);
			}
			}
		});
	</script>
</body>
</html>