package implementation;

class ACTScorePrerequsite extends Prerequisite 
{
	String section;
	int minimumScore;

	public ACTScorePrerequsite(int score)
	{
		section = null;
		mininimumScore = score;
	}

	public ACTScorePrerequsite(String subject, int min)
	{
		section = subject;
		minimumScore = min;
	}

	@Override
    public boolean IsMetBy(Student stu)
    {
    	if (section != null)
    	{	
   			if (stu.getTestScore("ACT " + section) >= minimumScore)
   				return true;
   		}
   		else
   			if (stu.getTestScore("ACT") >= minimumScore)
   				return true;
   		return false;
    }

}