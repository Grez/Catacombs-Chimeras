<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<my:pagetemplate title="Report of money per hero in troops">
    <jsp:attribute name="body">

        <table class="table table-striped">
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Money per hero</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${reportItems}" var="reportItem">
                    <tr>
                        <td>${reportItem.troopDTO.id}</td>
                        <td><c:out value="${reportItem.troopDTO.name}"/></td>
                        <td><c:out value="${reportItem.moneyPerHero}"/></td>
                        <td>
                            <my:a href="/pa165/troop/${reportItem.troopDTO.id}/details" class="btn btn-primary">Details</my:a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>
