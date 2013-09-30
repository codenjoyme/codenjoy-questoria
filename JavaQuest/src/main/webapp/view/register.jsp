<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.7.2.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/register.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
    <form:form commandName="player" action="register" method="POST">
        <table>
            <c:choose>
                <c:when test="${!registered}">
                    <tr>
                        <td>Как завать твоего героя?</td>
                    </tr>
                    <tr>
                        <td><form:input id="name" path="name"/></td>
                        <c:if test="${busy}">
                            <td><span>Игрок с таким именем уже зарегистрирован</span></td>
                        </c:if>
                    </tr>
                <tr>
                    <td colspan="3">
                        <input type="submit" value="Зарегистрировать меня!"/>
                    </td>
                </tr>
                </c:when>
                <c:otherwise>
                    <c:set var="link" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}/user/${playerGameCode}" />
                    <span>Запомни этот <a href="${link}">${link}</a> линк,
                        далее по нему ты сможешь продолжить свою игру. Для начала игры просто кликни по нему.</span>
                </c:otherwise>
            </c:choose>
        </table>
    </form:form>
</body>
</html>