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

        <table border="1" cellpadding="5" width="300" class="table">
            <thead>
            <tr>
                <th>id</th>
                <th>name</th>
                <th>mission</th>
                <th>money</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${troops}" var="troop">
                <tr>
                    <td>${troop.id}</td>
                    <td>${troop.name}</td>
                    <td>${troop.mission}</td>
                    <td>${troop.amountOfMoney}</td>
                    <td>
                        <my:a href="/troop/${troop.id}/details" class="btn btn-primary">Details</my:a>
                    </td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/troop/${troop.id}/delete">
                            <button type="submit" class="btn btn-primary">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>
