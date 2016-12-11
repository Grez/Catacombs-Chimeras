<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Troops">
    <jsp:attribute name="body">

        <my:a href="/troop/new" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            New troop
        </my:a>

        <h2>List of Troops</h2>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Mission</th>
                    <th>Money</th>
                    <th></th>
                    <th></th>
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
                            <my:a href="/troop/${troop.id}/details" class="btn btn-primary">Details</my:a>
                        </td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/troop/${troop.id}/delete">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>
