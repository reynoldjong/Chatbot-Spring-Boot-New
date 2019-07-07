<html>
   <head>
   <meta http-equiv = "Content-Type" content = "text/html; charset = ISO-8859-1">
      <title>File Uploading Form</title>
   </head>

   <body>
   <div align="center">
      <form  action = "upload" method = "post" enctype = "multipart/form-data">
            <table border = "1">
                <tr>
                    <td align = "centre"><b>Select multiple files to upload</b></td>
                </tr>
                <tr>
                    <td>
                        Specify file: <input type = "file" name = "file" size = "1024" id = "file" multiple>
                    </td>
                </tr>
                <tr>
                    <td align = "centre">
                        <input type = "submit" name = "submit" value = "Upload Files" >
                    </td>
                </tr>
            </table>
      </form>
   </body>
</html>