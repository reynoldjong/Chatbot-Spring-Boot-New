<html>
<body>
<div style="color:red">
    <%
     Object msg = request.getAttribute("error");
        if(msg != null ){
           out.println(msg);
        }
     %>
</div>
<form action="/login" method="post">
    <table>
        <tr>
            <td> User Name:</td>
            <td><input type="text" name="username"/></td>
        </tr>
        <tr>
            <td> Password:</td>
            <td><input type="password" name="password"/></td>
        </tr>
    </table>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>