<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
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
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css" href="assets/css/custom-font.css">
<link rel="stylesheet" type="text/css" href="assets/css/style.css">
<script type="text/javascript">
		history.pushState(null, null, '');
		window.addEventListener('popstate', function(event) {
			history.pushState(null, null, '');
		});
	</script>
</head>
<body style="background-color: #ddd;" class="OpenSans-font">
	<div class="container-fluid">
		<header>
			<div class="pull-left login-header">
				<img src="assets/images/logo.png" alt="logo">
			</div>
			<div class="brand-name SEGOEUIL-font">MT PLANNING</div>
		</header>
		<div class="body-content">
			<div class="row">
				<div class="col-md-12"></div>
			</div>
			<div class="login-details">
				<div class="row">

					<div
						class="col-md-4 col-sm-9 col-sm-offset-2 col-md-offset-4 error-box">

						<div class="row">

							<div class="error-header">Error Occurred</div>
							<div class="error-content">The server encountered a
								temporary error or Your Session has timed out Please Login again !!!</div>
							<div class="error-footer  text-center">

								<a class="btn marginR10 btn-info btn-sm" href="index.jsp">HOME</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	</div>
	<footer>
	<div class="pull-right unilever-copyright">
		&copy; Unilever
		<script type="text/javascript">
			document.write(new Date().getFullYear());
		</script>
	</div>
	</footer>
</body>
</html>
