package implementation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class that represents a student.
 * This class contains references to all of the student's courses.
 * 
 * @author Sebastian Snyder
 */
public class Student
{
	private final HashMap<String,ArrayList<Course>> Courses;
	private final String BANNER;
	private final Classification Class;
	
	/**
	 * @return the courses
	 */
	public ArrayList<Course> getCourses(String TermCode)
	{
		return Courses.get(TermCode);
	}
    
	public Student(String banner,String C)
	{
		BANNER = banner;
		Courses = new HashMap<String,ArrayList<Course>>();
		switch(C)
		{
			case "FR":
				Class = Classification.FRESHMAN;
				break;
			case "SO":
				Class = Classification.SOPHOMORE;
				break;
			case "JR":
				Class = Classification.JUNIOR;
				break;
			case "SR":
				Class = Classification.SENIOR;
				break;
			case "GR":
				Class = Classification.GRADUATE;
				break;
			default:
				Class = null;
		}
	}

	/**
	 * Gets this Student's Banner ID.
	 * @return the Student's Banner ID.
	 */
	public String getBanner()
	{
		return BANNER;
	}

	/**
	 * Adds a course to the list of this student's courses.
	 * Silently fails if the course has no time slots.
	 * @param course The course to add
	 */
	public void addCourse(Course c)
	{
		if(!c.hasTime())
		{
			return;
		}
		ArrayList<Course> Cs;
		String TC = c.getTermCode();
		if(!Courses.containsKey(TC))
		{
			Cs = new ArrayList<Course>();
			Cs.add(new ChapelCourse());
			Courses.put(TC, Cs);
		}
		else Cs = Courses.get(TC);
		Cs.add(c);
		//Cs.sort(Course.comparator());
	}

	/**
	 * Gets the set of courses this student is taking for a particular semester
	 * @param termcode the semester to check.
	 * @return an ArrayList of courses this student is taking for the given semester
	 */
	public ArrayList<Course> getCourseSet(String termcode)
	{
		return Courses.get(termcode);
	}
	
	/**
	 * Returns wheter or not a time range is free for a given time slot on a given day in a given semester
	 * @param Termcode the semester on which to test
	 * @param D the day to test on
	 * @param ST the start timecode
	 * @param ET the end timecode
	 * @return whether or not the timeslot is free
	 */
	public boolean SlotFree(String Termcode, Day D,short ST, short ET)
	{
		if(!Courses.containsKey(Termcode))
		{
			ArrayList<Course> Cs;
			Cs = new ArrayList<Course>();
			Cs.add(new ChapelCourse());
			Courses.put(Termcode, Cs);
		}
		for(Course c: Courses.get(Termcode))
		{
			if(!c.SlotFree(D,ST,ET))
			{
				return false;
			}
		}
		return true;
	}

	public Classification getClassification()
	{
		return Class;
	}
}
