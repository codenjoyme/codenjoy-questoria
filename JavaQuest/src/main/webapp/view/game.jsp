<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <style>
        div {
            font-family:lucida console;
            font-size:16px;
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
    <script src="${pageContext.request.contextPath}/resources/js/game.js"></script>
    <script>
        $(document).ready(function () {
            initGame('${pageContext.request.contextPath}/');
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
                <textarea id="answer" rows="15" cols="80"></textarea>
                <input id="say" type="button" value="Say"/></br>
                <textarea id="message" rows="25" cols="80" wrap="off">${message}</textarea></br>
    	    </div>
        </td>
        <td>
        </td>
    </tr>
</body>
</html>