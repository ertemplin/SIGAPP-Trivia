import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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

	public void run() {
		initGame();

		while(true) {
			if(!questionStart) {
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
	
	/* Modify the frame to the question screen */
	public void nextQuestion() {
		question = new Question();
		/*String s = question.question;
		label[4].setText("Question " + count + ". " + s);
		f.remove(pScore);
		f.add(pCent, BorderLayout.CENTER);
		label[0].setText(question.answers[0]);
		label[1].setText(question.answers[1]);
		label[2].setText(question.answers[2]);
		label[3].setText(question.answers[3]);
		for(int i = 0; i < 4; i++)     
			pCent.add(label[i]);
		f.repaint();*/
	}

	
	private void initGame() {
		f = new JFrame("Quiz game");
		f.setLayout(new BorderLayout());
		
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
		
		JPanel leaderBoardPanel = new JPanel(new GridLayout(2, 1));
		
		leaderBoardPanel.setBackground(new Color(0,0,0,0));
		JLabel leaderBoardLabel = new JLabel("leader board");
		leaderBoardPanel.add(leaderBoardLabel);
		JLabel leaderBoardList = new JLabel("Name\t300\n");
		leaderBoardList.setVerticalTextPosition(JLabel.CENTER);
		leaderBoardPanel.add(leaderBoardList, BorderLayout.NORTH);
		rightPanel.add(leaderBoardPanel);
		
		JLabel roomCodeLabel = new JLabel("LWSN");
		rightPanel.add(roomCodeLabel, BorderLayout.SOUTH);

		f.add(rightPanel, BorderLayout.EAST);
		f.setSize(1200, 1200);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		active = new ArrayList<Player>();
		observer = new RoomObserver(this);
		room = new RoomConnection("LWSN", observer);
	}
}