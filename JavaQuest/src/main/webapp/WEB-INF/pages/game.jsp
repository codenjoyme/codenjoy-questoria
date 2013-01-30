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
</head>
<body>
    <table>
    <tr>
        <td>
    	    <div>${message}</div>
    	</td>
    	<td>
    	    <div>
        	    <form:form commandName="answerForm" action="/play" method="POST">
        	        <form:textarea path="message" rows="30" cols="60"/>
        	        <input type="submit" value="Save"/>
        	    </form:form>
    	    </div>
	    	<div style="font-size:50px;">
        	    <a href="/play/?left">&#8656;</a>
        	    <a href="/play/?up">&#8657;</a>
        	    <a href="/play/?down">&#8659;</a>
        	    <a href="/play/?right">&#8658;</a>
        	</div>
        </td>
    </tr>
</body>
</html>