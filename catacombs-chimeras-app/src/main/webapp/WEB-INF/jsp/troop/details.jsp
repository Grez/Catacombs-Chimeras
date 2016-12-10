<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Troop Details">
    <jsp:attribute name="body">

        <div class="row">
            <div class="col-xs-6 col-sm-6 col-md-2 col-lg-2">
                <form method="get" action="${pageContext.request.contextPath}/troop/update/new/${troop.id}">
                    <button type="submit" class="btn btn-primary">Update</button>
                </form>
            </div>

            <div class="col-xs-6 col-sm-6 col-md-2 col-lg-2">
                <form method="post" action="${pageContext.request.contextPath}/troop/${troop.id}/delete">
                    <button type="submit" class="btn btn-primary">Delete</button>
                </form>
            </div>
        </div>

        <table class="table">
            <thead>
            <tr>
                <th>id</th>
                <th>name</th>
                <th>mission</th>
                <th>money</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${troop.id}</td>
                <td><c:out value="${troop.name}"/></td>
                <td><c:out value="${troop.mission}"/></td>
                <td><c:out value="${troop.amountOfMoney}"/></td>
            </tr>
            </tbody>
        </table>

<h2>Heroes</h2>
<table border="1" cellpadding="5" width="300" class="table">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>experience</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${troopHeroes}" var="hero">
        <tr>
            <td>${hero.id}</td>
            <td>${hero.name}</td>
            <td>${hero.experience}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/troop/${troop.id}/hero/remove/${hero.id}">
                    <button type="submit" class="btn btn-primary">Remove</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

    </jsp:attribute>
</my:pagetemplate>
