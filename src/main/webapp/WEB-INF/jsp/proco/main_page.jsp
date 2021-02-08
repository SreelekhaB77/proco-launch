<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>MT PLANNING</title>
<%
	String errorMsg = (String) request.getAttribute("errorMsg");
	String successMsg = (String) request.getAttribute("success");
%>
<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/bootstrap-theme.min.css">

<link rel="stylesheet" type="text/css" href="assets/css/style.css">
<link rel="stylesheet" type="text/css" href="assets/css/custom-font.css">

<script type="text/javascript" src="assets/js/jquery-1.11.3.min.js"></script>

<script type="text/javascript">
		history.pushState(null, null, '');
		window.addEventListener('popstate', function(event) {
			history.pushState(null, null, '');
		});
	</script>
</head>
<body style="background-color: #ddd;" class="OpenSans-font">
	<div class="container-fluid">
		<header style="position: fixed;    z-index: 999999;">
		<div class="pull-left login-header">
			<img src="assets/images/logo.png"	style="height: 75px; padding-top: 5px;"logo">
		</div>
		<div class="brand-name SEGOEUIL-font">MT PLANNING</div>
		</header>
		<div class="body-content" style="margin-top: 80px; display: none;">
			<div class="row">
				<div class="main-success-msg SEGOEUIL-font col-md-7 col-md-offset-3">Logged
					in successfully in your account</div>
			</div>
		</div>
		<div class="tracker-details" style="margin-top: 315px;">
			<div class="row">
				
				<div class="col-md-2 col-sm-9 col-sm-offset-2 col-md-offset-3 loginL-right">
					<c:if test="${roleId eq 'TME'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/dashboard.htm">
							<div class="main-login-box">
								<div class="mt-vat">Visibility Asset</div>
								<div>Tracker</div>
							</div>
						</a>
					</c:if>
					<c:if test="${roleId eq 'COE'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/coeDashboard.htm">
							<div class="main-login-box">
								<div class="mt-vat">Visibility Asset</div>
								<div>Tracker</div>
							</div>
						</a>
					</c:if>
					<c:if test="${roleId eq 'KAM'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/kamDashboard.htm">
							<div class="main-login-box">
								<div class="mt-vat">Visibility Asset</div>
								<div>Tracker</div>
							</div>
						</a>
					</c:if>
					
					<c:if test="${roleId eq 'NCMM'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/finDashboard.htm">
							<div class="main-login-box">
								<div class="mt-vat">Visibility Asset</div>
								<div>Tracker</div>
							</div>
						</a>
					</c:if>
				</div>
				<div class="col-md-2 col-sm-9 col-sm-offset-2 col-md-offset-0">
					<c:if test="${roleId eq 'COE'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/promoStatusTracker.htm">
						<div class="proco-login-box">
							<div class="mt-vat">MT PROCO Tool</div>
						</div>
					</a>
					</c:if>
					
					<c:if test="${roleId eq 'TME'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/procoHome.htm">
						<div class="proco-login-box">
							<div class="mt-vat">MT PROCO Tool</div>
						</div>
					</a>
					</c:if>
					
					<c:if test="${roleId eq 'KAM'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/procoHome.htm">
						<div class="proco-login-box">
							<div class="mt-vat">MT PROCO Tool</div>
						</div>
					</a>
					</c:if>
					
					<c:if test="${roleId eq 'NCMM'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/procoHome.htm">
						<div class="proco-login-box">
							<div class="mt-vat">MT PROCO Tool</div>
						</div>
					</a>
					</c:if>
					
				</div>
				<!-- new div by priyanka -->
				
				 <div class="col-md-2 col-sm-9 col-sm-offset-2 col-md-offset-0">
					<c:if test="${roleId eq 'TME'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/getLaunchPlanPage.htm">
							<div class="comp-logofor-launch">
								<div class="mt-vat">Launch Tool</div>
								
							</div>
						</a>
					</c:if>
					 <c:if test="${roleId eq 'COE'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/getAllCompletedLaunchData.htm">
							<div class="comp-logofor-launch">
								<div class="mt-vat">Launch Tool</div>
								
							</div>
						</a>
					</c:if> 
					<c:if test="${roleId eq 'KAM'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/getAllCompletedLaunchDataKam.htm">
							<div class="comp-logofor-launch">
								<div class="mt-vat">Launch Tool</div>
								
							</div>
						</a>
					</c:if> 
					<c:if test="${roleId eq 'SC'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/getAllCompletedLaunchDataSc.htm">
							<div class="comp-logofor-launch">
								<div class="mt-vat">Launch Tool</div>
								
							</div>
						</a>
					</c:if> 
				</div> 
				
			<div class="col-md-2 col-sm-9 col-sm-offset-2 col-md-offset-0">
				<c:if test="${roleId eq 'KAM'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/cpsKamHome.htm">
							<div class="main-cps-login-box">
								<div class="mt-vat">CPS System</div>
							</div>
						</a>
					</c:if>
					<c:if test="${roleId eq 'NCMM'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/cpsNcmmHome.htm">
							<div class="main-cps-login-box">
								<div class="mt-vat">CPS System</div>
							</div>
						</a>
					</c:if>
					<%-- <c:if test="${roleId eq 'MTFIN'}">
						<a href="http://localhost:8083/VisibilityAssetTracker/cpsMtFinHome.htm">
							<div class="main-cps-login-box">
								<div class="mt-vat">CPS System</div>
							</div>
						</a>
					</c:if> --%>
			</div>	
				
			</div>
		</div>
	</div>
	<jsp:include page="../header/footer.jsp" />
	<script type="text/javascript">
    $(document).ready(function() {
            var introHeight = $('.main-login-details').height();
            popupLoading(introHeight);
          });
          function popupLoading(h) {
            var top = (window.innerHeight / 2) - (h   /2);
            $('.main-login-details').css({
              "top": top,
            });
          }

  </script>
</body>
</html>