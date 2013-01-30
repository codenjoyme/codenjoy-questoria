<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <style>
        div {
            font-family:lucida console;
            font-size:16px;
            text-decoration: none;
        }
        div a, div a:hover {
            text-decoration: none;
        }
    </style>
    <script src="/resources/js/jquery-1.7.2.js"></script>
    <script src="/resources/js/game.js"></script>
</head>
<body>
    <table>
    <tr>
        <td>
    	    <div id="message">${message}</div>
    	</td>
    	<td>
    	    <div>
                <textarea id="answer" rows="30" cols="60"></textarea>
                <input id="say" type="button" value="Say"/>
    	    </div>
        </td>
    </tr>
</body>
</html>