<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <title>MT PLANNING / Launch Plan</title>
      <script src="assets/js/jquery-1.11.3.min.js"></script>
		<%--  <link rel="stylesheet" type="text/css" href="assets/css/launchplancss/launchstyle.css/> --%>
    </head>

    <body>
      <header class="user-header-bg">
    <div class="row">
      <div class="common-header">
        <div class="col-xs-12 col-sm-6 col-md-6">
          <span>
		<img src="assets/images/proco/Unilever-logo.png" class="pull-left launch-logo" alt="logo">
		</span>
          <span>
		<h2 class="header-title">UNILEVER - Launch Plan</h2>
		</span>
        </div>
        <div class="col-md-6 col-sm-6 col-xs-12"> <!-- The drop down menu -->
          <ul class="nav pull-right user-name">
				<li class="dropdown"><a class="dropdown-toggle " href="#"
					data-toggle="dropdown"> <span class=""><img
							src="assets/images/profile.png" width="25" alt="profile-img"></span>
						<span class="profile-name">WELCOME <c:out
								value="${username}"></c:out></span> <strong class="caret"></strong></a>
					<div class="dropdown-menu user-info">
						<ul>
							<li><a href="http://34.102.191.145/VisibilityAssetTracker/tmeEditPassword.htm" data-toggle="modal" class="modelClick"
								data-target="#change_pswd">CHANGE PASSWORD</a></li>
							<li><a href="http://34.102.191.145/VisibilityAssetTracker/logoutForm.htm" class="modelClick">LOGOUT</a></li>
						</ul>
					</div>
				</li>
			</ul>
               <div class="gotoaunch">
			         <c:if test="${roleId eq 'TME'}">
			           <div class="user-name">
			                    <a href="http://34.102.191.145/VisibilityAssetTracker/goToHome.htm"><i class="fa fa-home" aria-hidden="true"></i></a>
			                </div>
			          </c:if> 
			          <c:if test="${roleId eq 'COE'}">
			           <div class="user-name">
			                    <a href="http://34.102.191.145/VisibilityAssetTracker/goToHome.htm"><i class="fa fa-home" aria-hidden="true"></i></a>
			                </div>
			          </c:if> 
			           <c:if test="${roleId eq 'KAM'}">
			           <div class="user-name">
			                    <a href="http://34.102.191.145/VisibilityAssetTracker/goToHome.htm"><i class="fa fa-home" aria-hidden="true"></i></a>
			                </div>
			          </c:if> 
			           <c:if test="${roleId eq 'DP'}">
			           <div class="user-name">
			                    <a href="http://34.102.191.145/VisibilityAssetTracker/procoHome.htm"><i class="fa fa-home" aria-hidden="true"></i></a>
			                </div>
			          </c:if> 
			           <c:if test="${roleId eq 'SC'}">
			           <div class="user-name">
			                    <a href="http://34.102.191.145/VisibilityAssetTracker/goToHome.htm"><i class="fa fa-home" aria-hidden="true"></i></a>
			                </div>
			          </c:if> 
          </div>

        </div>
        <div class="clearfix"></div>    
      </div>
    </div>
  </header>
      <div id="change_pswd" class="modal fade" role="dialog">
        <div class="modal-dialog">
          <div class="modal-content"></div>
        </div>
      </div>
     
    </body>
    <script type="text/javascript">

    $(".modelClick").on('click', function(e) {

        if (e.which == 2) {

                e.preventDefault();

        }

});

$('.modelClick').on("contextmenu", function(e) {

        return false;

});


	</script>
</html>