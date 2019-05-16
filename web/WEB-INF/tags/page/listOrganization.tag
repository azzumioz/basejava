<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8" %>
<%@attribute name="name" required="true" type="java.lang.String" %>
<%@attribute name="nameSection" required="true" type="java.lang.String" %>

<c:forEach var="sectionEntry3" items="${resume.sections}">
    <section>
        <dl>
            <c:choose>
            <c:when test="${sectionEntry3.getKey() == nameSection}">
            <%! int index = 0; %>
            <c:set var="index" value="0" scope="request"/>
            <c:forEach var="organization" items="${sectionEntry3.getValue().getOrganizations()}">

            <div class="block-item">Организация</div>
            <dd><input class="block-input" type="text" name="${name}:<%=index%>:name"
                       size="50"
                       value="${organization.getHomePage().getName()}"></dd>
            <div class="block-item">Сайт</div>
            <dd><input class="block-input" type="text" name="${name}:<%=index%>:url"
                       size="50"
                       value="${organization.getHomePage().getUrl()}"></dd>
            <c:forEach var="position" items="${organization.getPositions()}">
                <div class="block-item">Начало</div>
                <dd><input class="block-input" type="text" name="${name}:<%=index%>:startDate"
                           size="10"
                           value="${position.getStartDate().toString()}"></dd>
                <div class="block-item">Окончание</div>
                <dd><input class="block-input" type="text" name="${name}:<%=index%>:endDate"
                           size="10"
                           value="${position.getEndDate().toString()}"></dd>
                <div class="block-item"></div>
                <dd><input class="block-input" type="text" name="${name}:<%=index%>:title"
                           size="50"
                           value="${position.getTitle()}"></dd>
                <c:choose>
                    <c:when test="${!nameSection.equals('EDUCATION') }">
                        <div class="block-item"></div>
                        <dd><textarea name="${name}:<%=index%>:description" placeholder="Введите текст"
                                      rows="5"
                                      cols="100">${position.getDescription()}</textarea></dd>
                    </c:when>
                </c:choose>
            </c:forEach>
            <p>
                        <%index++;%>
                </c:forEach>
                </c:when>
                </c:choose>
        </dl>
    </section>
</c:forEach>


