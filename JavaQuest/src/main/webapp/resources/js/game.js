$(document).ready(function() {

    var serverAnswered = true;

    function drawMap(data) {
        $("#map").html(data.map);
        $("#message").html(data.message);
        $("#info").html(data.info);
        serverAnswered = true;
    }

    function send(command) {
        $.getJSON('/answer', 'command=' + command + "&time=" + $.now(), drawMap);
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