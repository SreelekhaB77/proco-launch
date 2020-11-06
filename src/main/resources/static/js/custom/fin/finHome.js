$(document).ready(function() {
	$('#fin-viewModal').on('show.bs.modal', function (e) {
		var link = $(e.relatedTarget);
		   $(this).find(".fin-dash").load(link.attr("href"));
		});
	
});

/*$(document).ready(
		function() {
$('#moc_track_table').find("tr[id='moc_track_tr']").find(
					"td[id='moc_track_td']")
					.each(
							function() {
								var dateVal = $(this).html();
								var monthNames = [ "Jan", "Feb", "Mar", "Apr",
										"May", "Jun", "Jul", "Aug", "Sep",
										"Oct", "Nov", "Dec" ];
								var date = new Date(dateVal);
								var day = date.getDate();
								var monthIndex = date.getMonth();
								var year = date.getFullYear();
								$(this).html(day + "-" + monthNames[day - 1])
							});

		});*/