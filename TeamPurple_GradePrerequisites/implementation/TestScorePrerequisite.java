package implementation;

class TestScorePrerequsite extends Prerequisite 
{
	String section;
	int minimumScore;

	public ACTScorePrerequsite(String subject, int min)
	{
		section = subject;
		minimumScore = min;
	}

	@Override
	public boolean IsMetBy(Student stu)
    	{
   		if (stu.getTestScore(section) >= minimumScore)
   		return true;
		return false;
    	}
}
