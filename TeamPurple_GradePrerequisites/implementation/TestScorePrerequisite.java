package implementation;

class TestScorePrerequisite extends Prerequisite 
{
	String section;
	int minimumScore;

	public TestScorePrerequisite(String subject, int min)
	{
		section = subject;
		minimumScore = min;
	}

	@Override
	public boolean IsMetBy(Student stu)
    	{
   		if (stu.GetTestScore(section) >= minimumScore)
   		return true;
		return false;
    	}
}
