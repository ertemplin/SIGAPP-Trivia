import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONObject;


public class Question
{
	public String question;
	public String answers[];
	public char correct;

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
				s+=inputLine;
			in.close();

			JSONObject object = new JSONObject(s);
			answers = new String[4];
			question = object.getString("question");
			String answer = object.getString("correctAnswer");
			answers[0] = answer;
			answers[1] = object.getString("incorrect1");
			answers[2] = object.getString("incorrect2");
			answers[3] = object.getString("incorrect3");
			answers = shuffleAnswers(answers);
			correct = findCorrect(answers, answer);
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	private static String[] shuffleAnswers(String[] answers)
	{
		for(int i=0;i<answers.length;i++)
		{
			String temp = answers[i];
			int index = (int)Math.random()*answers.length;
			answers[i] = answers[index];
			answers[index] = temp;
		}
		return answers;
	}
	
	private char findCorrect(String[] answers, String answer)
	{
		for(int i=0;i<answers.length;i++)
			if(answers[i].equals(answer))
				return (char)(i+97);
		return 'a';
	}
}