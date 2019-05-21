<%@tag pageEncoding="UTF-8" %>
<%@attribute name="title" required="true" type="java.lang.String" %>
<%@attribute name="name" required="true" type="java.lang.String" %>
<%@attribute name="value" required="true" type="java.lang.String" %>

<dl>
    <dt>${title}:</dt>
    <dd><textarea
            name=${name} rows="5" cols="75">${value}
    </textarea>
    </dd>
</dl>