$(document).ready(function() {
	$('#myModal').on('show.bs.modal', function (e) {
		var link = $(e.relatedTarget);
		   $(this).find(".tme-track-preview").load(link.attr("href"));
		});
		
	$('#coe-history').on('show.bs.modal', function(e) {
		var link = $(e.relatedTarget);
		$(this).find(".coe-view-history").load(link.attr("href"));
	});

	
	$('#kam-mapModal').on('show.bs.modal', function(e) {
		var link = $(e.relatedTarget);
		$(this).find(".kam-map-outlet").load(link.attr("href"));
	});

	$('#kam-mapModal').on('show.bs.modal', function(e) {
		var link = $(e.relatedTarget);
		$(this).find(".kam-map-out").load(link.attr("href"));
	});

	$('#kam-mapModal').on('show.bs.modal', function(e) {
		var link = $(e.relatedTarget);
		$(this).find(".kam-store-map").load(link.attr("href"));
	});
	$('#kam-viewModal').on('show.bs.modal', function(e) {
		var link = $(e.relatedTarget);
		$(this).find(".kam-view").load(link.attr("href"));
	});
	$('#kam-editModal').on('show.bs.modal', function(e) {
		var link = $(e.relatedTarget);
		$(this).find(".kam-edit-outlet").load(link.attr("href"));
	});
	
	/*$('#revertAsset').on('show.bs.modal', function(e) {
		var link = $(e.relatedTarget);
		$(this).find(".revert-plan").load(link.attr("href"));
	});
	
	/*$('#paidVisibilityAsset').on('show.bs.modal', function(e) {
		var link = $(e.relatedTarget);
		$(this).find(".paid-visi").load(link.attr("href"));
	});*/
	
	$('#kam-mapModal').on('show.bs.modal', function(e) {
		var link = $(e.relatedTarget);
		$(this).find(".kam-map-alert").load(link.attr("href"));
	});

	$(".modelClick").on('click', function(e) {
		if (e.which == 2) {
			e.preventDefault();
		}
	});

	$('.modelClick').on("contextmenu", function(e) {
		return false;
	});
	
	
	$("#mapOutlet").on('click', function(e) {
		if (e.which == 2) {
			e.preventDefault();
		}
	});
	$('#mapOutlet').on("contextmenu", function(e) {
        return false;
	});

	$('#mapOutletEdit').on("contextmenu", function(e) {
		return false;
	});
	
	$("#mapOutletEdit").on('click', function(e) {
        if (e.which == 2) {
                e.preventDefault();
        }
	});
});


   		
