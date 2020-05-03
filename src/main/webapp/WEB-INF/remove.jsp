<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
    <title>File Removing Form</title>
</head>
<body>

<div align="center">
    <h2>Drop Down List of files</h2>
    <form action="remove" method="post">
        Select a File:&nbsp;
        <select name="file">
            <c:forEach items="${listUploadedFile}" var="uploadedfile">
                <option value="${uploadedfile.filename}"
                    <c:if test="${uploadedfile.id eq selectedFileId}">selected="selected"</c:if>
                    >
                    ${uploadedfile.filename}
                </option>
            </c:forEach>
        </select>
        <br/><br/>
        <input type="submit" value="Remove" />
    </form>
</div>
</body>
</html>