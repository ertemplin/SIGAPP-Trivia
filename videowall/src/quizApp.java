import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.purdue.cs.Message;
import edu.purdue.cs.RoomConnection;

public class quizApp extends Thread
{
	JFrame f;
	JPanel pTop, pCent, pBot, pScore, pRecap;
	JLabel label[];
	JLabel scores[];
	JTextField userName;
	Boolean qStart = false;
	Boolean sStart = false;
	RoomConnection room;
	long time = 0;
	String userNow;
	int count = 1;
	
	public void run()
	{
		initGame();
		
		while(true)
		{
			if(!qStart)
			{
				question();
				announceQuestion();
				qStart = true;
				time = System.currentTimeMillis();
			}
			else
			{
				if(sStart && System.currentTimeMillis()-time >= 5000)
				{
					qStart = false;
					sStart = false;
				}
				else if(!sStart && System.currentTimeMillis()-time >= 5000)
				{
					if(count++ % 5 == 0)
						recap();
					else
						score();
					sStart = true;
					time = System.currentTimeMillis();
				}
				else if(System.currentTimeMillis()-time < 5000)
				{
					label[5].setText(Long.toString(5-(System.currentTimeMillis()-time)/1000) + " second(s) left..");
				}
			}
		}
	}
	
	private void announceQuestion()
	{
		room.sendMessage(new Message("New Question"));
	}
	
	/* Modify the frame to the score list */
	public void score()
	{
		label[4].setText("~~~~~~~~~~Current Scores~~~~~~~~~~");
		f.remove(pCent);
		f.add(pScore, BorderLayout.CENTER);
		for(int i = 0; i < 10; i++)
		{
			scores[i].setText("BOB " + i + "---Correct---" + "---+20---");
			pScore.add(scores[i]);
		}
		scores[0].setText(userName.getText() + "---Correct---" + "---+20---");
		f.repaint();
	}
	
	/* Modify the frame to the recap list */
	public void recap()
	{
		label[4].setText(">>>>>>>>>>Total Scores<<<<<<<<<<");
		f.remove(pCent);
		f.add(pScore, BorderLayout.CENTER);
		for(int i = 0; i < 10; i++)
		{
			scores[i].setText(i + ".  BOB" + i + "---Number of Questions Correct---"+"-----Total Score-----");
			pScore.add(scores[i]);
		}
		scores[0].setText(userName.getText() + ". " + "---Number of Questions Correct---"+"-----Total Score-----");
		f.repaint();
	}
	
	/* Modify the frame to the question screen */
	public void question()
	{
		Question q = new Question();
		String s = q.question;
		label[4].setText("Question " + count + ". " + s);
		f.remove(pScore);
		f.add(pCent, BorderLayout.CENTER);
		label[0].setText(q.answers[0]);
		label[1].setText(q.answers[1]);
		label[2].setText(q.answers[2]);
		label[3].setText(q.answers[3]);
		for(int i = 0; i < 4; i++)     
			pCent.add(label[i]);
		f.repaint();
	}
	
	public void initGame()
	{
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
		
		room = new RoomConnection("LWSN", new RoomObserver());
	}
	
	public void createFrame()
	{
		f = new JFrame("Quiz game");
		f.setLayout(new BorderLayout());
	}
	
	public void createPanel()
	{
		pCent = new JPanel(new GridLayout(4, 1, 10, 10));
		pTop = new JPanel();
		pBot = new JPanel();
		pScore = new JPanel(new GridLayout(10, 1, 10, 10));
	}
	
	public void createLabel()
	{
		label = new JLabel[6];
		for(int i = 0; i < 4; i++)
		{
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
		for(int i = 0; i < 10; i++)
		{
			scores[i] = new JLabel("");
			scores[i].setHorizontalAlignment(SwingConstants.CENTER);
			scores[i].setFont(new Font("Georgia", Font.ITALIC, 15));
		}
	}
}