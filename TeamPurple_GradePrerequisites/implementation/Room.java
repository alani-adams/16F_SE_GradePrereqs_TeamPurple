package implementation;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Sebastian Snyder
 * 
 */
public class Room
{
	private final String RoomNumber;
	private final int MaxSize;
	private HashSet<Course> Courses;
	
	public Room(String N, int S)
	{
		RoomNumber = N;
		MaxSize = S;
		Courses = new HashSet<>();
	}
	
	public void addCourse(Course C)
	{
		Courses.add(C);
	}
	
	public void removeCourse(Course C)
	{
		Courses.remove(C);
	}
	
	/**
	 * @return the roomNumber
	 */
	public String getRoomNumber()
	{
		return RoomNumber;
	}
	/**
	 * @return the maxSize
	 */
	public int getMaxSize()
	{
		return MaxSize;
	}
	
	/**
	 * Returns wheter or not a time range is free for a given time slot on a given day in a given semester
	 * @param Termcode the semester on which to test
	 * @param D the day to test on
	 * @param ST the start timecode
	 * @param ET the end timecode
	 * @return whether or not the timeslot is free
	 */
	public boolean SlotFree(Day D,short ST, short ET)
	{
		for(Course c: Courses)
		{
			if(!c.SlotFree(D,ST,ET))
			{
				return false;
			}
		}
		return true;
	}
}
