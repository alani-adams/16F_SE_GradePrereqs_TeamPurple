package implementation;

import java.util.HashMap;
import java.util.HashSet;

/**
 * A class designed to store a course and its prerequisites
 */
public final class Course
{
	//Maps CRN to CourseData
	private static final HashMap<String,CourseData> AllCourses = new HashMap<String,CourseData>();
	//Maps CourseCode to CourseData
	private static final HashMap<String,CourseData> AllData = new HashMap<String,CourseData>();
	
	private CourseData Data;
	private Character Grade;
	private String CRN;

	/**
	 * Adds the course data to the internal list of all course data
	 */
	public static void RegisterCourse(String Code, String CRN, String Prereq)
	{
		CourseData CD;
		//For example, if "IT300" has two CRNs.
		if(!AllData.containsKey(Code))
		{
			CD = new CourseData(Code,Prereq);
			AllData.put(Code, CD);
		}
		else
		{
			CD = AllData.get(Code);
		}
		AllCourses.put(CRN, CD);
		
	}
	
	/**
	 * Creates a Course object from the given CRN
	 * @param CRN the CRN to look up
	 * @returns a Course with the given CRN
	 */
	public static Course GetFromCRN(String CRN)
	{
		return GetFromCRN(CRN,null);
	}
	
	/**
	 * Creates a Course object from the given CRN and gives it a grade
	 * @param CRN the CRN to look up
	 * @param Grade the grade earned
	 * @returns a Course with the given CRN and grade
	 */
	public static Course GetFromCRN(String CRN, Character Grade)
	{
		Course c = new Course(CRN, Grade);
		c.Data = AllCourses.get(CRN);
		return c;
	}
	
	private Course(String CRN, Character Grade)
	{
		this.CRN = CRN;
		this.Grade = Grade;
	}
	
	/**
	 * Gets the CRN of this course
	 * @returns the CRN of this course
	 */
	public String GetCRN()
	{
		return CRN;
	}
	/**
	 * Gets the Course Code of this course
	 * @returns the Course Code of this course
	 */
	public String GetCourseCode()
	{
		return Data.getCode();
	}
	
	/**
	 * Gets the grade made this course.
	 * @returns Grade 
	 * @throws NullPointerException if grade has not been set
	 */
	public char GetGrade()
	{
		if(Grade == null)
			throw new NullPointerException();
		return Grade;
	}
	
	/**
	 * Sets the grade made in this course.
	 * @param Grade The grade made in this course
	 */
	public void SetGrade(char Grade)
	{
		this.Grade = Grade;
	}
	
	/**
	 * Returns a TreeSet containing all of this course's Prerequisites
	 * @return a TreeSet containing all of this course's Prerequisites
	 */
	public HashSet<Prerequisite> GetPrerequisites()
	{
		return Data.GetPrerequisites();
	}
}


class CourseData
{
	private String Code;
	private final HashSet<Prerequisite> Prerequisites;
	
	public CourseData(String c, String p)
	{
		Code = c;
		Prerequisites = new HashSet<Prerequisite>();
		if(p != null)
			Prerequisites.add(new CoursePrerequisite(p,'C'));
	}
	
	public String getCode()
	{
		return Code;
	}
	
	public void addPrerequisite(Prerequisite P)
	{
		Prerequisites.add(P);
	}
	
	public HashSet<Prerequisite> GetPrerequisites()
	{
		return Prerequisites;
	}
}