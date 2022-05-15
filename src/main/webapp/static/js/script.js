$(function () {
    function callServlet() {
        console.log("atejau")
        var input = $("#content").val();
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: 'orders/validation',
            data: input,
            dataType: "json",
            success: function (json) {
                if (json.resultCodes.length === 0) {
                    $("#result").html("Work order is valid");
                } else {
                    $("#result").html("Work order is not valid: " + json.resultCodes);
                }
            },
            error: function () {
                $("#result").html("Error from server");
            }
        });
    }

    $('#validateBtn').click(function() {
        callServlet();
    });
});
