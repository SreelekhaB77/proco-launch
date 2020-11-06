$(document).ready(function() {
	$('#coe-viewModal').on('show.bs.modal', function (e) {
		var link = $(e.relatedTarget);
		   $(this).find(".coe-track").load(link.attr("href"));
		});
	
});
