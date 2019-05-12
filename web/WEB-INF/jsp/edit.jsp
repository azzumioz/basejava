<%@ page import="com.urise.webapp.model.ContactTypes" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
            <dt>Имя:</dt>
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
        <h3>Секции:</h3>
        <p>

            <c:forEach var="type2" items="<%=SectionType.values()%>">
        <dl>
            <dt>${type2.title}</dt>
            <c:choose>
                <c:when test="${type2.name() == 'OBJECTIVE'}">
                    <dd><textarea name="sectionObjective" placeholder="Введите текст" rows="5"
                                  cols="100">${resume.getSection(type2)}</textarea></dd>
                </c:when>
                <c:when test="${type2.name() == 'PERSONAL'}">
                    <dd><textarea name="sectionPersonal" placeholder="Введите текст" rows="5"
                                  cols="100">${resume.getSection(type2)}</textarea></dd>
                </c:when>

                <c:when test="${type2.name() == 'ACHIEVEMENT'}">
                    <dd><textarea name="sectionAchievement" placeholder="Введите текст" rows="5"
                                  cols="100">${""}</textarea></dd>
                    <c:forEach var="sectionEntry" items="${resume.sections}">
                        <dl>
                            <c:choose>
                                <c:when test="${sectionEntry.getKey() == 'ACHIEVEMENT'}">
                                    <c:forEach var="item" items="${sectionEntry.getValue().getItems()}">
                                        <dd><textarea name="sectionAchievement" rows="5" cols="100">${item}</textarea>
                                        </dd>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </dl>
                    </c:forEach>
                </c:when>

                <c:when test="${type2.name() == 'QUALIFICATIONS'}">
                    <dd><textarea name="sectionQualifications" placeholder="Введите текст" rows="5"
                                  cols="100">${""}</textarea></dd>
                    <c:forEach var="sectionEntry2" items="${resume.sections}">
                        <dl>
                            <c:choose>
                                <c:when test="${sectionEntry2.getKey() == 'QUALIFICATIONS'}">
                                    <c:forEach var="item2" items="${sectionEntry2.getValue().getItems()}">
                                        <dd><textarea name="sectionQualifications" rows="5"
                                                      cols="100">${item2}</textarea></dd>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </dl>
                    </c:forEach>
                </c:when>

            </c:choose>
        </dl>
        </c:forEach>


        </p>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
