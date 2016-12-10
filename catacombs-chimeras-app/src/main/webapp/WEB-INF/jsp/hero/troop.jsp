<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<my:pagetemplate title="Troop Assignment">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/hero/${id}/troop/add"
                   modelAttribute="troop" cssClass="form-horizontal">
            <form:label path="name" cssClass="col-sm-2 control-label">Join troop</form:label>
            <div class="col-sm-10">
                <form:select id="name" path="name" name="name" class="form-control" required="required">
                     <form:options items="${availableTroops}" itemValue="name" itemLabel="name"/>
                </form:select>
            </div>
            <button class="btn btn-primary" type="submit">Join troop</button>
        </form:form>

    </jsp:attribute>
</my:pagetemplate>
