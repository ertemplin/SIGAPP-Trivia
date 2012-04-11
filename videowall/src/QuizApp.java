import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.purdue.cs.Message;
import edu.purdue.cs.RoomConnection;

public class QuizApp extends Thread
{
	JFrame f;
	JPanel pTop, pCent, pBot, pScore, pRecap;
	JLabel label[];
	JLabel scores[];
	Boolean qStart = false;
	Boolean sStart = false;
	RoomConnection room;
	Question question;
	long time = 0;
	String userNow;
	int count = 1;
	final int SECONDS = 15;
	RoomObserver observer;
	ArrayList<Player> active;
	ArrayList<Player> inactive;

	public void run() {
		initGame();

		while(true) {
			if(!qStart) {
				nextQuestion();
				announceQuestion();
				qStart = true;
				time = System.currentTimeMillis();
			}
			else {
				if(sStart && System.currentTimeMillis()-time >= SECONDS * 1000) {
					qStart = false;
					sStart = false;
					sort(active);
					announceAnswer();
				}
				else if(!sStart && System.currentTimeMillis()-time >= SECONDS * 1000) {
					count++;
					score();
					sStart = true;
					time = System.currentTimeMillis();
				}
				else if(System.currentTimeMillis()-time < SECONDS * 1000) {
					label[5].setText(Long.toString(SECONDS-(System.currentTimeMillis()-time)/1000) + " second(s) left..");
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
	
	/* Modify the frame to the score list */
	public void score() {
		label[4].setText("~~~~~~~~~~Current Scores~~~~~~~~~~");
		f.remove(pCent);
		f.add(pScore, BorderLayout.CENTER);
		for(int i=0;i<active.size();i++) {
			scores[i].setText(active.get(i).name + "---" + active.get(i).score + "---");
			pScore.add(scores[i]);
		}
		f.repaint();
	}
	
	/* Modify the frame to the recap list */
	public void recap() {
		label[4].setText(">>>>>>>>>>Total Scores<<<<<<<<<<");
		f.remove(pCent);
		f.add(pScore, BorderLayout.CENTER);
		for(int i = 0; i < 10; i++) {
			scores[i].setText(i + ".  BOB" + i + "---Number of Questions Correct---"+"-----Total Score-----");
			pScore.add(scores[i]);
		}
		f.repaint();
	}
	
	/* Modify the frame to the question screen */
	public void nextQuestion() {
		question = new Question();
		String s = question.question;
		label[4].setText("Question " + count + ". " + s);
		f.remove(pScore);
		f.add(pCent, BorderLayout.CENTER);
		label[0].setText(question.answers[0]);
		label[1].setText(question.answers[1]);
		label[2].setText(question.answers[2]);
		label[3].setText(question.answers[3]);
		for(int i = 0; i < 4; i++)     
			pCent.add(label[i]);
		f.repaint();
	}
	
	private void initGame() {
		createFrame();
		createPanel(); 
		createLabel();
		for(int i = 0; i < 4; i++)
			pCent.add(label[i]);
		pTop.add(label[4]);
		pBot.add(label[5]);

		f.add(pTop, BorderLayout.NORTH);
		f.add(pCent, BorderLayout.CENTER);
		f.add(pBot, BorderLayout.SOUTH);
		f.setSize(1200, 1200);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		active = new ArrayList<Player>();
		inactive = new ArrayList<Player>();
		observer = new RoomObserver(this);
		room = new RoomConnection("LWSN", observer);
	}
	
	private void createFrame() {
		f = new JFrame("Quiz game");
		f.setLayout(new BorderLayout());
	}
	
	private void createPanel() {
		pCent = new JPanel(new GridLayout(4, 1, 10, 10));
		pTop = new JPanel();
		pBot = new JPanel();
		pScore = new JPanel(new GridLayout(10, 1, 10, 10));
	}
	
	private void createLabel() {
		label = new JLabel[6];
		for(int i = 0; i < 4; i++) {
			label[i] = new JLabel("");
			label[i].setHorizontalAlignment(SwingConstants.CENTER);
			label[i].setFont(new Font("Serif", Font.BOLD, 20));
		}
		
		label[4] = new JLabel("");
		label[4].setHorizontalAlignment(SwingConstants.CENTER);
		label[4].setFont(new Font("Courier New", Font.BOLD, 20));
		label[5] = new JLabel("");
		label[5].setHorizontalAlignment(SwingConstants.CENTER);
		label[5].setFont(new Font("Courier New", Font.ITALIC, 30));
		
		scores = new JLabel[10];
		for(int i = 0; i < 10; i++) {
			scores[i] = new JLabel("");
			scores[i].setHorizontalAlignment(SwingConstants.CENTER);
			scores[i].setFont(new Font("Georgia", Font.ITALIC, 15));
		}
	}
}