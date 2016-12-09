<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Roles">
    <jsp:attribute name="body">

        <my:a href="/role/new" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            New role
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
                <form method="post" action="${pageContext.request.contextPath}/role/${role.id}/delete">
                    <button type="submit" class="btn btn-primary">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

    </jsp:attribute>
</my:pagetemplate>