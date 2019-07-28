<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
    <title>Indexing Form</title>
</head>
<body>

<div align="center">
    <h2>Drop Down List of links</h2>
    <form action="index" method="post">
        Select a Link:&nbsp;
        <select name="link">
            <c:forEach items="${listCrawledLink}" var="crawledlink">
                <option value="${crawledlink.seed}"
                    <c:if test="${crawledlink.seed eq selectedLinkSeed}">selected="selected"</c:if>
                    >
                    ${crawledlink.seed}
                </option>
            </c:forEach>
        </select>
        <br/><br/>
        <input type="submit" name = "index" value="Index" />
    </form>
</div>
</body>
</html>