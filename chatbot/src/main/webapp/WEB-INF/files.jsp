<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
   <head>
   <meta http-equiv = "Content-Type" content = "text/html; charset = ISO-8859-1">
      <title>File Uploading Form</title>
   </head>

   <body>
   <div align="centre">
      <form action = "files" method = "post" enctype = "multipart/form-data">
            <table border = "1">
                <tr>
                    <td align = "centre"><b>Select multiple files to upload</b></td>
                </tr>
                <tr>
                    <td>
                        Specify file: <input type="file" name="file" multiple="true">
                    </td>
                </tr>
                <tr>
                    <td align = "centre">
                        <input type = "submit" name = "action" value = "upload" >
                    </td>
                </tr>
            </table>
          <h2>Drop Down List of files</h2>
              Select a File:&nbsp;
              <select name="file">
                  <c:forEach items="${listUploadedFile}" var="uploadedfile">
                      <option value="${uploadedfile.filename}"
                          <c:if test="${uploadedfile.id eq selectedFileId}">selected="selected"</c:if>
                          >
                          ${uploadedfile.filename} &nbsp;&nbsp; ${uploadedfile.date}
                      </option>
                  </c:forEach>
              </select>
              <br/><br/>
              <input type= "submit" name = "action" value= "remove" >
          </form>
      </div>
   </body>
</html>