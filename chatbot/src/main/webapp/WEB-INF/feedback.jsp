<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Rating and Comments</title>
</head>
<body>
<center><b>
<h2>Feedback Form</h2>
<form action="feedback" method="post">
        <select name="feedback">
            <c:forEach items="${listFeedback}" var="feedback">
                <option value="${feedback.comments}"
                    <c:if test="${feedback.id eq selectedFeedbackId}">selected="selected"</c:if>
                    >
                    ${feedback.rating} &nbsp;&nbsp; ${feedback.comments}
                </option>
            </c:forEach>
        </select>
        <br/><br/>

How do you rate the Chatbot?
    <input type="radio" name="rating" value="1" required /> Hate
    <input type="radio" name="rating" value="2" /> Dislike
    <input type="radio" name="rating" value="3" /> Neutral
    <input type="radio" name="rating" value="4" /> Like
    <input type="radio" name="rating" value="5" /> Love
<br /><br />

Any extra comments?
	<textarea name="comments" rows="6" cols="40"></textarea><BR><BR>

<input type="submit" value="Send">
<input type="reset" value="Clear">

</form>
</body>
</html>