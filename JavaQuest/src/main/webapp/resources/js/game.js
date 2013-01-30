$(document).ready(function() {

    var serverAnswered = true;

//    $.ajaxSetup({
//        async: true
//    });

    function drawMap(data) {
        $("#message").html(data.map);
        serverAnswered = true;
    }

    function send(command) {
        $.getJSON('/answer', 'command=' + command + "&time=" + $.now(), drawMap);
//        $.ajax({
//            url: '/answer',
//            dataType : "json",
//            data:'command=' + command + "&time=" + $.now(),
//            success: drawMap
//        });
    }

    function onTextArea() {
        return $("#answer").is(":focus");
    }

    $("body").keydown(function(e){
        if (onTextArea() || !serverAnswered) {
            return;
        }
        serverAnswered = false;

        if (e.keyCode == 32) {
            send('refresh');
        } else if (e.keyCode == 38) {
            send('up');
        } else if (e.keyCode == 40) {
            send('down');
        } else if(e.keyCode == 37) {
            send('left');
        } else if(e.keyCode == 39) {
            send('right');
        }
    });

    $("#say").click(function(){
        send($("#answer").val());
    });

});