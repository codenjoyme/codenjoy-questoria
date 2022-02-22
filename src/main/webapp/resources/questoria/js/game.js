/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
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
