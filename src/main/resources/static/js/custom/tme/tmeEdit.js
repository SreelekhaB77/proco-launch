$(document).ready(function() {
    $('#refModel').on('show.bs.modal', function(e) {
        var link = $(e.relatedTarget);
        $(this).find(".tme-edit-preview").load(link.attr("href"));
    });

});

function editVisibilityPlan(id) {
    var RandomPageIndexId = $("#RandomPageIndexId").val();
    document.forms[0].method = "POST";
    document.forms[0].action = "editVisibilityPlan.htm?RandomPageIndexId=" + RandomPageIndexId + "&sr_no=" + id;
    document.forms[0].submit();

}


function deleteRequest(id) {
    $('#errorMsg').hide();
    var RandomPageIndexId = $("#RandomPageIndexId").val();
    document.forms[0].method = "POST";
    document.forms[0].action = "deleteRequest.htm?RandomPageIndexId=" + RandomPageIndexId + "&sr_no=" + id;
    document.forms[0].submit();
}

$(document)
    .ready(
        function() {

            $(".searchKey")
                .keyup(
                    function() {
                        var searchTerm = $(".searchKey")
                            .val().replace(/["']/g, "");
                        // var listItem = $('.results tbody').children('tr');
                        var searchSplit = searchTerm
                            .replace(/AND/g, "'):containsi('");
                        $
                            .extend(
                                $.expr[':'], {
                                    'containsi': function(
                                        element,
                                        i,
                                        match,
                                        array) {
                                        return (element.textContent || element.innerText || '')
                                            .toLowerCase()
                                            .indexOf(
                                                (match[3] || "")
                                                .toLowerCase()) >= 0;
                                    }
                                });
                        $(".results tbody tr").not(
                            ":containsi('" + searchSplit + "')").each(
                            function(e) {
                                $(this).attr('visible',
                                    'false');
                            });
                        $(".results tbody tr:containsi('" + searchSplit + "')").each(
                            function(e) {
                                $(this).attr('visible',
                                    'true');
                            });
                        var searchCount = $('.results tbody tr[visible="true"]').length;
                        $('.searchCount').text(
                            searchCount + ' item');
                        if (searchCount == '0') {
                            $('.no-result').show();
                        } else {
                            $('.no-result').hide();
                        }
                        if ($('.searchKey').val().length == 0) {
                            $('.searchCount').hide();
                        } else {
                            $('.searchCount').show();
                        }
                    });

            $('#moc_edit_table').find("tr[id='moc_edit_tr']").find(
                    "td[id='moc_edit_td']")
                .each(
                    function() {
                        // console.log(i++ + ": " + $(this).html())
                        var dateVal = $(this).html();
                        var monthNames = ["Jan", "Feb", "Mar", "Apr",
                            "May", "Jun", "Jul", "Aug", "Sep",
                            "Oct", "Nov", "Dec"
                        ];
                        var date = new Date(dateVal);
                        var day = date.getDate();
                        var monthIndex = date.getMonth();
                        var year = date.getFullYear();
                        $(this).html(day + "-" + monthNames[day - 1])
                    });



            $("#moc_edit_table #checkall").click(function() {
                if ($("#moc_edit_table #checkall").is(':checked')) {
                    $("#moc_edit_table input[type=checkbox]").each(function() {
                        $(this).prop("checked", true);
                    });

                } else {
                    $("#moc_edit_table input[type=checkbox]").each(function() {
                        $(this).prop("checked", false);
                    });
                }
            });

            $("[data-toggle=tooltip]").tooltip();

            $('#btnDelete').click(function() {
                bootbox.confirm("Are you sure want to delete?", function(result) {

                });
            });


        });
