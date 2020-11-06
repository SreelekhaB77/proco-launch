$(document).ready(function() {
	$('#msg-success').hide();
	$('#msg-error').hide();
}

);

$('#add-depot').on('hidden.bs.modal', function() {
	$('#msg-success').hide();
	$('#msg-error').hide();
	$('#cluster').empty();
	$("#cluster").prop('disabled', 'disabled');
	$('#depot').empty();
	$("#depot").prop('disabled', 'disabled');
	$('#quantity').val("");
});

function validateForm() {
	$('#msg-success').hide();
	$('#msg-error').hide();
	var promoId = "";
	var oTable = $('#disTable').dataTable();
	var rowcollection = oTable.$(".visiData:checked", {
		"page" : "all"
	});
	rowcollection.each(function(index, elem) {
		var checkbox_value = $(elem).val();
		promoId = checkbox_value;
		// Do something with 'checkbox_value'
	});
	var branch = $("#branch").val();
	var clusterVal = $("#cluster").val();
	var depotVal = $("#depot").val();
	var quantity = $("#quantity").val();
	// var quantity = document.getElementById("quantity").value

	if (branch == 'SELECT') {
		$('#msg-error').html('Please select Branch.');
		$('#msg-error').show();
		return false;
	} else if (clusterVal == "" || clusterVal == 'SELECT') {
		$('#msg-error').html('Please select Cluster.');
		$('#msg-error').show();
		return false;
	} else if (depotVal == "" || depotVal == 'SELECT') {
		$('#msg-error').html('Please select depot.');
		$('#msg-error').show();
		return false;
	} else if (quantity == "") {
		$('#msg-success').hide();
		$('#msg-error').html('Please enter quantity.');
		$('#msg-error').show();
		return false;
	} else if (isNaN(quantity)) {
		$('#msg-success').hide();
		$('#msg-error').html('Quantity should be number.');
		$('#msg-error').show();
		return false;
	}

	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		cache : false,
		url : "saveDepotForAddDepot.htm?promoId=" + promoId + "&branch="
				+ branch + "&quantity=" + quantity + "&depot=" + depotVal+"&cluster=" + clusterVal,
		success : function(data) {
			var res = $.parseJSON(data);
			if (res == "SUCCESS") {
				$('#msg-success').html('Depot added successfully.');
				$('#msg-success').show();
			} else {
				$('#msg-error').html('Failed to add depot.');
				$('#msg-error').show();
			}
			//console.log(res);
		},
		error : function(error) {
			console.log(error)
		}
	});
	return true;
}

function getBranch() {
	$('#msg-success').hide();
	$('#msg-error').hide();
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		cache : false,
		url : "getBranchForAddDepot.htm",
		success : function(data) {
			var branch = $.parseJSON(data);
			console.log(branch);
			$('#branch').empty();
			$('#branch').append("<option value=SELECT>SELECT</option>");
			$.each(branch,
					function(key, value) {
						$('#branch').append(
								"<option value='" + value + "'>" + value
										+ "</option>");
					});
		},
		error : function(error) {
			console.log(error)
		}
	});
}

function getCluster() {
	$('#msg-success').hide();
	$('#msg-error').hide();
	var branch = $("#branch").val();
	if (branch == "SELECT") {
		$('#msg-success').hide();
		$('#msg-error').html('Please select Branch.');
		$('#msg-error').show();
		$('#cluster').empty();
		$("#cluster").prop('disabled', 'disabled');
		$('#depot').empty();
		$("#depot").prop('disabled', 'disabled');
		return false;
	} else {
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			cache : false,
			url : "getClusterForAddDepot.htm?branch=" + branch,
			success : function(data) {
				var cluster = $.parseJSON(data);
				console.log(cluster);
				$('#cluster').empty();
				$('#cluster').append("<option value=SELECT>SELECT</option>");
				$.each(cluster, function(key, value) {
					$('#cluster').append(
							"<option value='" + value + "'>" + value
									+ "</option>");
				});
				$("#cluster").prop('disabled', false);
			},
			error : function(error) {
				console.log(error)
			}
		});
	}
}

function getDepot() {
	$('#msg-success').hide();
	$('#msg-error').hide();
	var promoId = "";
	var oTable = $('#disTable').dataTable();
	var rowcollection = oTable.$(".visiData:checked", {
		"page" : "all"
	});
	rowcollection.each(function(index, elem) {
		var checkbox_value = $(elem).val();
		promoId = checkbox_value;
		// Do something with 'checkbox_value'
	});
	var branch = $("#branch").val();
	var cluster = $("#cluster").val();
	if (branch != "SELECT") {
		if (cluster != "SELECT") {
			$.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				cache : false,
				url : "getDepotForAddDepot.htm?promoId=" + promoId + "&branch="
						+ branch + "&cluster=" + cluster,
				success : function(data) {
					var depot = $.parseJSON(data);
					console.log(depot);
					$('#depot').empty();
					$('#depot').append("<option value=SELECT>SELECT</option>");
					$.each(depot, function(key, value) {
						$('#depot').append(
								"<option value='" + value + "'>" + value
										+ "</option>");
					});
					$("#depot").prop('disabled', false);
				},
				error : function(error) {
					console.log(error)
				}
			});
		} else {
			$('#msg-success').hide();
			$('#msg-error').html('Please select Cluster.');
			$('#msg-error').show();
			$('#depot').empty();
			$("#depot").prop('disabled', 'disabled');
			return false;
		}
	} else {
		$('#msg-success').hide();
		$('#msg-error').html('Please select Branch.');
		$('#msg-error').show();
		$('#cluster').empty();
		$("#cluster").prop('disabled', 'disabled');
		$('#depot').empty();
		$("#depot").prop('disabled', 'disabled');
		return false;
	}

}

function onlyNos(e, t) {
	try {
		if (window.event) {
			var charCode = window.event.keyCode;
		} else if (e) {
			var charCode = e.which;
		} else {
			return true;
		}
		if (charCode > 31 && (charCode < 48 || charCode > 57)) {
			return false;
		}
		return true;
	} catch (err) {
		alert(err.Description);
	}
}
