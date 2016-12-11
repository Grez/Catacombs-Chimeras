<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<my:pagetemplate title="Troops">
    <jsp:attribute name="body">

        <div class="row">
            <div class="col-xs-6 col-sm-6 col-md-2 col-lg-2">
                <form method="get" action="${pageContext.request.contextPath}/pa165/troop/report/avg">
                    <button type="submit" class="btn btn-primary">Average experience report</button>
                </form>
            </div>
            <div class="col-xs-6 col-sm-6 col-md-2 col-lg-2">
                <form method="get" action="${pageContext.request.contextPath}/pa165/troop/report/money">
                    <button type="submit" class="btn btn-primary">Money per hero report</button>
                </form>
            </div>
        </div>
        <p></p>
        <sec:authorize access="hasAnyRole('ADMIN')">
                <my:a href="/pa165/troop/new" class="btn btn-primary">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    New troop
                </my:a>
        </sec:authorize>

        <h2>List of Troops</h2>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Mission</th>
                    <th>Money</th>
                    <th></th>
                    <sec:authorize access="hasAnyRole('ADMIN')">
                        <th></th>
                    </sec:authorize>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${troops}" var="troop">
                    <tr>
                        <td>${troop.id}</td>
                        <td><c:out value="${troop.name}"/></td>
                        <td><c:out value="${troop.mission}"/></td>
                        <td><c:out value="${troop.amountOfMoney}"/></td>
                        <td>
                            <my:a href="/pa165/troop/${troop.id}/details" class="btn btn-primary">Details</my:a>
                        </td>
                        <sec:authorize access="hasAnyRole('ADMIN')">
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/pa165/troop/${troop.id}/delete">
                                    <button type="submit" class="btn btn-danger">Delete</button>
                                </form>
                            </td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>
