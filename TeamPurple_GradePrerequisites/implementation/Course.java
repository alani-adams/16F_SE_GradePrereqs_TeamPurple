package implementation;

import java.util.Comparator;
import java.util.HashSet;

/**
 * A class that represents a course and the semester it's in.
 * 
 * @author Sebastian Snyder
 */
public class Course
{
	private final String COURSE;
	private final String TERM_CODE;
	private final Room ROOM;
	private final HashSet<Student> Students;


	private final Professor Instructor;
	private final ScheduleData CourseTimes;
	
	/**
	 * @return the students
	 */
	public HashSet<Student> getStudents()
	{
		return Students;
	}

	public Course(String termcode, String course, Room R, Professor p)
	{
		TERM_CODE = termcode;
		COURSE = course;
		ROOM = R;
		
		if(R != null)
			R.addCourse(this);
		
		Instructor = p;
		Students = new HashSet<Student>();
		CourseTimes = new ScheduleData();
		if(p != null || !(this instanceof ChapelCourse))
			p.addCourse(this);
	}


	/**
	 * Gets the Course ID for this course
	 * @return the Course ID for this course
	 */
	public String getCourseID()
	{
		return TERM_CODE+" "+COURSE;
	}

	/**
	 * @return the Professor of this course
	 */
	public Professor getInstructor()
	{
		return Instructor;
	}
	
	/**
	 * Adds a student to the course
	 * @param s the Student to add
	 */
	public void addStudent(Student s)
	{
		Students.add(s);
	}	
	/**
	 * Sets the timeslot of this class for a given day of the week
	 * @param day The day to set
	 * @param timeStart The start time, where timeStart/100 is the
	 *        hour and timeStart%100 is the minute.
	 * @param timeEnd The end time, where timeEnd/100 is the hour
	 *        and timeEnd%100 is the minute.
	 */
	public void setClassPeriod(Day day, int timeStart, int timeEnd)
	{
		 CourseTimes.setData(day, timeStart, timeEnd);
	}


	public String getTermCode()
	{
		return TERM_CODE;
	}


	public int getFirstStartTime()
	{
		return CourseTimes.getFirstStartTime();
	}
	public int getFirstEndTime()
	{
		return CourseTimes.getFirstEndTime();
	}


	public static Comparator<Course> comparator()
	{
		return CourseComparator.instance;
	}


	public boolean hasTime()
	{
		try
		{
			getFirstStartTime();
			return true;
		}
		catch(IllegalStateException e)
		{
			return false;
		}
	}


	public boolean SlotFree(Day D, short ST, short ET)
	{
		return CourseTimes.SlotFree(D,ST,ET);
	}

	/**
	 * @return the room
	 */
	public Room getRoom()
	{
		return ROOM;
	}

}

class CourseComparator implements Comparator<Course>
{
	public static final Comparator<Course> instance = new CourseComparator();
	private CourseComparator() {}
	@Override
	public int compare(Course c1, Course c2)
	{
		return c1.getFirstStartTime()-c2.getFirstStartTime();
	}
}