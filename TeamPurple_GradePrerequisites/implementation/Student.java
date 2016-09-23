package implementation;

import java.util.ArrayList;

/**
 * Represents a student that has a banner ID that has
 * taken various courses.
 */
public class Student
{
	private String Banner;
	private ArrayList<Course> CoursesTaken;
	
	/**
	 * Creates a new student that has not taken any courses.
	 * @param BannerID The student's BannerID
	 */
	public Student(String BannerID)
	{
		CoursesTaken = new ArrayList<Course>();
		Banner = BannerID;
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
}
