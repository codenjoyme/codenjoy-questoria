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
    </style>
    <script src="/resources/js/jquery-1.7.2.js"></script>
    <script src="/resources/js/game.js"></script>
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
                <textarea id="answer" rows="3" cols="40"></textarea>
                <input id="say" type="button" value="Say"/></br>
                <div id="message">${message}</div></br>
    	    </div>
        </td>
        <td>
        </td>
    </tr>
</body>
</html>