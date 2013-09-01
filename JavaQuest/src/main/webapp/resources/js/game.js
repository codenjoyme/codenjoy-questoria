function initGame(contextPath) {

    var serverAnswered = true;

    function drawMap(data) {
        $("#map").html(data.map);
        showMessage(data.message);
        showAnswer(data.code);
        $("#info").html(data.info);
        serverAnswered = true;
    }

    function showMessage(message) {
        var container = $("#message");
        container.val(message);
        scrollDown(container);
    }

    function showAnswer(code) {
        if (code == '') {
            return;
        }
        var container = $("#answer");

        if (code == 'no_code') {
            container.val('');
            return;
        }

        if (container.val() == '') {
            container.val(code.replace('|', ''));
            if (!onTextArea()) {
                setCaretToPos(container, code.indexOf('|'));
            }
        }
    }

    function scrollDown(textarea) {
        textarea.scrollTop(
            textarea[0].scrollHeight
        );
    }

    function setSelectionRange(input, selectionStart, selectionEnd) {
        input = input[0];
        if (input.setSelectionRange) {
            input.focus();
            input.setSelectionRange(selectionStart, selectionEnd);
        } else if (input.createTextRange) {
            var range = input.createTextRange();
            range.collapse(true);
            range.moveEnd('character', selectionEnd);
            range.moveStart('character', selectionStart);
            range.select();
        }
    }

    function setCaretToPos(input, pos) {
        setSelectionRange(input, pos, pos);
    }

    function send(command) {
        if (!serverAnswered) {
            return;
        }
        serverAnswered = false;

        command = command.split("\n").join("</br>");
        $.getJSON(contextPath + 'answer', {command:command, time:$.now()}, drawMap);
    }

    function onTextArea() {
        return $("#answer").is(":focus");
    }

    $("body").keydown(function(e){
        if (onTextArea()) {
            return;
        }

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

    function timer(delay, onTimer) {
        window.setInterval(function() {
            if (onTimer) {
                onTimer();
            }
	    }, delay);
	}

    timer(1000, function () {
        send('refresh');
    });
}