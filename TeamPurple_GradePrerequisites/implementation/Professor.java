package implementation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class that represents a professor.
 * This class stores all of the professor's courses.
 * 
 * @author Sebastian Snyder
 */
public class Professor
{
	private final String NAME;
	private final HashMap<String,ArrayList<Course>> Courses;
	
	public Professor(String name)
	{
        NAME = name;
        Courses = new HashMap<String,ArrayList<Course>>();
	}

	/**
	 * @return the courses
	 */
	public ArrayList<Course> getCourses(String TermCode)
	{
		return Courses.get(TermCode);
	}

	/**
	 * Gets this Professor's name.
	 * @return this Professor's name.
	 */
	public String getName()
	{
		return NAME;
	}

	public void addCourse(Course c)
	{
		ArrayList<Course> Cs;
		String TC = c.getTermCode();
		if(!Courses.containsKey(TC))
		{
			Cs = new ArrayList<Course>();
			Courses.put(TC, Cs);
		}
		else Cs = Courses.get(TC);
		Cs.add(c);
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
		for(Course c: Courses.get(Termcode))
		{
			if(!c.SlotFree(D,ST,ET))
			{
				return false;
			}
		}
		return true;
	}
}
