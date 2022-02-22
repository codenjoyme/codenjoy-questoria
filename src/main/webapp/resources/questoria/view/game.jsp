<%--
  #%L
  Codenjoy - it's a dojo-like platform from developers to developers.
  %%
  Copyright (C) 2012 - 2022 Codenjoy
  %%
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as
  published by the Free Software Foundation, either version 3 of the
  License, or (at your option) any later version.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  
  You should have received a copy of the GNU General Public
  License along with this program.  If not, see
  <http://www.gnu.org/licenses/gpl-3.0.html>.
  #L%
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <style>
        body {
            margin : 15px;
        }
        div, #message {
            font-family:lucida console;
            font-size:11px;
        }
        #answer {
            width:540px;
            height:430px;
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
        .mentor {
            color:deeppink;
        }
        .dron {
            color:firebrick;
        }
        .stone {
            color:sienna;
        }
        .default {
            color:black;
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
                <div id="field">${field}</div>
            </td>
            <td valign="top">
                <div>
                    <div id="info">${info}</div></br>
                    <div id="answer"></div>
                    <div align="right"><input id="say" type="button" value="Say"/></div>
                </div>
            </td>
        </tr>
        <tr>
            <td valign="top" colspan="2">
                <textarea id="message" rows="20" cols="159" wrap="off">${message}</textarea></br>
            </td>
            <td>
            </td>
        </tr>
    </table>
</body>
</html>
