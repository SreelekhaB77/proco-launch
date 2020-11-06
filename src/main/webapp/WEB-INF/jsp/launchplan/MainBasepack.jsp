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
<link rel="stylesheet" href="assets/css/launchstyle.css" type="text/css">
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
			<img src="assets/images/proco/Unilever-logo.png" class=""launch-logo
				style="height: 75px; padding-top: 5px;"logo">
		</div>
		<div class="brand-name SEGOEUIL-font">UNILEVER - Launch Plan</div>
		</header>
		<div class="body-content" style="margin-top: 80px; display: none;">
			<div class="row">
				<div class="main-success-msg SEGOEUIL-font col-md-7 col-md-offset-3">Logged
					in successfully in your account</div>
			</div>
		</div>
		 <div class="tracker-details" style="margin-top: 10%;">
		
		<div class="row">
		  <div class="col-sm-2 launchHome">
		    <div class="card">
		      <div class="card-body">
		        <h5 class="card-title">Launch Details</h5>
		       
		        <a href="#" class="btn btn-info">Go to step 1</a>
		      </div>
		    </div>
		  </div>
		  <div class="col-sm-2 launchHome">
		    <div class="card">
		      <div class="card-body">
		        <h5 class="card-title">Basepacks</h5>
		       
		        <a href="#" class="btn btn-info">Go to step 2</a>
		      </div>
		    </div>
		  </div>
		  <div class="col-sm-2 launchHome">
		    <div class="card">
		      <div class="card-body">
		        <h5 class="card-title">Launch Stores</h5>
		        
		        <a href="#" class="btn btn-info">Go to step 3</a>
		      </div>
		    </div>
		  </div>
		  <div class="col-sm-2 launchHome">
		    <div class="card">
		      <div class="card-body">
		        <h5 class="card-title">Sell In</h5>
		        
		        <a href="#" class="btn btn-info">Go to step 4</a>
		      </div>
		    </div>
		  </div>
		</div>
		<div class="row">
		  
		  <div class="col-sm-2 launchHome">
		    <div class="card">
		      <div class="card-body">
		        <h5 class="card-title">Vissi Plan</h5>
		       
		        <a href="#" class="btn btn-info">Go to step 5</a>
		      </div>
		    </div>
		  </div>
		  <div class="col-sm-2 launchHome">
		    <div class="card">
		      <div class="card-body">
		        <h5 class="card-title">Final Build Up</h5>
		        
		        <a href="#" class="btn btn-info">Go to step 6</a>
		      </div>
		    </div>
		  </div>
		  <div class="col-sm-2 launchHome">
		    <div class="card">
		      <div class="card-body">
		        <h5 class="card-title">Submit</h5>
		        
		        <a href="#" class="btn btn-info">Go to step 7</a>
		      </div>
		    </div>
		  </div>
		</div>
	</div>
</div>
	<%@include file="../launchplan/footer.jsp"%>
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