<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <style>
        div, #message {
            font-family:lucida console;
            font-size:12px;
        }
        #answer {
            width:500px;
            height:500px;
            border: 1px solid lightgray;
        }

        .fog {
            color:gray;
        }
        .gold {
            color:gold;
        }
        .border {
            color:pink;
        }
        .iam{
            color:blue;
        }
        .monster {
            color:red;
        }
        .wall {
            color:brown;
        }
        .alien {
            color:green;
        }
    </style>

    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.7.2.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/ace/src/ace.js" type="text/javascript" charset="utf-8"></script>
    <script>
        $(document).ready(function () {
            editor = ace.edit("answer");
            editor.setTheme("ace/theme/eclipse");
            editor.getSession().setMode("ace/mode/java");
        });
    </script>

    <script src="${pageContext.request.contextPath}/resources/js/game.js"></script>
    <script>
        $(document).ready(function () {
            initGame('${pageContext.request.contextPath}/', '${playerGameCode}', editor);
        });
    </script>
</head>
<body>
    <table>
        <tr>
            <td>
                <div id="map">${map}</div>
            </td>
            <td valign="top">
                <div>
                    <div id="info">${info}</div></br>
                    <div id="answer"></div>
                    <div align="right"><input id="say" type="button" value="Say"/></div>
                </div>
            </td>
            <td valign="top">
                <br>
                <textarea id="message" rows="40" cols="80" wrap="off">${message}</textarea></br>
            </td>
            <td>
            </td>
        </tr>
    </table>
</body>
</html>