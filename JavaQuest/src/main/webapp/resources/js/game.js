function initGame(contextPath, playerGameCode, editor) {

    var serverAnswered = true;

    function drawField(data) {
        $("#field").html(data.field);
        showMessage(data.message);
        showAnswer(data.code);
        $("#info").html(data.info);
        serverAnswered = true;
    }

    function showMessage(message) {
        var container = $("#message");
        if (container.val() != message) {
            container.val(message);
            scrollDown(container);
        }
    }

    function showAnswer(code) {
        if (code == '') {
            return;
        }

        if (code == 'no_code') {
            editor.setValue('');
            return;
        }

        if (editor.getValue() == '') {
            editor.setValue(code.replace('|', ''));
            if (!onEditor()) {
                setCaretToPos(editor, code.indexOf('|'));
            }
        }
    }

    function scrollDown(textarea) {
        textarea.scrollTop(
            textarea[0].scrollHeight
        );
    }

    function setCaretToPos(editor, pos) {
        if (pos == -1) return;
        editor.focus();
        editor.clearSelection();
        editor.getSession().getSelection().selectionLead.setPosition(1, 11);
    }

    function send(command) {
        if (!serverAnswered) {
            return;
        }
        serverAnswered = false;

        command = command.split("\n").join("</br>");
        $.getJSON(contextPath + 'answer', {command:command, playerGameCode:playerGameCode, time:$.now()}, drawField);
    }

    function onEditor() {
        return $("#editor").is(":focus");
    }

    $("body").keydown(function(e){
        if (onEditor()) {
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
        send(editor.getValue());
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