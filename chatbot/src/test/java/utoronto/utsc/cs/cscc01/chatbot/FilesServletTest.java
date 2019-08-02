package utoronto.utsc.cs.cscc01.chatbot;

import org.junit.Before;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


public class FilesServletTest {

    private FilesServlet servlet;

    @Before
    public void setUp() {
        servlet = new FilesServlet();
    }


    /**
     * Test if the file is successfully uploaded by checking the content of file. Content of test.txt is "testing"
     */

//    @Test
//    public void testUpload() throws Exception {
//        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
//        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
//
//
//
//        when(mockRequest.getParameter("action")).thenReturn("upload");
//
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter writer = new PrintWriter(stringWriter);
//        when(mockResponse.getWriter()).thenReturn(writer);
//
//        servlet.upload(mockRequest, mockResponse);
//        System.out.println(stringWriter.toString());
//
//        assertTrue(stringWriter.toString().contains("Can't upload any files"));
//
//    }
//
//    @Test
//    public void testUploadFailed() throws ServletException, IOException{
//        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
//        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
//        Principal principal = mock(Principal.class);
//        RequestDispatcher reqDisp = mock(RequestDispatcher.class);
//
//        when(mockRequest.getParameter("username")).thenReturn("FakeName");
//        when(mockRequest.getParameter("password")).thenReturn("asdfasdfasdf");
//        when(mockRequest.getUserPrincipal()).thenReturn(principal);
//        when(mockRequest.getRequestDispatcher("/login.jsp")).thenReturn(reqDisp);
//
//        servlet.upload(mockRequest, mockResponse);
//
//        verify(mockRequest, atLeastOnce()).getRequestDispatcher("/login.jsp");
//
//    }
//
//
//    /**
//     * Test if the file is successfully removed by checking the content of file. Query should return an empty string.
//     */
//
//    @Test
//    public void testRemoveValid() throws ServletException, IOException {
//        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
//        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
//        Principal principal = mock(Principal.class);
//
//        when(mockRequest.getParameter("username")).thenReturn("JohnDoe");
//        when(mockRequest.getParameter("password")).thenReturn("abc123");
//        when(mockRequest.getUserPrincipal()).thenReturn(principal);
//
//        servlet.doPost(mockRequest, mockResponse);
//
//        verify(mockResponse, atLeastOnce()).sendRedirect("/adminpage");
//    }
//
//    @Test
//    public void testRemoveFailed() throws ServletException, IOException {
//        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
//        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
//        Principal principal = mock(Principal.class);
//
//        when(mockRequest.getParameter("username")).thenReturn("JohnDoe");
//        when(mockRequest.getParameter("password")).thenReturn("abc123");
//        when(mockRequest.getUserPrincipal()).thenReturn(principal);
//
//        servlet.doPost(mockRequest, mockResponse);
//
//        verify(mockResponse, atLeastOnce()).sendRedirect("/adminpage");
//    }

}
