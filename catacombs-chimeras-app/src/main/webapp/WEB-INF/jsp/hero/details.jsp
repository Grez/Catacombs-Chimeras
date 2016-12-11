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
                <form method="get" action="${pageContext.request.contextPath}/hero/update/new/${hero.id}">
                    <button type="submit" class="btn btn-primary">Update</button>
                </form>
            </div>

            <div class="col-xs-6 col-sm-6 col-md-2 col-lg-2">
                <form method="post" action="${pageContext.request.contextPath}/hero/delete/${hero.id}">
                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
            </div>

            <div class="col-xs-6 col-sm-6 col-md-2 col-lg-2">
                <form method="post" action="${pageContext.request.contextPath}/hero/${hero.id}/troop/remove/">
                    <button type="submit" class="btn btn-primary">Leave troop</button>
                </form>
            </div>

            <div class="col-xs-6 col-sm-6 col-md-2 col-lg-2">
                <form method="get" action="${pageContext.request.contextPath}/hero/${hero.id}/troop/">
                    <button type="submit" class="btn btn-primary">Join troop</button>
                </form>
            </div>
        </div>

        <h2>Details</h2>
        <table class="table">
            <tr>
                <th>Id</th>
                <td>${hero.id}</td>
            </tr>
            <tr>
                <th>Name</th>
                <td><c:out value="${hero.name}" /></td>
            </tr>
            <tr>
                <th>Experience</th>
                <td><c:out value="${hero.experience}" /></td>
            </tr>
            <tr>
                <th>Troop</th>
                <td><c:out value="${troopName}" /></td>
            </tr>
        </table>

        <h2>Roles</h2>
        <my:a href="/hero/${hero.id}/role" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            Assign role
        </my:a>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${roles}" var="role">
                    <tr>
                        <td>${role.id}</td>
                        <td><c:out value="${role.name}" /></td>
                        <td><c:out value="${role.description}" /></td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/hero/${hero.id}/role/remove/${role.id}">
                                <button type="submit" class="btn btn-danger">Remove</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>
