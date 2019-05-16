<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8" %>
<%@attribute name="nameSection" required="true" type="java.lang.String" %>
<%@attribute name="name" required="true" type="java.lang.String" %>
<%@attribute name="url" required="true" type="java.lang.String" %>
<%@attribute name="startDate" required="true" type="java.lang.String" %>
<%@attribute name="endDate" required="true" type="java.lang.String" %>
<%@attribute name="title" required="true" type="java.lang.String" %>

<div class="block-item">Организация</div>
<dd><input class="block-input" placeholder="Имя организации" type="text"
           name="sectionExperience" size="50"
           value="${name}"></dd>
<div class="block-item">Сайт</div>
<dd><input class="block-input" placeholder="Имя сайта" type="text" name="sectionExperience"
           size="50"
           value="${url}"></dd>
<div class="block-item">Начало</div>
<dd><input class="block-input" placeholder="Дата" type="text" name="sectionExperience"
           size="10"
           value="${startDate}"></dd>
<div class="block-item">Окончание</div>
<dd><input class="block-input" placeholder="Дата" type="text" name="sectionExperience"
           size="10"
           value="${endDate}"></dd>
<div class="block-item">Должность</div>
<dd><input class="block-input" placeholder="Должность" type="text" name="sectionExperience"
           size="50"
           value="${title}"></dd>
<c:choose>
    <c:when test="${!nameSection.equals('EDUCATION') }">
        <div class="block-item">Опыт</div>
        <dd><textarea name="${name}" placeholder="Опыт работы"
                      rows="5"
                      cols="100">${position.getDescription()}</textarea></dd>
    </c:when>
</c:choose>


