public class AppStart
{
	public static void main(String[] args)
	{
		QuizApp ap = new QuizApp();
		ap.start();
		try
		{
			ap.join();
		}
		catch(Exception e){}
	}
}