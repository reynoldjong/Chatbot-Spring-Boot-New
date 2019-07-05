package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class LogoutServletTest {
  
  private LogoutServlet servlet;
  
  @Before
  public void setUp() {
    servlet = new LogoutServlet();
  }
  
  @Test
  public void testLogout() throws ServletException, IOException {
    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    
    servlet.doGet(mockRequest, mockResponse);
    
    verify(mockRequest, atLeastOnce()).logout();
  }
}
