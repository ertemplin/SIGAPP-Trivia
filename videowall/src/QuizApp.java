import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.purdue.cs.Message;
import edu.purdue.cs.RoomConnection;

public class QuizApp extends Thread
{
 JFrame f;
 Boolean questionStart = false;
 Boolean scoreStart = false;
 RoomConnection room;
 Question question;
 long time = 0;
 String userNow;
 int count = 1;
 final int QUESTION_LENGTH = 15;
 RoomObserver observer;
 ArrayList<Player> active; //Current list of players in the game.
 Player temp; 
 JLabel questionLabel;
 JLabel choiceA;
 JLabel choiceB;
 JLabel choiceC;
 JLabel choiceD;
 JLabel leaderBoardList;
 

 public void run() {
  initGame();

  while(true) {
   if(!questionStart) {
     for(int i = 0; i < active.size(); i++){
       temp = active.get(i);
       temp.respond = false;
     }
    nextQuestion();
    announceQuestion();
    questionStart = true;
    time = System.currentTimeMillis();
   }
   else {
    if(scoreStart && System.currentTimeMillis()-time >= QUESTION_LENGTH * 1000) {
     questionStart = false;
     scoreStart = false;
     sort(active);
     updateLeaderboard();
     announceAnswer();
    }
    else if(!scoreStart && System.currentTimeMillis()-time >= QUESTION_LENGTH * 1000) {
     count++;
     //TODO highlight the correct answer
     scoreStart = true;
     time = System.currentTimeMillis();
    }
    else if(System.currentTimeMillis()-time < QUESTION_LENGTH * 1000) {
     //label[5].setText(Long.toString(QUESTION_LENGTH-(System.currentTimeMillis()-time)/1000) + " second(s) left..");
    }
   }
   //TODO: Check for people joining and then add them to the leaderboard/check if a phone sends in an answer
  }
 }
 
 private void announceQuestion() {
  room.sendMessage(new Message("starting new question"));
 }
 
 private void announceAnswer() {
  String message = "question end\ncorrect answer: " + question.correct + "\nLeaderboard:\n";
  for(int i=0;i<active.size();i++)
   message+= active.get(i).name + " " + active.get(i).score + "\n";
  message += "End Leaderboard";
  room.sendMessage(new Message(message));
 }
 
 private void sort(ArrayList<Player> list) {
  Collections.sort(list,new Comparator<Player>() {public int compare(Player player, Player otherPlayer) {return (player.score > otherPlayer.score) ? 1 : -1;}});
 }

    public char getAnswer(){
 return question.correct;
    }
 
 /* Modify the frame to the question screen */
 public void nextQuestion() {
  question = new Question();
  questionLabel.setText(question.question);

  choiceA.setText("a  "+question.answers[0]);
  choiceB.setText("b  "+question.answers[1]);
  choiceC.setText("c  "+question.answers[2]);
  choiceD.setText("d  "+question.answers[3]);

  f.repaint();
 }
 
 public void updateLeaderboard() {
  String toPutOnLeaderboard = "<html><table>";
  for(int i = 0; i<active.size(); i++) {
   toPutOnLeaderboard += "<tr><td>" +active.get(i).name + "</td><td width=\"50\" /><td>" + active.get(i).score + "</td></tr>";
  }
  toPutOnLeaderboard += "</table></html>";
  leaderBoardList.setText(toPutOnLeaderboard);
 }

 
 private void initGame() {
  f = new JFrame("Quiz game");
  f.setLayout(new BorderLayout());
  
  // Stuff for the panel on the right
  JPanel rightPanel = new JPanel(new BorderLayout());
  rightPanel.setBackground(Color.decode("#33CCFF"));
  ImageIcon logoIcon = new ImageIcon("src/images/trivial_logo_white.png");
  Image img = logoIcon.getImage();  
  Image newimg = img.getScaledInstance(348, 130,  java.awt.Image.SCALE_SMOOTH);  
  logoIcon = new ImageIcon(newimg);
  JLabel logo = new JLabel(logoIcon);
  logo.setBorder(BorderFactory.createEmptyBorder(20, 70, 50, 35));
  logo.setBackground(Color.WHITE);
  rightPanel.add(logo, BorderLayout.NORTH);
  
  JPanel leaderBoardHolder = new JPanel(new BorderLayout());
  leaderBoardHolder.setBackground(new Color(0,0,0,0));
  JPanel leaderBoardPanel = new JPanel(new GridLayout(2, 1));
  leaderBoardPanel.setBackground(new Color(0,0,0,0));
  JLabel leaderBoardLabel = new JLabel("leader board");
  leaderBoardLabel.setHorizontalAlignment(JLabel.CENTER);
  leaderBoardLabel.setForeground(Color.decode("#FF6633"));
  leaderBoardLabel.setFont(new Font("Helvitica", Font.PLAIN, 40));
  leaderBoardPanel.add(leaderBoardLabel);
  leaderBoardList = new JLabel("<html><table><tr><td>name1fadfjdkdfja</td><td width=\"50\" /><td>100</td></tr><tr><td>name2</td><td width=\"50\" /><td>10</td></tr></table></html>");
  leaderBoardList.setHorizontalAlignment(JLabel.CENTER);
  leaderBoardList.setFont(new Font("Helvitica", Font.PLAIN, 35));
  leaderBoardPanel.add(leaderBoardList);
  leaderBoardHolder.add(leaderBoardPanel, BorderLayout.NORTH);
  rightPanel.add(leaderBoardHolder, BorderLayout.CENTER);
  
  JLabel roomCodeLabel = new JLabel("LWSN");
  roomCodeLabel.setHorizontalAlignment(JLabel.RIGHT);
  roomCodeLabel.setForeground(Color.decode("#FF6633"));
  roomCodeLabel.setFont(new Font("Helvictica", Font.PLAIN, 50));
  rightPanel.add(roomCodeLabel, BorderLayout.SOUTH);
  // END stuff for the panel on the right.
  
  // Stuff for the main panel
  JPanel leftPanel = new JPanel(new GridLayout(7,1));
  leftPanel.setBackground(Color.decode("#bbeeff"));
  
  questionLabel = new JLabel("Lorem ipsum dolor sit amet, consectetur adipiscing elit?");
  questionLabel.setFont(new Font("Helvitica", Font.PLAIN, 40));
  choiceA = new JLabel("Lorem");
  choiceA.setFont(new Font("Helvitica", Font.PLAIN, 40));
  choiceB = new JLabel("Ipsum");
  choiceB.setFont(new Font("Helvitica", Font.PLAIN, 40));
  choiceC = new JLabel("Dolor");
  choiceC.setFont(new Font("Helvitica", Font.PLAIN, 40));
  choiceD = new JLabel("Amet");
  choiceD.setFont(new Font("Helvitica", Font.PLAIN, 40));
  
  leftPanel.add(questionLabel);
  leftPanel.add(choiceA);
  leftPanel.add(choiceB);
  leftPanel.add(choiceC);
  leftPanel.add(choiceD);
  
  
  // END stuff for the main panel

  f.add(rightPanel, BorderLayout.EAST);
  f.add(leftPanel, BorderLayout.CENTER);
  f.setSize(1200, 1200);
  f.setVisible(true);
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
  active = new ArrayList<Player>();
  observer = new RoomObserver(this);
  room = new RoomConnection("LWSN", observer);
 }
}