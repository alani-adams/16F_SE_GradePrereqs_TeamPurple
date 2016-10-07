package implementation;

import java.util.HashMap;
import java.util.HashSet;

/**
 * A class designed to store a course and its prerequisites
 * @author Sebastian Snyder
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
	 * Adds the course data to the internal list of all course data or adds
	 * a prerequisite to the specified course.
	 * @param Code The course code to register or add to
	 * @param Prereq The course's prerequisite. null may be passed in if none desired.
	 */
	public static void RegisterCourse(String Code, String Prereq)
	{
		CourseData CD;
		if(AllData.containsKey(Code))
			CD = AllData.get(Code);
		else
			CD = new CourseData(Code);
		
		CD.addPrerequisite(Prereq);
		
		AllData.put(Code, CD);
	}
	
	
	/**
	 * Adds the CRN to the internal list of sections and maps it to
	 * the specified course code.
	 * @param CRN The CRN to map.
	 * @param Code The Course code to map to.
	 */
	public static void RegisterCRN(String CRN, String Code)
	{
		if(AllCourses.containsKey(CRN))
		{
			//throw new IllegalArgumentException("CRN "+CRN+" already registered");// (as course "+AllCourses.get(CRN).getCode()+")");
			return;
		}
		if(AllData.containsKey(Code))
			AllCourses.put(CRN, AllData.get(Code));
		else
		{
			//throw new IllegalStateException("Course "+Code+" not yet registered.");
			return;
		}
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
		if(c.Data == null)
			throw new IllegalStateException("No Data For CRN "+CRN+" found.");
		return c;
	}

	/**
	 * Creates a Course object from the given Code
	 * @param Code the Code to look up
	 * @returns a Course with the given Code
	 */
	public static Course GetFromCode(String Code)
	{
		return GetFromCode(Code,null);
	}
	
	/**
	 * Creates a Course object from the given Code and gives it a grade
	 * @param Code the Code to look up
	 * @param Grade the grade earned
	 * @returns a Course with the given Code and grade
	 */
	public static Course GetFromCode(String Code, Character Grade)
	{
		Course c = new Course(null, Grade);
		c.Data = AllData.get(Code);
		if(c.Data == null)
			throw new IllegalStateException("No Data For Code "+Code+" found.");
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
	 * @returns Grade or null if not set
	 */
	public Character GetGrade()
	{
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
	
	@Override
	public String toString()
	{
		String s = "{"+Data.getCode();
		if(GetGrade() != null)
			s+="("+GetGrade()+")";
		for(Prerequisite p: Data.GetPrerequisites())
			s+=";"+p;
		return s+"}";
	}
}


class CourseData
{
	private String Code;
	private final HashSet<Prerequisite> Prerequisites;
	
	public CourseData(String c)
	{
		Code = c;
		Prerequisites = new HashSet<Prerequisite>();
	}
	
	public void addPrerequisite(String p)
	{
		if(p != null)
			Prerequisites.add(Prerequisite.Build(p));
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