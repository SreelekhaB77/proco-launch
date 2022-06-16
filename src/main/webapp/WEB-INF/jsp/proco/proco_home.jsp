<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<link rel="stylesheet" type="text/css" href="assets/css/proco-style.css">
<link rel="stylesheet" type="text/css"	href="assets/css/custom-font.css">



<script type="text/javascript">
		history.pushState(null, null, '');
		window.addEventListener('popstate', function(event) {
			history.pushState(null, null, '');
		});
	</script>
</head>
<body>
 
 <jsp:include page="../proco/proco-header.jsp" />  
 
  <!-- Main jumbotron for a primary marketing message or call to action -->
  <div class="container-fluid middle-section" style="margin-top:0px;">
       <div class="proco-home">
      <div class="row">
    
    		 <input type="hidden" id="roleId" value="${roleId}"/>
    <c:if test="${roleId eq 'TME'}">
    <div class="col-md-6 col-md-offset-3" style="margin-top:50px;">
          <div class="col-md-4 col-sm-4 col-xs-4">
         <a href="http://34.120.128.205/VisibilityAssetTracker/promoCreation.htm">
          <div class="promo-create promo-create-bg">
              <img src="assets/images/proco/icon-1.png"  class="img-responsive" alt="">
              <div class="promo-craete-txt promo-create-txt-bg"><div>Promo Creation</div></div>  
            </div>
             </a>             
         </div>
          <div class="col-md-4 col-sm-4 col-xs-4 ">
                <a href="http://34.120.128.205/VisibilityAssetTracker/promoListing.htm">
           <div class="promo-create promo-listing-bg">
              <img src="assets/images/proco/icon-2.png " class="img-responsive" alt="">
               <div class="promo-craete-txt promo-listing-txt-bg"><div>Promo Listing</div></div>
            </div>
                </a>
             </div>
              <div class="col-md-4 col-sm-4 col-xs-4 ">
                <a href="http://34.120.128.205/VisibilityAssetTracker/promoDeletion.htm">
		           <div class="promo-create promo-delete-bg">
		              <img src="assets/images/proco/deletepro.png" class="img-responsive" id="delimg" alt="">
		               <div class="promo-craete-txt promo-delete-txt-bg"><div>Dropped Offers</div></div>
		            </div>
                </a>
             </div>
          
          </div>
          </c:if>
 <c:if test="${roleId eq 'DP'}">
 <!--bharati changed in below div col-md-offset-1 to col-md-offset-2 in sprint-9-->
  <div class="col-md-12 col-md-offset-2">
           <div class="col-md-2 col-sm-6 col-xs-6 " >
              <a href="http://34.120.128.205/VisibilityAssetTracker/promoVolumeUpload.htm">
                 <div class="promo-create promo-volume-bg">
                    <img src="assets/images/proco/icon-6.png"  class="img-responsive" alt="">
                    <div class="promo-craete-txt promo-volume-txt-bg">
                       <div>
                          Volume Upload
                       </div>
                    </div>
                 </div>
              </a>
           </div>
         <!--bharati commented disaggregation tab in sprint-9-->
         <!--<div class="col-md-2 col-sm-6 col-xs-6 ">
         <a href="http://34.120.128.205/VisibilityAssetTracker/promoDisaggregation.htm">
           <div class="promo-create promo-disagg-bg">
              <img src="assets/images/proco/icon-3.png"  class="img-responsive" alt="">
                 <div class="promo-craete-txt promo-disagg-txt-bg"><div>Promo Disaggregation</div></div>
            </div>
              </a>
              </div>-->
        
           <div class="col-md-2 col-sm-6 col-xs-6 ">
                <a href="http://34.120.128.205/VisibilityAssetTracker/promoListing.htm">
           <div class="promo-create promo-listing-bg">
              <img src="assets/images/proco/icon-2.png " class="img-responsive" alt="">
               <div class="promo-craete-txt promo-listing-txt-bg"><div>Promo Listing</div></div>
            </div>
                </a>
             </div>
             
             <div class="col-md-2 col-sm-4 col-xs-4 ">
                <a href="http://34.120.128.205/VisibilityAssetTracker/promoDeletion.htm">
		           <div class="promo-create promo-delete-bg">
		              <img src="assets/images/proco/deletepro.png" class="img-responsive" id="delimg" alt="">
		               <div class="promo-craete-txt promo-delete-txt-bg"><div>Dropped Offer</div></div>
		            </div>
                </a>
             </div>
             
             <div class="col-md-2 col-sm-6 col-xs-6 ">
               <!--  <a href="http://34.120.128.205/VisibilityAssetTracker/getAllCompletedLaunchDataDp.htm">
           <div class="promo-create comp-logofor-launch">
              <img src="assets/images/proco/icon-2.png " class="img-responsive" alt="">
               <div class="promo-craete-txt promo-listing-txt-bg"><div>Launch Tool</div></div>
            </div>
                </a> -->
                <div class="launchDiv">
	                <a href="http://34.120.128.205/VisibilityAssetTracker/getAllCompletedLaunchDataDp.htm">
						<div class="comp-logofor-launch">
							<div class="mt-vat">Launch Tool</div>
							
						</div>
					</a>
				</div>
             </div>
 </div>
 
 </c:if>
    <c:if test="${roleId eq 'KAM'}">
    <div class="col-md-6 col-md-offset-4">
     
            <div class="col-md-4 col-sm-6 col-xs-6 ">
                 <a href="http://34.120.128.205/VisibilityAssetTracker/promoCollaboration.htm">
           <div class="promo-create promo-collab-bg">
              <img src="assets/images/proco/icon-4.png"  class="img-responsive" alt="">
               <div class="promo-craete-txt promo-collab-txt-bg"><div>KAM Collaboration</div></div>
            </div>
                </a>
              
              </div>
          	 <div class="col-md-4 col-sm-6 col-xs-6 ">
                <a href="http://34.120.128.205/VisibilityAssetTracker/promoListing.htm">
           <div class="promo-create promo-listing-bg">
              <img src="assets/images/proco/icon-2.png " class="img-responsive" alt="">
               <div class="promo-craete-txt promo-listing-txt-bg"><div>Promo Listing</div></div>
            </div>
                </a>
             </div> 
              
          </div>
        
          
          </c:if>
          
           <c:if test="${roleId eq 'NCMM'}">
    	<div class="col-md-4 col-md-offset-4">
          <div class="col-md-6 col-sm-6 col-xs-6">
         <a href="http://34.120.128.205/VisibilityAssetTracker/promoCr.htm">
          <div class="promo-create promo-create-bg">
              <img src="assets/images/proco/icon-1.png"  class="img-responsive" alt="">
              <div class="promo-craete-txt promo-create-txt-bg"><div>Promo CR</div></div>  
            </div>
             </a>
             
         </div>
          <div class="col-md-6 col-sm-6 col-xs-6 ">
                <a href="http://34.120.128.205/VisibilityAssetTracker/promoListing.htm">
           <div class="promo-create promo-listing-bg">
              <img src="assets/images/proco/icon-2.png " class="img-responsive" alt="">
               <div class="promo-craete-txt promo-listing-txt-bg"><div>Promo Listing</div></div>
            </div>
                </a>
             </div>
          
          </div>
          </c:if>
          
          <c:if test="${roleId eq 'NSCM'}">
    <div class="col-md-6 col-md-offset-2">
          <div class="col-md-4 col-sm-6 col-xs-6">
         <a href="http://34.120.128.205/VisibilityAssetTracker/promoCr.htm">
          <div class="promo-create promo-create-bg">
              <img src="assets/images/proco/icon-1.png"  class="img-responsive" alt="">
              <div class="promo-craete-txt promo-create-txt-bg"><div>Promo CR</div></div>  
            </div>
             </a>
             
         </div>
          <div class="col-md-4 col-sm-6 col-xs-6 ">
                <a href="http://34.120.128.205/VisibilityAssetTracker/promoListing.htm">
           <div class="promo-create promo-listing-bg">
              <img src="assets/images/proco/icon-2.png " class="img-responsive" alt="">
               <div class="promo-craete-txt promo-listing-txt-bg"><div>Promo Listing</div></div>
           </div>
                </a>
             </div>
          
          <div class="col-md-4 col-sm-4 col-xs-4 ">
                <a href="http://34.120.128.205/VisibilityAssetTracker/promoDeletion.htm">
		           <div class="promo-create promo-delete-bg">
		              <img src="assets/images/proco/deletepro.png" class="img-responsive" id="delimg" alt="">
		               <div class="promo-craete-txt promo-delete-txt-bg"><div>Promo Deletion</div></div>
		            </div>
                </a>
             </div>
   </div>
          </c:if>
          
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
  <script type="text/javascript">
  console.log($('#roleId').val());
    $(document).ready(function() {
            var introHeight = $('.proco-home').height();
            popupLoading(introHeight);
          });
          function popupLoading(h) {
            var top = (window.innerHeight/2)-(h/2);
            $('.proco-home').css({
              "top": top
            });
          }

  </script>

</body>
</html>