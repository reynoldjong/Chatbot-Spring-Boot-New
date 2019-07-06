package utoronto.utsc.cs.cscc01.chatbot;

// used for live testing with App.java will delete this after
// actual user database goes up
public class TempDatabase implements UserDatabase{

  @Override
  public boolean verifyUser(String username, String password) {
    // TODO Auto-generated method stub
    return true;
  }

}
