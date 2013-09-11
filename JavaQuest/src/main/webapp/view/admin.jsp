<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
</head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="link" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}" />
<table>
    <tr>
        <td><a href="${ctx}/admin31415?save">Save</a></td>
        <td><a href="${ctx}/admin31415?load">Load</a></td>
   </tr>

    <c:forEach var="player" items="${players}">
        <tr>
            <td><a href="${link}/user/${player.gameCode}">${player.name}</a></td>
            <td><a href="${ctx}/admin31415?remove=${player.name}">Game over</a></td>
        </tr>
        <tr>
            <td colspan="2">${player.game.playerInfo}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>