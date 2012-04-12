import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import edu.purdue.cs.Message;

public class RoomObserver implements Observer
{
  QuizApp app;
  public String line[];
  public int index;
  public static String ans;
  public static String location;
  Player user;
  Boolean inList = false;
  
  public RoomObserver(QuizApp app) {
    this.app = app;
  }
  
  
  
  
  public void update(Observable arg0, Object arg1)
  {
    Message message = (Message) arg1;
    String text = message.getText();
    System.out.println(text);
    if(text.contains("user:")){
      line = text.split(" ");
      
      // If the user just joined, check him in the list.
      if(!(line[1].equals("video-wall"))){
        if(line[2].equals("accepted")){
          for(int i = 0; i < app.active.size(); i++){
            //System.out.println(user.name);
            user = app.active.get(i);
            if(user.name == line[1])
              inList = true;
          }
          
          // If the user is not in the list, put him in the list. 
          if(!inList){
            user = new Player(line[1]);
            app.active.add(user);
          }
        }
        
        // If the answer is a score line, add scores for the user.
        else{
          for(int i = 0; i < app.active.size(); i++){
            user = app.active.get(i);
            ans = Character.toString(app.curAns);
            System.out.println(ans);
            if(line[1].equals(user.name) && line[2].equals(app.curAns))
              user.addPoints(5);
          }
        }
      }
    }
    
    for(int i = 0; i < app.active.size(); i++){
      user = app.active.get(i);
      System.out.println("User Name: " + user.name + " User Score: " + user.score);
    }
  }
}
