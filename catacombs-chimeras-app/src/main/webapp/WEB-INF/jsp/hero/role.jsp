<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Troop Assignment">
    <jsp:attribute name="body">


        <form:form method="post" action="${pageContext.request.contextPath}/hero/role/add/"
                   modelAttribute="hero" cssClass="form-horizontal">
            <div class="form-group ${role_error?'has-error':''}">
                <form:label path="id" cssClass="col-sm-2 control-label">New Troop</form:label>
                <div class="col-sm-10">
                    <form:select id="id" path="id" name="id" class="form-control" required="required">
                         <form:options items="${availableRoles}" itemValue="id" itemLabel="name"/>
                    </form:select>
                </div>
            </div>


            <button class="btn btn-primary" type="submit">Put in Troop</button>
        </form:form>

    </jsp:attribute>
</my:pagetemplate>