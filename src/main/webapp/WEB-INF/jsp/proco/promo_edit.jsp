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
<link rel="stylesheet" type="text/css"	href="assets/css/bootstrap-multiselect.css">
<link rel="stylesheet" type="text/css"	href="assets/css/proco-style.css">
<link rel="stylesheet" type="text/css"	href="assets/css/custom-font.css">
<link rel="stylesheet" type="text/css" href="assets/css/font-awesome.css">

<script type="text/javascript">
	history.pushState(null, null, '');
	window.addEventListener('popstate', function(event) {
		history.pushState(null, null, '');
	});
</script>
</head>
<body>

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
					<ul class="nav nav-pills">

						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 proco-craetion-active"><a
							href="https://vat.hulcd.com/VisibilityAssetTracker/promoCreation.htm">
								<div class="proco-create-icon"></div>
								<div class="tab-label-proco-create-active">Promo Edit</div>
						</a></li>
						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a
							href="https://vat.hulcd.com/VisibilityAssetTracker/promoListing.htm">
								<div class="proco-listing-icon"></div>
								<div class="tab-label-proco-create-inactive OpenSans-font">Promo
									Listing</div>
						</a></li>

						<li role="presentation"
							class="col-md-3 col-sm-6 col-xs-12 listing"><a href="https://vat.hulcd.com/VisibilityAssetTracker/promoDeletion.htm">
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

		<input type="hidden" id="roleId" value="${roleId}" />
		<c:if test="${FILE_STATUS=='SUCCESS_FILE'}">
			<div class="alert succ-alert-success sucess-msg" id="successblock"
				style="display: block">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${success}"></c:out>
			</div>
		</c:if>
		<c:if test="${errorMsg!=null}">
			<div class="alert err-alert-danger sucess-msg" id="errorblock">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${errorMsg}"></c:out>
				<c:if test="${FILE_STATUS=='ERROR_FILE'}">
					<a href="https://vat.hulcd.com/VisibilityAssetTracker/downloadPromotionErrorFile.htm" id="downloadTempFileLink">Click
						here to Download Error File:</a>

				</c:if>
			</div>
		</c:if>
		<%-- <c:if test="${FILE_STATUS=='ERROR_FILE'}">
			<div class="alert alert-danger  sucess-msg" id="errorblock">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<a href="#" id="downloadTempFileLink"
					onclick="javascript:downloadCoeErrorFile();">Click here to
					Download Error File:</a>

			</div>
		</c:if> --%>

		<div class="proco-creation form-horizontal">

			<!-- <div class="promo-back">
				<a href="https://vat.hulcd.com/VisibilityAssetTracker/procoHome.htm"><span
					class="glyphicon glyphicon-arrow-left" aria-hidden="true"> </span></a>Promotion
				Creation
			</div> -->
			<form:form action="https://vat.hulcd.com/VisibilityAssetTracker/updatePromotion.htm"
				modelAttribute="CreatePromotionBean" enctype="multipart/form-data"
				method="POST" id="updatePromoForm">
				<input type="hidden" name="reasonText" id="reasonText" value="" />
			<input type="hidden" name="remarkText" id="remarkText" value="" />
			<input type="hidden" name="changesMadeText" id="changesMadeText" value="" />
				<div class="promo-details ">
					<span class="promo-detail-txt col-xs-12"><b>DETAILS OF
							THE PROMO PLAN</b></span> <span class="pull-right promo-uom">
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
							<label for="unique-id" class="control-label col-md-4">UNIQUE ID</label>
							<div class="col-md-8">
								<form:input type="text" class="form-control" value="" readonly="true" id="uniqueId"  path="uniqueId"/>
							</div>

						</div>
						<div class="form-group">
							<label for="unique-id" class="control-label col-md-4">OFFER VALUE</label>
							<div class="col-md-4">
								<form:input type="text" class="form-control" value="" name="offerValue"
									id="offerValue" path="offerValue" />
							</div>
							
							<div class="col-md-4">
								<!-- <input type="text" class="form-control" value="ALL MODALITY" id="modality"> -->
								<form:select class="form-control" id="offerValDrop" path="offerValDrop">
									<option value="${CreatePromotionBean.offerValDrop}">
												<c:out value="${CreatePromotionBean.offerValDrop}"></c:out>
											</option>
											<c:choose>
												<c:when test="${CreatePromotionBean.offerValDrop == '%'}">
													<option value="ABS">ABS</option>
													<option value="">SELECT</option>
												</c:when>
												<c:otherwise>
													<option value="%">%</option>
													<option value="">SELECT</option>
												</c:otherwise>
											</c:choose>
								</form:select>
							</div>

						</div>
						
						<div class="form-group">
						<label for="unique-id" class="control-label col-md-4">KITTING VALUE</label>
							<div class="col-md-8">
								<form:input type="text" class="form-control" value=""
									id="kittingValue" path="kittingValue" />
							</div>
						</div>
					</div>

			
					<div class="col-md-4">

						<%-- <div class="form-group">
						<label for="unique-id" class="control-label col-md-4">START/END DATE</label>
							<div class="col-md-4">
								<form:input type='text' id="startdate" path="startDate" name="start_date" placeholder="START DATE"
								readonly="true"	class="form-control tme-datepicker" style="background-color:#fff!important;" />
							</div>
							<div class="col-md-4">
								<form:input type='text' name="end_date" path="endDate" id="enddate" readonly="true" placeholder="END DATE"
									 class="form-control tme-datepickerend" style="background-color:#fff!important;" />
							</div>
							<label class="control-label col-md-4" for="uom">YEAR</label>
							<div class="col-md-3">
							<form:input type="text" class="form-control"  id="editYear" readonly="true" path="year" />
							</div>

							<label class="control-label col-md-1" for="uom">MOC</label>
							<div class="col-md-4">
								<form:input type="text" class="form-control"  id="editMoc" readonly="true" path="moc" />
								<!-- <select class="form-control" id="">
								<option>Q1</option>
							</select> -->
							</div> 
						</div>--%>
						<div class="form-group">
							<label class="control-label col-md-4" for="uom">MODALITY</label>
							<div class="col-md-8">
								<!-- <input type="text" class="form-control" value="ALL MODALITY" id="modality"> -->
								<form:select class="form-control" id="modality" path="modality">
									<option>SELECT</option>
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
						<div class="form-group">
							<label class="control-label col-md-4" for="uom">GEOGRAPHY</label>
							<div class="col-md-8">
								<!-- <select class="form-control" id="geography">
						
						</select> -->
								<form:input type="text" class="form-control" 
									id="geography" path="geography" />

								<!-- <select class="form-control" id="">
								<option>ALL INDIA</option>
							</select> -->
							</div>
						</div>
							<div class="form-group">
							<label class="control-label col-md-4" for="uom">OFFER TYPE</label>
							<div class="col-md-8">
								<form:select class="form-control" id="" path="offerType">
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
									path="moc" /> --%>
							<select id="moc" name="moc" class="form-control" multiple="multiple">
										</select>
						</div>
						<input type="hidden" id="oldMoc" value=""/>
						</div>

						<div class="form-group">
							<label class="control-label col-md-4" for="uom">L1-CUST
								CHAIN (P)</label>
							<div class="col-md-8">
								<form:select class="form-control" id="customerChainL1"
									multiple="multiple" path="customerChainL1" disabled="disabled">
									<c:forEach var="custCh" items="${customerChainL1}">
									 <option value="${custCh}">${custCh}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4" for="uom">L2-CUST
								CHAIN (s)</label>
							<div class="col-md-8 switch-dynamic">
								<form:input type="text" class="form-control" id="cust-chain"
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
													<td><form:input type="text" class="form-control"
															path="category1" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="brand1" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="basepackDesc1" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="ratio1" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>2</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="basepack2"
															path="basepack2" /></td>
													<td><form:input type="text" class="form-control"
															path="category2" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="brand2" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="basepackDesc2" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="ratio2" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>3</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="basepack3"
															path="basepack3" /></td>
													<td><form:input type="text" class="form-control"
															path="category3" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="brand3" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="basepackDesc3" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="ratio3" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>4</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="basepack4"
															path="basepack4" /></td>
													<td><form:input type="text" class="form-control"
															path="category4" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="brand4" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="basepackDesc4" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="ratio4" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>5</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="basepack5"
															path="basepack5" /></td>
													<td><form:input type="text" class="form-control"
															path="category5" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="brand5" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="basepackDesc5" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="ratio5" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>6</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="basepack6"
															path="basepack6" /></td>
													<td><form:input type="text" class="form-control"
															path="category6" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="brand6" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="basepackDesc6" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
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
															class="form-control basepack-parent" id="childBasepack1"
															path="childBasepack1" /></td>
													<td><form:input type="text" class="form-control"
															path="childCategory1" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childBrand1" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childBasepackDesc1" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childRatio1" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>2</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="childBasepack2"
															path="childBasepack2" /></td>
													<td><form:input type="text" class="form-control"
															path="childCategory2" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childBrand2" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childBasepackDesc2" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childRatio2" /></td>
												</tr>

												<tr>
													<td class="serial-no"><div>3</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="childBasepack3"
															path="childBasepack3" /></td>
													<td><form:input type="text" class="form-control"
															path="childCategory3" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childBrand3" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childBasepackDesc3" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childRatio3" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>4</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="childBasepack4"
															path="childBasepack4" /></td>
													<td><form:input type="text" class="form-control"
															path="childCategory4" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childBrand4" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childBasepackDesc4" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childRatio4" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>5</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="childBasepack5"
															path="childBasepack5" /></td>
													<td><form:input type="text" class="form-control"
															path="childCategory5" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childBrand5" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childBasepackDesc5" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childRatio5" /></td>
												</tr>
												<tr>
													<td class="serial-no"><div>6</div></td>
													<td><form:input type="text"
															class="form-control basepack-parent" id="childBasepack6"
															path="childBasepack6" /></td>
													<td><form:input type="text" class="form-control"
															path="childCategory6" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childBrand6" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
															path="childBasepackDesc6" readonly="true" /></td>
													<td><form:input type="text" class="form-control"
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
				<form:textarea class="form-control" rows="3"
					placeholder="PROMO DESCRIPTION HERE..." path="promoDesc"
					id="promoDesc"></form:textarea>
				<div class="promo-create-btn">
					<input class="btn btn-primary" type="button" id="" value="UPDATE PROMO" onClick="javascript: validateForm();"></input>
					<!-- <button class="btn btn-primary" id="">UPDATE PROMO</button> -->
				</div>
			</form:form>
		</div>
		<div class="clearfix"></div>
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
<form id="pwdform" method="post" action="" class="form-horizontal" style="padding: 10px 0;">
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
								<label for="tme_oldPassword" class="col-sm-5 control-label">Changes Made</label>
								<div class="col-sm-6">
								<select class="form-control" id="changesMade"
									multiple="multiple" name="changesMade">
									<c:forEach var="custCh" items="${changesMadeList}">
									 <option value="${custCh}">${custCh}</option>
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
			<button type="submit" id="SaveDetails"
				class="btn btn-primary  resetBtn  marginR10" onClick="javascript: submitForm();">SAVE</button>
				
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
	<script type="text/javascript" src="assets/js/custom/proco/promoEdit.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap-multiselect.js"></script>
	<script type="text/javascript" src="assets/js/custom/proco/alert-modal.js"></script>
	<script type="text/javascript">
		var geographyData = '${geographyJson}';
		var moc = '${mocJson}';

		var bool;
		function getMocMonth(selectedDate){
			var d1=$('#startdate').val();
			var d2=selectedDate;
			$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "https://vat.hulcd.com/VisibilityAssetTracker/getMocMonthForProco.htm?startDate=" + $('#startdate').val()+"&endDate="+$('#enddate').val(),
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

			var oldMoc=$('#moc').val();
			$('#oldMoc').val(oldMoc);

			var oldMoc = '${CreatePromotionBean.moc}';
			var moc = oldMoc.split(',');
			
			for(var i=0;i<oldMoc.length;i++){
				$('#moc').multiselect('select',moc[i]);
			}

			$('.tme-datepicker').datepicker(
                    {
                           showOn : "both",
                           dateFormat : "dd/mm/yy",
                           minDate : 0,
                           maxDate: "+3m",
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
                            maxDate:"+6M",
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
                                          //$('#createPromo').prop('disabled', true);
                                   } else {
                                          //$('#moc-msg').hide();
                                         // $('#createPromo').prop('disabled', false);
                                   }
                            }
                     });
			
			
			var chainL1 = '${CreatePromotionBean.customerChainL1}';
			var arrChainL1 = chainL1.split(',');
			for(var i=0;i<arrChainL1.length;i++){
			$('#customerChainL1').multiselect('select',arrChainL1[i]);
			}
			
			if (arrChainL1.length > 0) {
				
				$('.switch-dynamic')
						.html(
								'<select class="form-control" name="cust-chain" id="cust-chain" multiple="multiple"><option values="ALL CUSTOMERS">ALL CUSTOMERS</option>');

				getCustChainValues(arrChainL1.toString());

			} else {
				$('.switch-dynamic')
						.html(
								'<input type="text" name="cust-chain" class="form-control" id="cust-chain" value="ALL CUSTOMERS" readonly="true">');
			}
			var chainL2 = '${CreatePromotionBean.customerChainL2}';
			var arrChainL2 = chainL2.split(',');
			for(var i=0;i<arrChainL2.length;i++){
			$('#cust-chain').multiselect('select',arrChainL2[i]);
			}
			
		});

		
		
	</script>
</body>
</html>