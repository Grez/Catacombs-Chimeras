<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="New troop">
    <jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/troop/create"
               modelAttribute="troopCreate" cssClass="form-horizontal">
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${mission_error?'has-error':''}">
            <form:label path="mission" cssClass="col-sm-2 control-label">Description</form:label>
            <div class="col-sm-10">
                <form:input path="mission" cssClass="form-control"/>
                <form:errors path="mission" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${amountOfMoney_error?'has-error':''}">
            <form:label path="amountOfMoney" cssClass="col-sm-2 control-label">Money</form:label>
            <div class="col-sm-10">
                <form:input path="amountOfMoney" cssClass="form-control"/>
                <form:errors path="amountOfMoney" cssClass="help-block"/>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Create troop</button>
    </form:form>

    </jsp:attribute>
</my:pagetemplate>