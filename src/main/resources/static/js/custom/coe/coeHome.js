$(document).ready(function() {
	$('#coe-viewModal').on('show.bs.modal', function (e) {
		var link = $(e.relatedTarget);
		   $(this).find(".coe-dash").load(link.attr("href"));
		});
	
	$('#downloadPlans').on('show.bs.modal', function(e) {
		var link = $(e.relatedTarget);
		$(this).find(".coe-dash-download").load(link.attr("href"));
	});
	
});
