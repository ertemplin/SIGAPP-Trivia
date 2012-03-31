public class appStart
{
	public static void main(String[] args)
	{
		quizApp ap = new quizApp();
		ap.start();
		try
		{
			ap.join();
		}
		catch(Exception e){}
	}
}