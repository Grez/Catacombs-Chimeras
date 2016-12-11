<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
    <jsp:attribute name="body">
            <c:if test="${param.error != null}">
                <b>Invalid username or password.</b>
            </c:if>
            <c:if test="${param.logout != null}">
                <b>You have been logged out.</b>
            </c:if>

            <form class="form-signin" name="f" action="${pageContext.request.contextPath}/login" method="POST">

                <fieldset>
                    <input class="form-control form-group" type="text" name="username" placeholder="Username">
                    <input class="form-control" type="password" name="password" placeholder="Password">
                    <button name="submit" class="btn btn-block btn-primary"  type="submit">Sign in</button>
                </fieldset>
            </form>

            </jsp:attribute>
</my:pagetemplate>