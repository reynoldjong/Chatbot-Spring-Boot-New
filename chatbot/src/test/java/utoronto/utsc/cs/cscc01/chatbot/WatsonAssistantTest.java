
package utoronto.utsc.cs.cscc01.chatbot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;

public class WatsonAssistantTest {
  private WatsonAssistant assistant;

  @Before
  public void setUp() {
    this.assistant = WatsonAssistant.buildAssistant();
  }

  @Test
  public void testDiscoverySingleton() {
    WatsonAssistant assistant2 = WatsonAssistant.buildAssistant();
    assertSame(assistant, assistant2);
  }

  @Test
  public void testAssistantId() {
    String assistantId = assistant.getAssistantId();
    assertEquals("84452a24-45c5-45db-af2d-3957c16a6f1e", assistantId);
  }
}
