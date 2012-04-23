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
	public static String userAns;
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
		//System.out.println(text);
		if(text.contains("user:")){
			line = text.split(" ");

			// If the user just joined, check him in the list.
			if(!(line[1].equals("video-wall"))){
				if(line[0].equals("user:") && line[2].equals("accepted")){
					for(int i = 0; i < app.active.size(); i++){
						//System.out.println(user.name);
						user = app.active.get(i);
						if(user.name.equals(line[1]))
							inList = true;
					}

					// If the user is not in the list, put him in the list. 
					if(!inList){
						user = new Player(line[1]);
						app.active.add(user);
					}
				}

				// If the answer is a score line, add scores for the user.
				else if(line[0].equals("user:")){
				    //System.out.println(app.active.size());
					for(int i = 0; i < app.active.size(); i++){
						user = app.active.get(i);
						if(user.name.equals(line[1]))
							break;
						
					   
					}
					if(user.respond == false){
						ans = Character.toString(app.getAnswer());
						//System.out.println(ans);
						user.respond = true;
						userAns = line[2].toLowerCase();
						if(userAns.equals(ans)){
						    System.out.println("here: " + user.name);
							System.out.println("YOU ARE RIGHT!");
							user.addPoints(5);
						}
					}
				}
			}
		}


		for(int i = 0; i < app.active.size(); i++){
			user = app.active.get(i);
			//System.out.println("User Name: " + user.name + " User Score: " + user.score);
		}
	}
}
