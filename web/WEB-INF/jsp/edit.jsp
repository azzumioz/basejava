<%@ page import="com.urise.webapp.model.ContactTypes" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
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
        <dl>
            <dt><h3>Имя:</h3></dt>
            <br>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <p>
            <c:forEach var="type" items="<%=ContactTypes.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContacts(type)}"></dd>
        </dl>
        </c:forEach>
        <p>
            <c:forEach var="type2" items="<%=SectionType.values()%>">
        <dl>
                <%--<dt><h3>${type2.title}:</h3></dt>--%>

            <c:choose>
                <c:when test="${type2.name() == 'OBJECTIVE'}">
                    <dt><h3>${type2.title}:</h3></dt>
                    <dd><textarea name="sectionObjective" placeholder="Позиция" rows="5"
                                  cols="100">${resume.getSection(type2)}</textarea></dd>
                </c:when>
                <c:when test="${type2.name() == 'PERSONAL'}">
                    <dt><h3>${type2.title}:</h3></dt>
                    <dd><textarea name="sectionPersonal" placeholder="Личные качества" rows="5"
                                  cols="100">${resume.getSection(type2)}</textarea></dd>
                </c:when>
                <c:when test="${type2.name() == 'ACHIEVEMENT'}">
                    <dt><h3>${type2.title}:</h3></dt>
                    <div class = "new"></div>
                    <page:listSection name="sectionAchievement" namePlaceholder="Достижения" nameSection="ACHIEVEMENT"/>
                </c:when>
                <c:when test="${type2.name() == 'QUALIFICATIONS'}">
                    <dt><h3>${type2.title}:</h3></dt>
                    <page:listSection name="sectionQualifications" namePlaceholder="Квалификация"
                                      nameSection="QUALIFICATIONS"/>
                </c:when>
                <c:when test="${type2.name() == 'EXPERIENCE'}">
                    <dt><h3>${type2.title}:</h3></dt>
                    <page:block nameSection="EXPERIENCE" name="" url="" startDate="" endDate="" title=""/>
                    <page:listOrganization name="sectionExperience" nameSection="EXPERIENCE"/>
                </c:when>
                <c:when test="${type2.name() == 'EDUCATION'}">
                    <dt><h3>${type2.title}:</h3></dt>
                    <page:block nameSection="EDUCATION" name="" url="" startDate="" endDate="" title=""/>
                    <page:listOrganization name="sectionEducation" nameSection="EDUCATION"/>
                </c:when>
            </c:choose>
        </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
