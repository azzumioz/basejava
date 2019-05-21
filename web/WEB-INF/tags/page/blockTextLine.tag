<%@tag pageEncoding="UTF-8" %>
<%@attribute name="title" required="true" type="java.lang.String" %>
<%@attribute name="name" required="true" type="java.lang.String" %>
<%@attribute name="value" required="true" type="java.lang.String" %>
<%@attribute name="size" required="true" type="java.lang.String" %>
<%@attribute name="placeholder" type="java.lang.String" %>

<dl>
    <dt>${title}:</dt>
    <dd><input type="text" name=${name}
               size=${size}
               value="${value}" placeholder="${placeholder}">
    </dd>
</dl>