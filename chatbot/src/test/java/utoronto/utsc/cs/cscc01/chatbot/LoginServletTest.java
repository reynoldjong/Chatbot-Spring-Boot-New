package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import java.security.Principal;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class LoginServletTest {
  
  private UserDatabase db;
  private LoginServlet servlet;
  @Before
  public void setUp() {
    db = new MockDatabase();
    //servlet = new LoginServlet(db);
  }
  
  @Test
  public void testUserLoginValid() throws ServletException, IOException {
  //  HttpServletRequest mockRequest = mock(HttpServletRequest.class);
   // HttpServletResponse mockResponse = mock(HttpServletResponse.class);
   // Principal principal = mock(Principal.class);
    
  //  when(mockRequest.getParameter("username")).thenReturn("JohnDoe");
   // when(mockRequest.getParameter("password")).thenReturn("abc123");
   // when(mockRequest.getUserPrincipal()).thenReturn(principal);
    
  //  servlet.doPost(mockRequest, mockResponse);
    
  //  verify(mockResponse, atLeastOnce()).sendRedirect("/adminpage");
  }
  
  @Test
  public void testUserLoginInvalid() throws ServletException, IOException{
   // HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    //HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    //Principal principal = mock(Principal.class);
    //RequestDispatcher reqDisp = mock(RequestDispatcher.class);
    
   // when(mockRequest.getParameter("username")).thenReturn("FakeName");
    //when(mockRequest.getParameter("password")).thenReturn("asdfasdfasdf");
    //when(mockRequest.getUserPrincipal()).thenReturn(principal);
    //when(mockRequest.getRequestDispatcher("/login.jsp")).thenReturn(reqDisp);
    
    //servlet.doPost(mockRequest, mockResponse);
    
   // verify(mockRequest, atLeastOnce()).getRequestDispatcher("/login.jsp");
    
  }
}
