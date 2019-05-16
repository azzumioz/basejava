<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8" %>
<%@attribute name="name" required="true" type="java.lang.String" %>
<%@attribute name="namePlaceholder" required="true" type="java.lang.String" %>
<%@attribute name="nameSection" required="true" type="java.lang.String" %>

<dd><textarea name="${name}" placeholder="${namePlaceholder}" rows="5"
              cols="100">${""}</textarea></dd>
<c:forEach var="sectionEntry" items="${resume.sections}">
    <dl>
        <c:choose>
            <c:when test="${sectionEntry.getKey() == nameSection}">
                <c:forEach var="item" items="${sectionEntry.getValue().getItems()}">
                    <dd><textarea class="block-input" name="${name}" rows="5"
                                  cols="100">${item}</textarea></dd>
                </c:forEach>
            </c:when>
        </c:choose>
    </dl>
</c:forEach>


