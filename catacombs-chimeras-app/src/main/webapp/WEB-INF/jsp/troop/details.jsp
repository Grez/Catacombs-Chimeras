<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<my:pagetemplate title="Troop Details">
    <jsp:attribute name="body">

        <div class="row">
            <sec:authorize access="hasAnyRole('ADMIN')">
                <div class="col-xs-6 col-sm-6 col-md-2 col-lg-2">
                    <form method="get" action="${pageContext.request.contextPath}/pa165/troop/update/new/${troop.id}">
                        <button type="submit" class="btn btn-primary">Update</button>
                    </form>
                </div>

                <div class="col-xs-6 col-sm-6 col-md-2 col-lg-2">
                    <form method="post" action="${pageContext.request.contextPath}/pa165/troop/${troop.id}/delete">
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                </div>
            </sec:authorize>
        </div>

        <h2>Details</h2>
        <table class="table">
            <tbody>
                <tr>
                    <th>Id</th>
                    <td>${troop.id}</td>
                </tr>
                <tr>
                    <th>Name</th>
                    <td><c:out value="${troop.name}"/></td>
                </tr>
                <tr>
                    <th>Mission</th>
                    <td><c:out value="${troop.mission}"/></td>
                </tr>
                <tr>
                    <th>Money</th>
                    <td><c:out value="${troop.amountOfMoney}"/></td>
                </tr>
                <tr>
            </tbody>
        </table>

        <h2>Heroes</h2>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Experience</th>
                    <sec:authorize access="hasAnyRole('ADMIN')">
                        <th></th>
                    </sec:authorize>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${troopHeroes}" var="hero">
                    <tr>
                        <td>${hero.id}</td>
                        <td><c:out value="${hero.name}" /></td>
                        <td><c:out value="${hero.experience}" /></td>
                        <sec:authorize access="hasAnyRole('ADMIN')">
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/pa165/troop/${troop.id}/hero/remove/${hero.id}">
                                    <button type="submit" class="btn btn-danger">Remove</button>
                                </form>
                            </td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>
