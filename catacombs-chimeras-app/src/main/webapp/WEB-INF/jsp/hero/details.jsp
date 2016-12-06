<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Hero Details">
    <jsp:attribute name="body">

        <div class="row">
            <div class="col-xs-6 col-sm-6 col-md-2 col-lg-2">
                <form method="post" action="${pageContext.request.contextPath}/hero/delete/${hero.id}">
                    <button type="submit" class="btn btn-primary">Delete</button>
                </form>
            </div>

            <div class="col-xs-6 col-sm-6 col-md-2 col-lg-2">
                <form method="post" action="${pageContext.request.contextPath}/hero/remove/${hero.id}">
                    <button type="submit" class="btn btn-primary">Remove From Troop</button>
                </form>
            </div>

            <div class="col-xs-6 col-sm-6 col-md-2 col-lg-2">
                <form method="post" action="${pageContext.request.contextPath}/hero/troop/${hero.id}">
                    <button type="submit" class="btn btn-primary">Add To Troop</button>
                </form>
            </div>
        </div>

        <table class="table">
            <thead>
                <tr>
                    <th>id</th>
                    <th>name</th>
                    <th>experience</th>
                    <th>troop name</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>${hero.id}</td>
                    <td><c:out value="${hero.name}"/></td>
                    <td><c:out value="${hero.experience}"/></td>
                    <td><c:out value="${troopName}"/></td>
                </tr>
            </tbody>
        </table>

<h2>Roles</h2>
<my:a href="/hero/${hero.id}/role" class="btn btn-primary">
    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
    Assign role
</my:a>
<table border="1" cellpadding="5" width="300" class="table">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>description</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${roles}" var="role">
        <tr>
            <td>${role.id}</td>
            <td>${role.name}</td>
            <td>${role.description}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/hero/${hero.id}/role/remove/${role.id}">
                    <button type="submit" class="btn btn-primary">Remove</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

    </jsp:attribute>
</my:pagetemplate>
    