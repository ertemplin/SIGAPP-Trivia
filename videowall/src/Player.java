public class Player
{
 public String name;
 public int score;
 public Boolean respond = false;
 
 public Player(String name)
 {
  this.name = name;
  score = 0;
 }
 
 public void addPoints(int points)
 {
  score += points;
 }
}