<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Get Frequently Asked Questions and Feedback</title>
</head>
<body>
<form action="getdata" method="post">
        <select name="query">
            <c:forEach items="${listQuery}" var="query">
                <option value="${query.content}"
                    <c:if test="${query.id eq selectedQueryId}">selected="selected"</c:if>
                    >
                    ${query.content} &nbsp;&nbsp; ${query.frequency}
                </option>
            </c:forEach>
        </select>
        <br/><br/>
    <input type="submit" name="getQueries" value="Export Queries Data to CSV" />
    <br /><br />
    <input type="submit" name="getFeedback" value="Export Feedback to CSV" />
    <br /><br />
    <input type="submit" name="getAnswerRatings" value="Export Answer Ratings to CSV" />
    <br /><br />
</form>
    The Average rating for the Chatbot is: ${average}
</body>
</html>