<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Crawl Url</title>
</head>
<body>
<form action="/webcrawler" method="post">
    <table>
        <tr>
            <td> url:</td>
            <td><input type="text" name="url" /></td>
        </tr>
                <tr>
                    <td> depth:</td>
                    <td><input type="text" name="depth" /></td>
                </tr>
    </table>
    <input type="submit" name="submit" value="Submit" />
    <br/><br/>
    <h2>Drop Down List of links</h2>
       Select a Link:&nbsp;
       <select name="link">
           <c:forEach items="${listCrawledLink}" var="crawledlink">
               <option value="${crawledlink.seed}"
                   <c:if test="${crawledlink.id eq selectedLinkId}">selected="selected"</c:if>
                   >
                   ${crawledlink.seed} &nbsp;&nbsp; ${crawledlink.date}
               </option>
           </c:forEach>
       </select>
</form>
</body>
</html>