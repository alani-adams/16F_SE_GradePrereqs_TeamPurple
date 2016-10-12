package implementation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a student that has a banner ID that has
 * taken various courses.
 * @author Sebastian Snyder, Alani Peters
 */
public class Student
{
	private String Banner;
	private ArrayList<Course> CoursesTaken;
	private String Classification;
    //Maps TestType to TestScore
    private final HashMap<String,Integer> TestScores;
    
	/**
	 * Creates a new student that has not taken any courses.
	 * @param BannerID The student's BannerID
	 */
	public Student(String BannerID)
	{
		CoursesTaken = new ArrayList<Course>();
		Banner = BannerID;
    	TestScores = new HashMap<String,Integer>();
    	Classification = "FR";
	}
	
	/**
	 * Gets the Student's Banner ID
	 * @return the Student's Banner ID
	 */
	public String GetBanner()
	{
		return Banner;
	}
	
	/**
	 * Adds a Course to the list of Courses the student has taken.
	 * @param C The Course to add.
	 */
	public void TakeCourse(Course C)
	{
		CoursesTaken.add(C);
	}
	
	/**
	 * Returns an ArrayList containing all Courses taken by the student
	 * @return an ArrayList containing all Courses taken by the student
	 */
	public ArrayList<Course> GetTakenCourses()
	{
		return CoursesTaken;
	}
	
	/**
	 * A method that returns whether or not the Student has met
	 * all of the prerequisites for a given course.
	 * @param C The Course to check.
	 * @returns whether or not the Student has met all
	 * of the prerequisites.
	 */
	public boolean CanTakeCourse(Course C)
	{
		for(Prerequisite P : C.GetPrerequisites())
		{
			if(!P.IsMetBy(this))
				return false;
		}
		return true;
	}
    
    /**
     * 
     * @param T
     * @param S
     */
    public void SetTestScore(String T, int S)
    {
        TestScores.put(T, S);
    }
    
    /**
     * 
     * @param TestType
     * @return
     */
    public Integer GetTestScore(String TestType)
    {
    	if(TestScores.containsKey(TestType))
    		return TestScores.get(TestType);
    	else
    		return null;
    }
    
    /**
     * Gets the student's classification
     * @returns the student's classification
     */
    public String GetClassification()
    {
    	return Classification;
    }
    
    /**
     * Sets the student's classification
     * @param c the student's new classification
     */
    public void SetClassification(String c)
    {
    	Classification = c;
    }
    

	public static final String[] ClStrings = {"FR","SO","JR","SR","GR"};
	
    /**
     * Sets the student's classification if the new classification is better
     * @param c the student's new classification
     */
    public void SetClassificationMax(String c)
    {
    	for(String s:ClStrings)
    	{
    		if(Classification == s)
    		{
    			Classification = c;
    			break;
    		}
    		else if(c == s)
    			break;
    		
    	}
    }
}
