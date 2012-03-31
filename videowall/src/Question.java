import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONObject;


public class Question
{
	public String question;
	public String answers[];
	public String correct;

	public Question()
	{
		try
		{
			URL oracle = new URL("http://pc.cs.purdue.edu:3000/random");
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
			
			String s = " ";
			String inputLine="";
			System.out.println(inputLine);
			while ((inputLine = in.readLine()) != null)
			{
				s+=inputLine;
				//System.out.println(inputLine);
			}
			in.close();

			JSONObject object = new JSONObject(s);
			answers = new String[4];
			question = object.getString("question");
			correct = object.getString("correctAnswer");
			answers[0] = correct;
			answers[1] = object.getString("incorrect1");
			answers[2] = object.getString("incorrect2");
			answers[3] = object.getString("incorrect3");
			answers = shuffleAnswers(answers);
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	private static String[] shuffleAnswers(String[] answers)
	{
		for(int i=0;i<answers.length;i++)
			answers[i] = answers[(int)Math.random()*answers.length];
		return answers;
	}
}