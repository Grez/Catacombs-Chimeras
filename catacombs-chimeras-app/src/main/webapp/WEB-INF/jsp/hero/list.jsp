<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Heroes">
    <jsp:attribute name="body">

        <my:a href="/hero/new" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            New hero
        </my:a>

        <h2>List of Heroes</h2>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Experience</th>
                    <th>Troop name</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${heroes}" var="pair">
                    <tr>
                        <td>${pair.hero.id}</td>
                        <td>${pair.hero.name}</td>
                        <td>${pair.hero.experience}</td>
                        <td>${pair.troopName}</td>
                        <td>
                            <my:a href="/hero/details/${pair.hero.id}" class="btn btn-primary">Details</my:a>
                        </td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/hero/delete/${pair.hero.id}">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>
