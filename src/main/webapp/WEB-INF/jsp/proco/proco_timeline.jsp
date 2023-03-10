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
<link rel="stylesheet" type="text/css"	href="assets/css/bootstrap-multiselect.css">
 <link rel="stylesheet" type="text/css" href="assets/css/style.css">

<link rel="stylesheet" type="text/css"	href="assets/css/custom-font.css">
<link rel="stylesheet" type="text/css" 	href="assets/css/fileinput.css" />

<link rel="stylesheet" type="text/css"	href="assets/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="assets/css/font-awesome.css">


<jsp:include page="../proco/proco-footer.jsp" />
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
		<script type="text/javascript" src="assets/js/fileinput.js"></script>
		
	
		
<style>
.timeline a{
line-height: 2.9!important;
    font-size: 1.5em!important;
}
.border-div{
	padding: 3.35em .625em .30em .75em !important;
    margin: 30px 30px 15px 30px !important;
    border: 2px solid #009ad4 !important;
}
.alert-box{
position: relative; 
z-index: 1; 
margin:15px;
}
.success-top{
     position: absolute; 
    top: 10px;
    left: 2%;
    display: block;
    margin: auto;
    width: 96%;
    border: 2px solid #6db06d;
    background-image: linear-gradient(to bottom,#dff0d8 0,#c8e5bc 100%);
    background-repeat: repeat-x;
    color: #3c763d;
    font-weight: 500;
    font-size: 15px;
}
.middle-sections {
	margin-top: 105px;
	margin-bottom: 42px;
	padding-bottom:60px;
}
@media only screen and (min-width: 320px) and (max-width:480px) {
.cust-mr{
	margin-bottom: 20px!important;
	}
	.promo_row {
    margin: 0px auto;
}
.border-div{
margin: 10px 10px 10px 10px !important;
}
.success-top{
top:2px;
}

@media only screen and (max-width:767px) {
.cust-mr{
	margin-bottom: 20px!important;
	}
	.promo_row {
    margin: 0px auto;
}
.border-div{
margin: 10px 10px 10px 10px !important;
}
.success-top{
top:2px;
}

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
		<div class="navbar-header" style="padding-bottom: 10px;">
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
		<div id="navbar" class="navbar-collapse collapse" style="margin-bottom:-40px;">
				<div class="container-fluid container-bg">

					<div class="row">
						<ul class="nav nav-pills nav-pills-new">
					
					<li role="presentation" class="col-md-3 col-sm-6 col-xs-12 proco-listing-inactive"style=" margin-right: -65px;">
						<a href="https://vat.hulcd.com/VisibilityAssetTracker/promoStatusTracker.htm" style="margin-left:-15px;">
								<div class="proco-listing-icon"></div>
								<div class="tab-label-proco-status-inactive OpenSans-font">Promo Status Tracker</div>
						</a></li>
						
						<li role="presentation" class="col-md-3 col-sm-6 col-xs-12 listing"style=" margin-top: 10px; margin-right:-50px;">
							<a href="https://vat.hulcd.com/VisibilityAssetTracker/promoDeletion.htm" style="width:234px;">
								<div class="proco-deletion-icon"></div>
								<div class="tab-label-proco-del-inactive OpenSans-font">Dropped Offer</div>
						</a></li>
						
						<li role="presentation"	class="col-md-3 col-sm-6 col-xs-12 promo-lib-bg" style=" margin-top: 10px; width:234px; margin-right:15px; ">
							<a href="https://vat.hulcd.com/VisibilityAssetTracker/ProcoMeasureReportUploadPage.htm" >
								<div class="proco-Signops-icon"></div>
								<div class="tab-label-proco-Signops-inactive OpenSans-font">Upload Measure Report</div>
							</a>
						</li>
						<li role="presentation"	class="col-md-3 col-sm-6 col-xs-12 promo-ppm-inactive" style="margin-top: 10px;width:17%; margin-right:15px;">
							<a href="https://vat.hulcd.com/VisibilityAssetTracker/ProcoPpmCoeRemarks.htm">
								<div class="proco-ppm-icon"></div>
								<div class="tab-label-proco-ppm-inactive OpenSans-font">PPM Upload</div>
							</a>
						</li>
						<li role="presentation"	class="col-md-3 col-sm-6 col-xs-12 timeline-active" style="width:19%;  margin-top: 10px">
							<a href="https://vat.hulcd.com/VisibilityAssetTracker/procoTimeline.htm" style="width: 220px;">
								<div class="proco-timeline-icon "></div>
								<div class="tab-label-proco-timeline-active OpenSans-font">Promo Timeline</div>
							</a>
						</li> 
			
					</ul>
					</div>
				</div>
			</div>
		</div>
	</nav>
	
	
	<div class="container-fluid container-bg middle-sections">
		<c:if test="${success!=null}"> 
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${success}"></c:out>
			</div>
		</c:if>
		<c:if test="${errorMsg!=null}">

			<div class="alert alert-danger sucess-msg" id="errorblock">
				<button type="button" class="close" data-hide="alert">&times;</button>
				<c:out value="${errorMsg}"></c:out>
			</div>
		</c:if>
		
		
	<div class="tab-content">
			<div class="tab-pane active">

				<div class="margin-t10">
					<ul class="nav nav-tabs nav-bgcolor  tme-tabs">
						<li class="active col-xs-12 col-md-3" style="padding: 0px;"><a
							href="#current_summ" role="tab" data-toggle="tab">COE PROMO TIMELINE</a></li>
						

					</ul>
				</div>
				<div style="background-color:#fff;" class="tab-content">
		<div class="tab-pane active" id="current_summ">
			<div class="tmeForm">
			
			<div class="alert-box">
			<div id="tmekam_success_alert" class="success-top alert" style="display:none" >
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>LOCK PROMO TIMELINE !</strong> Added Successfully.
            </div>
				
            <div id="success_alert" class="success-top alert" style="display:none" >
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>USER LOCK !</strong> Added Successfully.
            </div>
            </div>
            
			<form id="createpromoTimeline" action=""
					method="post" class="form-horizontal" style="padding: 10px 0; margin-top:0px;"
					onsubmit="return modifyValidation()">
		
					<div style="margin-top:45px;" class="row promo_row">
						<div class="col-md-12">
						<div class="border-div">
                        <div class="form-group">
								<label for="tme_Hht"
									class="col-sm-offset-1 col-sm-2 control-label  OpenSans-font" style="margin-top:25px;"><b>Lock Promo Timeline </b><span
									class="required" style="color: red">*</span></label>
									
								<div class="col-sm-3 cust-mr">

										<label for="tme_Hht" class="col-sm-7 control-label  OpenSans-font">Select TME Lock<span
											class="required" style="color: red">*</span></label>
										<select class="form-control" name="tme_lock"
											id="tme_lock">
											<!--<option value="Select a Category">Select</option>-->
											<option value="Y">Yes</option>
											<option value="N">No</option>
										</select>
								
									
								</div>
								
								<div class="col-sm-3 cust-mr">

										<label for="tme_Hht" class="col-sm-7 control-label  OpenSans-font">Select KAM Lock<span
											class="required" style="color: red">*</span></label>
										<select class="form-control" name="kam_lock"
											id="kam_lock">
											<!--<option value="Select a Category">Select</option>-->
											<option value="Y">Yes</option>
											<option value="N">No</option>
										</select>
								
									<span id='role-msg' class='cust-error small'>Please
										select correct value</span>
								</div>
								
							
							</div>
						
							 <div class="form-group">
								<label for="tme_Hht"
									class="col-sm-offset-1 col-sm-2 control-label  OpenSans-font"><b>Lock Days</b> </label>
								<div class="col-sm-3 cust-mr">
										
										<select class="form-control" name="tme_lock_days" id="tme_lock_days">
											<c:forEach items="${tmeLockDays}" var="data">
												<option value="${data}"><c:out value="${data}"></c:out></option>
											</c:forEach>
										</select>
									
								</div>
								<div class="col-sm-3 cust-mr">

										<select class="form-control" name="kam_lock_days" id="kam_lock_days">
											<c:forEach items="${kamLockDays}" var="data">
					                       		<option value="${data}"><c:out value="${data}"></c:out></option>
			                             	</c:forEach>
										</select>
									
								</div>
									<div class="col-sm-2">
							
							<div class="tmeButtons" style="text-align: center;margin-top: -10px;">
								<button type="button" id="btnPromoLock" class="btn btn-primary">Submit</button>
								
								</div>
							</div>
								</div>

							</div>
							
						</div>
						<input type="hidden" id="tmelock" name="tmelock" value="${globalLocks.TMELock}" />
						<input type="hidden" id="kamlock" name="kamlock" value="${globalLocks.KAMLock}" />
						<input type="hidden" id="tmelockday" name="tmelockday" value="${globalLocks.TMELockDay}" />
						<input type="hidden" id="kamlockday" name="kamlockday" value="${globalLocks.KAMLockDay}" />
						
					</div>
							
				</form>
				<form id="createpromoTimelineuser" action=""
					method="post" class="form-horizontal" style="padding: 10px 0; margin-top:0px;"
					onsubmit="return modifyValidation()">
					
						
							<div class="row promo_row">
						<div class="col-md-12">	
							<div class="border-div">
                        <div class="form-group">
						

									<label for="tme_Hht"
									class="col-sm-offset-1 col-sm-2 control-label  OpenSans-font" style="margin-top:25px;"><b>User Lock</b><span
									class="required" style="color: red">*</span></label>
									
									<div class="col-sm-3 cust-mr">
									<label for="tme_Hht" class="col-sm-7 control-label  OpenSans-font">Users List<span
											class="required" style="color: red">*</span></label>
											
			            
		            <select id="userlist" class="form-control">
		            <option value="Select a Category">Select</option>
		 	        	<c:forEach items="${userNames}" var="data">
						<option value="${data.userId}">
							<c:out value="${data.userId}"></c:out>
							<!-- <c:out value="${data.firstName}"></c:out> -->
						</option>
			        	</c:forEach>
		            </select>
								</div>
								
								<div class="col-sm-3 cust-mr">

									<label for="tme_Hht" class="col-sm-7 control-label  OpenSans-font">Select User Lock<span
											class="required" style="color: red">*</span></label>
											
									<select class="form-control" name="user_lock" id="user_lock">
											<option value="Select a Category">Select</option>
											<option value="Y">Yes</option>
											<option value="N">No</option>
										</select>
								
									
								</div>
								
								<div class="col-sm-2">
							
							<div class="tmeButtons"  style="text-align: center;margin: 15px;">
								<button type="button" id="btnUserLock" class="btn btn-primary">Submit</button>
								
								</div>
							</div>
							</div>
						
							</div>
							
							</div>
							</div>
							
				</form>
		</div>
		</div>
	</div>
	</div>
	</div>	
		<!-- Modal Dialog -->
	<div class="modal fade" id="confirmDelete" role="dialog"
		aria-labelledby="confirmDeleteLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Delete User</h4>
				</div>
				<div class="modal-body">
				<div class="alert alert-danger OpenSans-font">
					<span class="glyphicon glyphicon-warning-sign"></span> Are you sure you want to delete this User?
				</div>
			</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" id="confirm">DELETE</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
				</div>
			</div>
		</div>
	</div>
	<!-- end modal -->
		
	
	<script>
		$(document).ready(function() {
			
			$('#tme_lock').val($('#tmelock').val());
			$('#kam_lock').val($('#kamlock').val());
			$('#tme_lock_days').val($('#tmelockday').val());
			$('#kam_lock_days').val($('#kamlockday').val());
			
			
            
			
			$('#btnPromoLock').click(function(e){
				console.log("hi");
				
			   // alert(tmeLock.val());
				e.preventDefault();
				$.ajax({
					type: "POST",
					url: "http://34.120.128.205/VisibilityAssetTracker/coeUpdateProcoTimeline.htm",
					data: promolockJson(),
					contentType: "application/json; charset=utf-8",
		            dataType: "json",
		            success: function(data) {
					// alert('Updated Successfully');
					 $("#tmekam_success_alert").show();
						 setTimeout(function() { $("#tmekam_success_alert").hide(); }, 8000);
		            },
		            error: function(jqXHR, textStatus, errorThrown) {
		               // alert('Error: ' + textStatus);
		            }
				});
				console.log(promolockJson());
			});
			
			function promolockJson() {
				return JSON.stringify({
					"tmeLock": $('#tme_lock').val(),
					"kamLock": $('#kam_lock').val(),
					"tmeLockDays": $('#tme_lock_days').val(),
					"kamLockDays": $('#kam_lock_days').val()
				});
			}
			
			$('#btnUserLock').click(function(e){
				
			var userLock = $('#user_lock');
			var userList = $('#userlist');
			
				e.preventDefault();
				$.ajax({
					type: "POST",
					url: "http://34.120.128.205/VisibilityAssetTracker/coeUpdateProcoTimeline.htm",
					data: userlockJson(),
					contentType: "application/json; charset=utf-8",
		            dataType: "json",
		            success: function(data) {
		            
		            if(userList.val() == 'Select a Category'){
                    alert("Please select an User List.");
                         $('#userlist').focus();

                         return false;
                    } else if (userLock.val() == 'Select a Category' ) {
                         alert("Please select an User Lock.");
                         $('#user_lock').focus();

                         return false;
						 
                    }  else{
                    
						//alert('Updated Successfully');
						$("#success_alert").show();
						 setTimeout(function() { $("#success_alert").hide(); }, 8000);
						
					}
		            },
		            error: function(jqXHR, textStatus, errorThrown) {
		               // alert('Error: ' + textStatus);
		            }
				});
				
				console.log(userlockJson());
			});
			
			function userlockJson() {
				return JSON.stringify({
					"userId": $('#userlist').val(),
					"userLock": $('#user_lock').val()
				});
			}
					 
		});
	</script>
	
	</body>
		</html>		