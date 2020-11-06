$(document).ready(function() {
	$('#kamtrackmodal').on('show.bs.modal', function (e) {
		var link = $(e.relatedTarget);
		   $(this).find(".kam-track-preview").load(link.attr("href"));
		});
	
});
