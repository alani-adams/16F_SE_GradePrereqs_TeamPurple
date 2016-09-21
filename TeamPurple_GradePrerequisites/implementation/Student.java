package implementation;

import java.util.ArrayList;

public class Student
{
	private String Banner;
	private ArrayList<Course> CoursesTaken;
	
	public Student()
	{
		CoursesTaken = new ArrayList<Course>();
	}
	
	public void SetBanner(String BannerID)
	{
		Banner = BannerID;
	}
	
	public void TakeCourse(Course C)
	{
		CoursesTaken.add(C);
	}
	
	public boolean CanTakeCourse(String CRN)
	{
		Banner = Banner + "";
		return false;
	}
}
