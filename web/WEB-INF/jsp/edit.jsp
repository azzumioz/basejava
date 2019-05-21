<%@ page import="com.urise.webapp.model.ContactTypes" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/page" prefix="page" %>

<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">

            <dt><h1>Имя:</h1></dt>
            <br>
            <input type="text" name="fullName" size="50" value="${resume.fullName}">

        <h2>Контакты:</h2>
        <p>
            <c:forEach var="type" items="<%=ContactTypes.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContacts(type)}"></dd>
        </dl>
        </c:forEach>
        <hr>
            <c:forEach var="type" items="<%=SectionType.values()%>">
                <c:set var="section" value="${resume.getSection(type)}"/>
                <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
        <h2>${type.title}:</h2>
        <c:choose>
            <c:when test="${type.name() == 'OBJECTIVE'}">
                <textarea name="${type}" rows="5"
                          cols="100"><%=section%></textarea>
            </c:when>
            <c:when test="${type.name() == 'PERSONAL'}">
                <textarea name="${type}" rows="5"
                          cols="100"><%=section%></textarea>
            </c:when>
            <c:when test="${type.name() == 'ACHIEVEMENT' || type.name() == 'QUALIFICATIONS'}">
                    <textarea name='${type}' cols=75
                              rows=5><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
            </c:when>
            <c:when test="${type.name() == 'EXPERIENCE' || type.name()=='EDUCATION'}">
                <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>"
                           varStatus="counter">
                    <page:blockTextLine title="Организация" name="${type}" value="${org.homePage.name}" size="50" />
                    <page:blockTextLine title="Сайт" name="${type}url" value="${org.homePage.url}" size="50" />
                    <div style="margin-left: 30px">
                        <c:forEach var="pos" items="${org.positions}">
                            <jsp:useBean id="pos" type="com.urise.webapp.model.Organization.Position"/>
                            <page:blockTextLine title="Начальная дата" name="${type}${counter.index}startDate" value="<%=DateUtil.format(pos.getStartDate())%>" size="10" placeholder="MM/yyyy" />
                            <page:blockTextLine title="Конечная дата" name="${type}${counter.index}endDate" value="<%=DateUtil.format(pos.getEndDate())%>" size="10" placeholder="MM/yyyy" />
                            <page:blockTextLine title="Должность" name="${type}${counter.index}title" value="${pos.title}" size="50" />
                            <page:blockTextArea title="Описание" name="${type}${counter.index}description" value="${pos.description}" />
                        </c:forEach>
                    </div>
                </c:forEach>
            </c:when>
        </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
