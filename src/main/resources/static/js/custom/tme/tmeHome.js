$(document).ready(function() {

	// hideColumnColorRow(7); 
	
	$('#myModal').on('show.bs.modal', function(e) {
		var link = $(e.relatedTarget);
		$(this).find(".tme-preview").load(link.attr("href"));
		$(this).find(".tme-track-preview").load(link.attr("href"));
		//tme-track-preview
	});

	$('#tme-history').on('show.bs.modal', function(e) {
		var link = $(e.relatedTarget);
		$(this).find(".tme-history-view").load(link.attr("href"));
	});

});

/*
(function ($)
		{
		    hideColumnColorRow = function (column)
		    {
		     //   $('td:nth-child(' + column + '),th:nth-child( ' + column + ')').hide();
		    //	 $('tr').find('td:nth-child(' + column + '):contains(Rejected By CATMAN)').parent().addClass("rejct-row"); // Could be an hexadecimal value as #EE3B3B
		    //	 $('tr').find('td:nth-child(' + column + '):contains(Rejected By ARUN)').parent().addClass("rejct-row"); // Could be an hexadecimal value as #EE3B3B
		    //	$('tr').find('td:nth-child(' + column + '):contains(Rejected By COE)').parent().addClass("rejct-row"); // Could be an hexadecimal value as #EE3B3B 
		    	/* $('tr').find('td:nth-child(' + column + '):contains(Rejected By CATMAN)').child().addClass("rejct-col"); // Could be an hexadecimal value as #EE3B3B
		    	 $('tr').find('td:nth-child(' + column + '):contains(Rejected By ARUN)').child().addClass("rejct-col"); // Could be an hexadecimal value as #EE3B3B
		    	$('tr').find('td:nth-child(' + column + '):contains(Rejected By CEO)').child().addClass("rejct-col"); // Could be an hexadecimal value as #EE3B3B 
	
		    };

		})(jQuery);*/

