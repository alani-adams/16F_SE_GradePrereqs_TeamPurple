package implementation;

/**
 * 
 * @author Alani Peters, Sebastian Snyder
 *
 */
class TestScorePrerequisite extends Prerequisite 
{
	String section;
	int minimumScore;
	
	/**
	 * 
	 * @param subject
	 * @param min
	 */
	public TestScorePrerequisite(String subject, int min)
	{
		section = subject;
		minimumScore = min;
	}

	@Override
	public boolean IsMetBy(Student stu)
	{
		Integer Sc = stu.GetTestScore(section);
   		if (Sc != null && Sc >= minimumScore)
   			return true;
		return false;
	}
	
	@Override
	public String toString()
	{
		return "<"+section + ">("+minimumScore+")";
	}
}
