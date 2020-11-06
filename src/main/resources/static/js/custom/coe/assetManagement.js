
$(document).ready(
		function() {
			$('#coe-create').on('show.bs.modal', function (e) {
				var link = $(e.relatedTarget);
				   $(this).find(".asset-create").load(link.attr("href"));
				});
			
			$('#editAsset').on('show.bs.modal', function (e) {
				var link = $(e.relatedTarget);
				   $(this).find(".asset-edit").load(link.attr("href"));
				});
			
			$('#coe-delete').on('show.bs.modal', function (e) {
				var link = $(e.relatedTarget);
				   $(this).find(".asset-delete").load(link.attr("href"));
				});			
			$('.filter-ref').on('keyup', function() {
				oTable.columns(0).search(this.value).draw();
			});
			$('.example tr th:first-child').on('click', function() {
				$(".filter-ref").fadeIn();
				$('.example tr th:first-child span').hide();

			});

			$('.example thead').on('mouseleave', function() {
				$(".filter-ref").hide();
				$('.example tr th:first-child span').show();

			});

			$('#DataTables_Table_1_length').css({
				'text-align' : 'right',
				'padding' : '20px 0'
			});
			$('#DataTables_Table_1_length label').css({
				'color' : '#ccc'
			});
			$($('#DataTables_Table_1_wrapper .row')[0]).css({
				'float' : 'right'
			});
			$($($('#DataTables_Table_1_wrapper .row')[0]).find('.col-sm-6')[0])
					.css({
						'width' : 'auto',
						'float' : 'left'
					});
			$($($('#DataTables_Table_1_wrapper .row')[0]).find('.col-sm-6')[1])
					.css({
						'width' : 'auto',
						'float' : 'left'
					});
			$($($('#DataTables_Table_1_wrapper .row')[0]).find('.col-sm-6')[1])
					.after($(".search-icon1"));

		});